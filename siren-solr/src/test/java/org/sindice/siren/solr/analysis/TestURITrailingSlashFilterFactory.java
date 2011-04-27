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

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.sindice.siren.analysis.TupleTokenizer;

public class TestURITrailingSlashFilterFactory
extends BaseSirenStreamTestCase {

  @Test
  public void testURITrailingSlashFilter() throws Exception {
    final Map<String,String> args = this.getDefaultInitArgs();
    final URITrailingSlashFilterFactory factory = new URITrailingSlashFilterFactory();
    factory.init(args);

    final Reader reader = new StringReader("<http://renaud.delbru.fr/> . ");
    final TokenStream stream = factory.create(new TupleTokenizer(reader,
      Integer.MAX_VALUE, new WhitespaceAnalyzer(Version.LUCENE_31)));
    this.assertTokenStreamContents(stream,
        new String[] {"http://renaud.delbru.fr", "." });
  }

}
