/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 21 Apr 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

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

  public class InvalidCallException extends RuntimeException {

    private static final long serialVersionUID = -7392726157079278292L;

    public InvalidCallException(final String message) {
      super(message);
    }

  }

}
