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
 * @author Renaud Delbru [ 14 Feb 2008 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.ntriple;

import java.util.Vector;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

public class CustomQueryParser extends QueryParser {

  private static final int CONJ_NONE   = 0;
  private static final int CONJ_AND    = 1;
  private static final int CONJ_OR     = 2;

  private static final int MOD_NONE    = 0;
  private static final int MOD_NOT     = 10;
  private static final int MOD_REQ     = 11;

  /** Constructs a query parser.
   *  @param f  the default field for query terms.
   *  @param a   used to find terms in the query text.
   */
  public CustomQueryParser(final Version v, final String f, final Analyzer a) {
    super(v, f, a);
  }

  /**
   * Parses a literal string, returning a {@link org.apache.lucene.search.Query}.
   * Escape certain special lucene characters, such as ':'.
   *  @param query  the query string to be parsed.
   *  @throws ParseException if the parsing fails
   */
  @Override
  public Query parse(final String query) throws ParseException {
    return super.parse(CustomQueryParser.escapeLiteral(query));
  }

  /**
   * Returns a literal string where certain characters that QueryParser
   * expects to be escaped are escaped by a preceding <code>\</code>.
   */
  public static String escapeLiteral(final String literal) {
    final StringBuffer sb = new StringBuffer();
    for (int i = 0; i < literal.length(); i++) {
      final char c = literal.charAt(i);
      // These characters are part of the query syntax and must be escaped
      if (c == '!' || c == ':' || c == '^' || c == '[' || c == ']'
        || c == '{' || c == '}' || c == '~' || c == '*' || c == '?') {
        sb.append('\\');
      }
      sb.append(c);
    }
    return sb.toString();
  }

  protected void addClause(final Vector clauses, final int conj, final int mods, final org.apache.lucene.search.Query q) {
    boolean required, prohibited;

//    // If this term is introduced by AND, make the preceding term required,
//    // unless it's already prohibited
//    if (clauses.size() > 0 && conj == CONJ_AND) {
//      BooleanClause c = (BooleanClause) clauses.elementAt(clauses.size()-1);
//      if (!c.isProhibited())
//        c.setOccur(BooleanClause.Occur.MUST);
//    }
//
//    if (clauses.size() > 0 && operator == AND_OPERATOR && conj == CONJ_OR) {
//      // If this term is introduced by OR, make the preceding term optional,
//      // unless it's prohibited (that means we leave -a OR b but +a OR b-->a OR b)
//      // notice if the input is a OR b, first term is parsed as required; without
//      // this modification a OR b would parsed as +a OR b
//      BooleanClause c = (BooleanClause) clauses.elementAt(clauses.size()-1);
//      if (!c.isProhibited())
//        c.setOccur(BooleanClause.Occur.SHOULD);
//    }

    // We might have been passed a null query; the term might have been
    // filtered away by the analyzer.
    if (q == null)
      return;

    if (this.getDefaultOperator() == OR_OPERATOR) {
      // We set REQUIRED if we're introduced by AND or +; PROHIBITED if
      // introduced by NOT or -; make sure not to set both.
      prohibited = (mods == MOD_NOT);
      required = (mods == MOD_REQ);
      if (conj == CONJ_AND && !prohibited) {
        required = true;
      }
    } else {
      // We set PROHIBITED if we're introduced by NOT or -; We set REQUIRED
      // if not PROHIBITED and not introduced by OR
      prohibited = (mods == MOD_NOT);
      required   = (!prohibited && conj != CONJ_OR);
    }
    if (required && !prohibited)
      clauses.addElement(new BooleanClause(q, BooleanClause.Occur.MUST));
    else if (!required && !prohibited)
      clauses.addElement(new BooleanClause(q, BooleanClause.Occur.SHOULD));
    else if (!required && prohibited)
      clauses.addElement(new BooleanClause(q, BooleanClause.Occur.MUST_NOT));
    else
      throw new RuntimeException("Clause cannot be both required and prohibited");
  }

}
