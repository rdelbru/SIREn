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
 * Analyzer for indexing JSON content.
 *
 * <h2>Introduction</h2>
 *
 * This package extends the Lucene's analysis API to provide support for
 * parsing and indexing JSON content. For an introduction to Lucene's analysis
 * API, see the {@link org.apache.lucene.analysis} package documentation.
 *
 *
 * <h2>Overview of the API</h2>
 *
 * This package contains concrete components
 * ({@link org.apache.lucene.util.Attribute}s,
 * {@link org.apache.lucene.analysis.Tokenizer}s and
 * {@link org.apache.lucene.analysis.TokenFilter}s) for analyzing different
 * JSON content.
 * <p>
 * It also provides a pre-built JSON analyzer
 * {@link org.sindice.siren.analysis.JsonAnalyzer} that you can use to get
 * started quickly.
 * <p>
 * It also contains a number of
 * {@link org.sindice.siren.analysis.NumericAnalyzer}s that are used for
 * supporting datatypes.
 * <p>
 * The SIREn's analysis API is divided into several packages:
 * <ul>
 * <li><b>{@link org.sindice.siren.analysis.attributes}</b> contains a number of
 * {@link org.apache.lucene.util.Attribute}s that are used to add metadata
 * to a stream of tokens.
 * <li><b>{@link org.sindice.siren.analysis.filter}</b> contains a number of
 * {@link org.apache.lucene.analysis.TokenFilter}s that alter incoming tokens.
 * </ul>
 *
 * <h2>JSON Analyzer</h2>
 *
 * The {@link org.sindice.siren.analysis.JsonTokenizer JSON tokenizer} parses
 * the JSON data and converts it into an abstract tree model. The conversion
 * is performed in a streaming mode during the parsing.
 *
 * <p>
 *
 * The tokenizer traverses the JSON tree using a depth-first search approach.
 * During the traversal of the tree, the tokenizer increments the dewey code
 * (i.e., node label) whenever an object, an array, a field or a value
 * is encountered. The tokenizer attaches to any token generated the current
 * node label using the
 * {@link org.sindice.siren.analysis.attributes.NodeAttribute}.
 *
 * <p>
 *
 * The tokenizer attaches also a datatype metadata to any token generated using
 * the {@link org.sindice.siren.analysis.attributes.DatatypeAttribute}.
 * A datatype specifies the type of the data a node contains. By default, the
 * tokenizer differentiates five datatypes in the JSON syntax:
 *
 * <ul>
 * <li> {@link org.sindice.siren.util.XSDDatatype#XSD_STRING}
 * <li> {@link org.sindice.siren.util.XSDDatatype#XSD_LONG}
 * <li> {@link org.sindice.siren.util.XSDDatatype#XSD_DOUBLE}
 * <li> {@link org.sindice.siren.util.XSDDatatype#XSD_BOOLEAN}
 * <li> {@link org.sindice.siren.util.JSONDatatype#JSON_FIELD}
 * </ul>
 *
 * The datatype metadata is used to perform an appropriate analysis of the
 * content of a node. Such analysis is performed by the
 * {@link org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter}. The
 * analysis of each datatype can be configured freely by the user using the
 * method
 * {@link org.sindice.siren.analysis.JsonAnalyzer#registerDatatype(char[], org.apache.lucene.analysis.Analyzer)}.
 *
 * <p>
 * 
 * Custom datatypes can also be used thanks to a specific datatype JSON object.
 * The schema of the datatype JSON object is the following:
 * <pre>
 * {
 *   "_datatype_" : &lt;LABEL&gt;,
 *   "_value_" : &lt;VALUE&gt;
 * }
 * </pre>
 * <code>&lt;LABEL&gt;</code> is a string which represents the name of the datatype to be
 * applied on the associated value.
 * <code>&lt;VALUE&gt;</code> is the associated value and is a string.
 * The datatype JSON object is a way for passing custom datatypes to SIREn.
 * It does not have influence on the label of the value node.
 * For example, the label (i.e., <code>0.0</code>) to the value <code>b</code> below:
 * <pre>
 * {
 *   "a" : "b"
 * }
 * </pre>
 * is the same for the value <code>b</code> with a custom datatype:
 * <pre>
 * {
 *   "a" : {
 *     "_datatype_" : "my datatype",
 *     "_value_" : "b"
 *   }
 * }
 * </pre>
 * 
 * <p>
 *
 * The Lucene's
 * {@link org.apache.lucene.analysis.tokenattributes.PayloadAttribute payload}
 * interface is used by SIREn to encode information such as the node label and
 * the position of the token. This payload is then decoded by the
 * {@link org.sindice.siren.index index API} and encoded back into the node-based
 * inverted index data structure.
 *
 */
package org.sindice.siren.analysis;

