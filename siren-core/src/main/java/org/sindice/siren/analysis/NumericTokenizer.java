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

import org.apache.lucene.analysis.NumericTokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.NumericAnalyzer.NumericParser;
import org.sindice.siren.analysis.attributes.NodeNumericTermAttribute;

/**
 * This class provides a TokenStream for indexing numeric values that is used in
 * {@link NumericAnalyzer}.
 *
 * <p>
 *
 * This tokenizer expects to receive a string representation of a numeric value
 * as input. It parses the input using {@link NumericParser#parseAndConvert(Reader)},
 * and uses a {@link NodeNumericTermAttribute} to generate the numeric token.
 */
public class NumericTokenizer extends Tokenizer {

  private final NodeNumericTermAttribute numericAtt = this.addAttribute(NodeNumericTermAttribute.class);
  private final CharTermAttribute termAtt = this.addAttribute(CharTermAttribute.class);
  private final TypeAttribute typeAtt = this.addAttribute(TypeAttribute.class);
  private final PositionIncrementAttribute posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);

  private final NumericParser<?> parser;
  private boolean isInitialised = false;

  /**
   * Creates a token stream for numeric values with the specified
   * <code>precisionStep</code>.
   */
  public NumericTokenizer(final Reader input,
                          final NumericParser<? extends Number> parser,
                          final int precisionStep) {
    this(input, parser, precisionStep, AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
  }

  /**
   * Expert: Creates a token stream for numeric values with the specified
   * <code>precisionStep</code> using the given
   * {@link org.apache.lucene.util.AttributeSource.AttributeFactory}.
   */
  public NumericTokenizer(final Reader input,
                          final NumericParser<? extends Number> parser,
                          final int precisionStep,
                          final AttributeFactory factory) {
    super(factory, input);
    this.parser = parser;
    numericAtt.setPrecisionStep(precisionStep);
  }

  @Override
  public void reset() throws IOException {
    isInitialised = false;
  }

  @Override
  public final boolean incrementToken() throws IOException {
    // initialise the numeric attribute
    if (!isInitialised) {
      final long value = parser.parseAndConvert(this.input);
      numericAtt.init(parser.getNumericType(), value, parser.getValueSize());
      isInitialised = true;
    }

    // this will only clear all other attributes in this TokenStream
    this.clearAttributes();

    // increment the shift and generate next token
    final boolean hasNext = numericAtt.incrementShift(termAtt);
    // set other attributes after the call to incrementShift since getShift
    // is undefined before first call
    typeAtt.setType((numericAtt.getShift() == 0) ? NumericTokenStream.TOKEN_TYPE_FULL_PREC : NumericTokenStream.TOKEN_TYPE_LOWER_PREC);
    posIncrAtt.setPositionIncrement((numericAtt.getShift() == 0) ? 1 : 0);

    return hasNext;
  }

}
