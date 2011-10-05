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
 * @project siren-core
 * @author Renaud Delbru [ 27 Sep 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.search;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.RandomIndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.LuceneTestCase;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;

/**
 * Tests {@link SirenPrefixQuery} class.
 *
 * <p> Code taken from {@link TestPrefixQuery} and adapted for SIREn.
 */
public class TestSirenPrefixQuery extends LuceneTestCase {

  public void testPrefixQuery() throws Exception {
    final Directory directory = newDirectory();

    final String[] categories = new String[] {"/computers",
                                        "/computers/mac",
                                        "/computers/windows"};

    final RandomIndexWriter writer = new RandomIndexWriter(random, directory,
      new TupleAnalyzer(new WhitespaceAnalyzer(Version.LUCENE_31), new AnyURIAnalyzer()));

    for (final String category : categories) {
      final Document doc = new Document();
      doc.add(newField("category", this.getTriple(category), Field.Store.YES, Field.Index.ANALYZED));
      writer.addDocument(doc);
    }

    final IndexReader reader = writer.getReader();
    final IndexSearcher searcher = newSearcher(reader);

    SirenPrefixQuery query = new SirenPrefixQuery(new Term("category", "/computers"));
    ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("All documents in /computers category and below", 3, hits.length);

    query = new SirenPrefixQuery(new Term("category", "/computers/mac"));
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("One in /computers/mac", 1, hits.length);

    query = new SirenPrefixQuery(new Term("category", "/computers"));
    SirenCellQuery cq = new SirenCellQuery(query);
    cq.setConstraint(0);
    hits = searcher.search(cq, null, 1000).scoreDocs;
    assertEquals("No documents in /computers category and below in cell 0", 0, hits.length);

    query = new SirenPrefixQuery(new Term("category", "/computers"));
    cq = new SirenCellQuery(query);
    cq.setConstraint(2);
    hits = searcher.search(cq, null, 1000).scoreDocs;
    assertEquals("All documents in /computers category and below in cell 2", 3, hits.length);

    writer.close();
    searcher.close();
    reader.close();
    directory.close();
  }

  private String getTriple(final String text) {
    return "<http://fake.subject> <http://fake.predicate> \"" + text + "\" .\n";
  }

}