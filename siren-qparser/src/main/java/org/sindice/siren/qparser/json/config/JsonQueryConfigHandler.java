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
package org.sindice.siren.qparser.json.config;

import java.util.Properties;

import org.apache.lucene.queryparser.flexible.core.config.ConfigurationKey;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator;
import org.sindice.siren.qparser.json.JsonQueryParser;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.sindice.siren.qparser.keyword.processors.QNamesProcessor;

/**
 * This query configuration handler which is used in the
 * {@link JsonQueryParser}.
 */
public class JsonQueryConfigHandler extends QueryConfigHandler {

  final public static class ConfigurationKeys  {

    /**
     * Key used to set the {@link KeywordQueryParser} used for boolean clause
     * found in the query
     *
     * @see JsonQueryParser#setKeywordParser(KeywordQueryParser)
     * @see JsonQueryParser#getKeywordParser()
     */
    final public static ConfigurationKey<KeywordQueryParser> KEYWORD_PARSER = ConfigurationKey.newInstance();

    /**
     * Key used to set the default boolean operator
     *
     * @see JsonQueryParser#setDefaultOperator(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator)
     * @see JsonQueryParser#getDefaultOperator()
     */
    final public static ConfigurationKey<Operator> DEFAULT_OPERATOR = ConfigurationKey.newInstance();

    /**
     * Key used to set the qnames mapping
     *
     * @see QNamesProcessor
     */
    final public static ConfigurationKey<Properties> QNAMES = ConfigurationKey.newInstance();

  }

  /**
   * Create a default {@link JsonQueryConfigHandler}.
   * <p>
   * The default configuration includes:
   * <ul>
   * <li> {@link Operator.AND} as default operator
   * <li> A {@link KeywordQueryParser} with the twig syntactic sugar disabled
   * and {@link Operator.AND} as default operator.
   * </ul>
   */
  public JsonQueryConfigHandler() {
    // Set default operator
    this.set(ConfigurationKeys.DEFAULT_OPERATOR, Operator.AND);

    // Set default keyword parser
    final KeywordQueryParser parser = new KeywordQueryParser();
    // Disable twig queries: syntactic sugar for twig queries must be disabled
    // in the JSON parser
    parser.setAllowTwig(false);
    // set default operator
    parser.setDefaultOperator(Operator.AND);
    this.set(ConfigurationKeys.KEYWORD_PARSER, parser);
  }

}
