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

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.ToStringUtils;
import org.apache.lucene.util.automaton.Automaton;
import org.apache.lucene.util.automaton.BasicOperations;
import org.apache.lucene.util.automaton.CompiledAutomaton;

/**
 * A {@link MultiNodeTermQuery} that will match terms against a finite-state
 * machine.
 *
 * <p>
 *
 * This query will match documents that contain terms accepted by a given
 * finite-state machine. The automaton can be constructed with the
 * {@link org.apache.lucene.util.automaton} API. Alternatively, it can be
 * created from a regular expression with {@link RegexpQuery} or from
 * the standard Lucene wildcard syntax with {@link NodeWildcardQuery}.
 *
 * <p>
 *
 * When the query is executed, it will create an equivalent DFA of the
 * finite-state machine, and will enumerate the term dictionary in an
 * intelligent way to reduce the number of comparisons. For example: the regular
 * expression of <code>[dl]og?</code> will make approximately four comparisons:
 * do, dog, lo, and log.
 */
public class NodeAutomatonQuery extends MultiNodeTermQuery {

  /** the automaton to match index terms against */
  protected final Automaton automaton;
  protected final CompiledAutomaton compiled;
  /** term containing the field, and possibly some pattern structure */
  protected final Term term;

  /**
   * Create a new AutomatonQuery from an {@link Automaton}.
   *
   * @param term Term containing field and possibly some pattern structure. The
   *        term text is ignored.
   * @param automaton Automaton to run, terms that are accepted are considered a
   *        match.
   */
  public NodeAutomatonQuery(final Term term, final Automaton automaton) {
    super(term.field());
    this.term = term;
    this.automaton = automaton;
    this.compiled = new CompiledAutomaton(automaton);
  }

  @Override
  protected TermsEnum getTermsEnum(final Terms terms, final AttributeSource atts) throws IOException {
    return compiled.getTermsEnum(terms);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    if (automaton != null) {
      // we already minimized the automaton in the ctor, so
      // this hash code will be the same for automata that
      // are the same:
      int automatonHashCode = automaton.getNumberOfStates() * 3 + automaton.getNumberOfTransitions() * 2;
      if (automatonHashCode == 0) {
        automatonHashCode = 1;
      }
      result = prime * result + automatonHashCode;
    }
    result = prime * result + ((term == null) ? 0 : term.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final NodeAutomatonQuery other = (NodeAutomatonQuery) obj;
    if (automaton == null) {
      if (other.automaton != null)
        return false;
    } else if (!BasicOperations.sameLanguage(automaton, other.automaton))
      return false;
    if (term == null) {
      if (other.term != null)
        return false;
    } else if (!term.equals(other.term))
      return false;
    return true;
  }

  @Override
  public String toString(final String field) {
    final StringBuilder buffer = new StringBuilder();
    buffer.append(this.getClass().getSimpleName());
    buffer.append(" {");
    buffer.append('\n');
    buffer.append(automaton.toString());
    buffer.append("}");
    buffer.append(ToStringUtils.boost(this.getBoost()));
    return buffer.toString();
  }
}

