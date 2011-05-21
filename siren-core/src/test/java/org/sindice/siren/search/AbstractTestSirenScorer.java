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
 * @author Renaud Delbru [ 21 Apr 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.sindice.siren.analysis.DeltaTupleAnalyzer;
import org.sindice.siren.analysis.DeltaTupleAnalyzer.URINormalisation;

public abstract class AbstractTestSirenScorer {

  protected QueryTestingHelper _helper = null;

  @Before
  public void setUp()
  throws Exception {
    final DeltaTupleAnalyzer analyzer = new DeltaTupleAnalyzer(new StandardAnalyzer(Version.LUCENE_31));
    analyzer.setURINormalisation(URINormalisation.FULL);
    _helper = new QueryTestingHelper(analyzer);
  }

  @After
  public void tearDown()
  throws Exception {
    _helper.close();
  }

  protected abstract void assertTo(final AssertFunctor functor, final String[] input,
                        final String[] terms, final int expectedNumDocs,
                        final int[] expectedNumTuples, final int[] expectedNumCells,
                        final int[] expectedEntityID,
                        final int[] expectedTupleID, final int[] expectedCellID,
                        final int[] expectedPos)
  throws Exception;

  protected abstract class AssertFunctor {
    protected abstract void run(final SirenScorer scorer, final int expectedNumDocs,
                                final int[] expectedNumTuples, final int[] expectedNumCells,
                                final int[] expectedEntityID,
                                final int[] expectedTupleID,
                                final int[] expectedCellID,
                                final int[] expectedPos)
    throws Exception;
  }

  protected class AssertNextEntityFunctor
  extends AssertFunctor {

    @Override
    protected void run(final SirenScorer scorer,final int expectedNumDocs,
                       final int[] expectedNumTuples, final int[] expectedNumCells,
                       final int[] expectedEntityID,
                       final int[] expectedTupleID, final int[] expectedCellID,
                       final int[] expectedPos)
    throws Exception {
      for (int i = 0; i < expectedNumDocs; i++) {
        assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
        assertEquals(expectedEntityID[i], scorer.entity());
        assertEquals(expectedTupleID[i], scorer.tuple());
        assertEquals(expectedCellID[i], scorer.cell());
        assertEquals(expectedPos[i], scorer.pos());
      }
      assertTrue(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
    }
  }

  protected class AssertNextPositionEntityFunctor
  extends AssertFunctor {

    @Override
    protected void run(final SirenScorer scorer,final int expectedNumDocs,
                       final int[] expectedNumTuples, final int[] expectedNumCells,
                       final int[] expectedEntityID,
                       final int[] expectedTupleID, final int[] expectedCellID,
                       final int[] expectedPos)
    throws Exception {
      int index = 0;
      for (int i = 0; i < expectedNumDocs; i++) {
        assertFalse(scorer.nextDoc() == DocIdSetIterator.NO_MORE_DOCS);
        for (int j = 0; j < expectedNumTuples[i]; j++) {
          for (int k = 0; k < expectedNumCells[j]; k++) {
            assertEquals(expectedEntityID[index], scorer.entity());
            assertEquals(expectedTupleID[index], scorer.tuple());
            assertEquals(expectedCellID[index], scorer.cell());
            assertEquals(expectedPos[index], scorer.pos());
            index++;
            if (k < expectedNumCells[j] - 1)
              assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
          }
          if (j < expectedNumTuples[i] - 1)
            assertFalse(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
        }
        assertTrue(scorer.nextPosition() == SirenIdIterator.NO_MORE_POS);
      }
    }
  }

  protected SirenExactPhraseScorer getExactScorer(final String field,
                                                  final String[] phraseTerms)
  throws IOException {
    final IndexReader reader = _helper.getIndexReader();
    final TermPositions[] tps = new TermPositions[phraseTerms.length];
    final int[] positions = new int[phraseTerms.length];
    for (int i = 0; i < phraseTerms.length; i++) {
      final TermPositions p = reader.termPositions(
        new Term(QueryTestingHelper.DEFAULT_FIELD, phraseTerms[i]));
      if (p == null) return null;
      tps[i] = p;
      positions[i] = i;
    }

    return new SirenExactPhraseScorer(new ConstantWeight(), tps, positions,
      new DefaultSimilarity(), reader.norms(field));
  }

  protected SirenExactPhraseScorer getExactScorer(final String field,
                                                  final int[] positions,
                                                  final String[] phraseTerms)
  throws IOException {
    final IndexReader reader = _helper.getIndexReader();
    final TermPositions[] tps = new TermPositions[phraseTerms.length];
    for (int i = 0; i < phraseTerms.length; i++) {
      final TermPositions p = reader.termPositions(
        new Term(QueryTestingHelper.DEFAULT_FIELD, phraseTerms[i]));
      if (p == null) return null;
      tps[i] = p;
    }

    return new SirenExactPhraseScorer(new ConstantWeight(), tps, positions,
    new DefaultSimilarity(), reader.norms(field));
  }

  protected SirenTermScorer getTermScorer(final String field,
                                          final String term)
  throws IOException {
    final IndexReader reader = _helper.getIndexReader();
    final TermPositions termPositions = reader.termPositions(
      new Term(QueryTestingHelper.DEFAULT_FIELD, term));
    return new SirenTermScorer(new ConstantWeight(), termPositions,
      new DefaultSimilarity(), reader.norms(field));
  }

  /**
   * Return a term scorer which is positioned to the first element, i.e.
   * {@link SirenScorer#next()} has been called one time.
   */
  protected SirenTermScorer getPositionedTermScorer(final String term)
  throws IOException {
    final IndexReader reader = _helper.getIndexReader();
    final TermPositions termPositions = reader.termPositions(new Term(
      QueryTestingHelper.DEFAULT_FIELD, term));
    final SirenTermScorer s = new SirenTermScorer(new ConstantWeight(), termPositions,
      new DefaultSimilarity(), reader.norms(QueryTestingHelper.DEFAULT_FIELD));
    assertNotSame(DocIdSetIterator.NO_MORE_DOCS, s.nextDoc());
    return s;
  }

  protected SirenConjunctionScorer getConjunctionScorer(final String[] terms)
  throws IOException {
    final SirenTermScorer[] scorers = new SirenTermScorer[terms.length];
    for (int i = 0; i < terms.length; i++) {
      scorers[i] = this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, terms[i]);
    }
    return new SirenConjunctionScorer(new DefaultSimilarity(), scorers);
  }

  protected SirenConjunctionScorer getConjunctionScorer(final String[][] phraseTerms)
  throws IOException {
    final SirenPhraseScorer[] scorers = new SirenPhraseScorer[phraseTerms.length];
    for (int i = 0; i < phraseTerms.length; i++) {
      scorers[i] = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, phraseTerms[i]);
    }
    return new SirenConjunctionScorer(new DefaultSimilarity(), scorers);
  }

  protected SirenDisjunctionScorer getDisjunctionScorer(final String[] terms)
  throws IOException {
    final SirenTermScorer[] scorers = new SirenTermScorer[terms.length];
    for (int i = 0; i < terms.length; i++) {
      scorers[i] = this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, terms[i]);
    }
    return new SirenDisjunctionScorer(new DefaultSimilarity(), scorers);
  }

  protected SirenReqExclScorer getReqExclScorer(final String reqTerm, final String exclTerm)
  throws IOException {
    final SirenTermScorer reqScorer = this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, reqTerm);
    final SirenTermScorer exclScorer = this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, exclTerm);
    return new SirenReqExclScorer(reqScorer, exclScorer);
  }

  protected SirenReqExclScorer getReqExclScorer(final String[] reqPhrase, final String[] exclPhrase)
  throws IOException {
    final SirenExactPhraseScorer reqScorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, reqPhrase);
    final SirenExactPhraseScorer exclScorer = this.getExactScorer(QueryTestingHelper.DEFAULT_FIELD, exclPhrase);
    return new SirenReqExclScorer(reqScorer, exclScorer);
  }

  protected SirenReqOptScorer getReqOptScorer(final String reqTerm, final String optTerm)
  throws IOException {
    final SirenTermScorer reqScorer = this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, reqTerm);
    final SirenTermScorer optScorer = this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, optTerm);
    return new SirenReqOptScorer(reqScorer, optScorer);
  }

  protected SirenBooleanScorer getBooleanScorer(final String[] reqTerms,
                                                final String[] optTerms,
                                                final String[] exclTerms)
  throws IOException {
    final SirenBooleanScorer scorer = new SirenBooleanScorer(new DefaultSimilarity());
    if (reqTerms != null) {
      for (final String term : reqTerms)
        scorer.add(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, term), true, false);
    }
    if (optTerms != null) {
      for (final String term : optTerms)
        scorer.add(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, term), false, false);
    }
    if (exclTerms != null) {
      for (final String term : exclTerms)
        scorer.add(this.getTermScorer(QueryTestingHelper.DEFAULT_FIELD, term), false, true);
    }
    return scorer;
  }

  protected SirenCellScorer getCellScorer(final int startCell, final int endCell,
                                          final String[] reqTerms, final String[] optTerms,
                                          final String[] exclTerms)
  throws IOException {
    final SirenCellScorer cscorer = new SirenCellScorer(new DefaultSimilarity(), startCell, endCell);
    final SirenBooleanScorer bscorer = this.getBooleanScorer(reqTerms, optTerms, exclTerms);
    cscorer.setScorer(bscorer);
    return cscorer;
  }

  protected class ConstantWeight extends Weight {

    private static final long serialVersionUID = 1L;

    @Override
    public float getValue() { return 1; }

    @Override
    public Explanation explain(final IndexReader reader, final int doc)
    throws IOException { return null; }

    @Override
    public Query getQuery() { return null; }

    @Override
    public void normalize(final float norm) {}

    @Override
    public float sumOfSquaredWeights() throws IOException { return 0; }

    @Override
    public Scorer scorer(final IndexReader reader, final boolean scoreDocsInOrder,
                         final boolean topScorer)
    throws IOException { return null; }
  }

}
