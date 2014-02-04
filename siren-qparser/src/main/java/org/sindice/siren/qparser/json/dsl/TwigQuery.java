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
import org.sindice.siren.qparser.json.parser.BoostPropertyParser;
import org.sindice.siren.qparser.json.parser.ChildPropertyParser;
import org.sindice.siren.qparser.json.parser.DescendantPropertyParser;
import org.sindice.siren.qparser.json.parser.LevelPropertyParser;
import org.sindice.siren.qparser.json.parser.OccurPropertyParser;
import org.sindice.siren.qparser.json.parser.RangePropertyParser;
import org.sindice.siren.qparser.json.parser.RootPropertyParser;
import org.sindice.siren.qparser.json.parser.TwigPropertyParser;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;
import org.sindice.siren.search.node.NodeBooleanClause;
import org.sindice.siren.search.node.NodeQuery;

/**
 * Class that represents a twig object of the JSON query syntax.
 */
public class TwigQuery extends AbstractNodeQuery {

  private boolean hasRoot = false;
  private String rootBooleanExpression;

  private final List<QueryClause> clauses;

  private final KeywordQueryParser parser;

  public TwigQuery(final ObjectMapper mapper, final KeywordQueryParser parser) {
    super(mapper);
    this.parser = parser;
    clauses = new ArrayList<QueryClause>();
  }

  @Override
  public TwigQuery setLevel(final int level) {
    return (TwigQuery) super.setLevel(level);
  }

  @Override
  public TwigQuery setRange(final int lowerBound, final int upperBound) {
    return (TwigQuery) super.setRange(lowerBound, upperBound);
  }

  @Override
  public TwigQuery setBoost(final float boost) {
    return (TwigQuery) super.setBoost(boost);
  }

  /**
   * Set the ndoe boolean query expression for the root of the twig.
   *
   * @see org.sindice.siren.search.node.TwigQuery#addRoot(NodeQuery)
   */
  void setRoot(final String booleanExpression) {
    this.rootBooleanExpression = booleanExpression;
    this.hasRoot = true;
  }

  /**
   * Adds a child clause with a
   * {@link NodeBooleanClause.Occur#MUST} operator.
   * <p>
   * Use this method for child clauses that must appear in the matching twigs.
   *
   * @see NodeBooleanClause.Occur#MUST
   * @see org.sindice.siren.search.node.TwigQuery#addChild(NodeQuery, NodeBooleanClause.Occur)
   */
  public TwigQuery with(final AbstractNodeQuery child) {
    clauses.add(new BasicQueryClause(child, Occur.MUST));
    return this;
  }

  /**
   * Adds a descendant clause with a
   * {@link NodeBooleanClause.Occur#MUST} operator.
   * <p>
   * Use this method for descendant clauses that must appear in the matching
   * twigs.
   *
   * @see NodeBooleanClause.Occur#MUST
   * @see org.sindice.siren.search.node.TwigQuery#addDescendant(int, NodeQuery, NodeBooleanClause.Occur)
   */
  public TwigQuery with(final AbstractNodeQuery descendant, final int level) {
    clauses.add(new DescendantQueryClause(descendant, Occur.MUST, level));
    return this;
  }

  /**
   * Adds a child clause with a
   * {@link NodeBooleanClause.Occur#MUST_NOT} operator.
   * <p>
   * Use this method for child clauses that must not appear in the matching
   * twigs.
   *
   * @see NodeBooleanClause.Occur#MUST_NOT
   * @see org.sindice.siren.search.node.TwigQuery#addChild(NodeQuery, NodeBooleanClause.Occur)
   */
  public TwigQuery without(final AbstractNodeQuery child) {
    clauses.add(new BasicQueryClause(child, Occur.MUST_NOT));
    return this;
  }

  /**
   * Adds a descendant clause with a
   * {@link NodeBooleanClause.Occur#MUST_NOT} operator.
   * <p>
   * Use this method for descendant clauses that must not appear in the matching
   * twigs.
   *
   * @see NodeBooleanClause.Occur#MUST_NOT
   * @see org.sindice.siren.search.node.TwigQuery#addDescendant(int, NodeQuery, NodeBooleanClause.Occur)
   */
  public TwigQuery without(final AbstractNodeQuery descendant, final int level) {
    clauses.add(new DescendantQueryClause(descendant, Occur.MUST_NOT, level));
    return this;
  }

