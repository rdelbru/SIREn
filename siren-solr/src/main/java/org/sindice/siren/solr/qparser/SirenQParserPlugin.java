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
package org.sindice.siren.solr.qparser;

import java.io.IOException;
import java.util.Properties;

import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.search.QParserPlugin;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for SIREn's QParser plugin. In charge of creating a SIREn
 * query from the input value.
 * <p>
 * All of the following options may be configured for this plugin
 * in the solrconfig as defaults:
 *
 * <ul>
 * <li>qnames - The name of the file containing the qnames mapping for use in
 *              the {@link KeywordQueryParser}.
 * </ul>
 */
public abstract class SirenQParserPlugin extends QParserPlugin implements ResourceLoaderAware {

  private String qnamesFile;
  private Properties qnames;

  private static final Logger
  logger = LoggerFactory.getLogger(SirenQParserPlugin.class);

  protected Properties getQNames() {
    return qnames;
  }

  @Override
  public void init(final NamedList args) {
    qnamesFile = (String) args.get(SirenParams.QNAMES);
  }

  public void inform(final ResourceLoader loader) throws IOException {
    this.loadQNamesFile(loader);
  }

  /**
   * Load QNames mapping from the properties file
   * <p>
   * The mapping file contains lines such as:
   * <ul>
   * <li> foaf=http://xmlns.com/foaf/0.1/
   * </ul>
   * which means that the qname <code>foaf:name</code> will be expanded to
   * <code>http://xmlns.com/foaf/0.1/name</code>.
   */
  protected void loadQNamesFile(final ResourceLoader loader) {
    try {
      logger.info("Loading of the QNames mapping file: {}", qnamesFile);
      qnames = new Properties();
      qnames.load(loader.openResource(qnamesFile));
    }
    catch (final IOException e) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
        "Loading of the QNames mapping file failed: [" + qnamesFile + "]", e);
    }
  }

}
