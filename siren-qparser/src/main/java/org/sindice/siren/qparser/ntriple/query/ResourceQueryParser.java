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
package org.sindice.siren.qparser.ntriple.query;

import java.text.NumberFormat;
import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.NumericField.DataType;
import org.apache.lucene.queryParser.standard.StandardQueryParser;
import org.apache.lucene.queryParser.standard.config.NumericConfig;
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
    
    final HashMap<String, NumericConfig> map = new HashMap<String, NumericConfig>();
    map.put("INT", new NumericConfig(4, NumberFormat.getInstance(), DataType.INT));
    map.put("FLOAT", new NumericConfig(4, NumberFormat.getInstance(), DataType.FLOAT));
    this.setNumericConfigMap(map);
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
