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
package org.sindice.siren.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;
import org.sindice.siren.util.XSDDatatype;

public class TestJsonAnalyzer
extends NodeAnalyzerTestCase<JsonAnalyzer> {

  @Override
  protected JsonAnalyzer getNodeAnalyzer() {
    final Analyzer literalAnalyzer = new StandardAnalyzer(TEST_VERSION_CURRENT);
    final Analyzer fieldAnalyzer = new WhitespaceAnalyzer(TEST_VERSION_CURRENT);
    return new JsonAnalyzer(TEST_VERSION_CURRENT, fieldAnalyzer, literalAnalyzer);
  }

  @Test
  public void testLiteral()
  throws Exception {
    this.assertAnalyzesTo(_a, "{\"foo BAR\":[null,\"FOO bar\"]}", // null is typed as XSD_STRING
      new String[] { "foo", "BAR", "null", "foo", "bar" },
      new String[] { TypeAttribute.DEFAULT_TYPE, TypeAttribute.DEFAULT_TYPE,
                     "<ALPHANUM>", "<ALPHANUM>", "<ALPHANUM>" });
    this.assertAnalyzesTo(_a, "{\"ABC\\u0061\\u0062\\u0063\\u00E9\\u00e9ABC\":\"EmptY\"}",
      new String[] { "ABCabcééABC", "empty" },
      new String[] { TypeAttribute.DEFAULT_TYPE, "<ALPHANUM>" });
  }

  @Test
  public void testLong()
  throws Exception {
    _a.registerDatatype(XSDDatatype.XSD_LONG.toCharArray(), new StandardAnalyzer(TEST_VERSION_CURRENT));
    this.assertAnalyzesTo(_a, "{\"foo\":12}",
      new String[] { "foo", "12" },
      new String[] { TypeAttribute.DEFAULT_TYPE, "<NUM>" });
  }

  @Test
  public void testDouble()
  throws Exception {
    _a.registerDatatype(XSDDatatype.XSD_DOUBLE.toCharArray(), new StandardAnalyzer(TEST_VERSION_CURRENT));
    this.assertAnalyzesTo(_a, "{\"foo\":12.42}",
      new String[] { "foo", "12.42" },
      new String[] { TypeAttribute.DEFAULT_TYPE, "<NUM>" });
  }

  @Test
  public void testBoolean()
  throws Exception {
    _a.registerDatatype(XSDDatatype.XSD_BOOLEAN.toCharArray(), new WhitespaceAnalyzer(TEST_VERSION_CURRENT));
    this.assertAnalyzesTo(_a, "{\"foo\":[true,false]}",
      new String[] { "foo", "true", "false" },
      new String[] { TypeAttribute.DEFAULT_TYPE, TypeAttribute.DEFAULT_TYPE,
                     TypeAttribute.DEFAULT_TYPE });
  }

}
