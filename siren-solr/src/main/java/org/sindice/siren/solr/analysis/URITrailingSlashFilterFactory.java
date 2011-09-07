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
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.analysis;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.sindice.siren.analysis.filter.URITrailingSlashFilter;

public class URITrailingSlashFilterFactory
extends BaseTokenFilterFactory {

  public static final String CHECKTYPE_KEY = "checkTokenType";

  private boolean checkType = true;

  @Override
  public void init(final Map<String,String> args) {
   super.init(args);
   this.assureMatchVersion();
   final String check = args.get(CHECKTYPE_KEY);
   checkType = (check != null ? Boolean.parseBoolean(check) : URITrailingSlashFilter.DEFAULT_CHECKTYPE);
  }

  @Override
  public TokenStream create(final TokenStream input) {
    final URITrailingSlashFilter filter = new URITrailingSlashFilter(input);
    filter.setCheckTokenType(checkType);
    return filter;
  }

}
