/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sindice.siren.index;

import java.io.IOException;

import org.apache.lucene.util.IntsRef;

/**
 * Interface that defines methods to iterate over a set of increasing
 * doc identifiers and node labels.
 *
 * <p>
 *
 * This class iterates on doc ids and node labels. The sentinel value
 * {@link #NO_MORE_DOC} (which is set to {@value #NO_MORE_DOC}) is used to
 * indicate the end of the document stream. The sentinel value
 * {@link #NO_MORE_NOD} (which is set to [{@value #NO_MORE_DOC}]) is used
 * to indicate the end of a node stream.
 */
public interface DocsAndNodesIterator  {

  /**
   * When returned by {@link #doc()} it means there are no more docs in the
   * iterator.
   */
  public static final int NO_MORE_DOC = Integer.MAX_VALUE;

  /**
   * When returned by {@link #node()} it means there are no more nodes in the
   * iterator.
   */
  public static final IntsRef NO_MORE_NOD = new IntsRef(new int[] { Integer.MAX_VALUE }, 0, 1);

  /**
   * Advances to the next document in the set.
   *
   * @return false if there is no more docs in the set.
   */
  public boolean nextDocument() throws IOException;

  /**
   * Move to the next node path in the current document.
   * <p>
   * Should not be called until {@link #nextDocument()} or {@link #skipTo(int)}
   * are called for the first time.
   *
   * @return false if there is no more node for the current document or if
   * {@link #nextDocument()} or {@link #skipTo(int)} were not called yet.
   */
  public boolean nextNode() throws IOException;

  /**
   * Skip to the first document beyond (see NOTE below) the current whose
   * number is greater than or equal to <i>target</i>. Returns false if there
   * are no more docs in the set.
   * <p>
   * Behaves as if written:
   *
   * <pre>
   * boolean skipTo(int target) {
   *   while (nextDocument()) {
   *     if (target &le; doc())
   *       return true;
   *   }
   *   return false;
   * }
   * </pre>
   *
   * Some implementations are considerably more efficient than that.
   * <p>
   * <b>NOTE:</b> when <code> target &le; current</code> implementations must
   * not advance beyond their current {@link #doc()}.
   */
  public boolean skipTo(int target) throws IOException;

  /**
   * Returns the following:
   * <ul>
   * <li>-1 or {@link #NO_MORE_DOC} if {@link #nextDocument()} or
   * {@link #skipTo(int)} were not called yet.
   * <li>{@link #NO_MORE_DOC} if the iterator has exhausted.
   * <li>Otherwise it should return the doc ID it is currently on.
   * </ul>
   * <p>
   */
  public int doc();

  /**
   * Returns the following:
   * <ul>
   * <li>-1 or {@link #NO_MORE_NOD} if {@link #nextNode()} or
   * {@link #skipTo(int, int[])} were not called yet.
   * <li>{@link #NO_MORE_NOD} if the iterator has exhausted.
   * <li>Otherwise it should return the node it is currently on.
   * </ul>
   */
  public IntsRef node();

}
