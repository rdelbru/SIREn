/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sindice.siren.analysis.attributes;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.analysis.JsonTokenizer;
import org.sindice.siren.util.ArrayUtils;

/**
 * Implementation of {@link NodeAttribute} for a token coming from the
 * {@link JsonTokenizer}.
 */
public class JsonNodeAttributeImpl
extends AttributeImpl
implements NodeAttribute, Cloneable, Serializable {

  private static final long serialVersionUID = 8820316999175774635L;
  private final IntsRef     node             = new IntsRef();

  /**
   * Returns this Token's node path.
   */
  public IntsRef node() {
    return node;
  }

  @Override
  public void clear() {
    node.length = 0;
    node.offset = 0;
    Arrays.fill(node.ints, 0);
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }

    if (other instanceof JsonNodeAttributeImpl) {
      final JsonNodeAttributeImpl o = (JsonNodeAttributeImpl) other;
      return node.equals(o.node);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return node.hashCode();
  }

  @Override
  public void copyTo(final AttributeImpl target) {
    final JsonNodeAttributeImpl t = (JsonNodeAttributeImpl) target;
    t.copyNode(node);
  }

  @Override
  public void copyNode(final IntsRef nodePath) {
    ArrayUtils.growAndCopy(node, nodePath.length);
    System.arraycopy(nodePath.ints, nodePath.offset, node.ints, node.offset, nodePath.length);
    node.offset = nodePath.offset;
    node.length = nodePath.length;
  }

  @Override
  public String toString() {
    return "node=" + this.node();
  }

  @Override
  public void append(final int nodeID) {
    ArrayUtils.growAndCopy(node, node.length + 1);
    node.ints[++node.length] = nodeID;
  }

}
