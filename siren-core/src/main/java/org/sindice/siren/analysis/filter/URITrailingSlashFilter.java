/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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
