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
 * @project siren-core
 * @author Campinas Stephane [ 8 Dec 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.util;

import java.util.HashMap;

/**
 * 
 */
public class CSV2Tabular {

  /**
   * Convert an array of lines in CSV format into tuples. Use a comma ',' as
   * field separator. Consider all values as a Literal.
   * @param tuples
   * @return
   */
  public static String convert(String[] tuples) {
    return convert(null, tuples);
  }
  
  /**
   * Convert an array of lines in CSV format into tuples. Consider all values as
   * a Literal.
   * @param tuples
   * @param sep
   * @return
   */
  public static String convert(String[] tuples, char sep) {
    return convert(null, tuples, sep);
  }
  
  /**
   * Convert an array of lines in CSV format into tuples. Use a comma ',' as
   * field separator.
   * @param config
   * @param tuples
   * @return
   */
  public static String convert(HashMap<Integer, String> config,
                               String[] tuples) {
    return convert(config, tuples, ',');
  }
  
  /**
   * Convert an array of lines in CSV format into tuples. Config contains the
   * type definition of each field, which by default is {@link XSDDatatype#XSD_STRING}.
   * The type {@link XSDDatatype#XSD_ANY_URI} will enclose the value within &lt; &gt;.
   * With any other type, the value is written as "value"^^&lt;type&gt;.
   * 
   * <p>
   * This CSV parser doesn't support trailing whitespace adjacent to the separator.
   * Such whitespace is considered as part of the field value.
   * @param config the fields' type. The index of fields starts at 0.
   * @param tuples the array of String in CSV format
   * @param sep the field separator
   * @return
   */
  public static String convert(HashMap<Integer, String> config,
                               String[] tuples,
                               char sep) {
    final StringBuilder sb = new StringBuilder();
    
    for (String tuple : tuples) {
      int prevIndex = 0;
      int index = 0;
      int pos = 0;
      while ((index = tuple.indexOf(sep, prevIndex)) != -1) {
        final String type = getFieldType(config, pos);
        encode(sb, tuple, prevIndex, index, type);
        prevIndex = index + 1; // after the sep
        pos++;
      }
      // get the last field
      final String type = getFieldType(config, pos);
      encode(sb, tuple, prevIndex, tuple.length(), type);
      sb.append(".\n");
    }
    return sb.toString();
  }
  
  /**
   * Return the datatype of this field.
   * @param config
   * @param pos
   * @return
   */
  private static String getFieldType(HashMap<Integer, String> config, int pos) {
    if (config != null && config.containsKey(pos)) {
      return config.get(pos);
    }
    return XSDDatatype.XSD_STRING;
  }
  
  /**
   * Represent the field value either as an URI, or as a Literal with a defined
   * datatype.
   * 
   * <p>
   * Removes the surrounding quotes of this value only if they are adjacent to the
   * separator, e.g., -- tata,"plop",toto -- the second value is plop, not "plop".
   * @param sb
   * @param tuple
   * @param from
   * @param to
   * @param type
   */
  private static void encode(StringBuilder sb, String tuple, int from, int to, String type) {
    // Remove the surrounding quotes of this value only if they are adjacent to the separator.
    if (to >= 1 && from != tuple.length() &&
        tuple.charAt(from) == '"' && tuple.charAt(to - 1) == '"') {
      from++;
      to--;
    }
    final String value = tuple.substring(from, to);
    
    if (type.equals(XSDDatatype.XSD_ANY_URI)) {
      sb.append('<').append(value).append("> ");
    } else {
      sb.append('"').append(value).append("\"^^<").append(type).append("> ");
    }
  }
  
}
