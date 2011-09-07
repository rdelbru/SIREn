/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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
