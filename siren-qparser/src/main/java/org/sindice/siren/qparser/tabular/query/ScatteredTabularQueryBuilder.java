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
 * @author Renaud Delbru [ 29 Oct 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.tabular.query;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;
import org.sindice.siren.qparser.ntriple.query.ScatteredNTripleQueryBuilder;
import org.sindice.siren.qparser.ntriple.query.model.Literal;
import org.sindice.siren.qparser.ntriple.query.model.LiteralPattern;
import org.sindice.siren.qparser.ntriple.query.model.TriplePattern;
import org.sindice.siren.qparser.ntriple.query.model.URIPattern;
import org.sindice.siren.qparser.ntriple.query.model.Wildcard;
import org.sindice.siren.search.SirenCellQuery;
import org.sindice.siren.search.SirenPrimitiveQuery;
import org.sindice.siren.search.SirenTupleQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The visitor for translating the AST into a Siren NTriple Query.
 * This visitor must traverse the AST with a bottom up approach.
 */
public class ScatteredTabularQueryBuilder extends ScatteredNTripleQueryBuilder {

  private static final
  Logger logger = LoggerFactory.getLogger(ScatteredNTripleQueryBuilder.class);
  
  /**
   * @param matchVersion
   * @param boosts
   * @param datatypeConfigs
   */
  public ScatteredTabularQueryBuilder(Version matchVersion,
                                      Map<String, Float> boosts,
                                      Map<String, Map<String, Analyzer>> datatypeConfigs) {
    super(matchVersion, boosts, datatypeConfigs);
  }

  /**
   * Create a SirenTupleQuery
   */
  @Override
  public void visit(final TriplePattern tp) {
    logger.debug("Visiting TriplePattern - Enter");

    final BooleanQuery bq = new BooleanQuery(true);

    if (!this.hasError()) {
      for (final String fieldName : boosts.keySet()) {
        final SirenTupleQuery tupleQuery = new SirenTupleQuery();
        this.visitSubject(tp, tupleQuery, fieldName);
        this.visitPredicate(tp, tupleQuery, fieldName);
        this.visitObject(tp, tupleQuery, fieldName);
        tupleQuery.setBoost(boosts.get(fieldName));
        bq.add(tupleQuery, Occur.SHOULD);
      }
    }

    tp.setQuery(bq);

    logger.debug("Visiting TriplePattern - Exit");
  }
  
  private void visitSubject(final TriplePattern tp,
                            final SirenTupleQuery tupleQuery,
                            final String fieldName) {
    SirenCellQuery cellQuery = null;
    if (tp.getS() != null && !(tp.getS() instanceof Wildcard)) {
      // we should always receive a SirenPrimitiveQuery
      cellQuery = new SirenCellQuery((SirenPrimitiveQuery) tp.getS().getQueries().get(fieldName));
      cellQuery.setConstraint(tp.getS().getUp().getCellConstraint());
      tupleQuery.add(cellQuery, org.sindice.siren.search.SirenTupleClause.Occur.MUST);
    }
  }

  private void visitPredicate(final TriplePattern tp,
                              final SirenTupleQuery tupleQuery,
                              final String fieldName) {
    SirenCellQuery cellQuery = null;
    if (tp.getP() != null && !(tp.getP() instanceof Wildcard)) {
      // we should always receive a SirenPrimitiveQuery
      cellQuery = new SirenCellQuery((SirenPrimitiveQuery) tp.getP().getQueries().get(fieldName));
      cellQuery.setConstraint(tp.getP().getUp().getCellConstraint());
      tupleQuery.add(cellQuery, org.sindice.siren.search.SirenTupleClause.Occur.MUST);
    }
  }

  private void visitObject(final TriplePattern tp,
                           final SirenTupleQuery tupleQuery,
                           final String fieldName) {
    SirenCellQuery cellQuery = null;
    if (tp.getO() != null && !(tp.getO() instanceof Wildcard)) {
      // we should always receive a SirenPrimitiveQuery
      cellQuery = new SirenCellQuery((SirenPrimitiveQuery) tp.getO().getQueries().get(fieldName));
      if (tp.getO() instanceof Literal) {
        cellQuery.setConstraint(tp.getO().getL().getCellConstraint());
      } else if (tp.getO() instanceof LiteralPattern) {
        cellQuery.setConstraint(tp.getO().getLp().getCellConstraint());
      } else if (tp.getO() instanceof URIPattern) {
        cellQuery.setConstraint(((URIPattern) tp.getO()).getUp().getCellConstraint());
      }
      tupleQuery.add(cellQuery, org.sindice.siren.search.SirenTupleClause.Occur.MUST);
    }
  }

}
