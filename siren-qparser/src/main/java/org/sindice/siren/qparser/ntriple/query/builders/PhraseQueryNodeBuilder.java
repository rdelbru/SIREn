package org.sindice.siren.qparser.ntriple.query.builders;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryParser.core.nodes.FieldQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.nodes.TokenizedPhraseQueryNode;
import org.apache.lucene.search.PhraseQuery;
import org.sindice.siren.search.SirenPhraseQuery;
import org.sindice.siren.search.SirenTermQuery;

/**
 * Builds a {@link PhraseQuery} object from a {@link TokenizedPhraseQueryNode}
 * object.
 */
public class PhraseQueryNodeBuilder implements ResourceQueryBuilder {

  public PhraseQueryNodeBuilder() {
    // empty constructor
  }

  public SirenPhraseQuery build(QueryNode queryNode) throws QueryNodeException {
    final TokenizedPhraseQueryNode phraseNode = (TokenizedPhraseQueryNode) queryNode;

    final SirenPhraseQuery phraseQuery = new SirenPhraseQuery();
    final List<QueryNode> children = phraseNode.getChildren();

    if (children != null) {
      
      for (QueryNode child : children) {
        SirenTermQuery termQuery = (SirenTermQuery) child
            .getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
        FieldQueryNode termNode = (FieldQueryNode) child;
        
        phraseQuery.add(termQuery.getTerm(), termNode.getPositionIncrement());

      }

    }
    return phraseQuery;

  }

}
