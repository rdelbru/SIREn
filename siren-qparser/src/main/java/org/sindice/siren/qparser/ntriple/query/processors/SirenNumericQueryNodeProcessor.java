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
 * @author Campinas Stephane [ 14 Oct 2011 ]
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
import org.apache.lucene.queryParser.core.nodes.FieldQueryNode;
import org.apache.lucene.queryParser.core.nodes.ParametricQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryParser.standard.config.NumericConfig;
import org.apache.lucene.queryParser.standard.config.StandardQueryConfigHandler.ConfigurationKeys;
import org.apache.lucene.queryParser.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryParser.standard.nodes.NumericRangeQueryNode;
import org.apache.lucene.queryParser.standard.processors.NumericQueryNodeProcessor;

/**
 * Copied from {@link NumericQueryNodeProcessor} for the Siren use case.
 * Since the datatype of the value is set with ^^, there is no need for a field
 * query.
 */
public class SirenNumericQueryNodeProcessor extends QueryNodeProcessorImpl {

  /**
   * Constructs a {@link SirenNumericQueryNodeProcessor} object.
   */
  public SirenNumericQueryNodeProcessor() {
  // empty constructor
  }
  
  @Override
  protected QueryNode postProcessNode(QueryNode node) throws QueryNodeException {
    
    if (node instanceof FieldQueryNode && !(node instanceof ParametricQueryNode)) {
      
      FieldQueryNode fieldNode = (FieldQueryNode) node;
      QueryConfigHandler config = getQueryConfigHandler();

      NumericConfig numericConfig = config.get(ConfigurationKeys.NUMERIC_CONFIG);
      
      if (numericConfig != null) {

        NumberFormat numberFormat = numericConfig.getNumberFormat();
        Number number;
        
        try {
          number = numberFormat.parse(fieldNode.getTextAsString());
          
        } catch (ParseException e) {
          throw new QueryNodeParseException(new MessageImpl(
              QueryParserMessages.COULD_NOT_PARSE_NUMBER, fieldNode
                  .getTextAsString(), numberFormat.getClass()
                  .getCanonicalName()), e);
        }
        
        switch (numericConfig.getType()) {
          case LONG:
            number = number.longValue();
            break;
          case INT:
            number = number.intValue();
            break;
          case DOUBLE:
            number = number.doubleValue();
            break;
          case FLOAT:
            number = number.floatValue();
        }
        
        NumericQueryNode lowerNode = new NumericQueryNode(fieldNode
            .getField(), number, numberFormat);
        NumericQueryNode upperNode = new NumericQueryNode(fieldNode
            .getField(), number, numberFormat);
        
        return new NumericRangeQueryNode(lowerNode, upperNode, true, true,
            numericConfig);
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
