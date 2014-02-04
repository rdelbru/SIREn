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

package org.sindice.siren.search.node;

import java.io.IOException;

import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.similarities.Similarity;

/**
 * A {@link NodeQuery} that matches tuples, i.e., a boolean combination of
 * {@link NodeQuery} having the same parent node.
 */
public class TupleQuery extends TwigQuery {

  /**
   * Constructs an empty tuple. By default, the level of the tuple query is 1.
   * <p>
   * {@link Similarity#coord(int,int)} is disabled by default.
   */
  public TupleQuery() {
    super(1);
  }

  /**
   * Constructs an empty tuple query at a given level.
   *
   * @param disableCoord
   *          disables {@link Similarity#coord(int,int)} in scoring.
   */
  public TupleQuery(final int level) {
    super(level);
  }

  /**
   * Constructs an empty tuple query. {@link Similarity#coord(int,int)} may be
   * disabled in scoring, as appropriate. For example, this score factor does
   * not make sense for most automatically generated queries, like
   * {@link WildcardQuery} and {@link FuzzyQuery}.
   *
   * @param disableCoord
   *          disables {@link Similarity#coord(int,int)} in scoring.
   */
  public TupleQuery(final boolean disableCoord) {
    super(1); // by default, level at 1 as in SIREn 0.2
  }

  /**
   * Constructs an empty tuple query. {@link Similarity#coord(int,int)} may be
   * disabled in scoring, as appropriate. For example, this score factor does
   * not make sense for most automatically generated queries, like
   * {@link WildcardQuery} and {@link FuzzyQuery}.
   *
   * @param disableCoord
   *          disables {@link Similarity#coord(int,int)} in scoring.
   */
  public TupleQuery(final int level, final boolean disableCoord) {
    super(level);
  }

  /**
   * Adds a clause to a tuple query.
   *
   * @throws TooManyClauses
   *           if the new number of clauses exceeds the maximum clause number
   * @see #getMaxClauseCount()
   */
  public void add(final NodeQuery query, final NodeBooleanClause.Occur occur) {
    super.addChild(query, occur);
  }

  /**
   * Adds a clause to a tuple query.
   *
   * @throws TooManyClauses
   *           if the new number of clauses exceeds the maximum clause number
   * @see #getMaxClauseCount()
   */
  public void add(final NodeBooleanClause clause) {
    super.addChild(clause.getQuery(), clause.getOccur());
  }

  @Override
  public Weight createWeight(final IndexSearcher searcher) throws IOException {
    return new TwigWeight(searcher);
  }

}
