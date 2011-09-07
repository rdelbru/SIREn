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
 * @project solr-plugins
 * @author Renaud Delbru [ 3 mars 08 ]
 * @link http://renaud.delbru.fr/
 * All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import org.apache.lucene.index.PositionBasedTermVectorMapper.TVPositionInfo;

public class NTripleAnalyzerTestHelper extends NTripleTestHelper {
  
  public static boolean checkToken(TVPositionInfo info, String expectedTerm, 
      int expectedPosition, int expectedStartOffset, int expectedEndOffset) {
    if (!info.getTerms().get(0).equals(expectedTerm)) {
      System.err.println("Term " + expectedTerm + " expected. Get " + 
          info.getTerms().get(0));
      return false;
    }
    if (info.getPosition() != expectedPosition) {
      System.err.println("Position " + expectedPosition + " expected. Get " + 
          info.getPosition());
      return false;
    }
    if (info.getOffsets().get(0).getStartOffset() != expectedStartOffset) {
      System.err.println("Start offset " + expectedStartOffset + 
          " expected. Get " + info.getOffsets().get(0).getStartOffset());
      return false;
    }
    if (info.getOffsets().get(0).getEndOffset() != expectedEndOffset) {
      System.err.println("End offset " + expectedEndOffset + " expected. Get " + 
          info.getOffsets().get(0).getEndOffset());
      return false;
    }
    return true;
  }
  
}
