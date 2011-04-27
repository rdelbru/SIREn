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
 * @project siren-solr
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.qparser.keyword;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

/**
 * Underlying implementation of the parser for the simple query language.
 */
public class KeywordQParserImpl {

  private final QueryParser parser;

  /**
   * Flag for disabling field query
   */
  boolean disableField = false;

  public KeywordQParserImpl(final Analyzer analyzer,
                            final Map<String, Float> boosts,
                            final boolean disableField) {
    final String[] fields = boosts.keySet().toArray(new String[0]);
    this.disableField = disableField;
    parser = new MultiFieldQueryParser(Version.LUCENE_31, fields, analyzer, boosts) {
      @Override
      protected Query getFuzzyQuery(final String field, final String termStr,
          final float minSimilarity) throws ParseException {
        throw new ParseException("Fuzzy queries not allowed");
      }
    };
  }

  public Query parse(final String query) throws ParseException {
    if (disableField) {
      return parser.parse(KeywordQParserImpl.escape(query));
    }
    else { // If field queries enabled, escape only URIs
      return parser.parse(this.escapeURIs(query));
    }
  }

  static String uriRegExp = "(news|(ht|f)tp(s?))\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?";
  static Pattern pattern = Pattern.compile(uriRegExp);

  // TODO: check if other special characters of lucene can appear in a URI, and
  // escape them
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
      if (c == ':' || c == '\\') {
        sb.append('\\');
      }
      sb.append(c);
    }
    return sb.toString();
  }

  public void setDefaultOperator(final Operator operator) {
    parser.setDefaultOperator(operator);
  }

}

