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
import org.sindice.siren.util.ArrayUtils;

/**
 * Implementation of the {@link BlockIndexOutput} for the .nod file of the SIREn
 * postings format.
 */
public class NodBlockIndexOutput extends BlockIndexOutput {

  private final int maxBlockSize;
  private final BlockCompressor nodCompressor;

  public NodBlockIndexOutput(final IndexOutput out, final int maxBlockSize,
                             final BlockCompressor nodCompressor)
  throws IOException {
    super(out);
    this.nodCompressor = nodCompressor;
    this.maxBlockSize = maxBlockSize;
  }

  @Override
  public NodBlockWriter getBlockWriter() {
    return new NodBlockWriter();
  }

  /**
   * Implementation of the {@link BlockWriter} for the .nod file.
   *
   * <p>
   *
   * Encode and write blocks containing the node labels and term frequencies.
   *
   * <p>
   *
   * TODO: Can we try to reduce the number of test conditions for buffer size by
   * using term frequency information ? At each new document, nodBlockWriter is
   * informed of the term frequency, and check buffer size appropriately.
   */
  protected class NodBlockWriter extends BlockWriter {

    IntsRef nodLenBuffer;
    IntsRef nodBuffer;
    IntsRef termFreqBuffer;

    BytesRef nodLenCompressedBuffer;
    BytesRef nodCompressedBuffer;

    BytesRef termFreqCompressedBuffer;

    public NodBlockWriter() {
      // ensure that the input buffers has the minimum size required
      // maxBlockSize is just use as a minimum initial capacity for the buffers
      nodLenBuffer = new IntsRef(this.getMinimumBufferSize(maxBlockSize, nodCompressor.getWindowSize()));
      nodBuffer = new IntsRef(this.getMinimumBufferSize(maxBlockSize, nodCompressor.getWindowSize()));
      termFreqBuffer = new IntsRef(this.getMinimumBufferSize(maxBlockSize, nodCompressor.getWindowSize()));

      // init of the compressed buffers
      nodLenCompressedBuffer = new BytesRef();
      nodCompressedBuffer = new BytesRef();
      termFreqCompressedBuffer = new BytesRef();
    }

    /**
     * Add a node label to the buffer.
     */
    public void write(final IntsRef node) {
      final int nodeOffset = node.offset;
      final int nodeLength = node.length;
      final int[] nodeInts = node.ints;

      assert nodeLength > 0;

      /*
       * write node
       */

      int[] nodBufferInts = nodBuffer.ints;
      final int nodBufferOffset = nodBuffer.offset;

      // increase buffers if needed
      if (nodBufferOffset + nodeLength >= nodBufferInts.length) {
        // Take the max to ensure that buffer will be large enough
        int newLength = Math.max(nodBufferOffset + nodeLength, nodBufferInts.length * 3/2);
        // ensure that the buffer is large enough to accommodate the window size
        newLength = this.getMinimumBufferSize(newLength, nodCompressor.getWindowSize());
        ArrayUtils.growAndCopy(nodBuffer, newLength);
        // update reference of the buffer's int array
        nodBufferInts = nodBuffer.ints;
      }

      // compute delta encoding and copy to buffer
      this.deltaEncodingAndCopy(nodeInts, nodeOffset, nodeLength, nodBufferInts, nodBufferOffset);
      // cache current node for next delta encoding
      this.cacheNode(nodeInts, nodeOffset, nodeLength);
      // increment node buffer offset with node length
      nodBuffer.offset += nodeLength;

      /*
       * write node length
       */

      int[] nodLenBufferInts = nodLenBuffer.ints;

      // increase node length buffer if needed
      if (nodLenBuffer.offset >= nodLenBufferInts.length) {
        // Take the max to ensure that buffer will be large enough
        int newLength = Math.max(nodLenBuffer.offset + 1, nodLenBufferInts.length * 3/2);
        // ensure that the buffer is large enough to accomodate the window size
        newLength = this.getMinimumBufferSize(newLength, nodCompressor.getWindowSize());
        ArrayUtils.growAndCopy(nodLenBuffer, newLength);
        // update reference of the buffer's int array
        nodLenBufferInts = nodLenBuffer.ints;
      }
      // decrement length by one
      nodLenBufferInts[nodLenBuffer.offset++] = nodeLength - 1;
    }

    /**
     * Node cache used for computing the delta of a node label.
     */
    private IntsRef nodeCache = new IntsRef(DEFAULT_NODE_CACHE_SIZE);
    private static final int DEFAULT_NODE_CACHE_SIZE = 16;

    /**
     * Cache the given node
     */
    private final void cacheNode(final int[] nodeInts, final int nodeOffset, final int nodeLen) {
      int[] nodeCacheInts = nodeCache.ints;

      // ensure that the cache is large enough to accommodate the node array
      if (nodeLen > nodeCacheInts.length) {
        nodeCache = ArrayUtils.grow(nodeCache, nodeLen);
        // update reference of the cache's int array reference
        nodeCacheInts = nodeCache.ints;
      }

      System.arraycopy(nodeInts, nodeOffset, nodeCacheInts, 0, nodeLen);
      nodeCache.offset = 0;
      nodeCache.length = nodeLen;
    }

