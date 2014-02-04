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

package org.sindice.siren.index.codecs.siren10;

import java.io.IOException;

import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.index.codecs.block.BlockDecompressor;
import org.sindice.siren.index.codecs.block.BlockIndexInput;
import org.sindice.siren.util.ArrayUtils;

/**
 * Implementation of the {@link BlockIndexInput} for the .pos file of the SIREn
 * postings format.
 */
public class PosBlockIndexInput extends BlockIndexInput {

  protected BlockDecompressor posDecompressor;

  public PosBlockIndexInput(final IndexInput in, final BlockDecompressor posDecompressor)
  throws IOException {
    super(in);
    this.posDecompressor = posDecompressor;
  }

  @Override
  public PosBlockReader getBlockReader() {
    // Clone index input. A cloned index input does not need to be closed
    // by the block reader, as the underlying stream will be closed by the
    // input it was cloned from
    return new PosBlockReader(in.clone());
  }

  /**
   * Implementation of the {@link BlockReader} for the .pos file.
   *
   * <p>
   *
   * Read and decode blocks containing the the term positions.
   */
  protected class PosBlockReader extends BlockReader {

    int posBlockSize;
    IntsRef posBuffer = new IntsRef();

    boolean posReadPending = true;

    int posCompressedBufferLength;
    BytesRef posCompressedBuffer = new BytesRef();

    private int currentPos = 0;

    private PosBlockReader(final IndexInput in) {
      super(in);
      // ensure that the output buffers has the minimum size required
      posBuffer = ArrayUtils.grow(posBuffer, posDecompressor.getWindowSize());
    }

    @Override
    protected void readHeader() throws IOException {
      // logger.debug("Decode Pos header: {}", this.hashCode());
      // logger.debug("Pos header start at {}", in.getFilePointer());

      // read blockSize and check buffer size
      posBlockSize = in.readVInt();
      // ensure that the output buffer has the minimum size required
      final int posLength = this.getMinimumBufferSize(posBlockSize, posDecompressor.getWindowSize());
      posBuffer = ArrayUtils.grow(posBuffer, posLength);
      // logger.debug("Read Pos block size: {}", posBlockSize);

      // read size of each compressed data block and check buffer size
      posCompressedBufferLength = in.readVInt();
      posCompressedBuffer = ArrayUtils.grow(posCompressedBuffer, posCompressedBufferLength);
      posReadPending = true;
    }

    @Override
    protected void skipData() throws IOException {
      long size = 0;
      if (posReadPending) {
        size += posCompressedBufferLength;
      }
      this.seek(in.getFilePointer() + size);
      // logger.debug("Skip Pos data: {}", in.getFilePointer() + size);
    }

    /**
     * Decode and return the next position of the current block.
     */
    public int nextPosition() throws IOException {
      if (posReadPending) {
        this.decodePositions();
      }
      assert posBuffer.offset <= posBuffer.length;
      return currentPos = posBuffer.ints[posBuffer.offset++] + currentPos;
    }

    private void decodePositions() throws IOException {
      // logger.debug("Decode Pos: {}", this.hashCode());

      in.readBytes(posCompressedBuffer.bytes, 0, posCompressedBufferLength);
      posCompressedBuffer.offset = 0;
      posCompressedBuffer.length = posCompressedBufferLength;
      posDecompressor.decompress(posCompressedBuffer, posBuffer);
      // set length limit based on block size, as certain decompressor with
      // large window size can set it larger than the blockSize, e.g., AFor
      posBuffer.length = posBlockSize;

      posReadPending = false;
    }

    @Override
    public boolean isExhausted() {
      return posBuffer.offset >= posBuffer.length;
    }

    @Override
    protected void initBlock() {
      posBuffer.offset = posBuffer.length = 0;
      this.resetCurrentPosition();

      posReadPending = true;

      posCompressedBufferLength = 0;
    }

    public void resetCurrentPosition() {
      currentPos = 0;
    }

  }

}
