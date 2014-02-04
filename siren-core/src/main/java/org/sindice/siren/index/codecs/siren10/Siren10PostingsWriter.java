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
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.MappingMultiDocsAndPositionsEnum;
import org.apache.lucene.codecs.PostingsWriterBase;
import org.apache.lucene.codecs.TermStats;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.index.MergeState;
import org.apache.lucene.index.SegmentWriteState;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.store.RAMOutputStream;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.FixedBitSet;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.analysis.filter.VIntPayloadCodec;
import org.sindice.siren.index.MappingMultiDocsNodesAndPositionsEnum;
import org.sindice.siren.index.codecs.block.BlockIndexOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Writes the document identifiers, node frequencies, node labels, term
 * frequencies, term positions and block skip data.
 */
public class Siren10PostingsWriter extends PostingsWriterBase {

  final static String                          CODEC                       = "Siren10PostingsWriter";

  final static String                          DOC_EXTENSION               = "doc";
  final static String                          SKIP_EXTENSION              = "skp";
  final static String                          NOD_EXTENSION               = "nod";
  final static String                          POS_EXTENSION               = "pos";

  // Increment version to change it:
  final static int                             VERSION_START               = 0;
  final static int                             VERSION_CURRENT             = VERSION_START;

  DocsFreqBlockIndexOutput                     docOut;
  DocsFreqBlockIndexOutput.DocsFreqBlockWriter docWriter;
  DocsFreqBlockIndexOutput.Index               docIndex;

  NodBlockIndexOutput                          nodOut;
  NodBlockIndexOutput.NodBlockWriter           nodWriter;
  NodBlockIndexOutput.Index                    nodIndex;

  PosBlockIndexOutput                          posOut;
  PosBlockIndexOutput.PosBlockWriter           posWriter;
  PosBlockIndexOutput.Index                    posIndex;

  IndexOutput                                  skipOut;
  IndexOutput                                  termsOut;

  final Siren10SkipListWriter skipWriter;

  /**
   * Expert: The fraction of blocks stored in skip tables,
   * used to accelerate {@link DocsEnum#advance(int)}.  Larger values result in
   * smaller indexes, greater acceleration, but fewer accelerable cases, while
   * smaller values result in bigger indexes, less acceleration and more
   * accelerable cases.
   */
  final int blockSkipInterval;
  static final int DEFAULT_BLOCK_SKIP_INTERVAL = 2;

  /**
   * Expert: minimum block to write any skip data at all
   */
  final int blockSkipMinimum;

  /**
   * Expert: maximum block size allowed.
   */
  final int maxBlockSize;

  /**
   * Expert: The maximum number of skip levels. Smaller values result in
   * slightly smaller indexes, but slower skipping in big posting lists.
   */
  final int maxSkipLevels = 10;

  final int totalNumDocs;

  IndexOptions indexOptions;

  FieldInfo fieldInfo;

  int blockCount;

  // Holds pending byte[] blob for the current terms block
  private final RAMOutputStream indexBytesWriter = new RAMOutputStream();

  protected static final Logger logger = LoggerFactory.getLogger(Siren10PostingsWriter.class);

  public Siren10PostingsWriter(final SegmentWriteState state,
                               final Siren10BlockStreamFactory factory)
  throws IOException {
    this(state, DEFAULT_BLOCK_SKIP_INTERVAL, factory);
  }

  public Siren10PostingsWriter(final SegmentWriteState state,
                               final int blockSkipInterval,
                               final Siren10BlockStreamFactory factory)
  throws IOException {
    nodOut = null;
    nodIndex = null;
    posOut = null;
    posIndex = null;
    boolean success = false;

    try {
      this.blockSkipInterval = blockSkipInterval;
      this.blockSkipMinimum = blockSkipInterval; /* set to the same for now */

      final String docFileName = IndexFileNames.segmentFileName(state.segmentInfo.name,
        state.segmentSuffix, DOC_EXTENSION);
      docOut = factory.createDocsFreqOutput(state.directory, docFileName, state.context);
      docWriter = docOut.getBlockWriter();
      docIndex = docOut.index();

      this.maxBlockSize = docWriter.getMaxBlockSize();

      final String nodFileName = IndexFileNames.segmentFileName(state.segmentInfo.name,
        state.segmentSuffix, NOD_EXTENSION);
      nodOut = factory.createNodOutput(state.directory, nodFileName, state.context);
      nodWriter = nodOut.getBlockWriter();
      nodIndex = nodOut.index();

      final String posFileName = IndexFileNames.segmentFileName(state.segmentInfo.name,
        state.segmentSuffix, POS_EXTENSION);
      posOut = factory.createPosOutput(state.directory, posFileName, state.context);
      posWriter = posOut.getBlockWriter();
      posIndex = posOut.index();

      final String skipFileName = IndexFileNames.segmentFileName(state.segmentInfo.name,
        state.segmentSuffix, SKIP_EXTENSION);
      skipOut = state.directory.createOutput(skipFileName, state.context);

      totalNumDocs = state.segmentInfo.getDocCount();

      // EStimate number of blocks that will be written
      final int numBlocks = (int) Math.ceil(totalNumDocs / (double) docWriter.getMaxBlockSize());
      skipWriter = new Siren10SkipListWriter(blockSkipInterval, maxSkipLevels,
        numBlocks, docOut);
      docWriter.setNodeBlockIndex(nodIndex);
      docWriter.setPosBlockIndex(posIndex);

      success = true;
    }
    finally {
      if (!success) {
        IOUtils.closeWhileHandlingException(docOut, skipOut, nodOut, posOut);
      }
    }
  }

