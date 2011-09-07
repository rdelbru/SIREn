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
package org.sindice.siren.qparser.ntriple.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.qparser.ntriple.query.model.BinaryClause;
import org.sindice.siren.qparser.ntriple.query.model.EmptyQuery;
import org.sindice.siren.qparser.ntriple.query.model.Literal;
import org.sindice.siren.qparser.ntriple.query.model.LiteralPattern;
import org.sindice.siren.qparser.ntriple.query.model.NestedClause;
import org.sindice.siren.qparser.ntriple.query.model.Operator;
import org.sindice.siren.qparser.ntriple.query.model.SimpleExpression;
import org.sindice.siren.qparser.ntriple.query.model.TriplePattern;
import org.sindice.siren.qparser.ntriple.query.model.URIPattern;
import org.sindice.siren.qparser.ntriple.query.model.Wildcard;
import org.sindice.siren.search.SirenBooleanClause;
import org.sindice.siren.search.SirenBooleanQuery;
import org.sindice.siren.search.SirenCellQuery;
import org.sindice.siren.search.SirenPhraseQuery;
import org.sindice.siren.search.SirenTermQuery;
import org.sindice.siren.search.SirenTupleClause;
import org.sindice.siren.search.SirenTupleQuery;


public class NTripleQueryBuilderTest {

