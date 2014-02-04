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
import org.sindice.siren.util.NodeUtils;

/**
 * Abstraction of a {@link DocsNodesAndPositionsEnum} that filters nodes that
 * do not satisfy some constraints.
 *
 * <p>
 *
 * This {@link DocsNodesAndPositionsEnum} wraps another
 * {@link DocsNodesAndPositionsEnum} and applies the node constraints. It
 * filters all the nodes that do not satisfy the constraints.
 *
 * @see NodeUtils#isConstraintSatisfied(int[], int[], int[], boolean)
 */
public abstract class ConstrainedNodesEnum extends DocsNodesAndPositionsEnum {

  protected final DocsNodesAndPositionsEnum docsEnum;

  public ConstrainedNodesEnum(final DocsNodesAndPositionsEnum docsEnum) {
    this.docsEnum = docsEnum;
  }

  @Override
  public boolean nextDocument() throws IOException {
    return docsEnum.nextDocument();
  }

  @Override
  public abstract boolean nextNode() throws IOException;

  @Override
  public boolean skipTo(final int target) throws IOException {
    return docsEnum.skipTo(target);
  }

  @Override
  public int doc() {
    return docsEnum.doc();
  }

  @Override
  public IntsRef node() {
    return docsEnum.node();
  }

  @Override
  public boolean nextPosition() throws IOException {
    return docsEnum.nextPosition();
  }

  @Override
  public int pos() {
    return docsEnum.pos();
  }

  @Override
  public int termFreqInNode() throws IOException {
    return docsEnum.termFreqInNode();
  }

  @Override
  public int nodeFreqInDoc() throws IOException {
    return docsEnum.nodeFreqInDoc();
  }

}
