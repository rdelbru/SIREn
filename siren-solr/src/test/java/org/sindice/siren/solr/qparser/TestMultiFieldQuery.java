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
import org.apache.solr.common.SolrInputDocument;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sindice.siren.solr.SolrServerTestCase;

public class TestMultiFieldQuery extends SolrServerTestCase {

  @BeforeClass
  public static void beforeClass() throws Exception {
    initCore("solrconfig.xml", "schema-multifield.xml", SOLR_HOME);
  }

  @Test
  public void testMultiFieldQuery() throws SolrServerException, IOException {
    SolrInputDocument document = new SolrInputDocument();
    document.addField(ID_FIELD, "1");
    document.addField(JSON_FIELD + 1, "{ \"aaa\" : \"bbb\" }");
    document.addField(JSON_FIELD + 2, "{ \"aaa\" : \"ccc\" }");
    getWrapper().add(document);

    document = new SolrInputDocument();
    document.addField(ID_FIELD, "2");
    document.addField(JSON_FIELD + 1, "{ \"aaa\" : \"ccc\" }");
    document.addField(JSON_FIELD + 2, "{ \"aaa\" : \"bbb\" }");
    getWrapper().add(document);

    getWrapper().commit();

    final SolrQuery query = new SolrQuery();
    query.setQuery(" aaa : ccc ");
    query.setRequestHandler("keyword");
    query.set(SirenParams.QF, JSON_FIELD + 1, JSON_FIELD + 2);
    final String[] results = getWrapper().search(query, ID_FIELD);
    assertEquals(2, results.length);
  }

}
