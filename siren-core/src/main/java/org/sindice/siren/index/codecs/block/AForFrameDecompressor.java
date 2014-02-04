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
/* This program is generated, do not modify. See AForFrameDecompressorGenerator.java */
package org.sindice.siren.index.codecs.block;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;

/**
 * This class contains a lookup table of functors for decompressing fames.
 */
public class AForFrameDecompressor {

  public static final FrameDecompressor[] decompressors = new FrameDecompressor[] {
    new FrameDecompressor0(),
    new FrameDecompressor1(),
    new FrameDecompressor2(),
    new FrameDecompressor3(),
    new FrameDecompressor4(),
    new FrameDecompressor5(),
    new FrameDecompressor6(),
    new FrameDecompressor7(),
    new FrameDecompressor8(),
    new FrameDecompressor9(),
    new FrameDecompressor10(),
    new FrameDecompressor11(),
    new FrameDecompressor12(),
    new FrameDecompressor13(),
    new FrameDecompressor14(),
    new FrameDecompressor15(),
    new FrameDecompressor16(),
    new FrameDecompressor17(),
    new FrameDecompressor18(),
    new FrameDecompressor19(),
    new FrameDecompressor20(),
    new FrameDecompressor21(),
    new FrameDecompressor22(),
    new FrameDecompressor23(),
    new FrameDecompressor24(),
    new FrameDecompressor25(),
    new FrameDecompressor26(),
    new FrameDecompressor27(),
    new FrameDecompressor28(),
    new FrameDecompressor29(),
    new FrameDecompressor30(),
    new FrameDecompressor31(),
    new FrameDecompressor32(),
    new FrameDecompressor33(),
    new FrameDecompressor34(),
    new FrameDecompressor35(),
    new FrameDecompressor36(),
    new FrameDecompressor37(),
    new FrameDecompressor38(),
    new FrameDecompressor39(),
    new FrameDecompressor40(),
    new FrameDecompressor41(),
    new FrameDecompressor42(),
    new FrameDecompressor43(),
    new FrameDecompressor44(),
    new FrameDecompressor45(),
    new FrameDecompressor46(),
    new FrameDecompressor47(),
    new FrameDecompressor48(),
    new FrameDecompressor49(),
    new FrameDecompressor50(),
    new FrameDecompressor51(),
    new FrameDecompressor52(),
    new FrameDecompressor53(),
    new FrameDecompressor54(),
    new FrameDecompressor55(),
    new FrameDecompressor56(),
    new FrameDecompressor57(),
    new FrameDecompressor58(),
    new FrameDecompressor59(),
    new FrameDecompressor60(),
    new FrameDecompressor61(),
    new FrameDecompressor62(),
    new FrameDecompressor63(),
    new FrameDecompressor64(),
    new FrameDecompressor65(),
    new FrameDecompressor66(),
    new FrameDecompressor67(),
    new FrameDecompressor68(),
    new FrameDecompressor69(),
    new FrameDecompressor70(),
    new FrameDecompressor71(),
    new FrameDecompressor72(),
    new FrameDecompressor73(),
    new FrameDecompressor74(),
    new FrameDecompressor75(),
    new FrameDecompressor76(),
    new FrameDecompressor77(),
    new FrameDecompressor78(),
    new FrameDecompressor79(),
    new FrameDecompressor80(),
    new FrameDecompressor81(),
    new FrameDecompressor82(),
    new FrameDecompressor83(),
    new FrameDecompressor84(),
    new FrameDecompressor85(),
    new FrameDecompressor86(),
    new FrameDecompressor87(),
    new FrameDecompressor88(),
    new FrameDecompressor89(),
    new FrameDecompressor90(),
    new FrameDecompressor91(),
    new FrameDecompressor92(),
    new FrameDecompressor93(),
    new FrameDecompressor94(),
    new FrameDecompressor95(),
    new FrameDecompressor96(),
    new FrameDecompressor97(),
    new FrameDecompressor98(),
  };

  static abstract class FrameDecompressor {
    public abstract void decompress(final BytesRef input, final IntsRef output);
  }

