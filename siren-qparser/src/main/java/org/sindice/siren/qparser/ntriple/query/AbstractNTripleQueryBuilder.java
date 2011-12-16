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
 * @project siren
 * @author Renaud Delbru [ 29 Oct 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.ntriple.query;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.NumericAnalyzer;
import org.sindice.siren.qparser.ntriple.query.model.LiteralPattern;
import org.sindice.siren.qparser.ntriple.query.model.VisitorAdaptor;
import org.sindice.siren.qparser.tuple.QueryBuilderException;
import org.sindice.siren.qparser.tuple.ResourceQueryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The visitor for translating the AST into a Siren NTriple Query.
 */
public abstract class AbstractNTripleQueryBuilder extends VisitorAdaptor {

  /**
   * Analyzer used on a {@link LiteralPattern}, in the case of a numeric query.
   */
  private final WhitespaceAnalyzer wsAnalyzer;

  /**
   * The default operator to use in the inner parsers
   */
  DefaultOperatorAttribute.Operator defaultOp = DefaultOperatorAttribute.Operator.AND;

  /**
   * Exception handling during building a query
   */
  private QueryBuilderException     queryException    = null;

  private static final
  Logger logger = LoggerFactory.getLogger(AbstractNTripleQueryBuilder.class);

  public AbstractNTripleQueryBuilder(final Version matchVersion) {
    this.wsAnalyzer = new WhitespaceAnalyzer(matchVersion);
  }

  public boolean hasError() {
    return queryException != null &&
      queryException.getError() != QueryBuilderException.Error.NO_ERROR;
  }

  public String getErrorDescription() {
    return queryException.toString();
  }

  public void setDefaultOperator(final DefaultOperatorAttribute.Operator op) {
    defaultOp = op;
  }

  /**
   * Convert the given exception into a {@link QueryBuilderException} and inform
   * the query builder.
   */
  protected void createQueryException(final Exception e) {
    if (queryException == null) {
      String message = null;
      if (e.getCause() != null) {
        message = e.getCause().getMessage();
      }
      else {
        message = e.getMessage();
      }
      queryException = new QueryBuilderException(QueryBuilderException.Error.PARSE_ERROR,
        message, e.getStackTrace());
    }
  }

  /**
   * Instantiate a {@link ResourceQueryParser} depending on the object type.
   * Then, set the default operator.
   */
  protected ResourceQueryParser getResourceQueryParser(final Analyzer analyzer) {
    final ResourceQueryParser qph;
    
    if (analyzer instanceof NumericAnalyzer)
      qph = new ResourceQueryParser(wsAnalyzer, (NumericAnalyzer) analyzer);
    else
      qph = new ResourceQueryParser(analyzer);
    qph.setDefaultOperator(defaultOp);
    return qph;
  }

}
