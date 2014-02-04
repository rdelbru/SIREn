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
 * Token's filters used for term processing, URI processing and JSON indexing.
 *
 * <h2>Introduction</h2>
 *
 * This package contains a number of token's filters that are used to
 * pre-process URI tokens or to index JSON data.
 *
 * <h3>JSON Indexing</h3>
 *
 * <ul>
 * <li> {@link org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter}
 * analyzes the token given its datatype.
 * <li> {@link org.sindice.siren.analysis.filter.PositionAttributeFilter}
 * computes the relative position of the token within a node.
 * <li> {@link org.sindice.siren.analysis.filter.SirenPayloadFilter} encodes
 * the node information into the token payload.
 * </ul>
 *
 * <h3>URI Processing</h3>
 *
 * <ul>
 * <li> {@link org.sindice.siren.analysis.filter.URIDecodingFilter} decodes
 * special URI characters.
 * <li> {@link org.sindice.siren.analysis.filter.URILocalnameFilter} extracts
 * the local name of a URI.
 * <li> {@link org.sindice.siren.analysis.filter.URINormalisationFilter} breaks
 * an URI into smaller components.
 * <li> {@link org.sindice.siren.analysis.filter.URITrailingSlashFilter} removes
 * the trailing slash of a URI.
 * <li> {@link org.sindice.siren.analysis.filter.MailtoFilter} extracts the mail
 * address from a mailto URI scheme.
 * </ul>
 *
 * <h3>Term Processing</h3>
 *
 * <ul>
 * <li> {@link org.sindice.siren.analysis.filter.ASCIIFoldingExpansionFilter}
 * expands tokens containing diacritical mark and other special characters.
 * </ul>
 *
 */
package org.sindice.siren.analysis.filter;


