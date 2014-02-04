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
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.parser.EscapeQuerySyntaxImpl;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;

/**
 * This processor checks if the {@link KeywordConfigurationKeys#ALLOW_TWIG}
 * configuration is satisfied.
 *
 * <p>
 *
 * This processor verifies if the configuration key
 * {@link KeywordConfigurationKeys#ALLOW_TWIG} is defined in the
 * {@link KeywordQueryConfigHandler}. If it is and twig is not allowed,
 * it looks for every {@link TwigQueryNode} contained in the query node tree
 * and throws an exception if it finds any.
 *
 * @see KeywordConfigurationKeys#ALLOW_TWIG
 */
public class AllowTwigProcessor
extends QueryNodeProcessorImpl {

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof TwigQueryNode) {
      if (this.getQueryConfigHandler().has(KeywordConfigurationKeys.ALLOW_TWIG)) {
        if (!this.getQueryConfigHandler().get(KeywordConfigurationKeys.ALLOW_TWIG)) {
          throw new QueryNodeException(new MessageImpl("TwigQuery not allowed", node
            .toQueryString(new EscapeQuerySyntaxImpl())));
        }
      } else {
        throw new IllegalArgumentException("KeywordConfigurationKeys.ALLOW_TWIG should be set on the KeywordQueryConfigHandler");
      }
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
