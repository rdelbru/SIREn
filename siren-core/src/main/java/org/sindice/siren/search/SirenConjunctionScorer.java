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
 * @author Renaud Delbru [ 27 Apr 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.apache.lucene.search.Similarity;

/**
 * Scorer for conjunctions, sets of queries, within a cell. All the queries
 * are required.
 * <p> A cell is considered matching if all the queries match in the same cell.
 * The {@link #nextDoc()} method iterates over entities that contain one
 * or more matching cells. The {@link #nextPosition()} allows to iterate
 * over the cells within an entity.
 * <p> Code taken from {@link ConjunctionScorer} and adapted for the Siren use
 * case.
 **/
class SirenConjunctionScorer
extends SirenScorer {

  private final SirenPrimitiveScorer[] scorers;

  /** Flag to know if next or skipTo has been called */
  private boolean             firstTime = true;

  private boolean             more;

  private final float         coord;

  private final int           lastDataset   = -1;

  private int                 lastEntity   = -1;

  private int                 lastTuple   = -1;

  private int                 lastCell   = -1;

  public SirenConjunctionScorer(final Similarity similarity,
                                final Collection<SirenPrimitiveScorer> scorers)
  throws IOException {
    this(similarity, scorers.toArray(new SirenPrimitiveScorer[scorers.size()]));
  }

  public SirenConjunctionScorer(final Similarity similarity,
                                final SirenPrimitiveScorer[] scorers)
  throws IOException {
    super(similarity);
    this.scorers = scorers;
    coord = this.getSimilarity().coord(this.scorers.length, this.scorers.length);
  }

  @Override
  public int dataset() {
    return lastDataset;
  }

  @Override
  public int docID() {
    return lastEntity;
  }

  @Override
  public int entity() {
    return lastEntity;
  }

  @Override
  public int tuple() {
    return lastTuple;
  }

  @Override
  public int cell() {
    return lastCell;
  }

  /**
   * Position is invalid in high-level scorers. It will always return
   * {@link Integer.MAX_VALUE}.
   */
  @Override
  public int pos() {
    return Integer.MAX_VALUE;
  }

  @Override
  public int nextDoc()
  throws IOException {
    if (firstTime)
      return this.init(0);
    else if (more) {
      more = (scorers[(scorers.length - 1)].nextDoc() != NO_MORE_DOCS);
    }
    return this.doNext();
  }

  /**
   * Perform a next without initial increment
   */
  private int doNext()
  throws IOException {
    int first = 0;
    SirenScorer lastScorer = scorers[scorers.length - 1];
    SirenScorer firstScorer = scorers[first];

    while (more &&
           (firstScorer.entity() < lastScorer.entity() ||
           (firstScorer.entity() == lastScorer.entity() && firstScorer.tuple() < lastScorer.tuple()) ||
           (firstScorer.entity() == lastScorer.entity() && firstScorer.tuple() == lastScorer.tuple() && firstScorer.cell() < lastScorer.cell()))) {
      more = (firstScorer.advance(lastScorer.entity(), lastScorer.tuple(), lastScorer.cell()) != NO_MORE_DOCS);
      lastScorer = firstScorer;
      first = (first == (scorers.length - 1)) ? 0 : first + 1;
      firstScorer = scorers[first];
    }

    if (more) {
      lastEntity = lastScorer.entity();
      lastTuple = lastScorer.tuple();
      lastCell = lastScorer.cell();
      return lastEntity;
    }
    else {
      lastEntity = lastTuple = lastCell = Integer.MAX_VALUE; // sentinel value
      return NO_MORE_DOCS;
    }
  }

  @Override
  public int nextPosition() throws IOException {
    int first = 0;
    SirenScorer lastScorer = scorers[scorers.length - 1];
    SirenScorer firstScorer = scorers[first];

    if (lastScorer.nextPosition() == NO_MORE_POS) { // scan forward in last
      return NO_MORE_POS;
    }

    while (firstScorer.tuple() < lastScorer.tuple() ||
          (firstScorer.tuple() == lastScorer.tuple() && firstScorer.cell() < lastScorer.cell())) { // scan forward in first
      do {
        if (firstScorer.nextPosition() == NO_MORE_POS)
          return NO_MORE_POS;
      } while (firstScorer.tuple() < lastScorer.tuple() ||
              (firstScorer.tuple() == lastScorer.tuple() && firstScorer.cell() < lastScorer.cell()));
      lastScorer = firstScorer;
      first = (first == (scorers.length - 1)) ? 0 : first + 1;
      firstScorer = scorers[first];
    }
    // all equal: a match
    lastTuple = firstScorer.tuple();
    lastCell = firstScorer.cell();
    return -1; // position is invalid in this scorer, returns -1
  }

  @Override
  public int advance(final int entityID) throws IOException {
    if (firstTime)
      return this.init(entityID);
    else if (more) {
      more = (scorers[(scorers.length - 1)].advance(entityID) != NO_MORE_DOCS);
    }
    return this.doNext();
  }

  @Override
  public int advance(final int entityID, final int tupleID)
  throws IOException {
    if (firstTime)
      return this.init(entityID); //TODO: should not skip to the right tuple in certain case
    else if (more) {
      more = (scorers[(scorers.length - 1)].advance(entityID, tupleID) != NO_MORE_DOCS);
    }
    return this.doNext();
  }

  @Override
  public int advance(final int entityID, final int tupleID, final int cellID)
  throws IOException {
    if (firstTime)
      return this.init(entityID); //TODO: should not skip to the right position in certain case
    else if (more) {
      more = (scorers[(scorers.length - 1)].advance(entityID, tupleID, cellID) != NO_MORE_DOCS);
    }
    return this.doNext();
  }

  // Note... most of this could be done in the constructor
  // thus skipping a check for firstTime per call to next() and skipTo()
  private int init(final int target)
  throws IOException {
    firstTime = false;
    more = scorers.length > 1;

    for (final SirenScorer scorer : scorers) {
      more = target == 0 ?
                          (scorer.nextDoc() != NO_MORE_DOCS)
                         :
                          (scorer.advance(target) != NO_MORE_DOCS);

      if (!more) return NO_MORE_DOCS;
    }

    // Sort the array the first time...
    // We don't need to sort the array in any future calls because we know
    // it will already start off sorted (all scorers on same doc).

    // note that this comparator is not consistent with equals!
    Arrays.sort(scorers, new Comparator<SirenScorer>() { // sort the array
        public int compare(final SirenScorer o1, final SirenScorer o2) {
          if (o1.entity() != o2.entity())
            return o1.entity() - o2.entity();
          else if (o1.tuple() != o2.tuple())
            return o1.tuple() - o2.tuple();
          else
            return o1.cell() - o2.cell();
        }
      });

    this.doNext();

    // If first-time skip distance is any predictor of
    // scorer sparseness, then we should always try to skip first on
    // those scorers.
    // Keep last scorer in it's last place (it will be the first
    // to be skipped on), but reverse all of the others so that
    // they will be skipped on in order of original high skip.
    final int end = (scorers.length - 1);
    for (int i = 0; i < (end >> 1); i++) {
      final SirenPrimitiveScorer tmp = scorers[i];
      scorers[i] = scorers[end - i - 1];
      scorers[end - i - 1] = tmp;
    }

    if (more) {
      return lastEntity;
    }
    else {
      return NO_MORE_DOCS;
    }
  }

  @Override
  public float score()
  throws IOException {
    float sum = 0.0f;
    for (final SirenPrimitiveScorer scorer : scorers) {
      sum += scorer.score();
    }
    return sum * coord;
  }

  @Override
  public String toString() {
    return "SirenConjunctionScorer(" + this.dataset() + "," + this.entity() + "," + this.tuple() + "," + this.cell() + ")";
  }

}
