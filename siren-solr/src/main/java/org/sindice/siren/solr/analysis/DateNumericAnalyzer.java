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
package org.sindice.siren.solr.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.solr.schema.DateField;
import org.apache.solr.util.DateMathParser;
import org.sindice.siren.analysis.LongNumericAnalyzer;
import org.sindice.siren.analysis.NumericAnalyzer;
import org.sindice.siren.analysis.NumericTokenizer;
import org.sindice.siren.util.ReusableCharArrayReader;

/**
 * An implementation of {@link NumericAnalyzer} for date value.
 *
 * <p>
 *
 * This {@link NumericAnalyzer} is located in the siren-solr module because
 * it relies on {@link DateField} to parse date. See {@link DateMathParser} for
 * more information about the date syntax.
 */
public class DateNumericAnalyzer extends LongNumericAnalyzer {

  /**
   * Used for handling date types following the same semantics as DateField
   * <p>
   * Thread-safe.
   */
  static final DateField dateField = new DateField();

  public DateNumericAnalyzer(final int precisionStep) {
    super(precisionStep);
  }

  @Override
  protected TokenStreamComponents createComponents(final String fieldName,
                                                   final Reader reader) {
    final Tokenizer sink = new NumericTokenizer(reader, new DateNumericParser(), precisionStep);
    return new TokenStreamComponents(sink);
  }

  @Override
  public DateNumericParser getNumericParser() {
    return new DateNumericParser();
  }

  public class DateNumericParser extends LongNumericParser {

    @Override
    public Long parse(final Reader input) throws IOException {
      // Readers received for datatype should always be ReusableCharArrayReader
      if (!(input instanceof ReusableCharArrayReader)) {
        throw new IllegalArgumentException("ReusableCharArrayReader expected");
      }

      // not very nice, but necessary to create a string for the date parser
      // The string is created by reusing the char array buffer of the reader.
      final String value = ((ReusableCharArrayReader) input).toString();
      return dateField.parseMath(null, value).getTime();
    }

  }

}
