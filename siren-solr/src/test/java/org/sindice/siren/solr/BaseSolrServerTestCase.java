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
 * @author Renaud Delbru [ 31 Oct 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.sindice.siren.util.IOUtils;

public abstract class BaseSolrServerTestCase {

  private static final String SOLR_HOME = "./src/test/resources/solr.home/";
  protected static SolrServerWrapper wrapper;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass()
  throws Exception {
    wrapper = new SolrServerWrapper(SOLR_HOME);
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass()
  throws Exception {
    wrapper.close();
  }

  protected void addNTripleFile(final String url, final String path)
  throws IOException, SolrServerException {
    this.addNTripleFileWoCommit(url, path);
    wrapper.commit();
  }

  protected void addNTripleFileWoCommit(final String url, final String path)
  throws IOException, SolrServerException {
    final FileReader reader = new FileReader(path);
    final List<String> lines = org.apache.commons.io.IOUtils.readLines(reader);
    final String[] ntriples = lines.toArray(new String[lines.size()]);
    final String ntuples = IOUtils.flattenNTriples(ntriples);
    final SolrInputDocument document = new SolrInputDocument();
    document.addField("url", url);
    document.addField("ntriple", ntuples);
    wrapper.add(document);
  }

  protected void addNTripleString(final String url, final String ntuples)
  throws IOException, SolrServerException {
    final SolrInputDocument document = new SolrInputDocument();
    document.addField("url", url);
    document.addField("ntriple", ntuples);
    wrapper.add(document);
    wrapper.commit();
    wrapper.optimize();
  }

}
