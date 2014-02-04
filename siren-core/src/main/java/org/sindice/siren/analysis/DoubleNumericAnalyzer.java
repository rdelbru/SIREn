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

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.util.NumericUtils;
import org.sindice.siren.util.XSDPrimitiveTypeParser;

/**
 * An implementation of the {@link NumericAnalyzer} for double value.
 */
public class DoubleNumericAnalyzer extends NumericAnalyzer {

  public DoubleNumericAnalyzer(final int precisionStep) {
    super(precisionStep);
  }

  @Override
  protected TokenStreamComponents createComponents(final String fieldName,
                                                   final Reader reader) {
    final Tokenizer sink = new NumericTokenizer(reader, new DoubleNumericParser(), precisionStep);
    return new TokenStreamComponents(sink);
  }

  @Override
  public DoubleNumericParser getNumericParser() {
    return new DoubleNumericParser();
  }

  public class DoubleNumericParser extends NumericParser<Double> {

    @Override
    public long parseAndConvert(final Reader input) throws IOException {
      return NumericUtils.doubleToSortableLong(this.parse(input));
    }

    @Override
    public Double parse(final Reader input)
    throws IOException {
      return XSDPrimitiveTypeParser.parseDouble(input);
    }

    @Override
    public NumericType getNumericType() {
      return NumericType.DOUBLE;
    }

    @Override
    public int getValueSize() {
      return 64;
    }

  }

}
