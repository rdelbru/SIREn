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

/**
 * Abstract class that represents a top-level node query object (either node or
 * twig query) of the JSON query syntax.
 */
public abstract class AbstractNodeQuery extends AbstractQuery {

  private boolean hasLevel = false;
  private int level;

  private boolean hasRange = false;
  private int lowerBound;
  private int upperBound;

  public AbstractNodeQuery(final ObjectMapper mapper) {
    super(mapper);
  }

  /**
   * Sets the node level constraint.
   *
   * @see {@link org.sindice.siren.search.node.NodeQuery#setLevelConstraint(int)}
   */
  public AbstractNodeQuery setLevel(final int level) {
    this.level = level;
    this.hasLevel = true;
    return this;
  }

  protected boolean hasLevel() {
    return hasLevel;
  }

  protected int getLevel() {
    return level;
  }

  /**
   * Sets the node range constraint.
   *
   * @see {@link org.sindice.siren.search.node.NodeQuery#setNodeConstraint(int, int)}
   */
  public AbstractNodeQuery setRange(final int lowerBound, final int upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.hasRange = true;
    return this;
  }

  protected boolean hasRange() {
    return hasRange;
  }

  protected int getLowerBound() {
    return lowerBound;
  }

  protected int getUpperBound() {
    return upperBound;
  }

  @Override
  public abstract Query toQuery(boolean proxy) throws QueryNodeException;

}
