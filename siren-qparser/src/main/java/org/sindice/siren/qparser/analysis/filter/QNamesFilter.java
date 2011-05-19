/**
 * Copyright 2010, Campinas Stephane
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
 * @project siren-qparser
 * @author Campinas Stephane [ 21 Oct 2010 ]
 * @link stephane.campinas@deri.org
 * @author Renaud Delbru [ 8 Sep 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.analysis.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use the mapping QNames file to replace qnames by their namespace.
 * The path of the mapping file is provided in the definition of the SIREn
 * request handler in solrconfig.xml.
 */
public final class QNamesFilter extends TokenFilter {

  /** The property file containing the mapping */
  private final Properties            qnames        = new Properties();

  /* Attributes */
  private final CharTermAttribute     cTermAtt;

  private int   termLength;

  private static final
  Logger logger = LoggerFactory.getLogger(QNamesFilter.class);

  public QNamesFilter(final TokenStream input, final String path) {
    super(input);
    cTermAtt = input.getAttribute(CharTermAttribute.class);
    try {
      qnames.load(new FileInputStream(path));
    } catch (final FileNotFoundException e) {
      logger.error("QNames mapping file not found", e);
      throw new RuntimeException("QNames mapping file not found", e);
    } catch (final IOException e) {
      logger.error("Parsing of the QNames mapping file failed", e);
      throw new RuntimeException("Parsing of the QNames mapping file failed", e);
    }
    logger.debug("Loading QNames mapping file located at {}", path);
  }

  /**
   * Find the offset of the QName delimiter. If no delimiter is
   * found, return last offset, i.e., {@code termLength}.
   */
  protected int findDelimiter() {
    int ptr = 0;
    final char[] buffer = cTermAtt.buffer();

    while (ptr < termLength - 1) {
      if (this.isQNameDelim(buffer[ptr])) {
        if (!this.isNameStartChar(buffer[ptr + 1])) {
          break; // if this is not a valid name start char, we can stop
        }
        return ptr;
      }
      ptr++;
    }

    return termLength;
  }

  /**
   * Based on {@link http://www.w3.org/TR/REC-xml/#NT-Name}.
   * @param c
   * @return
   */
  protected boolean isNameStartChar(final char c) {
    return c == ':' || c == '_' || Character.isLetter(c);
  }
  
  protected boolean isQNameDelim(final char c) {
    return c == ':';
  }

  protected CharSequence convertQName(final int offset) {
    final String prefix = cTermAtt.subSequence(0, offset).toString();

    if (qnames.containsKey(prefix)) {
      return qnames.getProperty(prefix);
    }
    return cTermAtt.subSequence(0, offset + 1);
  }

  @Override
  public boolean incrementToken() throws IOException {
    if (!input.incrementToken()) {
      return false;
    }
    termLength = cTermAtt.length();
    int offset = 0;
    if ((offset = this.findDelimiter()) != termLength) {
      final CharSequence prefix = this.convertQName(offset);
      final CharSequence suffix = cTermAtt.subSequence(offset + 1, termLength); // skip the QName delimiter
      final int newSize = prefix.length() + suffix.length();
      cTermAtt.resizeBuffer(newSize);
      cTermAtt.setEmpty();
      cTermAtt.append(prefix);
      cTermAtt.append(suffix);
    }
    return true;
  }

}
