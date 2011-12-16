/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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

import org.apache.lucene.util.AttributeImpl;

public class DatatypeAttributeImpl extends AttributeImpl implements DatatypeAttribute, Cloneable, Serializable {

  private char[] dataTypeURI = null;

  @Override
  public char[] datatypeURI() {
    return dataTypeURI;
  }

  @Override
  public void setDatatypeURI(final char[] datatypeURI) {
    clear();
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