    /**
     * Compute the delta of the new node based on the cache node and copy
     * the delta encoding to the node buffer
     */
    private final void deltaEncodingAndCopy(final int[] nodeInts,
                                            final int nodeOffset,
                                            final int nodeLen,
                                            final int[] nodeBufferInts,
                                            final int nodeBufferOffset) {
      final int nodEnd = nodeOffset + nodeLen;
      final int[] nodeCacheInts = nodeCache.ints;
      final int nodeCacheOffset = nodeCache.offset;
      final int nodeCacheEnd = nodeCacheOffset + nodeCache.length;

      int i, j, k;

      // iterate over the node levels
      for (i = nodeCacheOffset, j = nodeOffset, k = 0;
           i < nodeCacheEnd && j < nodEnd && nodeCacheInts[i] <= nodeInts[j];
           i++, j++, k++) {

        // if previous node id is inferior to current node id, then we must stop
        // delta encoding
        if (nodeCacheInts[i] < nodeInts[j]) {
          // compute delta
          nodeBufferInts[nodeBufferOffset + k] = nodeInts[j] - nodeCacheInts[i];
          // increment for preparing copy of remaining ids
          j += 1;
          k += 1;
          // stop iteration
          break;
        }

        // otherwise if equal, compute delta (== 0) and move to next level
        nodeBufferInts[nodeBufferOffset + k] = 0;
      }
      // copy the remaining ids
      for (; j < nodEnd; j++, k++) {
        nodeBufferInts[nodeBufferOffset + k] = nodeInts[j];
      }
    }

    /**
     * Add the term frequency within the current node to the buffer
     */
    public void writeTermFreq(final int termFreq) {
      // check size of the buffer and increase it if needed
      if (termFreqBuffer.offset >= termFreqBuffer.ints.length) {
        // Take the max to ensure that buffer will be large enough
        int newLength = Math.max(termFreqBuffer.offset + 1, termFreqBuffer.ints.length * 3/2);
        // ensure that the buffer is large enough to accomodate the window size
        newLength = this.getMinimumBufferSize(newLength, nodCompressor.getWindowSize());
        ArrayUtils.growAndCopy(termFreqBuffer, newLength);
      }
      // decrement freq by one
      termFreqBuffer.ints[termFreqBuffer.offset++] = termFreq - 1;
    }

    @Override
    public boolean isEmpty() {
      return nodBuffer.offset == 0;
    }

    @Override
    public boolean isFull() {
      // this implementation is never full as it is synchronised with doc block
      // and grows on demand
      return false;
    }

    @Override
    protected void writeHeader() throws IOException {
      // logger.debug("Write Nod header: {}", this.hashCode());
      // logger.debug("Nod header start at {}", out.getFilePointer());

      // write block sizes
      out.writeVInt(nodLenBuffer.length);
      out.writeVInt(nodBuffer.length);
      out.writeVInt(termFreqBuffer.length);
      assert nodLenBuffer.length <= nodBuffer.length;

      // write size of compressed data blocks
      out.writeVInt(nodLenCompressedBuffer.length);
      out.writeVInt(nodCompressedBuffer.length);
      out.writeVInt(termFreqCompressedBuffer.length);
    }

    @Override
    protected void compress() {
      // Flip buffers before compression
      nodLenBuffer.length = nodLenBuffer.offset;
      nodLenBuffer.offset = 0;

      nodBuffer.length = nodBuffer.offset;
      nodBuffer.offset = 0;

      termFreqBuffer.length = termFreqBuffer.offset;
      termFreqBuffer.offset = 0;

      // determine max size of compressed buffer to avoid overflow
      int size = nodCompressor.maxCompressedSize(nodLenBuffer.length);
      nodLenCompressedBuffer = ArrayUtils.grow(nodLenCompressedBuffer, size);

      size = nodCompressor.maxCompressedSize(nodBuffer.length);
      nodCompressedBuffer = ArrayUtils.grow(nodCompressedBuffer, size);

      size = nodCompressor.maxCompressedSize(termFreqBuffer.length);
      termFreqCompressedBuffer = ArrayUtils.grow(termFreqCompressedBuffer, size);

      // compress
      nodCompressor.compress(nodLenBuffer, nodLenCompressedBuffer);
      nodCompressor.compress(nodBuffer, nodCompressedBuffer);
      nodCompressor.compress(termFreqBuffer, termFreqCompressedBuffer);
    }

    @Override
    protected void writeData() throws IOException {
      // logger.debug("Write Node data: {}", this.hashCode());
      // logger.debug("Write Node Length at {}", out.getFilePointer());
      out.writeBytes(nodLenCompressedBuffer.bytes, nodLenCompressedBuffer.length);
      // logger.debug("Write Node at {}", out.getFilePointer());
      out.writeBytes(nodCompressedBuffer.bytes, nodCompressedBuffer.length);
      // logger.debug("Write Term Freq in Node at {}", out.getFilePointer());
      out.writeBytes(termFreqCompressedBuffer.bytes, termFreqCompressedBuffer.length);
    }

    @Override
    protected void initBlock() {
      nodLenBuffer.offset = nodLenBuffer.length = 0;
      nodBuffer.offset = nodBuffer.length = 0;
      termFreqBuffer.offset = termFreqBuffer.length = 0;
      this.resetCurrentNode();
    }

    protected void resetCurrentNode() {
      nodeCache.offset = 0;
      nodeCache.length = 0;
    }

  }

}
