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

import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.standard.nodes.PrefixWildcardQueryNode;
import org.sindice.siren.qparser.ntriple.query.config.SirenMultiTermRewriteMethodAttribute;
import org.sindice.siren.search.SirenMultiTermQuery;
import org.sindice.siren.search.SirenPrefixQuery;

/**
 * Builds a {@link SirenPrefixQuery} object from a {@link PrefixWildcardQueryNode}
 * object.
 */
public class PrefixWildcardQueryNodeBuilder implements ResourceQueryBuilder {

  public PrefixWildcardQueryNodeBuilder() {
    // empty constructor
  }

  public SirenPrefixQuery build(QueryNode queryNode) throws QueryNodeException {
    PrefixWildcardQueryNode wildcardNode = (PrefixWildcardQueryNode) queryNode;
    
    final String text = wildcardNode.getText().subSequence(0, wildcardNode.getText().length() - 1).toString();
    SirenPrefixQuery q = new SirenPrefixQuery(new Term(wildcardNode.getFieldAsString(), text));
    
    SirenMultiTermQuery.RewriteMethod method = (SirenMultiTermQuery.RewriteMethod)queryNode.getTag(SirenMultiTermRewriteMethodAttribute.TAG_ID);
    if (method != null) {
      q.setRewriteMethod(method);
    }
        
    return q;
  }

}
