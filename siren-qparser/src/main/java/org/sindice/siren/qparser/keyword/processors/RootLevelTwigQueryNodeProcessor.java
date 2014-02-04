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
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;

/**
 * This processor sets the level of the root of the {@link TwigQueryNode}.
 *
 * @see TwigQueryNode#setRootLevel(int)
 */
public class RootLevelTwigQueryNodeProcessor
extends QueryNodeProcessorImpl {

  private final int DEFAULT_ROOT = 1;

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof TwigQueryNode) {
      final int root;
      if (this.getQueryConfigHandler().has(KeywordConfigurationKeys.ROOT_LEVEL)) {
        root = this.getQueryConfigHandler().get(KeywordConfigurationKeys.ROOT_LEVEL);
      } else {
        root = DEFAULT_ROOT;
      }
      // Set the ROOT level of the query
      /*
       * When adding nested TwigQueries, their root level is set to the current
       * Twig level + 1. See {@link TwigQuery#addChild}.
       */
      final TwigQueryNode twigNode = (TwigQueryNode) node;
      twigNode.setRootLevel(root);
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
