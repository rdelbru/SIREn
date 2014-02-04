/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sindice.siren.index.codecs.block;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;

/**
 * Implementation of the {@link BlockCompressor} based on the Variable Integer
 * encoding algorithm.
 */
public class VIntBlockCompressor extends BlockCompressor {

  /** Size of header in buffer */
  protected final int HEADER_SIZE = 0;

  /**
   * Compress the uncompressed data into the buffer using variable integer
   * encoding technique. All uncompressed integers are stored sequentially in
   * compressed form in the buffer after the header.
   * <p>
   * No header is stored.
   */
  @Override
  public void compress(final IntsRef input, final BytesRef output) {
    final int[] uncompressedData = input.ints;
    final byte[] compressedData = output.bytes;

    for (int i = 0; i < input.length; i++) {
      int value = uncompressedData[i];
      while ((value & ~0x7F) != 0) {
        compressedData[output.offset++] = (byte) ((value & 0x7F) | 0x80);
        value >>>= 7;
      }
      compressedData[output.offset++] = (byte) value;
    }

    // flip buffer
    output.length = output.offset;
    output.offset = 0;
  }

  @Override
  public int maxCompressedSize(final int arraySize) {
    return HEADER_SIZE + (5 * arraySize);
  }

  @Override
  public int getWindowSize() {
    return 1;
  }

}
