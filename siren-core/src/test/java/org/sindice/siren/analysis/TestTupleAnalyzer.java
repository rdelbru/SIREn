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

package org.sindice.siren.analysis;

import java.io.IOException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer.URINormalisation;
import org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter;
import org.sindice.siren.analysis.filter.URILocalnameFilter;
import org.sindice.siren.analysis.filter.URINormalisationFilter;
import org.sindice.siren.util.XSDDatatype;

public class TestTupleAnalyzer extends NodeAnalyzerTestCase<TupleAnalyzer> {

  @Override
  protected TupleAnalyzer getNodeAnalyzer() {
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    uriAnalyzer.setUriNormalisation(URINormalisation.FULL);
    final TupleAnalyzer tupleAnalyzer = new TupleAnalyzer(TEST_VERSION_CURRENT,
      new StandardAnalyzer(TEST_VERSION_CURRENT), uriAnalyzer);
    tupleAnalyzer.registerDatatype(XSDDatatype.XSD_ANY_URI.toCharArray(), uriAnalyzer);
    return tupleAnalyzer;
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
    _a.registerDatatype(XSDDatatype.XSD_ANY_URI.toCharArray(), uriAnalyzer);

    this.assertAnalyzesTo(_a, "<http://dbpedia.org/resource/The_Kingston_Trio>",
                          new String[] { "kingston", "trio", "the_kingston_trio",
                                         "http://dbpedia.org/resource/the_kingston_trio" },
                          new String[] { "word", "word", "word", "word" },
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
    _a.registerDatatype(XSDDatatype.XSD_ANY_URI.toCharArray(), uriAnalyzer);

    this.assertAnalyzesTo(_a, "<http://dbpedia.org/resource/their_Kingston_Trio>",
                          new String[] { "dbpedia", "resource", "kingston", "trio",
                                         "http://dbpedia.org/resource/their_kingston_trio" },
                          new String[] { "word", "word", "word", "word", "word" },
                          new int[] { 1, 1, 2, 1, 0 });
  }

  @Test
  public void testURI()
  throws Exception {
    this.assertAnalyzesTo(_a, "<http://renaud.delbru.fr/>",
      new String[] { "renaud", "delbru", "http://renaud.delbru.fr" },
      new String[] { "word", "word", "word" });
    this.assertAnalyzesTo(_a, "<http://Renaud.Delbru.fr/>",
      new String[] { "renaud", "delbru", "http://renaud.delbru.fr" },
      new String[] { "word", "word", "word" });
    this.assertAnalyzesTo(
      _a,
      "<http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=N>",
      new String[] { "renaud", "delbru", "page", "html", "query",
                     "query", "start",
                     "http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=n" },
      new String[] { "word", "word", "word", "word", "word", "word",
                     "word", "word" });
    this.assertAnalyzesTo(_a, "<mailto:renaud@delbru.fr>",
      new String[] { "renaud", "delbru",
                     "renaud@delbru.fr",
                     "mailto:renaud@delbru.fr" },
      new String[] { "word", "word", "word", "word" });
    this.assertAnalyzesTo(_a, "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
      new String[] { "1999", "syntax", "type",
                     "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"},
      new String[] { "word", "word", "word", "word" });
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
   * The datatype "en" was not registered.
   * IOException thrown by {@link DatatypeAnalyzerFilter}.
   */
  @Test(expected=IOException.class)
  public void testLanguage()
  throws Exception {
    this.assertAnalyzesTo(_a, "\"test test2\"@en",
      new String[] { "test test2" },
      new String[] { TupleTokenizer.getTokenTypes()[TupleTokenizer.LITERAL] }
    );
  }

  /**
   * Register the "en" and "fr" datatypes analyzers
   */
  @Test
  public void testLanguage2()
  throws Exception {
    _a.registerDatatype("en".toCharArray(), new StandardAnalyzer(TEST_VERSION_CURRENT));
    _a.registerDatatype("fr".toCharArray(), new WhitespaceAnalyzer(TEST_VERSION_CURRENT));
    this.assertAnalyzesTo(_a, "\"Test Test2\"@en <aaa> \"Test Test2\"@fr",
      new String[] { "test", "test2", "aaa", "Test", "Test2" },
      new String[] { "<ALPHANUM>", "<ALPHANUM>", "word", "word", "word" });
    _a.clearDatatypes();
  }

  @Test
  public void testAlreadyRegisteredAnalyzer()
  throws Exception {
    _a.registerDatatype("en".toCharArray(), new WhitespaceAnalyzer(TEST_VERSION_CURRENT));
    // this analyzer is not used, as the datatype "en" is already to an analyzer
    _a.registerDatatype("en".toCharArray(), new StandardAnalyzer(TEST_VERSION_CURRENT));
    this.assertAnalyzesTo(_a, "\"Test tesT2\"@en", new String[] { "Test", "tesT2" }, new String[] { "word", "word" });
    _a.clearDatatypes();
  }

  @Test
  public void testBNodeFiltering()
  throws Exception {
    this.assertAnalyzesTo(_a, "_:b123 <aaa> <bbb> _:b212",
      new String[] { "aaa", "bbb" },
      new String[] { "word", "word" });
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
      new String[] { "word", "word", "word", "<ALPHANUM>", "<ALPHANUM>" });
  }

  /**
   * In Lucene4.0, the position increment behaviour changed: it is not allowed
   * anymore to have the first position increment == 0
   * @throws Exception
   */
  @Test
  public void testFirstPosInc()
  throws Exception {
    this.assertAnalyzesTo(_a, "<aaa>",
      new String[] { "aaa" },
      new String[] { "word" },
      new int[] { 1 });
  }

}
