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
 * @project siren-qparser_rdelbru
 * @author Campinas Stephane [ 17 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.qparser.ntriple.query.nodes;

import org.apache.lucene.document.NumericField;
import org.apache.lucene.messages.MessageImpl;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.messages.QueryParserMessages;
import org.apache.lucene.queryParser.standard.config.NumericConfig;
import org.apache.lucene.queryParser.standard.nodes.AbstractRangeQueryNode;
import org.apache.lucene.queryParser.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryParser.standard.nodes.NumericRangeQueryNode;
import org.sindice.siren.analysis.NumericAnalyzer;

/**
 * Class copied from {@link NumericRangeQueryNode} for the Siren purpose: the configuration
 * in Siren of a numeric value is done through analyzers, not {@link NumericConfig}.
 */
public class SirenNumericRangeQueryNode extends AbstractRangeQueryNode<NumericQueryNode> {

  public NumericAnalyzer numericAnalyzer;
  
  /**
   * Constructs a {@link NumericRangeQueryNode} object using the given
   * {@link NumericQueryNode} as its bounds and {@link NumericConfig}.
   * 
   * @param lower the lower bound
   * @param upper the upper bound
   * @param lowerInclusive <code>true</code> if the lower bound is inclusive, otherwise, <code>false</code>
   * @param upperInclusive <code>true</code> if the upper bound is inclusive, otherwise, <code>false</code>
   * @param numericAnalyzer the {@link NumericAnalyzer} that represents associated with the upper and lower bounds
   * 
   * @see #setBounds(NumericQueryNode, NumericQueryNode, boolean, boolean, NumericAnalyzer)
   */
  public SirenNumericRangeQueryNode(NumericQueryNode lower, NumericQueryNode upper,
      boolean lowerInclusive, boolean upperInclusive, NumericAnalyzer numericAnalyzer) throws QueryNodeException {
    setBounds(lower, upper, lowerInclusive, upperInclusive, numericAnalyzer);
  }
  
  private static NumericField.DataType getNumericDataType(Number number) throws QueryNodeException {
    
    if (number instanceof Long) {
      return NumericField.DataType.LONG;
    } else if (number instanceof Integer) {
      return NumericField.DataType.INT;
    } else if (number instanceof Double) {
      return NumericField.DataType.DOUBLE;
    } else if (number instanceof Float) {
      return NumericField.DataType.FLOAT;
    } else {
      throw new QueryNodeException(
          new MessageImpl(
              QueryParserMessages.NUMBER_CLASS_NOT_SUPPORTED_BY_NUMERIC_RANGE_QUERY,
              number.getClass()));
    }
    
  }
  
  /**
   * Sets the upper and lower bounds of this range query node and the
   * {@link NumericConfig} associated with these bounds.
   * 
   * @param lower the lower bound
   * @param upper the upper bound
   * @param lowerInclusive <code>true</code> if the lower bound is inclusive, otherwise, <code>false</code>
   * @param upperInclusive <code>true</code> if the upper bound is inclusive, otherwise, <code>false</code>
   * @param numericAnalyzer the {@link numericAnalyzer} that represents associated with the upper and lower bounds
   * 
   */
  public void setBounds(NumericQueryNode lower, NumericQueryNode upper,
      boolean lowerInclusive, boolean upperInclusive, NumericAnalyzer numericAnalyzer) throws QueryNodeException {
    
    if (numericAnalyzer == null) {
      throw new IllegalArgumentException("numericAnalyzer cannot be null!");
    }
    
    NumericField.DataType lowerNumberType, upperNumberType;
    
    if (lower != null && lower.getValue() != null) {
      lowerNumberType = getNumericDataType(lower.getValue());
    } else {
      lowerNumberType = null;
    }
    
    if (upper != null && upper.getValue() != null) {
      upperNumberType = getNumericDataType(upper.getValue());
    } else {
      upperNumberType = null;
    }
    
    if (lowerNumberType != null
        && !lowerNumberType.equals(numericAnalyzer.getNumericType())) {
      throw new IllegalArgumentException(
          "lower value's type should be the same as numericAnalyzer type: "
              + lowerNumberType + " != " + numericAnalyzer.getNumericType());
    }
    
    if (upperNumberType != null
        && !upperNumberType.equals(numericAnalyzer.getNumericType())) {
      throw new IllegalArgumentException(
          "upper value's type should be the same as numericAnalyzer type: "
              + upperNumberType + " != " + numericAnalyzer.getNumericType());
    }
    
    super.setBounds(lower, upper, lowerInclusive, upperInclusive);
    this.numericAnalyzer = numericAnalyzer;
    
  }
  
  /**
   * Returns the {@link NumericAnalyzer} associated with the lower and upper bounds.
   * 
   * @return the {@link NumericAnalyzer} associated with the lower and upper bounds
   */
  public NumericAnalyzer getNumericAnalyzer() {
    return this.numericAnalyzer;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("<numericRange lowerInclusive='");
    
    sb.append(isLowerInclusive()).append("' upperInclusive='").append(
        isUpperInclusive()).append(
        "' precisionStep='" + numericAnalyzer.getPrecisionStep()).append(
        "' type='" + numericAnalyzer.getNumericType()).append("'>\n");
    
    sb.append(getLowerBound()).append('\n');
    sb.append(getUpperBound()).append('\n');
    sb.append("</numericRange>");
    
    return sb.toString();
    
  }
  
}
