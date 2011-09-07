/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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
