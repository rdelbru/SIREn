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

import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.must;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.should;
import static org.sindice.siren.search.AbstractTestSirenScorer.NodeBooleanQueryBuilder.nbq;
import static org.sindice.siren.search.AbstractTestSirenScorer.TupleQueryBuilder.tuple;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.search.AbstractTestSirenScorer;
import org.sindice.siren.util.XSDDatatype;


public class TestTupleQuery extends AbstractTestSirenScorer {

  @Override
  protected void configure() throws IOException {
    this.setAnalyzer(AnalyzerType.TUPLE);
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    ((TupleAnalyzer) analyzer).registerDatatype(XSDDatatype.XSD_ANY_URI.toCharArray(), uriAnalyzer);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  @Test
  public void testEquality() throws Exception {
    final NodeQuery query1 = tuple().optional(
      nbq(should("value1"), should("value2"))
    ).getNodeQuery();

    final NodeQuery query2 = tuple().optional(
      nbq(should("value1"), should("value2"))
    ).getNodeQuery();

    assertEquals(query1, query2);
  }

  @Test
  public void testUnaryClause() throws IOException {
    this.addDocument("\"aaa ccc\" \"bbb ccc\" . \"aaa bbb\" \"ccc eee\" . ");

    // {[aaa]}
    Query query = tuple().optional(nbq(should("aaa"))).getLuceneProxyQuery();
    TopDocs hits = searcher.search(query, 100);
    assertEquals(1, hits.totalHits);

    // {[ccc]}
    query = tuple().optional(nbq(should("ccc"))).getLuceneProxyQuery();
    hits = searcher.search(query, 100);
    assertEquals(1, hits.totalHits);

    // {[+ccc +aaa]}
    query = tuple().optional(nbq(must("aaa"), must("ccc"))).getLuceneProxyQuery();
    hits = searcher.search(query, 100);
    assertEquals(1, hits.totalHits);

    // {[ddd]}
    query = tuple().optional(nbq(should("ddd"))).getLuceneProxyQuery();
    hits = searcher.search(query, 100);
    assertEquals(0, hits.totalHits);

    // {[+bbb +eee]}
    query = tuple().optional(nbq(must("bbb"), must("eee"))).getLuceneProxyQuery();
    hits = searcher.search(query, 100);
    assertEquals(0, hits.totalHits);
  }

  /**
   * <code>aaa ({[+ddd] [+eee]})</code>
   * Here, the keywords are mandatory in the cell, but each cell is optional in
   * the tuple. So, even the first document (having only one matching cell)
   * should match.
   */
  @Test
  public void testParenthesisMust() throws IOException {
    this.addDocument("\"bbb\" . \"ccc\" \"ddd bbb\" . ");
    this.addDocument("\"bbb\" . \"ccc eee\" \"ddd bbb\" . ");
    this.addDocument("\"bbb\" . ");

    final Query query = tuple()
      .optional(nbq(must("ddd")), nbq(must("eee")))
      .getLuceneProxyQuery();

    final BooleanQuery q = new BooleanQuery();
    q.add(new TermQuery(new Term(DEFAULT_TEST_FIELD, "aaa")), BooleanClause.Occur.SHOULD);
    q.add(query, BooleanClause.Occur.SHOULD);
    assertEquals(2, searcher.search(q, 100).totalHits);
  }

  /**
   * <code>bbb ({[+ddd] [+eee]})</code>
   */
  @Test
  public void testParenthesisMust2() throws IOException {
    this.addDocument("\"bbb\" . ");

    final Query query = tuple()
      .optional(nbq(must("ddd")), nbq(must("eee")))
      .getLuceneProxyQuery();

    final BooleanQuery q = new BooleanQuery();
    q.add(new TermQuery(new Term(DEFAULT_TEST_FIELD, "bbb")), BooleanClause.Occur.SHOULD);
    q.add(query, BooleanClause.Occur.SHOULD);
    assertEquals(1, searcher.search(q, 100).totalHits);
  }

  /**
   * <code>bbb +({[ddd] [eee]})</code>
   * Here, the keywords are mandatory in the cell, but each cell is optional in
   * the tuple. So, even the first document (having only one cell) should match.
   */
  @Test
  public void testParenthesisMust3() throws IOException {
    this.addDocument("\"aaa\" . \"eee\" \"bbb\" . ");
    this.addDocument("\"bbb\" . \"ccc\" \"ddd bbb\" . ");
    this.addDocument("\"bbb\" . \"ccc eee\" \"ddd bbb\" . ");

    final Query query = tuple()
      .optional(nbq(should("ddd")), nbq(should("eee")))
      .getLuceneProxyQuery();

    final BooleanQuery q = new BooleanQuery();
    q.add(new TermQuery(new Term(DEFAULT_TEST_FIELD, "aaa")), BooleanClause.Occur.SHOULD);
    q.add(query, BooleanClause.Occur.MUST);
    assertEquals(3, searcher.search(q, 100).totalHits);
  }

  /**
   * <code>{[bbb eee]} {[ccc eee]}</code>
   */
  @Test
  public void testParenthesisShould() throws IOException {
    this.addDocument("\"bbb eee\" . \"ccc eee\" . ");
    this.addDocument("\"bbb\" . \"aaa\" . ");
    this.addDocument("\"eee\" . \"aaa\" . ");
    this.addDocument("\"aaa\" . \"ccc\" . ");
    this.addDocument("\"aaa\" . \"eee\" . ");

    final Query tq1 = tuple()
      .optional(nbq(should("bbb"), should("eee")))
      .getLuceneProxyQuery();

    final Query tq2 = tuple()
      .optional(nbq(should("ccc"), should("eee")))
      .getLuceneProxyQuery();

    final BooleanQuery q = new BooleanQuery();
    q.add(tq1, BooleanClause.Occur.SHOULD);
    q.add(tq2, BooleanClause.Occur.SHOULD);
    assertEquals(5, searcher.search(q, 100).totalHits);
  }

  /**
   * <code>+{+[+actor]} +{+[+actor]}</code>
   */
  @Test
  public void testLuceneBooleanMust() throws IOException {
    this.addDocument("<actor> \"actor 1\" <birthdate> \"456321\" . " +
    		"<actor> \"actor 2\" <birthdate> \"456321\" . ");
    this.addDocument("<actor> \"actor 1\" . <actor> \"actor 2\" . ");

    final Query tq1 = tuple()
      .with(nbq(must("actor")).bound(0,0))
      .getLuceneProxyQuery();

    final Query tq2 = tuple()
      .with(nbq(must("actor")).bound(1,1))
      .getLuceneProxyQuery();

    final BooleanQuery bq = new BooleanQuery();
    bq.add(tq1, Occur.MUST);
    bq.add(tq2, Occur.MUST);

    assertEquals(2, searcher.search(bq, 100).totalHits);
  }

}
