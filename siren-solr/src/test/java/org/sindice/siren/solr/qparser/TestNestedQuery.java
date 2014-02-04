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

package org.sindice.siren.solr.qparser;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.sindice.siren.solr.BaseSolrServerTestCase;

public class TestNestedQuery extends BaseSolrServerTestCase {

  /**
   * When no local parameters are defined, then it should rely on the default
   * solr parser (i.e., lucene).
   */
  @Test
  public void testNoLocalParamater() throws IOException, SolrServerException {
    this.addJsonString("1", "{ \"aaa\" : \"bbb\" }");
    final SolrQuery query = new SolrQuery();
    query.setQuery("aaa : bbb");
    query.setParam("nested", URL_FIELD + ":1");
    query.setRequestHandler("keyword");
    final String[] results = this.search(query, ID_FIELD);
    assertEquals(1, results.length);
  }

  @Test
  public void testSimpleNestedLuceneQuery() throws IOException, SolrServerException {
    this.addJsonString("1", "{ \"aaa\" : \"bbb\" }");
    SolrQuery query = new SolrQuery();
    query.setQuery("aaa : bbb");
    query.setParam("nested", "{!lucene}" + URL_FIELD + ":1");
    query.setRequestHandler("keyword");
    String[] results = this.search(query, ID_FIELD);
    assertEquals(1, results.length);

    query = new SolrQuery();
    query.setQuery("aaa : bbb");
    // non existing document id
    query.setParam("nested", "{!lucene}" + URL_FIELD + ":2");
    query.setRequestHandler("keyword");
    results = this.search(query, ID_FIELD);
    assertEquals(0, results.length);
  }

  @Test
  public void testMultipleNestedQuery() throws IOException, SolrServerException {
    this.addJsonString("1", "{ \"aaa\" : [\"bbb\", \"ccc\"] }");
    SolrQuery query = new SolrQuery();
    query.setQuery("aaa : bbb");
    query.setParam("nested", "{!lucene}" + URL_FIELD + ":1", "{!keyword} aaa : ccc");
    query.setRequestHandler("keyword");
    String[] results = this.search(query, ID_FIELD);
    assertEquals(1, results.length);

    query = new SolrQuery();
    query.setQuery("aaa : bbb");
    query.setParam("nested", "{!lucene}" + URL_FIELD + ":1", "{!keyword} aaa : ddd");
    query.setRequestHandler("keyword");
    results = this.search(query, ID_FIELD);
    assertEquals(0, results.length);
  }

  @Test
  public void testCurlyBracketInNestedQuery() throws IOException, SolrServerException {
    this.addJsonString("1", "{ \"aaa\" : { \"bbb\" : \"ccc\" } }");
    SolrQuery query = new SolrQuery();
    query.setQuery("aaa : bbb");
    query.setParam("nested", "{!keyword} aaa : { bbb : ccc } ");
    query.setRequestHandler("keyword");
    String[] results = this.search(query, ID_FIELD);
    assertEquals(1, results.length);

    query = new SolrQuery();
    query.setQuery("aaa : bbb");
    query.setParam("nested", "{!json} { \"node\" : { \"query\" : \"ccc\" } }");
    query.setRequestHandler("keyword");
    results = this.search(query, ID_FIELD);
    assertEquals(1, results.length);
  }

}
