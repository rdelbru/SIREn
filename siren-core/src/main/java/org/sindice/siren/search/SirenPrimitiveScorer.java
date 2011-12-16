/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 29 Apr 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;

/**
 * Abstract class for the SIREn primitive scorers
 */
public abstract class SirenPrimitiveScorer
extends SirenScorer {

  protected SirenPrimitiveScorer(final Similarity similarity) {
    super(similarity);
  }

  protected SirenPrimitiveScorer(final Similarity similarity, final Weight weight) {
    super(similarity, weight);
  }

}
