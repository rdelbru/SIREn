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
 * @author Renaud Delbru [ 21 Apr 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.LuceneTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.search.DocTupCelIdSetIterator;
import org.sindice.siren.search.QueryTestingHelper;

public class TestSirenTermPositions extends LuceneTestCase {

  protected QueryTestingHelper _helper = null;

  @Before
  public void setUp()
  throws Exception {
    super.setUp();
    _helper = new QueryTestingHelper(new TupleAnalyzer(TEST_VERSION_CURRENT, new StandardAnalyzer(TEST_VERSION_CURRENT), new AnyURIAnalyzer(TEST_VERSION_CURRENT)));
  }

  @After
  public void tearDown()
  throws Exception {
    super.tearDown();
    _helper.close();
  }

  @Test
  public void testNextSimpleOccurence1()
  throws Exception {
    _helper.addDocument("\"word1\" . ");
    final Term term = new Term("content", "word1");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    assertTrue(termPositions.next());
    assertEquals(0, termPositions.doc());
    assertEquals(0, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());

    assertEquals(1, termPositions.freq());
    assertEquals(0, termPositions.nextPosition());
    assertEquals(0, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(0, termPositions.pos());

    // end of the list, should return NO_MORE_POS
    assertEquals(DocTupCelIdSetIterator.NO_MORE_POS, termPositions.nextPosition());
  }

  @Test
  public void testNextSimpleOccurence2()
  throws Exception {
    _helper.addDocument("\"word1\" \"word2 word3 word4\" . ");
    final Term term = new Term("content", "word3");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    assertTrue(termPositions.next());
    assertEquals(0, termPositions.doc());
    assertEquals(0, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());

    assertEquals(1, termPositions.freq());
    // here it is assumed that the position of the term in the global position
    // in the flow of tokens (and not within a cell).
    assertEquals(2, termPositions.nextPosition());
    assertEquals(0, termPositions.tuple());
    assertEquals(1, termPositions.cell());
    assertEquals(2, termPositions.pos());

    // end of the list, should return NO_MORE_POS
    assertEquals(DocTupCelIdSetIterator.NO_MORE_POS, termPositions.nextPosition());
  }

  @Test
  public void testNextMultipleOccurences1()
  throws Exception {
    _helper.addDocument("\"word1 word1 word1\" . ");
    final Term term = new Term("content", "word1");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    assertTrue(termPositions.next());
    assertEquals(0, termPositions.doc());
    assertEquals(0, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());

    assertEquals(3, termPositions.freq());
    assertEquals(0, termPositions.nextPosition());
    assertEquals(0, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(0, termPositions.pos());
    assertEquals(1, termPositions.nextPosition());
    assertEquals(0, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(1, termPositions.pos());
    assertEquals(2, termPositions.nextPosition());
    assertEquals(0, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(2, termPositions.pos());

    // end of the list, should return NO_MORE_POS
    assertEquals(DocTupCelIdSetIterator.NO_MORE_POS, termPositions.nextPosition());
  }

  @Test
  public void testNextMultipleOccurences2()
  throws Exception {
    _helper.addDocument("\"word1 word2\" \"word1\" . \"word1 word2\" . \"word1\" . ");
    final Term term = new Term("content", "word1");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    assertTrue(termPositions.next());
    assertEquals(0, termPositions.doc());
    assertEquals(0, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());

    assertEquals(4, termPositions.freq());
    assertEquals(0, termPositions.nextPosition());
    assertEquals(0, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(0, termPositions.pos());
    assertEquals(2, termPositions.nextPosition());
    assertEquals(0, termPositions.tuple());
    assertEquals(1, termPositions.cell());
    assertEquals(2, termPositions.pos());
    assertEquals(3, termPositions.nextPosition());
    assertEquals(1, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(3, termPositions.pos());
    assertEquals(5, termPositions.nextPosition());
    assertEquals(2, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(5, termPositions.pos());

    // end of the list, should return NO_MORE_POS
    assertEquals(DocTupCelIdSetIterator.NO_MORE_POS, termPositions.nextPosition());
  }

  @Test
  public void testSkipTo()
  throws Exception {
    for (int i = 0; i < 64; i++) {
      _helper.addDocument("\"aaa aaa\" . \"aaa\" \"aaa\" .");
      _helper.addDocument("\"aaa bbb\" . \"aaa ccc\" .");
    }

    final Term term = new Term("content", "aaa");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    termPositions.skipTo(16);
    assertEquals(16, termPositions.doc());
    assertEquals(16, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());
    assertEquals(4, termPositions.freq());

    termPositions.skipTo(33, 1);
    assertEquals(33, termPositions.doc());
    assertEquals(33, termPositions.entity());
    assertEquals(1, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(2, termPositions.pos());
    assertEquals(2, termPositions.freq());

    termPositions.skipTo(96, 1, 1);
    assertEquals(96, termPositions.doc());
    assertEquals(96, termPositions.entity());
    assertEquals(1, termPositions.tuple());
    assertEquals(1, termPositions.cell());
    assertEquals(3, termPositions.pos());
    assertEquals(4, termPositions.freq());
  }

  /**
   * If the entity, tuple and cell are not found, it should return the first
   * match that is greater than the target. (SRN-17)
   */
  @Test
  public void testSkipToNotFound()
  throws Exception {
    for (int i = 0; i < 32; i++) {
      _helper.addDocument("\"aaa aaa\" . \"aaa\" \"aaa\" .");
      _helper.addDocument("\"aaa bbb\" . \"aaa ccc\" . \"aaa bbb\" . \"aaa ccc\" \"aaa bbb\" . ");
    }

    final Term term = new Term("content", "bbb");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    // Should move to the next entity, without updating tuple and cell
    // information
    assertTrue(termPositions.skipTo(16));
    assertEquals(17, termPositions.doc());
    assertEquals(17, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());
    assertEquals(3, termPositions.freq());

    // Should jump to the third tuples
    assertTrue(termPositions.skipTo(17, 1));
    assertEquals(17, termPositions.doc());
    assertEquals(17, termPositions.entity());
    assertEquals(2, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(5, termPositions.pos());
    assertEquals(3, termPositions.freq());

    // Should jump to the second cell
    assertTrue(termPositions.skipTo(17, 3, 0));
    assertEquals(17, termPositions.doc());
    assertEquals(17, termPositions.entity());
    assertEquals(3, termPositions.tuple());
    assertEquals(1, termPositions.cell());
    assertEquals(9, termPositions.pos());
    assertEquals(3, termPositions.freq());
  }

  @Test
  public void testSkipToAtSameEntity()
  throws Exception {
    for (int i = 0; i < 64; i++) {
      _helper.addDocument("\"aaa aaa\" . \"aaa\" \"aaa\" .");
      _helper.addDocument("\"aaa bbb\" . \"aaa ccc\" .");
    }

    final Term term = new Term("content", "aaa");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    termPositions.skipTo(16);
    assertEquals(16, termPositions.doc());
    assertEquals(16, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());
    assertEquals(4, termPositions.freq());

    termPositions.skipTo(16, 1);
    assertEquals(16, termPositions.doc());
    assertEquals(16, termPositions.entity());
    assertEquals(1, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(2, termPositions.pos());
    assertEquals(4, termPositions.freq());

    termPositions.skipTo(16, 1, 1);
    assertEquals(16, termPositions.doc());
    assertEquals(16, termPositions.entity());
    assertEquals(1, termPositions.tuple());
    assertEquals(1, termPositions.cell());
    assertEquals(3, termPositions.pos());
    assertEquals(4, termPositions.freq());
  }

  @Test
  public void testSkipToEntityNextPosition()
  throws Exception {
    for (int i = 0; i < 32; i++) {
      _helper.addDocument("\"aaa aaa\" . \"aaa\" \"aaa\" .");
      _helper.addDocument("\"aaa bbb\" . \"aaa ccc\" .");
    }

    final Term term = new Term("content", "aaa");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    termPositions.skipTo(16);
    assertEquals(16, termPositions.doc());
    assertEquals(16, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());
    assertEquals(4, termPositions.freq());

    for (int i = 0; i < termPositions.freq(); i++) {
      assertEquals(i, termPositions.nextPosition());
    }
    assertEquals(DocTupCelIdSetIterator.NO_MORE_POS, termPositions.nextPosition());
  }

  @Test
  public void testSkipToCellNextPosition()
  throws Exception {
    for (int i = 0; i < 32; i++) {
      _helper.addDocument("\"aaa aaa\" . \"aaa\" \"aaa\" .");
      _helper.addDocument("\"aaa bbb\" . \"aaa ccc\" .");
    }

    final Term term = new Term("content", "aaa");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    termPositions.skipTo(16, 1, 0);
    assertEquals(16, termPositions.doc());
    assertEquals(16, termPositions.entity());
    assertEquals(1, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(2, termPositions.pos());
    assertEquals(4, termPositions.freq());

    assertEquals(3, termPositions.nextPosition());
    assertEquals(1, termPositions.tuple());
    assertEquals(1, termPositions.cell());
    assertEquals(3, termPositions.pos());

    // end of the list, should return NO_MORE_POS
    assertEquals(DocTupCelIdSetIterator.NO_MORE_POS, termPositions.nextPosition());
  }

  @Test
  public void testSkipToNext()
  throws Exception {
    for (int i = 0; i < 32; i++) {
      _helper.addDocument("\"aaa aaa\" . \"aaa\" \"aaa\" .");
      _helper.addDocument("\"aaa bbb\" . \"aaa ccc\" .");
    }

    final Term term = new Term("content", "aaa");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    termPositions.next();
    assertEquals(0, termPositions.doc());
    assertEquals(0, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());
    assertEquals(4, termPositions.freq());

    termPositions.skipTo(16);
    assertEquals(16, termPositions.doc());
    assertEquals(16, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());
    assertEquals(4, termPositions.freq());

    termPositions.next();
    assertEquals(17, termPositions.doc());
    assertEquals(17, termPositions.entity());
    assertEquals(-1, termPositions.tuple());
    assertEquals(-1, termPositions.cell());
    assertEquals(-1, termPositions.pos());
    assertEquals(2, termPositions.freq());
  }

  @Test
  public void testSkipToNonExistingEntityTupleCell()
  throws Exception {
    for (int i = 0; i < 16; i++) {
      _helper.addDocument("\"aaa aaa\" . \"aaa\" \"aaa\" .");
      _helper.addDocument("\"aaa bbb\" . \"aaa ccc\" .");
    }

    final Term term = new Term("content", "aaa");
    final IndexReader reader = _helper.getIndexReader();
    final SirenTermPositions termPositions = new SirenTermPositions(reader.termPositions(term));

    // does not exist, should skip to entity 17 and to the first cell
    assertTrue(termPositions.skipTo(16, 3, 2));
    assertEquals(17, termPositions.doc());
    assertEquals(17, termPositions.entity());
    assertEquals(0, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(-1, termPositions.dataset());
    assertEquals(0, termPositions.pos());

    // does not exist, should skip to entity 19 and to the first cell
    assertTrue(termPositions.skipTo(18, 2, 2));
    assertEquals(19, termPositions.doc());
    assertEquals(19, termPositions.entity());
    assertEquals(0, termPositions.tuple());
    assertEquals(0, termPositions.cell());
    assertEquals(-1, termPositions.dataset());
    assertEquals(0, termPositions.pos());

    assertFalse(termPositions.skipTo(31, 2, 0)); // does not exist, reach end of list: should return false
  }

}
