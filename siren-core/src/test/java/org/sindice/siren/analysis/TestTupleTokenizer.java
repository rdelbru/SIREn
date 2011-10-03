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
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.DatatypeAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;
import org.sindice.siren.util.XSDDatatype;

public class TestTupleTokenizer {

  @Test
  public void testURI()
  throws Exception {
    this.assertTokenizesTo(_t, "<http://renaud.delbru.fr/>",
      new String[] { "http://renaud.delbru.fr/" }, new String[] { "<URI>" });
    this.assertTokenizesTo(_t, "<http://renaud.delbru.fr>",
      new String[] { "http://renaud.delbru.fr" }, new String[] { "<URI>" });
    this.assertTokenizesTo(_t, "<http://user@renaud.delbru.fr>",
      new String[] { "http://user@renaud.delbru.fr" }, new String[] { "<URI>" });
    this.assertTokenizesTo(_t, "<http://user:passwd@renaud.delbru.fr>",
      new String[] { "http://user:passwd@renaud.delbru.fr" },
      new String[] { "<URI>" });
    this.assertTokenizesTo(_t, "<http://user:passwd@renaud.delbru.fr:8080>",
      new String[] { "http://user:passwd@renaud.delbru.fr:8080" },
      new String[] { "<URI>" });
    this.assertTokenizesTo(_t, "<http://renaud.delbru.fr:8080>",
      new String[] { "http://renaud.delbru.fr:8080" }, new String[] { "<URI>" });
    this.assertTokenizesTo(_t, "<http://renaud.delbru.fr/subdir/page.html>",
      new String[] { "http://renaud.delbru.fr/subdir/page.html" },
      new String[] { "<URI>" });
    this.assertTokenizesTo(_t, "<http://renaud.delbru.fr/page.html#fragment>",
      new String[] { "http://renaud.delbru.fr/page.html#fragment" },
      new String[] { "<URI>" });
    this.assertTokenizesTo(
      _t,
      "<http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=N>",
      new String[] { "http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=N" },
      new String[] { "<URI>" });
    this.assertTokenizesTo(_t, "<ftp://renaud.delbru.fr/>",
      new String[] { "ftp://renaud.delbru.fr/" }, new String[] { "<URI>" });
    this.assertTokenizesTo(_t, "<mailto:renaud@delbru.fr>",
      new String[] { "mailto:renaud@delbru.fr" }, new String[] { "<URI>" });
  }

  @Test
  public void testBNode()
  throws Exception {
    this.assertTokenizesTo(_t, "_:x74562", new String[] { "x74562" },
      new String[] { "<BNODE>" });
    this.assertTokenizesTo(_t, "_:node1", new String[] { "node1" },
      new String[] { "<BNODE>" });
    this.assertTokenizesTo(_t, "_:httpsaojfsd", new String[] { "httpsaojfsd" },
      new String[] { "<BNODE>" });
    this.assertTokenizesTo(_t, "_:asd", new String[] { "asd" },
      new String[] { "<BNODE>" });
  }

  @Test
  public void testLiteral()
  throws Exception {
    this.assertTokenizesTo(_t, "\"Renaud\"", new String[] { "Renaud" },
      new String[] { "<LITERAL>" });
    this.assertTokenizesTo(_t, "\"1 and 2\"", new String[] { "1 and 2" },
      new String[] { "<LITERAL>" });
    this.assertTokenizesTo(_t, "\"renaud http://test/ \"", new String[] {
        "renaud http://test/ " }, new String[] { "<LITERAL>" });
    this.assertTokenizesTo(_t, "\"foo bar FOO BAR\"",
      new String[] { "foo bar FOO BAR" }, new String[] { "<LITERAL>" });
    this.assertTokenizesTo(_t, "\"ABC\\u0061\\u0062\\u0063\\u00E9\\u00e9ABC\"",
      new String[] { "ABCabcééABC" }, new String[] { "<LITERAL>" });
  }

// TODO: Convert test for TupleTokenAnalzerFilter
//  @Test
//  public void testLiteral()
//  throws Exception {
//    this.assertTokenizesTo(_t, "\"Renaud\"", new String[] { "renaud" },
//      new String[] { "<ALPHANUM>" });
//    this.assertTokenizesTo(_t, "\"1 and 2\"", new String[] { "1", "2" },
//      new String[] { "<NUM>", "<NUM>" });
//    this.assertTokenizesTo(_t, "\"renaud http://test/ \"", new String[] {
//        "renaud", "http", "test" }, new String[] { "<ALPHANUM>", "<ALPHANUM>", "<ALPHANUM>" });
//    this.assertTokenizesTo(_t, "\"foo bar FOO BAR\"", new String[] { "foo",
//        "bar", "foo", "bar" }, new String[] { "<ALPHANUM>", "<ALPHANUM>",
//        "<ALPHANUM>", "<ALPHANUM>" });
//    this.assertTokenizesTo(_t, "\"ABC\\u0061\\u0062\\u0063\\u00E9\\u00e9ABC\"",
//      new String[] { "abcabcééabc" }, new String[] { "<ALPHANUM>" });
//  }

  @Test
  public void testDot()
  throws Exception {
    this.assertTokenizesTo(_t, "<http://te.st> . \"ren . aud\" . ",
      new String[] { "http://te.st", ".", "ren . aud", "." }, new String[] {
          "<URI>", "<DOT>", "<LITERAL>", "<DOT>" });
    this.assertTokenizesTo(_t, "<aaa> \"bbb\". <bbb> <aaa>. <ccc> .",
      new String[] { "aaa", "bbb", ".", "bbb", "aaa", ".", "ccc", "." }, new String[] {
          "<URI>", "<LITERAL>", "<DOT>", "<URI>", "<URI>", "<DOT>", "<URI>", "<DOT>" });
  }

