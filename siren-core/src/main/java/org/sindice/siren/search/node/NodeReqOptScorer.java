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
import org.sindice.siren.util.NodeUtils;

/**
 * A {@link NodeScorer} for queries with a required part and an optional part.
 * Delays advance() on the optional part until a score() is needed.
 *
 * <p>
 *
 * Code taken from {@link ReqOptSumScorer} and adapted for the Siren use
 * case.
 */
class NodeReqOptScorer extends NodeScorer {

  /**
   * The required scorer passed from the constructor.
   */
  private final NodeScorer reqScorer;

  /**
   * The optional scorer passed from the constructor, and used for boosting
   * score.
   */
  private NodeScorer optScorer;

  /**
   * Construct a {@link NodeReqOptScorer}.
   *
   * @param reqScorer
   *          The required scorer. This must match.
   * @param optScorer
   *          The optional scorer. This is used for scoring only.
   */
  public NodeReqOptScorer(final NodeScorer reqScorer,
                          final NodeScorer optScorer) {
    super(reqScorer.getWeight());
    this.reqScorer = reqScorer;
    this.optScorer = optScorer;
  }

  @Override
  public boolean nextCandidateDocument() throws IOException {
    return reqScorer.nextCandidateDocument();
  }

  @Override
  public boolean nextNode() throws IOException {
    return reqScorer.nextNode();
  }

  @Override
  public boolean skipToCandidate(final int target) throws IOException {
    return reqScorer.skipToCandidate(target);
  }

  @Override
  public int doc() {
    return reqScorer.doc();
  }

  @Override
  public IntsRef node() {
    return reqScorer.node();
  }

  @Override
  public float freqInNode() throws IOException {
    // TODO: Computation similar to #scoreInNode. Could the instructions be
    // abstracted and merged somehow
    final float reqFreq = reqScorer.freqInNode();
    final int doc = this.doc();

    if (optScorer == null) {
      return reqFreq;
    } else if (optScorer.doc() < doc && // if it is the first call, optScorer.doc() returns -1
               !optScorer.skipToCandidate(doc)) {
      optScorer = null;
      return reqFreq;
    }

    final IntsRef reqNode = this.node();
    /*
     * the optional scorer can be in a node that is before the one where
     * the required scorer is in.
     */
    int cmp = 1;
    while ((cmp = NodeUtils.compare(optScorer.node(), reqNode)) < 0) {
      if (!optScorer.nextNode()) {
        return reqFreq;
      }
    }
    // If the optional scorer matches the same node, increase the freq
    return (optScorer.doc() == doc && cmp == 0)
           ? reqFreq + optScorer.freqInNode()
           : reqFreq;
  }

  @Override
  public float scoreInNode() throws IOException {
    final float reqScore = reqScorer.scoreInNode();
    final int doc = this.doc();

    if (optScorer == null) {
      return reqScore;
    } else if (optScorer.doc() < doc && // if it is the first call, optScorer.doc() returns -1
               !optScorer.skipToCandidate(doc)) {
      optScorer = null;
      return reqScore;
    }

    final IntsRef reqNode = this.node();
    /*
     * the optional scorer can be in a node that is before the one where
     * the required scorer is in.
     */
    int cmp = 1;
    while ((cmp = NodeUtils.compare(optScorer.node(), reqNode)) < 0) {
      if (!optScorer.nextNode()) {
        return reqScore;
      }
    }
    // If the optional scorer matches the same node, increase the score
    return (optScorer.doc() == doc && cmp == 0)
           ? reqScore + optScorer.scoreInNode()
           : reqScore;
  }

  @Override
  public String toString() {
    return "NodeReqOptScorer(" + weight + "," +
      this.doc() + "," + this.node() + ")";
  }

}
