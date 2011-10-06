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

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.NumericTokenStream;
import org.apache.lucene.analysis.TokenStream;

/**
 * Analyzer that enables indexing of numeric values for efficient range
 * querying. It is a simple wrapper around {@link NumericTokenStream}.
 *
 * <p>See {@link NumericTokenStream} for more details.
 */
public abstract class NumericAnalyzer extends Analyzer {

  private final int precisionStep;

  public NumericAnalyzer(final int precisionStep) {
    this.precisionStep = precisionStep;
  }

  @Override
  public final TokenStream tokenStream(final String fieldName, final Reader reader) {
    try {
      final NumericTokenStream result = new NumericTokenStream(precisionStep);
      this.setNumericValue(result, reader);
      return result;
    }
    catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public final TokenStream reusableTokenStream(final String fieldName, final Reader reader)
  throws IOException {
    SavedStreams streams = (SavedStreams) this.getPreviousTokenStream();
    if (streams == null) {
      streams = new SavedStreams();
      this.setPreviousTokenStream(streams);
      streams.tokenStream = new NumericTokenStream(precisionStep);
      this.setNumericValue(streams.tokenStream, reader);
    } else {
      streams.tokenStream.reset();
      this.setNumericValue(streams.tokenStream, reader);
    }
    return streams.tokenStream;
  }

  /**
   * Set the numeric value of the token stream
   */
  protected abstract void setNumericValue(final NumericTokenStream tokenStream, final Reader reader)
  throws IOException;

  private static final class SavedStreams {
    NumericTokenStream tokenStream;
  }

}
