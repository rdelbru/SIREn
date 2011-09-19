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
 * @project siren
 * @author Renaud Delbru [ 29 Oct 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr;

import java.util.Map;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.solr.common.params.DefaultSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.util.SolrPluginUtils;
import org.sindice.siren.solr.qparser.keyword.KeywordQParser;
import org.sindice.siren.solr.qparser.ntriple.NTripleQParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SirenQParser extends QParser {

  protected Map<String, Float> keywordQueryFields;
  protected Map<String, Float> ntripleQueryFields;

  private static final
  Logger logger = LoggerFactory.getLogger(SirenQParser.class);

  public SirenQParser(final String qstr, final SolrParams localParams,
                      final SolrParams params, final SolrQueryRequest req) {
    super(qstr, localParams, params, req);
  }

  /* (non-Javadoc)
   * @see org.apache.solr.search.QParser#parse()
   */
  @Override
  public Query parse() throws ParseException {
    final SolrParams solrParams = localParams == null ? params : new DefaultSolrParams(localParams, params);
    // parse and init field boost
    this.initFieldBoost(solrParams);

    // the main query we will execute. we disable the coord because
    // this query is an artificial construct, and because it is not very useful
    // with SIREn fields.
    final BooleanQuery query = new BooleanQuery(true);

    try {
      if (qstr != null) { // if q param specified
        this.addKeywordQuery(query, solrParams);
      }
      if (params.get(SirenParams.NQ) != null) { // If nq param specified
        this.addNTripleQuery(query, solrParams);
      }
    }
    catch (final ParseException e) {
      // SRN-102: catch ParseException and log error
      logger.error(e.getMessage());
    }

    return query;
  }

  /**
   * Parse the field boost params (qf, nqf).
   * <br>
   * If there is no field boost specified, use the default field value.
   */
  private void initFieldBoost(final SolrParams solrParams) {
    keywordQueryFields = SolrPluginUtils.parseFieldBoosts(solrParams.getParams(SirenParams.KQF));
    if (0 == keywordQueryFields.size()) {
      keywordQueryFields.put(req.getSchema().getDefaultSearchFieldName(), 1.0f);
    }
    ntripleQueryFields = SolrPluginUtils.parseFieldBoosts(solrParams.getParams(SirenParams.NQF));
    if (0 == ntripleQueryFields.size()) {
      ntripleQueryFields.put(req.getSchema().getDefaultSearchFieldName(), 1.0f);
    }
  }

  protected void addKeywordQuery(final BooleanQuery query, final SolrParams solrParams)
  throws ParseException {
    // Rely on the SimpleQParser implementation for creating the keyword query
    final KeywordQParser parser = new KeywordQParser(keywordQueryFields, qstr,
      localParams, params, req);
    final Query parsedKeywordQuery = parser.parse();
    query.add(parsedKeywordQuery, BooleanClause.Occur.MUST);
  }

  protected void addNTripleQuery(final BooleanQuery query, final SolrParams solrParams)
  throws ParseException {
    final String nqstr = solrParams.get(SirenParams.NQ);
    // Rely on NTripleQParser for creating the ntriple query
    final NTripleQParser parser = new NTripleQParser(ntripleQueryFields,
      nqstr, localParams, params, req);
    final Query parsedNTripleQuery = parser.parse();
    query.add(parsedNTripleQuery, BooleanClause.Occur.MUST);
  }

}
