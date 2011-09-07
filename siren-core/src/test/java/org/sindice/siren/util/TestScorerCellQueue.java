/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.junit.Test;
import org.sindice.siren.search.AbstractTestSirenScorer;
import org.sindice.siren.search.QueryTestingHelper;
import org.sindice.siren.search.SirenScorer;

public class TestScorerCellQueue
extends AbstractTestSirenScorer {

  @Test
  public void testPut() throws IOException {
    final ScorerCellQueue q = new ScorerCellQueue(3);
    q.put(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, "term1"));
    q.put(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, "term2"));
    q.put(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, "term3"));
    assertEquals(3, q.size());
  }

  @Test(expected=ArrayIndexOutOfBoundsException.class)
  public void testPutFail() throws IOException {
    final ScorerCellQueue q = new ScorerCellQueue(3);
    q.put(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, "term1"));
    q.put(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, "term2"));
    q.put(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, "term3"));
    q.put(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, "term4"));
  }

  @Test
  public void testInsert() throws CorruptIndexException, IOException {
    _helper.addDocument("\"term1\" \"term2\" . ");
    _helper.addDocument("\"term3\" .  \"term4\" . ");
    _helper.addDocument("\"term5\" . ");

    final SirenScorer s1 = this.getPositionedTermScorer("term1");
    final SirenScorer s2 = this.getPositionedTermScorer("term2");
    final SirenScorer s3 = this.getPositionedTermScorer("term3");
    final SirenScorer s4 = this.getPositionedTermScorer("term4");
    final SirenScorer s5 = this.getPositionedTermScorer("term5");

    final ScorerCellQueue q = new ScorerCellQueue(3);
    q.put(s3);
    assertTrue(q.insert(s2));
    assertTrue(q.insert(s4));
    assertFalse(q.insert(s1));
    assertTrue(q.insert(s5));
  }

  /**
   * Test method for {@link org.sindice.siren.util.ScorerCellQueue#top()}.
   * @throws IOException
   * @throws CorruptIndexException
   */
  @Test
  public void testTop() throws CorruptIndexException, IOException {
    _helper.addDocument("\"term1\" \"term2\" . ");
    _helper.addDocument("\"term3\" .  \"term4\" . ");
    _helper.addDocument("\"term5\" . ");

    final SirenScorer s2 = this.getPositionedTermScorer("term2");
    final SirenScorer s3 = this.getPositionedTermScorer("term3");
    final SirenScorer s4 = this.getPositionedTermScorer("term4");
    final SirenScorer s5 = this.getPositionedTermScorer("term5");

    final ScorerCellQueue q = new ScorerCellQueue(3);
    q.put(s3);
    assertSame(s3, q.top());
    assertTrue(q.insert(s2));
    assertSame(s2, q.top()); // s2 should be the least scorer
    assertTrue(q.insert(s4));
    assertSame(s2, q.top());
    assertTrue(q.insert(s5));
    // s2 should have been removed, and the least scorer is now s3
    assertSame(s3, q.top());
  }

  @Test
  public void testNrMatches() throws CorruptIndexException, IOException {
    _helper.addDocument("\"term1\" \"term2\" . \"term3\" .  \"term4\" . ");

    final SirenScorer s1 = this.getPositionedTermScorer("term1");
    final SirenScorer s2 = this.getPositionedTermScorer("term2");
    final SirenScorer s3 = this.getPositionedTermScorer("term3");
    final SirenScorer s4 = this.getPositionedTermScorer("term4");

    final ScorerCellQueue q = new ScorerCellQueue(4);
    q.put(s1);
    q.put(s2);
    q.put(s3);
    q.put(s4);
    assertEquals(4, q.nrMatches());
  }

  @Test
  public void testScoreSum() throws CorruptIndexException, IOException {
    _helper.addDocument("\"term1\" \"term2\" . \"term3\" .  \"term4\" . ");

    final SirenScorer s1 = this.getPositionedTermScorer("term1");
    final SirenScorer s2 = this.getPositionedTermScorer("term2");
    final SirenScorer s3 = this.getPositionedTermScorer("term3");
    final SirenScorer s4 = this.getPositionedTermScorer("term4");

    final ScorerCellQueue q = new ScorerCellQueue(4);
    q.put(s1);
    q.put(s2);
    q.put(s3);
    q.put(s4);
    assertEquals(s1.score() + s2.score() + s3.score() + s4.score(), q.scoreSum(), 0f);
  }

  @Test
  public void testNextAndAdjustElsePop() throws CorruptIndexException, IOException {
    _helper.addDocument("\"term1\" \"term2\" . \"term3\" .  \"term4\" . ");
    _helper.addDocument("\"term1\" \"term2\" .  \"term4\" . ");
    _helper.addDocument("\"term1\" . \"term3\" . ");

    final SirenScorer s1 = this.getPositionedTermScorer("term1");
    final SirenScorer s2 = this.getPositionedTermScorer("term2");
    final SirenScorer s3 = this.getPositionedTermScorer("term3");
    final SirenScorer s4 = this.getPositionedTermScorer("term4");

    final ScorerCellQueue q = new ScorerCellQueue(4);
    q.put(s1);
    q.put(s2);
    q.put(s3);
    q.put(s4);
    assertEquals(0, q.nextAndAdjustElsePop());
    assertEquals(s1, q.top());
    assertEquals(1, q.topEntity());
    assertEquals(2, q.nextAndAdjustElsePop());
    assertEquals(s1, q.top());
    assertEquals(2, q.topEntity());
    assertEquals(2, q.nextAndAdjustElsePop());
  }

  /**
   * Test method for {@link org.sindice.siren.util.ScorerCellQueue#topNextAndAdjustElsePop()}.
   * @throws IOException
   * @throws CorruptIndexException
   */
  @Test
  public void testTopNextAndAdjustElsePop() throws CorruptIndexException, IOException {
    _helper.addDocument("\"term1\" \"term2\" . ");
    _helper.addDocument("\"term3\" .  \"term1\" . ");
    _helper.addDocument("\"term2\" \"term3\" . ");

    final SirenScorer s1 = this.getPositionedTermScorer("term1");
    final SirenScorer s2 = this.getPositionedTermScorer("term2");
    final SirenScorer s3 = this.getPositionedTermScorer("term3");

    final ScorerCellQueue q = new ScorerCellQueue(3);
    q.put(s1);
    q.put(s2);
    q.put(s3);
    assertSame(s1, q.top());
    q.topNextAndAdjustElsePop();
    assertSame(s2, q.top());
    q.topNextAndAdjustElsePop();
    assertSame(s3, q.top());
  }

  @Test
  public void testTopNextPositionAndAdjust() throws CorruptIndexException, IOException {
    _helper.addDocument("\"term1\" \"term2\" . \"term1\" . \"term1 term2\" \"term2\" . ");

    final SirenScorer s1 = this.getPositionedTermScorer("term1");
    final SirenScorer s2 = this.getPositionedTermScorer("term2");

    final ScorerCellQueue q = new ScorerCellQueue(2);
    q.put(s1);
    q.put(s2);
    assertSame(s1, q.top());
    assertTrue(q.topNextPositionAndAdjust());
    assertSame(s2, q.top());
    assertEquals(0, q.topTuple());
    assertEquals(1, q.topCell());
    assertTrue(q.topNextPositionAndAdjust());
    assertSame(s1, q.top());
    assertEquals(1, q.topTuple());
    assertEquals(0, q.topCell());
    assertTrue(q.topNextPositionAndAdjust());
    assertSame(s1, q.top());
    assertEquals(2, q.topTuple());
    assertEquals(0, q.topCell());
    assertTrue(q.topNextPositionAndAdjust());
    assertSame(s2, q.top());
    assertEquals(2, q.topTuple());
    assertEquals(0, q.topCell());
    assertTrue(q.topNextPositionAndAdjust());
    assertSame(s2, q.top());
    assertEquals(2, q.topTuple());
    assertEquals(1, q.topCell());
    assertFalse(q.topNextPositionAndAdjust());
  }

  /**
   * Test method for {@link org.sindice.siren.util.ScorerCellQueue#topSkipToAndAdjustElsePop(int)}.
   * @throws IOException
   * @throws CorruptIndexException
   */
  @Test
  public void testTopSkipToAndAdjustElsePop() throws CorruptIndexException, IOException {
    _helper.addDocument("\"term1\" \"term2\" . ");
    _helper.addDocument("\"term3\" .  \"term1\" . ");
    _helper.addDocument("\"term2\" \"term3\" . ");
    _helper.addDocument("\"term3\" .  \"term1\" . ");

    final SirenScorer s1 = this.getPositionedTermScorer("term1");
    final SirenScorer s2 = this.getPositionedTermScorer("term2");
    final SirenScorer s3 = this.getPositionedTermScorer("term3");

    final ScorerCellQueue q = new ScorerCellQueue(3);
    q.put(s1);
    q.put(s2);
    q.put(s3);
    assertSame(s1, q.top());
    q.topSkipToAndAdjustElsePop(3, 1, 0);
    assertSame(s2, q.top());
    q.topSkipToAndAdjustElsePop(2, 0);
    assertSame(s3, q.top());
    q.topSkipToAndAdjustElsePop(3);
    assertSame(s2, q.top());
  }

  @Override
  protected void assertTo(final AssertFunctor functor, final String[] input,
                          final String[] terms, final int expectedNumDocs,
                          final int[] expectedNumTuples, final int[] expectedNumCells,
                          final int[] expectedEntityID, final int[] expectedTupleID,
                          final int[] expectedCellID, final int[] expectedPos)
  throws Exception {
    // TODO Auto-generated method stub

  }

}
