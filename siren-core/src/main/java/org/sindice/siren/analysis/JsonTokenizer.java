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

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.attributes.DatatypeAttribute;
import org.sindice.siren.analysis.attributes.JsonNodeAttributeImpl;
import org.sindice.siren.analysis.attributes.NodeAttribute;
import org.sindice.siren.util.JSONDatatype;
import org.sindice.siren.util.XSDDatatype;

import java.io.IOException;
import java.io.Reader;

/**
 * A tokenizer for data following the JSON syntax.
 * <p>
 * The tokenizer parses JSON data and generates a token for each field name
 * and each value. The tokenizer attaches a node label and a datatype to each
 * token.
 *
 * <p>
 *
 * Regarding datatype, the convention is the following:
 * <ul>
 *   <li> If a field name is parsed, the datatype
 *        {@link JSONDatatype#JSON_FIELD} is assigned;
 *   <li> If a value string is parsed, the datatype
 *        {@link XSDDatatype#XSD_STRING} is assigned;
 *   <li> If a boolean value is parsed, the datatype
 *        {@link XSDDatatype#XSD_BOOLEAN} is assigned;
 *   <li> If a numerical value is parsed, the datatype
 *        {@link XSDDatatype#XSD_LONG} is assigned;
 *   <li> If a numerical value with a fraction is parsed, the datatype
 *        {@link XSDDatatype#XSD_DOUBLE} is assigned;
 * </ul>
 */
public class JsonTokenizer extends Tokenizer {

  private final JsonTokenizerImpl scanner;

  /** Token Definition */
  public static final int         NULL      = 0;
  public static final int         TRUE      = 1;
  public static final int         FALSE     = 2;
  public static final int         NUMBER    = 3;
  public static final int         LITERAL   = 4;

  /** Datatype JSON schema: field for the datatype label */
  public static final String      DATATYPE_LABEL  = "_datatype_";
  /** Datatype JSON schema: field for the datatype value */
  public static final String      DATATYPE_VALUES = "_value_";

  public JsonTokenizer(final Reader input) {
    super(input);
    scanner = new JsonTokenizerImpl(input);
    this.initAttributes();
  }

  protected static String[] TOKEN_TYPES = getTokenTypes();

  public static String[] getTokenTypes() {
    if (TOKEN_TYPES == null) {
      TOKEN_TYPES = new String[5];
      TOKEN_TYPES[NULL] = "<NULL>";
      TOKEN_TYPES[TRUE] = "<TRUE>";
      TOKEN_TYPES[FALSE] = "<FALSE>";
      TOKEN_TYPES[NUMBER] = "<NUMBER>";
      TOKEN_TYPES[LITERAL] = "<LITERAL>";
    }
    return TOKEN_TYPES;
  }

  // the TupleTokenizer generates 6 attributes:
  // term, offset, positionIncrement, type, datatype, node
  private CharTermAttribute          termAtt;
  private OffsetAttribute            offsetAtt;
  private PositionIncrementAttribute posIncrAtt;
  private TypeAttribute              typeAtt;
  private DatatypeAttribute          dtypeAtt;
  private NodeAttribute              nodeAtt;

  private void initAttributes() {
    termAtt = this.addAttribute(CharTermAttribute.class);
    offsetAtt = this.addAttribute(OffsetAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
    dtypeAtt = this.addAttribute(DatatypeAttribute.class);
    if (!this.hasAttribute(NodeAttribute.class)) {
      this.addAttributeImpl(new JsonNodeAttributeImpl());
    }
    nodeAtt = this.addAttribute(NodeAttribute.class);
  }

  @Override
  public final boolean incrementToken() throws IOException {
    this.clearAttributes();
    posIncrAtt.setPositionIncrement(1);
    return this.nextToken();
  }

  private boolean nextToken() throws IOException {
    final int tokenType = scanner.getNextToken();

    switch (tokenType) {
      case FALSE:
        termAtt.append("false");
        this.updateToken(tokenType, scanner.getDatatypeURI(), scanner.yychar());
        break;

      case TRUE:
        termAtt.append("true");
        this.updateToken(tokenType, scanner.getDatatypeURI(), scanner.yychar());
        break;

      case NULL:
        termAtt.append("null");
        this.updateToken(tokenType, scanner.getDatatypeURI(), scanner.yychar());
        break;

      case NUMBER:
        scanner.getLiteralText(termAtt);
        this.updateToken(tokenType, scanner.getDatatypeURI(), scanner.yychar());
        break;

      case LITERAL:
        scanner.getLiteralText(termAtt);
        this.updateToken(tokenType, scanner.getDatatypeURI(), scanner.yychar() + 1);
        break;

      case JsonTokenizerImpl.YYEOF:
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
    nodeAtt.copyNode(scanner.getNodePath());
  }

  @Override
  public void reset() throws IOException {
    super.reset();
    if (input.markSupported()) {
      input.reset();
    }
    scanner.yyreset(input);
  }

  @Override
  public void close() throws IOException {
    scanner.yyclose();
  }

}
