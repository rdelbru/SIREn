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
 * @project solr-plugins
 * @author Renaud Delbru [ 8 Sep 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public final class EntityQueryTokenizer extends Tokenizer {

  private final CharTermAttribute cTermAtt;
  private final TypeAttribute typeAtt;

  /** A private instance of the JFlex-constructed scanner */
  private EntityQueryTokenizerImpl _scanner;

  /** Token definition */
  public static final int EOF      = 0;
  public static final int ERROR    = 1;
  public static final int PLUS     = 2;
  public static final int MINUS    = 3;
  public static final int LPAR     = 4;
  public static final int RPAR     = 5;
  public static final int EQUAL    = 6;
  public static final int QUOTE    = 7;
  public static final int WILDCARD = 8;
  public static final int TERM     = 9;

  public static final String[] TOKEN_TYPES = {
    "<EOF>",
    "<ERROR>",
    "<PLUS>",
    "<MINUS>",
    "<LPAR>",
    "<RPAR>",
    "<EQUAL>",
    "<QUOTE>",
    "<WILDCARD>",
    "<TERM>"
  };

  /**
   * Creates a new instance of the {@link EntityQueryTokenizer}. Attaches the
   * <code>input</code> to a newly created JFlex scanner.
   */
  public EntityQueryTokenizer(final Reader input) {
    this.input = input;
    this._scanner = new EntityQueryTokenizerImpl(input);
    cTermAtt = this.addAttribute(CharTermAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
  }

  @Override
  public final boolean incrementToken()
  throws IOException {
    final int tokenType = _scanner.getNextToken();

    switch (tokenType) {

      case EntityQueryTokenizer.ERROR:
      case EntityQueryTokenizer.PLUS:
      case EntityQueryTokenizer.MINUS:
      case EntityQueryTokenizer.LPAR:
      case EntityQueryTokenizer.RPAR:
      case EntityQueryTokenizer.EQUAL:
      case EntityQueryTokenizer.QUOTE:
      case EntityQueryTokenizer.WILDCARD:
      case EntityQueryTokenizer.TERM:
        typeAtt.setType(EntityQueryTokenizerImpl.TOKEN_TYPES[tokenType]);
        cTermAtt.setEmpty();
        _scanner.getText(cTermAtt);
        break;

      case EntityQueryTokenizer.EOF:
      default:
        return false;
    }
    return true;
  }

  @Override
  public void reset(final Reader reader) throws IOException {
    super.close(); // close input stream
    input = reader;
    if (_scanner == null) {
      _scanner = new EntityQueryTokenizerImpl(input);
    }
    else {
      _scanner.yyreset(input);
    }
  }

  @Override
  public void close() throws IOException {
    super.close();
    if (_scanner != null) {
      _scanner.yyclose();
      _scanner = null;
    }
  }

}
