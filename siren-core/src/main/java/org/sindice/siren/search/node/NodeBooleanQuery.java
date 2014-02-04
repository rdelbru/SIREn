/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sindice.siren.search.node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;
import org.sindice.siren.search.node.TwigQuery.TwigWeight;

/**
 * A {@link NodeQuery} that matches a boolean combination of primitive queries, e.g.,
 * {@link NodeTermQuery}s, {@link NodePhraseQuery}s, {@link NodeBooleanQuery}s,
 * ...
 *
 * <p>
 *
 * Code taken from {@link BooleanQuery} and adapted for the Siren use case.
 */
public class NodeBooleanQuery extends NodeQuery {

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
   */
  public static void setMaxClauseCount(final int maxClauseCount) {
    if (maxClauseCount < 1)
      throw new IllegalArgumentException("maxClauseCount must be >= 1");
    NodeBooleanQuery.maxClauseCount = maxClauseCount;
  }

  protected ArrayList<NodeBooleanClause> clauses = new ArrayList<NodeBooleanClause>();

  /** Constructs an empty boolean query. */
  public NodeBooleanQuery() {}

  @Override
  public void setLevelConstraint(final int levelConstraint) {
    super.setLevelConstraint(levelConstraint);
    // keep clauses synchronised
    for (final NodeBooleanClause clause : clauses) {
      clause.getQuery().setLevelConstraint(levelConstraint);
    }
  }

  @Override
  public void setNodeConstraint(final int lowerBound, final int upperBound) {
    super.setNodeConstraint(lowerBound, upperBound);
    // keep clauses synchronised
    for (final NodeBooleanClause clause : clauses) {
      clause.getQuery().setNodeConstraint(lowerBound, upperBound);
    }
  }

  @Override
  protected void setAncestorPointer(final NodeQuery ancestor) {
    super.setAncestorPointer(ancestor);
    // keep clauses synchronised
    for (final NodeBooleanClause clause : clauses) {
      clause.getQuery().setAncestorPointer(ancestor);
    }
  }

  /**
   * Adds a clause to a boolean query.
   *
   * @throws TooManyClauses
   *           if the new number of clauses exceeds the maximum clause number
   * @see #getMaxClauseCount()
   */
  public void add(final NodeQuery query, final NodeBooleanClause.Occur occur) {
    this.add(new NodeBooleanClause(query, occur));
  }

  /**
   * Adds a clause to a boolean query.
   *
   * @throws TooManyClauses
   *           if the new number of clauses exceeds the maximum clause number
   * @see #getMaxClauseCount()
   */
  public void add(final NodeBooleanClause clause) {
    if (clauses.size() >= maxClauseCount) {
      throw new TooManyClauses();
    }
    clauses.add(clause);
    // keep clause synchronised in term of constraint management
    clause.getQuery().setLevelConstraint(levelConstraint);
    clause.getQuery().setNodeConstraint(lowerBound, upperBound);
    clause.getQuery().setAncestorPointer(ancestor);
  }

  /** Returns the set of clauses in this query. */
  public NodeBooleanClause[] getClauses() {
    return clauses.toArray(new NodeBooleanClause[clauses.size()]);
  }

  /** Returns the list of clauses in this query. */
  public List<NodeBooleanClause> clauses() {
    return clauses;
  }

  /**
   * Returns an iterator on the clauses in this query. It implements the
   * {@link Iterable} interface to make it possible to do:
   * <pre>for (SirenBooleanClause clause : booleanQuery) {}</pre>
   */
  public final Iterator<NodeBooleanClause> iterator() {
    return this.clauses().iterator();
  }

  /**
   * Expert: An abstract weight for queries with node boolean clauses.
   * <p>
   * This abstract class enables to use the {@link NodeBooleanScorer} by many
   * different query implementation.
   * <p>
   * It is subclassed by {@link NodeBooleanWeight}, {@link TwigWeight}.
   */
  static abstract class AbstractNodeBooleanWeight extends Weight {

    /** The Similarity implementation. */
    protected Similarity similarity;
    protected ArrayList<Weight> weights;

    public AbstractNodeBooleanWeight(final IndexSearcher searcher)
    throws IOException {
      this.similarity = searcher.getSimilarity();
      this.initWeights(searcher);
    }

    protected abstract void initWeights(final IndexSearcher searcher) throws IOException;

    public float coord(final int overlap, final int maxOverlap) {
      return similarity.coord(overlap, maxOverlap);
    }

  }

  /**
   * Expert: the Weight for {@link NodeBooleanQuery}, used to
   * normalize, score and explain these queries.
   */
  public class NodeBooleanWeight extends AbstractNodeBooleanWeight {

