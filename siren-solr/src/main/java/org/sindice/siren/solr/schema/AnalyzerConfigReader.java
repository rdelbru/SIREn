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
 * @author Renaud Delbru [ 15 Oct 2011 ]
 * @link http://renaud.delbru.fr/
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
import org.apache.lucene.util.Version;
import org.apache.solr.analysis.CharFilterFactory;
import org.apache.solr.analysis.TokenFilterFactory;
import org.apache.solr.analysis.TokenizerChain;
import org.apache.solr.analysis.TokenizerFactory;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.util.DOMUtil;
import org.apache.solr.core.Config;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.util.plugin.AbstractPluginLoader;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AnalyzerConfigReader {

  public static final String LUCENE_MATCH_VERSION_PARAM = IndexSchema.LUCENE_MATCH_VERSION_PARAM;

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
    if (analyzerName != null) {
      // No need to be core-aware as Analyzers are not in the core-aware list
      final Class<? extends Analyzer> clazz = loader.findClass(analyzerName).asSubclass(Analyzer.class);
      try {
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
        } catch (final NoSuchMethodException nsme) {
          // otherwise use default ctor
          return clazz.newInstance();
        }
      } catch (final Exception e) {
        throw new SolrException( SolrException.ErrorCode.SERVER_ERROR,
              "Cannot load analyzer: "+analyzerName );
      }
    }

    final XPath xpath = XPathFactory.newInstance().newXPath();

    // Load the CharFilters
    // --------------------------------------------------------------------------------
    final ArrayList<CharFilterFactory> charFilters = new ArrayList<CharFilterFactory>();
    final AbstractPluginLoader<CharFilterFactory> charFilterLoader =
      new AbstractPluginLoader<CharFilterFactory>( "[analyzerConfig] analyzer/charFilter", false, false )
    {
      @Override
      protected void init(final CharFilterFactory plugin, final Node node) throws Exception {
        if( plugin != null ) {
          final Map<String,String> params = DOMUtil.toMapExcept(node.getAttributes(),"class");
          // copy the luceneMatchVersion from config, if not set
          if (!params.containsKey(LUCENE_MATCH_VERSION_PARAM))
            params.put(LUCENE_MATCH_VERSION_PARAM, luceneMatchVersion.toString());
          plugin.init( params );
          charFilters.add( plugin );
        }
      }

      @Override
      protected CharFilterFactory register(final String name, final CharFilterFactory plugin) throws Exception {
        return null; // used for map registration
      }
    };
    charFilterLoader.load(loader, (NodeList)xpath.evaluate("./charFilter", node, XPathConstants.NODESET));

    // Load the Tokenizer
    // Although an analyzer only allows a single Tokenizer, we load a list to make sure
    // the configuration is ok
    // --------------------------------------------------------------------------------
    final ArrayList<TokenizerFactory> tokenizers = new ArrayList<TokenizerFactory>(1);
    final AbstractPluginLoader<TokenizerFactory> tokenizerLoader =
      new AbstractPluginLoader<TokenizerFactory>( "[analyzerConfig] analyzer/tokenizer", false, false )
    {
      @Override
      protected void init(final TokenizerFactory plugin, final Node node)
      throws Exception {
        if (!tokenizers.isEmpty()) {
          throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
              "Multiple tokenizers defined for: "+node);
        }
        final Map<String,String> params = DOMUtil.toMapExcept(node.getAttributes(),"class");
        // copy the luceneMatchVersion from config, if not set
        if (!params.containsKey(LUCENE_MATCH_VERSION_PARAM))
          params.put(LUCENE_MATCH_VERSION_PARAM, luceneMatchVersion.toString());
        plugin.init(params);
        tokenizers.add(plugin);
      }

      @Override
      protected TokenizerFactory register(final String name, final TokenizerFactory plugin)
      throws Exception {
        return null; // used for map registration
      }
    };
    tokenizerLoader.load(loader, (NodeList) xpath.evaluate("./tokenizer", node, XPathConstants.NODESET));

    // Make sure something was loaded
    if (tokenizers.isEmpty()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
        "analyzer without class or tokenizer & filter list");
    }


    // Load the Filters
    // --------------------------------------------------------------------------------
    final ArrayList<TokenFilterFactory> filters = new ArrayList<TokenFilterFactory>();
    final AbstractPluginLoader<TokenFilterFactory> filterLoader =
      new AbstractPluginLoader<TokenFilterFactory>("[analyzerConfig] analyzer/filter", false, false)
    {
      @Override
      protected void init(final TokenFilterFactory plugin, final Node node) throws Exception {
        if (plugin != null) {
          final Map<String,String> params = DOMUtil.toMapExcept(node.getAttributes(), "class");
          // copy the luceneMatchVersion from config, if not set
          if (!params.containsKey(LUCENE_MATCH_VERSION_PARAM))
            params.put(LUCENE_MATCH_VERSION_PARAM, luceneMatchVersion.toString());
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
    filterLoader.load(loader, (NodeList) xpath.evaluate("./filter", node, XPathConstants.NODESET));

    return new TokenizerChain(charFilters.toArray(new CharFilterFactory[charFilters.size()]),
        tokenizers.get(0), filters.toArray(new TokenFilterFactory[filters.size()]));
  };

}
