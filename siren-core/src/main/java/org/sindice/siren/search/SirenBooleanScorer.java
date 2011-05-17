/**
 * Copyright 2009, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 6 May 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Collector;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.search.Similarity;

/**
 * A Query that matches cells matching boolean combinations of other primitive
 * queries, e.g. {@link SirenTermQuery}s, {@link SirenPhraseQuery}s, etc.
 * Implements skipTo(), and has no limitations on the numbers of added scorers. <br>
 * Uses ConjunctionScorer, DisjunctionScorer, ReqOptScorer and ReqExclScorer.
 * <p> Code taken from {@link BooleanScorer2} and adapted for the Siren use case.
 * <br>
 * We consider a SirenBooleanScorer as a primitive scorer in order to support
 * nested (group) boolean query within a cell.
 */
class SirenBooleanScorer extends SirenPrimitiveScorer {

  private final List<SirenPrimitiveScorer> requiredScorers   = new ArrayList<SirenPrimitiveScorer>();

  private final List<SirenPrimitiveScorer> optionalScorers   = new ArrayList<SirenPrimitiveScorer>();

  private final List<SirenPrimitiveScorer> prohibitedScorers = new ArrayList<SirenPrimitiveScorer>();

  private final Coordinator coordinator;

  /**
   * The scorer to which all scoring will be delegated, except for computing and
   * using the coordination factor.
   */
  private SirenScorer            countingSumScorer = null;

  private static Similarity defaultSimilarity = new DefaultSimilarity();

  private final int dataset = -1;
  private int entity = -1;
  private int tuple = -1;
  private int cell = -1;

  /**
   * Create a SirenBooleanScorer, that matches a boolean combination of
   * primitive siren scorers. In no required scorers are added, at least one of
   * the optional scorers will have to match during the search.
   *
   * @param similarity
   *          The similarity to be used.
   */
  public SirenBooleanScorer(final Similarity similarity) {
    super(similarity);
    coordinator = new Coordinator();
  }

  public void add(final SirenPrimitiveScorer scorer, final boolean required,
                  final boolean prohibited) {
    if (!prohibited) {
      coordinator.maxCoord++;
    }

    if (required) {
      if (prohibited) {
        throw new IllegalArgumentException(
          "scorer cannot be required and prohibited");
      }
      requiredScorers.add(scorer);
    }
    else if (prohibited) {
      prohibitedScorers.add(scorer);
    }
    else {
      optionalScorers.add(scorer);
    }
  }

  /**
   * Initialize the match counting scorer that sums all the scores.
   * <p>
   * When "counting" is used in a name it means counting the number of matching
   * scorers.<br>
   * When "sum" is used in a name it means score value summing over the matching
   * scorers
   */
  private void initCountingSumScorer()
  throws IOException {
    coordinator.init();
    countingSumScorer = this.makeCountingSumScorer();
  }

  private SirenScorer countingDisjunctionSumScorer(final List<SirenPrimitiveScorer> scorers)
  // each scorer from the list counted as a single matcher
  {
    return new SirenDisjunctionScorer(defaultSimilarity, scorers) {

      private int lastScoredEntity = -1;

      @Override
      public float score()
      throws IOException {
        if (this.entity() >= lastScoredEntity) {
          lastScoredEntity = this.entity();
          coordinator.nrMatchers += super.nrMatchers;
        }
        return super.score();
      }
    };
  }

  private SirenScorer countingConjunctionSumScorer(final List<SirenPrimitiveScorer> requiredScorers)
  throws IOException {
    // each scorer from the list counted as a single matcher
    final int requiredNrMatchers = requiredScorers.size();

    return new SirenConjunctionScorer(defaultSimilarity, requiredScorers) {

      private int lastScoredEntity = -1;

      @Override
      public float score()
      throws IOException {
        if (this.entity() >= lastScoredEntity) {
          lastScoredEntity = this.entity();
          coordinator.nrMatchers += requiredNrMatchers;
        }
        // All scorers match, so defaultSimilarity super.score() always has 1 as
        // the coordination factor.
        // Therefore the sum of the scores of the requiredScorers
        // is used as score.
        return super.score();
      }
    };
  }

  /**
   * Returns the scorer to be used for match counting and score summing. Uses
   * requiredScorers, optionalScorers and prohibitedScorers.
   */
  private SirenScorer makeCountingSumScorer()
  throws IOException { // each scorer counted as a single matcher
    return (requiredScorers.size() == 0) ? this.makeCountingSumScorerNoReq()
                                         : this.makeCountingSumScorerSomeReq();
  }

