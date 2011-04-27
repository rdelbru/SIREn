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
package org.sindice.siren.analysis.filter;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.Version;
import org.junit.Assert;
import org.junit.Test;

public class TestASCIIFoldingExpansionFilter {

  @Test
  public void testTokenTypeFilter1() throws Exception {
    final Reader reader = new StringReader("aaa clés café");
    final TokenStream stream = new WhitespaceTokenizer(Version.LUCENE_31, reader);
    final ASCIIFoldingExpansionFilter filter = new ASCIIFoldingExpansionFilter(stream);

    final CharTermAttribute termAtt = filter.getAttribute(CharTermAttribute.class);
    final PositionIncrementAttribute posAtt = filter.getAttribute(PositionIncrementAttribute.class);

    this.assertTermEquals("aaa", 1, filter, termAtt, posAtt);
    this.assertTermEquals("cles", 1, filter, termAtt, posAtt);
    this.assertTermEquals("clés", 0, filter, termAtt, posAtt);
    this.assertTermEquals("cafe", 1, filter, termAtt, posAtt);
    this.assertTermEquals("café", 0, filter, termAtt, posAtt);
  }

  void assertTermEquals(final String termExpected, final int posIncExpected, final TokenStream stream,
                        final CharTermAttribute termAtt, final PositionIncrementAttribute posAtt)
  throws Exception {
    Assert.assertTrue(stream.incrementToken());
    Assert.assertEquals(termExpected, termAtt.toString());
    Assert.assertEquals(posIncExpected, posAtt.getPositionIncrement());
  }



}
