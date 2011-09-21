/**
 * Copyright 2011, Campinas Stephane
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
 * @project siren-core_rdelbru
 * @author Campinas Stephane [ 21 Sep 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoringRewrite;
import org.sindice.siren.search.SirenBooleanClause.Occur;
import org.sindice.siren.search.SirenMultiTermQuery.RewriteMethod;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Class copied from {@link ScoringRewrite} for the siren use case
 */
public abstract class SirenScoringRewrite<Q extends Query> extends SirenTermCollectingRewrite<Q> {

  /** A rewrite method that first translates each term into
   *  {@link BooleanClause.Occur#SHOULD} clause in a
   *  BooleanQuery, and keeps the scores as computed by the
   *  query.  Note that typically such scores are
   *  meaningless to the user, and require non-trivial CPU
   *  to compute, so it's almost always better to use {@link
   *  MultiTermQuery#CONSTANT_SCORE_AUTO_REWRITE_DEFAULT} instead.
   *
   *  <p><b>NOTE</b>: This rewrite method will hit {@link
   *  BooleanQuery.TooManyClauses} if the number of terms
   *  exceeds {@link BooleanQuery#getMaxClauseCount}.
   *
   *  @see #setRewriteMethod */
  public final static SirenScoringRewrite<SirenBooleanQuery> SCORING_BOOLEAN_QUERY_REWRITE = new SirenScoringRewrite<SirenBooleanQuery>() {
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
    
    // Make sure we are still a singleton even after deserializing
    protected Object readResolve() {
      return SCORING_BOOLEAN_QUERY_REWRITE;
    }    
  };
  
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
  public final static RewriteMethod CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE = new RewriteMethod() {
    @Override
    public Query rewrite(IndexReader reader, SirenMultiTermQuery query) throws IOException {
      final SirenBooleanQuery bq = SCORING_BOOLEAN_QUERY_REWRITE.rewrite(reader, query);
      // TODO: if empty boolean query return NullQuery?
      if (bq.clauses().isEmpty())
        return bq;
      // strip the scores off
//      final Query result = new ConstantScoreQuery(bq);
//      result.setBoost(query.getBoost());
//      return result;
      throw new NotImplementedException();
    }

    // Make sure we are still a singleton even after deserializing
    protected Object readResolve() {
      return CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE;
    }
  };

  @Override
  public Q rewrite(final IndexReader reader, final SirenMultiTermQuery query) throws IOException {
    final Q result = getTopLevelQuery();
    final int[] size = new int[1]; // "trick" to be able to make it final
    collectTerms(reader, query, new TermCollector() {
      public boolean collect(Term t, float boost) throws IOException {
        addClause(result, t, query.getBoost() * boost);
        size[0]++;
        return true;
      }
    });
    query.incTotalNumberOfTerms(size[0]);
    return result;
  }
  
}
