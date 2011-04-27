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
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.lucene.search.DocIdSetIterator;
import org.junit.Test;

public class TestSirenDisjunctionScorer
extends AbstractTestSirenScorer {

  @Test
  public void testNextWithTermDisjunction()
  throws Exception {
    _helper.addDocument("<http://renaud.delbru.fr/> . ");
    _helper.addDocument("<http://sindice.com/test/name> \"Renaud Delbrut\" . ");
    _helper.addDocument(
      "<http://sindice.com/test/type> <http://sindice.com/test/Person> . " +
      "<http://sindice.com/test/name> \"R. Delbru\" . ");

    final SirenDisjunctionScorer scorer =
      this.getDisjunctionScorer(new String[] {"renaud", "delbru"});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(2, scorer.nrMatchers());
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertEquals(1, scorer.nrMatchers());
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(2, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertEquals(1, scorer.nrMatchers());
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextPositionWithTermDisjunction()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"ccc\" \"bbb ccc\" .");
    _helper.addDocument("\"aaa ccc bbb\" . \"aaa aaa ccc bbb bbb\" . ");

    final SirenDisjunctionScorer scorer =
      this.getDisjunctionScorer(new String[] {"aaa", "bbb"});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(2, scorer.nrMatchers());
    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertEquals(1, scorer.nrMatchers());
    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertEquals(1, scorer.nrMatchers());
    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(2, scorer.nrMatchers());
    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(1, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(2, scorer.nrMatchers());
    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testSkipToNextWithTermDisjunction()
  throws Exception {
    for (int i = 0; i < 16; i++) {
      _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"ccc\" \"bbb ccc\" .");
      _helper.addDocument("\"aaa ccc bbb\" . \"aaa aaa ccc bbb bbb\" . ");
    }

    final SirenDisjunctionScorer scorer =
      this.getDisjunctionScorer(new String[] {"aaa", "bbb"});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(2, scorer.nrMatchers());
    assertFalse(scorer.advance(16) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(16, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(2, scorer.nrMatchers());
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(17, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertEquals(2, scorer.nrMatchers());
    assertFalse(scorer.advance(20, 1) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(20, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertEquals(1, scorer.nrMatchers());
    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(21, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());
    assertFalse(scorer.advance(30, 1, 0) == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(30, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());
    assertEquals(1, scorer.nrMatchers());
    assertTrue(scorer.advance(34) == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(scorer.advance(42, 2) == DocIdSetIterator.NO_MORE_DOCS);
    assertTrue(scorer.advance(123, 98, 12) == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Override
  protected void assertTo(final AssertFunctor functor, final String[] input,
                          final String[] terms, final int expectedNumDocs,
                          final int[] expectedNumTuples, final int[] expectedNumCells,
                          final int[] expectedEntityID, final int[] expectedTupleID,
                          final int[] expectedCellID, final int[] expectedPos)
  throws Exception {}

}
