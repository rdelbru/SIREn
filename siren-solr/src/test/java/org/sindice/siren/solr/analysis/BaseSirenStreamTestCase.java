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

package org.sindice.siren.solr.analysis;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.LuceneTestCase;
import org.apache.solr.schema.IndexSchema;
import org.sindice.siren.solr.BaseSolrServerTestCase;

public abstract class BaseSirenStreamTestCase extends BaseSolrServerTestCase {

  public void assertTokenStreamContents(final TokenStream stream,
                                        final String[] expectedImages)
  throws Exception {
    assertTrue("has TermAttribute", stream.hasAttribute(CharTermAttribute.class));
    final CharTermAttribute termAtt = stream.getAttribute(CharTermAttribute.class);

    stream.reset();
    for (int i = 0; i < expectedImages.length; i++) {
      stream.clearAttributes();
      assertTrue("token "+i+" does not exists", stream.incrementToken());

      assertEquals(expectedImages[i], termAtt.toString());
    }

    assertFalse("end of stream", stream.incrementToken());
    stream.end();
    stream.close();
  }

  public Map<String,String> getDefaultInitArgs() {
    final Map<String,String> args = new HashMap<String,String>();
    args.put(IndexSchema.LUCENE_MATCH_VERSION_PARAM, LuceneTestCase.TEST_VERSION_CURRENT.name());
    return args;
  }

}
