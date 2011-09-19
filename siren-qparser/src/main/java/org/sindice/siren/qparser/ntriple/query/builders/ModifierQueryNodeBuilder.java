/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
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
package org.sindice.siren.qparser.ntriple.query.builders;

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryParser.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.search.Query;

/**
 * Builds no object, it only returns the {@link Query} object set on the
 * {@link ModifierQueryNode} object using a
 * {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} tag.
 */
public class ModifierQueryNodeBuilder implements ResourceQueryBuilder {

  public ModifierQueryNodeBuilder() {
    // empty constructor
  }

  public Query build(QueryNode queryNode) throws QueryNodeException {
    ModifierQueryNode modifierNode = (ModifierQueryNode) queryNode;

    return (Query) (modifierNode).getChild().getTag(
        QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);

  }

}
