/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Decode the URI encoding format of special characters such as '?' or '<'; special
 * characters (except of the SPACE that can be encoded with '+' and '%20') begins
 * with a '%' and are followed by two characters in hexadecimal format.
 * if a special character cannot be decoded, it is just skipped and the decoding
 * process just continue.
 * When an URI has special characters, two stems of the URI are produced
 * (both terms have the same position):
 * <ul>
 * <li> the original URI </li>
 * <li> the decoded URI </li>
 * </ul>
 * <p>
 * This filter is to be applied on a token of type {@link TupleTokenizer.URI} only.
 */
public class URIEncodingFilter extends TokenFilter {

  private final CharsetDecoder    charsetDecoder;
  private final ByteBuffer        decoded = ByteBuffer.allocate(32);

  private boolean                 modifiedURI = false;
  private CharBuffer              termBuffer;
  private int                     termLength;

  private final CharTermAttribute           termAtt;
  private final PositionIncrementAttribute  posIncrAtt;

  /**
   *
   * @param input
   * @param encoding the name of a supported <a href="../lang/package-summary.html#charenc">character encoding</a>.
   * @throws UnsupportedCharsetException if the character encoding is not supported or recognised.
   */
  public URIEncodingFilter(final TokenStream input, final String encoding)
  throws UnsupportedCharsetException {
    super(input);
    final Charset charset = this.lookupCharset(encoding);
    charsetDecoder = charset.newDecoder()
                            .onMalformedInput(CodingErrorAction.REPLACE)
                            .onUnmappableCharacter(CodingErrorAction.REPLACE);
    termAtt = this.addAttribute(CharTermAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    termBuffer = CharBuffer.allocate(256);
  }

  @Override
  public final boolean incrementToken()
  throws IOException {
    if (modifiedURI) { // Return the previously decoded URI
      modifiedURI = false;
      termAtt.setEmpty();
      termAtt.copyBuffer(termBuffer.array(), 0, termBuffer.position());
      posIncrAtt.setPositionIncrement(0);
      return true;
    }

    if (input.incrementToken()) {
      termLength = termAtt.length();
      this.updateBuffer();
      this.decode();
      return true;
    }
    return false;
  }

  /**
   * Check if the buffer is big enough
   */
  private void updateBuffer() {
    if (termBuffer.capacity() < termLength) {
      termBuffer = CharBuffer.allocate(termLength);
    }
    termBuffer.clear();
  }

  /**
   * look for the class of the given charset
   * @param csn
   * @return
   * @throws UnsupportedCharsetException
   */
  private Charset lookupCharset(final String csn)
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
   * Return the decimal value of an hexadecimal number, multiplied by 16.
   * If it is not hexadecimal, a negative value is returned.
   * @param c
   * @return
   */
  private int hexaToInt2(final char c) {
    switch (c) {
      case '0':
        return 0;
      case '1':
        return 16;
      case '2':
        return 32;
      case '3':
        return 48;
      case '4':
        return 64;
      case '5':
        return 80;
      case '6':
        return 96;
      case '7':
        return 112;
      case '8':
        return 128;
      case '9':
        return 144;
      case 'a':
        return 160;
      case 'b':
        return 176;
      case 'c':
        return 192;
      case 'd':
        return 208;
      case 'e':
        return 224;
      case 'f':
        return 240;
      case 'A':
        return 160;
      case 'B':
        return 176;
      case 'C':
        return 192;
      case 'D':
        return 208;
      case 'E':
        return 224;
      case 'F':
        return 240;
      default:
        /*
         * Return a negative value if the hexadecimal character is invalid.
         * Because it is < 0 and big enough, the character won't be decoded.
         */
        return -241;
    }
  }

  /**
   * Partial decoding of URI encoded characters.
   * <br>
   * Ignore the '+' (SPACE) cases, as it does not
   * make sense to index URIs with a space. Nobody will be able to search them
   * as a space will be considered as a character delimitation.
   * <br>
   * Replace %20 by +, so that the URI can be tokenised easily (%20 causes
   * problem during tokenisation, while + does not).
   */
  private void decode() {
    char c;
    int i = 0;

    while (i < termLength) {
      c = termAtt.charAt(i);
      switch (c) {
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

        while (i + 2 < termLength && c == '%') {
          final char c1 = termAtt.charAt(i + 1);
          final char c2 = termAtt.charAt(i + 2);

          // The next two characters converted from a hex to a decimal value
          final int value = this.hexaToInt2(c1) + this.hexaToInt(c2);
          if (value == 32) { // replace the SPACE character, encoded by %20, by +
            this.decodeChars();
            termBuffer.put('+');
          } else if (value >= 0) { // Negative value are illegal. Just skip it.
            if (!decoded.hasRemaining()) { // No more place in the buffer, output what is already there.
              this.decodeChars();
            }
            decoded.put((byte) value);
          } else { // put the value back, without changing it
            this.decodeChars();
            termBuffer.put('%').put(c1).put(c2);
          }
          i += 3;
          if (i < termLength)
            c = termAtt.charAt(i);
        }
        // decode the chain of special characters
        this.decodeChars();
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

  private void decodeChars() {
    final int limit = decoded.position();
    decoded.position(0);
    decoded.limit(limit);
    charsetDecoder.decode(decoded, termBuffer, true);
    decoded.clear();
  }

}
