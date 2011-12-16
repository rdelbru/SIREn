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
package org.sindice.siren.solr.analysis;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter;

public class DatatypeAnalyzerFilterFactory
extends BaseTokenFilterFactory {

  private final Map<String, Analyzer> analyzers;

  public DatatypeAnalyzerFilterFactory() {
    analyzers = new HashMap<String, Analyzer>();
  }

  public Map<String, Analyzer> getDatatypeAnalyzers() {
    return analyzers;
  }

  public void register(final String datatype, final Analyzer analyzer) {
    analyzers.put(datatype, analyzer);
  }

  @Override
  public TokenStream create(final TokenStream input) {
    final DatatypeAnalyzerFilter f = new DatatypeAnalyzerFilter(luceneMatchVersion, input);

    for (final String datatype : analyzers.keySet()) {
      f.register(datatype.toCharArray(), analyzers.get(datatype));
    }

    return f;
  }

}