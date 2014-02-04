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
package org.sindice.siren.qparser.keyword.nodes;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.flexible.core.nodes.FieldableNode;
import org.apache.lucene.queryparser.flexible.core.nodes.GroupQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNodeImpl;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.sindice.siren.search.node.NodeQuery;
import org.sindice.siren.search.node.TwigQuery;

/**
 * A {@link TwigQueryNode} represents a structured query, i.e., a {@link TwigQuery}.
 *
 * <p>
 *
 * It is composed of two nodes, a root and of a direct child.
 *
 * <p>
 *
 * A node with multiple children can be built by passing an
 * {@link ArrayQueryNode} to the {@link #setChild(QueryNode)}.
 *
 * <p>
 *
 * A complex twig can be built by chaining multiple {@link TwigQueryNode}.
 */
public class TwigQueryNode
extends QueryNodeImpl
implements FieldableNode {

  /** The root of the Twig Query */
  private int rootLevel = -1;
  /** Position in the list of the root of the twig */
  private static final int ROOT_POS  = 0;
  /** Position in the list of the child of the twig */
  private static final int CHILD_POS = 1;

  /**
   * Build a {@link TwigQueryNode} where the root can be either
   * an {@link WildcardNodeQueryNode} or a {@link NodeBooleanQueryNode}.
   * In addition to the root, the child node can be an {@link ArrayQueryNode}
   * or another {@link TwigQueryNode}.
   *
   * @param root the {@link QueryNode} as the root of the twig
   * @param child the {@link QueryNode} as the child of the twig
   */
  public TwigQueryNode(final QueryNode root, final QueryNode child) {
    this.allocate();
    this.setLeaf(false);
    this.add(root);
    this.add(child);
  }

  /**
   * Set the root of the twig
   */
  public void setRoot(final QueryNode root) {
    final ArrayList<QueryNode> newChildren = new ArrayList<QueryNode>();
    newChildren.add(root);
    newChildren.add(this.getChild());
    this.set(newChildren);
  }

  /**
   * Returns the root of the twig.
   */
  public QueryNode getRoot() {
    return this.getChildren().get(ROOT_POS);
  }

  /**
   * Set the child of the twig.
   */
  public void setChild(final QueryNode child) {
    final ArrayList<QueryNode> newChildren = new ArrayList<QueryNode>();
    newChildren.add(this.getRoot());
    newChildren.add(child);
    this.set(newChildren);
  }

  /**
   * Returns the child of the twig.
   */
  public QueryNode getChild() {
    return this.getChildren().get(CHILD_POS);
  }

  /**
   * Set the level of the root node in the document tree.
   * @see NodeQuery#setLevelConstraint(int)
   */
  public void setRootLevel(final int rootLevel) {
    this.rootLevel = rootLevel;
  }

  /**
   * Get the level of the root node in the document tree.
   * 
   * <p>
   * 
   * If no root level has been set, <code>-1</code> is returned.
   * @see NodeQuery#setLevelConstraint(int)
   */
  public int getRootLevel() {
    return rootLevel;
  }

  @Override
  public CharSequence getField() {
    return this.doGetField(this.getChildren());
  }

  private CharSequence doGetField(final List<QueryNode> children) {
    if (children != null) {
      for (final QueryNode child : children) {
        if (child instanceof FieldableNode) {
          return ((FieldableNode) child).getField();
        } else if (child instanceof TwigQueryNode) {
          return ((TwigQueryNode) child).getField();
        }
        final CharSequence field = this.doGetField(child.getChildren());
        if (field != null) {
          return field;
        }
      }
    }
    return null;
  }

  @Override
  public void setField(final CharSequence fieldName) {
    this.doSetField(this.getChildren(), fieldName);
  }

  private void doSetField(final List<QueryNode> children, final CharSequence fieldName) {
    if (children != null) {
      for (final QueryNode child : children) {
        this.doSetField(child.getChildren(), fieldName);
        if (child instanceof FieldableNode) {
          ((FieldableNode) child).setField(fieldName);
        } else if (child instanceof TwigQueryNode) {
          ((TwigQueryNode) child).setField(fieldName);
        }
      }
    }
  }

  @Override
  public String toString() {
    final QueryNode att = this.getRoot();
    final QueryNode child = this.getChild();
    final String s = "<twig root=\"" + this.getRootLevel() + "\">\n" +
                 "<root>\n" + (att instanceof WildcardNodeQueryNode ? "" : att + "\n") + "</root>\n" +
                 "<child>\n" + (child instanceof WildcardNodeQueryNode ? "" : child + "\n") + "</child>\n" +
               "</twig>";
    return s;
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escapeSyntaxParser) {
    if (this.getChildren() == null || this.getChildren().size() == 0)
      return "";

    final StringBuilder sb = new StringBuilder();
    final QueryNode root = this.getRoot();
    final QueryNode child = this.getChild();
    if (root instanceof WildcardNodeQueryNode) {
      sb.append("* : ");
    } else {
      sb.append(root.toQueryString(escapeSyntaxParser)).append(" : ");
    }
    if (child instanceof WildcardNodeQueryNode) {
      sb.append("*");
    } else {
      sb.append(child.toQueryString(escapeSyntaxParser));
    }

    // in case is root or the parent is a group node avoid parenthesis
    if ((this.getParent() != null && this.getParent() instanceof GroupQueryNode) || this.isRoot()) {
      return sb.toString();
    } else {
      return "( " + sb.toString() + " )";
    }
  }

}
