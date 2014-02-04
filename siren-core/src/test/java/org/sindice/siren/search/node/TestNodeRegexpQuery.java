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
package org.sindice.siren.search.node;

import static org.sindice.siren.search.AbstractTestSirenScorer.dq;

import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.automaton.Automaton;
import org.apache.lucene.util.automaton.AutomatonProvider;
import org.apache.lucene.util.automaton.BasicAutomata;
import org.apache.lucene.util.automaton.BasicOperations;
import org.apache.lucene.util.automaton.RegExp;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.search.node.NodeRegexpQuery;
import org.sindice.siren.util.BasicSirenTestCase;

/**
 * Code taken from {@link TestRegexpQuery} and adapted for SIREn.
 */
public class TestNodeRegexpQuery extends BasicSirenTestCase {

  @Override
  protected void configure() throws IOException {
    this.setAnalyzer(
      new TupleAnalyzer(TEST_VERSION_CURRENT,
        new WhitespaceAnalyzer(TEST_VERSION_CURRENT),
        new AnyURIAnalyzer(TEST_VERSION_CURRENT))
    );
    this.setPostingsFormat(PostingsFormatType.RANDOM);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    this.addDocument("\"the quick brown fox jumps over the lazy ??? dog 493432 49344\"");
  }

  private Term newTerm(final String value) {
    return new Term(DEFAULT_TEST_FIELD, value);
  }

  private int regexQueryNrHits(final String regex) throws IOException {
    final NodeRegexpQuery query = new NodeRegexpQuery(this.newTerm(regex));
    return searcher.search(dq(query), 5).totalHits;
  }

  public void testRegex1() throws IOException {
    assertEquals(1, this.regexQueryNrHits("q.[aeiou]c.*"));
  }

  public void testRegex2() throws IOException {
    assertEquals(0, this.regexQueryNrHits(".[aeiou]c.*"));
  }

  public void testRegex3() throws IOException {
    assertEquals(0, this.regexQueryNrHits("q.[aeiou]c"));
  }

  public void testNumericRange() throws IOException {
    assertEquals(1, this.regexQueryNrHits("<420000-600000>"));
    assertEquals(0, this.regexQueryNrHits("<493433-600000>"));
  }

  public void testRegexComplement() throws IOException {
    assertEquals(1, this.regexQueryNrHits("4934~[3]"));
    // not the empty lang, i.e. match all docs
    assertEquals(1, this.regexQueryNrHits("~#"));
  }

  public void testCustomProvider() throws IOException {
    final AutomatonProvider myProvider = new AutomatonProvider() {
      // automaton that matches quick or brown
      private final Automaton quickBrownAutomaton = BasicOperations.union(Arrays
          .asList(BasicAutomata.makeString("quick"),
          BasicAutomata.makeString("brown"),
          BasicAutomata.makeString("bob")));

      public Automaton getAutomaton(final String name) {
        if (name.equals("quickBrown")) return quickBrownAutomaton;
        else return null;
      }
    };
    final NodeRegexpQuery query = new NodeRegexpQuery(this.newTerm("<quickBrown>"),
      RegExp.ALL, myProvider);
    assertEquals(1, searcher.search(dq(query), 5).totalHits);
  }

  /**
   * Test a corner case for backtracking: In this case the term dictionary has
   * 493432 followed by 49344. When backtracking from 49343... to 4934, its
   * necessary to test that 4934 itself is ok before trying to append more
   * characters.
   */
  public void testBacktracking() throws IOException {
    assertEquals(1, this.regexQueryNrHits("4934[314]"));
  }

}
