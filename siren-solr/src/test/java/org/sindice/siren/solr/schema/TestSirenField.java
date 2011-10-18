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
 * @project siren-solr
 * @author Renaud Delbru [ 14 Oct 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.solr.schema;

import org.apache.lucene.document.NumericField.DataType;
import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.analysis.TokenizerChain;
import org.apache.solr.analysis.UAX29URLEmailTokenizerFactory;
import org.apache.solr.analysis.WhitespaceTokenizerFactory;
import org.apache.solr.schema.FieldType;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaField;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sindice.siren.analysis.IntNumericAnalyzer;
import org.sindice.siren.analysis.NumericAnalyzer;
import org.sindice.siren.solr.analysis.DatatypeAnalyzerFilterFactory;
import org.sindice.siren.solr.analysis.TupleTokenizerFactory;

public class TestSirenField extends SolrTestCaseJ4 {

  @BeforeClass
  public static void beforeClass() throws Exception {
    initCore("solrconfig.xml", "schema.xml", "src/test/resources/solr.home");
  }

  @Test
  public void testSirenFieldType() throws Exception {
    final IndexSchema schema = h.getCore().getSchema();
    final SchemaField ntriple = schema.getField("ntriple");
    assertNotNull(ntriple);
    final FieldType tmp = ntriple.getType();
    assertTrue(tmp instanceof SirenField);
  }

  @Test
  public void testSirenFieldTopLevelAnalyzer() throws Exception {
    final IndexSchema schema = h.getCore().getSchema();
    final SchemaField ntriple = schema.getField("ntriple");
    final FieldType tmp = ntriple.getType();

    assertTrue(tmp.getAnalyzer() instanceof TokenizerChain);
    TokenizerChain ts = (TokenizerChain) tmp.getAnalyzer();
    assertNotNull(ts.getTokenizerFactory());
    assertTrue(ts.getTokenizerFactory() instanceof TupleTokenizerFactory);

    // 3 filters for index
    assertNotNull(ts.getTokenFilterFactories());
    assertEquals(3, ts.getTokenFilterFactories().length);

    assertTrue(tmp.getQueryAnalyzer() instanceof TokenizerChain);
    ts = (TokenizerChain) tmp.getQueryAnalyzer();
    assertNotNull(ts.getTokenizerFactory());
    assertTrue(ts.getTokenizerFactory() instanceof WhitespaceTokenizerFactory);

    // 6 filters for keyword-query
    assertNotNull(ts.getTokenFilterFactories());
    assertEquals(6, ts.getTokenFilterFactories().length);
  }

  @Test
  public void testSirenFieldDatatypeAnalyzer() throws Exception {
    final IndexSchema schema = h.getCore().getSchema();
    final SchemaField ntriple = schema.getField("ntriple");
    final FieldType tmp = ntriple.getType();

    TokenizerChain ts = (TokenizerChain) tmp.getAnalyzer();

    assertTrue(ts.getTokenFilterFactories()[1] instanceof DatatypeAnalyzerFilterFactory);
    final DatatypeAnalyzerFilterFactory f = (DatatypeAnalyzerFilterFactory) ts.getTokenFilterFactories()[1];
    assertNotNull(f.getDatatypeAnalyzers());
    // three datatypes are defined
    assertEquals(6, f.getDatatypeAnalyzers().size());

    assertNotNull(f.getDatatypeAnalyzers().get("http://www.w3.org/2001/XMLSchema#anyURI"));
    ts = (TokenizerChain) f.getDatatypeAnalyzers().get("http://www.w3.org/2001/XMLSchema#anyURI");
    assertNotNull(ts.getTokenizerFactory());
    assertTrue(ts.getTokenizerFactory() instanceof WhitespaceTokenizerFactory);

    assertNotNull(f.getDatatypeAnalyzers().get("http://www.w3.org/2001/XMLSchema#string"));
    ts = (TokenizerChain) f.getDatatypeAnalyzers().get("http://www.w3.org/2001/XMLSchema#string");
    assertNotNull(ts.getTokenizerFactory());
    assertTrue(ts.getTokenizerFactory() instanceof UAX29URLEmailTokenizerFactory);

    assertNotNull(f.getDatatypeAnalyzers().get("xsd:int"));
    assertTrue(f.getDatatypeAnalyzers().get("xsd:int") instanceof IntNumericAnalyzer);
    final NumericAnalyzer a = (NumericAnalyzer) f.getDatatypeAnalyzers().get("xsd:int");
    assertEquals(8, a.getPrecisionStep());
    assertEquals(DataType.INT, a.getNumericType());
  }

}
