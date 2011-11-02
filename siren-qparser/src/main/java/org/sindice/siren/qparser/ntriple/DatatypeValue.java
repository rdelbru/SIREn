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
 * @project siren-qparser_rdelbru
 * @author Campinas Stephane [ 11 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.qparser.ntriple;

import org.sindice.siren.qparser.ntriple.query.model.Literal;
import org.sindice.siren.qparser.ntriple.query.model.LiteralPattern;
import org.sindice.siren.qparser.ntriple.query.model.URIPattern;

/**
 * Set the datatype of an {@link URIPattern}, a {@link LiteralPattern} or a
 * {@link Literal}. For a literal for instance, the datatype is the URI extracted
 * from Literal^^&lt;URI&gt;.
 */
public final class DatatypeValue {

  private final String datatype;
  private final String value;
  
  public DatatypeValue(final char[] datatype, final String value) {
    this.datatype = new String(datatype);
    this.value = value;
  }

  public String getDatatypeURI() {
    return datatype;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return datatype + ":" + value;
  }
  
}
