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

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Similarity;
import org.sindice.siren.search.SirenPrimitiveQuery;

/**
 * This builder does the same as the {@link BooleanQueryNodeBuilder}, but this
 * considers if the built {@link BooleanQuery} should have its coord disabled or
 * not. <br/>
 *
 * @see BooleanQueryNodeBuilder
 * @see BooleanQuery
 * @see Similarity#coord(int, int)
 */
public class StandardBooleanQueryNodeBuilder implements ResourceQueryBuilder {

  public StandardBooleanQueryNodeBuilder() {
    // empty constructor
  }

  public SirenPrimitiveQuery build(final QueryNode queryNode)
  throws QueryNodeException {
    final BooleanQueryNodeBuilder bqNodeBuilder = new BooleanQueryNodeBuilder();

    return bqNodeBuilder.build(queryNode);
  }

}
