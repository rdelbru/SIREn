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
 * @author Renaud Delbru [ 18 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.apache.solr.schema;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.apache.solr.common.ResourceLoader;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.apache.solr.common.util.DOMUtil;
import org.apache.solr.core.Config;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.util.plugin.AbstractPluginLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>SirenIndexSchema</code> is used to parse secondary schema containing
 * exclusively FieldType definitions. All other definitions (Field, Similarity,
 * etc.) will be ignored.
 * <br>
 * These FieldType definitions belong to a parent FieldType in the main schema.
 * <br>
 * Only limited information about the FieldType, i.e., their analyzers, is
 * parsed. Other information such as omitNorm or PositionIncrementGap
 * are ignored, since the definition of the parent FieldType prevails.
 */
public final class SubIndexSchema {

  public static final String DEFAULT_SCHEMA_FILE = "schema.xml";
  public static final String LUCENE_MATCH_VERSION_PARAM = "luceneMatchVersion";

  final static Logger log = LoggerFactory.getLogger(SubIndexSchema.class);
  private String name;
  private float version;
  private final SolrResourceLoader loader;
  private final Version luceneMatchVersion;

  private final HashMap<String, FieldType> fieldTypes = new HashMap<String,FieldType>();

    /**
   * Constructs a schema using the specified resource name and stream.
   * If the is stream is null, the resource loader will load the schema resource by name.
     * @throws FileNotFoundException
   * @see SolrResourceLoader#openSchema
   * By default, this follows the normal config path directory searching rules.
   * @see Config#openResource
   */
  public SubIndexSchema(final String path, final Version luceneMatchVersion)
  throws FileNotFoundException {
    this.luceneMatchVersion = luceneMatchVersion;
    // important - Get the class loader of one of the SIREn class as parent
    // class loader, otehrwise it is not possible to find the SIREn classes
    loader = new SolrResourceLoader(null, SubTextField.class.getClassLoader());
    final InputStream lis = loader.openSchema(path);
    this.readSchema(lis);
    // important - allows filters to complete loading.
    // For example, StopWordFilterFactory will load the stopwords list at this
    // point
    loader.inform(loader);
  }

  /**
   * @since solr 1.4
   */
  public SolrResourceLoader getResourceLoader() {
    return loader;
  }

  /** Gets the name of the schema as specified in the schema resource. */
  public String getSchemaName() {
    return name;
  }

  float getVersion() {
    return version;
  }

  /**
   * Provides direct access to the Map containing all Field Types
   * in the index, keyed on field type name.
   *
   * <p>
   * Modifying this Map (or any item in it) will affect the real schema.  However if you
   * make any modifications, be sure to call {@link IndexSchema#refreshAnalyzers()} to
   * update the Analyzers for the registered fields.
   * </p>
   *
   * <p>
   * NOTE: this function is not thread safe.  However, it is safe to use within the standard
   * <code>inform( SolrCore core )</code> function for <code>SolrCoreAware</code> classes.
   * Outside <code>inform</code>, this could potentially throw a ConcurrentModificationException
   * </p>
   */
  public Map<String,FieldType> getFieldTypes() { return fieldTypes; }

  private void readSchema(final InputStream is) {
    log.info("Reading Solr Schema");

    try {
      // pass the config resource loader to avoid building an empty one for no reason:
      // in the current case though, the stream is valid so we wont load the resource by name
      final Config schemaConf = new Config(loader, "schema", is, "/schema/");
      final Document document = schemaConf.getDocument();
      final XPath xpath = schemaConf.getXPath();
      final List<SchemaAware> schemaAware = new ArrayList<SchemaAware>();
      final Node nd = (Node) xpath.evaluate("/schema/@name", document, XPathConstants.NODE);
      if (nd==null) {
        log.warn("schema has no name!");
      } else {
        name = nd.getNodeValue();
        log.info("Schema name=" + name);
      }

      version = schemaConf.getFloat("/schema/@version", 1.0f);

      final AbstractPluginLoader<FieldType> fieldLoader = new AbstractPluginLoader<FieldType>( "[schema.xml] fieldType", true, true) {

        @Override
        protected FieldType create( final ResourceLoader loader, final String name, final String className, final Node node ) throws Exception
        {
          final FieldType ft = (FieldType)loader.newInstance(className);
          if (!(ft instanceof SubTextField)) {
            throw new SolrException(ErrorCode.FORBIDDEN, "Subschema must use SubTextField");
          }
          ((SubTextField) ft).setTypeName(name);

          String expression = "./analyzer[@type='query']";
          Node anode = (Node)xpath.evaluate(expression, node, XPathConstants.NODE);
          Analyzer queryAnalyzer = SubIndexSchema.this.readAnalyzer(anode);

          // An analyzer without a type specified, or with type="index"
          expression = "./analyzer[not(@type)] | ./analyzer[@type='index']";
          anode = (Node)xpath.evaluate(expression, node, XPathConstants.NODE);
          Analyzer analyzer = SubIndexSchema.this.readAnalyzer(anode);

          if (queryAnalyzer==null) queryAnalyzer=analyzer;
          if (analyzer==null) analyzer=queryAnalyzer;
          if (analyzer!=null) {
            ft.setAnalyzer(analyzer);
            ft.setQueryAnalyzer(queryAnalyzer);
          }
          return ft;
        }

        @Override
        protected void init(final FieldType plugin, final Node node) throws Exception {}

        @Override
        protected FieldType register(final String name, final FieldType plugin) throws Exception {
          log.trace("fieldtype defined: " + plugin );
          return fieldTypes.put( name, plugin );
        }
      };


      final String expression = "/schema/types/fieldtype | /schema/types/fieldType";
      final NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
      fieldLoader.load( loader, nodes );
    }
    catch (final SolrException e) {
      SolrConfig.severeErrors.add( e );
      throw e;
    }
    catch (final Exception e) {
      // unexpected exception...
      SolrConfig.severeErrors.add( e );
      throw new SolrException( SolrException.ErrorCode.SERVER_ERROR,"Schema Parsing Failed",e,false);
    }
  }

  //
  // <analyzer><tokenizer class="...."/><tokenizer class="...." arg="....">
  //
  //
  private Analyzer readAnalyzer(final Node node) throws XPathExpressionException {
    // parent node used to be passed in as "fieldtype"
    // if (!fieldtype.hasChildNodes()) return null;
    // Node node = DOMUtil.getChild(fieldtype,"analyzer");

    if (node == null) return null;
    final NamedNodeMap attrs = node.getAttributes();
    final String analyzerName = DOMUtil.getAttr(attrs,"class");
    if (analyzerName != null) {
      // No need to be core-aware as Analyzers are not in the core-aware list
      final Class<? extends Analyzer> clazz = loader.findClass(analyzerName).asSubclass(Analyzer.class);
      try {
        try {
          // first try to use a ctor with version parameter (needed for many new Analyzers that have no default one anymore)
          final Constructor<? extends Analyzer> cnstr = clazz.getConstructor(Version.class);
          final String matchVersionStr = DOMUtil.getAttr(attrs, LUCENE_MATCH_VERSION_PARAM);
          final Version luceneMatchVersion = (matchVersionStr == null) ?
            this.luceneMatchVersion : Config.parseLuceneVersionString(matchVersionStr);
          if (luceneMatchVersion == null) {
            throw new SolrException( SolrException.ErrorCode.SERVER_ERROR,
              "Configuration Error: Analyzer '" + clazz.getName() +
              "' needs a 'luceneMatchVersion' parameter");
          }
          return cnstr.newInstance(luceneMatchVersion);
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
      new AbstractPluginLoader<CharFilterFactory>( "[schema.xml] analyzer/charFilter", false, false )
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
    charFilterLoader.load(loader, (NodeList)xpath.evaluate("./charFilter", node, XPathConstants.NODESET) );

    // Load the Tokenizer
    // Although an analyzer only allows a single Tokenizer, we load a list to make sure
    // the configuration is ok
    // --------------------------------------------------------------------------------
    final ArrayList<TokenizerFactory> tokenizers = new ArrayList<TokenizerFactory>(1);
    final AbstractPluginLoader<TokenizerFactory> tokenizerLoader =
      new AbstractPluginLoader<TokenizerFactory>( "[schema.xml] analyzer/tokenizer", false, false )
    {
      @Override
      protected void init(final TokenizerFactory plugin, final Node node) throws Exception {
        if( !tokenizers.isEmpty() ) {
          throw new SolrException( SolrException.ErrorCode.SERVER_ERROR,
              "The schema defines multiple tokenizers for: "+node );
        }
        final Map<String,String> params = DOMUtil.toMapExcept(node.getAttributes(),"class");
        // copy the luceneMatchVersion from config, if not set
        if (!params.containsKey(LUCENE_MATCH_VERSION_PARAM))
          params.put(LUCENE_MATCH_VERSION_PARAM, luceneMatchVersion.toString());
        plugin.init( params );
        tokenizers.add( plugin );
      }

      @Override
      protected TokenizerFactory register(final String name, final TokenizerFactory plugin) throws Exception {
        return null; // used for map registration
      }
    };
    tokenizerLoader.load( loader, (NodeList)xpath.evaluate("./tokenizer", node, XPathConstants.NODESET) );

    // Make sure something was loaded
    if( tokenizers.isEmpty() ) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,"analyzer without class or tokenizer & filter list");
    }


    // Load the Filters
    // --------------------------------------------------------------------------------
    final ArrayList<TokenFilterFactory> filters = new ArrayList<TokenFilterFactory>();
    final AbstractPluginLoader<TokenFilterFactory> filterLoader =
      new AbstractPluginLoader<TokenFilterFactory>( "[schema.xml] analyzer/filter", false, false )
    {
      @Override
      protected void init(final TokenFilterFactory plugin, final Node node) throws Exception {
        if( plugin != null ) {
          final Map<String,String> params = DOMUtil.toMapExcept(node.getAttributes(),"class");
          // copy the luceneMatchVersion from config, if not set
          if (!params.containsKey(LUCENE_MATCH_VERSION_PARAM))
            params.put(LUCENE_MATCH_VERSION_PARAM, luceneMatchVersion.toString());
          plugin.init( params );
          filters.add( plugin );
        }
      }

      @Override
      protected TokenFilterFactory register(final String name, final TokenFilterFactory plugin) throws Exception {
        return null; // used for map registration
      }
    };
    filterLoader.load( loader, (NodeList)xpath.evaluate("./filter", node, XPathConstants.NODESET) );

    return new TokenizerChain(charFilters.toArray(new CharFilterFactory[charFilters.size()]),
        tokenizers.get(0), filters.toArray(new TokenFilterFactory[filters.size()]));
  };

  /**
   * Given the name of a {@link org.apache.solr.schema.FieldType} (not to be confused with {@link #getFieldType(String)} which
   * takes in the name of a field), return the {@link org.apache.solr.schema.FieldType}.
   * @param fieldTypeName The name of the {@link org.apache.solr.schema.FieldType}
   * @return The {@link org.apache.solr.schema.FieldType} or null.
   */
  public FieldType getFieldTypeByName(final String fieldTypeName){
    return fieldTypes.get(fieldTypeName);
  }

//  /**
//   * Create a new instance of the given field type.<br>
//   * This method is necessary because the SolrResourceLoader is not able to find
//   * custom FieldType class.<br>
//   * This should be safe, because all the custom classes should have been
//   * already loaded into the system previously.
//   */
//  private Object newInstance(final String cname) {
//    Class clazz = null;
//    try {
//      clazz = Class.forName(cname);
//    }
//    catch (final ClassNotFoundException e) {
//      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
//          "Can not find class: "+cname, false);
//    }
//
//    Object obj = null;
//    try {
//      obj = clazz.newInstance();
//    }
//    catch (final Exception e) {
//      throw new SolrException( SolrException.ErrorCode.SERVER_ERROR,
//          "Error instantiating class: '" + clazz.getName()+"'", e, false );
//    }
//
//    return obj;
//  }

}

