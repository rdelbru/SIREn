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
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Weight;
import org.junit.Test;
import org.sindice.siren.search.SirenScorer.InvalidCallException;

public class TestSirenExactPhraseScorer
extends AbstractTestSirenScorer {

  /**
   * Test exact phrase scorer: should not match two words in separate cells
   *
   * @throws Exception
   */
  @Test
  public void testExactNextFail1()
  throws Exception {
    final String field = "content";
    _helper.addDocument("\"word1 word2 word3\" \"word4 word5\" . ");

    final SirenExactPhraseScorer scorer = this.getExactScorer(field, new int[] { 0, 1 },
      new String[] { "word1", "word4" });
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  /**
   * Test exact phrase scorer: should not match phrase with a gap of 1 between
   * the two phrase query term
   *
   * @throws Exception
   */
  @Test
  public void testExactNextFail2()
  throws Exception {
    final String field = "content";
    _helper.addDocument("\"word1 word2 word3\" \"word4 word5\" . ");
    final SirenExactPhraseScorer scorer = this.getExactScorer(field,
      new int[] { 0, 2 }, new String[] { "word4", "word5" });
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextWithURI()
  throws Exception {
    this.assertTo(
      new AssertNextEntityFunctor(),
      new String[] { "<http://renaud.delbru.fr/> <http://renaud.delbru.fr/> . " },
      new String[] { "renaud", "delbru" }, 1, new int[] { 1 }, new int[] { 2 },
      new int[] { 0 }, new int[] { 0 }, new int[] { 0 }, new int[] { 0 });
    this.assertTo(new AssertNextEntityFunctor(), new String[] {
        "<http://renaud.delbru.fr/> <http://renaud.delbru.fr/> . ",
        "<http://renaud.delbru.fr/> <http://test/name> \"Renaud Delbru\" . " },
      new String[] { "renaud", "delbru" }, 2, new int[] { 1, 1 }, new int[] { 2, 2 },
      new int[] { 0, 1 }, new int[] { 0, 0 }, new int[] { 0, 0 },
      new int[] { 0, 0 });
  }

  @Test
  public void testNextPositionWithURI()
  throws Exception {
    this.assertTo(
      new AssertNextPositionEntityFunctor(),
      new String[] { "<http://renaud.delbru.fr/> <http://renaud.delbru.fr/> . " },
      new String[] { "renaud", "delbru" }, 1, new int[] { 1 }, new int[] { 2 },
      new int[] { 0, 0 }, new int[] { 0, 0 }, new int[] { 0, 1 },
      new int[] { 0, 2 });
    this.assertTo(new AssertNextPositionEntityFunctor(), new String[] {
        "<http://renaud.delbru.fr/> <http://renaud.delbru.fr/> . ",
        "<http://renaud.delbru.fr/> <http://test/name> \"Renaud Delbru\" . " },
      new String[] { "renaud" }, 2, new int[] { 1, 1 }, new int[] { 2, 2 },
      new int[] { 0, 0, 1, 1 }, new int[] { 0, 0, 0, 0 }, new int[] { 0, 1, 0, 2 },
      new int[] { 0, 2, 0, 4 });
  }

  @Test
  public void testNextPositionWithMultipleOccurrencesInLiteral()
  throws Exception {
    this.assertTo(
      new AssertNextPositionEntityFunctor(),
      new String[] { "<http://renaud.delbru.fr/> \"renaud delbru delbru renaud renaud delbru\" . " },
      new String[] { "renaud", "delbru" }, 1, new int[] { 1 }, new int[] { 3 },
      new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, new int[] { 0, 1, 1 },
      new int[] { 0, 2, 6 });
  }

  @Test
  public void testSkipToEntity()
  throws Exception {
    for (int i = 0; i < 32; i++)
      _helper.addDocument("<http://renaud.delbru.fr/> . ");
    final SirenScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, new String[] {"renaud", "delbru"});
    assertFalse(scorer.advance(16) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(16, scorer.docID());
    assertEquals(16, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(0, scorer.pos());
  }

  @Test
  public void testSkipToEntityNext()
  throws Exception {
    for (int i = 0; i < 32; i++)
      _helper.addDocument("<http://renaud.delbru.fr/> . ");
    final SirenScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, new String[] {"renaud", "delbru"});
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertFalse(scorer.advance(16) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(16, scorer.docID());
    assertEquals(16, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(0, scorer.pos());
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(17, scorer.entity());
  }

  /**
   * Check if {@link SirenPhraseScorer#advance(int, int, int)} works correctly
   * when advancing to the same entity.
   */
  @Test
  public void testNextSkipToEntity1()
  throws Exception {
    _helper.addDocument("\"aaa bbb aaa\" . \"aaa bbb ccc\" .");
    final SirenScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, new String[] {"bbb", "ccc"});
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.docID());
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(4, scorer.pos());
    assertFalse(scorer.advance(0, 1, 0) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.docID());
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(4, scorer.pos());
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  /**
   * Check if {@link SirenPhraseScorer#advance(int, int, int)} works correctly
   * when advancing to the same entity.
   */
  @Test
  public void testNextSkipToEntity2()
  throws Exception {
    _helper.addDocument("\"aaa bbb aaa\" . \"ccc bbb ccc\" . \"aaa bbb ccc\" .");
    final SirenScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, new String[] {"bbb", "ccc"});
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.docID());
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(4, scorer.pos());
    assertFalse(scorer.advance(0, 0) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.docID());
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertFalse(scorer.advance(0, 1, 2) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.docID());
    assertEquals(0, scorer.entity());
    assertEquals(2, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testSkipToEntityNextPosition()
  throws Exception {
    for (int i = 0; i < 32; i++)
      _helper.addDocument("<http://renaud.delbru.fr/> . \"renaud delbru\" .");
    final SirenScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, new String[] {"renaud", "delbru"});
    assertFalse(scorer.advance(16) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(16, scorer.docID());
    assertEquals(16, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(0, scorer.pos());

    assertFalse(scorer.nextPosition() == DocTupCelIdSetIterator.NO_MORE_POS);
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(2, scorer.pos());

    assertTrue(scorer.nextPosition() == DocTupCelIdSetIterator.NO_MORE_POS);
  }

  @Test
  public void testSkipToEntityTuple()
  throws Exception {
    for (int i = 0; i < 32; i++)
      _helper.addDocument("<http://renaud.delbru.fr/> . \"renaud delbru\" . \"renaud delbru\" . ");
    final SirenScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, new String[] {"renaud", "delbru"});
    assertFalse(scorer.advance(16, 2) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(16, scorer.docID());
    assertEquals(16, scorer.entity());
    assertEquals(2, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(4, scorer.pos());
  }

  @Test
  public void testSkipToEntityTupleCell()
  throws Exception {
    for (int i = 0; i < 32; i++)
      _helper.addDocument("<http://renaud.delbru.fr/> . \"renaud delbru\" \"renaud delbru\" . ");
    final SirenScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, new String[] {"renaud", "delbru"});
    assertFalse(scorer.advance(16, 1, 1) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(16, scorer.docID());
    assertEquals(16, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(4, scorer.pos());
  }

  @Test
  public void testSkipToNonExistingEntityTupleCell()
  throws Exception {
    for (int i = 0; i < 32; i++)
      _helper.addDocument("<http://renaud.delbru.fr/> . \"renaud delbru\" \"renaud delbru\" . ");
    final SirenScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, new String[] {"renaud", "delbru"});
    assertFalse(scorer.advance(16, 3, 2) == DocIdSetIterator.NO_MORE_DOCS); // does not exist, should skip to entity 17
    assertEquals(17, scorer.docID());
    assertEquals(17, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(0, scorer.pos());
  }

  @Test
  public void testSkipToEntityTupleCellNextPosition()
  throws Exception {
    for (int i = 0; i < 32; i++)
      _helper.addDocument("<http://renaud.delbru.fr/> . \"renaud delbru\" \"renaud delbru\" . ");
    final SirenScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, new String[] {"renaud", "delbru"});
    assertFalse(scorer.advance(16, 1, 0) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(16, scorer.docID());
    assertEquals(16, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(2, scorer.pos());

    // Should not return match in first tuple (tuple 0)
    assertFalse(scorer.nextPosition() == DocTupCelIdSetIterator.NO_MORE_POS);
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertEquals(-1, scorer.dataset());
    assertEquals(4, scorer.pos());

    assertTrue(scorer.nextPosition() == DocTupCelIdSetIterator.NO_MORE_POS);
  }



  @Test(expected=InvalidCallException.class)
  public void testInvalidScoreCall() throws IOException {
    _helper.addDocument("\"Renaud Delbru\" . ");

    final Term t1 = new Term(QueryTestingHelper.DEFAULT_FIELD, "renaud");
    final Term t2 = new Term(QueryTestingHelper.DEFAULT_FIELD, "delbru");
    final SirenPhraseQuery query = new SirenPhraseQuery();
    query.add(t1); query.add(t2);
    final Weight w = query.weight(_helper.getSearcher());

    final IndexReader reader = _helper.getIndexReader();
    final TermPositions[] tps = new TermPositions[2];
    tps[0] = reader.termPositions(t1);
    tps[1] = reader.termPositions(t2);

    final SirenPhraseScorer scorer = new SirenExactPhraseScorer(w, tps, new int[] {0, 1},
      _helper.getSearcher().getSimilarity(), reader.norms(QueryTestingHelper.DEFAULT_FIELD));
    assertNotNull("ts is null and it shouldn't be", scorer);

    // Invalid call
    scorer.score();
  }

  @Test
  public void testScore() throws IOException {
    _helper.addDocument("\"Renaud Delbru\" . <http://renaud.delbru.fr> . ");

    final Term t1 = new Term(QueryTestingHelper.DEFAULT_FIELD, "renaud");
    final Term t2 = new Term(QueryTestingHelper.DEFAULT_FIELD, "delbru");
    final SirenPhraseQuery query = new SirenPhraseQuery();
    query.add(t1); query.add(t2);

    final IndexReader reader = _helper.getIndexReader();
    final TermPositions[] tps = new TermPositions[2];
    tps[0] = reader.termPositions(t1);
    tps[1] = reader.termPositions(t2);

    final SirenPhraseScorer scorer = new SirenExactPhraseScorer(
      new ConstantWeight(), tps, new int[] {0, 1},
      _helper.getSearcher().getSimilarity(),
      reader.norms(QueryTestingHelper.DEFAULT_FIELD));
    assertNotNull("ts is null and it shouldn't be", scorer);

    assertFalse("no doc returned", scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0.70, scorer.score(), 0.01);
  }

  // /////////////////////////////////
  //
  // END OF TESTS
  // START HELPER METHODS AND CLASSES
  //
  // /////////////////////////////////

  @Override
  protected void assertTo(final AssertFunctor functor, final String[] input,
                          final String[] phraseTerms,
                          final int expectedNumDocs,
                          final int[] expectedNumTuples,
                          final int[] expectedNumCells,
                          final int[] expectedEntityID,
                          final int[] expectedTupleID,
                          final int[] expectedCellID, final int[] expectedPos)
  throws Exception {
    _helper.reset();
    _helper.addDocuments(input);
    final IndexReader reader = _helper.getIndexReader();
    assertEquals(expectedNumDocs, reader.numDocs());

    final SirenExactPhraseScorer scorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, phraseTerms);
    functor.run(scorer, expectedNumDocs, expectedNumTuples, expectedNumCells,
      expectedEntityID, expectedTupleID, expectedCellID, expectedPos);
    reader.close();
  }

}
