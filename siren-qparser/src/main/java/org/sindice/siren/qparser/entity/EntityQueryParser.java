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
package org.sindice.siren.qparser.entity;

import java.io.StringReader;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.analysis.EntityQueryTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityQueryParser {

  private static final
  Logger logger = LoggerFactory.getLogger(EntityQueryParser.class);

  /**
   * Parse a NTriple query and return a Lucene {@link Query}.
   *
   * @param qstr The query string
   * @param defaultField The default field to query
   * @param analyzer The query analyser
   * @return A Lucene's {@link Query}
   * @throws ParseException If something is wrong with the query string
   */
  public static final Query parse(final String qstr, final String defaultField, final Analyzer analyzer)
  throws ParseException {
    final TokenStream stream = analyzer.tokenStream(defaultField, new StringReader(qstr));
    final EntityQParserImpl lparser = new EntityQParserImpl(new CupScannerWrapper(stream));

    Symbol sym = null;
    try {
      sym = lparser.parse();
      stream.close(); // safe since stream is backed by StringReader
    }
    catch (final Exception e) {
      e.printStackTrace();
      if (e != null) throw new ParseException(e.toString());
    }

//    final NTripleQueryBuilder translator = new NTripleQueryBuilder(Version.LUCENE_31, defaultField);
//    final NTripleQuery q = (NTripleQuery) sym.value;
//    q.traverseBottomUp(translator);
//
//    return q.getQuery();
    return null;
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
        for (int i = 0; i < EntityQueryTokenizer.TOKEN_TYPES.length; i++) {
          if (typeAtt.type().equals(EntityQueryTokenizer.TOKEN_TYPES[i])) {
            idx = i;
          }
        }

        logger.debug("Received token {}", cTermAtt.toString());
        if (idx == -1) {
          logger.error("Received unknown token: {}", cTermAtt.toString());
        }

        if (idx == EntityQueryTokenizer.TERM) {
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
