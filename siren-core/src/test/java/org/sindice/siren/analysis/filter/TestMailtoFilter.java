/**
 * Copyright 2011, Campinas Stephane
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren-core
 * @author Campinas Stephane [ 21 May 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.analysis.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.sindice.siren.analysis.TupleTokenizer;


/**
 * 
 */
public class TestMailtoFilter {

  private final String uritype = TupleTokenizer.getTokenTypes()[TupleTokenizer.URI];
  private final String defaulttype = TypeAttribute.DEFAULT_TYPE;
  
  private final Tokenizer _t = new TupleTokenizer(new StringReader(""),
    Integer.MAX_VALUE, new WhitespaceAnalyzer(Version.LUCENE_31));
  
  /*
   * Helpers
   */
  
  private void assertURLDecodedTo(Tokenizer t, String uri, String[] expectedStems)
  throws IOException {
    this.assertURLDecodedTo(t, uri, expectedStems, null);
  }
  
  private void assertURLDecodedTo(Tokenizer t, String uri, String[] expectedStems, String[] expectedTypes)
  throws IOException {
    this.assertURLDecodedTo(t, uri, expectedStems, expectedTypes, null);
  }
  
  private void assertURLDecodedTo(Tokenizer t, String uri, String[] expectedStems, String[] expectedTypes, int[] expectedPosIncr)
  throws IOException {
    assertTrue("has CharTermAttribute", t.hasAttribute(CharTermAttribute.class));
    final CharTermAttribute termAtt = t.getAttribute(CharTermAttribute.class);

    assertTrue("has TypeAttribute", t.hasAttribute(TypeAttribute.class));
    final TypeAttribute typeAtt = t.getAttribute(TypeAttribute.class);

    assertTrue("has PositionIncrementAttribute", t.hasAttribute(PositionIncrementAttribute.class));
    PositionIncrementAttribute posIncrAtt = t.getAttribute(PositionIncrementAttribute.class);
    
    t.reset(new StringReader(uri));
    final TokenFilter filter = new MailtoFilter(t);
    for (int i = 0; i < expectedStems.length; i++) {
        assertTrue("token " + i + " exists", filter.incrementToken());
        assertEquals(expectedStems[i], termAtt.toString());
        if (expectedTypes == null)
          assertEquals(uritype, typeAtt.type());
        else
          assertEquals(expectedTypes[i], typeAtt.type());
        if (expectedPosIncr != null)
          assertEquals(expectedPosIncr[i], posIncrAtt.getPositionIncrement());
    }
    filter.end();
  }

  @Test
  public void testNoMailto()
  throws Exception {
    this.assertURLDecodedTo(_t, "<http://stephane.net>", new String[] { "http://stephane.net" });
  }
  
  @Test
  public void testMailto()
  throws Exception {
    this.assertURLDecodedTo(_t, "<mailto:stephane.campinas@deri.org>",
      new String[] { "mailto", "stephane.campinas@deri.org", "mailto:stephane.campinas@deri.org" });
  }

  @Test
  public void testBadMailto()
  throws Exception {
    this.assertURLDecodedTo(_t, "<mailto//stephane.net>", new String[] { "mailto//stephane.net" });
    this.assertURLDecodedTo(_t, "<mailTo:stephane.net>", new String[] { "mailTo:stephane.net" });
  }
  
  @Test
  public void testDifferentTypes()
  throws Exception {
    this.assertURLDecodedTo(_t, "<mailto:stephane.net> \"literal\" <mailto:stephane.campinas@deri.org>",
      new String[] { "mailto", "stephane.net", "mailto:stephane.net", "literal",
                     "mailto", "stephane.campinas@deri.org", "mailto:stephane.campinas@deri.org" },
      new String[] { uritype, uritype, uritype, defaulttype, uritype, uritype, uritype},
      new int[] { 1, 1, 0, 1, 1, 1, 0 });
  }
  
  @Test
  public void testShortURI()
  throws Exception {
    this.assertURLDecodedTo(_t, "<steph>", new String[] { "steph" });
  }
  
}
