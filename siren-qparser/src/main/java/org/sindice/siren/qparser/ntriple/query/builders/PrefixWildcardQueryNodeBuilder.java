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
import org.apache.lucene.queryParser.standard.nodes.PrefixWildcardQueryNode;
import org.apache.lucene.search.PrefixQuery;

/**
 * Builds a {@link PrefixQuery} object from a {@link PrefixWildcardQueryNode}
 * object.
 */
public class PrefixWildcardQueryNodeBuilder implements ResourceQueryBuilder {

  public PrefixWildcardQueryNodeBuilder() {
    // empty constructor
  }

  public PrefixQuery build(QueryNode queryNode) throws QueryNodeException {    
    throw new NotImplementedException("Prefix queries are not implemented in siren yet");
    
//TODO: To implement when SIRen will support prefix queries    
//    PrefixWildcardQueryNode wildcardNode = (PrefixWildcardQueryNode) queryNode;
//
//    String text = wildcardNode.getText().subSequence(0, wildcardNode.getText().length() - 1).toString();
//    PrefixQuery q = new PrefixQuery(new Term(wildcardNode.getFieldAsString(), text));
//    
//    MultiTermQuery.RewriteMethod method = (MultiTermQuery.RewriteMethod)queryNode.getTag(MultiTermRewriteMethodAttribute.TAG_ID);
//    if (method != null) {
//      q.setRewriteMethod(method);
//    }
//    
//    return q;
  }

}
