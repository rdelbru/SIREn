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
 * @project siren-qparser_rdelbru
 * @author Campinas Stephane [ 7 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.qparser.tuple.query.config;

import org.apache.lucene.queryParser.standard.config.MultiTermRewriteMethodAttribute;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.util.Attribute;
import org.sindice.siren.search.SirenMultiTermQuery;

/**
 * Copied from {@link MultiTermRewriteMethodAttribute} for the SIren use case:
 * it uses the {@link SirenMultiTermQuery} instead of {@link MultiTermQuery}.
 */
public interface SirenMultiTermRewriteMethodAttribute extends Attribute {

  public static final String TAG_ID = "SirenMultiTermRewriteMethodAttribute";
  
  public void setSirenMultiTermRewriteMethod(SirenMultiTermQuery.RewriteMethod method);

  public SirenMultiTermQuery.RewriteMethod getSirenMultiTermRewriteMethod();
  
}
