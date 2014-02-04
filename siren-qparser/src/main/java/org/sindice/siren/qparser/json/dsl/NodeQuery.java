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
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.sindice.siren.qparser.json.parser.BoostPropertyParser;
import org.sindice.siren.qparser.json.parser.LevelPropertyParser;
import org.sindice.siren.qparser.json.parser.NodePropertyParser;
import org.sindice.siren.qparser.json.parser.QueryPropertyParser;
import org.sindice.siren.qparser.json.parser.RangePropertyParser;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;

/**
 * Class that represents a node query object of the JSON query syntax.
 */
public class NodeQuery extends AbstractNodeQuery {

  private final KeywordQueryParser parser;

  private String booleanExpression;

  NodeQuery(final ObjectMapper mapper, final KeywordQueryParser parser) {
    super(mapper);
    this.parser = parser;
  }

  void setBooleanExpression(final String booleanExpression) {
    this.booleanExpression = booleanExpression;
  }

  @Override
  public NodeQuery setLevel(final int level) {
    return (NodeQuery) super.setLevel(level);
  }

  @Override
  public NodeQuery setRange(final int lowerBound, final int upperBound) {
    return (NodeQuery) super.setRange(lowerBound, upperBound);
  }

  @Override
  public NodeQuery setBoost(final float boost) {
    return (NodeQuery) super.setBoost(boost);
  }

  @Override
  public Query toQuery(final boolean proxy) throws QueryNodeException {
    final org.sindice.siren.search.node.NodeQuery query = (org.sindice.siren.search.node.NodeQuery) parser.parse(booleanExpression, "");
    if (this.hasLevel()) {
      query.setLevelConstraint(this.getLevel());
    }
    if (this.hasRange()) {
      query.setNodeConstraint(this.getLowerBound(), this.getUpperBound());
    }
    if (this.hasBoost()) {
      query.setBoost(this.getBoost());
    }

    // should we wrap the query into a lucene proxy
    if (proxy) {
      return new LuceneProxyNodeQuery(query);
    }
    return query;
  }

  @Override
  ObjectNode toJson() {
    final ObjectNode obj = mapper.createObjectNode();
    final ObjectNode node = obj.putObject(NodePropertyParser.NODE_PROPERTY);
    node.put(QueryPropertyParser.QUERY_PROPERTY, booleanExpression);
    if (this.hasLevel()) {
      node.put(LevelPropertyParser.LEVEL_PROPERTY, this.getLevel());
    }
    if (this.hasRange()) {
      final ArrayNode array = node.putArray(RangePropertyParser.RANGE_PROPERTY);
      array.add(this.getLowerBound());
      array.add(this.getUpperBound());
    }
    if (this.hasBoost()) {
      node.put(BoostPropertyParser.BOOST_PROPERTY, this.getBoost());
    }
    return obj;
  }

}
