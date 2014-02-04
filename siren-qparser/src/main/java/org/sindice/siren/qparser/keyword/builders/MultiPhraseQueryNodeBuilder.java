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

import org.apache.commons.lang.NotImplementedException;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.MultiPhraseQueryNode;
import org.apache.lucene.search.MultiPhraseQuery;

/**
 * Builds a {@link MultiPhraseQuery} object from a {@link MultiPhraseQueryNode}
 * object.
 */
public class MultiPhraseQueryNodeBuilder implements KeywordQueryBuilder {

  public MultiPhraseQueryNodeBuilder() {
    // empty constructor
  }

  public MultiPhraseQuery build(QueryNode queryNode) throws QueryNodeException {
    throw new NotImplementedException("Multi phrase queries are not supported by SIRen yet");
//    
//    MultiPhraseQueryNode phraseNode = (MultiPhraseQueryNode) queryNode;
//
//    MultiPhraseQuery phraseQuery = new MultiPhraseQuery();
//
//    List<QueryNode> children = phraseNode.getChildren();
//
//    if (children != null) {
//      TreeMap<Integer, List<Term>> positionTermMap = new TreeMap<Integer, List<Term>>();
//
//      for (QueryNode child : children) {
//        FieldQueryNode termNode = (FieldQueryNode) child;
//        TermQuery termQuery = (TermQuery) termNode
//            .getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
//        List<Term> termList = positionTermMap.get(termNode
//            .getPositionIncrement());
//
//        if (termList == null) {
//          termList = new LinkedList<Term>();
//          positionTermMap.put(termNode.getPositionIncrement(), termList);
//
//        }
//
//        termList.add(termQuery.getTerm());
//
//      }
//
//      for (int positionIncrement : positionTermMap.keySet()) {
//        List<Term> termList = positionTermMap.get(positionIncrement);
//
//        phraseQuery.add(termList.toArray(new Term[termList.size()]),
//            positionIncrement);
//
//      }
//
//    }
//
//    return phraseQuery;

  }

}
