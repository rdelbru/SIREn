/**
 * Copyright 2011, Campinas Stephane
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
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Split the URI with a mailto scheme into.
 * The URI mailto:test@test.fr is splitted into
 * <ul>
 * <li>mailto</li>
 * <li>test@test.fr</li>
 * <li>mailto:test@test.fr</li> (at the same position as the last one)
 * </ul>
 */
public class MailtoFilter extends TokenFilter {

  private final CharTermAttribute           termAtt;
  private final TypeAttribute               typeAtt;
  private final PositionIncrementAttribute  posIncrAtt;
  
  private CharBuffer termBuffer;
  private boolean isMailto = false;
  private boolean getMail = true;
  
  /**
   * @param input
   */
  protected MailtoFilter(TokenStream input) {
    super(input);
    termAtt = this.addAttribute(CharTermAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    termBuffer = CharBuffer.allocate(256);
  }

  @Override
  public final boolean incrementToken()
  throws IOException {
    if (isMailto) {
      termAtt.setEmpty();
      if (getMail) { // return only the mail part
        getMail = false;
        termAtt.copyBuffer(termBuffer.array(), 7, termBuffer.position() - 7);
      } else { // return the scheme + the mail part
        isMailto = false;
        getMail = true;
        posIncrAtt.setPositionIncrement(0);
        termAtt.copyBuffer(termBuffer.array(), 0, termBuffer.position());
      }
      return true;
    }
    
    if (input.incrementToken()) {
      final String type = typeAtt.type();
      if (type.equals(TupleTokenizer.getTokenTypes()[TupleTokenizer.URI]) && isMailtoScheme()) {
        updateBuffer();
        termBuffer.put(termAtt.buffer(), 0, termAtt.length());
        termAtt.setLength(6); // keep only mailto
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
    if (termAtt.charAt(6) != ':') {
      return false;
    }
    if (termAtt.charAt(0) != 'm' || termAtt.charAt(1) != 'a' || termAtt.charAt(2) != 'i' ||
        termAtt.charAt(3) != 'l' || termAtt.charAt(4) != 't' || termAtt.charAt(5) != 'o')
      return false;
    isMailto = true;
    return true;
  }

}
