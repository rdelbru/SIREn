/**
 * Copyright 2010, Renaud Delbru
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
 * @author Renaud Delbru [ 8 mars 2008 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public final class NTripleQueryTokenizer extends Tokenizer {

  private final CharTermAttribute cTermAtt;
  private final TypeAttribute typeAtt;

  /** A private instance of the JFlex-constructed scanner */
  private final NTripleQueryTokenizerImpl _scanner;

  /** Token definition */
  public static final int EOF = 0;
  public static final int ERROR = 1;
  public static final int AND = 2;
  public static final int OR = 3;
  public static final int MINUS = 4;
  public static final int LPAREN = 5;
  public static final int RPAREN = 6;
  public static final int WILDCARD = 7;
  public static final int URIPATTERN = 8;
  public static final int LITERAL = 9;
  public static final int LPATTERN = 10;

  /**
   * Creates a new instance of the {@link NTripleQueryTokenizer}. Attaches the
   * <code>input</code> to a newly created JFlex scanner.
   */
  public NTripleQueryTokenizer(final Reader input) {
    this.input = input;
    this._scanner = new NTripleQueryTokenizerImpl(input);
    cTermAtt = this.addAttribute(CharTermAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
  }

  @Override
  public final boolean incrementToken()
  throws IOException {
    final int tokenType = _scanner.getNextToken();

    switch (tokenType) {

      case NTripleQueryTokenizer.ERROR:
      case NTripleQueryTokenizer.AND:
      case NTripleQueryTokenizer.OR:
      case NTripleQueryTokenizer.MINUS:
      case NTripleQueryTokenizer.LPAREN:
      case NTripleQueryTokenizer.RPAREN:
      case NTripleQueryTokenizer.WILDCARD:
        typeAtt.setType(NTripleQueryTokenizerImpl.TOKEN_TYPES[tokenType]);
        cTermAtt.setEmpty();
        cTermAtt.append(NTripleQueryTokenizerImpl.TOKEN_TYPES[tokenType]);
        break;

      case NTripleQueryTokenizer.URIPATTERN:
        typeAtt.setType(NTripleQueryTokenizerImpl.TOKEN_TYPES[tokenType]);
        cTermAtt.setEmpty();
        cTermAtt.append(_scanner.getURIText());
        break;

      case NTripleQueryTokenizer.LITERAL:
        typeAtt.setType(NTripleQueryTokenizerImpl.TOKEN_TYPES[tokenType]);
        cTermAtt.setEmpty();
        cTermAtt.append(_scanner.getLiteralText());
        break;

      case NTripleQueryTokenizer.LPATTERN:
        typeAtt.setType(NTripleQueryTokenizerImpl.TOKEN_TYPES[tokenType]);
        cTermAtt.setEmpty();
        cTermAtt.append(_scanner.getLiteralText());
        break;

      case NTripleQueryTokenizer.EOF:
      default:
        return false;
    }
    return true;
  }

  /**
   * Reset the tokenizer and the underlying flex scanner with a new reader.
   * <br>
   * This method is called by Analyzers in its resuableTokenStream method.
   */
  @Override
  public void reset(final Reader input) throws IOException {
    super.reset(input);
    _scanner.yyreset(input);
  }

}
