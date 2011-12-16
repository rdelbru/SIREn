/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
package org.sindice.siren.search;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.LuceneTestCase;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.search.SirenMultiTermQuery.TopTermsBoostOnlySirenBooleanQueryRewrite;

/**
 * Tests copied from {@link FuzzyQuery} for the siren use case.
 */
public class TestSirenFuzzyQuery extends LuceneTestCase {

  @Test
  public void testFuzziness() throws Exception {
    final Directory directory = new RAMDirectory();
    final IndexWriterConfig conf = new IndexWriterConfig(TEST_VERSION_CURRENT,
      new TupleAnalyzer(TEST_VERSION_CURRENT, new StandardAnalyzer(TEST_VERSION_CURRENT),
        new AnyURIAnalyzer(TEST_VERSION_CURRENT)));
    final IndexWriter writer = new IndexWriter(directory, conf);

    this.addDoc("aaaaa", writer);
    this.addDoc("aaaab", writer);
    this.addDoc("aaabb", writer);
    this.addDoc("aabbb", writer);
    this.addDoc("abbbb", writer);
    this.addDoc("bbbbb", writer);
    this.addDoc("ddddd", writer);

    final IndexReader reader = IndexReader.open(directory);
    final IndexSearcher searcher = new IndexSearcher(directory);
    writer.close();

    SirenFuzzyQuery query = new SirenFuzzyQuery(new Term("field", "aaaaa"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);

    // same with prefix
    query = new SirenFuzzyQuery(new Term("field", "aaaaa"), SirenFuzzyQuery.defaultMinSimilarity, 1);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "aaaaa"), SirenFuzzyQuery.defaultMinSimilarity, 2);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "aaaaa"), SirenFuzzyQuery.defaultMinSimilarity, 3);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "aaaaa"), SirenFuzzyQuery.defaultMinSimilarity, 4);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(2, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "aaaaa"), SirenFuzzyQuery.defaultMinSimilarity, 5);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "aaaaa"), SirenFuzzyQuery.defaultMinSimilarity, 6);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);

    // test scoring
    query = new SirenFuzzyQuery(new Term("field", "bbbbb"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("3 documents should match", 3, hits.length);
    List<String> order = Arrays.asList("bbbbb","abbbb","aabbb");
    for (int i = 0; i < hits.length; i++) {
      final String term = searcher.doc(hits[i].doc).get("field");
      //System.out.println(hits[i].score);
      assertEquals(this.getTriple(order.get(i)), term);
    }

    // test pq size by supplying maxExpansions=2
    // This query would normally return 3 documents, because 3 terms match (see above):
    query = new SirenFuzzyQuery(new Term("field", "bbbbb"), SirenFuzzyQuery.defaultMinSimilarity, 0, 2);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals("only 2 documents should match", 2, hits.length);
    order = Arrays.asList("bbbbb","abbbb");
    for (int i = 0; i < hits.length; i++) {
      final String term = searcher.doc(hits[i].doc).get("field");
      //System.out.println(hits[i].score);
      assertEquals(this.getTriple(order.get(i)), term);
    }

    // not similar enough:
    query = new SirenFuzzyQuery(new Term("field", "xxxxx"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "aaccc"), SirenFuzzyQuery.defaultMinSimilarity, 0);   // edit distance to "aaaaa" = 3
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    // query identical to a word in the index:
    query = new SirenFuzzyQuery(new Term("field", "aaaaa"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);
    assertEquals(this.getTriple("aaaaa"), searcher.doc(hits[0].doc).get("field"));
    // default allows for up to two edits:
    assertEquals(this.getTriple("aaaab"), searcher.doc(hits[1].doc).get("field"));
    assertEquals(this.getTriple("aaabb"), searcher.doc(hits[2].doc).get("field"));

    // query similar to a word in the index:
    query = new SirenFuzzyQuery(new Term("field", "aaaac"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);
    assertEquals(this.getTriple("aaaaa"), searcher.doc(hits[0].doc).get("field"));
    assertEquals(this.getTriple("aaaab"), searcher.doc(hits[1].doc).get("field"));
    assertEquals(this.getTriple("aaabb"), searcher.doc(hits[2].doc).get("field"));

    // now with prefix
    query = new SirenFuzzyQuery(new Term("field", "aaaac"), SirenFuzzyQuery.defaultMinSimilarity, 1);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);
    assertEquals(this.getTriple("aaaaa"), searcher.doc(hits[0].doc).get("field"));
    assertEquals(this.getTriple("aaaab"), searcher.doc(hits[1].doc).get("field"));
    assertEquals(this.getTriple("aaabb"), searcher.doc(hits[2].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "aaaac"), SirenFuzzyQuery.defaultMinSimilarity, 2);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);
    assertEquals(this.getTriple("aaaaa"), searcher.doc(hits[0].doc).get("field"));
    assertEquals(this.getTriple("aaaab"), searcher.doc(hits[1].doc).get("field"));
    assertEquals(this.getTriple("aaabb"), searcher.doc(hits[2].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "aaaac"), SirenFuzzyQuery.defaultMinSimilarity, 3);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);
    assertEquals(this.getTriple("aaaaa"), searcher.doc(hits[0].doc).get("field"));
    assertEquals(this.getTriple("aaaab"), searcher.doc(hits[1].doc).get("field"));
    assertEquals(this.getTriple("aaabb"), searcher.doc(hits[2].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "aaaac"), SirenFuzzyQuery.defaultMinSimilarity, 4);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(2, hits.length);
    assertEquals(this.getTriple("aaaaa"), searcher.doc(hits[0].doc).get("field"));
    assertEquals(this.getTriple("aaaab"), searcher.doc(hits[1].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "aaaac"), SirenFuzzyQuery.defaultMinSimilarity, 5);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);


    query = new SirenFuzzyQuery(new Term("field", "ddddX"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    assertEquals(this.getTriple("ddddd"), searcher.doc(hits[0].doc).get("field"));

    // now with prefix
    query = new SirenFuzzyQuery(new Term("field", "ddddX"), SirenFuzzyQuery.defaultMinSimilarity, 1);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    assertEquals(this.getTriple("ddddd"), searcher.doc(hits[0].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "ddddX"), SirenFuzzyQuery.defaultMinSimilarity, 2);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    assertEquals(this.getTriple("ddddd"), searcher.doc(hits[0].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "ddddX"), SirenFuzzyQuery.defaultMinSimilarity, 3);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    assertEquals(this.getTriple("ddddd"), searcher.doc(hits[0].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "ddddX"), SirenFuzzyQuery.defaultMinSimilarity, 4);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    assertEquals(this.getTriple("ddddd"), searcher.doc(hits[0].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "ddddX"), SirenFuzzyQuery.defaultMinSimilarity, 5);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);


    // different field = no match:
    query = new SirenFuzzyQuery(new Term("anotherfield", "ddddX"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    searcher.close();
    reader.close();
    directory.close();
  }

  @Test
  public void testFuzzinessLong() throws Exception {
    final Directory directory = new RAMDirectory();
    final IndexWriterConfig conf = new IndexWriterConfig(TEST_VERSION_CURRENT,
      new TupleAnalyzer(TEST_VERSION_CURRENT,
        new StandardAnalyzer(TEST_VERSION_CURRENT),
        new AnyURIAnalyzer(TEST_VERSION_CURRENT)));
    final IndexWriter writer = new IndexWriter(directory, conf);
    this.addDoc("aaaaaaa", writer);
    this.addDoc("segment", writer);

    final IndexReader reader = IndexReader.open(directory);
    final IndexSearcher searcher = new IndexSearcher(directory);
    writer.close();

    SirenFuzzyQuery query;
    // not similar enough:
    query = new SirenFuzzyQuery(new Term("field", "xxxxx"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);
    // edit distance to "aaaaaaa" = 3, this matches because the string is longer than
    // in testDefaultFuzziness so a bigger difference is allowed:
    query = new SirenFuzzyQuery(new Term("field", "aaaaccc"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    assertEquals(this.getTriple("aaaaaaa"), searcher.doc(hits[0].doc).get("field"));

    // now with prefix
    query = new SirenFuzzyQuery(new Term("field", "aaaaccc"), SirenFuzzyQuery.defaultMinSimilarity, 1);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    assertEquals(this.getTriple("aaaaaaa"), searcher.doc(hits[0].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "aaaaccc"), SirenFuzzyQuery.defaultMinSimilarity, 4);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    assertEquals(this.getTriple("aaaaaaa"), searcher.doc(hits[0].doc).get("field"));
    query = new SirenFuzzyQuery(new Term("field", "aaaaccc"), SirenFuzzyQuery.defaultMinSimilarity, 5);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    // no match, more than half of the characters is wrong:
    query = new SirenFuzzyQuery(new Term("field", "aaacccc"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    // now with prefix
    query = new SirenFuzzyQuery(new Term("field", "aaacccc"), SirenFuzzyQuery.defaultMinSimilarity, 2);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    // "student" and "stellent" are indeed similar to "segment" by default:
    query = new SirenFuzzyQuery(new Term("field", "student"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "stellent"), SirenFuzzyQuery.defaultMinSimilarity, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);

    // now with prefix
    query = new SirenFuzzyQuery(new Term("field", "student"), SirenFuzzyQuery.defaultMinSimilarity, 1);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "stellent"), SirenFuzzyQuery.defaultMinSimilarity, 1);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "student"), SirenFuzzyQuery.defaultMinSimilarity, 2);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);
    query = new SirenFuzzyQuery(new Term("field", "stellent"), SirenFuzzyQuery.defaultMinSimilarity, 2);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    // "student" doesn't match anymore thanks to increased minimum similarity:
    query = new SirenFuzzyQuery(new Term("field", "student"), 0.6f, 0);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    try {
      query = new SirenFuzzyQuery(new Term("field", "student"), 1.1f);
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // expecting exception
    }
    try {
      query = new SirenFuzzyQuery(new Term("field", "student"), -0.1f);
      fail("Expected IllegalArgumentException");
    } catch (final IllegalArgumentException e) {
      // expecting exception
    }

    searcher.close();
    reader.close();
    directory.close();
  }

  @Test
  public void testTokenLengthOpt() throws IOException {
    final Directory directory = new RAMDirectory();
    final IndexWriterConfig conf = new IndexWriterConfig(TEST_VERSION_CURRENT,
      new TupleAnalyzer(TEST_VERSION_CURRENT, new StandardAnalyzer(TEST_VERSION_CURRENT), new AnyURIAnalyzer(TEST_VERSION_CURRENT)));
    final IndexWriter writer = new IndexWriter(directory, conf);
    this.addDoc("12345678911", writer);
    this.addDoc("segment", writer);

    final IndexReader reader = IndexReader.open(directory);
    final IndexSearcher searcher = new IndexSearcher(directory);
    writer.close();

    Query query;
    // term not over 10 chars, so optimization shortcuts
    query = new SirenFuzzyQuery(new Term("field", "1234569"), 0.9f);
    ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    // 10 chars, so no optimization
    query = new SirenFuzzyQuery(new Term("field", "1234567891"), 0.9f);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    // over 10 chars, so no optimization
    query = new SirenFuzzyQuery(new Term("field", "12345678911"), 0.9f);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);

    // over 10 chars, no match
    query = new SirenFuzzyQuery(new Term("field", "sdfsdfsdfsdf"), 0.9f);
    hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(0, hits.length);

    searcher.close();
    reader.close();
    directory.close();
  }

  /** Test the {@link TopTermsBoostOnlySirenBooleanQueryRewrite} rewrite method. */
  @Test
  public void testBoostOnlyRewrite() throws Exception {
    final Directory directory = new RAMDirectory();
    final IndexWriterConfig conf = new IndexWriterConfig(TEST_VERSION_CURRENT,
      new TupleAnalyzer(TEST_VERSION_CURRENT, new StandardAnalyzer(TEST_VERSION_CURRENT), new AnyURIAnalyzer(TEST_VERSION_CURRENT)));
    final IndexWriter writer = new IndexWriter(directory, conf);
    this.addDoc("Lucene", writer);
    this.addDoc("Lucene", writer);
    this.addDoc("Lucenne", writer);

    final IndexReader reader = IndexReader.open(directory);
    final IndexSearcher searcher = new IndexSearcher(directory);
    writer.close();

    final SirenFuzzyQuery query = new SirenFuzzyQuery(new Term("field", "Lucene"));
    query.setRewriteMethod(new SirenMultiTermQuery.TopTermsBoostOnlySirenBooleanQueryRewrite(50));
    final ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
    assertEquals(3, hits.length);
    // normally, 'Lucenne' would be the first result as IDF will skew the score.
    assertEquals(this.getTriple("Lucene"), reader.document(hits[0].doc).get("field"));
    assertEquals(this.getTriple("Lucene"), reader.document(hits[1].doc).get("field"));
    assertEquals(this.getTriple("Lucenne"), reader.document(hits[2].doc).get("field"));
    searcher.close();
    reader.close();
    directory.close();
  }

  @Test
  public void testGiga() throws Exception {

    final Directory directory = new RAMDirectory();
    final IndexWriterConfig conf = new IndexWriterConfig(TEST_VERSION_CURRENT,
      new TupleAnalyzer(TEST_VERSION_CURRENT, new StandardAnalyzer(TEST_VERSION_CURRENT), new AnyURIAnalyzer(TEST_VERSION_CURRENT)));
    final IndexWriter w = new IndexWriter(directory, conf);

    this.addDoc("Lucene in Action", w);
    this.addDoc("Lucene for Dummies", w);

    //addDoc("Giga", w);
    this.addDoc("Giga byte", w);

    this.addDoc("ManagingGigabytesManagingGigabyte", w);
    this.addDoc("ManagingGigabytesManagingGigabytes", w);

    this.addDoc("The Art of Computer Science", w);
    this.addDoc("J. K. Rowling", w);
    this.addDoc("JK Rowling", w);
    this.addDoc("Joanne K Roling", w);
    this.addDoc("Bruce Willis", w);
    this.addDoc("Willis bruce", w);
    this.addDoc("Brute willis", w);
    this.addDoc("B. willis", w);
    final IndexReader r = IndexReader.open(directory);
    w.close();

    // 3. search
    final IndexSearcher searcher = new IndexSearcher(directory);

    final SirenFuzzyQuery q = new SirenFuzzyQuery(new Term("field", "giga"), 0.9f);
    ScoreDoc[] hits = searcher.search(q, 10).scoreDocs;
    assertEquals(1, hits.length);
    assertEquals(this.getTriple("Giga byte"), searcher.doc(hits[0].doc).get("field"));

    SirenCellQuery cq = new SirenCellQuery(q);
    cq.setConstraint(0);
    hits = searcher.search(cq, null, 1000).scoreDocs;
    assertEquals("No documents in /computers category and below in cell 0", 0, hits.length);

    cq = new SirenCellQuery(q);
    cq.setConstraint(2);
    hits = searcher.search(cq, null, 1000).scoreDocs;
    assertEquals("All documents in /computers category and below in cell 2", 1, hits.length);

    searcher.close();
    r.close();
    directory.close();
  }

  private String getTriple(final String text) {
    return "<http://fake.subject> <http://fake.predicate> \"" + text + "\" .\n";
  }

  private void addDoc(final String text, final IndexWriter writer)
  throws IOException {
    final Document doc = new Document();
    doc.add(new Field("field", this.getTriple(text), Store.YES, Index.ANALYZED));
    writer.addDocument(doc);
    writer.commit();
  }

}
