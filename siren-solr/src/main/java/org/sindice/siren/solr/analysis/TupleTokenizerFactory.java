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
/**
 * @project siren
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.analysis;

import java.io.Reader;
import java.util.Map;

import org.apache.solr.analysis.BaseTokenizerFactory;
import org.sindice.siren.analysis.TupleTokenizer;

public class TupleTokenizerFactory extends BaseTokenizerFactory {

  public static final String MAXLENGTH_KEY = "maxLength";

  private int maxLength = 0;

	@Override
	public void init(final Map<String,String> args) {
	  super.init(args);
	  this.assureMatchVersion();
	  // load maxLength param
	  final String maxArg = args.get(MAXLENGTH_KEY);
	  maxLength = (maxArg != null ? Integer.parseInt(maxArg) : Integer.MAX_VALUE);
	}

	@Override
	public TupleTokenizer create(final Reader input) {
		return new TupleTokenizer(input, maxLength);
	}

}
