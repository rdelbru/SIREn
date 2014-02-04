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

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.SlopQueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.nodes.MultiPhraseQueryNode;
import org.apache.lucene.queryparser.flexible.standard.parser.EscapeQuerySyntaxImpl;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.builders.MatchAllDocsQueryNodeBuilder;
import org.sindice.siren.search.node.NodePhraseQuery;

/**
 * This processor throws an exception if it encounters a {@link Query}
 * that is not supported in SIREn.
 *
 * <p>
 *
 * Such queries are:
 * <ul>
 * <li>{@link NodePhraseQuery} with slop different to 0</li>
 * <li>{@link MultiPhraseQuery}</li>
 * <li>{@link MatchAllDocsQuery}</li>
 * </ul>
 */
public class NotSupportedQueryProcessor
extends QueryNodeProcessorImpl {

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof SlopQueryNode && ((SlopQueryNode) node).getValue() != 0) {
      throw new QueryNodeException(new MessageImpl("Slop queries are not supported",
        node.toQueryString(new EscapeQuerySyntaxImpl())));
    } else if (node instanceof MultiPhraseQueryNode) {
      throw new QueryNodeException(new MessageImpl("Multi phrase queries are not supported",
        node.toQueryString(new EscapeQuerySyntaxImpl())));
    } else if (node instanceof MatchAllDocsQueryNodeBuilder) {
      throw new QueryNodeException(new MessageImpl("MatchAllDocsQueries are not supported",
        node.toQueryString(new EscapeQuerySyntaxImpl())));
    }
    return node;
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node)
  throws QueryNodeException {
    return node;
  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
  throws QueryNodeException {
    return children;
  }

}
