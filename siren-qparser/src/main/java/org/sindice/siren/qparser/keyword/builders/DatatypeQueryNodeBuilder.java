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
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.nodes.DatatypeQueryNode;
import org.sindice.siren.search.node.NodePrimitiveQuery;

/**
 * Builds no object, it only returns the {@link Query} object set on the
 * {@link DatatypeQueryNode} child using a
 * {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} tag.
 */
public class DatatypeQueryNodeBuilder implements KeywordQueryBuilder {

  public Query build(final QueryNode queryNode) throws QueryNodeException {
    final DatatypeQueryNode dtNode = (DatatypeQueryNode) queryNode;
    final QueryNode child = dtNode.getChild();

    assert queryNode.getChildren().size() == 1;

    final Query query = (Query) child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
    this.setDatatype(child, dtNode.getDatatype());
    return query;
  }

  /**
   * Sets the given datatype on each descendant of the {@link DatatypeQueryNode}
   */
  private void setDatatype(final QueryNode node,
                           final String datatype) {
    final Query query = (Query) node.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
    if (query instanceof NodePrimitiveQuery) {
      ((NodePrimitiveQuery) query).setDatatype(datatype);
    }
    if (node.isLeaf()) {
      return;
    }
    for (final QueryNode child : node.getChildren()) {
      this.setDatatype(child, datatype);
    }
  }

}
