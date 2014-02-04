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
import org.apache.lucene.queryparser.flexible.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.nodes.WildcardQueryNode;
import org.apache.lucene.queryparser.flexible.standard.parser.EscapeQuerySyntaxImpl;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;

/**
 * This processor checks if the {@link KeywordConfigurationKeys#ALLOW_FUZZY_AND_WILDCARD}
 * configuration is satisfied.
 *
 * <p>
 *
 * This processor verifies if the configuration key
 * {@link KeywordConfigurationKeys#ALLOW_FUZZY_AND_WILDCARD} is defined in the
 * {@link KeywordQueryConfigHandler}. If it is and if fuzzy and wildcard are not
 * allowed, it looks for every {@link FuzzyQueryNode} or
 * {@link WildcardQueryNode} contained in the query node tree and throws an
 * exception if it finds any of them.
 *
 * @see KeywordConfigurationKeys#ALLOW_FUZZY_AND_WILDCARD
 */
public class AllowFuzzyAndWildcardProcessor
extends QueryNodeProcessorImpl {

  public AllowFuzzyAndWildcardProcessor() {
    // empty constructor
  }

  @Override
  public QueryNode process(final QueryNode queryTree) throws QueryNodeException {

    if (this.getQueryConfigHandler().has(KeywordConfigurationKeys.ALLOW_FUZZY_AND_WILDCARD)) {
      if (!this.getQueryConfigHandler().get(KeywordConfigurationKeys.ALLOW_FUZZY_AND_WILDCARD)) {
        return super.process(queryTree);
      }
    }
    return queryTree;
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node) throws QueryNodeException {

    if (node instanceof WildcardQueryNode) {
      throw new QueryNodeException(new MessageImpl("Wildcard not allowed", node
              .toQueryString(new EscapeQuerySyntaxImpl())));
    }


    if (node instanceof FuzzyQueryNode) {
      throw new QueryNodeException(new MessageImpl("Fuzzy not allowed", node
              .toQueryString(new EscapeQuerySyntaxImpl())));
    }

    return node;

  }

  @Override
  protected QueryNode preProcessNode(final QueryNode node) throws QueryNodeException {

    return node;

  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
      throws QueryNodeException {

    return children;

  }

}
