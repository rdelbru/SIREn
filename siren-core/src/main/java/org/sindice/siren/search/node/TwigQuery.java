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

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.ToStringUtils;
import org.sindice.siren.search.node.NodeBooleanQuery.AbstractNodeBooleanWeight;
import org.sindice.siren.search.node.NodeBooleanQuery.TooManyClauses;
import org.sindice.siren.search.node.TwigQuery.EmptyRootQuery.EmptyRootWeight.EmptyRootScorer;

/**
 * A {@link NodeQuery} that matches a boolean combination of Ancestor-Descendant
 * and Parent-Child node queries.
 *
 * <p>
 *
 * Code taken from {@link BooleanQuery} and adapted for the Siren use case.
 */
public class TwigQuery extends NodeQuery {

  private NodeQuery root;

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
   * Set the maximum number of clauses permitted per {@link TwigQuery}. Default value
   * is 1024.
   */
  public static void setMaxClauseCount(final int maxClauseCount) {
    if (maxClauseCount < 1)
      throw new IllegalArgumentException("maxClauseCount must be >= 1");
    TwigQuery.maxClauseCount = maxClauseCount;
  }

  protected ArrayList<NodeBooleanClause> clauses = new ArrayList<NodeBooleanClause>();

  /**
   * Constructs an empty twig query at a given level
   */
  public TwigQuery(final int rootLevel) {
    // set an empty root
    this.root = new EmptyRootQuery();
    // set level constraint
    super.setLevelConstraint(rootLevel);
    root.setLevelConstraint(rootLevel);
  }

  /**
   * Constructs an empty twig query with a default level of 1.
   */
  public TwigQuery() {
    this(1);
  }

 /**
  * Adds a root query.
  * <p>
  * Overwrite the level and node constraints of the root query with the
  * currently defined level and node constraints of the twig query.
  */
  public void addRoot(final NodeQuery root) {
    this.root = root;
    // set the node constraint=
    root.setNodeConstraint(lowerBound, upperBound);
    // set level constraint
    root.setLevelConstraint(levelConstraint);
    // set the ancestor
    root.setAncestorPointer(ancestor);
  }

  /**
   * Adds a clause to a twig query.
   *
   * @throws TooManyClauses
   *           if the new number of clauses exceeds the maximum clause number
   * @see #getMaxClauseCount()
   */
  protected void addClause(final NodeBooleanClause clause) {
    if (clauses.size() >= maxClauseCount) {
      throw new TooManyClauses();
    }
    clauses.add(clause);
  }

  /**
   * Adds a child clause to the twig query.
   *
   * @throws TooManyClauses
   *           if the new number of clauses exceeds the maximum clause number
   * @see #getMaxClauseCount()
   */
  public void addChild(final NodeQuery query, final NodeBooleanClause.Occur occur) {
    // set the level constraint on the query
    query.setLevelConstraint(levelConstraint + 1);
    // set the ancestor pointer
    query.setAncestorPointer(root);
    // add the query to the clauses
    this.addClause(new NodeBooleanClause(query, occur));
  }

  /**
   * Adds a descendant clause to the twig query. The node level of the
   * descendant is relative to the twig level.
   *
   * @throws TooManyClauses
   *           if the new number of clauses exceeds the maximum clause number
   * @see #getMaxClauseCount()
   */
  public void addDescendant(final int nodeLevel, final NodeQuery query,
                            final NodeBooleanClause.Occur occur) {
    if (nodeLevel <= 0) {
      throw new IllegalArgumentException("The node level of a descendant should be superior to 0");
    }
    // set the level constraint on the query
    query.setLevelConstraint(levelConstraint + nodeLevel);
    // set the ancestor pointer
    query.setAncestorPointer(root);
    // add the query to the clauses
    this.addClause(new NodeBooleanClause(query, occur));
  }

  @Override
  protected void setAncestorPointer(final NodeQuery ancestor) {
    super.setAncestorPointer(ancestor);
    // keep root query synchronised with twig query
    root.setAncestorPointer(ancestor);
  }

  @Override
  public void setNodeConstraint(final int lowerBound, final int upperBound) {
    super.setNodeConstraint(lowerBound, upperBound);
    // keep root query synchronised with twig query
    root.setNodeConstraint(lowerBound, upperBound);
  }

