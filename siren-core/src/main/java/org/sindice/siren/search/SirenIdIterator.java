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
 * @author Renaud Delbru [ 9 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;

/**
 * Interface for the SirenScorer that extends the functionalities of
 * {@link DocIdSetIterator} for the SIREn use case.
 */
public interface SirenIdIterator {

  /**
   * When returned by {@link #nextPosition()} it means there are no more
   * positions in the iterator.
   */
  public static final int NO_MORE_POS = Integer.MAX_VALUE;

  /**
   * Moves to the next entity identifier in the set. Returns true, iff
   * there is such an entity identifier.
   * <p> Move the tuple, cell and pos pointer to the first position [SRN-24].
   **/
  public abstract int nextDoc() throws IOException;

  /**
   * Move to the next tuple, cell and position in the current entity matching
   * the query.
   *
   * <p> This is invalid until {@link #nextDoc()} is called for the first time.
   *
   * @return false iff there is no more tuple, cell and position for the current
   * entity.
   */
  public abstract int nextPosition() throws IOException;

  /** Skips to the first match (including the current) whose
   * is greater than or equal to a given entity.
   */
  public int advance(int entityID) throws IOException;

  /** Skips to the first match (including the current) whose
   * is greater than or equal to a given entity and tuple.
   */
  public int advance(int entityID, int tupleID) throws IOException;

  /** Skips to the first match (including the current) whose
   * is greater than or equal to a given entity, tuple and cell.
   */
  public int advance(int entityID, int tupleID, int cellID) throws IOException;

  /**
   * Returns the current dataset identifier.
   * <p> This is invalid until {@link #nextDoc()} is called for the first time.
   **/
  public int dataset();

  /**
   * Returns the current entity identifier.
   * <p> This is invalid until {@link #nextDoc()} is called for the first time.
   **/
  public int entity();

  /**
   * Returns the current tuple identifier.
   * <p> Initially invalid, until {@link #nextPosition()} is
   * called the first time.
   **/
  public int tuple();

  /**
   * Returns the current cell identifier.
   * <p> Initially invalid, until {@link #nextPosition()} is
   * called the first time.
   **/
  public int cell();

  /** Returns the current position identifier matching the query.
   * <p> Initially invalid, until {@link #nextPosition()} is
   * called the first time.
   */
  public int pos();

}
