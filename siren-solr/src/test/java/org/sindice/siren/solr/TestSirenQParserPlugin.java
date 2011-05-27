/**
 * Copyright 2010, Renaud Delbru
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
 * @author Renaud Delbru [ 26 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSirenQParserPlugin extends BaseSolrServerTestCase  {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp()
  throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown()
  throws Exception {
    wrapper.clear();
  }

  @Test
  public void testParams() throws IOException, SolrServerException {
    final SolrQuery query = new SolrQuery();
    query.setQuery("test");
    query.set(SirenParams.KQF, "ntriple^1.0");
    query.set(SirenParams.NQ, "* <name> \"renaud\"");
    query.set(SirenParams.NQF, "ntriple^1.0");
    query.set(SirenParams.NQFO, "scattered");
    query.setQueryType("siren");
    wrapper.search(query, "url");
  }

  @Test
  public void testEmptyKeywordQueryParam() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> \"test\" .");
    final SolrQuery query = new SolrQuery();
    query.set(SirenParams.NQF, "ntriple");
    query.set(SirenParams.NQ, "* <http://p> \"test\"");
    query.setQueryType("unittest-siren");

    final String[] results = wrapper.search(query, "url");
    assertEquals(1, results.length);
  }

  @Test
  public void testEmptyNtripleQueryParam() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> \"test\" .");
    final SolrQuery query = new SolrQuery();
    query.setQuery("test");
    query.setQueryType("unittest-siren");

    final String[] results = wrapper.search(query, "url");
    assertEquals(1, results.length);
  }

  /**
   * SRN-90: Test keyword query with default operator AND
   */
  @Test
  public void testKeywordQueryWithDefaultOperator()
  throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> \"test\" .");
    this.addNTripleString("id2", "<http://s> <http://p> \"test\" .");
    final SolrQuery query = new SolrQuery();
    query.setQuery("id1 test");
    query.set(SirenParams.KQF, "ntriple^1.0 url^1.0");
    query.setQueryType("siren");

    final String[] results = wrapper.search(query, "url");
    // Default Operator = AND : Only one document should match
    assertEquals(1, results.length);
  }

  /**
   * SRN-97
   */
  @Test
  public void testDefaultOperatorLiteralPattern()
  throws SolrServerException, IOException {
    this.addNTripleString("id1", "<http://test.com/1> <http://test.com/p> \"apple orange\"");
    this.addNTripleString("id2", "<http://test.com/1> <http://test.com/p> \"apple banana\"");

    final SolrQuery query = new SolrQuery();
    query.set(SirenParams.NQ, "* * 'apple orange'");
    query.setQueryType("siren");

    final String[] results = wrapper.search(query, "url");
    // Default Operator = AND : Only one document should match
    assertEquals(1, results.length);
  }

  /**
   * SRN-97
   */
  @Test
  public void testDefaultOperatorURIPattern()
  throws SolrServerException, IOException {
    this.addNTripleString("id1", "<http://test.com/1> <http://test.com/p> <http://test.com/AppleOrange>");
    this.addNTripleString("id2", "<http://test.com/1> <http://test.com/p> <http://test.com/AppleBanana>");

    final SolrQuery query = new SolrQuery();
    query.set(SirenParams.NQ, "* * <apple orange>");
    query.setQueryType("siren");

    final String[] results = wrapper.search(query, "url");
    // Default Operator = AND : Only one document should match
    assertEquals(1, results.length);
  }

  @Test
  public void testEmptyNtripleFieldParam() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://test.org/name> \"renaud\" \"test\".");
    this.addNTripleString("id2", "<http://s> <http://p> \"test\" .");
    final SolrQuery query = new SolrQuery();
    query.setQuery("test");
    query.set(SirenParams.NQ, "* <name> \"renaud\"");
    query.setQueryType("unittest-siren");

    final String[] results = wrapper.search(query, "url");
    assertEquals(1, results.length);
  }

  @Test
  public void testEnableFieldQueryInKeywordParser() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> \"test\" .");
    final SolrQuery query = new SolrQuery();
    query.setQuery("url:id1");
    query.setQueryType("unittest-siren");

    final String[] results = wrapper.search(query, "url");
    assertEquals(1, results.length);
  }

  @Test
  public void testDisableFieldQueryInKeywordParser() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> \"test\" .");
    final SolrQuery query = new SolrQuery();
    query.setQuery("url:id1");
    query.setQueryType("siren");

    final String[] results = wrapper.search(query, "url");
    assertEquals(0, results.length);
  }

  @Test
  public void testDisableFieldQueryExpanded() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> <http://xmlns.com/foaf/0.1/Person> .");
    this.addNTripleString("id2", "<http://s> <http://p> <http://xmlns.com/foaf/0.1/Agent> .");
    final SolrQuery query = new SolrQuery();
    query.setQuery("foaf:Person");
    query.setQueryType("siren");

    final String[] results = wrapper.search(query, "url");
    assertEquals(1, results.length);
  }

  /**
   * SRN-96
   */
  @Test
  public void testASCIIFoldingExpansion() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> \"cafe\" .");
    this.addNTripleString("id2", "<http://s> <http://p> \"café\" .");
    SolrQuery query = new SolrQuery();
    query.setQuery("cafe");
    query.setQueryType("siren");
    query.setIncludeScore(true);

    // should match the two documents, with same score
    QueryResponse response = wrapper.server.query(query);
    SolrDocumentList docList = response.getResults();
    assertEquals(2, docList.getNumFound());
    float score1 = (Float) docList.get(0).getFieldValue("score");
    float score2 = (Float) docList.get(1).getFieldValue("score");
    Assert.assertTrue("Score should be identical", score1 == score2);

    query = new SolrQuery();
    query.setQueryType("siren");
    query.set(SirenParams.NQ, "* * 'cafe'");
    query.setIncludeScore(true);

    // should match the two documents, with same score
    response = wrapper.server.query(query);
    docList = response.getResults();
    assertEquals(2, docList.getNumFound());
    score1 = (Float) docList.get(0).getFieldValue("score");
    score2 = (Float) docList.get(1).getFieldValue("score");
    Assert.assertTrue("Score should be identical", score1 == score2);

    // should match the two documents, but should assign different score
    // id2 should receive better score than id1
    query = new SolrQuery();
    query.setQuery("café");
    query.setQueryType("siren");
    query.setIncludeScore(true);

    response = wrapper.server.query(query);
    docList = response.getResults();
    assertEquals(2, docList.getNumFound());
    if (docList.get(0).getFieldValue("url").equals("id1")) {
      score1 = (Float) docList.get(0).getFieldValue("score");
      score2 = (Float) docList.get(1).getFieldValue("score");
    }
    else {
      score2 = (Float) docList.get(0).getFieldValue("score");
      score1 = (Float) docList.get(1).getFieldValue("score");
    }
    Assert.assertTrue("id2 should get higher score than id1", score1 < score2);

    query = new SolrQuery();
    query.setQueryType("siren");
    query.set(SirenParams.NQ, "* * 'café'");
    query.setIncludeScore(true);

    response = wrapper.server.query(query);
    System.out.println(response);
    docList = response.getResults();
    assertEquals(2, docList.getNumFound());
    if (docList.get(0).getFieldValue("url").equals("id1")) {
      score1 = (Float) docList.get(0).getFieldValue("score");
      score2 = (Float) docList.get(1).getFieldValue("score");
    }
    else {
      score2 = (Float) docList.get(0).getFieldValue("score");
      score1 = (Float) docList.get(1).getFieldValue("score");
    }
    Assert.assertTrue("id2 should get higher score than id1", score1 < score2);
  }

  @Test
  public void testMailto() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> <mailto:test@test.org> .");
    SolrQuery query = new SolrQuery();
    query.setQuery("mailto:test@test.org");
    query.setQueryType("siren");

    String[] results = wrapper.search(query, "url");
    assertEquals(1, results.length);

    query = new SolrQuery();
    query.setQuery("test@test.org");
    query.setQueryType("siren");

    results = wrapper.search(query, "url");
    assertEquals(1, results.length);
  }

  @Test
  public void testTildeInURI() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> <http://sw.deri.org/~aidanh/> .");
    SolrQuery query = new SolrQuery();
    query.setQuery("http://sw.deri.org/~aidanh/");
    query.setQueryType("siren");

    String[] results = wrapper.search(query, "url");
    assertEquals(1, results.length);

    query = new SolrQuery();
    query.setQuery("http://sw.deri.org/~aidanh");
    query.setQueryType("siren");

    results = wrapper.search(query, "url");
    assertEquals(1, results.length);
  }

  @Test
  public void testEncodedURI() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://s> <http://p> <http://dblp.l3s.de/d2r/resource/authors/Knud_M%C3%B6ller> .");
    SolrQuery query = new SolrQuery();
    query.setQuery("http://dblp.l3s.de/d2r/resource/authors/Knud_M%C3%B6ller");
    query.setQueryType("siren");

    String[] results = wrapper.search(query, "url");
    assertEquals(1, results.length);

    query = new SolrQuery();
    query.setQuery("Möller");
    query.setQueryType("siren");

    results = wrapper.search(query, "url");
    assertEquals(1, results.length);
  }

// TODO: try to solve this issue
//  @Test
//  public void testMatchingPartURL() throws Exception {
//    this.addNTripleString("http://data-gov.tw.rpi.edu/raw/", "<http://s> <http://p> <http://o> .");
//
//    final SolrQuery query = new SolrQuery();
//    query.setParam("qt", "siren");
//    query.setQuery("rpi.edu");
//    final String[] results = wrapper.search(query, "url");
//    assertEquals(1, results.length);
//  }

}
