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
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.analysis;

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.junit.Test;
import org.sindice.siren.analysis.TupleTokenizer;

public class TestTokenTypeFilterFactory
extends BaseSirenStreamTestCase {

  @Test
  public void testTokenTypeFilter1() throws Exception {
    final Map<String,String> args = this.getDefaultInitArgs();
    final TokenTypeFilterFactory factory = new TokenTypeFilterFactory();
    factory.init(args);

    final Reader reader = new StringReader("<aaa> <bbb> \"ooo\" . \"aaa\" <ooo> . ");
    final TokenStream stream = factory.create(new TupleTokenizer(reader,
      Integer.MAX_VALUE));
    this.assertTokenStreamContents(stream,
        new String[] {"aaa", "bbb", "ooo", "aaa", "ooo" });
  }

  @Test
  public void testTokenTypeFilter2() throws Exception {
    final Map<String,String> args = this.getDefaultInitArgs();
    final TokenTypeFilterFactory factory = new TokenTypeFilterFactory();
    factory.init(args);

    final Reader reader = new StringReader("\"ooo aaa uuu\"@en . \"aaa\" _:bn1 . ");
    final TokenStream stream = factory.create(new TupleTokenizer(reader,
      Integer.MAX_VALUE));
    this.assertTokenStreamContents(stream,
        new String[] {"ooo aaa uuu", "aaa" });
  }

  @Test
  public void testRetainBNode() throws Exception {
    final Map<String,String> args = this.getDefaultInitArgs();
    args.put(TokenTypeFilterFactory.BNODE_KEY, "0");
    final TokenTypeFilterFactory factory = new TokenTypeFilterFactory();
    factory.init(args);

    final Reader reader = new StringReader("\"ooo aaa uuu\"@en . \"aaa\" _:bn1 . ");
    final TokenStream stream = factory.create(new TupleTokenizer(reader,
      Integer.MAX_VALUE));
    this.assertTokenStreamContents(stream,
        new String[] {"ooo aaa uuu", "aaa", "bn1" });
  }

}
