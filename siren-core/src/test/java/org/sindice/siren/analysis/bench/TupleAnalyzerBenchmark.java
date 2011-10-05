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
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.analysis.WhitespaceAnyURIAnalyzer;
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

  public long timePlainTupleAnalyzer(final int reps) throws IOException {
    long counter = 0;
    for (int i = 0; i < reps; i++) {
      final Analyzer analyzer = new PlainTupleAnalyzer();
      counter += this.performAnalysis(analyzer);
    }
    return counter;
  }

  public long timePlainDeltaTupleAnalyzer(final int reps) throws IOException {
    long counter = 0;
    for (int i = 0; i < reps; i++) {
      final Analyzer analyzer = new PlainDeltaTupleAnalyzer();
      counter += this.performAnalysis(analyzer);
    }
    return counter;
  }

  public long timeNoDeltaWhitespaceTupleAnalyzer(final int reps) throws IOException {
    long counter = 0;
    for (int i = 0; i < reps; i++) {
      final Analyzer analyzer = new NoDeltaTupleAnalyzer(
        new WhitespaceAnalyzer(Version.LUCENE_31),
        new WhitespaceAnyURIAnalyzer());
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

  public long timeDeltaTupleAnalyzer(final int reps) throws IOException {
    long counter = 0;
    for (int i = 0; i < reps; i++) {
      final Analyzer analyzer = new TupleAnalyzer(new StandardAnalyzer(Version.LUCENE_31), new AnyURIAnalyzer());
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
