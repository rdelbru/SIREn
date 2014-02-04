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

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.AutomatonQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.ToStringUtils;
import org.apache.lucene.util.automaton.Automaton;
import org.apache.lucene.util.automaton.BasicAutomata;
import org.apache.lucene.util.automaton.BasicOperations;

/**
 * A {@link NodePrimitiveQuery} that implements the wildcard search query.
 *
 * <p>
 *
 * Supported wildcards are <code>*</code>, which
 * matches any character sequence (including the empty one), and <code>?</code>,
 * which matches any single character. Note this query can be slow, as it
 * needs to iterate over many terms. In order to prevent extremely slow WildcardQueries,
 * a Wildcard term should not start with the wildcards <code>*</code>.
 *
 * <p>This query uses the {@link
 * MultiNodeTermQuery#CONSTANT_SCORE_AUTO_REWRITE_DEFAULT}
 * rewrite method.
 *
 * <p> Code taken from {@link WildcardQuery} and adapted for SIREn.
 *
 * @see AutomatonQuery
 **/
public class NodeWildcardQuery extends NodeAutomatonQuery {

  /** String equality with support for wildcards */
  public static final char WILDCARD_STRING = '*';

  /** Char equality with support for wildcards */
  public static final char WILDCARD_CHAR = '?';

  /** Escape character */
  public static final char WILDCARD_ESCAPE = '\\';

  public NodeWildcardQuery(final Term term) {
    super(term, toAutomaton(term));
  }

  /**
   * Convert wildcard syntax into an automaton.
   * @lucene.internal
   */
  @SuppressWarnings("fallthrough")
  public static Automaton toAutomaton(final Term wildcardquery) {
    final List<Automaton> automata = new ArrayList<Automaton>();

    final String wildcardText = wildcardquery.text();

    for (int i = 0; i < wildcardText.length();) {
      final int c = wildcardText.codePointAt(i);
      int length = Character.charCount(c);
      switch(c) {
        case WILDCARD_STRING:
          automata.add(BasicAutomata.makeAnyString());
          break;
        case WILDCARD_CHAR:
          automata.add(BasicAutomata.makeAnyChar());
          break;
        case WILDCARD_ESCAPE:
          // add the next codepoint instead, if it exists
          if (i + length < wildcardText.length()) {
            final int nextChar = wildcardText.codePointAt(i + length);
            length += Character.charCount(nextChar);
            automata.add(BasicAutomata.makeChar(nextChar));
            break;
          } // else fallthru, lenient parsing with a trailing \
        default:
          automata.add(BasicAutomata.makeChar(c));
      }
      i += length;
    }

    return BasicOperations.concatenate(automata);
  }

  /**
   * Returns the pattern term.
   */
  public Term getTerm() {
    return term;
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String field) {
    final StringBuffer buffer = new StringBuffer();
    buffer.append(term.text());
    buffer.append(ToStringUtils.boost(this.getBoost()));
    return this.wrapToStringWithDatatype(buffer).toString();
  }

}

