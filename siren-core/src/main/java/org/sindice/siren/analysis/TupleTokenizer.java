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
 * @project siren
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.UAX29URLEmailTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;

/**
 * <p>
 * A grammar-based tokenizer constructed with JFlex. This should correctly
 * tokenize Tuples:
 * <ul>
 * <li>Splits a Tuple line into URI, Literal, Language, Datatype, Dot tokens.
 * <li>The value of a literal is tokenized with Lucene {@link UAX29URLEmailTokenizer}
 * by default.
 * </ul>
 */
public class TupleTokenizer
extends Tokenizer {

  /** A private instance of the JFlex-constructed scanner */
  private final TupleTokenizerImpl _scanner;

  /** The analyzer that will be used for the literals */
  private final Analyzer           _literalAnalyzer;
  private TokenStream              _literalTokenStream;

  private boolean                  _isTokenizingLiteral = false;

  private int                      _literalStartOffset  = 0;

  /** Structural node counters */
  private int                      _tid = 0;

  private int                      _cid = 0;

  /** Maximum length limitation */
  private int                      _maxLength           = 0;

  private int                      _length              = 0;

  /** Token definition */

  public static final int          BNODE                = 0;

  public static final int          URI                  = 1;

  public static final int          LITERAL              = 2;

  public static final int          LANGUAGE             = 3;

  public static final int          DATATYPE             = 4;

  public static final int          DOT                  = 5;

  protected static String[]        TOKEN_TYPES;

  public static String[] getTokenTypes() {
    if (TOKEN_TYPES == null) {
      TOKEN_TYPES = new String[6];
      TOKEN_TYPES[BNODE] = "<BNODE>";
      TOKEN_TYPES[URI] = "<URI>";
      TOKEN_TYPES[LITERAL] = "<LITERAL>";
      TOKEN_TYPES[LANGUAGE] = "<LANGUAGE>";
      TOKEN_TYPES[DATATYPE] = "<DATATYPE>";
      TOKEN_TYPES[DOT] = "<DOT>";
    }
    return TOKEN_TYPES;
  }

  /**
   * Creates a new instance of the {@link TupleTokenizer}. Attaches the
   * <code>input</code> to a newly created JFlex scanner. The
   * <code>literalAnalyzer</code> will be used for tokenizing RDF Literals. The
   * <code>maxLength</code> determines the maximum number of tokens allowed for
   * one triple.
   */
  public TupleTokenizer(final Reader input, final int maxLength, final Analyzer literalAnalyzer) {
    super();
    this.input = input;
    this._scanner = new TupleTokenizerImpl(input);
    this._maxLength = maxLength;
    this._literalAnalyzer = literalAnalyzer;
    this.initAttributes();
  }

  private void initAttributes() {
    termAtt = this.addAttribute(CharTermAttribute.class);
    offsetAtt = this.addAttribute(OffsetAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
    tupleAtt = this.addAttribute(TupleAttribute.class);
    cellAtt = this.addAttribute(CellAttribute.class);
  }

  // the TupleTokenizer generates 6 attributes:
  // term, offset, positionIncrement, type, tuple and cell
  private CharTermAttribute termAtt;
  private OffsetAttribute offsetAtt;
  private PositionIncrementAttribute posIncrAtt;
  private TypeAttribute typeAtt;
  private TupleAttribute tupleAtt;
  private CellAttribute cellAtt;

  @Override
  public final boolean incrementToken()
  throws IOException {
    this.clearAttributes();

    while (_length < _maxLength) {
      posIncrAtt.setPositionIncrement(1);
      if (this._isTokenizingLiteral) {
        // If no more token in the literal, continue to the next triple token
        if (!this.nextLiteralToken()) {
          continue;
        }
        else {
          return true;
        }
      }
      return this.nextTupleToken();
    }
    return false;
  }

  private boolean nextTupleToken()
  throws IOException {
    final int tokenType = _scanner.getNextToken();

    switch (tokenType) {
      case TupleTokenizer.BNODE:
        _scanner.getBNodeText(termAtt);
        this.updateToken(tokenType, _scanner.yychar() + 2);
        _length++;
        // Increment tuple element node ID counter
        _cid++;
        break;

      case TupleTokenizer.URI:
        _scanner.getURIText(termAtt);
        this.updateToken(tokenType, _scanner.yychar() + 1);
        _length++;
        // Increment tuple element node ID counter
        _cid++;
        break;

      case TupleTokenizer.LITERAL:
        this.initLiteralTokenizer(_scanner.getLiteralText(), _scanner.yychar());
        return this.incrementToken();

      case LANGUAGE:
        _scanner.getLanguageText(termAtt);
        this.updateToken(tokenType, _scanner.yychar() + 1);
        break;

      case DATATYPE:
        _scanner.getDatatypeText(termAtt);
        this.updateToken(tokenType, _scanner.yychar() + 3);
        break;

      case DOT:
        _scanner.getText(termAtt);
        this.updateToken(tokenType, _scanner.yychar());
        // Increment tuple node ID counter, reset tuple element node ID counter
        _tid++; _cid = 0;
        break;

      case TupleTokenizerImpl.YYEOF:
        return false;

      default:
        return false;
    }
    return true;
  }

  // the StandardTokenizer generates three attributes:
  // term, offset and type
  private CharTermAttribute litTermAtt;
  private OffsetAttribute litOffsetAtt;
  private TypeAttribute litTypeAtt;

  private boolean nextLiteralToken()
  throws IOException {
    // If there is no more tokens in the literal
    if (!this._literalTokenStream.incrementToken()) {
      this._isTokenizingLiteral = false;
      this._literalStartOffset = 0;
      // Increment tuple element node ID counter
      this._cid++;
      return false;
    }

    final int len = litTermAtt.length();
    termAtt.copyBuffer(litTermAtt.buffer(), 0, len);

    // Update Offset information of the literal token
    offsetAtt.setOffset(
      this.correctOffset(litOffsetAtt.startOffset() + this._literalStartOffset),
      this.correctOffset(litOffsetAtt.endOffset() + len));

    typeAtt.setType(litTypeAtt.type());

    // Update structural information of the literal token
    tupleAtt.setTuple(_tid);
    cellAtt.setCell(_cid);

    _length++;
    return true;
  }

  private void initLiteralTokenizer(final char[] text, final int startOffset)
  throws IOException {
    this.initLiteralTokenizer(new CharArrayReader(text));
    this._isTokenizingLiteral = true;
    // Save the start offset of the literal token
    this._literalStartOffset = startOffset - text.length;
  }

  private void initLiteralTokenizer(final Reader reader)
  throws IOException {
    _literalTokenStream = this._literalAnalyzer.reusableTokenStream("", reader);
    // get the associated attribute of the literal tokenizers
    litTermAtt = this._literalTokenStream.getAttribute(CharTermAttribute.class);
    litOffsetAtt = this._literalTokenStream.getAttribute(OffsetAttribute.class);
    litTypeAtt = this._literalTokenStream.addAttribute(TypeAttribute.class);
  }

  private void updateToken(final int tokenType, final int startOffset) {
    // Update offset and type
    offsetAtt.setOffset(this.correctOffset(startOffset),
      this.correctOffset(startOffset + termAtt.length()));
    // update token type
    typeAtt.setType(TOKEN_TYPES[tokenType]);
    // Update structural information
    tupleAtt.setTuple(_tid);
    cellAtt.setCell(_cid);
  }

  /*
   * (non-Javadoc)
   * @see org.apache.lucene.analysis.TokenStream#reset()
   */
  @Override
  public void reset() throws IOException {
    super.reset();
    if (input.markSupported()) {
      input.reset();
    }
    _scanner.yyreset(input);
    _length = 0;
    _tid = _cid = 0;
  }

  @Override
  public void reset(final Reader reader) throws IOException {
    if (this._literalTokenStream != null) {
      this.resetLiteralTokenizer();
    }
    input = reader;
    this.reset();
  }

  private void resetLiteralTokenizer() throws IOException {
    this._literalTokenStream.reset();
    this._isTokenizingLiteral = false;
    this._literalStartOffset = 0;
  }

  @Override
  public void close() throws IOException {
    _scanner.yyclose();
    // Do not close the literal analyzer, but the literal token stream. This
    // is safe
    if (this._literalTokenStream != null) {
      _literalTokenStream.close();
    }
  }

}
