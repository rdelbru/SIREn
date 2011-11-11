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
import java.util.PriorityQueue;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopTermsRewrite;

/**
 * Code taken from {@link TopTermsRewrite} in order to use {@link SirenMultiTermQuery}.
 */
public abstract class SirenTopTermsRewrite<Q extends Query> extends SirenTermCollectingRewrite<Q> {

  private final int size;
  
  /** 
   * Create a TopTermsBooleanQueryRewrite for 
   * at most <code>size</code> terms.
   * <p>
   * NOTE: if {@link SirenBooleanQuery#getMaxClauseCount} is smaller than 
   * <code>size</code>, then it will be used instead. 
   */
  public SirenTopTermsRewrite(int size) {
    this.size = size;
  }
  
  /** return the maximum priority queue size */
  public int getSize() {
    return size;
  }
  
  /** return the maximum size of the priority queue (for boolean rewrites this is {@link SirenBooleanQuery#getMaxClauseCount}). */
  protected abstract int getMaxSize();
  
  @Override
  public Q rewrite(final IndexReader reader, final SirenMultiTermQuery query) throws IOException {
    final int maxSize = Math.min(size, getMaxSize());
    final PriorityQueue<ScoreTerm> stQueue = new PriorityQueue<ScoreTerm>();
    collectTerms(reader, query, new TermCollector() {
      public boolean collect(Term t, float boost) {
        // ignore uncompetitive hits
        if (stQueue.size() >= maxSize && boost <= stQueue.peek().boost)
          return true;
        // add new entry in PQ
        st.term = t;
        st.boost = boost;
        stQueue.offer(st);
        // possibly drop entries from queue
        st = (stQueue.size() > maxSize) ? stQueue.poll() : new ScoreTerm();
        return true;
      }
      
      // reusable instance
      private ScoreTerm st = new ScoreTerm();
    });
    
    final Q q = getTopLevelQuery();
    for (final ScoreTerm st : stQueue) {
      addClause(q, st.term, query.getBoost() * st.boost); // add to query
    }
    query.incTotalNumberOfTerms(stQueue.size());
    
    return q;
  }

  @Override
  public int hashCode() {
    return 31 * size;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final SirenTopTermsRewrite other = (SirenTopTermsRewrite) obj;
    if (size != other.size) return false;
    return true;
  }
  
  private static class ScoreTerm implements Comparable<ScoreTerm> {
    public Term term;
    public float boost;
    
    public int compareTo(ScoreTerm other) {
      if (this.boost == other.boost)
        return other.term.compareTo(this.term);
      else
        return Float.compare(this.boost, other.boost);
    }
  }

}
