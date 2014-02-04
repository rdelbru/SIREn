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

package org.sindice.siren.solr.qparser.keyword;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.search.Query;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.sindice.siren.solr.qparser.SirenQParser;

/**
 * Implementation of {@link SirenQParser} for the {@link KeywordQueryParser}.
 *
 * <p>
 *
 * The {@link KeywordQParser} is in charge of parsing a SIREn's keyword query
 * request.
 */
public class KeywordQParser extends SirenQParser {

  public KeywordQParser(final String qstr, final SolrParams localParams,
                        final SolrParams params, final SolrQueryRequest req) {
    super(qstr, localParams, params, req);
  }

  @Override
  protected Query parse(final String field, final String qstr,
                        final Map<String, Analyzer> datatypeConfig) {
    final KeywordQueryParser parser = new KeywordQueryParser();
    parser.setDefaultOperator(this.getDefaultOperator());
    parser.setQNames(qnames);
    parser.setDatatypeAnalyzers(datatypeConfig);

    try {
      return parser.parse(qstr, field);
    }
    catch (final QueryNodeException e) {
      throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, e);
    }
  }

}
