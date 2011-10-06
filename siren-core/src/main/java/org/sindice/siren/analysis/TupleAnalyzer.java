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
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.filter.SirenDeltaPayloadFilter;
import org.sindice.siren.analysis.filter.TokenTypeFilter;
import org.sindice.siren.analysis.filter.TupleTokenAnalyzerFilter;

/**
 * The TupleAnalyzer is especially designed to process RDF data. It applies
 * various post-processing on URIs and Literals.
 * <br>
 * The URI normalisation can be configured. You can disable it, activate it
 * only on URI local name, or on the full URI. However, URI normalisation on the
 * full URI is costly in term of CPU at indexing time, and can double the size
 * of the index, since each URI is duplicated by n tokens.
 * <br>
 * By default, the URI normalisation is disabled.
 * <br>
 * When full uri normalisation is activated, the analyzer is much slower than
 * the WhitespaceTupleAnalyzer. If you are not indexing RDF data, consider to
 * use the WhitespaceTupleAnalyzer instead.
 */
public class TupleAnalyzer extends Analyzer {

  private Analyzer stringAnalyzer;
  private Analyzer anyURIAnalyzer;
  
  private final Version matchVersion;
  
  private final HashMap<CharBuffer, Analyzer> regLitAnalyzers = new HashMap<CharBuffer, Analyzer>();

  
  public TupleAnalyzer(Version version, final Analyzer stringAnalyzer, final Analyzer anyURIAnalyzer) {
    matchVersion = version;
    this.stringAnalyzer = stringAnalyzer;
    this.anyURIAnalyzer = anyURIAnalyzer;
    
  }
  
  public void setLiteralAnalyzer(final Analyzer analyzer) {
    stringAnalyzer = analyzer;
  }

  public void setAnyURIAnalyzer(final Analyzer analyzer) {
    anyURIAnalyzer = analyzer;
  }

  public void registerLiteralAnalyzer(CharBuffer datatype, Analyzer a) {
    if (!regLitAnalyzers.containsKey(datatype)) {
      regLitAnalyzers.put(datatype, a);
    }
  }
  
  public void clearRegisterLiteralAnalyzers() {
    regLitAnalyzers.clear();
  }

  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    final TupleTokenizer stream = new TupleTokenizer(reader, Integer.MAX_VALUE);
    TokenStream result = new TokenTypeFilter(stream, new int[] {TupleTokenizer.BNODE,
                                                                TupleTokenizer.DOT});
    final TupleTokenAnalyzerFilter tt = new TupleTokenAnalyzerFilter(result, stringAnalyzer, anyURIAnalyzer);
    for (Entry<CharBuffer, Analyzer> e : regLitAnalyzers.entrySet()) {
      tt.register(e.getKey(), e.getValue());
    }
    result = new SirenDeltaPayloadFilter(tt);
    return result;
  }

  @Override
  public final TokenStream reusableTokenStream(final String fieldName, final Reader reader) throws IOException {
    SavedStreams streams = (SavedStreams) this.getPreviousTokenStream();
    if (streams == null) {
      streams = new SavedStreams();
      this.setPreviousTokenStream(streams);
      streams.tokenStream = new TupleTokenizer(reader, Integer.MAX_VALUE);
      streams.filteredTokenStream = new TokenTypeFilter(streams.tokenStream,
        new int[] {TupleTokenizer.BNODE, TupleTokenizer.DOT});
      final TupleTokenAnalyzerFilter tt = new TupleTokenAnalyzerFilter(streams.filteredTokenStream, stringAnalyzer, anyURIAnalyzer);
      for (Entry<CharBuffer, Analyzer> e : regLitAnalyzers.entrySet()) {
        tt.register(e.getKey(), e.getValue());
      }
      streams.filteredTokenStream = new SirenDeltaPayloadFilter(tt);

    } else {
      streams.tokenStream.reset(reader);
    }
    return streams.filteredTokenStream;
  }

  private static final class SavedStreams {
    TupleTokenizer tokenStream;
    TokenStream filteredTokenStream;
  }

}
