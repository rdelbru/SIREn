/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 10 Dec 2009 ]
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
 * A Query that matches tuples matching boolean combinations of cell
 * queries, e.g. {@link SirenCellQuery}s.
 * Implements advance(), and has no limitations on the numbers of added scorers. <br>
 * Uses SirenCellConjunctionScorer, SirenCellDisjunctionScorer, ReqOptScorer and SirenCellReqExclScorer.
 * <p> Code taken from {@link BooleanScorer2} and adapted for the Siren use case.
 */
class SirenCellBooleanScorer
extends SirenScorer {

  private final List<SirenCellScorer> requiredScorers   = new ArrayList<SirenCellScorer>();

  private final List<SirenCellScorer> optionalScorers   = new ArrayList<SirenCellScorer>();

  private final List<SirenCellScorer> prohibitedScorers = new ArrayList<SirenCellScorer>();

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

  /**
   * The tuple index constraints
   */
  private final int tupleConstraintStart;
  private final int tupleConstraintEnd;

  /**
   * Create a SirenCellBooleanScorer, that matches a boolean combination of
   * siren boolean scorers. In no required scorers are added, at least one of
   * the optional scorers will have to match during the search.
   * </br>
   * This constructor accepts a tuple index constraint, to force the scorers
   * to match only a certain tuple.
   *
   * @param similarity
   *          The similarity to be used.
   * @param tupleConstraintStart
   *          The minimum cell index that should match (inclusive)
   * @param tupleConstraintEnd
   *          The maximum cell index that should match (inclusive)
   */
  public SirenCellBooleanScorer(final Similarity similarity,
                                final int tupleConstraintStart,
                                final int tupleConstraintEnd) {
    super(similarity);
    coordinator = new Coordinator();
    this.tupleConstraintStart = tupleConstraintStart;
    this.tupleConstraintEnd = tupleConstraintEnd;
  }

  /**
   * Create a SirenCellBooleanScorer, that matches a boolean combination of
   * siren boolean scorers. In no required scorers are added, at least one of
   * the optional scorers will have to match during the search.
   *
   * @param similarity
   *          The similarity to be used.
   */
  public SirenCellBooleanScorer(final Similarity similarity) {
    this(similarity, 0, Integer.MAX_VALUE);
  }

  public void add(final SirenCellScorer scorer, final boolean required,
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

  private SirenScorer countingDisjunctionSumScorer(final List<SirenCellScorer> scorers)
  // each scorer from the list counted as a single matcher
  {
    return new SirenCellDisjunctionScorer(defaultSimilarity, scorers) {

      private int lastScoredEntity = -1;

      @Override
      public float score() throws IOException {
        if (this.entity() >= lastScoredEntity) {
          lastScoredEntity = this.entity();
          coordinator.nrMatchers += super.nrMatchers;
        }
        return super.score();
      }
    };
  }

  private SirenScorer countingConjunctionSumScorer(final List<SirenCellScorer> requiredScorers)
  throws IOException {
    // each scorer from the list counted as a single matcher
    final int requiredNrMatchers = requiredScorers.size();

    return new SirenCellConjunctionScorer(defaultSimilarity, requiredScorers) {

      private int lastScoredEntity = -1;

      @Override
      public float score() throws IOException {
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
      return new SirenCellReqExclScorer(requiredCountingSumScorer,
        prohibitedScorers.get(0));
    }
    return new SirenCellReqExclScorer(requiredCountingSumScorer,
      new SirenCellDisjunctionScorer(defaultSimilarity, prohibitedScorers));
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
  public void score(final Collector collector)
  throws IOException {
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

  /**
   * Cell is invalid in high-level scorers. It will always return
   * {@link Integer.MAX_VALUE}.
   */
  @Override
  public int cell() {
    return Integer.MAX_VALUE;
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
      entity = this.doNext();
    }
    else {
      entity = NO_MORE_DOCS;
    }
    return entity;
  }

  /**
   * Perform a next without initial increment.
   * <p> The next is valid when the tupleID matches the constraints.
   */
  private int doNext() throws IOException {
    boolean more = true;
    tuple = countingSumScorer.tuple();

    while (more && (tuple < tupleConstraintStart ||
                    tuple > tupleConstraintEnd)) {
      if (countingSumScorer.nextPosition() == NO_MORE_POS) {
        more = (countingSumScorer.nextDoc() != NO_MORE_DOCS);
      }
      tuple = countingSumScorer.tuple();
    }

    if (more) {
      entity = countingSumScorer.entity();
    }
    else {
      entity = NO_MORE_DOCS;
    }
    return entity;
  }

  @Override
  public int nextPosition() throws IOException {
    boolean more = false;
    do {
      more = (countingSumScorer.nextPosition() != NO_MORE_POS);
      tuple = countingSumScorer.tuple();
    } while (more && (tuple < tupleConstraintStart ||
                      tuple > tupleConstraintEnd));
    if (more) {
      return 0; // position is invalid in this scorer, return 0
    }
    else {
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
      this.entity = this.doNext();
      this.tuple = countingSumScorer.tuple();
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
      this.entity = this.doNext();
      this.tuple = countingSumScorer.tuple();
    }
    else {
      this.entity = NO_MORE_DOCS;
    }
    return this.entity;
  }

  @Override
  public int advance(final int entity, final int tuple, final int cell) {
    throw new UnsupportedOperationException();
  }

  private class Coordinator {

    int             maxCoord     = 0;   // to be increased for each non
                                        // prohibited scorer

    private float[] coordFactors = null;

    void init() { // use after all scorers have been added.
      coordFactors = new float[maxCoord + 1];
      final Similarity sim = SirenCellBooleanScorer.this.getSimilarity();
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
  private class SingleMatchScorer extends SirenScorer {

    private final SirenCellScorer scorer;

    private int          lastScoredEntity = -1;

    SingleMatchScorer(final SirenCellScorer scorer) {
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

    /**
     * Cell is invalid in high-level scorers. It will always return
     * {@link Integer.MAX_VALUE}.
     */
    @Override
    public int cell() {
      return Integer.MAX_VALUE;
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
      if (scorer.nextDoc() != NO_MORE_DOCS)
        return this.doNext();
      return NO_MORE_DOCS;
    }

    /**
     * Perform a next without initial increment.
     * <p> The next is valid when the tupleID matches the constraints.
     */
    private int doNext() throws IOException {
      boolean more = true;
      int tupleID = scorer.tuple();

      while (more && (tupleID < tupleConstraintStart ||
                      tupleID > tupleConstraintEnd)) {
        if (scorer.nextPosition() == NO_MORE_POS) {
          more = (scorer.nextDoc() != NO_MORE_DOCS);
        }
        tupleID = scorer.tuple();
      }

      if (more) {
        return scorer.entity();
      }
      else {
        return NO_MORE_DOCS;
      }
    }

    @Override
    public int nextPosition() throws IOException {
      boolean more = false;
      do {
        more = (scorer.nextPosition() != NO_MORE_POS);
      } while (more && (scorer.tuple() < tupleConstraintStart ||
                        scorer.tuple() > tupleConstraintEnd));
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
        return this.doNext();
      return NO_MORE_DOCS;
    }

    @Override
    public int advance(final int entityID, final int tupleID) throws IOException {
      if (scorer.advance(entityID, tupleID) != NO_MORE_DOCS)
        return this.doNext();
      return NO_MORE_DOCS;
    }

    @Override
    public int advance(final int entityID, final int tupleID, final int cellID)
    throws IOException {
      if (scorer.advance(entityID, tupleID, cellID) != NO_MORE_DOCS)
        return this.doNext();
      return NO_MORE_DOCS;
    }

  }

}
