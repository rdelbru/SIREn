/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 21 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.ntriple.query.builders;

import org.apache.commons.lang.NotImplementedException;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.nodes.MatchAllDocsQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.search.MatchAllDocsQuery;

/**
 * Builds a {@link MatchAllDocsQuery} object from a
 * {@link MatchAllDocsQueryNode} object.
 */
public class MatchAllDocsQueryNodeBuilder implements ResourceQueryBuilder {

  public MatchAllDocsQueryNodeBuilder() {
    // empty constructor
  }

  public MatchAllDocsQuery build(QueryNode queryNode) throws QueryNodeException {
    throw new NotImplementedException("MatchAllDocsQueries are not supported yet");
    
//TODO: To implement when Siren will support MatchAllDocs queries
//    // validates node
//    if (!(queryNode instanceof MatchAllDocsQueryNode)) {
//      throw new QueryNodeException(new MessageImpl(
//          QueryParserMessages.LUCENE_QUERY_CONVERSION_ERROR, queryNode
//              .toQueryString(new EscapeQuerySyntaxImpl()), queryNode.getClass()
//              .getName()));
//    }
//
//    return new MatchAllDocsQuery();

  }

}
