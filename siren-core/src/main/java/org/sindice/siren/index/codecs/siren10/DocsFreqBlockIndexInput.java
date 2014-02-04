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
 * Implementation of the {@link BlockIndexInput} for the .doc file of the SIREn
 * postings format.
 */
public class DocsFreqBlockIndexInput extends BlockIndexInput {

  protected BlockDecompressor docDecompressor;
  protected BlockDecompressor freqDecompressor;

  public DocsFreqBlockIndexInput(final IndexInput in,
                                 final BlockDecompressor docDecompressor,
                                 final BlockDecompressor freqDecompressor)
  throws IOException {
    super(in);
    this.docDecompressor = docDecompressor;
    this.freqDecompressor = freqDecompressor;
  }

  @Override
  public DocsFreqBlockReader getBlockReader() {
    // Clone index input. A cloned index input does not need to be closed
    // by the block reader, as the underlying stream will be closed by the
    // input it was cloned from
    return new DocsFreqBlockReader(in.clone());
  }

  /**
   * Implementation of the {@link BlockReader} for the .doc file.
   *
   * <p>
   *
   * Read and decode blocks containing the document identifiers and the node
   * frequencies. It also decodes the pointers of the associated
   * blocks from the .nod and .pos files and updates the specified
   * {@link Index}s.
   */
  public class DocsFreqBlockReader extends BlockReader {

    protected int blockSize;

    IntsRef docBuffer = new IntsRef();
    IntsRef nodFreqBuffer = new IntsRef();

    boolean docsReadPending = true;
    boolean nodFreqsReadPending = true;

    int docCompressedBufferLength;
    int nodFreqCompressedBufferLength;

    BytesRef docCompressedBuffer = new BytesRef();
    BytesRef nodFreqCompressedBuffer = new BytesRef();

    int firstDocId, lastDocId;

    long dataBlockOffset = -1;

    NodBlockIndexInput.Index nodeBlockIndex;
    PosBlockIndexInput.Index posBlockIndex;

    private DocsFreqBlockReader(final IndexInput in) {
      super(in);
      // ensure that the output buffers have the minimum size required
      docBuffer = ArrayUtils.grow(docBuffer, docDecompressor.getWindowSize());
      nodFreqBuffer = ArrayUtils.grow(nodFreqBuffer, freqDecompressor.getWindowSize());
    }

    /**
     * Set the {@link Index} of the {@link NodBlockIndexInput}. The
     * {@link Index} is used to update the current file pointer of the
     * {@link NodBlockIndexInput} when decoding a block.
     */
    public void setNodeBlockIndex(final NodBlockIndexInput.Index index) throws IOException {
      this.nodeBlockIndex = index;
    }

    /**
     * Set the {@link Index} of the {@link PosBlockIndexInput}. The
     * {@link Index} is used to update the current file pointer of the
     * {@link PosBlockIndexInput} when decoding a block.
     */
    public void setPosBlockIndex(final PosBlockIndexInput.Index index) throws IOException {
      this.posBlockIndex = index;
    }

    @Override
    protected void readHeader() throws IOException {
      // logger.debug("Read DocFreq header: {}", this.hashCode());
      // logger.debug("DocFreq header start at {}", in.getFilePointer());

      // read blockSize and check buffer size
      blockSize = in.readVInt();

      // ensure that the output buffers has the minimum size required
      final int docBufferLength = this.getMinimumBufferSize(blockSize, docDecompressor.getWindowSize());
      docBuffer = ArrayUtils.grow(docBuffer, docBufferLength);
      final int nodFreqBufferLength = this.getMinimumBufferSize(blockSize, freqDecompressor.getWindowSize());
      nodFreqBuffer = ArrayUtils.grow(nodFreqBuffer, nodFreqBufferLength);

      // read size of each compressed data block and check buffer size
      docCompressedBufferLength = in.readVInt();
      docCompressedBuffer = ArrayUtils.grow(docCompressedBuffer, docCompressedBufferLength);
      docsReadPending = true;

      nodFreqCompressedBufferLength = in.readVInt();
      nodFreqCompressedBuffer = ArrayUtils.grow(nodFreqCompressedBuffer, nodFreqCompressedBufferLength);
      nodFreqsReadPending = true;

      // read first and last doc id
      firstDocId = in.readVInt();
      lastDocId = firstDocId + in.readVInt();

      // read node and pos skip data
      nodeBlockIndex.read(in, true);
      posBlockIndex.read(in, true);

      // record file pointer as data block offset for skipping
      dataBlockOffset = in.getFilePointer();
    }

