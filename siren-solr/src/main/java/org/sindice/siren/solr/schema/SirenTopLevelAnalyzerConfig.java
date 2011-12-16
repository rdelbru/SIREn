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
 * @author Renaud Delbru [ 14 Oct 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.solr.schema;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;
import org.apache.solr.analysis.TokenFilterFactory;
import org.apache.solr.analysis.TokenizerChain;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.util.SystemIdResolver;
import org.apache.solr.core.Config;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrResourceLoader;
import org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter;
import org.sindice.siren.solr.analysis.DatatypeAnalyzerFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * Read a SIREn top-level analyzer configuration and load the respective
 * analyzers:
 * <ul>
 * <li> An index analyzer, identified by the type 'index'
 * <li> A keyword query analyzer, identified by the type 'keyword-query'
 * </ul>
 */
public final class SirenTopLevelAnalyzerConfig {

  private final String resourceName;
  private String name;
  private String version;
  private final SolrResourceLoader loader;

  private Analyzer analyzer;
  private Analyzer kqAnalyzer;

  private final Version luceneMatchVersion;

  final static Logger log = LoggerFactory.getLogger(SirenTopLevelAnalyzerConfig.class);

  /**
   * Constructs a config using the specified resource name and stream.
   * If the stream is null, the resource loader will load the resource
   * by name.
   * @see SolrResourceLoader#openConfig
   * By default, this follows the normal config path directory searching rules.
   * @see SolrResourceLoader#openResource
   */
  public SirenTopLevelAnalyzerConfig(final SolrResourceLoader loader,
                                     final String name, InputSource is,
                                     final Version luceneMatchVersion) {
    this.luceneMatchVersion = luceneMatchVersion;
    this.resourceName = name;
    this.loader = loader;

    if (is == null) {
      is = new InputSource(loader.openConfig(name));
      is.setSystemId(SystemIdResolver.createSystemIdFromResourceName(name));
    }
    this.readConfig(is);
    loader.inform(loader);
  }

  /** Gets the name of the resource used to instantiate this config. */
  public String getResourceName() {
    return resourceName;
  }

  /** Gets the name of the config as specified in the config resource. */
  public String getConfigName() {
    return name;
  }

  /** Gets the version of the config as specified in the config resource. */
  public String getVersion() {
    return version;
  }

  /**
   * Returns the Analyzer used when indexing {@link SirenField}.
   */
  public Analyzer getAnalyzer() { return analyzer; }

  /**
   * Returns the Analyzer used when querying a {@link SirenField} with keyword
   * query.
   */
  public Analyzer getKeywordQueryAnalyzer() { return kqAnalyzer; }

  /**
   * Read the XML config file, and load the analyzers.
   */
  private void readConfig(final InputSource is) {
    log.info("Reading SIREn top-level analyzer configuration");

    try {
      // in the current case though, the stream is valid so we wont load the resource by name
      final Config analyzerConf = new Config(loader, "analyzerConfig", is, "/analyzerConfig/");
      final Document document = analyzerConf.getDocument();
      final XPath xpath = analyzerConf.getXPath();
      final Node node = (Node) xpath.evaluate("/analyzerConfig/@name", document, XPathConstants.NODE);
      if (node == null) {
        log.warn("analyzerConfig has no name!");
      } else {
        name = node.getNodeValue();
        log.info("analyzerConfig name=" + name);
      }

      version = analyzerConf.get("/analyzerConfig/@version");

      // Read analyzer with type="index"
      String expression = "/analyzerConfig/analyzer[@type='index']";
      Node anode = (Node) xpath.evaluate(expression, node, XPathConstants.NODE);
      analyzer = AnalyzerConfigReader.readAnalyzer(anode, loader, luceneMatchVersion);

      if (analyzer == null) {
        throw new SolrException( SolrException.ErrorCode.SERVER_ERROR,
          "Configuration Error: No analyzer defined for type 'index'");
      }

      // Read analyzer with type="keyword-query"
      expression = "/analyzerConfig/analyzer[@type='keyword-query']";
      anode = (Node)xpath.evaluate(expression, node, XPathConstants.NODE);
      kqAnalyzer = AnalyzerConfigReader.readAnalyzer(anode, loader, luceneMatchVersion);

      if (kqAnalyzer == null) {
        throw new SolrException( SolrException.ErrorCode.SERVER_ERROR,
          "Configuration Error: No analyzer defined for type 'keyword-query'");
      }

    }
    catch (final SolrException e) {
      SolrConfig.severeErrors.add( e );
      throw e;
    }
    catch(final Exception e) {
      // unexpected exception...
      SolrConfig.severeErrors.add( e );
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
        "SIREn top-level analyzer configuration parsing failed", e, false);
    }
  }

  /**
   * Register the datatypes in the {@link DatatypeAnalyzerFilter}.
   * <p> Go through the filters of the index analyzer, and register the datatypes
   * if a {@link DatatypeAnalyzerFilter} is found.
   *
   * @param datatypes The datatypes to register.
   */
  public void register(final Map<String, Datatype> datatypes) {
    // Register datatype analyzer for indexing
    final TokenFilterFactory[] filters = ((TokenizerChain) this.analyzer).getTokenFilterFactories();
    for (final TokenFilterFactory filter : filters) {
      if (filter instanceof DatatypeAnalyzerFilterFactory) {
        final DatatypeAnalyzerFilterFactory tmp = (DatatypeAnalyzerFilterFactory) filter;

        for (final Entry<String, Datatype> e : datatypes.entrySet()) {

          if (e.getValue().getAnalyzer() == null) {
            throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
              "Configuration Error: No analyzer defined for type 'index' in " +
              "datatype " + e.getKey());
          }

          tmp.register(e.getKey(), e.getValue().getAnalyzer());
        }

      }
    }
  }

}

