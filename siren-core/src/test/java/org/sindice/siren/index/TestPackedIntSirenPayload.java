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
