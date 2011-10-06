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
 * @project siren-core_rdelbru
 * @author Campinas Stephane [ 3 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.analysis;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.filter.AssignTokenTypeFilter;

/**
 * Analyzer designed to deal with any kind of URIs.
 */
public class WhitespaceAnyURIAnalyzer extends Analyzer {

  private final Set<?>            stopSet;

  /**
   * An array containing some common English words that are usually not useful
   * for searching.
   */
  public static final Set<?> STOP_WORDS = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
  
  public WhitespaceAnyURIAnalyzer() {
    this(STOP_WORDS);
  }
  
  public WhitespaceAnyURIAnalyzer(final Set<?> stopWords) {
    stopSet = stopWords;
  }
  
  public WhitespaceAnyURIAnalyzer(final String[] stopWords) {
    stopSet = StopFilter.makeStopSet(Version.LUCENE_31, stopWords);
  }
  
  public WhitespaceAnyURIAnalyzer(final File stopwords) throws IOException {
    stopSet = WordlistLoader.getWordSet(stopwords);
  }
  
  public WhitespaceAnyURIAnalyzer(final Reader stopWords) throws IOException {
    stopSet = WordlistLoader.getWordSet(stopWords);
  }
  
  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    TokenStream result = new WhitespaceTokenizer(Version.LUCENE_31, reader);
    result = new LowerCaseFilter(Version.LUCENE_31, result);
    result = new AssignTokenTypeFilter(result, TupleTokenizer.URI);
    return result;
  }

  @Override
  public final TokenStream reusableTokenStream(final String fieldName, final Reader reader) throws IOException {
    SavedStreams streams = (SavedStreams) this.getPreviousTokenStream();
    if (streams == null) {
      streams = new SavedStreams();
      this.setPreviousTokenStream(streams);
      streams.tokenStream = new WhitespaceTokenizer(Version.LUCENE_31, reader);
      streams.filteredTokenStream = new LowerCaseFilter(Version.LUCENE_31, streams.tokenStream);
      streams.filteredTokenStream = new AssignTokenTypeFilter(streams.filteredTokenStream, TupleTokenizer.URI);
    } else {
      streams.tokenStream.reset(reader);
    }
    return streams.filteredTokenStream;
  }

  private static final class SavedStreams {
    WhitespaceTokenizer tokenStream;
    TokenStream filteredTokenStream;
  }

}
