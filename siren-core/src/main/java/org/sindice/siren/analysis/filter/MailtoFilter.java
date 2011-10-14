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
 * @project siren-core
 * @author Campinas Stephane [ 21 May 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.analysis.filter;

import java.io.IOException;
import java.nio.CharBuffer;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Split the URI with a mailto scheme into.
 * The URI mailto:test@test.fr is splitted into
 * <ul>
 * <li>mailto</li>
 * <li>test@test.fr</li>
 * <li>mailto:test@test.fr</li> (at the same position as the last one)
 * </ul>
 * <p>
 * This filter is to be applied on a token of type {@link TupleTokenizer.URI} only.
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
