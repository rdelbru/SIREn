/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.SimilarityDelegator;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.ToStringUtils;

/**
 * A Query that matches a boolean combination of term
 * queries, e.g. {@link SirenTermQuery}s or {@link SirenPhraseQuery}s.
 * <p>
 * Code taken from {@link BooleanQuery} and adapted for the Siren use case.
 */
public class SirenBooleanQuery
extends SirenPrimitiveQuery {

  private static final long serialVersionUID = 1L;

  private static int maxClauseCount = 1024;

  /**
   * Return the maximum number of clauses permitted, 1024 by default. Attempts
   * to add more than the permitted number of clauses cause
   * {@link TooManyClauses} to be thrown.
   *
   * @see #setMaxClauseCount(int)
   */
  public static int getMaxClauseCount() {
    return maxClauseCount;
  }

  /**
   * Set the maximum number of clauses permitted per BooleanQuery. Default value
   * is 1024.
   * <p>
   * TermQuery clauses are generated from for example prefix queries and fuzzy
   * queries. Each TermQuery needs some buffer space during search, so this
   * parameter indirectly controls the maximum buffer requirements for query
   * search.
   * <p>
   * When this parameter becomes a bottleneck for a Query one can use a Filter.
   * For example instead of a {@code TermRangeQuery} one can use a
   * {@code TermRangeFilter}.
   * <p>
   * Normally the buffers are allocated by the JVM. When using for example
   * {@code org.apache.lucene.store.MMapDirectory} the buffering is left to the
   * operating system.
   */
  public static void setMaxClauseCount(final int maxClauseCount) {
    if (maxClauseCount < 1)
      throw new IllegalArgumentException("maxClauseCount must be >= 1");
    SirenBooleanQuery.maxClauseCount = maxClauseCount;
  }

  private ArrayList<SirenBooleanClause> clauses = new ArrayList<SirenBooleanClause>();

  private boolean   disableCoord;

  /** Constructs an empty boolean query. */
  public SirenBooleanQuery() {}

  /**
   * Constructs an empty boolean query. {@link Similarity#coord(int,int)} may be
   * disabled in scoring, as appropriate. For example, this score factor does
   * not make sense for most automatically generated queries, like
   * {@link WildcardQuery} and {@link FuzzyQuery}.
   *
   * @param disableCoord
   *          disables {@link Similarity#coord(int,int)} in scoring.
   */
  public SirenBooleanQuery(final boolean disableCoord) {
    this.disableCoord = disableCoord;
  }

  /**
   * Returns true iff {@link Similarity#coord(int,int)} is disabled in scoring
   * for this query instance.
   *
   * @see #SirenCellQuery(boolean)
   */
  public boolean isCoordDisabled() {
    return disableCoord;
  }

  // Implement coord disabling.
  // Inherit javadoc.
  @Override
  public Similarity getSimilarity(final Searcher searcher) {
    Similarity result = super.getSimilarity(searcher);
    if (disableCoord) { // disable coord as requested
      result = new SimilarityDelegator(result) {

        private static final long serialVersionUID = 1L;

        @Override
        public float coord(final int overlap, final int maxOverlap) {
          return 1.0f;
        }
      };
    }
    return result;
  }

  /**
   * Adds a clause to a boolean query.
   *
   * @throws TooManyClauses
   *           if the new number of clauses exceeds the maximum clause number
   * @see #getMaxClauseCount()
   */
  public void add(final SirenPrimitiveQuery query, final SirenBooleanClause.Occur occur) {
    this.add(new SirenBooleanClause(query, occur));
  }

  /**
   * Adds a clause to a SIREn cell query.
   *
   * @throws TooManyClauses
   *           if the new number of clauses exceeds the maximum clause number
   * @see #getMaxClauseCount()
   */
  public void add(final SirenBooleanClause clause) {
    if (clauses.size() >= maxClauseCount) throw new TooManyClauses();
    clauses.add(clause);
  }

  /** Returns the set of clauses in this query. */
  public SirenBooleanClause[] getClauses() {
    return clauses.toArray(new SirenBooleanClause[clauses.size()]);
  }

  /** Returns the list of clauses in this query. */
  public List<SirenBooleanClause> clauses() {
    return clauses;
  }

  private class SirenBooleanWeight extends Weight {

    private static final long serialVersionUID = 1L;

    protected Similarity similarity;

    protected ArrayList<Weight>  weights;

    public SirenBooleanWeight(final Searcher searcher) throws IOException {
      this.similarity = SirenBooleanQuery.this.getSimilarity(searcher);
      weights = new ArrayList<Weight>(clauses.size());
      for (int i = 0; i < clauses.size(); i++) {
        final SirenBooleanClause c = clauses.get(i);
        weights.add(c.getQuery().createWeight(searcher));
      }
    }

    @Override
    public Query getQuery() {
      return SirenBooleanQuery.this;
    }

    @Override
    public float getValue() {
      return SirenBooleanQuery.this.getBoost();
    }

    @Override
    public float sumOfSquaredWeights()
    throws IOException {
      float sum = 0.0f;
      for (int i = 0; i < weights.size(); i++) {
        final SirenBooleanClause c = clauses.get(i);
        final Weight w = weights.get(i);
        // call sumOfSquaredWeights for all clauses in case of side effects
        final float s = w.sumOfSquaredWeights(); // sum sub weights
        if (!c.isProhibited()) {
        // only add to sum for non-prohibited clauses
          sum += s;
        }
      }

      sum *= SirenBooleanQuery.this.getBoost() * SirenBooleanQuery.this.getBoost(); // boost each sub-weight

      return sum;
    }

    @Override
    public void normalize(float norm) {
      norm *= SirenBooleanQuery.this.getBoost(); // incorporate boost
      for (final Weight weight : weights) {
        // normalize all clauses, (even if prohibited in case of side affects)
        weight.normalize(norm);
      }
    }

    @Override
    public Explanation explain(final IndexReader reader, final int doc)
    throws IOException {
      throw new UnsupportedOperationException();
    }

    @Override
    public Scorer scorer(final IndexReader reader, final boolean scoreDocsInOrder, final boolean topScorer)
    throws IOException {
      final SirenBooleanScorer result = new SirenBooleanScorer(similarity);

      for (int i = 0 ; i < weights.size(); i++) {
        final SirenBooleanClause c = clauses.get(i);
        final Weight w = weights.get(i);
        final SirenPrimitiveScorer subScorer = (SirenPrimitiveScorer) w.scorer(reader, true, false);
        if (subScorer != null) {
          result.add(subScorer, c.isRequired(), c.isProhibited());
        }
        else if (c.isRequired()) {
          return null;
        }
      }

      return result;
    }

  }

  @Override
  public Weight createWeight(final Searcher searcher)
  throws IOException {
    return new SirenBooleanWeight(searcher);
  }

  @Override
  public Query rewrite(final IndexReader reader)
  throws IOException {
    // TODO we cannot optimise 1-clause queries. The query will be rewritten
    // into a single {@link SirenTermQuery} without any cell index constraint.
    return this; // no clauses rewrote
  }

  @Override
  public void extractTerms(final Set<Term> terms) {
    for (final SirenBooleanClause clause : clauses) {
      clause.getQuery().extractTerms(terms);
    }
  }

  @Override @SuppressWarnings("unchecked")
  public Object clone() {
    final SirenBooleanQuery clone = (SirenBooleanQuery) super.clone();
    clone.clauses = (ArrayList<SirenBooleanClause>) this.clauses.clone();
    return clone;
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String field) {
    final StringBuffer buffer = new StringBuffer();
    final boolean needParens = (this.getBoost() != 1.0);
    if (needParens) {
      buffer.append("(");
    }

    for (int i = 0; i < clauses.size(); i++) {
      final SirenBooleanClause c = clauses.get(i);
      if (c.isProhibited())
        buffer.append("-");
      else if (c.isRequired()) buffer.append("+");

      final Query subQuery = c.getQuery();
      if (subQuery != null) {
        if (subQuery instanceof SirenBooleanQuery) { // wrap sub-bools in parens
          buffer.append("(");
          buffer.append(subQuery.toString(field));
          buffer.append(")");
        }
        else {
          buffer.append(subQuery.toString(field));
        }
      }
      else {
        buffer.append("null");
      }

      if (i != clauses.size() - 1) buffer.append(" ");
    }

    if (needParens) {
      buffer.append(")");
    }

    if (this.getBoost() != 1.0f) {
      buffer.append(ToStringUtils.boost(this.getBoost()));
    }

    return buffer.toString();
  }

  /** Returns true iff <code>o</code> is equal to this. */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof SirenBooleanQuery)) return false;
    final SirenBooleanQuery other = (SirenBooleanQuery) o;
    return (this.getBoost() == other.getBoost()) &&
           this.clauses.equals(other.clauses);
  }

  /** Returns a hash code value for this object. */
  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost()) ^ clauses.hashCode();
  }

  /**
   * Thrown when an attempt is made to add more than
   * {@link #getMaxClauseCount()} clauses. This typically happens if a
   * PrefixQuery, FuzzyQuery, WildcardQuery, or TermRangeQuery is expanded to
   * many terms during search.
   */
  public static class TooManyClauses
  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TooManyClauses() {}

    @Override
    public String getMessage() {
      return "maxClauseCount is set to " + maxClauseCount;
    }
  }

}
