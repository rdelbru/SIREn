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
import org.apache.lucene.analysis.LengthFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.filter.AssignTokenTypeFilter;
import org.sindice.siren.analysis.filter.MailtoFilter;
import org.sindice.siren.analysis.filter.URIEncodingFilter;
import org.sindice.siren.analysis.filter.URILocalnameFilter;
import org.sindice.siren.analysis.filter.URINormalisationFilter;
import org.sindice.siren.analysis.filter.URITrailingSlashFilter;

/**
 * Analyzer designed to deal with any kind of URIs.
 */
public class AnyURIAnalyzer extends Analyzer {

  private final Set<?>            stopSet;

  private final Version matchVersion;
  
  /**
   * An array containing some common English words that are usually not useful
   * for searching.
   */
  public static final Set<?> STOP_WORDS = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
  
  public enum URINormalisation {NONE, LOCALNAME, FULL};

  private URINormalisation normalisationType = URINormalisation.NONE;

  public AnyURIAnalyzer(Version version) {
    this(version, STOP_WORDS);
  }
  
  public AnyURIAnalyzer(Version version, final Set<?> stopWords) {
    stopSet = stopWords;
    matchVersion = version;
  }
  
  public AnyURIAnalyzer(Version version, final String[] stopWords) {
    stopSet = StopFilter.makeStopSet(Version.LUCENE_31, stopWords);
    matchVersion = version;
  }
  
  public AnyURIAnalyzer(Version version, final File stopwords) throws IOException {
    stopSet = WordlistLoader.getWordSet(stopwords);
    matchVersion = version;
  }
  
  public AnyURIAnalyzer(Version version, final Reader stopWords) throws IOException {
    stopSet = WordlistLoader.getWordSet(stopWords);
    matchVersion = version;
  }

  public void setUriNormalisation(final URINormalisation n) {
    normalisationType = n;
  }
  
  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    TokenStream result = new WhitespaceTokenizer(matchVersion, reader);
    result = new URIEncodingFilter(result, "UTF-8");
    result = this.applyURINormalisation(result);
    result = new MailtoFilter(result);
    result = new LowerCaseFilter(matchVersion, result );
    result = new StopFilter(matchVersion, result, stopSet);
    result = new LengthFilter(true, result, 2, 256);
    result = new AssignTokenTypeFilter(result, TupleTokenizer.URI);
    return result;
  }

  @Override
  public final TokenStream reusableTokenStream(final String fieldName, final Reader reader) throws IOException {
    SavedStreams streams = (SavedStreams) this.getPreviousTokenStream();
    if (streams == null) {
      streams = new SavedStreams();
      this.setPreviousTokenStream(streams);
      streams.tokenStream = new WhitespaceTokenizer(matchVersion, reader);
      streams.filteredTokenStream = new URIEncodingFilter(streams.tokenStream, "UTF-8");
      streams.filteredTokenStream = this.applyURINormalisation(streams.filteredTokenStream);
      streams.filteredTokenStream = new MailtoFilter(streams.filteredTokenStream);
      streams.filteredTokenStream = new LowerCaseFilter(matchVersion, streams.filteredTokenStream);
      streams.filteredTokenStream = new StopFilter(matchVersion, streams.filteredTokenStream, stopSet);
      streams.filteredTokenStream = new LengthFilter(true, streams.filteredTokenStream, 2, 256);
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

  /**
   * Given the type of URI normalisation, apply the right sequence of operations
   * and filters to the token stream.
   */
  private TokenStream applyURINormalisation(TokenStream in) {
    switch (normalisationType) {
      case NONE:
        return new URITrailingSlashFilter(in, false); // don't check for token types

      // here, trailing slash filter is after localname filtering, in order to
      // avoid filtering subdirectory instead of localname
      case LOCALNAME:
        in = new URILocalnameFilter(in);
        return new URITrailingSlashFilter(in, false); // don't check for token types

      // here, trailing slash filter is before localname filtering, in order to
      // avoid trailing slash checking on every tokens generated by the
      // URI normalisation filter
      case FULL:
        in = new URITrailingSlashFilter(in, false); // don't check for token types
        return new URINormalisationFilter(in);

      default:
        throw new EnumConstantNotPresentException(URINormalisation.class,
          normalisationType.toString());
    }
  }

}
