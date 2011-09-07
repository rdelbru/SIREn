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
package org.sindice.siren.qparser.keyword;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

public class KeywordQParserTest {

  private Map<String, Float> boosts;
  private KeywordQParserImpl parser;

  @Before
  public void setUp() throws Exception {
    final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_31);
    final PerFieldAnalyzerWrapper analyzerWrapper = new PerFieldAnalyzerWrapper(analyzer);
    boosts = new HashMap<String, Float>();
    boosts.put("explicit-content", 1.0f);
    boosts.put("label", 2.5f);
    parser = new KeywordQParserImpl(analyzerWrapper, boosts, false);
  }

  @Test
  public void testSingleWord() throws ParseException {
    final Query q = parser.parse("hello");
    assertEquals("explicit-content:hello label:hello^2.5",
        q.toString());
  }

  @Test
  public void testDistinctAnalyzer() throws ParseException {
    final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_31);
    final PerFieldAnalyzerWrapper analyzerWrapper = new PerFieldAnalyzerWrapper(analyzer);
    analyzerWrapper.addAnalyzer("label", new StandardAnalyzer(Version.LUCENE_31));
    final KeywordQParserImpl parser = new KeywordQParserImpl(analyzerWrapper, boosts, false);
    final Query q = parser.parse("hELlo");
    assertEquals("explicit-content:hELlo label:hello^2.5",
        q.toString());
  }

  @Test
  public void testMultipleWords() throws ParseException {
    final Query q = parser.parse("hello world");
    assertEquals("(explicit-content:hello label:hello^2.5) " +
                 "(explicit-content:world label:world^2.5)", q.toString());
  }

  @Test
  public void testURIsWithDefaultOR() throws ParseException {
    parser.setDefaultOperator(Operator.OR);
    final Query q = parser.parse("http://www.google.com http://hello.world#me");
    assertEquals("(explicit-content:http://www.google.com label:http://www.google.com^2.5) " +
                 "(explicit-content:http://hello.world#me label:http://hello.world#me^2.5)",
                 q.toString());
  }

  @Test
  public void testURIsWithDefaultAND() throws ParseException {
    parser.setDefaultOperator(Operator.AND);
    final Query q = parser.parse("http://www.google.com http://hello.world#me");
    assertEquals("+(explicit-content:http://www.google.com label:http://www.google.com^2.5) " +
                 "+(explicit-content:http://hello.world#me label:http://hello.world#me^2.5)",
                 q.toString());
  }

  @Test
  public void testCompoundQuery() throws ParseException {
    final Query q = parser.parse("http://www.google.com +hello -world");
    assertEquals("(explicit-content:http://www.google.com label:http://www.google.com^2.5) " +
    		"+(explicit-content:hello label:hello^2.5) " +
    		"-(explicit-content:world label:world^2.5)", q.toString());
  }

  @Test
  public void testCustomFieldQuery() throws ParseException {
    final Query q = parser.parse("domain:dbpedia data-source:DUMP");
    assertEquals("domain:dbpedia data-source:DUMP", q.toString());
  }

  @Test
  public void testFormatQuery() throws Exception {
    final Query q = parser.parse("format:MICROFORMAT");
    assertEquals("format:MICROFORMAT", q.toString());
  }

  @Test
  public void testFuzzyQuery() throws Exception {
    try {
      final Query q = parser.parse("michele~0.9");
      fail("Expected ParseException");
    } catch (final ParseException e) {
    }
  }

  @Test
  public void testWildcardQuery() throws Exception {
    try {
      final Query q = parser.parse("miche*");
      fail("Expected ParseException");
    } catch (final ParseException e) {
    }
  }

  @Test
  public void testEncoding() throws Exception {
    final Query q = parser.parse("möller");
    assertEquals("explicit-content:möller label:möller^2.5", q.toString());
  }

  @Test
  public void testDashedURI() throws Exception {
    final String url = "http://semantic-conference.com/session/569/";
    assertEquals("explicit-content:http://semantic-conference.com/session/569/ " +
                   "label:http://semantic-conference.com/session/569/^2.5", parser.parse(url).toString());
  }

  @Test
  public void testDisabledFieldQuery() throws ParseException {
    final Map<String, Float> boosts = new HashMap<String, Float>();
    boosts.put("explicit-content", 1.0f);
    final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_31);
    final KeywordQParserImpl parser = new KeywordQParserImpl(analyzer, boosts, true);
    final Query q = parser.parse("+foaf:name -foaf\\:person domain:dbpedia.org http://test.org/");
    assertEquals("+explicit-content:foaf:name " +
    		"-explicit-content:foaf\\:person " +
    		"explicit-content:domain:dbpedia.org " +
    		"explicit-content:http://test.org/", q.toString());
  }

  @Test
  public void testDisabledFieldQueryExpanded() throws ParseException {
    final Map<String, Float> boosts = new HashMap<String, Float>();
    boosts.put("explicit-content", 1.0f);
    boosts.put("label", 1.0f);
    final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_31);
    final KeywordQParserImpl parser = new KeywordQParserImpl(analyzer, boosts, true);
    final Query q = parser.parse("+foaf:name http://test.org/");
    assertEquals("+(explicit-content:foaf:name label:foaf:name) " +
        "(explicit-content:http://test.org/ label:http://test.org/)", q.toString());
  }

  // SRN-106: Query expansion does not respect unary operator
  @Test
  public void testUnaryOperator() throws ParseException {
    final Map<String, Float> boosts = new HashMap<String, Float>();
    boosts.put("explicit-content", 1.0f);
    final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_31);
    final KeywordQParserImpl parser = new KeywordQParserImpl(analyzer, boosts, true);

    parser.setDefaultOperator(Operator.AND);
    final Query q = parser.parse("+mike +conlon vivo");
    assertEquals("+explicit-content:mike +explicit-content:conlon explicit-content:vivo",
                 q.toString());
  }

  // SRN-106: Query expansion does not respect unary operator
  @Test
  public void testUnaryOperatorMultiField() throws ParseException {
    parser.setDefaultOperator(Operator.AND);
    final Query q = parser.parse("+mike +conlon vivo");
    assertEquals("+(explicit-content:mike label:mike^2.5) " +
                 "+(explicit-content:conlon label:conlon^2.5) " +
                 "(explicit-content:vivo label:vivo^2.5)",
                 q.toString());
  }

  @Test
  public void testNestedGroups() throws ParseException {
    final Map<String, Float> boosts = new HashMap<String, Float>();
    boosts.put("explicit-content", 1.0f);
    final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_31);
    final KeywordQParserImpl parser = new KeywordQParserImpl(analyzer, boosts, true);
    parser.setDefaultOperator(Operator.AND);

    final Query q = parser.parse("Test AND ((literal OR uri OR resource) AND (pattern OR patterns OR query))");
    System.out.println(q.toString());
    assertEquals("+explicit-content:Test " +
                 "+(+(explicit-content:literal explicit-content:uri explicit-content:resource) " +
                 "+(explicit-content:pattern explicit-content:patterns explicit-content:query))",
                 q.toString());
  }

}
