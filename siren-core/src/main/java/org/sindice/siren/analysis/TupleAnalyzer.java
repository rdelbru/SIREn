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

package org.sindice.siren.analysis;

import java.io.Reader;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.CharArrayMap;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter;
import org.sindice.siren.analysis.filter.PositionAttributeFilter;
import org.sindice.siren.analysis.filter.SirenPayloadFilter;
import org.sindice.siren.analysis.filter.TokenTypeFilter;

/**
 * The TupleAnalyzer is especially designed to process RDF data. It applies
 * various post-processing on URIs and Literals.
 *
 * @deprecated Use {@link JsonAnalyzer} instead
 */
@Deprecated
public class TupleAnalyzer extends Analyzer {

  private Analyzer stringAnalyzer;
  private Analyzer anyURIAnalyzer;

  private final Version matchVersion;

  private final CharArrayMap<Analyzer> regLitAnalyzers;

  /**
   * Create a {@link TupleAnalyzer} with the default {@link Analyzer} for Literals and URIs.
   * @param version
   * @param stringAnalyzer default Literal {@link Analyzer}
   * @param anyURIAnalyzer default URI {@link Analyzer}
   */
  public TupleAnalyzer(final Version version, final Analyzer stringAnalyzer, final Analyzer anyURIAnalyzer) {
    matchVersion = version;
    this.stringAnalyzer = stringAnalyzer;
    this.anyURIAnalyzer = anyURIAnalyzer;
    regLitAnalyzers = new CharArrayMap<Analyzer>(version, 64, false);

  }

  public void setLiteralAnalyzer(final Analyzer analyzer) {
    stringAnalyzer = analyzer;
  }

  public void setAnyURIAnalyzer(final Analyzer analyzer) {
    anyURIAnalyzer = analyzer;
  }

  /**
   * Assign an {@link Analyzer} to be used with that key. That analyzer is used
   * to process tokens outputed from the {@link TupleTokenizer}.
   * @param datatype the Datatype
   * @param a the associated {@link Analyzer}
   */
  public void registerDatatype(final char[] datatype, final Analyzer a) {
    if (!regLitAnalyzers.containsKey(datatype)) {
      regLitAnalyzers.put(datatype, a);
    }
  }

  /**
   * Remove all registered Datatype {@link Analyzer}s.
   */
  public void clearDatatypes() {
    regLitAnalyzers.clear();
  }

  @Override
  protected TokenStreamComponents createComponents(final String fieldName, final Reader reader) {
    final TupleTokenizer source = new TupleTokenizer(reader);

    TokenStream sink = new TokenTypeFilter(source, new int[] {TupleTokenizer.BNODE,
                                                              TupleTokenizer.DOT});
    final DatatypeAnalyzerFilter tt = new DatatypeAnalyzerFilter(matchVersion, sink, anyURIAnalyzer, stringAnalyzer);
    for (final Entry<Object, Analyzer> e : regLitAnalyzers.entrySet()) {
      tt.register((char[]) e.getKey(), e.getValue());
    }
    sink = new PositionAttributeFilter(tt);
    sink = new SirenPayloadFilter(sink);
    return new TokenStreamComponents(source, sink);
  }

}
