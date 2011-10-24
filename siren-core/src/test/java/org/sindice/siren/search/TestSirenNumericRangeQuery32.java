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
 * @author Renaud Delbru [ 29 Sep 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.search;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField.DataType;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.RandomIndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FilteredTermEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryUtils;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.LuceneTestCase;
import org.apache.lucene.util.NumericUtils;
import org.apache.lucene.util.Version;
import org.apache.lucene.util._TestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sindice.siren.analysis.FloatNumericAnalyzer;
import org.sindice.siren.analysis.IntNumericAnalyzer;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.util.XSDDatatype;

public class TestSirenNumericRangeQuery32 extends LuceneTestCase {

  // distance of entries
  private static final int distance = 6666;
  // shift the starting of the values to the left, to also have negative values:
  private static final int startOffset = - 1 << 15;
  // number of docs to generate for testing
  private static final int noDocs = 10000 * RANDOM_MULTIPLIER;

  private static Directory directory = null;
  private static IndexReader reader = null;
  private static IndexSearcher searcher = null;

  @BeforeClass
  public static void beforeClass() throws Exception {
    directory = newDirectory();

    final TupleAnalyzer analyzer = new TupleAnalyzer(TEST_VERSION_CURRENT,
      new WhitespaceAnalyzer(Version.LUCENE_31),
      new WhitespaceAnalyzer(Version.LUCENE_31));
    analyzer.registerLiteralAnalyzer((XSDDatatype.XSD_INT+"8").toCharArray(), new IntNumericAnalyzer(8));
    analyzer.registerLiteralAnalyzer((XSDDatatype.XSD_INT+"4").toCharArray(), new IntNumericAnalyzer(4));
    analyzer.registerLiteralAnalyzer((XSDDatatype.XSD_INT+"2").toCharArray(), new IntNumericAnalyzer(2));
    analyzer.registerLiteralAnalyzer((XSDDatatype.XSD_FLOAT+"8").toCharArray(), new FloatNumericAnalyzer(8));
    analyzer.registerLiteralAnalyzer((XSDDatatype.XSD_FLOAT+"4").toCharArray(), new FloatNumericAnalyzer(4));
    analyzer.registerLiteralAnalyzer((XSDDatatype.XSD_FLOAT+"2").toCharArray(), new FloatNumericAnalyzer(2));
    analyzer.registerLiteralAnalyzer((XSDDatatype.XSD_INT+Integer.MAX_VALUE).toCharArray(), new IntNumericAnalyzer(Integer.MAX_VALUE));

    final RandomIndexWriter writer = new RandomIndexWriter(random, directory,
        newIndexWriterConfig(TEST_VERSION_CURRENT, analyzer)
        .setMaxBufferedDocs(_TestUtil.nextInt(random, 100, 1000))
        .setMergePolicy(newLogMergePolicy()));

    // Add a series of noDocs docs with increasing int values
    for (int l=0; l<noDocs; l++) {
      final Document doc = new Document();

      int val=distance*l+startOffset;

      // add fields, that have a distance to test general functionality
      doc.add(newField("field8", getTriple(val, XSDDatatype.XSD_INT+"8"), Field.Store.YES, Field.Index.ANALYZED));
      doc.add(newField("field4", getTriple(val, XSDDatatype.XSD_INT+"4"), Field.Store.YES, Field.Index.ANALYZED));
      doc.add(newField("field2", getTriple(val, XSDDatatype.XSD_INT+"2"), Field.Store.YES, Field.Index.ANALYZED));
      doc.add(newField("field"+Integer.MAX_VALUE, getTriple(val, XSDDatatype.XSD_INT+Integer.MAX_VALUE), Field.Store.YES, Field.Index.ANALYZED));

      // add ascending fields with a distance of 1, beginning at -noDocs/2 to
      // test the correct splitting of range and inclusive/exclusive
      val=l-(noDocs/2);

      doc.add(newField("ascfield8", getTriple(val, XSDDatatype.XSD_INT+"8"), Field.Store.YES, Field.Index.ANALYZED));
      doc.add(newField("ascfield4", getTriple(val, XSDDatatype.XSD_INT+"4"), Field.Store.YES, Field.Index.ANALYZED));
      doc.add(newField("ascfield2", getTriple(val, XSDDatatype.XSD_INT+"2"), Field.Store.YES, Field.Index.ANALYZED));

      doc.add(newField("float8", getTriple(val, XSDDatatype.XSD_FLOAT+"8"), Field.Store.YES, Field.Index.ANALYZED));
      doc.add(newField("float4", getTriple(val, XSDDatatype.XSD_FLOAT+"4"), Field.Store.YES, Field.Index.ANALYZED));
      doc.add(newField("float2", getTriple(val, XSDDatatype.XSD_FLOAT+"2"), Field.Store.YES, Field.Index.ANALYZED));
      
      writer.addDocument(doc);
    }

    reader = writer.getReader();
    searcher=newSearcher(reader);
    writer.close();
  }

  @AfterClass
  public static void afterClass() throws Exception {
    searcher.close();
    searcher = null;
    reader.close();
    reader = null;
    directory.close();
    directory = null;
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    // Remove maximum clause limit for the tests
    SirenBooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
  }

  private static String getTriple(final int val, final String datatypeURI) {
    return "<http://fake.subject> <http://fake.predicate/"+val+"> \"" + val + "\"^^<"+datatypeURI+"> .\n";
  }

  private static String getLiteralValue(final String triple) {
    final int firstColon = triple.indexOf('"');
    final int secondColon = triple.indexOf('"', firstColon + 1);
    return triple.substring(firstColon + 1, secondColon);
  }

  /** test for both constant score and boolean query, the other tests only use the constant score mode */
  private void testRange(final int precisionStep) throws Exception {
    final String field="field"+precisionStep;
    final int count=3000;
    final int lower=(distance*3/2)+startOffset, upper=lower + count*distance + (distance/3);
    final SirenNumericRangeQuery<Integer> q = SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, upper, true, true);
    final SirenCellQuery cq = new SirenCellQuery(q);
    cq.setConstraint(2);

    TopDocs topDocs;
    String type;
    q.clearTotalNumberOfTerms();

    type = " (constant score boolean rewrite)";
    q.setRewriteMethod(SirenMultiTermQuery.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE);
    topDocs = searcher.search(cq, null, noDocs, Sort.INDEXORDER);

    final ScoreDoc[] sd = topDocs.scoreDocs;
    assertNotNull(sd);
    assertEquals("Score doc count"+type, count, sd.length);
    Document doc=searcher.doc(sd[0].doc);
    assertEquals("First doc"+type, 2*distance+startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));
    doc=searcher.doc(sd[sd.length-1].doc);
    assertEquals("Last doc"+type, (1+count)*distance+startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));
  }

  @Test
  public void testRange_8bit() throws Exception {
    this.testRange(8);
  }

  @Test
  public void testRange_4bit() throws Exception {
    this.testRange(4);
  }

  @Test
  public void testRange_2bit() throws Exception {
    this.testRange(2);
  }

  @Test
  public void testInverseRange() throws Exception {
    SirenNumericRangeQuery<Integer> q = SirenNumericRangeQuery.newIntRange("field8", 8, 1000, -1000, true, true);
    q.clearTotalNumberOfTerms();
    q.setRewriteMethod(SirenMultiTermQuery.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE);
    TopDocs topDocs = searcher.search(q, null, noDocs, Sort.INDEXORDER);
    assertEquals("A inverse range should return the EMPTY_DOCIDSET instance", 0, topDocs.totalHits);

    q = SirenNumericRangeQuery.newIntRange("field8", 8, Integer.MAX_VALUE, null, false, false);
    q.clearTotalNumberOfTerms();
    q.setRewriteMethod(SirenMultiTermQuery.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE);
    topDocs = searcher.search(q, null, noDocs, Sort.INDEXORDER);
    assertEquals("A exclusive range starting with Integer.MAX_VALUE should return the EMPTY_DOCIDSET instance", 0, topDocs.totalHits);

    q = SirenNumericRangeQuery.newIntRange("field8", 8, null, Integer.MIN_VALUE, false, false);
    q.clearTotalNumberOfTerms();
    q.setRewriteMethod(SirenMultiTermQuery.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE);
    topDocs = searcher.search(q, null, noDocs, Sort.INDEXORDER);
    assertEquals("A exclusive range ending with Integer.MIN_VALUE should return the EMPTY_DOCIDSET instance", 0, topDocs.totalHits);
  }

  @Test
  public void testOneMatchQuery() throws Exception {
    final SirenNumericRangeQuery<Integer> q = SirenNumericRangeQuery.newIntRange("ascfield8", 8, 1000, 1000, true, true);
    assertSame(SirenMultiTermQuery.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE, q.getRewriteMethod());
    final TopDocs topDocs = searcher.search(q, noDocs);
    final ScoreDoc[] sd = topDocs.scoreDocs;
    assertNotNull(sd);
    assertEquals("Score doc count", 1, sd.length );
  }

  private void testLeftOpenRange(final int precisionStep) throws Exception {
    final String field="field"+precisionStep;
    final int count=3000;
    final int upper=(count-1)*distance + (distance/3) + startOffset;
    SirenNumericRangeQuery<Integer> q = SirenNumericRangeQuery.newIntRange(field, precisionStep, null, upper, true, true);
    SirenCellQuery cq = new SirenCellQuery(q);
    cq.setConstraint(2);

    TopDocs topDocs = searcher.search(cq, null, noDocs, Sort.INDEXORDER);
    if (VERBOSE) System.out.println("Found "+q.getTotalNumberOfTerms()+" distinct terms in left open range for field '"+field+"'.");
    ScoreDoc[] sd = topDocs.scoreDocs;
    assertNotNull(sd);
    assertEquals("Score doc count", count, sd.length );
    Document doc = searcher.doc(sd[0].doc);
    assertEquals("First doc", startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));
    doc=searcher.doc(sd[sd.length-1].doc);
    assertEquals("Last doc", (count-1)*distance+startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));

    q = SirenNumericRangeQuery.newIntRange(field, precisionStep, null, upper, false, true);
    cq = new SirenCellQuery(q);
    cq.setConstraint(2);
    topDocs = searcher.search(cq, null, noDocs, Sort.INDEXORDER);
    sd = topDocs.scoreDocs;
    assertNotNull(sd);
    assertEquals("Score doc count", count, sd.length );
    doc=searcher.doc(sd[0].doc);
    assertEquals("First doc", startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));
    doc=searcher.doc(sd[sd.length-1].doc);
    assertEquals("Last doc", (count-1)*distance+startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));
  }

  @Test
  public void testLeftOpenRange_8bit() throws Exception {
    this.testLeftOpenRange(8);
  }

  @Test
  public void testLeftOpenRange_4bit() throws Exception {
    this.testLeftOpenRange(4);
  }

  @Test
  public void testLeftOpenRange_2bit() throws Exception {
    this.testLeftOpenRange(2);
  }

  private void testRightOpenRange(final int precisionStep) throws Exception {
    final String field="field"+precisionStep;
    final int count=3000;
    final int lower=(count-1)*distance + (distance/3) +startOffset;
    SirenNumericRangeQuery<Integer> q = SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, null, true, true);
    SirenCellQuery cq = new SirenCellQuery(q);
    cq.setConstraint(2);

    TopDocs topDocs = searcher.search(cq, null, noDocs, Sort.INDEXORDER);
    if (VERBOSE) System.out.println("Found "+q.getTotalNumberOfTerms()+" distinct terms in right open range for field '"+field+"'.");
    ScoreDoc[] sd = topDocs.scoreDocs;
    assertNotNull(sd);
    assertEquals("Score doc count", noDocs-count, sd.length);
    Document doc = searcher.doc(sd[0].doc);
    assertEquals("First doc", count*distance+startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));
    doc = searcher.doc(sd[sd.length-1].doc);
    assertEquals("Last doc", (noDocs-1)*distance+startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));

    q = SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, null, true, false);
    cq = new SirenCellQuery(q);
    cq.setConstraint(2);
    topDocs = searcher.search(cq, null, noDocs, Sort.INDEXORDER);
    sd = topDocs.scoreDocs;
    assertNotNull(sd);
    assertEquals("Score doc count", noDocs-count, sd.length );
    doc = searcher.doc(sd[0].doc);
    assertEquals("First doc", count*distance+startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));
    doc = searcher.doc(sd[sd.length-1].doc);
    assertEquals("Last doc", (noDocs-1)*distance+startOffset, Integer.parseInt(getLiteralValue(doc.get(field))));
  }

  @Test
  public void testRightOpenRange_8bit() throws Exception {
    this.testRightOpenRange(8);
  }

  @Test
  public void testRightOpenRange_4bit() throws Exception {
    this.testRightOpenRange(4);
  }

  @Test
  public void testRightOpenRange_2bit() throws Exception {
    this.testRightOpenRange(2);
  }

  private void testRandomTrieAndClassicRangeQuery(final int precisionStep) throws Exception {
    final String field="field"+precisionStep;
    int termCountT=0,termCountC=0;
    final int num = 10 * RANDOM_MULTIPLIER;
    for (int i = 0; i < num; i++) {
      int lower=(int)(random.nextDouble()*noDocs*distance)+startOffset;
      int upper=(int)(random.nextDouble()*noDocs*distance)+startOffset;
      if (lower>upper) {
        final int a=lower; lower=upper; upper=a;
      }
      final String prefix = DataType.INT.name() + precisionStep;
      // test inclusive range
      SirenNumericRangeQuery<Integer> tq=SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, upper, true, true);
      SirenTermRangeQuery cq=new SirenTermRangeQuery(field, prefix + NumericUtils.intToPrefixCoded(lower), prefix + NumericUtils.intToPrefixCoded(upper), true, true);
      TopDocs tTopDocs = searcher.search(tq, 1);
      TopDocs cTopDocs = searcher.search(cq, 1);
      assertEquals("Returned count for SirenNumericRangeQuery and SirenTermRangeQuery must be equal", cTopDocs.totalHits, tTopDocs.totalHits );
      termCountT += tq.getTotalNumberOfTerms();
      termCountC += cq.getTotalNumberOfTerms();
      // test exclusive range
      tq=SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, upper, false, false);
      cq=new SirenTermRangeQuery(field, prefix + NumericUtils.intToPrefixCoded(lower), prefix + NumericUtils.intToPrefixCoded(upper), false, false);
      tTopDocs = searcher.search(tq, 1);
      cTopDocs = searcher.search(cq, 1);
      assertEquals("Returned count for SirenNumericRangeQuery and SirenTermRangeQuery must be equal", cTopDocs.totalHits, tTopDocs.totalHits );
      termCountT += tq.getTotalNumberOfTerms();
      termCountC += cq.getTotalNumberOfTerms();
      // test left exclusive range
      tq=SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, upper, false, true);
      cq=new SirenTermRangeQuery(field, prefix + NumericUtils.intToPrefixCoded(lower), prefix + NumericUtils.intToPrefixCoded(upper), false, true);
      tTopDocs = searcher.search(tq, 1);
      cTopDocs = searcher.search(cq, 1);
      assertEquals("Returned count for SirenNumericRangeQuery and SirenTermRangeQuery must be equal", cTopDocs.totalHits, tTopDocs.totalHits );
      termCountT += tq.getTotalNumberOfTerms();
      termCountC += cq.getTotalNumberOfTerms();
      // test right exclusive range
      tq=SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, upper, true, false);
      cq=new SirenTermRangeQuery(field, prefix + NumericUtils.intToPrefixCoded(lower), prefix + NumericUtils.intToPrefixCoded(upper), true, false);
      tTopDocs = searcher.search(tq, 1);
      cTopDocs = searcher.search(cq, 1);
      assertEquals("Returned count for SirenNumericRangeQuery and SirenTermRangeQuery must be equal", cTopDocs.totalHits, tTopDocs.totalHits );
      termCountT += tq.getTotalNumberOfTerms();
      termCountC += cq.getTotalNumberOfTerms();
    }
    if (precisionStep == Integer.MAX_VALUE && searcher.getIndexReader().getSequentialSubReaders().length == 1) {
      assertEquals("Total number of terms should be equal for unlimited precStep", termCountT, termCountC);
    } else if (VERBOSE) {
      System.out.println("Average number of terms during random search on '" + field + "':");
      System.out.println(" Trie query: " + (((double)termCountT)/(num * 4)));
      System.out.println(" Classical query: " + (((double)termCountC)/(num * 4)));
    }
  }

  @Test
  public void testRandomTrieAndClassicRangeQuery_8bit() throws Exception {
    this.testRandomTrieAndClassicRangeQuery(8);
  }

  @Test
  public void testRandomTrieAndClassicRangeQuery_4bit() throws Exception {
    this.testRandomTrieAndClassicRangeQuery(4);
  }

  @Test
  public void testRandomTrieAndClassicRangeQuery_2bit() throws Exception {
    this.testRandomTrieAndClassicRangeQuery(2);
  }

  @Test
  public void testRandomTrieAndClassicRangeQuery_NoTrie() throws Exception {
    this.testRandomTrieAndClassicRangeQuery(Integer.MAX_VALUE);
  }

  private void testRangeSplit(final int precisionStep) throws Exception {
    final String field="ascfield"+precisionStep;
    // 10 random tests
    final int num = 10 * RANDOM_MULTIPLIER;
    for (int i = 0; i < num; i++) {
      int lower=(int)(random.nextDouble()*noDocs - noDocs/2);
      int upper=(int)(random.nextDouble()*noDocs - noDocs/2);
      if (lower>upper) {
        final int a=lower; lower=upper; upper=a;
      }
      // test inclusive range
      Query tq=SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, upper, true, true);
      TopDocs tTopDocs = searcher.search(tq, 1);
      assertEquals("Returned count of range query must be equal to inclusive range length", upper-lower+1, tTopDocs.totalHits );
      // test exclusive range
      tq=SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, upper, false, false);
      tTopDocs = searcher.search(tq, 1);
      assertEquals("Returned count of range query must be equal to exclusive range length", Math.max(upper-lower-1, 0), tTopDocs.totalHits );
      // test left exclusive range
      tq=SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, upper, false, true);
      tTopDocs = searcher.search(tq, 1);
      assertEquals("Returned count of range query must be equal to half exclusive range length", upper-lower, tTopDocs.totalHits );
      // test right exclusive range
      tq=SirenNumericRangeQuery.newIntRange(field, precisionStep, lower, upper, true, false);
      tTopDocs = searcher.search(tq, 1);
      assertEquals("Returned count of range query must be equal to half exclusive range length", upper-lower, tTopDocs.totalHits );
    }
  }

  @Test
  public void testRangeSplit_8bit() throws Exception {
    this.testRangeSplit(8);
  }

  @Test
  public void testRangeSplit_4bit() throws Exception {
    this.testRangeSplit(4);
  }

  @Test
  public void testRangeSplit_2bit() throws Exception {
    this.testRangeSplit(2);
  }

  /** we fake a float test using int2float conversion of NumericUtils */
  private void testFloatRange(final int precisionStep) throws Exception {
    final String field="float"+precisionStep;
    final float lower=-1000, upper=+2000;

//    final Query tq=SirenNumericRangeQuery.newFloatRange(field, precisionStep,
//      NumericUtils.sortableIntToFloat(lower), NumericUtils.sortableIntToFloat(upper), true, true);

    /*
     * Original Lucene test was faking a float using the NumericUtils.sortableIntToFloat method.
     * Since in Siren we index also the datatype, we cannot do that: using a float query to search
     * for a value indexed with XSD_INT datatype. 
     */
    final Query tq=SirenNumericRangeQuery.newFloatRange(field, precisionStep,
      lower, upper, true, true);
    final TopDocs tTopDocs = searcher.search(tq, 1);
    assertEquals("Returned count of range query must be equal to inclusive range length", upper-lower+1, tTopDocs.totalHits );
  }

  @Test
  public void testFloatRange_8bit() throws Exception {
    this.testFloatRange(8);
  }

  @Test
  public void testFloatRange_4bit() throws Exception {
    this.testFloatRange(4);
  }

  @Test
  public void testFloatRange_2bit() throws Exception {
    this.testFloatRange(2);
  }

  @Test
  public void testEqualsAndHash() throws Exception {
    QueryUtils.checkHashEquals(SirenNumericRangeQuery.newIntRange("test1", 4, 10, 20, true, true));
    QueryUtils.checkHashEquals(SirenNumericRangeQuery.newIntRange("test2", 4, 10, 20, false, true));
    QueryUtils.checkHashEquals(SirenNumericRangeQuery.newIntRange("test3", 4, 10, 20, true, false));
    QueryUtils.checkHashEquals(SirenNumericRangeQuery.newIntRange("test4", 4, 10, 20, false, false));
    QueryUtils.checkHashEquals(SirenNumericRangeQuery.newIntRange("test5", 4, 10, null, true, true));
    QueryUtils.checkHashEquals(SirenNumericRangeQuery.newIntRange("test6", 4, null, 20, true, true));
    QueryUtils.checkHashEquals(SirenNumericRangeQuery.newIntRange("test7", 4, null, null, true, true));
    QueryUtils.checkEqual(
      SirenNumericRangeQuery.newIntRange("test8", 4, 10, 20, true, true),
      SirenNumericRangeQuery.newIntRange("test8", 4, 10, 20, true, true)
    );
    QueryUtils.checkUnequal(
      SirenNumericRangeQuery.newIntRange("test9", 4, 10, 20, true, true),
      SirenNumericRangeQuery.newIntRange("test9", 8, 10, 20, true, true)
    );
    QueryUtils.checkUnequal(
      SirenNumericRangeQuery.newIntRange("test10a", 4, 10, 20, true, true),
      SirenNumericRangeQuery.newIntRange("test10b", 4, 10, 20, true, true)
    );
    QueryUtils.checkUnequal(
      SirenNumericRangeQuery.newIntRange("test11", 4, 10, 20, true, true),
      SirenNumericRangeQuery.newIntRange("test11", 4, 20, 10, true, true)
    );
    QueryUtils.checkUnequal(
      SirenNumericRangeQuery.newIntRange("test12", 4, 10, 20, true, true),
      SirenNumericRangeQuery.newIntRange("test12", 4, 10, 20, false, true)
    );
    QueryUtils.checkUnequal(
      SirenNumericRangeQuery.newIntRange("test13", 4, 10, 20, true, true),
      SirenNumericRangeQuery.newFloatRange("test13", 4, 10f, 20f, true, true)
    );
    // the following produces a hash collision, because Long and Integer have the same hashcode, so only test equality:
    final Query q1 = SirenNumericRangeQuery.newIntRange("test14", 4, 10, 20, true, true);
    final Query q2 = SirenNumericRangeQuery.newLongRange("test14", 4, 10L, 20L, true, true);
    assertFalse(q1.equals(q2));
    assertFalse(q2.equals(q1));
  }

  private void testEnum(final int lower, final int upper) throws Exception {
    final SirenNumericRangeQuery<Integer> q = SirenNumericRangeQuery.newIntRange("field4", 4, lower, upper, true, true);
    final FilteredTermEnum termEnum = q.getEnum(searcher.getIndexReader());
    try {
      int count = 0;
      do {
        final Term t = termEnum.term();
        if (t != null) {
          // without the datatype and precision step prefix
          final int val = NumericUtils.prefixCodedToInt(t.text().substring(DataType.INT.name().length() + 1));
          assertTrue("value not in bounds", val >= lower && val <= upper);
          count++;
        } else break;
      } while (termEnum.next());
      assertFalse(termEnum.next());
      if (VERBOSE) System.out.println("TermEnum on 'field4' for range [" + lower + "," + upper + "] contained " + count + " terms.");
    } finally {
      termEnum.close();
    }
  }

  @Test
  public void testEnum() throws Exception {
    final int count=3000;
    int lower=(distance*3/2)+startOffset, upper=lower + count*distance + (distance/3);
    // test enum with values
    this.testEnum(lower, upper);
    // test empty enum
    this.testEnum(upper, lower);
    // test empty enum outside of bounds
    lower = distance*noDocs+startOffset;
    upper = 2 * lower;
    this.testEnum(lower, upper);
  }

}