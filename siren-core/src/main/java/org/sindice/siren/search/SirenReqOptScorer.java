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
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;

/**
 * A Scorer for queries with a required part and an optional part. Delays
 * advance() on the optional part until a score() is needed. <br>
 * <p> Code taken from {@link ReqOptsumScorer} and adapted for the Siren use
 * case.
 */
class SirenReqOptScorer
extends SirenScorer {

  /**
   * The required scorer passed from the constructor. It is set to null as soon
   * as its nextDoc() or advance() returns false.
   */
  private final SirenScorer reqScorer;

  /**
   * The optional scorer passed from the constructor, and used for boosting score.
   * It is set to null as soon as its nextDoc() or advance() returns false.
   */
  private SirenScorer optScorer;

  private boolean firstTimeOptScorer = true;

  /**
   * Construct a <code>SirenReqOptScorer</code>.
   *
   * @param reqScorer
   *          The required scorer. This must match.
   * @param optScorer
   *          The optional scorer. This is used for scoring only.
   */
  public SirenReqOptScorer(final SirenScorer reqScorer, final SirenScorer optScorer) {
    super(null, null); // No similarity used.
    this.reqScorer = reqScorer;
    this.optScorer = optScorer;
  }

  @Override
  public int nextDoc() throws IOException {
    return reqScorer.nextDoc();
  }

  @Override
  public int nextPosition() throws IOException {
    return reqScorer.nextPosition();
  }

  @Override
  public int advance(final int entity) throws IOException {
    return reqScorer.advance(entity);
  }

  @Override
  public int advance(final int entity, final int tuple)
  throws IOException {
    return reqScorer.advance(entity, tuple);
  }

  @Override
  public int advance(final int entity, final int tuple, final int cell)
  throws IOException {
    return reqScorer.advance(entity, tuple, cell);
  }

  @Override
  public int dataset() {
    return reqScorer.dataset();
  }

  @Override
  public int docID() {
    return reqScorer.docID();
  }

  @Override
  public int entity() {
    return reqScorer.entity();
  }

  @Override
  public int tuple() {
    return reqScorer.tuple();
  }

  @Override
  public int cell() {
    return reqScorer.cell();
  }

  @Override
  public int pos() {
    return reqScorer.pos();
  }

  /**
   * Returns the score of the current entity matching the query. Initially
   * invalid, until {@link #nextDoc()} is called the first time.
   *
   * @return The score of the required scorer, eventually increased by the score
   *         of the optional scorer when it also matches the current entity.
   */
  @Override
  public float score()
  throws IOException {
    final int curEntity = reqScorer.entity();
    final int curTuple = reqScorer.tuple();
    final int curCell= reqScorer.cell();
    final float reqScore = reqScorer.score();

    if (firstTimeOptScorer) {
      firstTimeOptScorer = false;
      // Advance to the matching cell
      if (optScorer.advance(curEntity, curTuple, curCell) == NO_MORE_DOCS) {
        optScorer = null;
        return reqScore;
      }
    }
    else if (optScorer == null) {
      return reqScore;
    }
    else if ((optScorer.entity() < curEntity) &&
             (optScorer.advance(curEntity) == NO_MORE_DOCS)) {
      optScorer = null;
      return reqScore;
    }

    // If the optional scorer matches the same cell, increase the score
    return (optScorer.entity() == curEntity &&
            optScorer.tuple() == curTuple &&
            optScorer.cell() == curCell) ? reqScore + optScorer.score()
                                         : reqScore;
  }

  @Override
  public String toString() {
    return "SirenReqOptScorer(" + this.dataset() + "," + this.entity() + "," + this.tuple() + "," + this.cell() + ")";
  }

}
