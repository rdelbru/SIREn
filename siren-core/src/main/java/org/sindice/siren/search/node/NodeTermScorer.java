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

import org.apache.lucene.search.Weight;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.index.DocsAndNodesIterator;
import org.sindice.siren.index.DocsNodesAndPositionsEnum;

/**
 * A {@link NodeScorer} for nodes matching a <code>Term</code>.
 */
class NodeTermScorer extends NodePositionScorer {

  private final DocsNodesAndPositionsEnum docsEnum;

  private final Similarity.ExactSimScorer docScorer;

  /**
   * Construct a <code>NodeTermScorer</code>.
   *
   * @param weight
   *          The weight of the <code>Term</code> in the query.
   * @param docsEnum
   *          An iterator over the documents and the positions matching the
   *          <code>Term</code>.
   * @param similarity
   *          The </code>Similarity</code> implementation to be used for score
   *          computations.
   * @param norms
   *          The field norms of the document fields for the <code>Term</code>.
   * @throws IOException
   */
  protected NodeTermScorer(final Weight weight,
                           final DocsNodesAndPositionsEnum docsEnum,
                           final Similarity.ExactSimScorer docScorer)
  throws IOException {
    super(weight);
    this.docScorer = docScorer;
    this.docsEnum = docsEnum;
  }

  @Override
  public int doc() {
    return docsEnum.doc();
  }

  @Override
  public float freqInNode()
  throws IOException {
    return docsEnum.termFreqInNode();
  }

  @Override
  public int pos() {
    return docsEnum.pos();
  }

  @Override
  public IntsRef node() {
    return docsEnum.node();
  }

  @Override
  public boolean nextCandidateDocument() throws IOException {
    return docsEnum.nextDocument();
  }

  @Override
  public boolean nextNode() throws IOException {
    return docsEnum.nextNode();
  }

  @Override
  public boolean nextPosition() throws IOException {
    return docsEnum.nextPosition();
  }

  @Override
  public float scoreInNode()
  throws IOException {
    assert this.doc() != DocsAndNodesIterator.NO_MORE_DOC;
    return docScorer.score(docsEnum.doc(), docsEnum.termFreqInNode());
  }

  @Override
  public boolean skipToCandidate(final int target) throws IOException {
    return docsEnum.skipTo(target);
  }

  @Override
  public String toString() {
    return "NodeTermScorer(" + weight + "," + this.doc() + "," + this.node() + ")";
  }

}
