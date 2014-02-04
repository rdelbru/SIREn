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

package org.sindice.siren.search;

import java.util.ArrayList;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;
import org.sindice.siren.search.node.NodeBooleanClause;
import org.sindice.siren.search.node.NodeQuery;

/**
 * This class represents a combination of {@link Query}s with
 * a specific Occur.
 */
public class BooleanBag {

  private final boolean isMust;
  private final boolean isNot;
  private final boolean isShould;
  private final ArrayList<Query> queries = new ArrayList<Query>();

  private BooleanBag(Query[] queries, boolean isMust, boolean isNot, boolean isShould) {
    this.isShould = isShould;
    this.isMust = isMust;
    this.isNot = isNot;
    for (Query q : queries) {
      this.queries.add(q);
    }
  }

  public static BooleanBag must(Query...queries) {
    return new BooleanBag(queries, true, false, false);
  }

  public static BooleanBag should(Query...queries) {
    return new BooleanBag(queries, false, false, true);
  }

  public static BooleanBag not(Query...queries) {
    return new BooleanBag(queries, false, true, false);
  }

  public NodeBooleanClause[] toNodeBooleanClauses() {
    final NodeBooleanClause[] clauses = new NodeBooleanClause[queries.size()];

    for (int i = 0; i < clauses.length; i++) {
      final Query q = queries.get(i);
      if (q instanceof NodeQuery) {
        final NodeBooleanClause.Occur occur;
        if (isMust) {
          occur = NodeBooleanClause.Occur.MUST;
        } else if (isNot) {
          occur = NodeBooleanClause.Occur.MUST_NOT;
        } else if (isShould) {
          occur = NodeBooleanClause.Occur.SHOULD;
        } else {
          // Shouldn't happen
          throw new IllegalArgumentException("No occurrence could be built!");
        }
        clauses[i] = new NodeBooleanClause((NodeQuery) q, occur);
      } else {
        throw new IllegalArgumentException("Building NodeBooleanClauses, " +
            "expecting only NodeQuery, but got: " + q.getClass().getName());
      }
    }
    return clauses;
  }

  public BooleanClause[] toBooleanClauses() {
    final BooleanClause[] clauses = new BooleanClause[queries.size()];

    for (int i = 0; i < clauses.length; i++) {
      Query q = queries.get(i);
      if (q instanceof NodeQuery) {
        q = new LuceneProxyNodeQuery((NodeQuery) q);
      }
      final BooleanClause.Occur occur;
      if (isMust) {
        occur = BooleanClause.Occur.MUST;
      } else if (isNot) {
        occur = BooleanClause.Occur.MUST_NOT;
      } else if (isShould) {
        occur = BooleanClause.Occur.SHOULD;
      } else {
        // Shouldn't happen
        throw new IllegalArgumentException("No occurrence could be built!");
      }
      clauses[i] = new BooleanClause(q, occur);
    }
    return clauses;
  }

}
