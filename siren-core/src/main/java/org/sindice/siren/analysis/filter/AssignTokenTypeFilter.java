/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sindice.siren.analysis.filter;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Assign the given token type to the tokens which pass through.
 */
@Deprecated
public class AssignTokenTypeFilter extends TokenFilter {

  private final TypeAttribute typeAtt;
  private final String tokenType;

  public AssignTokenTypeFilter(final TokenStream input, final int tupleTokenizerType) {
    super(input);
    typeAtt = this.input.addAttribute(TypeAttribute.class);
    tokenType = TupleTokenizer.getTokenTypes()[tupleTokenizerType];
  }

  @Override
  public final boolean incrementToken()
  throws IOException {
    if (!input.incrementToken()) {
      return false;
    }
    typeAtt.setType(tokenType);
    return true;
  }

}
