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
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;

/**
 * Interface that extends the functionalities of
 * {@link DocIdSetIterator} for the SIREn use case.
 */
public interface DocTupCelIdSetIterator {

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
  public int nextDoc() throws IOException;

  /**
   * Move to the next tuple, cell and position in the current entity matching
   * the query.
   *
   * <p> This is invalid until {@link #nextDoc()} is called for the first time.
   *
   * @return false iff there is no more tuple, cell and position for the current
   * entity.
   */
  public int nextPosition() throws IOException;

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
