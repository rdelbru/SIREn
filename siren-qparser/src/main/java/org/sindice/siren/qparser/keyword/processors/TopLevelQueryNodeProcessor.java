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

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.BooleanQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessor;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.TopLevelQueryNode;

/**
 * This processor removes the {@link TopLevelQueryNode} and returns its
 * child wrapped in a {@link BooleanQueryNode}, in the case where
 * {@link KeywordConfigurationKeys#ALLOW_TWIG} is <code>false</code>; otherwise,
 * it is left unchanged.
 */
public class TopLevelQueryNodeProcessor
implements QueryNodeProcessor {

  private QueryConfigHandler queryConfig;

  @Override
  public QueryNode process(final QueryNode queryTree)
  throws QueryNodeException {
    final TopLevelQueryNode top = (TopLevelQueryNode) queryTree;

    if (this.getQueryConfigHandler().has(KeywordConfigurationKeys.ALLOW_TWIG)) {
      if (!this.getQueryConfigHandler().get(KeywordConfigurationKeys.ALLOW_TWIG)) {
        // Wraps the children into a BooleanQueryNode, so that the parent
        // pointers are correct.
        // This relies on the BooleanSingleChildOptimizationQueryNodeProcessor
        return new BooleanQueryNode(top.getChildren());
      }
    } else {
      throw new IllegalArgumentException("KeywordConfigurationKeys.ALLOW_TWIG should be set on the KeywordQueryConfigHandler");
    }
    return queryTree;
  }

  /**
   * For reference about this method check:
   * {@link QueryNodeProcessor#setQueryConfigHandler(QueryConfigHandler)}.
   *
   * @param queryConfigHandler
   *          the query configuration handler to be set.
   *
   * @see QueryNodeProcessor#getQueryConfigHandler()
   * @see QueryConfigHandler
   */
  public void setQueryConfigHandler(final QueryConfigHandler queryConfigHandler) {
    this.queryConfig = queryConfigHandler;
  }

  /**
   * For reference about this method check:
   * {@link QueryNodeProcessor#getQueryConfigHandler()}.
   *
   * @return QueryConfigHandler the query configuration handler to be set.
   *
   * @see QueryNodeProcessor#setQueryConfigHandler(QueryConfigHandler)
   * @see QueryConfigHandler
   */
  public QueryConfigHandler getQueryConfigHandler() {
    return this.queryConfig;
  }

}
