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
/**
 * Implementation of the encoding and decoding of the block-based
 * postings format for SIREn 1.0.
 *
 * <h2>Introduction</h2>
 *
 * This package contains the implementation of the encoding and decoding of the
 * SIREn 1.0 block-based postings format. This postings format is compatible
 * with the Lucene 4.0 codec. For an introduction to Lucene 4.0 codec API, see
 * the {@link org.apache.lucene.codecs.lucene40} package documentation.
 *
 * <h2>SIREn 1.0 Postings Format</h2>
 *
 * The SIREn 1.0 postings format is organised around four files:
 * <ul>
 *   <li> The .doc file contains the document identifiers and node frequencies;
 *   <li> The .nod file contains the node labels and the term frequencies;
 *   <li> The .pos file contains the term positions;
 *   <li> The .skp file contains the skip data.
 * </ul>
 *
 * The SIREn 1.0 postings format is divided into multiple blocks. The default
 * block size is defined by
 * {@link org.sindice.siren.index.codecs.siren10.Siren10PostingsFormat#DEFAULT_POSTINGS_BLOCK_SIZE}.
 *
 * <h3>Documents and Node Frequencies</h3>
 *
 * The .doc file contains a list of document identifiers for each
 * term along with the node frequency of the term in that document. The
 * document identifiers are ordered by increasing number.
 *
 * <p>
 *
 * The file contains one postings lists for each term. The term is implicit
 * and the position of the start of the postings list for a term is provided
 * by the term dictionary.
 *
 * <p>
 *
 * One postings list is organised by block. Each block has a maximum size which
 * determines the maximum number of document identifiers it can contain. It is
 * possible that one block contain less document identifiers than its maximum
 * size. For example, if a postings list contain only one document identifier,
 * the size of the block will be one.
 *
 * The block format follows the schema:
 * <pre>
 *   Block  = Header, CompressedDoc, CompressedNodeFreq
 *   Header = BlockSize,
 *            CompressedDocSize, CompressedNodeFreqSize,
 *            FirstDocId, LastDocId,
 *            NodeBlockPointer, PosBlockPointer
 *   CompressedDoc      = [DeltaDocId]
 *   CompressedNodeFreq = [NodeFreq]
 * </pre>
 *
 * <b>BlockSize</b> records the size of the block, i.e., the number of document
 * identifiers.
 * <p>
 * <b>CompressedDocLength</b> records the size (in bytes) of the compressed byte
 * array CompressedDoc.
 * <p>
 * <b>CompressedNodeFreqLength</b> records the size (in bytes) of the compressed
 * byte array CompressedNodeFreq.
 * <p>
 * <b>FirstDocId</b> and <b>LastDocId</b> records the first and last document identifiers
 * of the block. This information is used by the skip list algorithm. The
 * LastDocId is encoded as delta between the FirstDocId.
 * <p>
 * <b>NodeBlockPointer</b> records the pointer of the node block in the .nod file that
 * is associated to this block.
 * <p>
 * <b>PosBlockPointer</b> records the pointer of the position block in the .pos file
 * that is associated to this block.
 * <p>
 * <b>CompressedDoc</b> is the compressed list of document identifiers. This list is
 * compressed using the AFOR algorithm. The document identifiers are encoded
 * as delta. The first document of this list is always 0 as it is encoded as
 * delta with FirstDocId.
 * <p>
 * <b>CompressedNodeFreq</b> is the compressed list of node frequencies. This list is
 * compressed using the AFOR algorithm. There is one node frequency per document
 * identifier. The node frequency is encoded with a decrement of 1 to optimise
 * AFOR compression: it gives a higher chance to get smaller bit frames.
 *
 * <h3>Node Labels and Term Frequencies</h3>
 *
 * The .nod file contains a list of node labels for each
 * document along with the length of each node labels, and the frequency of the
 * term for each node. The node labels are ordered by increasing dewey codes.
 *
 * <p>
 *
 * The file is organised by block. Each block is synchronised with a .doc block,
 * i.e., a single block contains all the node labels for the complete set of
 * document identifiers contained in the .doc block. A block has a variable size
 * which is determined by the number of node labels associated with the
 * documents. Synchronising blocks across files simplifies encoding and decoding
 * instructions and improves the performance.
 *
 * <p>
 *
 * The block format follows the schema:
 * <pre>
 *   Block  = Header, CompressedNodeLength, CompressedNode, CompressedTermFreq
 *   Header = NodeLengthBlockSize, NodeBlockSize, TermFreqBlockSize,
 *            CompressedNodeLengthSize, CompressedNodeSize, CompressedTermFreqSize
 *   CompressedNodeLength = [NodeLength]
 *   CompressedNode       = [DeltaNode]
 *   CompressedTermFreq   = [TermFreq]
 * </pre>
 *
 * <b>NodeLengthBlockSize</b> records the size of the block of the node lengths,
 * i.e., the number of node lengths.
 * <p>
 * <b>NodeBlockSize</b> records the size of the node block, i.e., the number of
 * integers composing the node labels.
 * <p>
 * <b>TermFreqBlockSize</b> records the size of the term frequency block, i.e.,
 * the number of term frequencies.
 * <p>
 * <b>CompressedNodeLengthSize</b> records the size (in bytes) of the compressed
 * byte array CompressedNodeLength.
 * <p>
 * <b>CompressedNodeSize</b> records the size (in bytes) of the compressed
 * byte array CompressedNode.
 * <p>
 * <b>CompressedTermFreqSize</b> records the size (in bytes) of the compressed
 * byte array CompressedTermFreq.
 * <p>
 * <b>CompressedNodeLength</b> is the compressed list of node lengths. Since
 * each node label can have a different length, the node length records the
 * number of integers that composes a node label. This list
 * is compressed using the AFOR algorithm. The node frequency is encoded with a
 * decrement of 1 to optimise AFOR compression: it gives a higher chance to get
 * smaller bit frames.
 * <p>
 * <b>CompressedNode</b> is the compressed list of node labels. This list is
 * compressed using the AFOR algorithm. The node labels relative to a document
 * are encoded as delta.
 * <p>
 * <b>CompressedTermFreq</b> is the compressed list of term frequencies. This list is
 * compressed using the AFOR algorithm. There is one term frequency per node
 * label. The node frequency is encoded with a decrement of 1 to optimise
 * AFOR compression: it gives a higher chance to get smaller bit frames.
 *
 * <h3>Term Positions</h3>
 *
 * The .pos file contains the list of term positions within nodes. The term
 * positions are ordered by increasing number.
 *
 * <p>
 *
 * The file is organised by block. Each block is synchronised with a .nod block,
 * i.e., a single block contains all the term positions for the complete set of
 * node labels contained in the .nod block. A block has a variable size
 * which is determined by the number of term positions associated with the
 * nodes. Synchronising blocks across files simplifies encoding and decoding
 * instructions and improves the performance.
 *
 * <p>
 *
 * The block format follows the schema:
 * <pre>
 *   Block  = Header, CompressedTermPos
 *   Header = TermPosBlockSize,
 *            CompressedTermPosSize
 *   CompressedTermPos  = [DeltaTermPos]
 * </pre>
 *
 * <b>TermPosBlockSize</b> records the size of the term position block, i.e.,
 * the number of term positions.
 * <p>
 * <b>CompressedTermPosSize</b> records the size (in bytes) of the compressed
 * byte array CompressedTermPos.
 * <p>
 * <b>CompressedTermPos</b> is the compressed list of term positions. This list
 * is compressed using the AFOR algorithm. There is one or more term positions
 * per node. The number of term positions per node is provided by the .nod block
 * with the term frequency information. The term positions relative to a node
 * are encoded as delta.
 *
 * <h3>Skip Lists</h3>
 *
 * The .skp file contains the skip data. The structure of the skip table
 * is quite similar to Lucene40PostingsFormat. However, the skip data is defined
 * around the concept of block instead of document. The skip interval defines
 * the number of blocks between each skip data. The default skip interval is
 * defined by
 * {@link org.sindice.siren.index.codecs.siren10.Siren10PostingsFormat#DEFAULT_POSTINGS_BLOCK_SIZE}.
 * Each skip entry points to the beginning of one block.
 *
 * <p>
 *
 * In contrast to the Lucene skip lists, part of the skip data is inlined within
 * the .doc file. The pointers to the .nod block and .pos block associated to
 * the .doc block are encoded in the header of the .doc file by NodeBlockPointer
 * and PosBlockPointer.
 *
 * <p>
 *
 * For more information about our skip table approach over blocks, please refer
 * to the publication <a href="http://dx.doi.org/10.1007/978-3-642-20161-5_55">
 * SkipBlock: Self-indexing for Block-Based Inverted List</a>.
 *
 * <h2>Interaction with the Postings List</h2>
 *
 * The reading of the SIREn 1.0 postings format relies on the lazy-loading
 * approach. Any information, e.g., term positions, term frequencies,
 * node labels and node frequencies, that are not requested explicitly are (1)
 * never decoded, and (2) not read from disk whenever possible.
 *
 */
package org.sindice.siren.index.codecs.siren10;

