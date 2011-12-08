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
package org.sindice.siren.qparser.tuple.query.builders;

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
