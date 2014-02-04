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

import static org.sindice.siren.analysis.MockSirenDocument.doc;
import static org.sindice.siren.analysis.MockSirenToken.node;
import static org.sindice.siren.analysis.MockSirenToken.token;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.must;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.not;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.should;
import static org.sindice.siren.search.AbstractTestSirenScorer.NodeBooleanQueryBuilder.nbq;
import static org.sindice.siren.search.AbstractTestSirenScorer.TwigChildBuilder.child;
import static org.sindice.siren.search.AbstractTestSirenScorer.TwigDescendantBuilder.desc;
import static org.sindice.siren.search.AbstractTestSirenScorer.TwigQueryBuilder.twq;

import java.io.IOException;
import java.util.Collection;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer.ChildScorer;
import org.junit.Test;
import org.sindice.siren.index.DocsAndNodesIterator;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.search.AbstractTestSirenScorer;

public class TestTwigScorer extends AbstractTestSirenScorer {

  @Override
  protected void configure() throws IOException {
    this.setAnalyzer(AnalyzerType.MOCK);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  @Test(expected=UnsupportedOperationException.class)
  public void testEmptyRootScorer()
  throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1)), token("req", node(1)),
        token("bbb", node(1,0)), token("ccc", node(1,0)))
    );

    NodeScorer scorer = this.getScorer(
      twq(1)
    );

    scorer.nextCandidateDocument();
  }

  @Test
  public void testNonMatchingRootQuery()
  throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1)), token("req", node(1)),
        token("bbb", node(1,0)), token("ccc", node(1,0))),
      doc(token("ccc", node(1)), token("req", node(1)), token("bbb", node(1,0)))
    );

    // the term does not occur
    NodeScorer scorer = this.getScorer(
      twq(1, should("eee")).with(child(must("bbb")))
    );
    assertTrue(scorer == null);

    // the boolean condition is not met
    scorer = this.getScorer(
      twq(1, must(nbq(must("req"), not("aaa"), not("ccc")))).with(child(must("bbb")))
    );
    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    // test for the EmptyRootScorer
    scorer = this.getScorer(
      twq(1).with(child(must("ccc"))).with(child(should("bbb")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());
  
    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());
  
    assertEndOfStream(scorer);
  }

  @Test
  public void testSingleChild() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0))),
      doc(token("aaa", node(5,3,6,3)), token("bbb", node(5,3,6,3,7)))
    );

    NodeScorer scorer = this.getScorer(
      twq(1, must("aaa")).with(child(must("bbb")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(2, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must("bbb")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(2, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(4, must("aaa")).with(child(must("bbb")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(2, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(5,3,6,3), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

  }

  @Test
  public void testEmptyRoot() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0))),
      doc(token("aaa", node(5,3,6,3)), token("bbb", node(5,3,6,3,7)))
    );

    NodeScorer scorer = this.getScorer(
      twq(1).with(child(must("bbb")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(2, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2).with(child(must("bbb")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(2, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  @Test
  public void testSingleDescendant() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,1,2,3)))
    );

    final NodeScorer scorer = this.getScorer(
      twq(2, must("aaa")).with(desc(2, must("bbb")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(2, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

  }

  @Test
  public void testChildDescendant() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0)), token("ccc", node(1,0,1,0))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0)), token("ccc", node(1,0,4)))
    );

    final NodeScorer scorer = this.getScorer(
      twq(2, must("aaa")).with(desc(2, must("bbb")))
                         .with(child(must("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

  }

  @Test
  public void testBooleanChild() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1)), token("ccc", node(1,0,4))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1)), token("ccc", node(1,0,1)))
    );

    NodeScorer scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must("bbb"), must("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    final float d1score10 = scorer.scoreInNode();
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must("bbb"), should("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    final float d0score10 = scorer.scoreInNode();
    assertTrue(d1score10 + " > " + d0score10, d1score10 > d0score10);
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must("bbb"), not("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  @Test
  public void testBooleanRoot() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1,0)), token("bbb", node(1,1)), token("ccc", node(1,0,1))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0)), token("ccc", node(1,0,1)))
    );

    NodeScorer scorer = this.getScorer(
      twq(2, must("aaa"), must("bbb")).with(child(must("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    final float d1score10 = scorer.scoreInNode();
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa"), should("bbb")).with(child(must("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    final float d0score10 = scorer.scoreInNode();
    assertTrue(d1score10 + " > " + d0score10, d1score10 > d0score10);
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa"), not("bbb")).with(child(must("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

  }

  @Test
  public void testBooleanClause() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1)), token("ccc", node(1,0,4))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1))),
      doc(token("aaa", node(1,0)))
    );

    NodeScorer scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must("bbb")))
                         .optional(child(must("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must("bbb")))
                         .without(child(must("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa")).optional(child(must("bbb")))
                         .without(child(must("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(2, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    // Test with only a prohibited clause
    scorer = this.getScorer(
      twq(2, must("aaa")).without(child(must("ccc")))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(2, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

  }

  @Test
  public void testNestedTwig() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1)), token("ccc", node(1,0,1,0)),
                                   token("eee", node(1,0,4)), token("fff", node(1,0,4,1)),
                                   token("ggg", node(1,0,4,1)), token("hhh", node(1,0,1)))
    );

    NodeScorer scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must(
                                twq(3, must("bbb")).with(child(must("ccc")))
                               )))
                         .with(child(must(
                                twq(3, must("eee")).with(child(must("fff")))
                               )))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    final float s0 = scorer.scoreInNode();
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must(
                                twq(3, must("bbb"), must("hhh")).with(child(must("ccc")))
                               )))
                         .with(child(must(
                                twq(3, must("eee")).with(child(must("fff")))
                               )))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    final float s1 = scorer.scoreInNode();
    assertTrue(s1 + " > " + s0, s1 > s0);
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must(
                                twq(3, must("bbb")).with(child(must("ccc")))
                               )))
                         .with(child(must(
                                twq(3, must("eee")).with(child(must("fff"), must("ggg")))
                               )))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    final float s2 = scorer.scoreInNode();
    assertTrue(s2 + " > " + s0, s2 > s0);
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);

    scorer = this.getScorer(
      twq(2, must("aaa")).with(child(must(
                                twq(3, must("bbb"), must("hhh")).with(child(must("ccc")))
                               )))
                         .with(child(must(
                                twq(3, must("eee")).with(child(must("fff"), must("ggg")))
                               )))
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(1,0), scorer.node());
    final float s3 = scorer.scoreInNode();
    assertTrue(s3 + " > " + s0, s3 > s1);
    assertTrue(s3 + " > " + s2, s3 > s2);
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  @Test
  public void testNodeConstraints() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(2)), token("bbb", node(2,0))),
      doc(token("aaa", node(0)), token("bbb", node(1,0))),
      doc(token("aaa", node(4)), token("bbb", node(4,0)))
    );

    final NodeScorer scorer = this.getScorer(
      twq(1, must("aaa")).with(child(must("bbb")))
                         .bound(1,3)
    );

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(0, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertTrue(scorer.nextNode());
    assertEquals(node(2), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(1, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertTrue(scorer.nextCandidateDocument());
    assertEquals(2, scorer.doc());
    assertEquals(node(-1), scorer.node());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());

    assertEndOfStream(scorer);
  }

  @Test
  public void testRewriteRootAndClause() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(2)), token("bbb", node(2,0)))
    );

    final NodeScorer scorer = this.getScorer(
      twq(1, must("aaa")).with(child(must("bbb")))
    );

    assertTrue(scorer instanceof TwigScorer);
    final TwigScorer ts = (TwigScorer) scorer;
    final Collection<ChildScorer> childs = ts.getChildren();
    assertEquals(2, childs.size());
    // The root and child must have been rewritten into NodeTermScorer
    NodeScorer s = null;
    for (final ChildScorer child : childs) {
      s = (NodeScorer) child.child;
      if (s instanceof AncestorFilterScorer) {
        s = ((AncestorFilterScorer) child.child).getScorer();
      }
      assertTrue(s instanceof NodeTermScorer);
    }
  }

  @Test
  public void testRewriteWithZeroClause() throws Exception {
    this.addDocuments(
      doc(token("aaa", node(2)), token("bbb", node(2,0)))
    );

    final NodeScorer scorer = this.getScorer(
      twq(1, must("aaa"))
    );

    // Should be rewritten into a NodeTermScorer
    assertTrue(scorer instanceof NodeTermScorer);
  }

  @Test
  public void testNoRequiredAndOptionalClause() throws IOException {
    // if the root node of a twig query is empty, and if there is no required
    // and optional clauses, it should return no result

    // optional clauses with terms that do not exist
    Query query = twq(1)
      .optional(child(must("ddd")))
      .optional(child(must("eee")))
      .getLuceneProxyQuery();
    assertEquals(0, searcher.search(query, 100).totalHits);
  }

  @Test
  public void testNullAncestorQuery() throws IOException {
    // when a twig with an empty root and only one clause is rewritten in an
    // ancestor query, if the ancestor query is composed of terms that do not
    // exist it should return no result

    // optional clauses with terms that do not exist
    final Query query = twq(1)
      .optional(child(must("eee")))
      .getLuceneProxyQuery();
    assertEquals(0, searcher.search(query, 100).totalHits);
  }

}
