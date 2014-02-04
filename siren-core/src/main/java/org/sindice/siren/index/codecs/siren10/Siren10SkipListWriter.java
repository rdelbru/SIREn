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
import java.util.Arrays;

import org.apache.lucene.codecs.MultiLevelSkipListWriter;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.store.IndexOutput;
import org.sindice.siren.index.codecs.block.BlockIndexOutput;

/**
 * Implementation of the {@link MultiLevelSkipListWriter} for the default
 * block-based posting list format of SIREn 1.0.
 *
 * <p>
 *
 * Compared to the original Lucene format, this skip list is only storing the
 * document identifiers and the file pointer of the block within the .doc file.
 *
 * <p>
 *
 * The {@link MultiLevelSkipListWriter} implementation is based on document
 * count, but it is used here with block count instead of document count.
 * In order to make it compatible with block, this class is converting
 * document count into block count.
 */
public class Siren10SkipListWriter extends MultiLevelSkipListWriter {

  private final int[] lastSkipDoc;

  private final BlockIndexOutput.Index[] docIndex;

  private int curDoc;

  Siren10SkipListWriter(final int blockSkipInterval, final int maxSkipLevels,
                        final int blockCount, final BlockIndexOutput docOutput)
  throws IOException {
    super(blockSkipInterval, maxSkipLevels, blockCount);

    lastSkipDoc = new int[numberOfSkipLevels];

    docIndex = new BlockIndexOutput.Index[numberOfSkipLevels];

    for(int i = 0; i < numberOfSkipLevels; i++) {
      docIndex[i] = docOutput.index();
    }
  }

  IndexOptions indexOptions;

  void setIndexOptions(final IndexOptions v) {
    indexOptions = v;
  }

  /**
   * Sets the values for the current skip data.
   * <p>
   * Called at every index interval (every block by default)
   */
  void setSkipData(final int doc) {
    this.curDoc = doc;
  }

  /**
   * Called at start of new term
   */
  protected void resetSkip(final BlockIndexOutput.Index topDocIndex)
  throws IOException {
    super.resetSkip();

    Arrays.fill(lastSkipDoc, 0);
    for(int i = 0; i < numberOfSkipLevels; i++) {
      docIndex[i].copyFrom(topDocIndex, true);
    }
  }

  @Override
  protected void writeSkipData(final int level, final IndexOutput skipBuffer) throws IOException {
    skipBuffer.writeVInt(curDoc - lastSkipDoc[level]);

    docIndex[level].mark();
    docIndex[level].write(skipBuffer, false);

    lastSkipDoc[level] = curDoc;
  }
}
