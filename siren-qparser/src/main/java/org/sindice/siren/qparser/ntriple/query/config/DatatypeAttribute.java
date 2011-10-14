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
 * @author Renaud Delbru [ 3 Oct 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.qparser.ntriple.query.config;

import org.apache.lucene.util.Attribute;

/**
 * The datatype of a literal token.
 */
public interface DatatypeAttribute extends Attribute {

  /**
   * Returns the datatype URI.
   */
  public String datatypeURI();

  /**
   * Set the datatype URI.
   * @see #datatypeURI()
   */
  public void setDatatypeURI(String datatypeURI);

}
