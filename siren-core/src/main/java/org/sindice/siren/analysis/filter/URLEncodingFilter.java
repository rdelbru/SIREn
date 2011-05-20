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
 * @author Campinas Stephane [ 19 May 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.analysis.filter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Decode the URL encoding format of special characters such as '?' or '<'; special
 * characters (excepth of the SPACE that can be encoded with '+') begins with a '%'
 * and are followed by two characters in hexadecimal format.
 * if a special character cannot be decoded, it is just skipped and the decoding
 * process just continue.
 * When an URI has special characters, two stems of the URI are produced 
 * (both terms have the same position):
 * <ul>
 * <li> the original URI </li>
 * <li> the decoded URI </li>
 * </ul>
 */
public class URLEncodingFilter extends TokenFilter {

  private final CharsetDecoder    charsetDecoder;
  private final ByteBuffer        decoded = ByteBuffer.allocate(32);
  
  private boolean                 modifiedURI = false;
  private CharBuffer              termBuffer;
  private int                     termLength;

  private final CharTermAttribute           termAtt;
  private final TypeAttribute               typeAtt;
  private final PositionIncrementAttribute  posIncrAtt;

  /**
   * 
   * @param input
   * @param encoding the name of a supported <a href="../lang/package-summary.html#charenc">character encoding</a>.
   * @throws UnsupportedCharsetException if the character encoding is not supported or recognised.
   */
  public URLEncodingFilter(TokenStream input, String encoding)
  throws UnsupportedCharsetException {
    super(input);
    final Charset charset = this.lookupCharset(encoding);
    charsetDecoder = charset.newDecoder()
                            .onMalformedInput(CodingErrorAction.REPLACE)
                            .onUnmappableCharacter(CodingErrorAction.REPLACE);    
    termAtt = this.addAttribute(CharTermAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    termBuffer = CharBuffer.allocate(256);
  }

  @Override
  public boolean incrementToken()
  throws IOException {
    if (modifiedURI) { // Return the previously decoded URI
      modifiedURI = false;
      termAtt.setEmpty();
      termAtt.setLength(termBuffer.position());
      termAtt.copyBuffer(termBuffer.array(), 0, termBuffer.position());
      posIncrAtt.setPositionIncrement(0);
      return true;
    }
    
    if (input.incrementToken()) {
      final String type = typeAtt.type();
      if (type.equals(TupleTokenizer.getTokenTypes()[TupleTokenizer.URI])) {
        termLength = termAtt.length();
        this.updateBuffer();
        decode();
      }
      return true;
    }
    return false;
  }

  /**
   * Check if the buffer is big enough
   */
  private void updateBuffer() {
    final int newTermLength = termLength >> 1;
    
    if (termBuffer.capacity() < termLength && termBuffer.capacity() < newTermLength) {
      termBuffer = CharBuffer.allocate(termLength > 500 ? newTermLength : termLength);
    }
    termBuffer.clear();
  }
  
  /**
   * look for the class of the given charset
   * @param csn
   * @return
   * @throws UnsupportedCharsetException
   */
  private Charset lookupCharset(String csn)
  throws UnsupportedCharsetException {
    if (Charset.isSupported(csn)) {
      return Charset.forName(csn);
    }
    throw new UnsupportedCharsetException(csn);
  }

  /**
   * Return the decimal value of an hexadecimal number. If it is not hexadecimal,
   * a negative value is returned.
   * @param c
   * @return
   */
  private int hexaToInt(final char c) {
    switch (c) {
      case '0':
        return 0;
      case '1':
        return 1;
      case '2':
        return 2;
      case '3':
        return 3;
      case '4':
        return 4;
      case '5':
        return 5;
      case '6':
        return 6;
      case '7':
        return 7;
      case '8':
        return 8;
      case '9':
        return 9;
      case 'a':
        return 10;
      case 'b':
        return 11;
      case 'c':
        return 12;
      case 'd':
        return 13;
      case 'e':
        return 14;
      case 'f':
        return 15;
      case 'A':
        return 10;
      case 'B':
        return 11;
      case 'C':
        return 12;
      case 'D':
        return 13;
      case 'E':
        return 14;
      case 'F':
        return 15;
      default:
        /*
         * Return a negative value if the hexadecimal character is invalid.
         * Because it is < 0 and big enough, the character won't be decoded.
         */
        return -241;
    }
  }
  
  /**
   * Decode URL encoded characters of the URI
   */
  private void decode() {
    char c;
    int i = 0;
    
    while (i < termLength) {
      c = termAtt.charAt(i);
      switch (c) {
      case '+': // SPACE
        modifiedURI = true;
        termBuffer.put(' ');
        i++;
        break;
      case '%': // Special character
        /*
         * Starting with this instance of %, process all consecutive substrings
         * of the form %xy. Each substring %xy will yield a byte. Convert all
         * consecutive  bytes obtained this way to whatever character(s) they
         * represent in the provided encoding.
         * 
         * xy is a hexadecimal number.
         */
        modifiedURI = true;
        decoded.clear();
        while (i + 2 < termLength && c == '%') {
          final char c1 = termAtt.charAt(i + 1);
          final char c2 = termAtt.charAt(i + 2);
          
          // The next two characters converted from a hex to a decimal value
          final int value = (hexaToInt(c1) << 4) + hexaToInt(c2);
          if (value >= 0) { // Negative value are illegal. Just skip it.
            if (!decoded.hasRemaining()) { // No more place in the buffer, output what is already there.
              decoded.position(0);
              charsetDecoder.decode(decoded, termBuffer, true);
              decoded.clear();
            }
            decoded.put((byte) value);
          } else // put the value back, without changing it
            termBuffer.put('%').put(c1).put(c2);
          i += 3;
          if (i < termLength)
            c = termAtt.charAt(i);
        }
        // decode the chain of special characters
        final int limit = decoded.position();
        decoded.position(0);
        decoded.limit(limit);
        charsetDecoder.decode(decoded, termBuffer, true);
        // incomplete byte encoding (e.g., %x). Skip it.
        if (i < termLength && c == '%') {
          termBuffer.put('%');
          i++;
        }
        break;
      default:
        termBuffer.put(c);
        i++;
        break;
      }
    }
  }
  
}
