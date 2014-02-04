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

package org.sindice.siren.solr.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.apache.lucene.util.Version;
import org.apache.solr.common.SolrException;
import org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter;
import org.sindice.siren.solr.schema.Datatype;

/**
 * Factory for {@link DatatypeAnalyzerFilter}.
 */
public class DatatypeAnalyzerFilterFactory extends TokenFilterFactory {

  private final Map<String, Analyzer> analyzers;
  private final Version luceneDefaultVersion;

  public DatatypeAnalyzerFilterFactory(final Version luceneDefaultVersion) {
    analyzers = new HashMap<String, Analyzer>();
    this.luceneDefaultVersion = luceneDefaultVersion;
  }

  public Map<String, Analyzer> getDatatypeAnalyzers() {
    return analyzers;
  }

  /**
   * Register the datatypes to be used by the {@link DatatypeAnalyzerFilter}.
   *
   * @param datatypes The datatypes to register.
   */
  public void register(final Map<String, Datatype> datatypes) {
    for (final Entry<String, Datatype> e : datatypes.entrySet()) {

      if (e.getValue().getAnalyzer() == null) {
        throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
          "Configuration Error: No analyzer defined for type 'index' in " +
          "datatype " + e.getKey());
      }

      analyzers.put(e.getKey(), e.getValue().getAnalyzer());
    }
  }

  @Override
  public TokenStream create(final TokenStream input) {
    final DatatypeAnalyzerFilter f = new DatatypeAnalyzerFilter(luceneDefaultVersion, input);

    for (final String datatype : analyzers.keySet()) {
      f.register(datatype.toCharArray(), analyzers.get(datatype));
    }

    return f;
  }

}