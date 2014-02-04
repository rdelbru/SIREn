/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sindice.siren.analysis;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.sindice.siren.analysis.filter.PositionAttributeFilter;
import org.sindice.siren.analysis.filter.SirenPayloadFilter;

public class MockSirenAnalyzer extends Analyzer {

  public MockSirenAnalyzer() {}

  @Override
  protected TokenStreamComponents createComponents(final String fieldName,
                                                   final Reader reader) {
    final MockSirenReader mockReader = (MockSirenReader) reader;
    final MockSirenTokenizer tokenizer = new MockSirenTokenizer(mockReader);

    TokenStream sink = new PositionAttributeFilter(tokenizer);
    sink = new SirenPayloadFilter(sink);
    return new TokenStreamComponents(tokenizer, sink);
  }

  public TokenStream tokenStream() throws IOException {
    return this.tokenStream("", new StringReader(""));
  }

}
