/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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
import org.apache.solr.common.params.DefaultSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.schema.FieldType;
import org.apache.solr.search.QParser;
import org.sindice.siren.qparser.keyword.KeywordQParserImpl;
import org.sindice.siren.solr.SirenParams;
import org.sindice.siren.solr.schema.SirenField;

/**
 * Solr implementation of the simple keyword query parser. This class is
 * coupled with {@link KeywordQParserImpl}.
 * <p>
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
   * The {@link SirenField#getQueryAnalyzer()} returns the keyword query
   * analyzer.
   *
   * @param boosts The field boosts
   * @return The per-field analyzer wrapper.
   */
  private Analyzer initAnalyzers(final Map<String, Float> boosts) {
    final Version version = req.getCore().getSolrConfig().luceneMatchVersion;
    final Analyzer defaultAnalyzer = new WhitespaceAnalyzer(version);
    final PerFieldAnalyzerWrapper analyzerWrapper = new PerFieldAnalyzerWrapper(defaultAnalyzer);

    // Add analyzers for each field type
    for (final String fieldName : boosts.keySet()) {
      final FieldType fieldType = req.getSchema().getFieldType(fieldName);
      // #getQueryAnalyzer for SirenField returns the keyword query analyzer
      analyzerWrapper.addAnalyzer(fieldName, fieldType.getQueryAnalyzer());
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
