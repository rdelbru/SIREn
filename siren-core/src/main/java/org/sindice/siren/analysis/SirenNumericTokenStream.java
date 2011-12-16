/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
 * @project siren-core_rdelbru
 * @author Campinas Stephane [ 24 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.analysis;

import org.apache.lucene.analysis.NumericTokenStream;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.NumericField.DataType;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.NumericUtils;

/**
 * Copied from {@link NumericTokenStream} for the Siren use case: for more precise
 * search, we put as prefix the numeric type and the precision step to the term to the term.
 */
public final class SirenNumericTokenStream extends TokenStream {
  
  /** The full precision token gets this token type assigned. */
  public static final String TOKEN_TYPE_FULL_PREC  = "fullPrecNumeric";

  /** The lower precision tokens gets this token type assigned. */
  public static final String TOKEN_TYPE_LOWER_PREC = "lowerPrecNumeric";

  /**
   * Creates a token stream for numeric values using the default <code>precisionStep</code>
   * {@link NumericUtils#PRECISION_STEP_DEFAULT} (4). The stream is not yet initialized,
   * before using set a value using the various set<em>???</em>Value() methods.
   */
  public SirenNumericTokenStream() {
    this(NumericUtils.PRECISION_STEP_DEFAULT);
  }
  
  /**
   * Creates a token stream for numeric values with the specified
   * <code>precisionStep</code>. The stream is not yet initialized,
   * before using set a value using the various set<em>???</em>Value() methods.
   */
  public SirenNumericTokenStream(final int precisionStep) {
    super();
    this.precisionStep = precisionStep;
    precisionStepCA = init();
    if (precisionStep < 1)
      throw new IllegalArgumentException("precisionStep must be >=1");
  }

  /**
   * Expert: Creates a token stream for numeric values with the specified
   * <code>precisionStep</code> using the given {@link AttributeSource}.
   * The stream is not yet initialized,
   * before using set a value using the various set<em>???</em>Value() methods.
   */
  public SirenNumericTokenStream(AttributeSource source, final int precisionStep) {
    super(source);
    this.precisionStep = precisionStep;
    precisionStepCA = init();
    if (precisionStep < 1)
      throw new IllegalArgumentException("precisionStep must be >=1");
  }

  /**
   * Expert: Creates a token stream for numeric values with the specified
   * <code>precisionStep</code> using the given
   * {@link org.apache.lucene.util.AttributeSource.AttributeFactory}.
   * The stream is not yet initialized,
   * before using set a value using the various set<em>???</em>Value() methods.
   */
  public SirenNumericTokenStream(AttributeFactory factory, final int precisionStep) {
    super(factory);
    this.precisionStep = precisionStep;
    precisionStepCA = init();
    if (precisionStep < 1)
      throw new IllegalArgumentException("precisionStep must be >=1");
  }

  /**
   * Create a char array from an integer
   * @return
   */
  private char[] init() {
    int pstep = precisionStep;
    int size = 1;
    
    while (pstep / 10 > 0) {
      size++;
      pstep /= 10;
    }
    pstep = precisionStep;
    final char[] c = new char[size];
    
    for (int i = size - 1; i >= 0; i--) {
      c[i] = (char) ('0' + pstep % 10);
      pstep /= 10;
    }
    return c;
  }
  
  /**
   * Initializes the token stream with the supplied <code>long</code> value.
   * @param value the value, for which this TokenStream should enumerate tokens.
   * @return this instance, because of this you can use it the following way:
   * <code>new Field(name, new NumericTokenStream(precisionStep).setLongValue(value))</code>
   */
  public SirenNumericTokenStream setLongValue(final long value) {
    this.value = value;
    valSize = 64;
    shift = 0;
    datatype = DataType.LONG;
    return this;
  }
  
  /**
   * Initializes the token stream with the supplied <code>int</code> value.
   * @param value the value, for which this TokenStream should enumerate tokens.
   * @return this instance, because of this you can use it the following way:
   * <code>new Field(name, new NumericTokenStream(precisionStep).setIntValue(value))</code>
   */
  public SirenNumericTokenStream setIntValue(final int value) {
    this.value = value;
    valSize = 32;
    shift = 0;
    datatype = DataType.INT;
    return this;
  }
  
  /**
   * Initializes the token stream with the supplied <code>double</code> value.
   * @param value the value, for which this TokenStream should enumerate tokens.
   * @return this instance, because of this you can use it the following way:
   * <code>new Field(name, new NumericTokenStream(precisionStep).setDoubleValue(value))</code>
   */
  public SirenNumericTokenStream setDoubleValue(final double value) {
    this.value = NumericUtils.doubleToSortableLong(value);
    valSize = 64;
    shift = 0;
    datatype = DataType.DOUBLE;
    return this;
  }
  
  /**
   * Initializes the token stream with the supplied <code>float</code> value.
   * @param value the value, for which this TokenStream should enumerate tokens.
   * @return this instance, because of this you can use it the following way:
   * <code>new Field(name, new NumericTokenStream(precisionStep).setFloatValue(value))</code>
   */
  public SirenNumericTokenStream setFloatValue(final float value) {
    this.value = NumericUtils.floatToSortableInt(value);
    valSize = 32;
    shift = 0;
    datatype = DataType.FLOAT;
    return this;
  }
  
  @Override
  public void reset() {
    if (valSize == 0)
      throw new IllegalStateException("call set???Value() before usage");
    shift = 0;
  }

  @Override
  public boolean incrementToken() {
    if (valSize == 0)
      throw new IllegalStateException("call set???Value() before usage");
    if (shift >= valSize)
      return false;

    clearAttributes();
    final String dt = datatype.name();
    final char[] buffer;
    final int prefixSize = dt.length() + precisionStepCA.length;
    switch (valSize) {
      case 64:
        buffer = termAtt.resizeBuffer(NumericUtils.BUF_SIZE_LONG + prefixSize);
        termAtt.setLength(NumericUtils.longToPrefixCoded(value, shift, buffer));
        break;
      
      case 32:
        buffer = termAtt.resizeBuffer(NumericUtils.BUF_SIZE_INT + prefixSize);
        termAtt.setLength(NumericUtils.intToPrefixCoded((int) value, shift, buffer));
        break;
      
      default:
        // should not happen
        throw new IllegalArgumentException("valSize must be 32 or 64");
    }

    /*
     * Append the datatype for more precise search
     */
    // move the encoded numeric value to the end of the buffer
    for (int i = termAtt.length() - 1; i >= 0; i--) {
      buffer[i + prefixSize] = buffer[i];
    }
    // write the prefix datatype
    switch (datatype) {
      case INT:
        buffer[0] = 'I';
        buffer[1] = 'N';
        buffer[2] = 'T';
        System.arraycopy(precisionStepCA, 0, buffer, 3, precisionStepCA.length);
        break;
      case FLOAT:
        buffer[0] = 'F';
        buffer[1] = 'L';
        buffer[2] = 'O';
        buffer[3] = 'A';
        buffer[4] = 'T';
        System.arraycopy(precisionStepCA, 0, buffer, 5, precisionStepCA.length);
        break;
      case LONG:
        buffer[0] = 'L';
        buffer[1] = 'O';
        buffer[2] = 'N';
        buffer[3] = 'G';
        System.arraycopy(precisionStepCA, 0, buffer, 4, precisionStepCA.length);
        break;
      case DOUBLE:
        buffer[0] = 'D';
        buffer[1] = 'O';
        buffer[2] = 'U';
        buffer[3] = 'B';
        buffer[4] = 'L';
        buffer[5] = 'E';
        System.arraycopy(precisionStepCA, 0, buffer, 6, precisionStepCA.length);
        break;
      default:
        break;
    }
    termAtt.setLength(termAtt.length() + prefixSize);
    
    typeAtt.setType((shift == 0) ? TOKEN_TYPE_FULL_PREC : TOKEN_TYPE_LOWER_PREC);
    posIncrAtt.setPositionIncrement((shift == 0) ? 1 : 0);
    shift += precisionStep;
    return true;
  }
  
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("(numeric,valSize=").append(valSize);
    sb.append(",precisionStep=").append(precisionStep).append(')');
    return sb.toString();
  }

  /** Returns the precision step. */
  public int getPrecisionStep() {
    return precisionStep;
  }
  
  // members
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
  private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);
  private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
  
  private int shift = 0, valSize = 0; // valSize==0 means not initialized
  private final int precisionStep;
  private final char[] precisionStepCA;
  
  /** The datatype of the current token, after the call to set***Value */
  private DataType datatype;
  
  private long value = 0L;

}
