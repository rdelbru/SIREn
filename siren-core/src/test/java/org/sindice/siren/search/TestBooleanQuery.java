/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
 * @author Renaud Delbru [ 8 Feb 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;


import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.LuceneTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;

public class TestBooleanQuery extends LuceneTestCase {

  private QueryTestingHelper _helper = null;

  @Before
  public void setUp()
  throws Exception {
    super.setUp();
    _helper = new QueryTestingHelper(new TupleAnalyzer(TEST_VERSION_CURRENT,
      new StandardAnalyzer(TEST_VERSION_CURRENT),
      new AnyURIAnalyzer(TEST_VERSION_CURRENT)));
  }

  @After
  public void tearDown()
  throws Exception {
    super.tearDown();
    _helper.close();
  }

  @Test
  public void testReqTuple() throws CorruptIndexException, IOException {
    for (int i = 0; i < 10; i++) {
      _helper.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . ");
      _helper.addDocument("<subj> <aaa> <bbb> . ");
    }

    SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "aaa")), SirenBooleanClause.Occur.MUST);
    SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(1);

    SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "bbb")), SirenBooleanClause.Occur.MUST);
    SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(2);

    final SirenTupleQuery reqTuple1 = new SirenTupleQuery();
    reqTuple1.add(cq1, SirenTupleClause.Occur.MUST);
    reqTuple1.add(cq2, SirenTupleClause.Occur.MUST);

    bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ccc")), SirenBooleanClause.Occur.MUST);
    cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(1);

    bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ddd")), SirenBooleanClause.Occur.MUST);
    cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(2);

    final SirenTupleQuery optTuple = new SirenTupleQuery();
    optTuple.add(cq1, SirenTupleClause.Occur.MUST);
    optTuple.add(cq2, SirenTupleClause.Occur.MUST);

    final BooleanQuery q = new BooleanQuery();
    q.add(reqTuple1, Occur.MUST);
    q.add(optTuple, Occur.MUST);

    assertEquals(10, _helper.search(q).length);
  }

  @Test
  public void testReqOptTuple() throws CorruptIndexException, IOException {
    for (int i = 0; i < 10; i++) {
      _helper.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . ");
      _helper.addDocument("<subj> <aaa> <bbb> . ");
    }

    SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "aaa")), SirenBooleanClause.Occur.MUST);
    SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(1);

    SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "bbb")), SirenBooleanClause.Occur.MUST);
    SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(2);

    final SirenTupleQuery reqTuple1 = new SirenTupleQuery();
    reqTuple1.add(cq1, SirenTupleClause.Occur.MUST);
    reqTuple1.add(cq2, SirenTupleClause.Occur.MUST);

    bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ccc")), SirenBooleanClause.Occur.MUST);
    cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(1);

    bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ddd")), SirenBooleanClause.Occur.MUST);
    cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(2);

    final SirenTupleQuery optTuple = new SirenTupleQuery();
    optTuple.add(cq1, SirenTupleClause.Occur.MUST);
    optTuple.add(cq2, SirenTupleClause.Occur.MUST);

    final BooleanQuery q = new BooleanQuery();
    q.add(reqTuple1, Occur.MUST);
    q.add(optTuple, Occur.SHOULD);

    assertEquals(20, _helper.search(q).length);
  }

  @Test
  public void testReqExclTuple() throws CorruptIndexException, IOException {
    for (int i = 0; i < 10; i++) {
      _helper.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . <subj> <eee> <fff> . ");
      _helper.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . <subj> <eee> <ggg> . ");
    }

    SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "eee")), SirenBooleanClause.Occur.MUST);
    SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(1);

    SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ggg")), SirenBooleanClause.Occur.MUST);
    SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(2);

    final SirenTupleQuery exclTuple = new SirenTupleQuery();
    exclTuple.add(cq1, SirenTupleClause.Occur.MUST);
    exclTuple.add(cq2, SirenTupleClause.Occur.MUST);

    bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "aaa")), SirenBooleanClause.Occur.MUST);
    cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(1);

    bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "bbb")), SirenBooleanClause.Occur.MUST);
    cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(2);

    final SirenTupleQuery reqTuple1 = new SirenTupleQuery();
    reqTuple1.add(cq1, SirenTupleClause.Occur.MUST);
    reqTuple1.add(cq2, SirenTupleClause.Occur.MUST);

    bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ccc")), SirenBooleanClause.Occur.MUST);
    cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(1);

    bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ddd")), SirenBooleanClause.Occur.MUST);
    cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(2);

    final SirenTupleQuery reqTuple2 = new SirenTupleQuery();
    reqTuple2.add(cq1, SirenTupleClause.Occur.MUST);
    reqTuple2.add(cq2, SirenTupleClause.Occur.MUST);

    final BooleanQuery q = new BooleanQuery();
    q.add(exclTuple, Occur.MUST_NOT);
    q.add(reqTuple1, Occur.MUST);
    q.add(reqTuple2, Occur.MUST);

    assertEquals(10, _helper.search(q).length);
  }

  @Test
  public void testReqExclCell() throws CorruptIndexException, IOException {
    for (int i = 0; i < 10; i++) {
      _helper.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . <subj> <eee> <fff> . ");
      _helper.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . <subj> <eee> <ggg> . ");
    }

    final SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "aaa")), SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(1);

    final SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "bbb")), SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(2);

    final SirenBooleanQuery bq3 = new SirenBooleanQuery();
    bq3.add(new SirenTermQuery(new Term(QueryTestingHelper.DEFAULT_FIELD, "ggg")), SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq3 = new SirenCellQuery(bq3);
    cq3.setConstraint(2);

    final BooleanQuery q = new BooleanQuery();
    q.add(cq3, Occur.MUST_NOT);
    q.add(cq1, Occur.MUST);
    q.add(cq2, Occur.MUST);

    assertEquals(10, _helper.search(q).length);
  }

}
