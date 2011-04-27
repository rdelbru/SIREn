/**
 * Copyright 2009, Renaud Delbru
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
