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

package org.sindice.siren.solr.qparser;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.schema.FieldType;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QueryParsing;
import org.apache.solr.util.SolrPluginUtils;
import org.sindice.siren.solr.schema.Datatype;
import org.sindice.siren.solr.schema.SirenField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link SirenQParser} is in charge of parsing a SIREn query request.
 * <p>
 * Expand the query to multiple fields by constructing a disjunction of the
 * parsed query across the fields.
 * <p>
 * For each <code>nested</code> parameter in the request, its argument
 * is parsed as a subquery and added to the main query.
 * <p>
 * The default operator for use by the query parsers is {@link Operator#AND}. It
 * can be overwritten using the parameter {@link QueryParsing#OP}.
 */
public abstract class SirenQParser extends QParser {

  protected Properties qnames;

  private static final Logger
  logger = LoggerFactory.getLogger(SirenQParser.class);

  public SirenQParser(final String qstr, final SolrParams localParams,
                      final SolrParams params, final SolrQueryRequest req) {
    super(qstr, localParams, params, req);
  }

  /**
   * Set the QNames mapping for use in the query parser.
   */
  public void setQNames(final Properties qnames) {
    this.qnames = qnames;
  }

  @Override
  public Query parse() throws ParseException {
    if (qstr == null || qstr.length()==0) return null;
    final SolrParams solrParams = SolrParams.wrapDefaults(localParams, params);
    final Map<String, Float> boosts = parseQueryFields(req.getSchema(), solrParams);

    final BooleanQuery main = this.getMainQuery(boosts, qstr);
    this.addNestedQuery(main, solrParams);

    return main;
  }

  /**
   * Build the main query that will be executed. Expand to multiple fields if
   * necessary.
   * @throws ParseException
   */
  private BooleanQuery getMainQuery(final Map<String, Float> boosts, final String qstr)
  throws ParseException {
    // We disable the coord because this query is an artificial construct
    final BooleanQuery query = new BooleanQuery(true);
    for (final String field : boosts.keySet()) {
      final Map<String, Analyzer> datatypeConfig = this.getDatatypeConfig(field);
      final Query q = this.parse(field, qstr, datatypeConfig);
      if (boosts.get(field) != null) {
        q.setBoost(boosts.get(field));
      }
      query.add(q, Occur.SHOULD);
    }
    return query;
  }

  /**
   * Build the nested queries and add them as a (MUST) clause of the main query.
   */
  private void addNestedQuery(final BooleanQuery main, final SolrParams solrParams)
  throws ParseException {
    if (solrParams.getParams("nested") != null) {
      for (final String nested : solrParams.getParams("nested")) {
        final QParser baseParser = this.subQuery(nested, null);
        main.add(baseParser.getQuery(), Occur.MUST);
      }
    }
  }

  protected abstract Query parse(final String field, final String qstr,
                                 final Map<String, Analyzer> datatypeConfig)
  throws ParseException;

  /**
   * Create a new QParser for parsing an embedded nested query.
   * <p>
   * Remove the nested parameters from the original request to avoid infinite
   * recursion.
   */
  @Override
  public QParser subQuery(final String q, final String defaultType)
  throws ParseException {
    final QParser nestedParser = super.subQuery(q, defaultType);
    final NamedList<Object> params = nestedParser.getParams().toNamedList();
    params.remove("nested");
    nestedParser.setParams(SolrParams.toSolrParams(params));
    return nestedParser;
  }

  /**
   * Retrieve the datatype query analyzers associated to this field
   */
  private Map<String, Analyzer> getDatatypeConfig(final String field) {
    final Map<String, Analyzer> datatypeConfig = new HashMap<String, Analyzer>();
    final SirenField fieldType = (SirenField) req.getSchema().getFieldType(field);
    final Map<String, Datatype> datatypes = fieldType.getDatatypes();

    for (final Entry<String, Datatype> e : datatypes.entrySet()) {

      if (e.getValue().getQueryAnalyzer() == null) {
        throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
          "Configuration Error: No analyzer defined for type 'query' in " +
          "datatype " + e.getKey());
      }

      datatypeConfig.put(e.getKey(), e.getValue().getQueryAnalyzer());
    }

    return datatypeConfig;
  }

  protected Operator getDefaultOperator() {
    final String val = params.get(QueryParsing.OP);
    Operator defaultOp = Operator.AND; // default AND operator
    if (val != null) {
      defaultOp = "AND".equals(val) ? Operator.AND : Operator.OR;
    }
    return defaultOp;
  }

  /**
   * Uses {@link SolrPluginUtils#parseFieldBoosts(String)} with the 'qf'
   * parameter. Falls back to the 'df' parameter or
   * {@link org.apache.solr.schema.IndexSchema#getDefaultSearchFieldName()}.
   */
  public static Map<String, Float> parseQueryFields(final IndexSchema indexSchema, final SolrParams solrParams)
  throws ParseException {
    final Map<String, Float> queryFields = SolrPluginUtils.parseFieldBoosts(solrParams.getParams(SirenParams.QF));
    if (queryFields.isEmpty()) {
      final String df = QueryParsing.getDefaultField(indexSchema, solrParams.get(CommonParams.DF));
      if (df == null) {
        throw new ParseException("Neither "+SirenParams.QF+", "+CommonParams.DF +", nor the default search field are present.");
      }
      queryFields.put(df, 1.0f);
    }
    checkFieldTypes(indexSchema, queryFields);
    return queryFields;
  }

  /**
   * Check if all fields are of type {@link SirenField}.
   */
  private static void checkFieldTypes(final IndexSchema indexSchema, final Map<String, Float> queryFields) {
    for (final String fieldName : queryFields.keySet()) {
      final FieldType fieldType = indexSchema.getFieldType(fieldName);
      if (!(fieldType instanceof SirenField)) {
        throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
          "FieldType: " + fieldName + " (" + fieldType.getTypeName() + ") do not support Siren's tree query");
      }
    }
  }

}
