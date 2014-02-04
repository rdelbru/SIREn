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
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.nodes.DatatypeQueryNode;
import org.sindice.siren.qparser.keyword.nodes.NodeBooleanQueryNode;
import org.sindice.siren.search.node.NodeBooleanClause;
import org.sindice.siren.search.node.NodeBooleanQuery;
import org.sindice.siren.search.node.NodeQuery;

/**
 * Builds a {@link NodeBooleanQuery} object from a {@link NodeBooleanQueryNode}
 * object.
 *
 * <p>
 *
 * Every children in the {@link NodeBooleanQueryNode} object must be already tagged
 * using {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} with a {@link Query}
 * object.
 *
 * <p>
 *
 * It takes in consideration if the children is a {@link ModifierQueryNode} to
 * define the {@link NodeBooleanClause}.
 */
public class NodeBooleanQueryNodeBuilder implements KeywordQueryBuilder {

  public NodeBooleanQueryNodeBuilder() {
  }

  public NodeQuery build(final QueryNode queryNode)
  throws QueryNodeException {
    final NodeBooleanQueryNode booleanNode = (NodeBooleanQueryNode) queryNode;
    final List<QueryNode> children = booleanNode.getChildren();
    final NodeBooleanQuery bq = new NodeBooleanQuery();

    if (children == null) {
      return bq; // return empty boolean query
    }

    // If more than one child, wrap them into a NodeBooleanQuery
    if (children.size() > 1) {
      for (final QueryNode child : children) {
        final Object obj = child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
        if (obj != null) {
          if (obj instanceof NodeQuery) {
            final QueryNode mod;
            if (child instanceof DatatypeQueryNode) {
              mod = ((DatatypeQueryNode) child).getChild();
            } else {
              mod = child;
            }
            bq.add((NodeQuery) obj,
              NodeQueryBuilderUtil.getModifierValue(mod, NodeBooleanClause.Occur.SHOULD));
          }
          else {
            throw new QueryNodeException(new Error("Expected NodeQuery: got '" +
            	obj.getClass().getCanonicalName() + "'"));
          }
        }
      }
      return bq;
    }
    // If only one child, return it directly
    else {
      final Object obj = children.get(0).getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
      if (obj != null) {
        if (obj instanceof NodeQuery) {
          return (NodeQuery) obj;
        }
        else {
          throw new QueryNodeException(new Error("Non NodeQuery query '" +
            obj.getClass().getCanonicalName() + "' received"));
        }
      }
      return bq; // return empty boolean query
    }
  }

}
