/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
 * @project siren
 * @author Renaud Delbru [ 26 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class IOUtils {

  /**
   * Flatten a list of triples to n-tuples containing many objects for the same
   * subject/predicate pair. Generate one n-tuple per subject/predicate pair.
   * <br>
   * This is useful for the document-centric indexing approach. The flatten
   * representation is more efficient in term of index size than the plain
   * n-triples approach.
   *
   * @param values The list of n-triples.
   * @return The n-tuples concatenated.
   */
  public static String flattenNTriples(final String[] values) {
    final Map<String,StringBuilder> map = new HashMap<String, StringBuilder>();
    return flattenNTriples(values, map);
  }

  /**
   * Sort and flatten a list of triples to n-tuples containing many objects for
   * the same subject/predicate pair. Generate one n-tuple per subject/predicate
   * pair. The tuples are ordered by subject/predicate.
   * <br>
   * This is useful for the document-centric indexing approach. The sorted and
   * flatten representation is generally more efficient in term of index size
   * than the normal flatten approach.
   *
   * @param values The list of n-triples.
   * @return The n-tuples concatenated.
   */
  public static String sortAndFlattenNTriples(final String[] values) {
    final Map<String,StringBuilder> map = new TreeMap<String, StringBuilder>();
    return flattenNTriples(values, map);
  }

  /**
   * Flatten a list of triples to n-tuples containing many objects for the same
   * subject/predicate pair. Generate one n-tuple per subject/predicate pair.
   * <br>
   * This is useful for the document-centric indexing approach. The flatten
   * representation is more efficient in term of index size than the plain
   * n-triples approach.
   *
   * @param values The list of n-triples.
   * @return The n-tuples concatenated.
   */
  private static String flattenNTriples(final String[] values, final Map<String,StringBuilder> map) {
    for (final String value : values) {
      if (value != null) {
        final int firstWhitespace = value.indexOf(' ');
        final int secondWhitespace = value.indexOf(' ', firstWhitespace + 1);
        final int lastDot = value.lastIndexOf('.');
        if (firstWhitespace == -1 || secondWhitespace == -1 || lastDot == -1) {
          continue; // probably invalid triple, just skip it
        }
        final String key = value.substring(0, secondWhitespace);
        final String object = value.substring(secondWhitespace, lastDot - 1);
        StringBuilder tb = map.get(key);
        if (tb == null) {
          tb = new StringBuilder();
          tb.append(key);
          map.put(key, tb);
        }
        tb.append(object);
      }
    }
    final StringBuilder result = new StringBuilder();
    for (final StringBuilder tb : map.values()) {
      result.append(tb).append('.').append("\n");
    }
    return result.toString();
  }

}
