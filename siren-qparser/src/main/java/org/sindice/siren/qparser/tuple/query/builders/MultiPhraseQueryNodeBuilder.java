/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
package org.sindice.siren.qparser.tuple.query.builders;

import org.apache.commons.lang.NotImplementedException;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.standard.nodes.MultiPhraseQueryNode;
import org.apache.lucene.search.MultiPhraseQuery;

/**
 * Builds a {@link MultiPhraseQuery} object from a {@link MultiPhraseQueryNode}
 * object.
 */
public class MultiPhraseQueryNodeBuilder implements ResourceQueryBuilder {

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
