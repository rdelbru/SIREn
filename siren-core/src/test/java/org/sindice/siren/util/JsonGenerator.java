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
package org.sindice.siren.util;

import static org.sindice.siren.analysis.JsonTokenizer.FALSE;
import static org.sindice.siren.analysis.JsonTokenizer.LITERAL;
import static org.sindice.siren.analysis.JsonTokenizer.NULL;
import static org.sindice.siren.analysis.JsonTokenizer.NUMBER;
import static org.sindice.siren.analysis.JsonTokenizer.TRUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import org.apache.lucene.util.IntsRef;
import org.sindice.siren.analysis.JsonTokenizer;

public class JsonGenerator {

  public final Random             rand;
  public int                      valueType;

  // used for generating a random json document
  private final StringBuilder     sb          = new StringBuilder();
  private final Stack<Integer>    states      = new Stack<Integer>();
  private static final int        ARRAY       = 0;
  private static final int        OBJECT_ATT  = 1;
  private static final int        OBJECT_VAL  = 2;
  public final ArrayList<String>  images      = new ArrayList<String>();
  public final ArrayList<IntsRef> nodes       = new ArrayList<IntsRef>();
  public final ArrayList<Integer> incr        = new ArrayList<Integer>();
  public final ArrayList<String>  types       = new ArrayList<String>();
  public final ArrayList<String>  datatypes   = new ArrayList<String>();
  private final IntsRef           curNodePath = new IntsRef(1024);
  public boolean                  shouldFail  = false;
  private final int               MAX_DEPTH   = 50;
  private int                     nestedObjs  = 0;

  public JsonGenerator(final Random rand) {
    this.rand = rand;
  }

  /**
   * Create a random Json document with random values
   */
  public String getRandomJson(int nbNodes) {
    // init
    sb.setLength(0);
    sb.append("{");
    states.clear();
    states.add(OBJECT_ATT);
    images.clear();
    nodes.clear();
    incr.clear();
    datatypes.clear();
    types.clear();
    curNodePath.length = 1;
    curNodePath.offset = 0;
    Arrays.fill(curNodePath.ints, -1);
    shouldFail = false;
    nestedObjs = 0;

    // <= so that when nbNodes == 1, the json is still valid
    /*
     * the generated json might be uncomplete, if states is not empty, and
     * the maximum number of nodes has been reached.
     */
    for (final int i = 0; i <= nbNodes && !states.empty(); nbNodes++) {
      sb.append(this.getWhitespace()).append(this.getNextNode()).append(this.getWhitespace());
    }
    shouldFail = shouldFail ? true : !states.empty();
    return sb.toString();
  }

