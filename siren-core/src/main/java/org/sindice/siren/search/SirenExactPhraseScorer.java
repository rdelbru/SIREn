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
  public int doNextPosition() throws IOException {
    while (first.tuple() < last.tuple() ||
          (first.tuple() == last.tuple() && first.cell() < last.cell()) ||
          (first.tuple() == last.tuple() && first.cell() == last.cell() && first.pos() < last.pos())) {
      do {
        if (first.nextPosition() == NO_MORE_POS)
          return NO_MORE_POS;
      } while (first.tuple() < last.tuple() ||
          (first.tuple() == last.tuple() && first.cell() < last.cell()) ||
          (first.tuple() == last.tuple() && first.cell() == last.cell() && first.pos() < last.pos()));
      this.firstToLast();
    }
    // all equal: a match
    tuple = first.tuple();
    cell = first.cell();
    pos = first.pos();
    occurrences++; // increase occurrences
    return pos;
  }

}
