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

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.junit.Test;
import org.sindice.siren.analysis.JsonTokenizer;

public class TestURILocalnameFilterFactory
extends BaseSirenStreamTestCase {

  @Test
  public void testMaxLength() throws Exception {
    final Map<String,String> args = this.getDefaultInitArgs();
    args.put(URILocalnameFilterFactory.MAXLENGTH_KEY, "8");
    final URILocalnameFilterFactory factory = new URILocalnameFilterFactory();
    factory.init(args);

    final Reader reader = new StringReader("{ \"uri\" : \"http://test/anotherLocalname\" }");
    final TokenStream stream = factory.create(new JsonTokenizer(reader));
    this.assertTokenStreamContents(stream,
        new String[] { "uri", "anotherLocalname", "http://test/anotherLocalname" });
  }

}
