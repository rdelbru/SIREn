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
import org.sindice.siren.index.PositionsIterator;

/**
 * Implementation of the {@link NodePhraseScorer} for exact phrase query.
 */
class NodeExactPhraseScorer extends NodePhraseScorer {

  NodeExactPhraseScorer(final Weight weight,
                        final NodePhraseQuery.PostingsAndPosition[] postings,
                        final Similarity.SloppySimScorer sloppyScorer,
                        final Similarity.ExactSimScorer exactScorer)
  throws IOException {
    super(weight, postings, sloppyScorer, exactScorer);
  }

  @Override
  int phraseFreq() throws IOException {
    int freq = 1; // set to one to count the first phrase found
    while (this.nextPhrase()) {
      freq++;
    }
    return freq;
  }

  @Override
  public String toString() {
    return "NodeExactPhraseScorer(" + weight + "," + this.doc() + "," + this.node() + ")";
  }

  @Override
  boolean nextPhrase() throws IOException {
    int first = 0;
    NodePhrasePosition lastPosition = phrasePositions[phrasePositions.length - 1];
    NodePhrasePosition firstPosition = phrasePositions[first];

    // scan forward in last
    if (lastPosition.pos == PositionsIterator.NO_MORE_POS || !lastPosition.nextPosition()) {
      return false;
    }

    while (firstPosition.pos < lastPosition.pos) {
      do {
        if (!firstPosition.nextPosition()) {  // scan forward in first
          return false;
        }
      } while (firstPosition.pos < lastPosition.pos);
      lastPosition = firstPosition;
      first = (first == (phrasePositions.length - 1)) ? 0 : first + 1;
      firstPosition = phrasePositions[first];
    }
    // all equal: a match
    return true;
  }

  @Override
  boolean firstPhrase() throws IOException {
    for (final NodePhrasePosition phrasePosition : phrasePositions) {
      phrasePosition.init();
    }
    // check for phrase
    return this.nextPhrase();
  }

}
