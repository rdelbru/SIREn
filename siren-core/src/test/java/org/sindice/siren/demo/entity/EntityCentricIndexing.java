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
 * @project siren
 * @author Renaud Delbru [ 9 Jul 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.demo.entity;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.search.SirenBooleanClause;
import org.sindice.siren.search.SirenBooleanQuery;
import org.sindice.siren.search.SirenCellQuery;
import org.sindice.siren.search.SirenPhraseQuery;
import org.sindice.siren.search.SirenTermQuery;
import org.sindice.siren.search.SirenTupleClause;
import org.sindice.siren.search.SirenTupleQuery;

/**
 * A demo that shows how to index and query entity description. The entity
 * description can contain 'path' describing related entities (e.g.
 * people that I know)
 */
public class EntityCentricIndexing {

  public RAMDirectory  dir;

  public IndexWriter   writer;

  public static final String DEFAULT_FIELD = "content";

  public EntityCentricIndexing()
  throws CorruptIndexException, LockObtainFailedException, IOException {
    dir = new RAMDirectory();
    final TupleAnalyzer analyzer = new TupleAnalyzer(Version.LUCENE_31, new StandardAnalyzer(Version.LUCENE_31), new AnyURIAnalyzer());
    final IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_31,
      analyzer);
    writer = new IndexWriter(dir, conf);
  }

  public void addDocument(final File input) throws IOException {
    final Document doc = new Document();

    doc.add(new Field("url", input.getAbsolutePath(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
    doc.add(new Field(DEFAULT_FIELD, FileUtils.readFileToString(input, "UTF-8"), Store.NO, Field.Index.ANALYZED_NO_NORMS));

    writer.addDocument(doc);
    writer.commit();
  }

  public ScoreDoc[] search(final Query q) throws IOException {
    final IndexSearcher searcher = new IndexSearcher(dir);
    return searcher.search(q, null, 10).scoreDocs;
  }

  public Document getDocument(final int docId)
  throws CorruptIndexException, IOException {
    final IndexReader reader = IndexReader.open(dir, true);
    try {
      return reader.document(docId);
    }
    finally {
      reader.close();
    }
  }

  public void close() throws CorruptIndexException, IOException {
    writer.close();
    dir.close();
  }

  /**
   * Create the first example query.
   * <p>
   * Simple tuple query that search entities with a specific predicate
   * and object (*, foaf:name, "renaud delbru")
   */
  public Query getQuery1() {
    // Create a cell query matching
    // 'http://xmlns.com/foaf/0.1/name "renaud delbru"'.
    final SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(DEFAULT_FIELD, "http://xmlns.com/foaf/0.1/name")), SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(0);

    final SirenBooleanQuery bq2 = new SirenBooleanQuery();
    final SirenPhraseQuery spq = new SirenPhraseQuery();
    spq.add(new Term(DEFAULT_FIELD, "renaud"));
    spq.add(new Term(DEFAULT_FIELD, "delbru"));
    bq2.add(spq, SirenBooleanClause.Occur.MUST);
    final SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(1);

    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(cq1, SirenTupleClause.Occur.MUST);
    tq.add(cq2, SirenTupleClause.Occur.MUST);

    return tq;
  }

  /**
   * Create the second example query.
   * <p>
   * Tuple query that lookup a RDF path:
   * (*, foaf:knows, *, foaf:name, "giovanni")
   */
  public Query getQuery2() {
    // Create a cell query matching "http://xmlns.com/foaf/0.1/knows"
    final SirenBooleanQuery bq1 = new SirenBooleanQuery();
    bq1.add(new SirenTermQuery(new Term(DEFAULT_FIELD, "http://xmlns.com/foaf/0.1/knows")), SirenBooleanClause.Occur.MUST);
    // Constraint the cell index to 0 (first column: predicate position)
    final SirenCellQuery cq1 = new SirenCellQuery(bq1);
    cq1.setConstraint(0);

    // Create a cell query matching "http://xmlns.com/foaf/0.1/name"
    final SirenBooleanQuery bq2 = new SirenBooleanQuery();
    bq2.add(new SirenTermQuery(new Term(DEFAULT_FIELD, "http://xmlns.com/foaf/0.1/name")), SirenBooleanClause.Occur.MUST);
    // Constraint the cell index to 2 (third column: predicate position)
    final SirenCellQuery cq2 = new SirenCellQuery(bq2);
    cq2.setConstraint(2);

    // Create a cell query matching "giovanni"
    final SirenBooleanQuery bq3 = new SirenBooleanQuery();
    bq3.add(new SirenTermQuery(new Term(DEFAULT_FIELD, "giovanni")), SirenBooleanClause.Occur.MUST);
    // Constraint the cell index to 3 (fourth column: object position)
    final SirenCellQuery cq3 = new SirenCellQuery(bq3);
    cq3.setConstraint(3);

    // Create a tuple query that combines the three cell queries
    final SirenTupleQuery tq = new SirenTupleQuery();
    tq.add(cq1, SirenTupleClause.Occur.MUST);
    tq.add(cq2, SirenTupleClause.Occur.MUST);
    tq.add(cq3, SirenTupleClause.Occur.MUST);

    return tq;
  }

  /**
   * Create the third example query. search for an entity named "renaud delbru"
   * and that knows somebody called "giovanni"
   * <p>
   * Complex tuple queries that combine query 1 and query 2:
   * (*, foaf:name, "renaud delbru")
   * AND
   * (*, foaf:knows, *, foaf:name, "giovanni")
   */
  public Query getQuery3() {
    // Combine two tuple queries with a Lucene boolean query
    final BooleanQuery q = new BooleanQuery();
    // Get the tuple query (*, foaf:name, "renaud delbru")
    q.add(this.getQuery1(), Occur.MUST);
    // Get the tuple query (*, foaf:knows, *, foaf:name, "giovanni")
    q.add(this.getQuery2(), Occur.MUST);

    return q;
  }

  public static void main(final String[] args)
  throws CorruptIndexException, LockObtainFailedException, IOException {
    final String filename1 = "./src/test/resources/demo/entity/renaud.nt";
    final EntityCentricIndexing indexer = new EntityCentricIndexing();
    indexer.addDocument(new File(filename1));

    ScoreDoc[] results = indexer.search(indexer.getQuery1());
    System.out.println("Number of hits: " + results.length);
    for (final ScoreDoc doc : results) {
      System.out.println(doc.doc + ": " + indexer.getDocument(doc.doc));
      System.out.println(doc.score);
    }

    results = indexer.search(indexer.getQuery2());
    System.out.println("Number of hits: " + results.length);
    for (final ScoreDoc doc : results) {
      System.out.println(doc.doc + ": " + indexer.getDocument(doc.doc));
      System.out.println(doc.score);
    }

    results = indexer.search(indexer.getQuery3());
    System.out.println("Number of hits: " + results.length);
    for (final ScoreDoc doc : results) {
      System.out.println(doc.doc + ": " + indexer.getDocument(doc.doc));
      System.out.println(doc.score);
    }

    indexer.close();
  }

}
