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
package org.sindice.siren.qparser.entity.query;

import java.util.Arrays;

import org.apache.commons.lang.NotImplementedException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.util.Version;
import org.sindice.siren.qparser.entity.query.model.AClause;
import org.sindice.siren.qparser.entity.query.model.AVClause;
import org.sindice.siren.qparser.entity.query.model.EClause;
import org.sindice.siren.qparser.entity.query.model.EClauseList;
import org.sindice.siren.qparser.entity.query.model.EQuery;
import org.sindice.siren.qparser.entity.query.model.KClause;
import org.sindice.siren.qparser.entity.query.model.KClauseList;
import org.sindice.siren.qparser.entity.query.model.KQuery;
import org.sindice.siren.qparser.entity.query.model.Query;
import org.sindice.siren.qparser.entity.query.model.VClause;
import org.sindice.siren.qparser.entity.query.model.VisitorAdaptor;
import org.sindice.siren.search.SirenBooleanClause.Occur;
import org.sindice.siren.search.SirenBooleanQuery;
import org.sindice.siren.search.SirenCellQuery;
import org.sindice.siren.search.SirenTermQuery;
import org.sindice.siren.search.SirenTupleQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The visitor for translating the AST into a SIREn Tabular Entity Query.
 * This visitor must traverse the AST with a bottom up approach.
 */
public class TabularEntityQueryBuilder extends VisitorAdaptor {

  /**
   * Holds the mapping between field names and cell ids.
   */
  private final String[] fields;

  private String currentField = null;

  private final Version version;

  private static final
  Logger logger = LoggerFactory.getLogger(VisitorAdaptor.class);

  public TabularEntityQueryBuilder(final Version version, final String[] fields) {
    this.version = version;
    this.fields = fields;
  }

  @Override
  public void visit(final Query query) {
    logger.debug("Query - Enter");
    final BooleanQuery bq = new BooleanQuery();
    query.setQuery(bq);
    query.childrenAccept(this);
    logger.debug("Query - Exit");
  }

  @Override
  public void visit(final EQuery eQuery) {
    logger.debug("EQuery - Enter");
    currentField = eQuery.getField();
    final SirenTupleQuery tq = new SirenTupleQuery();
    eQuery.setQuery(tq);
    eQuery.getEclauses().accept(this);
    ((Query) eQuery.getParent()).getQuery().add(tq, this.toBooleanOccur(eQuery.getOp()));
    logger.debug("EQuery - Exit");
  }

  @Override
  public void visit(final EClauseList eClauseList) {
    logger.debug("EClauseList - Enter");
    eClauseList.childrenAccept(this);
//    for (int i = 0; i < eClauseList.size(); i++) {
//      eClauseList.elementAt(i).accept(this);
//      eClauseList.elementAt(i).accept(this);
//    }
    logger.debug("EClauseList - Exit");
  }

  @Override
  public void visit(final AVClause aVClause) {
    logger.debug("AVClause - Enter");
    if (aVClause.getA().size() == 0) { // no keyword clause.
      final SirenCellQuery cq = new SirenCellQuery();
      aVClause.setQuery(cq);
      aVClause.getV().accept(this);
    }
    else if (aVClause.getA().size() == 1) { // 1 keyword clause = field name
      final SirenCellQuery cq = new SirenCellQuery();
      aVClause.setQuery(cq);
      aVClause.getV().accept(this);

      final String fieldname = aVClause.getA().elementAt(0).getTerm();
      cq.setConstraint(this.fieldLookup(fieldname));
    }
    logger.debug("AVClause - Exit");
  }

  @Override
  public void visit(final AClause aClause) {
    throw new NotImplementedException("Wildcard for value not supported");
  }

  @Override
  public void visit(final VClause vClause) {
    logger.debug("VClause - Enter");
    final SirenCellQuery cq = new SirenCellQuery();
    vClause.setQuery(cq);
    vClause.getV().accept(this);
    logger.debug("VClause - Exit");
  }

  @Override
  public void visit(final KQuery kQuery) {
    logger.debug("KQuery - Enter");
    kQuery.getKclauses().accept(this);
    logger.debug("KQuery - Exit");
  }

  @Override
  public void visit(final KClauseList kClauseList) {
    logger.debug("KClauseList - Enter");

    if (kClauseList.getParent() instanceof EClause) {
      KClause kClause = null;
      SirenTermQuery tq = null;
      final SirenBooleanQuery bq = new SirenBooleanQuery();
      for (int i = 0; i < kClauseList.size(); i++) {
        kClause = kClauseList.elementAt(i);
        tq = new SirenTermQuery(new Term(currentField, kClause.getTerm()));
        bq.add(tq, this.toCellOccur(kClause.getOp()));
        ((EClause) kClauseList.getParent()).getQuery().setQuery(bq);
      }
    }

    logger.debug("KClauseList - Exit");
  }

  private final int fieldLookup(final String fieldname) {
    return Arrays.binarySearch(fields, fieldname);
  }

  private final org.apache.lucene.search.BooleanClause.Occur toBooleanOccur(final int op) {
    switch (op) {
      case org.sindice.siren.qparser.entity.query.model.Operator.PLUS:
        return org.apache.lucene.search.BooleanClause.Occur.MUST;

      case org.sindice.siren.qparser.entity.query.model.Operator.MINUS:
        return org.apache.lucene.search.BooleanClause.Occur.MUST_NOT;

      case org.sindice.siren.qparser.entity.query.model.Operator.NONE:
        return org.apache.lucene.search.BooleanClause.Occur.SHOULD;

      default:
        throw new RuntimeException("Unkown query operator: " + op);
    }
  }

  private final org.sindice.siren.search.SirenTupleClause.Occur toTupleOccur(final int op) {
    switch (op) {
      case org.sindice.siren.qparser.entity.query.model.Operator.PLUS:
        return org.sindice.siren.search.SirenTupleClause.Occur.MUST;

      case org.sindice.siren.qparser.entity.query.model.Operator.MINUS:
        return org.sindice.siren.search.SirenTupleClause.Occur.MUST_NOT;

      case org.sindice.siren.qparser.entity.query.model.Operator.NONE:
        return org.sindice.siren.search.SirenTupleClause.Occur.SHOULD;

      default:
        throw new RuntimeException("Unkown query operator: " + op);
    }
  }

  private final Occur toCellOccur(final int op) {
    switch (op) {
      case org.sindice.siren.qparser.entity.query.model.Operator.PLUS:
        return Occur.MUST;

      case org.sindice.siren.qparser.entity.query.model.Operator.MINUS:
        return Occur.MUST_NOT;

      case org.sindice.siren.qparser.entity.query.model.Operator.NONE:
        return Occur.SHOULD;

      default:
        throw new RuntimeException("Unkown query operator: " + op);
    }
  }

}
