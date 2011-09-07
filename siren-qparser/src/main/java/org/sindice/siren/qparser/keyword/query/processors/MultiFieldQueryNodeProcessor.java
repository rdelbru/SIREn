/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 21 May 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010, 2011, by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword.query.processors;

import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.config.QueryConfigHandler;
import org.apache.lucene.queryParser.core.nodes.BooleanQueryNode;
import org.apache.lucene.queryParser.core.nodes.FieldableNode;
import org.apache.lucene.queryParser.core.nodes.GroupQueryNode;
import org.apache.lucene.queryParser.core.nodes.OrQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryParser.standard.config.MultiFieldAttribute;

/**
 * This processor is used to expand terms so the query looks for the same term
 * in different fields. It also boosts a query based on its field. <br/>
 * <br/>
 * This processor looks for every {@link FieldableNode} contained in the query
 * node tree. If a {@link FieldableNode} is found, it checks if there is a
 * {@link MultiFieldAttribute} defined in the {@link QueryConfigHandler}. If
 * there is, the {@link FieldableNode} is cloned N times and the clones are
 * added to a {@link BooleanQueryNode} together with the original node. N is
 * defined by the number of fields that it will be expanded to. The
 * {@link BooleanQueryNode} is returned. <br/>
 * <br/>
 * Extended version of the original
 * {@link org.apache.lucene.queryParser.standard.processors.MultiFieldQueryNodeProcessor}
 * to expand query using OR operator.
 *
 * @see MultiFieldAttribute
 */
public class MultiFieldQueryNodeProcessor extends QueryNodeProcessorImpl {

  private boolean processChildren = true;

  public MultiFieldQueryNodeProcessor() {
    // empty constructor
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node) throws QueryNodeException {

    return node;

  }

  @Override
  protected void processChildren(final QueryNode queryTree) throws QueryNodeException {

    if (this.processChildren) {
      super.processChildren(queryTree);

    } else {
      this.processChildren = true;
    }

  }

  @Override
  protected QueryNode preProcessNode(final QueryNode node) throws QueryNodeException {

    if (node instanceof FieldableNode) {
      this.processChildren = false;
      FieldableNode fieldNode = (FieldableNode) node;

      if (fieldNode.getField() == null) {

        if (!this.getQueryConfigHandler().hasAttribute(MultiFieldAttribute.class)) {
          throw new IllegalArgumentException(
              "MultiFieldAttribute should be set on the QueryConfigHandler");
        }

        final CharSequence[] fields = this.getQueryConfigHandler().getAttribute(
            MultiFieldAttribute.class).getFields();

        if (fields != null && fields.length > 0) {
          fieldNode.setField(fields[0]);

          if (fields.length == 1) {
            return fieldNode;

          } else {
            final LinkedList<QueryNode> children = new LinkedList<QueryNode>();
            children.add(fieldNode);

            for (int i = 1; i < fields.length; i++) {
              try {
                fieldNode = (FieldableNode) fieldNode.cloneTree();
                fieldNode.setField(fields[i]);

                children.add(fieldNode);

              } catch (final CloneNotSupportedException e) {
                // should never happen
              }

            }

            return new GroupQueryNode(new OrQueryNode(children));

          }

        }

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
