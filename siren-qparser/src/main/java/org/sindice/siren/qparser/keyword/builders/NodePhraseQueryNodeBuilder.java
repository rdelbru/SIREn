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
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.TokenizedPhraseQueryNode;
import org.apache.lucene.queryparser.flexible.standard.builders.PhraseQueryNodeBuilder;
import org.sindice.siren.search.node.NodePhraseQuery;
import org.sindice.siren.search.node.NodeTermQuery;

/**
 * Builds a {@link NodePhraseQuery} object from a {@link TokenizedPhraseQueryNode}
 * object.
 *
 * <p>
 *
 * Code taken from {@link PhraseQueryNodeBuilder} and adapted for SIREn
 */
public class NodePhraseQueryNodeBuilder implements KeywordQueryBuilder {

  public NodePhraseQueryNodeBuilder() {
    // empty constructor
  }

  public NodePhraseQuery build(final QueryNode queryNode) throws QueryNodeException {
    final TokenizedPhraseQueryNode phraseNode = (TokenizedPhraseQueryNode) queryNode;

    final NodePhraseQuery phraseQuery = new NodePhraseQuery();
    final List<QueryNode> children = phraseNode.getChildren();

    if (children != null) {
      for (final QueryNode child : children) {
        final NodeTermQuery termQuery = (NodeTermQuery) child
            .getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
        final FieldQueryNode termNode = (FieldQueryNode) child;
        phraseQuery.add(termQuery.getTerm(), termNode.getPositionIncrement());
      }
    }
    return phraseQuery;
  }

}
