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
