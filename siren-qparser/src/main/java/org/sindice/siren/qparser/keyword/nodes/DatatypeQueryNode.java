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
import org.sindice.siren.qparser.keyword.processors.DatatypeQueryNodeProcessor;

/**
 * This {@link QueryNode} defines the datatype of all its descendant nodes.
 *
 * @see DatatypeQueryNodeProcessor
 */
public class DatatypeQueryNode
extends QueryNodeImpl {

  /**
   * This tag is used to set the label of the datatype to be used
   * on that query node.
   */
  public static final String DATATYPE_TAGID = DatatypeQueryNode.class.getName();

  /** The datatype label */
  private String datatype;

  public DatatypeQueryNode(final QueryNode qn,
                           final String datatype) {
    this.allocate();
    this.setLeaf(false);
    this.add(qn);
    this.datatype = datatype;
  }

  /**
   * @param datatype the datatype to set
   */
  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  /**
   * @return the datatype
   */
  public String getDatatype() {
    return datatype;
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escapeSyntaxParser) {
    String s = this.getDatatype() + "(";
    for (final QueryNode child: this.getChildren()) {
      s += child.toString() + " ";
    }
    return s + ")";
  }

  @Override
  public String toString() {
    String s = "<datatype name=\"" + datatype + "\">\n";
    for (final QueryNode child: this.getChildren()) {
      s += child.toString() + "\n";
    }
    return s + "</datatype>";
  }

  /**
   * Returns the typed {@link QueryNode}
   */
  public QueryNode getChild() {
    return this.getChildren().get(0);
  }

}
