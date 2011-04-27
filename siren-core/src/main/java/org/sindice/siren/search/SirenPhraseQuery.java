/**
 * Copyright 2009, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.Explanation.IDFExplanation;
import org.apache.lucene.util.ToStringUtils;

/**
 * A Query that matches cells containing a particular sequence of terms. A
 * SirenPhraseQuery is built for input like <code>"new york"</code>.
 * <p>
 * This query may be combined with other terms or queries with a
 * {@link BooleanQuery} or a {@link SirenCellQuery}.
 * <p>
 * Code taken from {@link PhraseQuery} and adapted for the Siren use case.
 */
public class SirenPhraseQuery
extends SirenPrimitiveQuery {

  private String          field;

  private final ArrayList<Term> terms       = new ArrayList<Term>(4);

  private final ArrayList<Integer> positions   = new ArrayList<Integer>(4);

  private int             maxPosition = 0;

  private int             slop        = 0;

  /** Constructs an empty phrase query. */
  public SirenPhraseQuery() {}

  /**
   * Sets the number of other words permitted between words in query phrase. If
   * zero, then this is an exact phrase search. For larger values this works
   * like a <code>WITHIN</code> or <code>NEAR</code> operator.
   * <p>
   * The slop is in fact an edit-distance, where the units correspond to moves
   * of terms in the query phrase out of position. For example, to switch the
   * order of two words requires two moves (the first move places the words atop
   * one another), so to permit re-orderings of phrases, the slop must be at
   * least two.
   * <p>
   * More exact matches are scored higher than sloppier matches, thus search
   * results are sorted by exactness.
   * <p>
   * The slop is zero by default, requiring exact matches.
   */
  public void setSlop(final int s) {
    slop = s;
  }

  /** Returns the slop. See setSlop(). */
  public int getSlop() {
    return slop;
  }

  /**
   * Adds a term to the end of the query phrase. The relative position of the
   * term is the one immediately after the last term added.
   */
  public void add(final Term term) {
    int position = 0;
    if (positions.size() > 0)
      position = (positions.get(positions.size() - 1)).intValue() + 1;

    this.add(term, position);
  }

  /**
   * Adds a term to the end of the query phrase. The relative position of the
   * term within the phrase is specified explicitly. This allows e.g. phrases
   * with more than one term at the same position or phrases with gaps (e.g. in
   * connection with stopwords).
   *
   * @param term
   * @param position
   */
  public void add(final Term term, final int position) {
    if (terms.size() == 0)
      field = term.field();
    else if (term.field() != field)
      throw new IllegalArgumentException(
        "All phrase terms must be in the same field: " + term);

    terms.add(term);
    positions.add(new Integer(position));
    if (position > maxPosition) maxPosition = position;
  }

  /** Returns the set of terms in this phrase. */
  public Term[] getTerms() {
    return terms.toArray(new Term[0]);
  }

  /**
   * Returns the relative positions of terms in this phrase.
   */
  public int[] getPositions() {
    final int[] result = new int[positions.size()];
    for (int i = 0; i < positions.size(); i++)
      result[i] = (positions.get(i)).intValue();
    return result;
  }

  private class SirenPhraseWeight
  extends Weight {

    private final Similarity similarity;

    private float            value;

    private final float      idf;

    private float            queryNorm;

    private float            queryWeight;

    private final IDFExplanation idfExp;

    public SirenPhraseWeight(final Searcher searcher) throws IOException {
      this.similarity = SirenPhraseQuery.this.getSimilarity(searcher);

      idfExp = similarity.idfExplain(terms, searcher);
      idf = idfExp.getIdf();
    }

    @Override
    public String toString() {
      return "weight(" + SirenPhraseQuery.this + ")";
    }

    @Override
    public Query getQuery() {
      return SirenPhraseQuery.this;
    }

    @Override
    public float getValue() {
      return value;
    }

    @Override
    public float sumOfSquaredWeights() {
      queryWeight = idf * SirenPhraseQuery.this.getBoost(); // compute query weight
      return queryWeight * queryWeight; // square it
    }

    @Override
    public void normalize(final float queryNorm) {
      this.queryNorm = queryNorm;
      queryWeight *= queryNorm; // normalize query weight
      value = queryWeight * idf; // idf for document
    }

    @Override
    public Scorer scorer(final IndexReader reader,
                         final boolean scoreDocsInOrder,
                         final boolean topScorer)
    throws IOException {
      if (terms.size() == 0) // optimize zero-term case
        return null;

      final TermPositions[] tps = new TermPositions[terms.size()];
      for (int i = 0; i < terms.size(); i++) {
        final TermPositions p = reader.termPositions(terms.get(i));
        if (p == null)
          return null;
        tps[i] = p;
      }

      if (slop == 0) { // optimize exact case
        return new SirenExactPhraseScorer(this, tps,
          SirenPhraseQuery.this.getPositions(), similarity, reader.norms(field));
      }
      else {
        throw new UnsupportedOperationException();
      }
    }

    @Override
    public Explanation explain(final IndexReader reader, final int doc)
    throws IOException {

      final Explanation result = new Explanation();
      result.setDescription("weight(" + this.getQuery() + " in " + doc +
                            "), product of:");

      final StringBuffer docFreqs = new StringBuffer();
      final StringBuffer query = new StringBuffer();
      query.append('\"');
      docFreqs.append(idfExp.explain());
      for (int i = 0; i < terms.size(); i++) {
        if (i != 0) {
          query.append(" ");
        }

        final Term term = terms.get(i);

        query.append(term.text());
      }
      query.append('\"');

      final Explanation idfExpl = new Explanation(idf, "idf(" + field + ": " +
                                                       docFreqs + ")");

      // explain query weight
      final Explanation queryExpl = new Explanation();
      queryExpl.setDescription("queryWeight(" + this.getQuery() +
                               "), product of:");

      final Explanation boostExpl = new Explanation(SirenPhraseQuery.this.getBoost(), "boost");
      if (SirenPhraseQuery.this.getBoost() != 1.0f) queryExpl.addDetail(boostExpl);
      queryExpl.addDetail(idfExpl);

      final Explanation queryNormExpl = new Explanation(queryNorm, "queryNorm");
      queryExpl.addDetail(queryNormExpl);

      queryExpl.setValue(boostExpl.getValue() * idfExpl.getValue() *
                         queryNormExpl.getValue());

      result.addDetail(queryExpl);

      // explain field weight
      final Explanation fieldExpl = new Explanation();
      fieldExpl.setDescription("fieldWeight("+field+":"+query+" in "+doc+
                               "), product of:");

      final SirenPhraseScorer scorer = (SirenPhraseScorer) this.scorer(reader, true, false);
      if (scorer == null) {
        return new Explanation(0.0f, "no matching docs");
      }
      final Explanation tfExplanation = new Explanation();
      final int d = scorer.advance(doc);
      final float phraseFreq = (d == doc) ? scorer.currentFreq() : 0.0f;
      tfExplanation.setValue(similarity.tf(phraseFreq));
      tfExplanation.setDescription("tf(phraseFreq=" + phraseFreq + ")");

      fieldExpl.addDetail(tfExplanation);
      fieldExpl.addDetail(idfExpl);

      final Explanation fieldNormExpl = new Explanation();
      final byte[] fieldNorms = reader.norms(field);
      final float fieldNorm =
        fieldNorms != null && doc < fieldNorms.length ? Similarity.decodeNorm(fieldNorms[doc]) : 1.0f;
      fieldNormExpl.setValue(fieldNorm);
      fieldNormExpl.setDescription("fieldNorm(field="+field+", doc="+doc+")");
      fieldExpl.addDetail(fieldNormExpl);

      fieldExpl.setValue(tfExplanation.getValue() *
                         idfExpl.getValue() *
                         fieldNormExpl.getValue());

      result.addDetail(fieldExpl);

      return result;
    }
  }

  @Override
  public Weight createWeight(final Searcher searcher)
  throws IOException {
    if (terms.size() == 1) { // optimize one-term case
      final Term term = terms.get(0);
      final SirenTermQuery termQuery = new SirenTermQuery(term);
      termQuery.setBoost(this.getBoost());
      return termQuery.createWeight(searcher);
    }
    return new SirenPhraseWeight(searcher);
  }

  /**
   * @see org.apache.lucene.search.Query#extractTerms(java.util.Set)
   */
  @Override
  public void extractTerms(final Set<Term> queryTerms) {
    queryTerms.addAll(terms);
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String f) {
    final StringBuffer buffer = new StringBuffer();
    if (field != null && !field.equals(f)) {
      buffer.append(field);
      buffer.append(":");
    }

    buffer.append("\"");
    final String[] pieces = new String[maxPosition + 1];
    for (int i = 0; i < terms.size(); i++) {
      final int pos = (positions.get(i)).intValue();
      String s = pieces[pos];
      if (s == null) {
        s = (terms.get(i)).text();
      }
      else {
        s = s + "|" + (terms.get(i)).text();
      }
      pieces[pos] = s;
    }
    for (int i = 0; i < pieces.length; i++) {
      if (i > 0) {
        buffer.append(' ');
      }
      final String s = pieces[i];
      if (s == null) {
        buffer.append('?');
      }
      else {
        buffer.append(s);
      }
    }
    buffer.append("\"");

    if (slop != 0) {
      buffer.append("~");
      buffer.append(slop);
    }

    buffer.append(ToStringUtils.boost(this.getBoost()));

    return buffer.toString();
  }

  /** Returns true iff <code>o</code> is equal to this. */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof SirenPhraseQuery)) return false;
    final SirenPhraseQuery other = (SirenPhraseQuery) o;
    return (this.getBoost() == other.getBoost()) && (this.slop == other.slop) &&
           this.terms.equals(other.terms) &&
           this.positions.equals(other.positions);
  }

  /** Returns a hash code value for this object. */
  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost()) ^ slop ^ terms.hashCode() ^
           positions.hashCode();
  }

}
