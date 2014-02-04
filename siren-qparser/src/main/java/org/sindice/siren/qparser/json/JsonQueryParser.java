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

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.QueryParserHelper;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.json.builders.JsonQueryTreeBuilder;
import org.sindice.siren.qparser.json.config.JsonQueryConfigHandler;
import org.sindice.siren.qparser.json.config.JsonQueryConfigHandler.ConfigurationKeys;
import org.sindice.siren.qparser.json.parser.JsonSyntaxParser;
import org.sindice.siren.qparser.json.processors.JsonQueryNodeProcessorPipeline;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;

/**
 * This class is a helper that enables users to easily use the SIREn's JSON
 * query parser.
 *
 * <p>
 *
 * To construct a {@link Query} object from a query string, use the
 * {@link #parse(String, String)} method:
 * <pre>
 * JsonQueryParser queryParser = new JsonQueryParser();
 * Query query = JsonQueryParser.parse("{ \"node\" : { \"query\" : \"aaa\" }}", "defaultField");
 * </pre>
 *
 * <p>
 *
 * To change any configuration before parsing the query string do, for example:
 * <pre>
 * // the query config handler returned by {@link JsonQueryParser} is a {@link JsonQueryConfigHandler}
 * queryParser.getQueryConfigHandler().setDefaultOperator(Operator.AND);
 * </pre>
 *
 * <p>
 *
 * Examples of appropriately formatted queries can be found in the <a
 * href="{@docRoot}/org/sindice/siren/qparser/json/package-summary.html#package_description">
 * query syntax documentation</a>.
 *
 * <p>
 *
 * The text parser used by this helper is a {@link JsonSyntaxParser}.
 *
 * <p>
 *
 * The query node processor used by this helper is a
 * {@link JsonQueryNodeProcessorPipeline}.
 *
 * <p>
 *
 * The builder used by this helper is a {@link JsonQueryTreeBuilder}.
 *
 * @see JsonQueryConfigHandler
 * @see JsonSyntaxParser
 * @see JsonQueryNodeProcessorPipeline
 * @see JsonQueryTreeBuilder
 */
public class JsonQueryParser extends QueryParserHelper {

  public JsonQueryParser() {
    super(new JsonQueryConfigHandler(), new JsonSyntaxParser(),
      new JsonQueryNodeProcessorPipeline(null),
      new JsonQueryTreeBuilder(null));

    final KeywordQueryParser keywordParser = this.getKeywordQueryParser();
    // ensure that the default operator of the keyword parser is in synch
    keywordParser.setDefaultOperator(this.getDefaultOperator());

    // configure builders with the keyword parser
    final JsonQueryTreeBuilder builder = (JsonQueryTreeBuilder) this.getQueryBuilder();
    builder.setBuilders(keywordParser);
  }

  /**
   * Overrides {@link QueryParserHelper#parse(String, String)} so it casts the
   * return object to {@link Query}. For more reference about this method, check
   * {@link QueryParserHelper#parse(String, String)}.
   *
   * @param query
   *          the query string
   * @param defaultField
   *          the default field used by the text parser
   *
   * @return the object built from the query
   *
   * @throws ParseException
   *           if something wrong happens during the query parsing
   */
  @Override
  public Query parse(final String query, final String defaultField) throws QueryNodeException {
    try {
      return (Query) super.parse(query, defaultField);
    }
    catch (final QueryNodeException e) {
      throw new ParseException("Query parsing failed", e);
    }
  }

  /**
   * Gets implicit operator setting, which will be either {@link Operator#AND}
   * or {@link Operator#OR}.
   */
  public Operator getDefaultOperator() {
    return this.getQueryConfigHandler().get(ConfigurationKeys.DEFAULT_OPERATOR);
  }

  /**
   * Sets the boolean operator of the QueryParser. In default mode (
   * {@link Operator#OR}) terms without any modifiers are considered optional:
   * for example <code>capital of Hungary</code> is equal to
   * <code>capital OR of OR Hungary</code>.<br/>
   * In {@link Operator#AND} mode terms are considered to be in conjunction: the
   * above mentioned query is parsed as <code>capital AND of AND Hungary</code>
   */
  public void setDefaultOperator(final Operator operator) {
    this.getQueryConfigHandler().set(ConfigurationKeys.DEFAULT_OPERATOR, operator);

    // ensure that the default operator of the keyword parser is in synch
    final KeywordQueryParser keywordParser = this.getKeywordQueryParser();
    keywordParser.setDefaultOperator(this.getDefaultOperator());
  }

  public KeywordQueryParser getKeywordQueryParser() {
    return this.getQueryConfigHandler().get(ConfigurationKeys.KEYWORD_PARSER);
  }

  /**
   * Set the keyword query parser that will be used to parse boolean expressions
   * found in the JSON query objects.
   */
  public void setKeywordQueryParser(final KeywordQueryParser keywordParser) {
    // ensure that the default operator of the keyword parser is in synch
    keywordParser.setDefaultOperator(this.getDefaultOperator());

    // set keyword query parser
    this.getQueryConfigHandler().set(ConfigurationKeys.KEYWORD_PARSER, keywordParser);

    // configure builders with the new keyword parser
    final JsonQueryTreeBuilder builder = (JsonQueryTreeBuilder) this.getQueryBuilder();
    builder.setBuilders(keywordParser);
  }

  @Override
  public void setQueryConfigHandler(final QueryConfigHandler config) {
    super.setQueryConfigHandler(config);

    final KeywordQueryParser keywordParser = this.getKeywordQueryParser();
    // ensure that the default operator of the keyword parser is in synch
    keywordParser.setDefaultOperator(this.getDefaultOperator());

    // configure builders with the keyword parser
    final JsonQueryTreeBuilder builder = (JsonQueryTreeBuilder) this.getQueryBuilder();
    builder.setBuilders(keywordParser);
  }

}
