/**
 * Copyright 2009, Renaud Delbru
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
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.filter;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.sindice.siren.analysis.TupleTokenizer;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;

public class TestURINormalisationFilter {

  private final Tokenizer _t = new TupleTokenizer(new StringReader(""),
    Integer.MAX_VALUE, new WhitespaceAnalyzer(Version.LUCENE_31));

  public void assertNormalisesTo(final Tokenizer t, final String input,
                                final String[] expected)
  throws Exception {
    this.assertNormalisesTo(t, input, expected, null);
  }

  public void assertNormalisesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes)
  throws Exception {
    this.assertNormalisesTo(t, input, expectedImages, expectedTypes, null);
  }

  public void assertNormalisesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final int[] expectedPosIncrs)
  throws Exception {
    this.assertNormalisesTo(t, input, expectedImages, expectedTypes, null, null,
      null);
  }

  public void assertNormalisesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final int[] expectedPosIncrs,
                                final int[] expectedTupleID,
                                final int[] expectedCellID)
  throws Exception {

    assertTrue("has TermAttribute", t.hasAttribute(TermAttribute.class));
    final TermAttribute termAtt = t.getAttribute(TermAttribute.class);

    TypeAttribute typeAtt = null;
    if (expectedTypes != null) {
      assertTrue("has TypeAttribute", t.hasAttribute(TypeAttribute.class));
      typeAtt = t.getAttribute(TypeAttribute.class);
    }

    PositionIncrementAttribute posIncrAtt = null;
    if (expectedPosIncrs != null) {
      assertTrue("has PositionIncrementAttribute", t.hasAttribute(PositionIncrementAttribute.class));
      posIncrAtt = t.getAttribute(PositionIncrementAttribute.class);
    }

    TupleAttribute tupleAtt = null;
    if (expectedTupleID != null) {
      assertTrue("has TupleAttribute", t.hasAttribute(TupleAttribute.class));
      tupleAtt = t.getAttribute(TupleAttribute.class);
    }

    CellAttribute cellAtt = null;
    if (expectedCellID != null) {
      assertTrue("has CellAttribute", t.hasAttribute(CellAttribute.class));
      cellAtt = t.getAttribute(CellAttribute.class);
    }

    t.reset(new StringReader(input));
    final TokenStream filter = new URINormalisationFilter(t);

    for (int i = 0; i < expectedImages.length; i++) {

      assertTrue("token "+i+" exists", filter.incrementToken());

      assertEquals(expectedImages[i], termAtt.term());

      if (expectedTypes != null) {
        assertEquals(expectedTypes[i], typeAtt.type());
      }

      if (expectedPosIncrs != null) {
        assertEquals(expectedPosIncrs[i], posIncrAtt.getPositionIncrement());
      }

      if (expectedTupleID != null) {
        assertEquals(expectedTupleID[i], tupleAtt.tuple());
      }

      if (expectedCellID != null) {
        assertEquals(expectedCellID[i], cellAtt.cell());
      }
    }

    assertFalse("end of stream", filter.incrementToken());
    filter.end();
  }

  @Test
  public void testURI()
  throws Exception {
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr/>",
      new String[] { "renaud", "delbru", "http://renaud.delbru.fr/" }, new String[] { "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr>",
      new String[] { "renaud", "delbru", "http://renaud.delbru.fr" }, new String[] { "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://user@renaud.delbru.fr>",
      new String[] { "user", "renaud", "delbru", "http://user@renaud.delbru.fr" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://user:passwd@renaud.delbru.fr>",
      new String[] { "user", "passwd", "renaud", "delbru", "http://user:passwd@renaud.delbru.fr" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr:8080>",
      new String[] { "renaud", "delbru", "8080", "http://renaud.delbru.fr:8080" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr/page.html#fragment>",
      new String[] { "renaud", "delbru", "page", "html", "fragment", "http://renaud.delbru.fr/page.html#fragment" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(
      _t,
      "<http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=N>",
      new String[] { "renaud", "delbru", "page", "html", "query", "query", "start", "http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=N" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<mailto:renaud@delbru.fr>",
      new String[] { "renaud", "delbru", "mailto:renaud@delbru.fr" }, new String[] { "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://xmlns.com/foaf/0.1/workplaceHomepage/>",
      new String[] { "xmlns", "foaf", "workplace", "Homepage", "http://xmlns.com/foaf/0.1/workplaceHomepage/" },
      new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
  }

}
