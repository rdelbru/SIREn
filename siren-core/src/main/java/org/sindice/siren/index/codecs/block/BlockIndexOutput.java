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

import java.io.Closeable;
import java.io.IOException;

import org.apache.lucene.store.IndexOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract API to encode a block-based posting format.
 *
 * <p>
 *
 * This class is not thread-safe and not safe for concurrent file access. It
 * must not be used to encode multiple blocks concurrently. In the current
 * implementation of Lucene, terms are processed sequentially during the
 * creation of a new index segment. It is ensured that (1) one instance of this
 * class is always used by one single thread, and (2) one instance of this class
 * is always encoding postings one term at a time.
 */
public abstract class BlockIndexOutput implements Closeable {

  protected final IndexOutput out;

  protected static final Logger logger = LoggerFactory.getLogger(BlockIndexOutput.class);

  public BlockIndexOutput(final IndexOutput out) {
    this.out = out;
  }

  /**
   * Instantiates a new block index.
   */
  public Index index() throws IOException {
    return new Index();
  }

  /**
   * This class stores the file pointer of an {@link IndexOutput}.
   */
  public class Index {

    long fp;
    long lastFP;

    public void mark() throws IOException {
      fp = out.getFilePointer();
    }

    public void copyFrom(final BlockIndexOutput.Index other, final boolean copyLast)
    throws IOException {
      final Index idx = other;
      fp = idx.fp;
      if (copyLast) {
        lastFP = fp;
      }
    }

    public void write(final IndexOutput indexOut, final boolean absolute)
    throws IOException {
      // logger.debug("Write index at {}", fp);
      if (absolute) {
        indexOut.writeVLong(fp);
      }
      else {
        indexOut.writeVLong(fp - lastFP);
      }
      lastFP = fp;
    }

    @Override
    public String toString() {
      return "fp=" + fp;
    }
  }

  public void close() throws IOException {
    out.close();
  }

  /**
   * Create a new {@link BlockWriter} associated to this
   * {@link BlockIndexOutput}.
   *
   * <p>
   *
   * You should ensure to flush all {@link BlockWriter} before closing the
   * {@link BlockIndexOutput}.
   *
   * <p>
   *
   * More than one {@link BlockWriter} can be instantiated by a
   * {@link BlockIndexOutput}. Usually one writer is instantiated for each term.
   */
  public abstract BlockWriter getBlockWriter();

  /**
   * Abstraction over the writer of the blocks of the postings file.
   *
   * <p>
   *
   * The abstraction provides an interface to write and flush blocks. Subclasses
   * must implement the encoding of the block header and the encoding of
   * the block data.
   */
  protected abstract class BlockWriter {

    /**
     * Flush of pending data block to the output file.
     */
    public void flush() throws IOException {
      // Flush only if the block is non empty
      if (!this.isEmpty()) {
        this.writeBlock();
      }
    }

    /**
     * Write data block to the output file with the following sequence of
     * operations:
     * <ul>
     * <li> Compress the data
     * <li> Write block header (as header can depend on statistic computed
     * from data compression)
     * <li> Write compressed data block
     * <li> Reset writer for new block
     * </ul>
     */
    protected void writeBlock() throws IOException {
      this.compress();
      this.writeHeader();
      this.writeData();
      this.initBlock();
    }

    public abstract boolean isEmpty();

    public abstract boolean isFull();

    /**
     * Compress the data block
     */
    protected abstract void compress();

    /**
     * Write block header to the output file
     */
    protected abstract void writeHeader() throws IOException;

    /**
     * Write compressed data block to the output file
     */
    protected abstract void writeData() throws IOException;

    /**
     * Init writer for new block
     */
    protected abstract void initBlock();

    /**
     * Compute the minimum size of a buffer based on the required size and
     * the compression window size.
     */
    protected int getMinimumBufferSize(final int bufferSize, final int windowSize) {
      return (int) Math.ceil((float) bufferSize / (float) windowSize) * windowSize;
    }

  }

}
