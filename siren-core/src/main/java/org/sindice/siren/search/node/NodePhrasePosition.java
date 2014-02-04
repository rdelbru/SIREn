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

package org.sindice.siren.search.node;

import java.io.IOException;

import org.sindice.siren.index.DocsNodesAndPositionsEnum;
import org.sindice.siren.index.PositionsIterator;

/**
 * Maps the current position of a {@link DocsNodesAndPositionsEnum} to a
 * position relative to the offset of the term in the phrase.
 */
class NodePhrasePosition {

  /**
   * Current position
   * <p>
   * Sentinel value is equal to {@link Integer.MIN_VALUE} since a position can
   * be negative.
   */
  int pos = Integer.MIN_VALUE;

  final int offset;            // position in phrase

  private final DocsNodesAndPositionsEnum docsEnum; // stream of positions
  protected NodePhrasePosition next;                // used to make lists

  NodePhrasePosition(final DocsNodesAndPositionsEnum docsEnum, final int offset) {
    this.docsEnum = docsEnum;
    this.offset = offset;
  }

  void init() throws IOException {
    pos = Integer.MIN_VALUE;
  }

  /**
   * Go to next location of this term current document, and set
   * <code>position</code> as <code>location - offset</code>, so that a
   * matching exact phrase is easily identified when all NodePhrasePositions
   * have exactly the same <code>position</code>.
   */
  public final boolean nextPosition() throws IOException {
    if (docsEnum.nextPosition()) {          // read subsequent pos's
      pos = docsEnum.pos() - offset;
      return true;
    }
    else {
      pos = PositionsIterator.NO_MORE_POS;
      return false;
    }
  }

  @Override
  public String toString() {
    return "NodePhrasePositions(d:"+docsEnum.doc()+" n:"+docsEnum.node()+" o:"+offset+" p:"+pos+")";
  }

}
