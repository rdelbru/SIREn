/**
 * Copyright 2010, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.ntriple;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.analysis.NTripleQueryTokenizerImpl;
import org.sindice.siren.qparser.ntriple.query.NTripleQueryBuilder;
import org.sindice.siren.qparser.ntriple.query.ScatteredNTripleQueryBuilder;
import org.sindice.siren.qparser.ntriple.query.model.NTripleQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create a Ntriple query from the AST tree
 */
public class NTripleQueryParser {

  private static final
  Logger logger = LoggerFactory.getLogger(NTripleQueryParser.class);

  /**
   * Parse a NTriple query and return a Lucene {@link Query}. The query is built
   * over one Lucene's field.
   *
   * @param qstr The query string
   * @param field The field to query
   * @param ntripleAnalyzer The analyzers for ntriple
   * @param uriAnalyzer The analyzers for URIs
   * @param literalAnalyzer The analyzers for Literals
   * @return A Lucene's {@link Query}
   * @throws ParseException If something is wrong with the query string
   */
  public static final Query parse(final String qstr,
                                  final String field,
                                  final Analyzer ntripleAnalyzer,
                                  final Analyzer uriAnalyzer,
                                  final Analyzer literalAnalyzer,
                                  final DefaultOperatorAttribute.Operator op)
  throws ParseException {
    // Parse NTriple and create abstract syntax tree
    final TokenStream tokenStream = prepareTokenStream(qstr, ntripleAnalyzer);
    final Symbol sym = createAST(tokenStream);
    // Translate the AST into query objects
    return buildSingleFieldQuery(sym, field, uriAnalyzer, literalAnalyzer, op);
  }

  /**
   * Parse a NTriple query and return a Lucene {@link Query}.
   * <br>
   * Different query builders are used depending on the number of fields to
   * query.
   *
   * @param qstr The query string
   * @param boosts The field boosts
   * @param ntripleAnalyzer The set of analyzers (ntriple, uri, literal) for each
   * queried field
   * @param uriAnalyzer
   * @param literalAnalyzer
   * @return A Lucene's {@link Query}
   * @throws ParseException If something is wrong with the query string
   */
  public static final Query parse(final String qstr,
                                  final Map<String, Float> boosts,
                                  final boolean scattered,
                                  final Analyzer ntripleAnalyzer,
                                  final Analyzer uriAnalyzer,
                                  final Analyzer literalAnalyzer,
                                  final DefaultOperatorAttribute.Operator op)
  throws ParseException {
    if (boosts.isEmpty()) {
      throw new ParseException("Cannot parse query: no field specified");
    }

    // Parse NTriple and create abstract syntax tree
    final TokenStream tokenStream = prepareTokenStream(qstr, ntripleAnalyzer);
    final Symbol sym = createAST(tokenStream);

    // Translate the AST into query objects
    if (scattered) {
      return buildScatteredMultiFieldQuery(sym, boosts, uriAnalyzer, literalAnalyzer, op);
    }
    else {
      return buildMultiFieldQuery(sym, boosts, uriAnalyzer, literalAnalyzer, op);
    }
  }

  /**
   * Prepare the token stream of the ntriple query using the ntriple analyzer.
   * @param qstr The NTriple query
   * @param ntripleAnalyzer A NTriple Analyzer
   * @return A stream of tokens
   */
  private static TokenStream prepareTokenStream(final String qstr,
                                                final Analyzer ntripleAnalyzer) {
    TokenStream tokenStream = null;
    try {
      tokenStream = ntripleAnalyzer.reusableTokenStream("", new StringReader(qstr));
    } catch (final IOException e) {
      tokenStream = ntripleAnalyzer.tokenStream("", new StringReader(qstr));
    }
    return tokenStream;
  }

  /**
   * Create the Abstract Syntax Tree of the NTriple query based on the given
   * token stream.
   * @param tokenStream The token stream of the NTriple query
   * @return The Abstract Syntax Tree of the query
   * @throws ParseException If a irreparable error occurs during parsing
   */
  private static Symbol createAST(final TokenStream tokenStream)
  throws ParseException {
    final NTripleQParserImpl lparser = new NTripleQParserImpl(new CupScannerWrapper(tokenStream));
    Symbol sym = null;
    try {
      sym = lparser.parse();
    }
    catch (final Exception e) {
      logger.error("Parse error", e);
      if (e != null) throw new ParseException(e.toString());
    }
    return sym;
  }

  /**
   * throw an error if the visitor failed
   * @param translator
   * @throws ParseException
   */
  private static void queryBuildingError(final NTripleQueryBuilder translator)
  throws ParseException {
    if (translator.hasError()) {
      throw new ParseException(translator.getErrorDescription());
    }
  }

