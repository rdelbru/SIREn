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
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.sindice.siren.qparser.json.nodes.NodeQueryNode;
import org.sindice.siren.qparser.json.parser.LevelPropertyParser;
import org.sindice.siren.qparser.json.parser.RangePropertyParser;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.sindice.siren.search.node.NodeQuery;

/**
 * Builds a {@link NodeQuery} object from a {@link NodeQueryNode}
 * object.
 * <p>
 * Relies on a {@link KeywordQueryParser} object to convert the node boolean
 * expression into a {@link NodeQuery}.
 */
public class NodeQueryNodeBuilder implements JsonQueryBuilder {

  private final KeywordQueryParser keywordParser;

  public NodeQueryNodeBuilder(final KeywordQueryParser keywordParser) {
    this.keywordParser = keywordParser;
  }

  @Override
  public NodeQuery build(final QueryNode queryNode) throws QueryNodeException {
    final NodeQueryNode node = (NodeQueryNode) queryNode;
    final String field = node.getField().toString();
    final String expr = node.getValue().toString();
    final NodeQuery query = (NodeQuery) keywordParser.parse(expr, field);
    // check if the node has a level constraint
    if (node.getTag(LevelPropertyParser.LEVEL_PROPERTY) != null) {
      query.setLevelConstraint((Integer) node.getTag(LevelPropertyParser.LEVEL_PROPERTY));
    }
    // check if the node has a node range constraint
    if (node.getTag(RangePropertyParser.RANGE_PROPERTY) != null) {
      final int[] range = (int[]) node.getTag(RangePropertyParser.RANGE_PROPERTY);
      query.setNodeConstraint(range[0], range[1]);
    }
    return query;
  }

}
