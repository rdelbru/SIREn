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
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.util.FilteringTokenFilter;
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Filter out tokens with a given type.
 */
@Deprecated
public class TokenTypeFilter extends FilteringTokenFilter {

  protected Set<String> stopTokenTypes;

  private final TypeAttribute typeAtt = this.addAttribute(TypeAttribute.class);

  public TokenTypeFilter(final TokenStream input, final int[] stopTokenTypes) {
    super(true, input);
    this.stopTokenTypes = new HashSet<String>(stopTokenTypes.length);
    for (final int type : stopTokenTypes) {
      this.stopTokenTypes.add(TupleTokenizer.getTokenTypes()[type]);
    }
  }

  @Override
  protected boolean accept() throws IOException {
    if (stopTokenTypes.contains(typeAtt.type())) {
      return false;
    }
    return true;
  }



}
