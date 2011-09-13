/**
 * Copyright 2010, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 23 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.ntriple.query;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.standard.StandardQueryParser;
import org.sindice.siren.qparser.ntriple.query.builders.ResourceQueryTreeBuilder;
import org.sindice.siren.qparser.ntriple.query.processors.ResourceQueryNodeProcessorPipeline;


public class ResourceQueryParser extends StandardQueryParser {

  /**
   * Constructs a {@link ResourceQueryParser} object. The same as:
   * <ul>
   * StandardQueryParser qph = new StandardQueryParser(literalAnalyzer);
   * qph.setQueryBuilder(new ResourceQueryTreeBuilder());
   * qph.setQueryNodeProcessor(new ResourceQueryNodeProcessorPipeline(qph.getQueryConfigHandler()));
   * <ul>
   */
  public ResourceQueryParser() {
    super();
    this.setQueryNodeProcessor(new ResourceQueryNodeProcessorPipeline(this.getQueryConfigHandler()));
    this.setQueryBuilder(new ResourceQueryTreeBuilder());
  }

  /**
   * Constructs a {@link ResourceQueryParser} object and sets an
   * {@link Analyzer} to it. The same as:
   *
   * <ul>
   * ResourceQueryParser qp = new ResourceQueryParser();
   * qp.getQueryConfigHandler().setAnalyzer(analyzer);
   * </ul>
   *
   * @param analyzer
   *          the analyzer to be used by this query parser helper
   */
  public ResourceQueryParser(final Analyzer analyzer) {
    this();
    this.setAnalyzer(analyzer);
  }

}
