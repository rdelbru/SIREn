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

import org.apache.lucene.queryparser.flexible.core.nodes.BooleanQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.GroupQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNodeImpl;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.apache.lucene.search.BooleanQuery;
import org.sindice.siren.qparser.keyword.processors.NodeBooleanQueryNodeProcessor;
import org.sindice.siren.search.node.NodeBooleanQuery;

/**
 * A {@link NodeBooleanQueryNode} is used to represent a boolean
 * combination of terms inside a SIREn node.
 *
 * <p>
 *
 * This is done in {@link NodeBooleanQueryNodeProcessor}.
 * A {@link NodeBooleanQueryNode} is used to indicate that a
 * {@link NodeBooleanQuery} must be built, rather than a {@link BooleanQuery}.
 *
 * <p>
 *
 * Copied from {@link BooleanQueryNode} for the SIREn use case.
 */
public class NodeBooleanQueryNode extends QueryNodeImpl {

  /**
   * @param bq
   *          - the {@link BooleanQueryNode} to convert
   */
  public NodeBooleanQueryNode(final BooleanQueryNode bq) {
    this.setLeaf(false);
    this.allocate();
    this.set(bq.getChildren());
  }

  @Override
  public String toString() {
    if (this.getChildren() == null || this.getChildren().size() == 0)
      return "<nodeBoolean operation='default'/>";
    final StringBuilder sb = new StringBuilder();
    sb.append("<nodeBoolean operation='default'>");
    for (final QueryNode child : this.getChildren()) {
      sb.append("\n");
      sb.append(child.toString());
    }
    sb.append("\n</nodeBoolean>");
    return sb.toString();
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escapeSyntaxParser) {
    if (this.getChildren() == null || this.getChildren().size() == 0)
      return "";

    final StringBuilder sb = new StringBuilder();
    String filler = "";
    for (final QueryNode child : this.getChildren()) {
      sb.append(filler).append(child.toQueryString(escapeSyntaxParser));
      filler = " ";
    }

    // in case is root or the parent is a group node avoid parenthesis
    if ((this.getParent() != null && this.getParent() instanceof GroupQueryNode)
        || this.isRoot())
      return sb.toString();
    else
      return "( " + sb.toString() + " )";
  }

  @Override
  public QueryNode cloneTree() throws CloneNotSupportedException {
    final NodeBooleanQueryNode clone = (NodeBooleanQueryNode) super.cloneTree();

    // nothing to do here

    return clone;
  }

}
