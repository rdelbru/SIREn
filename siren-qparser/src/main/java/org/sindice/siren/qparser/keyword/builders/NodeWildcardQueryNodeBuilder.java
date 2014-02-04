/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sindice.siren.qparser.keyword.builders;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.standard.builders.WildcardQueryNodeBuilder;
import org.apache.lucene.queryparser.flexible.standard.nodes.WildcardQueryNode;
import org.sindice.siren.qparser.keyword.processors.MultiNodeTermRewriteMethodProcessor;
import org.sindice.siren.search.node.MultiNodeTermQuery;
import org.sindice.siren.search.node.NodeWildcardQuery;

/**
 * Builds a {@link NodeWildcardQuery} object from a {@link WildcardQueryNode}
 * object.
 *
 * <p>
 *
 * Code taken from {@link WildcardQueryNodeBuilder} and adapted to SIREn
 */
public class NodeWildcardQueryNodeBuilder implements KeywordQueryBuilder {

  public NodeWildcardQueryNodeBuilder() {
    // empty constructor
  }

  public NodeWildcardQuery build(final QueryNode queryNode) throws QueryNodeException {
    final WildcardQueryNode wildcardNode = (WildcardQueryNode) queryNode;

    final NodeWildcardQuery q = new NodeWildcardQuery(new Term(wildcardNode.getFieldAsString(),
                                                         wildcardNode.getTextAsString()));

    final MultiNodeTermQuery.RewriteMethod method = (MultiNodeTermQuery.RewriteMethod)queryNode.getTag(MultiNodeTermRewriteMethodProcessor.TAG_ID);
    if (method != null) {
      q.setRewriteMethod(method);
    }
    return q;
  }

}
