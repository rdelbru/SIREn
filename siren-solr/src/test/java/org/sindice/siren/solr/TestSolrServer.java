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
 * @author Renaud Delbru [ 26 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSolrServer extends BaseSolrServerTestCase {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp()
  throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown()
  throws Exception {}

  @Test
  public void testAddDocument() throws IOException, SolrServerException {
    this.addNTripleFile("http://renaud.delbru.fr/rdf/foaf", "src/test/resources/data/foaf1.nt");
    final String[] results = wrapper.searchNTriple("* <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://xmlns.com/foaf/0.1/PersonalProfileDocument>", "url");
    assertEquals(1, results.length);
    assertEquals("http://renaud.delbru.fr/rdf/foaf", results[0]);
  }

  /**
   * Fail because the value is not an integer, throws a {@link NumberFormatException},
   * wrapped inside a {@link SolrServerException}
   * @throws IOException
   * @throws SolrServerException
   */
  @Test(expected=SolrServerException.class)
  public void testAddInvalidDatatype() throws IOException, SolrServerException {
    this.addNTripleString("id1", "<http://example.org/foo> <http://example.org/bar> \"flargh\"^^<xsd:int> .");
  }

}
