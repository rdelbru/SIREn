/**
 * Copyright 2009, Renaud Delbru
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
 * @project siren
 * @author Renaud Delbru [ 10 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.util.Collection;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.RAMDirectory;
import org.sindice.siren.similarity.SirenSimilarity;

public class QueryTestingHelper {

  public RAMDirectory  _dir;

  public IndexWriter   _writer;

  public Analyzer      _analyzer;

  public static final String DEFAULT_FIELD = "content";

  public QueryTestingHelper(final Analyzer analyzer) throws CorruptIndexException, IOException {
    _analyzer = analyzer;
    this.initiate();
  }

  public void initiate()
  throws CorruptIndexException, IOException {
    _dir = new RAMDirectory();
    _writer = new IndexWriter(_dir, _analyzer, MaxFieldLength.UNLIMITED);
  }

  public void reset()
  throws CorruptIndexException, IOException {
    this.close();
    this.initiate();
  }

  public IndexWriter getIndexWriter() {
    return _writer;
  }

  public IndexReader getIndexReader()
  throws CorruptIndexException, IOException {
    return _writer.getReader();
  }

  /**
   * Return a fresh searcher. This is necessary because the searcher cannot
   * found document added after its initialisation.
   */
  public IndexSearcher getSearcher()
  throws IOException {
    // Instantiate a new searcher
//    return new IndexSearcher(this.getIndexReader());
    final IndexSearcher searcher = new IndexSearcher(this.getIndexReader());
    searcher.setSimilarity(new SirenSimilarity());
    return searcher;
  }

  public void close()
  throws CorruptIndexException, IOException {
    _writer.close();
    _dir.close();
  }

  public void addDocument(final String data)
  throws CorruptIndexException, IOException {
    final Document doc = new Document();
    doc.add(new Field(DEFAULT_FIELD, data, Store.NO, Field.Index.ANALYZED));
    _writer.addDocument(doc);
    _writer.commit();
  }

  public void addDocumentNoNorms(final String data)
  throws CorruptIndexException, IOException {
    final Document doc = new Document();
    doc.add(new Field(DEFAULT_FIELD, data, Store.NO, Field.Index.ANALYZED_NO_NORMS));
    _writer.addDocument(doc);
    _writer.commit();
  }

  public void addDocuments(final Collection<String> data)
  throws CorruptIndexException, IOException {
    for (final String doc : data)
      this.addDocument(doc);
  }

  public void addDocuments(final String[] data)
  throws CorruptIndexException, IOException {
    for (final String doc : data)
      this.addDocument(doc);
  }

  public ScoreDoc[] search(final Query q) throws IOException {
    return this.getSearcher().search(q, null, 1000).scoreDocs;
  }

}
