/**
 * Copyright 2010, Renaud Delbru
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
 * @project siren
 * @author Renaud Delbru [ 19 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.schema.FieldType;
import org.apache.solr.schema.SubIndexSchema;

/**
 * Wrapper used to provide various Analyzers to the query parser.
 */
public class MultiQueryAnalyzerWrapper extends Analyzer {

  /**
   * At the moment, it is hardcoded until we can pass parameter to an Analzyer
   * in the schema.
   */
  private final String subschema = "ntriple-schema.xml";

  private final Map<String, Analyzer> analyzers = new HashMap<String, Analyzer>();

  public MultiQueryAnalyzerWrapper() {
    this.extractAnalyzers(this.loadSubSchema(subschema));
  }

  public Analyzer getAnalyzer(final String fieldtype) {
    return analyzers.get(fieldtype);
  }

  /**
   * Extract the query analyzers for each field type.
   */
  private void extractAnalyzers(final SubIndexSchema subschema) {
    for (final Entry<String, FieldType> entry : subschema.getFieldTypes().entrySet()) {
      analyzers.put(entry.getKey(), entry.getValue().getQueryAnalyzer());
    }
  }

  /**
   * load subschema
   */
  private SubIndexSchema loadSubSchema(final String subschema) {
    final String solrHome = SolrResourceLoader.locateSolrHome();
    final String schemaPath = solrHome + "conf" + File.separator + subschema;
    try {
      return new SubIndexSchema(schemaPath, Version.LUCENE_31);
    } catch (final FileNotFoundException e) {
      throw new SolrException(SolrException.ErrorCode.NOT_FOUND, e.getMessage());
    }
  }

  /* (non-Javadoc)
   * @see org.apache.lucene.analysis.Analyzer#tokenStream(java.lang.String, java.io.Reader)
   */
  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    // Might be called when using a filter query without field operators
    throw new SolrException(ErrorCode.SERVER_ERROR, "Invalid call: you might have used a filter query without field operators");
  }

  /* (non-Javadoc)
   * @see org.apache.lucene.analysis.Analyzer#tokenStream(java.lang.String, java.io.Reader)
   */
  @Override
  public final TokenStream reusableTokenStream(final String fieldName, final Reader reader) {
    // Might be called when using a filter query without field operators
    throw new SolrException(ErrorCode.SERVER_ERROR, "Invalid call: you might have used a filter query without field operators");
  }

}
