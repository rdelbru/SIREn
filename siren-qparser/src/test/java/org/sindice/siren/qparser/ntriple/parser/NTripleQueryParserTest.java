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
 * @author Renaud Delbru [ 25 Apr 2008 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.ntriple.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NTripleQueryParserTest {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQuerySimpleTriple()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> <http://o> .";
    final String query = " <http://s> <http://p> <http://o>";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }
  
  @Test
  public void testLuceneSpecialCharacter()
  throws Exception {
    final String ntriple = "<http://sw.deri.org/~aidanh/> <http://p> <http://o> .";
    final String query = " <http://sw.deri.org/~aidanh/> <http://p> <http://o>";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  @Test
  public void testNTripleQuerySimpleImplicitTriple()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> <http://o> .";
    final String query = " <http://s> <http://p> <http://o>";

    assertTrue(NTripleQueryParserTestHelper.matchImplicit(ntriple, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryWildcard()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> <http://o> .";
    final String query1 = " <http://s> <http://p> *";
    final String query2 = " <http://s> * <http://o>";
    final String query3 = " * <http://p> <http://o>";
    final String query4 = "<http://s> * * ";
    final String query5 = "* <http://p> * ";
    final String query6 = "* * <http://o> ";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query1));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query2));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query3));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query4));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query5));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query6));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryWildcardFalse()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> <http://o> ";
    final String query = " * * *";

    assertFalse(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryPropertyLiteral()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> \"a simple literal\" .";
    final String query = "* <http://p> \"a simple literal\"";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   * Test if an URIPattern is correctly executed
   */
  @Test
  public void testNTripleQueryURIPattern1()
  throws Exception {
    final String ntriple = "<http://s> <http://p> \"literal\" .";
    final String query1 = "<http://s OR aaa://s> <http://p> 'literal'";
    final String query2 = "<http://s || aaa://s> <http://p> 'literal'";
    final String query3 = "<http://s && aaa://s> <http://p> 'literal'";
    final String query4 = "<NOT http://s || aaa://s> <http://p> 'literal'";
    final String query5 = "* <http://p1 OR http://p2 OR http://p3 OR http://p> *";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query1));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query2));
    assertFalse(NTripleQueryParserTestHelper.match(ntriple, query3));
    assertFalse(NTripleQueryParserTestHelper.match(ntriple, query4));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query5));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   * Test that a parsing in the Query Builder happened
   * @throws Exception
   */
  @Test(expected=ParseException.class)
  public void testNTripleQueryBuilderError()
  throws Exception {
    final String ntriple = "<http://s> <http://p> \"test simple literal\" .";
    final String query1 = "<http://s OR aaa://s OR> <http://p> 'literal'";
    final String query2 = "<http://s OR aaa://s> <http://p> 'test & literal'";

    NTripleQueryParserTestHelper.match(ntriple, query1);
    NTripleQueryParserTestHelper.match(ntriple, query2);
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryPropertyLiteralPattern1()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> \"Test for literal pattern\" .";
    final String query = "* <http://p> 'literal'";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryPropertyLiteralPattern2()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> \"Test for literal pattern\" .";
    final String query = "* <http://p> 'Test && literal'";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  /**
   * SRN-99
   */
  @Test
  public void testNTripleQueryPropertyLiteralPattern3()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple1 = "<http://s> <http://p> \"Test for literal pattern\" .";
    final String ntriple2 = "<http://s> <http://p> \"Test for pattern\" .";
    final String query1 = "* <http://p> 'Test AND ((literal OR uri) AND pattern)'";
    final String query2 = "* <http://p> 'Test AND ((literal OR uri OR resource) AND (pattern OR patterns OR query))'";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple1, query1));
    assertFalse(NTripleQueryParserTestHelper.match(ntriple2, query1));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple1, query1));
    assertFalse(NTripleQueryParserTestHelper.match(ntriple2, query2));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleSubjectLiteral()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> \"A simple literal\" .";
    final String query = "<http://s> * \"A simple literal\"";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleSubjectLiteralPattern1()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> \"Test for literal pattern\" .";
    final String query = "<http://s> * 'literal'";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleSubjectLiteralPattern2()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> \"Test for literal pattern\" .";
    final String query = "<http://s> * 'Test && literal'";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryLiteralPattern()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> \"Blue Socks\" .";
    final String query1 = "* <http://p> 'Blue'";
    final String query2 = "* <http://p> 'Socks'";
    final String query3 = "* <http://p> '\"Blue Socks\"'";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query1));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query2));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query3));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryTriplePatternDisjunction()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple1 = "<http://s> <http://p1> \"literal\" .\n" +
    		"<http://s> <http://p2> <http://o2> .";
    final String ntriple2 = "<http://s> <http://p> \"literals\" .\n" +
    "<http://s> <http://p2> <http://o> .";
    final String query = "<http://s> * 'literal' OR * <http://p2> <http://o>";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple1, query));
    assertTrue(NTripleQueryParserTestHelper.match(ntriple2, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryTriplePatternConjunction()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriples = "<http://s> <http://p1> \"literal\" .\n" +
    		"<http://s> <http://p2> <http://o> .\n";
    final String query = "<http://s> * 'literal' AND * <http://p2> <http://o>";

    assertTrue(NTripleQueryParserTestHelper.match(ntriples, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryTriplePatternComplement1()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriples1 = "<http://s> <http://p1> \"literal\" .\n" +
      "<http://s> <http://p2> <http://o> .\n";
    final String ntriples2 = "<http://s> <http://p1> \"literal\" .\n" +
      "<http://s> <http://p2> <http://o2> .\n";
    final String query = "<http://s> * 'literal' - * <http://p2> <http://o>";

    assertFalse(NTripleQueryParserTestHelper.match(ntriples1, query));
    assertTrue(NTripleQueryParserTestHelper.match(ntriples2, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   * SRN-91
   */
  @Test
  public void testNTripleQueryTriplePatternComplement2()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriples1 = "<http://s> <http://p1> \"literal\" .\n" +
      "<http://s> <http://p2> <http://o> .\n";
    final String ntriples2 = "<http://s> <http://p1> \"literal\" .\n" +
      "<http://s> <http://p2> <http://o2> .\n";
    final String query = "<http://s> * 'literal' NOT * <http://p2> <http://o>";

    assertFalse(NTripleQueryParserTestHelper.match(ntriples1, query));
    assertTrue(NTripleQueryParserTestHelper.match(ntriples2, query));
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.analysis.NTripleStandardAnalyzer#RDFNtripleStandardAnalyzer()}.
   */
  @Test
  public void testNTripleQueryLineFeed()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p1> \"literal\" .\n" +
    		"<http://s> <http://p2> <http://o> .";
    final String query = "<http://s> * 'literal' AND\r\n * <http://p2> \n\r \n <http://o>";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  @Test
  public void testNTripleMultiFieldQuery()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final Map<String, Float> boosts = new HashMap<String, Float>();
    boosts.put("explicit", 1.0f);
    boosts.put("implicit", 1.0f);

    Map<String, String> ntriples = new HashMap<String, String>();
    ntriples.put("explicit", "<http://s> <http://p1> \"literal\" .\n");
    ntriples.put("implicit", "<http://s> <http://p2> <http://o> .\n");
    final String query = "<http://s> * 'literal' AND\r\n * <http://p2> \n\r \n <http://o>";

    // Should not match, no field content is matching the two triple patterns
    assertFalse(NTripleQueryParserTestHelper.match(ntriples, boosts, query, false));

    ntriples = new HashMap<String, String>();
    ntriples.put("explicit", "<http://s> <http://p1> \"literal\" .\n" +
    		"<http://s> <http://p2> <http://o> .\n");
    ntriples.put("implicit", "<http://s> <http://p1> \"literal\" .\n" +
    		"<http://s> <http://p2> <http://o> .\n");

    // Should match, the two field content are matching the two triple patterns
    assertTrue(NTripleQueryParserTestHelper.match(ntriples, boosts, query, false));

    ntriples = new HashMap<String, String>();
    ntriples.put("explicit", "<http://s> <http://p1> \"literal\" .\n" +
        "<http://s> <http://p2> <http://o> .\n");
    ntriples.put("implicit", "<http://s> <http://p2> <http://o> .\n");

    // Should match, one of the field content is matching the two triple patterns
    assertTrue(NTripleQueryParserTestHelper.match(ntriples, boosts, query, false));
  }

  @Test
  public void testScatteredNTripleMultiFieldQuery()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final Map<String, Float> boosts = new HashMap<String, Float>();
    boosts.put("explicit", 1.0f);
    boosts.put("implicit", 1.0f);
    Map<String, String> ntriples = new HashMap<String, String>();
    ntriples.put("explicit", "<http://s> <http://p1> \"literal\" .\n");
    ntriples.put("implicit", "<http://s> <http://p2> <http://o> .\n");
    final String query = "<http://s> * 'literal' AND\r\n * <http://p2> \n\r \n <http://o>";

    // Should match, the two field content are matching either one of the two triple patterns
    assertTrue(NTripleQueryParserTestHelper.match(ntriples, boosts, query, true));

    ntriples = new HashMap<String, String>();
    ntriples.put("explicit", "<http://s> <http://p1> \"literal\" .\n");
    ntriples.put("implicit", "<http://s> <http://p2> <http://o2> .\n");

    // Should not match, only the first field content is matching one triple pattern
    assertFalse(NTripleQueryParserTestHelper.match(ntriples, boosts, query, true));
  }

  @Test
  public void testScatteredNTripleMultiFieldQueryScore()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    Map<String, Float> boosts = new HashMap<String, Float>();
    boosts.put("explicit", 1.0f);
    boosts.put("implicit", 1.0f);
    final Map<String, String> ntriples = new HashMap<String, String>();
    ntriples.put("explicit", "<http://s> <http://p1> \"literal\" .\n");
    ntriples.put("implicit", "<http://s> <http://p2> <http://o> .\n");
    final String query = "<http://s> * 'literal' AND\r\n * <http://p2> \n\r \n <http://o>";

    final float score1 = NTripleQueryParserTestHelper.getScore(ntriples, boosts, query, true);

    boosts = new HashMap<String, Float>();
    boosts.put("explicit", 1.0f);
    boosts.put("implicit", 0.6f);
    final float score2 = NTripleQueryParserTestHelper.getScore(ntriples, boosts, query, true);

    assertTrue(score1 > score2);
  }

}
