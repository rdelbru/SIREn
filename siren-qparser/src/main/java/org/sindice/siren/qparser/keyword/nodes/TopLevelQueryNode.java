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

package org.sindice.siren.qparser.keyword.nodes;

import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNodeImpl;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.sindice.siren.qparser.keyword.builders.TopLevelQueryNodeBuilder;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;
import org.sindice.siren.search.node.NodeTermQuery;

/**
 * This {@link QueryNode} represents the top level {@link QueryNode}
 * of a keyword query.
 *
 * <p>
 *
 * It is used in {@link TopLevelQueryNodeBuilder} for wrapping
 * primitive queries, e.g., {@link NodeTermQuery}, into a
 * {@link LuceneProxyNodeQuery}.
 */
public class TopLevelQueryNode
extends QueryNodeImpl {

  public TopLevelQueryNode(final QueryNode q) {
    this.allocate();
    this.setLeaf(false);
    this.add(q);
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escapeSyntaxParser) {
    return this.getChildren().get(0).toQueryString(escapeSyntaxParser);
  }

  @Override
  public String toString() {
    return "<top>\n" + this.getChildren().get(0) + "\n</top>";
  }

}
