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

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermContext;
import org.apache.lucene.index.TermState;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.MultiTermQuery.ConstantScoreAutoRewrite;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.ByteBlockPool;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefHash;
import org.apache.lucene.util.BytesRefHash.DirectBytesStartArray;
import org.apache.lucene.util.RamUsageEstimator;
import org.sindice.siren.search.node.NodeBooleanClause.Occur;

/**
 * A rewrite method that tries to pick the best constant-score rewrite method
 * based on term and document counts from the query.
 *
 * <p>
 *
 * Term and document cutoffs are deactivated to disable {@link
 * MultiNodeTermQuery#CONSTANT_SCORE_FILTER_REWRITE}.
 *
 * <p>
 *
 * Code taken from {@link ConstantScoreAutoRewrite} and adapted for SIREn.
 */
class NodeConstantScoreAutoRewrite extends NodeTermCollectingRewrite<NodeBooleanQuery> {

  // Term cutoff deactivated until a efficient filter-based approach is found
  public static int DEFAULT_TERM_COUNT_CUTOFF = Integer.MAX_VALUE;

  // Document cutoff deactivated until a efficient filter-based approach is found
  public static double DEFAULT_DOC_COUNT_PERCENT = Integer.MAX_VALUE;

  private int termCountCutoff = DEFAULT_TERM_COUNT_CUTOFF;
  private double docCountPercent = DEFAULT_DOC_COUNT_PERCENT;

  /** If the number of terms in this query is equal to or
   *  larger than this setting then {@link
   *  #CONSTANT_SCORE_FILTER_REWRITE} is used. */
  public void setTermCountCutoff(final int count) {
    termCountCutoff = count;
  }

  /** @see #setTermCountCutoff */
  public int getTermCountCutoff() {
    return termCountCutoff;
  }

  /** If the number of documents to be visited in the
   *  postings exceeds this specified percentage of the
   *  maxDoc() for the index, then {@link
   *  #CONSTANT_SCORE_FILTER_REWRITE} is used.
   *  @param percent 0.0 to 100.0 */
  public void setDocCountPercent(final double percent) {
    docCountPercent = percent;
  }

  /** @see #setDocCountPercent */
  public double getDocCountPercent() {
    return docCountPercent;
  }

  @Override
  protected NodeBooleanQuery getTopLevelQuery() {
    return new NodeBooleanQuery();
  }

  @Override
  protected void addClause(final NodeBooleanQuery topLevel, final Term term,
                           final int docFreq, final float boost /*ignored*/,
                           final TermContext states) {
    topLevel.add(new NodeTermQuery(term, states), Occur.SHOULD);
  }

  @Override
  public Query rewrite(final IndexReader reader, final MultiNodeTermQuery query) throws IOException {

    // Disabled cutoffs
    final int docCountCutoff = Integer.MAX_VALUE;
    final int termCountLimit = Integer.MAX_VALUE;

    final CutOffTermCollector col = new CutOffTermCollector(docCountCutoff, termCountLimit);
    this.collectTerms(reader, query, col);
    final int size = col.pendingTerms.size();

    if (col.hasCutOff) {
      return MultiNodeTermQuery.CONSTANT_SCORE_FILTER_REWRITE.rewrite(reader, query);
    } else if (size == 0) {
      return this.getTopLevelQuery();
    } else {
      final NodeBooleanQuery bq = this.getTopLevelQuery();
      final BytesRefHash pendingTerms = col.pendingTerms;
      final int sort[] = pendingTerms.sort(col.termsEnum.getComparator());
      for(int i = 0; i < size; i++) {
        final int pos = sort[i];
        // docFreq is not used for constant score here, we pass 1
        // to explicitely set a fake value, so it's not calculated
        this.addClause(bq,
          new Term(query.field, pendingTerms.get(pos, new BytesRef())),
          1, 1.0f, col.array.termState[pos]);
      }
      // Strip scores
      final NodeQuery result = new NodeConstantScoreQuery(bq);
      result.setBoost(query.getBoost());
      // set level and node constraints
      result.setLevelConstraint(query.getLevelConstraint());
      result.setNodeConstraint(query.getNodeConstraint()[0], query.getNodeConstraint()[1]);
      // set ancestor
      result.setAncestorPointer(query.ancestor);

      return result;
    }
  }

  static final class CutOffTermCollector extends TermCollector {
    CutOffTermCollector(final int docCountCutoff, final int termCountLimit) {
      this.docCountCutoff = docCountCutoff;
      this.termCountLimit = termCountLimit;
    }

    @Override
    public void setNextEnum(final TermsEnum termsEnum) throws IOException {
      this.termsEnum = termsEnum;
    }

    @Override
    public boolean collect(final BytesRef bytes) throws IOException {
      int pos = pendingTerms.add(bytes);
      docVisitCount += termsEnum.docFreq();
      if (pendingTerms.size() >= termCountLimit || docVisitCount >= docCountCutoff) {
        hasCutOff = true;
        return false;
      }

      final TermState termState = termsEnum.termState();
      assert termState != null;
      if (pos < 0) {
        pos = (-pos)-1;
        array.termState[pos].register(termState, readerContext.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
      } else {
        array.termState[pos] = new TermContext(topReaderContext, termState, readerContext.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
      }
      return true;
    }

    int docVisitCount = 0;
    boolean hasCutOff = false;
    TermsEnum termsEnum;

    final int docCountCutoff, termCountLimit;
    final TermStateByteStart array = new TermStateByteStart(16);
    final BytesRefHash pendingTerms = new BytesRefHash(new ByteBlockPool(new ByteBlockPool.DirectAllocator()), 16, array);
  }

  @Override
  public int hashCode() {
    final int prime = 1279;
    return (int) (prime * termCountCutoff + Double.doubleToLongBits(docCountPercent));
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (this.getClass() != obj.getClass())
      return false;

    final NodeConstantScoreAutoRewrite other = (NodeConstantScoreAutoRewrite) obj;
    if (other.termCountCutoff != termCountCutoff) {
      return false;
    }

    if (Double.doubleToLongBits(other.docCountPercent) != Double.doubleToLongBits(docCountPercent)) {
      return false;
    }

    return true;
  }

  /**
   * Special implementation of BytesStartArray that keeps parallel arrays for
   * {@link TermContext}
   */
  static final class TermStateByteStart extends DirectBytesStartArray  {

    TermContext[] termState;

    public TermStateByteStart(final int initSize) {
      super(initSize);
    }

    @Override
    public int[] init() {
      final int[] ord = super.init();
      termState = new TermContext[ArrayUtil.oversize(ord.length,
        RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
      assert termState.length >= ord.length;
      return ord;
    }

    @Override
    public int[] grow() {
      final int[] ord = super.grow();
      if (termState.length < ord.length) {
        final TermContext[] tmpTermState = new TermContext[ArrayUtil.oversize(ord.length,
          RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
        System.arraycopy(termState, 0, tmpTermState, 0, termState.length);
        termState = tmpTermState;
      }
      assert termState.length >= ord.length;
      return ord;
    }

    @Override
    public int[] clear() {
     termState = null;
     return super.clear();
    }

  }

}
