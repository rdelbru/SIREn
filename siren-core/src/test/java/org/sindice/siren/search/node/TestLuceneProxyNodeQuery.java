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

import static org.sindice.siren.search.AbstractTestSirenScorer.NodeTermQueryBuilder.ntq;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.junit.Test;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.search.AbstractTestSirenScorer;

public class TestLuceneProxyNodeQuery
extends AbstractTestSirenScorer {

  @Override
  protected void configure()
  throws IOException {
    this.setAnalyzer(AnalyzerType.TUPLE);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  @Test
  public void testBoost()
  throws Exception {
    final float boost = 2.5f;

    this.addDocument("\"aaa ccc\" \"one five\" . \"aaa bbb\" \"ccc eee\" .");

    BooleanQuery bq1 = new BooleanQuery();
    NodeTermQuery tq = new NodeTermQuery(new Term (DEFAULT_TEST_FIELD, "one"));
    tq.setBoost(boost);
    bq1.add(new LuceneProxyNodeQuery(tq), Occur.MUST);
    bq1.add(new LuceneProxyNodeQuery(new NodeTermQuery(new Term (DEFAULT_TEST_FIELD, "five"))), Occur.MUST);

    BooleanQuery bq2 = new BooleanQuery();
    tq = new NodeTermQuery(new Term (DEFAULT_TEST_FIELD, "one"));
    LuceneProxyNodeQuery dq = new LuceneProxyNodeQuery(tq);
    dq.setBoost(boost);
    bq2.add(dq, Occur.MUST);
    bq2.add(new LuceneProxyNodeQuery(new NodeTermQuery(new Term (DEFAULT_TEST_FIELD, "five"))), Occur.MUST);

    assertScoreEquals(bq1, bq2);
  }

  /**
   * Tests whether the scores of the two queries are the same.
   */
  public void assertScoreEquals(Query q1, Query q2)
  throws Exception {
    ScoreDoc[] hits1 = searcher.search (q1, null, 1000).scoreDocs;
    ScoreDoc[] hits2 = searcher.search (q2, null, 1000).scoreDocs;

    assertEquals(hits1.length, hits2.length);

    for (int i = 0; i < hits1.length; i++) {
      assertEquals(hits1[i].score, hits2[i].score, 0.0000001f);
    }
  }

  @Test
  public void testExplain()
  throws Exception {
    this.setAnalyzer(AnalyzerType.JSON);

    this.addDocument("{\"aaa\" : \"bbb\"}");

    Query query = ntq("aaa").getLuceneProxyQuery();
    final Explanation exp = searcher.explain(query, 0);
    assertTrue(exp.getValue() != 0);
  }

}
