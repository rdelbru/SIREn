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

import static org.sindice.siren.search.AbstractTestSirenScorer.NodePhraseQueryBuilder.npq;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Weight;
import org.junit.Test;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.util.BasicSirenTestCase;

public class TestNodePhraseQuery extends BasicSirenTestCase {

  @Override
  protected void configure() throws IOException {
    this.setAnalyzer(AnalyzerType.TUPLE);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  /**
   * Ensures slop of 0 works for exact matches, but not reversed
   */
  @Test
  public void testExact1() throws Exception {
    this.addDocuments("\"Renaud Delbru\" . ", "\"Renaud\" . " );

    // slop is zero by default
    Query query = npq("renaud", "delbru").getLuceneProxyQuery();
    TopDocs hits = searcher.search(query, 100);
    assertEquals("exact match", 1, hits.totalHits);

    query = npq("field", new String[] { "delbru", "renaud" }).getLuceneProxyQuery();
    hits = searcher.search(query, 100);
    assertEquals("reverse not exact", 0, hits.totalHits);
  }

  /**
   * Ensures slop of 0 works for exact matches within a longer literal, but not
   * reversed
   */
  @Test
  public void testExact2() throws Exception {
    this.addDocuments("\"word1 word2 Renaud Delbru word3 \" . ",
                      "\"Renaud word1 Delbru\" . ");

    // slop is zero by default
    Query query = npq("renaud", "delbru").getLuceneProxyQuery();
    TopDocs hits = searcher.search(query, 100);
    assertEquals("exact match", 1, hits.totalHits);

    query = npq("field", new String[] { "delbru", "renaud" }).getLuceneProxyQuery();
    hits = searcher.search(query, 100);
    assertEquals("reverse not exact", 0, hits.totalHits);
  }

  /**
   * Ensures slop of 0 works for exact matches, but not in separate cells or
   * tuples
   */
  @Test
  public void testExact3() throws Exception {
    this.addDocuments("\"word1 word2\" \"Renaud Delbru word3 \" . ",
                      "\"Renaud\" \"Delbru\" . ",
                      "\"Renaud\" . \"Delbru\" . ");

    // slop is zero by default
    final Query query = npq("renaud", "delbru").getLuceneProxyQuery();
    final TopDocs hits = searcher.search(query, 100);
    assertEquals("exact match", 1, hits.totalHits);
  }

  /**
   * Ensures slop of 0 works for exact matches, but not in separate cells or
   * tuples
   * <br>
   * Same test with no norms in order to check [SRN-44].
   */
  @Test
  public void testExact3WithNoNorms() throws Exception {
    this.addDocuments("\"word1 word2\" \"Renaud Delbru word3 \" . ",
                      "\"Renaud\" \"Delbru\" . ",
                      "\"Renaud\" . \"Delbru\" . ");

    // slop is zero by default
    final Query query = npq("renaud", "delbru").getLuceneProxyQuery();
    final TopDocs hits = searcher.search(query, 100);
    assertEquals("exact match", 1, hits.totalHits);
  }

  // TODO: To uncomment when SirenCellQuery is working
//  @Test
//  public void testPhraseQueryOnLocalname()
//  throws Exception {
//    final AnyURIAnalyzer uri = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
//    uri.setUriNormalisation(URINormalisation.LOCALNAME);
//    _helper = new QueryTestingHelper(new TupleAnalyzer(TEST_VERSION_CURRENT,
//      new StandardAnalyzer(TEST_VERSION_CURRENT), uri));
//
//    final String triple = "<http://dbpedia.org/resource/The_Kingston_Trio> " +
//                          "<http://purl.org/dc/terms/subject>  " +
//                          "<http://dbpedia.org/resource/Category:Decca_Records_artists> .";
//    _helper.addDocument(triple);
//
//    final NodePhraseQuery q1 = new NodePhraseQuery();
//    q1.add(new Term("content", "decca"));
//    q1.add(new Term("content", "records"));
//    final NodePhraseQuery q2 = new NodePhraseQuery();
//    q2.add(new Term("content", "kingston"));
//    q2.add(new Term("content", "trio"));
//
//    final SirenCellQuery cq1 = new SirenCellQuery(q1);
//    final SirenCellQuery cq2 = new SirenCellQuery(q2);
//    TupleQuery bq = new TupleQuery();
//    bq.add(cq1, Occur.MUST);
//    bq.add(cq2, Occur.MUST);
//
//    ScoreDoc[] hits = _helper.search(bq);
//    assertEquals(1, hits.length);
//  }

  @Test
  public void testExplain() throws IOException {
    this.addDocuments("\"Renaud Delbru\" . ",
                      "\"Renaud Delbru\" . \"Renaud Delbru\" . ");

    final Query query = npq("renaud", "delbru").getLuceneProxyQuery();

    final Weight w = query.createWeight(searcher);

    // Explain entity 0 : 1 match
    Explanation explanation = w.explain((AtomicReaderContext) reader.getContext(), 0);
    assertNotNull("explanation is null and it shouldn't be", explanation);

//    final Similarity sim = searcher.getSimilarity();
    // TODO: The similarity is randomly set
//     System.out.println("Explanation: " + explanation.toString());
//    //All this Explain does is return the term frequency
//    assertEquals("term frq is not 1",
//      sim.tf(1), explanation.getDetails()[1].getDetails()[0].getValue(), 0.01);

    // Explain entity 1 : 2 match
    explanation = w.explain((AtomicReaderContext) reader.getContext(), 1);
    assertNotNull("explanation is null and it shouldn't be", explanation);
    // TODO: The similarity is randomly set
//     System.out.println("Explanation: " + explanation.toString());
//    //All this Explain does is return the term frequency
//    assertEquals("term frq is not 2",
//      sim.tf(2), explanation.getDetails()[1].getDetails()[0].getValue(), 0f);

    // Explain non existing entity
    explanation = w.explain((AtomicReaderContext) reader.getContext(), 2);
    assertNotNull("explanation is null and it shouldn't be", explanation);
//    System.out.println("Explanation: " + explanation.toString());
    //All this Explain does is return the term frequency
    assertEquals("term frq is not 0", 0f, explanation.getValue(), 0f);
  }

}
