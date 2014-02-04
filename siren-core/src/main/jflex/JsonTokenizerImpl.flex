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

import static org.sindice.siren.analysis.JsonTokenizer.*;

import java.util.Stack;
import java.util.Arrays;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.IntsRef;
import org.sindice.siren.util.ArrayUtils;
import org.sindice.siren.util.XSDDatatype;
import org.sindice.siren.util.JSONDatatype;

/**
 * Json document scanner
 *
 */
%%
%yylexthrow java.lang.IllegalStateException
%public
%class JsonTokenizerImpl


/**
 * Both options cause the generated scanner to use the full 16 bit 
 * Unicode input character set (character codes 0-65535).
 */
%unicode

/**
 * The current character number with the variable yychar.
 */
%char

/**
 * Both cause the scanning method to be declared as of Java type int. 
 * Actions in the specification can then return int values as tokens
 */
%integer

/** 
 * causes JFlex to compress the generated DFA table and to store it 
 * in one or more string literals.
 */
%pack

%function getNextToken

%{

  /** Datatype representing xsd:string */
  private static final char[]   XSD_STRING   = XSDDatatype.XSD_STRING.toCharArray();

  /** Datatype representing json:field */
  private static final char[]   JSON_FIELD   = JSONDatatype.JSON_FIELD.toCharArray();

  /**
   * Datatype representing xsd:double
   */
  private static final char[]   XSD_DOUBLE   = XSDDatatype.XSD_DOUBLE.toCharArray();

  /**
   * Datatype representing xsd:long
   */
  private static final char[]   XSD_LONG     = XSDDatatype.XSD_LONG.toCharArray();

  /** Datatype representing xsd:boolean */
  private static final char[]   XSD_BOOLEAN  = XSDDatatype.XSD_BOOLEAN.toCharArray();

  private char[]                datatype;

  /** Buffer containing literal or number value */
  private final StringBuffer    buffer       = new StringBuffer();

  /** The size of the path buffer */
  private final static int      BUFFER_SIZE  = 1024;

  /** The path to a node */
  private final IntsRef         nodePath     = new IntsRef(BUFFER_SIZE);

  /** Stack of lexical states */
  private final Stack<Integer>  states       = new Stack();

  /**
   * Indicates if a leaf node, i.e., a literal, a number, null, or a boolean,
   * was encountered, in which case it needs to be closed, either in the COMMA
   * state, or in the closing curly bracket.
   */
  private boolean               openLeafNode = false;

  /**
   * Indicates how many nested objects there are.
   * A nested object implies a blank node.
   */
  private int                   nestedObjects = 0;

  /** Datatypes */
  
  /** Buffer containing the datatype label */
  private final StringBuffer    dtLabel            = new StringBuffer();

  private final static int      DATATYPE_OBJ_OFF   = 0;
  private final static int      DATATYPE_OBJ_ON    = 1;
  private final static int      DATATYPE_OBJ_JUNK  = 2;
  private final static int      DATATYPE_OBJ_ERROR = 3;
  private final static int      DATATYPE_OBJ_LABEL = 4;
  private final static int      DATATYPE_OBJ_VALUE = 8;
  private final static int      DATATYPE_OBJ_OK    = 13;
  private int                   datatypeObject     = DATATYPE_OBJ_OFF;

  /**
   * Return the datatype URI.
   */
  public final char[] getDatatypeURI() {
    return datatype;
  }

  public final int yychar() {
    return yychar;
  }

  /**
   * Fills Lucene TermAttribute with the current string buffer.
   */
  public final void getLiteralText(CharTermAttribute t) {
    char[] chars = new char[buffer.length()];
    buffer.getChars(0, buffer.length(), chars, 0);
    t.copyBuffer(chars, 0, chars.length);
  }

  /**
   * Returns the node path of the token
   */
  public final IntsRef getNodePath() {
    return nodePath;
  }

  /**
   * Initialise inner variables
   */
  private void reset() {
    states.clear();
    Arrays.fill(nodePath.ints, -1);
    nodePath.offset = 0;
    nodePath.length = 1;
    openLeafNode = false;
    datatype = null;
    nestedObjects = 0;
    datatypeObject = DATATYPE_OBJ_OFF;
  }

  /**
   * Add an object to the current node path
   */
  private void incrNodeObjectPath() {
    ArrayUtils.growAndCopy(nodePath, nodePath.length + 1);
    nodePath.length++;
    // Initialise node
    setLastNode(-1);
  }

  private void initDatatypeObject() {
    // Initialise the datatype data, this is possibly a Datatype object
    datatype = null;
    datatypeObject = DATATYPE_OBJ_OFF;
    buffer.setLength(0);
  }

  /**
   * Decrement the tree level of 1.
   */
  private void decrNodeObjectPath() {
    nodePath.length--;
  }

  /** Update the path of the current values of the current object node */
  private void setLastNode(int val) {
    nodePath.ints[nodePath.length - 1] = val;
  }

  /** Update the path of the current values of the current object node */
  private void addToLastNode(int val) {
    nodePath.ints[nodePath.length - 1] += val;
  }

  /**
   * Remove the leading ':' from the matched number,
   * and return the {@link NUMBER} token type.
   */
  private int processNumber() {
    final String text = yytext();
    buffer.setLength(0);
    buffer.append(text.substring(text.indexOf(':') + 1).trim());
    return NUMBER;
  }

  /**
   * Helper method to print an error while scanning a JSON
   * document with line and column information.
   */
  private String errorMessage(String msg) {
    return "Error parsing JSON document at [line=" + yyline + ", column=" + yycolumn + "]: " + msg;
  }

  /**
   * Throws an {@link IllegalStateException} if the scanned value has no field
   */
  private void checkMissingField() {
    if (datatype != JSON_FIELD) {
      throw new IllegalStateException(errorMessage("Missing a field name, got " + yytext()));
    }
  }

  /**
   * Close the JSON object:
   * - decrement the tree level of 1;
   * - decrement the tree level of 1 one more time if the last value was a leaf;
   * - decrement the tree level of 1 one more time if the object was nested into another one; and
   * - remove sOBJECT from the stack of states.
   */
  private void closeObject() {
    decrNodeObjectPath();
    if (openLeafNode) { // unclosed entry to a leaf node
      decrNodeObjectPath();
      openLeafNode = false;
    }
    // this curly bracket closes a nested object
    // this requires an additional call to decrNodeObjectPath()
    // since nested objects have implicitly a blank node.
    if (nestedObjects > 0) {
      nestedObjects--;
      decrNodeObjectPath();
    }
    final int state = states.pop();
    if (state != sOBJECT) {
      throw new IllegalStateException(errorMessage("Expected '}', got " + yychar()));
    }
  }

  /**
   * Closes the JSON object and further checks if the node path should be updated.
   * It returns a {@link #LITERAL}, which is the value associated to the field "_value_"
   * with the datatype equal to the value associated to the field "_datatype_".
   */
  private int closeDatatypeObject() {
    closeObject();
    // when the states stack is empty, I am the root of the JSON tree
    if (states.empty()) {
      throw new IllegalStateException(errorMessage("A datatype object at the" +
        " root of the JSON document is not possible."));
    }
    if (states.peek() == sOBJECT) {
      incrNodeObjectPath();
      setLastNode(0);
    }
    return LITERAL;
  }

%}

