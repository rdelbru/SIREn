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
import org.sindice.siren.similarity.SirenWeight;

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

  private class SirenCellWeight extends SirenWeight {

    private static final long serialVersionUID = 1L;

    protected Weight      primitiveWeight;

    public SirenCellWeight(final Searcher searcher) throws IOException {
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
  public void extractTerms(final Set<Term> terms) {
    this.primitive.extractTerms(terms);
  }

  @Override @SuppressWarnings("unchecked")
  public Object clone() {
    final SirenCellQuery clone = (SirenCellQuery) super.clone();
    clone.primitive = (SirenPrimitiveQuery) this.primitive.clone();
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
           this.primitive.equals(other.primitive);
  }

  /** Returns a hash code value for this object. */
  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost()) ^ primitive.hashCode();
  }

}
