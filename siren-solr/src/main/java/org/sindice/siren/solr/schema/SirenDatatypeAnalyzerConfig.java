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

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;
import org.apache.solr.common.ResourceLoader;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.util.DOMUtil;
import org.apache.solr.common.util.SystemIdResolver;
import org.apache.solr.core.Config;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.util.plugin.AbstractPluginLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Read a SIREn datatype analyzer configuration. Each datatype must define
 * one index and one query analyzer.
 */
public final class SirenDatatypeAnalyzerConfig {

  private final String resourceName;
  private String name;
  private String version;
  private final SolrResourceLoader loader;

  private final HashMap<String, Datatype> datatypes = new HashMap<String,Datatype>();

  private final Version luceneMatchVersion;

  final static Logger log = LoggerFactory.getLogger(SirenDatatypeAnalyzerConfig.class);

  /**
   * Constructs a config using the specified resource name and stream.
   * If the is stream is null, the resource loader will load the resource
   * by name.
   * @see SolrResourceLoader#openConfig
   * By default, this follows the normal config path directory searching rules.
   * @see SolrResourceLoader#openResource
   */
  public SirenDatatypeAnalyzerConfig(final SolrResourceLoader loader,
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

  /** Gets the name of the resource used to instantiate this schema. */
  public String getResourceName() {
    return resourceName;
  }

  /** Gets the name of the schema as specified in the schema resource. */
  public String getSchemaName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  /**
   * Provides direct access to the Map containing all Datatypes, keyed on
   * datatype name.
   *
   * <p>
   * Modifying this Map (or any item in it) will affect the real schema.
   * However if you make any modifications, be sure to call
   * {@link IndexSchema#refreshAnalyzers()} to update the Analyzers for the
   * registered fields.
   * </p>
   *
   * <p>
   * NOTE: this function is not thread safe.  However, it is safe to use within the standard
   * <code>inform( SolrCore core )</code> function for <code>SolrCoreAware</code> classes.
   * Outside <code>inform</code>, this could potentially throw a ConcurrentModificationException
   * </p>
   */
  public Map<String,Datatype> getDatatypes() { return datatypes; }

  /**
   * Read the definition of the datatypes and load them into the
   * {@code datatypes} map.
   */
  private void readConfig(final InputSource is) {
    log.info("Reading SIREn datatype analyzer configuration");

    try {
      // in the current case though, the stream is valid so we wont load the resource by name
      final Config schemaConf = new Config(loader, "datatypeConfig", is, "/datatypeConfig/");
      final Document document = schemaConf.getDocument();
      final XPath xpath = schemaConf.getXPath();
      final Node nd = (Node) xpath.evaluate("/datatypeConfig/@name", document, XPathConstants.NODE);
      if (nd==null) {
        log.warn("datatypeConfig has no name!");
      }
      else {
        name = nd.getNodeValue();
        log.info("datatypeConfig name=" + name);
      }

      version = schemaConf.get("/datatypeConfig/@version");

      final AbstractPluginLoader<Datatype> datatypeLoader = new AbstractPluginLoader<Datatype>( "[datatypeConfig] datatype", true, true) {

        @Override
        protected Datatype create(final ResourceLoader loader,
                                  final String name, final String className,
                                  final Node node)
        throws Exception {
          final Datatype dt = (Datatype) loader.newInstance(className);
          dt.setDatatypeName(name);

          // An analyzer with type="index"
          String expression = "./analyzer[@type='index']";
          Node anode = (Node) xpath.evaluate(expression, node, XPathConstants.NODE);
          final Analyzer analyzer = AnalyzerConfigReader.readAnalyzer(anode,
            (SolrResourceLoader) loader, luceneMatchVersion);
          if (analyzer != null) dt.setAnalyzer(analyzer);

          expression = "./analyzer[@type='query']";
          anode = (Node) xpath.evaluate(expression, node, XPathConstants.NODE);
          final Analyzer queryAnalyzer = AnalyzerConfigReader.readAnalyzer(anode,
            (SolrResourceLoader) loader, luceneMatchVersion);
          if (queryAnalyzer != null) dt.setQueryAnalyzer(queryAnalyzer);

          return dt;
        }

        @Override
        protected void init(final Datatype plugin, final Node node)
        throws Exception {
          final Map<String,String> params = DOMUtil.toMapExcept(node.getAttributes(), "name", "class");
          plugin.setArgs(params);
        }

        @Override
        protected Datatype register(final String name, final Datatype plugin)
        throws Exception {
          log.trace("datatype defined: " + plugin);
          return datatypes.put(name, plugin);
        }
      };


      final String expression = "/datatypeConfig/datatype";
      final NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
      datatypeLoader.load(loader, nodes);
    }
    catch (final SolrException e) {
      SolrConfig.severeErrors.add(e);
      throw e;
    }
    catch(final Exception e) {
      // unexpected exception...
      SolrConfig.severeErrors.add( e );
      throw new SolrException( SolrException.ErrorCode.SERVER_ERROR,
        "datatypeConfig parsing failed: " + e.getMessage(), e,false);
    }
  }

  /**
   * Returns the {@link Datatype} for the specified datatype.
   *
   * @param datatype The name of the datatype.
   * @return Null if the specified datatype does not exist
   */
  public Datatype getDatatype(final String datatype) {
    return this.datatypes.get(datatype);
  }

}
