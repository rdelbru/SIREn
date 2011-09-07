/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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
