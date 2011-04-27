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
 * @author Renaud Delbru [ 11 Dec 2008 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.apache.lucene.store.IndexInput;

/**
 * Various methods to encode / decode integers
 */
public class CodecUtils {

  /**
   * Convert a byte array to an int (32bit word).
   *
   * @param b The byte array (length = 4)
   * @return The integer
   */
  public static int byteArrayToInt(final byte[] b) {
    int value = 0;
    for (int i = 0; i < 4; i++) {
      value += (b[i] & 0x000000FF) << (4 - 1 - i) * 8;
    }
    return value;
  }

  /**
   * Convert an integer to a byte array.
   *
   * @param x The integer
   * @return The byte array (length = 4)
   */
  public static byte[] intToByteArray(final int x) {
    final byte[] bytes = new byte[4];
    bytes[3] = (byte) (x & 0x000000ff);
    bytes[2] = (byte) ((x & 0x0000ff00) >>> 8);
    bytes[1] = (byte) ((x & 0x00ff0000) >>> 16);
    bytes[0] = (byte) ((x & 0xff000000) >>> 24);
    return bytes;
  }

  /**
   * Convert an integer to a byte array.
   *
   * @param x The integer
   * @param bytes The byte array (length = 4)
   */
  public static final void intToByteArray(final int x, final byte[] bytes) {
    bytes[3] = (byte) (x & 0x000000ff);
    bytes[2] = (byte) ((x & 0x0000ff00) >>> 8);
    bytes[1] = (byte) ((x & 0x00ff0000) >>> 16);
    bytes[0] = (byte) ((x & 0xff000000) >>> 24);
  }

  /**
   * Convert an integer to a byte array. Copy 'length' integer bytes.
   *
   * @param x The integer
   * @param bytes The byte array
   * @param length The number of bytes to copy into the byte array
   */
  public static final void intToByteArray(final int x, final byte[] bytes,
                                          final int length) {
    switch (length) {
      case 1:
        bytes[0] = (byte) (x & 0x000000ff);
        return;

      case 2:
        bytes[1] = (byte) (x & 0x000000ff);
        bytes[0] = (byte) ((x & 0x0000ff00) >>> 8);
        return;

      case 3:
        bytes[2] = (byte) (x & 0x000000ff);
        bytes[1] = (byte) ((x & 0x0000ff00) >>> 8);
        bytes[0] = (byte) ((x & 0x00ff0000) >>> 16);
        return;

      case 4:
        bytes[3] = (byte) (x & 0x000000ff);
        bytes[2] = (byte) ((x & 0x0000ff00) >>> 8);
        bytes[1] = (byte) ((x & 0x00ff0000) >>> 16);
        bytes[0] = (byte) ((x & 0xff000000) >>> 24);

      default:
        break;
    }
  }

  /**
   * Convert an integer into bytes, and store them into the byte buffer.
   *
   * @param x The integer
   * @param buffer The byte buffer to be filled
   */
  public static void intToBytes(final int x, final ByteBuffer buffer) {
    buffer.put(3, (byte) (x & 0x000000ff));
    buffer.put(2, (byte) ((x & 0x0000ff00) >>> 8));
    buffer.put(1, (byte) ((x & 0x00ff0000) >>> 16));
    buffer.put(0, (byte) ((x & 0xff000000) >>> 24));
  }

  /**
   * Convert a short to a byte array.
   *
   * @param x The short
   * @return The byte array (length = 2)
   */
  public static byte[] shortToByteArray(final short x) {
    final byte[] bytes = new byte[2];
    bytes[1] = (byte) (x & 0x000000ff);
    bytes[0] = (byte) ((x & 0x0000ff00) >>> 8);
    return bytes;
  }

  /**
   * Convert a short into bytes, and store them into the byte buffer.
   *
   * @param x The short
   * @param buffer The byte buffer to be filled
   */
  public static void shortToByteArray(final short x, final ByteBuffer buffer) {
    buffer.put(1, (byte) (x & 0x000000ff));
    buffer.put(0, (byte) ((x & 0x0000ff00) >>> 8));
  }

  /**
   * Writes an integer in a variable-length format. Writes between one and
   * five bytes. Smaller values take fewer bytes. Negative numbers are not
   * supported.
   */
  public static byte[] vIntToByteArray(int i) {
    final byte[] bytes = new byte[5];
    int pos = 0;

    while ((i & ~0x7F) != 0) {
      bytes[pos] = (byte) ((i & 0x7f) | 0x80);
      i >>>= 7;
      pos++;
    }
    bytes[pos] = (byte) i;

    final byte[] newArray = new byte[pos];
    System.arraycopy(bytes, 0, newArray, 0, pos);
    return newArray;
  }

  /**
   * Writes an integer in a variable-length format. Writes between one and
   * five bytes. Smaller values take fewer bytes. Negative numbers are not
   * supported.
   * @see IndexInput#readVInt()
   */
  public static void vIntToByteArray(int i, final ByteArrayOutputStream bytes) {
    while ((i & ~0x7F) != 0) {
      bytes.write((byte)((i & 0x7f) | 0x80));
      i >>>= 7;
    }
    bytes.write((byte) i);
  }

  /**
   * Reads an int stored in variable-length format. Reads between one and five
   * bytes. Smaller values take fewer bytes. Negative numbers are not supported.
   */
  public static int byteArrayToVInt(final ByteArrayInputStream bytes) {
    byte b = (byte) bytes.read();
    int i = b & 0x7F;
    for (int shift = 7; (b & 0x80) != 0; shift += 7) {
      b = (byte) bytes.read();
      i |= (b & 0x7F) << shift;
    }
    return i;
  }

  /**
   * Reads an int stored in variable-length format. Reads between one and five
   * bytes. Smaller values take fewer bytes. Negative numbers are not supported.
   */
  public static int[] shortArrayToIntArray(final short[] values) {
    final int[] result = new int[values.length];
    for (int i = 0; i < values.length; i++)
      result[i] = values[i];
    return result;
  }

  /**
   * Write the content, i.e. bits, of a byte into the string buffer.
   * </br>
   * Code taken from Lucene-1410.
   *
   * @param b The byte
   * @param buf the string buffer
   */
  private static void writeBits(final int b, final StringBuffer buf) {
    for (int i = 7; i >= 0; i--) {
      buf.append((b >>> i) & 1);
    }
  }

  /**
   * Display the content, i.e. bits, of a byte array. Write to stdout the
   * content of the array on a single line.
   * </br>
   * Code taken from Lucene-1410.
   *
   * @param array The byte array
   */
  public static void writeBytes(final byte[] array) {
    final StringBuffer buf = new StringBuffer();
    for (final byte element : array) {
      CodecUtils.writeBits(element & 255, buf);
      buf.append(' ');
    }
    System.out.println(buf);
  }

  /**
   * Display the content, i.e. bits, of a byte array. Write to stdout 4 bytes
   * per lines.
   * </br>
   * Code taken from Lucene-1410.
   *
   * @param array The byte array
   */
  public static void writeIndentBytes(final byte[] array) {
    final StringBuffer buf = new StringBuffer();
    for (int i = 0; i < array.length; i++) {
      CodecUtils.writeBits(array[i] & 255, buf);
      if (((i+1) % 4) != 0) {
        buf.append(' ');
      } else {
        System.out.println(buf);
        buf.setLength(0);
      }
    }
  }

}
