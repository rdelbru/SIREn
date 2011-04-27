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
 * @author Renaud Delbru [ 9 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.index;

import java.util.Arrays;

import org.sindice.siren.util.CodecUtils;

/**
 * An implementation of the SIREn payload using Integer.
 */
public class IntSirenPayload
extends AbstractSirenPayload {

  private static final long serialVersionUID = -5679376513881275955L;

  public IntSirenPayload() {
    super();
  }

  public IntSirenPayload(final byte[] data) {
    super(data);
  }

  public IntSirenPayload(final byte[] data, final int offset, final int length) {
    super(data, offset, length);
  }

  public IntSirenPayload(final int tupleID, final int cellID) {
    super(tupleID, cellID);
  }

  @Override
  public void decode() {
    _tupleID = CodecUtils.byteArrayToInt(Arrays.copyOfRange(data, 0, 4));
    _cellID = CodecUtils.byteArrayToInt(Arrays.copyOfRange(data, 4, 8));
  }

  @Override
  public void encode(final int tupleID, final int cellID) {
    data = new byte[12];
    System.arraycopy(CodecUtils.intToByteArray(tupleID), 0, data, 0, 4);
    System.arraycopy(CodecUtils.intToByteArray(cellID), 0, data, 4, 4);
    offset = 0;
    length = data.length;
  }

}
