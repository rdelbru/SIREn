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
 * @author Renaud Delbru [ 12 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import java_cup.runtime.Symbol;
import junit.framework.Assert;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.sindice.siren.qparser.analysis.filter.QNamesFilter;
import org.sindice.siren.qparser.ntriple.NTripleQueryParser.CupScannerWrapper;

public class QNamesFilterTest {

  @Test
  public void testQName() throws Exception {
    final String query = "foaf:name";
    final WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_31);
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final TokenFilter filter = new QNamesFilter(stream, "./src/test/resources/conf/qnames");

    final CharTermAttribute cTermAtt = filter.getAttribute(CharTermAttribute.class);
    Assert.assertTrue(filter.incrementToken());
    Assert.assertEquals("http://xmlns.com/foaf/0.1/name", cTermAtt.toString());
    filter.close();
  }
  
  @Test
  public void testNotQName() throws Exception {
    final String query = "mailto:aidan.hogan@deri.org";
    final WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_31);
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final TokenFilter filter = new QNamesFilter(stream, "./src/test/resources/conf/qnames");

    final CharTermAttribute cTermAtt = filter.getAttribute(CharTermAttribute.class);
    Assert.assertTrue(filter.incrementToken());
    Assert.assertEquals("mailto:aidan.hogan@deri.org", cTermAtt.toString());
    filter.close();
  }

  @Test
  public void testInvalidQName() throws Exception {
    final String query = "<http:> <foaf:2> <foaf:-qw>";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final TokenFilter filter = new QNamesFilter(stream, "./src/test/resources/conf/qnames");

    final CupScannerWrapper wrapper = new CupScannerWrapper(filter);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.value.toString().equals("http:"));
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.value.toString().equals("foaf:2"));
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.value.toString().equals("foaf:-qw"));
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }

  @Test
  public void testQNameHTTP() throws Exception {
    final String query = "<http://ns/#s> <http://ns/p> <http://ns/o>";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final TokenFilter filter = new QNamesFilter(stream, "./src/test/resources/conf/qnames");

    final CupScannerWrapper wrapper = new CupScannerWrapper(filter);
    Symbol symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.value.toString().equals("http://ns/#s"));
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.value.toString().equals("http://ns/p"));
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertTrue(symbol.value.toString().equals("http://ns/o"));
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }

}
