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
package org.sindice.siren.search.node;

import java.io.IOException;

import org.apache.lucene.util.IntsRef;
import org.sindice.siren.index.DocsAndNodesIterator;

/**
 * A {@link NodeScorer} that filters node path and output the ancestor node path.
 *
 * <p>
 *
 * The level of the ancestor must be specified. It is used to modify the
 * {@link IntsRef#length} of the node label returned by the inner scorer.
 */
class AncestorFilterScorer extends NodeScorer {

  private final NodeScorer scorer;
  private final int ancestorLevel;

  public AncestorFilterScorer(final NodeScorer scorer, final int ancestorLevel) {
    super(scorer.getWeight());
    this.scorer = scorer;
    this.ancestorLevel = ancestorLevel;
  }

  protected NodeScorer getScorer() {
    return scorer;
  }

  @Override
  public float freqInNode() throws IOException {
    return scorer.freqInNode();
  }

  @Override
  public float scoreInNode()
  throws IOException {
    return scorer.scoreInNode();
  }

  @Override
  public String toString() {
    return "AncestorFilterScorer(" + weight + "," + this.doc() + "," +
      this.node() + ")";
  }

  @Override
  public boolean nextCandidateDocument() throws IOException {
    return scorer.nextCandidateDocument();
  }

  @Override
  public boolean nextNode() throws IOException {
    return scorer.nextNode();
  }

  @Override
  public boolean skipToCandidate(final int target) throws IOException {
    return scorer.skipToCandidate(target);
  }

  @Override
  public int doc() {
    return scorer.doc();
  }

  @Override
  public IntsRef node() {
    final IntsRef node = scorer.node();
    // resize node array only if node is not a sentinel value
    if (node.length > ancestorLevel &&
        node.ints[node.offset] != -1 &&
        node != DocsAndNodesIterator.NO_MORE_NOD) {
      node.length = ancestorLevel;
    }
    return node;
  }

}
