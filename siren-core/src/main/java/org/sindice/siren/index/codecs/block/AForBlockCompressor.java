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
import org.sindice.siren.index.codecs.block.AForFrameCompressor.FrameCompressor;

/**
 * Implementation of {@link BlockCompressor} based on the Adaptive Frame Of
 * Reference algorithm.
 *
 * <p>
 *
 * Adaptive Frame Of Reference (AFOR) attempts to retain the best of FOR
 * (Frame Of Reference), i.e., a very efficient compression and decompression
 * algorithm using highly-optimised routines, while providing a better tolerance
 * against outliers and therefore achieving a higher compression ratio. Compared
 * to PFOR, AFOR does not rely on the encoding of exceptions in the presence of
 * outliers. Instead, AFOR partitions a block into multiple frames of variable
 * length, the partition and the length of the frames being chosen appropriately
 * in order to adapt the encoding to the value distribution.
 *
 * <p>
 *
 * For more information about AFOR, please refer to the journal article
 * <a href="http://dx.doi.org/10.1016/j.websem.2011.04.004">Searching web
 * data: An entity retrieval and high-performance indexing model</a>.
 */
public class AForBlockCompressor extends BlockCompressor {

  /** Size of header in buffer */
  protected final int HEADER_SIZE = 0;

  protected final FrameCompressor[] compressors = AForFrameCompressor.compressors;

  private final long[][]            maxFrames       = new long[6][];

  protected static final int          MAX_FRAME_SIZE  = 32;
  protected static final int          MIN_FRAME_SIZE  = 8;

  public AForBlockCompressor() {
    // initialise the arrays that will host the 6 configurations
    maxFrames[0] = new long[4];
    maxFrames[1] = new long[3];
    maxFrames[2] = new long[3];
    maxFrames[3] = new long[3];
    maxFrames[4] = new long[2];
    maxFrames[5] = new long[1];
  }

  @Override
  public void compress(final IntsRef input, final BytesRef output) {
    assert input.ints.length % 32 == 0;
    final int[] uncompressedData = input.ints;
    final byte[] compressedData = output.bytes;

    // prepare the input buffer before starting the compression
    this.prepareInputBuffer(input);

    while (input.offset < input.length) {
      for (final long compressorCode : this.frameCompressorCodes(uncompressedData, input.offset, input.length)) {
        compressedData[output.offset] = (byte) compressorCode;
        this.compressors[(int) compressorCode].compress(input, output);
      }
    }

    // flip buffer
    input.offset = 0;
    output.length = output.offset;
    output.offset = 0;
  }

  @Override
  public int maxCompressedSize(final int arraySize) {
    // the number of windows
    final int numberOfWindows = (int) Math.ceil((float) arraySize / (float) this.getWindowSize());
    // 1 byte for the frame code, and 32 integers of 4 bytes each
    final int maxSize = numberOfWindows * (1 + (32 * 4));
    return HEADER_SIZE + maxSize;
  }

  /**
   * Prepare the input buffer for compression
   * <p>
   * This method will fill with 0 the portion of the input array that will be
   * covered by the frame window but that is outside the block.
   * <p>
   * This is necessary in order to avoid the compression instructions to behave
   * unexpectedly. The compression instructions are optimised to work over
   * frame window of integers that can be encoded with a certain number of bits.
   * If the last frame window of the block contains unexpected integers, i.e.,
   * integers with a larger number of bits than expected, then the encoding
   * of the last integer of a block can be corrupted. By filling the array with
   * 0, we avoid such problem as 0 does not have consequence in the compression
   * instructions.
   */
  private void prepareInputBuffer(final IntsRef input) {
    final int[] ints = input.ints;
    final int length = input.length;
    // the number of windows
    final int numberOfWindows = (int) Math.ceil((float) length / (float) this.getWindowSize());
    // compute the subset of the array that will be covered by the frame window
    final int frameWindowCoverage = numberOfWindows * MAX_FRAME_SIZE;
    // fill with 0 the portion of the array that is outside the block but will
    // be covered by the sliding frame window
    for (int j = length; j < frameWindowCoverage; j++) {
      ints[j] = 0;
    }
  }

