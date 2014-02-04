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

import static org.sindice.siren.search.AbstractTestSirenScorer.NodeTermQueryBuilder.ntq;

import java.io.IOException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Weight;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.util.BasicSirenTestCase;
import org.sindice.siren.util.XSDDatatype;

public class TestNodeTermQuery extends BasicSirenTestCase {

  @Override
  protected void configure() throws IOException {
    this.setAnalyzer(AnalyzerType.TUPLE);
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    ((TupleAnalyzer) analyzer).registerDatatype(XSDDatatype.XSD_ANY_URI.toCharArray(), uriAnalyzer);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  /**
   * Ensures simple term queries match all the documents
   */
  @Test
  public void testSimpleMatch() throws Exception {
    this.addDocument("\"Renaud Delbru\" . ");
    this.addDocument("\"Renaud\" . ");

    Query query = ntq("renaud").getLuceneProxyQuery();
    TopDocs hits = searcher.search(query, 100);
    assertEquals(2, hits.totalHits);

    query = ntq("delbru").getLuceneProxyQuery();
    hits = searcher.search(query, 100);
    assertEquals(1, hits.totalHits);
  }

  @Test
  public void testSimpleMatchWithConstraint() throws Exception {
    this.addDocument("\"Renaud Delbru\" . ");
    this.addDocument("\"Delbru\" \"Renaud\" . ");
    this.addDocument("\"Delbru\" . \"Renaud\" . ");

    Query query = ntq("renaud").level(1).getLuceneProxyQuery();
    TopDocs hits = searcher.search(query, 100);
    assertEquals(0, hits.totalHits);

    query = ntq("renaud").bound(0,0).getLuceneProxyQuery();
    hits = searcher.search(query, 100);
    assertEquals(2, hits.totalHits);
  }

  /**
   * Ensures simple term queries match all the documents
   * <br>
   * Test with no norms [SRN-44]
   */
  @Test
  public void testSimpleMatchWithNoNorms() throws Exception {
    this.addDocumentNoNorms("\"Renaud Delbru\" . ");
    this.addDocumentNoNorms("\"Renaud\" . ");

    Query query = ntq("renaud").getLuceneProxyQuery();
    TopDocs hits = searcher.search(query, 100);
    assertEquals(2, hits.totalHits);

    query = ntq("delbru").getLuceneProxyQuery();
    hits = searcher.search(query, 100);
    assertEquals(1, hits.totalHits);
  }

  /**
   * Ensures simple term queries does not match
   */
  @Test
  public void testSimpleDontMatch() throws Exception {
    this.addDocument("\"Renaud Delbru\" . ");

    final Query query = ntq("nomatch").getLuceneProxyQuery();
    final TopDocs hits = searcher.search(query, 100);
    assertEquals(0, hits.totalHits);
  }

  @Test
  public void testExplain() throws IOException {
    this.addDocumentNoNorms("<http://renaud.delbru.fr/rdf/foaf#me> <http://xmlns.com/foaf/0.1/name> \"Renaud Delbru\" . ");

    final Query query = ntq("renaud").getLuceneProxyQuery();
    final Weight w = searcher.createNormalizedWeight(query);
    assertTrue(searcher.getTopReaderContext() instanceof AtomicReaderContext);
    final AtomicReaderContext context = (AtomicReaderContext) searcher.getTopReaderContext();

    // Explain entity 0
    Explanation explanation = w.explain(context, 0);
    assertNotNull("explanation is null and it shouldn't be", explanation);

    // TODO: the similarity is random
    // All this Explain does is return the term frequency
//    final float termFreq = explanation.getDetails()[0].getDetails()[0].getValue();
//    assertEquals("term frq is not 2", 2f, termFreq, 0f);

    // Explain non existing entity
    explanation = w.explain(context, 1);
    assertNotNull("explanation is null and it shouldn't be", explanation);
    //All this Explain does is return the term frequency
    assertEquals("term frq is not 0", 0f, explanation.getValue(), 0f);
  }

}
