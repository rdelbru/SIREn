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
 * @project siren-solr
 * @author Renaud Delbru [ 13 Oct 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.solr.schema;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SortField;
import org.apache.solr.common.ResourceLoader;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.response.TextResponseWriter;
import org.apache.solr.response.XMLWriter;
import org.apache.solr.schema.FieldType;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaField;
import org.apache.solr.search.QParser;
import org.apache.solr.util.plugin.ResourceLoaderAware;
import org.xml.sax.InputSource;

/** <code>SirenField</code> is the basic type for configurable n-tuple analysis.
 * Analyzers for fields using this field type should be defined in:
 * <ul>
 * <li> the top-level analyzer config
 * <li> the datatype config
 * <ul>
 */
public class SirenField extends FieldType implements ResourceLoaderAware {

  private IndexSchema schema;

  private String topLevelAnalyzerConfigPath;
  private String datatypeAnalyzerConfigPath;

  private final AtomicReference<SirenDatatypeAnalyzerConfig> datatypeConfigRef = new AtomicReference<SirenDatatypeAnalyzerConfig>();
  private final AtomicReference<SirenTopLevelAnalyzerConfig> analyzerConfigRef = new AtomicReference<SirenTopLevelAnalyzerConfig>();

  @Override
  protected void init(final IndexSchema schema, final Map<String,String> args) {
    this.schema = schema;

    this.topLevelAnalyzerConfigPath = args.get("analyzerConfig");
    args.remove("analyzerConfig");

    if (topLevelAnalyzerConfigPath == null) {
      final SolrException e = this.createAndLogOnceException("Missing analyzerConfig parameter");
      throw e;
    }

    this.datatypeAnalyzerConfigPath = args.get("datatypeConfig");
    args.remove("datatypeConfig");

    if (datatypeAnalyzerConfigPath == null) {
      final SolrException e = this.createAndLogOnceException("Missing datatypeConfig parameter");
      throw e;
    }

    // Check if OMIT_TF_POSITIONS is set to false
    // Necessary since we cannot modify ourself the {@code properties} attribute
    if (this.hasProperty(OMIT_TF_POSITIONS)) {
      throw this.createAndLogOnceException("'omitTermFreqAndPositions' parameter must be set to false");
    }

    super.init(schema, args);
  }

  /**
   * Returns always true.
   * <p>
   * SIREn fields are always tokenised.
   * <p>
   * We have to override this method because the {@code properties}
   * attribute from {@link FieldType} is private.
   **/
  @Override
  public boolean isTokenized() {
    return true;
  }

  /**
   * Returns always false.
   * <p>
   * SIREn fields are not multi-valued.
   * <p>
   * We have to override this method because the {@code properties}
   * attribute from {@link FieldType} is private.
   **/
  @Override
  public boolean isMultiValued() {
    return false;
  }

  private SolrException createAndLogOnceException(final String message) {
    final SolrException e = new SolrException
    (ErrorCode.SERVER_ERROR, "FieldType: " + this.getClass().getSimpleName() +
     " (" + typeName + ") " + message);
    SolrException.logOnce(log, null, e);
    return e;
  }

  @Override
  public SortField getSortField(final SchemaField field, final boolean reverse) {
    final SolrException e = this.createAndLogOnceException("does not support sorting");
    throw e;
  }

  @Override
  public void write(final XMLWriter xmlWriter, final String name, final Fieldable f)
  throws IOException {
    xmlWriter.writeStr(name, f.stringValue());
  }

  @Override
  public void write(final TextResponseWriter writer, final String name, final Fieldable f)
  throws IOException {
    writer.writeStr(name, f.stringValue(), true);
  }

  @Override
  public Query getFieldQuery(final QParser parser, final SchemaField field,
                             final String externalVal) {
    final SolrException e = this.createAndLogOnceException("does not support field query");
    throw e;
  }

  @Override
  public Query getRangeQuery(final QParser parser, final SchemaField field,
                             final String part1, final String part2,
                             final boolean minInclusive, final boolean maxInclusive) {
    final SolrException e = this.createAndLogOnceException("does not support range query");
    throw e;
  }

  @Override
  public void setAnalyzer(final Analyzer analyzer) {
    this.analyzer = analyzer;
  }

  @Override
  public void setQueryAnalyzer(final Analyzer analyzer) {
    this.queryAnalyzer = analyzer;
  }

  public Map<String, Datatype> getDatatypes() {
    return this.datatypeConfigRef.get().getDatatypes();
  }

  /**
   * Load the datatype analyzer config file specified by the schema.
   * <p/>
   * This should be called whenever the datatype analyzer configuration file changes.
   *
   * @param loader The resource loader.
   */
  private void loadDatatypeConfig(final ResourceLoader loader) {
    InputStream is;
    log.info("Loading datatype analyzer configuration file at " + datatypeAnalyzerConfigPath);

    try {
      is = loader.openResource(datatypeAnalyzerConfigPath);
    } catch (final IOException e) {
      log.error("Error loading datatype analyzer configuration file at " + datatypeAnalyzerConfigPath, e);
      throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, e);
    }

    try {
      final SirenDatatypeAnalyzerConfig newConfig =
        new SirenDatatypeAnalyzerConfig((SolrResourceLoader) loader,
          datatypeAnalyzerConfigPath, new InputSource(is),
          schema.getSolrConfig().luceneMatchVersion);
      log.info("Read new datatype analyzer configuration " + newConfig);
      datatypeConfigRef.set(newConfig);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (final IOException ignored) {
        }
      }
    }
  }

  /**
   * Load the top-level analyzer config file specified by the schema.
   * <p/>
   * This should be called whenever the top-level analyzer configuration file changes.
   *
   * @param loader The resource loader.
   */
  private void loadTopLevelAnalyzerConfig(final ResourceLoader loader) {
    InputStream is;
    log.info("Loading top-level analyzer configuration file at " + topLevelAnalyzerConfigPath);

    try {
      is = loader.openResource(topLevelAnalyzerConfigPath);
    } catch (final IOException e) {
      log.error("Error loading top-level analyzer configuration file at " + topLevelAnalyzerConfigPath, e);
      throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, e);
    }

    try {
      final SirenTopLevelAnalyzerConfig newConfig =
        new SirenTopLevelAnalyzerConfig((SolrResourceLoader) loader,
          topLevelAnalyzerConfigPath, new InputSource(is),
          schema.getSolrConfig().luceneMatchVersion);
      log.info("Read new top-level analyzer configuration " + newConfig);
      analyzerConfigRef.set(newConfig);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (final IOException ignored) {
        }
      }
    }
  }

  /**
   * Load the datatype config and the top-level analyzer config when resource
   * loader initialized.
   *
   * @param resourceLoader The resource loader.
   */
  public void inform(final ResourceLoader loader) {
    // load the datatypes
    this.loadDatatypeConfig(loader);
    // load the top level analyzers
    this.loadTopLevelAnalyzerConfig(loader);
    // register the datatypes
    this.analyzerConfigRef.get().register(this.datatypeConfigRef.get().getDatatypes());
    // update the analyzer references
    this.analyzer = this.analyzerConfigRef.get().getAnalyzer();
    this.queryAnalyzer = this.analyzerConfigRef.get().getKeywordQueryAnalyzer();
    // tell the {@link IndexSchema} to refresh its analyzers
    schema.refreshAnalyzers();
  }

}

