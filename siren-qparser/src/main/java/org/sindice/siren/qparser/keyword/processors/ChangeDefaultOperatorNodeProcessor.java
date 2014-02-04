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
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator;

/**
 * This processor change the default operator if a unary operator +
 * ({@link Modifier#MOD_REQ}) is found.
 *
 * <p>
 *
 * This is needed to correctly implement the query logic with unary operators
 * (SRN-106).
 */
public class ChangeDefaultOperatorNodeProcessor
extends QueryNodeProcessorImpl {

  boolean hasUnaryReqOperator = false;

  public ChangeDefaultOperatorNodeProcessor() {}

  @Override
  protected QueryNode postProcessNode(final QueryNode node) throws QueryNodeException {

    if (node.getParent() == null) { // node is root, we processed the tree
      if (hasUnaryReqOperator) { // we found a req modifier in the tree

        final QueryConfigHandler conf = this.getQueryConfigHandler();
        if (!conf.has(ConfigurationKeys.DEFAULT_OPERATOR)) {
          throw new IllegalArgumentException(
              "ConfigurationKeys.DEFAULT_OPERATOR should be set on the QueryConfigHandler");
        }
        conf.set(ConfigurationKeys.DEFAULT_OPERATOR, Operator.OR);
      }
    }
    return node;

  }

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {

    if (node instanceof ModifierQueryNode) {
      final Modifier mod = ((ModifierQueryNode) node).getModifier();
      if (mod == Modifier.MOD_REQ) {
        this.hasUnaryReqOperator = true;
      }
    }

    return node;

  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
  throws QueryNodeException {

    return children;

  }


}
