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
package org.sindice.siren.qparser.json.dsl;

/**
 * Class that represents a descendant clause object of the JSON query syntax.
 * Compared to a {@link BasicQueryClause}, a descendant clause object has a
 * level field.
 */
class DescendantQueryClause extends QueryClause {

  private final int level;

  /**
   * Create a new descendant clause object.
   *
   * @see {@link org.sindice.siren.search.node.TwigQuery#addDescendant(int,
   * org.sindice.siren.search.node.NodeQuery,
   * org.sindice.siren.search.node.NodeBooleanClause.Occur)
   */
  DescendantQueryClause(final AbstractNodeQuery query, final Occur occur, final int level) {
    super(query, occur);
    this.level = level;
  }

  /**
   * Retrieve the node level
   */
  int getLevel() {
    return level;
  }

}
