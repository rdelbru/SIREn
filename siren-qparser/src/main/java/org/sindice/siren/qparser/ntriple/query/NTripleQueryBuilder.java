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
 * @author Renaud Delbru [ 25 Apr 2008 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.ntriple.query;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.core.QueryNodeException;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanClause.Occur;
import org.sindice.siren.qparser.ntriple.query.QueryBuilderException.Error;
import org.sindice.siren.qparser.ntriple.query.model.BinaryClause;
import org.sindice.siren.qparser.ntriple.query.model.ClauseQuery;
import org.sindice.siren.qparser.ntriple.query.model.EmptyQuery;
import org.sindice.siren.qparser.ntriple.query.model.Literal;
import org.sindice.siren.qparser.ntriple.query.model.LiteralPattern;
import org.sindice.siren.qparser.ntriple.query.model.NestedClause;
import org.sindice.siren.qparser.ntriple.query.model.Operator;
import org.sindice.siren.qparser.ntriple.query.model.QueryExpression;
import org.sindice.siren.qparser.ntriple.query.model.SimpleExpression;
import org.sindice.siren.qparser.ntriple.query.model.TriplePattern;
import org.sindice.siren.qparser.ntriple.query.model.URIPattern;
import org.sindice.siren.qparser.ntriple.query.model.UnaryClause;
import org.sindice.siren.qparser.ntriple.query.model.VisitorAdaptor;
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
public class NTripleQueryBuilder extends VisitorAdaptor implements QueryBuilderException.Exception {

  /**
   * Lucene's field to query
   */
  String field;

  /**
   * The inner query analyzers for processing URI and Literal patterns
   */
  Analyzer uriAnalyzer;
  Analyzer literalAnalyzer;

  /**
   * The default operator to use in the inner parsers
   */
  DefaultOperatorAttribute.Operator defaultOp         = DefaultOperatorAttribute.Operator.AND;

  /**
   * Exception handling during building a query
   */
  private QueryBuilderException     queryException    = null;

  private static final Logger logger = LoggerFactory.getLogger(VisitorAdaptor.class);

  public NTripleQueryBuilder(final String field,
                             final Analyzer uriAnalyzer,
                             final Analyzer literalAnalyzer) {
    this.field = field;
    this.uriAnalyzer = uriAnalyzer;
    this.literalAnalyzer = literalAnalyzer;
  }

  public void setDefaultOperator(final DefaultOperatorAttribute.Operator op) {
    defaultOp = op;
  }

  @Override
  public boolean hasError() {
    return queryException != null && queryException.getError() != Error.NO_ERROR;
  }

  @Override
  public String getErrorDescription() {
    return queryException.toString();
  }

  @Override
  public void visit(final ClauseQuery q) {
    logger.debug("ClauseQuery - Enter");
    q.setQuery(q.getC().getQuery());
    logger.debug("ClauseQuery - Exit");
  }

  /**
   * Create an empty BooleanQuery
   */
  @Override
  public void visit(final EmptyQuery q) {
    logger.debug("EmptyQuery - Enter");
    q.setQuery(new BooleanQuery(true));
    logger.debug("EmptyQuery - Exit");
  }

  @Override
  public void visit(final UnaryClause c) {
    logger.debug("Enter UnaryClause");
    c.setQuery(c.getExpr().getQuery());
    logger.debug("Exit UnaryClause");
  }

  @Override
  public void visit(final NestedClause c) {
    logger.debug("Enter NestedClause");
    c.setQuery(this.translate(c.getLhc().getQuery(), c.getOp(), c.getRhe().getQuery()));
    logger.debug("Exit NestedClause");
  }

  @Override
  public void visit(final BinaryClause c) {
    logger.debug("Enter BinaryClause");
    c.setQuery(this.translate(c.getLhe().getQuery(), c.getOp(), c.getRhe().getQuery()));
    logger.debug("Exit BinaryClause");
  }

  private Query translate(final Query l, final int op, final Query r) {
    logger.debug("Enter BinaryClause");
    final BooleanQuery query = new BooleanQuery();

    switch (op) {
      case Operator.AND:
        logger.debug("{} AND {}", l.toString(), r.toString());
        query.add(l, Occur.MUST);
        query.add(r, Occur.MUST);
        break;
      case Operator.OR:
        logger.debug("{} OR {}", l.toString(), r.toString());
        query.add(l, Occur.SHOULD);
        query.add(r, Occur.SHOULD);
        break;
      case Operator.MINUS:
        logger.debug("{} MINUS {}", l.toString(), r.toString());
        query.add(l, Occur.MUST);
        query.add(r, Occur.MUST_NOT);
        break;
      default:
        break;
    }
    return query;
  }

  @Override
  public void visit(final SimpleExpression simpleExpression) {
    simpleExpression.setQuery(simpleExpression.getTp().getQuery());
  }

  @Override
  public void visit(final QueryExpression queryExpression) {
    queryExpression.setQuery(queryExpression.getQ().getQuery());
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
        cellQuery.setConstraint(0);
        tupleQuery.add(cellQuery,
          org.sindice.siren.search.SirenTupleClause.Occur.MUST);
      }

      // Predicate
      if (tp.getP() != null && !(tp.getP() instanceof Wildcard)) {
        // we should always receive a SirenPrimitiveQuery
        cellQuery = new SirenCellQuery((SirenPrimitiveQuery) tp.getP().getQuery());
        cellQuery.setConstraint(1);
        tupleQuery.add(cellQuery,
          org.sindice.siren.search.SirenTupleClause.Occur.MUST);
      }

      // Object
      if (tp.getO() != null && !(tp.getO() instanceof Wildcard)) {
        // we should always receive a SirenPrimitiveQuery
        cellQuery = new SirenCellQuery((SirenPrimitiveQuery) tp.getO().getQuery());
        cellQuery.setConstraint(2, Integer.MAX_VALUE);
        tupleQuery.add(cellQuery,
          org.sindice.siren.search.SirenTupleClause.Occur.MUST);
      }
    }

    tp.setQuery(tupleQuery);
    logger.debug("Visiting TriplePattern - Exit");
  }

  /**
   * Parse the literal using the StandardAnalzyer, creating
   * a SirenPhraseQuery from the tokens.
   * If the literal parsing fails, it is ignored.
   */
  @Override
  public void visit(final Literal l) {
    logger.debug("Visiting Literal");
    final ResourceQueryParser qph = new ResourceQueryParser(literalAnalyzer);
    qph.setDefaultOperator(defaultOp);
    try {
      // Add quotes so that the parser evaluates it as a phrase query
      l.setQuery(qph.parse("\"" + l.getV() + "\"", field));
    }
    catch (final QueryNodeException e) {
      logger.error("Parsing of the LiteralPattern failed", e);
      this.createQueryException(e);
    }
  }

  /**
   * Create one of the Siren specific queries (SirenPhraseQuery, SirenTermQuery,
   * SirenTupleQuery) from the LiteralPattern
   * @throws ParseException
   */
  @Override
  public void visit(final LiteralPattern lp) {
    logger.debug("Visiting Literal Pattern");
    final ResourceQueryParser qph = new ResourceQueryParser(literalAnalyzer);
    qph.setDefaultOperator(defaultOp);
    try {
      lp.setQuery(qph.parse(lp.getV(), field));
    }
    catch (final QueryNodeException e) {
      logger.error("Parsing of the LiteralPattern failed", e);
      this.createQueryException(e);
    }
  }

  /**
   * Create a SirenTermQuery
   * @throws ParseException
   */
  @Override
  public void visit(final URIPattern u) {
    logger.debug("Visiting URI");
    final ResourceQueryParser qph = new ResourceQueryParser(uriAnalyzer);
    qph.setDefaultOperator(defaultOp);
    u.setV(NTripleQueryBuilder.escape(u.getV())); // URI schemes handling
    try {
      u.setQuery(qph.parse(u.getV(), field));
    }
    catch (final QueryNodeException e) {
      logger.error("Parsing of the URIPattern failed", e);
      this.createQueryException(e);
    }
  }

  private void createQueryException(final QueryNodeException e) {
    if (queryException == null) {
      String message = null;
      if (e.getCause() != null) {
        message = e.getCause().getMessage();
      }
      else {
        message = e.getMessage();
      }
      queryException = new QueryBuilderException(Error.PARSE_ERROR,
        "Parsing of the LiteralPattern failed: " + message,
        e.getStackTrace());
    }
  }

  /**
   * Do nothing
   */
  @Override
  public void visit(final Wildcard w) {
    logger.debug("Visiting Wildcard");
  }

  /**
   * Returns a String where the colon character that QueryParser
   * expects to be escaped are escaped by a preceding <code>\</code>.
   */
  protected static String escape(final String s) {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      final char c = s.charAt(i);
      // These characters are part of the query syntax and must be escaped
      if (c == ':' || c == '~') {
        sb.append('\\');
      }
      sb.append(c);
    }
    return sb.toString();
  }

}
