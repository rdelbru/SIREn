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

import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.must;
import static org.sindice.siren.search.AbstractTestSirenScorer.NodeBooleanQueryBuilder.nbq;
import static org.sindice.siren.search.AbstractTestSirenScorer.TupleQueryBuilder.tuple;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.util.BasicSirenTestCase;
import org.sindice.siren.util.XSDDatatype;

public class TestBooleanQuery extends BasicSirenTestCase {

  @Override
  protected void configure() throws IOException {
    this.setAnalyzer(AnalyzerType.TUPLE);
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    ((TupleAnalyzer) analyzer).registerDatatype(XSDDatatype.XSD_ANY_URI.toCharArray(), uriAnalyzer);
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  @Test
  public void testReqTuple() throws CorruptIndexException, IOException {
    for (int i = 0; i < 10; i++) {
      this.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . ");
      this.addDocument("<subj> <aaa> <bbb> . ");
    }

    final Query nested1 = tuple().with(nbq(must("aaa")).bound(1,1))
                                 .with(nbq(must("bbb")).bound(2,2))
                                 .getLuceneProxyQuery();

    final Query nested2 = tuple().with(nbq(must("ccc")).bound(1,1))
                                 .with(nbq(must("ddd")).bound(2,2))
                                 .getLuceneProxyQuery();

    final BooleanQuery q = new BooleanQuery();
    q.add(nested1, Occur.MUST);
    q.add(nested2, Occur.MUST);

    assertEquals(10, searcher.search(q, 10).totalHits);
  }

  @Test
  public void testReqOptTuple() throws CorruptIndexException, IOException {
    for (int i = 0; i < 10; i++) {
      this.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . ");
      this.addDocument("<subj> <aaa> <bbb> . ");
    }

    final Query nested1 = tuple().with(nbq(must("aaa")).bound(1,1))
                                 .with(nbq(must("bbb")).bound(2,2))
                                 .getLuceneProxyQuery();

    final Query nested2 = tuple().with(nbq(must("ccc")).bound(1,1))
                                 .with(nbq(must("ddd")).bound(2,2))
                                 .getLuceneProxyQuery();

    final BooleanQuery q = new BooleanQuery();
    q.add(nested1, Occur.MUST);
    q.add(nested2, Occur.SHOULD);

    assertEquals(20, searcher.search(q, 10).totalHits);
  }

  @Test
  public void testReqExclTuple() throws CorruptIndexException, IOException {
    for (int i = 0; i < 10; i++) {
      this.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . <subj> <eee> <fff> . ");
      this.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . <subj> <eee> <ggg> . ");
    }

    final Query nested1 = tuple().with(nbq(must("eee")).bound(1,1))
                                 .with(nbq(must("ggg")).bound(2,2))
                                 .getLuceneProxyQuery();

    final Query nested2 = tuple().with(nbq(must("aaa")).bound(1,1))
                                 .with(nbq(must("bbb")).bound(2,2))
                                 .getLuceneProxyQuery();

    final Query nested3 = tuple().with(nbq(must("ccc")).bound(1,1))
                                 .with(nbq(must("ddd")).bound(2,2))
                                 .getLuceneProxyQuery();

    final BooleanQuery q = new BooleanQuery();
    q.add(nested1, Occur.MUST_NOT);
    q.add(nested2, Occur.MUST);
    q.add(nested3, Occur.MUST);

    assertEquals(10, searcher.search(q, 10).totalHits);
  }

  @Test
  public void testReqExclCell() throws CorruptIndexException, IOException {
    for (int i = 0; i < 10; i++) {
      this.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . <subj> <eee> <fff> . ");
      this.addDocument("<subj> <aaa> <bbb> . <subj> <ccc> <ddd> . <subj> <eee> <ggg> . ");
    }

    final Query nested1 = nbq(must("aaa")).bound(1,1).getLuceneProxyQuery();
    final Query nested2 = nbq(must("bbb")).bound(2,2).getLuceneProxyQuery();
    final Query nested3 = nbq(must("ggg")).bound(2,2).getLuceneProxyQuery();

    final BooleanQuery q = new BooleanQuery();
    q.add(nested3, Occur.MUST_NOT);
    q.add(nested1, Occur.MUST);
    q.add(nested2, Occur.MUST);

    assertEquals(10, searcher.search(q, 10).totalHits);
  }

}
