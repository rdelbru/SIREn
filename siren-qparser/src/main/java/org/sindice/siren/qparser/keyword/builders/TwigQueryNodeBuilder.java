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
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.builders.StandardQueryBuilder;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.nodes.ArrayQueryNode;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;
import org.sindice.siren.qparser.keyword.nodes.WildcardNodeQueryNode;
import org.sindice.siren.search.node.NodeBooleanClause;
import org.sindice.siren.search.node.NodeBooleanClause.Occur;
import org.sindice.siren.search.node.NodeQuery;
import org.sindice.siren.search.node.TwigQuery;

/**
 * Builds a {@link TwigQuery} from a {@link TwigQueryNode}. Both the root
 * and the child must be already tagged using a
 * {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} with a {@link Query} object.
 *
 * <p>
 *
 * It takes in consideration if a value is a {@link ModifierQueryNode} to
 * define its {@link NodeBooleanClause}.
 */
public class TwigQueryNodeBuilder
implements StandardQueryBuilder {

  public TwigQueryNodeBuilder() {}

  @Override
  public Query build(final QueryNode queryNode)
  throws QueryNodeException {
    final TwigQueryNode tqn = (TwigQueryNode) queryNode;
    final QueryNode root = tqn.getRoot();
    final QueryNode child = tqn.getChild();
    final TwigQuery twigQuery;
    final int rootLevel = tqn.getRootLevel();

    if (root == null && child == null) {
      throw new QueryNodeException(new MessageImpl(QueryParserMessages.EMPTY_MESSAGE));
    }
    if (tqn.getChildren().size() != 2) {
      throw new IllegalArgumentException("A TwigQueryNode cannot have more " +
          "than 2 children:\n" + tqn.getChildren().toString());
    }
    if (child instanceof WildcardNodeQueryNode &&
        root instanceof WildcardNodeQueryNode) {
      throw new QueryNodeException(new MessageImpl("Twig with both root and " +
          "child empty is not allowed."));
    }
    // Build the root operand
    if (root instanceof WildcardNodeQueryNode) { // Empty root query
      twigQuery = new TwigQuery(rootLevel);
    } else {
      final Object attQuery = root.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
      if (attQuery != null) {
        twigQuery = new TwigQuery(rootLevel);
        twigQuery.addRoot((NodeQuery) attQuery);
      } else {
        throw new QueryNodeException(new MessageImpl(QueryParserMessages.INVALID_SYNTAX,
        "Unable to get the root of the Twig query"));
      }
    }
    if (!(child instanceof WildcardNodeQueryNode)) {
      // Build the child operand
      final Object v = child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
      if (v instanceof ArrayQuery) { // array of children nodes
        final ArrayQueryNode aqn = (ArrayQueryNode) child;
        final List<Query> children = ((ArrayQuery) v).getElements();
        for (int i = 0; i < children.size(); i++) {
          twigQuery.addChild((NodeQuery) children.get(i),
            NodeQueryBuilderUtil.getModifierValue(aqn.getChildren().get(i), NodeBooleanClause.Occur.MUST));
        }
      } else if (v instanceof Query) {
        final NodeQuery valQuery = (NodeQuery) v;
        twigQuery.addChild(valQuery, Occur.MUST);
      } else {
        throw new QueryNodeException(new MessageImpl(QueryParserMessages.INVALID_SYNTAX,
          "Unexpected class of a Twig Query clause: " + v == null ? "null" : v.getClass().getName()));
      }
    }

    return twigQuery;
  }

}
