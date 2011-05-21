/**
 * Copyright 2010, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 23 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword.query.processors;

import java.util.List;

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.config.QueryConfigHandler;
import org.apache.lucene.queryParser.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryParser.core.nodes.ModifierQueryNode.Modifier;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute.Operator;

/**
 * This processor change the default operator if a unary operator +
 * ({@link Modifier.MOD_REQ}) is found.<br>
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
        if (!conf.hasAttribute(DefaultOperatorAttribute.class)) {
          throw new IllegalArgumentException(
              "DefaultOperatorAttribute should be set on the QueryConfigHandler");
        }

        final DefaultOperatorAttribute opAttr = conf.getAttribute(DefaultOperatorAttribute.class);

        opAttr.setOperator(Operator.OR);
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
