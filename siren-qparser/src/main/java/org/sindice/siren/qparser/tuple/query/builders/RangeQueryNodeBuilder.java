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

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.nodes.ParametricQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.nodes.ParametricQueryNode.CompareOperator;
import org.apache.lucene.queryParser.standard.nodes.RangeQueryNode;
import org.apache.lucene.search.TermRangeQuery;
import org.sindice.siren.qparser.tuple.query.config.SirenMultiTermRewriteMethodAttribute;
import org.sindice.siren.search.SirenMultiTermQuery;
import org.sindice.siren.search.SirenTermRangeQuery;

/**
 * Builds a {@link TermRangeQuery} object from a {@link RangeQueryNode} object.
 */
public class RangeQueryNodeBuilder implements ResourceQueryBuilder {

  public RangeQueryNodeBuilder() {
    // empty constructor
  }

  public SirenTermRangeQuery build(QueryNode queryNode) throws QueryNodeException {
    RangeQueryNode rangeNode = (RangeQueryNode) queryNode;
    ParametricQueryNode upper = rangeNode.getUpperBound();
    ParametricQueryNode lower = rangeNode.getLowerBound();
  
    boolean lowerInclusive = false;
    boolean upperInclusive = false;
  
    if (upper.getOperator() == CompareOperator.LE) {
      upperInclusive = true;
    }
  
    if (lower.getOperator() == CompareOperator.GE) {
      lowerInclusive = true;
    }
  
    String field = rangeNode.getField().toString();
  
    SirenTermRangeQuery rangeQuery = new SirenTermRangeQuery(field, lower
        .getTextAsString(), upper.getTextAsString(), lowerInclusive,
        upperInclusive, rangeNode.getCollator());
    
    SirenMultiTermQuery.RewriteMethod method = (SirenMultiTermQuery.RewriteMethod)queryNode.getTag(SirenMultiTermRewriteMethodAttribute.TAG_ID);
    if (method != null) {
      rangeQuery.setRewriteMethod(method);
    }
  
    return rangeQuery;
  }

}
