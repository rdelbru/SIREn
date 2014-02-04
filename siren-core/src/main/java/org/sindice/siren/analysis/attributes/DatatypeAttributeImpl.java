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

import org.apache.lucene.util.AttributeImpl;

/**
 * Default implementation of {@link DatatypeAttribute}.
 */
public class DatatypeAttributeImpl extends AttributeImpl
implements DatatypeAttribute, Cloneable, Serializable {

  private char[] dataTypeURI = null;

  private static final long serialVersionUID = -6117733199775936595L;

  @Override
  public char[] datatypeURI() {
    return dataTypeURI;
  }

  @Override
  public void setDatatypeURI(final char[] datatypeURI) {
    this.dataTypeURI = datatypeURI;
  }

  @Override
  public void clear() {
    dataTypeURI = null;
  }

  @Override
  public void copyTo(final AttributeImpl target) {
    final DatatypeAttributeImpl t = (DatatypeAttributeImpl) target;
    t.clear();
    t.setDatatypeURI(dataTypeURI);
  }

}
