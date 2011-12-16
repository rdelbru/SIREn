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
 * @project siren-solr
 * @author Campinas Stephane [ 1 Nov 2010 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.solr.analysis;

import java.io.File;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.apache.solr.core.SolrResourceLoader;
import org.sindice.siren.qparser.analysis.filter.QNamesFilter;

/**
 * Load the Qnames mapping file located in the folder conf of the Solr home
 */
public class QNamesFilterFactory
extends BaseTokenFilterFactory {
  
  public static final String    QNAMES_PATH_KEY = "qnames";
  private String                qnamesPath;
  
  @Override
  public void init(Map<String, String> args) {
    super.init(args);
    final String qnamesFile = args.get(QNAMES_PATH_KEY);
    qnamesPath = qnamesFile == null ? null : SolrResourceLoader.locateSolrHome() + "conf" + File.separator + qnamesFile;
  }
  
  @Override
  public TokenStream create(TokenStream input) {
    return new QNamesFilter(input, qnamesPath);
  }

}
