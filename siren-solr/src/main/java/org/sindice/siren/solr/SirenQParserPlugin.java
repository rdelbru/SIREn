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
 * @project siren
 * @author Renaud Delbru [ 29 Oct 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr;

import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * A Generic query plugin designed to support all types of SIREn queries
 * (keyword, ntriple, entity) over multiple fields.<br>
 * Queries of different types are intersected together using a BooleanQuery.
 * E.g., if a keyword query and a NTriple query is defined in the parameter,
 * then the computed results must match the two queries.
 * </p>
 *
 * <p>
 * All of the following options may be configured for this plugin
 * in the solrconfig as defaults, and may be overriden as request parameters.
 * The main parameter - q - is reserved for simple keyword queries.
 * </p>
 *
 * <ul>
 * <li> qf - (Query Fields) fields and boosts to use when building
 *           simple keyword queries from the users query.  Format is:
 *           "<code>fieldA^1.0 fieldB^2.2</code>".
 *           This param can be specified multiple times, and the fields
 *           are additive. If it is not specified, the default field is used
 *           instead.
 * </li>
 * <li> nq - (NTriple Query) a NTriple query from the user. This param can be
 *            specified only one time.
 * </li>
 * <li> nqf - (NTriple Query Fields) fields and boosts to use when building
 *            ntriple queries. Format is: "<code>fieldA^1.0 fieldB^2.2</code>".
 *            This param can be specified multiple times, and the fields are
 *            additive. If it is not specified, the default field is used
 *            instead.
 * </li>
 * </ul>
 *
 * <p>
 * The following options are only available as request params...
 * </p>
 *
 * <ul>
 * <li>   q - (Query) the raw unparsed, unescaped, keyword query from the user.
 * </li>
 * <li>sort - (Order By) list of fields and direction to sort on.
 * </li>
 * </ul>
 */
public class SirenQParserPlugin extends QParserPlugin {

  public static String NAME = "siren";

  private static final
  Logger logger = LoggerFactory.getLogger(SirenQParserPlugin.class);

  public void init(final NamedList args) {}

  @Override
  public QParser createParser(final String qstr, final SolrParams localParams,
                              final SolrParams params, final SolrQueryRequest req) {
    return new SirenQParser(qstr, localParams, params, req);
  }

}
