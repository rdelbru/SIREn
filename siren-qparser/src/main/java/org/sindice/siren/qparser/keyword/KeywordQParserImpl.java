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
  private final StringBuffer sbEscaped = new StringBuffer();
  private final StringBuffer sb = new StringBuffer();
  
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
        return parser.parse(this.escape(query), null);
      }
      else { // If field queries enabled, escape only URIs
        return parser.parse(this.escapeURIs(query), null);
      }
    }
    catch (final QueryNodeException e) {
      throw new ParseException(e.getMessage());
    }
  }

//  static String uriRegExp = "(news|(ht|f)tp(s?))\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?";
  /*
   * Regex got from http://www.regular-expressions.info/email.html
   */
  static String emailRegExp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)\\b";
  static String uriRegExp = "(mailto\\:" + emailRegExp + "|(news|(ht|f)tp(s?))\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?)";
  static Pattern pattern = Pattern.compile(uriRegExp);
    
  /**
   * Returns a String where special Lucene characters <code>:</code>, <code>~</code>
   * and <code>?</code> are escaped within terms matching the URI pattern.
   * @param query
   */
  private String escapeURIs(final String query) {
    final Matcher matcher = pattern.matcher(query);
    
    sbEscaped.setLength(0);
    while (matcher.find()) {
      replaceAndCountURI(matcher.group());
      matcher.appendReplacement(sbEscaped, sb.toString());
    }
    matcher.appendTail(sbEscaped);
    return sbEscaped.toString();
  }

  /**
   * Escape special Lucene characters <code>:</code>, <code>~</code>
   * and <code>?</code>.
   * @param match
   * @return the number of escaped characters
   */
  private int replaceAndCountURI(final String match) {
    int count = 0;
    
    sb.setLength(0);
    for (int i = 0; i < match.length(); i++) {
      final char c = match.charAt(i);
      if (c == ':' || c == '~' || c == '?') {
        sb.append("\\\\");
        count++;
      }
      sb.append(c);
    }
    return count;
  }
  
  /**
   * For non-URI terms, escape <code>:</code> and <code>\</code> special Lucene characters.
   * @param index
   * @param highBound
   * @return the number of escaped characters
   */
  private int replaceAndCountNonURI(int index, final int highBound) {
    int count = 0;
    
    int ind = index;
    // escape first the backslash, to avoid escaping the next escapes...
    while ((ind = sbEscaped.indexOf("\\", ind)) != -1 && ind < highBound) {
      sbEscaped.replace(ind, ind + 1, "\\\\");
      ind += 2; // skip the two \\
      count++;
    }
    
    ind = index;
    while ((ind = sbEscaped.indexOf(":", ind)) != -1 && ind < highBound) {
      sbEscaped.replace(ind, ind + 1, "\\:");
      ind += 2; // skip the semi-colon and the \\
      count++;
    }
    return count;
  }
  
  /**
   * Returns a String where <code>:</code> and where URIs are processed by
   * escapeURIs escaped by a preceding <code>\</code>.
   */
  private String escape(final String s) {
    final Matcher matcher = pattern.matcher(s);
    int lastURImatchEnd = 0;
    int count = 0;
    
    sbEscaped.setLength(0);
    while (matcher.find()) {
      final int hb = matcher.start() + count;
      
      count += replaceAndCountURI(matcher.group());
      // escape within the URI pattern
      matcher.appendReplacement(sbEscaped, sb.toString());
      // escape any special characters before that previous match
      count += replaceAndCountNonURI(lastURImatchEnd, hb);
      lastURImatchEnd = matcher.end() + count;
    }
    matcher.appendTail(sbEscaped);
    // escape special characters in the tail
    replaceAndCountNonURI(lastURImatchEnd, sbEscaped.length());
    return sbEscaped.toString();
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

