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
import org.sindice.siren.index.DocsNodesAndPositionsEnum;
import org.sindice.siren.util.NodeUtils;

/**
 * A NodeDisjunctionScorerQueue maintains a partial ordering of its
 * {@link NodeScorer}s such that the least scorer can always be found in
 * constant time. This allows to retrieve in order all matching documents and
 * nodes from multiple scorers, as if they were one stream.
 * <p>
 * <b>NOTE:</b> After initialisation, the NodeDisjunctionScorerQueue is
 * positioned on the first candidate document.
 * <p>
 * Based on a binary heap data structure. The root (top) of the heap contains
 * the least scorer.
 * <p>
 * {@link #put(NodeScorer)} requires log(size) time.
 * <p>
 * The ordering is based on {@link SirenScorer.doc()} and
 * {@link SirenScorer.node()}.
 * <p>
 * Code taken from {@link ScorerDocQueue} and adapted for the Siren use case.
 */
class NodeDisjunctionScorerQueue {

  private final HeapedScorerNode[] heap;

  private int size;

  private int nrMatchersInNode = -1;

  private float scoreInNode = 0;

  private class HeapedScorerNode {

    NodeScorer scorer;

    int doc;
    IntsRef node;

    HeapedScorerNode(final NodeScorer s) {
      this.scorer = s;
      this.adjust();
    }

    void adjust() {
      doc = scorer.doc();
      node = scorer.node();
    }

    @Override
    public String toString() {
      return "[" + doc + "," + "[" + node + "]]";
    }

  }

  private HeapedScorerNode topHSN; // same as heap[1], only for speed

  /** Create a NodeDisjunctionScorerQueue with a given capacity. */
  public NodeDisjunctionScorerQueue(final int capacity) {
    size = 0;
    final int heapSize = capacity + 1;
    heap = new HeapedScorerNode[heapSize];
    topHSN = heap[1]; // initially null
  }

  /**
   * Adds a SirenPrimitiveScorer to a NodeDisjunctionScorerQueue in log(size)
   * time. If one tries to add more Scorers than maxSize a RuntimeException
   * (ArrayIndexOutOfBound) is thrown.
   * <p>
   * The scorer is advanced to the next document to initiate heap ordering.
   */
  public final void put(final NodeScorer scorer) throws IOException {
    if (scorer.nextCandidateDocument()) { // if scorer exhausted, no need to add it
      size++;
      heap[size] = new HeapedScorerNode(scorer);
      this.upHeap();
    }
  }

  /**
   * For test purpose only
   * <p>
   * Returns the least Scorer of the NodeDisjunctionScorerQueue in constant time.
   * Should not be used when the queue is empty.
   */
  protected NodeScorer top() {
    return topHSN.scorer;
  }

  /**
   * Return the current document
   */
  public int doc() {
    return size == 0 ? DocsAndNodesIterator.NO_MORE_DOC : topHSN.doc;
  }

  /**
   * Return the current node
   */
  public IntsRef node() {
    return size == 0 ? DocsAndNodesIterator.NO_MORE_NOD : topHSN.node;
  }

  /**
   * Count the number of subscorers that provide the match in the current node,
   * and sum their score.
   * <p>
   * Counting the number of matchers within a node during node iteration is not
   * possible as it will require to cache the latest top node. This would have
   * required a copy of the array node since array node are reused in the
   * {@link DocsNodesAndPositionsEnum}.
   * <p>
   * Iterating over the elements of the queue enables us to save such a array
   * copy.
   *
   * @see #nrMatchersInNode()
   * @see #scoreInNode()
   */
  protected void countAndSumMatchers() throws IOException {
    if (nrMatchersInNode < 0) { // count and sum not done
      nrMatchersInNode = 1; // init counter at 1 to include the top
      scoreInNode = topHSN.scorer.scoreInNode();
      // perform recursive traversal of the heap
      this.computeSumRecursive(1);
    }
  }

  /**
   * Perform a traversal of the heap binary tree using recursion. Given a node,
   * visit its children and check if their subscorer is equivalent to the least
   * subscorer. If the subscorer is equivalent, it increments the number of
   * matchers, sum its score with the current score, and recursively visit its
   * two children.
   */
  private final void computeSumRecursive(final int root) throws IOException {
    final int i1 = (root << 1); // index of first child node
    final int i2 = (root << 1) + 1; // index of second child node

    if (i1 <= size) {
      final HeapedScorerNode child1 = heap[i1];
      if (topHSN.doc == child1.doc && topHSN.node.intsEquals(child1.node)) {
        nrMatchersInNode++;
        scoreInNode += child1.scorer.scoreInNode();
        this.computeSumRecursive(i1);
      }
    }

    if (i2 <= size) {
      final HeapedScorerNode child2 = heap[i2];
      if (topHSN.doc == child2.doc && topHSN.node.intsEquals(child2.node)) {
        nrMatchersInNode++;
        scoreInNode += child2.scorer.scoreInNode();
        this.computeSumRecursive(i2);
      }
    }
  }

  /**
   * Return the number of subscorers that provide the match in the current node.
   * <p>
   * <b>NOTE:</b> {@link #countAndSumMatchers()} must be called first before.
   */
  public int nrMatchersInNode() {
    return nrMatchersInNode;
  }

