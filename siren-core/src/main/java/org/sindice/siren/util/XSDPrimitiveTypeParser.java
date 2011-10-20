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
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 *    Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 *    Copyright (C) 2006 - Javolution (http://javolution.org/)
 *    All rights reserved.
 *
 *    Permission to use, copy, modify, and distribute this software is
 *    freely granted, provided that this notice is preserved.
 */
/**
 * @project siren-core
 * @author Renaud Delbru [ 5 Oct 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.util;

import java.io.IOException;
import java.io.Reader;

/**
 * <p> This class provides utility methods to parse
 *     {@link Reader} into primitive types, as specified in the xsd datatype
 *     reference.</p>
 *
 * <p> Methods from this class <b>do not create temporary objects</b> and
 *     are typically faster than standard library methods.</p>
 *
 * <p> For floating-point numbers, there is a lose of precision of
 * approximatively 1e-15 %.
 */
public final class XSDPrimitiveTypeParser {

    /**
     * Parses the specified character sequence as a signed 16 bit integer, as
     * specified in the xsd:short datatype reference.
     *
     * <p> The value space of xsd:short is the set of common short integers
     * (16 bits)—the integers between -32768 and 32767. Its lexical space allows
     * any number of insignificant leading zeros.
     *
     * @param  reader A reader for reading the character streams.
     * @throws IOException If a problem occurs with the specified reader
     * @throws NumberFormatException if the specified character sequence
     *         does not contain a parsable signed 16 bit integer.
     */
    public static short parseShort(final Reader reader)
    throws IOException {
      final int i = parseInt(reader);
      if ((i < Short.MIN_VALUE) || (i > Short.MAX_VALUE)) {
        throw new NumberFormatException("Short overflow");
      }
      return (short) i;
    }

    /**
     * Parses the specified character sequence as a signed 32 bit integer, as
     * specified in the xsd:int datatype reference.
     *
     * <p> The value space of xsd:int is the set of common single-size integers
     * (32 bits), the integers between -2147483648 and 2147483647. Its lexical
     * space allows any number of insignificant leading zeros.
     *
     * @param  reader A reader for reading the character streams.
     * @throws IOException If a problem occurs with the specified reader
     * @throws NumberFormatException if the specified reader
     *         does not contain a parsable signed 32 bit integer.
     */
    public static int parseInt(final Reader reader) throws IOException {
        boolean isNegative = false;
        int result = 0;

        // Check for sign
        int c = reader.read();
        if (isSign(c)) {
          isNegative = (c == '-') ? true : false;
          c = reader.read();
        }

        while (c != -1) {
          result = accumulateIntegerDecimal(c, result);
          c = reader.read();
        }

        // Check MAX_VALUE overflow
        if ((result == Integer.MIN_VALUE) && !isNegative) {
          throw new NumberFormatException("Integer overflow");
        }

        // Decimal has been accumulated negatively. We must switch sign if
        // the number is non-negative.
        return isNegative ? result : -result;
    }

    /**
     * Parses the specified character sequence as a signed 64 bit integer, as
     * specified in the xsd:long datatype reference.
     *
     * <p> The value space of xsd:long is the set of common double-size integers
     * (64 bits)—the integers between -9223372036854775808 and
     * 9223372036854775807. Its lexical space allows any number of insignificant
     * leading zeros.
     *
     * @param  reader A reader for reading the character streams.
     * @throws IOException If a problem occurs with the specified reader
     * @throws NumberFormatException if the specified reader
     *         does not contain a parsable signed 32 bit integer.
     */
    public static long parseLong(final Reader reader) throws IOException {
      boolean isNegative = false;
      long result = 0;

      // Check for sign
      int c = reader.read();
      if (isSign(c)) {
        isNegative = (c == '-') ? true : false;
        c = reader.read();
      }

      while (c != -1) {
        result = accumulateLongDecimal(c, result);
        c = reader.read();
      }

      // Check MAX_VALUE overflow
      if ((result == Long.MIN_VALUE) && !isNegative) {
        throw new NumberFormatException("Long overflow");
      }

      // Decimal has been accumulated negatively. We must switch sign if
      // the number is non-negative.
      return isNegative ? result : -result;
    }

    /**
     * Parses the specified character sequence as a 32 bit floating-point
     * numbers, as specified in the xsd:float datatype reference.
     *
     * <p> The value space of xsd:float is "float," 32-bit floating-point
     * numbers as defined by the IEEE. The lexical space uses a decimal format
     * with optional scientific notation. The match between lexical (powers of
     * 10) and value (powers of 2) spaces is approximate and maps to the closest
     * value.
     *
     * @param  reader A reader for reading the character streams.
     * @throws IOException If a problem occurs with the specified reader
     * @throws NumberFormatException if the specified reader
     *         does not contain a parsable signed 32 bit floating-point numbers.
     */
    public static float parseFloat(final Reader reader) throws IOException {
        return (float) parseDouble(reader);
    }

    /**
     * Parses the specified character sequence as a 64 bit floating-point
     * numbers, as specified in the xsd:double datatype reference.
     *
     * <p> The value space of xsd:double is double (64 bits) floating-point
     * numbers as defined by the IEEE (Institute of Electrical and Electronic
     * Engineers). The lexical space uses a decimal format with optional
     * scientific notation. The match between lexical (powers of 10) and value
     * (powers of 2) spaces is approximate and done on the closest value.
     *
     * @param  reader A reader for reading the character streams.
     * @throws IOException If a problem occurs with the specified reader
     * @throws NumberFormatException if the specified reader
     *         does not contain a parsable signed 64 bit floating-point numbers.
     */
    public static double parseDouble(final Reader reader) throws IOException {
      boolean isNegative = false;
      boolean isNegativeExp = false;

      long decimal = 0;
      double fraction = 0;
      long exp = 0;

      int c = reader.read();

      // Checks for NaN.
      if (c == 'N') {
        return parseNaN(reader);
      }

      // Check for sign
      if (isSign(c)) {
        isNegative = (c == '-') ? true : false;
        c = reader.read();
      }

      // Checks for INF.
      if (c == 'I') {
        return parseINF(reader, isNegative);
      }

      // At least one digit or a '.' required.
      if (((c < '0') || (c > '9')) && (c != '.')) {
        throw new NumberFormatException("Invalid double value");
      }

      // Reads decimal.
      while (c != -1 && c != '.' && (c != 'E' && c != 'e')) {
        decimal = accumulateLongDecimal(c, decimal);
        c = reader.read();
      }

      // Check MAX_VALUE overflow
      if ((decimal == Long.MIN_VALUE) && !isNegative) {
        throw new NumberFormatException("Double overflow");
      }

      // Decimal has been accumulated negatively. We must switch sign if
      // the number is non-negative.
      if (!isNegative) {
          decimal = -decimal;
      }

      // End - return decimal
      if (c == -1) {
        return decimal;
      }

      // Reads fraction.
      if (c == '.') {
        // skip '.'
        c = reader.read();

        double base = 0.1;
        while (c != -1 && (c != 'E' && c != 'e')) {
          fraction = accumulateDoubleFraction(c, fraction, base);
          base *= 0.1;
          c = reader.read();
        }
      }

      // End - return decimal + fraction
      if (c == -1) {
        return decimal + fraction;
      }

      // skip 'e' or 'E'
      c = reader.read();

      // Check for sign
      if (isSign(c)) {
        isNegativeExp = (c == '-') ? true : false;
        c = reader.read();
      }

      // Reads exponent.
      while (c != -1) {
        exp = accumulateLongDecimal(c, exp);
        c = reader.read();
      }

      // Decimal has been accumulated negatively. We must switch sign if
      // the number is non-negative.
      if (!isNegativeExp) {
        exp = -exp;
      }

      return (decimal + fraction) * Math.pow(10, exp);
    }

    private static double parseNaN(final Reader reader)
    throws IOException {
      if (reader.read() == 'a' && (reader.read() == 'N')) {
        return Double.NaN;
      }
      else {
        throw new NumberFormatException("Invalid double value");
      }
    }

    private static double parseINF(final Reader reader, final boolean isNegative)
    throws IOException {
      if (reader.read() == 'N' && (reader.read() == 'F')) {
        return isNegative ? Double.NEGATIVE_INFINITY
                          : Double.POSITIVE_INFINITY;
      }
      else {
        throw new NumberFormatException("Invalid double value");
      }
    }

    private static boolean isSign(final int c) {
      if ((c == '-') || (c == '+')) {
        return true;
      }
      return false;
    }

    /**
     * Accumulate digit negatively in order to avoid {@link Integer.MIN_VALUE}
     * overflow.
     *
     * @param c
     * @param decimal
     * @return
     */
    private static int accumulateIntegerDecimal(final int c, final int decimal) {
      if (c >= '0' && c <= '9') {
        final int digit = c - '0';
        final int newResult = decimal * 10 - digit;
        if (newResult > decimal) {
          throw new NumberFormatException("Integer overflow");
        }
        return newResult;
      }
      else {
        throw new NumberFormatException("Invalid integer value");
      }
    }

    /**
     * Accumulate digit negatively in order to avoid {@link Long.MIN_VALUE}
     * overflow.
     *
     * @param c
     * @param decimal
     * @return
     */
    private static long accumulateLongDecimal(final int c, final long decimal) {
      if (c >= '0' && c <= '9') {
        final int digit = c - '0';
        final long newResult = decimal * 10 - digit;
        if (newResult > decimal) {
          throw new NumberFormatException("Long overflow");
        }
        return newResult;
      }
      else {
        throw new NumberFormatException("Invalid long value");
      }
    }

    private static double accumulateDoubleFraction(final int c, final double fraction, final double base) {
      if (c >= '0' && c <= '9') {
        final int digit = c - '0';
        final double newResult = digit * base + fraction;
        if (newResult < fraction) {
          throw new NumberFormatException("Double overflow");
        }
        return newResult;
      }
      else {
        throw new NumberFormatException("Invalid double value");
      }
    }

}
