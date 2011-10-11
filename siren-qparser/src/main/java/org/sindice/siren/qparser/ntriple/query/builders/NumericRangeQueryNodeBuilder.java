/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved. Project and
 * contact information: http://www.siren.sindice.com/ This file is part of the
 * SIREn project. SIREn is a free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version. SIREn is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details. You should have received a
 * copy of the GNU Affero General Public License along with SIREn. If not, see
 * <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren-qparser_rdelbru
 * @author Campinas Stephane [ 10 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.qparser.ntriple.query.builders;

import org.apache.lucene.document.NumericField;
import org.apache.lucene.messages.MessageImpl;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.messages.QueryParserMessages;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.util.StringUtils;
import org.apache.lucene.queryParser.standard.config.NumericConfig;
import org.apache.lucene.queryParser.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryParser.standard.nodes.NumericRangeQueryNode;
import org.sindice.siren.search.SirenNumericRangeQuery;

/**
 * 
 */
public class NumericRangeQueryNodeBuilder implements ResourceQueryBuilder {

  /**
   * Constructs a {@link NumericRangeQueryNodeBuilder} object.
   */
  public NumericRangeQueryNodeBuilder() {
    // empty constructor
  }

  public SirenNumericRangeQuery<? extends Number> build(QueryNode queryNode)
  throws QueryNodeException {
    NumericRangeQueryNode numericRangeNode = (NumericRangeQueryNode) queryNode;

    NumericQueryNode lowerNumericNode = numericRangeNode.getLowerBound();
    NumericQueryNode upperNumericNode = numericRangeNode.getUpperBound();

    Number lowerNumber, upperNumber;

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

    NumericConfig numericConfig = numericRangeNode.getNumericConfig();
    NumericField.DataType numberType = numericConfig.getType();
    String field = StringUtils.toString(numericRangeNode.getField());
    boolean minInclusive = numericRangeNode.isLowerInclusive();
    boolean maxInclusive = numericRangeNode.isUpperInclusive();
    int precisionStep = numericConfig.getPrecisionStep();

    switch (numberType) {

      case LONG:
        return SirenNumericRangeQuery.newLongRange(field, precisionStep,
          (Long) lowerNumber, (Long) upperNumber, minInclusive, maxInclusive);

      case INT:
        return SirenNumericRangeQuery.newIntRange(field, precisionStep,
          (Integer) lowerNumber, (Integer) upperNumber, minInclusive,
          maxInclusive);

      case FLOAT:
        return SirenNumericRangeQuery.newFloatRange(field, precisionStep,
          (Float) lowerNumber, (Float) upperNumber, minInclusive, maxInclusive);

      case DOUBLE:
        return SirenNumericRangeQuery.newDoubleRange(field, precisionStep,
          (Double) lowerNumber, (Double) upperNumber, minInclusive,
          maxInclusive);

      default:
        throw new QueryNodeException(new MessageImpl(QueryParserMessages.UNSUPPORTED_NUMERIC_DATA_TYPE, numberType));

    }
  }

}
