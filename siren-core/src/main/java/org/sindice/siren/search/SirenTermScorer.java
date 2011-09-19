/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
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
 * @author Renaud Delbru [ 9 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.index.TermPositions;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;
import org.sindice.siren.index.SirenTermPositions;

class SirenTermScorer
extends SirenPrimitiveScorer {

  private final SirenTermPositions termPositions;

  private final Similarity    sim;

  private final byte[]        norms;

  private final float         weightValue;

  private static final int    SCORE_CACHE_SIZE = 32;

  private final float[]       scoreCache       = new float[SCORE_CACHE_SIZE];

  /** Current structural and positional information */
  private int                 dataset = -1;
  private int                 entity = -1;
  private int                 tuple = -1;
  private int                 cell = -1;
  private int                 pos = -1;

  /**
   * Construct a <code>SirenTermScorer</code>.
   *
   * @param weight
   *          The weight of the <code>Term</code> in the query.
   * @param tp
   *          An iterator over the documents and the positions matching the
   *          <code>Term</code>.
   * @param similarity
   *          The </code>Similarity</code> implementation to be used for score
   *          computations.
   * @param norms
   *          The field norms of the document fields for the <code>Term</code>.
   */
  protected SirenTermScorer(final Weight weight, final TermPositions tp,
                            final Similarity similarity, final byte[] norms) {
    super(similarity);
    sim = similarity;
    this.termPositions = new SirenTermPositions(tp);
    this.norms = norms;
    this.weightValue = weight.getValue();

    for (int i = 0; i < SCORE_CACHE_SIZE; i++)
      scoreCache[i] = sim.tf(i) * weightValue;
  }

  @Override
  public void score(final Collector c) throws IOException {
    this.score(c, Integer.MAX_VALUE, this.nextDoc());
  }

  // firstDocID is ignored since nextDoc() sets 'doc'
  @Override
  protected boolean score(final Collector c, final int end, final int firstDocID) throws IOException {
    c.setScorer(this);
    while (entity < end) {                           // for docs in window
      c.collect(entity);                             // collect score
      if (this.nextDoc() == NO_MORE_DOCS) {
        return false;
      }
    }
    return true;
  }

  @Override
  public float score() throws IOException {
    final int f = termPositions.freq();
    final float raw =                                   // compute tf(f)*weight
      f < SCORE_CACHE_SIZE                              // check cache
      ? scoreCache[f]                                   // cache hit
      : sim.tf(f) * weightValue;                        // cache miss

    return norms == null ? raw : raw * sim.decodeNormValue(norms[this.entity()]); // normalize for field
  }

  /** Move to the next entity matching the query.
   * @return next entity id matching the query.
   */
  @Override
  public int nextDoc() throws IOException {
    if (!termPositions.next()) {
      termPositions.close();      // close stream
      dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // set to sentinel value
      return NO_MORE_DOCS;
    }
    entity = termPositions.entity();
    this.nextPosition(); // advance to the first cell [SRN-24]
    return entity;
  }

  /**
   * Move to the next tuple, cell and position in the current entity.
   *
   * <p> This is invalid until {@link #next()} is called for the first time.
   *
   * @return false iff there is no more tuple, cell and position for the current
   * entity.
   * @throws IOException
   */
  @Override
  public int nextPosition() throws IOException {
    if (termPositions.nextPosition() == NO_MORE_POS) {
      tuple = cell = pos = Integer.MAX_VALUE; // set to sentinel value
      return NO_MORE_POS;
    }

    tuple = termPositions.tuple();
    cell = termPositions.cell();
    pos = termPositions.pos();
    return pos;
  }

  @Override
  public int advance(final int entityID)
  throws IOException {
    if (!termPositions.skipTo(entityID)) {
      dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // set to sentinel value
      return NO_MORE_DOCS;
    }
    entity = termPositions.entity();
    this.nextPosition(); // advance to the first cell [SRN-24]
    return entity;
  }

  @Override
  public int advance(final int entityID, final int tupleID)
  throws IOException {
    if (!termPositions.skipTo(entityID, tupleID)) {
      dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // set to sentinel value
      return NO_MORE_DOCS;
    }
    entity = termPositions.entity();
    tuple = termPositions.tuple();
    cell = termPositions.cell();
    pos = termPositions.pos();
    return entity;
  }

  @Override
  public int advance(final int entityID, final int tupleID, final int cellID)
  throws IOException {
    if (!termPositions.skipTo(entityID, tupleID, cellID)) {
      dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // set to sentinel value
      return NO_MORE_DOCS;
    }
    entity = termPositions.entity();
    tuple = termPositions.tuple();
    cell = termPositions.cell();
    pos = termPositions.pos();
    return entity;
  }

  /** Returns the current document number matching the query.
   * <p> Initially invalid, until {@link #next()} is called the first time.
   */
  @Override
  public int docID() { return entity; }

  /** Returns the current document number matching the query.
   * <p> Initially invalid, until {@link #next()} is called the first time.
   */
  public int dataset() { return dataset; }

  /** Returns the current entity identifier matching the query.
   * <p> Initially invalid, until {@link #next()} is called the first time.
   */
  public int entity() { return entity; }

  /** Returns the current tuple identifier matching the query.
   * <p> Initially invalid, until {@link #nextPosition()} is
   * called the first time.
   */
  public int tuple() { return tuple; }

  /** Returns the current cell identifier matching the query.
   * <p> Initially invalid, until {@link #nextPosition()} is
   * called the first time.
   */
  public int cell() { return cell; }

  /** Returns the current position identifier matching the query.
   * <p> Initially invalid, until {@link #nextPosition()} is
   * called the first time.
   */
  public int pos() { return pos; }

  @Override
  public String toString() {
    return "TermScorer(" + dataset + "," + entity + "," + tuple + "," + cell + ")";
  }

}
