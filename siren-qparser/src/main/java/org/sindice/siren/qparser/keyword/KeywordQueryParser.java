/**
 * Copyright 2010, 2011, Renaud Delbru
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
 * @author Renaud Delbru [ 21 May 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010, 2011, by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.standard.StandardQueryParser;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler;
import org.sindice.siren.qparser.keyword.query.KeywordQueryNodeProcessorPipeline;

/**
 * Parse and creates a query of a boolean expression of keyword terms
 */
public class KeywordQueryParser
extends StandardQueryParser {

  /**
   * Constructs a {@link KeywordQueryParser} object.
   */
  public KeywordQueryParser() {
    super();
    this.setQueryConfigHandler(new KeywordQueryConfigHandler());
    this.setQueryNodeProcessor(new KeywordQueryNodeProcessorPipeline(this.getQueryConfigHandler()));
  }

  /**
   * Constructs a {@link KeywordQueryParser} object and sets an
   * {@link Analyzer} to it. The same as:
   *
   * <ul>
   * KeywordQueryParser qp = new KeywordQueryParser();
   * qp.getQueryConfigHandler().setAnalyzer(analyzer);
   * </ul>
   *
   * @param analyzer
   *          the analyzer to be used by this query parser helper
   */
  public KeywordQueryParser(final Analyzer analyzer) {
    this();

    this.setAnalyzer(analyzer);
  }

  @Override
  public String toString(){
    return "<KeywordQueryParser config=\"" + this.getQueryConfigHandler() + "\"/>";
  }

}
