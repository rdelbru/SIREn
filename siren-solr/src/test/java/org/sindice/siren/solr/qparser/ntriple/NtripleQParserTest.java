/**
 * Copyright 2010, Campinas Stephane
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
 * @project siren-solr
 * @author Campinas Stephane [ 29 Oct 2010 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.solr.qparser.ntriple;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.sindice.siren.solr.SolrServerWrapper;
import org.xml.sax.SAXException;

public class NtripleQParserTest extends TestCase {

  private SolrServerWrapper     wrapper;
  private final String          solrHome      = "./src/test/resources/solr.home/";

  @Override
  public void setUp()
  throws ParserConfigurationException, SAXException, IOException {
    wrapper = new SolrServerWrapper(solrHome);
  }

  @Override
  protected void tearDown()
  throws Exception {
    super.tearDown();
    wrapper.close();
  }

  /**
   * QNames mapping
   */
  @Test
  public void testQNames()
  throws SolrServerException, IOException {
    final SolrInputDocument doc = new SolrInputDocument();

    doc.addField("url", "http://fakefoaf");
    doc.addField("ntriple", "<http://stephane-campinas.com> <http://xmlns.com/foaf/0.1/name> \"campinas\"");
    wrapper.add(doc);
    wrapper.commit();

    final String[] results = wrapper.searchNTriple(" * <foaf:name> \"campinas\"", "url");
    assertEquals(1, results.length);
    assertEquals("http://fakefoaf", results[0]);
  }

}
