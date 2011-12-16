/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
package org.sindice.siren.solr.qparser.ntriple;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.apache.solr.analysis.BaseTokenizerFactory;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.sindice.siren.qparser.ntriple.NTripleQueryParser;
import org.sindice.siren.solr.SirenParams;
import org.sindice.siren.solr.analysis.NTripleQueryTokenizerFactory;
import org.sindice.siren.solr.qparser.tuple.TupleQParser;

/**
 * The {@link NTripleQParser} is in charge of executing a NTriple query request.
 * <p>
 * Instantiate and execute the {@link NTripleQueryParser} based on the Solr
 * configuration and parameters.
 */
public class NTripleQParser extends TupleQParser {

  /**
   * @param boosts
   * @param qstr
   * @param localParams
   * @param params
   * @param req
   */
  public NTripleQParser(Map<String, Float> boosts,
                        String qstr,
                        SolrParams localParams,
                        SolrParams params,
                        SolrQueryRequest req) {
    super(boosts, qstr, localParams, params, req);
  }

  @Override
  protected BaseTokenizerFactory getTupleTokenizerFactory() {
    return new NTripleQueryTokenizerFactory();
  }

  @Override
  protected void initTupleQueryFieldOperator(SolrParams solrParams) {
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

  @Override
  protected Query parseTupleQuery(String qstr,
                                  Version matchVersion,
                                  String field,
                                  Analyzer tupleAnalyzer,
                                  Map<String, Analyzer> datatypeConfig,
                                  DefaultOperatorAttribute.Operator op)
  throws ParseException {
    return NTripleQueryParser.parse(qstr, matchVersion, field, tupleAnalyzer,
      datatypeConfig, op);
  }

  @Override
  protected Query parseTupleQuery(String qstr,
                                  Version matchVersion,
                                  Map<String, Float> boosts,
                                  Analyzer tupleAnalyzer,
                                  Map<String, Map<String, Analyzer>> datatypeConfigs,
                                  DefaultOperatorAttribute.Operator op)
  throws ParseException {
    return NTripleQueryParser.parse(qstr, matchVersion, boosts,
      tupleAnalyzer, datatypeConfigs, op, scattered);
  }

}
