/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
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
