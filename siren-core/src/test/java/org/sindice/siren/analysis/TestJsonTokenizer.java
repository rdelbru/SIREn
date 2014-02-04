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

import static org.sindice.siren.analysis.JsonTokenizer.FALSE;
import static org.sindice.siren.analysis.JsonTokenizer.LITERAL;
import static org.sindice.siren.analysis.JsonTokenizer.NULL;
import static org.sindice.siren.analysis.JsonTokenizer.NUMBER;
import static org.sindice.siren.analysis.JsonTokenizer.TOKEN_TYPES;
import static org.sindice.siren.analysis.JsonTokenizer.TRUE;
import static org.sindice.siren.analysis.MockSirenToken.node;
import static org.sindice.siren.util.JSONDatatype.JSON_FIELD;
import static org.sindice.siren.util.XSDDatatype.XSD_BOOLEAN;
import static org.sindice.siren.util.XSDDatatype.XSD_DOUBLE;
import static org.sindice.siren.util.XSDDatatype.XSD_LONG;
import static org.sindice.siren.util.XSDDatatype.XSD_STRING;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.lucene.util.IntsRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.util.JsonGenerator;

public class TestJsonTokenizer extends NodeTokenizerTestCase {

  private final JsonTokenizer _t      = new JsonTokenizer(new StringReader(""));