  /**
   * Return the sum of the score of the subscorers that provide the match in
   * the current node.
   * <p>
   * <b>NOTE:</b> {@link #countAndSumMatchers()} must be called first before.
   */
  public float scoreInNode() {
    return scoreInNode;
  }

  /**
   * Move all the scorers (including the top scorer) that have document
   * equals to the top document. If one of the subscorer is exhausted,
   * removes the scorer.
   *
   * @return If the least scorer is exhausted, return false.
   */
  public final boolean nextCandidateDocumentAndAdjustElsePop() throws IOException {
    if (topHSN != null) {
      final int currentDocument = topHSN.doc;

      while (size > 0 && topHSN.doc == currentDocument) {
        this.checkAdjustElsePop(topHSN.scorer.nextCandidateDocument());
      }

      // reset nrMatchersInNode
      nrMatchersInNode = -1;

      // no more doc when queue empty
      return (size > 0);
    }
    else {
      return false;
    }
  }

  /**
   * Move all the scorers that have document equals to the top document to the
   * next node and adjust the heap.
   *
   * @return If the least scorer has no more nodes, returns false.
   */
  public final boolean nextNodeAndAdjust() throws IOException {
    /*
     * TODO: stecam: I had a NPE in this method with topHSN. However,
     * I cannot reproduce it. Try to find the case it does occur.
     */
    // count number of scorers having the same document and node
    // counting the number of scorers and then performing the iterations of
    // all the scorers allows to avoid a node array copy (i.e., current node cache)
    if (size > 0 && nrMatchersInNode < 0) {
      this.countAndSumMatchers();
    }

    // Move the scorers to the next node
    for (int i = 0; i < nrMatchersInNode; i++) {
      topHSN.scorer.nextNode();
      this.adjustTop();
    }

    // reset nrMatchersInNode
    nrMatchersInNode = -1;
    // if top node has sentinel value, it means that there is no more nodes
    return this.node() != DocsAndNodesIterator.NO_MORE_NOD;
  }

  /**
   * Move all the scorers to the candidate document beyond (see NOTE below) the
   * current whose number is greater than or equal to <i>target</i>.
   * <p>
   * <b>NOTE:</b> when <code> target &le; current</code> implementations must
   * not advance beyond their current {@link #doc()}.
   *
   * @return If the least scorer has no more nodes, returns false.
   */
  public final boolean skipToCandidateAndAdjustElsePop(final int target)
  throws IOException {
    if (topHSN != null) {
      while (size > 0 && topHSN.doc < target) {
        this.checkAdjustElsePop(topHSN.scorer.skipToCandidate(target));
      }

      // no more doc when queue empty
      return (size > 0);
    }
    else {
      return false;
    }
  }

  /**
   * If condition is true, then pop the top and adjust the heap.
   */
  private boolean checkAdjustElsePop(final boolean cond) {
    if (cond) {
      topHSN.adjust();
    }
    else { // pop
      heap[1] = heap[size]; // move last to first
      heap[size] = null;
      size--;
    }
    this.downHeap();
    return cond;
  }

  /**
   * Should be called when the scorer at top changes of values.
   */
  public final void adjustTop() {
    topHSN.adjust();
    this.downHeap();
  }

  /**
   * Compares the given scorer with the specified heaped scorer for order.
   * Returns a negative integer, zero, or a positive integer as this scorer is
   * less than, equal to, or greater than the specified heaped scorer.
   */
  private int compareTo(final NodeScorer scorer, final NodeScorer other) {
    // compare docs
    final int doc = scorer.doc();
    final int otherDoc = other.doc();

    if (doc != otherDoc) {
      return doc - otherDoc;
    }

    // compare nodes
    return NodeUtils.compare(scorer.node(), other.node());
  }

  /**
   * Returns the number of scorers currently stored in the
   * NodeDisjunctionScorerQueue.
   **/
  public final int size() {
    return size;
  }

  /** Removes all entries from the NodeDisjunctionScorerQueue. */
  public final void clear() {
    for (int i = 0; i <= size; i++) {
      heap[i] = null;
    }
    size = 0;
  }

  private final void upHeap() {
    int i = size;
    final HeapedScorerNode node = heap[i]; // save bottom node
    int j = i >>> 1;
    while ((j > 0) && (this.compareTo(node.scorer, heap[j].scorer) < 0)) {
      heap[i] = heap[j]; // shift parents down
      i = j;
      j = j >>> 1;
    }
    heap[i] = node; // install saved node
    topHSN = heap[1];
  }

  private final void downHeap() {
    int i = 1;
    final HeapedScorerNode node = heap[i]; // save top node
    int j = i << 1; // find smaller child
    int k = j + 1;
    if ((k <= size) && (this.compareTo(heap[k].scorer, heap[j].scorer) < 0)) {
      j = k;
    }
    while ((j <= size) && (this.compareTo(heap[j].scorer, node.scorer) < 0)) {
      heap[i] = heap[j]; // shift up child
      i = j;
      j = i << 1;
      k = j + 1;
      if (k <= size && (this.compareTo(heap[k].scorer, heap[j].scorer) < 0)) {
        j = k;
      }
    }
    heap[i] = node; // install saved node
    topHSN = heap[1];
  }

}
