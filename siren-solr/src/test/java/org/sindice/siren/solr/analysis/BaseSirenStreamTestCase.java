/**
 * Copyright 2010, Renaud Delbru
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
 * @project siren
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public abstract class BaseSirenStreamTestCase {

  public BaseSirenStreamTestCase() {
    System.setProperty("solr.solr.home", "src/test/resources/solr.home/");
  }

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
    args.put("luceneMatchVersion", "LUCENE_31");
    args.put("subschema", "ntriple-schema.xml");
    args.put("literal-fieldtype", "ntriple-literal");
    return args;
  }

}
