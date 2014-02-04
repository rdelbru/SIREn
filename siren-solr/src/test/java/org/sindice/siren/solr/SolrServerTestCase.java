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

import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;

/**
 * Abstract class which instantiates a Solr server and provides helper methods
 * for testing SIREn functionalities.
 * <p>
 * Subclasses must call initCore in a <code>@BeforeClass</code> method.
 * <p>
 * The Solr server is reused across unit tests.
 * <p>
 * By default, the content of the Solr server is cleared after each unit tests.
 */
public abstract class SolrServerTestCase extends SolrTestCaseJ4 {

  protected static final String SOLR_HOME = "./src/test/resources/solr.home/";

  public static final String ID_FIELD = "id";
  public static final String URL_FIELD = "url";
  public static final String JSON_FIELD = "json";

  protected static SolrServerWrapper getWrapper() {
    return new SolrServerWrapper(h.getCoreContainer());
  }

  @Override
  @After
  public void tearDown() throws Exception {
    getWrapper().clear();
    super.tearDown();
  }

  protected void addJsonFile(final String id, final String path)
  throws IOException, SolrServerException {
    this.addJsonFileWoCommit(id, path);
    getWrapper().commit();
  }

  protected void addJsonFileWoCommit(final String id, final String path)
  throws IOException, SolrServerException {
    final FileReader reader = new FileReader(path);
    try {
      final String content = IOUtils.toString(reader);
      final SolrInputDocument document = new SolrInputDocument();
      document.addField(ID_FIELD, id);
      document.addField(JSON_FIELD, content);
      getWrapper().add(document);
    }
    finally {
      reader.close();
    }
  }

  protected void addJsonString(final String id, final String content)
  throws IOException, SolrServerException {
    this.addJsonString(id, JSON_FIELD, content);
  }

  protected void addJsonString(final String id, final String field,
                               final String content)
  throws IOException, SolrServerException {
    this.addJsonStringWoCommit(id, field, content);
    getWrapper().commit();
  }

  protected void addJsonStringWoCommit(final String id, final String content)
  throws IOException, SolrServerException {
    this.addJsonStringWoCommit(id, JSON_FIELD, content);
  }

  protected void addJsonStringWoCommit(final String id, final String field,
                                       final String content)
  throws IOException, SolrServerException {
    final SolrInputDocument document = new SolrInputDocument();
    document.addField(ID_FIELD, id);
    document.addField(field, content);
    getWrapper().add(document);
  }

  protected String[] search(final SolrQuery query, final String retrievedField)
  throws SolrServerException, IOException {
    return getWrapper().search(query, retrievedField);
  }

  protected long search(final SolrQuery query)
  throws SolrServerException, IOException {
    return getWrapper().search(query);
  }

  protected void commit() throws SolrServerException, IOException {
    getWrapper().commit();
  }

}
