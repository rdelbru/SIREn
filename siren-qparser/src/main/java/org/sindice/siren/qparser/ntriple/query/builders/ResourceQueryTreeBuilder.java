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
package org.sindice.siren.qparser.ntriple.query.builders;

import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryParser.core.nodes.BooleanQueryNode;
import org.apache.lucene.queryParser.core.nodes.BoostQueryNode;
import org.apache.lucene.queryParser.core.nodes.FieldQueryNode;
import org.apache.lucene.queryParser.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryParser.core.nodes.GroupQueryNode;
import org.apache.lucene.queryParser.core.nodes.MatchAllDocsQueryNode;
import org.apache.lucene.queryParser.core.nodes.MatchNoDocsQueryNode;
import org.apache.lucene.queryParser.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryParser.core.nodes.QueryNode;
import org.apache.lucene.queryParser.core.nodes.SlopQueryNode;
import org.apache.lucene.queryParser.core.nodes.TokenizedPhraseQueryNode;
import org.apache.lucene.queryParser.standard.nodes.MultiPhraseQueryNode;
import org.apache.lucene.queryParser.standard.nodes.PrefixWildcardQueryNode;
import org.apache.lucene.queryParser.standard.nodes.RangeQueryNode;
import org.apache.lucene.queryParser.standard.nodes.StandardBooleanQueryNode;
import org.apache.lucene.queryParser.standard.nodes.WildcardQueryNode;
import org.apache.lucene.queryParser.standard.processors.StandardQueryNodeProcessorPipeline;
import org.apache.lucene.search.Query;

/**
 * This query tree builder only defines the necessary map to build a
 * {@link Query} tree object. It should be used to generate a {@link Query} tree
 * object from a query node tree processed by a
 * {@link StandardQueryNodeProcessorPipeline}. <br/>
 * 
 * @see QueryTreeBuilder
 * @see StandardQueryNodeProcessorPipeline
 */
public class ResourceQueryTreeBuilder extends QueryTreeBuilder implements ResourceQueryBuilder {

  public ResourceQueryTreeBuilder() {
    setBuilder(GroupQueryNode.class, new GroupQueryNodeBuilder());
    setBuilder(FieldQueryNode.class, new FieldQueryNodeBuilder());
    setBuilder(BooleanQueryNode.class, new BooleanQueryNodeBuilder());
    setBuilder(FuzzyQueryNode.class, new FuzzyQueryNodeBuilder());
    setBuilder(BoostQueryNode.class, new BoostQueryNodeBuilder());
    setBuilder(ModifierQueryNode.class, new ModifierQueryNodeBuilder());
    setBuilder(WildcardQueryNode.class, new WildcardQueryNodeBuilder());
    setBuilder(TokenizedPhraseQueryNode.class, new PhraseQueryNodeBuilder());
    setBuilder(MatchNoDocsQueryNode.class, new MatchNoDocsQueryNodeBuilder());
    setBuilder(PrefixWildcardQueryNode.class, new PrefixWildcardQueryNodeBuilder());
    setBuilder(RangeQueryNode.class, new RangeQueryNodeBuilder());
    setBuilder(SlopQueryNode.class, new SlopQueryNodeBuilder());
    setBuilder(StandardBooleanQueryNode.class, new StandardBooleanQueryNodeBuilder());
    setBuilder(MultiPhraseQueryNode.class, new MultiPhraseQueryNodeBuilder());
    setBuilder(MatchAllDocsQueryNode.class, new MatchAllDocsQueryNodeBuilder());
  }

  @Override
  public Query build(QueryNode queryNode) throws QueryNodeException {
    try {
      return (Query) super.build(queryNode);
    }
    catch (ClassCastException e) {
      throw new Error("Unsupported query construct", e);
    }
  }

}
