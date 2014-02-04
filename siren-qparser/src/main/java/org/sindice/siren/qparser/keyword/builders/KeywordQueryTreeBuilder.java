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

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.BooleanQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.BoostQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.GroupQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.MatchAllDocsQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.MatchNoDocsQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.SlopQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.TokenizedPhraseQueryNode;
import org.apache.lucene.queryparser.flexible.standard.builders.DummyQueryNodeBuilder;
import org.apache.lucene.queryparser.flexible.standard.nodes.MultiPhraseQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.PrefixWildcardQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.RegexpQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.TermRangeQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.WildcardQueryNode;
import org.apache.lucene.queryparser.flexible.standard.processors.StandardQueryNodeProcessorPipeline;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.nodes.ArrayQueryNode;
import org.sindice.siren.qparser.keyword.nodes.DatatypeQueryNode;
import org.sindice.siren.qparser.keyword.nodes.NodeBooleanQueryNode;
import org.sindice.siren.qparser.keyword.nodes.NodeNumericRangeQueryNode;
import org.sindice.siren.qparser.keyword.nodes.TopLevelQueryNode;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;
import org.sindice.siren.qparser.keyword.nodes.WildcardNodeQueryNode;
import org.sindice.siren.qparser.keyword.processors.KeywordQueryNodeProcessorPipeline;

/**
 * This query tree builder only defines the necessary map to build a
 * {@link Query} tree object.
 *
 * <p>
 *
 * It should be used to generate a {@link Query} tree
 * object from a query node tree processed by a
 * {@link KeywordQueryNodeProcessorPipeline}.
 *
 * <p>
 *
 * Copied from {@link StandardQueryNodeProcessorPipeline} for the SIREn use case.
 *
 * @see QueryTreeBuilder
 * @see KeywordQueryNodeProcessorPipeline
 */
public class KeywordQueryTreeBuilder extends QueryTreeBuilder implements KeywordQueryBuilder {

  public KeywordQueryTreeBuilder() {
    // Create Siren primitive queries
    this.setBuilder(FuzzyQueryNode.class, new NodeFuzzyQueryNodeBuilder());
    this.setBuilder(WildcardQueryNode.class, new NodeWildcardQueryNodeBuilder());
    this.setBuilder(TokenizedPhraseQueryNode.class, new NodePhraseQueryNodeBuilder());
    this.setBuilder(PrefixWildcardQueryNode.class, new NodePrefixWildcardQueryNodeBuilder());
    this.setBuilder(SlopQueryNode.class, new SlopQueryNodeBuilder());
    this.setBuilder(MultiPhraseQueryNode.class, new MultiPhraseQueryNodeBuilder());
    this.setBuilder(FieldQueryNode.class, new FieldQueryNodeBuilder());
    this.setBuilder(NodeNumericRangeQueryNode.class, new NodeNumericRangeQueryNodeBuilder());
    this.setBuilder(TermRangeQueryNode.class, new NodeTermRangeQueryNodeBuilder());
    this.setBuilder(RegexpQueryNode.class, new NodeRegexpQueryNodeBuilder());
    this.setBuilder(TwigQueryNode.class, new TwigQueryNodeBuilder());
    this.setBuilder(ArrayQueryNode.class, new ArrayQueryNodeBuilder());
    this.setBuilder(NodeBooleanQueryNode.class, new NodeBooleanQueryNodeBuilder());
    this.setBuilder(WildcardNodeQueryNode.class, new DummyQueryNodeBuilder());
    this.setBuilder(DatatypeQueryNode.class, new DatatypeQueryNodeBuilder());

    this.setBuilder(TopLevelQueryNode.class, new TopLevelQueryNodeBuilder());
    // Create Lucene queries
    this.setBuilder(GroupQueryNode.class, new GroupQueryNodeBuilder());
    this.setBuilder(ModifierQueryNode.class, new ModifierQueryNodeBuilder());
    this.setBuilder(MatchAllDocsQueryNode.class, new MatchAllDocsQueryNodeBuilder());
    this.setBuilder(NumericQueryNode.class, new DummyQueryNodeBuilder());
    this.setBuilder(BooleanQueryNode.class, new BooleanQueryNodeBuilder());
    this.setBuilder(MatchNoDocsQueryNode.class, new MatchNoDocsQueryNodeBuilder());
    this.setBuilder(BoostQueryNode.class, new BoostQueryNodeBuilder());
  }

  @Override
  public Query build(final QueryNode queryNode) throws QueryNodeException {
    try {
      return (Query) super.build(queryNode);
    }
    catch (final ClassCastException e) {
      throw new Error("Unsupported query construct", e);
    }
  }

}
