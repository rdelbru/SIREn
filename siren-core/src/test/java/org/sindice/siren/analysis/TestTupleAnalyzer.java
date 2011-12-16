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
 * @author Renaud Delbru [ 5 Feb 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis;


import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.LuceneTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer.URINormalisation;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;
import org.sindice.siren.analysis.filter.URILocalnameFilter;
import org.sindice.siren.analysis.filter.URINormalisationFilter;

public class TestTupleAnalyzer extends LuceneTestCase {

  private TupleAnalyzer _a;

  @Before
  public void setUp()
  throws Exception {
    super.setUp();
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    uriAnalyzer.setUriNormalisation(URINormalisation.FULL);
    _a = new TupleAnalyzer(TEST_VERSION_CURRENT, new StandardAnalyzer(TEST_VERSION_CURRENT), uriAnalyzer);
  }
  
  @After
  public void tearDown()
  throws Exception {
    super.tearDown();
    _a.close();
  }

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
    this.assertAnalyzesTo(a, input, expectedImages, expectedTypes, expectedPosIncrs, null,
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

    assertFalse("end of stream, received token " + termAtt.term(), t.incrementToken());
    t.end();
    t.close();
  }

  /**
   * Test the local URINormalisation: the word "the" is a stop word, hence it is
   * filtered. The position increment is updated accordingly, but it is not reset for
   * future calls. Corrects issue SRN-117.
   * @throws Exception
   */
  @Test
  public void testURINormalisation()
  throws Exception {
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    uriAnalyzer.setUriNormalisation(URINormalisation.LOCALNAME);
    _a = new TupleAnalyzer(TEST_VERSION_CURRENT, new StandardAnalyzer(TEST_VERSION_CURRENT), uriAnalyzer);
    
    this.assertAnalyzesTo(_a, "<http://dbpedia.org/resource/The_Kingston_Trio>",
                          new String[] { "kingston", "trio", "the_kingston_trio",
                                         "http://dbpedia.org/resource/the_kingston_trio" },
                          new String[] { "<URI>", "<URI>", "<URI>", "<URI>" },
                          new int[] { 2, 1, 0, 0 });
  }
  
  /**
   * The same, with Full normalisation -- the stop word is now "their" because in
   * {@link URINormalisationFilter}, there is inside a filter of words smaller
   * than 4 (it was 3 for {@link URILocalnameFilter}. 
   * @throws Exception
   */
  @Test
  public void testURINormalisation2()
  throws Exception {
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    uriAnalyzer.setUriNormalisation(URINormalisation.FULL);
    _a = new TupleAnalyzer(TEST_VERSION_CURRENT, new StandardAnalyzer(TEST_VERSION_CURRENT), uriAnalyzer);
    
    this.assertAnalyzesTo(_a, "<http://dbpedia.org/resource/their_Kingston_Trio>",
                          new String[] { "dbpedia", "resource", "kingston", "trio",
                                         "http://dbpedia.org/resource/their_kingston_trio" },
                          new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" },
                          new int[] { 1, 1, 2, 1, 0 });
  }
  
  @Test
  public void testURI()
  throws Exception {
    this.assertAnalyzesTo(_a, "<http://renaud.delbru.fr/>",
      new String[] { "renaud", "delbru", "http://renaud.delbru.fr" },
      new String[] { "<URI>", "<URI>", "<URI>" });
    this.assertAnalyzesTo(_a, "<http://Renaud.Delbru.fr/>",
      new String[] { "renaud", "delbru", "http://renaud.delbru.fr" },
      new String[] { "<URI>", "<URI>", "<URI>" });
    this.assertAnalyzesTo(
      _a,
      "<http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=N>",
      new String[] { "renaud", "delbru", "page", "html", "query",
                     "query", "start",
                     "http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=n" },
      new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>",
                     "<URI>", "<URI>" });
    this.assertAnalyzesTo(_a, "<mailto:renaud@delbru.fr>",
      new String[] { "renaud", "delbru",
                     "renaud@delbru.fr",
                     "mailto:renaud@delbru.fr" },
      new String[] { "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertAnalyzesTo(_a, "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
      new String[] { "1999", "syntax", "type",
                     "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"},
      new String[] { "<URI>", "<URI>", "<URI>", "<URI>" });
  }

  @Test
  public void testLiteral()
  throws Exception {
    this.assertAnalyzesTo(_a, "\"foo bar FOO BAR\"", new String[] { "foo",
        "bar", "foo", "bar" }, new String[] { "<ALPHANUM>", "<ALPHANUM>",
        "<ALPHANUM>", "<ALPHANUM>" });
    this.assertAnalyzesTo(_a, "\"ABC\\u0061\\u0062\\u0063\\u00E9\\u00e9ABC\"",
      new String[] { "abcabcééabc" }, new String[] { "<ALPHANUM>" });
  }

  @Test
  public void testLiteral2()
  throws Exception {
    this.assertAnalyzesTo(_a, "\"Renaud\"", new String[] { "renaud" },
      new String[] { "<ALPHANUM>" });
    this.assertAnalyzesTo(_a, "\"1 and 2\"", new String[] { "1", "2" },
      new String[] { "<NUM>", "<NUM>" });
    this.assertAnalyzesTo(_a, "\"renaud http://test/ \"", new String[] {
        "renaud", "http", "test" }, new String[] { "<ALPHANUM>", "<ALPHANUM>", "<ALPHANUM>" });
    this.assertAnalyzesTo(_a, "\"foo bar FOO BAR\"", new String[] { "foo",
        "bar", "foo", "bar" }, new String[] { "<ALPHANUM>", "<ALPHANUM>",
        "<ALPHANUM>", "<ALPHANUM>" });
    this.assertAnalyzesTo(_a, "\"ABC\\u0061\\u0062\\u0063\\u00E9\\u00e9ABC\"",
      new String[] { "abcabcééabc" }, new String[] { "<ALPHANUM>" });
  }

  /**
   * The datatype "en" was not registered, hence the literal is not analyzed
   * @throws Exception
   */
  @Test
  public void testLanguage()
  throws Exception {
    this.assertAnalyzesTo(_a, "\"test test2\"@en", new String[] { "test test2" }, new String[] { TupleTokenizer.getTokenTypes()[TupleTokenizer.LITERAL] });
  }
  
  /**
   * Register the "en" and "fr" datatypes analyzers
   * @throws Exception
   */
  @Test
  public void testLanguage2()
  throws Exception {
    _a.registerLiteralAnalyzer("en".toCharArray(), new StandardAnalyzer(TEST_VERSION_CURRENT));
    _a.registerLiteralAnalyzer("fr".toCharArray(), new WhitespaceAnalyzer(TEST_VERSION_CURRENT));
    this.assertAnalyzesTo(_a, "\"Test Test2\"@en <aaa> \"Test Test2\"@fr",
      new String[] { "test", "test2", "aaa", "Test", "Test2" },
      new String[] { "<ALPHANUM>", "<ALPHANUM>", "<URI>", "word", "word" });
    _a.clearRegisterLiteralAnalyzers();
  }
  
  @Test
  public void testAlreadyRegisteredAnalyzer()
  throws Exception {
    _a.registerLiteralAnalyzer("en".toCharArray(), new WhitespaceAnalyzer(TEST_VERSION_CURRENT));
    // this analyzer is not used, as the datatype "en" is already to an analyzer
    _a.registerLiteralAnalyzer("en".toCharArray(), new StandardAnalyzer(TEST_VERSION_CURRENT));
    this.assertAnalyzesTo(_a, "\"Test tesT2\"@en", new String[] { "Test", "tesT2" }, new String[] { "word", "word" });
    _a.clearRegisterLiteralAnalyzers();
  }

  @Test
  public void testBNodeFiltering()
  throws Exception {
    this.assertAnalyzesTo(_a, "_:b123 <aaa> <bbb> _:b212",
      new String[] { "aaa", "bbb" },
      new String[] { "<URI>", "<URI>" });
  }
  
  /**
   * test that the tokenization is resumed after filtering a token
   * @throws Exception
   */
  @Test
  public void testBNodeFiltering2()
  throws Exception {
    this.assertAnalyzesTo(_a, "_:b123 <http://renaud.delbru.fr/> _:b212 \"bbb rrr\"",
      new String[] { "renaud", "delbru", "http://renaud.delbru.fr", "bbb", "rrr" },
      new String[] { "<URI>", "<URI>", "<URI>", "<ALPHANUM>", "<ALPHANUM>" });
  }

}