TRUE        = "true"
FALSE       = "false"
LONG        = -?[0-9][0-9]*({EXPONENT})?
DOUBLE      = -?[0-9][0-9]*\.[0-9]+({EXPONENT})?
EXPONENT    = [e|E][+|-]?[0-9]+
NULL        = "null"
ENDOFLINE   = \r|\n|\r\n
WHITESPACE  = {ENDOFLINE} | [ \t\f]

%xstate sSTRING, sOBJECT, sARRAY
%xstate sDATATYPE_STRING_VALUE, sDATATYPE_LABEL

%%

<YYINITIAL> {
  "{"                            { reset();
                                   states.push(sOBJECT);
                                   yybegin(sOBJECT);
                                 }

  <sOBJECT, sARRAY, sDATATYPE_LABEL, sDATATYPE_STRING_VALUE>
  {WHITESPACE}                   { /* ignore white space. */ }
}

<sOBJECT> {
  "}"                            { // A datatype object is already closed before,
                                   // in the state sDATATYPE_LABEL or sDATATYPE_STRING_VALUE
                                   if ((datatypeObject & DATATYPE_OBJ_ON) == DATATYPE_OBJ_ON && datatypeObject != DATATYPE_OBJ_OK) {
                                     if ((datatypeObject & DATATYPE_OBJ_LABEL) == DATATYPE_OBJ_LABEL) {
                                       throw new IllegalStateException(errorMessage("Uncomplete datatype object, missing _value_ field."));
                                     } else {
                                       throw new IllegalStateException(errorMessage("Uncomplete datatype object, missing _datatype_ field."));
                                     }
                                   } else if ((datatypeObject & DATATYPE_OBJ_ON) == DATATYPE_OBJ_OFF) {
                                     closeObject();
                                   } else if (datatypeObject == DATATYPE_OBJ_OK && states.peek() == sOBJECT) {
                                     // Decrement the node created in the state
                                     // sDATATYPE_LABEL or sDATATYPE_STRING_VALUE.
                                     // This only happens when in state sOBJECT, since it explicitly
                                     // adds a blank node (i.e., nestedObjects variable).
                                     // In the state sARRAY, the blank node is implicit.
                                     decrNodeObjectPath();
                                   }
                                   // when the states stack is empty, I am the root of the JSON tree
                                   yybegin(states.empty() ? YYINITIAL : states.peek());
                                   // Switch off the datatype object
                                   datatypeObject = DATATYPE_OBJ_OFF;
                                 }
  ":"{WHITESPACE}*{NULL}         { checkMissingField();
                                   openLeafNode = true;
                                   incrNodeObjectPath();
                                   datatype = XSD_STRING;
                                   setLastNode(0);
                                   return NULL; }
  ":"{WHITESPACE}*{FALSE}        { checkMissingField();
                                   openLeafNode = true;
                                   incrNodeObjectPath();
                                   datatype = XSD_BOOLEAN;
                                   setLastNode(0);
                                   return FALSE; }
  ":"{WHITESPACE}*{TRUE}         { checkMissingField();
                                   openLeafNode = true;
                                   incrNodeObjectPath();
                                   datatype = XSD_BOOLEAN;
                                   setLastNode(0);
                                   return TRUE; }
  ":"{WHITESPACE}*{LONG}         { checkMissingField();
                                   openLeafNode = true;
                                   incrNodeObjectPath();
                                   datatype = XSD_LONG;
                                   setLastNode(0);
                                   return processNumber(); }
  ":"{WHITESPACE}*{DOUBLE}       { checkMissingField();
                                   openLeafNode = true;
                                   incrNodeObjectPath();
                                   datatype = XSD_DOUBLE;
                                   setLastNode(0);
                                   return processNumber(); }
  ":"{WHITESPACE}*"["            { checkMissingField();
                                   incrNodeObjectPath();
                                   yybegin(sARRAY);
                                   states.push(sARRAY);
                                 }
  ":"{WHITESPACE}*"{"            { checkMissingField();
                                   initDatatypeObject();
                                   // Two incrementations, because the object introduce a "blank" node
                                   nestedObjects++;
                                   incrNodeObjectPath();
                                   setLastNode(0);
                                   incrNodeObjectPath();
                                   states.push(sOBJECT);
                                   yybegin(sOBJECT);
                                 }
  ":"{WHITESPACE}*\"             { checkMissingField();
                                   openLeafNode = true;
                                   incrNodeObjectPath();
                                   datatype = XSD_STRING;
                                   setLastNode(0);
                                   buffer.setLength(0);
                                   yybegin(sSTRING);
                                 }
  ","                            { if (openLeafNode) {
                                     openLeafNode = false;
                                     decrNodeObjectPath();
                                   }
                                 }
  \"                             { datatypeObject |= DATATYPE_OBJ_JUNK;
                                   if ((datatypeObject & DATATYPE_OBJ_ERROR) == DATATYPE_OBJ_ERROR) {
                                     throw new IllegalStateException(errorMessage("Wrong Datatype schema. Got unexpected text: " + yytext()));
                                   }
                                   datatype = JSON_FIELD;
                                   addToLastNode(1);
                                   buffer.setLength(0);
                                   yybegin(sSTRING);
                                 }
  // Datatype label
  \""_datatype_"\"{WHITESPACE}*":"{WHITESPACE}*\"
                                 { 
                                   datatypeObject |= DATATYPE_OBJ_ON;
                                   if ((datatypeObject & DATATYPE_OBJ_LABEL) == DATATYPE_OBJ_LABEL) {
                                     throw new IllegalStateException(errorMessage("Wrong Datatype schema. The field _datatype_ appears several times."));
                                   }
                                   datatypeObject |= DATATYPE_OBJ_LABEL;
                                   if ((datatypeObject & DATATYPE_OBJ_ERROR) == DATATYPE_OBJ_ERROR) {
                                     throw new IllegalStateException(errorMessage("Wrong Datatype schema. Got unexpected elements in the datatype object."));
                                   }
                                   dtLabel.setLength(0);
                                   yybegin(sDATATYPE_LABEL);
                                 }
  // Datatype value: a string
  \""_value_"\"{WHITESPACE}*":"{WHITESPACE}*\"
                                 { 
                                   datatypeObject |= DATATYPE_OBJ_ON;
                                   if ((datatypeObject & DATATYPE_OBJ_VALUE) == DATATYPE_OBJ_VALUE) {
                                     throw new IllegalStateException(errorMessage("Wrong Datatype schema. The field _value_ appears several times."));
                                   }
                                   datatypeObject |= DATATYPE_OBJ_VALUE;
                                   if ((datatypeObject & DATATYPE_OBJ_ERROR) == DATATYPE_OBJ_ERROR) {
                                     throw new IllegalStateException(errorMessage("Wrong Datatype schema. Got unexpected elements in the datatype object."));
                                   }
                                   buffer.setLength(0);
                                   yybegin(sDATATYPE_STRING_VALUE);
                                 }
  // Error state
  .                              { throw new IllegalStateException(errorMessage("Found bad character while in OBJECT state")); }
}

