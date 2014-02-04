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

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.FilteredTermsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermContext;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.AttributeSource;
import org.sindice.siren.search.node.NodeBooleanClause.Occur;

/**
 * An abstract {@link NodePrimitiveQuery} that matches documents
 * containing a subset of terms provided by a {@link
 * FilteredTermEnum} enumeration.
 *
 * <p>This query cannot be used directly; you must subclass
 * it and define {@link #getTermsEnum(Terms,AttributeSource)} to provide a {@link
 * FilteredTermsEnum} that iterates through the terms to be
 * matched.
 *
 * <p><b>NOTE</b>: since {@link #CONSTANT_SCORE_FILTER_REWRITE} is deactivated
 * in SIREn, the {@link #setRewriteMethod} will always be either
 * {@link #CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE} or {@link
 * #SCORING_BOOLEAN_QUERY_REWRITE}. Therefore, you may encounter a
 * {@link NodeBooleanQuery.TooManyClauses} exception during
 * searching, which happens when the number of terms to be
 * searched exceeds {@link NodeBooleanQuery#getMaxClauseCount()}.
 *
 * <p>The recommended rewrite method is {@link
 * #CONSTANT_SCORE_AUTO_REWRITE_DEFAULT}: it doesn't spend CPU
 * computing unhelpful scores, and it tries to pick the most
 * performant rewrite method given the query. If you
 * need scoring (like {@link NodeFuzzyQuery}, use
 * {@link TopTermsScoringNodeBooleanQueryRewrite} which uses
 * a priority queue to only collect competitive terms
 * and not hit this limitation.
 *
 * Note that {@link QueryParser} produces
 * SirenMultiTermQueries using {@link #CONSTANT_SCORE_AUTO_REWRITE_DEFAULT} by
 * default.
 *
 * <p>
 * Code taken from {@link MultiTermQuery} and adapted for SIREn.
 */
public abstract class MultiNodeTermQuery extends NodePrimitiveQuery {

  protected final String field;

  protected RewriteMethod rewriteMethod = CONSTANT_SCORE_AUTO_REWRITE_DEFAULT;

  /** Abstract class that defines how the query is rewritten. */
  public static abstract class RewriteMethod {

    public abstract Query rewrite(IndexReader reader, MultiNodeTermQuery query)
    throws IOException;

    /**
     * Returns the {@link MultiNodeTermQuery}s {@link TermsEnum}
     * @see MultiNodeTermQuery#getTermsEnum(Terms, AttributeSource)
     */
    protected TermsEnum getTermsEnum(final MultiNodeTermQuery query, final Terms terms,
                                     final AttributeSource atts)
    throws IOException {
      // allow RewriteMethod subclasses to pull a TermsEnum from the MTQ
      return query.getTermsEnum(terms, atts);
    }

  }

  /**
   * Rewrite method currently deactivated in SIREn.
   *
   * <p> A rewrite method that first creates a private Filter,
   * by visiting each term in sequence and marking all docs
   * for that term.  Matching documents are assigned a
   * constant score equal to the query's boost.
   *
   * <p> This method is faster than the BooleanQuery
   * rewrite methods when the number of matched terms or
   * matched documents is non-trivial. Also, it will never
   * hit an errant {@link NodeBooleanQuery.TooManyClauses}
   * exception.
   *
   *  @see #setRewriteMethod
   **/
  public static final RewriteMethod CONSTANT_SCORE_FILTER_REWRITE = new RewriteMethod() {

    @Override
    public Query rewrite(final IndexReader reader, final MultiNodeTermQuery query) {

      // TODO: Reactivate filter-based approach when a correct implementation of
      // SirenMultiTermQueryWrapperFilter is found.

//      Query result = new ConstantScoreQuery(new SirenMultiTermQueryWrapperFilter<MultiTermQuery>(query));
//      result.setBoost(query.getBoost());
//      return result;

      throw new UnsupportedOperationException("Filter-based rewrite method " +
      		"is currently deactivated");
    }

  };

