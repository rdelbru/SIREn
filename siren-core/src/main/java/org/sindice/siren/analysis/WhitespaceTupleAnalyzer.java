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
 * @author Renaud Delbru [ 19 Mar 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter;
import org.sindice.siren.analysis.filter.SirenDeltaPayloadFilter;
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

public class WhitespaceTupleAnalyzer extends Analyzer {

  private final Version matchVersion;
  
  public WhitespaceTupleAnalyzer(Version matchVersion) {
    this.matchVersion = matchVersion;
  }

  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    final TupleTokenizer stream = new TupleTokenizer(reader, Integer.MAX_VALUE);
    TokenStream result = new TokenTypeFilter(stream, new int[] {TupleTokenizer.BNODE,
                                                              TupleTokenizer.DOT});
    result = new DatatypeAnalyzerFilter(matchVersion, result, new WhitespaceAnalyzer(matchVersion),
                                                  new WhitespaceAnyURIAnalyzer(matchVersion));
    result = new LowerCaseFilter(Version.LUCENE_31, result);
    result = new SirenDeltaPayloadFilter(result);
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
      streams.filteredTokenStream = new DatatypeAnalyzerFilter(matchVersion, streams.filteredTokenStream, new WhitespaceAnalyzer(Version.LUCENE_31),
                                                                                              new WhitespaceAnyURIAnalyzer(matchVersion));
      streams.filteredTokenStream = new LowerCaseFilter(Version.LUCENE_31, streams.filteredTokenStream);
      streams.filteredTokenStream = new SirenDeltaPayloadFilter(streams.filteredTokenStream);
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
