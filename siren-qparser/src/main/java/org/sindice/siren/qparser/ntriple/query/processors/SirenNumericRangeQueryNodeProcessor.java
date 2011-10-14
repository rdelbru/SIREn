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
 * @author Campinas Stephane [ 13 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.qparser.ntriple.query.processors;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import org.apache.lucene.messages.MessageImpl;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.QueryNodeParseException;
import org.apache.lucene.queryParser.core.config.QueryConfigHandler;
import org.apache.lucene.queryParser.core.messages.QueryParserMessages;
import org.apache.lucene.queryParser.core.nodes.ParametricQueryNode;
import org.apache.lucene.queryParser.core.nodes.ParametricRangeQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.nodes.ParametricQueryNode.CompareOperator;
import org.apache.lucene.queryParser.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryParser.standard.config.NumericConfig;
import org.apache.lucene.queryParser.standard.config.StandardQueryConfigHandler.ConfigurationKeys;
import org.apache.lucene.queryParser.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryParser.standard.nodes.NumericRangeQueryNode;
import org.apache.lucene.queryParser.standard.processors.NumericRangeQueryNodeProcessor;

/**
 * 
 */
public class SirenNumericRangeQueryNodeProcessor extends QueryNodeProcessorImpl {

  /**
   * Constructs an empty {@link SirenNumericRangeQueryNodeProcessor} object.
   * Class copied from {@link NumericRangeQueryNodeProcessor} for the SIREn use case.
   */
  public SirenNumericRangeQueryNodeProcessor() {
  // empty constructor
  }
  
  @Override
  protected QueryNode postProcessNode(QueryNode node) throws QueryNodeException {
    
    if (node instanceof ParametricRangeQueryNode) {
      QueryConfigHandler config = getQueryConfigHandler();
      
      if (config != null) {
        ParametricRangeQueryNode parametricRangeNode = (ParametricRangeQueryNode) node;
        
        NumericConfig numericConfig = config.get(ConfigurationKeys.NUMERIC_CONFIG);
        
        if (numericConfig != null) {
          
          ParametricQueryNode lower = parametricRangeNode.getLowerBound();
          ParametricQueryNode upper = parametricRangeNode.getUpperBound();
          
          NumberFormat numberFormat = numericConfig.getNumberFormat();
          Number lowerNumber, upperNumber;
          
          try {
            lowerNumber = numberFormat.parse(lower.getTextAsString());
            
          } catch (ParseException e) {
            throw new QueryNodeParseException(new MessageImpl(
                QueryParserMessages.COULD_NOT_PARSE_NUMBER, lower
                    .getTextAsString(), numberFormat.getClass()
                    .getCanonicalName()), e);
          }
          
          try {
            upperNumber = numberFormat.parse(upper.getTextAsString());
            
          } catch (ParseException e) {
            throw new QueryNodeParseException(new MessageImpl(
                QueryParserMessages.COULD_NOT_PARSE_NUMBER, upper
                    .getTextAsString(), numberFormat.getClass()
                    .getCanonicalName()), e);
          }
          
          switch (numericConfig.getType()) {
            case LONG:
              upperNumber = upperNumber.longValue();
              lowerNumber = lowerNumber.longValue();
              break;
            case INT:
              upperNumber = upperNumber.intValue();
              lowerNumber = lowerNumber.intValue();
              break;
            case DOUBLE:
              upperNumber = upperNumber.doubleValue();
              lowerNumber = lowerNumber.doubleValue();
              break;
            case FLOAT:
              upperNumber = upperNumber.floatValue();
              lowerNumber = lowerNumber.floatValue();
          }
          
          NumericQueryNode lowerNode = new NumericQueryNode(
              parametricRangeNode.getField(), lowerNumber, numberFormat);
          NumericQueryNode upperNode = new NumericQueryNode(
              parametricRangeNode.getField(), upperNumber, numberFormat);
          
          boolean upperInclusive = upper.getOperator() == CompareOperator.LE;
          boolean lowerInclusive = lower.getOperator() == CompareOperator.GE;
          
          return new NumericRangeQueryNode(lowerNode, upperNode,
              lowerInclusive, upperInclusive, numericConfig);
        }
      }
      
    }
    
    return node;
    
  }
  
  @Override
  protected QueryNode preProcessNode(QueryNode node) throws QueryNodeException {
    return node;
  }
  
  @Override
  protected List<QueryNode> setChildrenOrder(List<QueryNode> children)
      throws QueryNodeException {
    return children;
  }
  
}
