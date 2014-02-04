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
/* This program is generated, do not modify. See AForFrameCompressorGenerator.java */
package org.sindice.siren.index.codecs.block;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;

/**
 * This class contains a lookup table of functors for compressing fames.
 */
public class AForFrameCompressor {

  public static final FrameCompressor[] compressors = new FrameCompressor[] {
    new FrameCompressor0(),
    new FrameCompressor1(),
    new FrameCompressor2(),
    new FrameCompressor3(),
    new FrameCompressor4(),
    new FrameCompressor5(),
    new FrameCompressor6(),
    new FrameCompressor7(),
    new FrameCompressor8(),
    new FrameCompressor9(),
    new FrameCompressor10(),
    new FrameCompressor11(),
    new FrameCompressor12(),
    new FrameCompressor13(),
    new FrameCompressor14(),
    new FrameCompressor15(),
    new FrameCompressor16(),
    new FrameCompressor17(),
    new FrameCompressor18(),
    new FrameCompressor19(),
    new FrameCompressor20(),
    new FrameCompressor21(),
    new FrameCompressor22(),
    new FrameCompressor23(),
    new FrameCompressor24(),
    new FrameCompressor25(),
    new FrameCompressor26(),
    new FrameCompressor27(),
    new FrameCompressor28(),
    new FrameCompressor29(),
    new FrameCompressor30(),
    new FrameCompressor31(),
    new FrameCompressor32(),
    new FrameCompressor33(),
    new FrameCompressor34(),
    new FrameCompressor35(),
    new FrameCompressor36(),
    new FrameCompressor37(),
    new FrameCompressor38(),
    new FrameCompressor39(),
    new FrameCompressor40(),
    new FrameCompressor41(),
    new FrameCompressor42(),
    new FrameCompressor43(),
    new FrameCompressor44(),
    new FrameCompressor45(),
    new FrameCompressor46(),
    new FrameCompressor47(),
    new FrameCompressor48(),
    new FrameCompressor49(),
    new FrameCompressor50(),
    new FrameCompressor51(),
    new FrameCompressor52(),
    new FrameCompressor53(),
    new FrameCompressor54(),
    new FrameCompressor55(),
    new FrameCompressor56(),
    new FrameCompressor57(),
    new FrameCompressor58(),
    new FrameCompressor59(),
    new FrameCompressor60(),
    new FrameCompressor61(),
    new FrameCompressor62(),
    new FrameCompressor63(),
    new FrameCompressor64(),
    new FrameCompressor65(),
    new FrameCompressor66(),
    new FrameCompressor67(),
    new FrameCompressor68(),
    new FrameCompressor69(),
    new FrameCompressor70(),
    new FrameCompressor71(),
    new FrameCompressor72(),
    new FrameCompressor73(),
    new FrameCompressor74(),
    new FrameCompressor75(),
    new FrameCompressor76(),
    new FrameCompressor77(),
    new FrameCompressor78(),
    new FrameCompressor79(),
    new FrameCompressor80(),
    new FrameCompressor81(),
    new FrameCompressor82(),
    new FrameCompressor83(),
    new FrameCompressor84(),
    new FrameCompressor85(),
    new FrameCompressor86(),
    new FrameCompressor87(),
    new FrameCompressor88(),
    new FrameCompressor89(),
    new FrameCompressor90(),
    new FrameCompressor91(),
    new FrameCompressor92(),
    new FrameCompressor93(),
    new FrameCompressor94(),
    new FrameCompressor95(),
    new FrameCompressor96(),
    new FrameCompressor97(),
    new FrameCompressor98(),
  };

  static abstract class FrameCompressor {
    public abstract void compress(final IntsRef input, final BytesRef output);
  }

