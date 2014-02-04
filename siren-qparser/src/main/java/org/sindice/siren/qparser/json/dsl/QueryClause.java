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

import org.apache.lucene.search.BooleanClause;
import org.sindice.siren.search.node.NodeBooleanClause;

/**
 * Abstract class that represents a clause object of the JSON query syntax.
 */
abstract class QueryClause {

  private final Occur occur;

  private final AbstractNodeQuery query;

  QueryClause(final AbstractNodeQuery query, final Occur occur) {
    this.query = query;
    this.occur = occur;
  }

  AbstractNodeQuery getQuery() {
    return query;
  }

  Occur getOccur() {
    return occur;
  }

  BooleanClause.Occur getBooleanOccur() {
    return toBooleanOccur(occur);
  }

  NodeBooleanClause.Occur getNodeBooleanOccur() {
    return toNodeBooleanOccur(occur);
  }

  enum Occur { MUST, MUST_NOT, SHOULD }

  static NodeBooleanClause.Occur toNodeBooleanOccur(final Occur occur) {
    switch (occur) {
      case MUST:
        return NodeBooleanClause.Occur.MUST;

      case MUST_NOT:
        return NodeBooleanClause.Occur.MUST_NOT;

      case SHOULD:
        return NodeBooleanClause.Occur.SHOULD;

      default:
        throw new IllegalArgumentException("Unknown occur received");
    }
  }

  static BooleanClause.Occur toBooleanOccur(final Occur occur) {
    switch (occur) {
      case MUST:
        return BooleanClause.Occur.MUST;

      case MUST_NOT:
        return BooleanClause.Occur.MUST_NOT;

      case SHOULD:
        return BooleanClause.Occur.SHOULD;

      default:
        throw new IllegalArgumentException("Unknown occur received");
    }
  }

}
