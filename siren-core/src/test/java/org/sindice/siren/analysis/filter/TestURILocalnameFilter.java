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
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.sindice.siren.analysis.TupleTokenizer;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;

public class TestURILocalnameFilter {

  private int MAX_LENGTH = URILocalnameFilter.DEFAULT_MAX_LENGTH;

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
    final URILocalnameFilter filter = new URILocalnameFilter(t);
    filter.setMaxLength(MAX_LENGTH);

    for (int i = 0; i < expectedImages.length; i++) {

      assertTrue("token "+i+" exists", filter.incrementToken());

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

    assertFalse("end of stream", filter.incrementToken());
    filter.end();
  }

  @Test
  public void testURI()
  throws Exception {
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr/>",
      new String[] { "http://renaud.delbru.fr/" },
      new String[] { "<URI>" });
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr/rdf/foaf#renaud>",
      new String[] { "renaud", "http://renaud.delbru.fr/rdf/foaf#renaud" },
      new String[] { "<URI>", "<URI>" });
    // too short localname, filtered out
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr/rdf/foaf#me>",
      new String[] { "http://renaud.delbru.fr/rdf/foaf#me" },
      new String[] { "<URI>" });
    // Tokenise on upper case
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr/rdf/uppercaseShouldBeTokenised>",
      new String[] { "uppercase", "Should", "Tokenised", "uppercaseShouldBeTokenised", "http://renaud.delbru.fr/rdf/uppercaseShouldBeTokenised" });
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr/rdf/AVeryLongLocalnameWithMoreThan64CharactersThatShouldNotBeTokenised>",
      new String[] { "AVeryLongLocalnameWithMoreThan64CharactersThatShouldNotBeTokenised", "http://renaud.delbru.fr/rdf/AVeryLongLocalnameWithMoreThan64CharactersThatShouldNotBeTokenised" });
  }

  @Test
  public void testOpenCycURI()
  throws Exception {
    this.assertNormalisesTo(_t, "<http://sw.opencyc.org/concept/Mx4ri_sbFDVGEdaAAACgydogAg>",
      new String[] { "Mx4ri", "Eda", "Cgydog", "Mx4ri_sbFDVGEdaAAACgydogAg", "http://sw.opencyc.org/concept/Mx4ri_sbFDVGEdaAAACgydogAg" },
      new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://sw.opencyc.org/concept/Mx4rpZ2oIm5SEdqAAAACs71DGQ>",
      new String[] { "Mx4rp", "Z2o", "Im5", "Edq", "Cs71", "Mx4rpZ2oIm5SEdqAAAACs71DGQ", "http://sw.opencyc.org/concept/Mx4rpZ2oIm5SEdqAAAACs71DGQ" },
      new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://sw.opencyc.org/concept/Mx4r7FpweNCOQdiMucbWDv61HQ>",
      new String[] { "Mx4r7", "Fpwe", "Qdi", "Mucb", "Dv61", "Mx4r7FpweNCOQdiMucbWDv61HQ", "http://sw.opencyc.org/concept/Mx4r7FpweNCOQdiMucbWDv61HQ" },
      new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
  }

  @Test
  public void testOpenCycURIWithMaxLength()
  throws Exception {
    MAX_LENGTH = 20;
    this.assertNormalisesTo(_t, "<http://sw.opencyc.org/concept/Mx4ri_sbFDVGEdaAAACgydogAg>",
      new String[] { "Mx4ri_sbFDVGEdaAAACgydogAg", "http://sw.opencyc.org/concept/Mx4ri_sbFDVGEdaAAACgydogAg" });
    this.assertNormalisesTo(_t, "<http://sw.opencyc.org/concept/Mx4rpZ2oIm5SEdqAAAACs71DGQ>",
      new String[] { "Mx4rpZ2oIm5SEdqAAAACs71DGQ", "http://sw.opencyc.org/concept/Mx4rpZ2oIm5SEdqAAAACs71DGQ" });
    this.assertNormalisesTo(_t, "<http://sw.opencyc.org/concept/Mx4r7FpweNCOQdiMucbWDv61HQ>",
      new String[] { "Mx4r7FpweNCOQdiMucbWDv61HQ", "http://sw.opencyc.org/concept/Mx4r7FpweNCOQdiMucbWDv61HQ" });
  }

  @Test
  public void testPosInc()
  throws Exception {
    this.assertNormalisesTo(_t, "<http://example.org/schema/age>",
      new String[] { "age", "http://example.org/schema/age" },
      new String[] { "<URI>", "<URI>" },
      new int[] { 1,0 });
    this.assertNormalisesTo(_t, "<http://example.org/schema/me>",
      new String[] { "http://example.org/schema/me" },
      new String[] { "<URI>" },
      new int[] { 1 });
    this.assertNormalisesTo(_t, "<http://rdf.data-vocabulary.org/#startDate>",
      new String[] { "start", "Date", "startDate", "http://rdf.data-vocabulary.org/#startDate" },
      new String[] { "<URI>", "<URI>", "<URI>", "<URI>" },
      new int[] { 1, 1, 0, 0 });
  }

}
