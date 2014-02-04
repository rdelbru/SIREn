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

package org.sindice.siren.analysis.filter;


import java.util.Random;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.LuceneTestCase;
import org.junit.Test;

public class TestVIntSirenPayload extends LuceneTestCase {

  VIntPayloadCodec codec = new VIntPayloadCodec();

  @Test
  public void testSimpleVInt()
  throws Exception {
    IntsRef ints = new IntsRef(new int[] { 12,43 }, 0, 2);
    int pos = 256;
    BytesRef bytes = codec.encode(ints, pos);
    codec.decode(bytes);

    IntsRef node = codec.getNode();
    assertEquals(ints.ints[0], node.ints[node.offset]);
    assertEquals(ints.ints[1], node.ints[node.offset + 1]);
    assertEquals(pos, codec.getPosition());

    ints = new IntsRef(new int[] { 3, 2 }, 0, 2);
    pos = 2;
    bytes = codec.encode(ints, pos);
    codec.decode(bytes);

    node = codec.getNode();
    assertEquals(ints.ints[0], node.ints[node.offset]);
    assertEquals(ints.ints[1], node.ints[node.offset + 1]);
    assertEquals(pos, codec.getPosition());

    ints = new IntsRef(new int[] { 0, 1 }, 0, 2);
    pos = 0;
    bytes = codec.encode(ints, pos);
    codec.decode(bytes);

    node = codec.getNode();
    assertEquals(ints.ints[0], node.ints[node.offset]);
    assertEquals(ints.ints[1], node.ints[node.offset + 1]);
    assertEquals(pos, codec.getPosition());
  }

  @Test
  public void testRandomVInt2()
  throws Exception {
    final Random r = LuceneTestCase.random();
    for (int i = 0; i < 10000; i++) {
      final int value1 = r.nextInt(Integer.MAX_VALUE);
      final int value2 = r.nextInt(Integer.MAX_VALUE);

      final IntsRef ints = new IntsRef(new int[] { value1,value2 }, 0, 2);
      final int pos = r.nextInt(Integer.MAX_VALUE);
      final BytesRef bytes = codec.encode(ints, pos);
      codec.decode(bytes);

      final IntsRef node = codec.getNode();
      assertEquals(ints.ints[0], node.ints[node.offset]);
      assertEquals(ints.ints[1], node.ints[node.offset + 1]);
      assertEquals(pos, codec.getPosition());
    }
  }

  @Test
  public void testRandomVInt3()
  throws Exception {
    final Random r = LuceneTestCase.random();
    for (int i = 0; i < 10000; i++) {
      final int value1 = r.nextInt(Integer.MAX_VALUE);
      final int value2 = r.nextInt(Integer.MAX_VALUE);
      final int value3 = r.nextInt(Integer.MAX_VALUE);

      final IntsRef ints = new IntsRef(new int[] { value1,value2,value3 }, 0, 3);
      final int pos = r.nextInt(Integer.MAX_VALUE);
      final BytesRef bytes = codec.encode(ints, pos);
      codec.decode(bytes);

      final IntsRef node = codec.getNode();
      assertEquals(ints.ints[0], node.ints[node.offset]);
      assertEquals(ints.ints[1], node.ints[node.offset + 1]);
      assertEquals(ints.ints[2], node.ints[node.offset + 2]);
      assertEquals(pos, codec.getPosition());
    }
  }

}
