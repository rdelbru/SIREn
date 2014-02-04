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

import org.apache.lucene.index.MultiDocsAndPositionsEnum.EnumWithSlice;
import org.apache.lucene.index.ReaderSlice;
import org.apache.lucene.util.IntsRef;

/**
 * Exposes SIREn API, merged from SIREn API of sub-segments.
 */
public class MultiDocsNodesAndPositionsEnum extends DocsNodesAndPositionsEnum {

  private final SirenEnumWithSlice[] subs;
  int numSubs;
  int upto;
  DocsNodesAndPositionsEnum current;
  int currentBase;
  int doc = -1;

  public MultiDocsNodesAndPositionsEnum(final EnumWithSlice[] subs, final int numSubs) {
    this.numSubs = numSubs;
    this.subs = new SirenEnumWithSlice[numSubs];
    for(int i = 0; i < numSubs; i++) {
      this.subs[i] = new SirenEnumWithSlice();
      this.subs[i].docsNodesAndPositionsEnum = ((SirenDocsEnum) subs[i].docsAndPositionsEnum).getDocsNodesAndPositionsEnum();
      this.subs[i].slice = subs[i].slice;
    }
    upto = -1;
    current = null;
  }

  public int getNumSubs() {
    return numSubs;
  }

  public SirenEnumWithSlice[] getSubs() {
    return subs;
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
          current = subs[upto].docsNodesAndPositionsEnum;
          currentBase = subs[upto].slice.start;
        }
      }

      if (current.nextDocument()) {
        this.doc = currentBase + current.doc();
        return true;
      }
      else {
        current = null;
      }
    }
  }

  @Override
  public boolean nextNode() throws IOException {
    if (current != null) {
      return current.nextNode();
    }
    return false;
  }

  @Override
  public boolean nextPosition() throws IOException {
    if (current != null) {
      return current.nextPosition();
    }
    return false;
  }

  @Override
  public boolean skipTo(final int target) throws IOException {
    while(true) {
      if (current != null) {
        // it is possible that the target is inferior to the current base, i.e.,
        // when the target is located in a gap between the last document of the
        // previous sub-enum and the first document of the current sub-enum.
        final int baseTarget = target < currentBase ? 0 : target - currentBase;
        if (current.skipTo(baseTarget)) {
          this.doc = current.doc() + currentBase;
          return true;
        }
        else {
          current = null;
        }
      }
      else if (upto == numSubs - 1) {
        this.doc = NO_MORE_DOC;
        return false;
      }
      else {
        upto++;
        current = subs[upto].docsNodesAndPositionsEnum;
        currentBase = subs[upto].slice.start;
      }
    }
  }

  @Override
  public int doc() {
    return doc;
  }

  @Override
  public IntsRef node() {
    if (current != null) {
      return current.node();
    }
    return NO_MORE_NOD;
  }

  @Override
  public int pos() {
    if (current != null) {
      return current.pos();
    }
    return NO_MORE_POS;
  }

  @Override
  public int termFreqInNode()
  throws IOException {
    return current.termFreqInNode();
  }

  @Override
  public int nodeFreqInDoc() throws IOException {
    return current.nodeFreqInDoc();
  }

  // TODO: implement bulk read more efficiently than super
  final class SirenEnumWithSlice {
    public DocsNodesAndPositionsEnum docsNodesAndPositionsEnum;
    public ReaderSlice slice;
  }

}
