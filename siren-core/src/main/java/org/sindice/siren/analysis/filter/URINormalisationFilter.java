/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sindice.siren.analysis.filter;

import java.nio.CharBuffer;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

/**
 * Break an URI into smaller components based on delimiters, such as ':', '/',
 * etc. and uppercase.
 * <p>
 * This filter is very demanding in term of CPU. In general, when this filter is
 * used, the parsing time for a set of tuples doubles. If you don't need it,
 * removed it from your token stream.
 */
public class URINormalisationFilter
extends TokenFilter {

  protected boolean _isNormalising = false;

  private int    start;
  private int    end;
  private int    termLength;
  private CharBuffer termBuffer;
  protected int     _nTokens = 0;

  private final CharTermAttribute termAtt;
  private final PositionIncrementAttribute posIncrAtt;

  public URINormalisationFilter(final TokenStream input) {
    super(input);
    termAtt = this.addAttribute(CharTermAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    termBuffer = CharBuffer.allocate(256);
  }

  @Override
  public final boolean incrementToken() throws java.io.IOException {

    // While we are normalising the URI
    if (_isNormalising) {
      this.posIncrAtt.setPositionIncrement(1); // reset the position increment
      this.nextToken();
      return true;
    }

    // Otherwise, get next URI token and start normalisation
    if (input.incrementToken()) {
      termLength = termAtt.length();
      this.updateBuffer();
      _isNormalising = true;
      start = end = 0;
      _nTokens =0;
      this.skipScheme();
      this.nextToken();
      return true;
    }

    return false;
  }

  protected void updateBuffer() {
    if (termBuffer.capacity() > termLength) {
      termBuffer.clear();
      termBuffer.put(termAtt.buffer(), 0, termLength);
    }
    else {
      termBuffer = CharBuffer.allocate(termLength);
      termBuffer.put(termAtt.buffer(), 0, termLength);
    }
  }

  /**
   * Skip the scheme part. Added for SRN-66 in order to make the URI
   * normalisation less aggressive.
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
      _nTokens++;
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
    termAtt.copyBuffer(termBuffer.array(), start, end - start);
    start = end;
  }

  protected void updateFinalToken() {
    termAtt.copyBuffer(termBuffer.array(), 0, termLength);
    final int posInc = _nTokens == 0 ? 1 : 0;
    posIncrAtt.setPositionIncrement(posInc);
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

}