  /**
   * Return the next element of the json document
   */
  private String getNextNode() {
    final int popState;

    switch (states.peek()) {
      case ARRAY:
        switch (rand.nextInt(9)) {
          case 0: // String case
            final String val = "stepha" + this.getWhitespace() + "n" + this.getWhitespace() + "e";
            this.addToLastNode(1);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            images.add(val);
            types.add(JsonTokenizer.getTokenTypes()[LITERAL]);
            incr.add(1);
            datatypes.add(XSDDatatype.XSD_STRING);
            return "\"" + val + "\"" + this.getWhitespace() + ",";
          case 1: // DOUBLE case
            this.addToLastNode(1);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            images.add("34.560e-9");
            types.add(JsonTokenizer.getTokenTypes()[NUMBER]);
            incr.add(1);
            datatypes.add(XSDDatatype.XSD_DOUBLE);
            return "34.560e-9" + this.getWhitespace() + ",";
          case 2: // LONG case
            this.addToLastNode(1);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            images.add("34560e-9");
            types.add(JsonTokenizer.getTokenTypes()[NUMBER]);
            incr.add(1);
            datatypes.add(XSDDatatype.XSD_LONG);
            return "34560e-9" + this.getWhitespace() + ",";
          case 3: // true case
            this.addToLastNode(1);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            images.add("true");
            types.add(JsonTokenizer.getTokenTypes()[TRUE]);
            incr.add(1);
            datatypes.add(XSDDatatype.XSD_BOOLEAN);
            return "true" + this.getWhitespace() + ",";
          case 4: // false case
            this.addToLastNode(1);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            images.add("false");
            types.add(JsonTokenizer.getTokenTypes()[FALSE]);
            incr.add(1);
            datatypes.add(XSDDatatype.XSD_BOOLEAN);
            return "false" + this.getWhitespace() + ",";
          case 5: // null case
            this.addToLastNode(1);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            images.add("null");
            types.add(JsonTokenizer.getTokenTypes()[NULL]);
            incr.add(1);
            datatypes.add(XSDDatatype.XSD_STRING);
            return "null" + this.getWhitespace() + ",";
          case 6: // nested array case
            if (states.size() <= MAX_DEPTH) {
              this.addToLastNode(1);
              this.incrNodeObjectPath();
              states.add(ARRAY);
              return "[";
            }
            return "";
          case 7: // nested object case
            if (states.size() <= MAX_DEPTH) {
              this.addToLastNode(1);
              this.incrNodeObjectPath();
              states.add(OBJECT_ATT);
              return "{";
            }
            return "";
          case 8: // closing array case
            this.decrNodeObjectPath();
            popState = states.pop();
            if (popState != ARRAY) {
              shouldFail = true;
            }
            // Remove previous comma, this is not allowed
            final int comma = sb.lastIndexOf(",");
            if (comma != -1 && sb.substring(comma + 1).matches("\\s*")) {
              sb.deleteCharAt(comma);
            }
            return "],";
        }
      case OBJECT_ATT:
        switch (rand.nextInt(3)) {
          case 0:
            types.add(JsonTokenizer.getTokenTypes()[LITERAL]);
            images.add("ste ph ane");
            incr.add(1);
            this.addToLastNode(1);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            datatypes.add(JSONDatatype.JSON_FIELD);

            states.push(OBJECT_VAL);
            return "\"ste ph ane\"" + this.getWhitespace() + ":";
          case 1:
            if (nestedObjs > 0) {
              this.decrNodeObjectPath();
              nestedObjs--;
            }
            this.decrNodeObjectPath();
            popState = states.pop();
            if (popState != OBJECT_ATT) {
              shouldFail = true;
            }
            return states.empty() ? "}" : "},";
          case 2:
            final String field;
            if (states.isEmpty()) {
              // datatype object at the root are not possible
              shouldFail = true;
              field = "";
            } else if (states.peek() == OBJECT_ATT) {
              // field name
              this.addToLastNode(1);
              field = "\"field\":";
              types.add(JsonTokenizer.getTokenTypes()[LITERAL]);
              images.add("field");
              incr.add(1);
              nodes.add(IntsRef.deepCopyOf(curNodePath));
              datatypes.add(JSONDatatype.JSON_FIELD);
              // value
              this.incrNodeObjectPath();
              this.setLastNode(0);
            } else if (states.peek() == ARRAY) {
              this.addToLastNode(1);
              field = "";
            } else {
              // should not happen
              throw new IllegalStateException();
            }

            types.add(JsonTokenizer.getTokenTypes()[LITERAL]);
            images.add("Luke Skywalker");
            incr.add(1);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            datatypes.add("jedi");
            // close datatype object
            this.decrNodeObjectPath();

            return field + "{" + this.getWhitespace() +
                      "\"" + JsonTokenizer.DATATYPE_LABEL + "\":" + this.getWhitespace() + "\"jedi\"," +
                      "\"" + JsonTokenizer.DATATYPE_VALUES + "\":" + this.getWhitespace() + "\"Luke Skywalker\"" +
                    this.getWhitespace() + "},";
        }
      case OBJECT_VAL:
        switch (rand.nextInt(8)) {
          case 0:
            return this.doValString("stepha" + this.getWhitespace() + "n" + this.getWhitespace() + "e");
          case 1: // DOUBLE case
            images.add("34.560e-9");
            types.add(JsonTokenizer.getTokenTypes()[NUMBER]);
            incr.add(1);
            this.incrNodeObjectPath();
            this.setLastNode(0);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            this.decrNodeObjectPath();
            datatypes.add(XSDDatatype.XSD_DOUBLE);

            states.pop(); // remove OBJECT_VAL state
            return "34.560e-9" + this.getWhitespace() + ",";
          case 2: // LONG case
            images.add("34560e-9");
            types.add(JsonTokenizer.getTokenTypes()[NUMBER]);
            incr.add(1);
            this.incrNodeObjectPath();
            this.setLastNode(0);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            this.decrNodeObjectPath();
            datatypes.add(XSDDatatype.XSD_LONG);

            states.pop(); // remove OBJECT_VAL state
            return "34560e-9" + this.getWhitespace() + ",";
          case 3:
            images.add("true");
            types.add(JsonTokenizer.getTokenTypes()[TRUE]);
            incr.add(1);
            this.incrNodeObjectPath();
            this.setLastNode(0);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            this.decrNodeObjectPath();
            datatypes.add(XSDDatatype.XSD_BOOLEAN);

            states.pop(); // remove OBJECT_VAL state
            return "true" + this.getWhitespace() + ",";
          case 4:
            images.add("false");
            types.add(JsonTokenizer.getTokenTypes()[FALSE]);
            incr.add(1);
            this.incrNodeObjectPath();
            this.setLastNode(0);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            this.decrNodeObjectPath();
            datatypes.add(XSDDatatype.XSD_BOOLEAN);

            states.pop(); // remove OBJECT_VAL state
            return "false" + this.getWhitespace() + ",";
          case 5:
            images.add("null");
            types.add(JsonTokenizer.getTokenTypes()[NULL]);
            incr.add(1);
            this.incrNodeObjectPath();
            this.setLastNode(0);
            nodes.add(IntsRef.deepCopyOf(curNodePath));
            this.decrNodeObjectPath();
            datatypes.add(XSDDatatype.XSD_STRING);

            states.pop(); // remove OBJECT_VAL state
            return "null" + this.getWhitespace() + ",";
          case 6:
            if (states.size() <= MAX_DEPTH) {
              states.pop(); // remove OBJECT_VAL state
              this.incrNodeObjectPath();
              states.add(ARRAY);
              return "[";
            }
            return this.doValString("");
          case 7:
            if (states.size() <= MAX_DEPTH) {
              states.pop(); // remove OBJECT_VAL state
              // Two incrementations, because the object introduce a "blank" node
              nestedObjs++;
              this.incrNodeObjectPath();
              this.setLastNode(0);
              this.incrNodeObjectPath();
              states.add(OBJECT_ATT);
              return "{";
            }
            return this.doValString("");
        }
      default:
        throw new IllegalStateException("Got unknown lexical state: " + states.peek());
    }
  }

