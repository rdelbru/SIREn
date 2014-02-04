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

import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.sindice.siren.qparser.json.parser.LevelPropertyParser;

/**
 * This query node represents a descendant clause for a twig query and holds
 * the associated level of the descendant clause. It has the
 * same behaviour than {@link ModifierQueryNode}.
 */
public class DescendantQueryNode extends ModifierQueryNode {

  public DescendantQueryNode(final QueryNode query, final Modifier mod, final int level) {
    super(query, mod);
    this.setTag(LevelPropertyParser.LEVEL_PROPERTY, level);
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escapeSyntaxParser) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return "<descendant operation='" + this.getModifier().toString() + "' " +
    		"level='" + this.getTag(LevelPropertyParser.LEVEL_PROPERTY) + "'>\n"
        + this.getChild().toString() + "\n</modifier>";
  }

}
