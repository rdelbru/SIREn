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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.search.Scorer;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.search.node.NodeBooleanClause.Occur;
import org.sindice.siren.search.node.NodeBooleanQuery.AbstractNodeBooleanWeight;

/**
 * A {@link NodeScorer} that matches a boolean combination of node scorers.
 *
 * <p>
 *
 * Uses {@link NodeConjunctionScorer}, {@link NodeDisjunctionScorer},
 * {@link NodeReqExclScorer} and {@link NodeReqOptScorer}.
 *
 * <p>
 *
 * Code taken from {@link BooleanScorer2} and adapted for the Siren use case.
 */
class NodeBooleanScorer extends NodeScorer {

  protected final List<NodeScorer> requiredScorers;
  protected final List<NodeScorer> optionalScorers;
  protected final List<NodeScorer> prohibitedScorers;

  private final Coordinator coordinator;

  /**
   * The scorer to which all scoring will be delegated, except for computing and
   * using the coordination factor.
   */
  protected NodeScorer countingSumScorer = null;

  /**
   * Creates a {@link NodeBooleanScorer} with the given lists of
   * required, prohibited and optional scorers. In no required scorers are added,
   * at least one of the optional scorers will have to match during the search.
   *
   * @param weight
   *          The BooleanWeight to be used.
   * @param required
   *          the list of required scorers.
   * @param prohibited
   *          the list of prohibited scorers.
   * @param optional
   *          the list of optional scorers.
   */
  public NodeBooleanScorer(final AbstractNodeBooleanWeight weight,
                           final List<NodeScorer> required,
                           final List<NodeScorer> prohibited,
                           final List<NodeScorer> optional) throws IOException {
    super(weight);
    coordinator = new Coordinator();

    optionalScorers = optional;
    requiredScorers = required;
    prohibitedScorers = prohibited;

    coordinator.init();
    countingSumScorer = this.makeCountingSumScorer();
  }

  private NodeScorer countingDisjunctionSumScorer(final List<NodeScorer> scorers)
  throws IOException {
    return new NodeDisjunctionScorer(this.getWeight(), scorers) {

      @Override
      public float scoreInNode()
      throws IOException {
        final float nodeScore = super.scoreInNode();
        coordinator.nrMatchers += super.nrMatchers();
        return nodeScore;
      }

    };
  }

  private NodeScorer countingConjunctionSumScorer(final List<NodeScorer> requiredScorers)
  throws IOException {
    // each scorer from the list counted as a single matcher
    final int requiredNrMatchers = requiredScorers.size();

    return new NodeConjunctionScorer(weight, 1.0f, requiredScorers) {

      @Override
      public float scoreInNode() throws IOException {
        final float nodeScore = super.scoreInNode();
        coordinator.nrMatchers += requiredNrMatchers;
        // All scorers match, so defaultSimilarity super.score() always has 1 as
        // the coordination factor.
        // Therefore the sum of the scores of the requiredScorers
        // is used as score.
        return nodeScore;
      }
    };
  }

  /**
   * Returns the scorer to be used for match counting and score summing. Uses
   * requiredScorers, optionalScorers and prohibitedScorers.
   */
  private NodeScorer makeCountingSumScorer()
  throws IOException { // each scorer counted as a single matcher
    return (requiredScorers.size() == 0) ? this.makeCountingSumScorerNoReq()
                                         : this.makeCountingSumScorerSomeReq();
  }

  private NodeScorer makeCountingSumScorerNoReq()
  throws IOException { // No required scorers
    NodeScorer requiredCountingSumScorer;
    if (optionalScorers.size() > 1)
      requiredCountingSumScorer = this.countingDisjunctionSumScorer(optionalScorers);
    else if (optionalScorers.size() == 1)
      requiredCountingSumScorer = new SingleMatchScorer(optionalScorers.get(0));
    else {
      requiredCountingSumScorer = this.countingConjunctionSumScorer(optionalScorers);
    }
    return this.addProhibitedScorers(requiredCountingSumScorer);
  }