  /**
   * Determine the frame compression codes for the next frame window. Each frame
   * compression code is defined by
   * <ul>
   * <li> the number of frame bits to be used for compression;
   * <li> the size of the frame
   * </ul>
   * The method tries to find the best configuration (i.e., the one with the
   * smallest size in term of bytes and the easier to decompress) of frame size and frame bits
   * for the current frame window. Currently it is based on six configurations:
   * <ul>
   * <li> one frame of 32 integers: [32]
   * <li> two frames of 16 integers: [16,16]
   * <li> one frame of 8 integers, followed by one frame of 16 integers and one
   * frame of 8 integers: [8, 16, 8]
   * <li> one frame of 16 integers, followed by two frames of 8 integers:
   * [16, 8, 8]
   * <li> two frames of 8 integers, followed by one frame of 16 integers:
   * [8, 8, 16]
   * <li> four frames of 8 integers: [8,8,8,8]
   * </ul>
   */
  private long[] frameCompressorCodes(final int[] unCompressedData, final int offset, final int length) {
    // Get the maximum integer for each frame of minimum size
    for (int i = 0; i < maxFrames[0].length; i++) {
      long max = 0;

      final int frameOffset = MIN_FRAME_SIZE * i;
      final int frameStart = offset + frameOffset;

      // if we reach the end of the block, stop checking for max integers
      for (int j = frameStart; j < length && j < frameStart + MIN_FRAME_SIZE; j++) {
        max = max >= (unCompressedData[j] & 0xFFFFFFFFL) ? max : (unCompressedData[j] & 0xFFFFFFFFL);
      }

      // Derive the frame compressor code from the max
      // 66 is the code of the special 8x0 frame compressor and 67 the code of the
      // first frame compressor for sequence of 8 integers
      maxFrames[0][i] = max == 0 ? 66 : logNextHigherPowerOf2(max) + 67;
    }

    // Choose the best config among the six
    int bestSize = this.getSize(0);
    int bestConfig = 0;

    for (int i = 1; i < 6; i++) {
      final int size = this.getSize(i);
      if (size <= bestSize) {
        bestSize = size;
        bestConfig = i;
      }
    }

    return maxFrames[bestConfig];
  }

  private int getSize(final int config) {
    switch (config) {
      case 0:
        return (int) (((maxFrames[0][0] + maxFrames[0][1] + maxFrames[0][2] + maxFrames[0][3] - 264) << 3) + 32);

      case 1:
        maxFrames[1][0] = maxFrames[0][0];
        maxFrames[1][1] = maxFrames[0][1];
        maxFrames[1][2] = maxFrames[0][2] > maxFrames[0][3] ? maxFrames[0][2] - 33 : maxFrames[0][3] - 33;
        return (int) (((maxFrames[1][0] + maxFrames[1][1] - 132) << 3) + ((maxFrames[1][2] - 33) << 4) + 24);

      case 2:
        maxFrames[2][0] = maxFrames[0][0] > maxFrames[0][1] ? maxFrames[0][0] - 33 : maxFrames[0][1] - 33;
        maxFrames[2][1] = maxFrames[0][2];
        maxFrames[2][2] = maxFrames[0][3];
        return (int) (((maxFrames[2][1] + maxFrames[2][2] - 132) << 3) + ((maxFrames[2][0] - 33) << 4) + 24);

      case 3:
        maxFrames[3][0] = maxFrames[0][0];
        maxFrames[3][1] = maxFrames[0][1] > maxFrames[0][2] ? maxFrames[0][1] - 33 : maxFrames[0][2] - 33;
        maxFrames[3][2] = maxFrames[0][3];
        return (int) (((maxFrames[3][0] + maxFrames[3][2] - 132) << 3) + ((maxFrames[3][1] - 33) << 4) + 24);

      case 4:
        maxFrames[4][0] = maxFrames[2][0];
        maxFrames[4][1] = maxFrames[1][2];
        return (int) (((maxFrames[4][0] + maxFrames[4][1] - 66) << 4) + 16);

      case 5:
        maxFrames[5][0] = maxFrames[4][0] > maxFrames[4][1] ? maxFrames[4][0] - 33: maxFrames[4][1] - 33;
        return (int) ((maxFrames[5][0] << 5) + 8);

      default:
        throw new Error("AFor: Unknown config");
    }
  }

  /**
   * Lookup table for finding the log base 2
   */
  private static final int[] LogTable256 = new int[256];

  static {
    LogTable256[0] = LogTable256[1] = 0;
    for (int i = 2; i < 256; i++) {
      LogTable256[i] = 1 + LogTable256[i / 2];
    }
  }

  /**
   * Optimised routine for finding the log base 2 of an integer.
   *
   * @see http://graphics.stanford.edu/~seander/bithacks.html#IntegerLogLookup
   */
  private static int logNextHigherPowerOf2(final long v) {
    long t, tt;

    tt = v >> 16;
    if (tt > 0) {
      return (t = tt >> 8) > 0 ? 24 + LogTable256[(int) t] : 16 + LogTable256[(int) tt];
    }
    else {
      return (t = v >> 8) > 0 ? 8 + LogTable256[(int) t] : LogTable256[(int) v];
    }
  }

  @Override
  public int getWindowSize() {
    return AForBlockCompressor.MAX_FRAME_SIZE;
  }

}
