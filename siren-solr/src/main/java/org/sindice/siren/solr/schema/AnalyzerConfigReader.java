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

package org.sindice.siren.solr.schema;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.util.CharFilterFactory;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.Version;
import org.apache.solr.analysis.TokenizerChain;
import org.apache.solr.common.SolrException;
import org.apache.solr.core.Config;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.schema.FieldTypePluginLoader;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.util.DOMUtil;
import org.apache.solr.util.plugin.AbstractPluginLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Read a datatype's analyzer configuration.
 * <p>
 * Code taken from {@link FieldTypePluginLoader} and adapted for the SIREn's
 * use case.
 */
public class AnalyzerConfigReader {

  public static final String LUCENE_MATCH_VERSION_PARAM = IndexSchema.LUCENE_MATCH_VERSION_PARAM;

  private static final
  Logger logger = LoggerFactory.getLogger(AnalyzerConfigReader.class);

  /**
   * Read an analyzer definition and instantiate an {@link Analyzer} object.
   *
   * <p> Code taken from {@link IndexSchema#readAnalyzer()}
   *
   * @param node An analyzer node from the config file
   * @return An analyzer
   * @throws XPathExpressionException If an XPath expression cannot be evaluated
   */
  protected static Analyzer readAnalyzer(final Node node,
                                         final SolrResourceLoader loader,
                                         final Version luceneMatchVersion)
  throws XPathExpressionException {
    if (node == null) return null;
    final NamedNodeMap attrs = node.getAttributes();

    final String analyzerName = DOMUtil.getAttr(attrs, "class");

    // check for all of these up front, so we can error if used in
    // conjunction with an explicit analyzer class.
    final XPath xpath = XPathFactory.newInstance().newXPath();
    final NodeList charFilterNodes = (NodeList) xpath.evaluate
      ("./charFilter",  node, XPathConstants.NODESET);
    final NodeList tokenizerNodes = (NodeList) xpath.evaluate
      ("./tokenizer", node, XPathConstants.NODESET);
    final NodeList tokenFilterNodes = (NodeList) xpath.evaluate
      ("./filter", node, XPathConstants.NODESET);

    if (analyzerName != null) {

      // explicitly check for child analysis factories instead of
      // just any child nodes, because the user might have their
      // own custom nodes (ie: <description> or something like that)
      if (0 != charFilterNodes.getLength() ||
          0 != tokenizerNodes.getLength() ||
          0 != tokenFilterNodes.getLength()) {
        throw new SolrException
        ( SolrException.ErrorCode.SERVER_ERROR,
          "Configuration Error: Analyzer class='" + analyzerName +
          "' can not be combined with nested analysis factories");
      }

      try {
        // No need to be core-aware as Analyzers are not in the core-aware list
        final Class<? extends Analyzer> clazz = loader.findClass(analyzerName, Analyzer.class);

        try {
          // first try to use a ctor with version parameter (needed for many new Analyzers that have no default one anymore)
          final Constructor<? extends Analyzer> cnstr = clazz.getConstructor(Version.class);
          final String matchVersionStr = DOMUtil.getAttr(attrs, LUCENE_MATCH_VERSION_PARAM);
          final Version matchVersion = (matchVersionStr == null) ?
            luceneMatchVersion : Config.parseLuceneVersionString(matchVersionStr);
          if (matchVersion == null) {
            throw new SolrException( SolrException.ErrorCode.SERVER_ERROR,
              "Configuration Error: Analyzer '" + clazz.getName() +
              "' needs a 'luceneMatchVersion' parameter");
          }
          return cnstr.newInstance(matchVersion);
        }
        catch (final NoSuchMethodException nsme) {
          // otherwise use default ctor
          return clazz.newInstance();
        }
      }
      catch (final Exception e) {
        logger.error("Cannot load analyzer: "+analyzerName, e);
        throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                                "Cannot load analyzer: "+analyzerName, e);
      }
    }

