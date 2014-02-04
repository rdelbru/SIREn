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
import org.apache.lucene.queryparser.flexible.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.search.FuzzyQuery;
import org.sindice.siren.search.node.NodeFuzzyQuery;

/**
 * Builds a {@link NodeFuzzyQuery} object from a {@link FuzzyQueryNode} object.
 */
public class NodeFuzzyQueryNodeBuilder implements KeywordQueryBuilder {

  public NodeFuzzyQueryNodeBuilder() {
    // empty constructor
  }

  public NodeFuzzyQuery build(QueryNode queryNode) throws QueryNodeException {
    FuzzyQueryNode fuzzyNode = (FuzzyQueryNode) queryNode;
    String text = fuzzyNode.getTextAsString();
    
    int numEdits = FuzzyQuery.floatToEdits(fuzzyNode.getSimilarity(), 
        text.codePointCount(0, text.length()));

    return new NodeFuzzyQuery(new Term(fuzzyNode.getFieldAsString(), fuzzyNode
        .getTextAsString()), numEdits, fuzzyNode
        .getPrefixLength());
  }

}
