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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Similarity;
import org.sindice.siren.util.ScorerCellQueue;

/**
 * A Scorer for OR like queries within a cell, counterpart of
 * {@link SirenConjunctionScorer}.
 *
 * <p> Code taken from {@link DisjunctionSumScorer} and adapted for the Siren
 * use case.
 */
class SirenDisjunctionScorer
extends SirenScorer {

  /** The number of subscorers. */
  private final int                          nrScorers;

  /** The scorers. */
  protected final Collection<SirenPrimitiveScorer> scorers;

  /**
   * The scorerDocQueue contains all subscorers ordered by their current docID(),
   * with the minimum at the top. <br>
   * The scorerDocQueue is initialized the first time nextDoc() or advance() is
   * called. <br>
   * An exhausted scorer is immediately removed from the scorerDocQueue. <br>
   * If less than the minimumNrMatchers scorers remain in the scorerDocQueue
   * nextDoc() and advance() return false.
   * <p>
   * After each to call to nextDoc() or advance() <code>currentScore</code> is
   * the total score of the current matching doc, <code>nrMatchers</code> is the
   * number of matching scorers, and all scorers are after the matching doc, or
   * are exhausted.
   */
  private ScorerCellQueue                    scorerCellQueue = null;

  /** used to avoid size() method calls on scorerDocQueue */
  private int                                queueSize      = -1;

  /** The dataset that currently matches. */
  private final int                          dataset     = -1;

  /** The entity that currently matches. */
  private int                                entity     = -1;

  /** The tuple that currently matches. */
  private int                                tuple      = -1;

  /** The cell that currently matches. */
  private int                                cell       = -1;

  /** The number of subscorers that provide the current match. */
  protected int                              nrMatchers     = -1;

  private float                              currentScore   = Float.NaN;

  /**
   * Construct a <code>DisjunctionScorer</code>.
   *
   * @param subScorers
   *          A collection of at least two primitives scorers.
   */
  public SirenDisjunctionScorer(final Similarity similarity,
                                final Collection<SirenPrimitiveScorer> scorers) {
    super(similarity);
    nrScorers = scorers.size();
    if (nrScorers <= 1) {
      throw new IllegalArgumentException("There must be at least 2 subScorers");
    }
    this.scorers = scorers;
  }

  /**
   * Construct a <code>DisjunctionScorer</code>.
   *
   * @param scorers
   *          An array of at least two primitive scorers.
   */
  public SirenDisjunctionScorer(final Similarity similarity,
                                final SirenPrimitiveScorer[] scorers) {
    this(similarity, Arrays.asList(scorers));
  }

  /**
   * Called the first time next() or skipTo() is called to initialize
   * <code>scorerCellQueue</code>.
   */
  private void initScorerCellQueue()
  throws IOException {
    final Iterator<SirenPrimitiveScorer> si = scorers.iterator();
    scorerCellQueue = new ScorerCellQueue(nrScorers);
    queueSize = 0;
    while (si.hasNext()) {
      final SirenPrimitiveScorer se = si.next();
      if (se.nextDoc() != NO_MORE_DOCS) { // entity(), tuple() and cell () method will be used in scorerDocQueue.
        if (scorerCellQueue.insert(se)) {
          queueSize++;
        }
      }
    }
  }

  /**
   * Scores and collects all matching documents.
   *
   * @param hc
   *          The collector to which all matching documents are passed through
   *          {@link HitCollector#collect(int, float)}. <br>
   *          When this method is used the {@link #explain(int)} method should
   *          not be used.
   */
  @Override
  public void score(final Collector collector) throws IOException {
    collector.setScorer(this);
    while (this.nextDoc() != NO_MORE_DOCS) {
      collector.collect(entity);
    }
  }

  /**
   * Expert: Collects matching documents in a range. Hook for optimization. Note
   * that {@link #next()} must be called once before this method is called for
   * the first time.
   *
   * @param hc
   *          The collector to which all matching documents are passed through
   *          {@link HitCollector#collect(int, float)}.
   * @param max
   *          Do not score documents past this.
   * @return true if more matching documents may remain.
   */
  @Override
  protected boolean score(final Collector collector, final int max, final int firstDocID)
  throws IOException {
    // firstDocID is ignored since nextDoc() sets 'currentDoc'
    collector.setScorer(this);
    while (entity < max) {
      collector.collect(entity);
      if (this.nextDoc() == NO_MORE_DOCS) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int nextDoc() throws IOException {
    if (scorerCellQueue == null) {
      this.initScorerCellQueue();
      if ((nrMatchers = scorerCellQueue.nrMatches()) > 0) {
        entity = scorerCellQueue.topEntity();
        this.nextPosition(); // advance to the first position [SRN-24]
        return entity;
      }
      return NO_MORE_DOCS;
    }
    return this.advanceAfterCurrent();
  }

  /**
   * Advance to the next position.<br>
   * Set the cell and tuple information.<br>
   * Iterate over the queue, and count how many matchers there are. Increment
   * the score consequently.
   */
  @Override
  public int nextPosition() throws IOException {
    // if tuple or cell have been set to sentinel value, there is no more position
    if (scorerCellQueue.topTuple() == Integer.MAX_VALUE ||
        scorerCellQueue.topCell() == Integer.MAX_VALUE) {
      return NO_MORE_POS;
    }
    tuple = scorerCellQueue.topTuple();
    cell = scorerCellQueue.topCell();
    currentScore = 0;
    nrMatchers = 0;
    // Count how many matchers there are, and increment current score
    while (scorerCellQueue.topEntity() == entity &&
           scorerCellQueue.topTuple() == tuple &&
           scorerCellQueue.topCell() == cell) { // while top is a match, advance
      currentScore += scorerCellQueue.topScore();
      if (scorerCellQueue.topIncMatchers()) nrMatchers++;
      if (!scorerCellQueue.topNextPositionAndAdjust()) {
        return 0; // stop, no more position. position is invalid in this scorer,
                  // return 0.
                  // All positions in the queue are consumed. If nextPosition
                  // is called another time, we will return NO_MORE_POS.
      }
    }
    return 0; // position is invalid in this scorer, return 0.
  }

  /**
   * Advance all subscorers after the current document determined by the top of
   * the <code>scorerCellQueue</code> if all the subscorers are not exhausted. <br>
   * At least the top scorer with the minimum entity number will be advanced.
   *
   * @return true iff there is a match. <br>
   *         In case there is a match, </code>entity</code>,
   *         </code>currentScore</code>, and </code>nrMatchers</code>
   *         describe the match.
   */
  protected int advanceAfterCurrent()
  throws IOException {
    if (scorerCellQueue.size() > 0) {
      if ((queueSize -= scorerCellQueue.nextAndAdjustElsePop()) == 0) {
        return NO_MORE_DOCS;
      }

      entity = scorerCellQueue.topEntity();
      this.nextPosition(); // advance to the first position [SRN-24]
      return entity;
    }
    return NO_MORE_DOCS;
  }

  /**
   * Returns the score of the current document matching the query. Initially
   * invalid, until {@link #next()} is called the first time.
   */
  @Override
  public float score()
  throws IOException {
    return currentScore;
  }

  @Override
  public float scoreCell() {
    // Cell is invalid in this scorer
    return 0;
  }

  /**
   * Returns the number of subscorers matching the current document. Initially
   * invalid, until {@link #next()} is called the first time.
   */
  public int nrMatchers() {
    return nrMatchers;
  }

  /**
   * Skips to the first match (including the current) whose document number is
   * greater than or equal to a given target. <br>
   * When this method is used the {@link #explain(int)} method should not be
   * used. <br>
   * The implementation uses the skipTo() method on the subscorers.
   *
   * @param entityID
   *          The target entity number.
   * @return true iff there is such a match.
   */
  @Override
  public int advance(final int entityID) throws IOException {
    if (scorerCellQueue == null) {
      this.initScorerCellQueue();
    }
    if (entityID <= entity) {
      return entity;
    }
    while (queueSize > 0) {
      if (scorerCellQueue.topEntity() >= entityID) {
        entity = scorerCellQueue.topEntity();
        this.nextPosition(); // advance to the first position [SRN-24]
        return entity;
      }
      else if (!scorerCellQueue.topSkipToAndAdjustElsePop(entityID)) {
        if (--queueSize == 0) {
          return NO_MORE_DOCS;
        }
      }
    }
    return NO_MORE_DOCS;
  }

  /**
   * Skips to the first match (including the current) whose entity and tuple
   * numbers are greater than or equal to a given target. <br>
   * When this method is used the {@link #explain(int)} method should not be
   * used. <br>
   * The implementation uses the skipTo() method on the subscorers.
   *
   * @param entityID
   *          The target entity number.
   * @param tupleID
   *          The target tuple number.
   * @return true iff there is such a match.
   */
  @Override
  public int advance(final int entityID, final int tupleID)
  throws IOException {
    if (scorerCellQueue == null) {
      this.initScorerCellQueue();
    }
    if (entityID < entity || (entityID == entity && tupleID <= tuple)) {
      return entity;
    }
    while (queueSize > 0) {
      if (scorerCellQueue.topEntity() > entityID ||
         (scorerCellQueue.topEntity() == entityID && (scorerCellQueue.topTuple() != Integer.MAX_VALUE && scorerCellQueue.topTuple() >= tupleID))) {
        entity = scorerCellQueue.topEntity();
        this.nextPosition();
        return entity;
      }
      else if (!scorerCellQueue.topSkipToAndAdjustElsePop(entityID, tupleID)) {
        if (--queueSize == 0) {
          return NO_MORE_DOCS;
        }
      }
    }
    return NO_MORE_DOCS;
  }

  /**
   * Skips to the first match (including the current) whose entity, tuple and
   * cell numbers are greater than or equal to the given targets. <br>
   * When this method is used the {@link #explain(int)} method should not be
   * used. <br>
   * The implementation uses the skipTo() method on the subscorers.
   *
   * @param entityID
   *          The target entity number.
   * @param tupleID
   *          The target tuple number.
   * @param cellID
   *          The target cell number.
   * @return true iff there is such a match.
   */
  @Override
  public int advance(final int entityID, final int tupleID, final int cellID)
  throws IOException {
    if (scorerCellQueue == null) {
      this.initScorerCellQueue();
    }
    if (entityID < entity || (entityID == entity && tupleID <= tuple) ||
       (entityID == entity && tupleID == tuple && cellID <= cell)) {
      return entity;
    }
    while (queueSize > 0) {
      if (scorerCellQueue.topEntity() > entityID ||
         (scorerCellQueue.topEntity() == entityID && (scorerCellQueue.topTuple() != Integer.MAX_VALUE && scorerCellQueue.topTuple() >= tupleID)) ||
         (scorerCellQueue.topEntity() == entityID && scorerCellQueue.topTuple() == tupleID && (scorerCellQueue.topCell() != Integer.MAX_VALUE && scorerCellQueue.topCell() >= cellID))) {
        entity = scorerCellQueue.topEntity();
        this.nextPosition();
        return entity;
      }
      else if (!scorerCellQueue.topSkipToAndAdjustElsePop(entityID, tupleID, cellID)) {
        if (--queueSize == 0) {
          return NO_MORE_DOCS;
        }
      }
    }
    return NO_MORE_DOCS;
  }

  @Override
  public int dataset() {
    return dataset;
  }

  @Override
  public int docID() {
    return entity;
  }

  @Override
  public int entity() {
    return entity;
  }

  @Override
  public int tuple() {
    return tuple;
  }

  @Override
  public int cell() {
    return cell;
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
  public String toString() {
    return "SirenDisjunctionScorer(" + this.dataset() + "," + this.entity() + "," + this.tuple() + "," + this.cell() + ")";
  }

}
