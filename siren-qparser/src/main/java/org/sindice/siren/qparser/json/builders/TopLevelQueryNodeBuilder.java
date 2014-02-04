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

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.json.nodes.BooleanQueryNode;
import org.sindice.siren.qparser.json.nodes.TopLevelQueryNode;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;
import org.sindice.siren.search.node.NodeQuery;

/**
 * Check if the child in the {@link TopLevelQueryNode} object is a
 * {@link BooleanQuery} or a {@link NodeQuery}, and wraps a {@link NodeQuery}
 * object into a {@link LuceneProxyNodeQuery}.
 * <p>
 * The child in the {@link TopLevelQueryNode} object must be tagged
 * using {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} with a
 * {@link Query} object.
 */
public class TopLevelQueryNodeBuilder implements JsonQueryBuilder {

  public TopLevelQueryNodeBuilder() {}

  @Override
  public Query build(final QueryNode queryNode) throws QueryNodeException {
    final TopLevelQueryNode topNode = (TopLevelQueryNode) queryNode;
    final QueryNode child = topNode.getChildren().get(0);
    final Object obj = child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);

    if (child instanceof BooleanQueryNode) {
      // no need to wrap the query object into a lucene proxy query
      return (Query) obj;
    }
    else {
      return new LuceneProxyNodeQuery((NodeQuery) obj);
    }
  }

}
