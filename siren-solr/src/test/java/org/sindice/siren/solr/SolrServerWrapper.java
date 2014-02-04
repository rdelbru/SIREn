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

package org.sindice.siren.solr;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.LukeRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.LukeResponse;
import org.apache.solr.client.solrj.response.LukeResponse.FieldInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps a {@link CoreContainer} and provides utility methods over the
 * Solr index.
 */
public class SolrServerWrapper {

  final CoreContainer coreContainer;
  private final SolrServer server;
  private Map<String, FieldInfo> fieldInfos;

  private static final Logger
  logger = LoggerFactory.getLogger(SolrServerWrapper.class);

  public SolrServerWrapper(final CoreContainer coreContainer) {
    this.coreContainer = coreContainer;
    server = new EmbeddedSolrServer(coreContainer, "");
  }

  protected SolrCore getCore() {
    return coreContainer.getCore(coreContainer.getDefaultCoreName());
  }

  /**
   * Add a document.
   */
  public void add(final SolrInputDocument doc)
  throws SolrServerException, IOException {
    final UpdateRequest request = new UpdateRequest();
    request.add(doc);
    request.process(this.getServer());
  }

  /**
   * Return the number of documents currently indexed.
   */
  public int getNumberOfDocuments() throws SolrServerException, IOException {
    final SolrRequest request = new FastLukeRequest();
    final LukeResponse response = (LukeResponse) request.process(this.getServer());
    return response.getNumDocs();
  }

  public String[] search(final SolrQuery query, final String retrievedField)
  throws SolrServerException, IOException {
    final QueryResponse response = this.getServer().query(query);
    final SolrDocumentList docList = response.getResults();

    final int size = docList.size();
    final String docIDs[] = new String[size];
    SolrDocument doc = null;
    for (int i = 0; i < size; i++) {
      doc = docList.get(i);
      docIDs[i] = (String) doc.getFieldValue(retrievedField);
    }
    return docIDs;
  }

  public long search(final SolrQuery query)
  throws SolrServerException, IOException {
    final QueryResponse response = this.getServer().query(query);
    return response.getResults().getNumFound();
  }

  public String[] search(final String q, final String retrievedField, final String qt)
  throws SolrServerException, IOException {
    final SolrQuery query = new SolrQuery();
    query.setRequestHandler(qt);
    query.setQuery(q);
    return this.search(query, retrievedField);
  }

  public int getNumberOfSegments() {
    return this.getCore().getSearcher().get().getIndexReader().leaves().size();
  }

  /**
   * Execute a Luke Request and extract the field names.
   */
  public Set<String> getFieldNames() throws SolrServerException, IOException {
    final Map<String, FieldInfo> fieldInfos = this.getFieldInfos();
    return fieldInfos.keySet();
  }

  private Map<String, FieldInfo> getFieldInfos() throws SolrServerException,
  IOException {
    if (fieldInfos == null) {
      final LukeRequest request = new FastLukeRequest();
      request.setShowSchema(true);
      final LukeResponse response = request.process(this.getServer());
      fieldInfos = response.getFieldInfo();
    }
    return fieldInfos;
  }

  /**
   * Commit all documents that have been submitted
   */
  public void commit() throws SolrServerException, IOException {
    this.getServer().commit();
  }

  /**
   * Execute optimisation of the Solr index
   */
  public void optimize() throws SolrServerException, IOException {
    this.optimize(1);
  }

  /**
   * Execute optimisation of the Solr index
   */
  public void optimize(final int maxSegments) throws SolrServerException, IOException {
    this.getServer().optimize(true, true, maxSegments);
  }

  /**
   * Delete all the documents
   */
  public void clear() throws SolrServerException, IOException {
    this.getServer().deleteByQuery("*:*");
    this.getServer().commit();
  }

  public SolrServer getServer() {
    return server;
  }

  /**
   * Extends LukeRequest in order to disable the fetching of the top K terms
   * for each field.
   */
  private static class FastLukeRequest extends LukeRequest {

    private static final long serialVersionUID = 3210430588671309573L;

    @Override
    public SolrParams getParams() {
      final ModifiableSolrParams params = (ModifiableSolrParams) super.getParams();
      params.add("numTerms", "0");
      return params;
    }

  }

}
