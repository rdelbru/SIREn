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
 * @project siren
 * @author Renaud Delbru [ 5 Feb 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis;


import static org.junit.Assert.*;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;

public class TestWhitespaceTupleAnalyzer {

  private final Analyzer _a = new WhitespaceTupleAnalyzer();

  public void assertAnalyzesTo(final Analyzer a, final String input,
                                final String[] expected)
  throws Exception {
    this.assertAnalyzesTo(a, input, expected, null);
  }

  public void assertAnalyzesTo(final Analyzer a, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes)
  throws Exception {
    this.assertAnalyzesTo(a, input, expectedImages, expectedTypes, null);
  }

  public void assertAnalyzesTo(final Analyzer a, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final int[] expectedPosIncrs)
  throws Exception {
    this.assertAnalyzesTo(a, input, expectedImages, expectedTypes, null, null,
      null);
  }

  public void assertAnalyzesTo(final Analyzer a, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final int[] expectedPosIncrs,
                                final int[] expectedTupleID,
                                final int[] expectedCellID)
  throws Exception {
    final TokenStream t = a.reusableTokenStream("", new StringReader(input));

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

    for (int i = 0; i < expectedImages.length; i++) {

      assertTrue("token "+i+" exists", t.incrementToken());

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

    assertFalse("end of stream", t.incrementToken());
    t.end();
    t.close();
  }

  @Test
  public void testURI()
  throws Exception {
    this.assertAnalyzesTo(_a, "<http://renaud.delbru.fr/>",
      new String[] { "http://renaud.delbru.fr/" },
      new String[] { "<URI>" });
    this.assertAnalyzesTo(_a, "<http://Renaud.Delbru.fr/>",
      new String[] { "http://renaud.delbru.fr/" },
      new String[] { "<URI>" });
    this.assertAnalyzesTo(
      _a,
      "<http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=N>",
      new String[] { "http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=n" },
      new String[] { "<URI>" });
    this.assertAnalyzesTo(_a, "<mailto:renaud@delbru.fr>",
      new String[] { "mailto:renaud@delbru.fr" },
      new String[] { "<URI>" });
    this.assertAnalyzesTo(_a, "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
      new String[] {"http://www.w3.org/1999/02/22-rdf-syntax-ns#type"},
      new String[] { "<URI>" });
  }

  @Test
  public void testLiteral()
  throws Exception {
    this.assertAnalyzesTo(_a, "\"foo bar FOO BAR\"", new String[] { "foo",
        "bar", "foo", "bar" }, new String[] { "word", "word",
        "word", "word" });
    this.assertAnalyzesTo(_a, "\"ABC\\u0061\\u0062\\u0063\\u00E9\\u00e9ABC\"",
      new String[] { "abcabcééabc" }, new String[] { "word" });
  }

  @Test
  public void testLanguage()
  throws Exception {
    this.assertAnalyzesTo(_a, "\"test\"@en", new String[] { "test" },
      new String[] { "word" });
  }

  @Test
  public void testDatatype()
  throws Exception {
    this.assertAnalyzesTo(_a, "<http://test/>^^<http://type/test>",
      new String[] { "http://test/" },
      new String[] { "<URI>" });
  }

  @Test
  public void testBNodeFiltering()
  throws Exception {
    this.assertAnalyzesTo(_a, "_:b123 <aaa> <bbb> _:b212",
      new String[] { "aaa", "bbb" },
      new String[] { "<URI>", "<URI>" });
  }

}
