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
 * Implementation of the {@link BlockDecompressor} based on the Variable Integer
 * encoding algorithm.
 */
public class VIntBlockDecompressor extends BlockDecompressor {

  @Override
  public void decompress(final BytesRef input, final IntsRef output) {
    final byte[] compressedData = input.bytes;
    final int[] unCompressedData = output.ints;

    while (input.offset < input.length) {
      byte b = compressedData[input.offset++];
      int i = b & 0x7F;
      for (int shift = 7; (b & 0x80) != 0; shift += 7) {
        b = compressedData[input.offset++];
        i |= (b & 0x7F) << shift;
      }
      unCompressedData[output.offset++] = i;
    }

    // flip buffer
    input.offset = 0;
    output.length = output.offset;
    output.offset = 0;
  }

  @Override
  public int getWindowSize() {
    return 1;
  }

}
