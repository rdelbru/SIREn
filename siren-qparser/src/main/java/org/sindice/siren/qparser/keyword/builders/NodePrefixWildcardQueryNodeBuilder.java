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
import org.apache.lucene.queryparser.flexible.standard.builders.PrefixWildcardQueryNodeBuilder;
import org.apache.lucene.queryparser.flexible.standard.nodes.PrefixWildcardQueryNode;
import org.sindice.siren.qparser.keyword.processors.MultiNodeTermRewriteMethodProcessor;
import org.sindice.siren.search.node.MultiNodeTermQuery;
import org.sindice.siren.search.node.NodePrefixQuery;

/**
 * Builds a {@link NodePrefixQuery} object from a {@link PrefixWildcardQueryNode}
 * object.
 *
 * <p>
 *
 * Code taken from {@link PrefixWildcardQueryNodeBuilder} and adapted to SIREn
 */
public class NodePrefixWildcardQueryNodeBuilder implements KeywordQueryBuilder {

  public NodePrefixWildcardQueryNodeBuilder() {
    // empty constructor
  }

  public NodePrefixQuery build(final QueryNode queryNode) throws QueryNodeException {
    final PrefixWildcardQueryNode wildcardNode = (PrefixWildcardQueryNode) queryNode;

    final String text = wildcardNode.getText().subSequence(0, wildcardNode.getText().length() - 1).toString();
    final NodePrefixQuery q = new NodePrefixQuery(new Term(wildcardNode.getFieldAsString(), text));

    final MultiNodeTermQuery.RewriteMethod method = (MultiNodeTermQuery.RewriteMethod)queryNode.getTag(MultiNodeTermRewriteMethodProcessor.TAG_ID);
    if (method != null) {
      q.setRewriteMethod(method);
    }
    return q;
  }

}
