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
    this.addNTripleStringWoCommit(url, ntuples);
    wrapper.commit();
  }

  protected void addNTripleStringWoCommit(final String url, final String ntuples)
  throws IOException, SolrServerException {
    final SolrInputDocument document = new SolrInputDocument();
    document.addField("url", url);
    document.addField("ntriple", ntuples);
    wrapper.add(document);
  }

}
