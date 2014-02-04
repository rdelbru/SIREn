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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;
import org.sindice.siren.analysis.TupleTokenizer;

public class TestURINormalisationFilter {

  private final Tokenizer _t = new TupleTokenizer(new StringReader(""));

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

  public void assertNormalisesTo(final Tokenizer t, final String input,
                                 final String[] expected)
   throws Exception {
     this.assertNormalisesTo(t, input, expected, null);
   }

   public void assertNormalisesTo(final Tokenizer t, final String input,
                                 final String[] expectedImages,
                                 final String[] expectedTypes)
   throws Exception {

     assertTrue("has CharTermAttribute", t.hasAttribute(CharTermAttribute.class));
     final CharTermAttribute termAtt = t.getAttribute(CharTermAttribute.class);

     TypeAttribute typeAtt = null;
     if (expectedTypes != null) {
       assertTrue("has TypeAttribute", t.hasAttribute(TypeAttribute.class));
       typeAtt = t.getAttribute(TypeAttribute.class);
     }

     t.setReader(new StringReader(input));
     t.reset();

     final TokenStream filter = new URINormalisationFilter(t);

     for (int i = 0; i < expectedImages.length; i++) {

       assertTrue("token "+i+" exists", filter.incrementToken());

       assertEquals(expectedImages[i], termAtt.toString());

       if (expectedTypes != null) {
         assertEquals(expectedTypes[i], typeAtt.type());
       }

     }

     assertFalse("end of stream", filter.incrementToken());
     filter.end();
     filter.close();
   }

}
