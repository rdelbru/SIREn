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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SortField;
import org.apache.lucene.util.Version;
import org.apache.solr.analysis.TokenizerChain;
import org.apache.solr.common.SolrException;
import org.apache.solr.response.TextResponseWriter;
import org.apache.solr.schema.FieldProperties;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaAware;
import org.apache.solr.schema.SchemaField;
import org.apache.solr.schema.TextField;
import org.apache.solr.search.QParser;
import org.sindice.siren.index.codecs.siren10.Siren10AForPostingsFormat;
import org.sindice.siren.solr.analysis.DatatypeAnalyzerFilterFactory;
import org.sindice.siren.solr.analysis.PositionAttributeFilterFactory;
import org.sindice.siren.solr.analysis.SirenPayloadFilterFactory;
import org.xml.sax.InputSource;

/**
 * <code>SirenField</code> is the basic type for configurable tree-based data
 * analysis.
 *
 * <p>
 *
 * This field type relies on:
 * <ul>
 * <li> an index analyzer configuration (no query analyzer is required)
 * <li> a datatype analyzers configuration using the parameter
 *      <code>datatypeConfig</code>
 * </ul>
 *
 * <p>
 *
 * This field type enforces certain field properties
 * by throwing a {@link SolrException} if the field type does not set properly
 * the properties. By default all the properties are set properly, i.e.,
 * a user should not modify these properties. This field type enforces also
 * the <code>postingsFormat</code> to <code>Siren10Afor</code>. The list of
 * enforced field properties are:
 * <ul>
 * <li> indexed = true
 * <li> tokenized = true
 * <li> omitNorm = true
 * <li> multiValued = false
 * <li> omitTermFreqAndPositions = false
 * <li> omitPositions = false
 * <li> termVectors = false
 * </ul>
 *
 * <p>
 *
 * A {@link SchemaField} can overwrite these properties, however an exception
 * will be thrown when converting it to a Lucene's
 * {@link org.apache.lucene.document.FieldType} in
 * {@link #createField(SchemaField, Object, float)}.
 *
 * <p>
 *
 * This field type extends {@link TextField} to have the
 * {@link FieldProperties#OMIT_TF_POSITIONS} set to false by default.
 */
public class SirenField extends TextField implements SchemaAware {

  private String datatypeAnalyzerConfigPath;

  private final AtomicReference<SirenDatatypeAnalyzerConfig> datatypeConfigRef = new AtomicReference<SirenDatatypeAnalyzerConfig>();

