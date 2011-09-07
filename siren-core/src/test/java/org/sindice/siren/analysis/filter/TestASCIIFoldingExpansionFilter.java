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
