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

import org.apache.lucene.search.Weight;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.IntsRef;

/**
 * Abstraction of a {@link NodeScorer} for {@link NodePhraseQuery}.
 *
 * <p>
 *
 * A node is considered matching if it contains the phrase-query terms at
 * "valid" positions. What "valid positions" are depends on the type of the
 * phrase query: for an exact phrase query terms are required to appear in
 * adjacent locations. The method {@link #phraseFreq()}, when invoked, compute all
 * the occurrences of the phrase within the node. A non
 * zero frequency means a match.
 */
abstract class NodePhraseScorer extends NodeScorer {

  NodePhrasePosition[] phrasePositions;

  final Similarity.SloppySimScorer sloppyScorer;
  final Similarity.ExactSimScorer exactScorer;

  final NodeConjunctionScorer conjunctionScorer;

  /**
   * Phrase frequency in current doc as computed by phraseFreq().
   */
  private float freq = 0.0f;

  NodePhraseScorer(final Weight weight,
                   final NodePhraseQuery.PostingsAndPosition[] postings,
                   final Similarity.SloppySimScorer sloppyScorer,
                   final Similarity.ExactSimScorer exactScorer)
  throws IOException {
    super(weight);
    this.sloppyScorer = sloppyScorer;
    this.exactScorer = exactScorer;

    // convert tps to a list of phrase positions.
    // note: phrase-position differs from term-position in that its position
    // reflects the phrase offset: pp.pos = tp.pos - offset.
    // this allows to easily identify a matching (exact) phrase
    // when all PhrasePositions have exactly the same position.
    phrasePositions = new NodePhrasePosition[postings.length];
    for (int i = 0; i < postings.length; i++) {
      phrasePositions[i] = new NodePhrasePosition(postings[i].postings, postings[i].position);
    }

    // create node conjunction scorer
    final NodeScorer[] scorers = new NodeScorer[postings.length];
    for (int i = 0; i < postings.length; i++) {
      scorers[i] = new NodeTermScorer(weight, postings[i].postings, exactScorer);
    }
    conjunctionScorer = new NodeConjunctionScorer(weight, 1.0f, scorers);
  }

  @Override
  public int doc() {
    return conjunctionScorer.doc();
  }

  @Override
  public IntsRef node() {
    return conjunctionScorer.node();
  }

  @Override
  public boolean nextCandidateDocument() throws IOException {
    freq = 0.0f; // reset freq
    return conjunctionScorer.nextCandidateDocument();
  }

  @Override
  public boolean skipToCandidate(final int target) throws IOException {
    return conjunctionScorer.skipToCandidate(target);
  }

  @Override
  public boolean nextNode() throws IOException {
    freq = 0.0f; // reset freq
    while (conjunctionScorer.nextNode()) { // if node contains phrase-query terms
      if (this.firstPhrase()) { // check for phrase
        freq = this.phraseFreq(); // compute frequency of the phrase
        return true;
      }
    }
    return false;
  }

  abstract boolean firstPhrase() throws IOException;

  abstract boolean nextPhrase() throws IOException;

  abstract int phraseFreq() throws IOException;

  /**
   * phrase frequency in current node as computed by phraseFreq().
   */
  @Override
  public float freqInNode()
  throws IOException {
    return freq;
  }

  @Override
  public float scoreInNode()
  throws IOException {
    return conjunctionScorer.scoreInNode();
  }

}
