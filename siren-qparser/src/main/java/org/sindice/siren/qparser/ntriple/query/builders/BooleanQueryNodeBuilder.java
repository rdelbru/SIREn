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
