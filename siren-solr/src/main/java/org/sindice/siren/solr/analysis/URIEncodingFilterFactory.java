/**
 * Copyright 2010, Renaud Delbru
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
 * @author Renaud Delbru [ 21 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.analysis;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.sindice.siren.analysis.filter.URIEncodingFilter;

/**
 * Create a filter that outputs for an URI two tokens: one with the URI
 * encoded special characters converted, the other unchanged.
 */
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