  /**
   * A rewrite method that first translates each term into
   * {@link NodeBooleanClause.Occur#SHOULD} clause in a
   * SirenBooleanQuery, and keeps the scores as computed by the
   * query.  Note that typically such scores are
   * meaningless to the user, and require non-trivial CPU
   * to compute, so it's almost always better to use {@link
   * #CONSTANT_SCORE_AUTO_REWRITE_DEFAULT} instead.
   *
   * <p><b>NOTE</b>: This rewrite method will hit {@link
   * NodeBooleanQuery.TooManyClauses} if the number of terms
   * exceeds {@link NodeBooleanQuery#getMaxClauseCount}.
   *
   * @see #setRewriteMethod
   **/
  public final static RewriteMethod SCORING_BOOLEAN_QUERY_REWRITE = NodeScoringRewrite.SCORING_BOOLEAN_QUERY_REWRITE;

  /**
   * Like {@link #SCORING_BOOLEAN_QUERY_REWRITE} except
   * scores are not computed.  Instead, each matching
   * document receives a constant score equal to the
   * query's boost.
   *
   * <p><b>NOTE</b>: This rewrite method will hit {@link
   * NodeBooleanQuery.TooManyClauses} if the number of terms
   * exceeds {@link NodeBooleanQuery#getMaxClauseCount}.
   *
   * @see #setRewriteMethod
   **/
  public final static RewriteMethod CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE = NodeScoringRewrite.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE;

  /**
   * A rewrite method that first translates each term into
   * {@link NodeBooleanClause.Occur#SHOULD} clause in a SirenBooleanQuery, and
   * keeps the scores as computed by the query.
   *
   * <p>
   * This rewrite method only uses the top scoring terms so it will not overflow
   * the boolean max clause count. It is the default rewrite method for
   * {@link FuzzyQuery}.
   *
   * @see #setRewriteMethod
   */
  static final class TopTermsScoringNodeBooleanQueryRewrite extends TopNodeTermsRewrite<NodeBooleanQuery> {

    /**
     * Create a {@link TopTermsScoringNodeBooleanQueryRewrite} for
     * at most <code>size</code> terms.
     * <p>
     * NOTE: if {@link NodeBooleanQuery#getMaxClauseCount} is smaller than
     * <code>size</code>, then it will be used instead.
     */
    public TopTermsScoringNodeBooleanQueryRewrite(final int size) {
      super(size);
    }

    @Override
    protected int getMaxSize() {
      return NodeBooleanQuery.getMaxClauseCount();
    }

    @Override
    protected NodeBooleanQuery getTopLevelQuery() {
      return new NodeBooleanQuery();
    }

    @Override
    protected void addClause(final NodeBooleanQuery topLevel, final Term term,
                             final int docCount, final float boost,
                             final TermContext states) {
      final NodeTermQuery tq = new NodeTermQuery(term, states);
      tq.setBoost(boost);
      topLevel.add(tq, Occur.SHOULD);
    }

  }

  /**
   * A rewrite method that first translates each term into
   * {@link NodeBooleanClause.Occur#SHOULD} clause in a {@link NodeBooleanQuery},
   * but the scores are only computed as the boost.
   * <p>
   * This rewrite method only uses the top scoring terms so it will not overflow
   * the boolean max clause count.
   *
   * @see #setRewriteMethod
   */
  static final class TopTermsBoostOnlyNodeBooleanQueryRewrite extends TopNodeTermsRewrite<NodeBooleanQuery> {

    /**
     * Create a TopTermsBoostOnlySirenBooleanQueryRewrite for
     * at most <code>size</code> terms.
     * <p>
     * NOTE: if {@link NodeBooleanQuery#getMaxClauseCount} is smaller than
     * <code>size</code>, then it will be used instead.
     */
    public TopTermsBoostOnlyNodeBooleanQueryRewrite(final int size) {
      super(size);
    }

    @Override
    protected int getMaxSize() {
      return NodeBooleanQuery.getMaxClauseCount();
    }

    @Override
    protected NodeBooleanQuery getTopLevelQuery() {
      return new NodeBooleanQuery();
    }

    @Override
    protected void addClause(final NodeBooleanQuery topLevel, final Term term,
                             final int docFreq, final float boost,
                             final TermContext states) {
      final NodePrimitiveQuery q = new NodeConstantScoreQuery(new NodeTermQuery(term, states));
      q.setBoost(boost);
      topLevel.add(q, NodeBooleanClause.Occur.SHOULD);
    }
  }

