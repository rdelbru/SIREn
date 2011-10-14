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
package org.sindice.siren.analysis.bench;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.TupleTokenizer;
import org.sindice.siren.analysis.filter.SirenPayloadFilter;
import org.sindice.siren.analysis.filter.TokenTypeFilter;
import org.sindice.siren.analysis.filter.DataTypeAnalyzerFilter;

/**
 * Old version of the TupleAnalyzer that does not encode tuple and cell ids in
 * delta representation.
 */
public class NoDeltaTupleAnalyzer
extends Analyzer {

  private Analyzer stringAnalyzer;
  private Analyzer anyURIAnalyzer;

  private final Set<?>            stopSet;

  /**
   * An array containing some common English words that are usually not useful
   * for searching.
   */
  public static final Set<?> STOP_WORDS = StopAnalyzer.ENGLISH_STOP_WORDS_SET;

  /**
   * Builds an analyzer with the default stop words ({@link #STOP_WORDS}).
   */
  public NoDeltaTupleAnalyzer(final Analyzer stringAnalyzer, final Analyzer anyURIAnalyzer) {
    this(stringAnalyzer, anyURIAnalyzer, STOP_WORDS);
  }

  /**
   * Builds an analyzer with the given stop words.
   */
  public NoDeltaTupleAnalyzer(final Analyzer stringAnalyzer, final Analyzer anyURIAnalyzer, final Set<?> stopWords) {
    this.stringAnalyzer = stringAnalyzer;
    this.anyURIAnalyzer = anyURIAnalyzer;
    stopSet = stopWords;
  }

  /**
   * Builds an analyzer with the given stop words.
   */
  public NoDeltaTupleAnalyzer(final Analyzer stringAnalyzer, final Analyzer anyURIAnalyzer, final String[] stopWords) {
    this.stringAnalyzer = stringAnalyzer;
    this.anyURIAnalyzer = anyURIAnalyzer;
    stopSet = StopFilter.makeStopSet(Version.LUCENE_31, stopWords);
  }

  /**
   * Builds an analyzer with the stop words from the given file.
   *
   * @see WordlistLoader#getWordSet(File)
   */
  public NoDeltaTupleAnalyzer(final Analyzer stringAnalyzer, final Analyzer anyURIAnalyzer, final File stopwords) throws IOException {
    this.stringAnalyzer = stringAnalyzer;
    this.anyURIAnalyzer = anyURIAnalyzer;
    stopSet = WordlistLoader.getWordSet(stopwords);
  }

  /**
   * Builds an analyzer with the stop words from the given reader.
   *
   * @see WordlistLoader#getWordSet(Reader)
   */
  public NoDeltaTupleAnalyzer(final Analyzer stringAnalyzer, final Analyzer anyURIAnalyzer, final Reader stopwords) throws IOException {
    this.stringAnalyzer = stringAnalyzer;
    this.anyURIAnalyzer = anyURIAnalyzer;
    stopSet = WordlistLoader.getWordSet(stopwords);
  }

  public void setLiteralAnalyzer(final Analyzer analyzer) {
    stringAnalyzer = analyzer;
  }

  public void setAnyURIAnalyzer(final Analyzer analyzer) {
    anyURIAnalyzer = analyzer;
  }

  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    final TupleTokenizer stream = new TupleTokenizer(reader, Integer.MAX_VALUE);
    TokenStream result = new TokenTypeFilter(stream, new int[] {TupleTokenizer.BNODE,
                                                                TupleTokenizer.DOT});
    result = new DataTypeAnalyzerFilter(Version.LUCENE_31, result, stringAnalyzer, anyURIAnalyzer);
    result = new SirenPayloadFilter(result);
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
      streams.filteredTokenStream = new DataTypeAnalyzerFilter(Version.LUCENE_31, streams.filteredTokenStream,
        stringAnalyzer, anyURIAnalyzer);
      streams.filteredTokenStream = new SirenPayloadFilter(streams.filteredTokenStream);
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
