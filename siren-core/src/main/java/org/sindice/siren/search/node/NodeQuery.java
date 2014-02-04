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

import java.util.Iterator;
import java.util.TreeMap;

import org.apache.lucene.index.DocsAndPositionsEnum;
import org.apache.lucene.search.Query;
import org.sindice.siren.index.ConstrainedNodesEnum;
import org.sindice.siren.index.DocsNodesAndPositionsEnum;
import org.sindice.siren.index.IntervalConstrainedNodesEnum;
import org.sindice.siren.index.LevelConstrainedNodesEnum;
import org.sindice.siren.index.SingleIntervalConstrainedNodesEnum;
import org.sindice.siren.index.SirenDocsEnum;

/**
 * Abstract class for the SIREn's node queries
 *
 * <p>
 *
 * This class provides an interface to manage node constraints. When a
 * {@link DocsNodesAndPositionsEnum} is requested using the method
 * {@link #getDocsNodesAndPositionsEnum(DocsAndPositionsEnum)}, it traverses
 * all the {@link NodeQuery} ancestor of the current {@link NodeQuery} and
 * creates a {@link ConstraintStack}. Given the constraint stack, the
 * appropriate {@link ConstrainedNodesEnum} is created and returned.
 */
public abstract class NodeQuery extends Query {

  /**
   * The node level constraint.
   * <p>
   * Set to sentinel value -1 by default.
   */
  protected int levelConstraint = -1;

  /**
   * Set a constraint on the node's level
   * <p>
   * Given that the root of the tree (level 0) is the document id, the node
   * level constraint ranges from 1 to <code>Integer.MAX_VALUE</code>. A node
   * level constraint of 0 will always return false.
   */
  public void setLevelConstraint(final int levelConstraint) {
    this.levelConstraint = levelConstraint;
  }

  public int getLevelConstraint() {
    return levelConstraint;
  }

  /**
   * The lower and upper bound of the interval constraint over the node indexes.
   * <p>
   * Set to sentinel value -1 by default.
   */
  protected int lowerBound = -1, upperBound = -1;

  /**
   * Set an index interval constraint for a node. These constraints are
   * inclusives.
   */
  public void setNodeConstraint(final int lowerBound, final int upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  /**
   * Set the node index constraint.
   */
  public void setNodeConstraint(final int index) {
    this.setNodeConstraint(index, index);
  }

  public int[] getNodeConstraint() {
    return new int[] { lowerBound, upperBound };
  }

  class ConstraintStack {

    final TreeMap<Integer, Integer[]> stack = new TreeMap<Integer, Integer[]>();

    protected void add(final int level, final int lowerBound, final int upperBound) {
      stack.put(level, new Integer[] { lowerBound, upperBound });
    }

    protected int size() {
      return stack.size();
    }

    protected int[] getLevelIndex() {
      final int[] levels = new int[stack.size()];
      final Iterator<Integer> it = stack.keySet().iterator();
      for (int i = 0; i < stack.size(); i++) {
        levels[i] = it.next();
      }
      return levels;
    }

    protected int[][] getConstraints() {
      final int[][] constraints = new int[stack.size()][2];
      final Iterator<Integer[]> it = stack.values().iterator();
      for (int i = 0; i < stack.size(); i++) {
        final Integer[] constraint = it.next();
        constraints[i] = new int[2];
        constraints[i][0] = constraint[0];
        constraints[i][1] = constraint[1];
      }
      return constraints;
    }

  }

  /**
   * The pointer to direct node query ancestor
   */
  protected NodeQuery ancestor;

  /**
   * Expert: Add a pointer to the node query ancestor
   * <p>
   * The pointer to node query ancestor is used to retrieve node constraints from
   * ancestors.
   */
  protected void setAncestorPointer(final NodeQuery ancestor) {
    this.ancestor = ancestor;
  }

  /**
   * Provides a {@link DocsNodesAndPositionsEnum} given a
   * {@link DocsAndPositionsEnum}. If a set of constraints is applied, it
   * automatically wraps the {@link DocsNodesAndPositionsEnum} into a
   * {@link ConstrainedNodesEnum}.
   */
  protected DocsNodesAndPositionsEnum getDocsNodesAndPositionsEnum(final DocsAndPositionsEnum docsEnum) {
    // Map Lucene's docs enum to a SIREn's docs, nodes and positions enum
    final DocsNodesAndPositionsEnum sirenDocsEnum = SirenDocsEnum.map(docsEnum);

    // Retrieve constraints starting from the direct ancestor
    final ConstraintStack stack = new ConstraintStack();
    this.retrieveConstraint(this.ancestor, stack);

    // if at least one constraint has been found among the ancestors
    if (stack.size() > 0) {
      // add the interval constraint of the current node
      if (lowerBound != -1 && upperBound != -1) {
        stack.add(levelConstraint, lowerBound, upperBound);
      }
      return new IntervalConstrainedNodesEnum(sirenDocsEnum, levelConstraint,
        stack.getLevelIndex(), stack.getConstraints());
    }
    // if an interval constraint has been set for the current node
    else if (lowerBound != -1 && upperBound != -1) {
      // use the interval constraint of the current node
      return new SingleIntervalConstrainedNodesEnum(sirenDocsEnum,
        levelConstraint, new int[] { lowerBound, upperBound });
    }
    // if only a level constraint has been set for the current node
    else if (levelConstraint != -1) {
      return new LevelConstrainedNodesEnum(sirenDocsEnum, levelConstraint);
    }
    else {
      return sirenDocsEnum;
    }

  }

  private void retrieveConstraint(final NodeQuery query, final ConstraintStack stack) {
    if (query == null) {
      return;
    }

    // add a constraint only if lower and upper bounds are defined
    if (query.lowerBound != -1 && query.upperBound != -1) {
      stack.add(query.levelConstraint, query.lowerBound, query.upperBound);
    }

    // recursively traverse the ancestors
    this.retrieveConstraint(query.ancestor, stack);
  }

}
