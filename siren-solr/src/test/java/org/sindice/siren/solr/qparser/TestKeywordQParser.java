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
import org.apache.solr.common.SolrException;
import org.junit.Test;
import org.sindice.siren.solr.BaseSolrServerTestCase;

public class TestKeywordQParser extends BaseSolrServerTestCase {

  @Test(expected=SolrException.class)
  public void testBadKeywordQuery()
  throws SolrServerException, IOException {
    final SolrQuery query = new SolrQuery();
    query.setQuery(" aaa : [ * : ccc } ");
    query.setRequestHandler("keyword");
    this.search(query, ID_FIELD);
  }

  @Test
  public void testSimpleKeywordQuery()
  throws SolrServerException, IOException {
    this.addJsonString("1", "{ \"aaa\" :  { \"bbb\" : \"ccc\" } }");
    this.addJsonString("2", "{ \"aaa\" :  \"ddd eee\" }");

    SolrQuery query = new SolrQuery();
    query.setQuery(" aaa : { * : ccc } ");
    query.setRequestHandler("keyword");
    String[] results = this.search(query, ID_FIELD);
    assertEquals(1, results.length);

    query = new SolrQuery();
    query.setQuery(" aaa : ddd ");
    query.setRequestHandler("keyword");
    results = this.search(query, ID_FIELD);
    assertEquals(1, results.length);
  }

  @Test
  public void testQNamesMapping()
  throws SolrServerException, IOException {
    this.addJsonString("1", "{ \"uri\" : { " +
        "\"_value_\" : \"http://xmlns.com/foaf/0.1/Person\", " +
        "\"_datatype_\" : \"uri\" " +
        "} }");

    final SolrQuery query = new SolrQuery();
    query.setQuery(" uri : 'foaf:Person' ");
    query.setRequestHandler("keyword");
    final String[] results = this.search(query, ID_FIELD);
    assertEquals(1, results.length);
  }

}