  // TODO: Check if language tag is correctly assigned when a
  // LanguageTagAttribute will be created
  @Test
  public void testLanguage()
  throws Exception {
    this.assertTokenizesTo(_t, "\"test\"@en", new String[] { "test" },
      new String[] { "<LITERAL>" });
    this.assertTokenizesTo(_t, "\"toto@titi.fr \"@fr", new String[] {
        "toto@titi.fr " },
        new String[] { "<LITERAL>", "<LITERAL>" });
  }

  @Test
  public void testDatatype()
  throws Exception {
    this.assertTokenizesTo(_t, "<http://test>",
      new String[] { "http://test" }, new String[] { "<URI>" },
      new String[] { XSDDatatype.XSD_ANY_URI } );
    this.assertTokenizesTo(_t, "\"test\"",
      new String[] { "test" }, new String[] { "<LITERAL>" },
      new String[] { XSDDatatype.XSD_STRING } );
    this.assertTokenizesTo(_t, "_:bnode1",
      new String[] { "bnode1" }, new String[] { "<BNODE>" },
      new String[] { "" } );
    this.assertTokenizesTo(_t, "\"test\"^^<http://type/test>",
      new String[] { "test" }, new String[] { "<LITERAL>" },
      new String[] { "http://type/test" } );
    this.assertTokenizesTo(_t, "\"te^^st\"^^<"+XSDDatatype.XSD_NAME+">",
      new String[] { "te^^st" }, new String[] { "<LITERAL>" },
      new String[] { XSDDatatype.XSD_NAME } );
  }

  @Test
  public void testStructuralNode()
  throws Exception {
    this.assertTokenizesTo(_t, "<http://renaud.delbru.fr/>",
      new String[] { "http://renaud.delbru.fr/" }, new String[] { "<URI>" },
      new int[] { 1 }, new int[] { 0 }, new int[] { 0 });
    this.assertTokenizesTo(_t,
      "<http://renaud.delbru.fr/> <http://renaud.delbru.fr/>",
      new String[] { "http://renaud.delbru.fr/", "http://renaud.delbru.fr/" },
      new String[] { "<URI>", "<URI>" }, new int[] { 1, 1 },
      new int[] { 0, 0 }, new int[] { 0, 1 });
    this.assertTokenizesTo(_t, "_:a1 _:a2 . _:a3 _:a4 . _:a5 _:a6 ",
      new String[] { "a1", "a2", ".", "a3", "a4", ".", "a5", "a6" },
      new String[] { "<BNODE>", "<BNODE>", "<DOT>", "<BNODE>", "<BNODE>",
          "<DOT>", "<BNODE>", "<BNODE>" },
      new int[] { 1, 1, 1, 1, 1, 1, 1, 1 },
      new int[] { 0, 0, 0, 1, 1, 1, 2, 2 },
      new int[] { 0, 1, 2, 0, 1, 2, 0, 1 });
    this.assertTokenizesTo(_t, "<http://te.st> . \"ren . aud\" . ",
      new String[] { "http://te.st", ".", "ren . aud", "." }, new String[] {
          "<URI>", "<DOT>", "<LITERAL>", "<DOT>" }, new int[] {
          1, 1, 1, 1 }, new int[] { 0, 0, 1, 1 }, new int[] { 0,
          1, 0, 1 });
  }

  private final Tokenizer _t = new TupleTokenizer(new StringReader(""),
                               Integer.MAX_VALUE);

  public void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes)
  throws Exception {
    this.assertTokenizesTo(t, input, expectedImages, expectedTypes, null, null, null, null);
  }

  public void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final String[] expectedDatatypes)
  throws Exception {
    this.assertTokenizesTo(t, input, expectedImages, expectedTypes, expectedDatatypes, null, null, null);
  }

  public void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final int[] expectedPosIncrs,
                                final int[] expectedTupleID,
                                final int[] expectedCellID)
  throws Exception {
    this.assertTokenizesTo(t, input, expectedImages, expectedTypes, null,
      expectedPosIncrs, expectedTupleID, expectedCellID);
  }

  public void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final String[] expectedDatatypes,
                                final int[] expectedPosIncrs,
                                final int[] expectedTupleID,
                                final int[] expectedCellID)
  throws Exception {

    assertTrue("has TermAttribute", t.hasAttribute(CharTermAttribute.class));
    final CharTermAttribute termAtt = t.getAttribute(CharTermAttribute.class);

    TypeAttribute typeAtt = null;
    if (expectedTypes != null) {
      assertTrue("has TypeAttribute", t.hasAttribute(TypeAttribute.class));
      typeAtt = t.getAttribute(TypeAttribute.class);
    }

    DatatypeAttribute dtypeAtt = null;
    if (expectedDatatypes != null) {
      assertTrue("has DatatypeAttribute", t.hasAttribute(DatatypeAttribute.class));
      dtypeAtt = t.getAttribute(DatatypeAttribute.class);
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

    for (int i = 0; i < expectedImages.length; i++) {

      assertTrue("token "+i+" exists", t.incrementToken());

      assertEquals(expectedImages[i], termAtt.toString());

      if (expectedTypes != null) {
        assertEquals(expectedTypes[i], typeAtt.type());
      }

      if (expectedDatatypes != null) {
        assertEquals(expectedDatatypes[i], dtypeAtt.datatypeURI().toString());
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
  }

}
