/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
package org.sindice.siren.qparser.ntriple.query.builders;

import org.apache.commons.lang.NotImplementedException;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.search.FuzzyQuery;

/**
 * Builds a {@link FuzzyQuery} object from a {@link FuzzyQueryNode} object.
 */
public class FuzzyQueryNodeBuilder implements ResourceQueryBuilder {

  public FuzzyQueryNodeBuilder() {
    // empty constructor
  }

  public FuzzyQuery build(QueryNode queryNode) throws QueryNodeException {
    throw new NotImplementedException("FuzzyQueries are not supported yet");
    
//TODO: TO implement when Siren will support Fuzzy queries    
//	  if(true){
//		  
//	  }
//    FuzzyQueryNode fuzzyNode = (FuzzyQueryNode) queryNode;
//
//    return new FuzzyQuery(new Term(fuzzyNode.getFieldAsString(), fuzzyNode
//        .getTextAsString()), fuzzyNode.getSimilarity(), fuzzyNode
//        .getPrefixLength());
//
  }

}
