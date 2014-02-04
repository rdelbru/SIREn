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
import java.nio.IntBuffer;

import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.util.ArrayUtils;

/**
 * Implementation of {@link NodeAttribute} for a token in a tuple document.
 */
@Deprecated
public class TupleNodeAttributeImpl extends AttributeImpl implements NodeAttribute, Cloneable, Serializable {

  private final IntsRef ref = new IntsRef(2);
  protected IntBuffer node = IntBuffer.allocate(2);
  private boolean isFlipped = false;

  private static final long serialVersionUID = -2226786769372232588L;

  /**
   * Returns this Token's node path.
   */
  public IntsRef node() {
    if (!isFlipped) {
      node.flip();
      isFlipped = true;
    }

    ref.ints = ArrayUtils.grow(ref.ints, node.limit());
    System.arraycopy(node.array(), node.position(), ref.ints, 0, node.limit());
    ref.offset = node.position();
    ref.length = node.limit();

    return ref;
  }

  @Override
  public void clear() {
    node.clear();
    isFlipped = false;
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }

    if (other instanceof TupleNodeAttributeImpl) {
      final TupleNodeAttributeImpl o = (TupleNodeAttributeImpl) other;
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
    final TupleNodeAttributeImpl t = (TupleNodeAttributeImpl) target;
    t.node.clear();
    t.node.put(node);
  }

  @Override
  public void append(final int nodeID) {
    this.ensureIntBufferCapacity(node.position() + 1);
    node.put(nodeID);
  }

  /**
   * Increases the capacity of the <tt>IntBuffer</tt> instance, if
   * necessary, to ensure that it can hold at least the number of elements.
   */
  private void ensureIntBufferCapacity(final int target) {
    if (node.capacity() < target) {
      final IntBuffer buffer = IntBuffer.allocate(target);
      node.flip();
      buffer.put(node);
      node = buffer;
    }
  }

  @Override
  public void copyNode(final IntsRef nodePath) {
    node.clear();
    this.ensureIntBufferCapacity(nodePath.length);
    node.put(nodePath.ints, nodePath.offset, nodePath.length);
  }

  @Override
  public String toString() {
    return "node=" + this.node();
  }

}
