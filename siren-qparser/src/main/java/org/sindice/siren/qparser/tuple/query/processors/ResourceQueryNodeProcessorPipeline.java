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
/**
 * @project siren-qparser_rdelbru
 * @author Renaud Delbru [ 21 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.tuple.query.processors;

import org.apache.lucene.queryParser.core.config.QueryConfigHandler;
import org.apache.lucene.queryParser.core.processors.NoChildOptimizationQueryNodeProcessor;
import org.apache.lucene.queryParser.core.processors.QueryNodeProcessorPipeline;
import org.apache.lucene.queryParser.core.processors.RemoveDeletedQueryNodesProcessor;
import org.apache.lucene.queryParser.standard.builders.StandardQueryTreeBuilder;
import org.apache.lucene.queryParser.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryParser.standard.parser.StandardSyntaxParser;
import org.apache.lucene.queryParser.standard.processors.AllowLeadingWildcardProcessor;
import org.apache.lucene.queryParser.standard.processors.BooleanSingleChildOptimizationQueryNodeProcessor;
import org.apache.lucene.queryParser.standard.processors.BoostQueryNodeProcessor;
import org.apache.lucene.queryParser.standard.processors.DefaultPhraseSlopQueryNodeProcessor;
import org.apache.lucene.queryParser.standard.processors.FuzzyQueryNodeProcessor;
import org.apache.lucene.queryParser.standard.processors.LowercaseExpandedTermsQueryNodeProcessor;
import org.apache.lucene.queryParser.standard.processors.MatchAllDocsQueryNodeProcessor;
import org.apache.lucene.queryParser.standard.processors.MultiTermRewriteMethodProcessor;
import org.apache.lucene.queryParser.standard.processors.ParametricRangeQueryNodeProcessor;
import org.apache.lucene.queryParser.standard.processors.PhraseSlopQueryNodeProcessor;
import org.apache.lucene.queryParser.standard.processors.RemoveEmptyNonLeafQueryNodeProcessor;
import org.apache.lucene.queryParser.standard.processors.WildcardQueryNodeProcessor;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.query.processors.AllowFuzzyAndWildcardProcessor;
import org.sindice.siren.qparser.keyword.query.processors.AnalyzerQueryNodeProcessor;
import org.sindice.siren.qparser.keyword.query.processors.ChangeDefaultOperatorNodeProcessor;
import org.sindice.siren.qparser.keyword.query.processors.GroupQueryNodeProcessor;
import org.sindice.siren.qparser.keyword.query.processors.MultiFieldQueryNodeProcessor;

/**
 * This pipeline has all the processors needed to process a query node tree,
 * generated by {@link StandardSyntaxParser}, already assembled. <br/>
 * <br/>
 * The order they are assembled affects the results. <br/>
 * <br/>
 * This processor pipeline was designed to work with
 * {@link StandardQueryConfigHandler}. <br/>
 * <br/>
 * The result query node tree can be used to build a {@link Query} object using
 * {@link StandardQueryTreeBuilder}. <br/>
 *
 * @see StandardQueryTreeBuilder
 * @see StandardQueryConfigHandler
 * @see StandardSyntaxParser
 */
public class ResourceQueryNodeProcessorPipeline
extends QueryNodeProcessorPipeline {

  public ResourceQueryNodeProcessorPipeline(final QueryConfigHandler queryConfig) {
    super(queryConfig);

    this.add(new ChangeDefaultOperatorNodeProcessor());
    this.add(new WildcardQueryNodeProcessor());
    this.add(new MultiFieldQueryNodeProcessor());
    this.add(new FuzzyQueryNodeProcessor());
    this.add(new MatchAllDocsQueryNodeProcessor());
    this.add(new SirenNumericQueryNodeProcessor());
    this.add(new SirenNumericRangeQueryNodeProcessor());
    this.add(new LowercaseExpandedTermsQueryNodeProcessor());
    this.add(new ParametricRangeQueryNodeProcessor());
    this.add(new AllowFuzzyAndWildcardProcessor());
    this.add(new AllowLeadingWildcardProcessor());
    this.add(new AnalyzerQueryNodeProcessor());
    this.add(new PhraseSlopQueryNodeProcessor());
    this.add(new GroupQueryNodeProcessor());
    this.add(new NoChildOptimizationQueryNodeProcessor());
    this.add(new RemoveDeletedQueryNodesProcessor());
    this.add(new RemoveEmptyNonLeafQueryNodeProcessor());
    this.add(new BooleanSingleChildOptimizationQueryNodeProcessor());
    this.add(new DefaultPhraseSlopQueryNodeProcessor());
    this.add(new BoostQueryNodeProcessor());
    this.add(new MultiTermRewriteMethodProcessor());
  }

}