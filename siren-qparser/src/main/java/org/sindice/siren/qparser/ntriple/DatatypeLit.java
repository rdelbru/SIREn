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

/**
 * 
 */
public final class DatatypeLit {

  private final String datatype;
  private final String lit;
  
  /**
   * 
   */
  public DatatypeLit(final String datatype, final String lit) {
    this.datatype = datatype;
    this.lit = lit;
  }

  public String getDatatypeURI() {
    return datatype;
  }

  public String getLit() {
    return lit;
  }

  @Override
  public String toString() {
    return new String(datatype) + ":" + lit;
  }
  
}
