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

package org.sindice.siren.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexReader.ReaderClosedListener;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.RandomIndexWriter;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.search.AssertingIndexSearcher;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.LuceneTestCase;
import org.apache.lucene.util.NamedThreadFactory;
import org.apache.lucene.util._TestUtil;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.AnyURIAnalyzer.URINormalisation;
import org.sindice.siren.analysis.JsonAnalyzer;
import org.sindice.siren.analysis.MockSirenAnalyzer;
import org.sindice.siren.analysis.MockSirenDocument;
import org.sindice.siren.analysis.MockSirenReader;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SirenTestCase extends LuceneTestCase {

  protected static final Logger logger = LoggerFactory.getLogger(SirenTestCase.class);

  public static final String DEFAULT_TEST_FIELD = "content";

  public static Analyzer newTupleAnalyzer() {
    final AnyURIAnalyzer uriAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    uriAnalyzer.setUriNormalisation(URINormalisation.FULL);
    final TupleAnalyzer analyzer = new TupleAnalyzer(TEST_VERSION_CURRENT,
      new StandardAnalyzer(TEST_VERSION_CURRENT), uriAnalyzer);
    return analyzer;
  }

  public static Analyzer newJsonAnalyzer() {
    final AnyURIAnalyzer fieldAnalyzer = new AnyURIAnalyzer(TEST_VERSION_CURRENT);
    fieldAnalyzer.setUriNormalisation(URINormalisation.FULL);
    final Analyzer literalAnalyzer = new StandardAnalyzer(TEST_VERSION_CURRENT);
    final JsonAnalyzer analyzer = new JsonAnalyzer(TEST_VERSION_CURRENT,
      fieldAnalyzer, literalAnalyzer);
    return analyzer;
  }

  public static Analyzer newMockAnalyzer() {
    return new MockSirenAnalyzer();
  }

  private static FieldType newFieldType() {
    final FieldType ft = new FieldType();
    ft.setStored(false);
    ft.setOmitNorms(false);
    ft.setIndexed(true);
    ft.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
    ft.setTokenized(true);
    return ft;
  }

  protected static FieldType newStoredFieldType() {
    final FieldType ft = newFieldType();
    ft.setStored(true);
    return ft;
  }

  private FieldType newStoredNoNormFieldType() {
    final FieldType ft = newStoredFieldType();
    ft.setOmitNorms(true);
    return ft;
  }

  protected static RandomIndexWriter newRandomIndexWriter(final Directory dir,
                                                          final Analyzer analyzer,
                                                          final Codec codec)
  throws IOException {
    return newRandomIndexWriter(dir, analyzer, codec,
      newIndexWriterConfig(TEST_VERSION_CURRENT, analyzer)
    .setCodec(codec).setMergePolicy(newLogMergePolicy())
    .setSimilarity(new DefaultSimilarity()));
  }

  protected static RandomIndexWriter newRandomIndexWriter(final Directory dir,
                                                          final Analyzer analyzer,
                                                          final Codec codec,
                                                          final IndexWriterConfig config)
  throws IOException {
    final RandomIndexWriter writer = new RandomIndexWriter(random(), dir, config);
    writer.setDoRandomForceMergeAssert(true);
    return writer;
  }

  protected static IndexReader newIndexReader(final RandomIndexWriter writer)
  throws IOException {
    // We are wrapping by default the reader into a slow reader, as most of the
    // tests require an atomic reader
    return SlowCompositeReaderWrapper.wrap(writer.getReader());
  }

  /**
   * Create a new searcher over the reader. This searcher might randomly use
   * threads.
   * <p>
   * Override the original {@link LuceneTestCase#newSearcher(IndexReader)}
   * implementation in order to avoid getting {@link AssertingIndexSearcher}
   * which is incompatible with SIREn.
   */
  public static IndexSearcher newSearcher(final IndexReader r) throws IOException {
    final Random random = random();
    if (usually()) {
      // compared to the original implementation, we do not wrap to avoid
      // wrapping into an AssertingAtomicReader
      return random.nextBoolean() ? new IndexSearcher(r) : new IndexSearcher(r.getContext());
    } else {
      int threads = 0;
      final ThreadPoolExecutor ex;
      if (random.nextBoolean()) {
        ex = null;
      } else {
        threads = _TestUtil.nextInt(random, 1, 8);
        ex = new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new NamedThreadFactory("LuceneTestCase"));
      }
      if (ex != null) {
       if (VERBOSE) {
        System.out.println("NOTE: newSearcher using ExecutorService with " + threads + " threads");
       }
       r.addReaderClosedListener(new ReaderClosedListener() {
         @Override
         public void onClose(final IndexReader reader) {
           _TestUtil.shutdownExecutorService(ex);
         }
       });
      }
      final IndexSearcher ret = random.nextBoolean()
          ? new IndexSearcher(r, ex)
          : new IndexSearcher(r.getContext(), ex);
      return ret;
    }
  }

  protected static void addDocument(final RandomIndexWriter writer, final String data)
  throws IOException {
    final Document doc = new Document();
    doc.add(new Field(DEFAULT_TEST_FIELD, data, newStoredFieldType()));
    writer.addDocument(doc);
    writer.commit();
  }

  protected void addDocumentNoNorms(final RandomIndexWriter writer, final String data)
  throws IOException {
    final Document doc = new Document();
    doc.add(new Field(DEFAULT_TEST_FIELD, data, this.newStoredNoNormFieldType()));
    writer.addDocument(doc);
    writer.commit();
  }

  /**
   * Atomically adds a block of documents with sequentially
   * assigned document IDs.
   * <br>
   * See also {@link IndexWriter#addDocuments(Iterable)}
   */
  protected static void addDocuments(final RandomIndexWriter writer,
                                     final String[] data)
  throws IOException {
    final ArrayList<Document> docs = new ArrayList<Document>();

    for (final String entry : data) {
      final Document doc = new Document();
      doc.add(new Field(DEFAULT_TEST_FIELD, entry, newStoredFieldType()));
      docs.add(doc);
    }
    writer.addDocuments(docs);
    writer.commit();
  }

  protected static void addDocuments(final RandomIndexWriter writer,
                                     final MockSirenDocument ... sdocs)
  throws IOException {
    final ArrayList<Document> docs = new ArrayList<Document>(sdocs.length);
    for (final MockSirenDocument sdoc : sdocs) {
      final Document doc = new Document();
      doc.add(new Field(DEFAULT_TEST_FIELD, new MockSirenReader(sdoc), newFieldType()));
      docs.add(doc);
    }
    writer.addDocuments(docs);
    writer.commit();
  }

  protected void deleteAll(final RandomIndexWriter writer) throws IOException {
    writer.deleteAll();
    writer.commit();
  }

  protected void forceMerge(final RandomIndexWriter writer) throws IOException {
    writer.forceMerge(1);
  }

}
