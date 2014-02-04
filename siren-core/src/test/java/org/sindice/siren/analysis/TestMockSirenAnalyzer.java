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
package org.sindice.siren.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.sindice.siren.analysis.MockSirenDocument.doc;
import static org.sindice.siren.analysis.MockSirenToken.node;
import static org.sindice.siren.analysis.MockSirenToken.token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.sindice.siren.analysis.attributes.NodeAttribute;
import org.sindice.siren.analysis.attributes.PositionAttribute;

public class TestMockSirenAnalyzer {

  @Test
  public void testMockSirenDocument() {
    final MockSirenDocument doc = doc(token("aaa", node(1)), token("aaa", node(1,0)), token("aaa", node(1)));
    final Iterator<ArrayList<MockSirenToken>> nodeIt = doc.iterator();

    assertTrue(nodeIt.hasNext());
    ArrayList<MockSirenToken> tokens = nodeIt.next();
    assertEquals(2, tokens.size());
    assertEquals(node(1), tokens.get(0).nodePath);
    assertEquals(node(1), tokens.get(1).nodePath);

    assertTrue(nodeIt.hasNext());
    tokens = nodeIt.next();
    assertEquals(1, tokens.size());
    assertEquals(node(1,0), tokens.get(0).nodePath);
  }

  @Test
  public void testMockSirenAnalyzer() throws IOException {
    final MockSirenDocument doc = doc(token("aaa", node(1)), token("aaa", node(1,0)), token("aaa", node(1)));
    final MockSirenAnalyzer analyzer = new MockSirenAnalyzer();
    final TokenStream ts = analyzer.tokenStream("", new MockSirenReader(doc));

    assertTrue(ts.incrementToken());
    assertEquals("aaa", ts.getAttribute(CharTermAttribute.class).toString());
    assertEquals(node(1), ts.getAttribute(NodeAttribute.class).node());
    assertEquals(0, ts.getAttribute(PositionAttribute.class).position());

    assertTrue(ts.incrementToken());
    assertEquals("aaa", ts.getAttribute(CharTermAttribute.class).toString());
    assertEquals(node(1), ts.getAttribute(NodeAttribute.class).node());
    assertEquals(1, ts.getAttribute(PositionAttribute.class).position());

    assertTrue(ts.incrementToken());
    assertEquals("aaa", ts.getAttribute(CharTermAttribute.class).toString());
    assertEquals(node(1,0), ts.getAttribute(NodeAttribute.class).node());
    assertEquals(0, ts.getAttribute(PositionAttribute.class).position());

  }

}
