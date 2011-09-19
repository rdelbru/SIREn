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
package org.sindice.siren.solr.analysis;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.sindice.siren.analysis.filter.URIEncodingFilter;

public class URIEncodingFilterFactory extends BaseTokenFilterFactory {

  /*
   * In URI, the characters are by default encoded with UTF-8.
   * http://tools.ietf.org/html/rfc3986
   */
  public static final String DEFAULT_ENCODING = "UTF-8";

  @Override
  public void init(final Map<String,String> args) {
   super.init(args);
   this.assureMatchVersion();
  }

  @Override
  public TokenStream create(final TokenStream input) {
    return new URIEncodingFilter(input, DEFAULT_ENCODING);
  }

}
