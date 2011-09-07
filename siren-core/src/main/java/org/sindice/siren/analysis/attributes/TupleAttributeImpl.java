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
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.attributes;

import java.io.Serializable;

import org.apache.lucene.util.AttributeImpl;

/**
 * The tuple of a token.
 */
public class TupleAttributeImpl extends AttributeImpl implements TupleAttribute, Cloneable, Serializable {

  private int tuple;

  /**
   * Returns this Token's tuple id.
   */
  public int tuple() {
    return tuple;
  }

  /**
   * Set the tuple id.
   * @see #tuple()
   */
  public void setTuple(final int tuple) {
    this.tuple = tuple;
  }

  @Override
  public void clear() {
    tuple = 0;
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }

    if (other instanceof TupleAttributeImpl) {
      final TupleAttributeImpl o = (TupleAttributeImpl) other;
      return tuple == o.tuple;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return tuple;
  }

  @Override
  public void copyTo(final AttributeImpl target) {
    final TupleAttribute t = (TupleAttribute) target;
    t.setTuple(tuple);
  }

}
