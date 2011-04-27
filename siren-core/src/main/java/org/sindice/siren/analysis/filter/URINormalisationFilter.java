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
import java.io.StringReader;
import java.nio.CharBuffer;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;
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
  private final TypeAttribute typeAtt;
  private final PositionIncrementAttribute posIncrAtt;

  public URINormalisationFilter(final TokenStream input) {
    super(input);
    termAtt = this.addAttribute(TermAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
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
      final String type = typeAtt.type();
      if (type.equals(TupleTokenizer.getTokenTypes()[TupleTokenizer.URI])) {
        termLength = termAtt.termLength();
        this.updateBuffer();
        _isNormalising = true;
        start = end = 0;
        this.skipScheme();
        this.nextToken();
      }
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
    		Integer.MAX_VALUE, new WhitespaceAnalyzer(Version.LUCENE_31));
    final TokenStream result = new URINormalisationFilter(stream);
    while (result.incrementToken()) {
      final CharTermAttribute termAtt = result.getAttribute(CharTermAttribute.class);
      final PositionIncrementAttribute posIncrAtt = result.getAttribute(PositionIncrementAttribute.class);
      System.out.println(termAtt.toString() + ", " + posIncrAtt.getPositionIncrement());
    }
  }

}
