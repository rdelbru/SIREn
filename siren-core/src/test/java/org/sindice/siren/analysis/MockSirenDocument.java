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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.lucene.util.IntsRef;

public class MockSirenDocument {

  TreeMap<IntsRef, ArrayList<MockSirenToken>> sortedTokens;

  private final Comparator<IntsRef> INTS_COMP = new Comparator<IntsRef>() {

    public int compare(final IntsRef ints1, final IntsRef ints2) {
        return ints1.compareTo(ints2);
    }

  };

  public MockSirenDocument(final MockSirenToken ... tokens) {
    sortedTokens = new TreeMap<IntsRef, ArrayList<MockSirenToken>>(INTS_COMP);

    IntsRef ints;
    for (final MockSirenToken token : tokens) {
      ints = token.nodePath;
      if (!sortedTokens.containsKey(ints)) {
        sortedTokens.put(ints, new ArrayList<MockSirenToken>());
      }
      sortedTokens.get(ints).add(token);
    }
  }

  public Iterator<ArrayList<MockSirenToken>> iterator() {
    return sortedTokens.values().iterator();
  }

  public static MockSirenDocument doc(final MockSirenToken ... tokens) {
    return new MockSirenDocument(tokens);
  }

}