<sARRAY> {
  "]"                            { decrNodeObjectPath();
                                   int state = states.pop();
                                   if (state != sARRAY) {
                                     throw new IllegalStateException(errorMessage("Expected ']', got " + yychar()));
                                   }
                                   yybegin(states.peek());
                                 }
  "{"                            { initDatatypeObject();
                                   addToLastNode(1);
                                   incrNodeObjectPath();
                                   states.push(sOBJECT);
                                   yybegin(sOBJECT);
                                 }
  "["                            { addToLastNode(1);
                                   incrNodeObjectPath();
                                   states.push(sARRAY);
                                   yybegin(sARRAY);
                                 }
  {NULL}                         { datatype = XSD_STRING; addToLastNode(1); return NULL; }
  {TRUE}                         { datatype = XSD_BOOLEAN; addToLastNode(1); return TRUE; }
  {FALSE}                        { datatype = XSD_BOOLEAN; addToLastNode(1); return FALSE; }
  {LONG}                         { datatype = XSD_LONG;
                                   addToLastNode(1);
                                   return processNumber(); }
  {DOUBLE}                       { datatype = XSD_DOUBLE;
                                   addToLastNode(1);
                                   return processNumber(); }
  ","                            { /* nothing */ }
  \"                             { datatype = XSD_STRING;
                                   addToLastNode(1);
                                   buffer.setLength(0);
                                   yybegin(sSTRING);
                                 }
  // Error state
  .                              { throw new IllegalStateException(errorMessage("Found bad character while in ARRAY state")); }
}

