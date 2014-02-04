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
 * A {@link NodeScorer} for queries within a node with a required subscorer
 * and an excluding (prohibited) subscorer.
 *
 * <p>
 *
 * Only nodes matching the required subscorer and not matching the prohibited
 * subscorer are kept.
 *
 * <p>
 *
 * Code taken from {@link ReqExclScorer} and adapted for the Siren use
 * case.
 */
class NodeReqExclScorer extends NodeScorer {

  /**
   * The required and excluded primitive Siren scorers.
   */
  private final NodeScorer reqScorer;
  private NodeScorer exclScorer;

  /**
   * Construct a {@link NodeReqExclScorer}.
   *
   * @param reqScorer
   *          The scorer that must match, except where
   * @param exclScorer
   *          indicates exclusion.
   */
  public NodeReqExclScorer(final NodeScorer reqScorer,
                           final NodeScorer exclScorer) {
    super(reqScorer.getWeight());
    this.reqScorer = reqScorer;
    this.exclScorer = exclScorer;
  }

  @Override
  public boolean nextCandidateDocument() throws IOException {
    if (!reqScorer.nextCandidateDocument()) {
      return false;
    }

    if (exclScorer == null) {
      return true; // reqScorer.nextCandidateDocument() already returned true
    }

    return this.toNonExcludedCandidateDocument();
  }

  /**
   * Advance to non excluded candidate document. <br>
   * On entry:
   * <ul>
   * <li>reqScorer != null,
   * <li>exclScorer != null,
   * <li>reqScorer was advanced once via {@link #nextCandidateDocument()} or
   * {@link #skipToCandidate(int)} and reqScorer.doc() may still be excluded.
   * </ul>
   * Advances reqScorer a non excluded candidate document, if any.
   * <p>
   * If reqScorer.doc() is equal to exclScorer.doc(), reqScorer.doc() cannot
   * be excluded immediately, i.e., it is a valid candidate document. We have to
   * check the exclusion of reqScorer.node().
   *
   * @return true iff there is a non excluded candidate document.
   */
  private boolean toNonExcludedCandidateDocument() throws IOException {
    do {
      int exclDoc = exclScorer.doc();
      final int reqDoc = reqScorer.doc(); // may be excluded

      if (reqDoc <= exclDoc) {
        return true; // reqScorer advanced to or before exclScorer, not excluded
      }
      else if (reqDoc > exclDoc) {
        if (!exclScorer.skipToCandidate(reqDoc)) {
          exclScorer = null; // exhausted, no more exclusions
          return true;
        }
        exclDoc = exclScorer.doc();
        if (exclDoc >= reqDoc) {
          return true; // exclScorer advanced to or before reqScorer, not excluded
        }
      }
    } while (reqScorer.nextCandidateDocument());

    return false;
  }

  @Override
  public boolean nextNode() throws IOException {
    if (!reqScorer.nextNode()) { // Move to the next matching node
      return false; // exhausted, nothing left
    }

    if (exclScorer == null || exclScorer.doc() != reqScorer.doc()) {
      return true; // reqScorer.nextNode() already returned true
    }

    // reqScorer and exclScorer are positioned on the same candidate document
    return this.toNonExcludedNode();
  }

  /**
   * Advance to an excluded cell. <br>
   * On entry:
   * <ul>
   * <li>reqScorer != null,
   * <li>exclScorer != null,
   * <li>reqScorer and exclScorer were advanced once via
   * {@link #nextCandidateDocument()} or {@link #skipToCandidate(int)} and were
   * positioned on the same candidate document number
   * <li> reqScorer.doc() and reqScorer.node() may still be excluded.
   * </ul>
   * Advances reqScorer to the next non excluded required node, if any.
   *
   * @return true iff the current candidate document has a non excluded required
   * node.
   */
  private boolean toNonExcludedNode() throws IOException {
    IntsRef reqNode = reqScorer.node(); // may be excluded
    IntsRef exclNode = exclScorer.node();

    int comparison;
    while ((comparison = NodeUtils.compare(reqNode, exclNode)) >= 0) {
      // if node equal, advance to next node in reqScorer
      if (comparison == 0 && !reqScorer.nextNode()) {
        return false;
      }

      // if node equal or excluded node ancestor, advance to next node
      if (!exclScorer.nextNode()) {
        return true;
      }

      reqNode = reqScorer.node();
      exclNode = exclScorer.node();
    }
    return true;
  }

  @Override
  public int doc() {
    return reqScorer.doc(); // reqScorer may be null when next() or skipTo()
                            // already return false
  }

  @Override
  public IntsRef node() {
    return reqScorer.node();
  }

  @Override
  public float freqInNode() throws IOException {
    return reqScorer.freqInNode();
  }

  @Override
  public float scoreInNode()
  throws IOException {
    return reqScorer.scoreInNode(); // reqScorer may be null when next() or skipTo()
                                    // already return false
  }

  @Override
  public boolean skipToCandidate(final int target) throws IOException {
    if (exclScorer == null) {
      return reqScorer.skipToCandidate(target);
    }

    if (!reqScorer.skipToCandidate(target)) {
      return false;
    }

    return this.toNonExcludedCandidateDocument();
  }

  @Override
  public String toString() {
    return "NodeReqExclScorer(" + weight + "," + this.doc() + "," +
      this.node() + ")";
  }

}
