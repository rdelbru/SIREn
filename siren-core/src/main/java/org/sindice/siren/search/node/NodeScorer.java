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

import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.IntsRef;

/**
 * The abstract {@link Scorer} class that defines the interface for iterating
 * over an ordered list of nodes matching a query.
 * <p>
 * Subclasses should implement {@link #docID()}, {@link #nextDoc()} and
 * {@link #advance(int)} for compatibility with {@link Scorer} if needed.
 */
public abstract class NodeScorer extends Scorer {

  protected NodeScorer(final Weight weight) {
    super(weight);
  }

  /**
   * Advances to the next candidate document in the set, or returns false if
   * there are no more docs in the set.
   * <p>
   * A candidate document is a document that represents a potential match for
   * the query. All the sub-scorers agree on its document id. However finding
   * the matching node within this document still needs to be done. If the
   * sub-scorers do not agree on a node by calling {@link #nextNode()}, the
   * document must be considered as a non-matching document.
   * <p>
   * This method is useful for optimisation, i.e., lazy loading and comparison
   * of node information. It allows to iterate over documents and find candidate
   * without loading node information. Node information are loaded and compared
   * only when a candidate is found.
   */
  public abstract boolean nextCandidateDocument() throws IOException;

  /**
   * Move to the next node path in the current document matching the query.
   * <p>
   * Should not be called until {@link #nextCandidateDocument()} or
   * {@link #skipToCandidate(int)} are called for the first time.
   *
   * @return false if there is no more node for the current entity or if
   * {@link #nextCandidateDocument()} or {@link #skipToCandidate(int)} were not
   * called yet.
   */
  public abstract boolean nextNode() throws IOException;

  /**
   * Skip to the first candidate document beyond (see NOTE below) the current
   * whose number is greater than or equal to <i>target</i>. Returns false if
   * there are no more docs in the set.
   * <p>
   * <b>NOTE:</b> when <code> target &le; current</code> implementations must
   * not advance beyond their current {@link #doc()}.
   */
  public abstract boolean skipToCandidate(int target) throws IOException;

  /**
   * Returns the following:
   * <ul>
   * <li>-1 or {@link #NO_MORE_DOC} if {@link #nextCandidateDocument()} or
   * {@link #skipToCandidate(int)} were not called yet.
   * <li>{@link #NO_MORE_DOC} if the iterator has exhausted.
   * <li>Otherwise it should return the doc ID it is currently on.
   * </ul>
   * <p>
   */
  public abstract int doc();

  /**
   * Returns the following:
   * <ul>
   * <li>-1 or {@link #NO_MORE_NOD} if {@link #nextNode()} were not called yet.
   * <li>{@link #NO_MORE_NOD} if the iterator has exhausted.
   * <li>Otherwise it should return the node it is currently on.
   * </ul>
   */
  public abstract IntsRef node();

  /**
   * Returns the number of occurrences in the current node
   */
  public abstract float freqInNode() throws IOException;

  /**
   * Returns the score of the current node of the current
   * document matching the query.
   */
  public abstract float scoreInNode() throws IOException;

  /**
   * Methods implemented in {@link LuceneProxyNodeScorer}
   */

  @Override
  public float score() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void score(final Collector collector) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean score(final Collector collector, final int max, final int firstDocID)
  throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public float freq() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public int docID() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int nextDoc() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public int advance(final int target) throws IOException {
    throw new UnsupportedOperationException();
  }

}