  @Override
  protected void init(final IndexSchema schema, final Map<String,String> args) {
    // first call TextField.init to set omitTermFreqAndPositions to false
    super.init(schema, args);
    this.checkFieldTypeProperties();
    // initialise specific SIREn's properties
    this.datatypeAnalyzerConfigPath = args.get("datatypeConfig");
    args.remove("datatypeConfig");

    if (datatypeAnalyzerConfigPath == null) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField types require a 'datatypeConfig' " +
                              "parameter: " + this.typeName);
    }

    // set the posting format
    this.postingsFormat = Siren10AForPostingsFormat.NAME;

    super.init(schema, args);
  }

  private void checkFieldTypeProperties() {
    if (this.isMultiValued()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField types can not be multiValued: " +
                              this.typeName);
    }
    if (!this.hasProperty(FieldProperties.OMIT_NORMS)) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField types must omit norms: " +
                              this.typeName);
    }
    if (this.hasProperty(FieldProperties.OMIT_TF_POSITIONS)) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField types can not omit term frequencies " +
                              "and positions: " + this.typeName);
    }
    if (this.hasProperty(FieldProperties.OMIT_POSITIONS)) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField types can not omit positions: " +
                              this.typeName);
    }
    if (this.hasProperty(FieldProperties.STORE_TERMVECTORS)) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField types can not store term vectors: " +
                              this.typeName);
    }
  }

  @Override
  public IndexableField createField(final SchemaField field, final Object value,
                                    final float boost) {
    if (!field.indexed()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances must be indexed: " +
                              field.getName());
    }
    if (field.multiValued()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances can not be multivalued: " +
                              field.getName());
    }
    if (!field.omitNorms()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances must omit norms: " +
                              field.getName());
    }
    if (field.omitTermFreqAndPositions()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances must not omit term " +
                              "frequencies and positions: " + field.getName());
    }
    if (field.omitPositions()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances must not omit term " +
                              "positions: " + field.getName());
    }
    if (field.storeTermVector()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances can not store term vectors: " +
                              field.getName());
    }
    return super.createField(field, value, boost);
  }

  @Override
  protected IndexableField createField(final String name, final String val,
                                       final org.apache.lucene.document.FieldType type,
                                       final float boost){

    if (!type.indexed()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances must be indexed: " + name);
    }
    if (!type.tokenized()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances must be tokenised: " + name);
    }
    if (!type.omitNorms()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances must omit norms: " + name);
    }
    if (!type.indexOptions().equals(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances must not omit term " +
                              "frequencies and positions: " + name);
    }
    if (type.storeTermVectors()) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                              "SirenField instances can not store term vectors: " +
                              name);
    }

    return super.createField(name, val, type, boost);
  }

  @Override
  public SortField getSortField(final SchemaField field, final boolean reverse) {
    throw new SolrException(SolrException.ErrorCode.BAD_REQUEST,
                            "Unsupported operation. Can not sort on SIREn field: "
                            + field.getName());
  }

  @Override
  public void write(final TextResponseWriter writer, final String name, final IndexableField f)
  throws IOException {
    writer.writeStr(name, f.stringValue(), true);
  }

  @Override
  public Query getFieldQuery(final QParser parser, final SchemaField field,
                             final String externalVal) {
    // Not useful for now in SIREn
    throw new SolrException(SolrException.ErrorCode.BAD_REQUEST,
                            "Not implemented operation."
                            + field.getName());
  }

  @Override
  public Query getRangeQuery(final QParser parser, final SchemaField field,
                             final String part1, final String part2,
                             final boolean minInclusive, final boolean maxInclusive) {
    throw new SolrException(SolrException.ErrorCode.BAD_REQUEST,
                            "Unsupported operation. Can not do range on SIREn field: "
                            + field.getName());
  }

  @Override
  public Analyzer getMultiTermAnalyzer() {
    throw new SolrException(SolrException.ErrorCode.BAD_REQUEST,
                            "Unsupported operation. Use getAnalyzer instead.");
  }

  @Override
  public boolean getAutoGeneratePhraseQueries() {
    throw new SolrException(SolrException.ErrorCode.BAD_REQUEST,
                            "Unsupported operation.");
  }

  @Override
  public void setAnalyzer(final Analyzer analyzer) {
    this.analyzer = analyzer;
  }

  @Override
  public void setQueryAnalyzer(final Analyzer analyzer) {
    // SirenField does not require a query analyzer
    queryAnalyzer = null;
  }

  public Map<String, Datatype> getDatatypes() {
    return this.datatypeConfigRef.get().getDatatypes();
  }

  /**
   * Load the datatype analyzer config file specified by the schema.
   * <p/>
   * This should be called whenever the datatype analyzer configuration file changes.
   */
  private void loadDatatypeConfig(final IndexSchema schema) {
    InputStream is;
    log.info("Loading datatype analyzer configuration file at " + datatypeAnalyzerConfigPath);

    try {
      is = schema.getResourceLoader().openResource(datatypeAnalyzerConfigPath);
    } catch (final IOException e) {
      log.error("Error loading datatype analyzer configuration file at " + datatypeAnalyzerConfigPath, e);
      throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, e);
    }

    try {
      final SirenDatatypeAnalyzerConfig newConfig =
        new SirenDatatypeAnalyzerConfig(schema.getResourceLoader(),
          datatypeAnalyzerConfigPath, new InputSource(is),
          schema.getDefaultLuceneMatchVersion());
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
   * When index schema is informed, load the datatype config and append the
   * SIREn's filters to the tokenizer chain.
   */
  @Override
  public void inform(final IndexSchema schema) {
    // load the datatypes
    this.loadDatatypeConfig(schema);
    // Append the SIREn's filters and update the index analyzer reference
    this.setAnalyzer(this.appendSirenFilters(
      this.getAnalyzer(),
      this.datatypeConfigRef.get().getDatatypes(),
      schema.getDefaultLuceneMatchVersion()));
    // tell the {@link IndexSchema} to refresh its analyzers
    schema.refreshAnalyzers();
  }

  /**
   * Append the mandatory SIREn filters, i.e.,
   * {@link DatatypeAnalyzerFilterFactory},
   * {@link PositionAttributeFilterFactory} and
   * {@link SirenPayloadFilterFactory}, to the tokenizer chain.
   */
  private Analyzer appendSirenFilters(final Analyzer analyzer,
                                      final Map<String, Datatype> datatypes,
                                      final Version luceneDefaultVersion) {
    if (!(analyzer instanceof TokenizerChain)) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
        "Invalid index analyzer '" + analyzer.getClass() + "' received");
    }

    final TokenizerChain chain = (TokenizerChain) analyzer;
    // copy the existing list of token filters
    final TokenFilterFactory[] old = chain.getTokenFilterFactories();
    final TokenFilterFactory[] filterFactories = new TokenFilterFactory[old.length + 3];
    System.arraycopy(old, 0, filterFactories, 0, old.length);
    // append the datatype analyzer filter factory
    final DatatypeAnalyzerFilterFactory datatypeFactory = new DatatypeAnalyzerFilterFactory(luceneDefaultVersion);
    datatypeFactory.register(datatypes);
    filterFactories[old.length] = datatypeFactory;
    // append the position attribute filter factory
    filterFactories[old.length + 1] = new PositionAttributeFilterFactory();
    // append the siren payload filter factory
    filterFactories[old.length + 2] = new SirenPayloadFilterFactory();
    // create a new tokenizer chain with the updated list of filter factories
    return new TokenizerChain(chain.getCharFilterFactories(),
      chain.getTokenizerFactory(), filterFactories);
  }

}

