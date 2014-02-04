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
package org.sindice.siren.util;

import org.apache.lucene.util.IntsRef;

/**
 * Reusable methods to manage and compare node paths.
 */
public class NodeUtils {

  /**
   * Compares the first node with the second node for order.
   * Returns a negative integer, zero, or a positive integer if the first node
   * is less than, equal to, or greater than the second node.
   */
  public static final int compare(final IntsRef n1, final IntsRef n2) {
    return compare(n1.ints, n1.offset, n1.length, n2.ints, n2.offset, n2.length);
  }

  private static final int compare(final int[] n1, final int n1Offset, final int n1Len,
                                   final int[] n2, final int n2Offset, final int n2Len) {
    final int n1Limit = n1Len + n1Offset;
    final int n2Limit = n2Len + n2Offset;

    for (int i = n1Offset, j = n2Offset; i < n1Limit && j < n2Limit; i++, j++) {
      if (n1[i] != n2[j]) {
        return n1[i] - n2[j];
      }
    }
    // exception, if node path is equal, check node path length
    return n1Len - n2Len;
  }

  /**
   * Compares the first node with the second node for order.
   * Returns:
   * <ul>
   * <li> a negative integer if the first node is a predecessor of the second
   * node,
   * <li> zero if the first node is an ancestor of the second node,
   * <li> a positive integer if the first node is equal to or greater than the
   * second node.
   * </ul>
   */
  public static final int compareAncestor(final IntsRef n1, final IntsRef n2) {
    return compareAncestor(n1.ints, n1.length, n2.ints, n2.length);
  }

  public static final int compareAncestor(final int[] n1, final int n1Len,
                                          final int[] n2, final int n2Len) {
    for (int i = 0; i < n1Len && i < n2Len; i++) {
      if (n1[i] != n2[i]) {
        return n1[i] - n2[i];
      }
    }
    // exception, if node path is equal, check node path length
    return n1Len < n2Len ? 0 : 1;
  }

  /**
   * Increase the size of the array and copy the content of the original array
   * into the new one.
   */
  public static final int[] growAndCopy(final int[] array, final int minSize) {
    assert minSize >= 0: "size must be positive (got " + minSize + "): likely integer overflow?";
    if (array.length < minSize) {
      final int[] newArray = new int[minSize];
      System.arraycopy(array, 0, newArray, 0, array.length);
      return newArray;
    } else {
      return array;
    }
  }

  /**
   * Check if a node path is satisfying the interval constraints.
   * <p>
   * The <code>level</code> parameter allows to force the node to be on a
   * certain level.
   * <p>
   * The <code>levelIndex</code> array contains the level associated with
   * the interval constraints in <code>constraints</code>.
   * <p>
   * Example: <br>
   * Given the lower bound constraint [1], upper bound constraint [10] at
   * level 1, then the node paths [1,4], [5,10,9] or [10,5] satisfy
   * the constraints. However, the node path [0,3] or [11,0,9] does not satisfy
   * the constraints.
   */
  public static final boolean isConstraintSatisfied(final IntsRef node,
                                                    final int level,
                                                    final int[] levelIndex,
                                                    final int[][] constraints) {
    // check if node satisfies level
    if (node.length != level) {
      return false;
    }

    final int offset = node.offset;
    final int[] ints = node.ints;
    int[] constraint;
    int value;

    for (int i = 0; i < levelIndex.length; i++) {
      // check if node value at given level satisfies the interval constraint
      constraint = constraints[i];
      value = ints[offset + levelIndex[i] - 1];
      if (value < constraint[0] || value > constraint[1]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if a node path is satisfying the interval constraints.
   * <p>
   * The <code>levelIndex</code> array contains the level associated with
   * the interval constraints in <code>constraints</code>.
   * <p>
   * Example: <br>
   * Given the lower bound constraint [1], upper bound constraint [10] at
   * level 1, then the node paths [1,4], [5,10,9] or [10,5] satisfy
   * the constraints. However, the node path [0,3] or [11,0,9] does not satisfy
   * the constraints.
   */
  public static final boolean isConstraintSatisfied(final IntsRef node,
                                                    final int level,
                                                    final int[] constraint) {
    // check if node satisfies level
    if (level != -1 && node.length != level) {
      return false;
    }

    // retrieve last value of the node path
    final int value = node.ints[node.offset + node.length - 1];

    if (value < constraint[0] || value > constraint[1]) {
      return false;
    }
    return true;
  }

  /**
   * Check if a node path is satisfying the level constraint.
   * <p>
   * The <code>level</code> parameter allows to force the node to be on a
   * certain level.
   * <br>
   * Given that the root of the tree (level 0) is the document id, the node
   * level constraint ranges from 1 to <code>Integer.MAX_VALUE</code>. A node
   * level constraint of 0 will always return false.
   * <br>
   * The sentinel value to ignore the node level constraint is -1.
   * <p>
   * Example: <br>
   * Given the level constraint 2, the node paths [0,1], [3,10] or [2,5] satisfy
   * the constraints. However, the node path [2,3,4] does not satisfy the
   * constraints.
   */
  public static final boolean isConstraintSatisfied(final IntsRef node,
                                                    final int level) {
    if (level != -1 && node.length != level) {
      return false;
    }
    return true;
  }

}
