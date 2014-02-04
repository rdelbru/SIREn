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
import org.sindice.siren.index.codecs.block.AForFrameDecompressor.FrameDecompressor;

/**
 * Implementation of {@link BlockDecompressor} based on the Adaptive Frame Of
 * Reference algorithm.
 *
 * @see AForBlockCompressor
 */
public class AForBlockDecompressor extends BlockDecompressor {

  protected final FrameDecompressor[] decompressors = AForFrameDecompressor.decompressors;

  @Override
  public void decompress(final BytesRef input, final IntsRef output) {
    assert output.ints.length % 32 == 0;
    final byte[] compressedArray = input.bytes;

    while (input.offset < input.length) {
      this.decompressors[compressedArray[input.offset]].decompress(input, output);
    }

    // flip buffer
    input.offset = 0;
    output.length = output.offset;
    output.offset = 0;
  }

  @Override
  public int getWindowSize() {
    return AForBlockCompressor.MAX_FRAME_SIZE;
  }

}
