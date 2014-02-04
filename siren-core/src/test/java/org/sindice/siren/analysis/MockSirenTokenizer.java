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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.attributes.DatatypeAttribute;
import org.sindice.siren.analysis.attributes.NodeAttribute;
import org.sindice.siren.analysis.attributes.TupleNodeAttributeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockSirenTokenizer extends Tokenizer {

  MockSirenDocument doc;

  // the TupleTokenizer generates 6 attributes:
  // term, offset, positionIncrement, type, datatype, node
  private final CharTermAttribute termAtt;
  private final OffsetAttribute offsetAtt;
  private final PositionIncrementAttribute posIncrAtt;
  private final TypeAttribute typeAtt;
  private final DatatypeAttribute dtypeAtt;
  private final NodeAttribute nodeAtt;

  Iterator<ArrayList<MockSirenToken>> nodeIt = null;
  Iterator<MockSirenToken> tokenIt = null;

  protected static final Logger logger = LoggerFactory.getLogger(MockSirenTokenizer.class);

  public MockSirenTokenizer(final MockSirenReader reader) {
    super(reader);

    this.doc = reader.getDocument();
    nodeIt = doc.iterator();

    termAtt = this.addAttribute(CharTermAttribute.class);
    offsetAtt = this.addAttribute(OffsetAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
    dtypeAtt = this.addAttribute(DatatypeAttribute.class);
    if (!this.hasAttribute(NodeAttribute.class)) {
      this.addAttributeImpl(new TupleNodeAttributeImpl());
    }
    nodeAtt = this.addAttribute(NodeAttribute.class);
  }

  @Override
  public final boolean incrementToken() throws IOException {
    this.clearAttributes();

    final MockSirenToken token;
    while (nodeIt.hasNext() || (tokenIt != null && tokenIt.hasNext())) {
      if (tokenIt == null || !tokenIt.hasNext()) { // new node
        tokenIt = nodeIt.next().iterator(); // move to next node
      }

      token = tokenIt.next();
      termAtt.copyBuffer(token.term, 0, token.term.length);
      offsetAtt.setOffset(token.startOffset, token.endOffset);
      typeAtt.setType(TupleTokenizer.getTokenTypes()[token.tokenType]);
      posIncrAtt.setPositionIncrement(token.posInc);
      dtypeAtt.setDatatypeURI(token.datatype);
      for (int i = 0; i < token.nodePath.length; i++) {
        nodeAtt.append(token.nodePath.ints[i]);
      }
      return true;
    }

    return false;
  }

  @Override
  public void reset() {
    final MockSirenReader reader = (MockSirenReader) this.input;
    this.doc = reader.getDocument();
    nodeIt = doc.iterator();
    this.clearAttributes();
  }

}
