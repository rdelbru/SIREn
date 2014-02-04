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
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.sindice.siren.qparser.json.nodes.ChildQueryNode;
import org.sindice.siren.qparser.json.nodes.DescendantQueryNode;
import org.sindice.siren.qparser.json.nodes.TwigQueryNode;
import org.sindice.siren.qparser.json.parser.LevelPropertyParser;
import org.sindice.siren.qparser.json.parser.RangePropertyParser;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.sindice.siren.search.node.NodeBooleanClause;
import org.sindice.siren.search.node.NodeQuery;
import org.sindice.siren.search.node.TwigQuery;

/**
 * Builds a {@link TwigQuery} object from a {@link TwigQueryNode} object.
 * <p>
 * Every children in the {@link TwigQueryNode} object must be already tagged
 * using {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} with a
 * {@link NodeQuery} object.
 * <p>
 * It takes in consideration if the children is a {@link ChildQueryNode} or
 * a {@link DescendantQueryNode} to define the clauses of the {@link TwigQuery}
 * object.
 * <p>
 * Relies on a {@link KeywordQueryParser} object to convert the root's node
 * boolean expression into a {@link NodeQuery}.
 */
public class TwigQueryNodeBuilder implements JsonQueryBuilder {

  private final KeywordQueryParser keywordParser;

  public TwigQueryNodeBuilder(final KeywordQueryParser keywordParser) {
    this.keywordParser = keywordParser;
  }

  @Override
  public TwigQuery build(final QueryNode queryNode) throws QueryNodeException {
    final TwigQueryNode twigNode = (TwigQueryNode) queryNode;
    final List<QueryNode> children = twigNode.getChildren();
    final TwigQuery query = new TwigQuery();

    // check if the node has a level constraint
    if (twigNode.getTag(LevelPropertyParser.LEVEL_PROPERTY) != null) {
      query.setLevelConstraint((Integer) twigNode.getTag(LevelPropertyParser.LEVEL_PROPERTY));
    }

    // check if the node has a node range constraint
    if (twigNode.getTag(RangePropertyParser.RANGE_PROPERTY) != null) {
      final int[] range = (int[]) twigNode.getTag(RangePropertyParser.RANGE_PROPERTY);
      query.setNodeConstraint(range[0], range[1]);
    }

    // process root query
    if (twigNode.hasRoot()) {
      final String rootExpr = twigNode.getRoot().toString();
      final String field = twigNode.getField().toString();
      query.addRoot((NodeQuery) keywordParser.parse(rootExpr, field));
    }

    // process child and descendant queries
    try {
      processChildren(children, query);
    }
    catch (final TooManyClauses ex) {
      throw new QueryNodeException(new MessageImpl(
          QueryParserMessages.TOO_MANY_BOOLEAN_CLAUSES,
          BooleanQuery.getMaxClauseCount(),
          twigNode.toQueryString(new EscapeQuerySyntaxImpl())), ex);
    }

    return query;
  }

  private static void processChildren(final List<QueryNode> children, final TwigQuery query) {
    for (final QueryNode child : children) {
      final Object obj = child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
      final NodeQuery nodeQuery = (NodeQuery) obj;

      // Append child queries
      if (child instanceof ChildQueryNode) {
        query.addChild(nodeQuery, getModifierValue(child));
      }
      // Append descendant queries
      else if (child instanceof DescendantQueryNode) {
        // A descendant node must always have a level constraint
        if (child.getTag(LevelPropertyParser.LEVEL_PROPERTY) == null) {
          throw new IllegalArgumentException("Invalid DescendantQueryNode received: no level constraint defined");
        }
        // set level constraint
        final int nodeLevel = (Integer) child.getTag(LevelPropertyParser.LEVEL_PROPERTY);
        // add descendant query
        query.addDescendant(nodeLevel, nodeQuery, getModifierValue(child));
      }
      else {
        throw new IllegalArgumentException("Invalid QueryNode received: " + child.getClass().getSimpleName());
      }
    }
  }

  private static NodeBooleanClause.Occur getModifierValue(final QueryNode node) {
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
    return NodeBooleanClause.Occur.SHOULD;
  }

}
