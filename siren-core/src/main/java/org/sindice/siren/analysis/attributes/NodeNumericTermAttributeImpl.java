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

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;

/**
 * Default implementation of the {@link NodeNumericTermAttribute}.
 */
public class NodeNumericTermAttributeImpl
extends AttributeImpl
implements NodeNumericTermAttribute {

  private NumericType numericType;
  private long     value    = 0L;
  private int      valueSize = 0, shift = 0, precisionStep = 0;

  /**
   * Char array containing the string representation of the numeric type
   */
  private char[] numericTypeCharArray;

  /**
   * Char array containing the string representation of the precision step
   */
  private char[] precisionStepCharArray;

  /**
   * Bytes buffer that will hold the current prefix coded bits of the value
   */
  private final BytesRef bytesRef  = new BytesRef();

  public NumericType getNumericType() { return numericType; }

  public int getShift() {
    // substract precistionStep to take into consideration the increment in
    // incrementShift
    return shift - precisionStep;
  }

  public void resetShift() { this.shift = 0; }

  public int getValueSize() { return valueSize; }

  public void setPrecisionStep(final int precisionStep) {
    if (precisionStep < 1) {
      throw new IllegalArgumentException("precisionStep must be >=1");
    }
    this.precisionStep = precisionStep;
    precisionStepCharArray = String.valueOf(precisionStep).toCharArray();
  }

  public int getPrecisionStep() { return precisionStep; }

  public void init(final NumericType numericType, final long value,
                   final int valueSize) {
    this.numericType = numericType;
    numericTypeCharArray = numericType.toString().toCharArray();
    this.value = value;
    this.valueSize = valueSize;
    this.shift = 0;
  }

  private void bytesRefToChar(final CharTermAttribute termAtt) {
    final char[] buffer;
    final int prefixSize = numericTypeCharArray.length + precisionStepCharArray.length;

    switch (valueSize) {
      case 64:
        NumericUtils.longToPrefixCoded(value, shift, bytesRef);
        buffer = termAtt.resizeBuffer(NumericUtils.BUF_SIZE_LONG + prefixSize);
        break;

      case 32:
        NumericUtils.intToPrefixCoded((int) value, shift, bytesRef);
        buffer = termAtt.resizeBuffer(NumericUtils.BUF_SIZE_INT + prefixSize);
        break;

      default:
        // should not happen
        throw new IllegalArgumentException("valueSize must be 32 or 64");
    }

    // Prepend the numericType
    System.arraycopy(numericTypeCharArray, 0, buffer, 0, numericTypeCharArray.length);
    // Prepend the precision step
    System.arraycopy(precisionStepCharArray, 0, buffer, numericTypeCharArray.length, precisionStepCharArray.length);
    // append the numeric encoded value
    for (int i = bytesRef.offset; i < bytesRef.length; i++) {
      buffer[prefixSize + i] = (char) bytesRef.bytes[i];
    }
    termAtt.setLength(prefixSize + bytesRef.length);
  }

  public boolean incrementShift(final CharTermAttribute termAtt) {
    // check if we reach end of the stream
    if (shift >= valueSize) {
      return false;
    }

    try {
      // generate the next token and update the char term attribute
      this.bytesRefToChar(termAtt);
      // increment shift for next token
      shift += precisionStep;
      return true;
    }
    catch (final IllegalArgumentException iae) {
      // return empty token before first or after last
      termAtt.setEmpty();
      // ends the numeric tokenstream
      shift = valueSize;
      return false;
    }
  }

  @Override
  public void clear() {
    // this attribute has no contents to clear!
    // we keep it untouched as it's fully controlled by outer class.
  }

  @Override
  public void copyTo(final AttributeImpl target) {
    final NodeNumericTermAttribute a = (NodeNumericTermAttribute) target;
    a.setPrecisionStep(precisionStep);
    a.init(numericType, value, valueSize);
  }

}
