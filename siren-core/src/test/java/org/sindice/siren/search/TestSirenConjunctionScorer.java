/**
 * Copyright 2009, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 28 Apr 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;
import org.junit.Test;

public class TestSirenConjunctionScorer
extends AbstractTestSirenScorer {

  @Test
  public void testNextWithTermConjunction()
  throws Exception {
    _helper.addDocument("<http://renaud.delbru.fr/> . ");
    _helper.addDocument("<http://sindice.com/test/name> \"Renaud Delbru\" . ");
    _helper.addDocument(
      "<http://sindice.com/test/type> <http://sindice.com/test/Person> . " +
      "<http://sindice.com/test/name> \"Renaud Delbru\" . ");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[] {"renaud", "delbru"});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(2, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNoNext() throws IOException {
    _helper.addDocument("\"eee\" . \"ddd\" . ");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[] {"ddd", "eee"});

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextWithPhraseConjunction()
  throws Exception {
    _helper.addDocument("\"aaa bbb aaa\". ");
    _helper.addDocument("\"aaa bbb aba\" \"aaa ccc bbb aaa\" . ");
    _helper.addDocument(
      "\"aaa bbb ccc\" \"aaa ccc aaa aaa ccc\" . " +
      "\" bbb ccc aaa \" \"aaa bbb bbb ccc aaa ccc\" . ");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[][] {{"aaa", "bbb"}, {"aaa", "ccc"}});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(2, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testSkipToWithTermConjunction()
  throws Exception {
    _helper.addDocument("<http://renaud.delbru.fr/> . ");
    _helper.addDocument("<http://sindice.com/test/name> \"Renaud Delbru\" . ");
    _helper.addDocument(
      "<http://sindice.com/test/type> <http://sindice.com/test/Person> . " +
      "<http://sindice.com/test/name> \"Renaud Delbru\" . ");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[] {"renaud", "delbru"});

    assertFalse(scorer.advance(1, 0) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertFalse(scorer.advance(2, 0, 0) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(2, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertTrue(scorer.advance(2, 2, 1) == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(scorer.advance(4) == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testSkipToWithPhrase()
  throws Exception {
    _helper.addDocument("<http://renaud.delbru.fr/> . ");
    _helper.addDocument("<http://sindice.com/test/name> \"Renaud Delbru\" . ");
    _helper.addDocument(
      "<http://sindice.com/test/type> <http://sindice.com/test/Person> . " +
      "<http://sindice.com/test/name> \"Renaud Delbru\" . ");
    _helper.addDocument("<http://sindice.com/test/name> \"Delbru Renaud\" . ");
    _helper.addDocument("<http://sindice.com/test/name> \"Renaud aaaa Delbru\" . ");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[][] {{"renaud", "delbru"}});

    assertFalse(scorer.advance(1, 0) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertFalse(scorer.advance(2, 0, 0) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(2, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertTrue(scorer.advance(2, 2, 1) == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(scorer.advance(4) == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testSkipToWithPhraseConjunction()
  throws Exception {
    _helper.addDocument("\"aaa bbb aaa\". ");
    _helper.addDocument(
      "\"aaa bbb\" \"aaa ccc\" . " +
      "\" bbb ccc \" . " +
      "\"aaa bbb aaa ccc\" . ");
    _helper.addDocument("\"aaa bbb aba\" \"aaa ccc bbb aaa\" . ");
    _helper.addDocument(
      "\"aaa bbb ccc\" \"aaa ccc aaa aaa ccc\" . " +
      "\" bbb ccc aaa \" \"aaa bbb bbb ccc aaa ccc\" . ");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[][] {{"aaa", "bbb"}, {"aaa", "ccc"}});

    assertFalse(scorer.advance(1, 0) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(2, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertFalse(scorer.advance(2, 0, 1) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(3, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertTrue(scorer.advance(3, 2, 1) == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(scorer.advance(4) == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextPositionWithTermConjunction()
  throws Exception {
    _helper.addDocument(
      "<http://sindice.com/test/type> <http://sindice.com/test/Person> . " +
      "<http://sindice.com/test/homepage> <http://renaud.delbru.fr/> . " +
      "<http://sindice.com/test/name> \"Renaud Delbru\" ." +
      "<http://sindice.com/test/description> \"aaaa Renaud bbbb aaaa Delbru aaaa\" .");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[] {"renaud", "delbru"});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(2, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(3, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextPositionWithPhraseConjunction()
  throws Exception {
    _helper.addDocument("\"aaa bbb aaa\". ");
    _helper.addDocument(
      "\"aaa bbb\" \"aaa ccc\" . " +
      "\" bbb ccc \" . " +
      "\"aaa bbb aaa ccc\" . ");
    _helper.addDocument(
      "\"aaa bbb ccc\" \"aaa ccc aaa bbb ccc\" . " +
      "\" bbb ccc aaa \" \"aaa bbb bbb ccc aaa ccc\" . ");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[][] {{"aaa", "bbb"}, {"aaa", "ccc"}});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(2, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(2, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(2, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testScoreWithTermConjunction()
  throws Exception {
    _helper.addDocument("<http://renaud.delbru.fr/> . ");
    _helper.addDocument("<http://sindice.com/test/name> \"Renaud Delbru\" . ");
    _helper.addDocument(
      "<http://sindice.com/test/type> <http://sindice.com/test/Person> . " +
      "<http://sindice.com/test/name> \"Renaud Delbru\" . ");
    _helper.addDocument(
      "<http://sindice.com/test/type> <http://sindice.com/test/Person> . " +
      "<http://sindice.com/test/homepage> <http://renaud.delbru.fr/> . " +
      "<http://sindice.com/test/name> \"Renaud Delbru\" .");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[] {"renaud", "delbru"});

    float lastScore = 0;
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    lastScore = scorer.score();
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(lastScore > scorer.score());
    lastScore = scorer.score();
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(lastScore > scorer.score());
    lastScore = scorer.score();
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(lastScore < scorer.score());
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testScoreWithPhraseConjunction()
  throws Exception {
    _helper.addDocument("<http://renaud.delbru.fr/> . ");
    _helper.addDocument("<http://sindice.com/test/name> \"Renaud Delbru\" . ");
    _helper.addDocument(
      "<http://sindice.com/test/type> <http://sindice.com/test/Person> . " +
      "<http://sindice.com/test/name> \"Renaud Delbru\" . ");
    _helper.addDocument(
      "<http://sindice.com/test/type> <http://sindice.com/test/Person> . " +
      "<http://sindice.com/test/homepage> <http://renaud.delbru.fr/> . " +
      "<http://sindice.com/test/name> \"Renaud Delbru\" .");

    final SirenConjunctionScorer scorer =
      this.getConjunctionScorer(new String[][] {{"renaud", "delbru"}});

    float lastScore = 0;
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    lastScore = scorer.score();
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(lastScore > scorer.score());
    lastScore = scorer.score();
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(lastScore > scorer.score());
    lastScore = scorer.score();
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(lastScore < scorer.score());
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Override
  protected void assertTo(final AssertFunctor functor, final String[] input,
                          final String[] terms, final int expectedNumDocs,
                          final int[] expectedNumTuples,
                          final int[] expectedNumCells,
                          final int[] expectedEntityID,
                          final int[] expectedTupleID,
                          final int[] expectedCellID, final int[] expectedPos)
  throws Exception {

  }



}
