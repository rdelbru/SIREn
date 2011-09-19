/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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

  private void assertURLDecodedTo(final Tokenizer t, final String uri, final String[] expectedStems)
  throws IOException {
    this.assertURLDecodedTo(t, uri, expectedStems, null);
  }

  private void assertURLDecodedTo(final Tokenizer t, final String uri, final String[] expectedStems, final String[] expectedTypes)
  throws IOException {
    this.assertURLDecodedTo(t, uri, expectedStems, expectedTypes, null);
  }

  private void assertURLDecodedTo(final Tokenizer t, final String uri, final String[] expectedStems, final String[] expectedTypes, final int[] expectedPosIncr)
  throws IOException {
    assertTrue("has CharTermAttribute", t.hasAttribute(CharTermAttribute.class));
    final CharTermAttribute termAtt = t.getAttribute(CharTermAttribute.class);

    assertTrue("has TypeAttribute", t.hasAttribute(TypeAttribute.class));
    final TypeAttribute typeAtt = t.getAttribute(TypeAttribute.class);

    assertTrue("has PositionIncrementAttribute", t.hasAttribute(PositionIncrementAttribute.class));
    final PositionIncrementAttribute posIncrAtt = t.getAttribute(PositionIncrementAttribute.class);

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
      new String[] { "stephane.campinas@deri.org", "mailto:stephane.campinas@deri.org" });
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
      new String[] { "stephane.net", "mailto:stephane.net", "literal",
                     "stephane.campinas@deri.org", "mailto:stephane.campinas@deri.org" },
      new String[] { uritype, uritype, defaulttype, uritype, uritype},
      new int[] { 1, 0, 1, 1, 0 });
  }

  @Test
  public void testShortURI()
  throws Exception {
    this.assertURLDecodedTo(_t, "<steph>", new String[] { "steph" });
  }

}
