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
package org.sindice.siren.analysis.attributes;

import org.apache.lucene.analysis.NumericTokenStream.NumericTermAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.util.Attribute;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.BytesRef;
import org.sindice.siren.analysis.NumericTokenizer;
import org.sindice.siren.search.node.NodeNumericRangeQuery;

/**
 * <b>Expert:</b> This class provides an {@link Attribute} for the
 * {@link NumericTokenizer} for indexing numeric values that can be used by {@link
 * NodeNumericRangeQuery}.
 * <p>
 * This attribute provides a stream of tokens which iterates over
 * the different precisions of a given numeric value.
 * <p>
 * The string representation of each precision is prefixed by:
 * <ul>
 * <li> the numeric type of the value;
 * <li> the precision step;
 * </ul>
 * This prefix is in fact encoding the numeric type and precision step inside
 * the dictionary. This prefix is necessary for two reasons:
 * <ul>
 * <li> it avoids overlapping value of different numeric type, and therefore
 * avoid getting false-positive;
 * <li> enables better clustering of the values of a particular numeric type
 * in the dictionary.
 * </ul>
 */
public interface NodeNumericTermAttribute extends Attribute {

  /**
   * Return the numeric type of the value
   */
  NumericType getNumericType();

  /**
   * Returns the current shift value
   * <p>
   * Undefined before first call to
   * {@link #incrementShift(CharTermAttribute, NumericType)}
   */
  int getShift();

  /**
   * Returns the value size in bits (32 for {@code float}, {@code int}; 64 for
   * {@code double}, {@code long})
   */
  int getValueSize();

  /**
   * Set the precision step
   */
  void setPrecisionStep(int precisionStep);

  /**
   * Returns the precision step
   */
  int getPrecisionStep();

  /**
   * Initialise this attribute
   */
  void init(NumericType numericType, long value, int valSize);

  /**
   * Reset the current shift value to 0
   */
  void resetShift();

  /**
   * Increment the shift and generate the next token.
   * <p>
   * The original Lucene's {@link NumericTermAttribute} implements
   * {@link TermToBytesRefAttribute}. There is a conflict problem with the
   * {@link CharTermAttribute} used in higher-level SIREn's analyzers, which also
   * implements {@link TermToBytesRefAttribute}.
   * The problem is that the {@link AttributeSource} is not able to choose
   * between the two when requested an attribute implementing
   * {@link TermToBytesRefAttribute}, e.g., in TermsHashPerField.
   * <p>
   * The current solution is to fill the {@link BytesRef} attribute of the
   * {@link CharTermAttribute} with the encoded numeric value.
   *
   * @return True if there are still tokens, false if we reach the end of the
   * stream.
   */
  boolean incrementShift(CharTermAttribute termAtt);

}
