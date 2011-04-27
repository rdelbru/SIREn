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
 * @author Renaud Delbru [ 21 Apr 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.analysis.TupleAnalyzer;

public class TestSirenPhraseQuery {

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

  /**
   * Ensures slop of 0 works for exact matches, but not reversed
   */
  @Test
  public void testExact1() throws Exception {
    _helper.addDocument("\"Renaud Delbru\" . ");
    _helper.addDocument("\"Renaud\" . ");

    SirenPhraseQuery query = new SirenPhraseQuery();
    // slop is zero by default
    query.add(new Term("content", "renaud"));
    query.add(new Term("content", "delbru"));
    ScoreDoc[] hits = _helper.search(query);
    assertEquals("exact match", 1, hits.length);

    query = new SirenPhraseQuery();
    query.add(new Term("field", "delbru"));
    query.add(new Term("field", "renaud"));
    hits = _helper.search(query);
    assertEquals("reverse not exact", 0, hits.length);
  }

  /**
   * Ensures slop of 0 works for exact matches within a longer literal, but not
   * reversed
   */
  @Test
  public void testExact2() throws Exception {
    _helper.addDocument("\"word1 word2 Renaud Delbru word3 \" . ");
    _helper.addDocument("\"Renaud word1 Delbru\" . ");

    SirenPhraseQuery query = new SirenPhraseQuery();
    // slop is zero by default
    query.add(new Term("content", "renaud"));
    query.add(new Term("content", "delbru"));
    ScoreDoc[] hits = _helper.search(query);
    assertEquals("exact match", 1, hits.length);

    query = new SirenPhraseQuery();
    query.add(new Term("field", "delbru"));
    query.add(new Term("field", "renaud"));
    hits = _helper.search(query);
    assertEquals("reverse not exact", 0, hits.length);
  }

  /**
   * Ensures slop of 0 works for exact matches, but not in separate cells or
   * tuples
   */
  @Test
  public void testExact3() throws Exception {
    _helper.addDocument("\"word1 word2\" \"Renaud Delbru word3 \" . ");
    _helper.addDocument("\"Renaud\" \"Delbru\" . ");
    _helper.addDocument("\"Renaud\" . \"Delbru\" . ");

    final SirenPhraseQuery query = new SirenPhraseQuery();
    // slop is zero by default
    query.add(new Term("content", "renaud"));
    query.add(new Term("content", "delbru"));
    final ScoreDoc[] hits = _helper.search(query);
    assertEquals("exact match", 1, hits.length);
  }

  /**
   * Ensures slop of 0 works for exact matches, but not in separate cells or
   * tuples
   * <br>
   * Same test with no norms in order to check [SRN-44].
   */
  @Test
  public void testExact3WithNoNorms() throws Exception {
    _helper.addDocumentNoNorms("\"word1 word2\" \"Renaud Delbru word3 \" . ");
    _helper.addDocumentNoNorms("\"Renaud\" \"Delbru\" . ");
    _helper.addDocumentNoNorms("\"Renaud\" . \"Delbru\" . ");

    final SirenPhraseQuery query = new SirenPhraseQuery();
    // slop is zero by default
    query.add(new Term("content", "renaud"));
    query.add(new Term("content", "delbru"));
    final ScoreDoc[] hits = _helper.search(query);
    assertEquals("exact match", 1, hits.length);
  }

  @Test
  public void testExplain() throws IOException {
    _helper.addDocument("\"Renaud Delbru\" . ");
    _helper.addDocument("\"Renaud Delbru\" . \"Renaud Delbru\" . ");

    final Term t1 = new Term(QueryTestingHelper.DEFAULT_FIELD, "renaud");
    final Term t2 = new Term(QueryTestingHelper.DEFAULT_FIELD, "delbru");
    final SirenPhraseQuery query = new SirenPhraseQuery();
    query.add(t1); query.add(t2);
    final Weight w = query.weight(_helper.getSearcher());
    final IndexReader reader = _helper.getIndexReader();

    // Explain entity 0 : 1 match
    Explanation explanation = w.explain(reader, 0);
    assertNotNull("explanation is null and it shouldn't be", explanation);
    // System.out.println("Explanation: " + explanation.toString());
    //All this Explain does is return the term frequency
    assertEquals("term frq is not 1",
      _helper.getSearcher().getSimilarity().tf(1),
      explanation.getDetails()[1].getDetails()[0].getValue(), 0.01);

    // Explain entity 1 : 2 match
    explanation = w.explain(reader, 1);
    assertNotNull("explanation is null and it shouldn't be", explanation);
    // System.out.println("Explanation: " + explanation.toString());
    //All this Explain does is return the term frequency
    assertEquals("term frq is not 2",
      _helper.getSearcher().getSimilarity().tf(2),
      explanation.getDetails()[1].getDetails()[0].getValue(), 0f);

    // Explain non existing entity
    explanation = w.explain(reader, 2);
    assertNotNull("explanation is null and it shouldn't be", explanation);
    //System.out.println("Explanation: " + explanation.toString());
    //All this Explain does is return the term frequency
    assertEquals("term frq is not 0", 0f, explanation.getValue(), 0f);
  }

}
