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
import java.util.Comparator;

import org.apache.lucene.search.Weight;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.index.DocsAndNodesIterator;
import org.sindice.siren.util.NodeUtils;

/**
 * A {@link NodeScorer} for conjunctions of a set of {@link NodeScorer}s within
 * a node. All the {@link NodeScorer}s are required.
 *
 * <p>
 *
 * A node is considered matching if all the queries match in the same node.
 * The {@link #nextCandidateDocument()} method iterates over candidate documents
 * that match all the queries. The {@link #nextNode()} method iterates
 * over the matching nodes within a document.
 *
 * <p>
 *
 * Code taken from {@link ConjunctionScorer} and adapted for the Siren use
 * case.
 **/
class NodeConjunctionScorer extends NodeScorer {

  private final NodeScorer[] scorers;

  private final float        coord;

  protected IntsRef          lastNode     = new IntsRef(new int[] { -1 }, 0, 1);

  protected int              lastDocument = -1;

  public NodeConjunctionScorer(final Weight weight, final float coord,
                               final Collection<NodeScorer> scorers)
  throws IOException {
    this(weight, coord, scorers.toArray(new NodeScorer[scorers.size()]));
  }

  public NodeConjunctionScorer(final Weight weight, final float coord,
                               final NodeScorer ... scorers)
  throws IOException {
    super(weight);
    this.scorers = scorers;
    this.coord = coord;
    this.init();
  }

  private void init() throws IOException {
    for (final NodeScorer scorer : scorers) {
      if (!scorer.nextCandidateDocument()) {
        // If even one of the sub-scorers does not have any documents, this
        // scorer should not attempt to do any more work.
        lastDocument = DocsAndNodesIterator.NO_MORE_DOC;
        lastNode = DocsAndNodesIterator.NO_MORE_NOD;
        return;
      }
    }

    // Sort the array the first time...
    // We don't need to sort the array in any future calls because we know
    // it will already start off sorted (all scorers on same candidate doc).

    // Note that this comparator is not consistent with equals!
    // Also we use mergeSort here to be stable (so order of Scorers that
    // match on first document keeps preserved):
    ArrayUtil.mergeSort(scorers, new Comparator<NodeScorer>() { // sort the array
      public int compare(final NodeScorer o1, final NodeScorer o2) {
        return o1.doc() - o2.doc();
      }
    });

    // NOTE: doNext() must be called before the re-sorting of the array later on.
    // The reason is this: assume there are 5 scorers, whose first docs are 1,
    // 2, 3, 5, 5 respectively. Sorting (above) leaves the array as is. Calling
    // doNext() here advances all the first scorers to 5 (or a larger doc ID
    // they all agree on).
    // However, if we re-sort before doNext() is called, the order will be 5, 3,
    // 2, 1, 5 and then doNext() will stop immediately, since the first scorer's
    // docs equals the last one. So the invariant that after calling doNext()
    // all scorers are on the same doc ID is broken.
    if (!this.doNext()) {
      // The scorers did not agree on any document.
      lastDocument = DocsAndNodesIterator.NO_MORE_DOC;
      lastNode = DocsAndNodesIterator.NO_MORE_NOD;
      return;
    }

    // If first-time skip distance is any predictor of
    // scorer sparseness, then we should always try to skip first on
    // those scorers.
    // Keep last scorer in it's last place (it will be the first
    // to be skipped on), but reverse all of the others so that
    // they will be skipped on in order of original high skip.
    final int end = (scorers.length - 1);
    for (int i = 0; i < (end >> 1); i++) {
      final NodeScorer tmp = scorers[i];
      scorers[i] = scorers[end - i - 1];
      scorers[end - i - 1] = tmp;
    }
  }

  /**
   * Perform a next without initial increment
   */
  private boolean doNext() throws IOException {
    int first = 0;
    int doc = scorers[scorers.length - 1].doc();
    NodeScorer firstScorer = scorers[first];
    boolean more = true;

    while (firstScorer.doc() < doc) {
      more = firstScorer.skipToCandidate(doc);
      doc = firstScorer.doc();
      first = first == scorers.length - 1 ? 0 : first + 1;
      firstScorer = scorers[first];
    }

    return more;
  }

  @Override
  public boolean skipToCandidate(final int target) throws IOException {
    if (lastDocument == DocsAndNodesIterator.NO_MORE_DOC) {
      return false;
    }
    else if (scorers[(scorers.length - 1)].doc() < target) {
      scorers[(scorers.length - 1)].skipToCandidate(target);
    }
    final boolean more = this.doNext();
    lastDocument = scorers[scorers.length - 1].doc();
    lastNode = scorers[scorers.length - 1].node();
    return more;
  }

  @Override
  public int doc() {
    return lastDocument;
  }

  @Override
  public IntsRef node() {
    return lastNode;
  }

  @Override
  public boolean nextCandidateDocument() throws IOException {
    if (lastDocument == DocsAndNodesIterator.NO_MORE_DOC) {
      return false;
    }
    else if (lastDocument == -1) { // first time called
      lastDocument = scorers[scorers.length - 1].doc();
      lastNode = scorers[scorers.length - 1].node();
      return true;
    }
    // advance the last scorer to the next candidate document
    scorers[(scorers.length - 1)].nextCandidateDocument();
    final boolean more = this.doNext();
    lastDocument = scorers[scorers.length - 1].doc();
    lastNode = scorers[scorers.length - 1].node();
    return more;
  }

  @Override
  public boolean nextNode() throws IOException {
    int first = 0;
    NodeScorer lastScorer = scorers[scorers.length - 1];
    NodeScorer firstScorer = scorers[first];

    // scan forward in last
    if (lastNode == DocsAndNodesIterator.NO_MORE_NOD || !lastScorer.nextNode()) {
      lastNode = DocsAndNodesIterator.NO_MORE_NOD;
      return false;
    }

    while (NodeUtils.compare(firstScorer.node(), lastScorer.node()) < 0) {
      do {
        if (!firstScorer.nextNode()) {  // scan forward in first
          lastNode = DocsAndNodesIterator.NO_MORE_NOD;
          return false;
        }
      } while (NodeUtils.compare(firstScorer.node(), lastScorer.node()) < 0);
      lastScorer = firstScorer;
      first = (first == (scorers.length - 1)) ? 0 : first + 1;
      firstScorer = scorers[first];
    }
    // all equal: a match
    lastNode = firstScorer.node();
    return true;
  }

  @Override
  public float freqInNode() throws IOException {
    // return the number of required matchers in the node
    return this.scorers.length;
  }

  @Override
  public float scoreInNode() throws IOException {
    float curNodeScore = 0;
    for (final NodeScorer scorer : scorers) {
      curNodeScore += scorer.scoreInNode();
    }
    // TODO: why is there a coord here ?
    return curNodeScore * coord;
  }

  @Override
  public String toString() {
    return "NodeConjunctionScorer(" + weight + "," + lastDocument + "," + lastNode + ")";
  }

}
