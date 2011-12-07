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

import java.io.IOException;
import java.io.StringReader;
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
import org.apache.lucene.queryParser.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryParser.standard.processors.NumericRangeQueryNodeProcessor;
import org.sindice.siren.analysis.NumericAnalyzer;
import org.sindice.siren.qparser.ntriple.query.nodes.SirenNumericRangeQueryNode;
import org.sindice.siren.qparser.tuple.ResourceQueryParser.SirenConfigurationKeys;
import org.sindice.siren.util.XSDPrimitiveTypeParser;

/**
 * Class copied from {@link NumericRangeQueryNodeProcessor} for the SIREn use case.
 */
public class SirenNumericRangeQueryNodeProcessor extends QueryNodeProcessorImpl {

  public SirenNumericRangeQueryNodeProcessor() {
  }
  
  @Override
  protected QueryNode postProcessNode(QueryNode node) throws QueryNodeException {
    
    if (node instanceof ParametricRangeQueryNode) {
      QueryConfigHandler config = getQueryConfigHandler();
      
      if (config != null) {
        ParametricRangeQueryNode parametricRangeNode = (ParametricRangeQueryNode) node;
        
        final NumericAnalyzer na = config.get(SirenConfigurationKeys.NUMERIC_ANALYZERS);
        
        if (na != null) {
          
          ParametricQueryNode lower = parametricRangeNode.getLowerBound();
          ParametricQueryNode upper = parametricRangeNode.getUpperBound();
          
          final Number lowerNumber, upperNumber;
          final String l = lower.getTextAsString();
          final String u = upper.getTextAsString();
          final StringReader lowerReader = new StringReader(l);
          final StringReader upperReader = new StringReader(u);
          try {
            switch (na.getNumericType()) {
              case LONG:
                lowerNumber = l.equals("*") ? Long.MIN_VALUE : XSDPrimitiveTypeParser.parseLong(lowerReader);
                upperNumber = u.equals("*") ? Long.MAX_VALUE : XSDPrimitiveTypeParser.parseLong(upperReader);
                break;
              case INT:
                lowerNumber = l.equals("*") ? Integer.MIN_VALUE : XSDPrimitiveTypeParser.parseInt(lowerReader);
                upperNumber = u.equals("*") ? Integer.MAX_VALUE : XSDPrimitiveTypeParser.parseInt(upperReader);
                break;
              case DOUBLE:
                lowerNumber = l.equals("*") ? Double.MIN_VALUE : XSDPrimitiveTypeParser.parseDouble(lowerReader);
                upperNumber = u.equals("*") ? Double.MAX_VALUE : XSDPrimitiveTypeParser.parseDouble(upperReader);
                break;
              case FLOAT:
                lowerNumber = l.equals("*") ? Float.MIN_VALUE : XSDPrimitiveTypeParser.parseFloat(lowerReader);
                upperNumber = u.equals("*") ? Float.MAX_VALUE : XSDPrimitiveTypeParser.parseFloat(upperReader);
                break;
              default:
                throw new QueryNodeParseException(new MessageImpl(
                  QueryParserMessages.UNSUPPORTED_NUMERIC_DATA_TYPE, na.getNumericType()));
            }          
          } catch (NumberFormatException e) {
            throw new QueryNodeParseException(new MessageImpl(
              QueryParserMessages.COULD_NOT_PARSE_NUMBER, lower.getTextAsString(), upper.getTextAsString()), e);
          } catch (IOException e) {
            throw new QueryNodeParseException(new MessageImpl(
              QueryParserMessages.COULD_NOT_PARSE_NUMBER, lower.getTextAsString(), upper.getTextAsString()), e);
          }
          
          NumericQueryNode lowerNode = new NumericQueryNode(
              parametricRangeNode.getField(), lowerNumber, null);
          NumericQueryNode upperNode = new NumericQueryNode(
              parametricRangeNode.getField(), upperNumber, null);
          
          boolean upperInclusive = upper.getOperator() == CompareOperator.LE;
          boolean lowerInclusive = lower.getOperator() == CompareOperator.GE;
          
          return new SirenNumericRangeQueryNode(lowerNode, upperNode,
              lowerInclusive, upperInclusive, na);
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
