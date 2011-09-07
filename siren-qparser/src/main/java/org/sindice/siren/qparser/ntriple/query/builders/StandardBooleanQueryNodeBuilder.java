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

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Similarity;
import org.sindice.siren.search.SirenPrimitiveQuery;

/**
 * This builder does the same as the {@link BooleanQueryNodeBuilder}, but this
 * considers if the built {@link BooleanQuery} should have its coord disabled or
 * not. <br/>
 *
 * @see BooleanQueryNodeBuilder
 * @see BooleanQuery
 * @see Similarity#coord(int, int)
 */
public class StandardBooleanQueryNodeBuilder implements ResourceQueryBuilder {

  public StandardBooleanQueryNodeBuilder() {
    // empty constructor
  }

  public SirenPrimitiveQuery build(final QueryNode queryNode)
  throws QueryNodeException {
    final BooleanQueryNodeBuilder bqNodeBuilder = new BooleanQueryNodeBuilder();

    return bqNodeBuilder.build(queryNode);
  }

}
