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
import java.util.Collection;
import java.util.List;

import org.apache.lucene.search.Weight;
import org.apache.lucene.util.IntsRef;

/**
 * A {@link NodeScorer} for OR like queries within a node, counterpart of
 * {@link NodeConjunctionScorer}.
 *
 * <p>
 *
 * Code taken from {@link DisjunctionSumScorer} and adapted for the Siren
 * use case.
 */
class NodeDisjunctionScorer extends NodeScorer {

  /** The number of subscorers. */
  private final int                      nrScorers;

  /** The scorers. */
  protected final Collection<NodeScorer> scorers;

  /**
   * The scorerNodeQueue contains all subscorers ordered by their current
   * docID(), with the minimum at the top. <br>
   * The scorerNodeQueue is initialized the first time nextDoc() or advance() is
   * called. <br>
   * An exhausted scorer is immediately removed from the scorerDocQueue. <br>
   * If less than the minimumNrMatchers scorers remain in the scorerDocQueue
   * nextDoc() and advance() return false.
   * <p>
   * After each to call to nextDoc() or advance() <code>currentScore</code> is
   * the total score of the current matching doc, <code>nrMatchers</code> is the
   * number of matching scorers, and all scorers are after the matching doc, or
   * are exhausted.
   */
  private NodeDisjunctionScorerQueue     nodeScorerQueue = null;

  /** The document number of the current match. */
  private int                            currentDoc      = -1;

  private IntsRef                        currentNode     = new IntsRef(new int[] { -1 }, 0, 1);

  /**
   * Construct a {@link NodeDisjunctionScorer}.
   *
   * @param subScorers
   *          A collection of at least two primitives scorers.
   * @throws IOException
   */
  public NodeDisjunctionScorer(final Weight weight,
                                final List<NodeScorer> scorers)
  throws IOException {
    super(weight);
    nrScorers = scorers.size();
    if (nrScorers <= 1) {
      throw new IllegalArgumentException("There must be at least 2 subScorers");
    }
    this.scorers = scorers;
    nodeScorerQueue  = this.initNodeScorerQueue();
  }

  /**
   * Initialize the {@link NodeDisjunctionScorerQueue}.
   */
  private NodeDisjunctionScorerQueue initNodeScorerQueue() throws IOException {
    final NodeDisjunctionScorerQueue nodeQueue = new NodeDisjunctionScorerQueue(nrScorers);
    for (final NodeScorer s : scorers) {
      nodeQueue.put(s);
    }
    return nodeQueue;
  }

  @Override
  public float freqInNode() throws IOException {
    if (currentDoc == -1) { // if nextCandidateDocument not called for the first time
      return 0;
    }
    // return the number of matchers in the node
    return this.nrMatchers();
  }

  @Override
  public float scoreInNode() throws IOException {
    if (currentDoc == -1) { // if nextCandidateDocument not called for the first time
      return 0;
    }
    nodeScorerQueue.countAndSumMatchers();
    return nodeScorerQueue.scoreInNode();
  }

  @Override
  public boolean nextCandidateDocument() throws IOException {
    boolean more = true;

    // The first time nextCandidateDocument is called, we must not advance the
    // underlying scorers as they have been already advanced during the queue init
    if (currentDoc != -1) { // if not called for the first time
      more = nodeScorerQueue.nextCandidateDocumentAndAdjustElsePop();
    }

    currentDoc = nodeScorerQueue.doc();
    currentNode = nodeScorerQueue.node();
    return more;
  }

  @Override
  public boolean nextNode() throws IOException {
    final boolean more = nodeScorerQueue.nextNodeAndAdjust();
    currentNode = nodeScorerQueue.node();
    return more;
  }

  /**
   * Returns the number of subscorers matching the current node. Initially
   * invalid, until {@link #nextCandidateDocument()} is called the first time.
   * @throws IOException
   */
  public int nrMatchers() throws IOException {
    // update the number of matched scorers
    nodeScorerQueue.countAndSumMatchers();
    return nodeScorerQueue.nrMatchersInNode();
  }

  @Override
  public boolean skipToCandidate(final int target) throws IOException {
    final boolean more = nodeScorerQueue.skipToCandidateAndAdjustElsePop(target);
    currentDoc = nodeScorerQueue.doc();
    currentNode = nodeScorerQueue.node();
    return more;
  }

  @Override
  public int doc() {
    return currentDoc;
  }

  @Override
  public IntsRef node() {
    return currentNode;
  }

  @Override
  public String toString() {
    return "NodeDisjunctionScorer(" + weight + "," + this.doc() + "," +
      this.node() + ")";
  }

}
