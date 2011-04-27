package org.sindice.siren.qparser.analysis;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.sindice.siren.analysis.filter.URITrailingSlashFilter;

/**
 * Filters {@link NTripleQueryTokenizer} with {@link LowerCaseFilter}
 * and {@link URITrailingSlashFilter}.
 */
public class NTripleQueryAnalyzer extends Analyzer {

  /**
   * Builds an analyzer.
   */
  public NTripleQueryAnalyzer() {}

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
