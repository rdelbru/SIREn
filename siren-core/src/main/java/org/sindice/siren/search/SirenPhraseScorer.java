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

import org.apache.lucene.index.TermPositions;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;

/**
 * <p> An entity is considered matching if it contains the phrase-query terms at
 * "valid" positions. What "valid positions" are depends on the type of the
 * phrase query: for an exact phrase query terms are required to appear in
 * adjacent locations, while for a sloppy phrase query some distance between the
 * terms is allowed. The method {@link #phraseFreq()}, when invoked, compute all the
 * occurrences of the phrase within the entity. A non
 * zero frequency means a match.
 * <p> Code taken from {@link PhraseScorer} and adapted for the Siren use case.
 */
abstract class SirenPhraseScorer
extends SirenPrimitiveScorer {

  private final Similarity   sim;

  protected byte[]           norms;

  protected float            value;

  private boolean            firstTime = true;

  private boolean            more      = true;

  protected SirenPhraseQueue pq;

  protected SirenPhrasePositions first, last;

  /**
   * phrase occurrences in current entity.
   */
  protected int              occurrences = 0;

  /** Current structural and positional information */
  protected final int           dataset = -1;
  protected int                 entity = -1;
  protected int                 tuple = -1;
  protected int                 cell = -1;
  protected int                 pos = -1;

  SirenPhraseScorer(final Weight weight, final TermPositions[] tps,
                    final int[] offsets, final Similarity similarity,
                    final byte[] norms) {
    super(similarity);
    sim = similarity;
    this.norms = norms;
    this.value = weight.getValue();

    // convert tps to a list of phrase positions.
    // note: phrase-position differs from term-position in that its position
    // reflects the phrase offset: pp.pos = tp.pos - offset.
    // this allows to easily identify a matching (exact) phrase
    // when all SirenPhrasePositions have exactly the same position, tuple and
    // cell.
    for (int i = 0; i < tps.length; i++) {
      final SirenPhrasePositions pp = new SirenPhrasePositions(tps[i], offsets[i]);
      if (last != null) { // add next to end of list
        last.next = pp;
      }
      else
        first = pp;
      last = pp;
    }

    pq = new SirenPhraseQueue(tps.length); // construct empty pq
  }

  @Override
  public int docID() {
    return entity;
  }

  @Override
  public int dataset() {
    return dataset;
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

  @Override
  public int pos() {
    return pos;
  }

  public int phraseFreq() throws IOException {
    // compute all remaining occurrences
    while (this.nextPosition() != NO_MORE_POS) {
      ;
    }
    return occurrences;
  }

  /**
   * Move to the next entity identifier. Return true if there is such an entity
   * identifier.
   */
  @Override
  public int nextDoc()
  throws IOException {
    if (firstTime) {
      this.init();
      firstTime = false;
    }
    else if (more) {
      more = (last.nextDoc() != NO_MORE_DOCS); // trigger further scanning
    }
    return this.doNext();
  }

  /**
   * Perform a next without initial increment
   */
  private int doNext()
  throws IOException {
    while (more) {
      while (more && first.entity() < last.entity()) { // find entity w/ all the terms
        more = (first.advance(last.entity()) != NO_MORE_DOCS); // skip first upto last
        this.firstToLast(); // and move it to the end
      }

      if (more) { // found an entity with all of the terms
        this.initQueue();
        if (this.doNextPosition() != NO_MORE_POS) { // check for phrase
          entity = first.entity();
          return entity;
        }
        else {
          more = (last.nextDoc() != NO_MORE_DOCS); // trigger further scanning
        }
      }
    }
    return NO_MORE_DOCS; // no more matches
  }

  @Override
  public int nextPosition() throws IOException {
    if (last.nextPosition() == NO_MORE_POS) { // scan forward in last
      return NO_MORE_POS;
    }

    return this.doNextPosition();
  }

  public abstract int doNextPosition() throws IOException;

  @Override
  public float score()
  throws IOException {
    if (firstTime)
      throw new InvalidCallException("next or skipTo should be called first");

    final float raw = sim.tf(this.phraseFreq()) * value; // raw score
    return norms == null ? raw : raw * sim.decodeNormValue(norms[first.entity()]); // normalize
  }

  @Override
  public int advance(final int entityID) throws IOException {
    if (entity == entityID) { // optimised case: do nothing
      return entity;
    }

    firstTime = false;
    for (SirenPhrasePositions pp = first; more && pp != null; pp = pp.next) {
      more = (pp.advance(entityID) != NO_MORE_DOCS);
    }
    if (more) {
      this.sort(); // re-sort
    }
    return this.doNext();
  }

  @Override
  public int advance(final int entityID, final int tupleID)
  throws IOException {
    if (entity == entityID) { // optimised case: find tuple in occurrences
      while (tuple < tupleID && this.nextPosition() != NO_MORE_POS) {
        ;
      }
      // If tuple found, return true, if not, skip to next entity
      return (tuple >= tupleID) ? entity : this.nextDoc();
    }

    firstTime = false;
    for (SirenPhrasePositions pp = first; more && pp != null; pp = pp.next) {
      more = (pp.advance(entityID, tupleID) != NO_MORE_DOCS);
    }
    if (more) {
      this.sort(); // re-sort
    }
    return this.doNext(); // find next matching entity
  }

  @Override
  public int advance(final int entityID, final int tupleID, final int cellID)
  throws IOException {
    if (entity == entityID) { // optimised case: find tuple in occurrences
      while ((tuple < tupleID || (tuple == tupleID && cell < cellID))
              && this.nextPosition() != NO_MORE_POS) {
        ;
      }
      // If tuple and cell found, return true, if not, skip to next entity
      return (tuple > tupleID || (tuple == tupleID && cell >= cellID)) ? entity : this.nextDoc();
    }

    firstTime = false;
    for (SirenPhrasePositions pp = first; more && pp != null; pp = pp.next) {
      more = (pp.advance(entityID, tupleID, cellID) != NO_MORE_DOCS);
    }
    if (more) {
      this.sort(); // re-sort
    }
    return this.doNext(); // find next matching entity
  }

  private void init()
  throws IOException {
    for (SirenPhrasePositions pp = first; more && pp != null; pp = pp.next) {
      more = (pp.nextDoc() != NO_MORE_DOCS);
    }
    if (more) {
      this.sort();
    }
  }

  private void initQueue() throws IOException {
    // sort list with pq
    pq.clear();
    for (SirenPhrasePositions pp = first; pp != null; pp = pp.next) {
      pp.firstPosition();
      pq.add(pp); // build pq from list
    }
    this.pqToList(); // rebuild list from pq
  }

  private void sort() {
    pq.clear();
    for (SirenPhrasePositions pp = first; pp != null; pp = pp.next) {
      pq.add(pp);
    }
    this.pqToList();
  }

  protected final void pqToList() {
    last = first = null;
    while (pq.top() != null) {
      final SirenPhrasePositions pp = pq.pop();
      if (last != null) { // add next to end of list
        last.next = pp;
      }
      else {
        first = pp;
      }
      last = pp;
      pp.next = null;
    }
  }

  protected final void firstToLast() {
    last.next = first; // move first to end of list
    last = first;
    first = first.next;
    last.next = null;
  }

  @Override
  public String toString() {
    return "PhraseScorer(" + dataset + "," + entity + "," + tuple + "," + cell + ")";
  }

}
