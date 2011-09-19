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

import org.apache.commons.lang.NotImplementedException;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.standard.nodes.WildcardQueryNode;
import org.apache.lucene.search.WildcardQuery;

/**
 * Builds a {@link WildcardQuery} object from a {@link WildcardQueryNode}
 * object.
 */
public class WildcardQueryNodeBuilder implements ResourceQueryBuilder {

  public WildcardQueryNodeBuilder() {
    // empty constructor
  }

  public WildcardQuery build(QueryNode queryNode) throws QueryNodeException {
    throw new NotImplementedException("Wildcard query is not supported yet");
    
//TODO: To implement when siren will support wildcard query
//    WildcardQueryNode wildcardNode = (WildcardQueryNode) queryNode;
//
//    WildcardQuery q = new WildcardQuery(new Term(wildcardNode.getFieldAsString(),
//                                                 wildcardNode.getTextAsString()));
//    
//    MultiTermQuery.RewriteMethod method = (MultiTermQuery.RewriteMethod)queryNode.getTag(MultiTermRewriteMethodAttribute.TAG_ID);
//    if (method != null) {
//      q.setRewriteMethod(method);
//    }
//    
//    return q;
  }

}
