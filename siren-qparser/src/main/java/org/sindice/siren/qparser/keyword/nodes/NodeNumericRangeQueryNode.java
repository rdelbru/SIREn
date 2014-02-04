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
package org.sindice.siren.qparser.keyword.nodes;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.standard.config.NumericConfig;
import org.apache.lucene.queryparser.flexible.standard.nodes.AbstractRangeQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericRangeQueryNode;
import org.sindice.siren.analysis.NumericAnalyzer;

/**
 * This {@link QueryNode} represents a range query composed by
 * {@link NodeNumericQueryNode} bounds, which means the bound values are
 * {@link Number}s.
 *
 * <p>
 *
 * The configuration in Siren of a numeric value is done through {@link Analyzer}s,
 * not through {@link NumericConfig}.
 *
 * <p>
 *
 * Class copied from {@link NumericRangeQueryNode} and adapted for the SIREn use
 * case.
 */
public class NodeNumericRangeQueryNode extends AbstractRangeQueryNode<NumericQueryNode> {

  public final NumericAnalyzer numericAnalyzer;
  public final NumericType numericType;

  /**
   * Constructs a {@link NodeNumericRangeQueryNode} object using the given
   * {@link NumericQueryNode} as its bounds and a {@link NumericAnalyzer}.
   *
   * @param lower the lower bound
   * @param upper the upper bound
   * @param lowerInclusive <code>true</code> if the lower bound is inclusive, otherwise, <code>false</code>
   * @param upperInclusive <code>true</code> if the upper bound is inclusive, otherwise, <code>false</code>
   * @param numericAnalyzer the {@link NumericAnalyzer} associated with the upper and lower bounds
   */
  public NodeNumericRangeQueryNode(final NumericQueryNode lower,
                                   final NumericQueryNode upper,
                                   final boolean lowerInclusive,
                                   final boolean upperInclusive,
                                   final NumericAnalyzer numericAnalyzer)
  throws QueryNodeException {
    if (numericAnalyzer == null) {
      throw new IllegalArgumentException("numericAnalyzer cannot be null!");
    }
    super.setBounds(lower, upper, lowerInclusive, upperInclusive);
    this.numericAnalyzer = numericAnalyzer;
    this.numericType = numericAnalyzer.getNumericParser().getNumericType();
  }

  /**
   * Returns the {@link NumericAnalyzer} associated with the lower and upper bounds.
   */
  public NumericAnalyzer getNumericAnalyzer() {
    return this.numericAnalyzer;
  }

  /**
   * Returns the {@link NumericType} of the lower and upper bounds.
   */
  public NumericType getNumericType() {
    return numericType;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("<numericRange lowerInclusive='");

    sb.append(this.isLowerInclusive()).append("' upperInclusive='").append(
        this.isUpperInclusive()).append(
        "' precisionStep='" + numericAnalyzer.getPrecisionStep()).append(
        "' type='" + numericType).append("'>\n");

    sb.append(this.getLowerBound()).append('\n');
    sb.append(this.getUpperBound()).append('\n');
    sb.append("</numericRange>");

    return sb.toString();
  }

}
