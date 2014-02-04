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
 * Implementation of the {@link BlockIndexInput} for the .nod file of the SIREn
 * postings format.
 */
public class NodBlockIndexInput extends BlockIndexInput {

  protected BlockDecompressor nodDecompressor;

  public NodBlockIndexInput(final IndexInput in, final BlockDecompressor nodDecompressor)
  throws IOException {
    super(in);
    this.nodDecompressor = nodDecompressor;
  }

  @Override
  public NodBlockReader getBlockReader() {
    // Clone index input. A cloned index input does not need to be closed
    // by the block reader, as the underlying stream will be closed by the
    // input it was cloned from
    return new NodBlockReader(in.clone());
  }

  /**
   * Implementation of the {@link BlockReader} for the .nod file.
   *
   * <p>
   *
   * Read and decode blocks containing the the node labels and term frequencies.
   */
  protected class NodBlockReader extends BlockReader {

    int nodLenBlockSize;
    IntsRef nodLenBuffer = new IntsRef();
    int nodBlockSize;
    IntsRef nodBuffer = new IntsRef();
    int termFreqBlockSize;
    IntsRef termFreqBuffer = new IntsRef();

    /**
     * Used to slice the nodBuffer and disclose only the subset containing
     * information about the current node.
     */
    private final IntsRef currentNode = new IntsRef();

    boolean nodLenReadPending = true;
    boolean nodReadPending = true;
    boolean termFreqReadPending = true;

    int nodLenCompressedBufferLength;
    BytesRef nodLenCompressedBuffer = new BytesRef();
    int nodCompressedBufferLength;
    BytesRef nodCompressedBuffer = new BytesRef();
    int termFreqCompressedBufferLength;
    BytesRef termFreqCompressedBuffer = new BytesRef();

    private NodBlockReader(final IndexInput in) {
      super(in);
      // ensure that the output buffers has the minimum size required
      nodLenBuffer = ArrayUtils.grow(nodLenBuffer, nodDecompressor.getWindowSize());
      nodBuffer = ArrayUtils.grow(nodBuffer, nodDecompressor.getWindowSize());
      termFreqBuffer = ArrayUtils.grow(termFreqBuffer, nodDecompressor.getWindowSize());
    }

    @Override
    protected void readHeader() throws IOException {
      // logger.debug("Read Nod header: {}", this.hashCode());
      // logger.debug("Nod header start at {}", in.getFilePointer());

      // read blockSize and check buffer size
      nodLenBlockSize = in.readVInt();
      // ensure that the output buffer has the minimum size required
      final int nodLenBufferLength = this.getMinimumBufferSize(nodLenBlockSize, nodDecompressor.getWindowSize());
      nodLenBuffer = ArrayUtils.grow(nodLenBuffer, nodLenBufferLength);
      // logger.debug("Read Nod length block size: {}", nodLenblockSize);

      nodBlockSize = in.readVInt();
      // ensure that the output buffer has the minimum size required
      final int nodBufferLength = this.getMinimumBufferSize(nodBlockSize, nodDecompressor.getWindowSize());
      nodBuffer = ArrayUtils.grow(nodBuffer, nodBufferLength);
      // logger.debug("Read Nod block size: {}", nodBlockSize);

      termFreqBlockSize = in.readVInt();
      // ensure that the output buffer has the minimum size required
      final int termFreqBufferLength = this.getMinimumBufferSize(termFreqBlockSize, nodDecompressor.getWindowSize());
      termFreqBuffer = ArrayUtils.grow(termFreqBuffer, termFreqBufferLength);
      // logger.debug("Read Term Freq In Node block size: {}", termFreqblockSize);

      // read size of each compressed data block and check buffer size
      nodLenCompressedBufferLength = in.readVInt();
      nodLenCompressedBuffer = ArrayUtils.grow(nodLenCompressedBuffer, nodLenCompressedBufferLength);
      nodLenReadPending = true;

      nodCompressedBufferLength = in.readVInt();
      nodCompressedBuffer = ArrayUtils.grow(nodCompressedBuffer, nodCompressedBufferLength);
      nodReadPending = true;

      termFreqCompressedBufferLength = in.readVInt();
      termFreqCompressedBuffer = ArrayUtils.grow(termFreqCompressedBuffer, termFreqCompressedBufferLength);
      termFreqReadPending = true;

      // decode node lengths
      this.decodeNodeLengths();

      // copy reference of node buffer
      currentNode.ints = nodBuffer.ints;
    }

    @Override
    protected void skipData() throws IOException {
      long size = 0;
      if (nodLenReadPending) {
        size += nodLenCompressedBufferLength;
      }
      if (nodReadPending) {
        size += nodCompressedBufferLength;
      }
      if (termFreqReadPending) {
        size += termFreqCompressedBufferLength;
      }
      this.seek(in.getFilePointer() + size);
      // logger.debug("Skip Nod data: {}", in.getFilePointer() + size);
    }

