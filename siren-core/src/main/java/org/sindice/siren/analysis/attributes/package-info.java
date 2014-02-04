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
 * Token's attributes used during JSON indexing
 *
 * <h2>Introduction</h2>
 *
 * This package contains a number of token's attributes that are used to attach
 * metadata to a token such as:
 * <ul>
 * <li> {@link org.sindice.siren.analysis.attributes.NodeAttribute} stores
 * the dewey code of the node from which this token comes from.
 * <li> {@link org.sindice.siren.analysis.attributes.PositionAttribute} stores
 * the position of the token relative to a node.
 * <li> {@link org.sindice.siren.analysis.attributes.NodeNumericTermAttribute}
 * stores a numeric value and is used by
 * {@link org.sindice.siren.analysis.NumericTokenizer} for indexing numeric
 * values.
 * <li> {@link org.sindice.siren.analysis.attributes.DatatypeAttribute} stores
 * the datatype of the node from which this token comes from.
 * </ul>
 *
 */
package org.sindice.siren.analysis.attributes;


