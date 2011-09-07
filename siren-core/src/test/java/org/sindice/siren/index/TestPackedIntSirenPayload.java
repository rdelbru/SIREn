/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 24 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.index;


import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPackedIntSirenPayload {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp()
  throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown()
  throws Exception {}

  @Test
  public void testSimplePackedInt()
  throws Exception {
    PackedIntSirenPayload payload1 = new PackedIntSirenPayload(12, 43);
    PackedIntSirenPayload payload2 = new PackedIntSirenPayload(payload1.getData());
    assertEquals(payload1._tupleID, payload2._tupleID);
    assertEquals(payload1._cellID, payload2._cellID);

    payload1 = new PackedIntSirenPayload(3, 2);
    payload2 = new PackedIntSirenPayload(payload1.getData());
    assertEquals(payload1._tupleID, payload2._tupleID);
    assertEquals(payload1._cellID, payload2._cellID);

    payload1 = new PackedIntSirenPayload(0, 1);
    payload2 = new PackedIntSirenPayload(payload1.getData());
    assertEquals(payload1._tupleID, payload2._tupleID);
    assertEquals(payload1._cellID, payload2._cellID);
  }

  @Test
  public void testSpecialCases()
  throws Exception {
    PackedIntSirenPayload payload1 = new PackedIntSirenPayload(0, 1);
    PackedIntSirenPayload payload2 = new PackedIntSirenPayload(payload1.getData());
    assertEquals(payload1._tupleID, payload2._tupleID);
    assertEquals(payload1._cellID, payload2._cellID);

    payload1 = new PackedIntSirenPayload(1, 0);
    payload2 = new PackedIntSirenPayload(payload1.getData());
    assertEquals(payload1._tupleID, payload2._tupleID);
    assertEquals(payload1._cellID, payload2._cellID);

    payload1 = new PackedIntSirenPayload(1, 1);
    payload2 = new PackedIntSirenPayload(payload1.getData());
    assertEquals(payload1._tupleID, payload2._tupleID);
    assertEquals(payload1._cellID, payload2._cellID);
  }


  @Test
  public void testRandomPackedInt88()
  throws Exception {
    final Random r = new Random(42);
    for (int i = 0; i < 1000; i++) {
      final int value1 = r.nextInt(255);
      final int value2 = r.nextInt(255);
      final PackedIntSirenPayload payload1 = new PackedIntSirenPayload(value1, value2);
      final PackedIntSirenPayload payload2 = new PackedIntSirenPayload(payload1.getData());
      assertEquals(payload1._tupleID, payload2._tupleID);
      assertEquals(payload1._cellID, payload2._cellID);
    }
  }

  @Test
  public void testRandomPackedInt44()
  throws Exception {
    final Random r = new Random(42);
    for (int i = 0; i < 1000; i++) {
      final int value1 = r.nextInt(15);
      final int value2 = r.nextInt(15);
      final PackedIntSirenPayload payload1 = new PackedIntSirenPayload(value1, value2);
      final PackedIntSirenPayload payload2 = new PackedIntSirenPayload(payload1.getData());
      assertEquals("Failed: [" + value1 + "," + value2 + "]", payload1._tupleID, payload2._tupleID);
      assertEquals("Failed: [" + value1 + "," + value2 + "]", payload1._cellID, payload2._cellID);
    }
  }

  @Test
  public void testRandomPackedInt168()
  throws Exception {
    final Random r = new Random(42);
    for (int i = 0; i < 1000; i++) {
      final int value1 = r.nextInt(65535);
      final int value2 = r.nextInt(255);
      final PackedIntSirenPayload payload1 = new PackedIntSirenPayload(value1, value2);
      final PackedIntSirenPayload payload2 = new PackedIntSirenPayload(payload1.getData());
      assertEquals(payload1._tupleID, payload2._tupleID);
      assertEquals(payload1._cellID, payload2._cellID);
    }
  }

  /**
   * SRN-82
   */
  @Test
  public void testRandomPackedInt2816()
  throws Exception {
    final Random r = new Random(42);
    for (int i = 0; i < 1000; i++) {
      final int value1 = r.nextInt(268435455);
      final int value2 = r.nextInt(65535);
      final PackedIntSirenPayload payload1 = new PackedIntSirenPayload(value1, value2);
      final PackedIntSirenPayload payload2 = new PackedIntSirenPayload(payload1.getData());
      assertEquals(payload1._tupleID, payload2._tupleID);
      assertEquals(payload1._cellID, payload2._cellID);
    }
  }

}
