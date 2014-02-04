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
 * A keyword query parser implemented with the Lucene's Flexible Query Parser,
 * with support for twig queries.
 *
 * <h2>Query Parser Syntax</h2>
 *
 * <p>
 * A keyword Query can be either:
 * <ul>
 * <li>a twig (<code>twig</code>) expression that represents a {@link org.sindice.siren.search.node.TwigQuery}; or
 * <li>a boolean (<code>boolean</code>) expression that represents a {@link org.apache.lucene.search.BooleanQuery}.
 * </ul>
 *
 * The boolean expression can be a mixture of primitive queries (e.g., a keyword
 * search) and of twig queries.
 * <p>
 * The syntax allows to use custom datatype on any part of the query.
 * 
 * <h3>Twig</h3>
 *
 * <p>
 * A twig expression is defined by the special character ':'. For example, the following
 *
 * <pre>
 * a : b
 * </pre>
 * 
 * where the term "a" appears in one of the top level node (i.e., one of the
 * top level field names) and the term "b" appears in one of its child node
 * (i.e., one of the value of the field).
 * <pre>
 *                             +---+
 *                             | a |
 *                             +-+-+
 *                               |
 *                             +-+-+
 *                             | b |
 *                             +---+
 * </pre>
 * </p>
 * <p>
 * In a twig query, the child clause has a
 * {@link org.sindice.siren.search.node.NodeBooleanClause.Occur#MUST}
 * occurrence by default.
 * </p>
 * 
 * <p>
 * Multiple children can be associated to a same top level node (i.e., one of
 * the top level field names) by using a JSON-like array syntax:
 * 
 * <pre>
 * a : [ b , c ]
 * </pre>
 * 
 * where the term "a" appears in one of the top level node, the term "b"
 * appears in one of its child node (i.e., one of the value of the field),
 * and the term "c" appears in a second
 * child node.
 * <pre>
 *                             +---+
 *                             | a |
 *                             +-+-+
 *                               |
 *                          +----+----+
 *                        +-+-+     +-+-+
 *                        | b |     | c |
 *                        +---+     +---+
 * </pre>
 * </p>
 * 
 * <p>
 * A JSON-like object syntax is also supported. For example, the following query
 * 
 * <pre>
 * { a : b , c : d }
 * </pre>
 * 
 * allows to query for nested objects in a JSON document.
 * This is a syntax sugar for
 * 
 * <pre>* : [ a : b , c : d ]</pre>
 * 
 * where the twigs <code>a : b</code> and <code>c : d</code> become children
 * of a parent with an empty top-level node.
 * <pre>
 *                             +---+
 *                             | * |
 *                             +-+-+
 *                               |
 *                          +----+----+
 *                        +-+-+     +-+-+
 *                        | a |     | c |
 *                        +---+     +---+
 *                          |         |
 *                        +-+-+     +-+-+
 *                        | b |     | d |
 *                        +---+     +---+
 * </pre>
 * </p>
 * 
 * <h4>Wildcard Node</h4>
 * 
 * <p>
 * The query syntax allows to use a wildcard <code>*</code> as a node
 * in the twig query. For example, a twig query node with no constraint
 * on the top-level node is written as
 * <pre>* : b</pre>.
 * The same goes for setting no constraint on the child:
 * <pre>a : *</pre>.
 * A chain of wildcards can be used to set a node constraint on a specific
 * descendant. The query
 * <pre>a : * : * : b</pre>
 * defines a twig with the term "a" occurring on the top level node,
 * and the term "b" occurring on a node three levels below.
 * </p>
 * <p>
 * Wildcards can be used with any of the previous syntaxes.
 * </p>
 * 
 * <h4>Nested Twigs</h4>
 * 
 * <p>
 * The query syntax allows you to create complex twig queries by nesting
 * arrays, objects, and other twigs. For example:
 * <pre>
 * a : { * : b , a : [ { b : c : d } , { e : a }, g ] }
 * </pre>
 * correspond to the following query tree:
 * <pre>
 *                             +---+
 *                             | a |
 *                             +-+-+
 *                               |
 *                        +------+--------+
 *                        |               |
 *                      +-+-+           +-+-+
 *                      | * |           | a |
 *                      +-+-+           +-+-+
 *                        |               |
 *                      +-+-+      +------+------+
 *                      | b |      |      |      |
 *                      +---+    +-+-+  +-+-+  +-+-+
 *                               | * |  | * |  | g |
 *                               +-+-+  +-+-+  +---+
 *                                 |      |
 *                               +-+-+  +-+-+
 *                               | b |  | e |
 *                               +-+-+  +-+-+
 *                                 |      |
 *                               +-+-+  +-+-+
 *                               | c |  | a |
 *                               +-+-+  +---+
 *                                 |
 *                               +-+-+
 *                               | d |
 *                               +---+
 * </pre>
 * </p>
 * 
 * <h3>Boolean</h3>
 *
 * <p>
 * A boolean expression follows the Lucene query syntax, except for the ':'
 * which does not define a field query but instead is used to build a twig
 * query.
 *
 * <pre>
 * a AND b
 * </pre>
 *
 * matches documents where the terms "a" and "b" occur in any node
 * of the JSON tree.
 * </p>
 * 
 * <h4>Boolean of Twigs</h4>
 * 
 * <p>
 * A boolean combination of twig queries is also possible:
 * 
 * <pre>
 * (a : b) AND (c : d)
 * </pre>
 * 
 * matches JSON documents where both twigs occurs.
 * </p>
 * 
 * <h4>Boolean in a Twig node</h4>
 * 
 * <p>
 * The complete Lucene query syntax, e.g., grouping, boolean operators or range
 * queries, can be used to match a single node of a twig. For example, the query
 * 
 * <pre>
 * a : b AND c
 * </pre>
 * 
 * matches JSON documents where the term "a" occurs on the top level node,
 * and with both terms "b" and "c" occurring in a child node.
 * <pre>
 *                             +---+
 *                             | a |
 *                             +-+-+
 *                               |
 *                          +---------+
 *                          | b AND c |
 *                          +---------+
 * </pre>
 * 
 * The twig operator ':' has priority over the boolean operators. Therefore,
 * the query
 * 
 * <pre>
 * a : b AND c : d
 * </pre>
 * 
 * matches documents as in the previous query, with the additional constraint
 * that a child node with an occurrence of the term "d" must be present. It is
 * the same as the query
 * 
 * <pre>
 * a : (b AND c) : d
 * </pre>
 * 
 * <pre>
 *                             +---+
 *                             | a |
 *                             +-+-+
 *                               |
 *                          +---------+
 *                          | b AND c |
 *                          +---------+
 *                               |
 *                             +---+
 *                             | d |
 *                             +-+-+
 * </pre>
 * </p>
 * 
 * <h3>Datatype</h3>
 * 
 * <p>
 * Some terms need to be analyzed in a specific way in order to be correctly
 * indexed and searched, e.g., numbers. For those terms to be searchable, the
 * keyword syntax provides a way to set how a query term should be analyzed.
 * Using a function-like syntax:
 * <pre>
 * datatype( ... )
 * </pre>
 * any query elements inside the parenthesis are processed using the datatype.
 * </p>
 * <p>
 * A mapping from a datatype label to an {@link org.apache.lucene.analysis.Analyzer}
 * is set thanks to configuration key
 * {@link org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys#DATATYPES_ANALYZERS}.
 * </p>
 * <p>
 * For example, I can search for documents where the field contains <code>age</code>,
 * and the values are integers ranging from <code>5</code> to <code>10</code>
 * using the range query below:
 * <pre>
 * age : int( [ 5 TO 50 ] )
 * </pre>
 * The keyword parser in that example is configured to use
 * {@link org.sindice.siren.analysis.IntNumericAnalyzer}.
 * for the datatype <code>int</code>.
 * </p>
 * <p>
 * The top level node of a twig query is by default set to use the datatype
 * {@link org.sindice.siren.util.JSONDatatype#JSON_FIELD}. Any query elements
 * which is not wrapped in a custom datatype uses the datatype
 * {@link org.sindice.siren.util.XSDDatatype#XSD_STRING}.
 * </p>
 * 
 * <h2>Query Examples</h2>
 *
 * <h3>Node query</h3>
 *
 * Match all the documents with one node containing the phrase
 * "Marie Antoinette"
 *
 * <pre>
 * "Marie Antoinette"
 * </pre>
 *
 * <h3>Twig query</h3>
 *
 * Match all the documents with one node containing the term "genre" and with
 * a child node containing the term "Drama".
 *
 * <pre>
 * genre : Drama
 * </pre>
 *
 * Such a twig query is the basic building block to query a particular
 * field name of a JSON object. The field name is always the root of the twig
 * query and the field value is defined as a child clause.
 * <p>
 * More complex twig queries can be constructed by using nested twig queries
 * or using more than one child clause.
 *
 * <pre>
 * director : { last_name : Eastwood , first_name : Clint }
 * </pre>
 *
 * <h3>Boolean Query</h3>
 *
 * Node and twig queries can be combined freely.
 *
 * <pre>
 * (genre : Drama) AND (year : 2010)
 * </pre>
 * 
 */
package org.sindice.siren.qparser.keyword;

