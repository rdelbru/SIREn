/**
 * Copyright 2010, Renaud Delbru
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
 * @project siren [0.2-rc4]
 * @author Renaud Delbru [ 18 Mar 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.bench;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.DeltaTupleAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.analysis.WhitespaceTupleAnalyzer;
import org.sindice.siren.bench.SirenBenchmark;

import com.google.caliper.Param;
import com.google.caliper.Runner;

public class TupleAnalyzerBenchmark extends SirenBenchmark {

  @Param({"100", "1000"}) private int size;

  @Override
  protected void setUp() throws Exception {
    rand.setSeed(42);
  }

  public long timeStandardAnalyzer(final int reps) throws IOException {
    long counter = 0;
    for (int i = 0; i < reps; i++) {
      final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
      counter += this.performAnalysis(analyzer);
    }
    return counter;
  }

  public long timeWhitespaceTupleAnalyzer(final int reps) throws IOException {
    long counter = 0;
    for (int i = 0; i < reps; i++) {
      final Analyzer analyzer = new WhitespaceTupleAnalyzer();
      counter += this.performAnalysis(analyzer);
    }
    return counter;
  }

  public long timeTupleAnalyzer(final int reps) throws IOException {
    long counter = 0;
    for (int i = 0; i < reps; i++) {
      final Analyzer analyzer = new TupleAnalyzer(new StandardAnalyzer(Version.LUCENE_31));
      counter += this.performAnalysis(analyzer);
    }
    return counter;
  }

  public long timeDeltaTupleAnalyzer(final int reps) throws IOException {
    long counter = 0;
    for (int i = 0; i < reps; i++) {
      final Analyzer analyzer = new DeltaTupleAnalyzer(new StandardAnalyzer(Version.LUCENE_31));
      counter += this.performAnalysis(analyzer);
    }
    return counter;
  }

  public int performAnalysis(final Analyzer analyzer)
  throws FileNotFoundException, IOException {
    int counter = 0;

    for (int i = 0; i < size; i++) {
      final String content = this.readNTriplesFile(this.nextFile());
      final TokenStream stream = analyzer.reusableTokenStream("", new StringReader(content));
      try {
        while (stream.incrementToken()) {
          counter++;
        }
      }
      finally {
        stream.close();
      }
    }
    return counter;
  }

  public static void main(final String[] args) throws Exception {
    Runner.main(TupleAnalyzerBenchmark.class, args);
  }

}
