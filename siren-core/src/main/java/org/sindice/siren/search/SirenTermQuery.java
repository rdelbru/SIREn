/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 9 Feb 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.Explanation.IDFExplanation;
import org.apache.lucene.util.ToStringUtils;

/**
 * A Query that matches entities containing a term. Provides an interface to
 * iterate over the entities, tuples and cells containing the term. This may be
 * combined with other terms with a {@link SirenCellQuery}, a {@link SirenTupleQuery} or a
 * {@link BooleanQuery}.
 */
public class SirenTermQuery
extends SirenPrimitiveQuery {

  private final Term term;

  protected class SirenTermWeight
  extends Weight {

    private final Similarity similarity;

    private float            value;

    private final float      idf;

    private float            queryNorm;

    private float            queryWeight;

    private final IDFExplanation   idfExp;

    public SirenTermWeight(final Searcher searcher) throws IOException {
      this.similarity = SirenTermQuery.this.getSimilarity(searcher);
      idfExp = similarity.idfExplain(term, searcher);
      idf = idfExp.getIdf();
    }

    @Override
    public String toString() {
      return "weight(" + SirenTermQuery.this + ")";
    }

    @Override
    public Query getQuery() {
      return SirenTermQuery.this;
    }

    @Override
    public float getValue() {
      return value;
    }

    @Override
    public float sumOfSquaredWeights() {
      queryWeight = idf * SirenTermQuery.this.getBoost(); // compute query weight
      return queryWeight * queryWeight; // square it
    }

    @Override
    public void normalize(final float queryNorm) {
      this.queryNorm = queryNorm;
      queryWeight *= queryNorm; // normalize query weight
      value = queryWeight * idf; // idf for document
    }

    @Override
    public Scorer scorer(final IndexReader reader, final boolean scoreDocsInOrder,
                         final boolean topScorer)
    throws IOException {
      final TermPositions termPositions = reader.termPositions(term);
      if (termPositions == null) {
        return null;
      }

      return new SirenTermScorer(this, termPositions, similarity,
        reader.norms(term.field()));
    }

    @Override
    public Explanation explain(final IndexReader reader, final int doc)
    throws IOException {

      final ComplexExplanation result = new ComplexExplanation();
      result.setDescription("weight(" + this.getQuery() + " in " + doc +
                            "), product of:");

      final Explanation expl = new Explanation(idf, idfExp.explain());

      // explain query weight
      final Explanation queryExpl = new Explanation();
      queryExpl.setDescription("queryWeight(" + this.getQuery() +
                               "), product of:");

      final Explanation boostExpl = new Explanation(SirenTermQuery.this.getBoost(), "boost");
      if (SirenTermQuery.this.getBoost() != 1.0f)
        queryExpl.addDetail(boostExpl);
      queryExpl.addDetail(expl);

      final Explanation queryNormExpl = new Explanation(queryNorm,"queryNorm");
      queryExpl.addDetail(queryNormExpl);

      queryExpl.setValue(boostExpl.getValue() *
                         expl.getValue() *
                         queryNormExpl.getValue());

      result.addDetail(queryExpl);

      // explain field weight
      final String field = term.field();
      final ComplexExplanation fieldExpl = new ComplexExplanation();
      fieldExpl.setDescription("fieldWeight("+term+" in "+doc+
                               "), product of:");

      final Explanation tfExplanation = new Explanation();
      int tf = 0;
      final TermDocs termDocs = reader.termDocs(term);
      if (termDocs != null) {
        try {
          if (termDocs.skipTo(doc) && termDocs.doc() == doc) {
            tf = termDocs.freq();
          }
        } finally {
          termDocs.close();
        }
        tfExplanation.setValue(similarity.tf(tf));
        tfExplanation.setDescription("tf(termFreq("+term+")="+tf+")");
      } else {
        tfExplanation.setValue(0.0f);
        tfExplanation.setDescription("no matching term");
      }
      fieldExpl.addDetail(tfExplanation);
      fieldExpl.addDetail(expl);

      final Explanation fieldNormExpl = new Explanation();
      final byte[] fieldNorms = reader.norms(field);
      final float fieldNorm =
        fieldNorms != null && doc < fieldNorms.length ? Similarity.decodeNorm(fieldNorms[doc]) : 1.0f;
      fieldNormExpl.setValue(fieldNorm);
      fieldNormExpl.setDescription("fieldNorm(field="+field+", doc="+doc+")");
      fieldExpl.addDetail(fieldNormExpl);

      fieldExpl.setMatch(Boolean.valueOf(tfExplanation.isMatch()));
      fieldExpl.setValue(tfExplanation.getValue() *
                         expl.getValue() *
                         fieldNormExpl.getValue());

      result.addDetail(fieldExpl);
      result.setMatch(fieldExpl.getMatch());

      // combine them
      result.setValue(queryExpl.getValue() * fieldExpl.getValue());

      if (queryExpl.getValue() == 1.0f)
        return fieldExpl;

      return result;
    }

  }

  /** Constructs a query for the term <code>t</code>. */
  public SirenTermQuery(final Term t) {
    term = t;
  }

  /** Returns the term of this query. */
  public Term getTerm() {
    return term;
  }

  @Override
  public Weight createWeight(final Searcher searcher)
  throws IOException {
    return new SirenTermWeight(searcher);
  }

  @Override
  public void extractTerms(final Set<Term> terms) {
    terms.add(this.getTerm());
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String field) {
    final StringBuffer buffer = new StringBuffer();
    if (!term.field().equals(field)) {
      buffer.append(term.field());
      buffer.append(":");
    }
    buffer.append(term.text());
    buffer.append(ToStringUtils.boost(this.getBoost()));
    return buffer.toString();
  }

  /** Returns true iff <code>o</code> is equal to this. */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof SirenTermQuery)) return false;
    final SirenTermQuery other = (SirenTermQuery) o;
    return (this.getBoost() == other.getBoost()) &&
           this.term.equals(other.term);
  }

  /** Returns a hash code value for this object. */
  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost()) ^ term.hashCode();
  }

}
