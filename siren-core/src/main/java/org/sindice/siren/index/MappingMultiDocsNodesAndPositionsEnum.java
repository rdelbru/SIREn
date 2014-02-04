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

package org.sindice.siren.index;

import java.io.IOException;

import org.apache.lucene.codecs.MappingMultiDocsAndPositionsEnum;
import org.apache.lucene.index.MergeState;
import org.apache.lucene.index.MergeState.DocMap;
import org.apache.lucene.index.MultiDocsAndPositionsEnum;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.index.codecs.siren10.Siren10PostingsWriter;

/**
 * Exposes SIREn API, merged from SIREn API of sub-segments, remapping docIDs
 * (this is used for segment merging).
 *
 * @see MappingMultiDocsAndPositionsEnum
 * @see Siren10PostingsWriter#merge(MergeState, org.apache.lucene.index.DocsEnum, org.apache.lucene.util.FixedBitSet)
 */
public class MappingMultiDocsNodesAndPositionsEnum
extends DocsNodesAndPositionsEnum {

  private MultiDocsAndPositionsEnum.EnumWithSlice[] subs;
  int numSubs;
  int upto;
  DocMap currentMap;
  DocsNodesAndPositionsEnum current;
  int currentBase;
  int doc = -1;
  private MergeState mergeState;

  public MappingMultiDocsNodesAndPositionsEnum reset(final MappingMultiDocsAndPositionsEnum postingsEnum)
  throws IOException {
    this.numSubs = postingsEnum.getNumSubs();
    this.subs = postingsEnum.getSubs();
    upto = -1;
    current = null;
    return this;
  }

  public void setMergeState(final MergeState mergeState) {
    this.mergeState = mergeState;
  }

  @Override
  public boolean nextDocument() throws IOException {
    while(true) {
      if (current == null) {
        if (upto == numSubs-1) {
          this.doc = NO_MORE_DOC;
          return false;
        }
        else {
          upto++;
          final int reader = subs[upto].slice.readerIndex;
          current = ((SirenDocsEnum) subs[upto].docsAndPositionsEnum).getDocsNodesAndPositionsEnum();
          currentBase = mergeState.docBase[reader];
          currentMap = mergeState.docMaps[reader];
        }
      }

      if (current.nextDocument()) {
        int doc = current.doc();
        if (currentMap != null) {
          // compact deletions
          doc = currentMap.get(doc);
          if (doc == -1) {
            continue;
          }
        }
        this.doc = currentBase + doc;
        return true;
      }
      else {
        current = null;
      }
    }
  }

  @Override
  public boolean nextNode() throws IOException {
    return current.nextNode();
  }

  @Override
  public boolean skipTo(final int target) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public int doc() {
    return doc;
  }

  @Override
  public IntsRef node() {
    return current.node();
  }

  @Override
  public boolean nextPosition() throws IOException {
    return current.nextPosition();
  }

  @Override
  public int pos() {
    return current.pos();
  }

  @Override
  public int termFreqInNode() throws IOException {
    return current.termFreqInNode();
  }

  @Override
  public int nodeFreqInDoc() throws IOException {
    return current.nodeFreqInDoc();
  }

}
