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
import java.util.ArrayList;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MultiTermQuery.ConstantScoreAutoRewrite;
import org.apache.lucene.search.Query;
import org.sindice.siren.search.SirenBooleanClause.Occur;

/**
 * Code taken from {@link ConstantScoreAutoRewrite} and adapted for SIREn.
 */
public class SirenConstantScoreAutoRewrite extends SirenTermCollectingRewrite<SirenBooleanQuery> {

  // Term cutoff deactivated until a efficient filter-based approach is found
  public static int DEFAULT_TERM_COUNT_CUTOFF = Integer.MAX_VALUE;

  // Document cutoff deactivated until a efficient filter-based approach is found
  public static double DEFAULT_DOC_COUNT_PERCENT = 200;

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
  protected SirenBooleanQuery getTopLevelQuery() {
    return new SirenBooleanQuery(true);
  }

  @Override
  protected void addClause(final SirenBooleanQuery topLevel, final Term term, final float boost /*ignored*/) {
    topLevel.add(new SirenTermQuery(term), Occur.SHOULD);
  }

  @Override
  public Query rewrite(final IndexReader reader, final SirenMultiTermQuery query) throws IOException {

    // Get the enum and start visiting terms.  If we
    // exhaust the enum before hitting either of the
    // cutoffs, we use ConstantBooleanQueryRewrite; else,
    // ConstantFilterRewrite:
    final int docCountCutoff = (int) ((docCountPercent / 100.) * reader.maxDoc());
    final int termCountLimit = Math.min(BooleanQuery.getMaxClauseCount(), termCountCutoff);

    final CutOffTermCollector col = new CutOffTermCollector(reader, docCountCutoff, termCountLimit);
    this.collectTerms(reader, query, col);

    if (col.hasCutOff) {
      return SirenMultiTermQuery.CONSTANT_SCORE_FILTER_REWRITE.rewrite(reader, query);
    } else {
      final Query result;
      if (col.pendingTerms.isEmpty()) {
        result = this.getTopLevelQuery();
      } else {
        final SirenBooleanQuery bq = this.getTopLevelQuery();
        for(final Term term : col.pendingTerms) {
          this.addClause(bq, term, 1.0f);
        }
        // Strip scores
        result = new SirenConstantScoreQuery(bq);
        result.setBoost(query.getBoost());
      }
      query.incTotalNumberOfTerms(col.pendingTerms.size());
      return result;
    }
  }

  private static final class CutOffTermCollector implements TermCollector {
    CutOffTermCollector(final IndexReader reader, final int docCountCutoff, final int termCountLimit) {
      this.reader = reader;
      this.docCountCutoff = docCountCutoff;
      this.termCountLimit = termCountLimit;
    }

    public boolean collect(final Term t, final float boost) throws IOException {
      pendingTerms.add(t);
      // Loading the TermInfo from the terms dict here
      // should not be costly, because 1) the
      // query/filter will load the TermInfo when it
      // runs, and 2) the terms dict has a cache:
      docVisitCount += reader.docFreq(t);
      if (pendingTerms.size() >= termCountLimit || docVisitCount >= docCountCutoff) {
        hasCutOff = true;
        return false;
      }
      return true;
    }

    int docVisitCount = 0;
    boolean hasCutOff = false;

    final IndexReader reader;
    final int docCountCutoff, termCountLimit;
    final ArrayList<Term> pendingTerms = new ArrayList<Term>();
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

    final SirenConstantScoreAutoRewrite other = (SirenConstantScoreAutoRewrite) obj;
    if (other.termCountCutoff != termCountCutoff) {
      return false;
    }

    if (Double.doubleToLongBits(other.docCountPercent) != Double.doubleToLongBits(docCountPercent)) {
      return false;
    }

    return true;
  }

}
