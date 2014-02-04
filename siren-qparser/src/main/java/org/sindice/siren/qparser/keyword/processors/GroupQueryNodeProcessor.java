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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.AndQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.BooleanQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.GroupQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier;
import org.apache.lucene.queryparser.flexible.core.nodes.OrQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.parser.SyntaxParser;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator;
import org.apache.lucene.queryparser.flexible.standard.nodes.BooleanModifierNode;
import org.sindice.siren.qparser.keyword.nodes.ArrayQueryNode;
import org.sindice.siren.qparser.keyword.nodes.DatatypeQueryNode;
import org.sindice.siren.qparser.keyword.nodes.TopLevelQueryNode;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;
import org.sindice.siren.qparser.keyword.nodes.WildcardNodeQueryNode;

/**
 * The {@link SyntaxParser} generates query node trees that consider the boolean
 * operator precedence, but Lucene current syntax does not support boolean
 * precedence, so this processor remove all the precedence and apply the
 * equivalent modifier according to the boolean operation defined on an specific
 * query node.
 *
 * <p>
 *
 * The original {@link org.apache.lucene.queryparser.flexible.standard.processors.GroupQueryNodeProcessor}
 * was not supporting correctly nested groups. This processor interprets a
 * {@link GroupQueryNode} and merges it with the node above it.
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
    if (!this.getQueryConfigHandler().has(ConfigurationKeys.DEFAULT_OPERATOR)) {
      throw new IllegalArgumentException(
          "DEFAULT_OPERATOR should be set on the QueryConfigHandler");
    }

    this.usingAnd = Operator.AND == this.getQueryConfigHandler()
    .get(ConfigurationKeys.DEFAULT_OPERATOR) ? true : false;

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
    else if (node instanceof TwigQueryNode) {
      final ArrayList<QueryNode> actualQueryNodeList = this.queryNodeList;
      final TwigQueryNode twigNode = (TwigQueryNode) node;
      final QueryNode root = twigNode.getRoot();
      final QueryNode child = twigNode.getChild();
      if (!(root instanceof WildcardNodeQueryNode)) { // the root is not empty
        twigNode.setRoot(this.process(root));
      }
      if (!(child instanceof WildcardNodeQueryNode)) { // the child is not empty
        twigNode.setChild(this.process(child));
      }
      actualQueryNodeList.add(twigNode);
      this.queryNodeList = actualQueryNodeList;
      this.latestNodeVerified = false;
    }
    else if (node instanceof ArrayQueryNode) {
      final ArrayList<QueryNode> actualQueryNodeList = this.queryNodeList;
      final ArrayQueryNode arrayNode = (ArrayQueryNode) node;
      final List<QueryNode> children = arrayNode.getChildren();
      final List<QueryNode> newChildren = new ArrayList<QueryNode>();
      for (final QueryNode child : children) {
        // The unary modifier sets the occurrence of this value in the TwigQuery
        if (!(child instanceof ModifierQueryNode)) {
          newChildren.add(this.process(child));
        } else {
          newChildren.add(child);
        }
      }
      arrayNode.set(newChildren);
      actualQueryNodeList.add(arrayNode);
      this.queryNodeList = actualQueryNodeList;
      this.latestNodeVerified = false;
    }
    else if (node instanceof TopLevelQueryNode) {
      final ArrayList<QueryNode> actualQueryNodeList = this.queryNodeList;
      final TopLevelQueryNode topNode = (TopLevelQueryNode) node;
      final QueryNode child = topNode.getChildren().get(0);
      topNode.set(Arrays.asList(this.process(child)));
      actualQueryNodeList.add(topNode);
      this.queryNodeList = actualQueryNodeList;
      this.latestNodeVerified = false;
    }
    else if (node instanceof DatatypeQueryNode) {
      final ArrayList<QueryNode> actualQueryNodeList = this.queryNodeList;
      final DatatypeQueryNode dtNode = (DatatypeQueryNode) node;
      final QueryNode child = dtNode.getChild();
      dtNode.set(Arrays.asList(this.applyModifier(this.process(child), node.getParent())));
      actualQueryNodeList.add(dtNode);
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
