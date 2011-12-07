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
 * @author Renaud Delbru [ 25 Apr 2008 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.tabular.query;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;
import org.sindice.siren.qparser.ntriple.query.SimpleNTripleQueryBuilder;
import org.sindice.siren.qparser.ntriple.query.model.Literal;
import org.sindice.siren.qparser.ntriple.query.model.LiteralPattern;
import org.sindice.siren.qparser.ntriple.query.model.TriplePattern;
import org.sindice.siren.qparser.ntriple.query.model.URIPattern;
import org.sindice.siren.qparser.ntriple.query.model.Wildcard;
import org.sindice.siren.search.SirenCellQuery;
import org.sindice.siren.search.SirenPrimitiveQuery;
import org.sindice.siren.search.SirenTupleClause;
import org.sindice.siren.search.SirenTupleQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The visitor for translating the AST into a Siren NTriple Query.
 * This visitor must traverse the AST with a bottom up approach.
 */
public class SimpleTabularQueryBuilder extends SimpleNTripleQueryBuilder {

  private static final Logger logger = LoggerFactory.getLogger(SimpleTabularQueryBuilder.class);
  
  /**
   * @param matchVersion
   * @param field
   * @param datatypeConfig
   */
  public SimpleTabularQueryBuilder(Version matchVersion,
                                   String field,
                                   Map<String, Analyzer> datatypeConfig) {
    super(matchVersion, field, datatypeConfig);
  }

  /**
   * Create a SirenTupleQuery
   */
  @Override
  public void visit(final TriplePattern tp) {
    logger.debug("Visiting TriplePattern - Enter");

    final SirenTupleQuery tupleQuery = new SirenTupleQuery();

    if (!this.hasError()) {
      SirenCellQuery cellQuery = null;

      // Subject
      if (tp.getS() != null && !(tp.getS() instanceof Wildcard)) {
        // we should always receive a SirenPrimitiveQuery
        cellQuery = new SirenCellQuery((SirenPrimitiveQuery) tp.getS().getQuery());
        cellQuery.setConstraint(tp.getS().getUp().getCellConstraint());
        tupleQuery.add(cellQuery, SirenTupleClause.Occur.MUST);
      }

      // Predicate
      if (tp.getP() != null && !(tp.getP() instanceof Wildcard)) {
        // we should always receive a SirenPrimitiveQuery
        cellQuery = new SirenCellQuery((SirenPrimitiveQuery) tp.getP().getQuery());
        cellQuery.setConstraint(tp.getP().getUp().getCellConstraint());
        tupleQuery.add(cellQuery, SirenTupleClause.Occur.MUST);
      }

      // Object
      if (tp.getO() != null && !(tp.getO() instanceof Wildcard)) {
        // we should always receive a SirenPrimitiveQuery
        cellQuery = new SirenCellQuery((SirenPrimitiveQuery) tp.getO().getQuery());
        if (tp.getO() instanceof Literal) {
          cellQuery.setConstraint(tp.getO().getL().getCellConstraint());
        } else if (tp.getO() instanceof LiteralPattern) {
          cellQuery.setConstraint(tp.getO().getLp().getCellConstraint());
        } else if (tp.getO() instanceof URIPattern) {
          cellQuery.setConstraint(((URIPattern) tp.getO()).getUp().getCellConstraint());
        }
        tupleQuery.add(cellQuery, SirenTupleClause.Occur.MUST);
      }
    }

    tp.setQuery(tupleQuery);
    logger.debug("Visiting TriplePattern - Exit");
  }

}
