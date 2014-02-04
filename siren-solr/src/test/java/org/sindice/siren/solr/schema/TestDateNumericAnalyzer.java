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
package org.sindice.siren.solr.schema;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.lucene.document.FieldType.NumericType;
import org.junit.Test;
import org.sindice.siren.analysis.NumericAnalyzer.NumericParser;
import org.sindice.siren.solr.analysis.DateNumericAnalyzer;
import org.sindice.siren.util.ReusableCharArrayReader;

public class TestDateNumericAnalyzer {

  @Test
  public void testParser() throws IOException {
    final DateNumericAnalyzer analyzer = new DateNumericAnalyzer(8);
    final NumericParser parser = analyzer.getNumericParser();
    assertEquals(NumericType.LONG, parser.getNumericType());
    assertEquals(64, parser.getValueSize());
    final ReusableCharArrayReader input = new ReusableCharArrayReader("2012-09-21T00:00:00Z".toCharArray());
    assertEquals(1348185600000l, parser.parseAndConvert(input));
  }

}
