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
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ScoreDoc;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.util.BasicSirenTestCase;
import org.sindice.siren.util.XSDDatatype;

/**
 * Tests {@link NodePrefixQuery} class.
 *
 * <p> Code taken from {@link TestPrefixQuery} and adapted for SIREn.
 */
public class TestNodePrefixQuery extends BasicSirenTestCase {

  @Override
  protected void configure() throws IOException {
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    final TupleAnalyzer tupleAnalyzer = new TupleAnalyzer(TEST_VERSION_CURRENT,
      new WhitespaceAnalyzer(TEST_VERSION_CURRENT), uriAnalyzer);
    tupleAnalyzer.registerDatatype(XSDDatatype.XSD_ANY_URI.toCharArray(), uriAnalyzer);
    this.setAnalyzer(tupleAnalyzer);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  public void testPrefixQuery() throws Exception {
    this.addDocument("</computers>");
    this.addDocument("</computers/mac>");
    this.addDocument("</computers/windows>");

    NodePrefixQuery query = new NodePrefixQuery(new Term(DEFAULT_TEST_FIELD, "/computers"));
    ScoreDoc[] hits = searcher.search(dq(query), null, 1000).scoreDocs;
    assertEquals("All documents in /computers category and below", 3, hits.length);

    query = new NodePrefixQuery(new Term(DEFAULT_TEST_FIELD, "/computers/mac"));
    hits = searcher.search(dq(query), null, 1000).scoreDocs;
    assertEquals("One in /computers/mac", 1, hits.length);

    query = new NodePrefixQuery(new Term(DEFAULT_TEST_FIELD, "/computers"));
    query.setNodeConstraint(1);
    hits = searcher.search(dq(query), null, 1000).scoreDocs;
    assertEquals("No documents in /computers category and below in node 1", 0, hits.length);

    query = new NodePrefixQuery(new Term(DEFAULT_TEST_FIELD, "/computers"));
    query.setNodeConstraint(0);
    hits = searcher.search(dq(query), null, 1000).scoreDocs;
    assertEquals("All documents in /computers category and below in node 0", 3, hits.length);
  }

}