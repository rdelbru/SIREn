/**
 * Copyright 2009, Renaud Delbru
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
 * @project siren
 * @author Renaud Delbru [ 7 Jul 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;


import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.analysis.TupleAnalyzer;

public class TestSirenTupleQuery {

  private final SirenTermQuery aaa = new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "aaa"));
  private final SirenTermQuery bbb = new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "bbb"));
  private final SirenTermQuery ccc = new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ccc"));
  private final SirenTermQuery ddd = new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ddd"));
  private final SirenTermQuery eee = new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "eee"));

  private QueryTestingHelper _helper = null;

  @Before
  public void setUp()
  throws Exception {
    _helper = new QueryTestingHelper(new TupleAnalyzer(new StandardAnalyzer(Version.LUCENE_31)));
  }

  @After
  public void tearDown()
  throws Exception {
    _helper.close();
  }

  @Test
  public void testEquality() throws Exception {
    final String fieldName = QueryTestingHelper.DEFAULT_FIELD;
    final SirenBooleanQuery bq1a = new SirenBooleanQuery();
    bq1a.add(new SirenTermQuery(new Term(fieldName, "value1")), SirenBooleanClause.Occur.SHOULD);
    final SirenBooleanQuery bq1b = new SirenBooleanQuery();
    bq1b.add(new SirenTermQuery(new Term(fieldName, "value2")), SirenBooleanClause.Occur.SHOULD);
    final SirenTupleQuery tq1 = new SirenTupleQuery();
    tq1.add(new SirenCellQuery(bq1a), SirenTupleClause.Occur.SHOULD);
    tq1.add(new SirenCellQuery(bq1b), SirenTupleClause.Occur.SHOULD);

    final SirenBooleanQuery bq2a = new SirenBooleanQuery();
    bq2a.add(new SirenTermQuery(new Term(fieldName, "value1")), SirenBooleanClause.Occur.SHOULD);
    final SirenBooleanQuery bq2b = new SirenBooleanQuery();
    bq2b.add(new SirenTermQuery(new Term(fieldName, "value2")), SirenBooleanClause.Occur.SHOULD);
    final SirenTupleQuery tq2 = new SirenTupleQuery();
    tq2.add(new SirenCellQuery(bq1a), SirenTupleClause.Occur.SHOULD);
    tq2.add(new SirenCellQuery(bq1b), SirenTupleClause.Occur.SHOULD);

    assertEquals(tq1, tq2);
  }

  @Test
  public void testUnaryClause() throws IOException {
    _helper.addDocument("\"aaa ccc\" \"bbb ccc\" . \"aaa bbb\" \"ccc eee\" . ");

    // {[aaa]}
    SirenBooleanQuery cq = new SirenBooleanQuery();
    cq.add(aaa, SirenBooleanClause.Occur.SHOULD);
    SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq), SirenTupleClause.Occur.SHOULD);
    assertEquals(1, _helper.search(cq).length);

    // {[ccc]}
    cq = new SirenBooleanQuery();
    cq.add(ccc, SirenBooleanClause.Occur.SHOULD);
    tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq), SirenTupleClause.Occur.SHOULD);
    assertEquals(1, _helper.search(cq).length);

    // {[+ccc +aaa]}
    cq = new SirenBooleanQuery();
    cq.add(ccc, SirenBooleanClause.Occur.MUST);
    cq.add(aaa, SirenBooleanClause.Occur.MUST);
    tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq), SirenTupleClause.Occur.SHOULD);
    assertEquals(1, _helper.search(cq).length);

    // {[ddd]}
    cq = new SirenBooleanQuery();
    cq.add(ddd, SirenBooleanClause.Occur.SHOULD);
    tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq), SirenTupleClause.Occur.SHOULD);
    assertEquals(0, _helper.search(cq).length);

    // {[bbb eee]}
    cq = new SirenBooleanQuery();
    cq.add(bbb, SirenBooleanClause.Occur.MUST);
    cq.add(eee, SirenBooleanClause.Occur.MUST);
    tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq), SirenTupleClause.Occur.SHOULD);
    assertEquals(0, _helper.search(cq).length);
  }

  /**
   * <code>bbb ({[+ddd] [+eee]})</code>
   * Here, the keywords are mandatory in the cell, but each cell is optional in
   * the tuple. So, even the first document (having only one matching cell)
   * should match.
   */
  @Test
  public void testParenthesisMust() throws IOException {
    _helper.addDocument("\"bbb\" . \"ccc\" \"ddd bbb\" . ");
    _helper.addDocument("\"bbb\" . \"ccc eee\" \"ddd bbb\" . ");
    _helper.addDocument("\"bbb\" . ");

    final SirenBooleanQuery cq1 = new SirenBooleanQuery();
    cq1.add(ddd, SirenBooleanClause.Occur.MUST);
    final SirenBooleanQuery cq2 = new SirenBooleanQuery();
    cq2.add(eee, SirenBooleanClause.Occur.MUST);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq1), SirenTupleClause.Occur.SHOULD);
    tq.add(new SirenCellQuery(cq2), SirenTupleClause.Occur.SHOULD);

    final BooleanQuery q = new BooleanQuery();
    q.add(aaa, BooleanClause.Occur.SHOULD);
    q.add(tq, BooleanClause.Occur.SHOULD);
    assertEquals(2, _helper.search(q).length);
  }

  /**
   * <code>bbb ({[+ddd] [+eee]})</code>
   * Here, the keywords are mandatory in the cell, but each cell is optional in
   * the tuple. So, even the first document (having only one matching cell)
   * should match.
   */
  @Test
  public void testParenthesisMust2() throws IOException {
    _helper.addDocument("\"bbb\" . ");

    final SirenBooleanQuery cq1 = new SirenBooleanQuery();
    cq1.add(ddd, SirenBooleanClause.Occur.MUST);
    final SirenBooleanQuery cq2 = new SirenBooleanQuery();
    cq2.add(eee, SirenBooleanClause.Occur.MUST);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq1), SirenTupleClause.Occur.SHOULD);
    tq.add(new SirenCellQuery(cq2), SirenTupleClause.Occur.SHOULD);

    final BooleanQuery q = new BooleanQuery();
    q.add(bbb, BooleanClause.Occur.SHOULD);
    q.add(tq, BooleanClause.Occur.SHOULD);
    assertEquals(1, _helper.search(q).length);
  }

  /**
   * <code>bbb +({[ddd] [eee]})</code>
   * Here, the keywords are mandatory in the cell, but each cell is optional in
   * the tuple. So, even the first document (having only one cell) should match.
   */
  @Test
  public void testParenthesisMust3() throws IOException {
    _helper.addDocument("\"aaa\" . \"eee\" \"bbb\" . ");
    _helper.addDocument("\"bbb\" . \"ccc\" \"ddd bbb\" . ");
    _helper.addDocument("\"bbb\" . \"ccc eee\" \"ddd bbb\" . ");

    final SirenBooleanQuery cq1 = new SirenBooleanQuery();
    cq1.add(ddd, SirenBooleanClause.Occur.SHOULD);
    final SirenBooleanQuery cq2 = new SirenBooleanQuery();
    cq2.add(eee, SirenBooleanClause.Occur.SHOULD);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq1), SirenTupleClause.Occur.SHOULD);
    tq.add(new SirenCellQuery(cq2), SirenTupleClause.Occur.SHOULD);

    final BooleanQuery q = new BooleanQuery();
    q.add(aaa, BooleanClause.Occur.SHOULD);
    q.add(tq, BooleanClause.Occur.MUST);
    assertEquals(3, _helper.search(q).length);
  }

  /**
   * <code>{[bbb eee]} {[ccc eee]}</code>
   */
  @Test
  public void testParenthesisShould() throws IOException {
    _helper.addDocument("\"bbb eee\" . \"ccc eee\" . ");
    _helper.addDocument("\"bbb\" . \"aaa\" . ");
    _helper.addDocument("\"eee\" . \"aaa\" . ");
    _helper.addDocument("\"aaa\" . \"ccc\" . ");
    _helper.addDocument("\"aaa\" . \"eee\" . ");

    final SirenBooleanQuery cq1 = new SirenBooleanQuery();
    cq1.add(bbb, SirenBooleanClause.Occur.SHOULD);
    cq1.add(eee, SirenBooleanClause.Occur.SHOULD);

    final SirenTupleQuery tq1 = new SirenTupleQuery();
    tq1.add(new SirenCellQuery(cq1), SirenTupleClause.Occur.SHOULD);

    final SirenBooleanQuery cq2 = new SirenBooleanQuery();
    cq2.add(ccc, SirenBooleanClause.Occur.SHOULD);
    cq2.add(eee, SirenBooleanClause.Occur.SHOULD);

    final SirenTupleQuery tq2 = new SirenTupleQuery();
    tq2.add(new SirenCellQuery(cq2), SirenTupleClause.Occur.SHOULD);

    final BooleanQuery q = new BooleanQuery();
    q.add(tq1, BooleanClause.Occur.SHOULD);
    q.add(tq2, BooleanClause.Occur.SHOULD);
    assertEquals(5, _helper.search(q).length);
  }

  /**
   * <code>{+[ddd] +[eee]}</code>
   */
  @Test
  public void testMust() throws IOException {
    _helper.addDocument("\"eee\" . \"ddd\" . ");
    _helper.addDocument("\"bbb\" . \"ddd eee\" . ");

    final SirenBooleanQuery cq1 = new SirenBooleanQuery();
    cq1.add(ddd, SirenBooleanClause.Occur.SHOULD);
    final SirenBooleanQuery cq2 = new SirenBooleanQuery();
    cq2.add(eee, SirenBooleanClause.Occur.SHOULD);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq1), SirenTupleClause.Occur.MUST);
    tq.add(new SirenCellQuery(cq2), SirenTupleClause.Occur.MUST);

    assertEquals(1, _helper.search(tq).length);
  }

  /**
   * <code>{+[ddd] [eee]}</code>
   */
  @Test
  public void testMustShould() throws IOException {
    _helper.addDocument("\"eee\" \"ddd\" . ");
    _helper.addDocument("\"bbb\" . \"ddd\" . ");
    _helper.addDocument("\"bbb\" . \"eee\" . ");

    final SirenBooleanQuery cq1 = new SirenBooleanQuery();
    cq1.add(ddd, SirenBooleanClause.Occur.SHOULD);
    final SirenBooleanQuery cq2 = new SirenBooleanQuery();
    cq2.add(eee, SirenBooleanClause.Occur.SHOULD);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq1), SirenTupleClause.Occur.MUST);
    tq.add(new SirenCellQuery(cq2), SirenTupleClause.Occur.SHOULD);

    assertEquals(2, _helper.search(tq).length);
  }

  /**
   * <code>{+[ddd] -[eee]}</code>
   */
  @Test
  public void testMustMustNot() throws IOException {
    _helper.addDocument("\"eee\" \"ddd aaa\" . ");
    _helper.addDocument("\"bbb\" \"ddd eee\" . ");
    _helper.addDocument("\"bbb\" \"ddd\" . ");

    final SirenBooleanQuery cq1 = new SirenBooleanQuery();
    cq1.add(ddd, SirenBooleanClause.Occur.SHOULD);
    final SirenBooleanQuery cq2 = new SirenBooleanQuery();
    cq2.add(eee, SirenBooleanClause.Occur.SHOULD);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq1), SirenTupleClause.Occur.MUST);
    tq.add(new SirenCellQuery(cq2), SirenTupleClause.Occur.MUST_NOT);

    assertEquals(1, _helper.search(tq).length);
  }

  /**
   * <code>{[ddd] [eee]}</code>
   */
  @Test
  public void testShould() throws IOException {
    _helper.addDocument("\"eee\" \"ddd\" . ");
    _helper.addDocument("\"bbb\" \"ddd\" . ");

    final SirenBooleanQuery cq1 = new SirenBooleanQuery();
    cq1.add(ddd, SirenBooleanClause.Occur.SHOULD);
    final SirenBooleanQuery cq2 = new SirenBooleanQuery();
    cq2.add(eee, SirenBooleanClause.Occur.SHOULD);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq1), SirenTupleClause.Occur.SHOULD);
    tq.add(new SirenCellQuery(cq2), SirenTupleClause.Occur.SHOULD);

    assertEquals(2, _helper.search(tq).length);
  }

  /**
   * <code>{[ddd] -[eee]}</code>
   */
  @Test
  public void testShouldMustNot() throws IOException {
    _helper.addDocument("\"eee\" . \"ddd\" . ");
    _helper.addDocument("\"bbb\" . \"ddd eee\" . ");

    final SirenBooleanQuery cq1 = new SirenBooleanQuery();
    cq1.add(ddd, SirenBooleanClause.Occur.SHOULD);
    final SirenBooleanQuery cq2 = new SirenBooleanQuery();
    cq2.add(eee, SirenBooleanClause.Occur.SHOULD);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq1), SirenTupleClause.Occur.SHOULD);
    tq.add(new SirenCellQuery(cq2), SirenTupleClause.Occur.MUST_NOT);

    assertEquals(1, _helper.search(tq).length);
  }

  /**
   * <code>+{+[+actor]} +{+[+actor]}</code>
   */
  @Test
  public void testMust2() throws IOException {
    _helper.addDocument("<actor> \"actor 1\" <birthdate> \"456321\" . <actor> \"actor 2\" <birthdate> \"456321\" . ");
    _helper.addDocument("<actor> \"actor 1\" . <actor> \"actor 2\" . ");

    final SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "actor")), SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(0);
    final SirenTupleQuery tq1 = new SirenTupleQuery();
    tq1.add(cq1, SirenTupleClause.Occur.MUST);

    final SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "actor")), SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq2 = new SirenCellQuery(bq1);
    cq2.setConstraint(0);
    final SirenTupleQuery tq2 = new SirenTupleQuery();
    tq2.add(cq2, SirenTupleClause.Occur.MUST);

    final BooleanQuery bq = new BooleanQuery();
    bq.add(tq1, Occur.MUST);
    bq.add(tq2, Occur.MUST);

    assertEquals(2, _helper.search(bq).length);
  }

  /**
   * <code>+{+[+actor]} +{+[+actor]}</code>
   */
  @Test
  public void testTupleConstraintOneClause() throws IOException {
    _helper.addDocument("<aaa> <bbb> . <ccc> <ddd> . ");
    _helper.addDocument("<ccc> . <aaa> <bbb> <ddd> . ");

    final SirenBooleanQuery cq = new SirenBooleanQuery();
    cq.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ccc")), SirenBooleanClause.Occur.MUST);
    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(new SirenCellQuery(cq), SirenTupleClause.Occur.MUST);
    tq.setConstraint(1); // constraint to match only in tuple 1

    assertEquals(1, _helper.search(tq).length);
  }

  /**
   * <code>+{+[+actor]} +{+[+actor]}</code>
   */
  @Test
  public void testTupleConstraintTwoClauses() throws IOException {
    _helper.addDocument("<aaa> <bbb> . <ccc> <ddd> . ");
    _helper.addDocument("<ccc> <ddd> . <aaa> <bbb> <ddd> . ");

    final SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ccc")), SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(0);

    final SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ddd")), SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(1);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(cq1, SirenTupleClause.Occur.MUST);
    tq.add(cq2, SirenTupleClause.Occur.MUST);
    tq.setConstraint(1); // constraint to match only in tuple 1

    assertEquals(1, _helper.search(tq).length);
  }

  /**
   * Test conjunction with exhausted scorer.
   * The scorer of ddd got exhausted, and
   * {@link SirenCellConjunctionScorer#doNext()} was trying to retrieve the
   * entity id from the exhausted scorer.
   */
  @Test
  public void testConjunctionWithExhaustedScorer() throws IOException {
    _helper.addDocument("\"ccc\" . <aaa> \"ddd\" . ");
    _helper.addDocument("\"ccc\" . <aaa> \"ddd eee\" . ");

    final SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(aaa, SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(0);

    final SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(eee, SirenBooleanClause.Occur.MUST_NOT);
    bq2.add(ddd, SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(1, Integer.MAX_VALUE);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(cq1, SirenTupleClause.Occur.MUST);
    tq.add(cq2, SirenTupleClause.Occur.MUST);

    assertEquals(1, _helper.search(tq).length);
  }

  @Test
  public void testMultiValuedPredicate() throws CorruptIndexException, IOException {
    _helper.addDocument("<aaa> \"ddd eee\" \"ddd ccc\" \"ccc eee\" \"eee bbb\" . ");
    _helper.addDocument("<aaa> \"ddd bbb\" \"ddd bbb\" \"eee bbb\" \"eee ccc\" . ");
    _helper.addDocument("<aaa> \"ddd ccc\" \"ddd bbb eee\" \"eee ccc bbb\" \"eee ccc\" . ");
    _helper.addDocument("<aaa> \"ddd eee\" \"ddd eee\" \"eee ccc bbb\" \"eee ccc\" . ");
    _helper.addDocument("<bbb> \"ddd eee\" \"ddd eee\" \"eee ccc ddd\" \"eee ccc\" . ");
    _helper.addDocument("<aaa> \"ddd eee\" \"ddd eee\" \"eee ccc bbb\" \"eee ccc\" . \n" +
    		"<bbb> \"ddd ccc\" \"ddd bbb eee\" \"eee ccc bbb\" \"eee ccc\" .\n" +
    		"<ccc> \"aaa eee ccc\" \"bbb eee ccc\" . ");

    final SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(aaa, SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(0);

    final SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(ddd, SirenBooleanClause.Occur.MUST);
    bq2.add(ccc, SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(1, Integer.MAX_VALUE);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(cq1, SirenTupleClause.Occur.MUST);
    tq.add(cq2, SirenTupleClause.Occur.MUST);

    assertEquals(2, _helper.search(tq).length);
  }

  @Test
  public void testTuple2ReqCell1Excl() throws CorruptIndexException, IOException {
    _helper.addDocument("<aaa> <bbb> <ddd> <eee> . ");
    _helper.addDocument("<aaa> <ccc> <eee> . ");
    _helper.addDocument("<aaa> <ccc> <ddd> . ");
    _helper.addDocument("<aaa> <ccc> <eee> <ddd> . ");

    final SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(aaa, SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(0);

    final SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(eee, SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(1, Integer.MAX_VALUE);

    final SirenBooleanQuery bq3 = new SirenBooleanQuery();
    bq3.add(ddd, SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq3 = new SirenCellQuery(bq3);
    cq3.setConstraint(1, Integer.MAX_VALUE);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(cq1, SirenTupleClause.Occur.MUST);
    tq.add(cq2, SirenTupleClause.Occur.MUST);
    tq.add(cq3, SirenTupleClause.Occur.MUST_NOT);

    assertEquals(1, _helper.search(tq).length);
  }

  @Test
  public void testMultiValuedPredicate2() throws CorruptIndexException, IOException {
    for (int i = 0; i < 100; i++) {
      _helper.addDocument("<http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#publicationTag> \"data data figure obtained\" \"belief tln parameters graphical\" \"incorrect rose proportions feature\" .");
      _helper.addDocument("<http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#publicationTag> \"statistical determining data ylx\" \"presented assumed mit factors\" \"jolla developed positive functions\" .");
      _helper.addDocument("<http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#publicationTag> \"data accuracy minutes applying\" \"focus perceive em parameterization\" \"yield learning separation rule\" .");
    }

    final SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD,
      "http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#publicationtag")),
      SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(0);

    final SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "data")),
      SirenBooleanClause.Occur.MUST);
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "accuracy")),
      SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(1, Integer.MAX_VALUE);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(cq1, SirenTupleClause.Occur.MUST);
    tq.add(cq2, SirenTupleClause.Occur.MUST);

    assertEquals(100, _helper.search(tq).length);
  }

}
