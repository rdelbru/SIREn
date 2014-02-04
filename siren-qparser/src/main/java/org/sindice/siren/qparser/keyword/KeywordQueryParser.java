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
package org.sindice.siren.qparser.keyword;

import java.util.Map;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.builders.KeywordQueryTreeBuilder;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.processors.KeywordQueryNodeProcessorPipeline;
import org.sindice.siren.search.node.TwigQuery;

/**
 * This class is a helper that enables to create easily a boolean of twig
 * queries.
 *
 * <p>
 *
 * To construct a {@link Query} object from a query string, use the
 * {@link #parse(String, String)} method:
 * <pre>
 * KeywordQueryParser queryParser = new KeywordQueryParser(); <br/>
 * Query query = queryParser.parse("a : b", "sirenField");
 * </pre>
 *
 *
 * To change any configuration before parsing the query string do, for example:
 * <pre>
 * queryParser.getQueryConfigHandler().setDefaultOperator(Operator.AND);
 * </pre>
 *
 * or use the setter methods:
 * <pre>
 *   queryParser.setDefaultOperator(Operator.AND);
 * </pre>
 *
 * <p>
 *
 * Examples of appropriately formatted queries can be found in the <a
 * href="{@docRoot}/org/sindice/siren/qparser/keyword/package-summary.html#package_description">
 * query syntax documentation</a>.
 *
 * <p>
 *
 * The text parser used by this helper is a {@link KeywordSyntaxParser}. The
 * BNF grammar of this parser is available <a href="{@docRoot}/../jjdoc/KeywordSyntaxParser.html">here</a>.
 *
 * <p>
 *
 * The {@link QueryConfigHandler} used by this helper is a
 * {@link KeywordQueryConfigHandler}.
 *
 * <p>
 *
 * The query node processor used by this helper is a
 * {@link KeywordQueryNodeProcessorPipeline}.
 *
 * <p>
 *
 * The builder used by this helper is a {@link KeywordQueryTreeBuilder}.
 *
 * @see KeywordQueryConfigHandler
 * @see KeywordSyntaxParser
 * @see KeywordQueryNodeProcessorPipeline
 * @see KeywordQueryTreeBuilder
 */
public class KeywordQueryParser
extends StandardQueryParser {

  /**
   * Constructs a {@link KeywordQueryParser} object.
   */
  public KeywordQueryParser() {
    super();
    this.setSyntaxParser(new KeywordSyntaxParser());
    this.setQueryConfigHandler(new KeywordQueryConfigHandler());
    this.setQueryNodeProcessor(new KeywordQueryNodeProcessorPipeline(this.getQueryConfigHandler()));
    this.setQueryBuilder(new KeywordQueryTreeBuilder());
  }

  /**
   * Sets the boolean operator of the QueryParser. In default mode (
   * {@link Operator#OR}) terms without any modifiers are considered optional:
   * for example <code>capital of Hungary</code> is equal to
   * <code>capital OR of OR Hungary</code>.<br/>
   * In {@link Operator#AND} mode terms are considered to be in conjunction: the
   * above mentioned query is parsed as <code>capital AND of AND Hungary</code>
   * <p>
   * Default: {@link Operator#AND}
   */
  @Override
  public void setDefaultOperator(final Operator operator) {
    if (operator == Operator.OR) {
      super.setDefaultOperator(Operator.OR);
    } else {
      super.setDefaultOperator(Operator.AND);
    }
  }

  /**
   * Set the set of qnames to URI mappings.
   * <p>
   * Default: <code>null</code>.
   */
  public void setQNames(final Properties qnames) {
    this.getQueryConfigHandler().set(KeywordConfigurationKeys.QNAMES, qnames);
  }

  /**
   * Set to <code>true</code> to allow fuzzy and wildcard queries.
   * <p>
   * Default: <code>true</code>.
   */
  public void setAllowFuzzyAndWildcard(final boolean allowFuzzyWildcard) {
    this.getQueryConfigHandler().set(KeywordConfigurationKeys.ALLOW_FUZZY_AND_WILDCARD, allowFuzzyWildcard);
  }

  /**
   * Set the {@link Map} that associates a datatype to its {@link Analyzer}.
   * <p>
   * Default:
   * <pre>
   *     map.put(XSDDatatype.XSD_STRING, new StandardAnalyzer(Version.LUCENE_40));
   *     map.put(JSONDatatype.JSON_FIELD, new WhitespaceAnalyzer(Version.LUCENE_40));
   * </pre>
   */
  public void setDatatypeAnalyzers(final Map<String, Analyzer> dtAnalyzers) {
    this.getQueryConfigHandler().set(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dtAnalyzers);
  }

  @Override
  public void setAnalyzer(final Analyzer analyzer) {
    throw new IllegalAccessError("Use #setDatatypesAnalyzers instead.");
  }

  /**
   * Set the default root level of a {@link TwigQuery}
   * <p>
   * Default: 1
   */
  public void setRootLevel(final int level) {
    this.getQueryConfigHandler().set(KeywordConfigurationKeys.ROOT_LEVEL, level);
  }

  /**
   * Enable or not {@link TwigQuery}
   * <p>
   * Default: <code>true</code>
   */
  public void setAllowTwig(final boolean allowTwig) {
    this.getQueryConfigHandler().set(KeywordConfigurationKeys.ALLOW_TWIG, allowTwig);
  }

  @Override
  public String toString(){
    return "<KeywordQueryParser config=\"" + this.getQueryConfigHandler() + "\"/>";
  }

}
