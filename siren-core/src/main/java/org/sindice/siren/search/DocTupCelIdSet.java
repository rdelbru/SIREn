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
 * @project siren-core
 * @author Renaud Delbru [ 27 Sep 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;

/**
 * A DocTupCelIdSet contains a set of doc, tuple and cell ids. Implementing
 * classes must only implement {@link #iterator} to provide access to the set.
 *
 * <p> Code taken from {@link DocIdSet} and adapted for SIREn.
 */
public abstract class DocTupCelIdSet {

  /** An empty {@code DocTupCelIdSet} instance for easy use, e.g. in Filters that hit no documents. */
  public static final DocTupCelIdSet EMPTY_IDSET = new DocTupCelIdSet() {

    private final DocTupCelIdSetIterator iterator = new DocTupCelIdSetIterator() {

      @Override
      public int advance(final int target) throws IOException { return DocIdSetIterator.NO_MORE_DOCS; }

      @Override
      public int nextDoc() throws IOException { return DocIdSetIterator.NO_MORE_DOCS; }

      @Override
      public int nextPosition() throws IOException { return DocTupCelIdSetIterator.NO_MORE_POS; }

      @Override
      public int advance(final int entityID, final int tupleID) throws IOException { return DocTupCelIdSetIterator.NO_MORE_POS; }

      @Override
      public int advance(final int entityID, final int tupleID, final int cellID) throws IOException { return DocTupCelIdSetIterator.NO_MORE_POS; }

      @Override
      public int dataset() { throw new UnsupportedOperationException(); }

      @Override
      public int entity() { throw new UnsupportedOperationException(); }

      @Override
      public int tuple() { throw new UnsupportedOperationException(); }

      @Override
      public int cell() { throw new UnsupportedOperationException(); }

      @Override
      public int pos() { throw new UnsupportedOperationException(); }

    };

    @Override
    public DocTupCelIdSetIterator iterator() {
      return iterator;
    }

  };

  /** Provides a {@link DocTupCelIdSetIterator} to access the set.
   * This implementation can return <code>null</code> or
   * <code>{@linkplain #EMPTY_DOCIDSET}.iterator()</code> if there
   * are no docs that match. */
  public abstract DocTupCelIdSetIterator iterator() throws IOException;

}

