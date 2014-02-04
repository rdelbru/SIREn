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

package org.sindice.siren.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.attributes.DatatypeAttribute;
import org.sindice.siren.analysis.attributes.NodeAttribute;
import org.sindice.siren.analysis.attributes.TupleNodeAttributeImpl;

/**
 * A grammar-based tokenizer constructed with JFlex for N-Tuples. Splits a
 * N-Tuple into BNode, URI, Literal and Dot tokens.
 *
 * @deprecated Use {@link JsonTokenizer} instead
 */
@Deprecated
public class TupleTokenizer extends Tokenizer {

  /** A private instance of the JFlex-constructed scanner */
  private final TupleTokenizerImpl _scanner;

  /** Structural node counters */
  private int                      _tid = 0;

  private int                      _cid = 0;

  /** Token definition */

  public static final int          BNODE                = 0;

  public static final int          URI                  = 1;

  public static final int          LITERAL              = 2;

  public static final int          DOT                  = 3;

  protected static String[]        TOKEN_TYPES;

  public static String[] getTokenTypes() {
    if (TOKEN_TYPES == null) {
      TOKEN_TYPES = new String[4];
      TOKEN_TYPES[BNODE] = "<BNODE>";
      TOKEN_TYPES[URI] = "<URI>";
      TOKEN_TYPES[LITERAL] = "<LITERAL>";
      TOKEN_TYPES[DOT] = "<DOT>";
    }
    return TOKEN_TYPES;
  }

  /**
   * Creates a new instance of the {@link TupleTokenizer}. Attaches the
   * <code>input</code> to a newly created JFlex scanner.
   */
  public TupleTokenizer(final Reader input) {
    super(input);
    this._scanner = new TupleTokenizerImpl(input);
    this.initAttributes();
  }

  // the TupleTokenizer generates 6 attributes:
  // term, offset, positionIncrement, type, datatype, node
  private CharTermAttribute termAtt;
  private OffsetAttribute offsetAtt;
  private PositionIncrementAttribute posIncrAtt;
  private TypeAttribute typeAtt;
  private DatatypeAttribute dtypeAtt;
  private NodeAttribute nodeAtt;

  private void initAttributes() {
    termAtt = this.addAttribute(CharTermAttribute.class);
    offsetAtt = this.addAttribute(OffsetAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
    dtypeAtt = this.addAttribute(DatatypeAttribute.class);
    if (!this.hasAttribute(NodeAttribute.class)) {
      this.addAttributeImpl(new TupleNodeAttributeImpl());
    }
    nodeAtt = this.addAttribute(NodeAttribute.class);
  }

  @Override
  public final boolean incrementToken() throws IOException {
    this.clearAttributes();
    posIncrAtt.setPositionIncrement(1);
    return this.nextTupleToken();
  }

  private boolean nextTupleToken() throws IOException {
    final int tokenType = _scanner.getNextToken();

    switch (tokenType) {
      case TupleTokenizer.BNODE:
        _scanner.getBNodeText(termAtt);
        this.updateToken(tokenType, null, _scanner.yychar() + 2);
        // Increment tuple cell ID counter
        _cid++;
        break;

      case TupleTokenizer.URI:
        _scanner.getURIText(termAtt);
        this.updateToken(tokenType, _scanner.getDatatypeURI(), _scanner.yychar() + 1);
        // Increment tuple cell ID counter
        _cid++;
        break;

      case TupleTokenizer.LITERAL:
        _scanner.getLiteralText(termAtt);
        this.updateToken(tokenType, _scanner.getDatatypeURI(), _scanner.yychar() + 1);
        // Increment tuple cell ID counter
        _cid++;
        break;

      case DOT:
        _scanner.getText(termAtt);
        this.updateToken(tokenType, null, _scanner.yychar());
        // Increment tuple ID counter, reset tuple cell ID counter
        _tid++; _cid = 0;
        break;

      case TupleTokenizerImpl.YYEOF:
        return false;

      default:
        return false;
    }
    return true;
  }

  /**
   * Update type, datatype, offset, tuple id and cell id of the token
   *
   * @param tokenType The type of the generated token
   * @param datatypeURI The datatype of the generated token
   * @param startOffset The starting offset of the token
   */
  private void updateToken(final int tokenType, final char[] datatypeURI, final int startOffset) {
    // Update offset
    offsetAtt.setOffset(this.correctOffset(startOffset),
      this.correctOffset(startOffset + termAtt.length()));
    // update token type
    typeAtt.setType(TOKEN_TYPES[tokenType]);
    // update datatype
    dtypeAtt.setDatatypeURI(datatypeURI);
    // Update structural information
    nodeAtt.append(_tid);
    nodeAtt.append(_cid);
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
    _tid = _cid = 0;
  }

  @Override
  public void close() throws IOException {
    _scanner.yyclose();
  }

}
