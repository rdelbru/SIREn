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
 * @project index-beta2
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Filter out tokens with a given type.
 */
public class TokenTypeFilter
extends TokenFilter {

  protected Set<String> _tokenTypes;

  private final TypeAttribute typeAtt;

  public TokenTypeFilter(final TokenStream input, final int[] tokenTypes) {
    super(input);
    _tokenTypes = new HashSet<String>(tokenTypes.length);
    for (final int type : tokenTypes) {
      _tokenTypes.add(TupleTokenizer.getTokenTypes()[type]);
    }
    typeAtt = this.addAttribute(TypeAttribute.class);
  }

  /**
   * Returns the next input Token whose type is not in the list.
   */
  @Override
  public final boolean incrementToken()
  throws IOException {
    while (input.incrementToken()) {
      if (!this.hasToBeFiltered(typeAtt.type())) {
        return true;
      }
    }
    // reached EOS -- return false
    return false;
  }

  private boolean hasToBeFiltered(final String tokenType) {
    if (_tokenTypes.contains(tokenType)) {
      return true;
    }
    return false;
  }

}
