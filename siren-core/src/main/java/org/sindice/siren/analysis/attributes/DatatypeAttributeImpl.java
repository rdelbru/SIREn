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
 * @project siren-core
 * @author Renaud Delbru [ 3 Oct 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.analysis.attributes;

import java.io.Serializable;
import java.nio.CharBuffer;

import org.apache.lucene.util.AttributeImpl;

public class DatatypeAttributeImpl extends AttributeImpl implements DatatypeAttribute, Cloneable, Serializable {

  private final CharBuffer buffer = CharBuffer.allocate(64);

  @Override
  public CharBuffer datatypeURI() {
    return buffer;
  }

  @Override
  public void setDatatypeURI(final char[] datatypeURI) {
    buffer.clear();
    buffer.put(datatypeURI);
  }

  @Override
  public void clear() {
    buffer.clear();
  }

  @Override
  public void copyTo(final AttributeImpl target) {
    final DatatypeAttributeImpl t = (DatatypeAttributeImpl) target;
    t.copyBuffer(buffer);
  }

  /**
   * Copies the contents of buffer, starting at offset for
   * length characters, into the termBuffer array.
   *
   * @param buffer the buffer to copy
   */
  protected void copyBuffer(final CharBuffer buffer) {
    this.buffer.clear();
    this.buffer.put(buffer);
  }

}
