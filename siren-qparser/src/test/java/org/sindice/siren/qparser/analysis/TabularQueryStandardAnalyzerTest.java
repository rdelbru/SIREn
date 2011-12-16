/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
 * @project solr-plugins
 *
 * Copyright (C) 2007,
 * @author Renaud Delbru [ 27 Apr 2008 ]
 * @link http://renaud.delbru.fr/
 * All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import java_cup.runtime.Symbol;

import org.apache.lucene.analysis.TokenStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.qparser.tabular.TabularQueryParser.CupScannerWrapper;
import org.sindice.siren.util.XSDDatatype;

public class TabularQueryStandardAnalyzerTest {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testTabularQueryStandardAnalyzer1() throws Exception {
    final String query = "[0]<http://ns/#s> [66]<http://ns/p> [4]<http://ns/o>";
    final TabularQueryAnalyzer analyzer = new TabularQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));

    final CupScannerWrapper wrapper = new CupScannerWrapper(stream);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue("recieved symbol: " + symbol.sym, symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[0] " + XSDDatatype.XSD_ANY_URI + ":http://ns/#s", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue("recieved symbol: " + symbol.sym, symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[66] " + XSDDatatype.XSD_ANY_URI + ":http://ns/p", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue("recieved symbol: " + symbol.sym, symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[4] " + XSDDatatype.XSD_ANY_URI + ":http://ns/o", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }

  @Test
  public void testTabularQueryStandardAnalyzer2() throws Exception {
    final String query = "[1]<http://ns/o1> [20]<http://ns/o2> [3]<http://ns/o3> [2]<http://ns/o4>";
    final TabularQueryAnalyzer analyzer = new TabularQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream("tets", new StringReader(query));

    final CupScannerWrapper wrapper = new CupScannerWrapper(stream);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[1] " + XSDDatatype.XSD_ANY_URI + ":http://ns/o1", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[20] " + XSDDatatype.XSD_ANY_URI + ":http://ns/o2", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[3] " + XSDDatatype.XSD_ANY_URI + ":http://ns/o3", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[2] " + XSDDatatype.XSD_ANY_URI + ":http://ns/o4", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }

  @Test
  public void testTabularQueryStandardAnalyzer3() throws Exception {
    final String query = "[1]<http://ns/p> [3]\"test\"";
    final TabularQueryAnalyzer analyzer = new TabularQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream("tets", new StringReader(query));

    final CupScannerWrapper wrapper = new CupScannerWrapper(stream);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[1] " + XSDDatatype.XSD_ANY_URI + ":http://ns/p", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.LITERAL);
    assertEquals("[3] " + XSDDatatype.XSD_STRING + ":test", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }
  
  @Test
  public void testTabularQueryStandardAnalyzer4() throws Exception {
    final String query = "[3]'test' [1]<http://ns/p>";
    final TabularQueryAnalyzer analyzer = new TabularQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream("tets", new StringReader(query));

    final CupScannerWrapper wrapper = new CupScannerWrapper(stream);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.LPATTERN);
    assertEquals("[3] " + XSDDatatype.XSD_STRING + ":test", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[1] " + XSDDatatype.XSD_ANY_URI + ":http://ns/p", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }
  
  @Test
  public void testTabularQueryStandardAnalyzer5() throws Exception {
    final String query = "[1]<http://ns/s> [2]<http://ns/p> *";
    final TabularQueryAnalyzer analyzer = new TabularQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream("tets", new StringReader(query));

    final CupScannerWrapper wrapper = new CupScannerWrapper(stream);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[1] " + XSDDatatype.XSD_ANY_URI + ":http://ns/s", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[2] " + XSDDatatype.XSD_ANY_URI + ":http://ns/p", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue("recieved symbol: " + symbol.sym, symbol.sym == TabularQueryTokenizer.ERROR);
    stream.close();
  }
  
  @Test
  public void testTabularQueryStandardAnalyzer6() throws Exception {
    final String query = "[1]'test' [20]<http://ns/o2> [3]\"tea 4 two\" [2]'you'^^<mydatatype>";
    final TabularQueryAnalyzer analyzer = new TabularQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream("tets", new StringReader(query));

    final CupScannerWrapper wrapper = new CupScannerWrapper(stream);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.LPATTERN);
    assertEquals("[1] " + XSDDatatype.XSD_STRING + ":test", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.URIPATTERN);
    assertEquals("[20] " + XSDDatatype.XSD_ANY_URI + ":http://ns/o2", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.LITERAL);
    assertEquals("[3] " + XSDDatatype.XSD_STRING + ":tea 4 two", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == TabularQueryTokenizer.LPATTERN);
    assertEquals("[2] mydatatype:you", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }

}
