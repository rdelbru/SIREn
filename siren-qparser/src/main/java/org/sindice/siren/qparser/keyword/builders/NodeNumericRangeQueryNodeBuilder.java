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
package org.sindice.siren.qparser.keyword.builders;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.builders.NumericRangeQueryNodeBuilder;
import org.apache.lucene.queryparser.flexible.standard.config.NumericConfig;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericQueryNode;
import org.sindice.siren.analysis.NumericAnalyzer;
import org.sindice.siren.qparser.keyword.nodes.NodeNumericRangeQueryNode;
import org.sindice.siren.search.node.NodeNumericRangeQuery;

/**
 * Builds a {@link NodeNumericRangeQuery} object from a
 * {@link NodeNumericRangeQueryNode} object.
 *
 * <p>
 *
 * Class copied from {@link NumericRangeQueryNodeBuilder} for the Siren use case:
 * in Siren, we use an {@link Analyzer} instead of a {@link NumericConfig}
 * configuration object.
 */
public class NodeNumericRangeQueryNodeBuilder implements KeywordQueryBuilder {

  /**
   * Constructs a {@link NodeNumericRangeQueryNodeBuilder} object.
   */
  public NodeNumericRangeQueryNodeBuilder() {
    // empty constructor
  }

  public NodeNumericRangeQuery<? extends Number> build(final QueryNode queryNode)
  throws QueryNodeException {
    final NodeNumericRangeQueryNode numericRangeNode = (NodeNumericRangeQueryNode) queryNode;

    final NumericQueryNode lowerNumericNode = numericRangeNode.getLowerBound();
    final NumericQueryNode upperNumericNode = numericRangeNode.getUpperBound();

    final Number lowerNumber, upperNumber;

    if (lowerNumericNode != null) {
      lowerNumber = lowerNumericNode.getValue();
    }
    else {
      lowerNumber = null;
    }

    if (upperNumericNode != null) {
      upperNumber = upperNumericNode.getValue();
    }
    else {
      upperNumber = null;
    }

    final NumericAnalyzer numericAnalyzer = numericRangeNode.getNumericAnalyzer();
    final NumericType numberType = numericRangeNode.getNumericType();
    final String field = numericRangeNode.getField().toString();
    final boolean minInclusive = numericRangeNode.isLowerInclusive();
    final boolean maxInclusive = numericRangeNode.isUpperInclusive();
    final int precisionStep = numericAnalyzer.getPrecisionStep();

    switch (numberType) {
      case LONG:
        return NodeNumericRangeQuery.newLongRange(field, precisionStep,
          (Long) lowerNumber, (Long) upperNumber, minInclusive, maxInclusive);

      case INT:
        return NodeNumericRangeQuery.newIntRange(field, precisionStep,
          (Integer) lowerNumber, (Integer) upperNumber, minInclusive,
          maxInclusive);

      case FLOAT:
        return NodeNumericRangeQuery.newFloatRange(field, precisionStep,
          (Float) lowerNumber, (Float) upperNumber, minInclusive, maxInclusive);

      case DOUBLE:
        return NodeNumericRangeQuery.newDoubleRange(field, precisionStep,
          (Double) lowerNumber, (Double) upperNumber, minInclusive,
          maxInclusive);

      default:
        throw new QueryNodeException(new MessageImpl(QueryParserMessages.UNSUPPORTED_NUMERIC_DATA_TYPE, numberType));
    }
  }

}
