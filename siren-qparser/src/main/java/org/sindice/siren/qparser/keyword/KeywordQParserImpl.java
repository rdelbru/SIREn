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
 * @project siren-solr
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.search.Query;

/**
 * Underlying implementation of the parser for the simple query language.
 */
public class KeywordQParserImpl {

  private final KeywordQueryParser parser = new KeywordQueryParser();

  /**
   * Flag for disabling field query
   */
  boolean disableField = false;

  public KeywordQParserImpl(final Analyzer analyzer,
                            final Map<String, Float> boosts,
                            final boolean disableField) {
    final String[] fields = boosts.keySet().toArray(new String[0]);
    this.disableField = disableField;

    parser.setAnalyzer(analyzer);
    parser.setMultiFields(fields);
    parser.setFieldsBoost(boosts);
  }

  public Query parse(final String query) throws ParseException {
    try {
      if (disableField) {
        return parser.parse(KeywordQParserImpl.escape(query), null);
      }
      else { // If field queries enabled, escape only URIs
        return parser.parse(this.escapeURIs(query), null);
      }
    }
    catch (final QueryNodeException e) {
      throw new ParseException(e.getMessage());
    }
  }

  // TODO: does not support mailto: uri
  static String uriRegExp = "(news|(ht|f)tp(s?))\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?";
  static Pattern pattern = Pattern.compile(uriRegExp);

  // TODO: check if other special characters of lucene can appear in a URI, and
  // escape them, for example ~.
  private String escapeURIs(final String query) {
    final Matcher matcher = pattern.matcher(query);

    final StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sb, matcher.group(0).replace(":", "\\\\:"));
    }
    matcher.appendTail(sb);

    return sb.toString();
  }

  /**
   * Returns a String where <code>:</code> and <code>\</code> are escaped by a
   * preceding <code>\</code>.
   */
  public static String escape(final String s) {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      final char c = s.charAt(i);
      // These characters are part of the field query syntax and must be escaped
      if (c == ':' || c == '\\' || c == '~') {
        sb.append('\\');
      }
      sb.append(c);
    }
    return sb.toString();
  }

  public void setDefaultOperator(final Operator operator) {
    if (operator == Operator.OR) {
      parser.setDefaultOperator(DefaultOperatorAttribute.Operator.OR);
    }
    else {
      parser.setDefaultOperator(DefaultOperatorAttribute.Operator.AND);
    }
  }

}

