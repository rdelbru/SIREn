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
 * An implementation of the {@link NumericAnalyzer} for float value.
 */
public class FloatNumericAnalyzer extends NumericAnalyzer {

  public FloatNumericAnalyzer(final int precisionStep) {
    super(precisionStep);
  }

  @Override
  protected TokenStreamComponents createComponents(final String fieldName,
                                                   final Reader reader) {
    final Tokenizer sink = new NumericTokenizer(reader, new FloatNumericParser(), precisionStep);
    return new TokenStreamComponents(sink);
  }

  @Override
  public FloatNumericParser getNumericParser() {
    return new FloatNumericParser();
  }

  public class FloatNumericParser extends NumericParser<Float> {

    @Override
    public long parseAndConvert(final Reader input) throws IOException {
      return NumericUtils.floatToSortableInt(this.parse(input));
    }

    @Override
    public NumericType getNumericType() {
      return NumericType.FLOAT;
    }

    @Override
    public int getValueSize() {
      return 32;
    }

    @Override
    public Float parse(final Reader input)
    throws IOException {
      return XSDPrimitiveTypeParser.parseFloat(input);
    }

  }


}
