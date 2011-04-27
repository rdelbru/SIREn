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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.TermPositions;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;

class SirenExactPhraseScorer
extends SirenPhraseScorer {

  protected final List<Integer> tuples = new ArrayList<Integer>();
  protected final List<Integer> cells = new ArrayList<Integer>();

  SirenExactPhraseScorer(final Weight weight, final TermPositions[] docsEnums, final int[] offsets,
                         final Similarity similarity, final byte[] norms) {
    super(weight, docsEnums, offsets, similarity, norms);
  }

  @Override
  protected final int[][] phraseOccurrences()
  throws IOException {
    // sort list with pq
    pq.clear();
    for (SirenPhrasePositions pp = first; pp != null; pp = pp.next) {
      pp.firstPosition();
      pq.add(pp); // build pq from list
    }
    this.pqToList(); // rebuild list from pq

    // for finding how many times the exact phrase is found in current
    // entity, just find how many times all SirenPhrasePosition's have exactly
    // the same position, tuple and cell.
    tuples.clear(); cells.clear();

    do { // find position w/ all terms
      while (first.tuple() < last.tuple() ||
            (first.tuple() == last.tuple() && first.cell() < last.cell()) ||
            (first.tuple() == last.tuple() && first.cell() == last.cell() && first.pos() < last.pos())) { // scan forward in first
        do {
          if (first.nextPosition() == NO_MORE_POS)
            return this.preprareOccurrences(occurrences);
        } while (first.tuple() < last.tuple() ||
                (first.tuple() == last.tuple() && first.cell() < last.cell()) ||
                (first.tuple() == last.tuple() && first.cell() == last.cell() && first.pos() < last.pos()));
        this.firstToLast();
      }
      // all equal: a match
      tuples.add(first.tuple());
      cells.add(first.cell());
    } while (last.nextPosition() != NO_MORE_POS);

    return this.preprareOccurrences(occurrences);
  }

  private int[][] preprareOccurrences(final int[][] occurrences) {
    occurrences[0] = new int[tuples.size()];
    for (int i = 0; i < tuples.size(); i++) {
      occurrences[0][i] = tuples.get(i);
    }
    occurrences[1] = new int[cells.size()];
    for (int i = 0; i < cells.size(); i++) {
      occurrences[1][i] = cells.get(i);
    }
    return occurrences;
  }

}
