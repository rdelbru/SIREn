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

import java.io.IOException;
import java.util.Collection;

import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;

/**
 * The {@link Scorer} class that defines the interface for iterating
 * over an ordered list of documents matching a {@link NodeQuery}.
 */
class LuceneProxyNodeScorer extends Scorer {

  private int              lastDoc = -1;
  private float            score;
  private int              freq;
  private final NodeScorer scorer;

  public LuceneProxyNodeScorer(final NodeScorer scorer) {
    super(scorer.getWeight());
    this.scorer = scorer;
  }

  /**
   * Scores and collects all matching documents.
   *
   * @param collector
   *          The collector to which all matching documents are passed through.
   */
  @Override
  public void score(final Collector collector) throws IOException {
    collector.setScorer(this);
    while (this.nextDoc() != NO_MORE_DOCS) {
      collector.collect(this.docID());
    }
  }

  /**
   * Expert: Collects matching documents in a range. Hook for optimization. Note
   * that {@link #nextDoc()} must be called once before this method is
   * called for the first time.
   *
   * @param collector
   *          The collector to which all matching documents are passed through.
   * @param max
   *          Do not score documents past this.
   * @return true if more matching documents may remain.
   */
  @Override
  public boolean score(final Collector collector, final int max, final int firstDocID)
  throws IOException {
    // firstDocID is ignored since nextDocument() sets 'currentDoc'
    collector.setScorer(this);
    while (this.docID() < max) {
      collector.collect(this.docID());
      if (this.nextDoc() == NO_MORE_DOCS) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int docID() {
    return scorer.doc();
  }

  @Override
  public int advance(final int target)
  throws IOException {
    if (scorer.skipToCandidate(target)) {
      do {
        if (scorer.nextNode()) {
          return this.docID();
        }
      } while (scorer.nextCandidateDocument());
    }
    return NO_MORE_DOCS;
  }

  @Override
  public int nextDoc()
  throws IOException {
    while (scorer.nextCandidateDocument()) {
      if (scorer.nextNode()) { // check if there is at least 1 node that matches the query
        return this.docID();
      }
    }
    return NO_MORE_DOCS;
  }

  @Override
  public float score()
  throws IOException {
    this.computeScoreAndFreq();
    return score;
  }

  /**
   * Returns number of matches for the current document. This returns a float
   * (not int) because SloppyPhraseScorer discounts its freq according to how
   * "sloppy" the match was.
   * <p>
   * Only valid after calling {@link #nextDoc()} or {@link #advance(int)}
   */
  @Override
  public float freq()
  throws IOException {
    this.computeScoreAndFreq();
    return freq;
  }

  @Override
  public Collection<ChildScorer> getChildren() {
    return scorer.getChildren();
  }

  /**
   * Compute the score and the frequency of the current document
   * @throws IOException
   */
  private void computeScoreAndFreq()
  throws IOException {
    final int doc = this.docID();

    if (doc != lastDoc) {
      lastDoc = doc;
      score = 0;
      freq = 0;

      do { // nextNode() was already called in nextDoc() or in advance()
        score += scorer.scoreInNode();
        freq += scorer.freqInNode();
      } while (scorer.nextNode());
    }
  }

}
