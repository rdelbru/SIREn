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
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.filter;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.PositionAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;

/**
 * Filter that encode the structural information of each token into its payload.
 */
public class PositionFilter
extends TokenFilter {

  private final TupleAttribute tupleAtt;
  private final CellAttribute cellAtt;
  private final PositionAttribute posAtt;
  private final PositionIncrementAttribute posIncrAtt;

  private int lastTuple = 0;
  private int lastCell = 0;
  private int lastPosition = 0;

  public PositionFilter(final TokenStream input) {
    super(input);
    tupleAtt = this.addAttribute(TupleAttribute.class);
    cellAtt = this.addAttribute(CellAttribute.class);
    posAtt = this.addAttribute(PositionAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
  }

  @Override
  public final boolean incrementToken() throws IOException {
    if (!input.incrementToken()) {
      return false;
    }

    if (lastTuple != tupleAtt.tuple() ||
       (lastTuple == tupleAtt.tuple() && lastCell != cellAtt.cell())) {
      lastPosition = 0;
    }

    lastPosition += posIncrAtt.getPositionIncrement();
    lastTuple = tupleAtt.tuple();
    lastCell = cellAtt.cell();
    if (lastPosition > 0) {
      posAtt.setPosition(lastPosition - 1);
    }
    else {
      posAtt.setPosition(lastPosition);
    }
    return true;
  }

}