  private SirenScorer makeCountingSumScorerNoReq()
  throws IOException { // No required scorers
    if (optionalScorers.size() == 0) {
      return new NonMatchingScorer(); // no clauses or only prohibited clauses
    }
    else { // No required scorers. At least one optional scorer.
      final SirenScorer requiredCountingSumScorer =
        (optionalScorers.size() == 1) ? new SingleMatchScorer(optionalScorers.get(0))
                                      : this.countingDisjunctionSumScorer(optionalScorers);
      return this.addProhibitedScorers(requiredCountingSumScorer);
    }
  }

  private SirenScorer makeCountingSumScorerSomeReq()
  throws IOException { // At least one required scorer.
    final SirenScorer requiredCountingSumScorer =
      (requiredScorers.size() == 1) ? new SingleMatchScorer(requiredScorers.get(0))
                                    : this.countingConjunctionSumScorer(requiredScorers);

    if (optionalScorers.size() == 0) {
      return new SirenReqOptScorer(
        this.addProhibitedScorers(requiredCountingSumScorer), new NonMatchingScorer());
    }
    else if (optionalScorers.size() == 1) {
      return new SirenReqOptScorer(
        this.addProhibitedScorers(requiredCountingSumScorer),
        new SingleMatchScorer(optionalScorers.get(0)));
    }
    else { // optionalScorers.size() > 1
      return new SirenReqOptScorer(
        this.addProhibitedScorers(requiredCountingSumScorer),
        this.countingDisjunctionSumScorer(optionalScorers));
    }
  }

  /**
   * Returns the scorer to be used for match counting and score summing. Uses
   * the given required scorer and the prohibitedScorers.
   *
   * @param requiredCountingSumScorer
   *          A required scorer already built.
   */
  private SirenScorer addProhibitedScorers(final SirenScorer requiredCountingSumScorer) {
    if (prohibitedScorers.size() == 0) {
      return requiredCountingSumScorer;
    }
    else if (prohibitedScorers.size() == 1) {
      return new SirenReqExclScorer(requiredCountingSumScorer,
        prohibitedScorers.get(0));
    }
    return new SirenReqExclScorer(requiredCountingSumScorer,
      new SirenDisjunctionScorer(defaultSimilarity, prohibitedScorers));
  }

  /**
   * Scores and collects all matching documents.
   *
   * @param hc
   *          The collector to which all matching documents are passed through
   *          {@link HitCollector#collect(int, float)}. <br>
   *          When this method is used the {@link #explain(int)} method should
   *          not be used.
   */
  @Override
  public void score(final Collector collector) throws IOException {
    int doc;
    collector.setScorer(this);
    while ((doc = this.nextDoc()) != NO_MORE_DOCS) {
      collector.collect(doc);
    }
  }

  /**
   * Expert: Collects matching documents in a range. <br>
   * Note that {@link #nextDoc()} must be called once before this method is called
   * for the first time.
   *
   * @param hc
   *          The collector to which all matching documents are passed through
   *          {@link HitCollector#collect(int, float)}.
   * @param max
   *          Do not score documents past this.
   * @return true if more matching documents may remain.
   */
  @Override
  protected boolean score(final Collector collector, final int max, final int firstDocID)
  throws IOException {
    int doc = firstDocID;
    collector.setScorer(this);
    while (doc < max) {
      collector.collect(doc);
      doc = this.nextDoc();
    }
    return doc != NO_MORE_DOCS;
  }

  @Override
  public int dataset() {
    return dataset;
  }

  @Override
  public int docID() {
    return entity;
  }

  @Override
  public int entity() {
    return entity;
  }

  @Override
  public int tuple() {
    return tuple;
  }

  @Override
  public int cell() {
    return cell;
  }

  /**
   * Position is invalid in high-level scorers. It will always return
   * {@link Integer.MAX_VALUE}.
   */
  @Override
  public int pos() {
    return Integer.MAX_VALUE;
  }

  @Override
  public int nextDoc() throws IOException {
    if (countingSumScorer == null) {
      this.initCountingSumScorer();
    }

    if (countingSumScorer.nextDoc() != NO_MORE_DOCS) {
      entity = countingSumScorer.entity();
      tuple = countingSumScorer.tuple();
      cell = countingSumScorer.cell();
    }
    else {
      entity = NO_MORE_DOCS;
    }
    return entity;
  }

  @Override
  public int nextPosition() throws IOException {
    final boolean more = (countingSumScorer.nextPosition() != NO_MORE_POS);
    if (more) {
      tuple = countingSumScorer.tuple(); // update current tuple
      cell = countingSumScorer.cell(); // update current cell
      return 0; // position is invalid in this scorer, return 0
    }
    else {
      tuple = Integer.MAX_VALUE; // set to sentinel value
      cell = Integer.MAX_VALUE; // set to sentinel value
      return NO_MORE_POS;
    }
  }

