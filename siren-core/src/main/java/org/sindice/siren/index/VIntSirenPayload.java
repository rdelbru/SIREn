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
 * @author Renaud Delbru [ 5 Feb 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.index;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.sindice.siren.util.CodecUtils;

/**
 * An implementation of the SIREn payload using Variable Int encoding.
 */
public class VIntSirenPayload
extends AbstractSirenPayload {

  private static final long serialVersionUID = -5679376513881275955L;

  public VIntSirenPayload() {
    super();
  }

  public VIntSirenPayload(final byte[] data) {
    super(data);
  }

  public VIntSirenPayload(final byte[] data, final int offset, final int length) {
    super(data, offset, length);
  }

  public VIntSirenPayload(final int tupleID, final int cellID) {
    super(tupleID, cellID);
  }

  @Override
  public void decode() {
    final ByteArrayInputStream input = new ByteArrayInputStream(data, offset, length);
    _tupleID = CodecUtils.byteArrayToVInt(input);
    _cellID = CodecUtils.byteArrayToVInt(input);
  }

  @Override
  public void encode(final int tupleID, final int cellID) {
    final ByteArrayOutputStream output = new ByteArrayOutputStream(15);
    CodecUtils.vIntToByteArray(tupleID, output);
    CodecUtils.vIntToByteArray(cellID, output);
    data = output.toByteArray();
    offset = 0;
    length = data.length;
  }

}
