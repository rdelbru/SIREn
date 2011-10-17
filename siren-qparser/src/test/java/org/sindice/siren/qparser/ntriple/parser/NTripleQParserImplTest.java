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
 * @project solr-plugins
 *
 * @author Renaud Delbru [ 13 févr. 08 ]
 * @link http://renaud.delbru.fr/
 * All rights reserved.
 */
package org.sindice.siren.qparser.ntriple.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import java_cup.runtime.Symbol;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.qparser.analysis.NTripleQueryAnalyzer;
import org.sindice.siren.qparser.ntriple.NTripleQParserImpl;
import org.sindice.siren.qparser.ntriple.NTripleQueryParser.CupScannerWrapper;
import org.sindice.siren.qparser.ntriple.query.model.EmptyQuery;
import org.sindice.siren.qparser.ntriple.query.model.NTripleQuery;

public class NTripleQParserImplTest {

  private final String _dsDir = "src/test/resources/dataset/ntriple-parser/";

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {}

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseEmpty() throws Exception {
    final String query = "";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertTrue(q instanceof EmptyQuery);
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseSimpleURI1() throws Exception {
    final String query = "* * <s>";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "SimpleURI1.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseSimpleURI2() throws Exception {
    final String query = "* <http://ns/#s> <http://ns/p>";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "SimpleURI2.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseSimpleLiteral2() throws Exception {
    final String query = "* * \"A literal ...\"";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "SimpleLiteral.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   * @throws Exception
   */
  @Test
  public void testParseLiteralWithWildcard() throws Exception {
    final String query = "* * \"pao*\"";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "LiteralWildcard.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseSimpleTriple1() throws Exception {
    final String query = "<http://ns/#s> <http://ns/p> <http://ns/o>";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "SimpleTriple1.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseSimpleTriple2() throws Exception {
    final String query = "<http://ns/#s> <http://ns/p> \"A Literal ...\"";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "SimpleTriple2.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseSimpleTriple3() throws Exception {
    final String query = "* <http://purl.org/dc/terms/license> \"http://creativecommons.org/licenses/by-nc-sa/3.0/us\"";
    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "SimpleTriple3.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseBinaryClauseAnd() throws Exception {
    String query = "<http://ns/#s> <http://ns/p> \"A Literal ...\" AND ";
    query += "* <http://#s> \"A \\\"second Literal\\\"\"";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "BinaryClauseAnd.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseBinaryClauseOr() throws Exception {
    String query = "<http://ns/#s> <http://ns/p> \"A Literal ...\" OR ";
    query += "* <http://#s> \"A \\\"second Literal\\\"\"";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "BinaryClauseOr.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseBinaryClauseMinus() throws Exception {
    String query = "<http://ns/#s> <http://ns/p> \"A Literal ...\" - ";
    query += "* <http://#s> \"A \\\"second Literal\\\"\"";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "BinaryClauseMinus.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseNestedClause1() throws Exception {
    final String query = "<s> <p> <o> AND (<s> <p2> <o2> OR <s> <p3> \"A literal\")";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "NestedClause1.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseNestedClause2() throws Exception {
    final String query = "(<s> <p> <o> AND <s> <p2> <o2>) OR <s> <p3> \"A literal\"";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "NestedClause2.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   * @throws Exception
   */
  @Test
  public void testParseNestedClause3() throws Exception {
    final String query = "(<s> <p> <o> AND <s> <p2> <o2>)";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "NestedClause3.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   */
  @Test
  public void testParseNestedClauseEmpty() throws Exception {
    final String query = "<s> <p> <o> AND ()";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    final Symbol sym = parser.parse();
    final NTripleQuery q = (NTripleQuery) sym.value;
    assertEquals(this.readFile(_dsDir + "NestedClauseEmpty.txt"), q.toString());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   */
  @Test(expected=IOException.class)
  public void testParseInvalid1() throws Exception {
    final String query = "   s  ";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    parser.parse();
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   */
  @Test(expected=IOException.class)
  public void testParseInvalid2() throws Exception {
    final String query = " <s> <p> AND (<p> OR <o>  ";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    parser.parse();
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   */
  @Test(expected=IOException.class)
  public void testParseInvalid3() throws Exception {
    final String query = " <s> <p> <p> <o>  ";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    parser.parse();
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.NTripleQParserImpl#parse()}.
   */
  @Test(expected=IOException.class)
  public void testParseInvalid4() throws Exception {
    final String query = " <s> <p> \"Literal ...  ";

    final NTripleQueryAnalyzer analyzer = new NTripleQueryAnalyzer();
    final TokenStream stream = analyzer.tokenStream(null, new StringReader(query));
    final NTripleQParserImpl parser = new NTripleQParserImpl(new CupScannerWrapper(stream));
    parser.parse();
  }

  private String readFile(final String filename) throws IOException {
    final FileReader reader = new FileReader(filename);
    final String content = FileUtils.readFileToString(new File(filename));
    reader.close();
    return content.trim();
  }

  /**
   * Simple boolean query inside an URI
   * @throws CorruptIndexException
   * @throws LockObtainFailedException
   * @throws IOException
   * @throws ParseException
   */
  @Test
  public void testSimpleURIPattern1()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<aaa://s> <http://p> <http://o> .";
    final String query = " <aaa://s OR http://notMe> <http://p> <http://o>";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  /**
   * Impossible boolean query with AND
   * @throws CorruptIndexException
   * @throws LockObtainFailedException
   * @throws IOException
   * @throws ParseException
   */
  @Test
  public void testSimpleURIPattern2()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> <http://o> .";
    final String query = " <http://s && http://notMe> <http://p> <http://o>";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query) == false);
  }

  /**
   * get me all triples with ane but not steph!!
   * @throws CorruptIndexException
   * @throws LockObtainFailedException
   * @throws IOException
   * @throws ParseException
   */
  @Test
  public void testSimpleURIPattern3()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://ane> <http://p> <http://o> .";
    final String query = " <NOT http://steph AND http://ane> <http://p> <http://o>";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }

  @Test
  public void testExtendedDocumentCentric()
  throws Exception {
    final String ntriple = "<http://s> <http://p> <http://o1> <http://o2> .";
    final String query = " <http://s> <http://p> <http://o2 || http://o5>";

    assertTrue(NTripleQueryParserTestHelper.match(ntriple, query));
  }
}
