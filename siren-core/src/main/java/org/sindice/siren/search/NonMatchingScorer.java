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
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;

/** A scorer that matches no entity at all. */
class NonMatchingScorer extends SirenPrimitiveScorer {

  public NonMatchingScorer() { super(null); } // no similarity used

  @Override
  public int docID() { throw new UnsupportedOperationException(); }

  @Override
  public int cell() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int dataset() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int entity() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int pos() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int tuple() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int nextDoc() throws IOException { return NO_MORE_DOCS; }

  @Override
  public int nextPosition()
  throws IOException {
    return NO_MORE_POS;
  }

  @Override
  public float score() { throw new UnsupportedOperationException(); }

  @Override
  public float scoreCell() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int advance(final int target) { return NO_MORE_DOCS; }


  @Override
  public int advance(final int entityID, final int tupleID)
  throws IOException {
    return NO_MORE_DOCS;
  }

  @Override
  public int advance(final int entityID, final int tupleID, final int cellID)
  throws IOException {
    return NO_MORE_DOCS;
  }

//  @Override
//  public Explanation explain(final int doc) {
//    final Explanation e = new Explanation();
//    e.setDescription("No entity matches.");
//    return e;
//  }

}