  private NodeScorer makeCountingSumScorerSomeReq()
  throws IOException { // At least one required scorer.
    final NodeScorer requiredCountingSumScorer =
      (requiredScorers.size() == 1) ? new SingleMatchScorer(requiredScorers.get(0))
                                    : this.countingConjunctionSumScorer(requiredScorers);

    if (optionalScorers.isEmpty()) {
      return this.addProhibitedScorers(requiredCountingSumScorer);
    }
    else {
      return new NodeReqOptScorer(
        this.addProhibitedScorers(requiredCountingSumScorer),
        optionalScorers.size() == 1
          ? new SingleMatchScorer(optionalScorers.get(0))
          // require 1 in combined, optional scorer.
          : this.countingDisjunctionSumScorer(optionalScorers));
    }
  }

  /**
   * Returns the scorer to be used for match counting and score summing. Uses
   * the given required scorer and the prohibitedScorers.
   *
   * @param requiredCountingSumScorer
   *          A required scorer already built.
   */
  private NodeScorer addProhibitedScorers(final NodeScorer requiredCountingSumScorer)
  throws IOException {
    return (prohibitedScorers.size() == 0)
      ? requiredCountingSumScorer // no prohibited
      : new NodeReqExclScorer(requiredCountingSumScorer,
                               ((prohibitedScorers.size() == 1)
                               ? prohibitedScorers.get(0)
                               : new NodeDisjunctionScorer(weight, prohibitedScorers)));
  }

  @Override
  public int doc() {
    return countingSumScorer.doc();
  }

  @Override
  public float freqInNode() throws IOException {
    return coordinator.nrMatchers;
  }

  @Override
  public IntsRef node() {
    return countingSumScorer.node();
  }

  @Override
  public boolean nextCandidateDocument() throws IOException {
    return countingSumScorer.nextCandidateDocument();
  }

  @Override
  public boolean nextNode() throws IOException {
    return countingSumScorer.nextNode();
  }

  @Override
  public float scoreInNode()
  throws IOException {
    coordinator.nrMatchers = 0;
    final float sum = countingSumScorer.scoreInNode();
    /*
     * TODO: the score is weighted by the number of matched scorer.
     * Is this the right place to do it ? Shouldn't it be done inside
     * the similarity implementation ?
     */
    return sum * coordinator.coordFactors[coordinator.nrMatchers];
  }

  @Override
  public boolean skipToCandidate(final int target) throws IOException {
    return countingSumScorer.skipToCandidate(target);
  }

  @Override
  public Collection<ChildScorer> getChildren() {
    final ArrayList<ChildScorer> children = new ArrayList<ChildScorer>();
    for (final Scorer s : optionalScorers) {
      children.add(new ChildScorer(s, Occur.SHOULD.toString()));
    }
    for (final Scorer s : prohibitedScorers) {
      children.add(new ChildScorer(s, Occur.MUST_NOT.toString()));
    }
    for (final Scorer s : requiredScorers) {
      children.add(new ChildScorer(s, Occur.MUST.toString()));
    }
    return children;
  }

  @Override
  public String toString() {
    return "NodeBooleanScorer(" + this.weight + "," + this.doc() + "," +
      this.node() + ")";
  }

  private class Coordinator {

    float[] coordFactors = null;
    int nrMatchers; // to be increased by score() of match counting scorers.

    void init() { // use after all scorers have been added.
      coordFactors = new float[optionalScorers.size() + requiredScorers.size() + 1];
      for (int i = 0; i < coordFactors.length; i++) {
        coordFactors[i] = 1.0f;
      }
    }
  }

  /** Count a scorer as a single match. */
  private class SingleMatchScorer extends NodeScorer {

    private final NodeScorer scorer;

    SingleMatchScorer(final NodeScorer scorer) {
      super(scorer.getWeight());
      this.scorer = scorer;
    }

    @Override
    public float freqInNode() throws IOException {
      return scorer.freqInNode();
    }

    /*
     * TODO: Is it useful to cache the score ?
     * It would mean (1) to cache an ints ref and (2) an array comparison for
     * each call to scoreInNode().
     */
    @Override
    public float scoreInNode() throws IOException {
      final float nodeScore = scorer.scoreInNode();
      coordinator.nrMatchers++;
      return nodeScore;
    }

    @Override
    public int doc() {
      return scorer.doc();
    }

    @Override
    public IntsRef node() {
      return scorer.node();
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
    public String toString() {
      return "SingleMatchScorer(" + weight + "," + this.doc() + "," +
        this.node() + ")";
    }

  }

}
