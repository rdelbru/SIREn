/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
 * @project siren-solr
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.util.EscapeLuceneCharacters;

/**
 * Underlying implementation of the parser for the simple query language.
 */
public class KeywordQParserImpl {

  private final KeywordQueryParser parser = new KeywordQueryParser();
  
  /**
   * Flag for disabling field query
   */
  boolean disableField = false;

  /**
   * If disableField is false, field queries are not possible. Special characters
   * within URIs are escaped.
   * @param analyzer
   * @param boosts
   * @param disableField
   */
  public KeywordQParserImpl(final Analyzer analyzer,
                            final Map<String, Float> boosts,
                            final boolean disableField) {
    final String[] fields = boosts.keySet().toArray(new String[0]);
    this.disableField = disableField;

    parser.setAnalyzer(analyzer);
    parser.setMultiFields(fields);
    parser.setFieldsBoost(boosts);
  }

  public Query parse(final String query) throws ParseException {
    try {
      if (disableField) {
        return parser.parse(EscapeLuceneCharacters.escape(query), null);
      }
      else { // If field queries enabled, escape only URIs
        return parser.parse(EscapeLuceneCharacters.escapeURIs(query), null);
      }
    }
    catch (final QueryNodeException e) {
      throw new ParseException("Invalid keyword query");
    }
  }

  public void setDefaultOperator(final Operator operator) {
    if (operator == Operator.OR) {
      parser.setDefaultOperator(DefaultOperatorAttribute.Operator.OR);
    }
    else {
      parser.setDefaultOperator(DefaultOperatorAttribute.Operator.AND);
    }
  }

}

