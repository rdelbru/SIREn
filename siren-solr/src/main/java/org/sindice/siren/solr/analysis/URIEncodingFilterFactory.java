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
package org.sindice.siren.solr.analysis;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.sindice.siren.analysis.filter.URIDecodingFilter;

/**
 * Factory for {@link URIDecodingFilter}.
 */
public class URIEncodingFilterFactory extends TokenFilterFactory {

  /*
   * In URI, the characters are by default encoded with UTF-8.
   * http://tools.ietf.org/html/rfc3986
   */
  public static final String DEFAULT_ENCODING = "UTF-8";

  @Override
  public void init(final Map<String,String> args) {
   super.init(args);
  }

  @Override
  public TokenStream create(final TokenStream input) {
    return new URIDecodingFilter(input, DEFAULT_ENCODING);
  }

}
