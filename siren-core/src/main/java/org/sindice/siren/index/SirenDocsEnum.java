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

import java.security.InvalidParameterException;

import org.apache.lucene.index.DocsAndPositionsEnum;
import org.apache.lucene.index.MultiDocsAndPositionsEnum;
import org.sindice.siren.search.node.NodeQuery;

/**
 * This {@link DocsAndPositionsEnum} extension acts as a decorator over a
 * {@link DocsNodesAndPositionsEnum}.
 *
 * <p>
 *
 * It enables to provide a {@link DocsNodesAndPositionsEnum} instance to a
 * {@link NodeQuery}.
 *
 * <p>
 *
 * Subclasses must wrap a {@link DocsNodesAndPositionsEnum} instance and
 * implements the method {@link SirenDocsEnum#getDocsNodesAndPositionsEnum()}
 * to return the wrapped instance.
 */
public abstract class SirenDocsEnum extends DocsAndPositionsEnum {

  public abstract DocsNodesAndPositionsEnum getDocsNodesAndPositionsEnum();

  /**
   * Helper method to map a Lucene's {@link DocsAndPositionsEnum} to a SIREn's
   * {@link DocsNodesAndPositionsEnum}.
   */
  public static DocsNodesAndPositionsEnum map(final DocsAndPositionsEnum docsEnum) {
    if (docsEnum instanceof MultiDocsAndPositionsEnum) {
      final MultiDocsAndPositionsEnum multiDocsEnum = (MultiDocsAndPositionsEnum) docsEnum;
      return new MultiDocsNodesAndPositionsEnum(multiDocsEnum.getSubs(), multiDocsEnum.getNumSubs());
    }
    else if (docsEnum instanceof SirenDocsEnum) {
      return ((SirenDocsEnum) docsEnum).getDocsNodesAndPositionsEnum();
    }
    else {
      throw new InvalidParameterException("Unknown DocsAndPositionsEnum received: " + docsEnum.getClass());
    }
  }

}
