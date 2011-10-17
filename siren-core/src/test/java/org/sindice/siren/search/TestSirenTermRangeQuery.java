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
 * @project siren-core
 * @author Renaud Delbru [ 29 Sep 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.io.Reader;
import java.text.Collator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.LuceneTestCase;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleTokenizer;
import org.sindice.siren.analysis.filter.SirenDeltaPayloadFilter;
import org.sindice.siren.analysis.filter.TokenTypeFilter;
import org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter;


public class TestSirenTermRangeQuery extends LuceneTestCase {

  private int docCount = 0;
  private Directory dir;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    dir = newDirectory();
  }

  @Override
  public void tearDown() throws Exception {
    dir.close();
    super.tearDown();
  }

  public void testExclusive() throws Exception {
    final SirenPrimitiveQuery q = new SirenTermRangeQuery("content", "A", "C", false, false);
    final SirenCellQuery query = new SirenCellQuery(q);
    query.setConstraint(2);

    this.initializeIndex(new String[] {"A", "B", "C", "D"});
    IndexSearcher searcher = new IndexSearcher(dir, true);
    ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("A,B,C,D, only B in range", 1, hits.length);
    searcher.close();

    this.initializeIndex(new String[] {"A", "B", "D"});
    searcher = new IndexSearcher(dir, true);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("A,B,D, only B in range", 1, hits.length);
    searcher.close();

    this.addDoc("C");
    searcher = new IndexSearcher(dir, true);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("C added, still only B in range", 1, hits.length);
    searcher.close();
  }

  public void testInclusive() throws Exception {
    final SirenPrimitiveQuery q = new SirenTermRangeQuery("content", "A", "C", true, true);
    final SirenCellQuery query = new SirenCellQuery(q);
    query.setConstraint(2);

    this.initializeIndex(new String[]{"A", "B", "C", "D"});
    IndexSearcher searcher = new IndexSearcher(dir, true);
    ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("A,B,C,D - A,B,C in range", 3, hits.length);
    searcher.close();

    this.initializeIndex(new String[]{"A", "B", "D"});
    searcher = new IndexSearcher(dir, true);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("A,B,D - A and B in range", 2, hits.length);
    searcher.close();

    this.addDoc("C");
    searcher = new IndexSearcher(dir, true);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("C added - A, B, C in range", 3, hits.length);
    searcher.close();
  }

  /** This test should not be here, but it tests the fuzzy query rewrite mode (TOP_TERMS_SCORING_BOOLEAN_REWRITE)
   * with constant score and checks, that only the lower end of terms is put into the range */
  public void testTopTermsRewrite() throws Exception {
    this.initializeIndex(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"});

    final IndexSearcher searcher = new IndexSearcher(dir, true);
    final SirenTermRangeQuery query = new SirenTermRangeQuery("content", "B", "J", true, true);
    this.checkBooleanTerms(searcher, query, "B", "C", "D", "E", "F", "G", "H", "I", "J");

    final int savedClauseCount = SirenBooleanQuery.getMaxClauseCount();
    try {
      SirenBooleanQuery.setMaxClauseCount(3);
      this.checkBooleanTerms(searcher, query, "B", "C", "D");
    } finally {
      SirenBooleanQuery.setMaxClauseCount(savedClauseCount);
    }
    searcher.close();
  }

  private void checkBooleanTerms(final Searcher searcher, final SirenTermRangeQuery query, final String... terms) throws IOException {
    query.setRewriteMethod(new SirenMultiTermQuery.TopTermsScoringSirenBooleanQueryRewrite(50));
    final SirenBooleanQuery bq = (SirenBooleanQuery) searcher.rewrite(query);
    final Set<String> allowedTerms = new HashSet<String>(Arrays.asList(terms));
    assertEquals(allowedTerms.size(), bq.clauses().size());
    for (final SirenBooleanClause c : bq.clauses()) {
      assertTrue(c.getQuery() instanceof SirenTermQuery);
      final SirenTermQuery tq = (SirenTermQuery) c.getQuery();
      final String term = tq.getTerm().text();
      assertTrue("invalid term: "+ term, allowedTerms.contains(term));
      allowedTerms.remove(term); // remove to fail on double terms
    }
    assertEquals(0, allowedTerms.size());
  }

  public void testEqualsHashcode() {
    Query query = new SirenTermRangeQuery("content", "A", "C", true, true);

    query.setBoost(1.0f);
    Query other = new SirenTermRangeQuery("content", "A", "C", true, true);
    other.setBoost(1.0f);

    assertEquals("query equals itself is true", query, query);
    assertEquals("equivalent queries are equal", query, other);
    assertEquals("hashcode must return same value when equals is true", query.hashCode(), other.hashCode());

    other.setBoost(2.0f);
    assertFalse("Different boost queries are not equal", query.equals(other));

    other = new SirenTermRangeQuery("notcontent", "A", "C", true, true);
    assertFalse("Different fields are not equal", query.equals(other));

    other = new SirenTermRangeQuery("content", "X", "C", true, true);
    assertFalse("Different lower terms are not equal", query.equals(other));

    other = new SirenTermRangeQuery("content", "A", "Z", true, true);
    assertFalse("Different upper terms are not equal", query.equals(other));

    query = new SirenTermRangeQuery("content", null, "C", true, true);
    other = new SirenTermRangeQuery("content", null, "C", true, true);
    assertEquals("equivalent queries with null lowerterms are equal()", query, other);
    assertEquals("hashcode must return same value when equals is true", query.hashCode(), other.hashCode());

    query = new SirenTermRangeQuery("content", "C", null, true, true);
    other = new SirenTermRangeQuery("content", "C", null, true, true);
    assertEquals("equivalent queries with null upperterms are equal()", query, other);
    assertEquals("hashcode returns same value", query.hashCode(), other.hashCode());

    query = new SirenTermRangeQuery("content", null, "C", true, true);
    other = new SirenTermRangeQuery("content", "C", null, true, true);
    assertFalse("queries with different upper and lower terms are not equal", query.equals(other));

    query = new SirenTermRangeQuery("content", "A", "C", false, false);
    other = new SirenTermRangeQuery("content", "A", "C", true, true);
    assertFalse("queries with different inclusive are not equal", query.equals(other));

    query = new SirenTermRangeQuery("content", "A", "C", false, false);
    other = new SirenTermRangeQuery("content", "A", "C", false, false, Collator.getInstance());
    assertFalse("a query with a collator is not equal to one without", query.equals(other));
  }

  public void testExclusiveCollating() throws Exception {
    final SirenPrimitiveQuery q = new SirenTermRangeQuery("content", "A", "C", false, false, Collator.getInstance(Locale.ENGLISH));
    final SirenCellQuery query = new SirenCellQuery(q);
    query.setConstraint(2);

    this.initializeIndex(new String[] {"A", "B", "C", "D"});
    IndexSearcher searcher = new IndexSearcher(dir, true);
    ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("A,B,C,D, only B in range", 1, hits.length);
    searcher.close();

    this.initializeIndex(new String[] {"A", "B", "D"});
    searcher = new IndexSearcher(dir, true);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("A,B,D, only B in range", 1, hits.length);
    searcher.close();

    this.addDoc("C");
    searcher = new IndexSearcher(dir, true);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("C added, still only B in range", 1, hits.length);
    searcher.close();
  }

  public void testInclusiveCollating() throws Exception {
    final SirenPrimitiveQuery q = new SirenTermRangeQuery("content", "A", "C",true, true, Collator.getInstance(Locale.ENGLISH));
    final SirenCellQuery query = new SirenCellQuery(q);
    query.setConstraint(2);

    this.initializeIndex(new String[]{"A", "B", "C", "D"});
    IndexSearcher searcher = new IndexSearcher(dir, true);
    ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("A,B,C,D - A,B,C in range", 3, hits.length);
    searcher.close();

    this.initializeIndex(new String[]{"A", "B", "D"});
    searcher = new IndexSearcher(dir, true);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("A,B,D - A and B in range", 2, hits.length);
    searcher.close();

    this.addDoc("C");
    searcher = new IndexSearcher(dir, true);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("C added - A, B, C in range", 3, hits.length);
    searcher.close();
  }

  private void initializeIndex(final String[] values) throws IOException {
    this.initializeIndex(values, new SimpleTupleAnalyzer(new WhitespaceAnalyzer(TEST_VERSION_CURRENT)));
  }

  private void initializeIndex(final String[] values, final Analyzer analyzer) throws IOException {
    final IndexWriter writer = new IndexWriter(dir, newIndexWriterConfig(
        TEST_VERSION_CURRENT, analyzer).setOpenMode(OpenMode.CREATE));
    for (final String value : values) {
      this.insertDoc(writer, value);
    }
    writer.close();
  }

  private void addDoc(final String content) throws IOException {
    final IndexWriter writer = new IndexWriter(dir,
      newIndexWriterConfig(TEST_VERSION_CURRENT,
        new SimpleTupleAnalyzer(new WhitespaceAnalyzer(TEST_VERSION_CURRENT))).setOpenMode(OpenMode.APPEND));
    this.insertDoc(writer, content);
    writer.close();
  }

  private void insertDoc(final IndexWriter writer, final String content) throws IOException {
    final Document doc = new Document();

    doc.add(newField("id", "id" + docCount, Field.Store.YES, Field.Index.NOT_ANALYZED));
    doc.add(newField("content", this.getTriple(content), Field.Store.NO, Field.Index.ANALYZED));

    writer.addDocument(doc);
    docCount++;
  }

  private String getTriple(final String text) {
    return "<http://fake.subject> <http://fake.predicate> \"" + text + "\" .\n";
  }

  public class SimpleTupleAnalyzer
  extends Analyzer {

    private final Analyzer literalAnalyzer;

    public SimpleTupleAnalyzer(final Analyzer literalAnalyzer) {
      this.literalAnalyzer = literalAnalyzer;
    }

    @Override
    public final TokenStream tokenStream(final String fieldName, final Reader reader) {
      final TupleTokenizer stream = new TupleTokenizer(reader, Integer.MAX_VALUE);
      TokenStream result = new TokenTypeFilter(stream, new int[] {TupleTokenizer.BNODE,
                                                                  TupleTokenizer.DOT});
      result = new DatatypeAnalyzerFilter(Version.LUCENE_31, result, literalAnalyzer, new AnyURIAnalyzer(Version.LUCENE_34));
      result = new SirenDeltaPayloadFilter(result);
      return result;
    }

    @Override
    public final TokenStream reusableTokenStream(final String fieldName, final Reader reader) throws IOException {
      SavedStreams streams = (SavedStreams) this.getPreviousTokenStream();
      if (streams == null) {
        streams = new SavedStreams();
        this.setPreviousTokenStream(streams);
        streams.tokenStream = new TupleTokenizer(reader, Integer.MAX_VALUE);
        streams.filteredTokenStream = new TokenTypeFilter(streams.tokenStream,
          new int[] {TupleTokenizer.BNODE, TupleTokenizer.DOT});
        streams.filteredTokenStream = new DatatypeAnalyzerFilter(Version.LUCENE_31, streams.filteredTokenStream, literalAnalyzer, new AnyURIAnalyzer(Version.LUCENE_34));
        streams.filteredTokenStream = new SirenDeltaPayloadFilter(streams.filteredTokenStream);
      } else {
        streams.tokenStream.reset(reader);
      }
      return streams.filteredTokenStream;
    }

    private final class SavedStreams {
      TupleTokenizer tokenStream;
      TokenStream filteredTokenStream;
    }

  }

}