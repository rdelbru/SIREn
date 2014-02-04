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
package org.sindice.siren.qparser.json.nodes;

import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNodeImpl;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

/**
 * This query node represents a list of elements which do have an explicit
 * boolean operator defined.
 */
public class BooleanQueryNode extends QueryNodeImpl {

  public BooleanQueryNode() {
    this.allocate();
    this.setLeaf(false);
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escapeSyntaxParser) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    if (this.getChildren().size() == 0) {
      return "<boolean/>";
    }

    final StringBuilder sb = new StringBuilder();
    sb.append("<boolean>\n");
    for (final QueryNode child : this.getChildren()) {
      sb.append(child.toString());
      sb.append("\n");
    }
    sb.append("</boolean>");
    return sb.toString();
  }

}