    public NodeBooleanWeight(final IndexSearcher searcher)
    throws IOException {
      super(searcher);
    }

    @Override
    protected void initWeights(final IndexSearcher searcher) throws IOException {
      weights = new ArrayList<Weight>(clauses.size());
      for (int i = 0; i < clauses.size(); i++) {
        final NodeBooleanClause c = clauses.get(i);
        final NodeQuery q = c.getQuery();

        // pass to child query the node contraints
        q.setNodeConstraint(lowerBound, upperBound);
        q.setLevelConstraint(levelConstraint);

        // transfer ancestor pointer to child
        q.setAncestorPointer(ancestor);

        weights.add(q.createWeight(searcher));
      }
    }

    @Override
    public String toString() {
      return "weight(" + NodeBooleanQuery.this + ")";
    }

    @Override
    public Query getQuery() {
      return NodeBooleanQuery.this;
    }

    @Override
    public float getValueForNormalization()
    throws IOException {
      float sum = 0.0f;
      for (int i = 0; i < weights.size(); i++) {
        // call sumOfSquaredWeights for all clauses in case of side effects
        final float s = weights.get(i).getValueForNormalization(); // sum sub weights
        if (!clauses.get(i).isProhibited()) {
        // only add to sum for non-prohibited clauses
          sum += s;
        }
      }

      // boost each sub-weight
      sum *= NodeBooleanQuery.this.getBoost() * NodeBooleanQuery.this.getBoost();

      return sum;
    }

    @Override
    public void normalize(final float norm, float topLevelBoost) {
      // incorporate boost
      topLevelBoost *= NodeBooleanQuery.this.getBoost();
      for (final Weight w : weights) {
        // normalize all clauses, (even if prohibited in case of side affects)
        w.normalize(norm, topLevelBoost);
      }
    }

    @Override
    public Explanation explain(final AtomicReaderContext context, final int doc)
    throws IOException {
      final ComplexExplanation sumExpl = new ComplexExplanation();
      sumExpl.setDescription("sum of:");
      int coord = 0;
      float sum = 0.0f;
      boolean fail = false;
      final Iterator<NodeBooleanClause> cIter = clauses.iterator();
      for (final Weight w : weights) {
        final NodeBooleanClause c = cIter.next();
        if (w.scorer(context, true, true, context.reader().getLiveDocs()) == null) {
          if (c.isRequired()) {
            fail = true;
            final Explanation r = new Explanation(0.0f, "no match on required " +
            		"clause (" + c.getQuery().toString() + ")");
            sumExpl.addDetail(r);
          }
          continue;
        }
        final Explanation e = w.explain(context, doc);
        if (e.isMatch()) {
          if (!c.isProhibited()) {
            sumExpl.addDetail(e);
            sum += e.getValue();
            coord++;
          }
          else {
            final Explanation r =
              new Explanation(0.0f, "match on prohibited clause (" +
                c.getQuery().toString() + ")");
            r.addDetail(e);
            sumExpl.addDetail(r);
            fail = true;
          }
        }
        else if (c.isRequired()) {
          final Explanation r = new Explanation(0.0f, "no match on required " +
          		"clause (" + c.getQuery().toString() + ")");
          r.addDetail(e);
          sumExpl.addDetail(r);
          fail = true;
        }
      }
      if (fail) {
        sumExpl.setMatch(Boolean.FALSE);
        sumExpl.setValue(0.0f);
        sumExpl.setDescription
          ("Failure to meet condition(s) of required/prohibited clause(s)");
        return sumExpl;
      }

      sumExpl.setMatch(0 < coord ? Boolean.TRUE : Boolean.FALSE);
      sumExpl.setValue(sum);
      return sumExpl;
    }

    @Override
    public Scorer scorer(final AtomicReaderContext context,
                         final boolean scoreDocsInOrder,
                         final boolean topScorer, final Bits acceptDocs)
    throws IOException {
      final List<NodeScorer> required = new ArrayList<NodeScorer>();
      final List<NodeScorer> prohibited = new ArrayList<NodeScorer>();
      final List<NodeScorer> optional = new ArrayList<NodeScorer>();
      final Iterator<NodeBooleanClause> cIter = clauses.iterator();
      for (final Weight w  : weights) {
        final NodeBooleanClause c =  cIter.next();
        final NodeScorer subScorer = (NodeScorer) w.scorer(context, true, false, acceptDocs);
        if (subScorer == null) {
          if (c.isRequired()) {
            return null;
          }
        } else if (c.isRequired()) {
          required.add(subScorer);
        } else if (c.isProhibited()) {
          prohibited.add(subScorer);
        } else {
          optional.add(subScorer);
        }
      }

      if (required.size() == 0 && optional.size() == 0) {
        // no required and optional clauses.
        return null;
      }

      return new NodeBooleanScorer(this, required, prohibited, optional);
    }

  }

