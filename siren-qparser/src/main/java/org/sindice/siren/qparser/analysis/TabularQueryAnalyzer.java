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
package org.sindice.siren.qparser.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

/**
 * Create a {@link NTripleQueryTokenizer} stream
 */
public class TabularQueryAnalyzer extends Analyzer {

  /**
   * Builds an analyzer.
   */
  public TabularQueryAnalyzer() {}

  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    final NTripleQueryTokenizer stream = new NTripleQueryTokenizer(reader);
    return stream;
  }

  @Override
  public final TokenStream reusableTokenStream(final String fieldName, final Reader reader) throws IOException {
    SavedStreams streams = (SavedStreams) this.getPreviousTokenStream();
    if (streams == null) {
      streams = new SavedStreams();
      this.setPreviousTokenStream(streams);
      streams.tokenStream = new NTripleQueryTokenizer(reader);
    } else {
      streams.tokenStream.reset(reader);
    }
    return streams.tokenStream;
  }

  private static final class SavedStreams {
    NTripleQueryTokenizer tokenStream;
  }

}
