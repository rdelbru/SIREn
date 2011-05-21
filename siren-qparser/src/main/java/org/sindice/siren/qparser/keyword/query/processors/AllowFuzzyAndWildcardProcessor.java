/**
 * Copyright 2010, 2011, Renaud Delbru
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
 * and throws an exception if it found any of them. <br/>
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
