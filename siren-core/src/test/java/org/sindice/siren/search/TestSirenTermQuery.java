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
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.sindice.siren.analysis.DeltaTupleAnalyzer;
import org.sindice.siren.analysis.DeltaTupleAnalyzer.URINormalisation;

public class TestSirenTermQuery {

  private QueryTestingHelper _helper = null;

  @Before
  public void setUp()
  throws Exception {
    final DeltaTupleAnalyzer analyzer = new DeltaTupleAnalyzer(new StandardAnalyzer(Version.LUCENE_31));
    analyzer.setURINormalisation(URINormalisation.FULL);
    _helper = new QueryTestingHelper(analyzer);
  }

  @After
  public void tearDown()
  throws Exception {
    _helper.close();
  }

  /**
   * Ensures simple term queries match all the documents
   */
  @Test
  public void testSimpleMatch() throws Exception {
    _helper.addDocument("\"Renaud Delbru\" . ");
    _helper.addDocument("\"Renaud\" . ");

    SirenTermQuery query = new SirenTermQuery(new Term("content", "renaud"));
    ScoreDoc[] hits = _helper.search(query);
    assertEquals(2, hits.length);

    query = new SirenTermQuery(new Term("content", "delbru"));
    hits = _helper.search(query);
    assertEquals(1, hits.length);
  }

  /**
   * Ensures simple term queries match all the documents
   * <br>
   * Test with no norms [SRN-44]
   */
  @Test
  public void testSimpleMatchWithNoNorms() throws Exception {
    _helper.addDocumentNoNorms("\"Renaud Delbru\" . ");
    _helper.addDocumentNoNorms("\"Renaud\" . ");

    SirenTermQuery query = new SirenTermQuery(new Term("content", "renaud"));
    ScoreDoc[] hits = _helper.search(query);
    assertEquals(2, hits.length);

    query = new SirenTermQuery(new Term("content", "delbru"));
    hits = _helper.search(query);
    assertEquals(1, hits.length);
  }

  /**
   * Ensures simple term queries match all the cells
   */
  @Test
  public void testMatchTuple() throws Exception {
    _helper.addDocument("<http://renaud.delbru.fr/rdf/foaf#me> <http://xmlns.com/foaf/0.1/name> \"Renaud Delbru\" . ");

    final SirenTermQuery query = new SirenTermQuery(new Term("content", "renaud"));
    final ScoreDoc[] hits = _helper.search(query);
    assertEquals(1, hits.length);
    assertEquals(0, hits[0].doc);
    assertEquals(0.13, hits[0].score, 0.01f);
    // System.out.println(query.weight(_helper.getSearcher()).explain(_helper.getIndexReader(), 0));
  }

  /**
   * Ensures simple term queries does not match
   */
  @Test
  public void testSimpleDontMatch() throws Exception {
    _helper.addDocument("\"Renaud Delbru\" . ");

    final SirenTermQuery query = new SirenTermQuery(new Term("content", "nomatch"));
    final ScoreDoc[] hits = _helper.search(query);
    assertEquals(0, hits.length);
  }

  /**
   * Check if explanation is correct
   */
  @Test
  public void testWeight() throws Exception {
    _helper.addDocument("\"Renaud Delbru\" . ");
    _helper.addDocument("\"Renaud\" . ");

    final SirenTermQuery query = new SirenTermQuery(new Term("content", "renaud"));
    final Weight w = query.weight(_helper.getSearcher());
    assertNotNull(w);
    final Explanation explain = w.explain(_helper.getIndexReader(), 0);
    assertNotNull(explain);
    assertTrue(explain.isMatch());
    assertEquals(0.37158427f, explain.getValue(), 0f);
    //System.out.println(explain.toString());
  }

  @Test
  public void testExplain() throws IOException {
    _helper.addDocument("\"Renaud\" . ");

    final Term t = new Term(QueryTestingHelper.DEFAULT_FIELD, "renaud");
    final SirenTermQuery query = new SirenTermQuery(t);
    final Weight w = query.weight(_helper.getSearcher());
    final IndexReader reader = _helper.getIndexReader();

    // Explain entity 0
    Explanation explanation = w.explain(reader, 0);
    assertNotNull("explanation is null and it shouldn't be", explanation);
    System.out.println("Explanation: " + explanation.toString());
    //All this Explain does is return the term frequency
    assertEquals("term frq is not 1", 1f, explanation.getDetails()[0].getValue(), 0f);

    // Explain non existing entity
    explanation = w.explain(reader, 1);
    assertNotNull("explanation is null and it shouldn't be", explanation);
    //System.out.println("Explanation: " + explanation.toString());
    //All this Explain does is return the term frequency
    assertEquals("term frq is not 0", 0f, explanation.getValue(), 0f);
  }

}
