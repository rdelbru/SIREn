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

/**
 * An implementation of the SIREn payload using a technique to pack two
 * integers:
 * <ul>
 * <li> 4 bits to encode the frame code
 * <li> n bits to encode the two integers.
 * <ul>
 * In the best case, this allows to use only 4 bits per integer.
 */
public class PackedIntSirenPayload
extends AbstractSirenPayload {

  private static final long serialVersionUID = -5679376513881275955L;

  public PackedIntSirenPayload() {
    super();
  }

  public PackedIntSirenPayload(final byte[] data) {
    super(data);
  }

  public PackedIntSirenPayload(final byte[] data, final int offset, final int length) {
    super(data, offset, length);
  }

  public PackedIntSirenPayload(final int tupleID, final int cellID) {
    super(tupleID, cellID);
  }

  @Override
  public void decode() {
    final int[] values = unpack(data);
    _tupleID = values[0];
    _cellID = values[1];
  }

  @Override
  public void encode(final int tupleID, final int cellID) {
    _tupleID = tupleID;
    _cellID = cellID;

    final int tupleIDFrame = PackedIntSirenPayload.logNextHigherPowerOf2(tupleID) + 1;
    final int cellIDFrame = PackedIntSirenPayload.logNextHigherPowerOf2(cellID) + 1;
    final int bestConfig = findBestConfiguration(tupleIDFrame, cellIDFrame);
    length = configurationSizes[bestConfig];

    if (data == null || data.length < length) { // optimisation, create array only when necessary
      data = new byte[length];
    }

    pack(bestConfig, tupleID, cellID, data);
    offset = 0;
  }

  // List of configurations in order of preference
  private static final int[][] configurations = new int[][] {
    {0,4},{4,0},{1,3},{3,1},{2,2},{4,8},{8,4},{0,12},{12,0},{6,6},{4,16},{16,4},
    {8,12},{12,8},{20,16},{28,16}};

  // List of configurations' size
  private static final int[] configurationSizes = new int[] {1,1,1,1,1,2,2,2,2,2,3,3,3,3,5,6};

  /**
   * Find the best configuration given two frame size.
   * @param frameSize1
   * @param frameSize2
   * @return The code of the best configuration, -1 if no configuration found.
   */
  protected static final int findBestConfiguration(final int frameSize1, final int frameSize2) {
    int[] config;

    for (int i = 0; i < configurations.length; i++) {
      config = configurations[i];
      if (frameSize1 <= config[0]) {
        if (frameSize2 <= config[1]) {
          return i;
        }
      }
    }

    return -1;
  }

  static final int[] values = new int[2];

  /**
   * Unpack two integers using optimised routines
   * @param compressedArray The compressed array
   */
  protected static final int[] unpack(final byte[] compressedArray) {
    final int code = (compressedArray[0] >>> 4) & 15;

    switch (code) {
      // 0 - 4
      case 0:
        values[0] = 0;
        values[1] = (compressedArray[0] & 15);
        break;

      // 4 - 0
      case 1:
        values[1] = 0;
        values[0] = (compressedArray[0] & 15);
        break;

      // 1 - 3
      case 2:
        values[0] = (compressedArray[0] >>> 3) & 1;
        values[1] = compressedArray[0] & 7;
        break;

      // 3 - 1
      case 3:
        values[1] = (compressedArray[0] >>> 3) & 1;
        values[0] = compressedArray[0] & 7;
        break;

      // 2 - 2
      case 4:
        values[0] = (compressedArray[0] >>> 2) & 3;
        values[1] = compressedArray[0] & 3;
        break;

      // 4 - 8
      case 5:
        values[0] = compressedArray[0] & 15;
        values[1] = compressedArray[1] & 255;
        break;

      // 8 - 4
      case 6:
        values[1] = compressedArray[0] & 15;
        values[0] = compressedArray[1] & 255;
        break;

      // 0 - 12
      case 7:
        values[0] = 0;
        values[1] = (((compressedArray[0] & 15) << 8) | (compressedArray[1] & 255));
        break;

      // 12 - 0
      case 8:
        values[1] = 0;
        values[0] = (((compressedArray[0] & 15) << 8) | (compressedArray[1] & 255));
        break;

      // 6 - 6
      case 9:
        values[0] = (((compressedArray[0] & 15) << 2) | ((compressedArray[1] >>> 6) & 3));
        values[1] = compressedArray[1] & 63;
        break;

      // 4 - 16
      case 10:
        values[0] = compressedArray[0] & 15;
        values[1] = (((compressedArray[1] & 255) << 8) | (compressedArray[2] & 255));
        break;

      // 16 - 4
      case 11:
        values[1] = compressedArray[0] & 15;
        values[0] = (((compressedArray[1] & 255) << 8) | (compressedArray[2] & 255));
        break;

      // 8 - 12
      case 12:
        values[1] = (((compressedArray[0] & 15) << 8) | (compressedArray[1] & 255));
        values[0] = compressedArray[2] & 255;
        break;

      // 12 - 8
      case 13:
        values[0] = (((compressedArray[0] & 15) << 8) | (compressedArray[1] & 255));
        values[1] = compressedArray[2] & 255;
        break;

      // 20 - 16
      case 14:
        values[0] = (((compressedArray[0] & 15) << 16) | ((compressedArray[1] & 255) << 8) | (compressedArray[2] & 255));
        values[1] = (((compressedArray[3] & 255) << 8) | (compressedArray[4] & 255));
        break;

        // 28 - 16
      case 15:
        values[0] = (((compressedArray[0] & 15) << 24) | ((compressedArray[1] & 255) << 16) | ((compressedArray[2] & 255) << 8) | (compressedArray[3] & 255));
        values[1] = (((compressedArray[4] & 255) << 8) | (compressedArray[5] & 255));
        break;
    }
    return values;
  }

  /**
   * Pack two integers using optimised routines
   * @param code The code of the configuration
   * @param value1 The first value to pack
   * @param value2 The second value to pack
   * @param compressedArray The compressed array
   */
  protected static final void pack(final int code, final int value1,
                                   final int value2, final byte[] compressedArray) {
    compressedArray[0] = (byte) ((code & 15) << 4);
    switch (code) {
      // 0 - 4
      case 0:
        compressedArray[0] |= (byte) (value2 & 15);
        break;

      // 4 - 0
      case 1:
        compressedArray[0] |= (byte) (value1 & 15);
        break;

      // 1 - 3
      case 2:
        compressedArray[0] |= (byte) (((value1 & 1) << 3) | (value2 & 7));
        break;

      // 3 - 1
      case 3:
        compressedArray[0] |= (byte) (((value2 & 1) << 3) | (value1 & 7));
        break;

      // 2 - 2
      case 4:
        compressedArray[0] |= (byte) (((value1 & 3) << 2) | (value2 & 3));
        break;

      // 4 - 8
      case 5:
        compressedArray[0] |= (byte) (value1 & 15);
        compressedArray[1] = (byte) (value2 & 255);
        break;

      // 8 - 4
      case 6:
        compressedArray[0] |= (byte) (value2 & 15);
        compressedArray[1] = (byte) (value1 & 255);
        break;

      // 0 - 12
      case 7:
        compressedArray[0] |= (byte) ((value2 >>> 8) & 15);
        compressedArray[1] = (byte) (value2 & 255);
        break;

      // 12 - 0
      case 8:
        compressedArray[0] |= (byte) ((value1 >>> 8) & 15);
        compressedArray[1] = (byte) (value1 & 255);
        break;

      // 6 - 6
      case 9:
        compressedArray[0] |= (byte) ((value1 >>> 2) & 15);
        compressedArray[1] = (byte) ((value1 & 3) << 6);
        compressedArray[1] |= (byte) (value2 & 63);
        break;

      // 4 - 16
      case 10:
        compressedArray[0] |= (byte) (value1 & 15);
        compressedArray[1] = (byte) ((value2 >>> 8) & 255);
        compressedArray[2] = (byte) (value2 & 255);
        break;

      // 16 - 4
      case 11:
        compressedArray[0] |= (byte) (value2 & 15);
        compressedArray[1] = (byte) ((value1 >>> 8) & 255);
        compressedArray[2] = (byte) (value1 & 255);
        break;

      // 8 - 12
      case 12:
        compressedArray[0] |= (byte) ((value2 >>> 8) & 15);
        compressedArray[1] = (byte) (value2 & 255);
        compressedArray[2] = (byte) (value1 & 255);
        break;

      // 12 - 8
      case 13:
        compressedArray[0] |= (byte) ((value1 >>> 8) & 15);
        compressedArray[1] = (byte) (value1 & 255);
        compressedArray[2] = (byte) (value2 & 255);
        break;

      // 20 - 16
      case 14:
        compressedArray[0] |= (byte) ((value1 >>> 16) & 15);
        compressedArray[1] = (byte) ((value1 >>> 8) & 255);
        compressedArray[2] = (byte) (value1 & 255);
        compressedArray[3] = (byte) ((value2 >>> 8) & 255);
        compressedArray[4] = (byte) (value2 & 255);
        break;

        // 28 - 16
      case 15:
        compressedArray[0] |= (byte) ((value1 >>> 24) & 15);
        compressedArray[1] = (byte) ((value1 >>> 16) & 255);
        compressedArray[2] = (byte) ((value1 >>> 8) & 255);
        compressedArray[3] = (byte) (value1 & 255);
        compressedArray[4] = (byte) ((value2 >>> 8) & 255);
        compressedArray[5] = (byte) (value2 & 255);
        break;
    }
  }

  private static final int[] LogTable256 = new int[256];

  static {
    LogTable256[0] = LogTable256[1] = 0;
    for (int i = 2; i < 256; i++)
    {
      LogTable256[i] = 1 + LogTable256[i / 2];
    }
  }

  private static int logNextHigherPowerOf2(final long v) {
    int r;
    long t, tt;

    tt = v >> 16;
    if (tt > 0) {
      r = (t = tt >> 8) > 0 ? 24 + LogTable256[(int) t] : 16 + LogTable256[(int) tt];
    } else {
      r = (t = v >> 8) > 0 ? 8 + LogTable256[(int) t] : LogTable256[(int) v];
    }
    return r;
  }

}