  @Override
  public void setLevelConstraint(final int levelConstraint) {
    // store the current level constraint before updating
    final int oldLevelConstraint = this.levelConstraint;
    // update level constraint
    super.setLevelConstraint(levelConstraint);
    // update level constraint of the root
    root.setLevelConstraint(levelConstraint);
    // update level of childs and descendants
    NodeQuery q;
    for (final NodeBooleanClause clause : clauses) {
      q = clause.getQuery();
      // compute delta between old level and descendant level
      final int levelDelta = q.getLevelConstraint() - oldLevelConstraint;
      // update level of descendant
      q.setLevelConstraint(levelConstraint + levelDelta);
    }
  }

  /**
   * Return the root of this query
   */
  public NodeQuery getRoot() {
    return root;
  }

  /**
   * Returns the set of ancestor clauses in this query.
   */
  public NodeBooleanClause[] getClauses() {
    return clauses.toArray(new NodeBooleanClause[clauses.size()]);
  }

  /**
   * Returns the list of ancestor clauses in this query.
   */
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
   * Expert: the Weight for {@link TwigQuery}, used to
   * normalize, score and explain these queries.
   */
  protected class TwigWeight extends AbstractNodeBooleanWeight {

    protected Weight rootWeight;

    public TwigWeight(final IndexSearcher searcher)
    throws IOException {
      super(searcher);
      rootWeight = root.createWeight(searcher);
    }

    @Override
    protected void initWeights(final IndexSearcher searcher) throws IOException {
      weights = new ArrayList<Weight>(clauses.size());
      for (int i = 0; i < clauses.size(); i++) {
        final NodeBooleanClause c = clauses.get(i);
        final NodeQuery q = c.getQuery();
        weights.add(q.createWeight(searcher));
      }
    }

    @Override
    public float getValueForNormalization() throws IOException {
      float sum = 0.0f;
      for (int i = 0; i < weights.size(); i++) {
        // call sumOfSquaredWeights for all clauses in case of side effects
        final float s = weights.get(i).getValueForNormalization(); // sum sub weights
        if (!clauses.get(i).isProhibited()) {
        // only add to sum for non-prohibited clauses
          sum += s;
        }
      }

      // incorporate root weight
      sum += rootWeight.getValueForNormalization();

      // boost each weight
      sum *= TwigQuery.this.getBoost() * TwigQuery.this.getBoost();

      return sum;
    }

    @Override
    public void normalize(final float norm, float topLevelBoost) {
      // incorporate boost
      topLevelBoost *= TwigQuery.this.getBoost();
      for (final Weight w : weights) {
        // normalize all clauses, (even if prohibited in case of side affects)
        w.normalize(norm, topLevelBoost);
      }
      // Normalise root weight
      rootWeight.normalize(norm, topLevelBoost);
    }

    // TODO: Add root node in the explanation
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
      final NodeScorer rootScorer = (NodeScorer) rootWeight.scorer(context, true, false, acceptDocs);
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

      if (rootScorer instanceof EmptyRootScorer) {
        if (required.size() == 0 && optional.size() == 0) {
          // empty root and no required and optional clauses.
          return null;
        }
        return new TwigScorer(this, levelConstraint, required,
          prohibited, optional);
      } else if (rootScorer == null) {
        return null;
      } else {
        return new TwigScorer(this, rootScorer, levelConstraint,
          required, prohibited, optional);
      }
    }

    @Override
    public Query getQuery() {
      return TwigQuery.this;
    }

