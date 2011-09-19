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
