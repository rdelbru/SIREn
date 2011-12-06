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
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.tabular;

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
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.attributes.DatatypeAttribute;
import org.sindice.siren.qparser.analysis.NTripleQueryTokenizerImpl;
import org.sindice.siren.qparser.ntriple.DatatypeValue;
import org.sindice.siren.qparser.ntriple.NTripleQParserImpl;
import org.sindice.siren.qparser.ntriple.query.ScatteredNTripleQueryBuilder;
import org.sindice.siren.qparser.ntriple.query.SimpleNTripleQueryBuilder;
import org.sindice.siren.qparser.ntriple.query.model.NTripleQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TabularQueryParser {

  private static final
  Logger logger = LoggerFactory.getLogger(TabularQueryParser.class);
  
  /**
   * Parse a NTriple query and return a Lucene {@link Query}. The query is built
   * over one Lucene's field.
   * 
   * @param qstr The query string
   * @param matchVersion the Lucene version to use
   * @param field The field to query
   * @param ntripleAnalyzer The analyzers for ntriple
   * @param datatypeConfig datatype configuration, which maps a datatype key to a
   * specific {@link Analyzer}.
   * @param op default boolean operator
   * @return A Lucene's {@link Query}
   * @throws ParseException If something is wrong with the query string
   */
  public static final Query parse(final String qstr,
                                  final Version matchVersion,
                                  final String field,
                                  final Analyzer ntripleAnalyzer,
                                  final Map<String, Analyzer> datatypeConfig,
                                  final DefaultOperatorAttribute.Operator op)
  throws ParseException {
    // Parse NTriple and create abstract syntax tree
    final TokenStream tokenStream = prepareTokenStream(qstr, ntripleAnalyzer);
    final Symbol sym = createAST(tokenStream);
    // Translate the AST into query objects
    return buildSingleFieldQuery(sym, matchVersion, field, datatypeConfig, op);
  }
  
  /**
   * Parse a NTriple query and return a Lucene {@link Query}.
   * <br>
   * Different query builders are used depending on the number of fields to
   * query.
   * 
   * @param qstr The query string
   * @param matchVersion the Lucene version to use
   * @param boosts The field boosts
   * @param ntripleAnalyzer The set of analyzers (ntriple, uri, literal) for each
   * queried field
   * @param datatypeConfigs datatype configuration for each field, which maps a
   * datatype key to a specific {@link Analyzer}.
   * @param op default boolean operator
   * @param scattered
   * @return A Lucene's {@link Query}
   * @throws ParseException If something is wrong with the query string
   */
  public static final Query parse(final String qstr,
                                  final Version matchVersion,
                                  final Map<String, Float> boosts,
                                  final Analyzer ntripleAnalyzer,
                                  final Map<String, Map<String, Analyzer>> datatypeConfigs,
                                  final DefaultOperatorAttribute.Operator op,
                                  final boolean scattered)
  throws ParseException {
    if (boosts.isEmpty()) {
      throw new ParseException("Cannot parse query: no field specified");
    }

    // Parse NTriple and create abstract syntax tree
    final TokenStream tokenStream = prepareTokenStream(qstr, ntripleAnalyzer);
    final Symbol sym = createAST(tokenStream);

    // Translate the AST into query objects
    if (scattered) {
      return buildScatteredMultiFieldQuery(sym, matchVersion, boosts, datatypeConfigs, op);
    }
    else {
      return buildMultiFieldQuery(sym, matchVersion, boosts, datatypeConfigs, op);
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
  private static void queryBuildingError(final SimpleNTripleQueryBuilder translator)
  throws ParseException {
    if (translator.hasError()) {
      throw new ParseException(translator.getErrorDescription());
    }
  }

  /**
   * Throws an error if the visitor failed
   * @param translator
   * @throws ParseException
   */
  private static void queryBuildingError(final ScatteredNTripleQueryBuilder translator)
  throws ParseException {
    if (translator.hasError()) {
      throw new ParseException(translator.getErrorDescription());
    }
  }
  
  /**
   * Translate the AST and build a single field query
   * @param sym The AST
   * @param matchVersion The Lucene version to use
   * @param field The field to query
   * @param datatypeConfig datatype configuration, which maps a datatype key to a
   * specific {@link Analyzer}.
   * @param op default boolean operator
   * @return A Lucene {@link Query} object
   * @throws ParseException
   */
  private static Query buildSingleFieldQuery(final Symbol sym,
                                             final Version matchVersion,
                                             final String field,
                                             final Map<String, Analyzer> datatypeConfig,
                                             final DefaultOperatorAttribute.Operator op)
  throws ParseException {
    final SimpleNTripleQueryBuilder translator = new SimpleNTripleQueryBuilder(matchVersion, field, datatypeConfig);
    translator.setDefaultOperator(op);
    final NTripleQuery nq = (NTripleQuery) sym.value;
    nq.traverseBottomUp(translator);
    queryBuildingError(translator);
    return nq.getQuery();
  }

  /**
   * 
   * @param sym The AST
   * @param matchVersion The Lucene version to use
   * @param boosts The field boosts
   * @param datatypeConfigs datatype configuration for each field, which maps a
   * datatype key to a specific {@link Analyzer}.
   * @param op default boolean operator
   * @return A Lucene {@link Query} object
   * @throws ParseException
   */
  private static Query buildMultiFieldQuery(final Symbol sym,
                                            final Version matchVersion,
                                            final Map<String, Float> boosts,
                                            final Map<String, Map<String, Analyzer>> datatypeConfigs,
                                            final DefaultOperatorAttribute.Operator op)
  throws ParseException {
    final BooleanQuery bq = new BooleanQuery(true);
    for (final String field : boosts.keySet()) {
      final SimpleNTripleQueryBuilder translator = new SimpleNTripleQueryBuilder(matchVersion,
        field, datatypeConfigs.get(field));
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
   * @param sym The AST
   * @param matchVersion The Lucene version to use
   * @param boosts The field boosts
   * @param datatypeConfigs datatype configuration for each field, which maps a
   * datatype key to a specific {@link Analyzer}.
   * @param op default boolean operator
   * @return A Lucene {@link Query} object
   * @throws ParseException
   */
  private static Query buildScatteredMultiFieldQuery(final Symbol sym,
                                                     final Version matchVersion,
                                                     final Map<String, Float> boosts,
                                                     final Map<String, Map<String, Analyzer>> datatypeConfigs,
                                                     final DefaultOperatorAttribute.Operator op)
  throws ParseException {
    final ScatteredNTripleQueryBuilder translator = new ScatteredNTripleQueryBuilder(matchVersion, boosts, datatypeConfigs);
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
    private final DatatypeAttribute dataTypeAtt;

    public CupScannerWrapper(final TokenStream stream) {
      _stream = stream;
      cTermAtt = _stream.getAttribute(CharTermAttribute.class);
      typeAtt = _stream.getAttribute(TypeAttribute.class);
      dataTypeAtt = stream.getAttribute(DatatypeAttribute.class);
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

        if (idx == NTripleQueryTokenizerImpl.URIPATTERN ||
            idx == NTripleQueryTokenizerImpl.LITERAL ||
            idx == NTripleQueryTokenizerImpl.LPATTERN) {
          return new Symbol(idx, new DatatypeValue(dataTypeAtt.datatypeURI(), cTermAtt.toString()));
        } else {
          return new Symbol(idx);
        }
      }
      return null;
    }

  }

}
