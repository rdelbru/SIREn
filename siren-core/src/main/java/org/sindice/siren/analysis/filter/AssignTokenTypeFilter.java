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
 * @project siren-core_rdelbru
 * @author Campinas Stephane [ 4 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.analysis.filter;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.TupleTokenizer;

/**
 * Assign the given token type to the tokens which pass through. To be used when
 * the token stream is of same token type (e.g., with the AnyURIanalyzer, any token
 * is an URI).
 */
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
