/**
 * @project siren [0.2-rc4]
 * @author Renaud Delbru [ 18 Mar 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

public class TupleAnalyzerBenchmark {

  final Analyzer analyzer;

  final File file1 = new File("./src/test/resources/demo/ntriples/foaf1.nt");
  final File file2 = new File("./src/test/resources/demo/ntriples/foaf2.nt");

  public TupleAnalyzerBenchmark(final Analyzer analyzer) throws IOException {
    this.analyzer = analyzer;
  }

  public void analyzeFile(final File input) throws FileNotFoundException, IOException {
    final TokenStream stream = analyzer.reusableTokenStream("", new FileReader(input));
    while (stream.incrementToken()) {
      // do nothing
    }
  }

  public void benchmark(final int iterations) throws FileNotFoundException, IOException {
    long startMillis = 0, testMillis = 0;

    int it = 0;
    while (it++ < 10) {
      startMillis = System.currentTimeMillis();
      for (int i = 0; i < iterations; i++) {
        this.analyzeFile(file1);
        this.analyzeFile(file2);
      }
      testMillis = System.currentTimeMillis() - startMillis;
      System.out.println("Execution Time: " + testMillis);
    }
  }

  /**
   * @param args
   * @throws IOException
   */
  public static void main(final String[] args) throws IOException {
    System.out.println("StandardAnalyzer Execution Time");
    TupleAnalyzerBenchmark bench = new TupleAnalyzerBenchmark(new StandardAnalyzer(Version.LUCENE_30));
    bench.benchmark(2000);
    System.out.println("WhitespaceTupleAnalyzer Execution Time");
    bench = new TupleAnalyzerBenchmark(new WhitespaceTupleAnalyzer());
    bench.benchmark(2000);
    System.out.println("TupleAnalyzer Execution Time");
    bench = new TupleAnalyzerBenchmark(new TupleAnalyzer(new StandardAnalyzer(Version.LUCENE_31)));
    bench.benchmark(2000);
  }

}
