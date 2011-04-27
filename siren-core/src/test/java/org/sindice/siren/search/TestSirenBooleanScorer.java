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
 * @author Renaud Delbru [ 8 May 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.lucene.search.DocIdSetIterator;
import org.junit.Test;

public class TestSirenBooleanScorer
extends AbstractTestSirenScorer {

  @Test
  public void testNextReq()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"aaa bbb ccc\" \"bbb ccc\" . ");
    _helper.addDocument("\"aaa\" \"aaa bbb\" . ");

    final SirenBooleanScorer scorer = this.getBooleanScorer(
      new String[] {"aaa", "bbb"},
      null, null);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextReqWithConstraints()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"aaa bbb ccc\" \"bbb ccc\" . ");
    _helper.addDocument("\"aaa\" \"aaa bbb\" . ");

    final SirenCellScorer scorer = this.getCellScorer(0, 0,
      new String[] {"aaa", "bbb"},
      null, null);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextReqExcl()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"aaa bbb ccc\" \"bbb ccc\" . ");
    _helper.addDocument("\"aaa\" \"aaa bbb\" . ");

    final SirenBooleanScorer scorer = this.getBooleanScorer(
      new String[] {"aaa"}, null, new String[] {"ccc"});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextReqExclWithConstraints()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"aaa bbb ccc\" \"bbb ccc\" . ");
    _helper.addDocument("\"aaa\" \"aaa bbb\" . ");

    final SirenCellScorer scorer = this.getCellScorer(1, 1,
      new String[] {"aaa"}, null, new String[] {"ccc"});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextOpt()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"aaa bbb ccc\" \"bbb ccc ddd\" . ");
    _helper.addDocument("\"aaa\" \"aaa bbb\" . ");

    final SirenBooleanScorer scorer = this.getBooleanScorer(null,
      new String[] {"aaa", "ddd"}, null);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextOptWithConstraints()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"aaa bbb ccc\" \"bbb ccc ddd\" . ");
    _helper.addDocument("\"aaa\" \"aaa bbb\" . ");

    final SirenCellScorer scorer = this.getCellScorer(1, 1, null,
      new String[] {"aaa", "ddd"}, null);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(1, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextReqOpt()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"ccc\" \"bbb ccc\" . ");
    _helper.addDocument("\"aaa\" \"aaa bbb\" . ");

    final SirenBooleanScorer scorer = this.getBooleanScorer(new String[] {"aaa"},
      new String[] {"bbb"}, null);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextOptExcl()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"ccc\" \"bbb ccc\" . ");
    _helper.addDocument("\"aaa\" \"aaa bbb\" . ");

    final SirenBooleanScorer scorer = this.getBooleanScorer(null,
      new String[] {"bbb"}, new String[] {"ccc"});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Test
  public void testNextReqOptExcl()
  throws Exception {
    _helper.addDocument("\"aaa bbb\" \"aaa ccc\" . \"aaa bbb ccc\" \"bbb ccc\" . ");
    _helper.addDocument("\"aaa\" \"aaa bbb\" . ");

    final SirenBooleanScorer scorer = this.getBooleanScorer(new String[] {"aaa"},
      new String[] {"bbb"}, new String[] {"ccc"});

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(0, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(0, scorer.cell());

    assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
    assertEquals(1, scorer.entity());
    assertEquals(0, scorer.tuple());
    assertEquals(1, scorer.cell());

    assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);

    assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
  }

  @Override
  protected void assertTo(final AssertFunctor functor, final String[] input,
                          final String[] terms, final int expectedNumDocs,
                          final int[] expectedNumTuples, final int[] expectedNumCells,
                          final int[] expectedEntityID, final int[] expectedTupleID,
                          final int[] expectedCellID, final int[] expectedPos)
  throws Exception {}


}
