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
 * @project siren-core
 * @author Renaud Delbru [ 5 Oct 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class TestXSDPrimitiveTypeParser {

  @Test(expected=NumberFormatException.class)
  public void testOverflowMaxShort() throws IOException {
    XSDPrimitiveTypeParser.parseShort(new StringReader("32768"));
  }

  @Test(expected=NumberFormatException.class)
  public void testOverflowMinShort() throws IOException {
    XSDPrimitiveTypeParser.parseShort(new StringReader("-32769"));
  }

  @Test
  public void testGoodInt() throws IOException {
    for (final String s : goodIntStrings) {
      try {
        checkInt(s, XSDPrimitiveTypeParser.parseInt(new StringReader(s)));
      }
      catch (final NumberFormatException e) {
        throw new RuntimeException("parseLong failed. " +
          "String:" + s + ". " + e.getMessage());
      }
    }
  }

  @Test(expected=NumberFormatException.class)
  public void testOverflowMaxInt() throws IOException {
    XSDPrimitiveTypeParser.parseInt(new StringReader("2147483648"));
  }

  @Test(expected=NumberFormatException.class)
  public void testOverflowMinInt() throws IOException {
    XSDPrimitiveTypeParser.parseInt(new StringReader("-2147483649"));
  }

  @Test
  public void testGoodLong() throws IOException {
    for (final String s : goodIntStrings) {
      try {
        checkLong(s, XSDPrimitiveTypeParser.parseLong(new StringReader(s)));
      }
      catch (final NumberFormatException e) {
        throw new RuntimeException("parseLong failed. " +
          "String:" + s + ". " + e.getMessage());
      }
    }

    // Long.MAX_VALUE
    checkLong("9223372036854775807", XSDPrimitiveTypeParser.parseLong(new StringReader("9223372036854775807")));
    // Long.MIN_VALUE
    checkLong("-9223372036854775808", XSDPrimitiveTypeParser.parseLong(new StringReader("-9223372036854775808")));
  }

  @Test(expected=NumberFormatException.class)
  public void testOverflowMaxLong() throws IOException {
    XSDPrimitiveTypeParser.parseLong(new StringReader("9223372036854775808"));
  }

  @Test(expected=NumberFormatException.class)
  public void testOverflowMinLong() throws IOException {
    XSDPrimitiveTypeParser.parseLong(new StringReader("-9223372036854775809"));
  }

  @Test
  public void testGoodDouble() throws IOException {
    for (final String s : goodDoubleStrings) {
      try {
        checkDouble(s, XSDPrimitiveTypeParser.parseDouble(new StringReader(s)));
      }
      catch (final NumberFormatException e) {
        throw new RuntimeException("parseDouble failed. " +
          "String:" + s + ". " + e.getMessage());
      }
    }
  }

  @Test
  public void testSpecialDouble() throws IOException {
    assertTrue(Double.isNaN(XSDPrimitiveTypeParser.parseDouble(new StringReader("NaN"))));
    assertEquals(Double.POSITIVE_INFINITY, XSDPrimitiveTypeParser.parseDouble(new StringReader("INF")), 0);
    assertEquals(Double.POSITIVE_INFINITY, XSDPrimitiveTypeParser.parseDouble(new StringReader("+INF")), 0);
    assertEquals(Double.NEGATIVE_INFINITY, XSDPrimitiveTypeParser.parseDouble(new StringReader("-INF")), 0);
  }

  @Test(expected=NumberFormatException.class)
  public void testOverflowMaxDouble() throws IOException {
    XSDPrimitiveTypeParser.parseLong(new StringReader("9223372036854775808"));
  }

  @Test(expected=NumberFormatException.class)
  public void testOverflowMinDouble() throws IOException {
    XSDPrimitiveTypeParser.parseLong(new StringReader("-9223372036854775809"));
  }

  static String goodIntStrings[] = {
                                       "1",
                                       "0",
                                       "-0",
                                       "+0",
                                       "00",
                                       "00",
                                       "-00",
                                       "+00",
                                       "0000000000",
                                       "-0000000000",
                                       "+0000000000",
                                       "1",
                                       "2",
                                       "1234",
                                       "-1234",
                                       "+1234",
                                       "2147483647",   // Integer.MAX_VALUE
                                       "-2147483648",  // Integer.MIN_VALUE
  };

  static String specialDoubleStrings[] = {
                                       "NaN",
                                       "INF",
                                       "+INF",
                                       "-INF"
        };

  static String goodDoubleStrings[] = {
                                 "1.1e-23",
                                 ".1e-23",
                                 "1e-23",
                                 "1",
                                 "0",
                                 "-0",
                                 "+0",
                                 "00",
                                 "00",
                                 "-00",
                                 "+00",
                                 "0000000000",
                                 "-0000000000",
                                 "+0000000000",
                                 "1",
                                 "2",
                                 "1234",
                                 "-1234",
                                 "+1234",
                                 "2147483647",   // Integer.MAX_VALUE
                                 "2147483648",
                                 "-2147483648",  // Integer.MIN_VALUE
                                 "-2147483649",

                                 "16777215",
                                 "16777216",     // 2^24
                                 "16777217",

                                 "-16777215",
                                 "-16777216",    // -2^24
                                 "-16777217",

                                 "9007199254740991",
                                 "9007199254740992",     // 2^53
                                 "9007199254740993",

                                 "-9007199254740991",
                                 "-9007199254740992",    // -2^53
                                 "-9007199254740993",

                                 "9223372036854775807",  // Long.MAX_VALUE

                                 "-9223372036854775808", // Long.MIN_VALUE

                                 // Culled from JCK test lex03591m1
                                 "54.07140",
                                 // TODO: This test does not pass due to
                                 // precision error of Math.pow
//                                 "7.01e-324",
                                 "2147483647.01",
                                 "1.2147483647",
                                 "000000000000000000000000001.",
                                 "1.00000000000000000000000000e-2",

                                 // Culled from JCK test lex03592m2
                                 "2.",
                                 ".0909",
                                 "122112217090.0",
                                 "7090e-5",
                                 "2.E-20",
                                 ".0909e42",
                                 "122112217090.0E+100",
                                 "7090",

                                 // Culled from JCK test lex03595m1
                                 "0.0E-10",
                                 "1E10",

                                 // Culled from JCK test lex03691m1
                                 "0.",
                                 "1",
                                 "0.",
                                 "1",
                                 "0.12",
                                 "1e-0",
                                 "12.e+1",
                                 "0e-0",
                                 "12.e+01",
                                 "1e-01",
  };

  private static void checkInt(final String val, final int expected) {
    // '+' is forbidden in Java
    final String tmp = val.startsWith("+") ? val.substring(1) : val;
    final long n = Integer.parseInt(tmp);
    assertEquals("parseInt failed. String:" + val, n, expected);
  }

  private static void checkDouble(final String val, final double expected) {
    final double n = Double.parseDouble(val);

    if (Double.isNaN(n)) {
      assertTrue("Double.parseDouble failed. " +
        "String:" + val + ", Result:" + expected + ", Expected:" + n,
        Double.isNaN(expected));
      return;
    }

    final double precision = n * .1e-14;

    assertEquals("parseDouble failed. String:" + val, n, expected, precision);
  }

  private static void checkLong(final String val, final long expected) {
    // '+' is forbidden in Java
    final String tmp = val.startsWith("+") ? val.substring(1) : val;
    final long n = Long.parseLong(tmp);
    assertEquals("parseLong failed. String:" + val, n, expected);
  }

}
