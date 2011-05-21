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
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;
import org.sindice.siren.index.PackedIntSirenPayload;

/**
 * Filter that encode the structural information of each token into its payload.
 */
public class SirenDeltaPayloadFilter
extends TokenFilter {

  private final TupleAttribute tupleAtt;
  private final CellAttribute cellAtt;
  private final PayloadAttribute payloadAtt;
  private final CharTermAttribute termAtt;

  Map<Integer, Integer[]> previousTerms = new HashMap<Integer, Integer[]>();

  public SirenDeltaPayloadFilter(final TokenStream input) {
    super(input);
    termAtt = this.addAttribute(CharTermAttribute.class);
    payloadAtt = this.addAttribute(PayloadAttribute.class);
    tupleAtt = this.addAttribute(TupleAttribute.class);
    cellAtt = this.addAttribute(CellAttribute.class);
  }

  @Override
  public void close() throws IOException {
    super.close();
    previousTerms.clear();
  }


  @Override
  public void reset() throws IOException {
    super.reset();
    previousTerms.clear();
  }

  @Override
  public final boolean incrementToken() throws IOException {
    int tuple, cell;

    if (input.incrementToken()) {

      final int hash = termAtt.hashCode();
      if (!previousTerms.containsKey(hash)) {
        tuple = tupleAtt.tuple();
        cell = cellAtt.cell();
        previousTerms.put(termAtt.hashCode(), new Integer[]{tuple, cell});

        if (tuple != 0 || cell != 0) { // if tuple and cell == 0, no need to store payload
          payloadAtt.setPayload(new PackedIntSirenPayload(tuple, cell));
        }
      }
      else {
        tuple = previousTerms.get(termAtt.hashCode())[0];
        cell = previousTerms.get(termAtt.hashCode())[1];
        previousTerms.put(termAtt.hashCode(), new Integer[]{tupleAtt.tuple(), cellAtt.cell()});

        if (tuple == tupleAtt.tuple()) {
          if (cell == cellAtt.cell()) { // tuple and cell == 0, no need to store payload
            return true;
          }
          payloadAtt.setPayload(new PackedIntSirenPayload(0,
            cellAtt.cell() - cell));
        }
        else { // tuple < tupleAtt.tuple()
          payloadAtt.setPayload(new PackedIntSirenPayload(tupleAtt.tuple() - tuple,
            cellAtt.cell()));
        }
      }
      return true;
    }
    else {
      return false;
    }
  }

}
