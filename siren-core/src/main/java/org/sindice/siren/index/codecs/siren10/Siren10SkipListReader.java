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

import org.apache.lucene.codecs.MultiLevelSkipListReader;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.store.IndexInput;
import org.sindice.siren.index.codecs.block.BlockIndexInput;

/**
 * Implementation of the {@link MultiLevelSkipListReader} for the default
 * block-based posting list format of SIREn 1.0.
 *
 * <p>
 *
 * The {@link MultiLevelSkipListReader} implementation is based on document
 * count, but it is used here with block count instead of document count.
 * In order to make it compatible with block, this class is converting
 * document count into block count and vice versa.
 *
 * @see Siren10SkipListWriter
 */
public class Siren10SkipListReader extends MultiLevelSkipListReader {

  private final BlockIndexInput.Index docIndex[];

  private final BlockIndexInput.Index lastDocIndex;

  private final int blockSize;

  Siren10SkipListReader(final IndexInput skipStream,
                        final BlockIndexInput docIn,
                        final int maxSkipLevels,
                        final int blockSkipInterval,
                        final int blockSize)
  throws IOException {
    super(skipStream, maxSkipLevels, blockSkipInterval);
    this.blockSize = blockSize;
    docIndex = new BlockIndexInput.Index[maxSkipLevels];
    for (int i = 0; i < maxSkipLevels; i++) {
      docIndex[i] = docIn.index();
    }
    lastDocIndex = docIn.index();
  }

  IndexOptions indexOptions;

  void setIndexOptions(final IndexOptions v) {
    indexOptions = v;
  }

  void init(final long skipPointer,
            final BlockIndexInput.Index docBaseIndex,
            final int blockCount) {
    super.init(skipPointer, blockCount);

    for (int i = 0; i < maxNumberOfSkipLevels; i++) {
      docIndex[i].set(docBaseIndex);
    }
  }

  @Override
  protected void seekChild(final int level) throws IOException {
    super.seekChild(level);
  }

  @Override
  protected void setLastSkipData(final int level) {
    super.setLastSkipData(level);

    lastDocIndex.set(docIndex[level]);

    if (level > 0) {
      docIndex[level-1].set(docIndex[level]);
    }
  }

  BlockIndexInput.Index getDocIndex() {
    return lastDocIndex;
  }

  @Override
  protected int readSkipData(final int level, final IndexInput skipStream) throws IOException {
    final int delta = skipStream.readVInt();
    docIndex[level].read(skipStream, false);
    return delta;
  }

  @Override
  public int skipTo(final int target) throws IOException {
    // multiply by blockSize to get the doc counts.
    return super.skipTo(target) * blockSize;
  }

}

