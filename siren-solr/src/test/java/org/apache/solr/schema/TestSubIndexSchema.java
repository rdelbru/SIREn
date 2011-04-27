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
