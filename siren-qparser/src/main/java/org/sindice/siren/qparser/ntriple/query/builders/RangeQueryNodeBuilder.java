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
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.standard.nodes.RangeQueryNode;
import org.apache.lucene.search.TermRangeQuery;

/**
 * Builds a {@link TermRangeQuery} object from a {@link RangeQueryNode} object.
 */
public class RangeQueryNodeBuilder implements ResourceQueryBuilder {

  public RangeQueryNodeBuilder() {
    // empty constructor
  }

  public TermRangeQuery build(QueryNode queryNode) throws QueryNodeException {
    throw new NotImplementedException("TermRange queries not supported yet");
    
//TODO: To implement when SIRen will support termrange queries    
//    RangeQueryNode rangeNode = (RangeQueryNode) queryNode;
//    ParametricQueryNode upper = rangeNode.getUpperBound();
//    ParametricQueryNode lower = rangeNode.getLowerBound();
//
//    boolean lowerInclusive = false;
//    boolean upperInclusive = false;
//
//    if (upper.getOperator() == CompareOperator.LE) {
//      upperInclusive = true;
//    }
//
//    if (lower.getOperator() == CompareOperator.GE) {
//      lowerInclusive = true;
//    }
//
//    String field = rangeNode.getField().toString();
//
//    TermRangeQuery rangeQuery = new TermRangeQuery(field, lower
//        .getTextAsString(), upper.getTextAsString(), lowerInclusive,
//        upperInclusive, rangeNode.getCollator());
//    
//    MultiTermQuery.RewriteMethod method = (MultiTermQuery.RewriteMethod)queryNode.getTag(MultiTermRewriteMethodAttribute.TAG_ID);
//    if (method != null) {
//      rangeQuery.setRewriteMethod(method);
//    }
//
//    return rangeQuery;

  }

}
