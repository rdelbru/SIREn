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
 *
 * ------------------------------------------------------------
 *
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
