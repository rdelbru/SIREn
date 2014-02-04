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

import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.index.codecs.block.BlockCompressor;
import org.sindice.siren.index.codecs.block.BlockIndexOutput;
import org.sindice.siren.index.codecs.siren10.NodBlockIndexOutput.NodBlockWriter;
import org.sindice.siren.index.codecs.siren10.PosBlockIndexOutput.PosBlockWriter;

/**
 * Implementation of the {@link BlockIndexOutput} for the .doc file of the SIREn
 * postings format.
 */
public class DocsFreqBlockIndexOutput extends BlockIndexOutput {

  private final int maxBlockSize;

  private final BlockCompressor docCompressor;
  private final BlockCompressor freqCompressor;

  public DocsFreqBlockIndexOutput(final IndexOutput out, final int maxBlockSize,
                                  final BlockCompressor docCompressor,
                                  final BlockCompressor freqCompressor)
  throws IOException {
    super(out);
    this.docCompressor = docCompressor;
    this.freqCompressor = freqCompressor;
    this.maxBlockSize = maxBlockSize;
  }

  @Override
  public DocsFreqBlockWriter getBlockWriter() {
    return new DocsFreqBlockWriter();
  }

  /**
   * Implementation of the {@link BlockWriter} for the .doc file.
   *
   * <p>
   *
   * Encode and write blocks containing the document identifiers and the node
   * frequencies. It also encodes the pointers of the associated blocks from
   * the .nod and .pos files into the block header.
   *
   * <p>
   *
   * This class must be associated to the {@link Index}s of the
   * {@link NodBlockWriter} and {@link PosBlockWriter} using
   * {@link #setNodeBlockIndex(Index)} and {@link #setPosBlockIndex(Index)}.
   */
  public class DocsFreqBlockWriter extends BlockWriter {

    IntsRef docBuffer;
    IntsRef nodFreqBuffer;

    int firstDocId, lastDocId = 0;
    NodBlockIndexOutput.Index nodeBlockIndex;
    PosBlockIndexOutput.Index posBlockIndex;

    BytesRef docCompressedBuffer;
    BytesRef nodFreqCompressedBuffer;

    public DocsFreqBlockWriter() {
      // ensure that the input buffers has the minimum size required
      docBuffer = new IntsRef(this.getMinimumBufferSize(maxBlockSize, docCompressor.getWindowSize()));
      nodFreqBuffer = new IntsRef(this.getMinimumBufferSize(maxBlockSize, freqCompressor.getWindowSize()));

      // determine max size of compressed buffer to avoid overflow
      int size = docCompressor.maxCompressedSize(maxBlockSize);
      docCompressedBuffer = new BytesRef(size);

      size = freqCompressor.maxCompressedSize(maxBlockSize);
      nodFreqCompressedBuffer = new BytesRef(size);
    }

    public int getMaxBlockSize() {
      return maxBlockSize;
    }

    public int getFirstDocId() {
      return firstDocId;
    }

    /**
     * Set the {@link Index} of the {@link NodBlockIndexOutput}. The
     * {@link Index} is used to read the current file pointer of the
     * {@link NodBlockIndexOutput} when flushing a block.
     */
    public void setNodeBlockIndex(final NodBlockIndexOutput.Index index) throws IOException {
      this.nodeBlockIndex = index;
    }

    /**
     * Set the {@link Index} of the {@link PosBlockIndexOutput}. The
     * {@link Index} is used to read the current file pointer of the
     * {@link PosBlockIndexOutput} when flushing a block.
     */
    public void setPosBlockIndex(final PosBlockIndexOutput.Index index) throws IOException {
      this.posBlockIndex = index;
    }

    /**
     * Add a document identifier to the buffer.
     */
    public void write(final int docId) throws IOException {
      int delta;

      // compute delta - first value in the block is always 0
      if (docBuffer.offset != 0) {
        assert docId > lastDocId;
        // encode delta and decrement by one
        delta = docId - lastDocId - 1;
      }
      else {
        delta = 0;
        firstDocId = docId;
      }

      // copy delta to buffer
      docBuffer.ints[docBuffer.offset++] = delta;
      // update last doc id
      lastDocId = docId;
    }


    /**
     * Add a node frenquency to the buffer.
     */
    public void writeNodeFreq(final int nodeFreqInDoc) {
      // decrement freq by one
      nodFreqBuffer.ints[nodFreqBuffer.offset++] = nodeFreqInDoc - 1;
    }

    @Override
    public boolean isEmpty() {
      return docBuffer.offset == 0;
    }

    @Override
    public boolean isFull() {
      return docBuffer.offset >= maxBlockSize;
    }

    @Override
    protected void compress() {
      // Flip buffer before compression
      docBuffer.length = nodFreqBuffer.length = docBuffer.offset;
      docBuffer.offset = nodFreqBuffer.offset = 0;

      docCompressor.compress(docBuffer, docCompressedBuffer);
      freqCompressor.compress(nodFreqBuffer, nodFreqCompressedBuffer);
    }

    @Override
    protected void writeHeader() throws IOException {
      // logger.debug("Write DocFreq header - writer-id={}", this.hashCode());
      // logger.debug("DocFreq header start at fp={}", out.getFilePointer());

      // write block size (same for all of them)
      out.writeVInt(docBuffer.length);
      // logger.debug("blockSize: {}", docBuffer.length);

      // write size of each compressed data block
      out.writeVInt(docCompressedBuffer.length);
      // logger.debug("docCompressedBuffer.length: {}", docCompressedBuffer.length);
      out.writeVInt(nodFreqCompressedBuffer.length);
      // logger.debug("nodFreqCompressedBuffer.length: {}", nodFreqCompressedBuffer.length);

      // write first and last doc id
      out.writeVInt(firstDocId);
      out.writeVInt(lastDocId - firstDocId);
      // logger.debug("firstDocId: {}, lastDocId: {}", firstDocId, lastDocId);

      // write node and pos skip data
      // logger.debug("Write node and pos skip data");
      nodeBlockIndex.mark();
      nodeBlockIndex.write(out, true);
      posBlockIndex.mark();
      posBlockIndex.write(out, true);
    }

    @Override
    protected void writeData() throws IOException {
      out.writeBytes(docCompressedBuffer.bytes, docCompressedBuffer.length);
      out.writeBytes(nodFreqCompressedBuffer.bytes, nodFreqCompressedBuffer.length);
    }

    @Override
    protected void initBlock() {
      docBuffer.offset = 0;
      nodFreqBuffer.offset = 0;
    }

  }

}
