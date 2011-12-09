/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.LukeRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.LukeResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.LukeResponse.FieldInfo;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SolrServerWrapper {

  final String solrHome;
  final CoreContainer coreContainer;
  private final SolrServer server;
  private Map<String, FieldInfo> fieldInfos;

  private static final Logger logger = LoggerFactory.getLogger(SolrServerWrapper.class);

  public SolrServerWrapper(final String solrHome)
  throws IOException, ParserConfigurationException, SAXException {
    this.solrHome = solrHome;
    System.setProperty("solr.solr.home", solrHome);
    final CoreContainer.Initializer initializer = new CoreContainer.Initializer();
    coreContainer = initializer.initialize();
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
    request.process(getServer());
  }

  /**
   * Return the number of documents currently indexed.
   */
  public int getNumberOfDocuments() throws SolrServerException, IOException {
    final SolrRequest request = new FastLukeRequest();
    final LukeResponse response = (LukeResponse) request.process(getServer());
    return response.getNumDocs();
  }

  public String[] search(final SolrQuery query, final String retrievedField)
  throws SolrServerException, IOException {
    final QueryResponse response = getServer().query(query);
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

  public String[] searchNTriple(final String q, final String retrievedField)
  throws SolrServerException, IOException {
    final SolrQuery query = new SolrQuery();
    query.setQueryType("siren");
    query.set(SirenParams.NQ, q);

    final QueryResponse response = getServer().query(query);
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

  public String[] searchTabular(final String q, final String retrievedField)
  throws SolrServerException, IOException {
    final SolrQuery query = new SolrQuery();
    query.setQueryType("siren");
    query.set(SirenParams.TQ, q);

    final QueryResponse response = getServer().query(query);
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
  
  public int getNumberOfSegments() {
    return this.getCore().getSearcher().get().getReader().getSequentialSubReaders().length;
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
      final LukeResponse response = request.process(getServer());
      fieldInfos = response.getFieldInfo();
    }
    return fieldInfos;
  }

  /**
   * Commit all documents that have been submitted
   */
  public void commit() throws SolrServerException, IOException {
    getServer().commit();
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
    getServer().optimize(true, true, maxSegments);
  }

  /**
   * Delete all the documents
   */
  public void clear() throws SolrServerException, IOException {
    getServer().deleteByQuery("*:*");
    getServer().commit();
  }

  public void close() {
    coreContainer.shutdown();
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
