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
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.SlopQueryNode;
import org.apache.lucene.search.Query;
import org.sindice.siren.search.node.NodePhraseQuery;

/**
 * This builder basically reads the {@link Query} object set on the
 * {@link SlopQueryNode} child using
 * {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} and applies the slop value
 * defined in the {@link SlopQueryNode}.
 */
public class SlopQueryNodeBuilder implements KeywordQueryBuilder {

  public SlopQueryNodeBuilder() {
  // empty constructor
  }

  public NodePhraseQuery build(QueryNode queryNode) throws QueryNodeException {
    final SlopQueryNode phraseSlopNode = (SlopQueryNode) queryNode;

    if (phraseSlopNode.getValue() != 0)
      throw new NotImplementedException("Slop Queries not supported in Siren yet");

    return (NodePhraseQuery) phraseSlopNode.getChild().getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);

    // TODO: To implement when siren will support slop queries
    // SlopQueryNode phraseSlopNode = (SlopQueryNode) queryNode;
    //
    // Query query = (Query) phraseSlopNode.getChild().getTag(
    // QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
    //
    //    
    // if (query instanceof PhraseQuery) {
    // ((PhraseQuery) query).setSlop(phraseSlopNode.getValue());
    //
    // } else {
    // ((MultiPhraseQuery) query).setSlop(phraseSlopNode.getValue());
    // }
    //
    // return query;

  }

}
