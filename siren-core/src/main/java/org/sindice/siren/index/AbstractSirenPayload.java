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
 * @project siren
 * @author Renaud Delbru [ 4 Feb 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.index;

import java.io.IOException;

import org.apache.lucene.index.Payload;

/**
 * Abstract class implementation of the SIREn payload
 */
public abstract class AbstractSirenPayload
extends Payload {

  protected int _tupleID;
  protected int _cellID;

  private static final long serialVersionUID = -6594151559705429913L;

  public AbstractSirenPayload() {
    super();
  }

  /**
   * @param data
   */
  public AbstractSirenPayload(final byte[] data) {
    super(data);
    this.decode();
  }

  /**
   * @param data
   * @param offset
   * @param length
   */
  public AbstractSirenPayload(final byte[] data, final int offset, final int length) {
    super(data, offset, length);
    this.decode();
  }

  public AbstractSirenPayload(final int tupleID, final int cellID) {
    _tupleID = tupleID;
    _cellID = cellID;
    this.encode(tupleID, cellID);
  }

  /**
   * Sets this payloads data.
   * A reference to the passed-in array is held, i. e. no
   * copy is made.
   */
  @Override
  public void setData(final byte[] data, final int offset, final int length) {
    super.setData(data, offset, length);
    this.decode();
  }

  /**
   * Encode the information into a byte array as payload data.
   *
   * @param tupleID The tuple identifier
   * @param cellID The cell identifier
   */
  public abstract void encode(final int tupleID, final int cellID);

  /**
   * Decode the payload data and set the tuple and cell identifiers
   */
  public abstract void decode();

  public int getTupleID() throws IOException {
    return _tupleID;
  }

  public int getCellID() throws IOException {
    return _cellID;
  }

}
