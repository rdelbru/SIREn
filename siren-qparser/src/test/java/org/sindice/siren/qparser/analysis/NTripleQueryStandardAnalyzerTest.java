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
 * @project solr-plugins
 *
 * Copyright (C) 2007,
 * @author Renaud Delbru [ 27 Apr 2008 ]
 * @link http://renaud.delbru.fr/
 * All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import java_cup.runtime.Symbol;

import org.apache.lucene.analysis.TokenStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.qparser.ntriple.NTripleQueryParser.CupScannerWrapper;

public class NTripleQueryStandardAnalyzerTest {

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

  /**
   * Test method for {@link org.sindice.siren.qparser.analysis.NTripleQueryAnalyzer#NTripleQueryStandardAnalyzer()}.
   * @throws Exception
   */
  @Test
  public void testNTripleQueryStandardAnalyzer1() throws Exception {
    final String query = "<http://ns/#s> <http://ns/p> <http://ns/o>";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));

    final CupScannerWrapper wrapper = new CupScannerWrapper(stream);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == NTripleQueryTokenizer.URIPATTERN);
    assertTrue(symbol.value.toString().equals("http://ns/#s"));
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == NTripleQueryTokenizer.URIPATTERN);
    assertTrue(symbol.value.toString().equals("http://ns/p"));
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == NTripleQueryTokenizer.URIPATTERN);
    assertTrue(symbol.value.toString().equals("http://ns/o"));
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.analysis.NTripleQueryAnalyzer#NTripleQueryStandardAnalyzer()}.
   * @throws Exception
   */
  @Test
  public void testNTripleQueryStandardAnalyzer2() throws Exception {
    final String query = "* <http://ns/p> <http://ns/o>";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream("tets", new StringReader(query));

    final CupScannerWrapper wrapper = new CupScannerWrapper(stream);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == NTripleQueryTokenizer.WILDCARD);
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == NTripleQueryTokenizer.URIPATTERN);
    assertTrue(symbol.value.toString().equals("http://ns/p"));
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == NTripleQueryTokenizer.URIPATTERN);
    assertTrue(symbol.value.toString().equals("http://ns/o"));
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.analysis.NTripleQueryAnalyzer#NTripleQueryStandardAnalyzer()}.
   * @throws Exception
   */
  @Test
  public void testNTripleQueryStandardAnalyzer3() throws Exception {
    final String query = "* <http://ns/p> \"test\"";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream("tets", new StringReader(query));

    final CupScannerWrapper wrapper = new CupScannerWrapper(stream);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == NTripleQueryTokenizer.WILDCARD);
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == NTripleQueryTokenizer.URIPATTERN);
    assertTrue(symbol.value.toString().equals("http://ns/p"));
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.sym == NTripleQueryTokenizer.LITERAL);
    assertTrue(symbol.value.toString().equals("test"));
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }

}
