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

/**
 * This interface defines methods to iterate over a set of increasing
 * positions.
 *
 * <p>
 *
 * This class iterates on positions. The sentinel value
 * {@link #NO_MORE_POS} (which is set to {@value #NO_MORE_POS}) is used to
 * indicate the end of the position stream.
 *
 * <p>
 *
 * To be used in conjunction with {@link DocsAndNodesIterator}.
 */
public interface PositionsIterator  {

  /**
   * When returned by {@link #pos()} it means there are no more
   * positions in the iterator.
   */
  public static final int NO_MORE_POS = Integer.MAX_VALUE;

  /**
   * Move to the next position in the current node matching the query.
   * <p>
   * Should not be called until {@link DocsAndNodesIterator#nextNode()} or
   * {@link DocsAndNodesIterator#skipTo(int, int)} are called for the first
   * time.
   *
   * @return false if there is no more position for the current node or if
   * {@link DocsAndNodesIterator#nextNode()} or
   * {@link DocsAndNodesIterator#skipTo(int, int)} were not called yet.
   */
  public boolean nextPosition() throws IOException;

  /**
   * Returns the following:
   * <ul>
   * <li>-1 or {@link #NO_MORE_POS} if {@link #nextPosition()} were not called
   * yet.
   * <li>{@link #NO_MORE_POS} if the iterator has exhausted.
   * <li>Otherwise it should return the position it is currently on.
   * </ul>
   */
  public int pos();

}
