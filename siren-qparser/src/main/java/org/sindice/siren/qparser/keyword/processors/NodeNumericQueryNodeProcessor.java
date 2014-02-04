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
import org.apache.lucene.queryparser.flexible.core.nodes.RangeQueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryparser.flexible.standard.processors.NumericQueryNodeProcessor;
import org.sindice.siren.analysis.NumericAnalyzer;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.DatatypeQueryNode;
import org.sindice.siren.qparser.keyword.nodes.NodeNumericQueryNode;
import org.sindice.siren.qparser.keyword.nodes.NodeNumericRangeQueryNode;
import org.sindice.siren.util.ReusableCharArrayReader;

/**
 * This processor is used to convert {@link FieldQueryNode}s to
 * {@link NodeNumericRangeQueryNode}s.
 *
 * <p>
 *
 * It gets the numeric {@link Analyzer} that was previously tagged with
 * {@link DatatypeQueryNode#DATATYPE_TAGID}
 * in {@link DatatypeQueryNodeProcessor}. If set and is a {@link NumericAnalyzer},
 * it considers this {@link FieldQueryNode} to be a numeric query and converts
 * it to {@link NodeNumericRangeQueryNode} with upper and lower inclusive and
 * lower and upper equals to the value represented by the {@link FieldQueryNode}
 * converted to {@link Number}. It means that <b>1^^&lt;int&gt;</b> is converted
 * to <b>[1 TO 1]^^&lt;int&gt;</b>.
 *
 * <p>
 *
 * The datatype of the value does not depend on a field as in Lucene (see {@link DatatypeQueryNodeProcessor}).
 *
 * <p>
 *
 * Note that {@link FieldQueryNode}s children of a
 * {@link RangeQueryNode} are ignored.
 *
 * <p>
 *
 * Copied from {@link NumericQueryNodeProcessor} and modified for the
 * SIREn use case.
 *
 * @see KeywordConfigurationKeys#DATATYPES_ANALYZERS
 * @see FieldQueryNode
 * @see NumericQueryNode
 * @see NodeNumericRangeQueryNode
 */
public class NodeNumericQueryNodeProcessor
extends QueryNodeProcessorImpl {

  /**
   * Constructs an empty {@link NodeNumericQueryNodeProcessor} object.
   */
  public NodeNumericQueryNodeProcessor() {
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node)
  throws QueryNodeException {

    if (node instanceof FieldQueryNode &&
        !(node.getParent() instanceof RangeQueryNode)) {
      final FieldQueryNode fieldNode = (FieldQueryNode) node;

      final Map<String, Analyzer> dts = this.getQueryConfigHandler().get(KeywordConfigurationKeys.DATATYPES_ANALYZERS);
      final Analyzer analyzer = dts.get(node.getTag(DatatypeQueryNode.DATATYPE_TAGID));

      if (analyzer instanceof NumericAnalyzer) {
        final NumericAnalyzer na = (NumericAnalyzer) analyzer;
        final char[] text = fieldNode.getTextAsString().toCharArray();
        final ReusableCharArrayReader textReader = new ReusableCharArrayReader(text);
        final Number number;
        try {
          number = na.getNumericParser().parse(textReader);
        } catch (final Exception e) {
          throw new QueryNodeParseException(new MessageImpl(QueryParserMessages.COULD_NOT_PARSE_NUMBER, text), e);
        }

        final CharSequence field = fieldNode.getField();
        final NodeNumericQueryNode lowerNode = new NodeNumericQueryNode(field, number);
        final NodeNumericQueryNode upperNode = new NodeNumericQueryNode(field, number);

        return new NodeNumericRangeQueryNode(lowerNode, upperNode, true, true, na);
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
