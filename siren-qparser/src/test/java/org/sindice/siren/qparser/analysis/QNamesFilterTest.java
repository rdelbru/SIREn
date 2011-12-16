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
 * @project siren
 * @author Renaud Delbru [ 12 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import java_cup.runtime.Symbol;
import junit.framework.Assert;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.LuceneTestCase;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.sindice.siren.qparser.analysis.filter.QNamesFilter;
import org.sindice.siren.qparser.ntriple.NTripleQueryParser.CupScannerWrapper;
import org.sindice.siren.util.XSDDatatype;


public class QNamesFilterTest {

  private final Version matchVersion = LuceneTestCase.TEST_VERSION_CURRENT;
  
  @Test
  public void testQName() throws Exception {
    final String query = "foaf:name";
    final WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer(matchVersion);
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
    final WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer(matchVersion);
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
    assertEquals(XSDDatatype.XSD_ANY_URI + ":http:", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertEquals(XSDDatatype.XSD_ANY_URI + ":foaf:2", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertEquals(XSDDatatype.XSD_ANY_URI + ":foaf:-qw", symbol.value.toString());
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
    assertEquals(XSDDatatype.XSD_ANY_URI + ":http://ns/#s", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertEquals(XSDDatatype.XSD_ANY_URI + ":http://ns/p", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol != null);
    assertEquals(XSDDatatype.XSD_ANY_URI + ":http://ns/o", symbol.value.toString());
    symbol = wrapper.next_token();
    assertTrue(symbol == null);
    stream.close();
  }

}
