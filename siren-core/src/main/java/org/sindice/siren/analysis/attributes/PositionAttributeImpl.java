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
 * @author Renaud Delbru [ 9 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.attributes;

import java.io.Serializable;

import org.apache.lucene.util.AttributeImpl;

/**
 * The position of a token within a cell.
 */
public class PositionAttributeImpl extends AttributeImpl implements PositionAttribute, Cloneable, Serializable {

  private int position;

  /**
   * Returns this Token's position within the cell.
   */
  public int position() {
    return position;
  }

  /**
   * Set the position.
   * @see #position()
   */
  public void setPosition(final int position) {
    this.position = position;
  }

  @Override
  public void clear() {
    position = 0;
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }

    if (other instanceof PositionAttributeImpl) {
      final PositionAttributeImpl o = (PositionAttributeImpl) other;
      return position == o.position;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return position;
  }

  @Override
  public void copyTo(final AttributeImpl target) {
    final PositionAttribute t = (PositionAttribute) target;
    t.setPosition(position);
  }

}
