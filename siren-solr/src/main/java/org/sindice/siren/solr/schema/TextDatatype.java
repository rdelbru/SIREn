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

package org.sindice.siren.solr.schema;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;

/**
 * <code>TextDatatype</code> is the basic datatype implementation for
 * configurable text analysis.
 * <p>
 * Analyzers for datatypes using this implementation should be defined in the
 * datatype configuration file.
 */
public class TextDatatype extends Datatype {

  @Override
  protected void init(final Map<String,String> args) {
    super.init(args);
  }

  @Override
  public void setAnalyzer(final Analyzer analyzer) {
    this.analyzer = analyzer;
  }

  @Override
  public void setQueryAnalyzer(final Analyzer analyzer) {
    this.queryAnalyzer = analyzer;
  }

}