  private static void queryBuildingError(final ScatteredNTripleQueryBuilder translator)
  throws ParseException {
    if (translator.hasError()) {
      throw new ParseException(translator.getErrorDescription());
    }
  }

  /**
   * Translate the AST and build a single field query
   * @param sym The AST
   * @param boosts The field boosts
   * @param uriAnalyzer The analyzer for URIs
   * @param literalAnalyzer The analyzer for Literals
   * @return A Lucene query object
   * @throws ParseException
   */
  private static Query buildSingleFieldQuery(final Symbol sym,
                                             final String field,
                                             final Analyzer uriAnalyzer,
                                             final Analyzer literalAnalyzer,
                                             final DefaultOperatorAttribute.Operator op)
  throws ParseException {
    final NTripleQueryBuilder translator = new NTripleQueryBuilder(field, uriAnalyzer, literalAnalyzer);
    translator.setDefaultOperator(op);
    final NTripleQuery nq = (NTripleQuery) sym.value;
    nq.traverseBottomUp(translator);
    queryBuildingError(translator);
    return nq.getQuery();
  }

  /**
   * Translate the AST and build a multi-field query. A multi-field query
   * performs a disjunction of the original NTriple query over multiple fields,
   * each field having a different boost.
   *
   * @param sym The AST
   * @param boosts The field boosts
   * @param uriAnalyzer The analyzer for URIs
   * @param literalAnalyzer The analyzer for Literals
   * @return A Lucene query object
   * @throws ParseException
   */
  private static Query buildMultiFieldQuery(final Symbol sym,
                                            final Map<String, Float> boosts,
                                            final Analyzer uriAnalyzer,
                                            final Analyzer literalAnalyzer,
                                            final DefaultOperatorAttribute.Operator op)
  throws ParseException {
    final BooleanQuery bq = new BooleanQuery(true);
    for (final String field : boosts.keySet()) {
      final NTripleQueryBuilder translator = new NTripleQueryBuilder(field, uriAnalyzer, literalAnalyzer);
      translator.setDefaultOperator(op);
      final NTripleQuery nq = (NTripleQuery) sym.value;
      nq.traverseBottomUp(translator);
      queryBuildingError(translator);
      final Query q = nq.getQuery();
      q.setBoost(boosts.get(field));
      bq.add(q, Occur.SHOULD);
    }
    return bq;
  }

  /**
   * Translate the AST and build a scattered multi-field query. A scattered
   * multi-field query performs a conjunction of triple patterns, in which
   * each triple pattern can appear in at least on of the fields. Each field
   * has a boost.
   *
   * @param sym The AST
   * @param boosts The field boosts
   * @param uriAnalyzer The analyzer for URIs
   * @param literalAnalyzer The analyzer for Literals
   * @return A Lucene query object
   * @throws ParseException
   */
  private static Query buildScatteredMultiFieldQuery(final Symbol sym,
                                            final Map<String, Float> boosts,
                                            final Analyzer uriAnalyzer,
                                            final Analyzer literalAnalyzer,
                                            final DefaultOperatorAttribute.Operator op)
  throws ParseException {
    final ScatteredNTripleQueryBuilder translator = new ScatteredNTripleQueryBuilder(boosts, uriAnalyzer, literalAnalyzer);
    translator.setDefaultOperator(op);
    final NTripleQuery nq = (NTripleQuery) sym.value;
    nq.traverseBottomUp(translator);
    queryBuildingError(translator);
    return nq.getQuery();
  }

  public static class CupScannerWrapper implements Scanner {

    private final TokenStream _stream;
    private final CharTermAttribute cTermAtt;
    private final TypeAttribute typeAtt;

    public CupScannerWrapper(final TokenStream stream) {
      _stream = stream;
      cTermAtt = _stream.getAttribute(CharTermAttribute.class);
      typeAtt = _stream.getAttribute(TypeAttribute.class);
    }

    /* (non-Javadoc)
     * @see java_cup.runtime.Scanner#next_token()
     */
    public Symbol next_token() throws Exception {
      if (_stream.incrementToken()) {

        int idx = -1;
        for (int i = 0; i < NTripleQueryTokenizerImpl.TOKEN_TYPES.length; i++) {
          if (typeAtt.type().equals(NTripleQueryTokenizerImpl.TOKEN_TYPES[i])) {
            idx = i;
          }
        }

        logger.debug("Received token {}", cTermAtt.toString());
        if (idx == -1) {
          logger.error("Received unknown token: {}", cTermAtt.toString());
        }

        if (idx == NTripleQueryTokenizerImpl.LITERAL ||
            idx == NTripleQueryTokenizerImpl.URIPATTERN ||
            idx == NTripleQueryTokenizerImpl.LPATTERN) {
          return new Symbol(idx, cTermAtt.toString());
        }
        else {
          return new Symbol(idx);
        }
      }
      return null;
    }

  }

}
