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
 * @author Renaud Delbru [ 21 Apr 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Similarity;

/**
 * The Siren abstract {@link Scorer} class that implements the interface
 * {@link SirenIdIterator}, an extension of the {@link DocIdSetIterator}.
 */
public abstract class SirenScorer extends Scorer implements SirenIdIterator {

  protected SirenScorer(final Similarity similarity) {
    super(similarity);
  }

  /**
   * Returns the score impact for the current cell.
   */
  public abstract float scoreCell() throws IOException;

  public class InvalidCallException extends RuntimeException {

    private static final long serialVersionUID = -7392726157079278292L;

    public InvalidCallException(final String message) {
      super(message);
    }

  }

}
