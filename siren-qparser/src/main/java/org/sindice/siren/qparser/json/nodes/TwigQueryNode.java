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

import org.apache.lucene.queryparser.flexible.core.nodes.FieldableNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNodeImpl;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.sindice.siren.qparser.json.parser.LevelPropertyParser;
import org.sindice.siren.qparser.json.parser.RangePropertyParser;

/**
 * This query node represents a twig query that holds a root query's node
 * boolean expression and a list of elements which can be either a
 * {@link ChildQueryNode} or a {@link DescendantQueryNode}.
 */
public class TwigQueryNode extends QueryNodeImpl
implements FieldableNode {

  /**
   * The twig query's field
   */
  protected CharSequence field;

  /**
   * The root query's node boolean expression.
   */
  protected CharSequence root;

  public TwigQueryNode() {
    this.allocate();
    this.setLeaf(false);
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escapeSyntaxParser) {
    throw new UnsupportedOperationException();
  }

  /**
   * Retrieves the root query's node boolean expression.
   */
  public CharSequence getRoot() {
    return root;
  }

  /**
   * Sets the root query's node boolean expression.
   */
  public void setRoot(final CharSequence text) {
    this.root = text;
  }

  /**
   * Return true if this twig query node has a root query
   */
  public boolean hasRoot() {
    return root != null;
  }

  @Override
  public CharSequence getField() {
    return field;
  }

  @Override
  public void setField(final CharSequence fieldName) {
    this.field = fieldName;
  }

  @Override
  public String toString() {
    if (this.getRoot() == null && this.getChildren().size() == 0) {
      return "<twig/>";
    }

    final StringBuilder sb = new StringBuilder();
    sb.append("<twig field='" + this.field + "' root='" + this.root +
      "' level='" + this.getTag(LevelPropertyParser.LEVEL_PROPERTY) +
      "' range='" + this.getTag(RangePropertyParser.RANGE_PROPERTY) + "'>");
    for (final QueryNode child : this.getChildren()) {
      sb.append("\n");
      sb.append(child.toString());
    }
    sb.append("\n</twig>");
    return sb.toString();
  }

}
