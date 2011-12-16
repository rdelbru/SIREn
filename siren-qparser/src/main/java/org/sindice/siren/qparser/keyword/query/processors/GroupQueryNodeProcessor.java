/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 23 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword.query.processors;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.config.QueryConfigHandler;
import org.apache.lucene.queryParser.core.nodes.AndQueryNode;
import org.apache.lucene.queryParser.core.nodes.BooleanQueryNode;
import org.apache.lucene.queryParser.core.nodes.GroupQueryNode;
import org.apache.lucene.queryParser.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryParser.core.nodes.ModifierQueryNode.Modifier;
import org.apache.lucene.queryParser.core.nodes.OrQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.parser.SyntaxParser;
import org.apache.lucene.queryParser.core.processors.QueryNodeProcessor;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute.Operator;
import org.apache.lucene.queryParser.standard.nodes.BooleanModifierNode;

/**
 * The {@link SyntaxParser}
 * generates query node trees that consider the boolean operator precedence, but
 * Lucene current syntax does not support boolean precedence, so this processor
 * remove all the precedence and apply the equivalent modifier according to the
 * boolean operation defined on an specific query node. <br/>
 * <br/>
 * The original {@link GroupQueryNodeProcessor} was not supporting correctly
 * nested groups. This processor interprets a {@link GroupQueryNode} and merges
 * it with the node above it.
 */
public class GroupQueryNodeProcessor implements QueryNodeProcessor {

  private ArrayList<QueryNode> queryNodeList;

  private boolean latestNodeVerified;

  private QueryConfigHandler queryConfig;

  private Boolean usingAnd = false;

  public GroupQueryNodeProcessor() {
    // empty constructor
  }

  public QueryNode process(QueryNode queryTree) throws QueryNodeException {

    if (!this.getQueryConfigHandler().hasAttribute(DefaultOperatorAttribute.class)) {
      throw new IllegalArgumentException(
          "DefaultOperatorAttribute should be set on the QueryConfigHandler");
    }

    this.usingAnd = Operator.AND == this.getQueryConfigHandler()
        .getAttribute(DefaultOperatorAttribute.class).getOperator() ? true : false;

    if (queryTree instanceof GroupQueryNode) {
      queryTree = ((GroupQueryNode) queryTree).getChild();
    }

    this.queryNodeList = new ArrayList<QueryNode>();
    this.latestNodeVerified = false;
    this.readTree(queryTree);

    if (queryTree instanceof BooleanQueryNode) {
      queryTree.set(this.queryNodeList);
      return queryTree;
    }
    else {
      return new BooleanQueryNode(this.queryNodeList);
    }

  }

  /**
   */
  private QueryNode applyModifier(final QueryNode node, final QueryNode parent) {

    if (this.usingAnd) {

      if (parent instanceof OrQueryNode) {

        if (node instanceof ModifierQueryNode) {

          final ModifierQueryNode modNode = (ModifierQueryNode) node;

          if (modNode.getModifier() == Modifier.MOD_REQ) {
            return modNode.getChild();
          }

        }

      } else {

        if (node instanceof ModifierQueryNode) {

          final ModifierQueryNode modNode = (ModifierQueryNode) node;

          if (modNode.getModifier() == Modifier.MOD_NONE) {
            return new BooleanModifierNode(modNode.getChild(), Modifier.MOD_REQ);
          }

        } else {
          return new BooleanModifierNode(node, Modifier.MOD_REQ);
        }

      }

    } else {

      if (parent instanceof AndQueryNode) {

        if (node instanceof ModifierQueryNode) {

          final ModifierQueryNode modNode = (ModifierQueryNode) node;

          if (modNode.getModifier() == Modifier.MOD_NONE) {
            return new BooleanModifierNode(modNode.getChild(), Modifier.MOD_REQ);
          }

        } else {
          return new BooleanModifierNode(node, Modifier.MOD_REQ);
        }

      }

    }

    return node;

  }

  private void readTree(final QueryNode node) throws QueryNodeException {

    if (node instanceof BooleanQueryNode) {
      final List<QueryNode> children = node.getChildren();

      if (children != null && children.size() > 0) {

        for (int i = 0; i < children.size() - 1; i++) {
          this.readTree(children.get(i));
        }

        this.processNode(node);
        this.readTree(children.get(children.size() - 1));

      } else {
        this.processNode(node);
      }

    } else {
      this.processNode(node);
    }

  }

  private void processNode(final QueryNode node) throws QueryNodeException {

    if (node instanceof AndQueryNode || node instanceof OrQueryNode) {

      if (!this.latestNodeVerified && !this.queryNodeList.isEmpty()) {
        this.queryNodeList.add(this.applyModifier(this.queryNodeList
            .remove(this.queryNodeList.size() - 1), node));
        this.latestNodeVerified = true;
      }

    }
    else if (node instanceof GroupQueryNode) {
      final ArrayList<QueryNode> actualQueryNodeList = this.queryNodeList;
      actualQueryNodeList.add(this.applyModifier(this.process(node), node.getParent()));
      this.queryNodeList = actualQueryNodeList;
      this.latestNodeVerified = false;
    }
    else if (!(node instanceof BooleanQueryNode)) {
      this.queryNodeList.add(this.applyModifier(node, node.getParent()));
      this.latestNodeVerified = false;

    }

  }

  public QueryConfigHandler getQueryConfigHandler() {
    return this.queryConfig;
  }

  public void setQueryConfigHandler(final QueryConfigHandler queryConfigHandler) {
    this.queryConfig = queryConfigHandler;
  }

}
