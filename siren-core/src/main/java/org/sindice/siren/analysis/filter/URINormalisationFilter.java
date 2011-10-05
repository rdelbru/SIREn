/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.filter;

import java.io.IOException;
import java.io.StringReader;
import java.nio.CharBuffer;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Break an URI into smaller components based on delimiters, such as ':', '/',
 * etc. and uppercase.
 * <br>
 * This filter is very demanding in term of CPU. In general, when this filter is
 * used, the parsing time for a set of tuples doubles. If you don't need it,
 * removed it from the TupleAnalyzer.
 */
public class URINormalisationFilter
extends TokenFilter {

  protected boolean _isNormalising = false;

  private int    start;
  private int    end;
  private int    termLength;
  private CharBuffer termBuffer;

  private final TermAttribute termAtt;
  private final PositionIncrementAttribute posIncrAtt;

  public URINormalisationFilter(final TokenStream input) {
    super(input);
    termAtt = this.addAttribute(TermAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    termBuffer = CharBuffer.allocate(256);
  }

  @Override
  public final boolean incrementToken() throws java.io.IOException {

    // While we are normalising the URI
    if (_isNormalising) {
      this.nextToken();
      return true;
    }

    // Otherwise, get next URI token and start normalisation
    if (input.incrementToken()) {
      termLength = termAtt.termLength();
      this.updateBuffer();
      _isNormalising = true;
      start = end = 0;
      this.skipScheme();
      this.nextToken();
      return true;
    }

    return false;
  }

  protected void updateBuffer() {
    if (termBuffer.capacity() > termLength) {
      termBuffer.clear();
      termBuffer.put(termAtt.termBuffer(), 0, termLength);
    }
    else {
      termBuffer = CharBuffer.allocate(termLength);
      termBuffer.put(termAtt.termBuffer(), 0, termLength);
    }
  }

  /**
   * Skip the scheme part. Added for SRN-66 in order to make the URI
   * normalisation less agressive.
   */
  protected void skipScheme() {
    while (start < termLength) {
      if (termBuffer.get(start++) == ':') {
        if (termBuffer.get(start) == '/') {
          if (termBuffer.get(start + 1) == '/') {
            start += 1;
          }
        }
        return;
      }
    }
  }

  protected void nextToken() {
    // There is still delimiters
    while (this.findNextToken()) {
      // SRN-66: skip tokens with less than 4 characters
      if (end - start < 4) {
        start = end;
        continue;
      }
      this.updateToken();
      return;
    }
    // No more delimiters, we have to return the full URI as last step
    this.updateFinalToken();
    _isNormalising = false;
  }

  protected boolean findNextToken() {
    while (start < termLength) {
      if (this.isDelim(termBuffer.get(start))) {
        start++; continue;
      }
      else {
        end = start;
        do {
          end++;
        } while (end < termLength && !this.isBreakPoint(termBuffer.get(end)));
        return true;
      }
    }
    return false;
  }

  protected void updateToken() {
    termAtt.setTermBuffer(termBuffer.array(), start, end - start);
    start = end;
  }

  protected void updateFinalToken() {
    termAtt.setTermBuffer(termBuffer.array(), 0, termLength);
    posIncrAtt.setPositionIncrement(0);
  }

  protected boolean isBreakPoint(final int c) {
    return this.isDelim(c) || this.isUppercase(c);
  }

  protected boolean isDelim(final int c) {
    return Character.isLetterOrDigit(c) ? false : true;
  }

  protected boolean isUppercase(final int c) {
    return Character.isUpperCase(c) ? true : false;
  }

  /**
   * For testing purpose
   */
  public static void main(final String[] args) throws IOException {
    final TupleTokenizer stream = new TupleTokenizer(new StringReader("" +
    		"<mailto:renaud.delbru@deri.org> <http://renaud.delbru.fr/rdf/foaf> " +
    		"<http://renaud.delbru.fr/>  <http://xmlns.com/foaf/0.1/workplaceHomepage/>"),
    		Integer.MAX_VALUE);
    final TokenStream result = new URINormalisationFilter(stream);
    while (result.incrementToken()) {
      final CharTermAttribute termAtt = result.getAttribute(CharTermAttribute.class);
      final PositionIncrementAttribute posIncrAtt = result.getAttribute(PositionIncrementAttribute.class);
      System.out.println(termAtt.toString() + ", " + posIncrAtt.getPositionIncrement());
    }
  }

}
