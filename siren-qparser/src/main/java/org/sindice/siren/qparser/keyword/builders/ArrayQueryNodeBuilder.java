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
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.builders.StandardQueryBuilder;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.nodes.ArrayQueryNode;
import org.sindice.siren.qparser.keyword.nodes.WildcardNodeQueryNode;
import org.sindice.siren.search.node.NodeBooleanClause;
import org.sindice.siren.search.node.NodeQuery;
import org.sindice.siren.search.node.TwigQuery;

/**
 * Builds an {@link ArrayQuery} from the children of a {@link ArrayQueryNode}.
 *
 * <p>
 *
 * Nested {@link ArrayQuery}s are transformed into a {@link TwigQuery} with
 * {@link WildcardNodeQueryNode} as the root (i.e., no root constraint).
 */
public class ArrayQueryNodeBuilder
implements StandardQueryBuilder {

  @Override
  public Query build(final QueryNode queryNode)
  throws QueryNodeException {
    final ArrayQueryNode arrayNode = (ArrayQueryNode) queryNode;
    final List<QueryNode> children = arrayNode.getChildren();
    final ArrayQuery arrayQuery = new ArrayQuery();

    for (final QueryNode child : children) {
      final Object v = child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
      if (v == null) { // DummyNode such as the EmptyNodeQueryNode
        continue;
      }
      if (v instanceof Query) {
        if (v instanceof ArrayQuery) {
          /*
           * Nested array query. It is transformed as a TwigQuery with empty root
           */
          final TwigQuery twigQuery = new TwigQuery();
          for (final Query qn : ((ArrayQuery) v).getElements()) {
            final NodeQuery valQuery = (NodeQuery) qn;
            twigQuery.addChild(valQuery, NodeQueryBuilderUtil.getModifierValue(child, NodeBooleanClause.Occur.MUST));
          }
          arrayQuery.addElement(twigQuery);
        } else {
          arrayQuery.addElement((NodeQuery) v);
        }
      } else {
        throw new QueryNodeException(new MessageImpl(QueryParserMessages.INVALID_SYNTAX,
          "Unexpected class of a Twig Query clause: " + v == null ? "null" : v.getClass().getName()));
      }
    }
    return arrayQuery;
  }

}
