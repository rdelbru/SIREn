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
