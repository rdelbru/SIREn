/**
 * Copyright 2010, Campinas Stephane
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