  @Override
  public float score()
  throws IOException {
    coordinator.initDoc();
    final float sum = countingSumScorer.score();
    return sum * coordinator.coordFactor();
  }

  @Override
  public int advance(final int entity) throws IOException {
    if (countingSumScorer == null) {
      this.initCountingSumScorer();
    }

    if (countingSumScorer.advance(entity) != NO_MORE_DOCS) {
      this.entity = countingSumScorer.entity();
      this.tuple = countingSumScorer.tuple();
      this.cell = countingSumScorer.cell();
    }
    else {
      this.entity = NO_MORE_DOCS;
    }
    return this.entity;
  }

  @Override
  public int advance(final int entity, final int tuple)
  throws IOException {
    if (countingSumScorer == null) {
      this.initCountingSumScorer();
    }

    if (countingSumScorer.advance(entity, tuple) != NO_MORE_DOCS) {
      this.entity = countingSumScorer.entity();
      this.tuple = countingSumScorer.tuple();
      this.cell = countingSumScorer.cell();
    }
    else {
      this.entity = NO_MORE_DOCS;
    }
    return this.entity;
  }

  @Override
  public int advance(final int entity, final int tuple, final int cell)
  throws IOException {
    if (countingSumScorer == null) {
      this.initCountingSumScorer();
    }

    if (countingSumScorer.advance(entity, tuple, cell) != NO_MORE_DOCS) {
      this.entity = countingSumScorer.entity();
      this.tuple = countingSumScorer.tuple();
      this.cell = countingSumScorer.cell();
    }
    else {
      this.entity = NO_MORE_DOCS;
    }
    return this.entity;
  }

  @Override
  public String toString() {
    return "SirenBooleanScorer(" + this.dataset() + "," + this.entity() + "," + this.tuple() + "," + this.cell() + ")";
  }

  private class Coordinator {

    int             maxCoord     = 0;   // to be increased for each non
                                        // prohibited scorer

    private float[] coordFactors = null;

    void init() { // use after all scorers have been added.
      coordFactors = new float[maxCoord + 1];
      final Similarity sim = SirenBooleanScorer.this.getSimilarity();
      for (int i = 0; i <= maxCoord; i++) {
        coordFactors[i] = sim.coord(i, maxCoord);
      }
    }

    int nrMatchers; // to be increased by score() of match counting scorers.

    void initDoc() {
      nrMatchers = 0;
    }

    float coordFactor() {
      return coordFactors[nrMatchers];
    }
  }

  /** Count a scorer as a single match. */
  private class SingleMatchScorer
  extends SirenPrimitiveScorer {

    private final SirenPrimitiveScorer scorer;

    private int          lastScoredEntity = -1;

    SingleMatchScorer(final SirenPrimitiveScorer scorer) {
      super(scorer.getSimilarity());
      this.scorer = scorer;
    }

    @Override
    public float score()
    throws IOException {
      if (this.entity() >= lastScoredEntity) {
        lastScoredEntity = this.entity();
        coordinator.nrMatchers++;
      }
      return scorer.score();
    }

    @Override
    public int dataset() {
      return scorer.dataset();
    }

    @Override
    public int docID() {
      return scorer.docID();
    }

    @Override
    public int entity() {
      return scorer.entity();
    }

    @Override
    public int tuple() {
      return scorer.tuple();
    }

    @Override
    public int cell() {
      return scorer.cell();
    }

    @Override
    public int pos() {
      return scorer.pos();
    }

    @Override
    public int nextDoc() throws IOException {
      if (scorer.nextDoc() != NO_MORE_DOCS)
        return scorer.entity();
      return NO_MORE_DOCS;
    }

    @Override
    public int nextPosition() throws IOException {
      final boolean more = (scorer.nextPosition() != NO_MORE_POS);
      if (more) {
        return 0; // position is invalid in this scorer, return 0.
      }
      else {
        return NO_MORE_POS;
      }
    }

    @Override
    public int advance(final int entityID) throws IOException {
      if (scorer.advance(entityID) != NO_MORE_DOCS)
        return scorer.entity();
      return NO_MORE_DOCS;
    }

    @Override
    public int advance(final int entityID, final int tupleID) throws IOException {
      if (scorer.advance(entityID, tupleID) != NO_MORE_DOCS)
        return scorer.entity();
      return NO_MORE_DOCS;
    }

    @Override
    public int advance(final int entityID, final int tupleID, final int cellID) throws IOException {
      if (scorer.advance(entityID, tupleID, cellID) != NO_MORE_DOCS)
        return scorer.entity();
      return NO_MORE_DOCS;
    }

    @Override
    public String toString() {
      return "SingleMatchScorer(" + this.dataset() + "," + this.entity() + "," + this.tuple() + "," + this.cell() + ")";
    }

  }

}