    @Override
    public String toString() {
      return "weight(" + TwigQuery.this + ")";
    }

  }

  @Override
  public Weight createWeight(final IndexSearcher searcher) throws IOException {
    return new TwigWeight(searcher);
  }

  @Override
  public Query rewrite(final IndexReader reader) throws IOException {
    // optimize 0-clause queries (root only)
    if (clauses.size() == 0) {
      // rewrite and return root
      NodeQuery query = (NodeQuery) root.rewrite(reader);

      if (this.getBoost() != 1.0f) {                 // incorporate boost
        if (query == root) {                         // if rewrite was no-op
          query = (NodeQuery) query.clone();         // then clone before boost
        }
        query.setBoost(this.getBoost() * query.getBoost());
      }

      // copy ancestor
      query.setAncestorPointer(ancestor);

      return query;
    }

    // optimize empty root queries with only one clause
    if (root instanceof EmptyRootQuery && clauses.size() == 1) {
      // rewrite single clause
      NodeQuery query = (NodeQuery) clauses.get(0).getQuery().rewrite(reader);

      // if rewrite was no-op then clone before other operations
      if (query == clauses.get(0).getQuery()) {
        query = (NodeQuery) query.clone();
      }

      // if the query is an AncestorFilterQuery
      if (query instanceof AncestorFilterQuery) {
        final AncestorFilterQuery tmp = (AncestorFilterQuery) query;
        // if no boost or node constraint has been set for this node
        // then extract the wrapped query
        if (tmp.getBoost() != 1.0f && tmp.lowerBound != -1 && tmp.upperBound != 1) {
          query = ((AncestorFilterQuery) query).getQuery();
        }
      }

      // wrap the rewritten query into an AncestorFilter query, so that
      // the matching node that is returned corresponds to the twig level
      query = new AncestorFilterQuery(query, levelConstraint);
      // copy ancestor
      query.setAncestorPointer(ancestor);
      // copy node constraints
      query.setNodeConstraint(lowerBound, upperBound);
      // incorporate boost
      if (this.getBoost() != 1.0f) {
        query.setBoost(this.getBoost());
      }
      // set ancestor of wrapped query to this AncestorFilterQuery
      ((AncestorFilterQuery) query).getQuery().setAncestorPointer(query);

      return query;
    }

    // optimize root query is a twig query
    if (root instanceof TwigQuery) {
      // clone
      final TwigQuery clone = (TwigQuery) this.clone();

      // incorporate the clauses of the twig root
      clone.clauses.addAll(((TwigQuery) clone.root).clauses);
      // assign the root of the twig root
      clone.root = ((TwigQuery) clone.root).getRoot();
      // update ancestor of descendants
      for (final NodeBooleanClause clause : clone.clauses) {
        clause.getQuery().setAncestorPointer(clone.root);
      }

      // copy ancestor
      clone.setAncestorPointer(ancestor);

      // rewrite after merge, and return the result
      return clone.rewrite(reader);
    }

    TwigQuery clone = null;

    // rewrite root
    clone = this.rewriteRoot(clone, reader);

    // rewrite ancestors and childs
    clone = this.rewriteClauses(clone, reader);

    // some clauses rewrote
    if (clone != null) {
      // copy ancestor
      clone.setAncestorPointer(ancestor);
      return clone;
    }
    else { // no clauses rewrote
      return this;
    }
  }

  private TwigQuery rewriteRoot(TwigQuery clone, final IndexReader reader)
  throws IOException {
    final NodeQuery query = (NodeQuery) root.rewrite(reader);
    if (query != root) {
      if (clone == null) {
        clone = (TwigQuery) this.clone();
      }
      // copy ancestor
      query.setAncestorPointer(ancestor);
      clone.root = query;
    }
    return clone;
  }

  private TwigQuery rewriteClauses(TwigQuery clone, final IndexReader reader)
  throws IOException {
    for (int i = 0 ; i < clauses.size(); i++) {
      final NodeBooleanClause c = clauses.get(i);
      final NodeQuery query = (NodeQuery) c.getQuery().rewrite(reader);
      if (query != c.getQuery()) { // clause rewrote: must clone
        if (clone == null) {
          clone = (TwigQuery) this.clone();
          // clone and set root since clone is null, i.e., root has not been rewritten
          clone.root = (NodeQuery) this.root.clone();
          // copy ancestor
          clone.root.setAncestorPointer(ancestor);
        }
        // set root as ancestor
        query.setAncestorPointer(clone.root);
        clone.clauses.set(i, new NodeBooleanClause(query, c.getOccur()));
      }
    }
    return clone;
  }

  @Override @SuppressWarnings("unchecked")
  public Query clone() {
    final TwigQuery clone = (TwigQuery) super.clone();
    clone.clauses = (ArrayList<NodeBooleanClause>) this.clauses.clone();
    clone.root = (NodeQuery) this.root.clone();
    return clone;
  }

  @Override
  public String toString(final String field) {
    final StringBuffer buffer = new StringBuffer();
    final boolean hasBoost = (this.getBoost() != 1.0);
    if (hasBoost) {
      buffer.append("(");
    }

    // Root
    if (root instanceof EmptyRootQuery) {
      buffer.append(root.toString(field));
    } else {
      buffer.append(root.toString(field));
    }
    buffer.append(" : ");
    // Child
    if (clauses.isEmpty()) {
      buffer.append("*");
    } else {
      if (clauses.size() != 1) {
        buffer.append("[");
      }
      for (int i = 0; i < clauses.size(); i++) {
        final NodeBooleanClause c = clauses.get(i);
        if (c.isProhibited())
          buffer.append("-");
        else if (c.isRequired()) buffer.append("+");

        final Query subQuery = c.getQuery();
        if (subQuery != null) {
          if (subQuery instanceof TwigQuery ||
              subQuery instanceof NodeBooleanQuery) { // wrap sub-bools in parens
            buffer.append("(");
            buffer.append(subQuery.toString(field));
            buffer.append(")");
          }
          else {
            buffer.append(subQuery.toString(field));
          }
        }
        if (i != clauses.size() - 1) {
          buffer.append(", ");
        }
      }
      if (clauses.size() != 1) {
        buffer.append("]");
      }
    }

    if (hasBoost) {
      buffer.append(")").append(ToStringUtils.boost(this.getBoost()));
    }

    return buffer.toString();
  }

  /** Returns true iff <code>o</code> is equal to this. */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof TwigQuery)) return false;
    final TwigQuery other = (TwigQuery) o;
    return (this.getBoost() == other.getBoost()) &&
           this.clauses.equals(other.clauses) &&
           this.root.equals(other.root); // root and twig query should have the same constraints,
                                         // no need to integrate them into the equality test
  }

  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost())
      ^ clauses.hashCode()
      ^ root.hashCode(); // root and twig query should have the same constraints,
                         // no need to integrate them into the hashcode
  }

  /**
   * An empty root query is used to create twig query in which the root query
   * is not specified.
   * <p>
   * Act as an interface for the constraint stack (i.e., ancestor pointer).
   */
  public static class EmptyRootQuery extends NodeQuery {

    @Override
    public Weight createWeight(final IndexSearcher searcher) throws IOException {
      return new EmptyRootWeight();
    }

    @Override
    public boolean equals(final Object o) {
      if (!(o instanceof EmptyRootQuery)) return false;
      final EmptyRootQuery other = (EmptyRootQuery) o;
      return (this.getBoost() == other.getBoost()) &&
              this.levelConstraint == other.levelConstraint &&
              this.lowerBound == other.lowerBound &&
              this.upperBound == other.upperBound;
    }

    @Override
    public int hashCode() {
      return Float.floatToIntBits(this.getBoost())
        ^ levelConstraint
        ^ upperBound
        ^ lowerBound;
    }

    @Override
    public String toString(final String field) {
      return "*";
    }

    class EmptyRootWeight extends Weight {

      @Override
      public Explanation explain(final AtomicReaderContext context, final int doc)
      throws IOException {
        return new ComplexExplanation(true, 0.0f, "empty root query");
      }

      @Override
      public Query getQuery() {
        return EmptyRootQuery.this;
      }

      @Override
      public float getValueForNormalization() throws IOException {
        return 0;
      }

      @Override
      public void normalize(final float norm, final float topLevelBoost) {}

      @Override
      public Scorer scorer(final AtomicReaderContext context,
                           final boolean scoreDocsInOrder,
                           final boolean topScorer,
                           final Bits acceptDocs)
      throws IOException {
        return new EmptyRootScorer();
      }

      class EmptyRootScorer extends NodeScorer {

        protected EmptyRootScorer() {
          super(null);
        }

        @Override
        public boolean nextCandidateDocument()
        throws IOException {
          throw new UnsupportedOperationException("EmptyRootScorer#nextCandidateDocument should not be called");
        }

        @Override
        public boolean nextNode()
        throws IOException {
          throw new UnsupportedOperationException("EmptyRootScorer#nextNode should not be called");
        }

        @Override
        public boolean skipToCandidate(final int target)
        throws IOException {
          throw new UnsupportedOperationException("EmptyRootScorer#skipToCandidate should not be called");
        }

        @Override
        public int doc() {
          throw new UnsupportedOperationException("EmptyRootScorer#doc should not be called");
        }

        @Override
        public IntsRef node() {
          throw new UnsupportedOperationException("EmptyRootScorer#node should not be called");
        }

        @Override
        public float freqInNode()
        throws IOException {
          throw new UnsupportedOperationException("EmptyRootScorer#freqInNode should not be called");
        }

        @Override
        public float scoreInNode()
        throws IOException {
          throw new UnsupportedOperationException("EmptyRootScorer#scoreInNode should not be called");
        }

      }

    }

  }

}
