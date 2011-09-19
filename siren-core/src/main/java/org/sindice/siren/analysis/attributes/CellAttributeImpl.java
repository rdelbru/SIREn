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
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.attributes;

import java.io.Serializable;

import org.apache.lucene.util.AttributeImpl;

/**
 * The cell of a token.
 */
public class CellAttributeImpl extends AttributeImpl implements CellAttribute, Cloneable, Serializable {

  private int cell;

  /**
   * Returns this Token's cell id.
   */
  public int cell() {
    return cell;
  }

  /**
   * Set the cell id.
   * @see #cell()
   */
  public void setCell(final int cell) {
    this.cell = cell;
  }

  @Override
  public void clear() {
    cell = 0;
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }

    if (other instanceof CellAttributeImpl) {
      final CellAttributeImpl o = (CellAttributeImpl) other;
      return cell == o.cell;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return cell;
  }

  @Override
  public void copyTo(final AttributeImpl target) {
    final CellAttribute t = (CellAttribute) target;
    t.setCell(cell);
  }

}
