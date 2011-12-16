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
 * @author Renaud Delbru [ 21 May 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010, 2011, by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword.query.processors;

import java.util.List;

import org.apache.lucene.messages.MessageImpl;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.config.QueryConfigHandler;
import org.apache.lucene.queryParser.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryParser.standard.nodes.WildcardQueryNode;
import org.apache.lucene.queryParser.standard.parser.EscapeQuerySyntaxImpl;
import org.sindice.siren.qparser.keyword.config.AllowFuzzyAndWildcardAttribute;

/**
 * This processor verifies if the attribute
 * {@link AllowFuzzyAndWildcardAttribute} is defined in the
 * {@link QueryConfigHandler}. If it is and fuzzy and wildcard are not allowed,
 * it looks for every {@link FuzzyQueryNode} or {@link WildcardQueryNode}
 * contained in the query node tree
 * and throws an exception if it founds any of them. <br/>
 *
 * @see AllowFuzzyAndWildcardAttribute
 */
public class AllowFuzzyAndWildcardProcessor
extends QueryNodeProcessorImpl {

  public AllowFuzzyAndWildcardProcessor() {
    // empty constructor
  }

  @Override
  public QueryNode process(final QueryNode queryTree) throws QueryNodeException {

    if (this.getQueryConfigHandler().hasAttribute(AllowFuzzyAndWildcardAttribute.class)) {

      final AllowFuzzyAndWildcardAttribute alwAttr= this.getQueryConfigHandler().getAttribute(AllowFuzzyAndWildcardAttribute.class);
      if (!alwAttr.isAllowFuzzyAndWildcard()) {
        return super.process(queryTree);
      }

    }

    return queryTree;
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node) throws QueryNodeException {

    if (node instanceof WildcardQueryNode) {
      throw new QueryNodeException(new MessageImpl("Wildcard not allowed", node
              .toQueryString(new EscapeQuerySyntaxImpl())));
    }


    if (node instanceof FuzzyQueryNode) {
      throw new QueryNodeException(new MessageImpl("Fuzzy not allowed", node
              .toQueryString(new EscapeQuerySyntaxImpl())));
    }

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
