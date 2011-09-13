/**
 * Copyright 2010, Renaud Delbru
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
 * @author Renaud Delbru [ 21 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
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