  /**
   * Adds a child clause with a
   * {@link NodeBooleanClause.Occur#SHOULD} operator.
   * <p>
   * Use this method for child clauses that should appear in the matching
   * twigs.
   *
   * @see NodeBooleanClause.Occur#SHOULD
   * @see org.sindice.siren.search.node.TwigQuery#addChild(NodeQuery, NodeBooleanClause.Occur)
   */
  public TwigQuery optional(final AbstractNodeQuery child) {
    clauses.add(new BasicQueryClause(child, Occur.SHOULD));
    return this;
  }

  /**
   * Adds a descendant clause with a
   * {@link NodeBooleanClause.Occur#SHOULD} operator.
   * <p>
   * Use this method for descendant clauses that should appear in the matching
   * twigs.
   *
   * @see NodeBooleanClause.Occur#SHOULD
   * @see org.sindice.siren.search.node.TwigQuery#addDescendant(int, NodeQuery, NodeBooleanClause.Occur)
   */
  public TwigQuery optional(final AbstractNodeQuery descendant, final int level) {
    clauses.add(new DescendantQueryClause(descendant, Occur.SHOULD, level));
    return this;
  }

  @Override
  public Query toQuery(final boolean proxy) throws QueryNodeException {
    final org.sindice.siren.search.node.TwigQuery query = new org.sindice.siren.search.node.TwigQuery();
    // parse and add root
    if (hasRoot) {
      query.addRoot((NodeQuery) parser.parse(rootBooleanExpression, ""));
    }
    // convert child and descendant clauses
    for (final QueryClause clause : clauses) {
      if (clause instanceof BasicQueryClause) {
        query.addChild((NodeQuery) clause.getQuery().toQuery(false),clause.getNodeBooleanOccur());
      }
      else {
        final int level = ((DescendantQueryClause) clause).getLevel();
        query.addDescendant(level, (NodeQuery) clause.getQuery().toQuery(false), clause.getNodeBooleanOccur());
      }
    }
    // add level
    if (this.hasLevel()) {
      query.setLevelConstraint(this.getLevel());
    }
    // add range
    if (this.hasRange()) {
      query.setNodeConstraint(this.getLowerBound(), this.getUpperBound());
    }
    // add boost
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
    final ObjectNode twig = obj.putObject(TwigPropertyParser.TWIG_PROPERTY);

    if (hasRoot) {
      twig.put(RootPropertyParser.ROOT_PROPERTY, rootBooleanExpression);
    }

    if (this.hasLevel()) {
      twig.put(LevelPropertyParser.LEVEL_PROPERTY, this.getLevel());
    }

    if (this.hasRange()) {
      final ArrayNode array = twig.putArray(RangePropertyParser.RANGE_PROPERTY);
      array.add(this.getLowerBound());
      array.add(this.getUpperBound());
    }

    if (this.hasBoost()) {
      twig.put(BoostPropertyParser.BOOST_PROPERTY, this.getBoost());
    }

    ArrayNode childArray = null;
    ArrayNode descendantArray = null;
    for (final QueryClause clause : clauses) {
      if (clause instanceof BasicQueryClause) {
        if (!twig.has(ChildPropertyParser.CHILD_PROPERTY)) { // avoid to create an empty array in the JSON
          childArray = twig.putArray(ChildPropertyParser.CHILD_PROPERTY);
        }
        final ObjectNode e = childArray.addObject();
        e.put(OccurPropertyParser.OCCUR_PROPERTY, clause.getOccur().toString());
        e.putAll(clause.getQuery().toJson());
      }
      else {
        if (!twig.has(DescendantPropertyParser.DESCENDANT_PROPERTY)) { // avoid to create an empty array in the JSON
          descendantArray = twig.putArray(DescendantPropertyParser.DESCENDANT_PROPERTY);
        }
        final ObjectNode e = descendantArray.addObject();
        e.put(OccurPropertyParser.OCCUR_PROPERTY, clause.getOccur().toString());
        e.put(LevelPropertyParser.LEVEL_PROPERTY, ((DescendantQueryClause) clause).getLevel());
        e.putAll(clause.getQuery().toJson());
      }
    }

    return obj;
  }



}
