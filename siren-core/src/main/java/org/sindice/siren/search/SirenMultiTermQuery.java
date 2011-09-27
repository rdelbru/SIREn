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
 * @project siren-core_rdelbru
 * @author Campinas Stephane [ 21 Sep 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.io.Serializable;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FilteredTermEnum;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.search.Query;
import org.sindice.siren.search.SirenBooleanClause.Occur;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Class copied from {@link MultiTermQuery} for the siren use case
 */
public abstract class SirenMultiTermQuery extends Query {

  protected RewriteMethod rewriteMethod = CONSTANT_SCORE_AUTO_REWRITE_DEFAULT;
  transient int numberOfTerms = 0;

  /** Abstract class that defines how the query is rewritten. */
  public static abstract class RewriteMethod implements Serializable {
    public abstract Query rewrite(IndexReader reader, SirenMultiTermQuery query) throws IOException;
  }

  /** A rewrite method that first creates a private Filter,
   *  by visiting each term in sequence and marking all docs
   *  for that term.  Matching documents are assigned a
   *  constant score equal to the query's boost.
   * 
   *  <p> This method is faster than the BooleanQuery
   *  rewrite methods when the number of matched terms or
   *  matched documents is non-trivial. Also, it will never
   *  hit an errant {@link BooleanQuery.TooManyClauses}
   *  exception.
   *
   *  @see #setRewriteMethod */
  public static final RewriteMethod CONSTANT_SCORE_FILTER_REWRITE = new RewriteMethod() {
    @Override
    public Query rewrite(IndexReader reader, SirenMultiTermQuery query) {
//      Query result = new ConstantScoreQuery(new MultiTermQueryWrapperFilter<MultiTermQuery>(query));
//      result.setBoost(query.getBoost());
//      return result;
      throw new NotImplementedException();
    }

    // Make sure we are still a singleton even after deserializing
    protected Object readResolve() {
      return CONSTANT_SCORE_FILTER_REWRITE;
    }
  };

  /** A rewrite method that first translates each term into
   *  {@link BooleanClause.Occur#SHOULD} clause in a
   *  BooleanQuery, and keeps the scores as computed by the
   *  query.  Note that typically such scores are
   *  meaningless to the user, and require non-trivial CPU
   *  to compute, so it's almost always better to use {@link
   *  #CONSTANT_SCORE_AUTO_REWRITE_DEFAULT} instead.
   *
   *  <p><b>NOTE</b>: This rewrite method will hit {@link
   *  BooleanQuery.TooManyClauses} if the number of terms
   *  exceeds {@link BooleanQuery#getMaxClauseCount}.
   *
   *  @see #setRewriteMethod */
  public final static RewriteMethod SCORING_BOOLEAN_QUERY_REWRITE = SirenScoringRewrite.SCORING_BOOLEAN_QUERY_REWRITE;
  
  /** Like {@link #SCORING_BOOLEAN_QUERY_REWRITE} except
   *  scores are not computed.  Instead, each matching
   *  document receives a constant score equal to the
   *  query's boost.
   * 
   *  <p><b>NOTE</b>: This rewrite method will hit {@link
   *  BooleanQuery.TooManyClauses} if the number of terms
   *  exceeds {@link BooleanQuery#getMaxClauseCount}.
   *
   *  @see #setRewriteMethod */
  public final static RewriteMethod CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE = SirenScoringRewrite.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE;

  /**
   * A rewrite method that first translates each term into
   * {@link BooleanClause.Occur#SHOULD} clause in a BooleanQuery, and keeps the
   * scores as computed by the query.
   * 
   * <p>
   * This rewrite method only uses the top scoring terms so it will not overflow
   * the boolean max clause count. It is the default rewrite method for
   * {@link FuzzyQuery}.
   * 
   * @see #setRewriteMethod
   */
  public static final class TopTermsScoringSirenBooleanQueryRewrite extends SirenTopTermsRewrite<SirenBooleanQuery> {

    /** 
     * Create a TopTermsScoringBooleanQueryRewrite for 
     * at most <code>size</code> terms.
     * <p>
     * NOTE: if {@link BooleanQuery#getMaxClauseCount} is smaller than 
     * <code>size</code>, then it will be used instead. 
     */
    public TopTermsScoringSirenBooleanQueryRewrite(int size) {
      super(size);
    }
    
    @Override
    protected int getMaxSize() {
      return SirenBooleanQuery.getMaxClauseCount();
    }
    
    @Override
    protected SirenBooleanQuery getTopLevelQuery() {
      return new SirenBooleanQuery(true);
    }
    
    @Override
    protected void addClause(SirenBooleanQuery topLevel, Term term, float boost) {
      final SirenTermQuery tq = new SirenTermQuery(term);
      tq.setBoost(boost);
      topLevel.add(tq, Occur.SHOULD);
    }
  }
  
  /**
   * A rewrite method that first translates each term into
   * {@link BooleanClause.Occur#SHOULD} clause in a BooleanQuery, but the scores
   * are only computed as the boost.
   * <p>
   * This rewrite method only uses the top scoring terms so it will not overflow
   * the boolean max clause count.
   * 
   * @see #setRewriteMethod
   */
  public static final class TopTermsBoostOnlySirenBooleanQueryRewrite extends SirenTopTermsRewrite<SirenBooleanQuery> {
    
    /** 
     * Create a TopTermsBoostOnlyBooleanQueryRewrite for 
     * at most <code>size</code> terms.
     * <p>
     * NOTE: if {@link BooleanQuery#getMaxClauseCount} is smaller than 
     * <code>size</code>, then it will be used instead. 
     */
    public TopTermsBoostOnlySirenBooleanQueryRewrite(int size) {
      super(size);
    }
    
    @Override
    protected int getMaxSize() {
      return SirenBooleanQuery.getMaxClauseCount();
    }
    
    @Override
    protected SirenBooleanQuery getTopLevelQuery() {
      return new SirenBooleanQuery(true);
    }
    
    @Override
    protected void addClause(SirenBooleanQuery topLevel, Term term, float boost) {
//      final Query q = new ConstantScoreQuery(new TermQuery(term));
//      q.setBoost(boost);
//      topLevel.add(q, BooleanClause.Occur.SHOULD);
      throw new NotImplementedException();
    }
  }
    
  /** A rewrite method that tries to pick the best
   *  constant-score rewrite method based on term and
   *  document counts from the query.  If both the number of
   *  terms and documents is small enough, then {@link
   *  #CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE} is used.
   *  Otherwise, {@link #CONSTANT_SCORE_FILTER_REWRITE} is
   *  used.
   */
  public static class ConstantScoreAutoRewrite extends org.sindice.siren.search.SirenConstantScoreAutoRewrite {}

  /** Read-only default instance of {@link
   *  ConstantScoreAutoRewrite}, with {@link
   *  ConstantScoreAutoRewrite#setTermCountCutoff} set to
   *  {@link
   *  ConstantScoreAutoRewrite#DEFAULT_TERM_COUNT_CUTOFF}
   *  and {@link
   *  ConstantScoreAutoRewrite#setDocCountPercent} set to
   *  {@link
   *  ConstantScoreAutoRewrite#DEFAULT_DOC_COUNT_PERCENT}.
   *  Note that you cannot alter the configuration of this
   *  instance; you'll need to create a private instance
   *  instead. */
  public final static RewriteMethod CONSTANT_SCORE_AUTO_REWRITE_DEFAULT = new ConstantScoreAutoRewrite() {
    @Override
    public void setTermCountCutoff(int count) {
      throw new UnsupportedOperationException("Please create a private instance");
    }

    @Override
    public void setDocCountPercent(double percent) {
      throw new UnsupportedOperationException("Please create a private instance");
    }

    // Make sure we are still a singleton even after deserializing
    protected Object readResolve() {
      return CONSTANT_SCORE_AUTO_REWRITE_DEFAULT;
    }
  };

  /**
   * Constructs a query matching terms that cannot be represented with a single
   * Term.
   */
  public SirenMultiTermQuery() {
  }

  /** Construct the enumeration to be used, expanding the pattern term. */
  protected abstract FilteredTermEnum getEnum(IndexReader reader)
      throws IOException;

  /**
   * Expert: Return the number of unique terms visited during execution of the query.
   * If there are many of them, you may consider using another query type
   * or optimize your total term count in index.
   * <p>This method is not thread safe, be sure to only call it when no query is running!
   * If you re-use the same query instance for another
   * search, be sure to first reset the term counter
   * with {@link #clearTotalNumberOfTerms}.
   * <p>On optimized indexes / no MultiReaders, you get the correct number of
   * unique terms for the whole index. Use this number to compare different queries.
   * For non-optimized indexes this number can also be achieved in
   * non-constant-score mode. In constant-score mode you get the total number of
   * terms seeked for all segments / sub-readers.
   * @see #clearTotalNumberOfTerms
   */
  public int getTotalNumberOfTerms() {
    return numberOfTerms;
  }
  
  /**
   * Expert: Resets the counting of unique terms.
   * Do this before executing the query/filter.
   * @see #getTotalNumberOfTerms
   */
  public void clearTotalNumberOfTerms() {
    numberOfTerms = 0;
  }
  
  protected void incTotalNumberOfTerms(int inc) {
    numberOfTerms += inc;
  }

  @Override
  public Query rewrite(IndexReader reader) throws IOException {
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
  public void setRewriteMethod(RewriteMethod method) {
    rewriteMethod = method;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Float.floatToIntBits(getBoost());
    result = prime * result;
    result += rewriteMethod.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SirenMultiTermQuery other = (SirenMultiTermQuery) obj;
    if (Float.floatToIntBits(getBoost()) != Float.floatToIntBits(other.getBoost()))
      return false;
    if (!rewriteMethod.equals(other.rewriteMethod)) {
      return false;
    }
    return true;
  }

}
