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
package org.sindice.siren.qparser.json.builders;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.standard.builders.ModifierQueryNodeBuilder;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.json.nodes.BooleanQueryNode;
import org.sindice.siren.qparser.json.nodes.ChildQueryNode;
import org.sindice.siren.qparser.json.nodes.DescendantQueryNode;
import org.sindice.siren.qparser.json.nodes.NodeQueryNode;
import org.sindice.siren.qparser.json.nodes.TopLevelQueryNode;
import org.sindice.siren.qparser.json.nodes.TwigQueryNode;
import org.sindice.siren.qparser.json.processors.JsonQueryNodeProcessorPipeline;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;

/**
 * This query tree builder defines the necessary map to build a
 * {@link Query} tree object.
 *
 * <p>
 *
 * It should be used to generate a {@link Query} tree
 * object from a query node tree processed by a
 * {@link JsonQueryNodeProcessorPipeline}.
 *
 * @see QueryTreeBuilder
 * @see JsonQueryNodeProcessorPipeline
 */
public class JsonQueryTreeBuilder extends QueryTreeBuilder
implements JsonQueryBuilder {

  public JsonQueryTreeBuilder(final KeywordQueryParser keywordParser) {
    this.setBuilders(keywordParser);
  }

  @Override
  public Query build(final QueryNode queryNode) throws QueryNodeException {
    return (Query) super.build(queryNode);
  }

  public void setBuilders(final KeywordQueryParser keywordParser) {
    this.setBuilder(TopLevelQueryNode.class, new TopLevelQueryNodeBuilder());
    this.setBuilder(NodeQueryNode.class, new NodeQueryNodeBuilder(keywordParser));
    this.setBuilder(ModifierQueryNode.class, new ModifierQueryNodeBuilder());
    this.setBuilder(DescendantQueryNode.class, new ModifierQueryNodeBuilder());
    this.setBuilder(ChildQueryNode.class, new ModifierQueryNodeBuilder());
    this.setBuilder(BooleanQueryNode.class, new BooleanQueryNodeBuilder());
    this.setBuilder(TwigQueryNode.class, new TwigQueryNodeBuilder(keywordParser));
  }

}
