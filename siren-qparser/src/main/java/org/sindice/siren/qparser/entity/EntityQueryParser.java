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
