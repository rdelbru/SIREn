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

import java.io.IOException;
import java.nio.CharBuffer;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

/**
 * Split an URI with a mailto scheme.
 *
 * <p>
 *
 * The mailto URI is tokenised into two tokens:
 * <ul>
 * <li> one token with the 'mailto:' removed (e.g., test@test.fr)
 * <li> one token with the original URI (e.g., mailto:test@test.fr) at the same
 *      position as the last one
 * </ul>
 */
public class MailtoFilter extends TokenFilter {

  private final CharTermAttribute           termAtt;
  private final PositionIncrementAttribute  posIncrAtt;

  private CharBuffer termBuffer;
  private boolean isMailto = false;

  /**
   * @param input
   */
  public MailtoFilter(final TokenStream input) {
    super(input);
    termAtt = this.addAttribute(CharTermAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    termBuffer = CharBuffer.allocate(256);
  }

  @Override
  public final boolean incrementToken()
  throws IOException {
    if (isMailto) {
      termAtt.setEmpty();
      // return the scheme + the mail part
      isMailto = false;
      posIncrAtt.setPositionIncrement(0);
      termAtt.copyBuffer(termBuffer.array(), 0, termBuffer.position());
      return true;
    }

    if (input.incrementToken()) {
      if (this.isMailtoScheme()) {
        this.updateBuffer();
        termBuffer.put(termAtt.buffer(), 0, termAtt.length());
        // return only the mail part
        posIncrAtt.setPositionIncrement(1);
        termAtt.copyBuffer(termBuffer.array(), 7, termBuffer.position() - 7);
      }
      return true;
    }
    return false;
  }

  /**
   * Check if the buffer is big enough
   */
  private void updateBuffer() {
    if (termBuffer.capacity() < termAtt.length()) {
      termBuffer = CharBuffer.allocate(termAtt.length());
    }
    termBuffer.clear();
  }

  /**
   *
   * @return true if the URI start with mailto:
   */
  private boolean isMailtoScheme() {
    if (termAtt.length() < 7 || termAtt.charAt(6) != ':') {
      return false;
    }
    if (termAtt.charAt(0) != 'm' || termAtt.charAt(1) != 'a' || termAtt.charAt(2) != 'i' ||
        termAtt.charAt(3) != 'l' || termAtt.charAt(4) != 't' || termAtt.charAt(5) != 'o')
      return false;
    isMailto = true;
    return true;
  }

}
