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
 * @project siren-solr
 * @author Campinas Stephane [ 21 May 2011 ]
 * @link stephane.campinas@deri.org
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

/**
 * 
 */
public class TestURIEncodingFilterFactory extends BaseSirenStreamTestCase {

  @Test
  public void testURLencodedURI()
  throws Exception {    
    final Map<String,String> args = this.getDefaultInitArgs();
    final URIEncodingFilterFactory factory = new URIEncodingFilterFactory();
    factory.init(args);

    final Reader reader = new StringReader("<http://stephane.net/who%3fwho>");
    final TokenStream stream = factory.create(new TupleTokenizer(reader, Integer.MAX_VALUE, new WhitespaceAnalyzer(Version.LUCENE_31)));
    this.assertTokenStreamContents(stream, new String[] {"http://stephane.net/who%3fwho", "http://stephane.net/who?who" });
  }
  
}