  /**
   * A rewrite method that tries to pick the best
   * constant-score rewrite method based on term and
   * document counts from the query.  If both the number of
   * terms and documents is small enough, then {@link
   * #CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE} is used.
   * Otherwise, {@link #CONSTANT_SCORE_FILTER_REWRITE} is
   * used.
   *
   * <p> The {@link #CONSTANT_SCORE_FILTER_REWRITE} method is currently
   * deactivated in SIREn.
   */
  static class NodeConstantScoreAutoRewrite extends org.sindice.siren.search.node.NodeConstantScoreAutoRewrite {}

  /**
   * Read-only default instance of {@link
   * NodeConstantScoreAutoRewrite}, with {@link
   * NodeConstantScoreAutoRewrite#setTermCountCutoff} set to
   * {@link
   * NodeConstantScoreAutoRewrite#DEFAULT_TERM_COUNT_CUTOFF}
   * and {@link
   * NodeConstantScoreAutoRewrite#setDocCountPercent} set to
   * {@link
   * NodeConstantScoreAutoRewrite#DEFAULT_DOC_COUNT_PERCENT}.
   * Note that you cannot alter the configuration of this
   * instance; you'll need to create a private instance
   * instead.
   **/
  public final static RewriteMethod CONSTANT_SCORE_AUTO_REWRITE_DEFAULT = new NodeConstantScoreAutoRewrite() {

    @Override
    public void setTermCountCutoff(final int count) {
      throw new UnsupportedOperationException("Please create a private instance");
    }

    @Override
    public void setDocCountPercent(final double percent) {
      throw new UnsupportedOperationException("Please create a private instance");
    }

  };

  /**
   * Constructs a query matching terms that cannot be represented with a single
   * Term.
   */
  public MultiNodeTermQuery(final String field) {
    this.field = field;
    assert field != null;
  }

  /** Returns the field name for this query */
  public final String getField() { return field; }

  /**
   * Construct the enumeration to be used, expanding the
   * pattern term.  This method should only be called if
   * the field exists (ie, implementations can assume the
   * field does exist).  This method should not return null
   * (should instead return {@link TermsEnum#EMPTY} if no
   * terms match).  The TermsEnum must already be
   * positioned to the first matching term.
   * The given {@link AttributeSource} is passed by the {@link RewriteMethod} to
   * provide attributes, the rewrite method uses to inform about e.g. maximum competitive boosts.
   * This is currently only used by {@link TopNodeTermsRewrite}
   */
  protected abstract TermsEnum getTermsEnum(Terms terms, AttributeSource atts) throws IOException;

  /**
   * Convenience method, if no attributes are needed:
   * This simply passes empty attributes and is equal to:
   * <code>getTermsEnum(terms, new AttributeSource())</code>
   */
  protected final TermsEnum getTermsEnum(final Terms terms) throws IOException {
    return this.getTermsEnum(terms, new AttributeSource());
  }

  @Override
  public Query rewrite(final IndexReader reader) throws IOException {
    return rewriteMethod.rewrite(reader, this);
  }

  /**
   * @see #setRewriteMethod
   */
  public RewriteMethod getRewriteMethod() {
    return rewriteMethod;
  }

  /**
   * Sets the rewrite method to be used when executing the
   * query.  You can use one of the four core methods, or
   * implement your own subclass of {@link RewriteMethod}. */
  public void setRewriteMethod(final RewriteMethod method) {
    rewriteMethod = method;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Float.floatToIntBits(this.getBoost());
    result = prime * result + rewriteMethod.hashCode();
    result = prime * result + lowerBound;
    result = prime * result + upperBound;
    result = prime * result + levelConstraint;
    if (field != null) result = prime * result + field.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final MultiNodeTermQuery other = (MultiNodeTermQuery) obj;
    if (Float.floatToIntBits(this.getBoost()) != Float.floatToIntBits(other.getBoost()))
      return false;
    if (!rewriteMethod.equals(other.rewriteMethod)) {
      return false;
    }
    if (!(this.lowerBound == other.lowerBound &&
          this.upperBound == other.upperBound &&
          this.levelConstraint == other.levelConstraint &&
          StringUtils.equals(this.datatype, other.datatype))) {
      return false;
    }
    return (other.field == null ? field == null : other.field.equals(field));
  }

}
