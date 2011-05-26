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
 * @project siren
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
import org.apache.lucene.analysis.standard.UAX29URLEmailTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Filter that removes the trailing slash to token of type
 * {@link TupleTokenizer.URI} or {@link UAX29URLEmailTokenizer.URL_TYPE}
 */
public class URITrailingSlashFilter extends TokenFilter {

  private Set<String> tokenTypes = new HashSet<String>();
  private final static Set<String> DEFAULT_TOKEN_TYPES = new HashSet<String>();

  private final CharTermAttribute termAtt;
  private final TypeAttribute typeAtt;

  // by default, check the token type
  public static final boolean DEFAULT_CHECKTYPE = true;
  private boolean checkType = DEFAULT_CHECKTYPE;

  static {
    DEFAULT_TOKEN_TYPES.add(TupleTokenizer.getTokenTypes()[TupleTokenizer.URI]);
    DEFAULT_TOKEN_TYPES.add(UAX29URLEmailTokenizer.URL_TYPE);
  }

  public URITrailingSlashFilter(final TokenStream in) {
    this(in, new HashSet<String>(DEFAULT_TOKEN_TYPES));
  }

  public URITrailingSlashFilter(final TokenStream in, final String tokenType) {
    super(in);
    this.tokenTypes.add(tokenType);
    termAtt = this.addAttribute(CharTermAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
  }

  public URITrailingSlashFilter(final TokenStream in, final Set<String> tokenTypes) {
    super(in);
    this.tokenTypes = tokenTypes;
    termAtt = this.addAttribute(CharTermAttribute.class);
    typeAtt = this.addAttribute(TypeAttribute.class);
  }

  public void setCheckTokenType(final boolean checkType) {
    this.checkType = checkType;
  }

  @Override
  public final boolean incrementToken() throws IOException {
    if (!input.incrementToken()) {
      return false;
    }

    final String type = typeAtt.type();
    if (checkType ? tokenTypes.contains(type) : true) {
      final int bufferLength = termAtt.length();
      // Remove trailing slash
      if (termAtt.buffer()[bufferLength - 1] == '/') {
        // Strip last character off
        termAtt.setLength(bufferLength - 1);
      }
    }
    return true;
  }

}
