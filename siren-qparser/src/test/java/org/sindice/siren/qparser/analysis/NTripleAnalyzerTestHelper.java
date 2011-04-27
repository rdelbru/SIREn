/**
 * 
 * ------------------------------------------------------------
 *
 * @project solr-plugins
 *
 * Copyright (C) 2007,
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
