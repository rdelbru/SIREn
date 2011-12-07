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
package org.sindice.siren.qparser.ntriple.query.builder;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.QueryParserHelper;
import org.apache.lucene.queryParser.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryParser.standard.StandardQueryParser;
import org.apache.lucene.queryParser.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute.Operator;
import org.apache.lucene.queryParser.standard.parser.StandardSyntaxParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.LuceneTestCase;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.analysis.filter.ASCIIFoldingExpansionFilter;
import org.sindice.siren.analysis.filter.SirenPayloadFilter;
import org.sindice.siren.qparser.ntriple.query.builders.ResourceQueryTreeBuilder;
import org.sindice.siren.qparser.tuple.ResourceQueryParser;
import org.sindice.siren.search.SirenBooleanQuery;
import org.sindice.siren.search.SirenPhraseQuery;
import org.sindice.siren.search.SirenTermQuery;

/**
 * This test case is a copy of the core Solr query parser helper test, it was adapted
 * to test the use of the QueryTreeBuilder for SIREn.
 */
public class StandardQueryTreeBuilderTest {

  private final String DEFAULT_CONTENT_FIELD = "content";
  private final Version matchVersion = LuceneTestCase.TEST_VERSION_CURRENT;
  
  private RAMDirectory        dir = null;
  private IndexWriter         writer = null;
  private Analyzer            analyser = null;
  private StandardQueryParser qph = null;
  private QueryTreeBuilder    qBuilder = null;
  private final Analyzer      simpleAnalyser = new SimpleAnalyzer(matchVersion);

  @Before
  public void setUp()
  throws Exception {
    qBuilder = new ResourceQueryTreeBuilder();
    final IndexWriterConfig config = new IndexWriterConfig(matchVersion,
      new StandardAnalyzer(matchVersion));

    dir = new RAMDirectory();
    writer = new IndexWriter(dir, config);
    analyser = new Analyzer() {

      @Override
      public final TokenStream tokenStream(final String fieldName, final Reader reader) {
        TokenStream ts = writer.getAnalyzer().tokenStream(fieldName, reader);
        ts = new SirenPayloadFilter(ts);
        return ts;
      }
    };

    qph = new ResourceQueryParser(simpleAnalyser);
    qph.setDefaultOperator(Operator.OR);
  }

  @After
  public void tearDown()
  throws Exception {
    analyser.close();
    writer.close();
  }

  private ScoreDoc[] search(final Query q, final int n) throws IOException {
    final IndexReader reader = writer.getReader();
    final IndexSearcher searcher = new IndexSearcher(reader);
    try {
      return searcher.search(q, null, n).scoreDocs;
    }
    finally {
      searcher.close();
      reader.close();
    }
  }

  public void assertQueryEquals(final String query, final Analyzer analyser, final String expected) throws QueryNodeException {
    if (analyser == null)
      qph.setAnalyzer(simpleAnalyser);
    else
      qph.setAnalyzer(analyser);
    assertEquals(expected, qph.parse(query, "field").toString("field"));
  }

