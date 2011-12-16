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

import org.apache.lucene.index.IndexReader;

/**
 *  Abstract base class for restricting which documents may
 *  be returned during searching.
 *
 *  Code taken from {@link Filter} and adapted for SIREn.
 */
public abstract class SirenFilter implements java.io.Serializable {

  /**
   * Creates a {@link DocTupCelIdSet} enumerating the documents, tuples and
   * cells that should be permitted in search results. <b>NOTE:</b> null can be
   * returned if no documents are accepted by this Filter.
   * <p>
   * Note: This method will be called once per segment in
   * the index during searching.  The returned {@link DocTupCelIdSet}
   * must refer to document, tuple and cell IDs for that segment, not for
   * the top-level reader.
   *
   * @param reader a {@link IndexReader} instance opened on the index currently
   *         searched on. Note, it is likely that the provided reader does not
   *         represent the whole underlying index i.e. if the index has more than
   *         one segment the given reader only represents a single segment.
   *
   * @return a DocTupCelIdSet that provides the documents which should be permitted or
   *         prohibited in search results. <b>NOTE:</b> null can be returned if
   *         no documents will be accepted by this Filter.
   *
   * @see DocTupCelIdSet
   */
  public abstract DocTupCelIdSet getDocTupCelIdSet(IndexReader reader) throws IOException;
}

