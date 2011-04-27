/**
 * Copyright 2009, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import org.apache.lucene.util.PriorityQueue;

/**
 * Code taken from {@link PhraseQueue} and adapted for the Siren use case.
 */
final class SirenPhraseQueue extends PriorityQueue<SirenPhrasePositions> {
  SirenPhraseQueue(final int size) {
    this.initialize(size);
  }

  @Override
  protected final boolean lessThan(final SirenPhrasePositions pp1, final SirenPhrasePositions pp2) {
    if (pp1.entity() == pp2.entity())
      if (pp1.tuple() == pp2.tuple())
        if (pp1.cell() == pp2.cell())
          if (pp1.pos() == pp2.pos())
            // same entity and pp.pos(), so decide by actual term positions.
            // rely on: pp.pos() == termPosition.getPosition() - offset.
            return pp1.offset() < pp2.offset();
          else
            return pp1.pos() < pp2.pos();
        else
          return pp1.cell() < pp2.cell();
      else
        return pp1.tuple() < pp2.tuple();
    else
      return pp1.entity() < pp2.entity();
  }
}
