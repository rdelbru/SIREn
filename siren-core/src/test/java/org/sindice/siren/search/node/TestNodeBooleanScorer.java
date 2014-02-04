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

import static org.sindice.siren.analysis.MockSirenToken.node;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.must;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.not;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.should;
import static org.sindice.siren.search.AbstractTestSirenScorer.NodeBooleanQueryBuilder.nbq;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;
import org.sindice.siren.index.DocsAndNodesIterator;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.search.AbstractTestSirenScorer;

public class TestNodeBooleanScorer extends AbstractTestSirenScorer {

  @Override
  protected void configure() throws IOException {
    this.setAnalyzer(AnalyzerType.TUPLE);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  @Test
  public void testNextReqOpt() throws Exception {
    this.addDocuments(
      "\"aaa bbb\" \"aaa ccc\" . \"ccc\" \"bbb ccc\" . ",
      "\"aaa\" \"aaa bbb\" . "
    );

    final NodeScorer scorer = this.getScorer(
      nbq(must("aaa"), should("bbb"))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,0), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,0), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  @Test
  public void testNextOptExcl() throws Exception {
    this.addDocuments(
      "\"aaa bbb\" \"aaa ccc\" . \"ccc\" \"bbb ccc\" . ",
      "\"aaa\" \"aaa bbb\" . "
    );

    final NodeScorer scorer = this.getScorer(
      nbq(should("bbb"), not("ccc"))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  @Test
  public void testNextReqOptExcl() throws Exception {
    this.addDocuments(
      "\"aaa bbb\" \"aaa ccc\" . \"aaa bbb ccc\" \"bbb ccc\" . ",
      "\"aaa\" \"aaa bbb\" . "
    );

    final NodeScorer scorer = this.getScorer(
      nbq(must("aaa"), should("bbb"), not("ccc"))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,0), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  @Test
  public void testNextReqOptExclWithConstraint() throws Exception {
    this.addDocuments(
      "\"aaa bbb\" \"aaa ccc\" . \"aaa bbb ccc\" \"bbb ccc\" . ",
      "\"aaa\" \"aaa bbb\" . "
    );

    final NodeScorer scorer = this.getScorer(
      nbq(must("aaa"), should("bbb"), not("ccc")).bound(1,1)
                                                 .level(2)
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  /**
   * Single clause rewrite should keep node constraint information.
   * <p>
   * The {@link NodeBooleanQuery} with one clause will be rewritten into a
   * single node term query.
   */
  @Test
  public void testSingleClauseRewriteWithConstraint() throws Exception {
    this.addDocuments(
      "\"aaa bbb\" \"aaa ccc\" . "
    );

    NodeScorer scorer = this.getScorer(nbq(must("aaa")).level(1));

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(nbq(must("aaa")).bound(1,2));

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  /**
   * Clause rewrite should keep node constraint information
   */
  @Test
  public void testClauseRewriteWithConstraint() throws Exception {
    this.addDocuments(
      "\"aaa bbb\" \"aaa bbb\" . "
    );

    // Each nested boolean query will be rewritten into a single clause,
    // therefore the top level boolean query will be rewritten too.
    NodeScorer scorer = this.getScorer(
      nbq(
        must(nbq(must("aaa"))),
        must(nbq(must("bbb")))
      ).level(1)
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      nbq(
        must(nbq(must("aaa"))),
        must(nbq(must("bbb")))
      ).bound(0,0)
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  @Test
  public void testSingleMatchScore() throws Exception {
    this.addDocuments(
      "\"ccc bbb\" \"aaa bbb\" . "
    );

    NodeScorer scorer = this.getScorer(
      nbq(
        must(nbq(should("aaa")))
      )
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,1), scorer.node());
    final float d0score01 = scorer.scoreInNode();
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      nbq(
        must(nbq(must("ccc")))
      )
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(0,0), scorer.node());
    final float d0score00 = scorer.scoreInNode();
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(d0score01 + " == " + d0score00, d0score01 == d0score00);
    assertEndOfStream(scorer);
  }

  // Unit Tests coming from SirenCellQuery

  @Test
  public void testUnaryClause() throws IOException {
    this.addDocuments(new String[] { "\"aaa ccc\" .",
                                      "\"bbb\" . \"ddd eee\" . ",
                                      "\"ccc ccc\" . \"ccc ccc\" . " });

    Query q = nbq(must("aaa")).getLuceneProxyQuery();
    assertEquals(1, searcher.search(q, 10).totalHits);

    q = nbq(must("bbb")).getLuceneProxyQuery();
    assertEquals(1, searcher.search(q, 10).totalHits);

    q = nbq(must("ccc")).getLuceneProxyQuery();
    assertEquals(2, searcher.search(q, 10).totalHits);

    q = nbq(must("ddd")).getLuceneProxyQuery();
    assertEquals(1, searcher.search(q, 10).totalHits);

    q = nbq(must("eee")).getLuceneProxyQuery();
    assertEquals(1, searcher.search(q, 10).totalHits);
  }

  @Test
  public void testUnaryClauseWithIndexConstraint()
  throws Exception {
    this.addDocuments(new String[] { "\"aaa\" \"bbb\" \"ccc\" .",
                                     "\"ccc\" \"bbb\" \"aaa\" ." });

    final Query q = nbq(must("aaa")).bound(0,0).getLuceneProxyQuery();
    assertEquals(1, searcher.search(q, 10).totalHits);
  }

  /**
   * <code>cell(aaa bbb ccc ddd eee)</code>
   */
  @Test
  public void testFlat() throws IOException {
    this.addDocuments(new String[] { "\"aaa ccc\" .",
                                     "\"bbb\" . \"ddd eee\" . ",
                                     "\"ccc ccc\" . \"ccc ccc\" . " });

    final Query q = nbq(should("aaa"), should("bbb"), should("ccc"),
                        should("ddd"), should("eee")).getLuceneProxyQuery();

    assertEquals(3, searcher.search(q, 10).totalHits);
  }

  /**
   * <code>bbb cell(+ddd +eee)</code>
   */
  @Test
  public void testParenthesisMust() throws IOException {
    this.addDocument("\"bbb\" . \"ddd eee\" . ");

    final Query nested = nbq(must("ddd"), must("eee")).getLuceneProxyQuery();
    final BooleanQuery bq = new BooleanQuery();
    bq.add(new TermQuery(new Term(DEFAULT_TEST_FIELD, "aaa")), BooleanClause.Occur.SHOULD);
    bq.add(nested, BooleanClause.Occur.SHOULD);
    assertEquals(1, searcher.search(bq, 10).totalHits);
  }

  /**
   * <code>aaa +cell(ddd eee)</code>
   */
  @Test
  public void testParenthesisMust2() throws IOException {
    this.addDocument("\"bbb\" . \"ddd eee\" . ");

    final Query nested = nbq(should("ddd"), should("eee")).getLuceneProxyQuery();
    final BooleanQuery bq = new BooleanQuery();
    bq.add(new TermQuery(new Term(DEFAULT_TEST_FIELD, "bbb")), BooleanClause.Occur.SHOULD);
    bq.add(nested, BooleanClause.Occur.MUST);
    assertEquals(1, searcher.search(bq, 10).totalHits);
  }

  /**
   * <code>cell(ddd ccc) cell(eee ccc)</code>
   */
  @Test
  public void testParenthesisShould() throws IOException {
    this.addDocument("\"bbb\" . \"ddd eee\" . ");

    final Query nested1 = nbq(should("ddd"), should("ccc")).getLuceneProxyQuery();
    final Query nested2 = nbq(should("eee"), should("ccc")).getLuceneProxyQuery();

    final BooleanQuery q = new BooleanQuery();
    q.add(nested1, BooleanClause.Occur.SHOULD);
    q.add(nested2, BooleanClause.Occur.SHOULD);
    assertEquals(1, searcher.search(q, 10).totalHits);
  }

  /**
   * <code>cell(+ddd +eee)</code>
   */
  @Test
  public void testMust() throws IOException {
    this.addDocuments(new String[] { "\"eee\" . \"ddd\" . ",
                                     "\"bbb\" . \"ddd eee\" . " });

    final Query q = nbq(must("ddd"), must("eee")).getLuceneProxyQuery();

    assertEquals(1, searcher.search(q, 10).totalHits);
  }

  /**
   * <code>cell(+ddd +eee)</code>, same tuple but not the same cell
   */
  @Test
  public void testMust2() throws IOException {
    this.addDocuments(new String[] { "\"eee\" \"ddd\" . ",
                                     "\"bbb\" \"ddd eee\" . " });

    final Query q = nbq(must("ddd"), must("eee")).getLuceneProxyQuery();

    assertEquals(1, searcher.search(q, 10).totalHits);
  }

  /**
   * <code>cell(+ddd eee)</code>
   */
  @Test
  public void testMustShould() throws IOException {
    this.addDocuments(new String[] { "\"eee\" . \"ddd\" . ",
                                     "\"bbb\" . \"ddd eee\" . " });

    final Query q = nbq(must("ddd"), should("eee")).getLuceneProxyQuery();

    assertEquals(2, searcher.search(q, 10).totalHits);
  }

  /**
   * <code>cell(+ddd -eee)</code>
   */
  @Test
  public void testMustMustNot() throws IOException {
    this.addDocuments(new String[] { "\"eee\" . \"ddd aaa\" . ",
                                     "\"bbb\" . \"ddd eee\" . " });

    final Query q = nbq(must("ddd"), not("eee")).getLuceneProxyQuery();

    assertEquals(1, searcher.search(q, 10).totalHits);
  }

  /**
   * <code>cell(eee bbb)</code>
   */
  @Test
  public void testShould() throws IOException {
    this.addDocuments(new String[] { "\"eee\" . \"ddd\" . ",
                                     "\"bbb\" . \"ddd eee\" . " });

    final Query q = nbq(should("ddd"), should("eee")).getLuceneProxyQuery();

    assertEquals(2, searcher.search(q, 10).totalHits);
  }

  /**
   * <code>cell(ddd -eee)</code>
   */
  @Test
  public void testShouldMustNot() throws IOException {
    this.addDocuments(new String[] { "\"eee\" . \"ddd\" . ",
                                     "\"bbb\" . \"ddd eee\" . " });

    final Query q = nbq(should("ddd"), not("eee")).getLuceneProxyQuery();

    assertEquals(1, searcher.search(q, 10).totalHits);
  }

  /**
   * SRN-99
   * <code>cell(+(aaa bbb) +(ccc ddd))</code>
   */
  @Test
  public void testReqNestedCellQuery() throws IOException {
    this.addDocuments(new String[] { "\"ccc\" . \"aaa ddd\" . ",
                                     "\"bbb\" . \"ddd ccc\" . " });

    final Query q = nbq(
        must(nbq(should("aaa"), should("bbb"))),
        must(nbq(should("ccc"), should("ddd")))
      ).getLuceneProxyQuery();

    assertEquals(1, searcher.search(q, 10).totalHits);
  }

  /**
   * SRN-99
   * <code>cell(+(aaa bbb) -(ccc ddd))</code>
   */
  @Test
  public void testReqExclNestedCellQuery() throws IOException {
    this.addDocuments(new String[] { "\"ccc\" . \"aaa ddd\" . ",
                                     "\"bbb\" . \"ddd ccc\" . " });

    final Query q = nbq(
      must(nbq(should("aaa"), should("bbb"))),
      not(nbq(should("ccc"), should("ddd")))
    ).getLuceneProxyQuery();

    assertEquals(1, searcher.search(q, 10).totalHits);
  }

  /**
   * SRN-99
   * <code>cell(+(aaa bbb) (ccc ddd))</code>
   */
  @Test
  public void testReqOptNestedCellQuery() throws IOException {
    this.addDocuments(new String[] { "\"ccc\" . \"aaa ddd\" . ",
                                     "\"bbb\" . \"ddd ccc\" . " });

    final Query q = nbq(
      must(nbq(should("aaa"), should("bbb"))),
      should(nbq(should("ccc"), should("ddd")))
    ).getLuceneProxyQuery();

    final TopDocs docs = searcher.search(q, 10);
    assertEquals(2, docs.totalHits);
    assertEquals(0, docs.scoreDocs[0].doc); // first doc should be ranked higher

  }


}
