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
 * Abstraction over the encoding and decoding of the node-based inverted index
 * data structure.
 *
 * <h2>Introduction</h2>
 *
 * This package contains the low-level API for encoding and decoding
 * node information from the inverted index data structure. For an introduction
 * to Lucene's index API, see the {@link org.apache.lucene.index} package
 * documentation.
 *
 * <h2>Overview of the API</h2>
 *
 * This package defines the interface
 * {@link org.sindice.siren.index.DocsNodesAndPositionsEnum} to iterates over
 * the list of document identifiers, node frequencies, node labels, term
 * frequencies and term positions for a term.
 * <p>
 * It also provides a number of extensions that are used:
 * <ul>
 * <li> to filter entries based on node constraints (level or interval
 * constraints);
 * <li> to iterate over multiple segments during query processing or merging.
 * </ul>
 * <p>
 * The SIREn's index API is composed of two sub-packages:
 * <ul>
 * <li><b>{@link org.sindice.siren.index.codecs.block}</b> defines the abstract
 * API for encoding and decoding block-based posting formats.
 * <li><b>{@link org.sindice.siren.index.codecs.siren10}</b> contains the
 * implementation of the SIREn block-based posting format.
 * </ul>
 *
 */
package org.sindice.siren.index;

