package org.sindice.siren.search.bench;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.DeltaTupleAnalyzer;
import org.sindice.siren.bench.SirenBenchmark;
import org.sindice.siren.search.QueryTestingHelper;
import org.sindice.siren.search.SirenCellQuery;
import org.sindice.siren.search.SirenTermQuery;
import org.sindice.siren.search.SirenTupleClause;
import org.sindice.siren.search.SirenTupleQuery;

import com.google.caliper.Param;
import com.google.caliper.Runner;

public class SirenQueryBenchmark extends SirenBenchmark {

  protected QueryTestingHelper _helper = null;
  @Param({"100", "1000", "10000"}) private int size;

  @Override
  protected void setUp() throws Exception {
    rand.setSeed(42);
    _helper = new QueryTestingHelper(new DeltaTupleAnalyzer(new StandardAnalyzer(Version.LUCENE_31)));
    this.prepareIndex();
  }

  @Override
  protected void tearDown() throws Exception {
    _helper.close();
  }

  public int timeType(final int reps) throws IOException {
    int nHits = 0;
    for (int i = 0; i < reps; i++) {
      final SirenTermQuery query = new SirenTermQuery(new Term("content", "type"));
      final ScoreDoc[] hits = _helper.search(query);
      nHits = hits.length;
    }
    return nHits;
  }

  public int timePerson(final int reps) throws IOException {
    int nHits = 0;
    for (int i = 0; i < reps; i++) {
      final SirenTermQuery query = new SirenTermQuery(new Term("content", "person"));
      final ScoreDoc[] hits = _helper.search(query);
      nHits = hits.length;
    }
    return nHits;
  }

  public int timeWine(final int reps) throws IOException {
    int nHits = 0;
    for (int i = 0; i < reps; i++) {
      final SirenTermQuery query = new SirenTermQuery(new Term("content", "wine"));
      final ScoreDoc[] hits = _helper.search(query);
      nHits = hits.length;
    }
    return nHits;
  }

  public int timeBusinessAndPerson(final int reps) throws IOException {
    int nHits = 0;
    for (int i = 0; i < reps; i++) {
      final SirenTermQuery qperson = new SirenTermQuery(new Term("content", "person"));
      final SirenTermQuery qbusiness = new SirenTermQuery(new Term("content", "business"));
      final BooleanQuery q = new BooleanQuery();
      q.add(qperson, Occur.MUST);
      q.add(qbusiness, Occur.MUST);
      final ScoreDoc[] hits = _helper.search(q);
      nHits = hits.length;
    }
    return nHits;
  }

  public int timeTypePerson(final int reps) throws IOException {
    int nHits = 0;
    for (int i = 0; i < reps; i++) {
      final SirenTermQuery qtype = new SirenTermQuery(new Term("content", "type"));
      final SirenTermQuery qperson = new SirenTermQuery(new Term("content", "person"));
      final SirenCellQuery cq1 = new SirenCellQuery(qtype);
      cq1.setConstraint(1);
      final SirenCellQuery cq2 = new SirenCellQuery(qperson);
      cq1.setConstraint(2, Integer.MAX_VALUE);
      final SirenTupleQuery tq = new SirenTupleQuery();
      tq.add(cq1, SirenTupleClause.Occur.MUST);
      tq.add(cq2, SirenTupleClause.Occur.MUST);
      final ScoreDoc[] hits = _helper.search(tq);
      nHits = hits.length;
    }
    return nHits;
  }

  private void prepareIndex() throws CorruptIndexException, IOException {
    for (int i = 0; i < size; i++) {
      _helper.addDocument(this.readNTriplesFile(this.nextFile()));
    }
  }

  public static void main(final String[] args) throws Exception {
    Runner.main(SirenQueryBenchmark.class, args);
  }

}