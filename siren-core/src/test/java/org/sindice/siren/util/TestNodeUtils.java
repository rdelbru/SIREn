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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.sindice.siren.analysis.MockSirenToken.node;

import org.apache.lucene.util.IntsRef;
import org.junit.Test;

public class TestNodeUtils {

  @Test
  public void testCompare() {
    IntsRef n1 = node(0, 0, 0);
    IntsRef n2 = node(1, 0, 0);
    assertTrue(NodeUtils.compare(n1, n2) < 0);

    n1 = node(1, 1, 0);
    n2 = node(1, 0, 0);
    assertTrue(NodeUtils.compare(n1, n2) > 0);

    n1 = node(1, 1, 0);
    n2 = node(1, 1, 0);
    assertTrue(NodeUtils.compare(n1, n2) == 0);

    n1 = node(1, 1);
    n2 = node(1, 1, 0);
    assertTrue(NodeUtils.compare(n1, n2) < 0);
  }

  @Test
  public void testCompareAncestor() {
    IntsRef n1 = node(0, 0, 0);
    IntsRef n2 = node(1, 0, 0);
    assertTrue(NodeUtils.compareAncestor(n1, n2) < 0);

    n1 = node(1, 1, 0);
    n2 = node(1, 0, 0);
    assertTrue(NodeUtils.compareAncestor(n1, n2) > 0);

    n1 = node(1, 1, 0);
    n2 = node(1, 1, 0);
    assertTrue(NodeUtils.compareAncestor(n1, n2) > 0);

    n1 = node(1, 1);
    n2 = node(1, 1, 0);
    assertTrue(NodeUtils.compareAncestor(n1, n2) == 0);
  }

  @Test
  public void testIsConstraintSatisfied() {
    final int[] levelIndex = new int[] { 1,3 };
    final int[][] constraints = new int[][] {
      new int[] { 1,8 },
      new int[] { 5,5 },
    };

    IntsRef node = node(1,0,5);
    assertTrue(NodeUtils.isConstraintSatisfied(node, 3, levelIndex, constraints));

    node = node(8,10,5);
    assertTrue(NodeUtils.isConstraintSatisfied(node, 3, levelIndex, constraints));

    node = node(4,5,5,90);
    assertTrue(NodeUtils.isConstraintSatisfied(node, 4, levelIndex, constraints));

    node = node(4);
    assertFalse(NodeUtils.isConstraintSatisfied(node, 3, levelIndex, constraints));

    node = node(4,10);
    assertFalse(NodeUtils.isConstraintSatisfied(node, 3, levelIndex, constraints));

    node = node(0,12,5);
    assertFalse(NodeUtils.isConstraintSatisfied(node, 3, levelIndex, constraints));

    node = node(9,12,5);
    assertFalse(NodeUtils.isConstraintSatisfied(node, 3, levelIndex, constraints));

    node = node(4,5,4);
    assertFalse(NodeUtils.isConstraintSatisfied(node, 3, levelIndex, constraints));

    node = node(4,5,6);
    assertFalse(NodeUtils.isConstraintSatisfied(node, 3, levelIndex, constraints));
  }

  @Test
  public void testIsLevelConstraintSatisfied() {
    IntsRef node = node(1,5,1);
    assertTrue(NodeUtils.isConstraintSatisfied(node, 3));

    node = node(1,5,1);
    assertFalse(NodeUtils.isConstraintSatisfied(node, 2));

    node = node(1,1,0,0);
    assertFalse(NodeUtils.isConstraintSatisfied(node, 3));
  }

}