    private void decodeNodeLengths() throws IOException {
      // logger.debug("Decode Nodes Length: {}", this.hashCode());
      // logger.debug("Decode Nodes Length at {}", in.getFilePointer());
      in.readBytes(nodLenCompressedBuffer.bytes, 0, nodLenCompressedBufferLength);
      nodLenCompressedBuffer.offset = 0;
      nodLenCompressedBuffer.length = nodLenCompressedBufferLength;
      nodDecompressor.decompress(nodLenCompressedBuffer, nodLenBuffer);
      // set length limit based on block size, as certain decompressor with
      // large window size can set it larger than the blockSize, e.g., AFor
      nodLenBuffer.length = nodLenBlockSize;

      nodLenReadPending = false;
    }

    private void decodeNodes() throws IOException {
      // logger.debug("Decode Nodes: {}", this.hashCode());
      // logger.debug("Decode Nodes at {}", in.getFilePointer());
      in.readBytes(nodCompressedBuffer.bytes, 0, nodCompressedBufferLength);
      nodCompressedBuffer.offset = 0;
      nodCompressedBuffer.length = nodCompressedBufferLength;
      nodDecompressor.decompress(nodCompressedBuffer, nodBuffer);
      // set length limit based on block size, as certain decompressor with
      // large window size can set it larger than the blockSize, e.g., AFor
      nodBuffer.length = nodBlockSize;

      nodReadPending = false;
    }

    private void decodeTermFreqs() throws IOException {
      // logger.debug("Decode Term Freq in Node: {}", this.hashCode());
      // logger.debug("Decode Term Freq in Node at {}", in.getFilePointer());
      in.readBytes(termFreqCompressedBuffer.bytes, 0, termFreqCompressedBufferLength);
      termFreqCompressedBuffer.offset = 0;
      termFreqCompressedBuffer.length = termFreqCompressedBufferLength;
      nodDecompressor.decompress(termFreqCompressedBuffer, termFreqBuffer);
      // set length limit based on block size, as certain decompressor with
      // large window size can set it larger than the blockSize, e.g., AFor
      termFreqBuffer.length = termFreqBlockSize;

      termFreqReadPending = false;
    }

    /**
     * Decode and return the next node label of the current block.
     *
     * <p>
     *
     * The {@link IntsRef} returned is a slice of the uncompressed node block.
     */
    public IntsRef nextNode() throws IOException {
      if (nodReadPending) {
        this.decodeNodes();
      }
      // decode delta
      this.deltaDecoding();
      return currentNode;
    }

    /**
     * Decode delta of the node.
     * <p>
     * If a new doc has been read (currentNode.length == 0), then update currentNode
     * offset and length. Otherwise, perform delta decoding.
     * <p>
     * Perform delta decoding while current node id and previous node id are
     * equals.
     */
    private final void deltaDecoding() {
      final int[] nodBufferInts = nodBuffer.ints;
      // increment length by one
      final int nodLength = nodLenBuffer.ints[nodLenBuffer.offset++] + 1;
      final int nodOffset = nodBuffer.offset;
      final int nodEnd = nodOffset + nodLength;

      final int currentNodeOffset = currentNode.offset;
      final int currentNodeEnd = currentNodeOffset + currentNode.length;

      for (int i = nodOffset, j = currentNodeOffset;
           i < nodEnd && j < currentNodeEnd; i++, j++) {
        nodBufferInts[i] += nodBufferInts[j];
        // if node ids are different, then stop decoding
        if (nodBufferInts[i] != nodBufferInts[j]) {
          break;
        }
      }

      // increment node buffer offset
      nodBuffer.offset += nodLength;
      // update last node offset and length
      currentNode.offset = nodOffset;
      currentNode.length = nodLength;
    }

    /**
     * Decode and return the next term frequency of the current block.
     */
    public int nextTermFreqInNode() throws IOException {
      if (termFreqReadPending) {
        this.decodeTermFreqs();
      }
      // increment freq by one
      return termFreqBuffer.ints[termFreqBuffer.offset++] + 1;
    }

    @Override
    public boolean isExhausted() {
      return nodLenBuffer.offset >= nodLenBuffer.length;
    }

    @Override
    public void initBlock() {
      nodLenBuffer.offset = nodLenBuffer.length = 0;
      nodBuffer.offset = nodBuffer.length = 0;
      termFreqBuffer.offset = termFreqBuffer.length = 0;
      this.resetCurrentNode();

      nodLenReadPending = true;
      nodReadPending = true;
      termFreqReadPending = true;

      nodLenCompressedBufferLength = 0;
      nodCompressedBufferLength = 0;
      termFreqCompressedBufferLength = 0;
    }

    public void resetCurrentNode() {
      currentNode.offset = currentNode.length = 0;
    }

  }

}
