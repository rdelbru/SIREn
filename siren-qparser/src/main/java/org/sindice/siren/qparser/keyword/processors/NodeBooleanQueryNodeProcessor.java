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
import org.apache.lucene.queryparser.flexible.core.nodes.BooleanQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.NodeBooleanQueryNode;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;

/**
 * This processor converts a {@link BooleanQueryNode} into a {@link NodeBooleanQueryNode}.
 * If {@link KeywordConfigurationKeys#ALLOW_TWIG} is <code>true</code>, only
 * those within a {@link TwigQueryNode} are converted.
 *
 * @see NodeBooleanQueryNode
 * @see KeywordConfigurationKeys#ALLOW_TWIG
 */
public class NodeBooleanQueryNodeProcessor
extends QueryNodeProcessorImpl {

  /**
   * Number of twigs
   */
  private int nbTwigs = 0;

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof TwigQueryNode) {
      nbTwigs++;
    } else if (node instanceof BooleanQueryNode) {
      /*
       * - if twig is allowed, convert the boolean querynode only if within a twig querynode
       * - if twig is not allowed, convert ALL the boolean querynodes
       */
      if (this.getQueryConfigHandler().has(KeywordConfigurationKeys.ALLOW_TWIG)) {
        if (this.getQueryConfigHandler().get(KeywordConfigurationKeys.ALLOW_TWIG) &&
            nbTwigs == 0) {
          return node;
        }
      } else {
        throw new IllegalArgumentException("KeywordConfigurationKeys.ALLOW_TWIG should be set on the KeywordQueryConfigHandler");
      }
      return new NodeBooleanQueryNode((BooleanQueryNode) node);
    }
    return node;
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof TwigQueryNode) {
      nbTwigs--;
      assert nbTwigs >= 0;
    }
    return node;
  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
  throws QueryNodeException {
    return children;
  }

}
