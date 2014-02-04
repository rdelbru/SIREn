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
package org.sindice.siren.qparser.json.builders;

import java.util.List;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.parser.EscapeQuerySyntaxImpl;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.json.nodes.BooleanQueryNode;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;
import org.sindice.siren.search.node.NodeQuery;

/**
 * Builds a {@link BooleanQuery} object from a {@link BooleanQueryNode} object.
 * <p>
 * Every child in the {@link BooleanQueryNode} object must be already tagged
 * using {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} with a
 * {@link NodeQuery} object.
 * <p>
 * It takes in consideration if the children is a {@link ModifierQueryNode} to
 * define the {@link BooleanClause}.
 * <p>
 * Every child is wrapped into a {@link LuceneProxyNodeQuery}.
 */
public class BooleanQueryNodeBuilder implements JsonQueryBuilder {

  public BooleanQueryNodeBuilder() {}

  public BooleanQuery build(final QueryNode queryNode) throws QueryNodeException {
    final BooleanQueryNode booleanNode = (BooleanQueryNode) queryNode;
    final BooleanQuery bQuery = new BooleanQuery(true);
    final List<QueryNode> children = booleanNode.getChildren();

    for (final QueryNode child : children) {
      final Object obj = child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
      // wrap the query into a LuceneProxyNodeQuery
      final Query query = new LuceneProxyNodeQuery((NodeQuery) obj);
      try {
        bQuery.add(query, getModifierValue(child));
      }
      catch (final TooManyClauses ex) {
        throw new QueryNodeException(new MessageImpl(
            QueryParserMessages.TOO_MANY_BOOLEAN_CLAUSES, BooleanQuery
                .getMaxClauseCount(), queryNode
                .toQueryString(new EscapeQuerySyntaxImpl())), ex);
      }
    }
    return bQuery;
  }

  private static BooleanClause.Occur getModifierValue(final QueryNode node) {
    if (node instanceof ModifierQueryNode) {
      final ModifierQueryNode mNode = ((ModifierQueryNode) node);
      switch (mNode.getModifier()) {
      case MOD_REQ:
        return BooleanClause.Occur.MUST;

      case MOD_NOT:
        return BooleanClause.Occur.MUST_NOT;

      case MOD_NONE:
        return BooleanClause.Occur.SHOULD;
      }
    }
    return BooleanClause.Occur.SHOULD;
  }

}
