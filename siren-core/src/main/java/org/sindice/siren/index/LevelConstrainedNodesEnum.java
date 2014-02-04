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

import org.sindice.siren.util.NodeUtils;

/**
 * Implementation of {@link ConstrainedNodesEnum} to apply a level constraint
 * over the retrieved node labels.
 *
 * @see NodeUtils#isConstraintSatisfied(int[], int[], int[], boolean)
 */
public class LevelConstrainedNodesEnum extends ConstrainedNodesEnum {

  private final int level;

  public LevelConstrainedNodesEnum(final DocsNodesAndPositionsEnum docsEnum,
                                   final int level) {
    super(docsEnum);
    this.level = level;
  }

  @Override
  public boolean nextNode() throws IOException {
    while (docsEnum.nextNode()) {
      if (NodeUtils.isConstraintSatisfied(docsEnum.node(), level)) {
        return true;
      }
    }
    return false;
  }

}
