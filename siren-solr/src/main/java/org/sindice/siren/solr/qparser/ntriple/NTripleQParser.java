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
package org.sindice.siren.solr.qparser.ntriple;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.search.Query;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.apache.solr.common.params.DefaultSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.schema.FieldType;
import org.apache.solr.search.QParser;
import org.sindice.siren.qparser.ntriple.NTripleQParserImpl;
import org.sindice.siren.qparser.ntriple.NTripleQueryParser;
import org.sindice.siren.solr.SirenParams;
import org.sindice.siren.solr.analysis.MultiQueryAnalyzerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NTripleQParser extends QParser {

  /**
   * The NTriple Parser implementation
   */
  NTripleQParserImpl lparser;

  /**
   * Field boosts
   */
  Map<String, Float> boosts;

  /**
   * Flag for scattered multi-field match
   */
  boolean scattered = false;

  /**
   * The three associated query analyzers (NTriple, URI, Literal)
   */
  Analyzer ntripleAnalyzer;
  Analyzer uriAnalyzer;
  Analyzer literalAnalyzer;

  /**
   * The default query operator
   */
  Operator defaultOp = Operator.AND;

  private static final
  Logger logger = LoggerFactory.getLogger(NTripleQParser.class);

  public NTripleQParser(final Map<String, Float> boosts, final String qstr,
                        final SolrParams localParams, final SolrParams params,
                        final SolrQueryRequest req) {
    super(qstr, localParams, params, req);
    this.boosts = boosts;
    final SolrParams solrParams = localParams == null ? params : new DefaultSolrParams(localParams, params);
    this.initNTripleQueryFieldOperator(solrParams);
    // try to get default operator from schema
    defaultOp = this.getReq().getSchema().getSolrQueryParser(null).getDefaultOperator();
  }

  /**
   * Parse the NTriple query field operator params (nqfo).
   * <br>
   * If there is no field boost specified, use by default the disjunction
   * operator.
   */
  private void initNTripleQueryFieldOperator(final SolrParams solrParams) {
    String nqfo = null;
    if ((nqfo = solrParams.get(SirenParams.NQFO)) != null) {
      if (nqfo.equals("disjunction")) {
        scattered = false;
      }
      else if (nqfo.equals("scattered")) {
        scattered = true;
      }
      else {
        throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, "Invalid " +
        		"NTripleQParser operator");
      }
    }
  }

  /**
   * Check if all the provided fields have the same field type otherwise throw
   * a solr exception.
   * @param boosts
   */
  private FieldType checkFieldType(final Map<String, Float> boosts) {
    final Set<FieldType> fieldTypes = new HashSet<FieldType>();
    for (final String fieldName : boosts.keySet()) {
      fieldTypes.add(req.getSchema().getFieldType(fieldName));
    }
    if (fieldTypes.size() != 1) { // cannot be 0 - param nqf must have at least one value
      throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, "querying " +
      		"multiple fields with different types");
    }
    return fieldTypes.iterator().next();
  }

  /**
   * Find the associated query analyzers for the given field type.
   * The query analyzers should be provided through the
   * {@link MultiQueryAnalyzerWrapper}.<br>
   * Derive the names of the field types from the name of the given field type.
   * There is three field types expected:
   * <ul>
   * <li> main: define the main NTriple query analyzer
   * <li> uri: define the analyzer for URIs
   * <li> literal: define the analyzer for literals
   * <ul>
   *
   * @param fieldType The field type of the ntriple fields to query
   */
  private void initAnalyzers(final FieldType fieldType) {
    if (!(fieldType.getQueryAnalyzer() instanceof MultiQueryAnalyzerWrapper)) {
      throw new SolrException(ErrorCode.SERVER_ERROR, "MultiQueryAnalyzerWrapper" +
      		" expected for field type " + fieldType.getTypeName());
    }
    final MultiQueryAnalyzerWrapper wrapper = (MultiQueryAnalyzerWrapper) fieldType.getQueryAnalyzer();
    if ((ntripleAnalyzer = wrapper.getAnalyzer(fieldType.getTypeName() + "-main")) == null) {
      throw new SolrException(ErrorCode.SERVER_ERROR, "Field type definition " +
        fieldType.getTypeName() + "-main not defined");
    }
    if ((uriAnalyzer = wrapper.getAnalyzer(fieldType.getTypeName() + "-uri")) == null) {
      throw new SolrException(ErrorCode.SERVER_ERROR, "Field type definition " +
        fieldType.getTypeName() + "-uri not defined");
    }
    if ((literalAnalyzer = wrapper.getAnalyzer(fieldType.getTypeName() + "-literal")) == null) {
      throw new SolrException(ErrorCode.SERVER_ERROR, "Field type definition " +
        fieldType.getTypeName() + "-literal not defined");
    }
  }

  @Override
  public Query parse() throws ParseException {
    this.initAnalyzers(this.checkFieldType(boosts));
    final DefaultOperatorAttribute.Operator defaultOp = this.getDefaultOperator();
    if (boosts.size() == 1) {
      final String field = boosts.keySet().iterator().next();
      return NTripleQueryParser.parse(qstr, field, ntripleAnalyzer,
        uriAnalyzer, literalAnalyzer, defaultOp);
    }
    else {
      return NTripleQueryParser.parse(qstr, boosts, scattered, ntripleAnalyzer,
        uriAnalyzer, literalAnalyzer, defaultOp);
    }
  }

  private DefaultOperatorAttribute.Operator getDefaultOperator() {
    if (defaultOp == Operator.OR) {
      return DefaultOperatorAttribute.Operator.OR;
    }
    return DefaultOperatorAttribute.Operator.AND;
  }

}
