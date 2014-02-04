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

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.search.Query;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.sindice.siren.qparser.json.dsl.QueryClause.Occur;
import org.sindice.siren.qparser.json.parser.BooleanPropertyParser;
import org.sindice.siren.qparser.json.parser.OccurPropertyParser;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;

/**
 * Class that represents a boolean query object of the JSON query syntax.
 */
public class BooleanQuery extends AbstractQuery {

  private final List<QueryClause> clauses;

  BooleanQuery(final ObjectMapper mapper) {
    super(mapper);
    clauses = new ArrayList<QueryClause>();
  }

  @Override
  public BooleanQuery setBoost(final float boost) {
    throw new UnsupportedOperationException("Boost on BooleanQuery not supported");
  }

  /**
   * Adds a boolean clause with a
   * {@link org.apache.lucene.search.BooleanClause.Occur#MUST} operator.
   * <p>
   * Use this method for clauses that must appear in the matching documents.
   *
   * @see {@link org.apache.lucene.search.BooleanClause.Occur#MUST}
   */
  public BooleanQuery with(final AbstractNodeQuery node) {
    clauses.add(new BasicQueryClause(node, Occur.MUST));
    return this;
  }

  /**
   * Adds a boolean clause with a
   * {@link org.apache.lucene.search.BooleanClause.Occur#MUST_NOT} operator.
   * <p>
   * Use this method for clauses that must not appear in the matching documents.
   *
   * @see {@link org.apache.lucene.search.BooleanClause.Occur#MUST}
   */
  public BooleanQuery without(final AbstractNodeQuery node) {
    clauses.add(new BasicQueryClause(node, Occur.MUST_NOT));
    return this;
  }

  /**
   * Adds a boolean clause with a
   * {@link org.apache.lucene.search.BooleanClause.Occur#SHOULD} operator.
   * <p>
   * Use this method for clauses that should appear in the matching documents.
   *
   * @see {@link org.apache.lucene.search.BooleanClause.Occur#SHOULD}
   */
  public BooleanQuery optional(final AbstractNodeQuery node) {
    clauses.add(new BasicQueryClause(node, Occur.SHOULD));
    return this;
  }

  @Override
  public org.apache.lucene.search.BooleanQuery toQuery(final boolean proxy) throws QueryNodeException {
    final org.apache.lucene.search.BooleanQuery query = new org.apache.lucene.search.BooleanQuery(true);
    // convert clauses
    for (final QueryClause clause : clauses) {
      // wrap node query into a lucene proxy query
      final Query q = new LuceneProxyNodeQuery((org.sindice.siren.search.node.NodeQuery) clause.getQuery().toQuery(false));
      query.add(q, clause.getBooleanOccur());
    }
    // add boost
    if (this.hasBoost()) {
      query.setBoost(this.getBoost());
    }
    return query;
  }

  @Override
  ObjectNode toJson() {
    final ObjectNode obj = mapper.createObjectNode();
    final ArrayNode bool = obj.putArray(BooleanPropertyParser.BOOLEAN_PROPERTY);

    for (final QueryClause clause : clauses) {
      final ObjectNode e = bool.addObject();
      e.put(OccurPropertyParser.OCCUR_PROPERTY, clause.getOccur().toString());
      e.putAll(clause.getQuery().toJson());
    }

    return obj;
  }


}