  private final String  _field = "triple";

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {}

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.TriplePattern)}.
   * Test a TriplePattern composed of 3 URIPattern: s, p, o.
   */
  @Test
  public void testVisitTriplePattern1() {
    final TriplePattern pattern = new TriplePattern(new URIPattern("s"), new URIPattern("p"),new URIPattern("o"));

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    pattern.traverseBottomUp(translator);
    final Query query = pattern.getQuery();

    assertTrue(query instanceof SirenTupleQuery);
    assertEquals(3, ((SirenTupleQuery) query).clauses().size());

    assertTrue(((SirenTupleQuery) query).clauses().get(0) instanceof SirenTupleClause);
    SirenTupleClause tupleClause = ((SirenTupleQuery) query).clauses().get(0);
    SirenCellQuery cellQuery = tupleClause.getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("s", termQuery.getTerm().text());

    assertTrue(((SirenTupleQuery) query).clauses().get(1) instanceof SirenTupleClause);
    tupleClause = ((SirenTupleQuery) query).clauses().get(1);
    cellQuery = tupleClause.getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    assertTrue(((SirenTupleQuery) query).clauses().get(2) instanceof SirenTupleClause);
    tupleClause = ((SirenTupleQuery) query).clauses().get(2);
    cellQuery = tupleClause.getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("o", termQuery.getTerm().text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.TriplePattern)}.
   * Test a TriplePattern composed of 1 URIPattern and 1 literal: p, " literal ".
   */
  @Test
  public void testVisitTriplePattern2() {
    final TriplePattern pattern = new TriplePattern(new Wildcard("*"), new URIPattern("p"), new Literal(" literal "));

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    pattern.traverseBottomUp(translator);
    final Query query = pattern.getQuery();

    assertTrue(query instanceof SirenTupleQuery);
    assertEquals(2, ((SirenTupleQuery) query).clauses().size());

    SirenCellQuery cellQuery = ((SirenTupleQuery) query).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = ((SirenTupleQuery) query).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("literal", termQuery.getTerm().text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.TriplePattern)}.
   * Test a TriplePattern composed of 1 URIPattern and 1 literal: p, " some literal ".
   */
  @Test
  public void testVisitTriplePattern3() {
    final TriplePattern pattern = new TriplePattern(new Wildcard("*"), new URIPattern("p"), new Literal(" some literal "));

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    pattern.traverseBottomUp(translator);
    final Query query = pattern.getQuery();

    assertTrue(query instanceof SirenTupleQuery);
    assertEquals(2, ((SirenTupleQuery) query).clauses().size());

    SirenCellQuery cellQuery = ((SirenTupleQuery) query).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    final SirenTermQuery termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = ((SirenTupleQuery) query).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenPhraseQuery);
    final SirenPhraseQuery phraseQuery = (SirenPhraseQuery) cellQuery.getQuery();
    assertTrue(phraseQuery.getTerms().length == 2);
    assertEquals(_field, phraseQuery.getTerms()[0].field());
    assertEquals("some", phraseQuery.getTerms()[0].text());
    assertEquals("literal", phraseQuery.getTerms()[1].text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.TriplePattern)}.
   * Test a TriplePattern composed of 1 URIPattern and 1 boolean literal pattern: p, "Some (literal OR text) ".
   */
  @Test
  public void testVisitTriplePattern4() {
    final TriplePattern pattern = new TriplePattern(new Wildcard("*"), new URIPattern("p"), new LiteralPattern(" (literal OR text) "));

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    pattern.traverseBottomUp(translator);
    final Query query = pattern.getQuery();

    assertTrue(query instanceof SirenTupleQuery);
    assertEquals(2, ((SirenTupleQuery) query).clauses().size());

    assertTrue(((SirenTupleQuery) query).clauses().get(0) instanceof SirenTupleClause);
    SirenCellQuery cellQuery = ((SirenTupleQuery) query).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    assertTrue(((SirenTupleQuery) query).clauses().get(1) instanceof SirenTupleClause);
    assertTrue(((SirenTupleQuery) query).clauses().get(1).getQuery() instanceof SirenCellQuery);
    cellQuery = ((SirenTupleQuery) query).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenBooleanQuery);
    final SirenBooleanQuery bq = (SirenBooleanQuery) cellQuery.getQuery();
    assertTrue(bq.getClauses().length == 2);
    assertTrue(bq.getClauses()[0].getQuery() instanceof SirenTermQuery);
    assertTrue(bq.getClauses()[1].getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) bq.getClauses()[0].getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("literal", termQuery.getTerm().text());
    termQuery = (SirenTermQuery) bq.getClauses()[1].getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("text", termQuery.getTerm().text());
  }

  /**
   * Test method for {@link org.sindice.solr.plugins.qparser.ntriple.query.StandardQueryTranslator#visit(org.sindice.siren.qparser.ntriple.query.model.EmptyQuery)}.
   * Test an EmptyQuery.
   */
  @Test
  public void testVisitEmptyQuery() {
    final EmptyQuery empty = new EmptyQuery();

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    empty.traverseBottomUp(translator);
    final Query query = empty.getQuery();
    //System.out.println(query.toString());
    assertTrue(query instanceof BooleanQuery);
    assertEquals(0, ((BooleanQuery) query).getClauses().length);
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.BinaryClause)}.
   * Test disjunctive binary clause
   */
  @Test
  public void testVisitBinaryClause1() {
    final TriplePattern pattern1 = new TriplePattern(new Wildcard("*"), new URIPattern("p"), new URIPattern("o"));
    final SimpleExpression lhe = new SimpleExpression(pattern1);

    final TriplePattern pattern2 = new TriplePattern(new Wildcard("s"), new URIPattern("p"), new Literal("some literal"));
    final SimpleExpression rhe = new SimpleExpression(pattern2);

    final BinaryClause clause = new BinaryClause(lhe, Operator.OR, rhe);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    clause.traverseBottomUp(translator);
    final Query query = clause.getQuery();

    assertTrue(query instanceof BooleanQuery);
    assertEquals(2, ((BooleanQuery) query).getClauses().length);

    // First part of OR
    assertEquals(Occur.SHOULD, ((BooleanQuery) query).getClauses()[0].getOccur());
    assertTrue(((BooleanQuery) query).getClauses()[0].getQuery() instanceof SirenTupleQuery);
    SirenTupleQuery q = (SirenTupleQuery) ((BooleanQuery) query).getClauses()[0].getQuery();
    assertTrue(q.clauses().size() == 2);

    SirenCellQuery cellQuery = (q).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = (q).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("o", termQuery.getTerm().text());

    // Second part of OR
    assertEquals(Occur.SHOULD, ((BooleanQuery) query).getClauses()[1].getOccur());
    assertTrue(((BooleanQuery) query).getClauses()[1].getQuery() instanceof SirenTupleQuery);
    q = (SirenTupleQuery) ((BooleanQuery) query).getClauses()[1].getQuery();
    assertTrue(q.clauses().size() == 2);

    cellQuery = (q).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = (q).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenPhraseQuery);
    final SirenPhraseQuery phraseQuery = (SirenPhraseQuery) cellQuery.getQuery();
    assertTrue(phraseQuery.getTerms().length == 2);
    assertEquals(_field, phraseQuery.getTerms()[0].field());
    assertEquals("some", phraseQuery.getTerms()[0].text());
    assertEquals(_field, phraseQuery.getTerms()[1].field());
    assertEquals("literal", phraseQuery.getTerms()[1].text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.BinaryClause)}.
   * Test conjunctive binary clause
   */
  @Test
  public void testVisitBinaryClause2() {
    final TriplePattern pattern1 = new TriplePattern(new Wildcard("*"), new URIPattern("p"), new URIPattern("o"));
    final SimpleExpression lhe = new SimpleExpression(pattern1);

    final TriplePattern pattern2 = new TriplePattern(new Wildcard("s"), new URIPattern("p"), new Literal("some literal"));
    final SimpleExpression rhe = new SimpleExpression(pattern2);

    final BinaryClause clause = new BinaryClause(lhe, Operator.AND, rhe);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    clause.traverseBottomUp(translator);
    final Query query = clause.getQuery();

    assertTrue(query instanceof BooleanQuery);
    assertEquals(2, ((BooleanQuery) query).getClauses().length);

    // First part of AND
    assertEquals(Occur.MUST, ((BooleanQuery) query).getClauses()[0].getOccur());
    assertTrue(((BooleanQuery) query).getClauses()[0].getQuery() instanceof SirenTupleQuery);
    SirenTupleQuery q = (SirenTupleQuery) ((BooleanQuery) query).getClauses()[0].getQuery();
    assertTrue(q.clauses().size() == 2);

    SirenCellQuery cellQuery = (q).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = (q).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("o", termQuery.getTerm().text());

    // Second part of AND
    assertEquals(Occur.MUST, ((BooleanQuery) query).getClauses()[1].getOccur());
    assertTrue(((BooleanQuery) query).getClauses()[1].getQuery() instanceof SirenTupleQuery);
    q = (SirenTupleQuery) ((BooleanQuery) query).getClauses()[1].getQuery();
    assertTrue(q.clauses().size() == 2);

    cellQuery = (q).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = (q).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenPhraseQuery);
    final SirenPhraseQuery phraseQuery = (SirenPhraseQuery) cellQuery.getQuery();
    assertTrue(phraseQuery.getTerms().length == 2);
    assertEquals(_field, phraseQuery.getTerms()[0].field());
    assertEquals("some", phraseQuery.getTerms()[0].text());
    assertEquals(_field, phraseQuery.getTerms()[1].field());
    assertEquals("literal", phraseQuery.getTerms()[1].text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.BinaryClause)}.
   * Test substractive binary clause
   */
  @Test
  public void testVisitBinaryClause3() {
    final TriplePattern pattern1 = new TriplePattern(new Wildcard("*"), new URIPattern("p"), new URIPattern("o"));
    final SimpleExpression lhe = new SimpleExpression(pattern1);

    final TriplePattern pattern2 = new TriplePattern(new Wildcard("s"), new URIPattern("p"), new Literal("some literal"));
    final SimpleExpression rhe = new SimpleExpression(pattern2);

    final BinaryClause clause = new BinaryClause(lhe, Operator.MINUS, rhe);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    clause.traverseBottomUp(translator);
    final Query query = clause.getQuery();

    assertTrue(query instanceof BooleanQuery);
    assertEquals(2, ((BooleanQuery) query).getClauses().length);

    // First part
    assertEquals(Occur.MUST, ((BooleanQuery) query).getClauses()[0].getOccur());
    assertTrue(((BooleanQuery) query).getClauses()[0].getQuery() instanceof SirenTupleQuery);
    SirenTupleQuery q = (SirenTupleQuery) ((BooleanQuery) query).getClauses()[0].getQuery();
    assertTrue(q.clauses().size() == 2);

    SirenCellQuery cellQuery = (q).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = (q).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("o", termQuery.getTerm().text());

    // Second part of MINUS
    assertEquals(Occur.MUST_NOT, ((BooleanQuery) query).getClauses()[1].getOccur());
    assertTrue(((BooleanQuery) query).getClauses()[1].getQuery() instanceof SirenTupleQuery);
    q = (SirenTupleQuery) ((BooleanQuery) query).getClauses()[1].getQuery();
    assertTrue(q.clauses().size() == 2);

    cellQuery = (q).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = (q).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenPhraseQuery);
    final SirenPhraseQuery phraseQuery = (SirenPhraseQuery) cellQuery.getQuery();
    assertTrue(phraseQuery.getTerms().length == 2);
    assertEquals(_field, phraseQuery.getTerms()[0].field());
    assertEquals("some", phraseQuery.getTerms()[0].text());
    assertEquals(_field, phraseQuery.getTerms()[1].field());
    assertEquals("literal", phraseQuery.getTerms()[1].text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.BinaryClause)}.
   * Test nested binary clause: <p> <o> OR (<s> <p> "some literal" AND <s> <p> <o2>)
   */
  @Test
  public void testVisitBinaryClause4() {
    final TriplePattern pattern1 = new TriplePattern(new Wildcard("*"), new URIPattern("p"), new URIPattern("o"));
    final SimpleExpression lhe = new SimpleExpression(pattern1);

    final TriplePattern pattern2 = new TriplePattern(new Wildcard("s"), new URIPattern("p"), new Literal("some literal"));
    final SimpleExpression rhe1 = new SimpleExpression(pattern2);

    final TriplePattern pattern3 = new TriplePattern(new Wildcard("s"), new URIPattern("p"), new URIPattern("o2"));
    final SimpleExpression rhe2 = new SimpleExpression(pattern3);

    final BinaryClause bclause = new BinaryClause(lhe, Operator.AND, rhe1);

    final NestedClause qclause = new NestedClause(bclause, Operator.OR, rhe2);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    qclause.traverseBottomUp(translator);
    final Query query = qclause.getQuery();

    assertTrue(query instanceof BooleanQuery);
    assertEquals(2, ((BooleanQuery) query).getClauses().length);

    // First part
    assertEquals(Occur.SHOULD, ((BooleanQuery) query).getClauses()[0].getOccur());
    assertTrue(((BooleanQuery) query).getClauses()[0].getQuery() instanceof BooleanQuery);

    final BooleanQuery bQueryAnd = (BooleanQuery) ((BooleanQuery) query).getClauses()[0].getQuery();
    assertEquals(2, bQueryAnd.getClauses().length);

    // First Part : Nested AND
    assertEquals(Occur.MUST, bQueryAnd.getClauses()[0].getOccur());
    assertTrue(bQueryAnd.getClauses()[0].getQuery() instanceof SirenTupleQuery);
    SirenTupleQuery q = (SirenTupleQuery) bQueryAnd.getClauses()[0].getQuery();
    assertTrue(q.clauses().size() == 2);

    SirenCellQuery cellQuery = q.clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = q.clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("o", termQuery.getTerm().text());

    // Second Part : Nested AND
    assertEquals(Occur.MUST, bQueryAnd.getClauses()[1].getOccur());
    assertTrue(bQueryAnd.getClauses()[1].getQuery() instanceof SirenTupleQuery);
    q = (SirenTupleQuery) bQueryAnd.getClauses()[1].getQuery();
    assertTrue(q.clauses().size() == 2);

    cellQuery = q.clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = q.clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenPhraseQuery);
    final SirenPhraseQuery phraseQuery = (SirenPhraseQuery) cellQuery.getQuery();
    assertTrue(phraseQuery.getTerms().length == 2);
    assertEquals(_field, phraseQuery.getTerms()[0].field());
    assertEquals("some", phraseQuery.getTerms()[0].text());
    assertEquals(_field, phraseQuery.getTerms()[1].field());
    assertEquals("literal", phraseQuery.getTerms()[1].text());

    // Second part
    assertEquals(Occur.SHOULD, ((BooleanQuery) query).getClauses()[1].getOccur());
    assertTrue(((BooleanQuery) query).getClauses()[1].getQuery() instanceof SirenTupleQuery);
    q = (SirenTupleQuery) ((BooleanQuery) query).getClauses()[1].getQuery();
    assertTrue(q.clauses().size() == 2);

    cellQuery = (q).clauses().get(0).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("p", termQuery.getTerm().text());

    cellQuery = (q).clauses().get(1).getQuery();
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    termQuery = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, termQuery.getTerm().field());
    assertEquals("o2", termQuery.getTerm().text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.Literal)}.
   */
  @Test
  public void testVisitLiteral() {
    final String text = "Some Literal ...";
    final Literal literal = new Literal(text);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    literal.traverseBottomUp(translator);
    final Query query = literal.getQuery();

    assertTrue(query instanceof SirenPhraseQuery);
    assertEquals(3, ((SirenPhraseQuery) query).getTerms().length);
    final Term[] terms = ((SirenPhraseQuery) query).getTerms();

    assertEquals(_field, terms[0].field());
    assertEquals("Some", terms[0].text());

    assertEquals(_field, terms[1].field());
    assertEquals("Literal", terms[1].text());

    assertEquals(_field, terms[2].field());
    assertEquals("...", terms[2].text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.Literal)}.
   */
  @Test
  public void testVisitLiteralPattern() {
    final String text = "\"Some Literal ...\"";
    final LiteralPattern literal = new LiteralPattern(text);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    literal.traverseBottomUp(translator);
    final Query query = literal.getQuery();

    assertTrue(query instanceof SirenPhraseQuery);
    assertEquals(3, ((SirenPhraseQuery) query).getTerms().length);
    final Term[] terms = ((SirenPhraseQuery) query).getTerms();

    assertEquals(_field, terms[0].field());
    assertEquals("Some", terms[0].text());

    assertEquals(_field, terms[1].field());
    assertEquals("Literal", terms[1].text());

    assertEquals(_field, terms[2].field());
    assertEquals("...", terms[2].text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.Literal)}.
   */
  @Test
  public void testVisitLiteralPattern2() {
    final String text = "Some AND Literal";
    final LiteralPattern literal = new LiteralPattern(text);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    literal.traverseBottomUp(translator);
    final Query query = literal.getQuery();

    assertTrue(query instanceof SirenBooleanQuery);
    final SirenBooleanQuery bq = (SirenBooleanQuery) query;
    assertEquals(2, bq.getClauses().length);

    SirenBooleanClause cellQuery = bq.getClauses()[0];
    assertEquals(cellQuery.getOccur(), org.sindice.siren.search.SirenBooleanClause.Occur.MUST);
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery q = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, q.getTerm().field());
    assertEquals("Some", q.getTerm().text());

    cellQuery = bq.getClauses()[1];
    assertEquals(cellQuery.getOccur(), org.sindice.siren.search.SirenBooleanClause.Occur.MUST);
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    q = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, q.getTerm().field());
    assertEquals("Literal", q.getTerm().text());
  }

  /**
   * Test method for {@link org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder#visit(org.sindice.siren.qparser.ntriple.query.model.Literal)}.
   */
  @Test
  public void testVisitLiteralPattern3() {
    final String text = "Some OR Literal";
    final LiteralPattern literal = new LiteralPattern(text);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    literal.traverseBottomUp(translator);
    final Query query = literal.getQuery();

    assertTrue(query instanceof SirenBooleanQuery);
    final SirenBooleanQuery bq = (SirenBooleanQuery) query;
    assertEquals(2, bq.getClauses().length);

    SirenBooleanClause cellQuery = bq.getClauses()[0];
    assertEquals(cellQuery.getOccur(), org.sindice.siren.search.SirenBooleanClause.Occur.SHOULD);
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery q = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, q.getTerm().field());
    assertEquals("Some", q.getTerm().text());

    cellQuery = bq.getClauses()[1];
    assertEquals(cellQuery.getOccur(), org.sindice.siren.search.SirenBooleanClause.Occur.SHOULD);
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    q = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, q.getTerm().field());
    assertEquals("Literal", q.getTerm().text());
  }

  /**
   * Create a SirenTermQuery from the URI.
   * The uriAnalyzer would interpret the URI scheme (e.g. aaa) as a field
   * name if the key word ":" was not escaped.
   */
  @Test
  public void testVisitURIPattern1() {
    final String text = "aaa://s";
    final URIPattern uri = new URIPattern(text);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    uri.traverseBottomUp(translator);
    final Query query = uri.getQuery();

    assertTrue(query instanceof SirenTermQuery);
    final SirenTermQuery tq = (SirenTermQuery) query;
    assertEquals(_field, tq.getTerm().field());
    assertEquals("aaa://s", tq.getTerm().text());
  }

  @Test
  public void testVisitURIPattern2() {
    final String text = "aaa://s || http://test";
    final URIPattern uri = new URIPattern(text);

    final NTripleQueryBuilder translator = new NTripleQueryBuilder(_field,
      new WhitespaceAnalyzer(Version.LUCENE_31), new WhitespaceAnalyzer(Version.LUCENE_31));
    uri.traverseBottomUp(translator);
    final Query query = uri.getQuery();

    assertTrue(query instanceof SirenBooleanQuery);
    final SirenBooleanQuery bq = (SirenBooleanQuery) query;
    assertEquals(2, bq.getClauses().length);

    SirenBooleanClause cellQuery = bq.getClauses()[0];
    assertEquals(cellQuery.getOccur(), org.sindice.siren.search.SirenBooleanClause.Occur.SHOULD);
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    SirenTermQuery q = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, q.getTerm().field());
    assertEquals("aaa://s", q.getTerm().text());

    cellQuery = bq.getClauses()[1];
    assertEquals(cellQuery.getOccur(), org.sindice.siren.search.SirenBooleanClause.Occur.SHOULD);
    assertTrue(cellQuery.getQuery() instanceof SirenTermQuery);
    q = (SirenTermQuery) cellQuery.getQuery();
    assertEquals(_field, q.getTerm().field());
    assertEquals("http://test", q.getTerm().text());
  }

}
