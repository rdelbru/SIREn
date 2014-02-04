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
package org.sindice.siren.solr.analysis;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class TestASCIIFoldingExpansionFilterFactory extends BaseSirenStreamTestCase {

  /**
   * SRN-96
   */
  @Test
  public void testASCIIFoldingExpansion() throws IOException, SolrServerException {
    this.addJsonString("1", " { \"value\" : \"cafe\" } ");
    this.addJsonString("2", " { \"value\" : \"café\" } ");
    SolrQuery query = new SolrQuery();
    query.setQuery("cafe");
    query.setRequestHandler("keyword");
    query.setIncludeScore(true);

    // should match the two documents, with same score
    QueryResponse response = getWrapper().getServer().query(query);
    SolrDocumentList docList = response.getResults();
    assertEquals(2, docList.getNumFound());
    float score1 = (Float) docList.get(0).getFieldValue("score");
    float score2 = (Float) docList.get(1).getFieldValue("score");
    Assert.assertTrue("Score should be identical", score1 == score2);

    // should match the two documents, but should assign different score
    // id2 should receive better score than id1
    query = new SolrQuery();
    query.setQuery("café");
    query.setRequestHandler("keyword");
    query.setIncludeScore(true);

    response = getWrapper().getServer().query(query);
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

}