  @Override
  public void start(final IndexOutput termsOut) throws IOException {
    this.termsOut = termsOut;
    CodecUtil.writeHeader(termsOut, CODEC, VERSION_CURRENT);
    termsOut.writeInt(blockSkipInterval);                // write skipInterval
    termsOut.writeInt(maxSkipLevels);               // write maxSkipLevels
    termsOut.writeInt(blockSkipMinimum);                 // write skipMinimum
    termsOut.writeInt(maxBlockSize);                 // write maxBlockSize
  }

  @Override
  public void startTerm() throws IOException {
    docIndex.mark();
    nodIndex.mark();
    posIndex.mark();

    skipWriter.resetSkip(docIndex);
  }

  // Currently, this instance is re-used across fields, so
  // our parent calls setField whenever the field changes
  @Override
  public void setField(final FieldInfo fieldInfo) {
    this.fieldInfo = fieldInfo;
    this.indexOptions = fieldInfo.getIndexOptions();
    if (indexOptions.compareTo(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0) {
      throw new UnsupportedOperationException("this codec cannot index offsets");
    }
    skipWriter.setIndexOptions(indexOptions);
  }

  /**
   * Adds a new doc in this term. If this returns null
   * then we just skip consuming positions.
   * <p>
   * {@code termDocFreq} parameter is ignored as term frequency in document is
   * not used.
   */
  @Override
  public void startDoc(final int docID, final int termDocFreq)
  throws IOException {
    if (docID < 0) {
      throw new CorruptIndexException("docs out of order (" + docID + ") (docOut: " + docOut + ")");
    }

    if (docWriter.isFull()) {
      if ((++blockCount % blockSkipInterval) == 0) {
        skipWriter.setSkipData(docWriter.getFirstDocId());
        skipWriter.bufferSkip(blockCount);
      }
      docWriter.flush();
      nodWriter.flush(); // flush node block to synchronise it with doc block
      posWriter.flush(); // flush pos block to synchronise it with doc block
    }

    docWriter.write(docID);

    // reset current node for delta computation
    nodWriter.resetCurrentNode();

    // reset payload hash to sentinel value
    lastNodeHash = Long.MAX_VALUE;
  }

  /**
   * Sentinel value {@link Long.MAX_VALUE} is necessary in order to avoid
   * equality with nodes composed of '0' values.
   * <p>
   * Use long to avoid collision between sentinel value and payload hashcode.
   * <p>
   * Using payload hashcode seems to be the fastest way for testing node
   * equality. See micro-benchmark {@link NodeEqualityBenchmark}.
   */
  private long lastNodeHash = Long.MAX_VALUE;

  private final VIntPayloadCodec sirenPayload = new VIntPayloadCodec();

  private int nodeFreqInDoc = 0;
  private int termFreqInNode = 0;

  @Override
  public void addPosition(final int position, final BytesRef payload,
                          final int startOffset, final int endOffset)
  throws IOException {
    assert indexOptions == IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
    // we always receive node ids in the payload
    assert payload != null;

    // decode payload
    sirenPayload.decode(payload);
    final IntsRef node = sirenPayload.getNode();

    // check if we received the same node
    // TODO: we pay the cost of decoding the node before testing the equality
    // we could instead directly compute the node hash based on the byte array
    final int nodeHash = node.hashCode();
    if (lastNodeHash != nodeHash) { // if different node
      // add term freq for previous node if not first payload.
      if (lastNodeHash != Long.MAX_VALUE) {
        this.addTermFreqInNode();
      }
      // add new node
      this.addNode(node);
    }
    lastNodeHash = nodeHash;

    // add position
    this.addPosition(sirenPayload.getPosition());
  }

  private void addNode(final IntsRef node) {
    nodWriter.write(node);
    nodeFreqInDoc++;
    // reset current position for delta computation
    posWriter.resetCurrentPosition();
  }

  private void addPosition(final int position) {
    posWriter.write(position);
    termFreqInNode++;
  }

  private void addNodeFreqInDoc() {
    docWriter.writeNodeFreq(nodeFreqInDoc);
    nodeFreqInDoc = 0;
  }

  private void addTermFreqInNode() {
    nodWriter.writeTermFreq(termFreqInNode);
    termFreqInNode = 0;
  }

  @Override
  public void finishDoc() {
    this.addNodeFreqInDoc();
    this.addTermFreqInNode();
  }

  private static class PendingTerm {

    public final BlockIndexOutput.Index docIndex;
    public final long skipFP;
    public final int blockCount;

    public PendingTerm(final BlockIndexOutput.Index docIndex,
                       final long skipFP, final int blockCount) {
      this.docIndex = docIndex;
      this.skipFP = skipFP;
      this.blockCount = blockCount;
    }
  }

  private final List<PendingTerm> pendingTerms = new ArrayList<PendingTerm>();

  /**
   * Called when we are done adding docs to this term
   */
  @Override
  public void finishTerm(final TermStats stats) throws IOException {
    assert stats.docFreq > 0;

    // if block flush pending, write last skip data
    if (!docWriter.isEmpty() && (++blockCount % blockSkipInterval) == 0) {
      skipWriter.setSkipData(docWriter.getFirstDocId());
      skipWriter.bufferSkip(blockCount);
    }

    // flush doc block
    docWriter.flush();
    final BlockIndexOutput.Index docIndexCopy = docOut.index();
    docIndexCopy.copyFrom(docIndex, false);

    // flush node block
    nodWriter.flush();
    final BlockIndexOutput.Index nodIndexCopy = nodOut.index();
    nodIndexCopy.copyFrom(nodIndex, false);

    // flush pos block
    posWriter.flush();
    final BlockIndexOutput.Index posIndexCopy = posOut.index();
    posIndexCopy.copyFrom(posIndex, false);

    // Write skip data to the output file
    final long skipFP;
    if (blockCount >= blockSkipMinimum) {
      skipFP = skipOut.getFilePointer();
      skipWriter.writeSkip(skipOut);
    }
    else {
      skipFP = -1;
    }

    pendingTerms.add(new PendingTerm(docIndexCopy, skipFP, blockCount));

    // reset block counter
    blockCount = 0;
  }

  @Override
  public void flushTermsBlock(final int start, final int count) throws IOException {
    // logger.debug("flushTermsBlock: {}", this.hashCode());
    assert indexBytesWriter.getFilePointer() == 0;
    final int absStart = pendingTerms.size() - start;
    final List<PendingTerm> slice = pendingTerms.subList(absStart, absStart+count);

    long lastSkipFP = 0;

    if (count == 0) {
      termsOut.writeByte((byte) 0);
      return;
    }

    final PendingTerm firstTerm = slice.get(0);
    final BlockIndexOutput.Index docIndexFlush = firstTerm.docIndex;

    for (int idx = 0; idx < slice.size(); idx++) {
      final boolean isFirstTerm = idx == 0;
      final PendingTerm t = slice.get(idx);

      // write block count stat
      // logger.debug("Write blockCount: {}", t.blockCount);
      indexBytesWriter.writeVInt(t.blockCount);

      docIndexFlush.copyFrom(t.docIndex, false);
      // logger.debug("Write docIndex: {}", docIndexFlush);
      docIndexFlush.write(indexBytesWriter, isFirstTerm);

      if (t.skipFP != -1) {
        if (isFirstTerm) {
          indexBytesWriter.writeVLong(t.skipFP);
        }
        else {
          indexBytesWriter.writeVLong(t.skipFP - lastSkipFP);
        }
        lastSkipFP = t.skipFP;
      }
    }

    termsOut.writeVLong((int) indexBytesWriter.getFilePointer());
    indexBytesWriter.writeTo(termsOut);
    indexBytesWriter.reset();
    slice.clear();
  }

  @Override
  public void close() throws IOException {
    IOUtils.close(docOut, skipOut, nodOut, posOut);
  }

  private final MappingMultiDocsNodesAndPositionsEnum postingsEnum = new MappingMultiDocsNodesAndPositionsEnum();

  /**
   * Default merge impl: append documents, nodes and positions, mapping around
   * deletes.
   * <p>
   * Bypass the {@link Siren10PostingsWriter} methods and work directly with
   * the BlockWriters for maximum efficiency.
   * <p>
   * TODO - Optimisation: If document blocks match the block size, and no
   * document deleted, then it would be possible to copy block directly as byte
   * array, avoiding decoding and encoding.
   **/
  @Override
  public TermStats merge(final MergeState mergeState, final DocsEnum postings,
                         final FixedBitSet visitedDocs)
  throws IOException {
    int df = 0;
    long totTF = 0;

    postingsEnum.setMergeState(mergeState);
    postingsEnum.reset((MappingMultiDocsAndPositionsEnum) postings);

    while (postingsEnum.nextDocument()) {
      final int doc = postingsEnum.doc();
      visitedDocs.set(doc);

      this.startDoc(doc, -1);

      final int nodeFreq = postingsEnum.nodeFreqInDoc();
      docWriter.writeNodeFreq(nodeFreq);

      while (postingsEnum.nextNode()) {
        final IntsRef node = postingsEnum.node();
        nodWriter.write(node);

        final int termFreqInNode = postingsEnum.termFreqInNode();
        nodWriter.writeTermFreq(termFreqInNode);

        // reset current position for delta computation
        posWriter.resetCurrentPosition();

        while (postingsEnum.nextPosition()) {
          final int position = postingsEnum.pos();
          posWriter.write(position);
          totTF++;
        }
      }
      df++;
    }

    return new TermStats(df, totTF);
  }

}
