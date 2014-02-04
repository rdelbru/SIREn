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

import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.sindice.siren.search.node.NodeBooleanClause;
import org.sindice.siren.search.node.NodeQuery;

/**
 * Set of utility methods for building a {@link NodeQuery}.
 * 
 * @see TwigQueryNodeBuilder
 * @see ArrayQueryNodeBuilder
 * @see NodeBooleanQueryNodeBuilder
 */
class NodeQueryBuilderUtil {

  /**
   * Returns the {@link NodeBooleanClause.Occur} that corresponds to the node
   * modifier.
   *
   * @param node the {@link ModifierQueryNode}
   * @param def the default {@link NodeBooleanClause.Occur} to return
   *            if the node is not a {@link ModifierQueryNode}
   * @return the {@link NodeBooleanClause.Occur} of the query node
   */
  static NodeBooleanClause.Occur getModifierValue(final QueryNode node,
                                                  final NodeBooleanClause.Occur def) {
    if (node instanceof ModifierQueryNode) {
      final ModifierQueryNode mNode = ((ModifierQueryNode) node);
      switch (mNode.getModifier()) {
        case MOD_REQ:
          return NodeBooleanClause.Occur.MUST;
        case MOD_NOT:
          return NodeBooleanClause.Occur.MUST_NOT;
        case MOD_NONE:
          return NodeBooleanClause.Occur.SHOULD;
      }
    }
    return def;
  }

}
