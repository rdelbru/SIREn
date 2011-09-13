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
/**
 * @project siren
 * @author Renaud Delbru [ 21 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.ntriple.query.builders;

import org.apache.commons.lang.NotImplementedException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.nodes.SlopQueryNode;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.sindice.siren.search.SirenPhraseQuery;

/**
 * This builder basically reads the {@link Query} object set on the
 * {@link SlopQueryNode} child using
 * {@link QueryTreeBuilder#QUERY_TREE_BUILDER_TAGID} and applies the slop value
 * defined in the {@link SlopQueryNode}.
 */
public class SlopQueryNodeBuilder implements ResourceQueryBuilder {

  public SlopQueryNodeBuilder() {
  // empty constructor
  }

  public SirenPhraseQuery build(QueryNode queryNode) throws QueryNodeException {
    final SlopQueryNode phraseSlopNode = (SlopQueryNode) queryNode;

    if (phraseSlopNode.getValue() != 0)
      throw new NotImplementedException("Slop Queries not supported in Siren yet");

    SirenPhraseQuery query = (SirenPhraseQuery) phraseSlopNode.getChild().getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
    return query;

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
