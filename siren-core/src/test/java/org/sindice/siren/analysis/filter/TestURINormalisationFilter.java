/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
 * @project siren
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.filter;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;
import org.sindice.siren.analysis.TupleTokenizer;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;

public class TestURINormalisationFilter {

  private final Tokenizer _t = new TupleTokenizer(new StringReader(""), Integer.MAX_VALUE);

  public void assertNormalisesTo(final Tokenizer t, final String input,
                                final String[] expected)
  throws Exception {
    this.assertNormalisesTo(t, input, expected, null);
  }

  public void assertNormalisesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes)
  throws Exception {
    this.assertNormalisesTo(t, input, expectedImages, expectedTypes, null);
  }

  public void assertNormalisesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final int[] expectedPosIncrs)
  throws Exception {
    this.assertNormalisesTo(t, input, expectedImages, expectedTypes, null, null,
      null);
  }

  public void assertNormalisesTo(final Tokenizer t, final String input,
                                final String[] expectedImages,
                                final String[] expectedTypes,
                                final int[] expectedPosIncrs,
                                final int[] expectedTupleID,
                                final int[] expectedCellID)
  throws Exception {

    assertTrue("has TermAttribute", t.hasAttribute(TermAttribute.class));
    final TermAttribute termAtt = t.getAttribute(TermAttribute.class);

    TypeAttribute typeAtt = null;
    if (expectedTypes != null) {
      assertTrue("has TypeAttribute", t.hasAttribute(TypeAttribute.class));
      typeAtt = t.getAttribute(TypeAttribute.class);
    }

    PositionIncrementAttribute posIncrAtt = null;
    if (expectedPosIncrs != null) {
      assertTrue("has PositionIncrementAttribute", t.hasAttribute(PositionIncrementAttribute.class));
      posIncrAtt = t.getAttribute(PositionIncrementAttribute.class);
    }

    TupleAttribute tupleAtt = null;
    if (expectedTupleID != null) {
      assertTrue("has TupleAttribute", t.hasAttribute(TupleAttribute.class));
      tupleAtt = t.getAttribute(TupleAttribute.class);
    }

    CellAttribute cellAtt = null;
    if (expectedCellID != null) {
      assertTrue("has CellAttribute", t.hasAttribute(CellAttribute.class));
      cellAtt = t.getAttribute(CellAttribute.class);
    }

    t.reset(new StringReader(input));
    final TokenStream filter = new URINormalisationFilter(t);

    for (int i = 0; i < expectedImages.length; i++) {

      assertTrue("token "+i+" exists", filter.incrementToken());

      assertEquals(expectedImages[i], termAtt.term());

      if (expectedTypes != null) {
        assertEquals(expectedTypes[i], typeAtt.type());
      }

      if (expectedPosIncrs != null) {
        assertEquals(expectedPosIncrs[i], posIncrAtt.getPositionIncrement());
      }

      if (expectedTupleID != null) {
        assertEquals(expectedTupleID[i], tupleAtt.tuple());
      }

      if (expectedCellID != null) {
        assertEquals(expectedCellID[i], cellAtt.cell());
      }
    }

    assertFalse("end of stream", filter.incrementToken());
    filter.end();
  }

  @Test
  public void testURI()
  throws Exception {
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr/>",
      new String[] { "renaud", "delbru", "http://renaud.delbru.fr/" }, new String[] { "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr>",
      new String[] { "renaud", "delbru", "http://renaud.delbru.fr" }, new String[] { "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://user@renaud.delbru.fr>",
      new String[] { "user", "renaud", "delbru", "http://user@renaud.delbru.fr" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://user:passwd@renaud.delbru.fr>",
      new String[] { "user", "passwd", "renaud", "delbru", "http://user:passwd@renaud.delbru.fr" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr:8080>",
      new String[] { "renaud", "delbru", "8080", "http://renaud.delbru.fr:8080" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://renaud.delbru.fr/page.html#fragment>",
      new String[] { "renaud", "delbru", "page", "html", "fragment", "http://renaud.delbru.fr/page.html#fragment" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(
      _t,
      "<http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=N>",
      new String[] { "renaud", "delbru", "page", "html", "query", "query", "start", "http://renaud.delbru.fr/page.html?query=a+query&hl=en&start=20&sa=N" }, new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<mailto:renaud@delbru.fr>",
      new String[] { "renaud", "delbru", "mailto:renaud@delbru.fr" }, new String[] { "<URI>", "<URI>", "<URI>" });
    this.assertNormalisesTo(_t, "<http://xmlns.com/foaf/0.1/workplaceHomepage/>",
      new String[] { "xmlns", "foaf", "workplace", "Homepage", "http://xmlns.com/foaf/0.1/workplaceHomepage/" },
      new String[] { "<URI>", "<URI>", "<URI>", "<URI>", "<URI>" });
  }

}
