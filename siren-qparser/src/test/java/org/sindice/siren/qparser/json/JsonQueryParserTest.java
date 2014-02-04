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
package org.sindice.siren.qparser.json;

import static org.junit.Assert.assertEquals;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.search.Query;
import org.junit.Test;
import org.sindice.siren.qparser.json.dsl.AbstractQuery;
import org.sindice.siren.qparser.json.dsl.BooleanQuery;
import org.sindice.siren.qparser.json.dsl.NodeQuery;
import org.sindice.siren.qparser.json.dsl.QueryBuilder;
import org.sindice.siren.qparser.json.dsl.TwigQuery;

public class JsonQueryParserTest {

  @Test(expected=ParseException.class)
  public void testEmptyQuery() throws QueryNodeException {
    final String query = "{ }";
    final JsonQueryParser parser = new JsonQueryParser();
    parser.parse(query, "");
  }

  @Test(expected=ParseException.class)
  public void testUnknownTopLevelField() throws QueryNodeException {
    final String query = "{ \"unknown\" : { } }";
    final JsonQueryParser parser = new JsonQueryParser();
    parser.parse(query, "");
  }

  @Test
  public void testMoreThanOneTopLevelQuery() throws QueryNodeException {
    final String query = "{ \"node\" : { \"query\" : \"aaa\" }, \"twig\" : { \"query\" : \"bbb\" } }";
    final JsonQueryParser parser = new JsonQueryParser();
    // must not failed as it will take only the first top-level field encountered
    parser.parse(query, "");
  }

  @Test(expected=ParseException.class)
  public void testNodeWithInvalidQuery() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"node\" : { \"query\" : 132 } }";
    parser.parse(query, "");
  }

  @Test(expected=ParseException.class)
  public void testNodeWithInvalidLevel() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"node\" : { \"query\" : \"aaa\", \"level\" : \"3\" } }";
    parser.parse(query, "");
  }

  @Test(expected=ParseException.class)
  public void testNodeWithInvalidRange1() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"node\" : { \"query\" : \"aaa\", \"range\" : 3 } }";
    parser.parse(query, "");
  }

  @Test(expected=ParseException.class)
  public void testNodeWithInvalidRange2() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"node\" : { \"query\" : \"aaa\", \"range\" : [3] } }";
    parser.parse(query, "");
  }

  @Test(expected=ParseException.class)
  public void testNodeWithInvalidRange3() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"node\" : { \"query\" : \"aaa\", \"range\" : [2.34, 3.45] } }";
    parser.parse(query, "");
  }

  @Test(expected=ParseException.class)
  public void testNodeWithNoQuery() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"node\" : { \"level\" : 1 } }";
    parser.parse(query, "");
  }

  @Test
  public void testNodeWithQuery() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final NodeQuery node = build.newNode("aaa");
    assertParser(node);
  }

  @Test
  public void testNodeWithLevelAndRange() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final NodeQuery node = build.newNode("aaa")
                           .setLevel(1)
                           .setRange(1, 2);
    assertParser(node);
  }

  @Test(expected=ParseException.class)
  public void testTwigWithInvalidChild() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"twig\" : { \"child\" : " +
    		"{ \"occur\" : \"MUST\", \"node\" : { \"query\" : \"aaa\" } } " +
    		"} }";
    parser.parse(query, "");
  }

  @Test(expected=ParseException.class)
  public void testTwigWithChildWithMissingOccur() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"twig\" : { \"child\" : " +
        "[ { \"node\" : { \"query\" : \"aaa\" } } ] " +
        "} }";
    parser.parse(query, "");
  }

  @Test
  public void testEmptyTwig() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final TwigQuery twig = build.newTwig();
    assertParser(twig);
  }

  @Test
  public void testTwigWithRootOnly() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final TwigQuery twig = build.newTwig("aaa");
    assertParser(twig);
  }

  @Test
  public void testTwigWithRootLevelAndRange() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final TwigQuery twig = build.newTwig("aaa")
                                .setLevel(2)
                                .setRange(2, 5);
    assertParser(twig);
  }

  @Test
  public void testTwigWithOneChild() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final TwigQuery twig = build.newTwig()
                                .with(build.newNode("aaa"));
    assertParser(twig);
  }

  @Test
  public void testTwigWithMultipleChildren() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final TwigQuery twig = build.newTwig()
                                .with(build.newNode("aaa"))
                                .without(build.newNode("bbb"))
                                .optional(build.newTwig()
                                               .with(build.newNode("ccc")));
    assertParser(twig);
  }

  @Test
  public void testTwigWithOneDescendant() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final TwigQuery twig = build.newTwig()
                                .with(build.newNode("aaa"), 2);
    assertParser(twig);
  }

  @Test
  public void testTwigWithChildAndDescendant() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final TwigQuery twig = build.newTwig()
                                .with(build.newNode("aaa"))
                                .without(build.newNode("bbb"), 2)
                                .optional(build.newTwig()
                                               .with(build.newNode("ccc")), 4);
    assertParser(twig);
  }

  @Test(expected=ParseException.class)
  public void testBooleanWithInvalidClause() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"boolean\" : " +
    		"{ \"occur\" : \"MUST\", \"node\" : { \"query\" : \"aaa\" } } " +
        "}";
    parser.parse(query, "");
  }

  @Test(expected=ParseException.class)
  public void testBooleanWithMissingPropertyInClause() throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final String query = "{ \"boolean\" : [ " +
        "{ \"node\" : { \"query\" : \"aaa\" } } " +
        "] }";
    parser.parse(query, "");
  }

  @Test
  public void testBooleanWithOneClause() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final BooleanQuery bool = build.newBoolean()
                                   .with(build.newNode("aaa"));
    assertParser(bool);
  }

  @Test
  public void testBooleanWithMultipleClauses() throws QueryNodeException {
    final QueryBuilder build = new QueryBuilder();
    final BooleanQuery bool = build.newBoolean()
                                   .with(build.newNode("aaa"))
                                   .without(build.newNode("bbb"))
                                   .optional(build.newTwig("ccc").with(build.newNode("ddd")));
    assertParser(bool);
  }

  private static void assertParser(final AbstractQuery query) throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final Query output = parser.parse(query.toString(), "");
    assertEquals(query.toQuery(true), output);
  }

}
