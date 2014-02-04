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

import org.apache.lucene.util.IntsRef;
import org.sindice.siren.util.XSDDatatype;

public class MockSirenToken {

  char[] term;
  int startOffset, endOffset;
  int posInc;
  int tokenType;
  char[] datatype;
  IntsRef nodePath;

  private MockSirenToken(final char[] term, final int startOffset,
                         final int endOffset, final int posInc,
                         final int tokenType, final char[] datatype,
                         final IntsRef nodePath) {
    this.term = term;
    this.startOffset = startOffset;
    this.endOffset = endOffset;
    this.posInc = posInc;
    this.tokenType = tokenType;
    this.datatype = datatype;
    this.nodePath = nodePath;
  }

  public static MockSirenToken token(final String term, final IntsRef nodePath) {
    return token(term, 0, 0, 1, TupleTokenizer.LITERAL,
      XSDDatatype.XSD_STRING.toCharArray(), nodePath);
  }

  public static MockSirenToken token(final String term, final int startOffset,
                                     final int endOffset, final int posInc,
                                     final int tokenType, final char[] datatype,
                                     final IntsRef nodePath) {
    return new MockSirenToken(term.toCharArray(), startOffset, endOffset,
      posInc, tokenType, datatype, nodePath);
  }

  public static IntsRef node(final int ... id) {
    return new IntsRef(id, 0, id.length);
  }

}
