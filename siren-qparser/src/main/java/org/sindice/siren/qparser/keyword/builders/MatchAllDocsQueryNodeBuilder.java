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
package org.sindice.siren.qparser.keyword.builders;

import org.apache.commons.lang.NotImplementedException;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.MatchAllDocsQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.search.MatchAllDocsQuery;

/**
 * Builds a {@link MatchAllDocsQuery} object from a
 * {@link MatchAllDocsQueryNode} object.
 */
public class MatchAllDocsQueryNodeBuilder implements KeywordQueryBuilder {

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