    @Override
    protected void skipData() {
      int size = docCompressedBufferLength;
      size += nodFreqCompressedBufferLength;

      this.seek(dataBlockOffset + size);
      // logger.debug("Skip DocFreq data: {}", dataBlockOffset + size);
    }

    private void decodeDocs() throws IOException {
      // logger.debug("Decode Doc block: {}", this.hashCode());

      in.seek(dataBlockOffset); // skip to doc data block
      in.readBytes(docCompressedBuffer.bytes, 0, docCompressedBufferLength);
      docCompressedBuffer.offset = 0;
      docCompressedBuffer.length = docCompressedBufferLength;
      docDecompressor.decompress(docCompressedBuffer, docBuffer);
      // set length limit based on block size, as certain decompressor with
      // large window size can set it larger than the blockSize, e.g., AFor
      docBuffer.length = blockSize;

      docsReadPending = false;
    }

    private void decodeNodeFreqs() throws IOException {
      // logger.debug("Decode Node Freqs block: {}", this.hashCode());

      in.seek(dataBlockOffset + docCompressedBufferLength); // skip to node freq data block
      in.readBytes(nodFreqCompressedBuffer.bytes, 0, nodFreqCompressedBufferLength);
      nodFreqCompressedBuffer.offset = 0;
      nodFreqCompressedBuffer.length = nodFreqCompressedBufferLength;
      freqDecompressor.decompress(nodFreqCompressedBuffer, nodFreqBuffer);
      // set length limit based on block size, as certain decompressor with
      // large window size can set it larger than the blockSize, e.g., AFor
      nodFreqBuffer.length = blockSize;

      nodFreqsReadPending = false;
    }

    /**
     * Return the first document identifier of the current block.
     */
    public int getFirstDocId() {
      return firstDocId;
    }

    /**
     * Return the last document identifier of the current block.
     */
    public int getLastDocId() {
      return lastDocId;
    }

    private int currentDocId;

    /**
     * Decode and return the next document identifier of the current block.
     */
    public int nextDocument() throws IOException {
      if (!docsReadPending) {
        // decode delta and increment by one
        currentDocId += docBuffer.ints[docBuffer.offset++] + 1;
        return currentDocId;
      }

      // if new block, first value is always equal to 0, no delta decoding
      this.decodeDocs();
      // set current doc with first doc
      currentDocId = firstDocId;
      // increment doc buffer offset to skip first value (== 0)
      docBuffer.offset++;
      return currentDocId;
    }

    /**
     * Decode and return the next node frequency of the current block.
     */
    public int nextNodeFreq() throws IOException {
      if (nodFreqsReadPending) {
        this.decodeNodeFreqs();
      }
      // Increment freq
      return nodFreqBuffer.ints[nodFreqBuffer.offset++] + 1;
    }

    @Override
    public boolean isExhausted() {
      return docBuffer.offset >= docBuffer.length;
    }

    @Override
    protected void initBlock() {
      docBuffer.offset = docBuffer.length = 0;
      nodFreqBuffer.offset = nodFreqBuffer.length = 0;

      docsReadPending = true;
      nodFreqsReadPending = true;

      docCompressedBufferLength = 0;
      nodFreqCompressedBufferLength = 0;

      dataBlockOffset = -1;
    }

  }

}