  @Override
  public Weight createWeight(final IndexSearcher searcher) throws IOException {
    return new NodeBooleanWeight(searcher);
  }

  @Override
  public Query rewrite(final IndexReader reader) throws IOException {
    if (clauses.size() == 1) {                    // optimize 1-clause queries
      final NodeBooleanClause c = clauses.get(0);
      if (!c.isProhibited()) {        // just return clause

        // rewrite first
        NodeQuery query = (NodeQuery) c.getQuery().rewrite(reader);

        if (this.getBoost() != 1.0f) {                 // incorporate boost
          if (query == c.getQuery()) {                 // if rewrite was no-op
            query = (NodeQuery) query.clone();         // then clone before boost
          }
          query.setBoost(this.getBoost() * query.getBoost());
        }

        // transfer constraints
        query.setNodeConstraint(lowerBound, upperBound);
        query.setLevelConstraint(levelConstraint);

        // transfer ancestor pointer
        query.setAncestorPointer(ancestor);

        return query;
      }
    }

    NodeBooleanQuery clone = null;                    // recursively rewrite
    for (int i = 0 ; i < clauses.size(); i++) {
      final NodeBooleanClause c = clauses.get(i);
      final NodeQuery query = (NodeQuery) c.getQuery().rewrite(reader);
      if (query != c.getQuery()) {                     // clause rewrote: must clone
        if (clone == null) {
          clone = (NodeBooleanQuery) this.clone();
        }

        // transfer constraints
        query.setNodeConstraint(lowerBound, upperBound);
        query.setLevelConstraint(levelConstraint);

        // transfer ancestor pointer
        query.setAncestorPointer(ancestor);

        clone.clauses.set(i, new NodeBooleanClause(query, c.getOccur()));
      }
    }
    if (clone != null) {
      return clone;                               // some clauses rewrote
    }
    else {
      return this;                                // no clauses rewrote
    }
  }

  @Override
  public void extractTerms(final Set<Term> terms) {
    for (final NodeBooleanClause clause : clauses) {
      clause.getQuery().extractTerms(terms);
    }
  }

  @Override @SuppressWarnings("unchecked")
  public Query clone() {
    final NodeBooleanQuery clone = (NodeBooleanQuery) super.clone();
    clone.clauses = (ArrayList<NodeBooleanClause>) this.clauses.clone();
    return clone;
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String field) {
    final StringBuffer buffer = new StringBuffer();
    final boolean hasBoost = (this.getBoost() != 1.0);

    if (hasBoost) {
      buffer.append("(");
    }

    for (int i = 0; i < clauses.size(); i++) {
      final NodeBooleanClause c = clauses.get(i);
      if (c.isProhibited())
        buffer.append("-");
      else if (c.isRequired()) buffer.append("+");

      final Query subQuery = c.getQuery();
      if (subQuery != null) {
        if (subQuery instanceof NodeBooleanQuery) { // wrap sub-bools in parens
          buffer.append("(");
          buffer.append(subQuery.toString(field));
          buffer.append(")");
        }
        else {
          buffer.append(subQuery.toString(field));
        }
      }
      if (i != clauses.size() - 1) buffer.append(" ");
    }

    if (hasBoost) {
      buffer.append(")").append(ToStringUtils.boost(this.getBoost()));
    }

    return buffer.toString();
  }

  /** Returns true iff <code>o</code> is equal to this. */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof NodeBooleanQuery)) return false;
    final NodeBooleanQuery other = (NodeBooleanQuery) o;
    return (this.getBoost() == other.getBoost()) &&
           this.clauses.equals(other.clauses) &&
           this.levelConstraint == other.levelConstraint &&
           this.lowerBound == other.lowerBound &&
           this.upperBound == other.upperBound;
  }

  /** Returns a hash code value for this object. */
  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost())
      ^ clauses.hashCode()
      ^ levelConstraint
      ^ upperBound
      ^ lowerBound;
  }

  /**
   * Thrown when an attempt is made to add more than {@link
   * #getMaxClauseCount()} clauses. This typically happens if
   * a PrefixQuery, FuzzyQuery, WildcardQuery, or TermRangeQuery
   * is expanded to many terms during search.
   */
  public static class TooManyClauses extends RuntimeException {
    public TooManyClauses() {
      super("maxClauseCount is set to " + maxClauseCount);
    }
  }

}
