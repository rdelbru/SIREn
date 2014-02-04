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
 * Set of parameters for configuring the behaviour of the query processing.
 * <p>
 * The processing of a query can be configured using a {@link org.apache.lucene.queryparser.flexible.core.config.ConfigurationKey}.
 * For example, the parameter {@link org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys#DEFAULT_OPERATOR}
 * sets the default {@link org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator} to use
 * when none is explicitly used in the query. In addition to the parameters of
 * the Lucene framework {@link org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys},
 * the keyword parser provides its own set of parameters in {@link org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys}.
 * </p>
 * 
 */
package org.sindice.siren.qparser.keyword.config;

