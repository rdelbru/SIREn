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
package org.sindice.siren.qparser.keyword.processors;

import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.QueryNodeParseException;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.nodes.TermRangeQueryNode;
import org.apache.lucene.queryparser.flexible.standard.processors.NumericRangeQueryNodeProcessor;
import org.sindice.siren.analysis.NumericAnalyzer;
import org.sindice.siren.analysis.NumericAnalyzer.NumericParser;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.DatatypeQueryNode;
import org.sindice.siren.qparser.keyword.nodes.NodeNumericQueryNode;
import org.sindice.siren.qparser.keyword.nodes.NodeNumericRangeQueryNode;
import org.sindice.siren.util.ReusableCharArrayReader;

/**
 * This processor is used to convert {@link TermRangeQueryNode}s to
 * {@link NodeNumericRangeQueryNode}s.
 *
 * <p>
 *
 * It gets the numeric {@link Analyzer}
 * that was previously tagged with {@link DatatypeQueryNode#DATATYPE_TAGID}
 * in {@link DatatypeQueryNodeProcessor}. If it is set and is a {@link NumericAnalyzer},
 * it considers this {@link TermRangeQueryNode} to be a numeric range query and
 * converts it to a {@link NodeNumericRangeQueryNode}.
 *
 * <p>
 *
 * Class copied from {@link NumericRangeQueryNodeProcessor} and modified for the
 * SIREn use case.
 *
 * @see KeywordConfigurationKeys#DATATYPES_ANALYZERS
 * @see TermRangeQueryNode
 * @see NodeNumericRangeQueryNode
 */
public class NodeNumericRangeQueryNodeProcessor
extends QueryNodeProcessorImpl {

  /**
   * Constructs an empty {@link NodeNumericRangeQueryNodeProcessor} object.
   */
  public NodeNumericRangeQueryNodeProcessor() {
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof TermRangeQueryNode) {
      final TermRangeQueryNode termRangeNode = (TermRangeQueryNode) node;

      final Map<String, Analyzer> dts = this.getQueryConfigHandler().get(KeywordConfigurationKeys.DATATYPES_ANALYZERS);
      final Analyzer analyzer = dts.get(node.getTag(DatatypeQueryNode.DATATYPE_TAGID));

      if (analyzer instanceof NumericAnalyzer) {
        final NumericAnalyzer na = (NumericAnalyzer) analyzer;

        final FieldQueryNode lower = termRangeNode.getLowerBound();
        final FieldQueryNode upper = termRangeNode.getUpperBound();

        final char[] lowerText = lower.getTextAsString().toCharArray();
        final char[] upperText = upper.getTextAsString().toCharArray();

        final NumericParser<?> parser = na.getNumericParser();
        final Number lowerNumber;
        try {
          if (lowerText.length == 0) { // open bound
            lowerNumber = null;
          } else {
            final ReusableCharArrayReader lowerReader = new ReusableCharArrayReader(lowerText);
            lowerNumber = parser.parse(lowerReader);
          }
        } catch (final Exception e) {
          throw new QueryNodeParseException(new MessageImpl(QueryParserMessages.COULD_NOT_PARSE_NUMBER,
            lowerText, parser.getNumericType() + " parser"), e);
        }
        final Number upperNumber;
        try {
          if (upperText.length == 0) { // open bound
            upperNumber = null;
          } else {
            final ReusableCharArrayReader upperReader = new ReusableCharArrayReader(upperText);
            upperNumber = parser.parse(upperReader);
          }
        } catch (final Exception e) {
          throw new QueryNodeParseException(new MessageImpl(QueryParserMessages.COULD_NOT_PARSE_NUMBER,
            upperText, parser.getNumericType() + " parser"), e);
        }

        final CharSequence field = termRangeNode.getField();
        final NodeNumericQueryNode lowerNode = new NodeNumericQueryNode(field, lowerNumber);
        final NodeNumericQueryNode upperNode = new NodeNumericQueryNode(field, upperNumber);

        final boolean lowerInclusive = termRangeNode.isLowerInclusive();
        final boolean upperInclusive = termRangeNode.isUpperInclusive();

        return new NodeNumericRangeQueryNode(lowerNode, upperNode, lowerInclusive, upperInclusive, na);
      }
    }
    return node;
  }

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {
    return node;
  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
  throws QueryNodeException {
    return children;
  }

}
