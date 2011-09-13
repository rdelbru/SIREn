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
package org.sindice.siren.solr.analysis;

import java.util.Arrays;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.sindice.siren.analysis.TupleTokenizer;
import org.sindice.siren.analysis.filter.TokenTypeFilter;

/**
 * Filter out tokens of the specified type.
 */
public class TokenTypeFilterFactory
extends BaseTokenFilterFactory {

  public static final String BNODE_KEY = "bnode";
  public static final String DATATYPE_KEY = "datatype";
  public static final String DOT_KEY = "dot";
  public static final String LANGUAGE_KEY = "languageTag";

  private int[] tokenTypes;

  @Override
  public void init(final Map<String, String> args) {
    super.init(args);
    final int[] buffer = new int[4];
    int offset = 0;

    if (this.getInt(BNODE_KEY, 1) == 1) {
      buffer[offset++] = TupleTokenizer.BNODE;
    }
    if (this.getInt(DATATYPE_KEY, 1) == 1) {
      buffer[offset++] = TupleTokenizer.DATATYPE;
    }
    if (this.getInt(DOT_KEY, 1) == 1) {
      buffer[offset++] = TupleTokenizer.DOT;
    }
    if (this.getInt(LANGUAGE_KEY, 1) == 1) {
      buffer[offset++] = TupleTokenizer.LANGUAGE;
    }
    tokenTypes = Arrays.copyOf(buffer, offset);
  }

  @Override
  public TokenStream create(final TokenStream input) {
    return new TokenTypeFilter(input, tokenTypes);
  }

}