  private JsonGenerator jsonGen;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    jsonGen = new JsonGenerator(random());
  }

  @Override
  @After
  public void tearDown() throws Exception {
    // help jvm to free memory (issue #135)
    jsonGen = null;
    super.tearDown();
  }

  @Test
  public void testNodePathBufferOverflow()
  throws Exception {
    final int size = 1030;
    final StringBuilder sb = new StringBuilder();
    final String[] images = new String[size + 1];
    final String[] types = new String[size + 1];

    for (int i = 0; i < size; i++) {
      images[i] = "o" + i;
      types[i] = TOKEN_TYPES[LITERAL];
    }
    images[size] = "true";
    types[size] = TOKEN_TYPES[TRUE];

    // Creates nested objects
    for (int i = 0; i < size; i++) {
      sb.append("{\"o").append(i).append("\":");
    }
    sb.append("true");
    // Close nested objects
    for (int i = 0; i < size; i++) {
      sb.append("}");
    }
    this.assertTokenizesTo(_t, sb.toString(), images, types);
  }

  @Test
  public void testNewlinesInLiterals()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"a\" : \"b\n\tc\n\r\tv\"}",
      new String[] { "a", "b\n\tc\n\r\tv" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], });
  }

  @Test
  public void testEmptyJson()
  throws Exception {
    this.assertTokenizesTo(_t, "{}", new String[] {}, new String[] {});
  }

  @Test
  public void testEmptyArray()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"a\":[]}", new String[] { "a" },
      new String[] { TOKEN_TYPES[LITERAL] });
    // nested empty array
    this.assertTokenizesTo(_t, "{\"a\":[ false, [], true ]}", new String[] { "a", "false", "true" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[FALSE], TOKEN_TYPES[TRUE] },
      new String[] { JSON_FIELD, XSD_BOOLEAN, XSD_BOOLEAN },
      new int[] { 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0), node(0, 2) });
  }

  @Test
  public void testURIinLiteral()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"http://oai.rkbexplorer.com/id/dspace.vsb.cz/oai:dspace.vsb.cz:10084/55723\":true}",
      new String[] { "http://oai.rkbexplorer.com/id/dspace.vsb.cz/oai:dspace.vsb.cz:10084/55723", "true" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[TRUE] });
  }

  @Test
  public void testBackslashes()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"test\":\"200309350 DP \\\\\\\\*\\\\\"}",
      new String[] { "test", "200309350 DP \\\\\\\\*\\\\" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] });
  }

  @Test
  public void testArraysNumber()
  throws Exception {
    this.assertTokenizesTo(_t, "{ \"a\" : [ 10.5, 20 ] }",
      new String[] { "a", "10.5", "20" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER], TOKEN_TYPES[NUMBER] },
      new String[] { JSON_FIELD, XSD_DOUBLE, XSD_LONG },
      new int[] { 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0), node(0, 1) });
  }

  @Test
  public void testNestedObjects()
  throws Exception {
    this.assertTokenizesTo(_t, "{ \"a\" : { \"b\" : \"c\", \"d\":\"e\" } }",
      new String[] { "a", "b", "c", "d", "e" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new int[] { 1, 1, 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0, 0), node(0, 0, 0, 0), node(0, 0, 1), node(0, 0, 1, 0) });
  }

  @Test
  public void testNestedObjects2()
  throws Exception {
    this.assertTokenizesTo(_t, "{ \"a\" : { \"b\" : { \"c\" : \"d\" } } }",
      new String[] { "a", "b", "c", "d" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new int[] { 1, 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0, 0), node(0, 0, 0, 0, 0), node(0, 0, 0, 0, 0, 0) });
  }

  @Test
  public void testValues()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"v0\":\"stephane\",\"v1\":12.3e-9,\"v2\":true,\"v3\":false,\"v4\":null}",
      new String[] { "v0", "stephane", "v1", "12.3e-9", "v2", "true", "v3", "false", "v4", "null" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER],
                     TOKEN_TYPES[LITERAL], TOKEN_TYPES[TRUE], TOKEN_TYPES[LITERAL], TOKEN_TYPES[FALSE],
                     TOKEN_TYPES[LITERAL], TOKEN_TYPES[NULL] },
      new String[] { JSON_FIELD, XSD_STRING, JSON_FIELD, XSD_DOUBLE,
                     JSON_FIELD, XSD_BOOLEAN, JSON_FIELD, XSD_BOOLEAN,
                     JSON_FIELD, XSD_STRING },
      new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0), node(1), node(1, 0),
                      node(2), node(2, 0), node(3), node(3, 0),
                      node(4), node(4, 0) });
  }

  @Test
  public void testArray()
  throws Exception {
    final ArrayList<String> images = new ArrayList<String>() {{
      this.add("array");
    }};
    final ArrayList<IntsRef> nodes = new ArrayList<IntsRef>() {{
      this.add(node(0));
    }};
    final ArrayList<String> types = new ArrayList<String>() {{
      this.add(TOKEN_TYPES[LITERAL]);
    }};

    final int arraySize = jsonGen.rand.nextInt(100);
    String array = "[";
    for (int i = 0; i < arraySize; i++) {
      final String v = jsonGen.getRandomValue();
      array += v + ",";
      images.add(jsonGen.valueType == LITERAL ? v.substring(1, v.length() - 1) : v);
      nodes.add(node(0, i));
      types.add(TOKEN_TYPES[jsonGen.valueType]);
    }
    array += "]";

    final int[] posIncr = new int[images.size()];
    Arrays.fill(posIncr, 1);
    this.assertTokenizesTo(_t, "{\"array\":" + array + "}",
      images.toArray(new String[0]), types.toArray(new String[0]),
      posIncr, nodes.toArray(new IntsRef[0]));
  }

  @Test
  public void testObjects()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"a0\":[{\"t1\":1},{\"t2\":2}],\"a1\":{\"t3\":3}}",
      new String[] { "a0", "t1", "1", "t2", "2", "a1", "t3", "3" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER], TOKEN_TYPES[LITERAL],
                     TOKEN_TYPES[NUMBER], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER] },
      new String[] { JSON_FIELD, JSON_FIELD, XSD_LONG, JSON_FIELD,
                     XSD_LONG, JSON_FIELD, JSON_FIELD, XSD_LONG },
      new int[] { 1, 1, 1, 1, 1, 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0, 0), node(0, 0, 0, 0), node(0, 1, 0),
                      node(0, 1, 0, 0), node(1), node(1, 0, 0), node(1, 0, 0, 0) });
    // nested objects
    final String a0a1 = jsonGen.getRandomValue();
    final int a0a1Type = jsonGen.valueType;
    final String a0a1Value = a0a1Type == LITERAL ? a0a1.substring(1, a0a1.length() - 1) : a0a1;
    final String a1a0 = jsonGen.getRandomValue();
    final int a1a0Type = jsonGen.valueType;
    final String a1a0Value = a1a0Type == LITERAL ? a1a0.substring(1, a1a0.length() - 1) : a1a0;
    this.assertTokenizesTo(_t, "{\"a0\":[{\"t1\":1},{\"t2\":{\"a0a1\":" + a0a1 + "}}],\"a1\":{\"t3\":{\"a1a0\":" + a1a0 + "}}}",
      new String[] { "a0", "t1", "1", "t2", "a0a1", a0a1Value, "a1", "t3", "a1a0", a1a0Value },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER], TOKEN_TYPES[LITERAL],
                     TOKEN_TYPES[LITERAL], TOKEN_TYPES[a0a1Type], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL],
                     TOKEN_TYPES[LITERAL], TOKEN_TYPES[a1a0Type] },
      new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0, 0), node(0, 0, 0, 0),
                      node(0, 1, 0), node(0, 1, 0, 0, 0), node(0, 1, 0, 0, 0, 0),
                      node(1), node(1, 0, 0), node(1, 0, 0, 0, 0), node(1, 0, 0, 0, 0, 0) });
    // nested objects + arrays
    this.assertTokenizesTo(_t, "{\"a0\":[{\"t1\":[1,2]},{\"t2\":{\"a0a1\":[" + a0a1 + ",23]}}],\"a1\":{\"t3\":{\"a1a0\":[true," + a1a0 + "]}}}",
      new String[] { "a0", "t1", "1", "2", "t2", "a0a1", a0a1Value, "23", "a1", "t3", "a1a0", "true", a1a0Value },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER], TOKEN_TYPES[NUMBER],
                     TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[a0a1Type], TOKEN_TYPES[NUMBER],
                     TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[TRUE], TOKEN_TYPES[a1a0Type] },
      new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0, 0), node(0, 0, 0, 0), node(0, 0, 0, 1),
                      node(0, 1, 0), node(0, 1, 0, 0, 0), node(0, 1, 0, 0, 0, 0), node(0, 1, 0, 0, 0, 1),
                      node(1), node(1, 0, 0), node(1, 0, 0, 0, 0), node(1, 0, 0, 0, 0, 0), node(1, 0, 0, 0, 0, 1) });
    // nested objects + arrays
    this.assertTokenizesTo(_t, "{\"a0\":[\"a\",{\"o6\":[\"b\",9E9]}],\"o2\":{\"o3\":\"obj3\",\"o4\":{\"o5\":null}}}",
      new String[] { "a0", "a", "o6", "b", "9E9", "o2", "o3", "obj3", "o4", "o5", "null" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL],
                     TOKEN_TYPES[NUMBER], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL],
                     TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NULL] },
      new String[] { JSON_FIELD, XSD_STRING, JSON_FIELD, XSD_STRING,
                     XSD_LONG, JSON_FIELD, JSON_FIELD, XSD_STRING,
                     JSON_FIELD, JSON_FIELD, XSD_STRING },
      new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0), node(0, 1, 0), node(0, 1, 0, 0), node(0, 1, 0, 1),
                      node(1), node(1, 0, 0), node(1, 0, 0, 0), node(1, 0, 1), node(1, 0, 1, 0, 0), node(1, 0, 1, 0, 0, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testUnclosedObject()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"a\":{\"34\":34,23}", // the 23 is not parsed, because it is not a literal
      new String[] { "a", "34", "34" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER] },
      new String[] { JSON_FIELD, JSON_FIELD, XSD_LONG },
      new int[] { 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0, 0), node(0, 0, 0, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testWrongClosingCharacter()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"a\":{\"34\":34],\"a\":1}", // \"a\":1 is not parsed because of the stray ']' character
      new String[] { "a", "34", "34" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER] },
      new String[] { JSON_FIELD, JSON_FIELD, XSD_LONG },
      new int[] { 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0, 0), node(0, 0, 0, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testWrongClosingCharacter2()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"a\":[\"34\",34},\"a\":1}", // \"a\":1 is not parsed because of the stray '}' character
      new String[] { "a", "34", "34" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER] },
      new String[] { JSON_FIELD, XSD_STRING, XSD_LONG },
      new int[] { 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0), node(0, 1) });
  }

  @Test(expected=IllegalStateException.class)
  public void testColonInArray()
  throws Exception {
    this.assertTokenizesTo(_t, "{\"a\":[\"34\":34},\"a\":1}", // 34},\"a\":1 is not parsed because of the stray '}' character
      new String[] { "a", "34" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, XSD_STRING },
      new int[] { 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testNoFieldJson1()
  throws Exception {
    this.assertTokenizesTo(_t, "{ : \"bad\" }",
      new String[] { "bad" },
      new String[] { TOKEN_TYPES[LITERAL] },
      new String[] { XSD_STRING },
      new int[] { 1 },
      new IntsRef[] { node(-1, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testNoFieldJson2()
  throws Exception {
    this.assertTokenizesTo(_t, "{ : 1 }",
      new String[] { "bad" },
      new String[] { TOKEN_TYPES[NUMBER] },
      new String[] { XSD_LONG },
      new int[] { 1 },
      new IntsRef[] { node(-1, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testNoFieldJson3()
  throws Exception {
    this.assertTokenizesTo(_t, "{ : 1.1 }",
      new String[] { "bad" },
      new String[] { TOKEN_TYPES[NUMBER] },
      new String[] { XSD_DOUBLE },
      new int[] { 1 },
      new IntsRef[] { node(-1, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testNoFieldJson4()
  throws Exception {
    this.assertTokenizesTo(_t, "{ : true }",
      new String[] { "bad" },
      new String[] { TOKEN_TYPES[TRUE] },
      new String[] { XSD_BOOLEAN },
      new int[] { 1 },
      new IntsRef[] { node(-1, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testNoFieldJson5()
  throws Exception {
    this.assertTokenizesTo(_t, "{ : false }",
      new String[] { "bad" },
      new String[] { TOKEN_TYPES[FALSE] },
      new String[] { XSD_BOOLEAN },
      new int[] { 1 },
      new IntsRef[] { node(-1, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testNoFieldJson6()
  throws Exception {
    this.assertTokenizesTo(_t, "{ : null }",
      new String[] { "bad" },
      new String[] { TOKEN_TYPES[NULL] },
      new String[] { XSD_STRING },
      new int[] { 1 },
      new IntsRef[] { node(-1, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testNoFieldJson7()
  throws Exception {
    this.assertTokenizesTo(_t, "{ : [ 1 ] }",
      new String[] { "bad" },
      new String[] { TOKEN_TYPES[NULL] },
      new String[] { XSD_STRING },
      new int[] { 1 },
      new IntsRef[] { node(-1, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testNoFieldJson8()
  throws Exception {
    this.assertTokenizesTo(_t, "{ : { \"a\" : \"b\" } }",
      new String[] { "bad" },
      new String[] { TOKEN_TYPES[NULL] },
      new String[] { XSD_STRING },
      new int[] { 1 },
      new IntsRef[] { node(-1, 0) });
  }

  @Test(expected=IllegalStateException.class)
  public void testNoFieldJson9()
  throws Exception {
    this.assertTokenizesTo(_t, "{ \"a\" : \"b\" ,  : 12 }",
      new String[] { "a", "b", "12" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL],
                     TOKEN_TYPES[NUMBER] },
      new String[] { JSON_FIELD, XSD_STRING, XSD_LONG },
      new int[] { 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0), node(1, 0) });
  }

  /**
   * Although there is a trailing comma, the JSON document
   * can still be scanned correctly
   */

  @Test
  public void testTrailingComma1a()
  throws Exception {
    this.assertTokenizesTo(_t, "{ , \"a\" : \"b\" }",
      new String[] { "a", "b" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, XSD_STRING },
      new int[] { 1, 1 },
      new IntsRef[] { node(0), node(0, 0) });
  }

  @Test
  public void testTrailingComma1b()
  throws Exception {
    this.assertTokenizesTo(_t, "{ \"a\" : \"b\" , }",
      new String[] { "a", "b" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, XSD_STRING },
      new int[] { 1, 1 },
      new IntsRef[] { node(0), node(0, 0) });
  }

  @Test
  public void testRandomJson()
  throws Exception {
    // should generate numbers between 50 and 75
    for (int i = 0; i < atLeast(50); i++) {
      String json = "";
      try {
        // should generate numbers between 50 and 75
        json = jsonGen.getRandomJson(atLeast(50));
        final int[] incr = new int[jsonGen.incr.size()];
        for (int j = 0; j < incr.length; j++) {
          incr[j] = jsonGen.incr.get(j);
        }
        this.assertTokenizesTo(_t, json,
          jsonGen.images.toArray(new String[jsonGen.images.size()]),
          jsonGen.types.toArray(new String[jsonGen.types.size()]),
          jsonGen.datatypes.toArray(new String[jsonGen.datatypes.size()]),
          incr,
          jsonGen.nodes.toArray(new IntsRef[jsonGen.nodes.size()]));
      } catch (final IllegalStateException e) {
        if (!jsonGen.shouldFail) {
          fail("Failed to parse json!\n" + json);
        }
      }
      if (jsonGen.shouldFail) {
        fail("Expected to fail JSON didn't fail!\n" + json);
      }
    }
  }

  @Test
  public void testEmptyField()
  throws Exception {
    this.assertTokenizesTo(_t, "{ \"\" : \"b\" , }",
      new String[] { "", "b" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, XSD_STRING },
      new int[] { 1, 1 },
      new IntsRef[] { node(0), node(0, 0) });
  }

  /**
   * Test for Datatype object
   */
  @Test
  public void testDatatypeObject()
  throws Exception {
    this.assertTokenizesTo(_t,
      "{ \"a\" : { \"_datatype_\" : \"lower\", \"_value_\" : \"BlA bLa\" } }",
      new String[] { "a", "BlA bLa" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, "lower" },
      new int[] { 1, 1 },
      new IntsRef[] { node(0), node(0, 0) });

    this.assertTokenizesTo(_t,
      "{ \"a\" : { \"_value_\" : \"BlA bLa\", \"_datatype_\" : \"lower\" } }",
      new String[] { "a", "BlA bLa" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, "lower" },
      new int[] { 1, 1 },
      new IntsRef[] { node(0), node(0, 0) });
  }

  @Test
  public void testDatatypeObjectInArray()
  throws Exception {
    this.assertTokenizesTo(_t,
      "{ \"a\" : [ { \"_datatype_\" : \"lower\", \"_value_\" : \"BlA bLa\" }," +
                  "{ \"_value_\" : \"toTO TOto\", \"_datatype_\" : \"upper\" } ] }",
      new String[] { "a", "BlA bLa", "toTO TOto" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, "lower", "upper" },
      new int[] { 1, 1, 1 },
      new IntsRef[] { node(0), node(0, 0), node(0, 1) });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectMissingLabel()
  throws Exception {
    this.assertTokenizesTo(_t,
      "{ \"a\":{ \"_value_\" : \"BlA bLa\" } }",
      new String[] { "a" },
      new String[] { TOKEN_TYPES[LITERAL] });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectMissingValue()
  throws Exception {
    this.assertTokenizesTo(_t,
      "{ \"a\":{ \"_datatype_\" : \"lower\" } }",
      new String[] { "a" },
      new String[] { TOKEN_TYPES[LITERAL] });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectDuplicates1a()
  throws Exception {
    this.assertTokenizesTo(_t,
      "{ \"a\":{ \"_value_\" : \"BlA bLa\", \"_datatype_\" : \"lower\", \"_value_\" : \"BlA bLa\" } }",
      new String[] { "a", "BlA bLa" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, "lower" });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectDuplicates1b()
  throws Exception {
    this.assertTokenizesTo(_t,
      "{ \"a\":{ \"_value_\" : \"BlA bLa\", \"_value_\" : \"BlA bLa\", \"_datatype_\" : \"lower\" } }",
      new String[] { "a" },
      new String[] { TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectDuplicates2a()
  throws Exception {
    this.assertTokenizesTo(_t,
      "{ \"a\":{ \"_value_\" : \"BlA bLa\", \"_datatype_\" : \"lower\", \"_datatype_\" : \"lower\" } }",
      new String[] { "a", "BlA bLa" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, "lower" });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectDuplicates2b()
  throws Exception {
    this.assertTokenizesTo(_t,
      "{ \"a\":{ \"_datatype_\" : \"lower\", \"_datatype_\" : \"lower\", \"_value_\" : \"BlA bLa\" } }",
      new String[] { "a" },
      new String[] { TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectAtRoot()
  throws Exception {
    this.assertTokenizesTo(_t,
      "{ \"_datatype_\" : \"lower\", \"_value_\" : \"BlA bLa\" }",
      new String[] { },
      new String[] { });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectWithBadFields1a()
  throws Exception {
    this.assertTokenizesTo(_t,
      // Stops parsing at \"_datatype_\"
      "{ \"a\":{ \"n\":12, \"_datatype_\" : \"lower\", \"_value_\" : \"BlA bLa\" } }",
      new String[] { "a", "n", "12" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER] });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectWithBadFields1b()
  throws Exception {
    this.assertTokenizesTo(_t,
      // Stops parsing at \"_value_\"
      "{ \"a\":{ \"n\":12, \"_value_\" : \"BlA bLa\", \"_datatype_\" : \"lower\" } }",
      new String[] { "a", "n", "12" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL], TOKEN_TYPES[NUMBER] });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectWithBadFields2()
  throws Exception {
    this.assertTokenizesTo(_t,
      // Stops parsing at \"n\":12
      "{ \"a\":{ \"_datatype_\" : \"lower\", \"n\":12, \"_value_\" : \"BlA bLa\" } }",
      new String[] { "a" },
      new String[] { TOKEN_TYPES[LITERAL] });
  }

  @Test(expected=IllegalStateException.class)
  public void testDatatypeObjectWithBadFields3()
  throws Exception {
    this.assertTokenizesTo(_t,
      // Stops parsing at \"n\":12
      "{ \"a\":{ \"_datatype_\" : \"lower\", \"_value_\" : \"BlA bLa\", \"n\":12 } }",
      new String[] { "a", "BlA bLa" },
      new String[] { TOKEN_TYPES[LITERAL], TOKEN_TYPES[LITERAL] },
      new String[] { JSON_FIELD, "lower" });
  }

}
