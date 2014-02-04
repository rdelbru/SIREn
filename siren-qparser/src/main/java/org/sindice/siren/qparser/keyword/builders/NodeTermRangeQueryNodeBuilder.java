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

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.apache.lucene.queryparser.flexible.standard.builders.TermRangeQueryNodeBuilder;
import org.apache.lucene.queryparser.flexible.standard.nodes.TermRangeQueryNode;
import org.sindice.siren.qparser.keyword.processors.MultiNodeTermRewriteMethodProcessor;
import org.sindice.siren.search.node.MultiNodeTermQuery;
import org.sindice.siren.search.node.NodeTermRangeQuery;

/**
 * This class builds a {@link NodeTermRangeQuery} from a {@link TermRangeQueryNode}.
 * 
 * <p>
 *
 * Code taken from {@link TermRangeQueryNodeBuilder} and adapted for SIREn.
 */
public class NodeTermRangeQueryNodeBuilder
implements KeywordQueryBuilder {

  public NodeTermRangeQueryNodeBuilder() {
  }

  public NodeTermRangeQuery build(final QueryNode queryNode) throws QueryNodeException {
    final TermRangeQueryNode rangeNode = (TermRangeQueryNode) queryNode;
    final FieldQueryNode upper = rangeNode.getUpperBound();
    final FieldQueryNode lower = rangeNode.getLowerBound();

    final String field = StringUtils.toString(rangeNode.getField());
    String lowerText = lower.getTextAsString();
    String upperText = upper.getTextAsString();

    if (lowerText.length() == 0) {
      lowerText = null;
    }

    if (upperText.length() == 0) {
      upperText = null;
    }

    final NodeTermRangeQuery rangeQuery = NodeTermRangeQuery.newStringRange(field, lowerText, upperText, rangeNode
        .isLowerInclusive(), rangeNode.isUpperInclusive());

    final MultiNodeTermQuery.RewriteMethod method = (MultiNodeTermQuery.RewriteMethod) queryNode
        .getTag(MultiNodeTermRewriteMethodProcessor.TAG_ID);
    if (method != null) {
      rangeQuery.setRewriteMethod(method);
    }
    return rangeQuery;
  }

}