  static final class FrameCompressor0 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      output.offset += 1;
      input.offset += 32;
  }
  }

  static final class FrameCompressor1 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 1) << 7)
                                                | ((intValues1 & 1) << 6)
                                                | ((intValues2 & 1) << 5)
                                                | ((intValues3 & 1) << 4)
                                                | ((intValues4 & 1) << 3)
                                                | ((intValues5 & 1) << 2)
                                                | ((intValues6 & 1) << 1)
                                                | (intValues7 & 1));
      compressedArray[outputOffset + 1] = (byte) (((intValues8 & 1) << 7)
                                                | ((intValues9 & 1) << 6)
                                                | ((intValues10 & 1) << 5)
                                                | ((intValues11 & 1) << 4)
                                                | ((intValues12 & 1) << 3)
                                                | ((intValues13 & 1) << 2)
                                                | ((intValues14 & 1) << 1)
                                                | (intValues15 & 1));
      compressedArray[outputOffset + 2] = (byte) (((intValues16 & 1) << 7)
                                                | ((intValues17 & 1) << 6)
                                                | ((intValues18 & 1) << 5)
                                                | ((intValues19 & 1) << 4)
                                                | ((intValues20 & 1) << 3)
                                                | ((intValues21 & 1) << 2)
                                                | ((intValues22 & 1) << 1)
                                                | (intValues23 & 1));
      compressedArray[outputOffset + 3] = (byte) (((intValues24 & 1) << 7)
                                                | ((intValues25 & 1) << 6)
                                                | ((intValues26 & 1) << 5)
                                                | ((intValues27 & 1) << 4)
                                                | ((intValues28 & 1) << 3)
                                                | ((intValues29 & 1) << 2)
                                                | ((intValues30 & 1) << 1)
                                                | (intValues31 & 1));
      input.offset += 32;
      output.offset += 5;
    }
  }

  static final class FrameCompressor2 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 3) << 6)
                                                | ((intValues1 & 3) << 4)
                                                | ((intValues2 & 3) << 2)
                                                | (intValues3 & 3));
      compressedArray[outputOffset + 1] = (byte) (((intValues4 & 3) << 6)
                                                | ((intValues5 & 3) << 4)
                                                | ((intValues6 & 3) << 2)
                                                | (intValues7 & 3));
      compressedArray[outputOffset + 2] = (byte) (((intValues8 & 3) << 6)
                                                | ((intValues9 & 3) << 4)
                                                | ((intValues10 & 3) << 2)
                                                | (intValues11 & 3));
      compressedArray[outputOffset + 3] = (byte) (((intValues12 & 3) << 6)
                                                | ((intValues13 & 3) << 4)
                                                | ((intValues14 & 3) << 2)
                                                | (intValues15 & 3));
      compressedArray[outputOffset + 4] = (byte) (((intValues16 & 3) << 6)
                                                | ((intValues17 & 3) << 4)
                                                | ((intValues18 & 3) << 2)
                                                | (intValues19 & 3));
      compressedArray[outputOffset + 5] = (byte) (((intValues20 & 3) << 6)
                                                | ((intValues21 & 3) << 4)
                                                | ((intValues22 & 3) << 2)
                                                | (intValues23 & 3));
      compressedArray[outputOffset + 6] = (byte) (((intValues24 & 3) << 6)
                                                | ((intValues25 & 3) << 4)
                                                | ((intValues26 & 3) << 2)
                                                | (intValues27 & 3));
      compressedArray[outputOffset + 7] = (byte) (((intValues28 & 3) << 6)
                                                | ((intValues29 & 3) << 4)
                                                | ((intValues30 & 3) << 2)
                                                | (intValues31 & 3));
      input.offset += 32;
      output.offset += 9;
    }
  }

  static final class FrameCompressor3 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 7) << 5)
                                                | ((intValues1 & 7) << 2)
                                                | ((intValues2 >>> 1) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues2 & 1) << 7)
                                                | ((intValues3 & 7) << 4)
                                                | ((intValues4 & 7) << 1)
                                                | ((intValues5 >>> 2) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues5 & 3) << 6)
                                                | ((intValues6 & 7) << 3)
                                                | (intValues7 & 7));
      compressedArray[outputOffset + 3] = (byte) (((intValues8 & 7) << 5)
                                                | ((intValues9 & 7) << 2)
                                                | ((intValues10 >>> 1) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues10 & 1) << 7)
                                                | ((intValues11 & 7) << 4)
                                                | ((intValues12 & 7) << 1)
                                                | ((intValues13 >>> 2) & 0xFF));
      compressedArray[outputOffset + 5] = (byte) (((intValues13 & 3) << 6)
                                                | ((intValues14 & 7) << 3)
                                                | (intValues15 & 7));
      compressedArray[outputOffset + 6] = (byte) (((intValues16 & 7) << 5)
                                                | ((intValues17 & 7) << 2)
                                                | ((intValues18 >>> 1) & 0xFF));
      compressedArray[outputOffset + 7] = (byte) (((intValues18 & 1) << 7)
                                                | ((intValues19 & 7) << 4)
                                                | ((intValues20 & 7) << 1)
                                                | ((intValues21 >>> 2) & 0xFF));
      compressedArray[outputOffset + 8] = (byte) (((intValues21 & 3) << 6)
                                                | ((intValues22 & 7) << 3)
                                                | (intValues23 & 7));
      compressedArray[outputOffset + 9] = (byte) (((intValues24 & 7) << 5)
                                                | ((intValues25 & 7) << 2)
                                                | ((intValues26 >>> 1) & 0xFF));
      compressedArray[outputOffset + 10] = (byte) (((intValues26 & 1) << 7)
                                                | ((intValues27 & 7) << 4)
                                                | ((intValues28 & 7) << 1)
                                                | ((intValues29 >>> 2) & 0xFF));
      compressedArray[outputOffset + 11] = (byte) (((intValues29 & 3) << 6)
                                                | ((intValues30 & 7) << 3)
                                                | (intValues31 & 7));
      input.offset += 32;
      output.offset += 13;
    }
  }

  static final class FrameCompressor4 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 15) << 4)
                                                | (intValues1 & 15));
      compressedArray[outputOffset + 1] = (byte) (((intValues2 & 15) << 4)
                                                | (intValues3 & 15));
      compressedArray[outputOffset + 2] = (byte) (((intValues4 & 15) << 4)
                                                | (intValues5 & 15));
      compressedArray[outputOffset + 3] = (byte) (((intValues6 & 15) << 4)
                                                | (intValues7 & 15));
      compressedArray[outputOffset + 4] = (byte) (((intValues8 & 15) << 4)
                                                | (intValues9 & 15));
      compressedArray[outputOffset + 5] = (byte) (((intValues10 & 15) << 4)
                                                | (intValues11 & 15));
      compressedArray[outputOffset + 6] = (byte) (((intValues12 & 15) << 4)
                                                | (intValues13 & 15));
      compressedArray[outputOffset + 7] = (byte) (((intValues14 & 15) << 4)
                                                | (intValues15 & 15));
      compressedArray[outputOffset + 8] = (byte) (((intValues16 & 15) << 4)
                                                | (intValues17 & 15));
      compressedArray[outputOffset + 9] = (byte) (((intValues18 & 15) << 4)
                                                | (intValues19 & 15));
      compressedArray[outputOffset + 10] = (byte) (((intValues20 & 15) << 4)
                                                | (intValues21 & 15));
      compressedArray[outputOffset + 11] = (byte) (((intValues22 & 15) << 4)
                                                | (intValues23 & 15));
      compressedArray[outputOffset + 12] = (byte) (((intValues24 & 15) << 4)
                                                | (intValues25 & 15));
      compressedArray[outputOffset + 13] = (byte) (((intValues26 & 15) << 4)
                                                | (intValues27 & 15));
      compressedArray[outputOffset + 14] = (byte) (((intValues28 & 15) << 4)
                                                | (intValues29 & 15));
      compressedArray[outputOffset + 15] = (byte) (((intValues30 & 15) << 4)
                                                | (intValues31 & 15));
      input.offset += 32;
      output.offset += 17;
    }
  }

  static final class FrameCompressor5 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 31) << 3)
                                                | ((intValues1 >>> 2) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues1 & 3) << 6)
                                                | ((intValues2 & 31) << 1)
                                                | ((intValues3 >>> 4) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues3 & 15) << 4)
                                                | ((intValues4 >>> 1) & 0xFF));
      compressedArray[outputOffset + 3] = (byte) (((intValues4 & 1) << 7)
                                                | ((intValues5 & 31) << 2)
                                                | ((intValues6 >>> 3) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues6 & 7) << 5)
                                                | (intValues7 & 31));
      compressedArray[outputOffset + 5] = (byte) (((intValues8 & 31) << 3)
                                                | ((intValues9 >>> 2) & 0xFF));
      compressedArray[outputOffset + 6] = (byte) (((intValues9 & 3) << 6)
                                                | ((intValues10 & 31) << 1)
                                                | ((intValues11 >>> 4) & 0xFF));
      compressedArray[outputOffset + 7] = (byte) (((intValues11 & 15) << 4)
                                                | ((intValues12 >>> 1) & 0xFF));
      compressedArray[outputOffset + 8] = (byte) (((intValues12 & 1) << 7)
                                                | ((intValues13 & 31) << 2)
                                                | ((intValues14 >>> 3) & 0xFF));
      compressedArray[outputOffset + 9] = (byte) (((intValues14 & 7) << 5)
                                                | (intValues15 & 31));
      compressedArray[outputOffset + 10] = (byte) (((intValues16 & 31) << 3)
                                                | ((intValues17 >>> 2) & 0xFF));
      compressedArray[outputOffset + 11] = (byte) (((intValues17 & 3) << 6)
                                                | ((intValues18 & 31) << 1)
                                                | ((intValues19 >>> 4) & 0xFF));
      compressedArray[outputOffset + 12] = (byte) (((intValues19 & 15) << 4)
                                                | ((intValues20 >>> 1) & 0xFF));
      compressedArray[outputOffset + 13] = (byte) (((intValues20 & 1) << 7)
                                                | ((intValues21 & 31) << 2)
                                                | ((intValues22 >>> 3) & 0xFF));
      compressedArray[outputOffset + 14] = (byte) (((intValues22 & 7) << 5)
                                                | (intValues23 & 31));
      compressedArray[outputOffset + 15] = (byte) (((intValues24 & 31) << 3)
                                                | ((intValues25 >>> 2) & 0xFF));
      compressedArray[outputOffset + 16] = (byte) (((intValues25 & 3) << 6)
                                                | ((intValues26 & 31) << 1)
                                                | ((intValues27 >>> 4) & 0xFF));
      compressedArray[outputOffset + 17] = (byte) (((intValues27 & 15) << 4)
                                                | ((intValues28 >>> 1) & 0xFF));
      compressedArray[outputOffset + 18] = (byte) (((intValues28 & 1) << 7)
                                                | ((intValues29 & 31) << 2)
                                                | ((intValues30 >>> 3) & 0xFF));
      compressedArray[outputOffset + 19] = (byte) (((intValues30 & 7) << 5)
                                                | (intValues31 & 31));
      input.offset += 32;
      output.offset += 21;
    }
  }

  static final class FrameCompressor6 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 63) << 2)
                                                | ((intValues1 >>> 4) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues1 & 15) << 4)
                                                | ((intValues2 >>> 2) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues2 & 3) << 6)
                                                | (intValues3 & 63));
      compressedArray[outputOffset + 3] = (byte) (((intValues4 & 63) << 2)
                                                | ((intValues5 >>> 4) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues5 & 15) << 4)
                                                | ((intValues6 >>> 2) & 0xFF));
      compressedArray[outputOffset + 5] = (byte) (((intValues6 & 3) << 6)
                                                | (intValues7 & 63));
      compressedArray[outputOffset + 6] = (byte) (((intValues8 & 63) << 2)
                                                | ((intValues9 >>> 4) & 0xFF));
      compressedArray[outputOffset + 7] = (byte) (((intValues9 & 15) << 4)
                                                | ((intValues10 >>> 2) & 0xFF));
      compressedArray[outputOffset + 8] = (byte) (((intValues10 & 3) << 6)
                                                | (intValues11 & 63));
      compressedArray[outputOffset + 9] = (byte) (((intValues12 & 63) << 2)
                                                | ((intValues13 >>> 4) & 0xFF));
      compressedArray[outputOffset + 10] = (byte) (((intValues13 & 15) << 4)
                                                | ((intValues14 >>> 2) & 0xFF));
      compressedArray[outputOffset + 11] = (byte) (((intValues14 & 3) << 6)
                                                | (intValues15 & 63));
      compressedArray[outputOffset + 12] = (byte) (((intValues16 & 63) << 2)
                                                | ((intValues17 >>> 4) & 0xFF));
      compressedArray[outputOffset + 13] = (byte) (((intValues17 & 15) << 4)
                                                | ((intValues18 >>> 2) & 0xFF));
      compressedArray[outputOffset + 14] = (byte) (((intValues18 & 3) << 6)
                                                | (intValues19 & 63));
      compressedArray[outputOffset + 15] = (byte) (((intValues20 & 63) << 2)
                                                | ((intValues21 >>> 4) & 0xFF));
      compressedArray[outputOffset + 16] = (byte) (((intValues21 & 15) << 4)
                                                | ((intValues22 >>> 2) & 0xFF));
      compressedArray[outputOffset + 17] = (byte) (((intValues22 & 3) << 6)
                                                | (intValues23 & 63));
      compressedArray[outputOffset + 18] = (byte) (((intValues24 & 63) << 2)
                                                | ((intValues25 >>> 4) & 0xFF));
      compressedArray[outputOffset + 19] = (byte) (((intValues25 & 15) << 4)
                                                | ((intValues26 >>> 2) & 0xFF));
      compressedArray[outputOffset + 20] = (byte) (((intValues26 & 3) << 6)
                                                | (intValues27 & 63));
      compressedArray[outputOffset + 21] = (byte) (((intValues28 & 63) << 2)
                                                | ((intValues29 >>> 4) & 0xFF));
      compressedArray[outputOffset + 22] = (byte) (((intValues29 & 15) << 4)
                                                | ((intValues30 >>> 2) & 0xFF));
      compressedArray[outputOffset + 23] = (byte) (((intValues30 & 3) << 6)
                                                | (intValues31 & 63));
      input.offset += 32;
      output.offset += 25;
    }
  }

  static final class FrameCompressor7 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 127) << 1)
                                                | ((intValues1 >>> 6) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues1 & 63) << 2)
                                                | ((intValues2 >>> 5) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues2 & 31) << 3)
                                                | ((intValues3 >>> 4) & 0xFF));
      compressedArray[outputOffset + 3] = (byte) (((intValues3 & 15) << 4)
                                                | ((intValues4 >>> 3) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues4 & 7) << 5)
                                                | ((intValues5 >>> 2) & 0xFF));
      compressedArray[outputOffset + 5] = (byte) (((intValues5 & 3) << 6)
                                                | ((intValues6 >>> 1) & 0xFF));
      compressedArray[outputOffset + 6] = (byte) (((intValues6 & 1) << 7)
                                                | (intValues7 & 127));
      compressedArray[outputOffset + 7] = (byte) (((intValues8 & 127) << 1)
                                                | ((intValues9 >>> 6) & 0xFF));
      compressedArray[outputOffset + 8] = (byte) (((intValues9 & 63) << 2)
                                                | ((intValues10 >>> 5) & 0xFF));
      compressedArray[outputOffset + 9] = (byte) (((intValues10 & 31) << 3)
                                                | ((intValues11 >>> 4) & 0xFF));
      compressedArray[outputOffset + 10] = (byte) (((intValues11 & 15) << 4)
                                                | ((intValues12 >>> 3) & 0xFF));
      compressedArray[outputOffset + 11] = (byte) (((intValues12 & 7) << 5)
                                                | ((intValues13 >>> 2) & 0xFF));
      compressedArray[outputOffset + 12] = (byte) (((intValues13 & 3) << 6)
                                                | ((intValues14 >>> 1) & 0xFF));
      compressedArray[outputOffset + 13] = (byte) (((intValues14 & 1) << 7)
                                                | (intValues15 & 127));
      compressedArray[outputOffset + 14] = (byte) (((intValues16 & 127) << 1)
                                                | ((intValues17 >>> 6) & 0xFF));
      compressedArray[outputOffset + 15] = (byte) (((intValues17 & 63) << 2)
                                                | ((intValues18 >>> 5) & 0xFF));
      compressedArray[outputOffset + 16] = (byte) (((intValues18 & 31) << 3)
                                                | ((intValues19 >>> 4) & 0xFF));
      compressedArray[outputOffset + 17] = (byte) (((intValues19 & 15) << 4)
                                                | ((intValues20 >>> 3) & 0xFF));
      compressedArray[outputOffset + 18] = (byte) (((intValues20 & 7) << 5)
                                                | ((intValues21 >>> 2) & 0xFF));
      compressedArray[outputOffset + 19] = (byte) (((intValues21 & 3) << 6)
                                                | ((intValues22 >>> 1) & 0xFF));
      compressedArray[outputOffset + 20] = (byte) (((intValues22 & 1) << 7)
                                                | (intValues23 & 127));
      compressedArray[outputOffset + 21] = (byte) (((intValues24 & 127) << 1)
                                                | ((intValues25 >>> 6) & 0xFF));
      compressedArray[outputOffset + 22] = (byte) (((intValues25 & 63) << 2)
                                                | ((intValues26 >>> 5) & 0xFF));
      compressedArray[outputOffset + 23] = (byte) (((intValues26 & 31) << 3)
                                                | ((intValues27 >>> 4) & 0xFF));
      compressedArray[outputOffset + 24] = (byte) (((intValues27 & 15) << 4)
                                                | ((intValues28 >>> 3) & 0xFF));
      compressedArray[outputOffset + 25] = (byte) (((intValues28 & 7) << 5)
                                                | ((intValues29 >>> 2) & 0xFF));
      compressedArray[outputOffset + 26] = (byte) (((intValues29 & 3) << 6)
                                                | ((intValues30 >>> 1) & 0xFF));
      compressedArray[outputOffset + 27] = (byte) (((intValues30 & 1) << 7)
                                                | (intValues31 & 127));
      input.offset += 32;
      output.offset += 29;
    }
  }

  static final class FrameCompressor8 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 & 255));
      compressedArray[outputOffset + 1] = (byte) ((intValues1 & 255));
      compressedArray[outputOffset + 2] = (byte) ((intValues2 & 255));
      compressedArray[outputOffset + 3] = (byte) ((intValues3 & 255));
      compressedArray[outputOffset + 4] = (byte) ((intValues4 & 255));
      compressedArray[outputOffset + 5] = (byte) ((intValues5 & 255));
      compressedArray[outputOffset + 6] = (byte) ((intValues6 & 255));
      compressedArray[outputOffset + 7] = (byte) ((intValues7 & 255));
      compressedArray[outputOffset + 8] = (byte) ((intValues8 & 255));
      compressedArray[outputOffset + 9] = (byte) ((intValues9 & 255));
      compressedArray[outputOffset + 10] = (byte) ((intValues10 & 255));
      compressedArray[outputOffset + 11] = (byte) ((intValues11 & 255));
      compressedArray[outputOffset + 12] = (byte) ((intValues12 & 255));
      compressedArray[outputOffset + 13] = (byte) ((intValues13 & 255));
      compressedArray[outputOffset + 14] = (byte) ((intValues14 & 255));
      compressedArray[outputOffset + 15] = (byte) ((intValues15 & 255));
      compressedArray[outputOffset + 16] = (byte) ((intValues16 & 255));
      compressedArray[outputOffset + 17] = (byte) ((intValues17 & 255));
      compressedArray[outputOffset + 18] = (byte) ((intValues18 & 255));
      compressedArray[outputOffset + 19] = (byte) ((intValues19 & 255));
      compressedArray[outputOffset + 20] = (byte) ((intValues20 & 255));
      compressedArray[outputOffset + 21] = (byte) ((intValues21 & 255));
      compressedArray[outputOffset + 22] = (byte) ((intValues22 & 255));
      compressedArray[outputOffset + 23] = (byte) ((intValues23 & 255));
      compressedArray[outputOffset + 24] = (byte) ((intValues24 & 255));
      compressedArray[outputOffset + 25] = (byte) ((intValues25 & 255));
      compressedArray[outputOffset + 26] = (byte) ((intValues26 & 255));
      compressedArray[outputOffset + 27] = (byte) ((intValues27 & 255));
      compressedArray[outputOffset + 28] = (byte) ((intValues28 & 255));
      compressedArray[outputOffset + 29] = (byte) ((intValues29 & 255));
      compressedArray[outputOffset + 30] = (byte) ((intValues30 & 255));
      compressedArray[outputOffset + 31] = (byte) ((intValues31 & 255));
      input.offset += 32;
      output.offset += 33;
    }
  }

  static final class FrameCompressor9 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 1) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 1) << 7
                                                | (intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 3) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 & 7) << 5
                                                | (intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 5) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues4 & 31) << 3
                                                | (intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 7) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues6 & 127) << 1
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues8 >>> 1) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues8 & 1) << 7
                                                | (intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 3) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues10 & 7) << 5
                                                | (intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 5) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues12 & 31) << 3
                                                | (intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 7) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues14 & 127) << 1
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues16 >>> 1) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues16 & 1) << 7
                                                | (intValues17 >>> 2) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues17 & 3) << 6
                                                | (intValues18 >>> 3) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues18 & 7) << 5
                                                | (intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 5) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues20 & 31) << 3
                                                | (intValues21 >>> 6) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues21 & 63) << 2
                                                | (intValues22 >>> 7) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues22 & 127) << 1
                                                | (intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues24 >>> 1) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues24 & 1) << 7
                                                | (intValues25 >>> 2) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues25 & 3) << 6
                                                | (intValues26 >>> 3) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues26 & 7) << 5
                                                | (intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 5) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues28 & 31) << 3
                                                | (intValues29 >>> 6) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues29 & 63) << 2
                                                | (intValues30 >>> 7) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues30 & 127) << 1
                                                | (intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 37;
    }
  }

  static final class FrameCompressor10 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 2) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 3) << 6
                                                | (intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 6) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 & 63) << 2
                                                | (intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues4 >>> 2) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues4 & 3) << 6
                                                | (intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 6) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues6 & 63) << 2
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues8 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues8 & 3) << 6
                                                | (intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 6) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues10 & 63) << 2
                                                | (intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues12 >>> 2) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues12 & 3) << 6
                                                | (intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues14 & 63) << 2
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues16 >>> 2) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues16 & 3) << 6
                                                | (intValues17 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues17 & 15) << 4
                                                | (intValues18 >>> 6) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues18 & 63) << 2
                                                | (intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues20 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues20 & 3) << 6
                                                | (intValues21 >>> 4) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues21 & 15) << 4
                                                | (intValues22 >>> 6) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues22 & 63) << 2
                                                | (intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues24 >>> 2) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues24 & 3) << 6
                                                | (intValues25 >>> 4) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues25 & 15) << 4
                                                | (intValues26 >>> 6) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues26 & 63) << 2
                                                | (intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues28 >>> 2) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues28 & 3) << 6
                                                | (intValues29 >>> 4) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues29 & 15) << 4
                                                | (intValues30 >>> 6) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues30 & 63) << 2
                                                | (intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 41;
    }
  }

  static final class FrameCompressor11 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 3) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 7) << 5
                                                | (intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 9) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 >>> 1) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 & 1) << 7
                                                | (intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 7) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues4 & 127) << 1
                                                | (intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 5) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues6 & 31) << 3
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues8 >>> 3) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues8 & 7) << 5
                                                | (intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 9) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues10 >>> 1) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues10 & 1) << 7
                                                | (intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 7) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues12 & 127) << 1
                                                | (intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 5) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues14 & 31) << 3
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues16 >>> 3) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues16 & 7) << 5
                                                | (intValues17 >>> 6) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues17 & 63) << 2
                                                | (intValues18 >>> 9) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues18 >>> 1) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues18 & 1) << 7
                                                | (intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 7) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues20 & 127) << 1
                                                | (intValues21 >>> 10) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues21 >>> 2) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues21 & 3) << 6
                                                | (intValues22 >>> 5) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues22 & 31) << 3
                                                | (intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues24 >>> 3) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues24 & 7) << 5
                                                | (intValues25 >>> 6) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues25 & 63) << 2
                                                | (intValues26 >>> 9) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues26 >>> 1) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues26 & 1) << 7
                                                | (intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 7) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues28 & 127) << 1
                                                | (intValues29 >>> 10) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues29 >>> 2) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues29 & 3) << 6
                                                | (intValues30 >>> 5) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues30 & 31) << 3
                                                | (intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 45;
    }
  }

  static final class FrameCompressor12 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 4) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 15) << 4
                                                | (intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 >>> 4) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 & 15) << 4
                                                | (intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues4 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues4 & 15) << 4
                                                | (intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues6 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues6 & 15) << 4
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues8 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues8 & 15) << 4
                                                | (intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues10 >>> 4) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues10 & 15) << 4
                                                | (intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues12 >>> 4) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues12 & 15) << 4
                                                | (intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues14 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues14 & 15) << 4
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues16 >>> 4) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues16 & 15) << 4
                                                | (intValues17 >>> 8) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) (intValues17 & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues18 >>> 4) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues18 & 15) << 4
                                                | (intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues20 >>> 4) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues20 & 15) << 4
                                                | (intValues21 >>> 8) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) (intValues21 & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues22 >>> 4) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues22 & 15) << 4
                                                | (intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues24 >>> 4) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues24 & 15) << 4
                                                | (intValues25 >>> 8) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) (intValues25 & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues26 >>> 4) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues26 & 15) << 4
                                                | (intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues28 >>> 4) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues28 & 15) << 4
                                                | (intValues29 >>> 8) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) (intValues29 & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues30 >>> 4) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues30 & 15) << 4
                                                | (intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 49;
    }
  }

  static final class FrameCompressor13 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 5) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 31) << 3
                                                | (intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 7) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 & 127) << 1
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 9) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues4 >>> 1) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 & 1) << 7
                                                | (intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 11) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues6 >>> 3) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues6 & 7) << 5
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues8 >>> 5) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues8 & 31) << 3
                                                | (intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 7) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues10 & 127) << 1
                                                | (intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 9) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues12 >>> 1) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues12 & 1) << 7
                                                | (intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 11) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues14 >>> 3) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues14 & 7) << 5
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues16 >>> 5) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues16 & 31) << 3
                                                | (intValues17 >>> 10) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues17 >>> 2) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues17 & 3) << 6
                                                | (intValues18 >>> 7) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues18 & 127) << 1
                                                | (intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 9) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues20 >>> 1) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues20 & 1) << 7
                                                | (intValues21 >>> 6) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues21 & 63) << 2
                                                | (intValues22 >>> 11) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues22 >>> 3) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues22 & 7) << 5
                                                | (intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues24 >>> 5) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues24 & 31) << 3
                                                | (intValues25 >>> 10) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues25 >>> 2) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues25 & 3) << 6
                                                | (intValues26 >>> 7) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues26 & 127) << 1
                                                | (intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 9) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues28 >>> 1) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues28 & 1) << 7
                                                | (intValues29 >>> 6) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues29 & 63) << 2
                                                | (intValues30 >>> 11) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues30 >>> 3) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues30 & 7) << 5
                                                | (intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 53;
    }
  }

  static final class FrameCompressor14 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 6) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 63) << 2
                                                | (intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 10) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 >>> 2) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 & 3) << 6
                                                | (intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues4 >>> 6) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 & 63) << 2
                                                | (intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 10) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues6 >>> 2) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues6 & 3) << 6
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues8 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues8 & 63) << 2
                                                | (intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 10) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues10 >>> 2) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues10 & 3) << 6
                                                | (intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues12 >>> 6) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues12 & 63) << 2
                                                | (intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 10) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues14 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues14 & 3) << 6
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues16 >>> 6) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues16 & 63) << 2
                                                | (intValues17 >>> 12) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues17 >>> 4) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues17 & 15) << 4
                                                | (intValues18 >>> 10) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues18 >>> 2) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues18 & 3) << 6
                                                | (intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues20 >>> 6) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues20 & 63) << 2
                                                | (intValues21 >>> 12) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues21 >>> 4) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues21 & 15) << 4
                                                | (intValues22 >>> 10) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues22 >>> 2) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues22 & 3) << 6
                                                | (intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues24 >>> 6) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues24 & 63) << 2
                                                | (intValues25 >>> 12) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues25 >>> 4) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues25 & 15) << 4
                                                | (intValues26 >>> 10) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues26 >>> 2) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues26 & 3) << 6
                                                | (intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues28 >>> 6) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues28 & 63) << 2
                                                | (intValues29 >>> 12) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues29 >>> 4) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues29 & 15) << 4
                                                | (intValues30 >>> 10) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues30 >>> 2) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues30 & 3) << 6
                                                | (intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 57;
    }
  }

  static final class FrameCompressor15 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 7) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 127) << 1
                                                | (intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 13) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 >>> 5) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 & 31) << 3
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 11) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 >>> 3) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues4 & 7) << 5
                                                | (intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 9) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues6 >>> 1) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues6 & 1) << 7
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues8 >>> 7) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues8 & 127) << 1
                                                | (intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 13) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues10 >>> 5) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues10 & 31) << 3
                                                | (intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 11) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues12 >>> 3) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues12 & 7) << 5
                                                | (intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 9) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues14 >>> 1) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues14 & 1) << 7
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues16 >>> 7) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues16 & 127) << 1
                                                | (intValues17 >>> 14) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues17 >>> 6) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues17 & 63) << 2
                                                | (intValues18 >>> 13) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues18 >>> 5) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues18 & 31) << 3
                                                | (intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 11) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues20 >>> 3) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues20 & 7) << 5
                                                | (intValues21 >>> 10) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues21 >>> 2) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues21 & 3) << 6
                                                | (intValues22 >>> 9) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues22 >>> 1) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues22 & 1) << 7
                                                | (intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues24 >>> 7) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues24 & 127) << 1
                                                | (intValues25 >>> 14) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues25 >>> 6) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues25 & 63) << 2
                                                | (intValues26 >>> 13) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues26 >>> 5) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues26 & 31) << 3
                                                | (intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 11) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues28 >>> 3) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues28 & 7) << 5
                                                | (intValues29 >>> 10) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues29 >>> 2) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues29 & 3) << 6
                                                | (intValues30 >>> 9) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues30 >>> 1) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues30 & 1) << 7
                                                | (intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 61;
    }
  }

  static final class FrameCompressor16 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 8) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) (intValues0 & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 >>> 8) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) (intValues2 & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 >>> 8) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) (intValues4 & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues6 >>> 8) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) (intValues6 & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues8 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues8 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues10 >>> 8) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) (intValues10 & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues12 >>> 8) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) (intValues12 & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues14 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues14 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues16 >>> 8) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) (intValues16 & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues17 >>> 8) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) (intValues17 & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues18 >>> 8) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) (intValues18 & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues20 >>> 8) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) (intValues20 & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues21 >>> 8) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) (intValues21 & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues22 >>> 8) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) (intValues22 & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues24 >>> 8) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) (intValues24 & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues25 >>> 8) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) (intValues25 & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues26 >>> 8) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) (intValues26 & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues28 >>> 8) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) (intValues28 & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues29 >>> 8) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) (intValues29 & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues30 >>> 8) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) (intValues30 & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 65;
    }
  }

  static final class FrameCompressor17 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 9) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 1) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 1) << 7
                                                | (intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 11) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 3) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 & 7) << 5
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 13) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues4 >>> 5) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 & 31) << 3
                                                | (intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 15) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues6 >>> 7) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues6 & 127) << 1
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues8 >>> 9) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues8 >>> 1) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues8 & 1) << 7
                                                | (intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 11) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues10 >>> 3) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues10 & 7) << 5
                                                | (intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 13) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues12 >>> 5) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues12 & 31) << 3
                                                | (intValues13 >>> 14) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 15) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues14 >>> 7) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues14 & 127) << 1
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues16 >>> 9) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues16 >>> 1) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues16 & 1) << 7
                                                | (intValues17 >>> 10) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues17 >>> 2) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues17 & 3) << 6
                                                | (intValues18 >>> 11) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues18 >>> 3) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues18 & 7) << 5
                                                | (intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 13) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues20 >>> 5) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues20 & 31) << 3
                                                | (intValues21 >>> 14) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues21 >>> 6) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues21 & 63) << 2
                                                | (intValues22 >>> 15) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues22 >>> 7) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues22 & 127) << 1
                                                | (intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues24 >>> 9) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues24 >>> 1) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues24 & 1) << 7
                                                | (intValues25 >>> 10) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues25 >>> 2) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues25 & 3) << 6
                                                | (intValues26 >>> 11) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues26 >>> 3) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues26 & 7) << 5
                                                | (intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 13) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues28 >>> 5) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues28 & 31) << 3
                                                | (intValues29 >>> 14) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues29 >>> 6) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues29 & 63) << 2
                                                | (intValues30 >>> 15) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues30 >>> 7) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues30 & 127) << 1
                                                | (intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 69;
    }
  }

  static final class FrameCompressor18 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 10) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 2) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 3) << 6
                                                | (intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 14) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 6) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 & 63) << 2
                                                | (intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues4 >>> 10) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 & 3) << 6
                                                | (intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 14) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues6 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues6 & 63) << 2
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues8 >>> 10) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues8 >>> 2) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues8 & 3) << 6
                                                | (intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 14) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues10 >>> 6) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues10 & 63) << 2
                                                | (intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues12 >>> 10) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues12 >>> 2) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues12 & 3) << 6
                                                | (intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 14) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues14 >>> 6) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues14 & 63) << 2
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues16 >>> 10) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues16 >>> 2) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues16 & 3) << 6
                                                | (intValues17 >>> 12) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues17 >>> 4) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues17 & 15) << 4
                                                | (intValues18 >>> 14) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues18 >>> 6) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues18 & 63) << 2
                                                | (intValues19 >>> 16) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues20 >>> 10) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues20 >>> 2) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues20 & 3) << 6
                                                | (intValues21 >>> 12) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues21 >>> 4) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues21 & 15) << 4
                                                | (intValues22 >>> 14) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues22 >>> 6) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues22 & 63) << 2
                                                | (intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues24 >>> 10) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues24 >>> 2) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues24 & 3) << 6
                                                | (intValues25 >>> 12) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues25 >>> 4) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues25 & 15) << 4
                                                | (intValues26 >>> 14) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues26 >>> 6) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues26 & 63) << 2
                                                | (intValues27 >>> 16) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues28 >>> 10) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues28 >>> 2) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues28 & 3) << 6
                                                | (intValues29 >>> 12) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues29 >>> 4) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues29 & 15) << 4
                                                | (intValues30 >>> 14) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues30 >>> 6) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues30 & 63) << 2
                                                | (intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 73;
    }
  }

  static final class FrameCompressor19 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 11) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 3) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 7) << 5
                                                | (intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 17) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 9) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 1) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 & 1) << 7
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 15) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 >>> 7) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 & 127) << 1
                                                | (intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 13) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues6 >>> 5) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues6 & 31) << 3
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues8 >>> 11) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues8 >>> 3) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues8 & 7) << 5
                                                | (intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 17) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues10 >>> 9) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues10 >>> 1) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues10 & 1) << 7
                                                | (intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 15) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues12 >>> 7) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues12 & 127) << 1
                                                | (intValues13 >>> 18) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 13) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues14 >>> 5) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues14 & 31) << 3
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues16 >>> 11) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues16 >>> 3) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues16 & 7) << 5
                                                | (intValues17 >>> 14) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues17 >>> 6) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues17 & 63) << 2
                                                | (intValues18 >>> 17) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues18 >>> 9) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues18 >>> 1) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues18 & 1) << 7
                                                | (intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 15) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues20 >>> 7) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues20 & 127) << 1
                                                | (intValues21 >>> 18) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues21 >>> 10) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues21 >>> 2) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues21 & 3) << 6
                                                | (intValues22 >>> 13) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues22 >>> 5) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues22 & 31) << 3
                                                | (intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues24 >>> 11) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues24 >>> 3) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues24 & 7) << 5
                                                | (intValues25 >>> 14) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues25 >>> 6) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues25 & 63) << 2
                                                | (intValues26 >>> 17) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues26 >>> 9) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues26 >>> 1) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues26 & 1) << 7
                                                | (intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 15) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues28 >>> 7) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues28 & 127) << 1
                                                | (intValues29 >>> 18) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues29 >>> 10) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues29 >>> 2) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues29 & 3) << 6
                                                | (intValues30 >>> 13) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues30 >>> 5) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues30 & 31) << 3
                                                | (intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 77;
    }
  }

  static final class FrameCompressor20 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 12) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 4) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 15) << 4
                                                | (intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 12) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 & 15) << 4
                                                | (intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 >>> 12) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 >>> 4) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 & 15) << 4
                                                | (intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues6 >>> 12) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues6 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues6 & 15) << 4
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues8 >>> 12) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues8 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues8 & 15) << 4
                                                | (intValues9 >>> 16) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues10 >>> 12) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues10 >>> 4) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues10 & 15) << 4
                                                | (intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues12 >>> 12) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues12 >>> 4) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues12 & 15) << 4
                                                | (intValues13 >>> 16) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues14 >>> 12) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues14 >>> 4) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues14 & 15) << 4
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues16 >>> 12) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues16 >>> 4) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues16 & 15) << 4
                                                | (intValues17 >>> 16) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues17 >>> 8) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) (intValues17 & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues18 >>> 12) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues18 >>> 4) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues18 & 15) << 4
                                                | (intValues19 >>> 16) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues20 >>> 12) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues20 >>> 4) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues20 & 15) << 4
                                                | (intValues21 >>> 16) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues21 >>> 8) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) (intValues21 & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues22 >>> 12) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues22 >>> 4) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues22 & 15) << 4
                                                | (intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues24 >>> 12) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues24 >>> 4) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues24 & 15) << 4
                                                | (intValues25 >>> 16) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues25 >>> 8) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) (intValues25 & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues26 >>> 12) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues26 >>> 4) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues26 & 15) << 4
                                                | (intValues27 >>> 16) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues28 >>> 12) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues28 >>> 4) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues28 & 15) << 4
                                                | (intValues29 >>> 16) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues29 >>> 8) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) (intValues29 & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues30 >>> 12) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues30 >>> 4) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues30 & 15) << 4
                                                | (intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 81;
    }
  }

  static final class FrameCompressor21 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 13) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 5) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 31) << 3
                                                | (intValues1 >>> 18) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 15) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 7) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 & 127) << 1
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 17) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 >>> 9) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 1) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 & 1) << 7
                                                | (intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 19) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues6 >>> 11) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues6 >>> 3) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 & 7) << 5
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues8 >>> 13) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues8 >>> 5) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues8 & 31) << 3
                                                | (intValues9 >>> 18) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 15) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues10 >>> 7) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues10 & 127) << 1
                                                | (intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 17) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues12 >>> 9) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues12 >>> 1) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues12 & 1) << 7
                                                | (intValues13 >>> 14) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 19) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues14 >>> 11) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues14 >>> 3) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues14 & 7) << 5
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues16 >>> 13) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues16 >>> 5) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues16 & 31) << 3
                                                | (intValues17 >>> 18) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues17 >>> 10) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues17 >>> 2) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues17 & 3) << 6
                                                | (intValues18 >>> 15) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues18 >>> 7) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues18 & 127) << 1
                                                | (intValues19 >>> 20) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 17) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues20 >>> 9) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues20 >>> 1) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues20 & 1) << 7
                                                | (intValues21 >>> 14) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues21 >>> 6) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues21 & 63) << 2
                                                | (intValues22 >>> 19) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues22 >>> 11) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues22 >>> 3) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues22 & 7) << 5
                                                | (intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues24 >>> 13) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues24 >>> 5) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues24 & 31) << 3
                                                | (intValues25 >>> 18) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues25 >>> 10) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues25 >>> 2) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues25 & 3) << 6
                                                | (intValues26 >>> 15) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues26 >>> 7) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues26 & 127) << 1
                                                | (intValues27 >>> 20) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 17) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues28 >>> 9) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues28 >>> 1) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues28 & 1) << 7
                                                | (intValues29 >>> 14) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues29 >>> 6) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues29 & 63) << 2
                                                | (intValues30 >>> 19) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues30 >>> 11) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues30 >>> 3) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues30 & 7) << 5
                                                | (intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 85;
    }
  }

  static final class FrameCompressor22 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 14) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 6) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 63) << 2
                                                | (intValues1 >>> 20) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 18) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 10) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 2) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 & 3) << 6
                                                | (intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 >>> 14) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 6) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 & 63) << 2
                                                | (intValues5 >>> 20) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 18) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues6 >>> 10) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 >>> 2) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 & 3) << 6
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues8 >>> 14) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues8 >>> 6) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues8 & 63) << 2
                                                | (intValues9 >>> 20) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 18) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues10 >>> 10) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues10 >>> 2) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues10 & 3) << 6
                                                | (intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues12 >>> 14) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues12 >>> 6) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues12 & 63) << 2
                                                | (intValues13 >>> 20) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 18) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues14 >>> 10) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues14 >>> 2) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues14 & 3) << 6
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues16 >>> 14) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues16 >>> 6) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues16 & 63) << 2
                                                | (intValues17 >>> 20) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues17 >>> 12) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues17 >>> 4) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues17 & 15) << 4
                                                | (intValues18 >>> 18) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues18 >>> 10) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues18 >>> 2) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues18 & 3) << 6
                                                | (intValues19 >>> 16) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues20 >>> 14) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues20 >>> 6) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues20 & 63) << 2
                                                | (intValues21 >>> 20) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues21 >>> 12) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues21 >>> 4) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues21 & 15) << 4
                                                | (intValues22 >>> 18) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues22 >>> 10) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues22 >>> 2) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues22 & 3) << 6
                                                | (intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues24 >>> 14) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues24 >>> 6) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues24 & 63) << 2
                                                | (intValues25 >>> 20) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues25 >>> 12) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues25 >>> 4) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues25 & 15) << 4
                                                | (intValues26 >>> 18) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues26 >>> 10) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues26 >>> 2) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues26 & 3) << 6
                                                | (intValues27 >>> 16) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues28 >>> 14) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues28 >>> 6) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues28 & 63) << 2
                                                | (intValues29 >>> 20) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues29 >>> 12) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues29 >>> 4) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues29 & 15) << 4
                                                | (intValues30 >>> 18) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) ((intValues30 >>> 10) & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues30 >>> 2) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues30 & 3) << 6
                                                | (intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 87] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 89;
    }
  }

  static final class FrameCompressor23 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 15) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 7) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 127) << 1
                                                | (intValues1 >>> 22) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 21) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 13) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 5) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 & 31) << 3
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 19) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 11) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 3) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 & 7) << 5
                                                | (intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 17) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 >>> 9) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 >>> 1) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues6 & 1) << 7
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues8 >>> 15) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues8 >>> 7) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues8 & 127) << 1
                                                | (intValues9 >>> 22) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 21) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues10 >>> 13) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues10 >>> 5) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues10 & 31) << 3
                                                | (intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 19) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues12 >>> 11) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues12 >>> 3) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues12 & 7) << 5
                                                | (intValues13 >>> 18) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 17) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues14 >>> 9) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues14 >>> 1) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues14 & 1) << 7
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues16 >>> 15) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues16 >>> 7) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues16 & 127) << 1
                                                | (intValues17 >>> 22) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues17 >>> 14) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues17 >>> 6) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues17 & 63) << 2
                                                | (intValues18 >>> 21) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues18 >>> 13) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues18 >>> 5) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues18 & 31) << 3
                                                | (intValues19 >>> 20) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 19) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues20 >>> 11) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues20 >>> 3) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues20 & 7) << 5
                                                | (intValues21 >>> 18) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues21 >>> 10) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues21 >>> 2) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues21 & 3) << 6
                                                | (intValues22 >>> 17) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues22 >>> 9) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues22 >>> 1) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues22 & 1) << 7
                                                | (intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues24 >>> 15) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues24 >>> 7) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues24 & 127) << 1
                                                | (intValues25 >>> 22) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues25 >>> 14) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues25 >>> 6) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues25 & 63) << 2
                                                | (intValues26 >>> 21) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues26 >>> 13) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues26 >>> 5) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues26 & 31) << 3
                                                | (intValues27 >>> 20) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 19) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues28 >>> 11) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues28 >>> 3) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) ((intValues28 & 7) << 5
                                                | (intValues29 >>> 18) & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues29 >>> 10) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues29 >>> 2) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) ((intValues29 & 3) << 6
                                                | (intValues30 >>> 17) & 0xFF);
      compressedArray[outputOffset + 87] = (byte) ((intValues30 >>> 9) & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues30 >>> 1) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) ((intValues30 & 1) << 7
                                                | (intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 90] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 91] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 93;
    }
  }

  static final class FrameCompressor24 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 16) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 8) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) (intValues0 & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 16) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues2 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 16) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues4 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 >>> 16) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues6 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues8 >>> 16) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues8 >>> 8) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) (intValues8 & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues9 >>> 16) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues10 >>> 16) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues10 >>> 8) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) (intValues10 & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues12 >>> 16) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues12 >>> 8) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) (intValues12 & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues13 >>> 16) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues14 >>> 16) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues14 >>> 8) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) (intValues14 & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues16 >>> 16) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues16 >>> 8) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) (intValues16 & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues17 >>> 16) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues17 >>> 8) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) (intValues17 & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues18 >>> 16) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues18 >>> 8) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) (intValues18 & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues19 >>> 16) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues20 >>> 16) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues20 >>> 8) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) (intValues20 & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues21 >>> 16) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues21 >>> 8) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) (intValues21 & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues22 >>> 16) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues22 >>> 8) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) (intValues22 & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues24 >>> 16) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues24 >>> 8) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) (intValues24 & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues25 >>> 16) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues25 >>> 8) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) (intValues25 & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues26 >>> 16) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues26 >>> 8) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) (intValues26 & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues27 >>> 16) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues28 >>> 16) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues28 >>> 8) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) (intValues28 & 0xFF);
      compressedArray[outputOffset + 87] = (byte) ((intValues29 >>> 16) & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues29 >>> 8) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) (intValues29 & 0xFF);
      compressedArray[outputOffset + 90] = (byte) ((intValues30 >>> 16) & 0xFF);
      compressedArray[outputOffset + 91] = (byte) ((intValues30 >>> 8) & 0xFF);
      compressedArray[outputOffset + 92] = (byte) (intValues30 & 0xFF);
      compressedArray[outputOffset + 93] = (byte) ((intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 94] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 95] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 97;
    }
  }

  static final class FrameCompressor25 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 17) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 9) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 1) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 1) << 7
                                                | (intValues1 >>> 18) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 19) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 11) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 3) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 & 7) << 5
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 21) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 13) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 5) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 & 31) << 3
                                                | (intValues5 >>> 22) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 23) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 >>> 15) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues6 >>> 7) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 & 127) << 1
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues8 >>> 17) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues8 >>> 9) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues8 >>> 1) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues8 & 1) << 7
                                                | (intValues9 >>> 18) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 19) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues10 >>> 11) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues10 >>> 3) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues10 & 7) << 5
                                                | (intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 21) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues12 >>> 13) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues12 >>> 5) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues12 & 31) << 3
                                                | (intValues13 >>> 22) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues13 >>> 14) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 23) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues14 >>> 15) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues14 >>> 7) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues14 & 127) << 1
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues16 >>> 17) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues16 >>> 9) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues16 >>> 1) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues16 & 1) << 7
                                                | (intValues17 >>> 18) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues17 >>> 10) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues17 >>> 2) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues17 & 3) << 6
                                                | (intValues18 >>> 19) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues18 >>> 11) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues18 >>> 3) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues18 & 7) << 5
                                                | (intValues19 >>> 20) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 21) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues20 >>> 13) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues20 >>> 5) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues20 & 31) << 3
                                                | (intValues21 >>> 22) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues21 >>> 14) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues21 >>> 6) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues21 & 63) << 2
                                                | (intValues22 >>> 23) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues22 >>> 15) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues22 >>> 7) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues22 & 127) << 1
                                                | (intValues23 >>> 24) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues24 >>> 17) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues24 >>> 9) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues24 >>> 1) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues24 & 1) << 7
                                                | (intValues25 >>> 18) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues25 >>> 10) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues25 >>> 2) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues25 & 3) << 6
                                                | (intValues26 >>> 19) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues26 >>> 11) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) ((intValues26 >>> 3) & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues26 & 7) << 5
                                                | (intValues27 >>> 20) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 87] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 21) & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues28 >>> 13) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) ((intValues28 >>> 5) & 0xFF);
      compressedArray[outputOffset + 90] = (byte) ((intValues28 & 31) << 3
                                                | (intValues29 >>> 22) & 0xFF);
      compressedArray[outputOffset + 91] = (byte) ((intValues29 >>> 14) & 0xFF);
      compressedArray[outputOffset + 92] = (byte) ((intValues29 >>> 6) & 0xFF);
      compressedArray[outputOffset + 93] = (byte) ((intValues29 & 63) << 2
                                                | (intValues30 >>> 23) & 0xFF);
      compressedArray[outputOffset + 94] = (byte) ((intValues30 >>> 15) & 0xFF);
      compressedArray[outputOffset + 95] = (byte) ((intValues30 >>> 7) & 0xFF);
      compressedArray[outputOffset + 96] = (byte) ((intValues30 & 127) << 1
                                                | (intValues31 >>> 24) & 0xFF);
      compressedArray[outputOffset + 97] = (byte) ((intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 98] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 99] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 101;
    }
  }

  static final class FrameCompressor26 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 18) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 10) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 2) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 3) << 6
                                                | (intValues1 >>> 20) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 22) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 14) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 6) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 & 63) << 2
                                                | (intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 18) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 10) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 2) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 & 3) << 6
                                                | (intValues5 >>> 20) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 22) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues6 >>> 14) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 >>> 6) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 & 63) << 2
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues8 >>> 18) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues8 >>> 10) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues8 >>> 2) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues8 & 3) << 6
                                                | (intValues9 >>> 20) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 22) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues10 >>> 14) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues10 >>> 6) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues10 & 63) << 2
                                                | (intValues11 >>> 24) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues12 >>> 18) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues12 >>> 10) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues12 >>> 2) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues12 & 3) << 6
                                                | (intValues13 >>> 20) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 22) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues14 >>> 14) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues14 >>> 6) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues14 & 63) << 2
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues16 >>> 18) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues16 >>> 10) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues16 >>> 2) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues16 & 3) << 6
                                                | (intValues17 >>> 20) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues17 >>> 12) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues17 >>> 4) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues17 & 15) << 4
                                                | (intValues18 >>> 22) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues18 >>> 14) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues18 >>> 6) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues18 & 63) << 2
                                                | (intValues19 >>> 24) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues19 >>> 16) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues20 >>> 18) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues20 >>> 10) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues20 >>> 2) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues20 & 3) << 6
                                                | (intValues21 >>> 20) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues21 >>> 12) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues21 >>> 4) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues21 & 15) << 4
                                                | (intValues22 >>> 22) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues22 >>> 14) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues22 >>> 6) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues22 & 63) << 2
                                                | (intValues23 >>> 24) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues24 >>> 18) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues24 >>> 10) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues24 >>> 2) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues24 & 3) << 6
                                                | (intValues25 >>> 20) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues25 >>> 12) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) ((intValues25 >>> 4) & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues25 & 15) << 4
                                                | (intValues26 >>> 22) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues26 >>> 14) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) ((intValues26 >>> 6) & 0xFF);
      compressedArray[outputOffset + 87] = (byte) ((intValues26 & 63) << 2
                                                | (intValues27 >>> 24) & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues27 >>> 16) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) ((intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 90] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 91] = (byte) ((intValues28 >>> 18) & 0xFF);
      compressedArray[outputOffset + 92] = (byte) ((intValues28 >>> 10) & 0xFF);
      compressedArray[outputOffset + 93] = (byte) ((intValues28 >>> 2) & 0xFF);
      compressedArray[outputOffset + 94] = (byte) ((intValues28 & 3) << 6
                                                | (intValues29 >>> 20) & 0xFF);
      compressedArray[outputOffset + 95] = (byte) ((intValues29 >>> 12) & 0xFF);
      compressedArray[outputOffset + 96] = (byte) ((intValues29 >>> 4) & 0xFF);
      compressedArray[outputOffset + 97] = (byte) ((intValues29 & 15) << 4
                                                | (intValues30 >>> 22) & 0xFF);
      compressedArray[outputOffset + 98] = (byte) ((intValues30 >>> 14) & 0xFF);
      compressedArray[outputOffset + 99] = (byte) ((intValues30 >>> 6) & 0xFF);
      compressedArray[outputOffset + 100] = (byte) ((intValues30 & 63) << 2
                                                | (intValues31 >>> 24) & 0xFF);
      compressedArray[outputOffset + 101] = (byte) ((intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 102] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 103] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 105;
    }
  }

  static final class FrameCompressor27 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 19) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 11) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 3) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 7) << 5
                                                | (intValues1 >>> 22) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 25) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 17) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 9) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 1) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 & 1) << 7
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 23) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 15) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 7) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 & 127) << 1
                                                | (intValues5 >>> 26) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 21) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 >>> 13) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 >>> 5) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 & 31) << 3
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues8 >>> 19) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues8 >>> 11) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues8 >>> 3) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues8 & 7) << 5
                                                | (intValues9 >>> 22) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 25) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues10 >>> 17) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues10 >>> 9) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues10 >>> 1) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues10 & 1) << 7
                                                | (intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 23) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues12 >>> 15) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues12 >>> 7) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues12 & 127) << 1
                                                | (intValues13 >>> 26) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues13 >>> 18) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 21) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues14 >>> 13) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues14 >>> 5) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues14 & 31) << 3
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues16 >>> 19) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues16 >>> 11) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues16 >>> 3) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues16 & 7) << 5
                                                | (intValues17 >>> 22) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues17 >>> 14) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues17 >>> 6) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues17 & 63) << 2
                                                | (intValues18 >>> 25) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues18 >>> 17) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues18 >>> 9) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues18 >>> 1) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues18 & 1) << 7
                                                | (intValues19 >>> 20) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 23) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues20 >>> 15) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues20 >>> 7) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues20 & 127) << 1
                                                | (intValues21 >>> 26) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues21 >>> 18) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues21 >>> 10) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues21 >>> 2) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues21 & 3) << 6
                                                | (intValues22 >>> 21) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues22 >>> 13) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues22 >>> 5) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues22 & 31) << 3
                                                | (intValues23 >>> 24) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues24 >>> 19) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues24 >>> 11) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) ((intValues24 >>> 3) & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues24 & 7) << 5
                                                | (intValues25 >>> 22) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues25 >>> 14) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) ((intValues25 >>> 6) & 0xFF);
      compressedArray[outputOffset + 87] = (byte) ((intValues25 & 63) << 2
                                                | (intValues26 >>> 25) & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues26 >>> 17) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) ((intValues26 >>> 9) & 0xFF);
      compressedArray[outputOffset + 90] = (byte) ((intValues26 >>> 1) & 0xFF);
      compressedArray[outputOffset + 91] = (byte) ((intValues26 & 1) << 7
                                                | (intValues27 >>> 20) & 0xFF);
      compressedArray[outputOffset + 92] = (byte) ((intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 93] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 94] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 23) & 0xFF);
      compressedArray[outputOffset + 95] = (byte) ((intValues28 >>> 15) & 0xFF);
      compressedArray[outputOffset + 96] = (byte) ((intValues28 >>> 7) & 0xFF);
      compressedArray[outputOffset + 97] = (byte) ((intValues28 & 127) << 1
                                                | (intValues29 >>> 26) & 0xFF);
      compressedArray[outputOffset + 98] = (byte) ((intValues29 >>> 18) & 0xFF);
      compressedArray[outputOffset + 99] = (byte) ((intValues29 >>> 10) & 0xFF);
      compressedArray[outputOffset + 100] = (byte) ((intValues29 >>> 2) & 0xFF);
      compressedArray[outputOffset + 101] = (byte) ((intValues29 & 3) << 6
                                                | (intValues30 >>> 21) & 0xFF);
      compressedArray[outputOffset + 102] = (byte) ((intValues30 >>> 13) & 0xFF);
      compressedArray[outputOffset + 103] = (byte) ((intValues30 >>> 5) & 0xFF);
      compressedArray[outputOffset + 104] = (byte) ((intValues30 & 31) << 3
                                                | (intValues31 >>> 24) & 0xFF);
      compressedArray[outputOffset + 105] = (byte) ((intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 106] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 107] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 109;
    }
  }

  static final class FrameCompressor28 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 20) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 12) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 4) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 15) << 4
                                                | (intValues1 >>> 24) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 20) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 12) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 & 15) << 4
                                                | (intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 20) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 12) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 & 15) << 4
                                                | (intValues5 >>> 24) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 >>> 20) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 >>> 12) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 >>> 4) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 & 15) << 4
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues8 >>> 20) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues8 >>> 12) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues8 >>> 4) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues8 & 15) << 4
                                                | (intValues9 >>> 24) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues9 >>> 16) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues10 >>> 20) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues10 >>> 12) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues10 >>> 4) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues10 & 15) << 4
                                                | (intValues11 >>> 24) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues12 >>> 20) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues12 >>> 12) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues12 >>> 4) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues12 & 15) << 4
                                                | (intValues13 >>> 24) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues13 >>> 16) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues14 >>> 20) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues14 >>> 12) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues14 >>> 4) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues14 & 15) << 4
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues16 >>> 20) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues16 >>> 12) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues16 >>> 4) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues16 & 15) << 4
                                                | (intValues17 >>> 24) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues17 >>> 16) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues17 >>> 8) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) (intValues17 & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues18 >>> 20) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues18 >>> 12) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues18 >>> 4) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues18 & 15) << 4
                                                | (intValues19 >>> 24) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues19 >>> 16) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues20 >>> 20) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues20 >>> 12) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues20 >>> 4) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues20 & 15) << 4
                                                | (intValues21 >>> 24) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues21 >>> 16) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues21 >>> 8) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) (intValues21 & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues22 >>> 20) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues22 >>> 12) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues22 >>> 4) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues22 & 15) << 4
                                                | (intValues23 >>> 24) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues24 >>> 20) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues24 >>> 12) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) ((intValues24 >>> 4) & 0xFF);
      compressedArray[outputOffset + 87] = (byte) ((intValues24 & 15) << 4
                                                | (intValues25 >>> 24) & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues25 >>> 16) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) ((intValues25 >>> 8) & 0xFF);
      compressedArray[outputOffset + 90] = (byte) (intValues25 & 0xFF);
      compressedArray[outputOffset + 91] = (byte) ((intValues26 >>> 20) & 0xFF);
      compressedArray[outputOffset + 92] = (byte) ((intValues26 >>> 12) & 0xFF);
      compressedArray[outputOffset + 93] = (byte) ((intValues26 >>> 4) & 0xFF);
      compressedArray[outputOffset + 94] = (byte) ((intValues26 & 15) << 4
                                                | (intValues27 >>> 24) & 0xFF);
      compressedArray[outputOffset + 95] = (byte) ((intValues27 >>> 16) & 0xFF);
      compressedArray[outputOffset + 96] = (byte) ((intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 97] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 98] = (byte) ((intValues28 >>> 20) & 0xFF);
      compressedArray[outputOffset + 99] = (byte) ((intValues28 >>> 12) & 0xFF);
      compressedArray[outputOffset + 100] = (byte) ((intValues28 >>> 4) & 0xFF);
      compressedArray[outputOffset + 101] = (byte) ((intValues28 & 15) << 4
                                                | (intValues29 >>> 24) & 0xFF);
      compressedArray[outputOffset + 102] = (byte) ((intValues29 >>> 16) & 0xFF);
      compressedArray[outputOffset + 103] = (byte) ((intValues29 >>> 8) & 0xFF);
      compressedArray[outputOffset + 104] = (byte) (intValues29 & 0xFF);
      compressedArray[outputOffset + 105] = (byte) ((intValues30 >>> 20) & 0xFF);
      compressedArray[outputOffset + 106] = (byte) ((intValues30 >>> 12) & 0xFF);
      compressedArray[outputOffset + 107] = (byte) ((intValues30 >>> 4) & 0xFF);
      compressedArray[outputOffset + 108] = (byte) ((intValues30 & 15) << 4
                                                | (intValues31 >>> 24) & 0xFF);
      compressedArray[outputOffset + 109] = (byte) ((intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 110] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 111] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 113;
    }
  }

  static final class FrameCompressor29 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 21) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 13) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 5) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 31) << 3
                                                | (intValues1 >>> 26) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 18) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 23) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 15) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 7) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 & 127) << 1
                                                | (intValues3 >>> 28) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 25) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 17) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 9) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 1) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 & 1) << 7
                                                | (intValues5 >>> 22) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 27) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 >>> 19) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 >>> 11) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 3) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 & 7) << 5
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues8 >>> 21) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues8 >>> 13) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues8 >>> 5) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues8 & 31) << 3
                                                | (intValues9 >>> 26) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues9 >>> 18) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 23) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues10 >>> 15) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues10 >>> 7) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues10 & 127) << 1
                                                | (intValues11 >>> 28) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 25) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues12 >>> 17) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues12 >>> 9) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues12 >>> 1) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues12 & 1) << 7
                                                | (intValues13 >>> 22) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues13 >>> 14) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 27) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues14 >>> 19) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues14 >>> 11) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues14 >>> 3) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues14 & 7) << 5
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues16 >>> 21) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues16 >>> 13) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues16 >>> 5) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues16 & 31) << 3
                                                | (intValues17 >>> 26) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues17 >>> 18) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues17 >>> 10) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues17 >>> 2) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues17 & 3) << 6
                                                | (intValues18 >>> 23) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues18 >>> 15) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues18 >>> 7) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues18 & 127) << 1
                                                | (intValues19 >>> 28) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues19 >>> 20) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 25) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues20 >>> 17) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues20 >>> 9) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues20 >>> 1) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues20 & 1) << 7
                                                | (intValues21 >>> 22) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues21 >>> 14) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues21 >>> 6) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues21 & 63) << 2
                                                | (intValues22 >>> 27) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues22 >>> 19) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues22 >>> 11) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues22 >>> 3) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) ((intValues22 & 7) << 5
                                                | (intValues23 >>> 24) & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 87] = (byte) ((intValues24 >>> 21) & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues24 >>> 13) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) ((intValues24 >>> 5) & 0xFF);
      compressedArray[outputOffset + 90] = (byte) ((intValues24 & 31) << 3
                                                | (intValues25 >>> 26) & 0xFF);
      compressedArray[outputOffset + 91] = (byte) ((intValues25 >>> 18) & 0xFF);
      compressedArray[outputOffset + 92] = (byte) ((intValues25 >>> 10) & 0xFF);
      compressedArray[outputOffset + 93] = (byte) ((intValues25 >>> 2) & 0xFF);
      compressedArray[outputOffset + 94] = (byte) ((intValues25 & 3) << 6
                                                | (intValues26 >>> 23) & 0xFF);
      compressedArray[outputOffset + 95] = (byte) ((intValues26 >>> 15) & 0xFF);
      compressedArray[outputOffset + 96] = (byte) ((intValues26 >>> 7) & 0xFF);
      compressedArray[outputOffset + 97] = (byte) ((intValues26 & 127) << 1
                                                | (intValues27 >>> 28) & 0xFF);
      compressedArray[outputOffset + 98] = (byte) ((intValues27 >>> 20) & 0xFF);
      compressedArray[outputOffset + 99] = (byte) ((intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 100] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 101] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 25) & 0xFF);
      compressedArray[outputOffset + 102] = (byte) ((intValues28 >>> 17) & 0xFF);
      compressedArray[outputOffset + 103] = (byte) ((intValues28 >>> 9) & 0xFF);
      compressedArray[outputOffset + 104] = (byte) ((intValues28 >>> 1) & 0xFF);
      compressedArray[outputOffset + 105] = (byte) ((intValues28 & 1) << 7
                                                | (intValues29 >>> 22) & 0xFF);
      compressedArray[outputOffset + 106] = (byte) ((intValues29 >>> 14) & 0xFF);
      compressedArray[outputOffset + 107] = (byte) ((intValues29 >>> 6) & 0xFF);
      compressedArray[outputOffset + 108] = (byte) ((intValues29 & 63) << 2
                                                | (intValues30 >>> 27) & 0xFF);
      compressedArray[outputOffset + 109] = (byte) ((intValues30 >>> 19) & 0xFF);
      compressedArray[outputOffset + 110] = (byte) ((intValues30 >>> 11) & 0xFF);
      compressedArray[outputOffset + 111] = (byte) ((intValues30 >>> 3) & 0xFF);
      compressedArray[outputOffset + 112] = (byte) ((intValues30 & 7) << 5
                                                | (intValues31 >>> 24) & 0xFF);
      compressedArray[outputOffset + 113] = (byte) ((intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 114] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 115] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 117;
    }
  }

  static final class FrameCompressor30 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 22) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 14) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 6) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 63) << 2
                                                | (intValues1 >>> 28) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 20) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 26) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 18) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 10) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues2 & 3) << 6
                                                | (intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 22) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 14) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 & 63) << 2
                                                | (intValues5 >>> 28) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 20) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 26) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 >>> 18) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 10) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues6 & 3) << 6
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues8 >>> 22) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues8 >>> 14) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues8 >>> 6) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues8 & 63) << 2
                                                | (intValues9 >>> 28) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues9 >>> 20) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 26) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues10 >>> 18) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues10 >>> 10) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues10 >>> 2) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues10 & 3) << 6
                                                | (intValues11 >>> 24) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues12 >>> 22) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues12 >>> 14) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues12 >>> 6) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues12 & 63) << 2
                                                | (intValues13 >>> 28) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues13 >>> 20) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 26) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues14 >>> 18) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues14 >>> 10) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues14 >>> 2) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues14 & 3) << 6
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues16 >>> 22) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues16 >>> 14) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues16 >>> 6) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues16 & 63) << 2
                                                | (intValues17 >>> 28) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues17 >>> 20) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues17 >>> 12) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues17 >>> 4) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues17 & 15) << 4
                                                | (intValues18 >>> 26) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues18 >>> 18) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues18 >>> 10) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues18 >>> 2) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues18 & 3) << 6
                                                | (intValues19 >>> 24) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues19 >>> 16) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues20 >>> 22) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues20 >>> 14) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues20 >>> 6) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues20 & 63) << 2
                                                | (intValues21 >>> 28) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues21 >>> 20) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues21 >>> 12) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues21 >>> 4) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues21 & 15) << 4
                                                | (intValues22 >>> 26) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) ((intValues22 >>> 18) & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues22 >>> 10) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues22 >>> 2) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) ((intValues22 & 3) << 6
                                                | (intValues23 >>> 24) & 0xFF);
      compressedArray[outputOffset + 87] = (byte) ((intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 90] = (byte) ((intValues24 >>> 22) & 0xFF);
      compressedArray[outputOffset + 91] = (byte) ((intValues24 >>> 14) & 0xFF);
      compressedArray[outputOffset + 92] = (byte) ((intValues24 >>> 6) & 0xFF);
      compressedArray[outputOffset + 93] = (byte) ((intValues24 & 63) << 2
                                                | (intValues25 >>> 28) & 0xFF);
      compressedArray[outputOffset + 94] = (byte) ((intValues25 >>> 20) & 0xFF);
      compressedArray[outputOffset + 95] = (byte) ((intValues25 >>> 12) & 0xFF);
      compressedArray[outputOffset + 96] = (byte) ((intValues25 >>> 4) & 0xFF);
      compressedArray[outputOffset + 97] = (byte) ((intValues25 & 15) << 4
                                                | (intValues26 >>> 26) & 0xFF);
      compressedArray[outputOffset + 98] = (byte) ((intValues26 >>> 18) & 0xFF);
      compressedArray[outputOffset + 99] = (byte) ((intValues26 >>> 10) & 0xFF);
      compressedArray[outputOffset + 100] = (byte) ((intValues26 >>> 2) & 0xFF);
      compressedArray[outputOffset + 101] = (byte) ((intValues26 & 3) << 6
                                                | (intValues27 >>> 24) & 0xFF);
      compressedArray[outputOffset + 102] = (byte) ((intValues27 >>> 16) & 0xFF);
      compressedArray[outputOffset + 103] = (byte) ((intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 104] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 105] = (byte) ((intValues28 >>> 22) & 0xFF);
      compressedArray[outputOffset + 106] = (byte) ((intValues28 >>> 14) & 0xFF);
      compressedArray[outputOffset + 107] = (byte) ((intValues28 >>> 6) & 0xFF);
      compressedArray[outputOffset + 108] = (byte) ((intValues28 & 63) << 2
                                                | (intValues29 >>> 28) & 0xFF);
      compressedArray[outputOffset + 109] = (byte) ((intValues29 >>> 20) & 0xFF);
      compressedArray[outputOffset + 110] = (byte) ((intValues29 >>> 12) & 0xFF);
      compressedArray[outputOffset + 111] = (byte) ((intValues29 >>> 4) & 0xFF);
      compressedArray[outputOffset + 112] = (byte) ((intValues29 & 15) << 4
                                                | (intValues30 >>> 26) & 0xFF);
      compressedArray[outputOffset + 113] = (byte) ((intValues30 >>> 18) & 0xFF);
      compressedArray[outputOffset + 114] = (byte) ((intValues30 >>> 10) & 0xFF);
      compressedArray[outputOffset + 115] = (byte) ((intValues30 >>> 2) & 0xFF);
      compressedArray[outputOffset + 116] = (byte) ((intValues30 & 3) << 6
                                                | (intValues31 >>> 24) & 0xFF);
      compressedArray[outputOffset + 117] = (byte) ((intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 118] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 119] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 121;
    }
  }

  static final class FrameCompressor31 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 23) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 15) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 7) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 127) << 1
                                                | (intValues1 >>> 30) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 22) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 29) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 21) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 13) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 >>> 5) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues2 & 31) << 3
                                                | (intValues3 >>> 28) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 27) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 19) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 11) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 >>> 3) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues4 & 7) << 5
                                                | (intValues5 >>> 26) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 25) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 17) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 >>> 9) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues6 >>> 1) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues6 & 1) << 7
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues8 >>> 23) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues8 >>> 15) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues8 >>> 7) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues8 & 127) << 1
                                                | (intValues9 >>> 30) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues9 >>> 22) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 29) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues10 >>> 21) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues10 >>> 13) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues10 >>> 5) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues10 & 31) << 3
                                                | (intValues11 >>> 28) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 27) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues12 >>> 19) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues12 >>> 11) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues12 >>> 3) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues12 & 7) << 5
                                                | (intValues13 >>> 26) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues13 >>> 18) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 25) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues14 >>> 17) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues14 >>> 9) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues14 >>> 1) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues14 & 1) << 7
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues16 >>> 23) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) ((intValues16 >>> 15) & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues16 >>> 7) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues16 & 127) << 1
                                                | (intValues17 >>> 30) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues17 >>> 22) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) ((intValues17 >>> 14) & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues17 >>> 6) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues17 & 63) << 2
                                                | (intValues18 >>> 29) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues18 >>> 21) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) ((intValues18 >>> 13) & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues18 >>> 5) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues18 & 31) << 3
                                                | (intValues19 >>> 28) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues19 >>> 20) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) ((intValues19 >>> 12) & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues19 >>> 4) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues19 & 15) << 4
                                                | (intValues20 >>> 27) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues20 >>> 19) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) ((intValues20 >>> 11) & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues20 >>> 3) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues20 & 7) << 5
                                                | (intValues21 >>> 26) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues21 >>> 18) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) ((intValues21 >>> 10) & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues21 >>> 2) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues21 & 3) << 6
                                                | (intValues22 >>> 25) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) ((intValues22 >>> 17) & 0xFF);
      compressedArray[outputOffset + 87] = (byte) ((intValues22 >>> 9) & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues22 >>> 1) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) ((intValues22 & 1) << 7
                                                | (intValues23 >>> 24) & 0xFF);
      compressedArray[outputOffset + 90] = (byte) ((intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 91] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 92] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 93] = (byte) ((intValues24 >>> 23) & 0xFF);
      compressedArray[outputOffset + 94] = (byte) ((intValues24 >>> 15) & 0xFF);
      compressedArray[outputOffset + 95] = (byte) ((intValues24 >>> 7) & 0xFF);
      compressedArray[outputOffset + 96] = (byte) ((intValues24 & 127) << 1
                                                | (intValues25 >>> 30) & 0xFF);
      compressedArray[outputOffset + 97] = (byte) ((intValues25 >>> 22) & 0xFF);
      compressedArray[outputOffset + 98] = (byte) ((intValues25 >>> 14) & 0xFF);
      compressedArray[outputOffset + 99] = (byte) ((intValues25 >>> 6) & 0xFF);
      compressedArray[outputOffset + 100] = (byte) ((intValues25 & 63) << 2
                                                | (intValues26 >>> 29) & 0xFF);
      compressedArray[outputOffset + 101] = (byte) ((intValues26 >>> 21) & 0xFF);
      compressedArray[outputOffset + 102] = (byte) ((intValues26 >>> 13) & 0xFF);
      compressedArray[outputOffset + 103] = (byte) ((intValues26 >>> 5) & 0xFF);
      compressedArray[outputOffset + 104] = (byte) ((intValues26 & 31) << 3
                                                | (intValues27 >>> 28) & 0xFF);
      compressedArray[outputOffset + 105] = (byte) ((intValues27 >>> 20) & 0xFF);
      compressedArray[outputOffset + 106] = (byte) ((intValues27 >>> 12) & 0xFF);
      compressedArray[outputOffset + 107] = (byte) ((intValues27 >>> 4) & 0xFF);
      compressedArray[outputOffset + 108] = (byte) ((intValues27 & 15) << 4
                                                | (intValues28 >>> 27) & 0xFF);
      compressedArray[outputOffset + 109] = (byte) ((intValues28 >>> 19) & 0xFF);
      compressedArray[outputOffset + 110] = (byte) ((intValues28 >>> 11) & 0xFF);
      compressedArray[outputOffset + 111] = (byte) ((intValues28 >>> 3) & 0xFF);
      compressedArray[outputOffset + 112] = (byte) ((intValues28 & 7) << 5
                                                | (intValues29 >>> 26) & 0xFF);
      compressedArray[outputOffset + 113] = (byte) ((intValues29 >>> 18) & 0xFF);
      compressedArray[outputOffset + 114] = (byte) ((intValues29 >>> 10) & 0xFF);
      compressedArray[outputOffset + 115] = (byte) ((intValues29 >>> 2) & 0xFF);
      compressedArray[outputOffset + 116] = (byte) ((intValues29 & 3) << 6
                                                | (intValues30 >>> 25) & 0xFF);
      compressedArray[outputOffset + 117] = (byte) ((intValues30 >>> 17) & 0xFF);
      compressedArray[outputOffset + 118] = (byte) ((intValues30 >>> 9) & 0xFF);
      compressedArray[outputOffset + 119] = (byte) ((intValues30 >>> 1) & 0xFF);
      compressedArray[outputOffset + 120] = (byte) ((intValues30 & 1) << 7
                                                | (intValues31 >>> 24) & 0xFF);
      compressedArray[outputOffset + 121] = (byte) ((intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 122] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 123] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 125;
    }
  }

  static final class FrameCompressor32 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];
      final int intValues16 = unCompressedData[offset + 16], intValues17 = unCompressedData[offset + 17];
      final int intValues18 = unCompressedData[offset + 18], intValues19 = unCompressedData[offset + 19];
      final int intValues20 = unCompressedData[offset + 20], intValues21 = unCompressedData[offset + 21];
      final int intValues22 = unCompressedData[offset + 22], intValues23 = unCompressedData[offset + 23];
      final int intValues24 = unCompressedData[offset + 24], intValues25 = unCompressedData[offset + 25];
      final int intValues26 = unCompressedData[offset + 26], intValues27 = unCompressedData[offset + 27];
      final int intValues28 = unCompressedData[offset + 28], intValues29 = unCompressedData[offset + 29];
      final int intValues30 = unCompressedData[offset + 30], intValues31 = unCompressedData[offset + 31];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 24) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 16) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 8) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) (intValues0 & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 24) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 24) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 16) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues2 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 24) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 16) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues4 & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 24) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 24) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 >>> 16) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues6 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues6 & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues8 >>> 24) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues8 >>> 16) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues8 >>> 8) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) (intValues8 & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues9 >>> 24) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues9 >>> 16) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues10 >>> 24) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues10 >>> 16) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues10 >>> 8) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) (intValues10 & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues11 >>> 24) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues12 >>> 24) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues12 >>> 16) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues12 >>> 8) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) (intValues12 & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues13 >>> 24) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues13 >>> 16) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues14 >>> 24) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues14 >>> 16) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues14 >>> 8) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) (intValues14 & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) (intValues15 & 0xFF);
      compressedArray[outputOffset + 64] = (byte) ((intValues16 >>> 24) & 0xFF);
      compressedArray[outputOffset + 65] = (byte) ((intValues16 >>> 16) & 0xFF);
      compressedArray[outputOffset + 66] = (byte) ((intValues16 >>> 8) & 0xFF);
      compressedArray[outputOffset + 67] = (byte) (intValues16 & 0xFF);
      compressedArray[outputOffset + 68] = (byte) ((intValues17 >>> 24) & 0xFF);
      compressedArray[outputOffset + 69] = (byte) ((intValues17 >>> 16) & 0xFF);
      compressedArray[outputOffset + 70] = (byte) ((intValues17 >>> 8) & 0xFF);
      compressedArray[outputOffset + 71] = (byte) (intValues17 & 0xFF);
      compressedArray[outputOffset + 72] = (byte) ((intValues18 >>> 24) & 0xFF);
      compressedArray[outputOffset + 73] = (byte) ((intValues18 >>> 16) & 0xFF);
      compressedArray[outputOffset + 74] = (byte) ((intValues18 >>> 8) & 0xFF);
      compressedArray[outputOffset + 75] = (byte) (intValues18 & 0xFF);
      compressedArray[outputOffset + 76] = (byte) ((intValues19 >>> 24) & 0xFF);
      compressedArray[outputOffset + 77] = (byte) ((intValues19 >>> 16) & 0xFF);
      compressedArray[outputOffset + 78] = (byte) ((intValues19 >>> 8) & 0xFF);
      compressedArray[outputOffset + 79] = (byte) (intValues19 & 0xFF);
      compressedArray[outputOffset + 80] = (byte) ((intValues20 >>> 24) & 0xFF);
      compressedArray[outputOffset + 81] = (byte) ((intValues20 >>> 16) & 0xFF);
      compressedArray[outputOffset + 82] = (byte) ((intValues20 >>> 8) & 0xFF);
      compressedArray[outputOffset + 83] = (byte) (intValues20 & 0xFF);
      compressedArray[outputOffset + 84] = (byte) ((intValues21 >>> 24) & 0xFF);
      compressedArray[outputOffset + 85] = (byte) ((intValues21 >>> 16) & 0xFF);
      compressedArray[outputOffset + 86] = (byte) ((intValues21 >>> 8) & 0xFF);
      compressedArray[outputOffset + 87] = (byte) (intValues21 & 0xFF);
      compressedArray[outputOffset + 88] = (byte) ((intValues22 >>> 24) & 0xFF);
      compressedArray[outputOffset + 89] = (byte) ((intValues22 >>> 16) & 0xFF);
      compressedArray[outputOffset + 90] = (byte) ((intValues22 >>> 8) & 0xFF);
      compressedArray[outputOffset + 91] = (byte) (intValues22 & 0xFF);
      compressedArray[outputOffset + 92] = (byte) ((intValues23 >>> 24) & 0xFF);
      compressedArray[outputOffset + 93] = (byte) ((intValues23 >>> 16) & 0xFF);
      compressedArray[outputOffset + 94] = (byte) ((intValues23 >>> 8) & 0xFF);
      compressedArray[outputOffset + 95] = (byte) (intValues23 & 0xFF);
      compressedArray[outputOffset + 96] = (byte) ((intValues24 >>> 24) & 0xFF);
      compressedArray[outputOffset + 97] = (byte) ((intValues24 >>> 16) & 0xFF);
      compressedArray[outputOffset + 98] = (byte) ((intValues24 >>> 8) & 0xFF);
      compressedArray[outputOffset + 99] = (byte) (intValues24 & 0xFF);
      compressedArray[outputOffset + 100] = (byte) ((intValues25 >>> 24) & 0xFF);
      compressedArray[outputOffset + 101] = (byte) ((intValues25 >>> 16) & 0xFF);
      compressedArray[outputOffset + 102] = (byte) ((intValues25 >>> 8) & 0xFF);
      compressedArray[outputOffset + 103] = (byte) (intValues25 & 0xFF);
      compressedArray[outputOffset + 104] = (byte) ((intValues26 >>> 24) & 0xFF);
      compressedArray[outputOffset + 105] = (byte) ((intValues26 >>> 16) & 0xFF);
      compressedArray[outputOffset + 106] = (byte) ((intValues26 >>> 8) & 0xFF);
      compressedArray[outputOffset + 107] = (byte) (intValues26 & 0xFF);
      compressedArray[outputOffset + 108] = (byte) ((intValues27 >>> 24) & 0xFF);
      compressedArray[outputOffset + 109] = (byte) ((intValues27 >>> 16) & 0xFF);
      compressedArray[outputOffset + 110] = (byte) ((intValues27 >>> 8) & 0xFF);
      compressedArray[outputOffset + 111] = (byte) (intValues27 & 0xFF);
      compressedArray[outputOffset + 112] = (byte) ((intValues28 >>> 24) & 0xFF);
      compressedArray[outputOffset + 113] = (byte) ((intValues28 >>> 16) & 0xFF);
      compressedArray[outputOffset + 114] = (byte) ((intValues28 >>> 8) & 0xFF);
      compressedArray[outputOffset + 115] = (byte) (intValues28 & 0xFF);
      compressedArray[outputOffset + 116] = (byte) ((intValues29 >>> 24) & 0xFF);
      compressedArray[outputOffset + 117] = (byte) ((intValues29 >>> 16) & 0xFF);
      compressedArray[outputOffset + 118] = (byte) ((intValues29 >>> 8) & 0xFF);
      compressedArray[outputOffset + 119] = (byte) (intValues29 & 0xFF);
      compressedArray[outputOffset + 120] = (byte) ((intValues30 >>> 24) & 0xFF);
      compressedArray[outputOffset + 121] = (byte) ((intValues30 >>> 16) & 0xFF);
      compressedArray[outputOffset + 122] = (byte) ((intValues30 >>> 8) & 0xFF);
      compressedArray[outputOffset + 123] = (byte) (intValues30 & 0xFF);
      compressedArray[outputOffset + 124] = (byte) ((intValues31 >>> 24) & 0xFF);
      compressedArray[outputOffset + 125] = (byte) ((intValues31 >>> 16) & 0xFF);
      compressedArray[outputOffset + 126] = (byte) ((intValues31 >>> 8) & 0xFF);
      compressedArray[outputOffset + 127] = (byte) (intValues31 & 0xFF);
      input.offset += 32;
      output.offset += 129;
    }
  }

  static final class FrameCompressor33 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      output.offset += 1;
      input.offset += 16;
  }
  }

  static final class FrameCompressor34 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 1) << 7)
                                                | ((intValues1 & 1) << 6)
                                                | ((intValues2 & 1) << 5)
                                                | ((intValues3 & 1) << 4)
                                                | ((intValues4 & 1) << 3)
                                                | ((intValues5 & 1) << 2)
                                                | ((intValues6 & 1) << 1)
                                                | (intValues7 & 1));
      compressedArray[outputOffset + 1] = (byte) (((intValues8 & 1) << 7)
                                                | ((intValues9 & 1) << 6)
                                                | ((intValues10 & 1) << 5)
                                                | ((intValues11 & 1) << 4)
                                                | ((intValues12 & 1) << 3)
                                                | ((intValues13 & 1) << 2)
                                                | ((intValues14 & 1) << 1)
                                                | (intValues15 & 1));
      input.offset += 16;
      output.offset += 3;
    }
  }

  static final class FrameCompressor35 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 3) << 6)
                                                | ((intValues1 & 3) << 4)
                                                | ((intValues2 & 3) << 2)
                                                | (intValues3 & 3));
      compressedArray[outputOffset + 1] = (byte) (((intValues4 & 3) << 6)
                                                | ((intValues5 & 3) << 4)
                                                | ((intValues6 & 3) << 2)
                                                | (intValues7 & 3));
      compressedArray[outputOffset + 2] = (byte) (((intValues8 & 3) << 6)
                                                | ((intValues9 & 3) << 4)
                                                | ((intValues10 & 3) << 2)
                                                | (intValues11 & 3));
      compressedArray[outputOffset + 3] = (byte) (((intValues12 & 3) << 6)
                                                | ((intValues13 & 3) << 4)
                                                | ((intValues14 & 3) << 2)
                                                | (intValues15 & 3));
      input.offset += 16;
      output.offset += 5;
    }
  }

  static final class FrameCompressor36 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 7) << 5)
                                                | ((intValues1 & 7) << 2)
                                                | ((intValues2 >>> 1) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues2 & 1) << 7)
                                                | ((intValues3 & 7) << 4)
                                                | ((intValues4 & 7) << 1)
                                                | ((intValues5 >>> 2) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues5 & 3) << 6)
                                                | ((intValues6 & 7) << 3)
                                                | (intValues7 & 7));
      compressedArray[outputOffset + 3] = (byte) (((intValues8 & 7) << 5)
                                                | ((intValues9 & 7) << 2)
                                                | ((intValues10 >>> 1) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues10 & 1) << 7)
                                                | ((intValues11 & 7) << 4)
                                                | ((intValues12 & 7) << 1)
                                                | ((intValues13 >>> 2) & 0xFF));
      compressedArray[outputOffset + 5] = (byte) (((intValues13 & 3) << 6)
                                                | ((intValues14 & 7) << 3)
                                                | (intValues15 & 7));
      input.offset += 16;
      output.offset += 7;
    }
  }

  static final class FrameCompressor37 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 15) << 4)
                                                | (intValues1 & 15));
      compressedArray[outputOffset + 1] = (byte) (((intValues2 & 15) << 4)
                                                | (intValues3 & 15));
      compressedArray[outputOffset + 2] = (byte) (((intValues4 & 15) << 4)
                                                | (intValues5 & 15));
      compressedArray[outputOffset + 3] = (byte) (((intValues6 & 15) << 4)
                                                | (intValues7 & 15));
      compressedArray[outputOffset + 4] = (byte) (((intValues8 & 15) << 4)
                                                | (intValues9 & 15));
      compressedArray[outputOffset + 5] = (byte) (((intValues10 & 15) << 4)
                                                | (intValues11 & 15));
      compressedArray[outputOffset + 6] = (byte) (((intValues12 & 15) << 4)
                                                | (intValues13 & 15));
      compressedArray[outputOffset + 7] = (byte) (((intValues14 & 15) << 4)
                                                | (intValues15 & 15));
      input.offset += 16;
      output.offset += 9;
    }
  }

  static final class FrameCompressor38 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 31) << 3)
                                                | ((intValues1 >>> 2) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues1 & 3) << 6)
                                                | ((intValues2 & 31) << 1)
                                                | ((intValues3 >>> 4) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues3 & 15) << 4)
                                                | ((intValues4 >>> 1) & 0xFF));
      compressedArray[outputOffset + 3] = (byte) (((intValues4 & 1) << 7)
                                                | ((intValues5 & 31) << 2)
                                                | ((intValues6 >>> 3) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues6 & 7) << 5)
                                                | (intValues7 & 31));
      compressedArray[outputOffset + 5] = (byte) (((intValues8 & 31) << 3)
                                                | ((intValues9 >>> 2) & 0xFF));
      compressedArray[outputOffset + 6] = (byte) (((intValues9 & 3) << 6)
                                                | ((intValues10 & 31) << 1)
                                                | ((intValues11 >>> 4) & 0xFF));
      compressedArray[outputOffset + 7] = (byte) (((intValues11 & 15) << 4)
                                                | ((intValues12 >>> 1) & 0xFF));
      compressedArray[outputOffset + 8] = (byte) (((intValues12 & 1) << 7)
                                                | ((intValues13 & 31) << 2)
                                                | ((intValues14 >>> 3) & 0xFF));
      compressedArray[outputOffset + 9] = (byte) (((intValues14 & 7) << 5)
                                                | (intValues15 & 31));
      input.offset += 16;
      output.offset += 11;
    }
  }

  static final class FrameCompressor39 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 63) << 2)
                                                | ((intValues1 >>> 4) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues1 & 15) << 4)
                                                | ((intValues2 >>> 2) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues2 & 3) << 6)
                                                | (intValues3 & 63));
      compressedArray[outputOffset + 3] = (byte) (((intValues4 & 63) << 2)
                                                | ((intValues5 >>> 4) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues5 & 15) << 4)
                                                | ((intValues6 >>> 2) & 0xFF));
      compressedArray[outputOffset + 5] = (byte) (((intValues6 & 3) << 6)
                                                | (intValues7 & 63));
      compressedArray[outputOffset + 6] = (byte) (((intValues8 & 63) << 2)
                                                | ((intValues9 >>> 4) & 0xFF));
      compressedArray[outputOffset + 7] = (byte) (((intValues9 & 15) << 4)
                                                | ((intValues10 >>> 2) & 0xFF));
      compressedArray[outputOffset + 8] = (byte) (((intValues10 & 3) << 6)
                                                | (intValues11 & 63));
      compressedArray[outputOffset + 9] = (byte) (((intValues12 & 63) << 2)
                                                | ((intValues13 >>> 4) & 0xFF));
      compressedArray[outputOffset + 10] = (byte) (((intValues13 & 15) << 4)
                                                | ((intValues14 >>> 2) & 0xFF));
      compressedArray[outputOffset + 11] = (byte) (((intValues14 & 3) << 6)
                                                | (intValues15 & 63));
      input.offset += 16;
      output.offset += 13;
    }
  }

  static final class FrameCompressor40 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 127) << 1)
                                                | ((intValues1 >>> 6) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues1 & 63) << 2)
                                                | ((intValues2 >>> 5) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues2 & 31) << 3)
                                                | ((intValues3 >>> 4) & 0xFF));
      compressedArray[outputOffset + 3] = (byte) (((intValues3 & 15) << 4)
                                                | ((intValues4 >>> 3) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues4 & 7) << 5)
                                                | ((intValues5 >>> 2) & 0xFF));
      compressedArray[outputOffset + 5] = (byte) (((intValues5 & 3) << 6)
                                                | ((intValues6 >>> 1) & 0xFF));
      compressedArray[outputOffset + 6] = (byte) (((intValues6 & 1) << 7)
                                                | (intValues7 & 127));
      compressedArray[outputOffset + 7] = (byte) (((intValues8 & 127) << 1)
                                                | ((intValues9 >>> 6) & 0xFF));
      compressedArray[outputOffset + 8] = (byte) (((intValues9 & 63) << 2)
                                                | ((intValues10 >>> 5) & 0xFF));
      compressedArray[outputOffset + 9] = (byte) (((intValues10 & 31) << 3)
                                                | ((intValues11 >>> 4) & 0xFF));
      compressedArray[outputOffset + 10] = (byte) (((intValues11 & 15) << 4)
                                                | ((intValues12 >>> 3) & 0xFF));
      compressedArray[outputOffset + 11] = (byte) (((intValues12 & 7) << 5)
                                                | ((intValues13 >>> 2) & 0xFF));
      compressedArray[outputOffset + 12] = (byte) (((intValues13 & 3) << 6)
                                                | ((intValues14 >>> 1) & 0xFF));
      compressedArray[outputOffset + 13] = (byte) (((intValues14 & 1) << 7)
                                                | (intValues15 & 127));
      input.offset += 16;
      output.offset += 15;
    }
  }

  static final class FrameCompressor41 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 & 255));
      compressedArray[outputOffset + 1] = (byte) ((intValues1 & 255));
      compressedArray[outputOffset + 2] = (byte) ((intValues2 & 255));
      compressedArray[outputOffset + 3] = (byte) ((intValues3 & 255));
      compressedArray[outputOffset + 4] = (byte) ((intValues4 & 255));
      compressedArray[outputOffset + 5] = (byte) ((intValues5 & 255));
      compressedArray[outputOffset + 6] = (byte) ((intValues6 & 255));
      compressedArray[outputOffset + 7] = (byte) ((intValues7 & 255));
      compressedArray[outputOffset + 8] = (byte) ((intValues8 & 255));
      compressedArray[outputOffset + 9] = (byte) ((intValues9 & 255));
      compressedArray[outputOffset + 10] = (byte) ((intValues10 & 255));
      compressedArray[outputOffset + 11] = (byte) ((intValues11 & 255));
      compressedArray[outputOffset + 12] = (byte) ((intValues12 & 255));
      compressedArray[outputOffset + 13] = (byte) ((intValues13 & 255));
      compressedArray[outputOffset + 14] = (byte) ((intValues14 & 255));
      compressedArray[outputOffset + 15] = (byte) ((intValues15 & 255));
      input.offset += 16;
      output.offset += 17;
    }
  }

  static final class FrameCompressor42 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 1) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 1) << 7
                                                | (intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 3) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 & 7) << 5
                                                | (intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 5) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues4 & 31) << 3
                                                | (intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 7) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues6 & 127) << 1
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues8 >>> 1) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues8 & 1) << 7
                                                | (intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 3) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues10 & 7) << 5
                                                | (intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 5) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues12 & 31) << 3
                                                | (intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 7) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues14 & 127) << 1
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 19;
    }
  }

  static final class FrameCompressor43 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 2) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 3) << 6
                                                | (intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 6) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 & 63) << 2
                                                | (intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues4 >>> 2) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues4 & 3) << 6
                                                | (intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 6) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues6 & 63) << 2
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues8 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues8 & 3) << 6
                                                | (intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 6) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues10 & 63) << 2
                                                | (intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues12 >>> 2) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues12 & 3) << 6
                                                | (intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues14 & 63) << 2
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 21;
    }
  }

  static final class FrameCompressor44 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 3) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 7) << 5
                                                | (intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 9) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 >>> 1) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 & 1) << 7
                                                | (intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 7) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues4 & 127) << 1
                                                | (intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 5) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues6 & 31) << 3
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues8 >>> 3) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues8 & 7) << 5
                                                | (intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 9) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues10 >>> 1) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues10 & 1) << 7
                                                | (intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 7) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues12 & 127) << 1
                                                | (intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 5) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues14 & 31) << 3
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 23;
    }
  }

  static final class FrameCompressor45 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 4) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 15) << 4
                                                | (intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 >>> 4) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 & 15) << 4
                                                | (intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues4 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues4 & 15) << 4
                                                | (intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues6 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues6 & 15) << 4
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues8 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues8 & 15) << 4
                                                | (intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues10 >>> 4) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues10 & 15) << 4
                                                | (intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues12 >>> 4) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues12 & 15) << 4
                                                | (intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues14 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues14 & 15) << 4
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 25;
    }
  }

  static final class FrameCompressor46 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 5) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 31) << 3
                                                | (intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 7) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 & 127) << 1
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 9) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues4 >>> 1) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 & 1) << 7
                                                | (intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 11) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues6 >>> 3) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues6 & 7) << 5
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues8 >>> 5) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues8 & 31) << 3
                                                | (intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 7) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues10 & 127) << 1
                                                | (intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 9) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues12 >>> 1) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues12 & 1) << 7
                                                | (intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 11) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues14 >>> 3) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues14 & 7) << 5
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 27;
    }
  }

  static final class FrameCompressor47 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 6) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 63) << 2
                                                | (intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 10) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 >>> 2) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 & 3) << 6
                                                | (intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues4 >>> 6) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 & 63) << 2
                                                | (intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 10) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues6 >>> 2) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues6 & 3) << 6
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues8 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues8 & 63) << 2
                                                | (intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 10) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues10 >>> 2) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues10 & 3) << 6
                                                | (intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues12 >>> 6) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues12 & 63) << 2
                                                | (intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 10) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues14 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues14 & 3) << 6
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 29;
    }
  }

  static final class FrameCompressor48 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 7) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 127) << 1
                                                | (intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 13) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 >>> 5) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 & 31) << 3
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 11) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 >>> 3) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues4 & 7) << 5
                                                | (intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 9) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues6 >>> 1) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues6 & 1) << 7
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues8 >>> 7) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues8 & 127) << 1
                                                | (intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 13) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues10 >>> 5) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues10 & 31) << 3
                                                | (intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 11) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues12 >>> 3) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues12 & 7) << 5
                                                | (intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 9) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues14 >>> 1) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues14 & 1) << 7
                                                | (intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 31;
    }
  }

  static final class FrameCompressor49 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 8) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) (intValues0 & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 >>> 8) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) (intValues2 & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 >>> 8) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) (intValues4 & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues6 >>> 8) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) (intValues6 & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues8 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues8 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues10 >>> 8) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) (intValues10 & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues12 >>> 8) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) (intValues12 & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues14 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues14 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 33;
    }
  }

  static final class FrameCompressor50 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 9) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 1) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 1) << 7
                                                | (intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 11) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 3) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 & 7) << 5
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 13) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues4 >>> 5) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 & 31) << 3
                                                | (intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 15) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues6 >>> 7) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues6 & 127) << 1
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues8 >>> 9) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues8 >>> 1) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues8 & 1) << 7
                                                | (intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 11) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues10 >>> 3) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues10 & 7) << 5
                                                | (intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 13) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues12 >>> 5) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues12 & 31) << 3
                                                | (intValues13 >>> 14) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 15) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues14 >>> 7) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues14 & 127) << 1
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 35;
    }
  }

  static final class FrameCompressor51 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 10) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 2) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 3) << 6
                                                | (intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 14) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 6) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 & 63) << 2
                                                | (intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues4 >>> 10) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 & 3) << 6
                                                | (intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 14) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues6 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues6 & 63) << 2
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues8 >>> 10) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues8 >>> 2) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues8 & 3) << 6
                                                | (intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 14) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues10 >>> 6) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues10 & 63) << 2
                                                | (intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues12 >>> 10) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues12 >>> 2) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues12 & 3) << 6
                                                | (intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 14) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues14 >>> 6) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues14 & 63) << 2
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 37;
    }
  }

  static final class FrameCompressor52 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 11) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 3) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 7) << 5
                                                | (intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 17) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 9) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 1) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 & 1) << 7
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 15) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 >>> 7) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 & 127) << 1
                                                | (intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 13) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues6 >>> 5) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues6 & 31) << 3
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues8 >>> 11) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues8 >>> 3) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues8 & 7) << 5
                                                | (intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 17) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues10 >>> 9) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues10 >>> 1) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues10 & 1) << 7
                                                | (intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 15) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues12 >>> 7) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues12 & 127) << 1
                                                | (intValues13 >>> 18) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 13) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues14 >>> 5) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues14 & 31) << 3
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 39;
    }
  }

  static final class FrameCompressor53 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 12) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 4) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 15) << 4
                                                | (intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 12) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 & 15) << 4
                                                | (intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 >>> 12) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 >>> 4) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 & 15) << 4
                                                | (intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues6 >>> 12) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues6 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues6 & 15) << 4
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues8 >>> 12) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues8 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues8 & 15) << 4
                                                | (intValues9 >>> 16) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues10 >>> 12) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues10 >>> 4) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues10 & 15) << 4
                                                | (intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues12 >>> 12) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues12 >>> 4) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues12 & 15) << 4
                                                | (intValues13 >>> 16) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues14 >>> 12) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues14 >>> 4) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues14 & 15) << 4
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 41;
    }
  }

  static final class FrameCompressor54 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 13) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 5) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 31) << 3
                                                | (intValues1 >>> 18) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 15) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 7) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 & 127) << 1
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 17) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 >>> 9) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 1) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 & 1) << 7
                                                | (intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 19) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues6 >>> 11) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues6 >>> 3) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 & 7) << 5
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues8 >>> 13) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues8 >>> 5) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues8 & 31) << 3
                                                | (intValues9 >>> 18) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 15) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues10 >>> 7) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues10 & 127) << 1
                                                | (intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 17) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues12 >>> 9) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues12 >>> 1) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues12 & 1) << 7
                                                | (intValues13 >>> 14) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 19) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues14 >>> 11) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues14 >>> 3) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues14 & 7) << 5
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 43;
    }
  }

  static final class FrameCompressor55 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 14) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 6) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 63) << 2
                                                | (intValues1 >>> 20) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 18) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 10) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 2) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 & 3) << 6
                                                | (intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 >>> 14) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 6) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 & 63) << 2
                                                | (intValues5 >>> 20) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 18) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues6 >>> 10) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 >>> 2) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 & 3) << 6
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues8 >>> 14) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues8 >>> 6) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues8 & 63) << 2
                                                | (intValues9 >>> 20) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 18) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues10 >>> 10) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues10 >>> 2) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues10 & 3) << 6
                                                | (intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues12 >>> 14) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues12 >>> 6) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues12 & 63) << 2
                                                | (intValues13 >>> 20) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 18) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues14 >>> 10) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues14 >>> 2) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues14 & 3) << 6
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 45;
    }
  }

  static final class FrameCompressor56 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 15) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 7) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 127) << 1
                                                | (intValues1 >>> 22) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 21) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 13) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 5) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 & 31) << 3
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 19) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 11) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 3) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 & 7) << 5
                                                | (intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 17) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 >>> 9) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 >>> 1) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues6 & 1) << 7
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues8 >>> 15) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues8 >>> 7) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues8 & 127) << 1
                                                | (intValues9 >>> 22) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 21) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues10 >>> 13) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues10 >>> 5) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues10 & 31) << 3
                                                | (intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 19) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues12 >>> 11) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues12 >>> 3) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues12 & 7) << 5
                                                | (intValues13 >>> 18) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 17) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues14 >>> 9) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues14 >>> 1) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues14 & 1) << 7
                                                | (intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 47;
    }
  }

  static final class FrameCompressor57 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 16) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 8) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) (intValues0 & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 16) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues2 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 16) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues4 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 >>> 16) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues6 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues8 >>> 16) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues8 >>> 8) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) (intValues8 & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues9 >>> 16) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues10 >>> 16) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues10 >>> 8) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) (intValues10 & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues12 >>> 16) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues12 >>> 8) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) (intValues12 & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues13 >>> 16) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues14 >>> 16) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues14 >>> 8) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) (intValues14 & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 49;
    }
  }

  static final class FrameCompressor58 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 17) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 9) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 1) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 1) << 7
                                                | (intValues1 >>> 18) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 19) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 11) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 3) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 & 7) << 5
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 21) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 13) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 5) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 & 31) << 3
                                                | (intValues5 >>> 22) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 23) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 >>> 15) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues6 >>> 7) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 & 127) << 1
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues8 >>> 17) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues8 >>> 9) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues8 >>> 1) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues8 & 1) << 7
                                                | (intValues9 >>> 18) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 19) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues10 >>> 11) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues10 >>> 3) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues10 & 7) << 5
                                                | (intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 21) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues12 >>> 13) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues12 >>> 5) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues12 & 31) << 3
                                                | (intValues13 >>> 22) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues13 >>> 14) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 23) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues14 >>> 15) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues14 >>> 7) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues14 & 127) << 1
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 51;
    }
  }

  static final class FrameCompressor59 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 18) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 10) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 2) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 3) << 6
                                                | (intValues1 >>> 20) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 22) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 14) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 6) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 & 63) << 2
                                                | (intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 18) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 10) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 2) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 & 3) << 6
                                                | (intValues5 >>> 20) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 22) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues6 >>> 14) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 >>> 6) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 & 63) << 2
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues8 >>> 18) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues8 >>> 10) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues8 >>> 2) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues8 & 3) << 6
                                                | (intValues9 >>> 20) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 22) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues10 >>> 14) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues10 >>> 6) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues10 & 63) << 2
                                                | (intValues11 >>> 24) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues12 >>> 18) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues12 >>> 10) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues12 >>> 2) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues12 & 3) << 6
                                                | (intValues13 >>> 20) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 22) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues14 >>> 14) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues14 >>> 6) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues14 & 63) << 2
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 53;
    }
  }

  static final class FrameCompressor60 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 19) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 11) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 3) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 7) << 5
                                                | (intValues1 >>> 22) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 25) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 17) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 9) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 1) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 & 1) << 7
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 23) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 15) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 7) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 & 127) << 1
                                                | (intValues5 >>> 26) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 21) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 >>> 13) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 >>> 5) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 & 31) << 3
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues8 >>> 19) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues8 >>> 11) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues8 >>> 3) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues8 & 7) << 5
                                                | (intValues9 >>> 22) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 25) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues10 >>> 17) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues10 >>> 9) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues10 >>> 1) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues10 & 1) << 7
                                                | (intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 23) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues12 >>> 15) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues12 >>> 7) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues12 & 127) << 1
                                                | (intValues13 >>> 26) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues13 >>> 18) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 21) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues14 >>> 13) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues14 >>> 5) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues14 & 31) << 3
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 55;
    }
  }

  static final class FrameCompressor61 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 20) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 12) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 4) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 15) << 4
                                                | (intValues1 >>> 24) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 20) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 12) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 & 15) << 4
                                                | (intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 20) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 12) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 & 15) << 4
                                                | (intValues5 >>> 24) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 >>> 20) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 >>> 12) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 >>> 4) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 & 15) << 4
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues8 >>> 20) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues8 >>> 12) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues8 >>> 4) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues8 & 15) << 4
                                                | (intValues9 >>> 24) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues9 >>> 16) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues10 >>> 20) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues10 >>> 12) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues10 >>> 4) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues10 & 15) << 4
                                                | (intValues11 >>> 24) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues12 >>> 20) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues12 >>> 12) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues12 >>> 4) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues12 & 15) << 4
                                                | (intValues13 >>> 24) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues13 >>> 16) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues14 >>> 20) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues14 >>> 12) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues14 >>> 4) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues14 & 15) << 4
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 57;
    }
  }

  static final class FrameCompressor62 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 21) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 13) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 5) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 31) << 3
                                                | (intValues1 >>> 26) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 18) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 23) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 15) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 7) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 & 127) << 1
                                                | (intValues3 >>> 28) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 25) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 17) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 9) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 1) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 & 1) << 7
                                                | (intValues5 >>> 22) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 27) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 >>> 19) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 >>> 11) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 3) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 & 7) << 5
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues8 >>> 21) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues8 >>> 13) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues8 >>> 5) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues8 & 31) << 3
                                                | (intValues9 >>> 26) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues9 >>> 18) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues9 >>> 10) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues9 >>> 2) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues9 & 3) << 6
                                                | (intValues10 >>> 23) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues10 >>> 15) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues10 >>> 7) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues10 & 127) << 1
                                                | (intValues11 >>> 28) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 25) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues12 >>> 17) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues12 >>> 9) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues12 >>> 1) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues12 & 1) << 7
                                                | (intValues13 >>> 22) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues13 >>> 14) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues13 >>> 6) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues13 & 63) << 2
                                                | (intValues14 >>> 27) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues14 >>> 19) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues14 >>> 11) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues14 >>> 3) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues14 & 7) << 5
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 59;
    }
  }

  static final class FrameCompressor63 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 22) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 14) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 6) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 63) << 2
                                                | (intValues1 >>> 28) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 20) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 26) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 18) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 10) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues2 & 3) << 6
                                                | (intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 22) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 14) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 & 63) << 2
                                                | (intValues5 >>> 28) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 20) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 26) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 >>> 18) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 10) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues6 & 3) << 6
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues8 >>> 22) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues8 >>> 14) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues8 >>> 6) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues8 & 63) << 2
                                                | (intValues9 >>> 28) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues9 >>> 20) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues9 >>> 12) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues9 >>> 4) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues9 & 15) << 4
                                                | (intValues10 >>> 26) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues10 >>> 18) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues10 >>> 10) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues10 >>> 2) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues10 & 3) << 6
                                                | (intValues11 >>> 24) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues12 >>> 22) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues12 >>> 14) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues12 >>> 6) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues12 & 63) << 2
                                                | (intValues13 >>> 28) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues13 >>> 20) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues13 >>> 12) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues13 >>> 4) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues13 & 15) << 4
                                                | (intValues14 >>> 26) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues14 >>> 18) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues14 >>> 10) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues14 >>> 2) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues14 & 3) << 6
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 61;
    }
  }

  static final class FrameCompressor64 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 23) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 15) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 7) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 127) << 1
                                                | (intValues1 >>> 30) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 22) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 29) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 21) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 13) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 >>> 5) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues2 & 31) << 3
                                                | (intValues3 >>> 28) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 27) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 19) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 11) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 >>> 3) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues4 & 7) << 5
                                                | (intValues5 >>> 26) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 25) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 17) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 >>> 9) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues6 >>> 1) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues6 & 1) << 7
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 31] = (byte) ((intValues8 >>> 23) & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues8 >>> 15) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues8 >>> 7) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues8 & 127) << 1
                                                | (intValues9 >>> 30) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) ((intValues9 >>> 22) & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues9 >>> 14) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues9 >>> 6) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues9 & 63) << 2
                                                | (intValues10 >>> 29) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) ((intValues10 >>> 21) & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues10 >>> 13) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues10 >>> 5) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues10 & 31) << 3
                                                | (intValues11 >>> 28) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) ((intValues11 >>> 20) & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues11 >>> 12) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues11 >>> 4) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues11 & 15) << 4
                                                | (intValues12 >>> 27) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) ((intValues12 >>> 19) & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues12 >>> 11) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues12 >>> 3) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues12 & 7) << 5
                                                | (intValues13 >>> 26) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) ((intValues13 >>> 18) & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues13 >>> 10) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues13 >>> 2) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues13 & 3) << 6
                                                | (intValues14 >>> 25) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) ((intValues14 >>> 17) & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues14 >>> 9) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues14 >>> 1) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues14 & 1) << 7
                                                | (intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 63;
    }
  }

  static final class FrameCompressor65 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];
      final int intValues8 = unCompressedData[offset + 8], intValues9 = unCompressedData[offset + 9];
      final int intValues10 = unCompressedData[offset + 10], intValues11 = unCompressedData[offset + 11];
      final int intValues12 = unCompressedData[offset + 12], intValues13 = unCompressedData[offset + 13];
      final int intValues14 = unCompressedData[offset + 14], intValues15 = unCompressedData[offset + 15];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 24) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 16) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 8) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) (intValues0 & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 24) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 24) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 16) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues2 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 24) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 16) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues4 & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 24) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 24) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 >>> 16) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues6 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues6 & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) (intValues7 & 0xFF);
      compressedArray[outputOffset + 32] = (byte) ((intValues8 >>> 24) & 0xFF);
      compressedArray[outputOffset + 33] = (byte) ((intValues8 >>> 16) & 0xFF);
      compressedArray[outputOffset + 34] = (byte) ((intValues8 >>> 8) & 0xFF);
      compressedArray[outputOffset + 35] = (byte) (intValues8 & 0xFF);
      compressedArray[outputOffset + 36] = (byte) ((intValues9 >>> 24) & 0xFF);
      compressedArray[outputOffset + 37] = (byte) ((intValues9 >>> 16) & 0xFF);
      compressedArray[outputOffset + 38] = (byte) ((intValues9 >>> 8) & 0xFF);
      compressedArray[outputOffset + 39] = (byte) (intValues9 & 0xFF);
      compressedArray[outputOffset + 40] = (byte) ((intValues10 >>> 24) & 0xFF);
      compressedArray[outputOffset + 41] = (byte) ((intValues10 >>> 16) & 0xFF);
      compressedArray[outputOffset + 42] = (byte) ((intValues10 >>> 8) & 0xFF);
      compressedArray[outputOffset + 43] = (byte) (intValues10 & 0xFF);
      compressedArray[outputOffset + 44] = (byte) ((intValues11 >>> 24) & 0xFF);
      compressedArray[outputOffset + 45] = (byte) ((intValues11 >>> 16) & 0xFF);
      compressedArray[outputOffset + 46] = (byte) ((intValues11 >>> 8) & 0xFF);
      compressedArray[outputOffset + 47] = (byte) (intValues11 & 0xFF);
      compressedArray[outputOffset + 48] = (byte) ((intValues12 >>> 24) & 0xFF);
      compressedArray[outputOffset + 49] = (byte) ((intValues12 >>> 16) & 0xFF);
      compressedArray[outputOffset + 50] = (byte) ((intValues12 >>> 8) & 0xFF);
      compressedArray[outputOffset + 51] = (byte) (intValues12 & 0xFF);
      compressedArray[outputOffset + 52] = (byte) ((intValues13 >>> 24) & 0xFF);
      compressedArray[outputOffset + 53] = (byte) ((intValues13 >>> 16) & 0xFF);
      compressedArray[outputOffset + 54] = (byte) ((intValues13 >>> 8) & 0xFF);
      compressedArray[outputOffset + 55] = (byte) (intValues13 & 0xFF);
      compressedArray[outputOffset + 56] = (byte) ((intValues14 >>> 24) & 0xFF);
      compressedArray[outputOffset + 57] = (byte) ((intValues14 >>> 16) & 0xFF);
      compressedArray[outputOffset + 58] = (byte) ((intValues14 >>> 8) & 0xFF);
      compressedArray[outputOffset + 59] = (byte) (intValues14 & 0xFF);
      compressedArray[outputOffset + 60] = (byte) ((intValues15 >>> 24) & 0xFF);
      compressedArray[outputOffset + 61] = (byte) ((intValues15 >>> 16) & 0xFF);
      compressedArray[outputOffset + 62] = (byte) ((intValues15 >>> 8) & 0xFF);
      compressedArray[outputOffset + 63] = (byte) (intValues15 & 0xFF);
      input.offset += 16;
      output.offset += 65;
    }
  }

  static final class FrameCompressor66 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      output.offset += 1;
      input.offset += 8;
  }
  }

  static final class FrameCompressor67 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 1) << 7)
                                                | ((intValues1 & 1) << 6)
                                                | ((intValues2 & 1) << 5)
                                                | ((intValues3 & 1) << 4)
                                                | ((intValues4 & 1) << 3)
                                                | ((intValues5 & 1) << 2)
                                                | ((intValues6 & 1) << 1)
                                                | (intValues7 & 1));
      input.offset += 8;
      output.offset += 2;
    }
  }

  static final class FrameCompressor68 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 3) << 6)
                                                | ((intValues1 & 3) << 4)
                                                | ((intValues2 & 3) << 2)
                                                | (intValues3 & 3));
      compressedArray[outputOffset + 1] = (byte) (((intValues4 & 3) << 6)
                                                | ((intValues5 & 3) << 4)
                                                | ((intValues6 & 3) << 2)
                                                | (intValues7 & 3));
      input.offset += 8;
      output.offset += 3;
    }
  }

  static final class FrameCompressor69 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 7) << 5)
                                                | ((intValues1 & 7) << 2)
                                                | ((intValues2 >>> 1) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues2 & 1) << 7)
                                                | ((intValues3 & 7) << 4)
                                                | ((intValues4 & 7) << 1)
                                                | ((intValues5 >>> 2) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues5 & 3) << 6)
                                                | ((intValues6 & 7) << 3)
                                                | (intValues7 & 7));
      input.offset += 8;
      output.offset += 4;
    }
  }

  static final class FrameCompressor70 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 15) << 4)
                                                | (intValues1 & 15));
      compressedArray[outputOffset + 1] = (byte) (((intValues2 & 15) << 4)
                                                | (intValues3 & 15));
      compressedArray[outputOffset + 2] = (byte) (((intValues4 & 15) << 4)
                                                | (intValues5 & 15));
      compressedArray[outputOffset + 3] = (byte) (((intValues6 & 15) << 4)
                                                | (intValues7 & 15));
      input.offset += 8;
      output.offset += 5;
    }
  }

  static final class FrameCompressor71 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 31) << 3)
                                                | ((intValues1 >>> 2) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues1 & 3) << 6)
                                                | ((intValues2 & 31) << 1)
                                                | ((intValues3 >>> 4) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues3 & 15) << 4)
                                                | ((intValues4 >>> 1) & 0xFF));
      compressedArray[outputOffset + 3] = (byte) (((intValues4 & 1) << 7)
                                                | ((intValues5 & 31) << 2)
                                                | ((intValues6 >>> 3) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues6 & 7) << 5)
                                                | (intValues7 & 31));
      input.offset += 8;
      output.offset += 6;
    }
  }

  static final class FrameCompressor72 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 63) << 2)
                                                | ((intValues1 >>> 4) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues1 & 15) << 4)
                                                | ((intValues2 >>> 2) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues2 & 3) << 6)
                                                | (intValues3 & 63));
      compressedArray[outputOffset + 3] = (byte) (((intValues4 & 63) << 2)
                                                | ((intValues5 >>> 4) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues5 & 15) << 4)
                                                | ((intValues6 >>> 2) & 0xFF));
      compressedArray[outputOffset + 5] = (byte) (((intValues6 & 3) << 6)
                                                | (intValues7 & 63));
      input.offset += 8;
      output.offset += 7;
    }
  }

  static final class FrameCompressor73 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) (((intValues0 & 127) << 1)
                                                | ((intValues1 >>> 6) & 0xFF));
      compressedArray[outputOffset + 1] = (byte) (((intValues1 & 63) << 2)
                                                | ((intValues2 >>> 5) & 0xFF));
      compressedArray[outputOffset + 2] = (byte) (((intValues2 & 31) << 3)
                                                | ((intValues3 >>> 4) & 0xFF));
      compressedArray[outputOffset + 3] = (byte) (((intValues3 & 15) << 4)
                                                | ((intValues4 >>> 3) & 0xFF));
      compressedArray[outputOffset + 4] = (byte) (((intValues4 & 7) << 5)
                                                | ((intValues5 >>> 2) & 0xFF));
      compressedArray[outputOffset + 5] = (byte) (((intValues5 & 3) << 6)
                                                | ((intValues6 >>> 1) & 0xFF));
      compressedArray[outputOffset + 6] = (byte) (((intValues6 & 1) << 7)
                                                | (intValues7 & 127));
      input.offset += 8;
      output.offset += 8;
    }
  }

  static final class FrameCompressor74 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 & 255));
      compressedArray[outputOffset + 1] = (byte) ((intValues1 & 255));
      compressedArray[outputOffset + 2] = (byte) ((intValues2 & 255));
      compressedArray[outputOffset + 3] = (byte) ((intValues3 & 255));
      compressedArray[outputOffset + 4] = (byte) ((intValues4 & 255));
      compressedArray[outputOffset + 5] = (byte) ((intValues5 & 255));
      compressedArray[outputOffset + 6] = (byte) ((intValues6 & 255));
      compressedArray[outputOffset + 7] = (byte) ((intValues7 & 255));
      input.offset += 8;
      output.offset += 9;
    }
  }

  static final class FrameCompressor75 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 1) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 1) << 7
                                                | (intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 3) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 & 7) << 5
                                                | (intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 5) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues4 & 31) << 3
                                                | (intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 7) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues6 & 127) << 1
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 10;
    }
  }

  static final class FrameCompressor76 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 2) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 3) << 6
                                                | (intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 6) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 & 63) << 2
                                                | (intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues4 >>> 2) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues4 & 3) << 6
                                                | (intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 6) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues6 & 63) << 2
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 11;
    }
  }

  static final class FrameCompressor77 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 3) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 7) << 5
                                                | (intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 9) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 >>> 1) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 & 1) << 7
                                                | (intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 7) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues4 & 127) << 1
                                                | (intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 5) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues6 & 31) << 3
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 12;
    }
  }

  static final class FrameCompressor78 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 4) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 15) << 4
                                                | (intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues2 >>> 4) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 & 15) << 4
                                                | (intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues4 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues4 & 15) << 4
                                                | (intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues6 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues6 & 15) << 4
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 13;
    }
  }

  static final class FrameCompressor79 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 5) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 31) << 3
                                                | (intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 7) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 & 127) << 1
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 9) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues4 >>> 1) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 & 1) << 7
                                                | (intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 11) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues6 >>> 3) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues6 & 7) << 5
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 14;
    }
  }

  static final class FrameCompressor80 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 6) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 63) << 2
                                                | (intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 10) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 >>> 2) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 & 3) << 6
                                                | (intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues4 >>> 6) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 & 63) << 2
                                                | (intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 10) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues6 >>> 2) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues6 & 3) << 6
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 15;
    }
  }

  static final class FrameCompressor81 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 7) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 & 127) << 1
                                                | (intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 13) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 >>> 5) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 & 31) << 3
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 11) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 >>> 3) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues4 & 7) << 5
                                                | (intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 9) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues6 >>> 1) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues6 & 1) << 7
                                                | (intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 16;
    }
  }

  static final class FrameCompressor82 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 8) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) (intValues0 & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues2 >>> 8) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) (intValues2 & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues4 >>> 8) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) (intValues4 & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues6 >>> 8) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) (intValues6 & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 17;
    }
  }

  static final class FrameCompressor83 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 9) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 1) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 1) << 7
                                                | (intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 11) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 3) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 & 7) << 5
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 13) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues4 >>> 5) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 & 31) << 3
                                                | (intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 15) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues6 >>> 7) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues6 & 127) << 1
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 18;
    }
  }

  static final class FrameCompressor84 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 10) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 2) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 3) << 6
                                                | (intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 14) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 6) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 & 63) << 2
                                                | (intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues4 >>> 10) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 & 3) << 6
                                                | (intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 14) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues6 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues6 & 63) << 2
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 19;
    }
  }

  static final class FrameCompressor85 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 11) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 3) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 7) << 5
                                                | (intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 17) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 9) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 1) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 & 1) << 7
                                                | (intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 15) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 >>> 7) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 & 127) << 1
                                                | (intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 13) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues6 >>> 5) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues6 & 31) << 3
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 20;
    }
  }

  static final class FrameCompressor86 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 12) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 4) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 15) << 4
                                                | (intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues2 >>> 12) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 & 15) << 4
                                                | (intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues4 >>> 12) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 >>> 4) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 & 15) << 4
                                                | (intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues6 >>> 12) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues6 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues6 & 15) << 4
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 21;
    }
  }

  static final class FrameCompressor87 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 13) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 5) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 31) << 3
                                                | (intValues1 >>> 18) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 15) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 7) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 & 127) << 1
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 17) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 >>> 9) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 1) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 & 1) << 7
                                                | (intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 19) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues6 >>> 11) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues6 >>> 3) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 & 7) << 5
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 22;
    }
  }

  static final class FrameCompressor88 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 14) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 6) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 63) << 2
                                                | (intValues1 >>> 20) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 18) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 10) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 2) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 & 3) << 6
                                                | (intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues4 >>> 14) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 6) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 & 63) << 2
                                                | (intValues5 >>> 20) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 18) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues6 >>> 10) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 >>> 2) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 & 3) << 6
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 23;
    }
  }

  static final class FrameCompressor89 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 15) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 7) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 & 127) << 1
                                                | (intValues1 >>> 22) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 21) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 13) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 5) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 & 31) << 3
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 19) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 11) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 3) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 & 7) << 5
                                                | (intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 17) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 >>> 9) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 >>> 1) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues6 & 1) << 7
                                                | (intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 24;
    }
  }

  static final class FrameCompressor90 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 16) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 8) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) (intValues0 & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues2 >>> 16) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 8) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) (intValues2 & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues4 >>> 16) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues4 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues6 >>> 16) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues6 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 25;
    }
  }

  static final class FrameCompressor91 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 17) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 9) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 1) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 1) << 7
                                                | (intValues1 >>> 18) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 19) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 11) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 3) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 & 7) << 5
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 21) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 13) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 5) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 & 31) << 3
                                                | (intValues5 >>> 22) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 23) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues6 >>> 15) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues6 >>> 7) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 & 127) << 1
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 26;
    }
  }

  static final class FrameCompressor92 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 18) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 10) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 2) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 3) << 6
                                                | (intValues1 >>> 20) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 22) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 14) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 6) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 & 63) << 2
                                                | (intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues4 >>> 18) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 10) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 2) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 & 3) << 6
                                                | (intValues5 >>> 20) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 22) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues6 >>> 14) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 >>> 6) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 & 63) << 2
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 27;
    }
  }

  static final class FrameCompressor93 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 19) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 11) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 3) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 7) << 5
                                                | (intValues1 >>> 22) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 25) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 17) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 9) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 1) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 & 1) << 7
                                                | (intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 23) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 15) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 7) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 & 127) << 1
                                                | (intValues5 >>> 26) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 21) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 >>> 13) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 >>> 5) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 & 31) << 3
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 28;
    }
  }

  static final class FrameCompressor94 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 20) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 12) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 4) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 15) << 4
                                                | (intValues1 >>> 24) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues2 >>> 20) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 12) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 4) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 & 15) << 4
                                                | (intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues4 >>> 20) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 12) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 4) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 & 15) << 4
                                                | (intValues5 >>> 24) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues6 >>> 20) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 >>> 12) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 >>> 4) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 & 15) << 4
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 29;
    }
  }

  static final class FrameCompressor95 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 21) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 13) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 5) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 31) << 3
                                                | (intValues1 >>> 26) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 18) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 10) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 2) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues1 & 3) << 6
                                                | (intValues2 >>> 23) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 15) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 7) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 & 127) << 1
                                                | (intValues3 >>> 28) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 25) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 17) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 9) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 1) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 & 1) << 7
                                                | (intValues5 >>> 22) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 14) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 6) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 & 63) << 2
                                                | (intValues6 >>> 27) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues6 >>> 19) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 >>> 11) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 3) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 & 7) << 5
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 30;
    }
  }

  static final class FrameCompressor96 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 22) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 14) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 6) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 63) << 2
                                                | (intValues1 >>> 28) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 20) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 12) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 4) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues1 & 15) << 4
                                                | (intValues2 >>> 26) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 18) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 10) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 >>> 2) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues2 & 3) << 6
                                                | (intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues4 >>> 22) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 14) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 6) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 & 63) << 2
                                                | (intValues5 >>> 28) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues5 >>> 20) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 12) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 >>> 4) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues5 & 15) << 4
                                                | (intValues6 >>> 26) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues6 >>> 18) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 10) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 >>> 2) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues6 & 3) << 6
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 31;
    }
  }

  static final class FrameCompressor97 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 23) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 15) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 7) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) ((intValues0 & 127) << 1
                                                | (intValues1 >>> 30) & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 22) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 14) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 6) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) ((intValues1 & 63) << 2
                                                | (intValues2 >>> 29) & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 21) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 13) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 >>> 5) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) ((intValues2 & 31) << 3
                                                | (intValues3 >>> 28) & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 20) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 12) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues3 >>> 4) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) ((intValues3 & 15) << 4
                                                | (intValues4 >>> 27) & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 19) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 11) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 >>> 3) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) ((intValues4 & 7) << 5
                                                | (intValues5 >>> 26) & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 18) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 >>> 10) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues5 >>> 2) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) ((intValues5 & 3) << 6
                                                | (intValues6 >>> 25) & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 17) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 >>> 9) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues6 >>> 1) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) ((intValues6 & 1) << 7
                                                | (intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 32;
    }
  }

  static final class FrameCompressor98 extends FrameCompressor {
    public final void compress(final IntsRef input, final BytesRef output) {
      final int[] unCompressedData = input.ints;
      final byte[] compressedArray = output.bytes;
      final int outputOffset = output.offset + 1;
      final int offset = input.offset;
      final int intValues0 = unCompressedData[offset + 0], intValues1 = unCompressedData[offset + 1];
      final int intValues2 = unCompressedData[offset + 2], intValues3 = unCompressedData[offset + 3];
      final int intValues4 = unCompressedData[offset + 4], intValues5 = unCompressedData[offset + 5];
      final int intValues6 = unCompressedData[offset + 6], intValues7 = unCompressedData[offset + 7];

      compressedArray[outputOffset + 0] = (byte) ((intValues0 >>> 24) & 0xFF);
      compressedArray[outputOffset + 1] = (byte) ((intValues0 >>> 16) & 0xFF);
      compressedArray[outputOffset + 2] = (byte) ((intValues0 >>> 8) & 0xFF);
      compressedArray[outputOffset + 3] = (byte) (intValues0 & 0xFF);
      compressedArray[outputOffset + 4] = (byte) ((intValues1 >>> 24) & 0xFF);
      compressedArray[outputOffset + 5] = (byte) ((intValues1 >>> 16) & 0xFF);
      compressedArray[outputOffset + 6] = (byte) ((intValues1 >>> 8) & 0xFF);
      compressedArray[outputOffset + 7] = (byte) (intValues1 & 0xFF);
      compressedArray[outputOffset + 8] = (byte) ((intValues2 >>> 24) & 0xFF);
      compressedArray[outputOffset + 9] = (byte) ((intValues2 >>> 16) & 0xFF);
      compressedArray[outputOffset + 10] = (byte) ((intValues2 >>> 8) & 0xFF);
      compressedArray[outputOffset + 11] = (byte) (intValues2 & 0xFF);
      compressedArray[outputOffset + 12] = (byte) ((intValues3 >>> 24) & 0xFF);
      compressedArray[outputOffset + 13] = (byte) ((intValues3 >>> 16) & 0xFF);
      compressedArray[outputOffset + 14] = (byte) ((intValues3 >>> 8) & 0xFF);
      compressedArray[outputOffset + 15] = (byte) (intValues3 & 0xFF);
      compressedArray[outputOffset + 16] = (byte) ((intValues4 >>> 24) & 0xFF);
      compressedArray[outputOffset + 17] = (byte) ((intValues4 >>> 16) & 0xFF);
      compressedArray[outputOffset + 18] = (byte) ((intValues4 >>> 8) & 0xFF);
      compressedArray[outputOffset + 19] = (byte) (intValues4 & 0xFF);
      compressedArray[outputOffset + 20] = (byte) ((intValues5 >>> 24) & 0xFF);
      compressedArray[outputOffset + 21] = (byte) ((intValues5 >>> 16) & 0xFF);
      compressedArray[outputOffset + 22] = (byte) ((intValues5 >>> 8) & 0xFF);
      compressedArray[outputOffset + 23] = (byte) (intValues5 & 0xFF);
      compressedArray[outputOffset + 24] = (byte) ((intValues6 >>> 24) & 0xFF);
      compressedArray[outputOffset + 25] = (byte) ((intValues6 >>> 16) & 0xFF);
      compressedArray[outputOffset + 26] = (byte) ((intValues6 >>> 8) & 0xFF);
      compressedArray[outputOffset + 27] = (byte) (intValues6 & 0xFF);
      compressedArray[outputOffset + 28] = (byte) ((intValues7 >>> 24) & 0xFF);
      compressedArray[outputOffset + 29] = (byte) ((intValues7 >>> 16) & 0xFF);
      compressedArray[outputOffset + 30] = (byte) ((intValues7 >>> 8) & 0xFF);
      compressedArray[outputOffset + 31] = (byte) (intValues7 & 0xFF);
      input.offset += 8;
      output.offset += 33;
    }
  }

}
