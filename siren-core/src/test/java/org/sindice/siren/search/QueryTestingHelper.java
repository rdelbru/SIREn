/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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
    return new IndexSearcher(this.getIndexReader());
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
