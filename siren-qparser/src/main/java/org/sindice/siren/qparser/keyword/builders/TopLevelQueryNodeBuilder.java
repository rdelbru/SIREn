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

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.TopLevelQueryNode;
import org.sindice.siren.qparser.keyword.processors.TopLevelQueryNodeProcessor;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;
import org.sindice.siren.search.node.NodeQuery;

/**
 * Visits each node in a {@link TopLevelQueryNode} and wraps each
 * {@link NodeQuery} object tagged with
 * {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} into a
 * {@link LuceneProxyNodeQuery}.
 * 
 * <p>
 * 
 * This builder is called at the last step of the query building. If the key
 * {@link KeywordConfigurationKeys#ALLOW_TWIG} is <code>false</code>, this
 * builder will not be called because it is removed from the {@link QueryNode}
 * tree (see {@link TopLevelQueryNodeProcessor}).
 */
public class TopLevelQueryNodeBuilder
implements KeywordQueryBuilder {

  @Override
  public Query build(final QueryNode queryNode)
  throws QueryNodeException {
    final TopLevelQueryNode top = (TopLevelQueryNode) queryNode;
    final Query q = (Query) top.getChildren().get(0)
                               .getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);

    return this.wrap(q);
  }

  /**
   * Wraps a {@link NodeQuery} into a {@link LuceneProxyNodeQuery}.
   * This method is applied on each clause of a {@link BooleanQuery}.
   */
  private Query wrap(final Query q)
  throws QueryNodeException {
    if (q instanceof BooleanQuery) {
      for (final BooleanClause clause: ((BooleanQuery) q).clauses()) {
        final Query cq = clause.getQuery();
        clause.setQuery(this.wrap(cq));
      }
      return q;
    } else if (q instanceof NodeQuery) {
      return new LuceneProxyNodeQuery((NodeQuery) q);
    } else {
      throw new QueryNodeException(new Error("Expected a BooleanQuery or a NodeQuery: got '" +
      q.getClass().getCanonicalName() + "'"));
    }
  }

}
