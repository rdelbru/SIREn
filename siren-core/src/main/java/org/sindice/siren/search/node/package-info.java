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
 * Programmatic API to search node-based inverted indexes.
 *
 * <h2>Introduction</h2>
 *
 * This package contains the API for building queries to search JSON data
 * over node-based inverted indexes. For an introduction about the Lucene's
 * search API, see the {@link org.apache.lucene.search} package documentation.
 *
 * <h2>Search Basics</h2>
 *
 * In contrast to the Lucene's {@link org.apache.lucene.search.Query} API
 * which provides complex querying capabilities to search for documents, SIREn
 * provide a {@link org.sindice.siren.search.node.NodeQuery} API to provide
 * complex querying capabilities to search for nodes and documents. The
 * information retrieved not only consists of the matching documents, but also
 * of the matching nodes within these documents.
 *
 * <p>
 *
 * SIREn offers a wide variety of
 * {@link org.sindice.siren.search.node.NodeQuery} implementations. Most of them
 * are similar to the ones provided by the Lucene's
 * {@link org.apache.lucene.search.Query} API. For example, while Lucene
 * provides a {@link org.apache.lucene.search.TermQuery} implementation
 * to search documents that contain a specific term, SIREn provides a {@link
 * org.sindice.siren.search.node.NodeTermQuery} implementation to search nodes
 * and documents that contain a specific term.
 *
 * <h3>Level and Range Constraints</h3>
 *
 * The {@link org.sindice.siren.search.node.NodeQuery} provides methods to set
 * constraints on the nodes matched by the query. There are two types of
 * constraints:
 * <ul>
 *   <li> Level constraint: this constraint will filter out all nodes that do
 *   not belong to the specified level of the tree.
 *   <li> Interval constraint: this constraint will filter out all nodes in
 *   which the last integer of their dewey code vector is not contained in the
 *   specified interval.
 * </ul>
 *
 * <h2>Query Classes</h2>
 *
 * <h3>{@link org.sindice.siren.search.node.NodeTermQuery}</h3>
 *
 * A {@link org.sindice.siren.search.node.NodeTermQuery} matches all the
 * nodes that contain the specified {@link org.apache.lucene.index.Term},
 * which is a word that occurs in a certain
 * {@link org.apache.lucene.document.Field} containing JSON data.
 * <p>
 * Constructing a {@link org.sindice.siren.search.node.NodeTermQuery} is as
 * simple as:
 * <pre>
 *      NodeTermQuery tq = new NodeTermQuery(new Term("json-field", "term"));
 * </pre>
 *
 * In this example, the {@link org.sindice.siren.search.node.NodeQuery}
 * identifies all {@link org.apache.lucene.document.Document}s that have the
 * {@link org.apache.lucene.document.Field} named <tt>"json-field"</tt>
 * where a node contains the word <tt>"term"</tt>.
 *
 * <h3>{@link org.sindice.siren.search.node.NodePhraseQuery}
 *
 * A {@link org.sindice.siren.search.node.NodePhraseQuery} matches all the nodes
 * containing the specified phrase. A phrase is defined as a sequence of
 * {@link org.apache.lucene.index.Term}.
 *
 * <h3>{@link org.sindice.siren.search.node.NodeBooleanQuery}</h3>
 *
 * A {@link org.sindice.siren.search.node.NodeBooleanQuery} matches all the
 * nodes containing the specified boolean combination of queries.
 * A {@link org.sindice.siren.search.node.NodeBooleanQuery} contains multiple
 * {@link org.sindice.siren.search.node.NodeBooleanClause}s, where each clause
 * contains a sub-query
 * ({@link org.sindice.siren.search.node.NodePrimitiveQuery} instance) and an
 * operator (from {@link org.sindice.siren.search.node.NodeBooleanClause.Occur})
 * describing how that sub-query is combined with the other clauses. The
 * semantic of {@link org.sindice.siren.search.node.NodeBooleanClause.Occur} is
 * identical to the semantic of {@link org.apache.lucene.search.BooleanClause.Occur}.
 *
 * <h3>{@link org.sindice.siren.search.node.NodeTermRangeQuery}</h3>
 *
 * A {@link org.sindice.siren.search.node.NodeTermRangeQuery} matches all
 * nodes containing a term that occurs in the inclusive or exclusive range of a
 * lower {@link org.apache.lucene.index.Term Term} and an upper
 * {@link org.apache.lucene.index.Term Term} according to
 * {@link org.apache.lucene.index.TermsEnum#getComparator TermsEnum.getComparator()}.
 * It is not intended for numerical ranges; use
 * {@link org.sindice.siren.search.node.NodeNumericRangeQuery} instead.
 *
 * <h3>{@link org.sindice.siren.search.node.NodeNumericRangeQuery}</h3>
 *
 * A {@link org.sindice.siren.search.node.NodeNumericRangeQuery} matches all
 * nodes containing a value that occurs in a numeric range. For
 * NodeNumericRangeQuery to work, you must index the values with the datatypes
 * configured with the appropriate numeric analyzers
 * ({@link org.sindice.siren.analysis.NumericAnalyzer}).
 *
 * <h3>{@link org.sindice.siren.search.node.NodePrefixQuery},
 *     {@link org.sindice.siren.search.node.NodeWildcardQuery},
 *     {@link org.sindice.siren.search.node.NodeRegexpQuery}</h3>
 *
 * A {@link org.sindice.siren.search.node.NodePrefixQuery} matches all nodes
 * containing terms that begin with the specified string. A
 * {@link org.sindice.siren.search.node.NodeWildcardQuery} generalizes this
 * by allowing for the use of <tt>+</tt> (matches 1 or more characters),
 * <tt>*</tt> (matches 0 or more characters) and
 * <tt>?</tt> (matches exactly one character) wildcards. Note that the
 * {@link org.sindice.siren.search.node.NodeWildcardQuery} can be quite slow. Also
 * note that {@link org.sindice.siren.search.node.NodeWildcardQuery} should
 * not start with <tt>+</tt>, <tt>*</tt> and <tt>?</tt>, as these are extremely slow.
 * Some QueryParsers may not allow this by default, but provide a
 * <code>setAllowLeadingWildcard</code> method to remove that protection.
 * The {@link org.sindice.siren.search.node.NodeRegexpQuery} is even more
 * general than NodeWildcardQuery, matching all nodes with terms that match a
 * regular expression pattern.
 *
 * <h3>{@link org.sindice.siren.search.node.NodeFuzzyQuery}</h3>
 *
 * A {@link org.sindice.siren.search.node.NodeFuzzyQuery} matches nodes that
 * contain terms similar to the specified term. Similarity is determined using
 * <a href="http://en.wikipedia.org/wiki/Levenshtein">Levenshtein (edit)
 * distance</a>.
 *
 * <h3>{@link org.sindice.siren.search.node.TwigQuery}</h3>
 *
 * A {@link org.sindice.siren.search.node.TwigQuery} enables to combine
 * {@link org.sindice.siren.search.node.NodeQuery}s with a Parent-Child or
 * Ancestor-Descendant relation. This is the basic building block to build
 * tree-shaped queries.
 *
 * <p>
 *
 * A {@link org.sindice.siren.search.node.TwigQuery} is composed of a root and
 * of one or more children or descendants:
 * <ul>
 *  <li> The root is a {@link org.sindice.siren.search.node.NodeQuery} instance.
 *       An empty root is considered as a wildcard node query and will match all
 *       nodes. We call "root nodes" the set of nodes that are retrieved by the
 *       root query.
 *  <li> A descendant is a {@link org.sindice.siren.search.node.NodeQuery}
 *       associated to an operator (from
 *       {@link org.sindice.siren.search.node.NodeBooleanClause.Occur}). A
 *       descendant query will match all the nodes for which it exists a path
 *       to a root node. A descendant is associated to a node level, which
 *       corresponds to the relative distance (in term of levels) from the root.
 *  <li> A child is a descendant that is exactly one level above the root level.
 * </ul>
 *
 * <p>
 *
 * A twig query is always associated to a level. If no level is specified, then
 * by default the level is set to 1. When a twig query is used as a child or
 * descendant of another twig query, then its level is automatically updated
 * according to the level of the parent twig query. For example, given
 * the following instructions:
 * <pre>
 *      TwigQuery tw1 = new TwigQuery();
 *      TwigQuery tw2 = new TwigQuery();
 *      tw1.addChild(tw2, Occur.MUST);
 * </pre>
 *
 * In this example, the first twig query <tt>tw1</tt> is defined at the default
 * level 1. The second twig query <tt>tw2</tt>, after the call to
 * {@link org.sindice.siren.search.node.TwigQuery#addChild(NodeQuery, org.sindice.siren.search.node.NodeBooleanClause.Occur)},
 * will have its level updated to 2 since it is now a child of a twig query at a
 * level 1.
 *
 * <h2>The Scorer Class</h2>
 *
 * The {@link org.sindice.siren.search.node.NodeScorer} abstract class provides
 * common scoring functionality for all the node scorer implementations which
 * are the heart of the SIREn scoring process.
 *
 * <p>
 *
 * The implementation of the query processing framework follows a node-at-a-time
 * approach, where the query operators (i.e., {@link org.sindice.siren.search.node.NodeScorer})
 * process one node at a time. The query processing framework has been
 * designed for high efficiency processing:
 * <ol>
 *   <li> All the query operators leverage has much as possible the lazy-loading
 *   feature of the
 *   {@link org.sindice.siren.index.codecs.siren10.Siren10PostingsReader}. For
 *   example, there is not the concept of next matching document (i.e.,
 *   {@link org.apache.lucene.search.Scorer#nextDoc()}) in the
 *   {@link NodeScorer} interface, but instead the concept of next candidate
 *   document (i.e.,
 *   {@link org.sindice.siren.search.node.NodeScorer#nextCandidateDocument()}).
 *   This enables {@link org.sindice.siren.search.node.NodeConjunctionScorer} to
 *   efficiently iterates over the document identifiers wihtout having to
 *   decode the node labels until a potential candidate is found.
 *   <li> The node label array (i.e., {@link org.apache.lucene.util.IntsRef})
 *   being processed is the same in all the query operators, which means that
 *   the same array is reused across and no new arrays are created during the
 *   query processing.
 *   <li> The node label array is itself a slice of the array of the
 *   uncompressed node block. The node label array is created by sliding a
 *   window (i.e., {@link org.apache.lucene.util.IntsRef}) over the array of the
 *   uncompressed node block.
 * </ol>
 *
 */
package org.sindice.siren.search.node;