  /**
   * Return a sequence of whitespace characters
   */
  private String getWhitespace() {
    final int nWS = rand.nextInt(5);
    String ws = "";

    for (int i = 0; i < nWS; i++) {
      switch (rand.nextInt(6)) {
        case 0:
          ws += " ";
          break;
        case 1:
          ws += "\t";
         break;
        case 2:
          ws += "\f";
          break;
        case 3:
          ws += "\r";
          break;
        case 4:
          ws += "\n";
          break;
        case 5:
          ws += "\r\n";
          break;
        default:
          break;
      }
    }
    return ws;
  }

  /**
   * Add an object/array to the current node path
   */
  private void incrNodeObjectPath() {
    ArrayUtils.growAndCopy(curNodePath, curNodePath.length + 1);
    curNodePath.length++;
    // initialise node
    this.setLastNode(-1);
  }

  /**
   * Remove an object/array from the node path
   */
  private void decrNodeObjectPath() {
    curNodePath.length--;
  }

  /** Update the path of the current values of the current object node */
  private void setLastNode(final int val) {
    curNodePath.ints[curNodePath.length - 1] = val;
  }

  /** Update the path of the current values of the current object node */
  private void addToLastNode(final int val) {
    curNodePath.ints[curNodePath.length - 1] += val;
  }

  /**
   * Add a string value to an object entry
   */
  private String doValString(final String val) {
    images.add(val);
    types.add(JsonTokenizer.getTokenTypes()[LITERAL]);
    incr.add(1);
    this.incrNodeObjectPath();
    this.setLastNode(0);
    nodes.add(IntsRef.deepCopyOf(curNodePath));
    this.decrNodeObjectPath();
    datatypes.add(XSDDatatype.XSD_STRING);

    states.pop(); // remove OBJECT_VAL state
    return "\"" + val + "\"" + this.getWhitespace() + ",";
  }

  /**
   * Returns a random value type
   */
  public String getRandomValue() {
    valueType = rand.nextInt(5);

    switch (valueType) {
      case FALSE:
        return "false";
      case LITERAL:
        return "\"stephane\"";
      case NULL:
        return "null";
      case NUMBER:
        return "324.90E-02";
      case TRUE:
        return "true";
      default:
        throw new IllegalArgumentException("No value for index=" + valueType);
    }
  }

}
