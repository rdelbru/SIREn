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
import java.util.ArrayList;
import java.util.List;

import org.sindice.siren.search.node.TwigQuery.TwigWeight;

/**
 * An extension of {@link NodeBooleanScorer} that matches a root (ancestor)
 * scorer with a boolean combination of descendant scorers.
 *
 * <p>
 *
 * The {@link TwigScorer} subclasses the {@link NodeBooleanScorer}. A Twig query
 * is rewritten into a pure boolean query. To achieve this, we perform the
 * following:
 * <ul>
 * <li> The descendant scorers are filtered so that they return potential common
 * ancestors. Such a filtering is performed by {@link AncestorFilterScorer}.
 * <li> The root scorer is added as a required clause into the boolean query.
 * </ul>
 */
class TwigScorer extends NodeBooleanScorer {

  /**
   * Creates a {@link TwigScorer} with the given root scorer and lists of
   * required, prohibited and optional scorers.
   *
   * @param weight
   *          The BooleanWeight to be used.
   * @param root
   *          The scorer of the twig root.
   * @param rootLevel
   *          The level of the twig root.
   * @param required
   *          the list of required scorers.
   * @param prohibited
   *          the list of prohibited scorers.
   * @param optional
   *          the list of optional scorers.
   */
  public TwigScorer(final TwigWeight weight,
                    final NodeScorer root, final int rootLevel,
                    final List<NodeScorer> required,
                    final List<NodeScorer> prohibited,
                    final List<NodeScorer> optional) throws IOException {
    super(weight,
      append(addAncestorFilter(required, rootLevel), root),
      addAncestorFilter(prohibited, rootLevel),
      addAncestorFilter(optional, rootLevel));
  }

  /**
   * Creates a {@link TwigScorer} with no root scorer and with lists of
   * required, prohibited and optional scorers.
   *
   * @param weight
   *          The BooleanWeight to be used.
   * @param rootLevel
   *          The level of the twig root.
   * @param required
   *          the list of required scorers.
   * @param prohibited
   *          the list of prohibited scorers.
   * @param optional
   *          the list of optional scorers.
   */
  public TwigScorer(final TwigWeight weight,
                    final int rootLevel,
                    final List<NodeScorer> required,
                    final List<NodeScorer> prohibited,
                    final List<NodeScorer> optional) throws IOException {
    super(weight,
      addAncestorFilter(required, rootLevel),
      addAncestorFilter(prohibited, rootLevel),
      addAncestorFilter(optional, rootLevel));
  }

  private static final List<NodeScorer> addAncestorFilter(final List<NodeScorer> scorers,
                                                          final int ancestorLevel) {
    final ArrayList<NodeScorer> filteredScorers = new ArrayList<NodeScorer>();
    for (final NodeScorer scorer : scorers) {
      filteredScorers.add(new AncestorFilterScorer(scorer, ancestorLevel));
    }
    return filteredScorers;
  }

  private static final List<NodeScorer> append(final List<NodeScorer> scorers,
                                               final NodeScorer scorer) {
    scorers.add(scorer);
    return scorers;
  }

  @Override
  public String toString() {
    return "TwigScorer(" + this.weight + "," + this.doc() + "," +
      this.node() + ")";
  }

}
