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
