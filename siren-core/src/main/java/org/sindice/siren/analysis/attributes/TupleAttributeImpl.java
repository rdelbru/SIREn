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
