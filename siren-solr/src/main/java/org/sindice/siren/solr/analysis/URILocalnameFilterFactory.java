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
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.analysis;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.sindice.siren.analysis.filter.URILocalnameFilter;

public class URILocalnameFilterFactory
extends BaseTokenFilterFactory {

  public static final String MAXLENGTH_KEY = "maxLength";

  private int maxLength = 0;

  @Override
  public void init(final Map<String,String> args) {
   super.init(args);
   this.assureMatchVersion();
   final String maxArg = args.get(MAXLENGTH_KEY);
   maxLength = (maxArg != null ? Integer.parseInt(maxArg) : URILocalnameFilter.DEFAULT_MAX_LENGTH);
  }

  @Override
  public TokenStream create(final TokenStream input) {
    final URILocalnameFilter filter = new URILocalnameFilter(input);
    filter.setMaxLength(maxLength);
    return filter;
  }

}
