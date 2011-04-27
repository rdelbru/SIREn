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
