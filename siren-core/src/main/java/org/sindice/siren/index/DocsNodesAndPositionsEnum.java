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

import org.apache.lucene.util.AttributeSource;

/**
 * Iterates through documents, node frequencies, nodes, term frequencies and
 * positions.
 */
public abstract class DocsNodesAndPositionsEnum implements DocsAndNodesIterator, PositionsIterator {

  private AttributeSource atts = null;

  /** Returns the related attributes. */
  public AttributeSource attributes() {
    if (atts == null) atts = new AttributeSource();
    return atts;
  }

  /**
   * Returns term frequency in the current node.  Do
   * not call this before {@link #nextNode} is first called,
   * nor after {@link #nextNode} returns false or
   * {@link #node()} returns {@link DocsAndNodesIterator#NO_MORE_NOD}.
   * @throws IOException
   **/
  public abstract int termFreqInNode() throws IOException;

  /**
   * Returns node frequency in the current document.  Do
   * not call this before {@link #nextDoc} is first called,
   * nor after {@link #nextDoc} returns false or
   * {@link #doc()} returns {@link DocsAndNodesIterator#NO_MORE_DOC}.
   **/
  public abstract int nodeFreqInDoc() throws IOException;

}
