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
 * @author Robert Fuller [ 20 Jul 2009 ]
 * @author Renaud Delbru [ 20 Jul 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009, All rights reserved.
 */
package org.sindice.siren.demo.imdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

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
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.DeltaTupleAnalyzer;
import org.sindice.siren.analysis.DeltaTupleAnalyzer.URINormalisation;
import org.sindice.siren.search.SirenBooleanClause;
import org.sindice.siren.search.SirenBooleanQuery;
import org.sindice.siren.search.SirenCellQuery;
import org.sindice.siren.search.SirenTermQuery;
import org.sindice.siren.search.SirenTupleClause;
import org.sindice.siren.search.SirenTupleQuery;

public class IMDBIndexing {

  private static final int   COLUMN_TUPLE_TYPE  = 0;

  private static final int   COLUMN_PERSON_NAME = 1;

  private static final int   COLUMN_MOVIE_TITLE = 1;

  private static final int   COLUMN_BIRTH_DATE  = 2;

  private static final int   COLUMN_BIRTH_PLACE = 3;

  private static final int   COLUMN_ANY         = -1;

  public RAMDirectory        dir;

  public IndexWriter         writer;

  public static final String DEFAULT_FIELD      = "content";

  public IMDBIndexing() throws CorruptIndexException, LockObtainFailedException, IOException {
    dir = new RAMDirectory();
    final DeltaTupleAnalyzer analyzer = new DeltaTupleAnalyzer(new StandardAnalyzer(Version.LUCENE_31));
    analyzer.setURINormalisation(URINormalisation.NONE);
    final IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_31,
      analyzer);
    writer = new IndexWriter(dir, conf);
  }

  public void addDocument(final File input)
  throws IOException {
    final Document doc = new Document();
    final BufferedReader br = new BufferedReader(new InputStreamReader(
      new FileInputStream(input), "UTF-8"));
    final StringBuilder sbTupleContent = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      if (line.matches("^\\w+:.*")) {
        // title and url fields.
        final String[] parts = line.split(":", 2);
        doc.add(new Field(parts[0], parts[1], Store.YES,
          Index.NOT_ANALYZED_NO_NORMS));
        if ("title".equals(parts[0])) {
          System.out.println("adding: " + parts[1] + " from " + input.getName());
        }
      }
      else {
        // tuple fields
        if (sbTupleContent.length() > 0) {
          sbTupleContent.append("\n");
        }
        sbTupleContent.append(line);
      }
    }
    br.close();
    if (sbTupleContent.length() > 0) {
      doc.add(new Field(DEFAULT_FIELD, sbTupleContent.toString(), Store.NO,
        Field.Index.ANALYZED_NO_NORMS));
    }
    writer.addDocument(doc);
    writer.commit();
  }

  public ScoreDoc[] search(final Query q)
  throws IOException {
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

  public void close()
  throws CorruptIndexException, IOException {
    writer.close();
    dir.close();
  }

  /**
   * create a query to find films in which stallone and fuller acted.
   */
  public Query getFullerStalloneQuery() {

    // Create a tuple query that combines the two cell queries
    final SirenTupleQuery tq1 = new SirenTupleQuery();
    tq1.add(this.termInCell("actor", COLUMN_TUPLE_TYPE),
      SirenTupleClause.Occur.MUST);
    tq1.add(this.termInCell("fuller", COLUMN_PERSON_NAME),
      SirenTupleClause.Occur.MUST);

    final SirenTupleQuery tq2 = new SirenTupleQuery();
    tq2.add(this.termInCell("actor", COLUMN_TUPLE_TYPE),
      SirenTupleClause.Occur.MUST);
    tq2.add(this.termInCell("stallone", COLUMN_PERSON_NAME),
      SirenTupleClause.Occur.MUST);

    // Combine two tuple queries with a Lucene boolean query
    final BooleanQuery q = new BooleanQuery();
    q.add(tq1, Occur.MUST);
    q.add(tq2, Occur.MUST);

    return q;
  }

  /**
   * Create the fourth example query.
   * <p>
   * Complex tuple queries that matches: (An actor named Margot born in canada)
   * AND (an actor named Brad born in Florida) AND (a director born during
   * September)
   */
  public Query getStaloneFilmMargotFromCanadaBradFromFloridaDirectorBornSeptemberQuery() {

    // Actor Margot born in Canada:
    final SirenTupleQuery tq1 = new SirenTupleQuery();
    tq1.add(this.termInCell("actor", COLUMN_TUPLE_TYPE),
      SirenTupleClause.Occur.MUST);
    tq1.add(this.termInCell("margot", COLUMN_PERSON_NAME),
      SirenTupleClause.Occur.MUST);
    tq1.add(this.termInCell("canada", COLUMN_BIRTH_PLACE),
      SirenTupleClause.Occur.MUST);

    // Actor Brad born in Florida:
    final SirenTupleQuery tq2 = new SirenTupleQuery();
    tq2.add(this.termInCell("actor", COLUMN_TUPLE_TYPE),
      SirenTupleClause.Occur.MUST);
    tq2.add(this.termInCell("brad", COLUMN_PERSON_NAME),
      SirenTupleClause.Occur.MUST);
    tq2.add(this.termInCell("florida", COLUMN_BIRTH_PLACE),
      SirenTupleClause.Occur.MUST);

    // Director born in September:
    final SirenTupleQuery tq3 = new SirenTupleQuery();
    tq3.add(this.termInCell("director", COLUMN_TUPLE_TYPE),
      SirenTupleClause.Occur.MUST);
    tq3.add(this.termInCell("september", COLUMN_BIRTH_DATE),
      SirenTupleClause.Occur.MUST);

    // A Stallone film:
    final SirenTupleQuery tq4 = new SirenTupleQuery();
    tq4.add(this.termInCell("stallone", COLUMN_ANY),
      SirenTupleClause.Occur.MUST);

    // Combine the four tuple queries with a Lucene boolean query
    final BooleanQuery q = new BooleanQuery();
    q.add(tq1, Occur.MUST);
    q.add(tq2, Occur.MUST);
    q.add(tq3, Occur.MUST);
    q.add(tq4, Occur.MUST);

    return q;
  }

  private SirenCellQuery termInCell(final String term, final int cell) {
    final SirenBooleanQuery bq = new SirenBooleanQuery();
    bq.add(new SirenTermQuery(new Term(DEFAULT_FIELD, term)),
      SirenBooleanClause.Occur.MUST);
    final SirenCellQuery scq = new SirenCellQuery(bq);
    if (cell != COLUMN_ANY) {
      scq.setConstraint(cell);
    }
    return scq;
  }

  public static void main(final String[] args)
  throws CorruptIndexException, LockObtainFailedException, IOException {
    final File folder = new File("./src/test/resources/demo/imdb/");

    final File[] files = folder.listFiles(new FilenameFilter() {
      public boolean accept(final File dir, final String name) {
        return name.endsWith(".txt");
      }
    });

    final IMDBIndexing indexer = new IMDBIndexing();

    for (final File file : files) {
      indexer.addDocument(file);
    }

    System.out.println("added " + files.length + " movies");

    ScoreDoc[] results = indexer.search(indexer.getFullerStalloneQuery());
    System.out.println("Simple Query Number of hits: " + results.length);

    for (final ScoreDoc doc : results) {
      System.out.println(doc.doc + ": " + indexer.getDocument(doc.doc));
      System.out.println(doc.score);
    }

    results = indexer.search(indexer.getStaloneFilmMargotFromCanadaBradFromFloridaDirectorBornSeptemberQuery());
    System.out.println("Complex query Number of hits: " + results.length);

    for (final ScoreDoc doc : results) {
      System.out.println(doc.doc + ": " + indexer.getDocument(doc.doc));
      System.out.println(doc.score);
    }

    indexer.close();
  }
}


