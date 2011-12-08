/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 23 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.tuple;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.core.config.ConfigurationKey;
import org.apache.lucene.queryParser.standard.StandardQueryParser;
import org.sindice.siren.analysis.NumericAnalyzer;
import org.sindice.siren.qparser.tuple.query.builders.ResourceQueryTreeBuilder;
import org.sindice.siren.qparser.tuple.query.processors.ResourceQueryNodeProcessorPipeline;

public class ResourceQueryParser extends StandardQueryParser {

  final public static class SirenConfigurationKeys {
    
    /**
     * Key used to set a token to its numeric datatype.
     */
    final public static ConfigurationKey<NumericAnalyzer> NUMERIC_ANALYZERS = ConfigurationKey.newInstance();
    
  }
  
  /**
   * Constructs a {@link ResourceQueryParser} object. The same as:
   * <ul>
   * StandardQueryParser qph = new StandardQueryParser();
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
  
  /**
   * Constructs a {@link ResourceQueryParser} object and sets a NumericAnalyzer
   * that will be used to parse numeric queries. The same as:
   *
   * <ul>
   * ResourceQueryParser qp = new ResourceQueryParser(analyzer);
   * qp.getQueryConfigHandler().set();
   * </ul>
   * 
   * @param analyzer
   * @param na
   */
  public ResourceQueryParser(final Analyzer analyzer, final NumericAnalyzer na) {
    this(analyzer);
    this.getQueryConfigHandler().set(SirenConfigurationKeys.NUMERIC_ANALYZERS, na);
  }

}
