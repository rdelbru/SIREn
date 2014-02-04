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
import static org.sindice.siren.search.AbstractTestSirenScorer.NodeBooleanQueryBuilder.nbq;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.must;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Scorer;
import org.junit.Test;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.search.AbstractTestSirenScorer;

public class TestLuceneProxyNodeScorer extends AbstractTestSirenScorer {

  @Override
  protected void configure() throws IOException {
    this.setAnalyzer(AnalyzerType.JSON);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  @Test
  public void testNextDoc()
  throws Exception {
    this.addDocuments(
      "{ \"aaa bbb\" : \"aaa ccc\" , \"ccc\" \"bbb ccc\" }",
      "{ \"aaa\" : \"aaa bbb ddd\" }"
    );

    final Scorer scorer1 = this.getScorer(
      ntq("aaa").getLuceneProxyQuery()
    );

    assertTrue(scorer1.nextDoc() != DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer1.docID());
    assertEquals(2, scorer1.freq(), 0);
    assertTrue(scorer1.nextDoc() != DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer1.docID());
    assertEquals(2, scorer1.freq(), 0);
    assertTrue(scorer1.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);

    final Scorer scorer2 = this.getScorer(
      ntq("ccc").getLuceneProxyQuery()
    );

    assertTrue(scorer2.nextDoc() != DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer2.docID());
    assertEquals(3, scorer2.freq(), 0);
    assertTrue(scorer2.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);

    final Scorer scorer3 = this.getScorer(
      ntq("ddd").getLuceneProxyQuery()
    );

    assertTrue(scorer3.nextDoc() != DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer3.docID());
    assertEquals(1, scorer3.freq(), 0);
    assertTrue(scorer3.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testAdvance()
  throws Exception {
    this.addDocuments(
      "{ \"baba\" : \"aaa ccc\" , \"ccc\" \"bbb ccc\" }",
      "{ \"aaa\" : \"aaa bbb ddd\" }",
      "{ \"ddd\" : [ \"bobo\", \"bibi\" ] }"
    );

    final Scorer scorer1 = this.getScorer(
      ntq("bobo").getLuceneProxyQuery()
    );

    assertTrue(scorer1.advance(2) != DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(2, scorer1.docID());
    assertEquals(1, scorer1.freq(), 0);
    assertTrue(scorer1.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);

    final Scorer scorer2 = this.getScorer(
      ntq("baba").getLuceneProxyQuery()
    );
    assertTrue(scorer2.advance(2) == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testAdvanceInfiniteLoop()
  throws Exception {
    this.addDocuments(
      "{ \"baba\" : \"bibi ccc\" , \"ccc\" \"bbb ccc\" }",
      "{ \"baba bibi baba bibi\" : \"aaa bbb ddd\" }",
      "{ \"baba bibi\" : \"aaa bbb ddd\" }"
    );

    final Scorer scorer1 = this.getScorer(
      nbq(must("baba", "bibi")).getLuceneProxyQuery()
    );

    assertTrue(scorer1.advance(0) != DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer1.docID());
    assertEquals(2, scorer1.freq(), 0);
    final float score1 = scorer1.score();
    assertTrue(scorer1.nextDoc() != DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(2, scorer1.docID());
    assertEquals(2, scorer1.freq(), 0);
    final float score2 = scorer1.score();
    assertTrue(score1 > score2);
    assertTrue(scorer1.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

}