  static final class FrameDecompressor0 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final int outOffset = output.offset;
      unCompressedData[outOffset] = 0;
      unCompressedData[outOffset + 1] = 0;
      unCompressedData[outOffset + 2] = 0;
      unCompressedData[outOffset + 3] = 0;
      unCompressedData[outOffset + 4] = 0;
      unCompressedData[outOffset + 5] = 0;
      unCompressedData[outOffset + 6] = 0;
      unCompressedData[outOffset + 7] = 0;
      unCompressedData[outOffset + 8] = 0;
      unCompressedData[outOffset + 9] = 0;
      unCompressedData[outOffset + 10] = 0;
      unCompressedData[outOffset + 11] = 0;
      unCompressedData[outOffset + 12] = 0;
      unCompressedData[outOffset + 13] = 0;
      unCompressedData[outOffset + 14] = 0;
      unCompressedData[outOffset + 15] = 0;
      unCompressedData[outOffset + 16] = 0;
      unCompressedData[outOffset + 17] = 0;
      unCompressedData[outOffset + 18] = 0;
      unCompressedData[outOffset + 19] = 0;
      unCompressedData[outOffset + 20] = 0;
      unCompressedData[outOffset + 21] = 0;
      unCompressedData[outOffset + 22] = 0;
      unCompressedData[outOffset + 23] = 0;
      unCompressedData[outOffset + 24] = 0;
      unCompressedData[outOffset + 25] = 0;
      unCompressedData[outOffset + 26] = 0;
      unCompressedData[outOffset + 27] = 0;
      unCompressedData[outOffset + 28] = 0;
      unCompressedData[outOffset + 29] = 0;
      unCompressedData[outOffset + 30] = 0;
      unCompressedData[outOffset + 31] = 0;
      output.offset += 32;
      input.offset += 1;
  }
  }

  static final class FrameDecompressor1 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 31);
      unCompressedData[outOffset + 1] = (i0 >>> 30) & 1;
      unCompressedData[outOffset + 2] = (i0 >>> 29) & 1;
      unCompressedData[outOffset + 3] = (i0 >>> 28) & 1;
      unCompressedData[outOffset + 4] = (i0 >>> 27) & 1;
      unCompressedData[outOffset + 5] = (i0 >>> 26) & 1;
      unCompressedData[outOffset + 6] = (i0 >>> 25) & 1;
      unCompressedData[outOffset + 7] = (i0 >>> 24) & 1;
      unCompressedData[outOffset + 8] = (i0 >>> 23) & 1;
      unCompressedData[outOffset + 9] = (i0 >>> 22) & 1;
      unCompressedData[outOffset + 10] = (i0 >>> 21) & 1;
      unCompressedData[outOffset + 11] = (i0 >>> 20) & 1;
      unCompressedData[outOffset + 12] = (i0 >>> 19) & 1;
      unCompressedData[outOffset + 13] = (i0 >>> 18) & 1;
      unCompressedData[outOffset + 14] = (i0 >>> 17) & 1;
      unCompressedData[outOffset + 15] = (i0 >>> 16) & 1;
      unCompressedData[outOffset + 16] = (i0 >>> 15) & 1;
      unCompressedData[outOffset + 17] = (i0 >>> 14) & 1;
      unCompressedData[outOffset + 18] = (i0 >>> 13) & 1;
      unCompressedData[outOffset + 19] = (i0 >>> 12) & 1;
      unCompressedData[outOffset + 20] = (i0 >>> 11) & 1;
      unCompressedData[outOffset + 21] = (i0 >>> 10) & 1;
      unCompressedData[outOffset + 22] = (i0 >>> 9) & 1;
      unCompressedData[outOffset + 23] = (i0 >>> 8) & 1;
      unCompressedData[outOffset + 24] = (i0 >>> 7) & 1;
      unCompressedData[outOffset + 25] = (i0 >>> 6) & 1;
      unCompressedData[outOffset + 26] = (i0 >>> 5) & 1;
      unCompressedData[outOffset + 27] = (i0 >>> 4) & 1;
      unCompressedData[outOffset + 28] = (i0 >>> 3) & 1;
      unCompressedData[outOffset + 29] = (i0 >>> 2) & 1;
      unCompressedData[outOffset + 30] = (i0 >>> 1) & 1;
      unCompressedData[outOffset + 31] = i0 & 1;
      input.offset += 5;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor2 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 30);
      unCompressedData[outOffset + 1] = (i0 >>> 28) & 3;
      unCompressedData[outOffset + 2] = (i0 >>> 26) & 3;
      unCompressedData[outOffset + 3] = (i0 >>> 24) & 3;
      unCompressedData[outOffset + 4] = (i0 >>> 22) & 3;
      unCompressedData[outOffset + 5] = (i0 >>> 20) & 3;
      unCompressedData[outOffset + 6] = (i0 >>> 18) & 3;
      unCompressedData[outOffset + 7] = (i0 >>> 16) & 3;
      unCompressedData[outOffset + 8] = (i0 >>> 14) & 3;
      unCompressedData[outOffset + 9] = (i0 >>> 12) & 3;
      unCompressedData[outOffset + 10] = (i0 >>> 10) & 3;
      unCompressedData[outOffset + 11] = (i0 >>> 8) & 3;
      unCompressedData[outOffset + 12] = (i0 >>> 6) & 3;
      unCompressedData[outOffset + 13] = (i0 >>> 4) & 3;
      unCompressedData[outOffset + 14] = (i0 >>> 2) & 3;
      unCompressedData[outOffset + 15] = i0 & 3;
      unCompressedData[outOffset + 16] = (i1 >>> 30);
      unCompressedData[outOffset + 17] = (i1 >>> 28) & 3;
      unCompressedData[outOffset + 18] = (i1 >>> 26) & 3;
      unCompressedData[outOffset + 19] = (i1 >>> 24) & 3;
      unCompressedData[outOffset + 20] = (i1 >>> 22) & 3;
      unCompressedData[outOffset + 21] = (i1 >>> 20) & 3;
      unCompressedData[outOffset + 22] = (i1 >>> 18) & 3;
      unCompressedData[outOffset + 23] = (i1 >>> 16) & 3;
      unCompressedData[outOffset + 24] = (i1 >>> 14) & 3;
      unCompressedData[outOffset + 25] = (i1 >>> 12) & 3;
      unCompressedData[outOffset + 26] = (i1 >>> 10) & 3;
      unCompressedData[outOffset + 27] = (i1 >>> 8) & 3;
      unCompressedData[outOffset + 28] = (i1 >>> 6) & 3;
      unCompressedData[outOffset + 29] = (i1 >>> 4) & 3;
      unCompressedData[outOffset + 30] = (i1 >>> 2) & 3;
      unCompressedData[outOffset + 31] = i1 & 3;
      input.offset += 9;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor3 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 29);
      unCompressedData[outOffset + 1] = (i0 >>> 26) & 7;
      unCompressedData[outOffset + 2] = (i0 >>> 23) & 7;
      unCompressedData[outOffset + 3] = (i0 >>> 20) & 7;
      unCompressedData[outOffset + 4] = (i0 >>> 17) & 7;
      unCompressedData[outOffset + 5] = (i0 >>> 14) & 7;
      unCompressedData[outOffset + 6] = (i0 >>> 11) & 7;
      unCompressedData[outOffset + 7] = (i0 >>> 8) & 7;
      unCompressedData[outOffset + 8] = (i0 >>> 5) & 7;
      unCompressedData[outOffset + 9] = (i0 >>> 2) & 7;
      unCompressedData[outOffset + 10] = ((i0 << 1) | (i1 >>> 31)) & 7;
      unCompressedData[outOffset + 11] = (i1 >>> 28) & 7;
      unCompressedData[outOffset + 12] = (i1 >>> 25) & 7;
      unCompressedData[outOffset + 13] = (i1 >>> 22) & 7;
      unCompressedData[outOffset + 14] = (i1 >>> 19) & 7;
      unCompressedData[outOffset + 15] = (i1 >>> 16) & 7;
      unCompressedData[outOffset + 16] = (i1 >>> 13) & 7;
      unCompressedData[outOffset + 17] = (i1 >>> 10) & 7;
      unCompressedData[outOffset + 18] = (i1 >>> 7) & 7;
      unCompressedData[outOffset + 19] = (i1 >>> 4) & 7;
      unCompressedData[outOffset + 20] = (i1 >>> 1) & 7;
      unCompressedData[outOffset + 21] = ((i1 << 2) | (i2 >>> 30)) & 7;
      unCompressedData[outOffset + 22] = (i2 >>> 27) & 7;
      unCompressedData[outOffset + 23] = (i2 >>> 24) & 7;
      unCompressedData[outOffset + 24] = (i2 >>> 21) & 7;
      unCompressedData[outOffset + 25] = (i2 >>> 18) & 7;
      unCompressedData[outOffset + 26] = (i2 >>> 15) & 7;
      unCompressedData[outOffset + 27] = (i2 >>> 12) & 7;
      unCompressedData[outOffset + 28] = (i2 >>> 9) & 7;
      unCompressedData[outOffset + 29] = (i2 >>> 6) & 7;
      unCompressedData[outOffset + 30] = (i2 >>> 3) & 7;
      unCompressedData[outOffset + 31] = i2 & 7;
      input.offset += 13;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor4 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 28);
      unCompressedData[outOffset + 1] = (i0 >>> 24) & 15;
      unCompressedData[outOffset + 2] = (i0 >>> 20) & 15;
      unCompressedData[outOffset + 3] = (i0 >>> 16) & 15;
      unCompressedData[outOffset + 4] = (i0 >>> 12) & 15;
      unCompressedData[outOffset + 5] = (i0 >>> 8) & 15;
      unCompressedData[outOffset + 6] = (i0 >>> 4) & 15;
      unCompressedData[outOffset + 7] = i0 & 15;
      unCompressedData[outOffset + 8] = (i1 >>> 28);
      unCompressedData[outOffset + 9] = (i1 >>> 24) & 15;
      unCompressedData[outOffset + 10] = (i1 >>> 20) & 15;
      unCompressedData[outOffset + 11] = (i1 >>> 16) & 15;
      unCompressedData[outOffset + 12] = (i1 >>> 12) & 15;
      unCompressedData[outOffset + 13] = (i1 >>> 8) & 15;
      unCompressedData[outOffset + 14] = (i1 >>> 4) & 15;
      unCompressedData[outOffset + 15] = i1 & 15;
      unCompressedData[outOffset + 16] = (i2 >>> 28);
      unCompressedData[outOffset + 17] = (i2 >>> 24) & 15;
      unCompressedData[outOffset + 18] = (i2 >>> 20) & 15;
      unCompressedData[outOffset + 19] = (i2 >>> 16) & 15;
      unCompressedData[outOffset + 20] = (i2 >>> 12) & 15;
      unCompressedData[outOffset + 21] = (i2 >>> 8) & 15;
      unCompressedData[outOffset + 22] = (i2 >>> 4) & 15;
      unCompressedData[outOffset + 23] = i2 & 15;
      unCompressedData[outOffset + 24] = (i3 >>> 28);
      unCompressedData[outOffset + 25] = (i3 >>> 24) & 15;
      unCompressedData[outOffset + 26] = (i3 >>> 20) & 15;
      unCompressedData[outOffset + 27] = (i3 >>> 16) & 15;
      unCompressedData[outOffset + 28] = (i3 >>> 12) & 15;
      unCompressedData[outOffset + 29] = (i3 >>> 8) & 15;
      unCompressedData[outOffset + 30] = (i3 >>> 4) & 15;
      unCompressedData[outOffset + 31] = i3 & 15;
      input.offset += 17;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor5 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 27);
      unCompressedData[outOffset + 1] = (i0 >>> 22) & 31;
      unCompressedData[outOffset + 2] = (i0 >>> 17) & 31;
      unCompressedData[outOffset + 3] = (i0 >>> 12) & 31;
      unCompressedData[outOffset + 4] = (i0 >>> 7) & 31;
      unCompressedData[outOffset + 5] = (i0 >>> 2) & 31;
      unCompressedData[outOffset + 6] = ((i0 << 3) | (i1 >>> 29)) & 31;
      unCompressedData[outOffset + 7] = (i1 >>> 24) & 31;
      unCompressedData[outOffset + 8] = (i1 >>> 19) & 31;
      unCompressedData[outOffset + 9] = (i1 >>> 14) & 31;
      unCompressedData[outOffset + 10] = (i1 >>> 9) & 31;
      unCompressedData[outOffset + 11] = (i1 >>> 4) & 31;
      unCompressedData[outOffset + 12] = ((i1 << 1) | (i2 >>> 31)) & 31;
      unCompressedData[outOffset + 13] = (i2 >>> 26) & 31;
      unCompressedData[outOffset + 14] = (i2 >>> 21) & 31;
      unCompressedData[outOffset + 15] = (i2 >>> 16) & 31;
      unCompressedData[outOffset + 16] = (i2 >>> 11) & 31;
      unCompressedData[outOffset + 17] = (i2 >>> 6) & 31;
      unCompressedData[outOffset + 18] = (i2 >>> 1) & 31;
      unCompressedData[outOffset + 19] = ((i2 << 4) | (i3 >>> 28)) & 31;
      unCompressedData[outOffset + 20] = (i3 >>> 23) & 31;
      unCompressedData[outOffset + 21] = (i3 >>> 18) & 31;
      unCompressedData[outOffset + 22] = (i3 >>> 13) & 31;
      unCompressedData[outOffset + 23] = (i3 >>> 8) & 31;
      unCompressedData[outOffset + 24] = (i3 >>> 3) & 31;
      unCompressedData[outOffset + 25] = ((i3 << 2) | (i4 >>> 30)) & 31;
      unCompressedData[outOffset + 26] = (i4 >>> 25) & 31;
      unCompressedData[outOffset + 27] = (i4 >>> 20) & 31;
      unCompressedData[outOffset + 28] = (i4 >>> 15) & 31;
      unCompressedData[outOffset + 29] = (i4 >>> 10) & 31;
      unCompressedData[outOffset + 30] = (i4 >>> 5) & 31;
      unCompressedData[outOffset + 31] = i4 & 31;
      input.offset += 21;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor6 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 26);
      unCompressedData[outOffset + 1] = (i0 >>> 20) & 63;
      unCompressedData[outOffset + 2] = (i0 >>> 14) & 63;
      unCompressedData[outOffset + 3] = (i0 >>> 8) & 63;
      unCompressedData[outOffset + 4] = (i0 >>> 2) & 63;
      unCompressedData[outOffset + 5] = ((i0 << 4) | (i1 >>> 28)) & 63;
      unCompressedData[outOffset + 6] = (i1 >>> 22) & 63;
      unCompressedData[outOffset + 7] = (i1 >>> 16) & 63;
      unCompressedData[outOffset + 8] = (i1 >>> 10) & 63;
      unCompressedData[outOffset + 9] = (i1 >>> 4) & 63;
      unCompressedData[outOffset + 10] = ((i1 << 2) | (i2 >>> 30)) & 63;
      unCompressedData[outOffset + 11] = (i2 >>> 24) & 63;
      unCompressedData[outOffset + 12] = (i2 >>> 18) & 63;
      unCompressedData[outOffset + 13] = (i2 >>> 12) & 63;
      unCompressedData[outOffset + 14] = (i2 >>> 6) & 63;
      unCompressedData[outOffset + 15] = i2 & 63;
      unCompressedData[outOffset + 16] = (i3 >>> 26);
      unCompressedData[outOffset + 17] = (i3 >>> 20) & 63;
      unCompressedData[outOffset + 18] = (i3 >>> 14) & 63;
      unCompressedData[outOffset + 19] = (i3 >>> 8) & 63;
      unCompressedData[outOffset + 20] = (i3 >>> 2) & 63;
      unCompressedData[outOffset + 21] = ((i3 << 4) | (i4 >>> 28)) & 63;
      unCompressedData[outOffset + 22] = (i4 >>> 22) & 63;
      unCompressedData[outOffset + 23] = (i4 >>> 16) & 63;
      unCompressedData[outOffset + 24] = (i4 >>> 10) & 63;
      unCompressedData[outOffset + 25] = (i4 >>> 4) & 63;
      unCompressedData[outOffset + 26] = ((i4 << 2) | (i5 >>> 30)) & 63;
      unCompressedData[outOffset + 27] = (i5 >>> 24) & 63;
      unCompressedData[outOffset + 28] = (i5 >>> 18) & 63;
      unCompressedData[outOffset + 29] = (i5 >>> 12) & 63;
      unCompressedData[outOffset + 30] = (i5 >>> 6) & 63;
      unCompressedData[outOffset + 31] = i5 & 63;
      input.offset += 25;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor7 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 25);
      unCompressedData[outOffset + 1] = (i0 >>> 18) & 127;
      unCompressedData[outOffset + 2] = (i0 >>> 11) & 127;
      unCompressedData[outOffset + 3] = (i0 >>> 4) & 127;
      unCompressedData[outOffset + 4] = ((i0 << 3) | (i1 >>> 29)) & 127;
      unCompressedData[outOffset + 5] = (i1 >>> 22) & 127;
      unCompressedData[outOffset + 6] = (i1 >>> 15) & 127;
      unCompressedData[outOffset + 7] = (i1 >>> 8) & 127;
      unCompressedData[outOffset + 8] = (i1 >>> 1) & 127;
      unCompressedData[outOffset + 9] = ((i1 << 6) | (i2 >>> 26)) & 127;
      unCompressedData[outOffset + 10] = (i2 >>> 19) & 127;
      unCompressedData[outOffset + 11] = (i2 >>> 12) & 127;
      unCompressedData[outOffset + 12] = (i2 >>> 5) & 127;
      unCompressedData[outOffset + 13] = ((i2 << 2) | (i3 >>> 30)) & 127;
      unCompressedData[outOffset + 14] = (i3 >>> 23) & 127;
      unCompressedData[outOffset + 15] = (i3 >>> 16) & 127;
      unCompressedData[outOffset + 16] = (i3 >>> 9) & 127;
      unCompressedData[outOffset + 17] = (i3 >>> 2) & 127;
      unCompressedData[outOffset + 18] = ((i3 << 5) | (i4 >>> 27)) & 127;
      unCompressedData[outOffset + 19] = (i4 >>> 20) & 127;
      unCompressedData[outOffset + 20] = (i4 >>> 13) & 127;
      unCompressedData[outOffset + 21] = (i4 >>> 6) & 127;
      unCompressedData[outOffset + 22] = ((i4 << 1) | (i5 >>> 31)) & 127;
      unCompressedData[outOffset + 23] = (i5 >>> 24) & 127;
      unCompressedData[outOffset + 24] = (i5 >>> 17) & 127;
      unCompressedData[outOffset + 25] = (i5 >>> 10) & 127;
      unCompressedData[outOffset + 26] = (i5 >>> 3) & 127;
      unCompressedData[outOffset + 27] = ((i5 << 4) | (i6 >>> 28)) & 127;
      unCompressedData[outOffset + 28] = (i6 >>> 21) & 127;
      unCompressedData[outOffset + 29] = (i6 >>> 14) & 127;
      unCompressedData[outOffset + 30] = (i6 >>> 7) & 127;
      unCompressedData[outOffset + 31] = i6 & 127;
      input.offset += 29;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor8 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 24);
      unCompressedData[outOffset + 1] = (i0 >>> 16) & 255;
      unCompressedData[outOffset + 2] = (i0 >>> 8) & 255;
      unCompressedData[outOffset + 3] = i0 & 255;
      unCompressedData[outOffset + 4] = (i1 >>> 24);
      unCompressedData[outOffset + 5] = (i1 >>> 16) & 255;
      unCompressedData[outOffset + 6] = (i1 >>> 8) & 255;
      unCompressedData[outOffset + 7] = i1 & 255;
      unCompressedData[outOffset + 8] = (i2 >>> 24);
      unCompressedData[outOffset + 9] = (i2 >>> 16) & 255;
      unCompressedData[outOffset + 10] = (i2 >>> 8) & 255;
      unCompressedData[outOffset + 11] = i2 & 255;
      unCompressedData[outOffset + 12] = (i3 >>> 24);
      unCompressedData[outOffset + 13] = (i3 >>> 16) & 255;
      unCompressedData[outOffset + 14] = (i3 >>> 8) & 255;
      unCompressedData[outOffset + 15] = i3 & 255;
      unCompressedData[outOffset + 16] = (i4 >>> 24);
      unCompressedData[outOffset + 17] = (i4 >>> 16) & 255;
      unCompressedData[outOffset + 18] = (i4 >>> 8) & 255;
      unCompressedData[outOffset + 19] = i4 & 255;
      unCompressedData[outOffset + 20] = (i5 >>> 24);
      unCompressedData[outOffset + 21] = (i5 >>> 16) & 255;
      unCompressedData[outOffset + 22] = (i5 >>> 8) & 255;
      unCompressedData[outOffset + 23] = i5 & 255;
      unCompressedData[outOffset + 24] = (i6 >>> 24);
      unCompressedData[outOffset + 25] = (i6 >>> 16) & 255;
      unCompressedData[outOffset + 26] = (i6 >>> 8) & 255;
      unCompressedData[outOffset + 27] = i6 & 255;
      unCompressedData[outOffset + 28] = (i7 >>> 24);
      unCompressedData[outOffset + 29] = (i7 >>> 16) & 255;
      unCompressedData[outOffset + 30] = (i7 >>> 8) & 255;
      unCompressedData[outOffset + 31] = i7 & 255;
      input.offset += 33;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor9 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 23);
      unCompressedData[outOffset + 1] = (i0 >>> 14) & 511;
      unCompressedData[outOffset + 2] = (i0 >>> 5) & 511;
      unCompressedData[outOffset + 3] = ((i0 << 4) | (i1 >>> 28)) & 511;
      unCompressedData[outOffset + 4] = (i1 >>> 19) & 511;
      unCompressedData[outOffset + 5] = (i1 >>> 10) & 511;
      unCompressedData[outOffset + 6] = (i1 >>> 1) & 511;
      unCompressedData[outOffset + 7] = ((i1 << 8) | (i2 >>> 24)) & 511;
      unCompressedData[outOffset + 8] = (i2 >>> 15) & 511;
      unCompressedData[outOffset + 9] = (i2 >>> 6) & 511;
      unCompressedData[outOffset + 10] = ((i2 << 3) | (i3 >>> 29)) & 511;
      unCompressedData[outOffset + 11] = (i3 >>> 20) & 511;
      unCompressedData[outOffset + 12] = (i3 >>> 11) & 511;
      unCompressedData[outOffset + 13] = (i3 >>> 2) & 511;
      unCompressedData[outOffset + 14] = ((i3 << 7) | (i4 >>> 25)) & 511;
      unCompressedData[outOffset + 15] = (i4 >>> 16) & 511;
      unCompressedData[outOffset + 16] = (i4 >>> 7) & 511;
      unCompressedData[outOffset + 17] = ((i4 << 2) | (i5 >>> 30)) & 511;
      unCompressedData[outOffset + 18] = (i5 >>> 21) & 511;
      unCompressedData[outOffset + 19] = (i5 >>> 12) & 511;
      unCompressedData[outOffset + 20] = (i5 >>> 3) & 511;
      unCompressedData[outOffset + 21] = ((i5 << 6) | (i6 >>> 26)) & 511;
      unCompressedData[outOffset + 22] = (i6 >>> 17) & 511;
      unCompressedData[outOffset + 23] = (i6 >>> 8) & 511;
      unCompressedData[outOffset + 24] = ((i6 << 1) | (i7 >>> 31)) & 511;
      unCompressedData[outOffset + 25] = (i7 >>> 22) & 511;
      unCompressedData[outOffset + 26] = (i7 >>> 13) & 511;
      unCompressedData[outOffset + 27] = (i7 >>> 4) & 511;
      unCompressedData[outOffset + 28] = ((i7 << 5) | (i8 >>> 27)) & 511;
      unCompressedData[outOffset + 29] = (i8 >>> 18) & 511;
      unCompressedData[outOffset + 30] = (i8 >>> 9) & 511;
      unCompressedData[outOffset + 31] = i8 & 511;
      input.offset += 37;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor10 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 22);
      unCompressedData[outOffset + 1] = (i0 >>> 12) & 1023;
      unCompressedData[outOffset + 2] = (i0 >>> 2) & 1023;
      unCompressedData[outOffset + 3] = ((i0 << 8) | (i1 >>> 24)) & 1023;
      unCompressedData[outOffset + 4] = (i1 >>> 14) & 1023;
      unCompressedData[outOffset + 5] = (i1 >>> 4) & 1023;
      unCompressedData[outOffset + 6] = ((i1 << 6) | (i2 >>> 26)) & 1023;
      unCompressedData[outOffset + 7] = (i2 >>> 16) & 1023;
      unCompressedData[outOffset + 8] = (i2 >>> 6) & 1023;
      unCompressedData[outOffset + 9] = ((i2 << 4) | (i3 >>> 28)) & 1023;
      unCompressedData[outOffset + 10] = (i3 >>> 18) & 1023;
      unCompressedData[outOffset + 11] = (i3 >>> 8) & 1023;
      unCompressedData[outOffset + 12] = ((i3 << 2) | (i4 >>> 30)) & 1023;
      unCompressedData[outOffset + 13] = (i4 >>> 20) & 1023;
      unCompressedData[outOffset + 14] = (i4 >>> 10) & 1023;
      unCompressedData[outOffset + 15] = i4 & 1023;
      unCompressedData[outOffset + 16] = (i5 >>> 22);
      unCompressedData[outOffset + 17] = (i5 >>> 12) & 1023;
      unCompressedData[outOffset + 18] = (i5 >>> 2) & 1023;
      unCompressedData[outOffset + 19] = ((i5 << 8) | (i6 >>> 24)) & 1023;
      unCompressedData[outOffset + 20] = (i6 >>> 14) & 1023;
      unCompressedData[outOffset + 21] = (i6 >>> 4) & 1023;
      unCompressedData[outOffset + 22] = ((i6 << 6) | (i7 >>> 26)) & 1023;
      unCompressedData[outOffset + 23] = (i7 >>> 16) & 1023;
      unCompressedData[outOffset + 24] = (i7 >>> 6) & 1023;
      unCompressedData[outOffset + 25] = ((i7 << 4) | (i8 >>> 28)) & 1023;
      unCompressedData[outOffset + 26] = (i8 >>> 18) & 1023;
      unCompressedData[outOffset + 27] = (i8 >>> 8) & 1023;
      unCompressedData[outOffset + 28] = ((i8 << 2) | (i9 >>> 30)) & 1023;
      unCompressedData[outOffset + 29] = (i9 >>> 20) & 1023;
      unCompressedData[outOffset + 30] = (i9 >>> 10) & 1023;
      unCompressedData[outOffset + 31] = i9 & 1023;
      input.offset += 41;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor11 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 21);
      unCompressedData[outOffset + 1] = (i0 >>> 10) & 2047;
      unCompressedData[outOffset + 2] = ((i0 << 1) | (i1 >>> 31)) & 2047;
      unCompressedData[outOffset + 3] = (i1 >>> 20) & 2047;
      unCompressedData[outOffset + 4] = (i1 >>> 9) & 2047;
      unCompressedData[outOffset + 5] = ((i1 << 2) | (i2 >>> 30)) & 2047;
      unCompressedData[outOffset + 6] = (i2 >>> 19) & 2047;
      unCompressedData[outOffset + 7] = (i2 >>> 8) & 2047;
      unCompressedData[outOffset + 8] = ((i2 << 3) | (i3 >>> 29)) & 2047;
      unCompressedData[outOffset + 9] = (i3 >>> 18) & 2047;
      unCompressedData[outOffset + 10] = (i3 >>> 7) & 2047;
      unCompressedData[outOffset + 11] = ((i3 << 4) | (i4 >>> 28)) & 2047;
      unCompressedData[outOffset + 12] = (i4 >>> 17) & 2047;
      unCompressedData[outOffset + 13] = (i4 >>> 6) & 2047;
      unCompressedData[outOffset + 14] = ((i4 << 5) | (i5 >>> 27)) & 2047;
      unCompressedData[outOffset + 15] = (i5 >>> 16) & 2047;
      unCompressedData[outOffset + 16] = (i5 >>> 5) & 2047;
      unCompressedData[outOffset + 17] = ((i5 << 6) | (i6 >>> 26)) & 2047;
      unCompressedData[outOffset + 18] = (i6 >>> 15) & 2047;
      unCompressedData[outOffset + 19] = (i6 >>> 4) & 2047;
      unCompressedData[outOffset + 20] = ((i6 << 7) | (i7 >>> 25)) & 2047;
      unCompressedData[outOffset + 21] = (i7 >>> 14) & 2047;
      unCompressedData[outOffset + 22] = (i7 >>> 3) & 2047;
      unCompressedData[outOffset + 23] = ((i7 << 8) | (i8 >>> 24)) & 2047;
      unCompressedData[outOffset + 24] = (i8 >>> 13) & 2047;
      unCompressedData[outOffset + 25] = (i8 >>> 2) & 2047;
      unCompressedData[outOffset + 26] = ((i8 << 9) | (i9 >>> 23)) & 2047;
      unCompressedData[outOffset + 27] = (i9 >>> 12) & 2047;
      unCompressedData[outOffset + 28] = (i9 >>> 1) & 2047;
      unCompressedData[outOffset + 29] = ((i9 << 10) | (i10 >>> 22)) & 2047;
      unCompressedData[outOffset + 30] = (i10 >>> 11) & 2047;
      unCompressedData[outOffset + 31] = i10 & 2047;
      input.offset += 45;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor12 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 20);
      unCompressedData[outOffset + 1] = (i0 >>> 8) & 4095;
      unCompressedData[outOffset + 2] = ((i0 << 4) | (i1 >>> 28)) & 4095;
      unCompressedData[outOffset + 3] = (i1 >>> 16) & 4095;
      unCompressedData[outOffset + 4] = (i1 >>> 4) & 4095;
      unCompressedData[outOffset + 5] = ((i1 << 8) | (i2 >>> 24)) & 4095;
      unCompressedData[outOffset + 6] = (i2 >>> 12) & 4095;
      unCompressedData[outOffset + 7] = i2 & 4095;
      unCompressedData[outOffset + 8] = (i3 >>> 20);
      unCompressedData[outOffset + 9] = (i3 >>> 8) & 4095;
      unCompressedData[outOffset + 10] = ((i3 << 4) | (i4 >>> 28)) & 4095;
      unCompressedData[outOffset + 11] = (i4 >>> 16) & 4095;
      unCompressedData[outOffset + 12] = (i4 >>> 4) & 4095;
      unCompressedData[outOffset + 13] = ((i4 << 8) | (i5 >>> 24)) & 4095;
      unCompressedData[outOffset + 14] = (i5 >>> 12) & 4095;
      unCompressedData[outOffset + 15] = i5 & 4095;
      unCompressedData[outOffset + 16] = (i6 >>> 20);
      unCompressedData[outOffset + 17] = (i6 >>> 8) & 4095;
      unCompressedData[outOffset + 18] = ((i6 << 4) | (i7 >>> 28)) & 4095;
      unCompressedData[outOffset + 19] = (i7 >>> 16) & 4095;
      unCompressedData[outOffset + 20] = (i7 >>> 4) & 4095;
      unCompressedData[outOffset + 21] = ((i7 << 8) | (i8 >>> 24)) & 4095;
      unCompressedData[outOffset + 22] = (i8 >>> 12) & 4095;
      unCompressedData[outOffset + 23] = i8 & 4095;
      unCompressedData[outOffset + 24] = (i9 >>> 20);
      unCompressedData[outOffset + 25] = (i9 >>> 8) & 4095;
      unCompressedData[outOffset + 26] = ((i9 << 4) | (i10 >>> 28)) & 4095;
      unCompressedData[outOffset + 27] = (i10 >>> 16) & 4095;
      unCompressedData[outOffset + 28] = (i10 >>> 4) & 4095;
      unCompressedData[outOffset + 29] = ((i10 << 8) | (i11 >>> 24)) & 4095;
      unCompressedData[outOffset + 30] = (i11 >>> 12) & 4095;
      unCompressedData[outOffset + 31] = i11 & 4095;
      input.offset += 49;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor13 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 19);
      unCompressedData[outOffset + 1] = (i0 >>> 6) & 8191;
      unCompressedData[outOffset + 2] = ((i0 << 7) | (i1 >>> 25)) & 8191;
      unCompressedData[outOffset + 3] = (i1 >>> 12) & 8191;
      unCompressedData[outOffset + 4] = ((i1 << 1) | (i2 >>> 31)) & 8191;
      unCompressedData[outOffset + 5] = (i2 >>> 18) & 8191;
      unCompressedData[outOffset + 6] = (i2 >>> 5) & 8191;
      unCompressedData[outOffset + 7] = ((i2 << 8) | (i3 >>> 24)) & 8191;
      unCompressedData[outOffset + 8] = (i3 >>> 11) & 8191;
      unCompressedData[outOffset + 9] = ((i3 << 2) | (i4 >>> 30)) & 8191;
      unCompressedData[outOffset + 10] = (i4 >>> 17) & 8191;
      unCompressedData[outOffset + 11] = (i4 >>> 4) & 8191;
      unCompressedData[outOffset + 12] = ((i4 << 9) | (i5 >>> 23)) & 8191;
      unCompressedData[outOffset + 13] = (i5 >>> 10) & 8191;
      unCompressedData[outOffset + 14] = ((i5 << 3) | (i6 >>> 29)) & 8191;
      unCompressedData[outOffset + 15] = (i6 >>> 16) & 8191;
      unCompressedData[outOffset + 16] = (i6 >>> 3) & 8191;
      unCompressedData[outOffset + 17] = ((i6 << 10) | (i7 >>> 22)) & 8191;
      unCompressedData[outOffset + 18] = (i7 >>> 9) & 8191;
      unCompressedData[outOffset + 19] = ((i7 << 4) | (i8 >>> 28)) & 8191;
      unCompressedData[outOffset + 20] = (i8 >>> 15) & 8191;
      unCompressedData[outOffset + 21] = (i8 >>> 2) & 8191;
      unCompressedData[outOffset + 22] = ((i8 << 11) | (i9 >>> 21)) & 8191;
      unCompressedData[outOffset + 23] = (i9 >>> 8) & 8191;
      unCompressedData[outOffset + 24] = ((i9 << 5) | (i10 >>> 27)) & 8191;
      unCompressedData[outOffset + 25] = (i10 >>> 14) & 8191;
      unCompressedData[outOffset + 26] = (i10 >>> 1) & 8191;
      unCompressedData[outOffset + 27] = ((i10 << 12) | (i11 >>> 20)) & 8191;
      unCompressedData[outOffset + 28] = (i11 >>> 7) & 8191;
      unCompressedData[outOffset + 29] = ((i11 << 6) | (i12 >>> 26)) & 8191;
      unCompressedData[outOffset + 30] = (i12 >>> 13) & 8191;
      unCompressedData[outOffset + 31] = i12 & 8191;
      input.offset += 53;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor14 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 18);
      unCompressedData[outOffset + 1] = (i0 >>> 4) & 16383;
      unCompressedData[outOffset + 2] = ((i0 << 10) | (i1 >>> 22)) & 16383;
      unCompressedData[outOffset + 3] = (i1 >>> 8) & 16383;
      unCompressedData[outOffset + 4] = ((i1 << 6) | (i2 >>> 26)) & 16383;
      unCompressedData[outOffset + 5] = (i2 >>> 12) & 16383;
      unCompressedData[outOffset + 6] = ((i2 << 2) | (i3 >>> 30)) & 16383;
      unCompressedData[outOffset + 7] = (i3 >>> 16) & 16383;
      unCompressedData[outOffset + 8] = (i3 >>> 2) & 16383;
      unCompressedData[outOffset + 9] = ((i3 << 12) | (i4 >>> 20)) & 16383;
      unCompressedData[outOffset + 10] = (i4 >>> 6) & 16383;
      unCompressedData[outOffset + 11] = ((i4 << 8) | (i5 >>> 24)) & 16383;
      unCompressedData[outOffset + 12] = (i5 >>> 10) & 16383;
      unCompressedData[outOffset + 13] = ((i5 << 4) | (i6 >>> 28)) & 16383;
      unCompressedData[outOffset + 14] = (i6 >>> 14) & 16383;
      unCompressedData[outOffset + 15] = i6 & 16383;
      unCompressedData[outOffset + 16] = (i7 >>> 18);
      unCompressedData[outOffset + 17] = (i7 >>> 4) & 16383;
      unCompressedData[outOffset + 18] = ((i7 << 10) | (i8 >>> 22)) & 16383;
      unCompressedData[outOffset + 19] = (i8 >>> 8) & 16383;
      unCompressedData[outOffset + 20] = ((i8 << 6) | (i9 >>> 26)) & 16383;
      unCompressedData[outOffset + 21] = (i9 >>> 12) & 16383;
      unCompressedData[outOffset + 22] = ((i9 << 2) | (i10 >>> 30)) & 16383;
      unCompressedData[outOffset + 23] = (i10 >>> 16) & 16383;
      unCompressedData[outOffset + 24] = (i10 >>> 2) & 16383;
      unCompressedData[outOffset + 25] = ((i10 << 12) | (i11 >>> 20)) & 16383;
      unCompressedData[outOffset + 26] = (i11 >>> 6) & 16383;
      unCompressedData[outOffset + 27] = ((i11 << 8) | (i12 >>> 24)) & 16383;
      unCompressedData[outOffset + 28] = (i12 >>> 10) & 16383;
      unCompressedData[outOffset + 29] = ((i12 << 4) | (i13 >>> 28)) & 16383;
      unCompressedData[outOffset + 30] = (i13 >>> 14) & 16383;
      unCompressedData[outOffset + 31] = i13 & 16383;
      input.offset += 57;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor15 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 17);
      unCompressedData[outOffset + 1] = (i0 >>> 2) & 32767;
      unCompressedData[outOffset + 2] = ((i0 << 13) | (i1 >>> 19)) & 32767;
      unCompressedData[outOffset + 3] = (i1 >>> 4) & 32767;
      unCompressedData[outOffset + 4] = ((i1 << 11) | (i2 >>> 21)) & 32767;
      unCompressedData[outOffset + 5] = (i2 >>> 6) & 32767;
      unCompressedData[outOffset + 6] = ((i2 << 9) | (i3 >>> 23)) & 32767;
      unCompressedData[outOffset + 7] = (i3 >>> 8) & 32767;
      unCompressedData[outOffset + 8] = ((i3 << 7) | (i4 >>> 25)) & 32767;
      unCompressedData[outOffset + 9] = (i4 >>> 10) & 32767;
      unCompressedData[outOffset + 10] = ((i4 << 5) | (i5 >>> 27)) & 32767;
      unCompressedData[outOffset + 11] = (i5 >>> 12) & 32767;
      unCompressedData[outOffset + 12] = ((i5 << 3) | (i6 >>> 29)) & 32767;
      unCompressedData[outOffset + 13] = (i6 >>> 14) & 32767;
      unCompressedData[outOffset + 14] = ((i6 << 1) | (i7 >>> 31)) & 32767;
      unCompressedData[outOffset + 15] = (i7 >>> 16) & 32767;
      unCompressedData[outOffset + 16] = (i7 >>> 1) & 32767;
      unCompressedData[outOffset + 17] = ((i7 << 14) | (i8 >>> 18)) & 32767;
      unCompressedData[outOffset + 18] = (i8 >>> 3) & 32767;
      unCompressedData[outOffset + 19] = ((i8 << 12) | (i9 >>> 20)) & 32767;
      unCompressedData[outOffset + 20] = (i9 >>> 5) & 32767;
      unCompressedData[outOffset + 21] = ((i9 << 10) | (i10 >>> 22)) & 32767;
      unCompressedData[outOffset + 22] = (i10 >>> 7) & 32767;
      unCompressedData[outOffset + 23] = ((i10 << 8) | (i11 >>> 24)) & 32767;
      unCompressedData[outOffset + 24] = (i11 >>> 9) & 32767;
      unCompressedData[outOffset + 25] = ((i11 << 6) | (i12 >>> 26)) & 32767;
      unCompressedData[outOffset + 26] = (i12 >>> 11) & 32767;
      unCompressedData[outOffset + 27] = ((i12 << 4) | (i13 >>> 28)) & 32767;
      unCompressedData[outOffset + 28] = (i13 >>> 13) & 32767;
      unCompressedData[outOffset + 29] = ((i13 << 2) | (i14 >>> 30)) & 32767;
      unCompressedData[outOffset + 30] = (i14 >>> 15) & 32767;
      unCompressedData[outOffset + 31] = i14 & 32767;
      input.offset += 61;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor16 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 16);
      unCompressedData[outOffset + 1] = i0 & 65535;
      unCompressedData[outOffset + 2] = (i1 >>> 16);
      unCompressedData[outOffset + 3] = i1 & 65535;
      unCompressedData[outOffset + 4] = (i2 >>> 16);
      unCompressedData[outOffset + 5] = i2 & 65535;
      unCompressedData[outOffset + 6] = (i3 >>> 16);
      unCompressedData[outOffset + 7] = i3 & 65535;
      unCompressedData[outOffset + 8] = (i4 >>> 16);
      unCompressedData[outOffset + 9] = i4 & 65535;
      unCompressedData[outOffset + 10] = (i5 >>> 16);
      unCompressedData[outOffset + 11] = i5 & 65535;
      unCompressedData[outOffset + 12] = (i6 >>> 16);
      unCompressedData[outOffset + 13] = i6 & 65535;
      unCompressedData[outOffset + 14] = (i7 >>> 16);
      unCompressedData[outOffset + 15] = i7 & 65535;
      unCompressedData[outOffset + 16] = (i8 >>> 16);
      unCompressedData[outOffset + 17] = i8 & 65535;
      unCompressedData[outOffset + 18] = (i9 >>> 16);
      unCompressedData[outOffset + 19] = i9 & 65535;
      unCompressedData[outOffset + 20] = (i10 >>> 16);
      unCompressedData[outOffset + 21] = i10 & 65535;
      unCompressedData[outOffset + 22] = (i11 >>> 16);
      unCompressedData[outOffset + 23] = i11 & 65535;
      unCompressedData[outOffset + 24] = (i12 >>> 16);
      unCompressedData[outOffset + 25] = i12 & 65535;
      unCompressedData[outOffset + 26] = (i13 >>> 16);
      unCompressedData[outOffset + 27] = i13 & 65535;
      unCompressedData[outOffset + 28] = (i14 >>> 16);
      unCompressedData[outOffset + 29] = i14 & 65535;
      unCompressedData[outOffset + 30] = (i15 >>> 16);
      unCompressedData[outOffset + 31] = i15 & 65535;
      input.offset += 65;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor17 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 15);
      unCompressedData[outOffset + 1] = ((i0 << 2) | (i1 >>> 30)) & 131071;
      unCompressedData[outOffset + 2] = (i1 >>> 13) & 131071;
      unCompressedData[outOffset + 3] = ((i1 << 4) | (i2 >>> 28)) & 131071;
      unCompressedData[outOffset + 4] = (i2 >>> 11) & 131071;
      unCompressedData[outOffset + 5] = ((i2 << 6) | (i3 >>> 26)) & 131071;
      unCompressedData[outOffset + 6] = (i3 >>> 9) & 131071;
      unCompressedData[outOffset + 7] = ((i3 << 8) | (i4 >>> 24)) & 131071;
      unCompressedData[outOffset + 8] = (i4 >>> 7) & 131071;
      unCompressedData[outOffset + 9] = ((i4 << 10) | (i5 >>> 22)) & 131071;
      unCompressedData[outOffset + 10] = (i5 >>> 5) & 131071;
      unCompressedData[outOffset + 11] = ((i5 << 12) | (i6 >>> 20)) & 131071;
      unCompressedData[outOffset + 12] = (i6 >>> 3) & 131071;
      unCompressedData[outOffset + 13] = ((i6 << 14) | (i7 >>> 18)) & 131071;
      unCompressedData[outOffset + 14] = (i7 >>> 1) & 131071;
      unCompressedData[outOffset + 15] = ((i7 << 16) | (i8 >>> 16)) & 131071;
      unCompressedData[outOffset + 16] = ((i8 << 1) | (i9 >>> 31)) & 131071;
      unCompressedData[outOffset + 17] = (i9 >>> 14) & 131071;
      unCompressedData[outOffset + 18] = ((i9 << 3) | (i10 >>> 29)) & 131071;
      unCompressedData[outOffset + 19] = (i10 >>> 12) & 131071;
      unCompressedData[outOffset + 20] = ((i10 << 5) | (i11 >>> 27)) & 131071;
      unCompressedData[outOffset + 21] = (i11 >>> 10) & 131071;
      unCompressedData[outOffset + 22] = ((i11 << 7) | (i12 >>> 25)) & 131071;
      unCompressedData[outOffset + 23] = (i12 >>> 8) & 131071;
      unCompressedData[outOffset + 24] = ((i12 << 9) | (i13 >>> 23)) & 131071;
      unCompressedData[outOffset + 25] = (i13 >>> 6) & 131071;
      unCompressedData[outOffset + 26] = ((i13 << 11) | (i14 >>> 21)) & 131071;
      unCompressedData[outOffset + 27] = (i14 >>> 4) & 131071;
      unCompressedData[outOffset + 28] = ((i14 << 13) | (i15 >>> 19)) & 131071;
      unCompressedData[outOffset + 29] = (i15 >>> 2) & 131071;
      unCompressedData[outOffset + 30] = ((i15 << 15) | (i16 >>> 17)) & 131071;
      unCompressedData[outOffset + 31] = i16 & 131071;
      input.offset += 69;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor18 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 14);
      unCompressedData[outOffset + 1] = ((i0 << 4) | (i1 >>> 28)) & 262143;
      unCompressedData[outOffset + 2] = (i1 >>> 10) & 262143;
      unCompressedData[outOffset + 3] = ((i1 << 8) | (i2 >>> 24)) & 262143;
      unCompressedData[outOffset + 4] = (i2 >>> 6) & 262143;
      unCompressedData[outOffset + 5] = ((i2 << 12) | (i3 >>> 20)) & 262143;
      unCompressedData[outOffset + 6] = (i3 >>> 2) & 262143;
      unCompressedData[outOffset + 7] = ((i3 << 16) | (i4 >>> 16)) & 262143;
      unCompressedData[outOffset + 8] = ((i4 << 2) | (i5 >>> 30)) & 262143;
      unCompressedData[outOffset + 9] = (i5 >>> 12) & 262143;
      unCompressedData[outOffset + 10] = ((i5 << 6) | (i6 >>> 26)) & 262143;
      unCompressedData[outOffset + 11] = (i6 >>> 8) & 262143;
      unCompressedData[outOffset + 12] = ((i6 << 10) | (i7 >>> 22)) & 262143;
      unCompressedData[outOffset + 13] = (i7 >>> 4) & 262143;
      unCompressedData[outOffset + 14] = ((i7 << 14) | (i8 >>> 18)) & 262143;
      unCompressedData[outOffset + 15] = i8 & 262143;
      unCompressedData[outOffset + 16] = (i9 >>> 14);
      unCompressedData[outOffset + 17] = ((i9 << 4) | (i10 >>> 28)) & 262143;
      unCompressedData[outOffset + 18] = (i10 >>> 10) & 262143;
      unCompressedData[outOffset + 19] = ((i10 << 8) | (i11 >>> 24)) & 262143;
      unCompressedData[outOffset + 20] = (i11 >>> 6) & 262143;
      unCompressedData[outOffset + 21] = ((i11 << 12) | (i12 >>> 20)) & 262143;
      unCompressedData[outOffset + 22] = (i12 >>> 2) & 262143;
      unCompressedData[outOffset + 23] = ((i12 << 16) | (i13 >>> 16)) & 262143;
      unCompressedData[outOffset + 24] = ((i13 << 2) | (i14 >>> 30)) & 262143;
      unCompressedData[outOffset + 25] = (i14 >>> 12) & 262143;
      unCompressedData[outOffset + 26] = ((i14 << 6) | (i15 >>> 26)) & 262143;
      unCompressedData[outOffset + 27] = (i15 >>> 8) & 262143;
      unCompressedData[outOffset + 28] = ((i15 << 10) | (i16 >>> 22)) & 262143;
      unCompressedData[outOffset + 29] = (i16 >>> 4) & 262143;
      unCompressedData[outOffset + 30] = ((i16 << 14) | (i17 >>> 18)) & 262143;
      unCompressedData[outOffset + 31] = i17 & 262143;
      input.offset += 73;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor19 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 13);
      unCompressedData[outOffset + 1] = ((i0 << 6) | (i1 >>> 26)) & 524287;
      unCompressedData[outOffset + 2] = (i1 >>> 7) & 524287;
      unCompressedData[outOffset + 3] = ((i1 << 12) | (i2 >>> 20)) & 524287;
      unCompressedData[outOffset + 4] = (i2 >>> 1) & 524287;
      unCompressedData[outOffset + 5] = ((i2 << 18) | (i3 >>> 14)) & 524287;
      unCompressedData[outOffset + 6] = ((i3 << 5) | (i4 >>> 27)) & 524287;
      unCompressedData[outOffset + 7] = (i4 >>> 8) & 524287;
      unCompressedData[outOffset + 8] = ((i4 << 11) | (i5 >>> 21)) & 524287;
      unCompressedData[outOffset + 9] = (i5 >>> 2) & 524287;
      unCompressedData[outOffset + 10] = ((i5 << 17) | (i6 >>> 15)) & 524287;
      unCompressedData[outOffset + 11] = ((i6 << 4) | (i7 >>> 28)) & 524287;
      unCompressedData[outOffset + 12] = (i7 >>> 9) & 524287;
      unCompressedData[outOffset + 13] = ((i7 << 10) | (i8 >>> 22)) & 524287;
      unCompressedData[outOffset + 14] = (i8 >>> 3) & 524287;
      unCompressedData[outOffset + 15] = ((i8 << 16) | (i9 >>> 16)) & 524287;
      unCompressedData[outOffset + 16] = ((i9 << 3) | (i10 >>> 29)) & 524287;
      unCompressedData[outOffset + 17] = (i10 >>> 10) & 524287;
      unCompressedData[outOffset + 18] = ((i10 << 9) | (i11 >>> 23)) & 524287;
      unCompressedData[outOffset + 19] = (i11 >>> 4) & 524287;
      unCompressedData[outOffset + 20] = ((i11 << 15) | (i12 >>> 17)) & 524287;
      unCompressedData[outOffset + 21] = ((i12 << 2) | (i13 >>> 30)) & 524287;
      unCompressedData[outOffset + 22] = (i13 >>> 11) & 524287;
      unCompressedData[outOffset + 23] = ((i13 << 8) | (i14 >>> 24)) & 524287;
      unCompressedData[outOffset + 24] = (i14 >>> 5) & 524287;
      unCompressedData[outOffset + 25] = ((i14 << 14) | (i15 >>> 18)) & 524287;
      unCompressedData[outOffset + 26] = ((i15 << 1) | (i16 >>> 31)) & 524287;
      unCompressedData[outOffset + 27] = (i16 >>> 12) & 524287;
      unCompressedData[outOffset + 28] = ((i16 << 7) | (i17 >>> 25)) & 524287;
      unCompressedData[outOffset + 29] = (i17 >>> 6) & 524287;
      unCompressedData[outOffset + 30] = ((i17 << 13) | (i18 >>> 19)) & 524287;
      unCompressedData[outOffset + 31] = i18 & 524287;
      input.offset += 77;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor20 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 12);
      unCompressedData[outOffset + 1] = ((i0 << 8) | (i1 >>> 24)) & 1048575;
      unCompressedData[outOffset + 2] = (i1 >>> 4) & 1048575;
      unCompressedData[outOffset + 3] = ((i1 << 16) | (i2 >>> 16)) & 1048575;
      unCompressedData[outOffset + 4] = ((i2 << 4) | (i3 >>> 28)) & 1048575;
      unCompressedData[outOffset + 5] = (i3 >>> 8) & 1048575;
      unCompressedData[outOffset + 6] = ((i3 << 12) | (i4 >>> 20)) & 1048575;
      unCompressedData[outOffset + 7] = i4 & 1048575;
      unCompressedData[outOffset + 8] = (i5 >>> 12);
      unCompressedData[outOffset + 9] = ((i5 << 8) | (i6 >>> 24)) & 1048575;
      unCompressedData[outOffset + 10] = (i6 >>> 4) & 1048575;
      unCompressedData[outOffset + 11] = ((i6 << 16) | (i7 >>> 16)) & 1048575;
      unCompressedData[outOffset + 12] = ((i7 << 4) | (i8 >>> 28)) & 1048575;
      unCompressedData[outOffset + 13] = (i8 >>> 8) & 1048575;
      unCompressedData[outOffset + 14] = ((i8 << 12) | (i9 >>> 20)) & 1048575;
      unCompressedData[outOffset + 15] = i9 & 1048575;
      unCompressedData[outOffset + 16] = (i10 >>> 12);
      unCompressedData[outOffset + 17] = ((i10 << 8) | (i11 >>> 24)) & 1048575;
      unCompressedData[outOffset + 18] = (i11 >>> 4) & 1048575;
      unCompressedData[outOffset + 19] = ((i11 << 16) | (i12 >>> 16)) & 1048575;
      unCompressedData[outOffset + 20] = ((i12 << 4) | (i13 >>> 28)) & 1048575;
      unCompressedData[outOffset + 21] = (i13 >>> 8) & 1048575;
      unCompressedData[outOffset + 22] = ((i13 << 12) | (i14 >>> 20)) & 1048575;
      unCompressedData[outOffset + 23] = i14 & 1048575;
      unCompressedData[outOffset + 24] = (i15 >>> 12);
      unCompressedData[outOffset + 25] = ((i15 << 8) | (i16 >>> 24)) & 1048575;
      unCompressedData[outOffset + 26] = (i16 >>> 4) & 1048575;
      unCompressedData[outOffset + 27] = ((i16 << 16) | (i17 >>> 16)) & 1048575;
      unCompressedData[outOffset + 28] = ((i17 << 4) | (i18 >>> 28)) & 1048575;
      unCompressedData[outOffset + 29] = (i18 >>> 8) & 1048575;
      unCompressedData[outOffset + 30] = ((i18 << 12) | (i19 >>> 20)) & 1048575;
      unCompressedData[outOffset + 31] = i19 & 1048575;
      input.offset += 81;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor21 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 11);
      unCompressedData[outOffset + 1] = ((i0 << 10) | (i1 >>> 22)) & 2097151;
      unCompressedData[outOffset + 2] = (i1 >>> 1) & 2097151;
      unCompressedData[outOffset + 3] = ((i1 << 20) | (i2 >>> 12)) & 2097151;
      unCompressedData[outOffset + 4] = ((i2 << 9) | (i3 >>> 23)) & 2097151;
      unCompressedData[outOffset + 5] = (i3 >>> 2) & 2097151;
      unCompressedData[outOffset + 6] = ((i3 << 19) | (i4 >>> 13)) & 2097151;
      unCompressedData[outOffset + 7] = ((i4 << 8) | (i5 >>> 24)) & 2097151;
      unCompressedData[outOffset + 8] = (i5 >>> 3) & 2097151;
      unCompressedData[outOffset + 9] = ((i5 << 18) | (i6 >>> 14)) & 2097151;
      unCompressedData[outOffset + 10] = ((i6 << 7) | (i7 >>> 25)) & 2097151;
      unCompressedData[outOffset + 11] = (i7 >>> 4) & 2097151;
      unCompressedData[outOffset + 12] = ((i7 << 17) | (i8 >>> 15)) & 2097151;
      unCompressedData[outOffset + 13] = ((i8 << 6) | (i9 >>> 26)) & 2097151;
      unCompressedData[outOffset + 14] = (i9 >>> 5) & 2097151;
      unCompressedData[outOffset + 15] = ((i9 << 16) | (i10 >>> 16)) & 2097151;
      unCompressedData[outOffset + 16] = ((i10 << 5) | (i11 >>> 27)) & 2097151;
      unCompressedData[outOffset + 17] = (i11 >>> 6) & 2097151;
      unCompressedData[outOffset + 18] = ((i11 << 15) | (i12 >>> 17)) & 2097151;
      unCompressedData[outOffset + 19] = ((i12 << 4) | (i13 >>> 28)) & 2097151;
      unCompressedData[outOffset + 20] = (i13 >>> 7) & 2097151;
      unCompressedData[outOffset + 21] = ((i13 << 14) | (i14 >>> 18)) & 2097151;
      unCompressedData[outOffset + 22] = ((i14 << 3) | (i15 >>> 29)) & 2097151;
      unCompressedData[outOffset + 23] = (i15 >>> 8) & 2097151;
      unCompressedData[outOffset + 24] = ((i15 << 13) | (i16 >>> 19)) & 2097151;
      unCompressedData[outOffset + 25] = ((i16 << 2) | (i17 >>> 30)) & 2097151;
      unCompressedData[outOffset + 26] = (i17 >>> 9) & 2097151;
      unCompressedData[outOffset + 27] = ((i17 << 12) | (i18 >>> 20)) & 2097151;
      unCompressedData[outOffset + 28] = ((i18 << 1) | (i19 >>> 31)) & 2097151;
      unCompressedData[outOffset + 29] = (i19 >>> 10) & 2097151;
      unCompressedData[outOffset + 30] = ((i19 << 11) | (i20 >>> 21)) & 2097151;
      unCompressedData[outOffset + 31] = i20 & 2097151;
      input.offset += 85;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor22 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 10);
      unCompressedData[outOffset + 1] = ((i0 << 12) | (i1 >>> 20)) & 4194303;
      unCompressedData[outOffset + 2] = ((i1 << 2) | (i2 >>> 30)) & 4194303;
      unCompressedData[outOffset + 3] = (i2 >>> 8) & 4194303;
      unCompressedData[outOffset + 4] = ((i2 << 14) | (i3 >>> 18)) & 4194303;
      unCompressedData[outOffset + 5] = ((i3 << 4) | (i4 >>> 28)) & 4194303;
      unCompressedData[outOffset + 6] = (i4 >>> 6) & 4194303;
      unCompressedData[outOffset + 7] = ((i4 << 16) | (i5 >>> 16)) & 4194303;
      unCompressedData[outOffset + 8] = ((i5 << 6) | (i6 >>> 26)) & 4194303;
      unCompressedData[outOffset + 9] = (i6 >>> 4) & 4194303;
      unCompressedData[outOffset + 10] = ((i6 << 18) | (i7 >>> 14)) & 4194303;
      unCompressedData[outOffset + 11] = ((i7 << 8) | (i8 >>> 24)) & 4194303;
      unCompressedData[outOffset + 12] = (i8 >>> 2) & 4194303;
      unCompressedData[outOffset + 13] = ((i8 << 20) | (i9 >>> 12)) & 4194303;
      unCompressedData[outOffset + 14] = ((i9 << 10) | (i10 >>> 22)) & 4194303;
      unCompressedData[outOffset + 15] = i10 & 4194303;
      unCompressedData[outOffset + 16] = (i11 >>> 10);
      unCompressedData[outOffset + 17] = ((i11 << 12) | (i12 >>> 20)) & 4194303;
      unCompressedData[outOffset + 18] = ((i12 << 2) | (i13 >>> 30)) & 4194303;
      unCompressedData[outOffset + 19] = (i13 >>> 8) & 4194303;
      unCompressedData[outOffset + 20] = ((i13 << 14) | (i14 >>> 18)) & 4194303;
      unCompressedData[outOffset + 21] = ((i14 << 4) | (i15 >>> 28)) & 4194303;
      unCompressedData[outOffset + 22] = (i15 >>> 6) & 4194303;
      unCompressedData[outOffset + 23] = ((i15 << 16) | (i16 >>> 16)) & 4194303;
      unCompressedData[outOffset + 24] = ((i16 << 6) | (i17 >>> 26)) & 4194303;
      unCompressedData[outOffset + 25] = (i17 >>> 4) & 4194303;
      unCompressedData[outOffset + 26] = ((i17 << 18) | (i18 >>> 14)) & 4194303;
      unCompressedData[outOffset + 27] = ((i18 << 8) | (i19 >>> 24)) & 4194303;
      unCompressedData[outOffset + 28] = (i19 >>> 2) & 4194303;
      unCompressedData[outOffset + 29] = ((i19 << 20) | (i20 >>> 12)) & 4194303;
      unCompressedData[outOffset + 30] = ((i20 << 10) | (i21 >>> 22)) & 4194303;
      unCompressedData[outOffset + 31] = i21 & 4194303;
      input.offset += 89;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor23 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 9);
      unCompressedData[outOffset + 1] = ((i0 << 14) | (i1 >>> 18)) & 8388607;
      unCompressedData[outOffset + 2] = ((i1 << 5) | (i2 >>> 27)) & 8388607;
      unCompressedData[outOffset + 3] = (i2 >>> 4) & 8388607;
      unCompressedData[outOffset + 4] = ((i2 << 19) | (i3 >>> 13)) & 8388607;
      unCompressedData[outOffset + 5] = ((i3 << 10) | (i4 >>> 22)) & 8388607;
      unCompressedData[outOffset + 6] = ((i4 << 1) | (i5 >>> 31)) & 8388607;
      unCompressedData[outOffset + 7] = (i5 >>> 8) & 8388607;
      unCompressedData[outOffset + 8] = ((i5 << 15) | (i6 >>> 17)) & 8388607;
      unCompressedData[outOffset + 9] = ((i6 << 6) | (i7 >>> 26)) & 8388607;
      unCompressedData[outOffset + 10] = (i7 >>> 3) & 8388607;
      unCompressedData[outOffset + 11] = ((i7 << 20) | (i8 >>> 12)) & 8388607;
      unCompressedData[outOffset + 12] = ((i8 << 11) | (i9 >>> 21)) & 8388607;
      unCompressedData[outOffset + 13] = ((i9 << 2) | (i10 >>> 30)) & 8388607;
      unCompressedData[outOffset + 14] = (i10 >>> 7) & 8388607;
      unCompressedData[outOffset + 15] = ((i10 << 16) | (i11 >>> 16)) & 8388607;
      unCompressedData[outOffset + 16] = ((i11 << 7) | (i12 >>> 25)) & 8388607;
      unCompressedData[outOffset + 17] = (i12 >>> 2) & 8388607;
      unCompressedData[outOffset + 18] = ((i12 << 21) | (i13 >>> 11)) & 8388607;
      unCompressedData[outOffset + 19] = ((i13 << 12) | (i14 >>> 20)) & 8388607;
      unCompressedData[outOffset + 20] = ((i14 << 3) | (i15 >>> 29)) & 8388607;
      unCompressedData[outOffset + 21] = (i15 >>> 6) & 8388607;
      unCompressedData[outOffset + 22] = ((i15 << 17) | (i16 >>> 15)) & 8388607;
      unCompressedData[outOffset + 23] = ((i16 << 8) | (i17 >>> 24)) & 8388607;
      unCompressedData[outOffset + 24] = (i17 >>> 1) & 8388607;
      unCompressedData[outOffset + 25] = ((i17 << 22) | (i18 >>> 10)) & 8388607;
      unCompressedData[outOffset + 26] = ((i18 << 13) | (i19 >>> 19)) & 8388607;
      unCompressedData[outOffset + 27] = ((i19 << 4) | (i20 >>> 28)) & 8388607;
      unCompressedData[outOffset + 28] = (i20 >>> 5) & 8388607;
      unCompressedData[outOffset + 29] = ((i20 << 18) | (i21 >>> 14)) & 8388607;
      unCompressedData[outOffset + 30] = ((i21 << 9) | (i22 >>> 23)) & 8388607;
      unCompressedData[outOffset + 31] = i22 & 8388607;
      input.offset += 93;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor24 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));
      final int i23 = ((compressedArray[inOffset + 92] & 0xFF) << 24) | ((compressedArray[inOffset + 93] & 0xFF) << 16) | ((compressedArray[inOffset + 94] & 0xFF) << 8) | ((compressedArray[inOffset + 95] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 8);
      unCompressedData[outOffset + 1] = ((i0 << 16) | (i1 >>> 16)) & 16777215;
      unCompressedData[outOffset + 2] = ((i1 << 8) | (i2 >>> 24)) & 16777215;
      unCompressedData[outOffset + 3] = i2 & 16777215;
      unCompressedData[outOffset + 4] = (i3 >>> 8);
      unCompressedData[outOffset + 5] = ((i3 << 16) | (i4 >>> 16)) & 16777215;
      unCompressedData[outOffset + 6] = ((i4 << 8) | (i5 >>> 24)) & 16777215;
      unCompressedData[outOffset + 7] = i5 & 16777215;
      unCompressedData[outOffset + 8] = (i6 >>> 8);
      unCompressedData[outOffset + 9] = ((i6 << 16) | (i7 >>> 16)) & 16777215;
      unCompressedData[outOffset + 10] = ((i7 << 8) | (i8 >>> 24)) & 16777215;
      unCompressedData[outOffset + 11] = i8 & 16777215;
      unCompressedData[outOffset + 12] = (i9 >>> 8);
      unCompressedData[outOffset + 13] = ((i9 << 16) | (i10 >>> 16)) & 16777215;
      unCompressedData[outOffset + 14] = ((i10 << 8) | (i11 >>> 24)) & 16777215;
      unCompressedData[outOffset + 15] = i11 & 16777215;
      unCompressedData[outOffset + 16] = (i12 >>> 8);
      unCompressedData[outOffset + 17] = ((i12 << 16) | (i13 >>> 16)) & 16777215;
      unCompressedData[outOffset + 18] = ((i13 << 8) | (i14 >>> 24)) & 16777215;
      unCompressedData[outOffset + 19] = i14 & 16777215;
      unCompressedData[outOffset + 20] = (i15 >>> 8);
      unCompressedData[outOffset + 21] = ((i15 << 16) | (i16 >>> 16)) & 16777215;
      unCompressedData[outOffset + 22] = ((i16 << 8) | (i17 >>> 24)) & 16777215;
      unCompressedData[outOffset + 23] = i17 & 16777215;
      unCompressedData[outOffset + 24] = (i18 >>> 8);
      unCompressedData[outOffset + 25] = ((i18 << 16) | (i19 >>> 16)) & 16777215;
      unCompressedData[outOffset + 26] = ((i19 << 8) | (i20 >>> 24)) & 16777215;
      unCompressedData[outOffset + 27] = i20 & 16777215;
      unCompressedData[outOffset + 28] = (i21 >>> 8);
      unCompressedData[outOffset + 29] = ((i21 << 16) | (i22 >>> 16)) & 16777215;
      unCompressedData[outOffset + 30] = ((i22 << 8) | (i23 >>> 24)) & 16777215;
      unCompressedData[outOffset + 31] = i23 & 16777215;
      input.offset += 97;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor25 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));
      final int i23 = ((compressedArray[inOffset + 92] & 0xFF) << 24) | ((compressedArray[inOffset + 93] & 0xFF) << 16) | ((compressedArray[inOffset + 94] & 0xFF) << 8) | ((compressedArray[inOffset + 95] & 0xFF));
      final int i24 = ((compressedArray[inOffset + 96] & 0xFF) << 24) | ((compressedArray[inOffset + 97] & 0xFF) << 16) | ((compressedArray[inOffset + 98] & 0xFF) << 8) | ((compressedArray[inOffset + 99] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 7);
      unCompressedData[outOffset + 1] = ((i0 << 18) | (i1 >>> 14)) & 33554431;
      unCompressedData[outOffset + 2] = ((i1 << 11) | (i2 >>> 21)) & 33554431;
      unCompressedData[outOffset + 3] = ((i2 << 4) | (i3 >>> 28)) & 33554431;
      unCompressedData[outOffset + 4] = (i3 >>> 3) & 33554431;
      unCompressedData[outOffset + 5] = ((i3 << 22) | (i4 >>> 10)) & 33554431;
      unCompressedData[outOffset + 6] = ((i4 << 15) | (i5 >>> 17)) & 33554431;
      unCompressedData[outOffset + 7] = ((i5 << 8) | (i6 >>> 24)) & 33554431;
      unCompressedData[outOffset + 8] = ((i6 << 1) | (i7 >>> 31)) & 33554431;
      unCompressedData[outOffset + 9] = (i7 >>> 6) & 33554431;
      unCompressedData[outOffset + 10] = ((i7 << 19) | (i8 >>> 13)) & 33554431;
      unCompressedData[outOffset + 11] = ((i8 << 12) | (i9 >>> 20)) & 33554431;
      unCompressedData[outOffset + 12] = ((i9 << 5) | (i10 >>> 27)) & 33554431;
      unCompressedData[outOffset + 13] = (i10 >>> 2) & 33554431;
      unCompressedData[outOffset + 14] = ((i10 << 23) | (i11 >>> 9)) & 33554431;
      unCompressedData[outOffset + 15] = ((i11 << 16) | (i12 >>> 16)) & 33554431;
      unCompressedData[outOffset + 16] = ((i12 << 9) | (i13 >>> 23)) & 33554431;
      unCompressedData[outOffset + 17] = ((i13 << 2) | (i14 >>> 30)) & 33554431;
      unCompressedData[outOffset + 18] = (i14 >>> 5) & 33554431;
      unCompressedData[outOffset + 19] = ((i14 << 20) | (i15 >>> 12)) & 33554431;
      unCompressedData[outOffset + 20] = ((i15 << 13) | (i16 >>> 19)) & 33554431;
      unCompressedData[outOffset + 21] = ((i16 << 6) | (i17 >>> 26)) & 33554431;
      unCompressedData[outOffset + 22] = (i17 >>> 1) & 33554431;
      unCompressedData[outOffset + 23] = ((i17 << 24) | (i18 >>> 8)) & 33554431;
      unCompressedData[outOffset + 24] = ((i18 << 17) | (i19 >>> 15)) & 33554431;
      unCompressedData[outOffset + 25] = ((i19 << 10) | (i20 >>> 22)) & 33554431;
      unCompressedData[outOffset + 26] = ((i20 << 3) | (i21 >>> 29)) & 33554431;
      unCompressedData[outOffset + 27] = (i21 >>> 4) & 33554431;
      unCompressedData[outOffset + 28] = ((i21 << 21) | (i22 >>> 11)) & 33554431;
      unCompressedData[outOffset + 29] = ((i22 << 14) | (i23 >>> 18)) & 33554431;
      unCompressedData[outOffset + 30] = ((i23 << 7) | (i24 >>> 25)) & 33554431;
      unCompressedData[outOffset + 31] = i24 & 33554431;
      input.offset += 101;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor26 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));
      final int i23 = ((compressedArray[inOffset + 92] & 0xFF) << 24) | ((compressedArray[inOffset + 93] & 0xFF) << 16) | ((compressedArray[inOffset + 94] & 0xFF) << 8) | ((compressedArray[inOffset + 95] & 0xFF));
      final int i24 = ((compressedArray[inOffset + 96] & 0xFF) << 24) | ((compressedArray[inOffset + 97] & 0xFF) << 16) | ((compressedArray[inOffset + 98] & 0xFF) << 8) | ((compressedArray[inOffset + 99] & 0xFF));
      final int i25 = ((compressedArray[inOffset + 100] & 0xFF) << 24) | ((compressedArray[inOffset + 101] & 0xFF) << 16) | ((compressedArray[inOffset + 102] & 0xFF) << 8) | ((compressedArray[inOffset + 103] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 6);
      unCompressedData[outOffset + 1] = ((i0 << 20) | (i1 >>> 12)) & 67108863;
      unCompressedData[outOffset + 2] = ((i1 << 14) | (i2 >>> 18)) & 67108863;
      unCompressedData[outOffset + 3] = ((i2 << 8) | (i3 >>> 24)) & 67108863;
      unCompressedData[outOffset + 4] = ((i3 << 2) | (i4 >>> 30)) & 67108863;
      unCompressedData[outOffset + 5] = (i4 >>> 4) & 67108863;
      unCompressedData[outOffset + 6] = ((i4 << 22) | (i5 >>> 10)) & 67108863;
      unCompressedData[outOffset + 7] = ((i5 << 16) | (i6 >>> 16)) & 67108863;
      unCompressedData[outOffset + 8] = ((i6 << 10) | (i7 >>> 22)) & 67108863;
      unCompressedData[outOffset + 9] = ((i7 << 4) | (i8 >>> 28)) & 67108863;
      unCompressedData[outOffset + 10] = (i8 >>> 2) & 67108863;
      unCompressedData[outOffset + 11] = ((i8 << 24) | (i9 >>> 8)) & 67108863;
      unCompressedData[outOffset + 12] = ((i9 << 18) | (i10 >>> 14)) & 67108863;
      unCompressedData[outOffset + 13] = ((i10 << 12) | (i11 >>> 20)) & 67108863;
      unCompressedData[outOffset + 14] = ((i11 << 6) | (i12 >>> 26)) & 67108863;
      unCompressedData[outOffset + 15] = i12 & 67108863;
      unCompressedData[outOffset + 16] = (i13 >>> 6);
      unCompressedData[outOffset + 17] = ((i13 << 20) | (i14 >>> 12)) & 67108863;
      unCompressedData[outOffset + 18] = ((i14 << 14) | (i15 >>> 18)) & 67108863;
      unCompressedData[outOffset + 19] = ((i15 << 8) | (i16 >>> 24)) & 67108863;
      unCompressedData[outOffset + 20] = ((i16 << 2) | (i17 >>> 30)) & 67108863;
      unCompressedData[outOffset + 21] = (i17 >>> 4) & 67108863;
      unCompressedData[outOffset + 22] = ((i17 << 22) | (i18 >>> 10)) & 67108863;
      unCompressedData[outOffset + 23] = ((i18 << 16) | (i19 >>> 16)) & 67108863;
      unCompressedData[outOffset + 24] = ((i19 << 10) | (i20 >>> 22)) & 67108863;
      unCompressedData[outOffset + 25] = ((i20 << 4) | (i21 >>> 28)) & 67108863;
      unCompressedData[outOffset + 26] = (i21 >>> 2) & 67108863;
      unCompressedData[outOffset + 27] = ((i21 << 24) | (i22 >>> 8)) & 67108863;
      unCompressedData[outOffset + 28] = ((i22 << 18) | (i23 >>> 14)) & 67108863;
      unCompressedData[outOffset + 29] = ((i23 << 12) | (i24 >>> 20)) & 67108863;
      unCompressedData[outOffset + 30] = ((i24 << 6) | (i25 >>> 26)) & 67108863;
      unCompressedData[outOffset + 31] = i25 & 67108863;
      input.offset += 105;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor27 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));
      final int i23 = ((compressedArray[inOffset + 92] & 0xFF) << 24) | ((compressedArray[inOffset + 93] & 0xFF) << 16) | ((compressedArray[inOffset + 94] & 0xFF) << 8) | ((compressedArray[inOffset + 95] & 0xFF));
      final int i24 = ((compressedArray[inOffset + 96] & 0xFF) << 24) | ((compressedArray[inOffset + 97] & 0xFF) << 16) | ((compressedArray[inOffset + 98] & 0xFF) << 8) | ((compressedArray[inOffset + 99] & 0xFF));
      final int i25 = ((compressedArray[inOffset + 100] & 0xFF) << 24) | ((compressedArray[inOffset + 101] & 0xFF) << 16) | ((compressedArray[inOffset + 102] & 0xFF) << 8) | ((compressedArray[inOffset + 103] & 0xFF));
      final int i26 = ((compressedArray[inOffset + 104] & 0xFF) << 24) | ((compressedArray[inOffset + 105] & 0xFF) << 16) | ((compressedArray[inOffset + 106] & 0xFF) << 8) | ((compressedArray[inOffset + 107] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 5);
      unCompressedData[outOffset + 1] = ((i0 << 22) | (i1 >>> 10)) & 134217727;
      unCompressedData[outOffset + 2] = ((i1 << 17) | (i2 >>> 15)) & 134217727;
      unCompressedData[outOffset + 3] = ((i2 << 12) | (i3 >>> 20)) & 134217727;
      unCompressedData[outOffset + 4] = ((i3 << 7) | (i4 >>> 25)) & 134217727;
      unCompressedData[outOffset + 5] = ((i4 << 2) | (i5 >>> 30)) & 134217727;
      unCompressedData[outOffset + 6] = (i5 >>> 3) & 134217727;
      unCompressedData[outOffset + 7] = ((i5 << 24) | (i6 >>> 8)) & 134217727;
      unCompressedData[outOffset + 8] = ((i6 << 19) | (i7 >>> 13)) & 134217727;
      unCompressedData[outOffset + 9] = ((i7 << 14) | (i8 >>> 18)) & 134217727;
      unCompressedData[outOffset + 10] = ((i8 << 9) | (i9 >>> 23)) & 134217727;
      unCompressedData[outOffset + 11] = ((i9 << 4) | (i10 >>> 28)) & 134217727;
      unCompressedData[outOffset + 12] = (i10 >>> 1) & 134217727;
      unCompressedData[outOffset + 13] = ((i10 << 26) | (i11 >>> 6)) & 134217727;
      unCompressedData[outOffset + 14] = ((i11 << 21) | (i12 >>> 11)) & 134217727;
      unCompressedData[outOffset + 15] = ((i12 << 16) | (i13 >>> 16)) & 134217727;
      unCompressedData[outOffset + 16] = ((i13 << 11) | (i14 >>> 21)) & 134217727;
      unCompressedData[outOffset + 17] = ((i14 << 6) | (i15 >>> 26)) & 134217727;
      unCompressedData[outOffset + 18] = ((i15 << 1) | (i16 >>> 31)) & 134217727;
      unCompressedData[outOffset + 19] = (i16 >>> 4) & 134217727;
      unCompressedData[outOffset + 20] = ((i16 << 23) | (i17 >>> 9)) & 134217727;
      unCompressedData[outOffset + 21] = ((i17 << 18) | (i18 >>> 14)) & 134217727;
      unCompressedData[outOffset + 22] = ((i18 << 13) | (i19 >>> 19)) & 134217727;
      unCompressedData[outOffset + 23] = ((i19 << 8) | (i20 >>> 24)) & 134217727;
      unCompressedData[outOffset + 24] = ((i20 << 3) | (i21 >>> 29)) & 134217727;
      unCompressedData[outOffset + 25] = (i21 >>> 2) & 134217727;
      unCompressedData[outOffset + 26] = ((i21 << 25) | (i22 >>> 7)) & 134217727;
      unCompressedData[outOffset + 27] = ((i22 << 20) | (i23 >>> 12)) & 134217727;
      unCompressedData[outOffset + 28] = ((i23 << 15) | (i24 >>> 17)) & 134217727;
      unCompressedData[outOffset + 29] = ((i24 << 10) | (i25 >>> 22)) & 134217727;
      unCompressedData[outOffset + 30] = ((i25 << 5) | (i26 >>> 27)) & 134217727;
      unCompressedData[outOffset + 31] = i26 & 134217727;
      input.offset += 109;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor28 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));
      final int i23 = ((compressedArray[inOffset + 92] & 0xFF) << 24) | ((compressedArray[inOffset + 93] & 0xFF) << 16) | ((compressedArray[inOffset + 94] & 0xFF) << 8) | ((compressedArray[inOffset + 95] & 0xFF));
      final int i24 = ((compressedArray[inOffset + 96] & 0xFF) << 24) | ((compressedArray[inOffset + 97] & 0xFF) << 16) | ((compressedArray[inOffset + 98] & 0xFF) << 8) | ((compressedArray[inOffset + 99] & 0xFF));
      final int i25 = ((compressedArray[inOffset + 100] & 0xFF) << 24) | ((compressedArray[inOffset + 101] & 0xFF) << 16) | ((compressedArray[inOffset + 102] & 0xFF) << 8) | ((compressedArray[inOffset + 103] & 0xFF));
      final int i26 = ((compressedArray[inOffset + 104] & 0xFF) << 24) | ((compressedArray[inOffset + 105] & 0xFF) << 16) | ((compressedArray[inOffset + 106] & 0xFF) << 8) | ((compressedArray[inOffset + 107] & 0xFF));
      final int i27 = ((compressedArray[inOffset + 108] & 0xFF) << 24) | ((compressedArray[inOffset + 109] & 0xFF) << 16) | ((compressedArray[inOffset + 110] & 0xFF) << 8) | ((compressedArray[inOffset + 111] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 4);
      unCompressedData[outOffset + 1] = ((i0 << 24) | (i1 >>> 8)) & 268435455;
      unCompressedData[outOffset + 2] = ((i1 << 20) | (i2 >>> 12)) & 268435455;
      unCompressedData[outOffset + 3] = ((i2 << 16) | (i3 >>> 16)) & 268435455;
      unCompressedData[outOffset + 4] = ((i3 << 12) | (i4 >>> 20)) & 268435455;
      unCompressedData[outOffset + 5] = ((i4 << 8) | (i5 >>> 24)) & 268435455;
      unCompressedData[outOffset + 6] = ((i5 << 4) | (i6 >>> 28)) & 268435455;
      unCompressedData[outOffset + 7] = i6 & 268435455;
      unCompressedData[outOffset + 8] = (i7 >>> 4);
      unCompressedData[outOffset + 9] = ((i7 << 24) | (i8 >>> 8)) & 268435455;
      unCompressedData[outOffset + 10] = ((i8 << 20) | (i9 >>> 12)) & 268435455;
      unCompressedData[outOffset + 11] = ((i9 << 16) | (i10 >>> 16)) & 268435455;
      unCompressedData[outOffset + 12] = ((i10 << 12) | (i11 >>> 20)) & 268435455;
      unCompressedData[outOffset + 13] = ((i11 << 8) | (i12 >>> 24)) & 268435455;
      unCompressedData[outOffset + 14] = ((i12 << 4) | (i13 >>> 28)) & 268435455;
      unCompressedData[outOffset + 15] = i13 & 268435455;
      unCompressedData[outOffset + 16] = (i14 >>> 4);
      unCompressedData[outOffset + 17] = ((i14 << 24) | (i15 >>> 8)) & 268435455;
      unCompressedData[outOffset + 18] = ((i15 << 20) | (i16 >>> 12)) & 268435455;
      unCompressedData[outOffset + 19] = ((i16 << 16) | (i17 >>> 16)) & 268435455;
      unCompressedData[outOffset + 20] = ((i17 << 12) | (i18 >>> 20)) & 268435455;
      unCompressedData[outOffset + 21] = ((i18 << 8) | (i19 >>> 24)) & 268435455;
      unCompressedData[outOffset + 22] = ((i19 << 4) | (i20 >>> 28)) & 268435455;
      unCompressedData[outOffset + 23] = i20 & 268435455;
      unCompressedData[outOffset + 24] = (i21 >>> 4);
      unCompressedData[outOffset + 25] = ((i21 << 24) | (i22 >>> 8)) & 268435455;
      unCompressedData[outOffset + 26] = ((i22 << 20) | (i23 >>> 12)) & 268435455;
      unCompressedData[outOffset + 27] = ((i23 << 16) | (i24 >>> 16)) & 268435455;
      unCompressedData[outOffset + 28] = ((i24 << 12) | (i25 >>> 20)) & 268435455;
      unCompressedData[outOffset + 29] = ((i25 << 8) | (i26 >>> 24)) & 268435455;
      unCompressedData[outOffset + 30] = ((i26 << 4) | (i27 >>> 28)) & 268435455;
      unCompressedData[outOffset + 31] = i27 & 268435455;
      input.offset += 113;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor29 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));
      final int i23 = ((compressedArray[inOffset + 92] & 0xFF) << 24) | ((compressedArray[inOffset + 93] & 0xFF) << 16) | ((compressedArray[inOffset + 94] & 0xFF) << 8) | ((compressedArray[inOffset + 95] & 0xFF));
      final int i24 = ((compressedArray[inOffset + 96] & 0xFF) << 24) | ((compressedArray[inOffset + 97] & 0xFF) << 16) | ((compressedArray[inOffset + 98] & 0xFF) << 8) | ((compressedArray[inOffset + 99] & 0xFF));
      final int i25 = ((compressedArray[inOffset + 100] & 0xFF) << 24) | ((compressedArray[inOffset + 101] & 0xFF) << 16) | ((compressedArray[inOffset + 102] & 0xFF) << 8) | ((compressedArray[inOffset + 103] & 0xFF));
      final int i26 = ((compressedArray[inOffset + 104] & 0xFF) << 24) | ((compressedArray[inOffset + 105] & 0xFF) << 16) | ((compressedArray[inOffset + 106] & 0xFF) << 8) | ((compressedArray[inOffset + 107] & 0xFF));
      final int i27 = ((compressedArray[inOffset + 108] & 0xFF) << 24) | ((compressedArray[inOffset + 109] & 0xFF) << 16) | ((compressedArray[inOffset + 110] & 0xFF) << 8) | ((compressedArray[inOffset + 111] & 0xFF));
      final int i28 = ((compressedArray[inOffset + 112] & 0xFF) << 24) | ((compressedArray[inOffset + 113] & 0xFF) << 16) | ((compressedArray[inOffset + 114] & 0xFF) << 8) | ((compressedArray[inOffset + 115] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 3);
      unCompressedData[outOffset + 1] = ((i0 << 26) | (i1 >>> 6)) & 536870911;
      unCompressedData[outOffset + 2] = ((i1 << 23) | (i2 >>> 9)) & 536870911;
      unCompressedData[outOffset + 3] = ((i2 << 20) | (i3 >>> 12)) & 536870911;
      unCompressedData[outOffset + 4] = ((i3 << 17) | (i4 >>> 15)) & 536870911;
      unCompressedData[outOffset + 5] = ((i4 << 14) | (i5 >>> 18)) & 536870911;
      unCompressedData[outOffset + 6] = ((i5 << 11) | (i6 >>> 21)) & 536870911;
      unCompressedData[outOffset + 7] = ((i6 << 8) | (i7 >>> 24)) & 536870911;
      unCompressedData[outOffset + 8] = ((i7 << 5) | (i8 >>> 27)) & 536870911;
      unCompressedData[outOffset + 9] = ((i8 << 2) | (i9 >>> 30)) & 536870911;
      unCompressedData[outOffset + 10] = (i9 >>> 1) & 536870911;
      unCompressedData[outOffset + 11] = ((i9 << 28) | (i10 >>> 4)) & 536870911;
      unCompressedData[outOffset + 12] = ((i10 << 25) | (i11 >>> 7)) & 536870911;
      unCompressedData[outOffset + 13] = ((i11 << 22) | (i12 >>> 10)) & 536870911;
      unCompressedData[outOffset + 14] = ((i12 << 19) | (i13 >>> 13)) & 536870911;
      unCompressedData[outOffset + 15] = ((i13 << 16) | (i14 >>> 16)) & 536870911;
      unCompressedData[outOffset + 16] = ((i14 << 13) | (i15 >>> 19)) & 536870911;
      unCompressedData[outOffset + 17] = ((i15 << 10) | (i16 >>> 22)) & 536870911;
      unCompressedData[outOffset + 18] = ((i16 << 7) | (i17 >>> 25)) & 536870911;
      unCompressedData[outOffset + 19] = ((i17 << 4) | (i18 >>> 28)) & 536870911;
      unCompressedData[outOffset + 20] = ((i18 << 1) | (i19 >>> 31)) & 536870911;
      unCompressedData[outOffset + 21] = (i19 >>> 2) & 536870911;
      unCompressedData[outOffset + 22] = ((i19 << 27) | (i20 >>> 5)) & 536870911;
      unCompressedData[outOffset + 23] = ((i20 << 24) | (i21 >>> 8)) & 536870911;
      unCompressedData[outOffset + 24] = ((i21 << 21) | (i22 >>> 11)) & 536870911;
      unCompressedData[outOffset + 25] = ((i22 << 18) | (i23 >>> 14)) & 536870911;
      unCompressedData[outOffset + 26] = ((i23 << 15) | (i24 >>> 17)) & 536870911;
      unCompressedData[outOffset + 27] = ((i24 << 12) | (i25 >>> 20)) & 536870911;
      unCompressedData[outOffset + 28] = ((i25 << 9) | (i26 >>> 23)) & 536870911;
      unCompressedData[outOffset + 29] = ((i26 << 6) | (i27 >>> 26)) & 536870911;
      unCompressedData[outOffset + 30] = ((i27 << 3) | (i28 >>> 29)) & 536870911;
      unCompressedData[outOffset + 31] = i28 & 536870911;
      input.offset += 117;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor30 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));
      final int i23 = ((compressedArray[inOffset + 92] & 0xFF) << 24) | ((compressedArray[inOffset + 93] & 0xFF) << 16) | ((compressedArray[inOffset + 94] & 0xFF) << 8) | ((compressedArray[inOffset + 95] & 0xFF));
      final int i24 = ((compressedArray[inOffset + 96] & 0xFF) << 24) | ((compressedArray[inOffset + 97] & 0xFF) << 16) | ((compressedArray[inOffset + 98] & 0xFF) << 8) | ((compressedArray[inOffset + 99] & 0xFF));
      final int i25 = ((compressedArray[inOffset + 100] & 0xFF) << 24) | ((compressedArray[inOffset + 101] & 0xFF) << 16) | ((compressedArray[inOffset + 102] & 0xFF) << 8) | ((compressedArray[inOffset + 103] & 0xFF));
      final int i26 = ((compressedArray[inOffset + 104] & 0xFF) << 24) | ((compressedArray[inOffset + 105] & 0xFF) << 16) | ((compressedArray[inOffset + 106] & 0xFF) << 8) | ((compressedArray[inOffset + 107] & 0xFF));
      final int i27 = ((compressedArray[inOffset + 108] & 0xFF) << 24) | ((compressedArray[inOffset + 109] & 0xFF) << 16) | ((compressedArray[inOffset + 110] & 0xFF) << 8) | ((compressedArray[inOffset + 111] & 0xFF));
      final int i28 = ((compressedArray[inOffset + 112] & 0xFF) << 24) | ((compressedArray[inOffset + 113] & 0xFF) << 16) | ((compressedArray[inOffset + 114] & 0xFF) << 8) | ((compressedArray[inOffset + 115] & 0xFF));
      final int i29 = ((compressedArray[inOffset + 116] & 0xFF) << 24) | ((compressedArray[inOffset + 117] & 0xFF) << 16) | ((compressedArray[inOffset + 118] & 0xFF) << 8) | ((compressedArray[inOffset + 119] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 2);
      unCompressedData[outOffset + 1] = ((i0 << 28) | (i1 >>> 4)) & 1073741823;
      unCompressedData[outOffset + 2] = ((i1 << 26) | (i2 >>> 6)) & 1073741823;
      unCompressedData[outOffset + 3] = ((i2 << 24) | (i3 >>> 8)) & 1073741823;
      unCompressedData[outOffset + 4] = ((i3 << 22) | (i4 >>> 10)) & 1073741823;
      unCompressedData[outOffset + 5] = ((i4 << 20) | (i5 >>> 12)) & 1073741823;
      unCompressedData[outOffset + 6] = ((i5 << 18) | (i6 >>> 14)) & 1073741823;
      unCompressedData[outOffset + 7] = ((i6 << 16) | (i7 >>> 16)) & 1073741823;
      unCompressedData[outOffset + 8] = ((i7 << 14) | (i8 >>> 18)) & 1073741823;
      unCompressedData[outOffset + 9] = ((i8 << 12) | (i9 >>> 20)) & 1073741823;
      unCompressedData[outOffset + 10] = ((i9 << 10) | (i10 >>> 22)) & 1073741823;
      unCompressedData[outOffset + 11] = ((i10 << 8) | (i11 >>> 24)) & 1073741823;
      unCompressedData[outOffset + 12] = ((i11 << 6) | (i12 >>> 26)) & 1073741823;
      unCompressedData[outOffset + 13] = ((i12 << 4) | (i13 >>> 28)) & 1073741823;
      unCompressedData[outOffset + 14] = ((i13 << 2) | (i14 >>> 30)) & 1073741823;
      unCompressedData[outOffset + 15] = i14 & 1073741823;
      unCompressedData[outOffset + 16] = (i15 >>> 2);
      unCompressedData[outOffset + 17] = ((i15 << 28) | (i16 >>> 4)) & 1073741823;
      unCompressedData[outOffset + 18] = ((i16 << 26) | (i17 >>> 6)) & 1073741823;
      unCompressedData[outOffset + 19] = ((i17 << 24) | (i18 >>> 8)) & 1073741823;
      unCompressedData[outOffset + 20] = ((i18 << 22) | (i19 >>> 10)) & 1073741823;
      unCompressedData[outOffset + 21] = ((i19 << 20) | (i20 >>> 12)) & 1073741823;
      unCompressedData[outOffset + 22] = ((i20 << 18) | (i21 >>> 14)) & 1073741823;
      unCompressedData[outOffset + 23] = ((i21 << 16) | (i22 >>> 16)) & 1073741823;
      unCompressedData[outOffset + 24] = ((i22 << 14) | (i23 >>> 18)) & 1073741823;
      unCompressedData[outOffset + 25] = ((i23 << 12) | (i24 >>> 20)) & 1073741823;
      unCompressedData[outOffset + 26] = ((i24 << 10) | (i25 >>> 22)) & 1073741823;
      unCompressedData[outOffset + 27] = ((i25 << 8) | (i26 >>> 24)) & 1073741823;
      unCompressedData[outOffset + 28] = ((i26 << 6) | (i27 >>> 26)) & 1073741823;
      unCompressedData[outOffset + 29] = ((i27 << 4) | (i28 >>> 28)) & 1073741823;
      unCompressedData[outOffset + 30] = ((i28 << 2) | (i29 >>> 30)) & 1073741823;
      unCompressedData[outOffset + 31] = i29 & 1073741823;
      input.offset += 121;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor31 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));
      final int i23 = ((compressedArray[inOffset + 92] & 0xFF) << 24) | ((compressedArray[inOffset + 93] & 0xFF) << 16) | ((compressedArray[inOffset + 94] & 0xFF) << 8) | ((compressedArray[inOffset + 95] & 0xFF));
      final int i24 = ((compressedArray[inOffset + 96] & 0xFF) << 24) | ((compressedArray[inOffset + 97] & 0xFF) << 16) | ((compressedArray[inOffset + 98] & 0xFF) << 8) | ((compressedArray[inOffset + 99] & 0xFF));
      final int i25 = ((compressedArray[inOffset + 100] & 0xFF) << 24) | ((compressedArray[inOffset + 101] & 0xFF) << 16) | ((compressedArray[inOffset + 102] & 0xFF) << 8) | ((compressedArray[inOffset + 103] & 0xFF));
      final int i26 = ((compressedArray[inOffset + 104] & 0xFF) << 24) | ((compressedArray[inOffset + 105] & 0xFF) << 16) | ((compressedArray[inOffset + 106] & 0xFF) << 8) | ((compressedArray[inOffset + 107] & 0xFF));
      final int i27 = ((compressedArray[inOffset + 108] & 0xFF) << 24) | ((compressedArray[inOffset + 109] & 0xFF) << 16) | ((compressedArray[inOffset + 110] & 0xFF) << 8) | ((compressedArray[inOffset + 111] & 0xFF));
      final int i28 = ((compressedArray[inOffset + 112] & 0xFF) << 24) | ((compressedArray[inOffset + 113] & 0xFF) << 16) | ((compressedArray[inOffset + 114] & 0xFF) << 8) | ((compressedArray[inOffset + 115] & 0xFF));
      final int i29 = ((compressedArray[inOffset + 116] & 0xFF) << 24) | ((compressedArray[inOffset + 117] & 0xFF) << 16) | ((compressedArray[inOffset + 118] & 0xFF) << 8) | ((compressedArray[inOffset + 119] & 0xFF));
      final int i30 = ((compressedArray[inOffset + 120] & 0xFF) << 24) | ((compressedArray[inOffset + 121] & 0xFF) << 16) | ((compressedArray[inOffset + 122] & 0xFF) << 8) | ((compressedArray[inOffset + 123] & 0xFF));

      unCompressedData[outOffset + 0] = (i0 >>> 1);
      unCompressedData[outOffset + 1] = ((i0 << 30) | (i1 >>> 2)) & 2147483647;
      unCompressedData[outOffset + 2] = ((i1 << 29) | (i2 >>> 3)) & 2147483647;
      unCompressedData[outOffset + 3] = ((i2 << 28) | (i3 >>> 4)) & 2147483647;
      unCompressedData[outOffset + 4] = ((i3 << 27) | (i4 >>> 5)) & 2147483647;
      unCompressedData[outOffset + 5] = ((i4 << 26) | (i5 >>> 6)) & 2147483647;
      unCompressedData[outOffset + 6] = ((i5 << 25) | (i6 >>> 7)) & 2147483647;
      unCompressedData[outOffset + 7] = ((i6 << 24) | (i7 >>> 8)) & 2147483647;
      unCompressedData[outOffset + 8] = ((i7 << 23) | (i8 >>> 9)) & 2147483647;
      unCompressedData[outOffset + 9] = ((i8 << 22) | (i9 >>> 10)) & 2147483647;
      unCompressedData[outOffset + 10] = ((i9 << 21) | (i10 >>> 11)) & 2147483647;
      unCompressedData[outOffset + 11] = ((i10 << 20) | (i11 >>> 12)) & 2147483647;
      unCompressedData[outOffset + 12] = ((i11 << 19) | (i12 >>> 13)) & 2147483647;
      unCompressedData[outOffset + 13] = ((i12 << 18) | (i13 >>> 14)) & 2147483647;
      unCompressedData[outOffset + 14] = ((i13 << 17) | (i14 >>> 15)) & 2147483647;
      unCompressedData[outOffset + 15] = ((i14 << 16) | (i15 >>> 16)) & 2147483647;
      unCompressedData[outOffset + 16] = ((i15 << 15) | (i16 >>> 17)) & 2147483647;
      unCompressedData[outOffset + 17] = ((i16 << 14) | (i17 >>> 18)) & 2147483647;
      unCompressedData[outOffset + 18] = ((i17 << 13) | (i18 >>> 19)) & 2147483647;
      unCompressedData[outOffset + 19] = ((i18 << 12) | (i19 >>> 20)) & 2147483647;
      unCompressedData[outOffset + 20] = ((i19 << 11) | (i20 >>> 21)) & 2147483647;
      unCompressedData[outOffset + 21] = ((i20 << 10) | (i21 >>> 22)) & 2147483647;
      unCompressedData[outOffset + 22] = ((i21 << 9) | (i22 >>> 23)) & 2147483647;
      unCompressedData[outOffset + 23] = ((i22 << 8) | (i23 >>> 24)) & 2147483647;
      unCompressedData[outOffset + 24] = ((i23 << 7) | (i24 >>> 25)) & 2147483647;
      unCompressedData[outOffset + 25] = ((i24 << 6) | (i25 >>> 26)) & 2147483647;
      unCompressedData[outOffset + 26] = ((i25 << 5) | (i26 >>> 27)) & 2147483647;
      unCompressedData[outOffset + 27] = ((i26 << 4) | (i27 >>> 28)) & 2147483647;
      unCompressedData[outOffset + 28] = ((i27 << 3) | (i28 >>> 29)) & 2147483647;
      unCompressedData[outOffset + 29] = ((i28 << 2) | (i29 >>> 30)) & 2147483647;
      unCompressedData[outOffset + 30] = ((i29 << 1) | (i30 >>> 31)) & 2147483647;
      unCompressedData[outOffset + 31] = i30 & 2147483647;
      input.offset += 125;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor32 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final int i0 = ((compressedArray[inOffset + 0] & 0xFF) << 24) | ((compressedArray[inOffset + 1] & 0xFF) << 16) | ((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF));
      final int i1 = ((compressedArray[inOffset + 4] & 0xFF) << 24) | ((compressedArray[inOffset + 5] & 0xFF) << 16) | ((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF));
      final int i2 = ((compressedArray[inOffset + 8] & 0xFF) << 24) | ((compressedArray[inOffset + 9] & 0xFF) << 16) | ((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF));
      final int i3 = ((compressedArray[inOffset + 12] & 0xFF) << 24) | ((compressedArray[inOffset + 13] & 0xFF) << 16) | ((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF));
      final int i4 = ((compressedArray[inOffset + 16] & 0xFF) << 24) | ((compressedArray[inOffset + 17] & 0xFF) << 16) | ((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF));
      final int i5 = ((compressedArray[inOffset + 20] & 0xFF) << 24) | ((compressedArray[inOffset + 21] & 0xFF) << 16) | ((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF));
      final int i6 = ((compressedArray[inOffset + 24] & 0xFF) << 24) | ((compressedArray[inOffset + 25] & 0xFF) << 16) | ((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF));
      final int i7 = ((compressedArray[inOffset + 28] & 0xFF) << 24) | ((compressedArray[inOffset + 29] & 0xFF) << 16) | ((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF));
      final int i8 = ((compressedArray[inOffset + 32] & 0xFF) << 24) | ((compressedArray[inOffset + 33] & 0xFF) << 16) | ((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF));
      final int i9 = ((compressedArray[inOffset + 36] & 0xFF) << 24) | ((compressedArray[inOffset + 37] & 0xFF) << 16) | ((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF));
      final int i10 = ((compressedArray[inOffset + 40] & 0xFF) << 24) | ((compressedArray[inOffset + 41] & 0xFF) << 16) | ((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF));
      final int i11 = ((compressedArray[inOffset + 44] & 0xFF) << 24) | ((compressedArray[inOffset + 45] & 0xFF) << 16) | ((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF));
      final int i12 = ((compressedArray[inOffset + 48] & 0xFF) << 24) | ((compressedArray[inOffset + 49] & 0xFF) << 16) | ((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF));
      final int i13 = ((compressedArray[inOffset + 52] & 0xFF) << 24) | ((compressedArray[inOffset + 53] & 0xFF) << 16) | ((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF));
      final int i14 = ((compressedArray[inOffset + 56] & 0xFF) << 24) | ((compressedArray[inOffset + 57] & 0xFF) << 16) | ((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF));
      final int i15 = ((compressedArray[inOffset + 60] & 0xFF) << 24) | ((compressedArray[inOffset + 61] & 0xFF) << 16) | ((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF));
      final int i16 = ((compressedArray[inOffset + 64] & 0xFF) << 24) | ((compressedArray[inOffset + 65] & 0xFF) << 16) | ((compressedArray[inOffset + 66] & 0xFF) << 8) | ((compressedArray[inOffset + 67] & 0xFF));
      final int i17 = ((compressedArray[inOffset + 68] & 0xFF) << 24) | ((compressedArray[inOffset + 69] & 0xFF) << 16) | ((compressedArray[inOffset + 70] & 0xFF) << 8) | ((compressedArray[inOffset + 71] & 0xFF));
      final int i18 = ((compressedArray[inOffset + 72] & 0xFF) << 24) | ((compressedArray[inOffset + 73] & 0xFF) << 16) | ((compressedArray[inOffset + 74] & 0xFF) << 8) | ((compressedArray[inOffset + 75] & 0xFF));
      final int i19 = ((compressedArray[inOffset + 76] & 0xFF) << 24) | ((compressedArray[inOffset + 77] & 0xFF) << 16) | ((compressedArray[inOffset + 78] & 0xFF) << 8) | ((compressedArray[inOffset + 79] & 0xFF));
      final int i20 = ((compressedArray[inOffset + 80] & 0xFF) << 24) | ((compressedArray[inOffset + 81] & 0xFF) << 16) | ((compressedArray[inOffset + 82] & 0xFF) << 8) | ((compressedArray[inOffset + 83] & 0xFF));
      final int i21 = ((compressedArray[inOffset + 84] & 0xFF) << 24) | ((compressedArray[inOffset + 85] & 0xFF) << 16) | ((compressedArray[inOffset + 86] & 0xFF) << 8) | ((compressedArray[inOffset + 87] & 0xFF));
      final int i22 = ((compressedArray[inOffset + 88] & 0xFF) << 24) | ((compressedArray[inOffset + 89] & 0xFF) << 16) | ((compressedArray[inOffset + 90] & 0xFF) << 8) | ((compressedArray[inOffset + 91] & 0xFF));
      final int i23 = ((compressedArray[inOffset + 92] & 0xFF) << 24) | ((compressedArray[inOffset + 93] & 0xFF) << 16) | ((compressedArray[inOffset + 94] & 0xFF) << 8) | ((compressedArray[inOffset + 95] & 0xFF));
      final int i24 = ((compressedArray[inOffset + 96] & 0xFF) << 24) | ((compressedArray[inOffset + 97] & 0xFF) << 16) | ((compressedArray[inOffset + 98] & 0xFF) << 8) | ((compressedArray[inOffset + 99] & 0xFF));
      final int i25 = ((compressedArray[inOffset + 100] & 0xFF) << 24) | ((compressedArray[inOffset + 101] & 0xFF) << 16) | ((compressedArray[inOffset + 102] & 0xFF) << 8) | ((compressedArray[inOffset + 103] & 0xFF));
      final int i26 = ((compressedArray[inOffset + 104] & 0xFF) << 24) | ((compressedArray[inOffset + 105] & 0xFF) << 16) | ((compressedArray[inOffset + 106] & 0xFF) << 8) | ((compressedArray[inOffset + 107] & 0xFF));
      final int i27 = ((compressedArray[inOffset + 108] & 0xFF) << 24) | ((compressedArray[inOffset + 109] & 0xFF) << 16) | ((compressedArray[inOffset + 110] & 0xFF) << 8) | ((compressedArray[inOffset + 111] & 0xFF));
      final int i28 = ((compressedArray[inOffset + 112] & 0xFF) << 24) | ((compressedArray[inOffset + 113] & 0xFF) << 16) | ((compressedArray[inOffset + 114] & 0xFF) << 8) | ((compressedArray[inOffset + 115] & 0xFF));
      final int i29 = ((compressedArray[inOffset + 116] & 0xFF) << 24) | ((compressedArray[inOffset + 117] & 0xFF) << 16) | ((compressedArray[inOffset + 118] & 0xFF) << 8) | ((compressedArray[inOffset + 119] & 0xFF));
      final int i30 = ((compressedArray[inOffset + 120] & 0xFF) << 24) | ((compressedArray[inOffset + 121] & 0xFF) << 16) | ((compressedArray[inOffset + 122] & 0xFF) << 8) | ((compressedArray[inOffset + 123] & 0xFF));
      final int i31 = ((compressedArray[inOffset + 124] & 0xFF) << 24) | ((compressedArray[inOffset + 125] & 0xFF) << 16) | ((compressedArray[inOffset + 126] & 0xFF) << 8) | ((compressedArray[inOffset + 127] & 0xFF));

      unCompressedData[outOffset + 0] = i0;
      unCompressedData[outOffset + 1] = i1;
      unCompressedData[outOffset + 2] = i2;
      unCompressedData[outOffset + 3] = i3;
      unCompressedData[outOffset + 4] = i4;
      unCompressedData[outOffset + 5] = i5;
      unCompressedData[outOffset + 6] = i6;
      unCompressedData[outOffset + 7] = i7;
      unCompressedData[outOffset + 8] = i8;
      unCompressedData[outOffset + 9] = i9;
      unCompressedData[outOffset + 10] = i10;
      unCompressedData[outOffset + 11] = i11;
      unCompressedData[outOffset + 12] = i12;
      unCompressedData[outOffset + 13] = i13;
      unCompressedData[outOffset + 14] = i14;
      unCompressedData[outOffset + 15] = i15;
      unCompressedData[outOffset + 16] = i16;
      unCompressedData[outOffset + 17] = i17;
      unCompressedData[outOffset + 18] = i18;
      unCompressedData[outOffset + 19] = i19;
      unCompressedData[outOffset + 20] = i20;
      unCompressedData[outOffset + 21] = i21;
      unCompressedData[outOffset + 22] = i22;
      unCompressedData[outOffset + 23] = i23;
      unCompressedData[outOffset + 24] = i24;
      unCompressedData[outOffset + 25] = i25;
      unCompressedData[outOffset + 26] = i26;
      unCompressedData[outOffset + 27] = i27;
      unCompressedData[outOffset + 28] = i28;
      unCompressedData[outOffset + 29] = i29;
      unCompressedData[outOffset + 30] = i30;
      unCompressedData[outOffset + 31] = i31;
      input.offset += 129;
      output.offset += 32;
    }
  }

  static final class FrameDecompressor33 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final int outOffset = output.offset;
      unCompressedData[outOffset] = 0;
      unCompressedData[outOffset + 1] = 0;
      unCompressedData[outOffset + 2] = 0;
      unCompressedData[outOffset + 3] = 0;
      unCompressedData[outOffset + 4] = 0;
      unCompressedData[outOffset + 5] = 0;
      unCompressedData[outOffset + 6] = 0;
      unCompressedData[outOffset + 7] = 0;
      unCompressedData[outOffset + 8] = 0;
      unCompressedData[outOffset + 9] = 0;
      unCompressedData[outOffset + 10] = 0;
      unCompressedData[outOffset + 11] = 0;
      unCompressedData[outOffset + 12] = 0;
      unCompressedData[outOffset + 13] = 0;
      unCompressedData[outOffset + 14] = 0;
      unCompressedData[outOffset + 15] = 0;
      output.offset += 16;
      input.offset += 1;
  }
  }

  static final class FrameDecompressor34 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];

      unCompressedData[outOffset + 0] = (i0 >>> 7) & 1;
      unCompressedData[outOffset + 1] = (i0 >>> 6) & 1;
      unCompressedData[outOffset + 2] = (i0 >>> 5) & 1;
      unCompressedData[outOffset + 3] = (i0 >>> 4) & 1;
      unCompressedData[outOffset + 4] = (i0 >>> 3) & 1;
      unCompressedData[outOffset + 5] = (i0 >>> 2) & 1;
      unCompressedData[outOffset + 6] = (i0 >>> 1) & 1;
      unCompressedData[outOffset + 7] = i0 & 1;
      unCompressedData[outOffset + 8] = (i1 >>> 7) & 1;
      unCompressedData[outOffset + 9] = (i1 >>> 6) & 1;
      unCompressedData[outOffset + 10] = (i1 >>> 5) & 1;
      unCompressedData[outOffset + 11] = (i1 >>> 4) & 1;
      unCompressedData[outOffset + 12] = (i1 >>> 3) & 1;
      unCompressedData[outOffset + 13] = (i1 >>> 2) & 1;
      unCompressedData[outOffset + 14] = (i1 >>> 1) & 1;
      unCompressedData[outOffset + 15] = i1 & 1;
      input.offset += 3;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor35 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];

      unCompressedData[outOffset + 0] = (i0 >>> 6) & 3;
      unCompressedData[outOffset + 1] = (i0 >>> 4) & 3;
      unCompressedData[outOffset + 2] = (i0 >>> 2) & 3;
      unCompressedData[outOffset + 3] = i0 & 3;
      unCompressedData[outOffset + 4] = (i1 >>> 6) & 3;
      unCompressedData[outOffset + 5] = (i1 >>> 4) & 3;
      unCompressedData[outOffset + 6] = (i1 >>> 2) & 3;
      unCompressedData[outOffset + 7] = i1 & 3;
      unCompressedData[outOffset + 8] = (i2 >>> 6) & 3;
      unCompressedData[outOffset + 9] = (i2 >>> 4) & 3;
      unCompressedData[outOffset + 10] = (i2 >>> 2) & 3;
      unCompressedData[outOffset + 11] = i2 & 3;
      unCompressedData[outOffset + 12] = (i3 >>> 6) & 3;
      unCompressedData[outOffset + 13] = (i3 >>> 4) & 3;
      unCompressedData[outOffset + 14] = (i3 >>> 2) & 3;
      unCompressedData[outOffset + 15] = i3 & 3;
      input.offset += 5;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor36 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];

      unCompressedData[outOffset + 0] = (i0 >>> 5) & 7;
      unCompressedData[outOffset + 1] = (i0 >>> 2) & 7;
      unCompressedData[outOffset + 2] = ((i0 & 3) << 1) | (i1 >>> 7) & 1;
      unCompressedData[outOffset + 3] = (i1 >>> 4) & 7;
      unCompressedData[outOffset + 4] = (i1 >>> 1) & 7;
      unCompressedData[outOffset + 5] = ((i1 & 1) << 2) | (i2 >>> 6) & 3;
      unCompressedData[outOffset + 6] = (i2 >>> 3) & 7;
      unCompressedData[outOffset + 7] = i2 & 7;
      unCompressedData[outOffset + 8] = (i3 >>> 5) & 7;
      unCompressedData[outOffset + 9] = (i3 >>> 2) & 7;
      unCompressedData[outOffset + 10] = ((i3 & 3) << 1) | (i4 >>> 7) & 1;
      unCompressedData[outOffset + 11] = (i4 >>> 4) & 7;
      unCompressedData[outOffset + 12] = (i4 >>> 1) & 7;
      unCompressedData[outOffset + 13] = ((i4 & 1) << 2) | (i5 >>> 6) & 3;
      unCompressedData[outOffset + 14] = (i5 >>> 3) & 7;
      unCompressedData[outOffset + 15] = i5 & 7;
      input.offset += 7;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor37 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];

      unCompressedData[outOffset + 0] = (i0 >>> 4) & 15;
      unCompressedData[outOffset + 1] = i0 & 15;
      unCompressedData[outOffset + 2] = (i1 >>> 4) & 15;
      unCompressedData[outOffset + 3] = i1 & 15;
      unCompressedData[outOffset + 4] = (i2 >>> 4) & 15;
      unCompressedData[outOffset + 5] = i2 & 15;
      unCompressedData[outOffset + 6] = (i3 >>> 4) & 15;
      unCompressedData[outOffset + 7] = i3 & 15;
      unCompressedData[outOffset + 8] = (i4 >>> 4) & 15;
      unCompressedData[outOffset + 9] = i4 & 15;
      unCompressedData[outOffset + 10] = (i5 >>> 4) & 15;
      unCompressedData[outOffset + 11] = i5 & 15;
      unCompressedData[outOffset + 12] = (i6 >>> 4) & 15;
      unCompressedData[outOffset + 13] = i6 & 15;
      unCompressedData[outOffset + 14] = (i7 >>> 4) & 15;
      unCompressedData[outOffset + 15] = i7 & 15;
      input.offset += 9;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor38 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];

      unCompressedData[outOffset + 0] = (i0 >>> 3) & 31;
      unCompressedData[outOffset + 1] = ((i0 & 7) << 2) | (i1 >>> 6) & 3;
      unCompressedData[outOffset + 2] = (i1 >>> 1) & 31;
      unCompressedData[outOffset + 3] = ((i1 & 1) << 4) | (i2 >>> 4) & 15;
      unCompressedData[outOffset + 4] = ((i2 & 15) << 1) | (i3 >>> 7) & 1;
      unCompressedData[outOffset + 5] = (i3 >>> 2) & 31;
      unCompressedData[outOffset + 6] = ((i3 & 3) << 3) | (i4 >>> 5) & 7;
      unCompressedData[outOffset + 7] = i4 & 31;
      unCompressedData[outOffset + 8] = (i5 >>> 3) & 31;
      unCompressedData[outOffset + 9] = ((i5 & 7) << 2) | (i6 >>> 6) & 3;
      unCompressedData[outOffset + 10] = (i6 >>> 1) & 31;
      unCompressedData[outOffset + 11] = ((i6 & 1) << 4) | (i7 >>> 4) & 15;
      unCompressedData[outOffset + 12] = ((i7 & 15) << 1) | (i8 >>> 7) & 1;
      unCompressedData[outOffset + 13] = (i8 >>> 2) & 31;
      unCompressedData[outOffset + 14] = ((i8 & 3) << 3) | (i9 >>> 5) & 7;
      unCompressedData[outOffset + 15] = i9 & 31;
      input.offset += 11;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor39 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];

      unCompressedData[outOffset + 0] = (i0 >>> 2) & 63;
      unCompressedData[outOffset + 1] = ((i0 & 3) << 4) | (i1 >>> 4) & 15;
      unCompressedData[outOffset + 2] = ((i1 & 15) << 2) | (i2 >>> 6) & 3;
      unCompressedData[outOffset + 3] = i2 & 63;
      unCompressedData[outOffset + 4] = (i3 >>> 2) & 63;
      unCompressedData[outOffset + 5] = ((i3 & 3) << 4) | (i4 >>> 4) & 15;
      unCompressedData[outOffset + 6] = ((i4 & 15) << 2) | (i5 >>> 6) & 3;
      unCompressedData[outOffset + 7] = i5 & 63;
      unCompressedData[outOffset + 8] = (i6 >>> 2) & 63;
      unCompressedData[outOffset + 9] = ((i6 & 3) << 4) | (i7 >>> 4) & 15;
      unCompressedData[outOffset + 10] = ((i7 & 15) << 2) | (i8 >>> 6) & 3;
      unCompressedData[outOffset + 11] = i8 & 63;
      unCompressedData[outOffset + 12] = (i9 >>> 2) & 63;
      unCompressedData[outOffset + 13] = ((i9 & 3) << 4) | (i10 >>> 4) & 15;
      unCompressedData[outOffset + 14] = ((i10 & 15) << 2) | (i11 >>> 6) & 3;
      unCompressedData[outOffset + 15] = i11 & 63;
      input.offset += 13;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor40 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];

      unCompressedData[outOffset + 0] = (i0 >>> 1) & 127;
      unCompressedData[outOffset + 1] = ((i0 & 1) << 6) | (i1 >>> 2) & 63;
      unCompressedData[outOffset + 2] = ((i1 & 3) << 5) | (i2 >>> 3) & 31;
      unCompressedData[outOffset + 3] = ((i2 & 7) << 4) | (i3 >>> 4) & 15;
      unCompressedData[outOffset + 4] = ((i3 & 15) << 3) | (i4 >>> 5) & 7;
      unCompressedData[outOffset + 5] = ((i4 & 31) << 2) | (i5 >>> 6) & 3;
      unCompressedData[outOffset + 6] = ((i5 & 63) << 1) | (i6 >>> 7) & 1;
      unCompressedData[outOffset + 7] = i6 & 127;
      unCompressedData[outOffset + 8] = (i7 >>> 1) & 127;
      unCompressedData[outOffset + 9] = ((i7 & 1) << 6) | (i8 >>> 2) & 63;
      unCompressedData[outOffset + 10] = ((i8 & 3) << 5) | (i9 >>> 3) & 31;
      unCompressedData[outOffset + 11] = ((i9 & 7) << 4) | (i10 >>> 4) & 15;
      unCompressedData[outOffset + 12] = ((i10 & 15) << 3) | (i11 >>> 5) & 7;
      unCompressedData[outOffset + 13] = ((i11 & 31) << 2) | (i12 >>> 6) & 3;
      unCompressedData[outOffset + 14] = ((i12 & 63) << 1) | (i13 >>> 7) & 1;
      unCompressedData[outOffset + 15] = i13 & 127;
      input.offset += 15;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor41 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      unCompressedData[outOffset] = compressedArray[inOffset] & 0xFF;
      unCompressedData[outOffset + 1] = compressedArray[inOffset + 1] & 0xFF;
      unCompressedData[outOffset + 2] = compressedArray[inOffset + 2] & 0xFF;
      unCompressedData[outOffset + 3] = compressedArray[inOffset + 3] & 0xFF;
      unCompressedData[outOffset + 4] = compressedArray[inOffset + 4] & 0xFF;
      unCompressedData[outOffset + 5] = compressedArray[inOffset + 5] & 0xFF;
      unCompressedData[outOffset + 6] = compressedArray[inOffset + 6] & 0xFF;
      unCompressedData[outOffset + 7] = compressedArray[inOffset + 7] & 0xFF;
      unCompressedData[outOffset + 8] = compressedArray[inOffset + 8] & 0xFF;
      unCompressedData[outOffset + 9] = compressedArray[inOffset + 9] & 0xFF;
      unCompressedData[outOffset + 10] = compressedArray[inOffset + 10] & 0xFF;
      unCompressedData[outOffset + 11] = compressedArray[inOffset + 11] & 0xFF;
      unCompressedData[outOffset + 12] = compressedArray[inOffset + 12] & 0xFF;
      unCompressedData[outOffset + 13] = compressedArray[inOffset + 13] & 0xFF;
      unCompressedData[outOffset + 14] = compressedArray[inOffset + 14] & 0xFF;
      unCompressedData[outOffset + 15] = compressedArray[inOffset + 15] & 0xFF;
      input.offset += 17;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor42 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));

      unCompressedData[outOffset + 0] = (i0 >>> 7) & 511;
      unCompressedData[outOffset + 1] = ((i0 << 2) | (i1 >>> 14 & 3)) & 511;
      unCompressedData[outOffset + 2] = (i1 >>> 5) & 511;
      unCompressedData[outOffset + 3] = ((i1 << 4) | (i2 >>> 12 & 15)) & 511;
      unCompressedData[outOffset + 4] = (i2 >>> 3) & 511;
      unCompressedData[outOffset + 5] = ((i2 << 6) | (i3 >>> 10 & 63)) & 511;
      unCompressedData[outOffset + 6] = (i3 >>> 1) & 511;
      unCompressedData[outOffset + 7] = ((i3 << 8) | (i4 >>> 8 & 255)) & 511;
      unCompressedData[outOffset + 8] = ((i4 << 1) | (i5 >>> 15 & 1)) & 511;
      unCompressedData[outOffset + 9] = (i5 >>> 6) & 511;
      unCompressedData[outOffset + 10] = ((i5 << 3) | (i6 >>> 13 & 7)) & 511;
      unCompressedData[outOffset + 11] = (i6 >>> 4) & 511;
      unCompressedData[outOffset + 12] = ((i6 << 5) | (i7 >>> 11 & 31)) & 511;
      unCompressedData[outOffset + 13] = (i7 >>> 2) & 511;
      unCompressedData[outOffset + 14] = ((i7 << 7) | (i8 >>> 9 & 127)) & 511;
      unCompressedData[outOffset + 15] = i8 & 511;
      input.offset += 19;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor43 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));

      unCompressedData[outOffset + 0] = (i0 >>> 6) & 1023;
      unCompressedData[outOffset + 1] = ((i0 << 4) | (i1 >>> 12 & 15)) & 1023;
      unCompressedData[outOffset + 2] = (i1 >>> 2) & 1023;
      unCompressedData[outOffset + 3] = ((i1 << 8) | (i2 >>> 8 & 255)) & 1023;
      unCompressedData[outOffset + 4] = ((i2 << 2) | (i3 >>> 14 & 3)) & 1023;
      unCompressedData[outOffset + 5] = (i3 >>> 4) & 1023;
      unCompressedData[outOffset + 6] = ((i3 << 6) | (i4 >>> 10 & 63)) & 1023;
      unCompressedData[outOffset + 7] = i4 & 1023;
      unCompressedData[outOffset + 8] = (i5 >>> 6) & 1023;
      unCompressedData[outOffset + 9] = ((i5 << 4) | (i6 >>> 12 & 15)) & 1023;
      unCompressedData[outOffset + 10] = (i6 >>> 2) & 1023;
      unCompressedData[outOffset + 11] = ((i6 << 8) | (i7 >>> 8 & 255)) & 1023;
      unCompressedData[outOffset + 12] = ((i7 << 2) | (i8 >>> 14 & 3)) & 1023;
      unCompressedData[outOffset + 13] = (i8 >>> 4) & 1023;
      unCompressedData[outOffset + 14] = ((i8 << 6) | (i9 >>> 10 & 63)) & 1023;
      unCompressedData[outOffset + 15] = i9 & 1023;
      input.offset += 21;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor44 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));

      unCompressedData[outOffset + 0] = (i0 >>> 5) & 2047;
      unCompressedData[outOffset + 1] = ((i0 << 6) | (i1 >>> 10 & 63)) & 2047;
      unCompressedData[outOffset + 2] = ((i1 << 1) | (i2 >>> 15 & 1)) & 2047;
      unCompressedData[outOffset + 3] = (i2 >>> 4) & 2047;
      unCompressedData[outOffset + 4] = ((i2 << 7) | (i3 >>> 9 & 127)) & 2047;
      unCompressedData[outOffset + 5] = ((i3 << 2) | (i4 >>> 14 & 3)) & 2047;
      unCompressedData[outOffset + 6] = (i4 >>> 3) & 2047;
      unCompressedData[outOffset + 7] = ((i4 << 8) | (i5 >>> 8 & 255)) & 2047;
      unCompressedData[outOffset + 8] = ((i5 << 3) | (i6 >>> 13 & 7)) & 2047;
      unCompressedData[outOffset + 9] = (i6 >>> 2) & 2047;
      unCompressedData[outOffset + 10] = ((i6 << 9) | (i7 >>> 7 & 511)) & 2047;
      unCompressedData[outOffset + 11] = ((i7 << 4) | (i8 >>> 12 & 15)) & 2047;
      unCompressedData[outOffset + 12] = (i8 >>> 1) & 2047;
      unCompressedData[outOffset + 13] = ((i8 << 10) | (i9 >>> 6 & 1023)) & 2047;
      unCompressedData[outOffset + 14] = ((i9 << 5) | (i10 >>> 11 & 31)) & 2047;
      unCompressedData[outOffset + 15] = i10 & 2047;
      input.offset += 23;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor45 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));

      unCompressedData[outOffset + 0] = (i0 >>> 4) & 4095;
      unCompressedData[outOffset + 1] = ((i0 << 8) | (i1 >>> 8 & 255)) & 4095;
      unCompressedData[outOffset + 2] = ((i1 << 4) | (i2 >>> 12 & 15)) & 4095;
      unCompressedData[outOffset + 3] = i2 & 4095;
      unCompressedData[outOffset + 4] = (i3 >>> 4) & 4095;
      unCompressedData[outOffset + 5] = ((i3 << 8) | (i4 >>> 8 & 255)) & 4095;
      unCompressedData[outOffset + 6] = ((i4 << 4) | (i5 >>> 12 & 15)) & 4095;
      unCompressedData[outOffset + 7] = i5 & 4095;
      unCompressedData[outOffset + 8] = (i6 >>> 4) & 4095;
      unCompressedData[outOffset + 9] = ((i6 << 8) | (i7 >>> 8 & 255)) & 4095;
      unCompressedData[outOffset + 10] = ((i7 << 4) | (i8 >>> 12 & 15)) & 4095;
      unCompressedData[outOffset + 11] = i8 & 4095;
      unCompressedData[outOffset + 12] = (i9 >>> 4) & 4095;
      unCompressedData[outOffset + 13] = ((i9 << 8) | (i10 >>> 8 & 255)) & 4095;
      unCompressedData[outOffset + 14] = ((i10 << 4) | (i11 >>> 12 & 15)) & 4095;
      unCompressedData[outOffset + 15] = i11 & 4095;
      input.offset += 25;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor46 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));

      unCompressedData[outOffset + 0] = (i0 >>> 3) & 8191;
      unCompressedData[outOffset + 1] = ((i0 << 10) | (i1 >>> 6 & 1023)) & 8191;
      unCompressedData[outOffset + 2] = ((i1 << 7) | (i2 >>> 9 & 127)) & 8191;
      unCompressedData[outOffset + 3] = ((i2 << 4) | (i3 >>> 12 & 15)) & 8191;
      unCompressedData[outOffset + 4] = ((i3 << 1) | (i4 >>> 15 & 1)) & 8191;
      unCompressedData[outOffset + 5] = (i4 >>> 2) & 8191;
      unCompressedData[outOffset + 6] = ((i4 << 11) | (i5 >>> 5 & 2047)) & 8191;
      unCompressedData[outOffset + 7] = ((i5 << 8) | (i6 >>> 8 & 255)) & 8191;
      unCompressedData[outOffset + 8] = ((i6 << 5) | (i7 >>> 11 & 31)) & 8191;
      unCompressedData[outOffset + 9] = ((i7 << 2) | (i8 >>> 14 & 3)) & 8191;
      unCompressedData[outOffset + 10] = (i8 >>> 1) & 8191;
      unCompressedData[outOffset + 11] = ((i8 << 12) | (i9 >>> 4 & 4095)) & 8191;
      unCompressedData[outOffset + 12] = ((i9 << 9) | (i10 >>> 7 & 511)) & 8191;
      unCompressedData[outOffset + 13] = ((i10 << 6) | (i11 >>> 10 & 63)) & 8191;
      unCompressedData[outOffset + 14] = ((i11 << 3) | (i12 >>> 13 & 7)) & 8191;
      unCompressedData[outOffset + 15] = i12 & 8191;
      input.offset += 27;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor47 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));

      unCompressedData[outOffset + 0] = (i0 >>> 2) & 16383;
      unCompressedData[outOffset + 1] = ((i0 << 12) | (i1 >>> 4 & 4095)) & 16383;
      unCompressedData[outOffset + 2] = ((i1 << 10) | (i2 >>> 6 & 1023)) & 16383;
      unCompressedData[outOffset + 3] = ((i2 << 8) | (i3 >>> 8 & 255)) & 16383;
      unCompressedData[outOffset + 4] = ((i3 << 6) | (i4 >>> 10 & 63)) & 16383;
      unCompressedData[outOffset + 5] = ((i4 << 4) | (i5 >>> 12 & 15)) & 16383;
      unCompressedData[outOffset + 6] = ((i5 << 2) | (i6 >>> 14 & 3)) & 16383;
      unCompressedData[outOffset + 7] = i6 & 16383;
      unCompressedData[outOffset + 8] = (i7 >>> 2) & 16383;
      unCompressedData[outOffset + 9] = ((i7 << 12) | (i8 >>> 4 & 4095)) & 16383;
      unCompressedData[outOffset + 10] = ((i8 << 10) | (i9 >>> 6 & 1023)) & 16383;
      unCompressedData[outOffset + 11] = ((i9 << 8) | (i10 >>> 8 & 255)) & 16383;
      unCompressedData[outOffset + 12] = ((i10 << 6) | (i11 >>> 10 & 63)) & 16383;
      unCompressedData[outOffset + 13] = ((i11 << 4) | (i12 >>> 12 & 15)) & 16383;
      unCompressedData[outOffset + 14] = ((i12 << 2) | (i13 >>> 14 & 3)) & 16383;
      unCompressedData[outOffset + 15] = i13 & 16383;
      input.offset += 29;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor48 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));

      unCompressedData[outOffset + 0] = (i0 >>> 1) & 32767;
      unCompressedData[outOffset + 1] = ((i0 << 14) | (i1 >>> 2 & 16383)) & 32767;
      unCompressedData[outOffset + 2] = ((i1 << 13) | (i2 >>> 3 & 8191)) & 32767;
      unCompressedData[outOffset + 3] = ((i2 << 12) | (i3 >>> 4 & 4095)) & 32767;
      unCompressedData[outOffset + 4] = ((i3 << 11) | (i4 >>> 5 & 2047)) & 32767;
      unCompressedData[outOffset + 5] = ((i4 << 10) | (i5 >>> 6 & 1023)) & 32767;
      unCompressedData[outOffset + 6] = ((i5 << 9) | (i6 >>> 7 & 511)) & 32767;
      unCompressedData[outOffset + 7] = ((i6 << 8) | (i7 >>> 8 & 255)) & 32767;
      unCompressedData[outOffset + 8] = ((i7 << 7) | (i8 >>> 9 & 127)) & 32767;
      unCompressedData[outOffset + 9] = ((i8 << 6) | (i9 >>> 10 & 63)) & 32767;
      unCompressedData[outOffset + 10] = ((i9 << 5) | (i10 >>> 11 & 31)) & 32767;
      unCompressedData[outOffset + 11] = ((i10 << 4) | (i11 >>> 12 & 15)) & 32767;
      unCompressedData[outOffset + 12] = ((i11 << 3) | (i12 >>> 13 & 7)) & 32767;
      unCompressedData[outOffset + 13] = ((i12 << 2) | (i13 >>> 14 & 3)) & 32767;
      unCompressedData[outOffset + 14] = ((i13 << 1) | (i14 >>> 15 & 1)) & 32767;
      unCompressedData[outOffset + 15] = i14 & 32767;
      input.offset += 31;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor49 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      unCompressedData[outOffset] = ((compressedArray[inOffset] & 0xFF) << 8) | (compressedArray[inOffset + 1] & 0xFF);
      unCompressedData[outOffset + 1] = ((compressedArray[inOffset + 2] & 0xFF) << 8) | (compressedArray[inOffset + 3] & 0xFF);
      unCompressedData[outOffset + 2] = ((compressedArray[inOffset + 4] & 0xFF) << 8) | (compressedArray[inOffset + 5] & 0xFF);
      unCompressedData[outOffset + 3] = ((compressedArray[inOffset + 6] & 0xFF) << 8) | (compressedArray[inOffset + 7] & 0xFF);
      unCompressedData[outOffset + 4] = ((compressedArray[inOffset + 8] & 0xFF) << 8) | (compressedArray[inOffset + 9] & 0xFF);
      unCompressedData[outOffset + 5] = ((compressedArray[inOffset + 10] & 0xFF) << 8) | (compressedArray[inOffset + 11] & 0xFF);
      unCompressedData[outOffset + 6] = ((compressedArray[inOffset + 12] & 0xFF) << 8) | (compressedArray[inOffset + 13] & 0xFF);
      unCompressedData[outOffset + 7] = ((compressedArray[inOffset + 14] & 0xFF) << 8) | (compressedArray[inOffset + 15] & 0xFF);
      unCompressedData[outOffset + 8] = ((compressedArray[inOffset + 16] & 0xFF) << 8) | (compressedArray[inOffset + 17] & 0xFF);
      unCompressedData[outOffset + 9] = ((compressedArray[inOffset + 18] & 0xFF) << 8) | (compressedArray[inOffset + 19] & 0xFF);
      unCompressedData[outOffset + 10] = ((compressedArray[inOffset + 20] & 0xFF) << 8) | (compressedArray[inOffset + 21] & 0xFF);
      unCompressedData[outOffset + 11] = ((compressedArray[inOffset + 22] & 0xFF) << 8) | (compressedArray[inOffset + 23] & 0xFF);
      unCompressedData[outOffset + 12] = ((compressedArray[inOffset + 24] & 0xFF) << 8) | (compressedArray[inOffset + 25] & 0xFF);
      unCompressedData[outOffset + 13] = ((compressedArray[inOffset + 26] & 0xFF) << 8) | (compressedArray[inOffset + 27] & 0xFF);
      unCompressedData[outOffset + 14] = ((compressedArray[inOffset + 28] & 0xFF) << 8) | (compressedArray[inOffset + 29] & 0xFF);
      unCompressedData[outOffset + 15] = ((compressedArray[inOffset + 30] & 0xFF) << 8) | (compressedArray[inOffset + 31] & 0xFF);
      input.offset += 33;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor50 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 1) | (i1 >>> 15 & 1)) & 131071;
      unCompressedData[outOffset + 1] = ((i1 << 2) | (i2 >>> 14 & 3)) & 131071;
      unCompressedData[outOffset + 2] = ((i2 << 3) | (i3 >>> 13 & 7)) & 131071;
      unCompressedData[outOffset + 3] = ((i3 << 4) | (i4 >>> 12 & 15)) & 131071;
      unCompressedData[outOffset + 4] = ((i4 << 5) | (i5 >>> 11 & 31)) & 131071;
      unCompressedData[outOffset + 5] = ((i5 << 6) | (i6 >>> 10 & 63)) & 131071;
      unCompressedData[outOffset + 6] = ((i6 << 7) | (i7 >>> 9 & 127)) & 131071;
      unCompressedData[outOffset + 7] = ((i7 << 8) | (i8 >>> 8 & 255)) & 131071;
      unCompressedData[outOffset + 8] = ((i8 << 9) | (i9 >>> 7 & 511)) & 131071;
      unCompressedData[outOffset + 9] = ((i9 << 10) | (i10 >>> 6 & 1023)) & 131071;
      unCompressedData[outOffset + 10] = ((i10 << 11) | (i11 >>> 5 & 2047)) & 131071;
      unCompressedData[outOffset + 11] = ((i11 << 12) | (i12 >>> 4 & 4095)) & 131071;
      unCompressedData[outOffset + 12] = ((i12 << 13) | (i13 >>> 3 & 8191)) & 131071;
      unCompressedData[outOffset + 13] = ((i13 << 14) | (i14 >>> 2 & 16383)) & 131071;
      unCompressedData[outOffset + 14] = ((i14 << 15) | (i15 >>> 1 & 32767)) & 131071;
      unCompressedData[outOffset + 15] = ((i15 << 16) | (i16 >>> 0 & 65535)) & 131071;
      input.offset += 35;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor51 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 2) | (i1 >>> 14 & 3)) & 262143;
      unCompressedData[outOffset + 1] = ((i1 << 4) | (i2 >>> 12 & 15)) & 262143;
      unCompressedData[outOffset + 2] = ((i2 << 6) | (i3 >>> 10 & 63)) & 262143;
      unCompressedData[outOffset + 3] = ((i3 << 8) | (i4 >>> 8 & 255)) & 262143;
      unCompressedData[outOffset + 4] = ((i4 << 10) | (i5 >>> 6 & 1023)) & 262143;
      unCompressedData[outOffset + 5] = ((i5 << 12) | (i6 >>> 4 & 4095)) & 262143;
      unCompressedData[outOffset + 6] = ((i6 << 14) | (i7 >>> 2 & 16383)) & 262143;
      unCompressedData[outOffset + 7] = ((i7 << 16) | (i8 >>> 0 & 65535)) & 262143;
      unCompressedData[outOffset + 8] = ((i9 << 2) | (i10 >>> 14 & 3)) & 262143;
      unCompressedData[outOffset + 9] = ((i10 << 4) | (i11 >>> 12 & 15)) & 262143;
      unCompressedData[outOffset + 10] = ((i11 << 6) | (i12 >>> 10 & 63)) & 262143;
      unCompressedData[outOffset + 11] = ((i12 << 8) | (i13 >>> 8 & 255)) & 262143;
      unCompressedData[outOffset + 12] = ((i13 << 10) | (i14 >>> 6 & 1023)) & 262143;
      unCompressedData[outOffset + 13] = ((i14 << 12) | (i15 >>> 4 & 4095)) & 262143;
      unCompressedData[outOffset + 14] = ((i15 << 14) | (i16 >>> 2 & 16383)) & 262143;
      unCompressedData[outOffset + 15] = ((i16 << 16) | (i17 >>> 0 & 65535)) & 262143;
      input.offset += 37;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor52 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 3) | (i1 >>> 13 & 7)) & 524287;
      unCompressedData[outOffset + 1] = ((i1 << 6) | (i2 >>> 10 & 63)) & 524287;
      unCompressedData[outOffset + 2] = ((i2 << 9) | (i3 >>> 7 & 511)) & 524287;
      unCompressedData[outOffset + 3] = ((i3 << 12) | (i4 >>> 4 & 4095)) & 524287;
      unCompressedData[outOffset + 4] = ((i4 << 15) | (i5 >>> 1 & 32767)) & 524287;
      unCompressedData[outOffset + 5] = ((i5 << 18) | (i6 << 2 & 262143) | (i7 >>> 14 & 3)) & 524287;
      unCompressedData[outOffset + 6] = ((i7 << 5) | (i8 >>> 11 & 31)) & 524287;
      unCompressedData[outOffset + 7] = ((i8 << 8) | (i9 >>> 8 & 255)) & 524287;
      unCompressedData[outOffset + 8] = ((i9 << 11) | (i10 >>> 5 & 2047)) & 524287;
      unCompressedData[outOffset + 9] = ((i10 << 14) | (i11 >>> 2 & 16383)) & 524287;
      unCompressedData[outOffset + 10] = ((i11 << 17) | (i12 << 1 & 131071) | (i13 >>> 15 & 1)) & 524287;
      unCompressedData[outOffset + 11] = ((i13 << 4) | (i14 >>> 12 & 15)) & 524287;
      unCompressedData[outOffset + 12] = ((i14 << 7) | (i15 >>> 9 & 127)) & 524287;
      unCompressedData[outOffset + 13] = ((i15 << 10) | (i16 >>> 6 & 1023)) & 524287;
      unCompressedData[outOffset + 14] = ((i16 << 13) | (i17 >>> 3 & 8191)) & 524287;
      unCompressedData[outOffset + 15] = ((i17 << 16) | (i18 >>> 0 & 65535)) & 524287;
      input.offset += 39;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor53 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 4) | (i1 >>> 12 & 15)) & 1048575;
      unCompressedData[outOffset + 1] = ((i1 << 8) | (i2 >>> 8 & 255)) & 1048575;
      unCompressedData[outOffset + 2] = ((i2 << 12) | (i3 >>> 4 & 4095)) & 1048575;
      unCompressedData[outOffset + 3] = ((i3 << 16) | (i4 >>> 0 & 65535)) & 1048575;
      unCompressedData[outOffset + 4] = ((i5 << 4) | (i6 >>> 12 & 15)) & 1048575;
      unCompressedData[outOffset + 5] = ((i6 << 8) | (i7 >>> 8 & 255)) & 1048575;
      unCompressedData[outOffset + 6] = ((i7 << 12) | (i8 >>> 4 & 4095)) & 1048575;
      unCompressedData[outOffset + 7] = ((i8 << 16) | (i9 >>> 0 & 65535)) & 1048575;
      unCompressedData[outOffset + 8] = ((i10 << 4) | (i11 >>> 12 & 15)) & 1048575;
      unCompressedData[outOffset + 9] = ((i11 << 8) | (i12 >>> 8 & 255)) & 1048575;
      unCompressedData[outOffset + 10] = ((i12 << 12) | (i13 >>> 4 & 4095)) & 1048575;
      unCompressedData[outOffset + 11] = ((i13 << 16) | (i14 >>> 0 & 65535)) & 1048575;
      unCompressedData[outOffset + 12] = ((i15 << 4) | (i16 >>> 12 & 15)) & 1048575;
      unCompressedData[outOffset + 13] = ((i16 << 8) | (i17 >>> 8 & 255)) & 1048575;
      unCompressedData[outOffset + 14] = ((i17 << 12) | (i18 >>> 4 & 4095)) & 1048575;
      unCompressedData[outOffset + 15] = ((i18 << 16) | (i19 >>> 0 & 65535)) & 1048575;
      input.offset += 41;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor54 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 5) | (i1 >>> 11 & 31)) & 2097151;
      unCompressedData[outOffset + 1] = ((i1 << 10) | (i2 >>> 6 & 1023)) & 2097151;
      unCompressedData[outOffset + 2] = ((i2 << 15) | (i3 >>> 1 & 32767)) & 2097151;
      unCompressedData[outOffset + 3] = ((i3 << 20) | (i4 << 4 & 1048575) | (i5 >>> 12 & 15)) & 2097151;
      unCompressedData[outOffset + 4] = ((i5 << 9) | (i6 >>> 7 & 511)) & 2097151;
      unCompressedData[outOffset + 5] = ((i6 << 14) | (i7 >>> 2 & 16383)) & 2097151;
      unCompressedData[outOffset + 6] = ((i7 << 19) | (i8 << 3 & 524287) | (i9 >>> 13 & 7)) & 2097151;
      unCompressedData[outOffset + 7] = ((i9 << 8) | (i10 >>> 8 & 255)) & 2097151;
      unCompressedData[outOffset + 8] = ((i10 << 13) | (i11 >>> 3 & 8191)) & 2097151;
      unCompressedData[outOffset + 9] = ((i11 << 18) | (i12 << 2 & 262143) | (i13 >>> 14 & 3)) & 2097151;
      unCompressedData[outOffset + 10] = ((i13 << 7) | (i14 >>> 9 & 127)) & 2097151;
      unCompressedData[outOffset + 11] = ((i14 << 12) | (i15 >>> 4 & 4095)) & 2097151;
      unCompressedData[outOffset + 12] = ((i15 << 17) | (i16 << 1 & 131071) | (i17 >>> 15 & 1)) & 2097151;
      unCompressedData[outOffset + 13] = ((i17 << 6) | (i18 >>> 10 & 63)) & 2097151;
      unCompressedData[outOffset + 14] = ((i18 << 11) | (i19 >>> 5 & 2047)) & 2097151;
      unCompressedData[outOffset + 15] = ((i19 << 16) | (i20 >>> 0 & 65535)) & 2097151;
      input.offset += 43;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor55 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 6) | (i1 >>> 10 & 63)) & 4194303;
      unCompressedData[outOffset + 1] = ((i1 << 12) | (i2 >>> 4 & 4095)) & 4194303;
      unCompressedData[outOffset + 2] = ((i2 << 18) | (i3 << 2 & 262143) | (i4 >>> 14 & 3)) & 4194303;
      unCompressedData[outOffset + 3] = ((i4 << 8) | (i5 >>> 8 & 255)) & 4194303;
      unCompressedData[outOffset + 4] = ((i5 << 14) | (i6 >>> 2 & 16383)) & 4194303;
      unCompressedData[outOffset + 5] = ((i6 << 20) | (i7 << 4 & 1048575) | (i8 >>> 12 & 15)) & 4194303;
      unCompressedData[outOffset + 6] = ((i8 << 10) | (i9 >>> 6 & 1023)) & 4194303;
      unCompressedData[outOffset + 7] = ((i9 << 16) | (i10 >>> 0 & 65535)) & 4194303;
      unCompressedData[outOffset + 8] = ((i11 << 6) | (i12 >>> 10 & 63)) & 4194303;
      unCompressedData[outOffset + 9] = ((i12 << 12) | (i13 >>> 4 & 4095)) & 4194303;
      unCompressedData[outOffset + 10] = ((i13 << 18) | (i14 << 2 & 262143) | (i15 >>> 14 & 3)) & 4194303;
      unCompressedData[outOffset + 11] = ((i15 << 8) | (i16 >>> 8 & 255)) & 4194303;
      unCompressedData[outOffset + 12] = ((i16 << 14) | (i17 >>> 2 & 16383)) & 4194303;
      unCompressedData[outOffset + 13] = ((i17 << 20) | (i18 << 4 & 1048575) | (i19 >>> 12 & 15)) & 4194303;
      unCompressedData[outOffset + 14] = ((i19 << 10) | (i20 >>> 6 & 1023)) & 4194303;
      unCompressedData[outOffset + 15] = ((i20 << 16) | (i21 >>> 0 & 65535)) & 4194303;
      input.offset += 45;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor56 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 7) | (i1 >>> 9 & 127)) & 8388607;
      unCompressedData[outOffset + 1] = ((i1 << 14) | (i2 >>> 2 & 16383)) & 8388607;
      unCompressedData[outOffset + 2] = ((i2 << 21) | (i3 << 5 & 2097151) | (i4 >>> 11 & 31)) & 8388607;
      unCompressedData[outOffset + 3] = ((i4 << 12) | (i5 >>> 4 & 4095)) & 8388607;
      unCompressedData[outOffset + 4] = ((i5 << 19) | (i6 << 3 & 524287) | (i7 >>> 13 & 7)) & 8388607;
      unCompressedData[outOffset + 5] = ((i7 << 10) | (i8 >>> 6 & 1023)) & 8388607;
      unCompressedData[outOffset + 6] = ((i8 << 17) | (i9 << 1 & 131071) | (i10 >>> 15 & 1)) & 8388607;
      unCompressedData[outOffset + 7] = ((i10 << 8) | (i11 >>> 8 & 255)) & 8388607;
      unCompressedData[outOffset + 8] = ((i11 << 15) | (i12 >>> 1 & 32767)) & 8388607;
      unCompressedData[outOffset + 9] = ((i12 << 22) | (i13 << 6 & 4194303) | (i14 >>> 10 & 63)) & 8388607;
      unCompressedData[outOffset + 10] = ((i14 << 13) | (i15 >>> 3 & 8191)) & 8388607;
      unCompressedData[outOffset + 11] = ((i15 << 20) | (i16 << 4 & 1048575) | (i17 >>> 12 & 15)) & 8388607;
      unCompressedData[outOffset + 12] = ((i17 << 11) | (i18 >>> 5 & 2047)) & 8388607;
      unCompressedData[outOffset + 13] = ((i18 << 18) | (i19 << 2 & 262143) | (i20 >>> 14 & 3)) & 8388607;
      unCompressedData[outOffset + 14] = ((i20 << 9) | (i21 >>> 7 & 511)) & 8388607;
      unCompressedData[outOffset + 15] = ((i21 << 16) | (i22 >>> 0 & 65535)) & 8388607;
      input.offset += 47;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor57 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));
      final short i23 = (short) (((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 8) | (i1 >>> 8 & 255)) & 16777215;
      unCompressedData[outOffset + 1] = ((i1 << 16) | (i2 >>> 0 & 65535)) & 16777215;
      unCompressedData[outOffset + 2] = ((i3 << 8) | (i4 >>> 8 & 255)) & 16777215;
      unCompressedData[outOffset + 3] = ((i4 << 16) | (i5 >>> 0 & 65535)) & 16777215;
      unCompressedData[outOffset + 4] = ((i6 << 8) | (i7 >>> 8 & 255)) & 16777215;
      unCompressedData[outOffset + 5] = ((i7 << 16) | (i8 >>> 0 & 65535)) & 16777215;
      unCompressedData[outOffset + 6] = ((i9 << 8) | (i10 >>> 8 & 255)) & 16777215;
      unCompressedData[outOffset + 7] = ((i10 << 16) | (i11 >>> 0 & 65535)) & 16777215;
      unCompressedData[outOffset + 8] = ((i12 << 8) | (i13 >>> 8 & 255)) & 16777215;
      unCompressedData[outOffset + 9] = ((i13 << 16) | (i14 >>> 0 & 65535)) & 16777215;
      unCompressedData[outOffset + 10] = ((i15 << 8) | (i16 >>> 8 & 255)) & 16777215;
      unCompressedData[outOffset + 11] = ((i16 << 16) | (i17 >>> 0 & 65535)) & 16777215;
      unCompressedData[outOffset + 12] = ((i18 << 8) | (i19 >>> 8 & 255)) & 16777215;
      unCompressedData[outOffset + 13] = ((i19 << 16) | (i20 >>> 0 & 65535)) & 16777215;
      unCompressedData[outOffset + 14] = ((i21 << 8) | (i22 >>> 8 & 255)) & 16777215;
      unCompressedData[outOffset + 15] = ((i22 << 16) | (i23 >>> 0 & 65535)) & 16777215;
      input.offset += 49;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor58 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));
      final short i23 = (short) (((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF)));
      final short i24 = (short) (((compressedArray[inOffset + 48] & 0xFF) << 8) | ((compressedArray[inOffset + 49] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 9) | (i1 >>> 7 & 511)) & 33554431;
      unCompressedData[outOffset + 1] = ((i1 << 18) | (i2 << 2 & 262143) | (i3 >>> 14 & 3)) & 33554431;
      unCompressedData[outOffset + 2] = ((i3 << 11) | (i4 >>> 5 & 2047)) & 33554431;
      unCompressedData[outOffset + 3] = ((i4 << 20) | (i5 << 4 & 1048575) | (i6 >>> 12 & 15)) & 33554431;
      unCompressedData[outOffset + 4] = ((i6 << 13) | (i7 >>> 3 & 8191)) & 33554431;
      unCompressedData[outOffset + 5] = ((i7 << 22) | (i8 << 6 & 4194303) | (i9 >>> 10 & 63)) & 33554431;
      unCompressedData[outOffset + 6] = ((i9 << 15) | (i10 >>> 1 & 32767)) & 33554431;
      unCompressedData[outOffset + 7] = ((i10 << 24) | (i11 << 8 & 16777215) | (i12 >>> 8 & 255)) & 33554431;
      unCompressedData[outOffset + 8] = ((i12 << 17) | (i13 << 1 & 131071) | (i14 >>> 15 & 1)) & 33554431;
      unCompressedData[outOffset + 9] = ((i14 << 10) | (i15 >>> 6 & 1023)) & 33554431;
      unCompressedData[outOffset + 10] = ((i15 << 19) | (i16 << 3 & 524287) | (i17 >>> 13 & 7)) & 33554431;
      unCompressedData[outOffset + 11] = ((i17 << 12) | (i18 >>> 4 & 4095)) & 33554431;
      unCompressedData[outOffset + 12] = ((i18 << 21) | (i19 << 5 & 2097151) | (i20 >>> 11 & 31)) & 33554431;
      unCompressedData[outOffset + 13] = ((i20 << 14) | (i21 >>> 2 & 16383)) & 33554431;
      unCompressedData[outOffset + 14] = ((i21 << 23) | (i22 << 7 & 8388607) | (i23 >>> 9 & 127)) & 33554431;
      unCompressedData[outOffset + 15] = ((i23 << 16) | (i24 >>> 0 & 65535)) & 33554431;
      input.offset += 51;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor59 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));
      final short i23 = (short) (((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF)));
      final short i24 = (short) (((compressedArray[inOffset + 48] & 0xFF) << 8) | ((compressedArray[inOffset + 49] & 0xFF)));
      final short i25 = (short) (((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 10) | (i1 >>> 6 & 1023)) & 67108863;
      unCompressedData[outOffset + 1] = ((i1 << 20) | (i2 << 4 & 1048575) | (i3 >>> 12 & 15)) & 67108863;
      unCompressedData[outOffset + 2] = ((i3 << 14) | (i4 >>> 2 & 16383)) & 67108863;
      unCompressedData[outOffset + 3] = ((i4 << 24) | (i5 << 8 & 16777215) | (i6 >>> 8 & 255)) & 67108863;
      unCompressedData[outOffset + 4] = ((i6 << 18) | (i7 << 2 & 262143) | (i8 >>> 14 & 3)) & 67108863;
      unCompressedData[outOffset + 5] = ((i8 << 12) | (i9 >>> 4 & 4095)) & 67108863;
      unCompressedData[outOffset + 6] = ((i9 << 22) | (i10 << 6 & 4194303) | (i11 >>> 10 & 63)) & 67108863;
      unCompressedData[outOffset + 7] = ((i11 << 16) | (i12 >>> 0 & 65535)) & 67108863;
      unCompressedData[outOffset + 8] = ((i13 << 10) | (i14 >>> 6 & 1023)) & 67108863;
      unCompressedData[outOffset + 9] = ((i14 << 20) | (i15 << 4 & 1048575) | (i16 >>> 12 & 15)) & 67108863;
      unCompressedData[outOffset + 10] = ((i16 << 14) | (i17 >>> 2 & 16383)) & 67108863;
      unCompressedData[outOffset + 11] = ((i17 << 24) | (i18 << 8 & 16777215) | (i19 >>> 8 & 255)) & 67108863;
      unCompressedData[outOffset + 12] = ((i19 << 18) | (i20 << 2 & 262143) | (i21 >>> 14 & 3)) & 67108863;
      unCompressedData[outOffset + 13] = ((i21 << 12) | (i22 >>> 4 & 4095)) & 67108863;
      unCompressedData[outOffset + 14] = ((i22 << 22) | (i23 << 6 & 4194303) | (i24 >>> 10 & 63)) & 67108863;
      unCompressedData[outOffset + 15] = ((i24 << 16) | (i25 >>> 0 & 65535)) & 67108863;
      input.offset += 53;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor60 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));
      final short i23 = (short) (((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF)));
      final short i24 = (short) (((compressedArray[inOffset + 48] & 0xFF) << 8) | ((compressedArray[inOffset + 49] & 0xFF)));
      final short i25 = (short) (((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF)));
      final short i26 = (short) (((compressedArray[inOffset + 52] & 0xFF) << 8) | ((compressedArray[inOffset + 53] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 11) | (i1 >>> 5 & 2047)) & 134217727;
      unCompressedData[outOffset + 1] = ((i1 << 22) | (i2 << 6 & 4194303) | (i3 >>> 10 & 63)) & 134217727;
      unCompressedData[outOffset + 2] = ((i3 << 17) | (i4 << 1 & 131071) | (i5 >>> 15 & 1)) & 134217727;
      unCompressedData[outOffset + 3] = ((i5 << 12) | (i6 >>> 4 & 4095)) & 134217727;
      unCompressedData[outOffset + 4] = ((i6 << 23) | (i7 << 7 & 8388607) | (i8 >>> 9 & 127)) & 134217727;
      unCompressedData[outOffset + 5] = ((i8 << 18) | (i9 << 2 & 262143) | (i10 >>> 14 & 3)) & 134217727;
      unCompressedData[outOffset + 6] = ((i10 << 13) | (i11 >>> 3 & 8191)) & 134217727;
      unCompressedData[outOffset + 7] = ((i11 << 24) | (i12 << 8 & 16777215) | (i13 >>> 8 & 255)) & 134217727;
      unCompressedData[outOffset + 8] = ((i13 << 19) | (i14 << 3 & 524287) | (i15 >>> 13 & 7)) & 134217727;
      unCompressedData[outOffset + 9] = ((i15 << 14) | (i16 >>> 2 & 16383)) & 134217727;
      unCompressedData[outOffset + 10] = ((i16 << 25) | (i17 << 9 & 33554431) | (i18 >>> 7 & 511)) & 134217727;
      unCompressedData[outOffset + 11] = ((i18 << 20) | (i19 << 4 & 1048575) | (i20 >>> 12 & 15)) & 134217727;
      unCompressedData[outOffset + 12] = ((i20 << 15) | (i21 >>> 1 & 32767)) & 134217727;
      unCompressedData[outOffset + 13] = ((i21 << 26) | (i22 << 10 & 67108863) | (i23 >>> 6 & 1023)) & 134217727;
      unCompressedData[outOffset + 14] = ((i23 << 21) | (i24 << 5 & 2097151) | (i25 >>> 11 & 31)) & 134217727;
      unCompressedData[outOffset + 15] = ((i25 << 16) | (i26 >>> 0 & 65535)) & 134217727;
      input.offset += 55;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor61 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));
      final short i23 = (short) (((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF)));
      final short i24 = (short) (((compressedArray[inOffset + 48] & 0xFF) << 8) | ((compressedArray[inOffset + 49] & 0xFF)));
      final short i25 = (short) (((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF)));
      final short i26 = (short) (((compressedArray[inOffset + 52] & 0xFF) << 8) | ((compressedArray[inOffset + 53] & 0xFF)));
      final short i27 = (short) (((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 12) | (i1 >>> 4 & 4095)) & 268435455;
      unCompressedData[outOffset + 1] = ((i1 << 24) | (i2 << 8 & 16777215) | (i3 >>> 8 & 255)) & 268435455;
      unCompressedData[outOffset + 2] = ((i3 << 20) | (i4 << 4 & 1048575) | (i5 >>> 12 & 15)) & 268435455;
      unCompressedData[outOffset + 3] = ((i5 << 16) | (i6 >>> 0 & 65535)) & 268435455;
      unCompressedData[outOffset + 4] = ((i7 << 12) | (i8 >>> 4 & 4095)) & 268435455;
      unCompressedData[outOffset + 5] = ((i8 << 24) | (i9 << 8 & 16777215) | (i10 >>> 8 & 255)) & 268435455;
      unCompressedData[outOffset + 6] = ((i10 << 20) | (i11 << 4 & 1048575) | (i12 >>> 12 & 15)) & 268435455;
      unCompressedData[outOffset + 7] = ((i12 << 16) | (i13 >>> 0 & 65535)) & 268435455;
      unCompressedData[outOffset + 8] = ((i14 << 12) | (i15 >>> 4 & 4095)) & 268435455;
      unCompressedData[outOffset + 9] = ((i15 << 24) | (i16 << 8 & 16777215) | (i17 >>> 8 & 255)) & 268435455;
      unCompressedData[outOffset + 10] = ((i17 << 20) | (i18 << 4 & 1048575) | (i19 >>> 12 & 15)) & 268435455;
      unCompressedData[outOffset + 11] = ((i19 << 16) | (i20 >>> 0 & 65535)) & 268435455;
      unCompressedData[outOffset + 12] = ((i21 << 12) | (i22 >>> 4 & 4095)) & 268435455;
      unCompressedData[outOffset + 13] = ((i22 << 24) | (i23 << 8 & 16777215) | (i24 >>> 8 & 255)) & 268435455;
      unCompressedData[outOffset + 14] = ((i24 << 20) | (i25 << 4 & 1048575) | (i26 >>> 12 & 15)) & 268435455;
      unCompressedData[outOffset + 15] = ((i26 << 16) | (i27 >>> 0 & 65535)) & 268435455;
      input.offset += 57;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor62 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));
      final short i23 = (short) (((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF)));
      final short i24 = (short) (((compressedArray[inOffset + 48] & 0xFF) << 8) | ((compressedArray[inOffset + 49] & 0xFF)));
      final short i25 = (short) (((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF)));
      final short i26 = (short) (((compressedArray[inOffset + 52] & 0xFF) << 8) | ((compressedArray[inOffset + 53] & 0xFF)));
      final short i27 = (short) (((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF)));
      final short i28 = (short) (((compressedArray[inOffset + 56] & 0xFF) << 8) | ((compressedArray[inOffset + 57] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 13) | (i1 >>> 3 & 8191)) & 536870911;
      unCompressedData[outOffset + 1] = ((i1 << 26) | (i2 << 10 & 67108863) | (i3 >>> 6 & 1023)) & 536870911;
      unCompressedData[outOffset + 2] = ((i3 << 23) | (i4 << 7 & 8388607) | (i5 >>> 9 & 127)) & 536870911;
      unCompressedData[outOffset + 3] = ((i5 << 20) | (i6 << 4 & 1048575) | (i7 >>> 12 & 15)) & 536870911;
      unCompressedData[outOffset + 4] = ((i7 << 17) | (i8 << 1 & 131071) | (i9 >>> 15 & 1)) & 536870911;
      unCompressedData[outOffset + 5] = ((i9 << 14) | (i10 >>> 2 & 16383)) & 536870911;
      unCompressedData[outOffset + 6] = ((i10 << 27) | (i11 << 11 & 134217727) | (i12 >>> 5 & 2047)) & 536870911;
      unCompressedData[outOffset + 7] = ((i12 << 24) | (i13 << 8 & 16777215) | (i14 >>> 8 & 255)) & 536870911;
      unCompressedData[outOffset + 8] = ((i14 << 21) | (i15 << 5 & 2097151) | (i16 >>> 11 & 31)) & 536870911;
      unCompressedData[outOffset + 9] = ((i16 << 18) | (i17 << 2 & 262143) | (i18 >>> 14 & 3)) & 536870911;
      unCompressedData[outOffset + 10] = ((i18 << 15) | (i19 >>> 1 & 32767)) & 536870911;
      unCompressedData[outOffset + 11] = ((i19 << 28) | (i20 << 12 & 268435455) | (i21 >>> 4 & 4095)) & 536870911;
      unCompressedData[outOffset + 12] = ((i21 << 25) | (i22 << 9 & 33554431) | (i23 >>> 7 & 511)) & 536870911;
      unCompressedData[outOffset + 13] = ((i23 << 22) | (i24 << 6 & 4194303) | (i25 >>> 10 & 63)) & 536870911;
      unCompressedData[outOffset + 14] = ((i25 << 19) | (i26 << 3 & 524287) | (i27 >>> 13 & 7)) & 536870911;
      unCompressedData[outOffset + 15] = ((i27 << 16) | (i28 >>> 0 & 65535)) & 536870911;
      input.offset += 59;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor63 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));
      final short i23 = (short) (((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF)));
      final short i24 = (short) (((compressedArray[inOffset + 48] & 0xFF) << 8) | ((compressedArray[inOffset + 49] & 0xFF)));
      final short i25 = (short) (((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF)));
      final short i26 = (short) (((compressedArray[inOffset + 52] & 0xFF) << 8) | ((compressedArray[inOffset + 53] & 0xFF)));
      final short i27 = (short) (((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF)));
      final short i28 = (short) (((compressedArray[inOffset + 56] & 0xFF) << 8) | ((compressedArray[inOffset + 57] & 0xFF)));
      final short i29 = (short) (((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 14) | (i1 >>> 2 & 16383)) & 1073741823;
      unCompressedData[outOffset + 1] = ((i1 << 28) | (i2 << 12 & 268435455) | (i3 >>> 4 & 4095)) & 1073741823;
      unCompressedData[outOffset + 2] = ((i3 << 26) | (i4 << 10 & 67108863) | (i5 >>> 6 & 1023)) & 1073741823;
      unCompressedData[outOffset + 3] = ((i5 << 24) | (i6 << 8 & 16777215) | (i7 >>> 8 & 255)) & 1073741823;
      unCompressedData[outOffset + 4] = ((i7 << 22) | (i8 << 6 & 4194303) | (i9 >>> 10 & 63)) & 1073741823;
      unCompressedData[outOffset + 5] = ((i9 << 20) | (i10 << 4 & 1048575) | (i11 >>> 12 & 15)) & 1073741823;
      unCompressedData[outOffset + 6] = ((i11 << 18) | (i12 << 2 & 262143) | (i13 >>> 14 & 3)) & 1073741823;
      unCompressedData[outOffset + 7] = ((i13 << 16) | (i14 >>> 0 & 65535)) & 1073741823;
      unCompressedData[outOffset + 8] = ((i15 << 14) | (i16 >>> 2 & 16383)) & 1073741823;
      unCompressedData[outOffset + 9] = ((i16 << 28) | (i17 << 12 & 268435455) | (i18 >>> 4 & 4095)) & 1073741823;
      unCompressedData[outOffset + 10] = ((i18 << 26) | (i19 << 10 & 67108863) | (i20 >>> 6 & 1023)) & 1073741823;
      unCompressedData[outOffset + 11] = ((i20 << 24) | (i21 << 8 & 16777215) | (i22 >>> 8 & 255)) & 1073741823;
      unCompressedData[outOffset + 12] = ((i22 << 22) | (i23 << 6 & 4194303) | (i24 >>> 10 & 63)) & 1073741823;
      unCompressedData[outOffset + 13] = ((i24 << 20) | (i25 << 4 & 1048575) | (i26 >>> 12 & 15)) & 1073741823;
      unCompressedData[outOffset + 14] = ((i26 << 18) | (i27 << 2 & 262143) | (i28 >>> 14 & 3)) & 1073741823;
      unCompressedData[outOffset + 15] = ((i28 << 16) | (i29 >>> 0 & 65535)) & 1073741823;
      input.offset += 61;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor64 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));
      final short i23 = (short) (((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF)));
      final short i24 = (short) (((compressedArray[inOffset + 48] & 0xFF) << 8) | ((compressedArray[inOffset + 49] & 0xFF)));
      final short i25 = (short) (((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF)));
      final short i26 = (short) (((compressedArray[inOffset + 52] & 0xFF) << 8) | ((compressedArray[inOffset + 53] & 0xFF)));
      final short i27 = (short) (((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF)));
      final short i28 = (short) (((compressedArray[inOffset + 56] & 0xFF) << 8) | ((compressedArray[inOffset + 57] & 0xFF)));
      final short i29 = (short) (((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF)));
      final short i30 = (short) (((compressedArray[inOffset + 60] & 0xFF) << 8) | ((compressedArray[inOffset + 61] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 15) | (i1 >>> 1 & 32767)) & 2147483647;
      unCompressedData[outOffset + 1] = ((i1 << 30) | (i2 << 14 & 1073741823) | (i3 >>> 2 & 16383)) & 2147483647;
      unCompressedData[outOffset + 2] = ((i3 << 29) | (i4 << 13 & 536870911) | (i5 >>> 3 & 8191)) & 2147483647;
      unCompressedData[outOffset + 3] = ((i5 << 28) | (i6 << 12 & 268435455) | (i7 >>> 4 & 4095)) & 2147483647;
      unCompressedData[outOffset + 4] = ((i7 << 27) | (i8 << 11 & 134217727) | (i9 >>> 5 & 2047)) & 2147483647;
      unCompressedData[outOffset + 5] = ((i9 << 26) | (i10 << 10 & 67108863) | (i11 >>> 6 & 1023)) & 2147483647;
      unCompressedData[outOffset + 6] = ((i11 << 25) | (i12 << 9 & 33554431) | (i13 >>> 7 & 511)) & 2147483647;
      unCompressedData[outOffset + 7] = ((i13 << 24) | (i14 << 8 & 16777215) | (i15 >>> 8 & 255)) & 2147483647;
      unCompressedData[outOffset + 8] = ((i15 << 23) | (i16 << 7 & 8388607) | (i17 >>> 9 & 127)) & 2147483647;
      unCompressedData[outOffset + 9] = ((i17 << 22) | (i18 << 6 & 4194303) | (i19 >>> 10 & 63)) & 2147483647;
      unCompressedData[outOffset + 10] = ((i19 << 21) | (i20 << 5 & 2097151) | (i21 >>> 11 & 31)) & 2147483647;
      unCompressedData[outOffset + 11] = ((i21 << 20) | (i22 << 4 & 1048575) | (i23 >>> 12 & 15)) & 2147483647;
      unCompressedData[outOffset + 12] = ((i23 << 19) | (i24 << 3 & 524287) | (i25 >>> 13 & 7)) & 2147483647;
      unCompressedData[outOffset + 13] = ((i25 << 18) | (i26 << 2 & 262143) | (i27 >>> 14 & 3)) & 2147483647;
      unCompressedData[outOffset + 14] = ((i27 << 17) | (i28 << 1 & 131071) | (i29 >>> 15 & 1)) & 2147483647;
      unCompressedData[outOffset + 15] = ((i29 << 16) | (i30 >>> 0 & 65535)) & 2147483647;
      input.offset += 63;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor65 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final short i0 = (short) (((compressedArray[inOffset + 0] & 0xFF) << 8) | ((compressedArray[inOffset + 1] & 0xFF)));
      final short i1 = (short) (((compressedArray[inOffset + 2] & 0xFF) << 8) | ((compressedArray[inOffset + 3] & 0xFF)));
      final short i2 = (short) (((compressedArray[inOffset + 4] & 0xFF) << 8) | ((compressedArray[inOffset + 5] & 0xFF)));
      final short i3 = (short) (((compressedArray[inOffset + 6] & 0xFF) << 8) | ((compressedArray[inOffset + 7] & 0xFF)));
      final short i4 = (short) (((compressedArray[inOffset + 8] & 0xFF) << 8) | ((compressedArray[inOffset + 9] & 0xFF)));
      final short i5 = (short) (((compressedArray[inOffset + 10] & 0xFF) << 8) | ((compressedArray[inOffset + 11] & 0xFF)));
      final short i6 = (short) (((compressedArray[inOffset + 12] & 0xFF) << 8) | ((compressedArray[inOffset + 13] & 0xFF)));
      final short i7 = (short) (((compressedArray[inOffset + 14] & 0xFF) << 8) | ((compressedArray[inOffset + 15] & 0xFF)));
      final short i8 = (short) (((compressedArray[inOffset + 16] & 0xFF) << 8) | ((compressedArray[inOffset + 17] & 0xFF)));
      final short i9 = (short) (((compressedArray[inOffset + 18] & 0xFF) << 8) | ((compressedArray[inOffset + 19] & 0xFF)));
      final short i10 = (short) (((compressedArray[inOffset + 20] & 0xFF) << 8) | ((compressedArray[inOffset + 21] & 0xFF)));
      final short i11 = (short) (((compressedArray[inOffset + 22] & 0xFF) << 8) | ((compressedArray[inOffset + 23] & 0xFF)));
      final short i12 = (short) (((compressedArray[inOffset + 24] & 0xFF) << 8) | ((compressedArray[inOffset + 25] & 0xFF)));
      final short i13 = (short) (((compressedArray[inOffset + 26] & 0xFF) << 8) | ((compressedArray[inOffset + 27] & 0xFF)));
      final short i14 = (short) (((compressedArray[inOffset + 28] & 0xFF) << 8) | ((compressedArray[inOffset + 29] & 0xFF)));
      final short i15 = (short) (((compressedArray[inOffset + 30] & 0xFF) << 8) | ((compressedArray[inOffset + 31] & 0xFF)));
      final short i16 = (short) (((compressedArray[inOffset + 32] & 0xFF) << 8) | ((compressedArray[inOffset + 33] & 0xFF)));
      final short i17 = (short) (((compressedArray[inOffset + 34] & 0xFF) << 8) | ((compressedArray[inOffset + 35] & 0xFF)));
      final short i18 = (short) (((compressedArray[inOffset + 36] & 0xFF) << 8) | ((compressedArray[inOffset + 37] & 0xFF)));
      final short i19 = (short) (((compressedArray[inOffset + 38] & 0xFF) << 8) | ((compressedArray[inOffset + 39] & 0xFF)));
      final short i20 = (short) (((compressedArray[inOffset + 40] & 0xFF) << 8) | ((compressedArray[inOffset + 41] & 0xFF)));
      final short i21 = (short) (((compressedArray[inOffset + 42] & 0xFF) << 8) | ((compressedArray[inOffset + 43] & 0xFF)));
      final short i22 = (short) (((compressedArray[inOffset + 44] & 0xFF) << 8) | ((compressedArray[inOffset + 45] & 0xFF)));
      final short i23 = (short) (((compressedArray[inOffset + 46] & 0xFF) << 8) | ((compressedArray[inOffset + 47] & 0xFF)));
      final short i24 = (short) (((compressedArray[inOffset + 48] & 0xFF) << 8) | ((compressedArray[inOffset + 49] & 0xFF)));
      final short i25 = (short) (((compressedArray[inOffset + 50] & 0xFF) << 8) | ((compressedArray[inOffset + 51] & 0xFF)));
      final short i26 = (short) (((compressedArray[inOffset + 52] & 0xFF) << 8) | ((compressedArray[inOffset + 53] & 0xFF)));
      final short i27 = (short) (((compressedArray[inOffset + 54] & 0xFF) << 8) | ((compressedArray[inOffset + 55] & 0xFF)));
      final short i28 = (short) (((compressedArray[inOffset + 56] & 0xFF) << 8) | ((compressedArray[inOffset + 57] & 0xFF)));
      final short i29 = (short) (((compressedArray[inOffset + 58] & 0xFF) << 8) | ((compressedArray[inOffset + 59] & 0xFF)));
      final short i30 = (short) (((compressedArray[inOffset + 60] & 0xFF) << 8) | ((compressedArray[inOffset + 61] & 0xFF)));
      final short i31 = (short) (((compressedArray[inOffset + 62] & 0xFF) << 8) | ((compressedArray[inOffset + 63] & 0xFF)));

      unCompressedData[outOffset + 0] = ((i0 << 16) | (i1 >>> 0 & 65535));
      unCompressedData[outOffset + 1] = ((i2 << 16) | (i3 >>> 0 & 65535));
      unCompressedData[outOffset + 2] = ((i4 << 16) | (i5 >>> 0 & 65535));
      unCompressedData[outOffset + 3] = ((i6 << 16) | (i7 >>> 0 & 65535));
      unCompressedData[outOffset + 4] = ((i8 << 16) | (i9 >>> 0 & 65535));
      unCompressedData[outOffset + 5] = ((i10 << 16) | (i11 >>> 0 & 65535));
      unCompressedData[outOffset + 6] = ((i12 << 16) | (i13 >>> 0 & 65535));
      unCompressedData[outOffset + 7] = ((i14 << 16) | (i15 >>> 0 & 65535));
      unCompressedData[outOffset + 8] = ((i16 << 16) | (i17 >>> 0 & 65535));
      unCompressedData[outOffset + 9] = ((i18 << 16) | (i19 >>> 0 & 65535));
      unCompressedData[outOffset + 10] = ((i20 << 16) | (i21 >>> 0 & 65535));
      unCompressedData[outOffset + 11] = ((i22 << 16) | (i23 >>> 0 & 65535));
      unCompressedData[outOffset + 12] = ((i24 << 16) | (i25 >>> 0 & 65535));
      unCompressedData[outOffset + 13] = ((i26 << 16) | (i27 >>> 0 & 65535));
      unCompressedData[outOffset + 14] = ((i28 << 16) | (i29 >>> 0 & 65535));
      unCompressedData[outOffset + 15] = ((i30 << 16) | (i31 >>> 0 & 65535));
      input.offset += 65;
      output.offset += 16;
    }
  }

  static final class FrameDecompressor66 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final int outOffset = output.offset;
      unCompressedData[outOffset] = 0;
      unCompressedData[outOffset + 1] = 0;
      unCompressedData[outOffset + 2] = 0;
      unCompressedData[outOffset + 3] = 0;
      unCompressedData[outOffset + 4] = 0;
      unCompressedData[outOffset + 5] = 0;
      unCompressedData[outOffset + 6] = 0;
      unCompressedData[outOffset + 7] = 0;
      output.offset += 8;
      input.offset += 1;
  }
  }

  static final class FrameDecompressor67 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];

      unCompressedData[outOffset + 0] = (i0 >>> 7) & 1;
      unCompressedData[outOffset + 1] = (i0 >>> 6) & 1;
      unCompressedData[outOffset + 2] = (i0 >>> 5) & 1;
      unCompressedData[outOffset + 3] = (i0 >>> 4) & 1;
      unCompressedData[outOffset + 4] = (i0 >>> 3) & 1;
      unCompressedData[outOffset + 5] = (i0 >>> 2) & 1;
      unCompressedData[outOffset + 6] = (i0 >>> 1) & 1;
      unCompressedData[outOffset + 7] = i0 & 1;
      input.offset += 2;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor68 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];

      unCompressedData[outOffset + 0] = (i0 >>> 6) & 3;
      unCompressedData[outOffset + 1] = (i0 >>> 4) & 3;
      unCompressedData[outOffset + 2] = (i0 >>> 2) & 3;
      unCompressedData[outOffset + 3] = i0 & 3;
      unCompressedData[outOffset + 4] = (i1 >>> 6) & 3;
      unCompressedData[outOffset + 5] = (i1 >>> 4) & 3;
      unCompressedData[outOffset + 6] = (i1 >>> 2) & 3;
      unCompressedData[outOffset + 7] = i1 & 3;
      input.offset += 3;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor69 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];

      unCompressedData[outOffset + 0] = (i0 >>> 5) & 7;
      unCompressedData[outOffset + 1] = (i0 >>> 2) & 7;
      unCompressedData[outOffset + 2] = ((i0 << 1) | (i1 >>> 7 & 1)) & 7;
      unCompressedData[outOffset + 3] = (i1 >>> 4) & 7;
      unCompressedData[outOffset + 4] = (i1 >>> 1) & 7;
      unCompressedData[outOffset + 5] = ((i1 << 2) | (i2 >>> 6 & 3)) & 7;
      unCompressedData[outOffset + 6] = (i2 >>> 3) & 7;
      unCompressedData[outOffset + 7] = i2 & 7;
      input.offset += 4;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor70 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];

      unCompressedData[outOffset + 0] = (i0 >>> 4) & 15;
      unCompressedData[outOffset + 1] = i0 & 15;
      unCompressedData[outOffset + 2] = (i1 >>> 4) & 15;
      unCompressedData[outOffset + 3] = i1 & 15;
      unCompressedData[outOffset + 4] = (i2 >>> 4) & 15;
      unCompressedData[outOffset + 5] = i2 & 15;
      unCompressedData[outOffset + 6] = (i3 >>> 4) & 15;
      unCompressedData[outOffset + 7] = i3 & 15;
      input.offset += 5;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor71 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];

      unCompressedData[outOffset + 0] = (i0 >>> 3) & 31;
      unCompressedData[outOffset + 1] = ((i0 << 2) | (i1 >>> 6 & 3)) & 31;
      unCompressedData[outOffset + 2] = (i1 >>> 1) & 31;
      unCompressedData[outOffset + 3] = ((i1 << 4) | (i2 >>> 4 & 15)) & 31;
      unCompressedData[outOffset + 4] = ((i2 << 1) | (i3 >>> 7 & 1)) & 31;
      unCompressedData[outOffset + 5] = (i3 >>> 2) & 31;
      unCompressedData[outOffset + 6] = ((i3 << 3) | (i4 >>> 5 & 7)) & 31;
      unCompressedData[outOffset + 7] = i4 & 31;
      input.offset += 6;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor72 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];

      unCompressedData[outOffset + 0] = (i0 >>> 2) & 63;
      unCompressedData[outOffset + 1] = ((i0 << 4) | (i1 >>> 4 & 15)) & 63;
      unCompressedData[outOffset + 2] = ((i1 << 2) | (i2 >>> 6 & 3)) & 63;
      unCompressedData[outOffset + 3] = i2 & 63;
      unCompressedData[outOffset + 4] = (i3 >>> 2) & 63;
      unCompressedData[outOffset + 5] = ((i3 << 4) | (i4 >>> 4 & 15)) & 63;
      unCompressedData[outOffset + 6] = ((i4 << 2) | (i5 >>> 6 & 3)) & 63;
      unCompressedData[outOffset + 7] = i5 & 63;
      input.offset += 7;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor73 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];

      unCompressedData[outOffset + 0] = (i0 >>> 1) & 127;
      unCompressedData[outOffset + 1] = ((i0 << 6) | (i1 >>> 2 & 63)) & 127;
      unCompressedData[outOffset + 2] = ((i1 << 5) | (i2 >>> 3 & 31)) & 127;
      unCompressedData[outOffset + 3] = ((i2 << 4) | (i3 >>> 4 & 15)) & 127;
      unCompressedData[outOffset + 4] = ((i3 << 3) | (i4 >>> 5 & 7)) & 127;
      unCompressedData[outOffset + 5] = ((i4 << 2) | (i5 >>> 6 & 3)) & 127;
      unCompressedData[outOffset + 6] = ((i5 << 1) | (i6 >>> 7 & 1)) & 127;
      unCompressedData[outOffset + 7] = i6 & 127;
      input.offset += 8;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor74 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];

      unCompressedData[outOffset + 0] = i0 & 255;
      unCompressedData[outOffset + 1] = i1 & 255;
      unCompressedData[outOffset + 2] = i2 & 255;
      unCompressedData[outOffset + 3] = i3 & 255;
      unCompressedData[outOffset + 4] = i4 & 255;
      unCompressedData[outOffset + 5] = i5 & 255;
      unCompressedData[outOffset + 6] = i6 & 255;
      unCompressedData[outOffset + 7] = i7 & 255;
      input.offset += 9;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor75 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];

      unCompressedData[outOffset + 0] = ((i0 << 1) | (i1 >>> 7 & 1)) & 511;
      unCompressedData[outOffset + 1] = ((i1 << 2) | (i2 >>> 6 & 3)) & 511;
      unCompressedData[outOffset + 2] = ((i2 << 3) | (i3 >>> 5 & 7)) & 511;
      unCompressedData[outOffset + 3] = ((i3 << 4) | (i4 >>> 4 & 15)) & 511;
      unCompressedData[outOffset + 4] = ((i4 << 5) | (i5 >>> 3 & 31)) & 511;
      unCompressedData[outOffset + 5] = ((i5 << 6) | (i6 >>> 2 & 63)) & 511;
      unCompressedData[outOffset + 6] = ((i6 << 7) | (i7 >>> 1 & 127)) & 511;
      unCompressedData[outOffset + 7] = ((i7 << 8) | (i8 >>> 0 & 255)) & 511;
      input.offset += 10;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor76 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];

      unCompressedData[outOffset + 0] = ((i0 << 2) | (i1 >>> 6 & 3)) & 1023;
      unCompressedData[outOffset + 1] = ((i1 << 4) | (i2 >>> 4 & 15)) & 1023;
      unCompressedData[outOffset + 2] = ((i2 << 6) | (i3 >>> 2 & 63)) & 1023;
      unCompressedData[outOffset + 3] = ((i3 << 8) | (i4 >>> 0 & 255)) & 1023;
      unCompressedData[outOffset + 4] = ((i5 << 2) | (i6 >>> 6 & 3)) & 1023;
      unCompressedData[outOffset + 5] = ((i6 << 4) | (i7 >>> 4 & 15)) & 1023;
      unCompressedData[outOffset + 6] = ((i7 << 6) | (i8 >>> 2 & 63)) & 1023;
      unCompressedData[outOffset + 7] = ((i8 << 8) | (i9 >>> 0 & 255)) & 1023;
      input.offset += 11;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor77 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];

      unCompressedData[outOffset + 0] = ((i0 << 3) | (i1 >>> 5 & 7)) & 2047;
      unCompressedData[outOffset + 1] = ((i1 << 6) | (i2 >>> 2 & 63)) & 2047;
      unCompressedData[outOffset + 2] = ((i2 << 9) | (i3 << 1 & 511) | (i4 >>> 7 & 1)) & 2047;
      unCompressedData[outOffset + 3] = ((i4 << 4) | (i5 >>> 4 & 15)) & 2047;
      unCompressedData[outOffset + 4] = ((i5 << 7) | (i6 >>> 1 & 127)) & 2047;
      unCompressedData[outOffset + 5] = ((i6 << 10) | (i7 << 2 & 1023) | (i8 >>> 6 & 3)) & 2047;
      unCompressedData[outOffset + 6] = ((i8 << 5) | (i9 >>> 3 & 31)) & 2047;
      unCompressedData[outOffset + 7] = ((i9 << 8) | (i10 >>> 0 & 255)) & 2047;
      input.offset += 12;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor78 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];

      unCompressedData[outOffset + 0] = ((i0 << 4) | (i1 >>> 4 & 15)) & 4095;
      unCompressedData[outOffset + 1] = ((i1 << 8) | (i2 >>> 0 & 255)) & 4095;
      unCompressedData[outOffset + 2] = ((i3 << 4) | (i4 >>> 4 & 15)) & 4095;
      unCompressedData[outOffset + 3] = ((i4 << 8) | (i5 >>> 0 & 255)) & 4095;
      unCompressedData[outOffset + 4] = ((i6 << 4) | (i7 >>> 4 & 15)) & 4095;
      unCompressedData[outOffset + 5] = ((i7 << 8) | (i8 >>> 0 & 255)) & 4095;
      unCompressedData[outOffset + 6] = ((i9 << 4) | (i10 >>> 4 & 15)) & 4095;
      unCompressedData[outOffset + 7] = ((i10 << 8) | (i11 >>> 0 & 255)) & 4095;
      input.offset += 13;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor79 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];

      unCompressedData[outOffset + 0] = ((i0 << 5) | (i1 >>> 3 & 31)) & 8191;
      unCompressedData[outOffset + 1] = ((i1 << 10) | (i2 << 2 & 1023) | (i3 >>> 6 & 3)) & 8191;
      unCompressedData[outOffset + 2] = ((i3 << 7) | (i4 >>> 1 & 127)) & 8191;
      unCompressedData[outOffset + 3] = ((i4 << 12) | (i5 << 4 & 4095) | (i6 >>> 4 & 15)) & 8191;
      unCompressedData[outOffset + 4] = ((i6 << 9) | (i7 << 1 & 511) | (i8 >>> 7 & 1)) & 8191;
      unCompressedData[outOffset + 5] = ((i8 << 6) | (i9 >>> 2 & 63)) & 8191;
      unCompressedData[outOffset + 6] = ((i9 << 11) | (i10 << 3 & 2047) | (i11 >>> 5 & 7)) & 8191;
      unCompressedData[outOffset + 7] = ((i11 << 8) | (i12 >>> 0 & 255)) & 8191;
      input.offset += 14;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor80 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];

      unCompressedData[outOffset + 0] = ((i0 << 6) | (i1 >>> 2 & 63)) & 16383;
      unCompressedData[outOffset + 1] = ((i1 << 12) | (i2 << 4 & 4095) | (i3 >>> 4 & 15)) & 16383;
      unCompressedData[outOffset + 2] = ((i3 << 10) | (i4 << 2 & 1023) | (i5 >>> 6 & 3)) & 16383;
      unCompressedData[outOffset + 3] = ((i5 << 8) | (i6 >>> 0 & 255)) & 16383;
      unCompressedData[outOffset + 4] = ((i7 << 6) | (i8 >>> 2 & 63)) & 16383;
      unCompressedData[outOffset + 5] = ((i8 << 12) | (i9 << 4 & 4095) | (i10 >>> 4 & 15)) & 16383;
      unCompressedData[outOffset + 6] = ((i10 << 10) | (i11 << 2 & 1023) | (i12 >>> 6 & 3)) & 16383;
      unCompressedData[outOffset + 7] = ((i12 << 8) | (i13 >>> 0 & 255)) & 16383;
      input.offset += 15;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor81 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];

      unCompressedData[outOffset + 0] = ((i0 << 7) | (i1 >>> 1 & 127)) & 32767;
      unCompressedData[outOffset + 1] = ((i1 << 14) | (i2 << 6 & 16383) | (i3 >>> 2 & 63)) & 32767;
      unCompressedData[outOffset + 2] = ((i3 << 13) | (i4 << 5 & 8191) | (i5 >>> 3 & 31)) & 32767;
      unCompressedData[outOffset + 3] = ((i5 << 12) | (i6 << 4 & 4095) | (i7 >>> 4 & 15)) & 32767;
      unCompressedData[outOffset + 4] = ((i7 << 11) | (i8 << 3 & 2047) | (i9 >>> 5 & 7)) & 32767;
      unCompressedData[outOffset + 5] = ((i9 << 10) | (i10 << 2 & 1023) | (i11 >>> 6 & 3)) & 32767;
      unCompressedData[outOffset + 6] = ((i11 << 9) | (i12 << 1 & 511) | (i13 >>> 7 & 1)) & 32767;
      unCompressedData[outOffset + 7] = ((i13 << 8) | (i14 >>> 0 & 255)) & 32767;
      input.offset += 16;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor82 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];

      unCompressedData[outOffset + 0] = ((i0 << 8) | (i1 >>> 0 & 255)) & 65535;
      unCompressedData[outOffset + 1] = ((i2 << 8) | (i3 >>> 0 & 255)) & 65535;
      unCompressedData[outOffset + 2] = ((i4 << 8) | (i5 >>> 0 & 255)) & 65535;
      unCompressedData[outOffset + 3] = ((i6 << 8) | (i7 >>> 0 & 255)) & 65535;
      unCompressedData[outOffset + 4] = ((i8 << 8) | (i9 >>> 0 & 255)) & 65535;
      unCompressedData[outOffset + 5] = ((i10 << 8) | (i11 >>> 0 & 255)) & 65535;
      unCompressedData[outOffset + 6] = ((i12 << 8) | (i13 >>> 0 & 255)) & 65535;
      unCompressedData[outOffset + 7] = ((i14 << 8) | (i15 >>> 0 & 255)) & 65535;
      input.offset += 17;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor83 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];

      unCompressedData[outOffset + 0] = ((i0 << 9) | (i1 << 1 & 511) | (i2 >>> 7 & 1)) & 131071;
      unCompressedData[outOffset + 1] = ((i2 << 10) | (i3 << 2 & 1023) | (i4 >>> 6 & 3)) & 131071;
      unCompressedData[outOffset + 2] = ((i4 << 11) | (i5 << 3 & 2047) | (i6 >>> 5 & 7)) & 131071;
      unCompressedData[outOffset + 3] = ((i6 << 12) | (i7 << 4 & 4095) | (i8 >>> 4 & 15)) & 131071;
      unCompressedData[outOffset + 4] = ((i8 << 13) | (i9 << 5 & 8191) | (i10 >>> 3 & 31)) & 131071;
      unCompressedData[outOffset + 5] = ((i10 << 14) | (i11 << 6 & 16383) | (i12 >>> 2 & 63)) & 131071;
      unCompressedData[outOffset + 6] = ((i12 << 15) | (i13 << 7 & 32767) | (i14 >>> 1 & 127)) & 131071;
      unCompressedData[outOffset + 7] = ((i14 << 16) | (i15 << 8 & 65535) | (i16 >>> 0 & 255)) & 131071;
      input.offset += 18;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor84 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];

      unCompressedData[outOffset + 0] = ((i0 << 10) | (i1 << 2 & 1023) | (i2 >>> 6 & 3)) & 262143;
      unCompressedData[outOffset + 1] = ((i2 << 12) | (i3 << 4 & 4095) | (i4 >>> 4 & 15)) & 262143;
      unCompressedData[outOffset + 2] = ((i4 << 14) | (i5 << 6 & 16383) | (i6 >>> 2 & 63)) & 262143;
      unCompressedData[outOffset + 3] = ((i6 << 16) | (i7 << 8 & 65535) | (i8 >>> 0 & 255)) & 262143;
      unCompressedData[outOffset + 4] = ((i9 << 10) | (i10 << 2 & 1023) | (i11 >>> 6 & 3)) & 262143;
      unCompressedData[outOffset + 5] = ((i11 << 12) | (i12 << 4 & 4095) | (i13 >>> 4 & 15)) & 262143;
      unCompressedData[outOffset + 6] = ((i13 << 14) | (i14 << 6 & 16383) | (i15 >>> 2 & 63)) & 262143;
      unCompressedData[outOffset + 7] = ((i15 << 16) | (i16 << 8 & 65535) | (i17 >>> 0 & 255)) & 262143;
      input.offset += 19;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor85 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];

      unCompressedData[outOffset + 0] = ((i0 << 11) | (i1 << 3 & 2047) | (i2 >>> 5 & 7)) & 524287;
      unCompressedData[outOffset + 1] = ((i2 << 14) | (i3 << 6 & 16383) | (i4 >>> 2 & 63)) & 524287;
      unCompressedData[outOffset + 2] = ((i4 << 17) | (i5 << 9 & 131071) | (i6 << 1 & 511) | (i7 >>> 7 & 1)) & 524287;
      unCompressedData[outOffset + 3] = ((i7 << 12) | (i8 << 4 & 4095) | (i9 >>> 4 & 15)) & 524287;
      unCompressedData[outOffset + 4] = ((i9 << 15) | (i10 << 7 & 32767) | (i11 >>> 1 & 127)) & 524287;
      unCompressedData[outOffset + 5] = ((i11 << 18) | (i12 << 10 & 262143) | (i13 << 2 & 1023) | (i14 >>> 6 & 3)) & 524287;
      unCompressedData[outOffset + 6] = ((i14 << 13) | (i15 << 5 & 8191) | (i16 >>> 3 & 31)) & 524287;
      unCompressedData[outOffset + 7] = ((i16 << 16) | (i17 << 8 & 65535) | (i18 >>> 0 & 255)) & 524287;
      input.offset += 20;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor86 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];

      unCompressedData[outOffset + 0] = ((i0 << 12) | (i1 << 4 & 4095) | (i2 >>> 4 & 15)) & 1048575;
      unCompressedData[outOffset + 1] = ((i2 << 16) | (i3 << 8 & 65535) | (i4 >>> 0 & 255)) & 1048575;
      unCompressedData[outOffset + 2] = ((i5 << 12) | (i6 << 4 & 4095) | (i7 >>> 4 & 15)) & 1048575;
      unCompressedData[outOffset + 3] = ((i7 << 16) | (i8 << 8 & 65535) | (i9 >>> 0 & 255)) & 1048575;
      unCompressedData[outOffset + 4] = ((i10 << 12) | (i11 << 4 & 4095) | (i12 >>> 4 & 15)) & 1048575;
      unCompressedData[outOffset + 5] = ((i12 << 16) | (i13 << 8 & 65535) | (i14 >>> 0 & 255)) & 1048575;
      unCompressedData[outOffset + 6] = ((i15 << 12) | (i16 << 4 & 4095) | (i17 >>> 4 & 15)) & 1048575;
      unCompressedData[outOffset + 7] = ((i17 << 16) | (i18 << 8 & 65535) | (i19 >>> 0 & 255)) & 1048575;
      input.offset += 21;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor87 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];

      unCompressedData[outOffset + 0] = ((i0 << 13) | (i1 << 5 & 8191) | (i2 >>> 3 & 31)) & 2097151;
      unCompressedData[outOffset + 1] = ((i2 << 18) | (i3 << 10 & 262143) | (i4 << 2 & 1023) | (i5 >>> 6 & 3)) & 2097151;
      unCompressedData[outOffset + 2] = ((i5 << 15) | (i6 << 7 & 32767) | (i7 >>> 1 & 127)) & 2097151;
      unCompressedData[outOffset + 3] = ((i7 << 20) | (i8 << 12 & 1048575) | (i9 << 4 & 4095) | (i10 >>> 4 & 15)) & 2097151;
      unCompressedData[outOffset + 4] = ((i10 << 17) | (i11 << 9 & 131071) | (i12 << 1 & 511) | (i13 >>> 7 & 1)) & 2097151;
      unCompressedData[outOffset + 5] = ((i13 << 14) | (i14 << 6 & 16383) | (i15 >>> 2 & 63)) & 2097151;
      unCompressedData[outOffset + 6] = ((i15 << 19) | (i16 << 11 & 524287) | (i17 << 3 & 2047) | (i18 >>> 5 & 7)) & 2097151;
      unCompressedData[outOffset + 7] = ((i18 << 16) | (i19 << 8 & 65535) | (i20 >>> 0 & 255)) & 2097151;
      input.offset += 22;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor88 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];

      unCompressedData[outOffset + 0] = ((i0 << 14) | (i1 << 6 & 16383) | (i2 >>> 2 & 63)) & 4194303;
      unCompressedData[outOffset + 1] = ((i2 << 20) | (i3 << 12 & 1048575) | (i4 << 4 & 4095) | (i5 >>> 4 & 15)) & 4194303;
      unCompressedData[outOffset + 2] = ((i5 << 18) | (i6 << 10 & 262143) | (i7 << 2 & 1023) | (i8 >>> 6 & 3)) & 4194303;
      unCompressedData[outOffset + 3] = ((i8 << 16) | (i9 << 8 & 65535) | (i10 >>> 0 & 255)) & 4194303;
      unCompressedData[outOffset + 4] = ((i11 << 14) | (i12 << 6 & 16383) | (i13 >>> 2 & 63)) & 4194303;
      unCompressedData[outOffset + 5] = ((i13 << 20) | (i14 << 12 & 1048575) | (i15 << 4 & 4095) | (i16 >>> 4 & 15)) & 4194303;
      unCompressedData[outOffset + 6] = ((i16 << 18) | (i17 << 10 & 262143) | (i18 << 2 & 1023) | (i19 >>> 6 & 3)) & 4194303;
      unCompressedData[outOffset + 7] = ((i19 << 16) | (i20 << 8 & 65535) | (i21 >>> 0 & 255)) & 4194303;
      input.offset += 23;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor89 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];

      unCompressedData[outOffset + 0] = ((i0 << 15) | (i1 << 7 & 32767) | (i2 >>> 1 & 127)) & 8388607;
      unCompressedData[outOffset + 1] = ((i2 << 22) | (i3 << 14 & 4194303) | (i4 << 6 & 16383) | (i5 >>> 2 & 63)) & 8388607;
      unCompressedData[outOffset + 2] = ((i5 << 21) | (i6 << 13 & 2097151) | (i7 << 5 & 8191) | (i8 >>> 3 & 31)) & 8388607;
      unCompressedData[outOffset + 3] = ((i8 << 20) | (i9 << 12 & 1048575) | (i10 << 4 & 4095) | (i11 >>> 4 & 15)) & 8388607;
      unCompressedData[outOffset + 4] = ((i11 << 19) | (i12 << 11 & 524287) | (i13 << 3 & 2047) | (i14 >>> 5 & 7)) & 8388607;
      unCompressedData[outOffset + 5] = ((i14 << 18) | (i15 << 10 & 262143) | (i16 << 2 & 1023) | (i17 >>> 6 & 3)) & 8388607;
      unCompressedData[outOffset + 6] = ((i17 << 17) | (i18 << 9 & 131071) | (i19 << 1 & 511) | (i20 >>> 7 & 1)) & 8388607;
      unCompressedData[outOffset + 7] = ((i20 << 16) | (i21 << 8 & 65535) | (i22 >>> 0 & 255)) & 8388607;
      input.offset += 24;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor90 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];
      final byte i23 = compressedArray[inOffset + 23];

      unCompressedData[outOffset + 0] = ((i0 << 16) | (i1 << 8 & 65535) | (i2 >>> 0 & 255)) & 16777215;
      unCompressedData[outOffset + 1] = ((i3 << 16) | (i4 << 8 & 65535) | (i5 >>> 0 & 255)) & 16777215;
      unCompressedData[outOffset + 2] = ((i6 << 16) | (i7 << 8 & 65535) | (i8 >>> 0 & 255)) & 16777215;
      unCompressedData[outOffset + 3] = ((i9 << 16) | (i10 << 8 & 65535) | (i11 >>> 0 & 255)) & 16777215;
      unCompressedData[outOffset + 4] = ((i12 << 16) | (i13 << 8 & 65535) | (i14 >>> 0 & 255)) & 16777215;
      unCompressedData[outOffset + 5] = ((i15 << 16) | (i16 << 8 & 65535) | (i17 >>> 0 & 255)) & 16777215;
      unCompressedData[outOffset + 6] = ((i18 << 16) | (i19 << 8 & 65535) | (i20 >>> 0 & 255)) & 16777215;
      unCompressedData[outOffset + 7] = ((i21 << 16) | (i22 << 8 & 65535) | (i23 >>> 0 & 255)) & 16777215;
      input.offset += 25;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor91 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];
      final byte i23 = compressedArray[inOffset + 23];
      final byte i24 = compressedArray[inOffset + 24];

      unCompressedData[outOffset + 0] = ((i0 << 17) | (i1 << 9 & 131071) | (i2 << 1 & 511) | (i3 >>> 7 & 1)) & 33554431;
      unCompressedData[outOffset + 1] = ((i3 << 18) | (i4 << 10 & 262143) | (i5 << 2 & 1023) | (i6 >>> 6 & 3)) & 33554431;
      unCompressedData[outOffset + 2] = ((i6 << 19) | (i7 << 11 & 524287) | (i8 << 3 & 2047) | (i9 >>> 5 & 7)) & 33554431;
      unCompressedData[outOffset + 3] = ((i9 << 20) | (i10 << 12 & 1048575) | (i11 << 4 & 4095) | (i12 >>> 4 & 15)) & 33554431;
      unCompressedData[outOffset + 4] = ((i12 << 21) | (i13 << 13 & 2097151) | (i14 << 5 & 8191) | (i15 >>> 3 & 31)) & 33554431;
      unCompressedData[outOffset + 5] = ((i15 << 22) | (i16 << 14 & 4194303) | (i17 << 6 & 16383) | (i18 >>> 2 & 63)) & 33554431;
      unCompressedData[outOffset + 6] = ((i18 << 23) | (i19 << 15 & 8388607) | (i20 << 7 & 32767) | (i21 >>> 1 & 127)) & 33554431;
      unCompressedData[outOffset + 7] = ((i21 << 24) | (i22 << 16 & 16777215) | (i23 << 8 & 65535) | (i24 >>> 0 & 255)) & 33554431;
      input.offset += 26;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor92 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];
      final byte i23 = compressedArray[inOffset + 23];
      final byte i24 = compressedArray[inOffset + 24];
      final byte i25 = compressedArray[inOffset + 25];

      unCompressedData[outOffset + 0] = ((i0 << 18) | (i1 << 10 & 262143) | (i2 << 2 & 1023) | (i3 >>> 6 & 3)) & 67108863;
      unCompressedData[outOffset + 1] = ((i3 << 20) | (i4 << 12 & 1048575) | (i5 << 4 & 4095) | (i6 >>> 4 & 15)) & 67108863;
      unCompressedData[outOffset + 2] = ((i6 << 22) | (i7 << 14 & 4194303) | (i8 << 6 & 16383) | (i9 >>> 2 & 63)) & 67108863;
      unCompressedData[outOffset + 3] = ((i9 << 24) | (i10 << 16 & 16777215) | (i11 << 8 & 65535) | (i12 >>> 0 & 255)) & 67108863;
      unCompressedData[outOffset + 4] = ((i13 << 18) | (i14 << 10 & 262143) | (i15 << 2 & 1023) | (i16 >>> 6 & 3)) & 67108863;
      unCompressedData[outOffset + 5] = ((i16 << 20) | (i17 << 12 & 1048575) | (i18 << 4 & 4095) | (i19 >>> 4 & 15)) & 67108863;
      unCompressedData[outOffset + 6] = ((i19 << 22) | (i20 << 14 & 4194303) | (i21 << 6 & 16383) | (i22 >>> 2 & 63)) & 67108863;
      unCompressedData[outOffset + 7] = ((i22 << 24) | (i23 << 16 & 16777215) | (i24 << 8 & 65535) | (i25 >>> 0 & 255)) & 67108863;
      input.offset += 27;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor93 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];
      final byte i23 = compressedArray[inOffset + 23];
      final byte i24 = compressedArray[inOffset + 24];
      final byte i25 = compressedArray[inOffset + 25];
      final byte i26 = compressedArray[inOffset + 26];

      unCompressedData[outOffset + 0] = ((i0 << 19) | (i1 << 11 & 524287) | (i2 << 3 & 2047) | (i3 >>> 5 & 7)) & 134217727;
      unCompressedData[outOffset + 1] = ((i3 << 22) | (i4 << 14 & 4194303) | (i5 << 6 & 16383) | (i6 >>> 2 & 63)) & 134217727;
      unCompressedData[outOffset + 2] = ((i6 << 25) | (i7 << 17 & 33554431) | (i8 << 9 & 131071) | (i9 << 1 & 511) | (i10 >>> 7 & 1)) & 134217727;
      unCompressedData[outOffset + 3] = ((i10 << 20) | (i11 << 12 & 1048575) | (i12 << 4 & 4095) | (i13 >>> 4 & 15)) & 134217727;
      unCompressedData[outOffset + 4] = ((i13 << 23) | (i14 << 15 & 8388607) | (i15 << 7 & 32767) | (i16 >>> 1 & 127)) & 134217727;
      unCompressedData[outOffset + 5] = ((i16 << 26) | (i17 << 18 & 67108863) | (i18 << 10 & 262143) | (i19 << 2 & 1023) | (i20 >>> 6 & 3)) & 134217727;
      unCompressedData[outOffset + 6] = ((i20 << 21) | (i21 << 13 & 2097151) | (i22 << 5 & 8191) | (i23 >>> 3 & 31)) & 134217727;
      unCompressedData[outOffset + 7] = ((i23 << 24) | (i24 << 16 & 16777215) | (i25 << 8 & 65535) | (i26 >>> 0 & 255)) & 134217727;
      input.offset += 28;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor94 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];
      final byte i23 = compressedArray[inOffset + 23];
      final byte i24 = compressedArray[inOffset + 24];
      final byte i25 = compressedArray[inOffset + 25];
      final byte i26 = compressedArray[inOffset + 26];
      final byte i27 = compressedArray[inOffset + 27];

      unCompressedData[outOffset + 0] = ((i0 << 20) | (i1 << 12 & 1048575) | (i2 << 4 & 4095) | (i3 >>> 4 & 15)) & 268435455;
      unCompressedData[outOffset + 1] = ((i3 << 24) | (i4 << 16 & 16777215) | (i5 << 8 & 65535) | (i6 >>> 0 & 255)) & 268435455;
      unCompressedData[outOffset + 2] = ((i7 << 20) | (i8 << 12 & 1048575) | (i9 << 4 & 4095) | (i10 >>> 4 & 15)) & 268435455;
      unCompressedData[outOffset + 3] = ((i10 << 24) | (i11 << 16 & 16777215) | (i12 << 8 & 65535) | (i13 >>> 0 & 255)) & 268435455;
      unCompressedData[outOffset + 4] = ((i14 << 20) | (i15 << 12 & 1048575) | (i16 << 4 & 4095) | (i17 >>> 4 & 15)) & 268435455;
      unCompressedData[outOffset + 5] = ((i17 << 24) | (i18 << 16 & 16777215) | (i19 << 8 & 65535) | (i20 >>> 0 & 255)) & 268435455;
      unCompressedData[outOffset + 6] = ((i21 << 20) | (i22 << 12 & 1048575) | (i23 << 4 & 4095) | (i24 >>> 4 & 15)) & 268435455;
      unCompressedData[outOffset + 7] = ((i24 << 24) | (i25 << 16 & 16777215) | (i26 << 8 & 65535) | (i27 >>> 0 & 255)) & 268435455;
      input.offset += 29;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor95 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];
      final byte i23 = compressedArray[inOffset + 23];
      final byte i24 = compressedArray[inOffset + 24];
      final byte i25 = compressedArray[inOffset + 25];
      final byte i26 = compressedArray[inOffset + 26];
      final byte i27 = compressedArray[inOffset + 27];
      final byte i28 = compressedArray[inOffset + 28];

      unCompressedData[outOffset + 0] = ((i0 << 21) | (i1 << 13 & 2097151) | (i2 << 5 & 8191) | (i3 >>> 3 & 31)) & 536870911;
      unCompressedData[outOffset + 1] = ((i3 << 26) | (i4 << 18 & 67108863) | (i5 << 10 & 262143) | (i6 << 2 & 1023) | (i7 >>> 6 & 3)) & 536870911;
      unCompressedData[outOffset + 2] = ((i7 << 23) | (i8 << 15 & 8388607) | (i9 << 7 & 32767) | (i10 >>> 1 & 127)) & 536870911;
      unCompressedData[outOffset + 3] = ((i10 << 28) | (i11 << 20 & 268435455) | (i12 << 12 & 1048575) | (i13 << 4 & 4095) | (i14 >>> 4 & 15)) & 536870911;
      unCompressedData[outOffset + 4] = ((i14 << 25) | (i15 << 17 & 33554431) | (i16 << 9 & 131071) | (i17 << 1 & 511) | (i18 >>> 7 & 1)) & 536870911;
      unCompressedData[outOffset + 5] = ((i18 << 22) | (i19 << 14 & 4194303) | (i20 << 6 & 16383) | (i21 >>> 2 & 63)) & 536870911;
      unCompressedData[outOffset + 6] = ((i21 << 27) | (i22 << 19 & 134217727) | (i23 << 11 & 524287) | (i24 << 3 & 2047) | (i25 >>> 5 & 7)) & 536870911;
      unCompressedData[outOffset + 7] = ((i25 << 24) | (i26 << 16 & 16777215) | (i27 << 8 & 65535) | (i28 >>> 0 & 255)) & 536870911;
      input.offset += 30;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor96 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];
      final byte i23 = compressedArray[inOffset + 23];
      final byte i24 = compressedArray[inOffset + 24];
      final byte i25 = compressedArray[inOffset + 25];
      final byte i26 = compressedArray[inOffset + 26];
      final byte i27 = compressedArray[inOffset + 27];
      final byte i28 = compressedArray[inOffset + 28];
      final byte i29 = compressedArray[inOffset + 29];

      unCompressedData[outOffset + 0] = ((i0 << 22) | (i1 << 14 & 4194303) | (i2 << 6 & 16383) | (i3 >>> 2 & 63)) & 1073741823;
      unCompressedData[outOffset + 1] = ((i3 << 28) | (i4 << 20 & 268435455) | (i5 << 12 & 1048575) | (i6 << 4 & 4095) | (i7 >>> 4 & 15)) & 1073741823;
      unCompressedData[outOffset + 2] = ((i7 << 26) | (i8 << 18 & 67108863) | (i9 << 10 & 262143) | (i10 << 2 & 1023) | (i11 >>> 6 & 3)) & 1073741823;
      unCompressedData[outOffset + 3] = ((i11 << 24) | (i12 << 16 & 16777215) | (i13 << 8 & 65535) | (i14 >>> 0 & 255)) & 1073741823;
      unCompressedData[outOffset + 4] = ((i15 << 22) | (i16 << 14 & 4194303) | (i17 << 6 & 16383) | (i18 >>> 2 & 63)) & 1073741823;
      unCompressedData[outOffset + 5] = ((i18 << 28) | (i19 << 20 & 268435455) | (i20 << 12 & 1048575) | (i21 << 4 & 4095) | (i22 >>> 4 & 15)) & 1073741823;
      unCompressedData[outOffset + 6] = ((i22 << 26) | (i23 << 18 & 67108863) | (i24 << 10 & 262143) | (i25 << 2 & 1023) | (i26 >>> 6 & 3)) & 1073741823;
      unCompressedData[outOffset + 7] = ((i26 << 24) | (i27 << 16 & 16777215) | (i28 << 8 & 65535) | (i29 >>> 0 & 255)) & 1073741823;
      input.offset += 31;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor97 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];
      final byte i23 = compressedArray[inOffset + 23];
      final byte i24 = compressedArray[inOffset + 24];
      final byte i25 = compressedArray[inOffset + 25];
      final byte i26 = compressedArray[inOffset + 26];
      final byte i27 = compressedArray[inOffset + 27];
      final byte i28 = compressedArray[inOffset + 28];
      final byte i29 = compressedArray[inOffset + 29];
      final byte i30 = compressedArray[inOffset + 30];

      unCompressedData[outOffset + 0] = ((i0 << 23) | (i1 << 15 & 8388607) | (i2 << 7 & 32767) | (i3 >>> 1 & 127)) & 2147483647;
      unCompressedData[outOffset + 1] = ((i3 << 30) | (i4 << 22 & 1073741823) | (i5 << 14 & 4194303) | (i6 << 6 & 16383) | (i7 >>> 2 & 63)) & 2147483647;
      unCompressedData[outOffset + 2] = ((i7 << 29) | (i8 << 21 & 536870911) | (i9 << 13 & 2097151) | (i10 << 5 & 8191) | (i11 >>> 3 & 31)) & 2147483647;
      unCompressedData[outOffset + 3] = ((i11 << 28) | (i12 << 20 & 268435455) | (i13 << 12 & 1048575) | (i14 << 4 & 4095) | (i15 >>> 4 & 15)) & 2147483647;
      unCompressedData[outOffset + 4] = ((i15 << 27) | (i16 << 19 & 134217727) | (i17 << 11 & 524287) | (i18 << 3 & 2047) | (i19 >>> 5 & 7)) & 2147483647;
      unCompressedData[outOffset + 5] = ((i19 << 26) | (i20 << 18 & 67108863) | (i21 << 10 & 262143) | (i22 << 2 & 1023) | (i23 >>> 6 & 3)) & 2147483647;
      unCompressedData[outOffset + 6] = ((i23 << 25) | (i24 << 17 & 33554431) | (i25 << 9 & 131071) | (i26 << 1 & 511) | (i27 >>> 7 & 1)) & 2147483647;
      unCompressedData[outOffset + 7] = ((i27 << 24) | (i28 << 16 & 16777215) | (i29 << 8 & 65535) | (i30 >>> 0 & 255)) & 2147483647;
      input.offset += 32;
      output.offset += 8;
    }
  }

  static final class FrameDecompressor98 extends FrameDecompressor {
    public final void decompress(final BytesRef input, final IntsRef output) {
      final int[] unCompressedData = output.ints;
      final byte[] compressedArray = input.bytes;
      final int inOffset = input.offset + 1;
      final int outOffset = output.offset;
      final byte i0 = compressedArray[inOffset + 0];
      final byte i1 = compressedArray[inOffset + 1];
      final byte i2 = compressedArray[inOffset + 2];
      final byte i3 = compressedArray[inOffset + 3];
      final byte i4 = compressedArray[inOffset + 4];
      final byte i5 = compressedArray[inOffset + 5];
      final byte i6 = compressedArray[inOffset + 6];
      final byte i7 = compressedArray[inOffset + 7];
      final byte i8 = compressedArray[inOffset + 8];
      final byte i9 = compressedArray[inOffset + 9];
      final byte i10 = compressedArray[inOffset + 10];
      final byte i11 = compressedArray[inOffset + 11];
      final byte i12 = compressedArray[inOffset + 12];
      final byte i13 = compressedArray[inOffset + 13];
      final byte i14 = compressedArray[inOffset + 14];
      final byte i15 = compressedArray[inOffset + 15];
      final byte i16 = compressedArray[inOffset + 16];
      final byte i17 = compressedArray[inOffset + 17];
      final byte i18 = compressedArray[inOffset + 18];
      final byte i19 = compressedArray[inOffset + 19];
      final byte i20 = compressedArray[inOffset + 20];
      final byte i21 = compressedArray[inOffset + 21];
      final byte i22 = compressedArray[inOffset + 22];
      final byte i23 = compressedArray[inOffset + 23];
      final byte i24 = compressedArray[inOffset + 24];
      final byte i25 = compressedArray[inOffset + 25];
      final byte i26 = compressedArray[inOffset + 26];
      final byte i27 = compressedArray[inOffset + 27];
      final byte i28 = compressedArray[inOffset + 28];
      final byte i29 = compressedArray[inOffset + 29];
      final byte i30 = compressedArray[inOffset + 30];
      final byte i31 = compressedArray[inOffset + 31];

      unCompressedData[outOffset + 0] = ((i0 << 24) | (i1 << 16 & 16777215) | (i2 << 8 & 65535) | (i3 >>> 0 & 255));
      unCompressedData[outOffset + 1] = ((i4 << 24) | (i5 << 16 & 16777215) | (i6 << 8 & 65535) | (i7 >>> 0 & 255));
      unCompressedData[outOffset + 2] = ((i8 << 24) | (i9 << 16 & 16777215) | (i10 << 8 & 65535) | (i11 >>> 0 & 255));
      unCompressedData[outOffset + 3] = ((i12 << 24) | (i13 << 16 & 16777215) | (i14 << 8 & 65535) | (i15 >>> 0 & 255));
      unCompressedData[outOffset + 4] = ((i16 << 24) | (i17 << 16 & 16777215) | (i18 << 8 & 65535) | (i19 >>> 0 & 255));
      unCompressedData[outOffset + 5] = ((i20 << 24) | (i21 << 16 & 16777215) | (i22 << 8 & 65535) | (i23 >>> 0 & 255));
      unCompressedData[outOffset + 6] = ((i24 << 24) | (i25 << 16 & 16777215) | (i26 << 8 & 65535) | (i27 >>> 0 & 255));
      unCompressedData[outOffset + 7] = ((i28 << 24) | (i29 << 16 & 16777215) | (i30 << 8 & 65535) | (i31 >>> 0 & 255));
      input.offset += 33;
      output.offset += 8;
    }
  }

}
