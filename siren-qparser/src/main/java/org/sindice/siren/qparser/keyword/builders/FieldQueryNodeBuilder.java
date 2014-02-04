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
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.sindice.siren.search.node.NodeTermQuery;

/**
 * Builds a {@link NodeTermQuery} object from a {@link FieldQueryNode} object.
 */
public class FieldQueryNodeBuilder implements KeywordQueryBuilder {

  public FieldQueryNodeBuilder() {
  }

  public NodeTermQuery build(QueryNode queryNode) throws QueryNodeException {
    final FieldQueryNode fieldNode = (FieldQueryNode) queryNode;
    
    return new NodeTermQuery(new Term(fieldNode.getFieldAsString(), fieldNode
        .getTextAsString()));
  }

}
