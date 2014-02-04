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
package org.sindice.siren.qparser.json.dsl;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.search.Query;
import org.codehaus.jackson.map.ObjectMapper;
import org.sindice.siren.qparser.json.JsonQueryParser;
import org.sindice.siren.qparser.json.config.JsonQueryConfigHandler;
import org.sindice.siren.qparser.json.config.JsonQueryConfigHandler.ConfigurationKeys;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;

/**
 * This class is a helper that enables users to easily build SIREn's JSON
 * queries.
 * <p>
 * More information about the JSON query syntax can be found in
 * {@link JsonQueryParser}.
 * <p>
 * The builder enables the creation of {@link AbstractQuery} which can then be
 * converted into a JSON representation using the method
 * {@link AbstractQuery#toString()} or into a {@link Query} using the method
 * {@link AbstractQuery#toQuery(boolean)}.
 *
 * @see JsonQueryParser
 */
public class QueryBuilder {

  private final KeywordQueryParser parser;

  private final ObjectMapper mapper;

  public QueryBuilder() {
    this(new JsonQueryConfigHandler());
  }

  public QueryBuilder(final JsonQueryConfigHandler config) {
    this.parser = config.get(ConfigurationKeys.KEYWORD_PARSER);
    this.mapper = new ObjectMapper();
  }

  /**
   * Create a new node query with the specified boolean expression
   * <p>
   * The boolean expression must follow the syntax of the
   * {@link KeywordQueryParser}.
   */
  public NodeQuery newNode(final String booleanExpression) throws QueryNodeException {
    final NodeQuery node = new NodeQuery(mapper, parser);
    node.setBooleanExpression(booleanExpression);
    return node;
  }

  /**
   * Create a new boolean query
   */
  public BooleanQuery newBoolean() {
    return new BooleanQuery(mapper);
  }

  /**
   * Create a new twig query with empty root
   */
  public TwigQuery newTwig() {
    return new TwigQuery(mapper, parser);
  }

  /**
   * Create a new twig query with the specified boolean expression as root query
   * <p>
   * The boolean expression must follow the syntax of the
   * {@link KeywordQueryParser}.
   */
  public TwigQuery newTwig(final String booleanExpression) throws QueryNodeException {
    final TwigQuery twig = new TwigQuery(mapper, parser);
    twig.setRoot(booleanExpression);
    return twig;
  }

}
