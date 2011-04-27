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