    // Load the CharFilters
    // --------------------------------------------------------------------------------
    final ArrayList<CharFilterFactory> charFilters = new ArrayList<CharFilterFactory>();
    final AbstractPluginLoader<CharFilterFactory> charFilterLoader =
      new AbstractPluginLoader<CharFilterFactory>("[analyzerConfig] analyzer/charFilter",
                                                  CharFilterFactory.class, false, false )
    {
      @Override
      protected void init(final CharFilterFactory plugin, final Node node) throws Exception {
        if (plugin != null) {
          final Map<String,String> params = DOMUtil.toMapExcept(node.getAttributes(),"class");

          final String configuredVersion = params.remove(LUCENE_MATCH_VERSION_PARAM);
          plugin.setLuceneMatchVersion(parseConfiguredVersion(configuredVersion,
            plugin.getClass().getSimpleName(), luceneMatchVersion));

          plugin.init( params );
          charFilters.add( plugin );
        }
      }

      @Override
      protected CharFilterFactory register(final String name, final CharFilterFactory plugin) {
        return null; // used for map registration
      }
    };

    charFilterLoader.load(loader, charFilterNodes);

    // Load the Tokenizer
    // Although an analyzer only allows a single Tokenizer, we load a list to make sure
    // the configuration is ok
    // --------------------------------------------------------------------------------
    final ArrayList<TokenizerFactory> tokenizers = new ArrayList<TokenizerFactory>(1);
    final AbstractPluginLoader<TokenizerFactory> tokenizerLoader =
      new AbstractPluginLoader<TokenizerFactory>("[analyzerConfig] analyzer/tokenizer",
                                                 TokenizerFactory.class, false, false )
    {
      @Override
      protected void init(final TokenizerFactory plugin, final Node node)
      throws Exception {
        if (!tokenizers.isEmpty()) {
          throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
              "Multiple tokenizers defined for: "+node);
        }
        final Map<String,String> params = DOMUtil.toMapExcept(node.getAttributes(),"class");

        final String configuredVersion = params.remove(LUCENE_MATCH_VERSION_PARAM);
        plugin.setLuceneMatchVersion(parseConfiguredVersion(configuredVersion,
          plugin.getClass().getSimpleName(), luceneMatchVersion));

        plugin.init(params);
        tokenizers.add(plugin);
      }

      @Override
      protected TokenizerFactory register(final String name, final TokenizerFactory plugin) {
        return null; // used for map registration
      }
    };

    tokenizerLoader.load(loader, tokenizerNodes);

    // Make sure something was loaded
    if (tokenizers.isEmpty()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
        "analyzer without class or tokenizer & filter list");
    }


    // Load the Filters
    // --------------------------------------------------------------------------------
    final ArrayList<TokenFilterFactory> filters = new ArrayList<TokenFilterFactory>();
    final AbstractPluginLoader<TokenFilterFactory> filterLoader =
      new AbstractPluginLoader<TokenFilterFactory>("[analyzerConfig] analyzer/filter",
      TokenFilterFactory.class, false, false)
    {
      @Override
      protected void init(final TokenFilterFactory plugin, final Node node) throws Exception {
        if (plugin != null) {
          final Map<String,String> params = DOMUtil.toMapExcept(node.getAttributes(), "class");

          final String configuredVersion = params.remove(LUCENE_MATCH_VERSION_PARAM);
          plugin.setLuceneMatchVersion(parseConfiguredVersion(configuredVersion,
            plugin.getClass().getSimpleName(), luceneMatchVersion));

          plugin.init(params);
          filters.add(plugin);
        }
      }

      @Override
      protected TokenFilterFactory register(final String name, final TokenFilterFactory plugin)
      throws Exception {
        return null; // used for map registration
      }
    };
    filterLoader.load(loader, tokenFilterNodes);

    return new TokenizerChain(charFilters.toArray(new CharFilterFactory[charFilters.size()]),
        tokenizers.get(0), filters.toArray(new TokenFilterFactory[filters.size()]));
  }

  private static Version parseConfiguredVersion(final String configuredVersion, final String pluginClassName, final Version luceneMatchVersion) {
    final Version version = (configuredVersion != null) ?
      Config.parseLuceneVersionString(configuredVersion) : luceneMatchVersion;

    if (!version.onOrAfter(Version.LUCENE_40)) {
      logger.warn(pluginClassName + " is using deprecated " + version +
        " emulation. You should at some point declare and reindex to at least 4.0, because " +
        "3.x emulation is deprecated and will be removed in 5.0");
    }

    return version;
  }

}
