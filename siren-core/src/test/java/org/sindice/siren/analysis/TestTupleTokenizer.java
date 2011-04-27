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
package org.sindice.siren.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;

/**
 * TODO: Replace token type string by a reference to their instances in the
 * related tokenizer classes (as done in {@link testLanguage}
 */
public class TestTupleTokenizer {

  private final Tokenizer _t = new TupleTokenizer(new StringReader(""),
                               Integer.MAX_VALUE, new StandardAnalyzer(Version.LUCENE_31));

  public void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expected)
  throws Exception {
    this.assertTokenizesTo(t, input, expected, null);
  }

  public void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes)
  throws Exception {
    this.assertTokenizesTo(t, input, expectedImages, expectedTypes, null);
  }

  public void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final int[] expectedPosIncrs)
  throws Exception {
    this.assertTokenizesTo(t, input, expectedImages, expectedTypes, null, null,
      null);
  }

  public void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
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
    this.assertTokenizesTo(_t, "\"Renaud\"", new String[] { "renaud" },
      new String[] { "<ALPHANUM>" });
    this.assertTokenizesTo(_t, "\"1 and 2\"", new String[] { "1", "2" },
      new String[] { "<NUM>", "<NUM>" });
    this.assertTokenizesTo(_t, "\"renaud http://test/ \"", new String[] {
        "renaud", "http", "test" }, new String[] { "<ALPHANUM>", "<ALPHANUM>", "<ALPHANUM>" });
    this.assertTokenizesTo(_t, "\"foo bar FOO BAR\"", new String[] { "foo",
        "bar", "foo", "bar" }, new String[] { "<ALPHANUM>", "<ALPHANUM>",
        "<ALPHANUM>", "<ALPHANUM>" });
    this.assertTokenizesTo(_t, "\"ABC\\u0061\\u0062\\u0063\\u00E9\\u00e9ABC\"",
      new String[] { "abcabcééabc" }, new String[] { "<ALPHANUM>" });
  }

  @Test
  public void testDot()
  throws Exception {
    this.assertTokenizesTo(_t, "<http://te.st> . \"ren . aud\" . ",
      new String[] { "http://te.st", ".", "ren", "aud", "." }, new String[] {
          "<URI>", "<DOT>", "<ALPHANUM>", "<ALPHANUM>", "<DOT>" });
    this.assertTokenizesTo(_t, "<aaa> \"bbb\". <bbb> <aaa>. <ccc> .",
      new String[] { "aaa", "bbb", ".", "bbb", "aaa", ".", "ccc", "." }, new String[] {
          "<URI>", "<ALPHANUM>", "<DOT>", "<URI>", "<URI>", "<DOT>", "<URI>", "<DOT>" });
  }

  @Test
  public void testLanguage()
  throws Exception {
    this.assertTokenizesTo(_t, "\"test\"@en", new String[] { "test", "en" },
      new String[] { "<ALPHANUM>", "<LANGUAGE>" });
    this.assertTokenizesTo(_t, "\"toto@titi.fr \"@fr", new String[] {
        "toto", "titi.fr", "fr" },
        new String[] { StandardTokenizer.TOKEN_TYPES[StandardTokenizer.ALPHANUM],
                       StandardTokenizer.TOKEN_TYPES[StandardTokenizer.ALPHANUM],
                       TupleTokenizer.TOKEN_TYPES[TupleTokenizer.LANGUAGE] });
  }

  @Test
  public void testDatatype()
  throws Exception {
    this.assertTokenizesTo(_t, "<http://test/>^^<http://type/test>",
      new String[] { "http://test/", "http://type/test" }, new String[] {
          "<URI>", "<DATATYPE>" });
  }

  @Test
  public void testStructuralNode()
  throws Exception {
    this.assertTokenizesTo(_t, "<http://renaud.delbru.fr/>",
      new String[] { "http://renaud.delbru.fr/" }, new String[] { "<URI>" },
      new int[] { 1 }, new int[] { 0 }, new int[] { 0 });
    this.assertTokenizesTo(_t,
      "<http://renaud.delbru.fr/> <http://renaud.delbru.fr/>", new String[] {
          "http://renaud.delbru.fr/", "http://renaud.delbru.fr/" },
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
      new String[] { "http://te.st", ".", "ren", "aud", "." }, new String[] {
          "<URI>", "<DOT>", "<ALPHANUM>", "<ALPHANUM>", "<DOT>" }, new int[] {
          1, 1, 1, 1, 1, 1, 1, 1 }, new int[] { 0, 0, 1, 1, 1 }, new int[] { 0,
          1, 0, 0, 1 });
  }

}
