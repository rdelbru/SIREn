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
