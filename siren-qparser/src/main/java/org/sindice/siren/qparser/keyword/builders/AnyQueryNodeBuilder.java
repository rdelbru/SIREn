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

import java.util.List;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.AnyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.apache.lucene.search.Query;

/**
 * Builds a {@link BooleanQuery} of SHOULD clauses, possibly with
 * some minimum number to match.
 * 
 * @see BooleanQuery#setMinimumNumberShouldMatch(int)
 */
public class AnyQueryNodeBuilder implements KeywordQueryBuilder {

  public AnyQueryNodeBuilder() {
    // empty constructor
  }

  public BooleanQuery build(QueryNode queryNode) throws QueryNodeException {
    AnyQueryNode andNode = (AnyQueryNode) queryNode;

    BooleanQuery bQuery = new BooleanQuery();
    List<QueryNode> children = andNode.getChildren();

    if (children != null) {

      for (QueryNode child : children) {
        Object obj = child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);

        if (obj != null) {
          Query query = (Query) obj;

          try {
            bQuery.add(query, BooleanClause.Occur.SHOULD);
          } catch (TooManyClauses ex) {

            throw new QueryNodeException(new MessageImpl(
            /*
             * IQQQ.Q0028E_TOO_MANY_BOOLEAN_CLAUSES,
             * BooleanQuery.getMaxClauseCount()
             */
              QueryParserMessages.EMPTY_MESSAGE), ex);

          }

        }

      }

    }

    bQuery.setMinimumNumberShouldMatch(andNode.getMinimumMatchingElements());

    return bQuery;

  }

}
