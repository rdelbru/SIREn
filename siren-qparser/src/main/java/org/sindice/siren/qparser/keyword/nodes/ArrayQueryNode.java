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

import java.util.List;

import org.apache.lucene.queryparser.flexible.core.nodes.GroupQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNodeImpl;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.sindice.siren.search.node.TwigQuery;
import org.sindice.siren.search.node.TwigQuery.EmptyRootQuery;

/**
 * An {@link ArrayQueryNode} represents a JSON-like array of nodes. This array is
 * mapped to a {@link TwigQuery}, possibly with an {@link EmptyRootQuery}.
 * 
 * <p>
 * 
 * Each child of an {@link ArrayQueryNode} has the same root node, which is
 * defined by {@link TwigQueryNode}.
 */
public class ArrayQueryNode
extends QueryNodeImpl {

  /**
   *
   * @param values a list of {@link QueryNode}s, evaluated on the same level
   *        in the document tree.
   */
  public ArrayQueryNode(final List<QueryNode> values) {
    this.allocate();
    this.setLeaf(false);
    this.add(values);
  }

  public ArrayQueryNode(final QueryNode value) {
    this.allocate();
    this.setLeaf(false);
    this.add(value);
  }

  @Override
  public String toString() {
    String s = "<array>\n";
    for (final QueryNode v: this.getChildren()) {
      s += "<el>\n" + v + "\n</el>\n";
    }
    s += "</array>";
    return s;
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escapeSyntaxParser) {
    if (this.getChildren() == null || this.getChildren().size() == 0)
      return "";

    final StringBuilder sb = new StringBuilder();
    if (this.getChildren().size() == 1) {
      sb.append(this.getChildren().get(0).toQueryString(escapeSyntaxParser));
    } else {
      sb.append("[ ");
      for (int i = 0; i < this.getChildren().size(); i++) {
        sb.append(this.getChildren().get(i).toQueryString(escapeSyntaxParser));
        if (i + 1 != this.getChildren().size()) {
          sb.append(", ");
        }
      }
      sb.append(" ]");
    }

    // in case is root or the parent is a group node avoid parenthesis
    if ((this.getParent() != null && this.getParent() instanceof GroupQueryNode) || this.isRoot()) {
      return sb.toString();
    } else {
      return "( " + sb.toString() + " )";
    }
  }

}
