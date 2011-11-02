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
 * @project siren-qparser_rdelbru
 * @author Campinas Stephane [ 2 Nov 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.qparser.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Escaping methods for special Lucene characters, which are <code>:</code>,
 * <code>~</code>, <code>?</code> and <code>\</code>.
 */
public class EscapeLuceneCharacters {

  private static final StringBuffer sbEscaped = new StringBuffer();
  private static final StringBuffer sbTemp = new StringBuffer();
  
  /*
   * Regex got from http://www.regular-expressions.info/email.html
   */
  private static String emailRegExp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)\\b";
  private static String uriRegExp = "(mailto\\:" + emailRegExp + "|(news|(ht|f)tp(s?))\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?)";
  private static Pattern pattern = Pattern.compile(uriRegExp);
    
  /**
   * Escape special Lucene characters <code>:</code>, <code>~</code>
   * and <code>?</code>.<br>
   * <code>~</code> is escaped only if:
   * <ol>
   * <li>it is at the end of the URI;</li>
   * <li>it is before a float, i.e., the similarity.</li>
   * </ol>
   * In other case, it is considered as a fuzzy search.
   * <br><br>
   * With URIs, <code>~</code> is an unsafe character. The wildcard character
   * <code>?</code> is escaped since it is reserved, while <code>*</code> can be left alone.
   * <code>:</code> is a reserved character.
   * @param query
   * @return the query with escaped URIs
   */
  public static String escapeURIs(final String query) {
    final Matcher matcher = pattern.matcher(query);
    
    sbEscaped.setLength(0);
    while (matcher.find()) {
      replaceAndCountURI(matcher.group());
      matcher.appendReplacement(sbEscaped, sbTemp.toString());
    }
    matcher.appendTail(sbEscaped);
    return sbEscaped.toString();
  }

  /**
   * Escape special Lucene characters <code>:</code>, <code>~</code>
   * and <code>?</code>.<br>
   * <code>~</code> is escaped only if:
   * <ol>
   * <li>it is at the end of the URI;</li>
   * <li>it is before a float, i.e., the similarity.</li>
   * </ol>
   * In other case, it is considered as a fuzzy search.
   * <br><br>
   * With URIs, <code>~</code> is an unsafe character. The wildcard character
   * <code>?</code> is escaped since it is reserved, while <code>*</code> can be left alone.
   * <code>:</code> is a reserved character.
   * @param match
   * @return the number of escaped characters
   */
  private static int replaceAndCountURI(final String match) {
    int count = 0;
    
    sbTemp.setLength(0);
    for (int i = 0; i < match.length(); i++) {
      final char c = match.charAt(i);
      if (c == ':' || c == '?' ||
          // tilde at the end or tilde before a float number
          (c == '~' && !isFloatOrEOL(match, i + 1))) {
        sbTemp.append("\\\\");
        count++;
      }
      sbTemp.append(c);
    }
    return count;
  }
  
  /**
   * returns true if the string is at the end of the line, or if the remaining
   * string starting at offset is a float number.
   * @param match
   * @param offset
   * @return
   */
  private static boolean isFloatOrEOL(String match, int offset) {
    if (offset == match.length()) return true;
    
    boolean dot = true; // match the dot only one time
    for (int i = offset; i < match.length(); i++) {
      if (dot && match.charAt(i) == '.') {
        dot = false;
      } else if (!Character.isDigit(match.charAt(i))) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * For non-URI terms, escape <code>:</code> and <code>\</code> special Lucene characters.
   * @param index
   * @param highBound
   * @return the number of escaped characters
   */
  private static int replaceAndCountNonURI(int index, final int highBound) {
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
   * Returns a String where <code>:</code> are escaped by a preceding <code>\</code>,
   * meaning that field queries are disabled, and where URIs are processed by
   * {@link #escapeURIs(String)}.
   */
  public static String escape(final String s) {
    final Matcher matcher = pattern.matcher(s);
    int lastURImatchEnd = 0;
    int count = 0;
    
    sbEscaped.setLength(0);
    while (matcher.find()) {
      final int hb = matcher.start() + count;
      
      count += replaceAndCountURI(matcher.group());
      // escape within the URI pattern
      matcher.appendReplacement(sbEscaped, sbTemp.toString());
      // escape any special characters before that previous match
      count += replaceAndCountNonURI(lastURImatchEnd, hb);
      lastURImatchEnd = matcher.end() + count;
    }
    matcher.appendTail(sbEscaped);
    // escape special characters in the tail
    replaceAndCountNonURI(lastURImatchEnd, sbEscaped.length());
    return sbEscaped.toString();
  }
  
}
