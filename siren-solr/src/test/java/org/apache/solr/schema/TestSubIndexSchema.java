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
 * @project siren
 * @author Renaud Delbru [ 18 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.apache.solr.schema;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.apache.lucene.util.Version;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.schema.FieldType;
import org.apache.solr.schema.SubIndexSchema;
import org.junit.Test;
import org.sindice.siren.solr.BaseSolrServerTestCase;
import org.xml.sax.SAXException;

public class TestSubIndexSchema extends BaseSolrServerTestCase {

  @Test
  public void testFieldTypeLoading()
  throws IOException, SolrServerException, ParserConfigurationException, SAXException {
    final String solrHome = SolrResourceLoader.locateSolrHome();
    final String schemaPath = solrHome + "conf" + File.separator + "ntriple-schema.xml";
    final SubIndexSchema schema = new SubIndexSchema(schemaPath, Version.LUCENE_31);
    FieldType ft = schema.getFieldTypeByName("ntriple-uri");
    Assert.assertNotNull(ft);
    Assert.assertNotNull(ft.getQueryAnalyzer());
    ft = schema.getFieldTypeByName("ntriple-literal");
    Assert.assertNotNull(ft);
    Assert.assertNotNull(ft.getQueryAnalyzer());
    ft = schema.getFieldTypeByName("ntriple-keyword");
    Assert.assertNotNull(ft);
    Assert.assertNotNull(ft.getQueryAnalyzer());
  }

}
