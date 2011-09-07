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
