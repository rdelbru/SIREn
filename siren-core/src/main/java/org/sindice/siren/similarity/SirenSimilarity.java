/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
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
 * @author Campinas Stephane [ 26 May 2011 ]
 * @link stephane.campinas@deri.org
 * @author Renaud Delbru [ 27 May 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010, 2011, by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.similarity;

import org.apache.lucene.search.DefaultSimilarity;

/**
 * Implement the BM25 saturation function for the term frequency.
 */
public class SirenSimilarity extends DefaultSimilarity {

  private static final long serialVersionUID = 1L;

  private final float K1 = 1.2f;

  @Override
  public float tf(final float freq) {
    return freq * (K1 + 1) / (freq + K1);
  }

}
