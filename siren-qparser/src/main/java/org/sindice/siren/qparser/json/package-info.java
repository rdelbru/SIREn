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
 * A JSON query parser implemented with Jackson and the Lucene's Flexible Query
 * Parser.
 *
 * <h2>Query Parser Syntax</h2>
 *
 * <p>
 * A JSON Query is composed of one top-level object, which can be either:
 * <ul>
 * <li>a node (<code>node</code>) object that represents a
 * {@link NodeBooleanQuery} or a {@link NodePrimitiveQuery}; or
 * <li>a twig (<code>twig</code>) object that represents a {@link TwigQuery}; or
 * <li>a boolean (<code>boolean</code>) object that represents a
 * {@link BooleanQuery} of node and twig query objects.
 * </ul>
 *
 * The node and twig query object is composed of node boolean query expressions.
 * A node boolean query expression must follow the syntax supported by the
 * {@link KeywordQueryParser}. Examples of appropriately formatted keyword
 * queries can be found in the <a
 * href="{@docRoot}/org/sindice/siren/qparser/keyword/package-summary.html#package_description">
 * keyword query syntax documentation</a>.
 *
 * <h3>Node</h3>
 *
 * A node object is always prefixed by the field name "node" and follows the
 * schema:
 *
 * <pre>
 *   "node" : {
 *
 *     "query" : String,    // Node boolean query expression - Mandatory
 *
 *     "level" : int,       // Node level constraint - Optional
 *
 *     "range" : [int, int] // Node range constraint - Optional
 *
 *   }
 * </pre>
 *
 * <h3>Twig</h3>
 *
 * A twig object is always prefixed by the field name "twig" and follows the
 * schema:
 *
 * <pre>
 *   "twig" : {
 *
 *     "root"  : String,         // Node boolean query expression - Optional
 *
 *     "level" : int,            // Node level constraint - Optional
 *
 *     "range" : [int, int]      // Node range constraint - Optional
 *
 *     "child" : [ Clause ]      // Array of child clauses - Optional
 *
 *     "descendant" : [ Clause ] // Array of descendant clauses - Optional
 *
 *   }
 * </pre>
 *
 * <h3>Boolean</h3>
 *
 * A boolean object is always prefixed by the field name "boolean" and follows
 * the schema:
 *
 * <pre>
 *   "boolean" : [ Clause ] // Array of clauses - Mandatory
 * </pre>
 *
 * <h3>Clause</h3>
 *
 * A clause object follows the schema:
 *
 * <pre>
 *   {
 *
 *     "occur" : String,   // Boolean operator (MUST, MUST_NOT, SHOULD) - Mandatory
 *
 *     // With either a node object
 *     "node"  : { ... },  // Node object - Mandatory
 *
 *     // Or a twig object
 *     "twig" : { ... },   // Twig object - Mandatory
 *
 *     // A level field is mandatory only for descendant clauses
 *     "level" : int
 *
 *   }
 * </pre>
 *
 * <h2>Query Examples</h2>
 *
 * <h3>Node query</h3>
 *
 * Match all the documents with one node containing the phrase
 * "Marie Antoinette"
 *
 * <pre>
 * {
 *   "node" : {
 *     "query" : "\"Marie Antoinette\""
 *   }
 * }
 * </pre>
 *
 * <h3>Twig query</h3>
 *
 * Match all the documents with one node containing the term "genre" and with
 * a child node containing the term "Drama".
 *
 * <pre>
 * {
 *   "twig" : {
 *     "root" : "genre",
 *     "child" : [ {
 *       "occur" : "MUST",
 *       "node" : {
 *         "query" : "Drama"
 *       }
 *     } ]
 *   }
 * }
 * </pre>
 *
 * Such a twig query is the basic building block to query to a particular
 * field name of a JSON object. The field name is always the root of the twig
 * query and the field value is defined as a child clause.
 * <p>
 * More complex twig queries can be constructed by using nested twig queries
 * or using more than one child (or descendant) clause.
 *
 * <pre>
 * {
 *   "twig" : {
 *     "root" : "director",
 *     "child" : [ {
 *       "occur" : "MUST",
 *       "twig" : {
 *         "child" : [ {
 *           "occur" : "MUST",
 *           "twig" : {
 *             "root" : "last_name",
 *             "child" : [ {
 *               "occur" : "MUST",
 *               "node" : {
 *                 "query" : "Eastwood"
 *               }
 *             } ]
 *           }
 *         }, {
 *           "occur" : "MUST",
 *           "twig" : {
 *             "root" : "first_name",
 *             "child" : [ {
 *               "occur" : "MUST",
 *               "node" : {
 *                 "query" : "Clint"
 *               }
 *             } ]
 *           }
 *         } ]
 *       }
 *     } ]
 *   }
 * }
 * </pre>
 *
 * <h3>Boolean Query</h3>
 *
 * Node and twig queries can be combined freely using the boolean query object
 * to create a boolean combination of node and twig queries.
 *
 * <pre>
 * {
 *   "boolean" : [ {
 *     "occur" : "MUST",
 *     "twig" : {
 *       "root" : "genre",
 *       "child" : [ {
 *         "occur" : "MUST",
 *         "node" : {
 *           "query" : "Drama"
 *         }
 *       } ]
 *     }
 *   }, {
 *     "occur" : "MUST",
 *     "twig" : {
 *       "root" : "year",
 *       "child" : [ {
 *         "occur" : "MUST",
 *         "node" : {
 *           "query" : "2010"
 *         }
 *       } ]
 *     }
 *   } ]
 * }
 * </pre>
 *
 * <h2>Query Builder DSL</h2>
 *
 * The package provides also a simple query builder to easily create JSON
 * queries programmatically. See {@link org.sindice.siren.qparser.json.dsl.QueryBuilder}.
 *
 */
package org.sindice.siren.qparser.json;

