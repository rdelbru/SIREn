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
/**
 * Create a {@link org.apache.lucene.search.Query} object
 * from the processed {@link org.apache.lucene.queryparser.flexible.core.nodes.QueryNode} tree
 * using a bottom-up approach.
 * 
 * Each {@link org.apache.lucene.queryparser.flexible.core.nodes.QueryNode} of the query tree
 * is mapped to a {@link org.sindice.siren.qparser.keyword.builders.KeywordQueryBuilder}.
 * Most builders create a {@link org.sindice.siren.search.node.NodeQuery} object.
 * In case the {@link org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys#ALLOW_TWIG}
 * is <code>true</code>, every {@link org.sindice.siren.search.node.NodeQuery} is wrapped
 * into a {@link org.sindice.siren.search.node.LuceneProxyNodeQuery}; otherwise, a pure
 * {@link org.sindice.siren.search.node.NodeQuery} object is created from the
 * query tree build.
 * 
 */
package org.sindice.siren.qparser.keyword.builders;

