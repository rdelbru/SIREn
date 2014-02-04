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

import org.apache.lucene.queryparser.flexible.core.nodes.FieldValuePairQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNodeImpl;
import org.apache.lucene.queryparser.flexible.core.nodes.TextableQueryNode;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.sindice.siren.qparser.json.parser.LevelPropertyParser;
import org.sindice.siren.qparser.json.parser.RangePropertyParser;

/**
 * This query node represents a node query that holds a node boolean expression
 * and a field.
 */
public class NodeQueryNode extends QueryNodeImpl
implements FieldValuePairQueryNode<CharSequence>, TextableQueryNode {

  /**
   * The node query's field
   */
  protected CharSequence field;

  /**
   * The node query's text.
   */
  protected CharSequence text;


  public NodeQueryNode() {
    this.setLeaf(true);
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escapeSyntaxParser) {
    throw new UnsupportedOperationException();
  }

  @Override
  public CharSequence getField() {
    return this.field;
  }

  @Override
  public void setField(final CharSequence fieldName) {
    this.field = fieldName;
  }

  @Override
  public void setValue(final CharSequence value) {
    this.setText(value);
  }

  @Override
  public CharSequence getValue() {
    return this.getText();
  }

  @Override
  public CharSequence getText() {
    return this.text;
  }

  @Override
  public void setText(final CharSequence text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "<node field='" + this.field + "' text='" + this.text +
      "' level='" + this.getTag(LevelPropertyParser.LEVEL_PROPERTY) +
      "' range='" + this.getTag(RangePropertyParser.RANGE_PROPERTY) + "'/>";
  }

}
