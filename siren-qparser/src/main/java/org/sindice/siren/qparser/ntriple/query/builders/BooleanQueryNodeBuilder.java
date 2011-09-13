/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 21 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.ntriple.query.builders;

import java.util.List;

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryParser.core.nodes.BooleanQueryNode;
import org.apache.lucene.queryParser.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.sindice.siren.search.SirenBooleanClause;
import org.sindice.siren.search.SirenBooleanQuery;
import org.sindice.siren.search.SirenPrimitiveQuery;

/**
 * Builds a {@link BooleanQuery} object from a {@link BooleanQueryNode} object.
 * Every children in the {@link BooleanQueryNode} object must be already tagged
 * using {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} with a {@link Query}
 * object. <br/>
 * <br/>
 * It takes in consideration if the children is a {@link ModifierQueryNode} to
 * define the {@link BooleanClause}.
 */
public class BooleanQueryNodeBuilder implements ResourceQueryBuilder {

  public BooleanQueryNodeBuilder() {
    // empty constructor
  }

  public SirenPrimitiveQuery build(final QueryNode queryNode)
  throws QueryNodeException {

    final BooleanQueryNode booleanNode = (BooleanQueryNode) queryNode;
    final List<QueryNode> children = booleanNode.getChildren();
    final SirenBooleanQuery bq = new SirenBooleanQuery();

    if (children == null) {
      return bq; // return empty boolean query
    }

    // If more than one child, wrap them into a SirenBooleanQuery
    if (children.size() > 1) {

      for (final QueryNode child : children) {
        final Object obj = child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
        if (obj != null) {
          if (obj instanceof SirenPrimitiveQuery) {
            bq.add((SirenPrimitiveQuery) obj,
              BooleanQueryNodeBuilder.getModifierValue(child));
          }
          else {
            throw new QueryNodeException(new Error("Non primitive query '" +
            	obj.getClass().getCanonicalName() + "' received in cell query"));
          }
        }
      }
      return bq;
    }
    // If only one child, return it directly
    else {
      final Object obj = children.get(0).getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
      if (obj != null) {
        if (obj instanceof SirenPrimitiveQuery) {
          return (SirenPrimitiveQuery) obj;
        }
        else {
          throw new QueryNodeException(new Error("Non primitive query '" +
            obj.getClass().getCanonicalName() + "' received in cell query"));
        }
      }
      return bq; // return empty boolean query
    }
  }

  private static SirenBooleanClause.Occur getModifierValue(final QueryNode node)
      throws QueryNodeException {

    if (node instanceof ModifierQueryNode) {
      final ModifierQueryNode mNode = ((ModifierQueryNode) node);
      switch (mNode.getModifier()) {

      case MOD_REQ:
        return SirenBooleanClause.Occur.MUST;

      case MOD_NOT:
        return SirenBooleanClause.Occur.MUST_NOT;

      case MOD_NONE:
        return SirenBooleanClause.Occur.SHOULD;

      }

    }

    return SirenBooleanClause.Occur.SHOULD;

  }

}
