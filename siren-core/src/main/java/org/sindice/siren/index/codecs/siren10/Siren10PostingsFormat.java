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

import org.apache.lucene.codecs.BlockTreeTermsReader;
import org.apache.lucene.codecs.BlockTreeTermsWriter;
import org.apache.lucene.codecs.FieldsConsumer;
import org.apache.lucene.codecs.FieldsProducer;
import org.apache.lucene.codecs.PostingsFormat;
import org.apache.lucene.codecs.PostingsReaderBase;
import org.apache.lucene.codecs.PostingsWriterBase;
import org.apache.lucene.index.SegmentReadState;
import org.apache.lucene.index.SegmentWriteState;

/**
 * Abstraction over the SIREn 1.0 postings format.
 *
 * <p>
 *
 * Subclasses must provide a pre-configured instance of the
 * {@link Siren10BlockStreamFactory}.
 */
public abstract class Siren10PostingsFormat extends PostingsFormat {

  static final int DEFAULT_POSTINGS_BLOCK_SIZE = 32;

  protected final int blockSize;

  public Siren10PostingsFormat(final String name) {
    this(name, DEFAULT_POSTINGS_BLOCK_SIZE);
  }

  /**
   * Create a SIREn 1.0 posting format.
   * <p>
   * The block size parameter is used only during indexing.
   */
  public Siren10PostingsFormat(final String name, final int blockSize) {
    super(name);
    this.blockSize = blockSize;
  }

  protected abstract Siren10BlockStreamFactory getFactory();

  @Override
  public FieldsConsumer fieldsConsumer(final SegmentWriteState state)
  throws IOException {
    final PostingsWriterBase docs = new Siren10PostingsWriter(state,
      this.getFactory());

    boolean success = false;
    try {
      final FieldsConsumer ret = new BlockTreeTermsWriter(state, docs,
        BlockTreeTermsWriter.DEFAULT_MIN_BLOCK_SIZE,
        BlockTreeTermsWriter.DEFAULT_MAX_BLOCK_SIZE);
      success = true;
      return ret;
    }
    finally {
      if (!success) {
        docs.close();
      }
    }
  }

  @Override
  public FieldsProducer fieldsProducer(final SegmentReadState state)
  throws IOException {
    final PostingsReaderBase postings = new Siren10PostingsReader(state.dir,
      state.segmentInfo, state.context, state.segmentSuffix,
      this.getFactory());

    boolean success = false;
    try {
      final FieldsProducer ret = new BlockTreeTermsReader(state.dir,
                                                    state.fieldInfos,
                                                    state.segmentInfo,
                                                    postings,
                                                    state.context,
                                                    state.segmentSuffix,
                                                    state.termsIndexDivisor);
      success = true;
      return ret;
    }
    finally {
      if (!success) {
        postings.close();
      }
    }
  }

}
