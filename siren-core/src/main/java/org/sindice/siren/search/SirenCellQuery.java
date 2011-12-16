/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.ToStringUtils;

/**
 * A Query that matches cells matching boolean combinations of term
 * queries, e.g. {@link SirenTermQuery}s or {@link SirenPhraseQuery}s.
 * <p>
 * Code taken from {@link BooleanQuery} and adapted for the Siren use case.
 */
public class SirenCellQuery
extends Query {

  private static final long serialVersionUID = 1L;

  private SirenPrimitiveQuery primitive;

  /** Constructs an empty cell query. */
  public SirenCellQuery() {}

  /** Constructs a cell query using the given primitive query */
  public SirenCellQuery(final SirenPrimitiveQuery query) {
    this.setQuery(query);
  }

  /**
   * Set the primitive SIREn query that will be wrapped by this cell query.
   */
  public void setQuery(final SirenPrimitiveQuery query) {
    this.primitive = query;
  }

  /**
   * Get the primitive SIREn query that is wrapped by this cell query.
   */
  public SirenPrimitiveQuery getQuery() {
    return this.primitive;
  }

  // Implement coord disabling.
  // Inherit javadoc.
  @Override
  public Similarity getSimilarity(final Searcher searcher) {
    return super.getSimilarity(searcher);
  }

  private int cellConstraintStart = 0;
  private int cellConstraintEnd = Integer.MAX_VALUE;

  /**
   * Set an interval contraint over the cell indexes. By default, try to match
   * all the cells. These constraints are inclusives.
   **/
  public void setConstraint(final int start, final int end) {
    this.cellConstraintStart = start;
    this.cellConstraintEnd = end;
  }

  /**
   * Set the cell index constraint. By default, try to match all the cells.
   **/
  public void setConstraint(final int index) {
    this.cellConstraintStart = index;
    this.cellConstraintEnd = index;
  }

  private class SirenCellWeight extends Weight {

    private static final long serialVersionUID = 1L;

    protected Similarity  similarity;

    protected Weight      primitiveWeight;

    public SirenCellWeight(final Searcher searcher) throws IOException {
      this.similarity = SirenCellQuery.this.getSimilarity(searcher);
      primitiveWeight = primitive.createWeight(searcher);
    }

    @Override
    public Query getQuery() {
      return SirenCellQuery.this;
    }

    @Override
    public float getValue() {
      return SirenCellQuery.this.getBoost();
    }

    @Override
    public float sumOfSquaredWeights()
    throws IOException {
      float sum = primitiveWeight.sumOfSquaredWeights();
      sum *= SirenCellQuery.this.getBoost() * SirenCellQuery.this.getBoost();
      return sum;
    }

    @Override
    public void normalize(float norm) {
      norm *= SirenCellQuery.this.getBoost(); // incorporate boost
      primitiveWeight.normalize(norm);
    }

    @Override
    public Explanation explain(final IndexReader reader, final int doc)
    throws IOException {
      throw new UnsupportedOperationException();
    }

    @Override
    public Scorer scorer(final IndexReader reader, final boolean scoreDocsInOrder, final boolean topScorer)
    throws IOException {
      final SirenCellScorer result = new SirenCellScorer(similarity,
                                                         cellConstraintStart,
                                                         cellConstraintEnd);
      // by default, we always return 'doc in order' and use the scorer #advance()
      result.setScorer((SirenPrimitiveScorer) primitiveWeight.scorer(reader, true, false));
      return result;
    }

  }

  @Override
  public Weight createWeight(final Searcher searcher)
  throws IOException {
    return new SirenCellWeight(searcher);
  }
  
  @Override
  public Query rewrite(final IndexReader reader)
  throws IOException {
    SirenPrimitiveQuery newQ = (SirenPrimitiveQuery) primitive.rewrite(reader);
    if (newQ == primitive) return this;
    SirenCellQuery bq = (SirenCellQuery) this.clone();
    bq.primitive = newQ;
    return bq;
  }

  @Override
  public void extractTerms(final Set<Term> terms) {
    this.primitive.extractTerms(terms);
  }

  @Override @SuppressWarnings("unchecked")
  public Object clone() {
    final SirenCellQuery clone = (SirenCellQuery) super.clone();
    clone.primitive = (SirenPrimitiveQuery) this.primitive.clone();
    clone.cellConstraintStart = this.cellConstraintStart;
    clone.cellConstraintEnd = this.cellConstraintEnd;
    return clone;
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String field) {
    final StringBuffer buffer = new StringBuffer();
    buffer.append("cell(");
    buffer.append(this.primitive.toString(field));
    buffer.append(")");

    if (this.getBoost() != 1.0f) {
      buffer.append(ToStringUtils.boost(this.getBoost()));
    }

    return buffer.toString();
  }

  /** Returns true iff <code>o</code> is equal to this. */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof SirenCellQuery)) return false;
    final SirenCellQuery other = (SirenCellQuery) o;
    return (this.getBoost() == other.getBoost()) &&
           this.cellConstraintStart == other.cellConstraintStart &&
           this.cellConstraintEnd == other.cellConstraintEnd &&
           this.primitive.equals(other.primitive);
  }

  /** Returns a hash code value for this object. */
  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost()) ^ primitive.hashCode();
  }

}
