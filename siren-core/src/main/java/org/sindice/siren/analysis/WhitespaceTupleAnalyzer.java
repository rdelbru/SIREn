/**
 * Copyright 2009, Renaud Delbru
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
 * @author Renaud Delbru [ 19 Mar 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
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
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.filter.PositionFilter;
import org.sindice.siren.analysis.filter.SirenPayloadFilter;
import org.sindice.siren.analysis.filter.TokenTypeFilter;

/**
 * The WhitespaceTupleAnalyzer is a more simple analyzer for tuples.
 * <br>
 * It uses a WhitespaceTokenizer for the literals, and do not perform
 * post-processing on URIs, Literals, etc. as the TupleAnalyzer is doing.
 * This results in much better performance.
 * <br>
 * This analyzer should be used instead of the TupleAnalyzer when you are
 * indexing records from database and not RDF.
 */

public class WhitespaceTupleAnalyzer
extends Analyzer {

  private final Set<?>            stopSet;

  /**
   * An array containing some common English words that are usually not useful
   * for searching.
   */
  public static final Set<?> STOP_WORDS = StopAnalyzer.ENGLISH_STOP_WORDS_SET;

  /**
   * Builds an analyzer with the default stop words ({@link #STOP_WORDS}).
   */
  public WhitespaceTupleAnalyzer() {
    this(STOP_WORDS);
  }

  /**
   * Builds an analyzer with the given stop words.
   */
  public WhitespaceTupleAnalyzer(final Set<?> stopWords) {
    stopSet = stopWords;
  }

  /**
   * Builds an analyzer with the given stop words.
   */
  public WhitespaceTupleAnalyzer(final String[] stopWords) {
    stopSet = StopFilter.makeStopSet(Version.LUCENE_31, stopWords);
  }

  /**
   * Builds an analyzer with the stop words from the given file.
   *
   * @see WordlistLoader#getWordSet(File)
   */
  public WhitespaceTupleAnalyzer(final File stopwords) throws IOException {
    stopSet = WordlistLoader.getWordSet(stopwords);
  }

  /**
   * Builds an analyzer with the stop words from the given reader.
   *
   * @see WordlistLoader#getWordSet(Reader)
   */
  public WhitespaceTupleAnalyzer(final Reader stopwords) throws IOException {
    stopSet = WordlistLoader.getWordSet(stopwords);
  }

  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    final TupleTokenizer stream = new TupleTokenizer(reader, Integer.MAX_VALUE,
        new WhitespaceAnalyzer(Version.LUCENE_31));
    TokenStream result = new TokenTypeFilter(stream, new int[] {TupleTokenizer.BNODE,
                                                                TupleTokenizer.DOT,
                                                                TupleTokenizer.DATATYPE,
                                                                TupleTokenizer.LANGUAGE});
    result = new LowerCaseFilter(Version.LUCENE_31, result);
    result = new PositionFilter(result); // in last, just before the payload filter since it assigns the token position
    result = new SirenPayloadFilter(result);
    return result;
  }

  @Override
  public final TokenStream reusableTokenStream(final String fieldName, final Reader reader) throws IOException {
    SavedStreams streams = (SavedStreams) this.getPreviousTokenStream();
    if (streams == null) {
      streams = new SavedStreams();
      this.setPreviousTokenStream(streams);
      streams.tokenStream = new TupleTokenizer(reader, Integer.MAX_VALUE,
          new WhitespaceAnalyzer(Version.LUCENE_31));
      streams.filteredTokenStream = new TokenTypeFilter(streams.tokenStream,
        new int[] {TupleTokenizer.BNODE, TupleTokenizer.DOT,
                   TupleTokenizer.DATATYPE, TupleTokenizer.LANGUAGE});
      streams.filteredTokenStream = new LowerCaseFilter(Version.LUCENE_31, streams.filteredTokenStream);
      streams.filteredTokenStream = new PositionFilter(streams.filteredTokenStream);
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
