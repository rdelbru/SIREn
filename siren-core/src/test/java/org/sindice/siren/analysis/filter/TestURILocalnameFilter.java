/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sindice.siren.analysis.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;
import org.sindice.siren.analysis.TupleTokenizer;

public class TestURILocalnameFilter {

  private int MAX_LENGTH = URILocalnameFilter.DEFAULT_MAX_LENGTH;

  private final Tokenizer _t = new TupleTokenizer(new StringReader(""));

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

    t.setReader(new StringReader(input));
    t.reset();

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

    }

    assertFalse("end of stream", filter.incrementToken());
    filter.end();
    filter.close();
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


    final String triple = "<http://dbpedia.org/resource/The_Kingston_Trio> " +
                          "<http://purl.org/dc/terms/subject>  " +
                          "<http://dbpedia.org/resource/Category:Decca_Records_artists>";
    this.assertNormalisesTo(_t, triple,
        new String[] { "The", "Kingston", "Trio", "The_Kingston_Trio", "http://dbpedia.org/resource/The_Kingston_Trio",
                       "subject", "http://purl.org/dc/terms/subject",
                       "Category", "Decca", "Records", "artists", "Category:Decca_Records_artists", "http://dbpedia.org/resource/Category:Decca_Records_artists" },
        new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>",
                       "<URI>", "<URI>",
                       "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" },
        new int[] { 1, 1, 1, 0, 0,
                    1, 0,
                    1, 1, 1, 1, 0, 0 });
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
