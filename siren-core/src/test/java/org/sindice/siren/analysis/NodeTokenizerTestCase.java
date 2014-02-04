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

import java.io.StringReader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.LuceneTestCase;
import org.sindice.siren.analysis.attributes.DatatypeAttribute;
import org.sindice.siren.analysis.attributes.NodeAttribute;

public abstract class NodeTokenizerTestCase extends LuceneTestCase {

  protected void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes)
  throws Exception {
    this.assertTokenizesTo(t, input, expectedImages, expectedTypes, null, null, null);
  }

  protected void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final String[] expectedDatatypes)
  throws Exception {
    this.assertTokenizesTo(t, input, expectedImages, expectedTypes, expectedDatatypes, null, null);
  }

  protected void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final int[] expectedPosIncrs,
                                final IntsRef[] expectedNode)
  throws Exception {
    this.assertTokenizesTo(t, input, expectedImages, expectedTypes, null,
      expectedPosIncrs, expectedNode);
  }

  protected void assertTokenizesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final String[] expectedDatatypes,
                                final int[] expectedPosIncrs,
                                final IntsRef[] expectedNode)
  throws Exception {

    assertTrue("has TermAttribute", t.hasAttribute(CharTermAttribute.class));
    final CharTermAttribute termAtt = t.getAttribute(CharTermAttribute.class);

    TypeAttribute typeAtt = null;
    if (expectedTypes != null) {
      assertTrue("has TypeAttribute", t.hasAttribute(TypeAttribute.class));
      typeAtt = t.getAttribute(TypeAttribute.class);
    }

    DatatypeAttribute dtypeAtt = null;
    if (expectedDatatypes != null) {
      assertTrue("has DatatypeAttribute", t.hasAttribute(DatatypeAttribute.class));
      dtypeAtt = t.getAttribute(DatatypeAttribute.class);
    }

    PositionIncrementAttribute posIncrAtt = null;
    if (expectedPosIncrs != null) {
      assertTrue("has PositionIncrementAttribute", t.hasAttribute(PositionIncrementAttribute.class));
      posIncrAtt = t.getAttribute(PositionIncrementAttribute.class);
    }

    NodeAttribute nodeAtt = null;
    if (expectedNode != null) {
      assertTrue("has NodeAttribute", t.hasAttribute(NodeAttribute.class));
      nodeAtt = t.getAttribute(NodeAttribute.class);
    }

    t.setReader(new StringReader(input));
    t.reset(); // reset the stream for the new reader

    for (int i = 0; i < expectedImages.length; i++) {

      assertTrue("token "+i+" exists", t.incrementToken());

      assertEquals("i=" + i, expectedImages[i], termAtt.toString());

      if (expectedTypes != null) {
        assertEquals("i=" + i, expectedTypes[i], typeAtt.type());
      }

      if (expectedDatatypes != null) {
        assertEquals("i=" + i, expectedDatatypes[i], dtypeAtt.datatypeURI() == null ? "" : String.valueOf(dtypeAtt.datatypeURI()));
      }

      if (expectedPosIncrs != null) {
        assertEquals("i=" + i, expectedPosIncrs[i], posIncrAtt.getPositionIncrement());
      }

      if (expectedNode != null) {
        assertEquals("i=" + i, expectedNode[i], nodeAtt.node());
      }

    }

    assertFalse("end of stream", t.incrementToken());
    t.end();
  }

}
