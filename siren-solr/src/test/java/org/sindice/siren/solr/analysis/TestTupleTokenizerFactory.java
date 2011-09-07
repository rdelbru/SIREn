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

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.junit.Test;

public class TestTupleTokenizerFactory extends BaseSirenStreamTestCase {

  @Test
  public void testTupleTokenizer1() throws Exception {
    final Reader reader = new StringReader("<aaa> <bbb> \"ooo\" . \"aaa\" <ooo> . ");
    final Map<String,String> args = this.getDefaultInitArgs();
    final TupleTokenizerFactory factory = new TupleTokenizerFactory();
    factory.init(args);
    final Tokenizer stream = factory.create(reader);
    this.assertTokenStreamContents(stream,
        new String[] {"aaa", "bbb", "ooo", ".", "aaa", "ooo", "." });
  }

  @Test
  public void testTupleTokenizer2() throws Exception {
    final Reader reader = new StringReader("\"ooo aaa uuu\"@en . \"aaa\" _:bn1 . ");
    final Map<String,String> args = this.getDefaultInitArgs();
    final TupleTokenizerFactory factory = new TupleTokenizerFactory();
    factory.init(args);
    final Tokenizer stream = factory.create(reader);
    this.assertTokenStreamContents(stream,
        new String[] {"ooo", "aaa", "uuu", "en", ".", "aaa", "bn1", "." });
  }

  @Test
  public void testTupleTokenizerMaxLength() throws Exception {
    final Map<String,String> args = this.getDefaultInitArgs();
    args.put(TupleTokenizerFactory.MAXLENGTH_KEY, "3");
    final TupleTokenizerFactory factory = new TupleTokenizerFactory();
    factory.init(args);

    final Reader reader = new StringReader("\"ooo aaa uuu iii eee\"@en .");
    final Tokenizer stream = factory.create(reader);
    this.assertTokenStreamContents(stream,
        new String[] {"ooo", "aaa", "uuu" });
  }

  @Test
  public void testCharacterEncoding1() throws Exception {
    final Reader reader = new StringReader("<http://test.com/M\u00F6ller> \"M\u00F6ller\" . ");
    final Map<String,String> args = this.getDefaultInitArgs();
    final TupleTokenizerFactory factory = new TupleTokenizerFactory();
    factory.init(args);
    final Tokenizer stream = factory.create(reader);
    // literal Möller is expanded in two forms: accented and non-accented
    this.assertTokenStreamContents(stream,
        new String[] {"http://test.com/Möller", "moller", "möller", "."});
  }

  @Test
  public void testCharacterEncoding2() throws Exception {
    final Reader reader = new StringReader("<http://test.com/Möller> \"Möller\" . ");
    final Map<String,String> args = this.getDefaultInitArgs();
    final TupleTokenizerFactory factory = new TupleTokenizerFactory();
    factory.init(args);
    final Tokenizer stream = factory.create(reader);
    // literal Möller is expanded in two forms: accented and non-accented
    this.assertTokenStreamContents(stream,
        new String[] {"http://test.com/Möller", "moller", "möller", "."});
  }

}
