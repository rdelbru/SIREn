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
package org.sindice.siren.solr.qparser.tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.apache.solr.analysis.BaseTokenizerFactory;
import org.apache.solr.analysis.TokenFilterFactory;
import org.apache.solr.analysis.TokenizerChain;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.DefaultSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.schema.FieldType;
import org.apache.solr.search.QParser;
import org.sindice.siren.solr.analysis.NTripleQueryTokenizerFactory;
import org.sindice.siren.solr.schema.Datatype;
import org.sindice.siren.solr.schema.SirenField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link TupleQParser} is in charge of executing a Tuple query request.
 */
public abstract class TupleQParser extends QParser {

  /**
   * Field boosts
   */
  Map<String, Float> boosts;

  /**
   * Flag for scattered multi-field match
   */
  protected boolean scattered = false;

  /**
   * The default query operator
   */
  private Operator defaultOp = Operator.AND;

  private static final Logger logger = LoggerFactory.getLogger(TupleQParser.class);

  public TupleQParser(final Map<String, Float> boosts, final String qstr,
                        final SolrParams localParams, final SolrParams params,
                        final SolrQueryRequest req) {
    super(qstr, localParams, params, req);
    this.boosts = boosts;
    final SolrParams solrParams = localParams == null ? params : new DefaultSolrParams(localParams, params);
    this.checkFieldTypes();
    this.initTupleQueryFieldOperator(solrParams);
    // try to get default operator from schema
    defaultOp = this.getReq().getSchema().getSolrQueryParser(null).getDefaultOperator();
  }

  /**
   * Check if all fields are of type {@link SirenField}.
   */
  private void checkFieldTypes() {
    for (final String fieldName : boosts.keySet()) {
      final FieldType fieldType = req.getSchema().getFieldType(fieldName);
      if (!(fieldType instanceof SirenField)) {
        throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
          "FieldType: " + fieldName + " (" + fieldType.getTypeName() + ") do not support NTriple Query");
      }
    }
  }

  /**
   * Parse the Tuple query field operator params.
   * <br>
   * If there is no field boost specified, use by default the disjunction
   * operator.
   */
  protected abstract void initTupleQueryFieldOperator(final SolrParams solrParams);

  /**
   * Initialise the Tuple query analyzer
   */
  private Analyzer initAnalyzer() {
    return new TokenizerChain(getTupleTokenizerFactory(), new TokenFilterFactory[0]);
  }

  /**
   * Return the implementation for tokenizing the tuple, e.g., a {@link NTripleQueryTokenizerFactory};
   * @return
   */
  protected abstract BaseTokenizerFactory getTupleTokenizerFactory();
  
  @Override
  public Query parse() throws ParseException {
    final Version version = req.getCore().getSolrConfig().luceneMatchVersion;
    final Analyzer nqAnalyzer = this.initAnalyzer();
    final DefaultOperatorAttribute.Operator defaultOp = this.getDefaultOperator();

    if (boosts.size() == 1) {
      final String field = boosts.keySet().iterator().next();
      final Map<String, Analyzer> datatypeConfig = this.initDatatypeConfig(field);
      return parseTupleQuery(qstr, version, field, nqAnalyzer, datatypeConfig,
      defaultOp);
    }
    else {
      final Map<String, Map<String, Analyzer>> datatypeConfigs = this.initDatatypeConfigs(boosts);
      return parseTupleQuery(qstr, version, boosts, nqAnalyzer, datatypeConfigs,
      defaultOp);
    }
  }
  
  /**
   * Parse the tuple query
   * @param qstr
   * @param matchVersion
   * @param field
   * @param tupleAnalyzer
   * @param datatypeConfig
   * @param op
   * @return
   * @throws ParseException
   */
  protected abstract Query parseTupleQuery(final String qstr,
                                           final Version matchVersion,
                                           final String field,
                                           final Analyzer tupleAnalyzer,
                                           final Map<String, Analyzer> datatypeConfig,
                                           final DefaultOperatorAttribute.Operator op)
  throws ParseException;
  
  /**
   * Parse the tuple query
   * @param qstr
   * @param matchVersion
   * @param boosts
   * @param tupleAnalyzer
   * @param datatypeConfigs
   * @param op
   * @param scattered
   * @return
   * @throws ParseException
   */
  protected abstract Query parseTupleQuery(final String qstr,
                                           final Version matchVersion,
                                           final Map<String, Float> boosts,
                                           final Analyzer tupleAnalyzer,
                                           final Map<String, Map<String, Analyzer>> datatypeConfigs,
                                           final DefaultOperatorAttribute.Operator op)
  throws ParseException;

  /**
   * Retrieve the datatype query analyzers associated to this field
   */
  private Map<String, Analyzer> initDatatypeConfig(final String field) {
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

  /**
   * For each field in the boost map, retrieve the datatype query analyzers.
   */
  private Map<String, Map<String, Analyzer>> initDatatypeConfigs(final Map<String, Float> boosts) {
    final Map<String, Map<String, Analyzer>> datatypeConfigs = new HashMap<String, Map<String, Analyzer>>();

    for (final String field : boosts.keySet()) {
      datatypeConfigs.put(field, this.initDatatypeConfig(field));
    }

    return datatypeConfigs;
  }

  private DefaultOperatorAttribute.Operator getDefaultOperator() {
    if (defaultOp == Operator.OR) {
      return DefaultOperatorAttribute.Operator.OR;
    }
    return DefaultOperatorAttribute.Operator.AND;
  }

}
