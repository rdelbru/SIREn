/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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