<sSTRING> {
  \"                             { yybegin(states.peek()); return LITERAL; }
  [^\"\\]+                       { buffer.append(yytext()); }
  \\\"                           { buffer.append('\"'); }
  \\.                            { buffer.append(yytext()); }
  \\u[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F] { buffer.append(Character.toChars(Integer.parseInt(new String(zzBufferL, zzStartRead+2, zzMarkedPos - zzStartRead - 2 ), 16))); }
}

<sDATATYPE_LABEL> {
  \"                             { yybegin(sOBJECT);
                                   datatype = new char[dtLabel.length()];
                                   dtLabel.getChars(0, dtLabel.length(), datatype, 0);
                                   // the datatype value is here already
                                   if (datatypeObject == DATATYPE_OBJ_OK) {
                                     return closeDatatypeObject();
                                   }
                                 }
  [^\"\\]+                       { dtLabel.append(yytext()); }
  \\\"                           { dtLabel.append('\"'); }
  \\.                            { dtLabel.append(yytext()); }
  \\u[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F] { dtLabel.append(Character.toChars(Integer.parseInt(new String(zzBufferL, zzStartRead+2, zzMarkedPos - zzStartRead - 2 ), 16))); }
}

<sDATATYPE_STRING_VALUE> {
  \"                             { yybegin(sOBJECT);
                                   // the datatype label is here already
                                   if (datatypeObject == DATATYPE_OBJ_OK) {
                                     return closeDatatypeObject();
                                   }
                                 }
  [^\"\\]+                       { buffer.append(yytext()); }
  \\\"                           { buffer.append('\"'); }
  \\.                            { buffer.append(yytext()); }
  \\u[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F] { buffer.append(Character.toChars(Integer.parseInt(new String(zzBufferL, zzStartRead+2, zzMarkedPos - zzStartRead - 2 ), 16))); }
}

/* Check that the states are empty */
<<EOF>>                          { if (!states.empty()) {
                                     throw new IllegalStateException(errorMessage("Check that all arrays/objects/strings are closed"));
                                   }
                                   return YYEOF;
                                 }

// catch all: ignore them
.                                { /* ignore */ }
