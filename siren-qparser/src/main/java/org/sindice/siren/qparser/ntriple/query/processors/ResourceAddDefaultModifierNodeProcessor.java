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
package org.sindice.siren.qparser.ntriple.query.processors;

import java.util.List;

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.nodes.GroupQueryNode;
import org.apache.lucene.queryParser.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryParser.core.nodes.ModifierQueryNode.Modifier;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute.Operator;

/**
 * This processor wraps the root node into a {@link ModifierQueryNode} and a
 * {@link GroupQueryNode}. The modifier is based on the default query parser
 * operators.<br>
 * This is needed to avoid problem during query rewriting optimisation. This
 * case is happening when using a single term without modifiers, and when this
 * term gets expanded. For example, the term 'café' gets expanded into
 * 'café cafe', which is correct. However, during processing, it gets rewritten
 * into '+café +cafe' if the default operator is AND, which is incorrect as it
 * should be instead +(café cafe).
 */
public class ResourceAddDefaultModifierNodeProcessor
extends QueryNodeProcessorImpl {

  public ResourceAddDefaultModifierNodeProcessor() {
    // empty constructor
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node) throws QueryNodeException {

    if (!this.getQueryConfigHandler().hasAttribute(DefaultOperatorAttribute.class)) {
      throw new IllegalArgumentException(
          "DefaultOperatorAttribute should be set on the QueryConfigHandler");
    }

    final Operator defaultOp = this.getQueryConfigHandler().getAttribute(DefaultOperatorAttribute.class).getOperator();
    Modifier mod;
    if (Operator.AND == defaultOp) {
      mod = Modifier.MOD_REQ;
    }
    else {
      mod = Modifier.MOD_NONE;
    }
    new ModifierQueryNode(new GroupQueryNode(node), mod);

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
