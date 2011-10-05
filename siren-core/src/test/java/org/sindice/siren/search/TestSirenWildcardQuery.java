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
 * @author Renaud Delbru [ 28 Sep 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.RandomIndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.LuceneTestCase;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;

/**
 * TestSirenWildcardQuery tests the '*' and '?' wildcard characters.
 */
public class TestSirenWildcardQuery extends LuceneTestCase {

  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  public void testEquals() {
    final SirenWildcardQuery wq1 = new SirenWildcardQuery(new Term("field", "b*a"));
    final SirenWildcardQuery wq2 = new SirenWildcardQuery(new Term("field", "b*a"));
    final SirenWildcardQuery wq3 = new SirenWildcardQuery(new Term("field", "b*a"));

    // reflexive?
    assertEquals(wq1, wq2);
    assertEquals(wq2, wq1);

    // transitive?
    assertEquals(wq2, wq3);
    assertEquals(wq1, wq3);

    assertFalse(wq1.equals(null));

    final SirenFuzzyQuery fq = new SirenFuzzyQuery(new Term("field", "b*a"));
    assertFalse(wq1.equals(fq));
    assertFalse(fq.equals(wq1));
  }

  /**
   * Tests if the ConstantScore filter rewrite return an exception
   */
  @Test(expected=UnsupportedOperationException.class)
  public void testFilterRewrite() throws IOException {
      final Directory indexStore = this.getIndexStore("field", new String[]{"nowildcard", "nowildcardx"});
      final IndexSearcher searcher = new IndexSearcher(indexStore, true);

      final SirenMultiTermQuery wq = new SirenWildcardQuery(new Term("field", "nowildcard"));
      this.assertMatches(searcher, wq, 1);

      try {
        wq.setRewriteMethod(SirenMultiTermQuery.CONSTANT_SCORE_FILTER_REWRITE);
        wq.setBoost(0.2F);
        final Query q = searcher.rewrite(wq);
        assertTrue(q instanceof SirenConstantScoreQuery);
        assertEquals(q.getBoost(), wq.getBoost());
      }
      finally {
        searcher.close();
        indexStore.close();
      }
  }

  /**
   * Tests if a WildcardQuery that has no wildcard in the term is rewritten to a single
   * TermQuery. The boost should be preserved, and the rewrite should return
   * a ConstantScoreQuery if the WildcardQuery had a ConstantScore rewriteMethod.
   */
  public void testTermWithoutWildcard2() throws IOException {
      final Directory indexStore = this.getIndexStore("field", new String[]{"nowildcard", "nowildcardx"});
      final IndexSearcher searcher = new IndexSearcher(indexStore, true);

      final MultiTermQuery wq = new WildcardQuery(new Term("field", "nowildcard"));
      this.assertMatches(searcher, wq, 1);

      wq.setRewriteMethod(MultiTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
      wq.setBoost(0.1F);
      final Query q = searcher.rewrite(wq);
      assertTrue(q instanceof TermQuery);
      assertEquals(q.getBoost(), wq.getBoost());

      searcher.close();
      indexStore.close();
  }

  /**
   * Tests if a SirenWildcardQuery that has no wildcard in the term is rewritten to a single
   * TermQuery. The boost should be preserved, and the rewrite should return
   * a SirenConstantScoreQuery if the SirenWildcardQuery had a ConstantScore rewriteMethod.
   */
  public void testTermWithoutWildcard() throws IOException {
      final Directory indexStore = this.getIndexStore("field", new String[]{"nowildcard", "nowildcardx"});
      final IndexSearcher searcher = new IndexSearcher(indexStore, true);

      final SirenMultiTermQuery wq = new SirenWildcardQuery(new Term("field", "nowildcard"));
      this.assertMatches(searcher, wq, 1);

      wq.setRewriteMethod(SirenMultiTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
      wq.setBoost(0.1F);
      Query q = searcher.rewrite(wq);
      assertTrue(q instanceof SirenTermQuery);
      assertEquals(q.getBoost(), wq.getBoost());

      wq.setRewriteMethod(SirenMultiTermQuery.CONSTANT_SCORE_AUTO_REWRITE_DEFAULT);
      wq.setBoost(0.3F);
      q = searcher.rewrite(wq);
      assertTrue(q instanceof SirenConstantScoreQuery);
      assertEquals(q.getBoost(), wq.getBoost());

      wq.setRewriteMethod(SirenMultiTermQuery.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE);
      wq.setBoost(0.4F);
      q = searcher.rewrite(wq);
      assertTrue(q instanceof SirenConstantScoreQuery);
      assertEquals(q.getBoost(), wq.getBoost());

      searcher.close();
      indexStore.close();
  }

  /**
   * Tests if a SirenWildcardQuery with an empty term is rewritten to an empty
   * SirenBooleanQuery
   */
  public void testEmptyTerm() throws IOException {
    final Directory indexStore = this.getIndexStore("field", new String[]{"nowildcard", "nowildcardx"});
    final IndexSearcher searcher = new IndexSearcher(indexStore, true);

    final SirenMultiTermQuery wq = new SirenWildcardQuery(new Term("field", ""));
    wq.setRewriteMethod(SirenMultiTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
    this.assertMatches(searcher, wq, 0);
    final Query q = searcher.rewrite(wq);
    assertTrue(q instanceof SirenBooleanQuery);
    assertEquals(0, ((SirenBooleanQuery) q).clauses().size());

    searcher.close();
    indexStore.close();
  }

  /**
   * Tests if a SirenWildcardQuery that has only a trailing * in the term is
   * rewritten to a single SirenPrefixQuery. The boost and rewriteMethod should be
   * preserved.
   */
  public void testPrefixTerm() throws IOException {
    final Directory indexStore = this.getIndexStore("field", new String[]{"prefix", "prefixx"});
    final IndexSearcher searcher = new IndexSearcher(indexStore, true);

    final SirenMultiTermQuery wq = new SirenWildcardQuery(new Term("field", "prefix*"));
    this.assertMatches(searcher, wq, 2);

    final SirenMultiTermQuery expected = new SirenPrefixQuery(new Term("field", "prefix"));
    wq.setRewriteMethod(SirenMultiTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
    wq.setBoost(0.1F);
    expected.setRewriteMethod(wq.getRewriteMethod());
    expected.setBoost(wq.getBoost());
    assertEquals(searcher.rewrite(expected), searcher.rewrite(wq));

    wq.setRewriteMethod(SirenMultiTermQuery.CONSTANT_SCORE_AUTO_REWRITE_DEFAULT);
    wq.setBoost(0.3F);
    expected.setRewriteMethod(wq.getRewriteMethod());
    expected.setBoost(wq.getBoost());
    assertEquals(searcher.rewrite(expected), searcher.rewrite(wq));

    wq.setRewriteMethod(SirenMultiTermQuery.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE);
    wq.setBoost(0.4F);
    expected.setRewriteMethod(wq.getRewriteMethod());
    expected.setBoost(wq.getBoost());
    assertEquals(searcher.rewrite(expected), searcher.rewrite(wq));

    searcher.close();
    indexStore.close();
  }

  /**
   * Tests Wildcard queries with an asterisk.
   */
  public void testAsterisk()
      throws IOException {
    final Directory indexStore = this.getIndexStore("body", new String[]
    {"metal", "metals"});
    final IndexSearcher searcher = new IndexSearcher(indexStore, true);

    final SirenPrimitiveQuery query1 = new SirenTermQuery(new Term("body", "metal"));
    final SirenPrimitiveQuery query2 = new SirenWildcardQuery(new Term("body", "metal*"));
    final SirenPrimitiveQuery query3 = new SirenWildcardQuery(new Term("body", "m*tal"));
    final SirenPrimitiveQuery query4 = new SirenWildcardQuery(new Term("body", "m*tal*"));
    final SirenPrimitiveQuery query5 = new SirenWildcardQuery(new Term("body", "m*tals"));

    final SirenBooleanQuery query6 = new SirenBooleanQuery();
    query6.add(query5, SirenBooleanClause.Occur.SHOULD);

    final SirenBooleanQuery query7 = new SirenBooleanQuery();
    query7.add(query3, SirenBooleanClause.Occur.SHOULD);
    query7.add(query5, SirenBooleanClause.Occur.SHOULD);

    // Queries do not automatically lower-case search terms:
    final SirenPrimitiveQuery query8 = new SirenWildcardQuery(new Term("body", "M*tal*"));

    // Cell query
    final SirenCellQuery cq1 = new SirenCellQuery(query7);
    cq1.setConstraint(0);

    final SirenCellQuery cq2 = new SirenCellQuery(query7);
    cq2.setConstraint(2);

    this.assertMatches(searcher, query1, 1);
    this.assertMatches(searcher, query2, 2);
    this.assertMatches(searcher, query3, 1);
    this.assertMatches(searcher, query4, 2);
    this.assertMatches(searcher, query5, 1);
    this.assertMatches(searcher, query6, 1);
    this.assertMatches(searcher, query7, 2);
    this.assertMatches(searcher, query8, 0);
    this.assertMatches(searcher, new SirenWildcardQuery(new Term("body", "*tall")), 0);
    this.assertMatches(searcher, new SirenWildcardQuery(new Term("body", "*tal")), 1);
    this.assertMatches(searcher, new SirenWildcardQuery(new Term("body", "*tal*")), 2);
    this.assertMatches(searcher, cq1, 0);
    this.assertMatches(searcher, cq2, 2);

    searcher.close();
    indexStore.close();
  }

  /**
   * LUCENE-2620
   */
  public void testLotsOfAsterisks()
      throws IOException {
    final Directory indexStore = this.getIndexStore("body",
      new String[]{"metal", "metals"});
    final IndexSearcher searcher = new IndexSearcher(indexStore, true);

    final StringBuilder term = new StringBuilder();
    term.append("m");
    for (int i = 0; i < 512; i++) {
      term.append("*");
    }
    term.append("tal");

    final SirenPrimitiveQuery query3 = new SirenWildcardQuery(new Term("body", term.toString()));

    this.assertMatches(searcher, query3, 1);

    searcher.close();
    indexStore.close();
  }

  /**
   * Tests Wildcard queries with a question mark.
   *
   * @throws IOException if an error occurs
   */
  public void testQuestionmark()
  throws IOException {
    final Directory indexStore = this.getIndexStore("body",
      new String[]{"metal", "metals", "mXtals", "mXtXls"});
    final IndexSearcher searcher = new IndexSearcher(indexStore, true);
    final SirenPrimitiveQuery query1 = new SirenWildcardQuery(new Term("body", "m?tal"));
    final SirenPrimitiveQuery query2 = new SirenWildcardQuery(new Term("body", "metal?"));
    final SirenPrimitiveQuery query3 = new SirenWildcardQuery(new Term("body", "metals?"));
    final SirenPrimitiveQuery query4 = new SirenWildcardQuery(new Term("body", "m?t?ls"));
    final SirenPrimitiveQuery query5 = new SirenWildcardQuery(new Term("body", "M?t?ls"));
    final SirenPrimitiveQuery query6 = new SirenWildcardQuery(new Term("body", "meta??"));

    this.assertMatches(searcher, query1, 1);
    this.assertMatches(searcher, query2, 1);
    this.assertMatches(searcher, query3, 0);
    this.assertMatches(searcher, query4, 3);
    this.assertMatches(searcher, query5, 0);
    this.assertMatches(searcher, query6, 1); // Query: 'meta??' matches 'metals' not 'metal'

    searcher.close();
    indexStore.close();
  }

  private Directory getIndexStore(final String field, final String[] contents)
  throws IOException {
    final Directory indexStore = newDirectory();
    final RandomIndexWriter writer = new RandomIndexWriter(random, indexStore,
      new TupleAnalyzer(Version.LUCENE_31, new WhitespaceAnalyzer(Version.LUCENE_31), new AnyURIAnalyzer()));
    for (int i = 0; i < contents.length; ++i) {
      final Document doc = new Document();
      doc.add(newField(field, this.getTriple(contents[i]), Field.Store.YES, Field.Index.ANALYZED));
      writer.addDocument(doc);
    }
    writer.close();

    return indexStore;
  }

  private String getTriple(final String text) {
    return "<http://fake.subject> <http://fake.predicate> \"" + text + "\" .\n";
  }

  private void assertMatches(final IndexSearcher searcher, final Query q, final int expectedMatches)
  throws IOException {
    final ScoreDoc[] result = searcher.search(q, null, 1000).scoreDocs;
    assertEquals(expectedMatches, result.length);
  }

}