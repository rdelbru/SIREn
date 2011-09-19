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

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LengthFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.filter.SirenPayloadFilter;
import org.sindice.siren.analysis.filter.TokenTypeFilter;
import org.sindice.siren.analysis.filter.URILocalnameFilter;
import org.sindice.siren.analysis.filter.URINormalisationFilter;
import org.sindice.siren.analysis.filter.URITrailingSlashFilter;

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
public class TupleAnalyzer
extends Analyzer {

  public enum URINormalisation {NONE, LOCALNAME, FULL};

  private URINormalisation normalisationType = URINormalisation.NONE;
  private Analyzer literalAnalyzer;

  private final Set<?>            stopSet;

  /**
   * An array containing some common English words that are usually not useful
   * for searching.
   */
  public static final Set<?> STOP_WORDS = StopAnalyzer.ENGLISH_STOP_WORDS_SET;

  /**
   * Builds an analyzer with the default stop words ({@link #STOP_WORDS}).
   */
  public TupleAnalyzer(final Analyzer literalAnalyzer) {
    this(literalAnalyzer, STOP_WORDS);
  }

  /**
   * Builds an analyzer with the given stop words.
   */
  public TupleAnalyzer(final Analyzer literalAnalyzer, final Set<?> stopWords) {
    this.literalAnalyzer = literalAnalyzer;
    stopSet = stopWords;
  }

  /**
   * Builds an analyzer with the given stop words.
   */
  public TupleAnalyzer(final Analyzer literalAnalyzer, final String[] stopWords) {
    this.literalAnalyzer = literalAnalyzer;
    stopSet = StopFilter.makeStopSet(Version.LUCENE_31, stopWords);
  }

  /**
   * Builds an analyzer with the stop words from the given file.
   *
   * @see WordlistLoader#getWordSet(File)
   */
  public TupleAnalyzer(final Analyzer literalAnalyzer, final File stopwords) throws IOException {
    this.literalAnalyzer = literalAnalyzer;
    stopSet = WordlistLoader.getWordSet(stopwords);
  }

  /**
   * Builds an analyzer with the stop words from the given reader.
   *
   * @see WordlistLoader#getWordSet(Reader)
   */
  public TupleAnalyzer(final Analyzer literalAnalyzer, final Reader stopwords) throws IOException {
    this.literalAnalyzer = literalAnalyzer;
    stopSet = WordlistLoader.getWordSet(stopwords);
  }

  public void setURINormalisation(final URINormalisation type) {
    this.normalisationType = type;
  }

  public void setLiteralAnalyzer(final Analyzer analyzer) {
    literalAnalyzer = analyzer;
  }

  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    final TupleTokenizer stream = new TupleTokenizer(reader, Integer.MAX_VALUE, literalAnalyzer);
    TokenStream result = new TokenTypeFilter(stream, new int[] {TupleTokenizer.BNODE,
                                                                TupleTokenizer.DOT,
                                                                TupleTokenizer.DATATYPE,
                                                                TupleTokenizer.LANGUAGE});
    result = new StandardFilter(Version.LUCENE_31, result);
    result = this.applyURINormalisation(result);
    result = new LowerCaseFilter(Version.LUCENE_31, result);
    result = new StopFilter(Version.LUCENE_31, result, stopSet);
    result = new LengthFilter(result, 2, 256);
    result = new SirenPayloadFilter(result);
    return result;
  }

  @Override
  public final TokenStream reusableTokenStream(final String fieldName, final Reader reader) throws IOException {
    SavedStreams streams = (SavedStreams) this.getPreviousTokenStream();
    if (streams == null) {
      streams = new SavedStreams();
      this.setPreviousTokenStream(streams);
      streams.tokenStream = new TupleTokenizer(reader, Integer.MAX_VALUE, literalAnalyzer);
      streams.filteredTokenStream = new TokenTypeFilter(streams.tokenStream,
        new int[] {TupleTokenizer.BNODE, TupleTokenizer.DOT,
                   TupleTokenizer.DATATYPE, TupleTokenizer.LANGUAGE});
      streams.filteredTokenStream = new StandardFilter(Version.LUCENE_31, streams.filteredTokenStream);
      streams.filteredTokenStream = this.applyURINormalisation(streams.filteredTokenStream);
      streams.filteredTokenStream = new LowerCaseFilter(Version.LUCENE_31, streams.filteredTokenStream);
      streams.filteredTokenStream = new StopFilter(Version.LUCENE_31, streams.filteredTokenStream, stopSet);
      streams.filteredTokenStream = new LengthFilter(streams.filteredTokenStream, 2, 256);
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

  /**
   * Given the type of URI normalisation, apply the right sequence of operations
   * and filters to the token stream.
   */
  private TokenStream applyURINormalisation(TokenStream in) {
    switch (normalisationType) {
      case NONE:
        return new URITrailingSlashFilter(in);

      // here, trailing slash filter is after localname filtering, in order to
      // avoid filtering subdirectory instead of localname
      case LOCALNAME:
        in = new URILocalnameFilter(in);
        return new URITrailingSlashFilter(in);

      // here, trailing slash filter is before localname filtering, in order to
      // avoid trailing slash checking on every tokens generated by the
      // URI normalisation filter
      case FULL:
        in = new URITrailingSlashFilter(in);
        return new URINormalisationFilter(in);

      default:
        throw new EnumConstantNotPresentException(URINormalisation.class,
          normalisationType.toString());
    }
  }

}
