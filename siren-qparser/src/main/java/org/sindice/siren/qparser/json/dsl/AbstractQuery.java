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

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.search.Query;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.sindice.siren.qparser.json.JsonQueryParser;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;

/**
 * Abstract class that represents a top-level query object of the JSON query
 * syntax.
 */
public abstract class AbstractQuery {

  protected final ObjectMapper mapper;

  private boolean hasBoost = false;
  private float boost;

  public AbstractQuery(final ObjectMapper mapper) {
    this.mapper = mapper;
  }

  /**
   * Convert the constructed query into a {@link Query}.
   *
   * @param proxy Should the query be wrapped into a {@link LuceneProxyNodeQuery} ?
   */
  public abstract Query toQuery(boolean proxy) throws QueryNodeException;

  /**
   * Return a JSON representation of the constructed query.
   * <p>
   * The JSON representation is compatible with the {@link JsonQueryParser}.
   *
   * @throws IllegalArgumentException If the created query object cannot be
   * converted to JSON.
   */
  @Override
  public String toString() {
    final ObjectNode node = this.toJson();
    try {
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }
    catch (final Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Build a JSON object {link ObjectNode} which maps the content of the query
   * object to the JSON query syntax.
   */
  abstract ObjectNode toJson();

  /**
   * Has the boost parameter been set ?
   */
  boolean hasBoost() {
    return hasBoost;
  }

  /**
   * Retrieve the boost parameter
   */
  float getBoost() {
    return boost;
  }

  /**
   * Sets the boost for this query.
   *
   * @see {@link Query#setBoost(float)}
   */
  public AbstractQuery setBoost(final float boost) {
    this.boost = boost;
    this.hasBoost = true;
    return this;
  }

}
