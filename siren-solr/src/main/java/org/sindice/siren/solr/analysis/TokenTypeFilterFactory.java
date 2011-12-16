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
package org.sindice.siren.solr.analysis;

import java.util.Arrays;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.sindice.siren.analysis.TupleTokenizer;
import org.sindice.siren.analysis.filter.TokenTypeFilter;

public class TokenTypeFilterFactory
extends BaseTokenFilterFactory {

  public static final String BNODE_KEY = "bnode";
  public static final String DOT_KEY = "dot";

  private int[] tokenTypes;

  @Override
  public void init(final Map<String, String> args) {
    super.init(args);
    final int[] buffer = new int[4];
    int offset = 0;

    if (this.getInt(BNODE_KEY, 1) == 1) {
      buffer[offset++] = TupleTokenizer.BNODE;
    }
    if (this.getInt(DOT_KEY, 1) == 1) {
      buffer[offset++] = TupleTokenizer.DOT;
    }
    tokenTypes = Arrays.copyOf(buffer, offset);
  }

  @Override
  public TokenStream create(final TokenStream input) {
    return new TokenTypeFilter(input, tokenTypes);
  }

}
