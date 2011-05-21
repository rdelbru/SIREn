/**
 * Copyright 2010, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren-solr
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.qparser.keyword;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.apache.solr.common.params.DefaultSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.schema.FieldType;
import org.apache.solr.search.QParser;
import org.sindice.siren.qparser.keyword.KeywordQParserImpl;
import org.sindice.siren.solr.SirenParams;
import org.sindice.siren.solr.analysis.MultiQueryAnalyzerWrapper;

/**
 * Solr implementation of the simple keyword query parser. This class is
 * coupled with KeywordQParserImpl.<br>
 * By default, field queries are disabled in order to differentiate QNames from
 * field queries. However, keyword query can still be expanded to multiple fields.<br>
 * When field query is enabled, only semicolon found in URIs are escaped.
 */
public class KeywordQParser extends QParser {

  private final KeywordQParserImpl impl;

  public KeywordQParser(final Map<String, Float> boosts, final String qstr,
                       final SolrParams localParams, final SolrParams params,
                       final SolrQueryRequest req) {
    super(qstr, localParams, params, req);
    final SolrParams solrParams = localParams == null ? params : new DefaultSolrParams(localParams, params);

    impl = new KeywordQParserImpl(this.initAnalyzers(boosts), boosts,
      this.getDisableFieldParam(solrParams));
    // try to get default operator from schema
    this.setDefaultOperator(this.getReq().getSchema().getSolrQueryParser(null).getDefaultOperator());
  }

  /**
   * Initialize the "per-field" analyzer. Walk over the field boosts and
   * retrieve the associated query analyzer. <br>
   * For each field type, check if there is not an associated keyword field
   * type, i.e., a field type with an identical name with "-keyword" appended.
   *
   * @param boosts The field boosts
   * @return The per-field analyzer wrapper.
   */
  private Analyzer initAnalyzers(final Map<String, Float> boosts) {
    final Analyzer defaultAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_31);
    final PerFieldAnalyzerWrapper analyzerWrapper = new PerFieldAnalyzerWrapper(defaultAnalyzer);

    // Add analyzers for each field type
    for (final String fieldName : boosts.keySet()) {
      final FieldType fieldType = req.getSchema().getFieldType(fieldName);
      // check if there is a MultiQueryAnalyzerWrapper
      // and extract the associated keyword query analzyer
      if (fieldType.getQueryAnalyzer() instanceof MultiQueryAnalyzerWrapper) {
        final MultiQueryAnalyzerWrapper wrapper = (MultiQueryAnalyzerWrapper) fieldType.getQueryAnalyzer();
        Analyzer keywordAnalyzer;
        if ((keywordAnalyzer = wrapper.getAnalyzer(fieldType.getTypeName() + "-keyword")) == null) {
          throw new SolrException(ErrorCode.SERVER_ERROR, "Field type definition " +
            fieldType.getTypeName() + "-keyword not defined");
        }
        analyzerWrapper.addAnalyzer(fieldName, keywordAnalyzer);
      }
      else {
        analyzerWrapper.addAnalyzer(fieldName, fieldType.getQueryAnalyzer());
      }
    }
    return analyzerWrapper;
  }

  /**
   * Parse the parameter (disableField) to disable field queries. Disabled by
   * default.
   */
  private boolean getDisableFieldParam(final SolrParams solrParams) {
    String param = null;
    if ((param = solrParams.get(SirenParams.KQ_DISABLE_FIELD)) != null) {
      if (param.equals("true")) {
        return true;
      }
      else if (param.equals("false")) {
        return false;
      }
      else {
        throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, "Invalid " +
            "KeywordQParser parameter: not a boolean");
      }
    }
    return true;
  }

  @Override
  public Query parse() throws ParseException {
    return impl.parse(this.getString());
  }

  public void setDefaultOperator(final Operator operator) {
    impl.setDefaultOperator(operator);
  }

}
