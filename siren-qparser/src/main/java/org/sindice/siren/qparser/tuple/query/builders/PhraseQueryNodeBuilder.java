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
