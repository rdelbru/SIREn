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

package org.sindice.siren.search.node;

import static org.sindice.siren.search.AbstractTestSirenScorer.dq;

import java.io.IOException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixTermsEnum;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.util.BasicSirenTestCase;
import org.sindice.siren.util.XSDDatatype;

/**
 * TestSirenWildcardQuery tests the '*' and '?' wildcard characters.
 */
public class TestNodeWildcardQuery extends BasicSirenTestCase {

  @Override
  protected void configure() throws IOException {
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    final TupleAnalyzer tupleAnalyzer = new TupleAnalyzer(TEST_VERSION_CURRENT,
      new WhitespaceAnalyzer(TEST_VERSION_CURRENT), uriAnalyzer);
    tupleAnalyzer.registerDatatype(XSDDatatype.XSD_ANY_URI.toCharArray(), uriAnalyzer);
    this.setAnalyzer(tupleAnalyzer);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  public void testEquals() {
    final NodeWildcardQuery wq1 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "b*a"));
    final NodeWildcardQuery wq2 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "b*a"));
    final NodeWildcardQuery wq3 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "b*a"));

    // reflexive?
    assertEquals(wq1, wq2);
    assertEquals(wq2, wq1);

    // transitive?
    assertEquals(wq2, wq3);
    assertEquals(wq1, wq3);

    assertFalse(wq1.equals(null));

    final NodeFuzzyQuery fq = new NodeFuzzyQuery(new Term(DEFAULT_TEST_FIELD, "b*a"));
    assertFalse(wq1.equals(fq));
    assertFalse(fq.equals(wq1));
  }

  /**
   * Tests if the ConstantScore filter rewrite return an exception
   */
  @Test(expected=UnsupportedOperationException.class)
  public void testFilterRewrite() throws IOException {
    this.addDocument("<nowildcard> <nowildcardx>");

    final MultiNodeTermQuery wq = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "nowildcard"));
    this.assertMatches(searcher, wq, 1);

    wq.setRewriteMethod(MultiNodeTermQuery.CONSTANT_SCORE_FILTER_REWRITE);
    wq.setBoost(0.2F);
    searcher.rewrite(wq);
  }

  /**
   * Tests if a SirenWildcardQuery that has no wildcard in the term is rewritten to a single
   * TermQuery. The boost should be preserved, and the rewrite should return
   * a SirenConstantScoreQuery if the SirenWildcardQuery had a ConstantScore rewriteMethod.
   */
  public void testTermWithoutWildcard() throws IOException {
    this.addDocument("<nowildcard> <nowildcardx>");
    final MultiNodeTermQuery wq = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "nowildcard"));
    this.assertMatches(searcher, wq, 1);

    wq.setRewriteMethod(MultiNodeTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
    wq.setBoost(0.1F);
    Query q = searcher.rewrite(wq);
    assertTrue(q instanceof NodeTermQuery);
    assertEquals(q.getBoost(), wq.getBoost(), 0);

    wq.setRewriteMethod(MultiNodeTermQuery.CONSTANT_SCORE_AUTO_REWRITE_DEFAULT);
    wq.setBoost(0.3F);
    q = searcher.rewrite(wq);
    assertTrue(q instanceof NodeConstantScoreQuery);
    assertEquals(q.getBoost(), wq.getBoost(), 0.1);

    wq.setRewriteMethod(MultiNodeTermQuery.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE);
    wq.setBoost(0.4F);
    q = searcher.rewrite(wq);
    assertTrue(q instanceof NodeConstantScoreQuery);
    assertEquals(q.getBoost(), wq.getBoost(), 0.1);
  }

  /**
   * Tests if a SirenWildcardQuery with an empty term is rewritten to an empty
   * SirenBooleanQuery
   */
  public void testEmptyTerm() throws IOException {
    this.addDocument("<nowildcard> <nowildcardx>");

    final MultiNodeTermQuery wq = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, ""));
    wq.setRewriteMethod(MultiNodeTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
    this.assertMatches(searcher, wq, 0);
    final Query q = searcher.rewrite(wq);
    assertTrue(q instanceof NodeBooleanQuery);
    assertEquals(0, ((NodeBooleanQuery) q).clauses().size());
  }

  /**
   * Tests if a SirenWildcardQuery that has only a trailing * in the term is
   * rewritten to a single SirenPrefixQuery. The boost and rewriteMethod should be
   * preserved.
   */
  public void testPrefixTerm() throws IOException {
    this.addDocuments("<prefix>", "<prefixx>");

    MultiNodeTermQuery wq = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "prefix*"));
    this.assertMatches(searcher, wq, 2);
    final Terms terms = MultiFields.getTerms(searcher.getIndexReader(), DEFAULT_TEST_FIELD);
    assertTrue(wq.getTermsEnum(terms) instanceof PrefixTermsEnum);

    final MultiNodeTermQuery expected = new NodePrefixQuery(new Term(DEFAULT_TEST_FIELD, "prefix"));
    wq.setRewriteMethod(MultiNodeTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
    wq.setBoost(0.1F);
    expected.setRewriteMethod(wq.getRewriteMethod());
    expected.setBoost(wq.getBoost());
    assertEquals(searcher.rewrite(expected), searcher.rewrite(wq));

    wq.setRewriteMethod(MultiNodeTermQuery.CONSTANT_SCORE_AUTO_REWRITE_DEFAULT);
    wq.setBoost(0.3F);
    expected.setRewriteMethod(wq.getRewriteMethod());
    expected.setBoost(wq.getBoost());
    assertEquals(searcher.rewrite(expected), searcher.rewrite(wq));

    wq.setRewriteMethod(MultiNodeTermQuery.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE);
    wq.setBoost(0.4F);
    expected.setRewriteMethod(wq.getRewriteMethod());
    expected.setBoost(wq.getBoost());
    assertEquals(searcher.rewrite(expected), searcher.rewrite(wq));

    wq = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "*"));
    this.assertMatches(searcher, wq, 2);
    assertFalse(wq.getTermsEnum(terms) instanceof PrefixTermsEnum);
    assertFalse(wq.getTermsEnum(terms).getClass().getSimpleName().contains("AutomatonTermsEnum"));
  }

  /**
   * Tests Wildcard queries with an asterisk.
   */
  public void testAsterisk() throws IOException {
    this.addDocuments("<metal>", "<metals>");

    final NodePrimitiveQuery query1 = new NodeTermQuery(new Term(DEFAULT_TEST_FIELD, "metal"));
    final NodePrimitiveQuery query2 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "metal*"));
    final NodePrimitiveQuery query3 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "m*tal"));
    final NodePrimitiveQuery query4 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "m*tal*"));
    final NodePrimitiveQuery query5 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "m*tals"));

    final NodeBooleanQuery query6 = new NodeBooleanQuery();
    query6.add(query5, NodeBooleanClause.Occur.SHOULD);

    final NodeBooleanQuery query7 = new NodeBooleanQuery();
    query7.add(query3, NodeBooleanClause.Occur.SHOULD);
    query7.add(query5, NodeBooleanClause.Occur.SHOULD);

    // Queries do not automatically lower-case search terms:
    final NodePrimitiveQuery query8 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "M*tal*"));

    this.assertMatches(searcher, query1, 1);
    this.assertMatches(searcher, query2, 2);
    this.assertMatches(searcher, query3, 1);
    this.assertMatches(searcher, query4, 2);
    this.assertMatches(searcher, query5, 1);
    this.assertMatches(searcher, query6, 1);
    this.assertMatches(searcher, query7, 2);
    this.assertMatches(searcher, query8, 0);
    this.assertMatches(searcher, new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "*tall")), 0);
    this.assertMatches(searcher, new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "*tal")), 1);
    this.assertMatches(searcher, new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "*tal*")), 2);
  }

  /**
   * Tests Wildcard queries with a question mark.
   *
   * @throws IOException if an error occurs
   */
  public void testQuestionmark() throws IOException {
    this.addDocuments("<metal>", "<metals>", "<mXtals>", "<mXtXls>");

    final NodePrimitiveQuery query1 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "m?tal"));
    final NodePrimitiveQuery query2 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "metal?"));
    final NodePrimitiveQuery query3 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "metals?"));
    final NodePrimitiveQuery query4 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "m?t?ls"));
    final NodePrimitiveQuery query5 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "M?t?ls"));
    final NodePrimitiveQuery query6 = new NodeWildcardQuery(new Term(DEFAULT_TEST_FIELD, "meta??"));

    this.assertMatches(searcher, query1, 1);
    this.assertMatches(searcher, query2, 1);
    this.assertMatches(searcher, query3, 0);
    this.assertMatches(searcher, query4, 3);
    this.assertMatches(searcher, query5, 0);
    this.assertMatches(searcher, query6, 1); // Query: 'meta??' matches 'metals' not 'metal'
  }

  private void assertMatches(final IndexSearcher searcher, final NodeQuery q, final int expectedMatches)
  throws IOException {
    final ScoreDoc[] result = searcher.search(dq(q), null, 1000).scoreDocs;
    assertEquals(expectedMatches, result.length);
  }

}