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
package org.sindice.siren.qparser.keyword.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.queryparser.flexible.core.config.ConfigurationKey;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.config.FieldDateResolutionFCListener;
import org.apache.lucene.queryparser.flexible.standard.config.FuzzyConfig;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator;
import org.apache.lucene.util.Version;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.sindice.siren.qparser.keyword.processors.AllowFuzzyAndWildcardProcessor;
import org.sindice.siren.qparser.keyword.processors.AllowTwigProcessor;
import org.sindice.siren.qparser.keyword.processors.DatatypeQueryNodeProcessor;
import org.sindice.siren.qparser.keyword.processors.KeywordQueryNodeProcessorPipeline;
import org.sindice.siren.qparser.keyword.processors.QNamesProcessor;
import org.sindice.siren.qparser.keyword.processors.RootLevelTwigQueryNodeProcessor;
import org.sindice.siren.search.node.MultiNodeTermQuery;
import org.sindice.siren.search.node.MultiNodeTermQuery.RewriteMethod;
import org.sindice.siren.search.node.TwigQuery;
import org.sindice.siren.util.JSONDatatype;
import org.sindice.siren.util.XSDDatatype;

/**
 * This is used to configure parameters for the {@link KeywordQueryNodeProcessorPipeline}.
 *
 * <p>
 *
 * Copied from {@link StandardQueryConfigHandler} and adapted to SIREn.
 */
public class KeywordQueryConfigHandler
extends QueryConfigHandler {

  /**
   * Class holding the {@link KeywordQueryNodeProcessorPipeline} options.
   */
  final public static class KeywordConfigurationKeys {

    /**
     * Key used to set the qnames mapping.
     * @see QNamesProcessor
     */
    final public static ConfigurationKey<Properties> QNAMES = ConfigurationKey.newInstance();

    /**
     * Key used to set if {@link TwigQuery}s are allowed.
     * @see AllowTwigProcessor
     * @see LuceneProxyQueryNodeProcessor
     */
    final public static ConfigurationKey<Boolean> ALLOW_TWIG = ConfigurationKey.newInstance();

    /**
     * Key used to set if fuzzy and wildcard queries are allowed.
     * @see AllowFuzzyAndWildcardProcessor
     */
    final public static ConfigurationKey<Boolean> ALLOW_FUZZY_AND_WILDCARD = ConfigurationKey.newInstance();

    /**
     * Key used to set the default root level of a {@link TwigQuery}. The level of
     * nested TwigQueries increments with the default root level as offset.
     * @see RootLevelTwigQueryNodeProcessor
     */
    final public static ConfigurationKey<Integer> ROOT_LEVEL = ConfigurationKey.newInstance();

    /**
     * Key used to set the pair of Datatype, e.g., {@link XSDDatatype#XSD_STRING},
     * and its associated {@link Analyzer}.
     * @see DatatypeQueryNodeProcessor
     */
    final public static ConfigurationKey<Map<String, Analyzer>> DATATYPES_ANALYZERS = ConfigurationKey.newInstance();

    /**
     * Key used to set the {@link RewriteMethod} used when creating queries
     *
     * @see KeywordQueryParser#setMultiTermRewriteMethod(MultiNodeTermQuery.RewriteMethod)
     * @see KeywordQueryParser#getMultiTermRewriteMethod()
     */
    final public static ConfigurationKey<MultiNodeTermQuery.RewriteMethod> MULTI_NODE_TERM_REWRITE_METHOD = ConfigurationKey.newInstance();

  }

  public KeywordQueryConfigHandler() {
    // Add listener that will build the FieldConfig attributes.
    // TODO: setting field configuration is to be deprecated once datatypes
    // in SIREn are correctly handled.
    this.addFieldConfigListener(new FieldDateResolutionFCListener(this));

    // Default Values
    this.set(ConfigurationKeys.ALLOW_LEADING_WILDCARD, false); // default in 2.9
    this.set(ConfigurationKeys.ANALYZER, null); //default value 2.4
    this.set(ConfigurationKeys.PHRASE_SLOP, 0); //default value 2.4
    this.set(ConfigurationKeys.LOWERCASE_EXPANDED_TERMS, true); //default value 2.4
    this.set(ConfigurationKeys.FIELD_BOOST_MAP, new LinkedHashMap<String, Float>());
    this.set(ConfigurationKeys.FUZZY_CONFIG, new FuzzyConfig());
    this.set(ConfigurationKeys.LOCALE, Locale.getDefault());
    this.set(ConfigurationKeys.FIELD_DATE_RESOLUTION_MAP, new HashMap<CharSequence, DateTools.Resolution>());

    // SIREn Default Values
    // This key is not used in SIREn. Instead, we use DATATYPES_ANALYZERS
    this.set(ConfigurationKeys.ANALYZER, null);
    // Set the default datatypes. Those are mandatory.
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put(XSDDatatype.XSD_STRING, new StandardAnalyzer(Version.LUCENE_40));
    datatypes.put(JSONDatatype.JSON_FIELD, new WhitespaceAnalyzer(Version.LUCENE_40));
    this.set(KeywordConfigurationKeys.DATATYPES_ANALYZERS, datatypes);

    this.set(KeywordConfigurationKeys.ALLOW_TWIG, true);
    this.set(ConfigurationKeys.ENABLE_POSITION_INCREMENTS, true);
    this.set(KeywordConfigurationKeys.ALLOW_FUZZY_AND_WILDCARD, true);
    this.set(ConfigurationKeys.DEFAULT_OPERATOR, Operator.AND);
    this.set(KeywordConfigurationKeys.MULTI_NODE_TERM_REWRITE_METHOD, MultiNodeTermQuery.CONSTANT_SCORE_AUTO_REWRITE_DEFAULT);
    this.set(KeywordConfigurationKeys.ROOT_LEVEL, 1);
  }

}