  @Test
  public void testQuerySyntax() throws QueryNodeException {

    this.assertQueryEquals("term term term", null, "term term term");
    this.assertQueryEquals("t�rm term term", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT), "t�rm term term");
    this.assertQueryEquals("�mlaut", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT), "�mlaut");

    this.assertQueryEquals("\"\"", new KeywordAnalyzer(), "");
    this.assertQueryEquals("foo:\"\"", new KeywordAnalyzer(), "foo:");

    this.assertQueryEquals("a AND b", null, "+a +b");
    this.assertQueryEquals("(a AND b)", null, "+a +b");

    this.assertQueryEquals("a AND NOT b", null, "+a -b");

    this.assertQueryEquals("a AND -b", null, "+a -b");

    this.assertQueryEquals("a AND !b", null, "+a -b");

    this.assertQueryEquals("a && b", null, "+a +b");

    this.assertQueryEquals("a && ! b", null, "+a -b");

    this.assertQueryEquals("a OR b", null, "a b");
    this.assertQueryEquals("a || b", null, "a b");

    this.assertQueryEquals("a OR !b", null, "a -b");

    this.assertQueryEquals("a OR ! b", null, "a -b");

    this.assertQueryEquals("a OR -b", null, "a -b");

    this.assertQueryEquals("+term -term term", null, "+term -term term");
    this.assertQueryEquals("foo:term AND field:anotherTerm", null, "+foo:term +anotherterm");
    this.assertQueryEquals("term AND \"phrase phrase\"", null, "+term +\"phrase phrase\"");
    this.assertQueryEquals("\"hello there\"", null, "\"hello there\"");
  }

  @Test
  public void testNumber() throws Exception {
    // The numbers go away because SimpleAnalyzer ignores them
    this.assertQueryEquals("3", null, ""); // test empty query
    this.assertQueryEquals("term 1.0 1 2", null, "term");
    this.assertQueryEquals("term term1 term2", null, "term term term");
  }

  @Test
  public void testBoost() throws Exception {
    this.assertQueryEquals("term^1.0", null, "term");
  }

  @Test
  public void testEscaped() throws QueryNodeException {
    final Analyzer a = new WhitespaceAnalyzer(matchVersion);

    this.assertQueryEquals("\\*", a, "*");

    this.assertQueryEquals("\\a", a, "a");

    this.assertQueryEquals("a\\-b:c", a, "a-b:c");
    this.assertQueryEquals("a\\+b:c", a, "a+b:c");
    this.assertQueryEquals("a\\:b:c", a, "a:b:c");
    this.assertQueryEquals("a\\\\b:c", a, "a\\b:c");

    this.assertQueryEquals("a:b\\-c", a, "a:b-c");
    this.assertQueryEquals("a:b\\+c", a, "a:b+c");
    this.assertQueryEquals("a:b\\:c", a, "a:b:c");
    this.assertQueryEquals("a:b\\\\c", a, "a:b\\c");

    this.assertQueryEquals("a\\\\\\+b", a, "a\\+b");

    this.assertQueryEquals("a \\\"b c\\\" d", a, "a \"b c\" d");
    this.assertQueryEquals("\"a \\\"b c\\\" d\"", a, "\"a \"b c\" d\"");
    this.assertQueryEquals("\"a \\+b c d\"", a, "\"a +b c d\"");
  }

  @Test
  public void testQueryType() throws QueryNodeException {
    Query query = qph.parse("a:aaa AND a:bbb", "a");
    assertTrue(query instanceof SirenBooleanQuery);
    query = qph.parse("a:hello", "a");
    assertTrue(query instanceof SirenTermQuery);
    query = qph.parse("a:\"hello Future\"", "a");
    assertTrue(query instanceof SirenPhraseQuery);
  }

  @Test
  public void testSirenBooleanQuery()
  throws QueryNodeException, CorruptIndexException, IOException {
    final Document doc = new Document();
    final Document doc2 = new Document();

    doc.add(new Field(DEFAULT_CONTENT_FIELD, "aaa bbb ccc", Store.NO, Field.Index.ANALYZED_NO_NORMS));
    writer.addDocument(doc, analyser);

    doc2.add(new Field(DEFAULT_CONTENT_FIELD, "bbb ccc ddd", Store.NO, Field.Index.ANALYZED_NO_NORMS));
    writer.addDocument(doc2, analyser);

    writer.commit();
    writer.optimize();

    final QueryParserHelper qph = new QueryParserHelper(new StandardQueryConfigHandler(),
                                                        new StandardSyntaxParser(),
                                                        null,
                                                        qBuilder);

    SirenBooleanQuery query = (SirenBooleanQuery) qph.parse(DEFAULT_CONTENT_FIELD + ":aaa OR " + DEFAULT_CONTENT_FIELD + ":bbb", DEFAULT_CONTENT_FIELD);

    ScoreDoc[] results = this.search(query, 10);
    assertEquals(2, results.length);
    assertEquals(0, results[0].doc);

    query = (SirenBooleanQuery) qph.parse(DEFAULT_CONTENT_FIELD + ":aaa AND NOT " + DEFAULT_CONTENT_FIELD + ":ddd", DEFAULT_CONTENT_FIELD);

    results = this.search(query, 10);
    assertEquals(1, results.length);
    assertEquals(0, results[0].doc);
  }

  @Test
  public void testSirenTermQuery()
  throws QueryNodeException, CorruptIndexException, IOException {
    final Document doc = new Document();

    doc.add(new Field(DEFAULT_CONTENT_FIELD, "aaa bbb ccc", Store.NO, Field.Index.ANALYZED_NO_NORMS));
    writer.addDocument(doc, analyser);
    writer.commit();
    writer.optimize();

    SirenTermQuery query = (SirenTermQuery) qph.parse(DEFAULT_CONTENT_FIELD + ":bbb", DEFAULT_CONTENT_FIELD);

    ScoreDoc[] results = this.search(query, 10);
    assertEquals(1, results.length);
    assertEquals(0, results[0].doc);

    query = (SirenTermQuery) qph.parse(DEFAULT_CONTENT_FIELD + ":ddd", DEFAULT_CONTENT_FIELD);

    results = this.search(query, 10);
    assertEquals(0, results.length);
  }

  @Test
  public void testQueryTermAtSamePosition() throws QueryNodeException {
    final Analyzer analyser = new Analyzer() {

      @Override
      public final TokenStream tokenStream(final String fieldName, final Reader reader) {
        TokenStream ts = new WhitespaceAnalyzer(matchVersion).tokenStream(fieldName, reader);
        ts = new ASCIIFoldingExpansionFilter(ts);
        return ts;
      }
    };

    this.assertQueryEquals("latte +café the", analyser, "latte +(cafe café) the");
    this.assertQueryEquals("+café", analyser, "cafe café");
    this.assertQueryEquals("+café +maté", analyser, "+(cafe café) +(mate maté)");
    this.assertQueryEquals("+café -maté", analyser, "+(cafe café) -(mate maté)");
    this.assertQueryEquals("café maté", analyser, "(cafe café) (mate maté)");
  }

}

