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
 * @author Renaud Delbru [ 28 Sep 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.ToStringUtils;

/**
 * A query that wraps another query or a filter and simply returns a constant score equal to the
 * query boost for every document that matches the filter or query.
 * For queries it therefore simply strips of all scores and returns a constant one.
 *
 * <p><b>NOTE</b>: if the wrapped filter is an instance of
 * {@link CachingWrapperFilter}, you'll likely want to
 * enforce deletions in the filter (using either {@link
 * CachingWrapperFilter.DeletesMode#RECACHE} or {@link
 * CachingWrapperFilter.DeletesMode#DYNAMIC}).
 */
public class SirenConstantScoreQuery extends SirenPrimitiveQuery {

  protected final SirenFilter filter;
  protected final SirenPrimitiveQuery query;

  /** Strips off scores from the passed in Query. The hits will get a constant score
   * dependent on the boost factor of this query. */
  public SirenConstantScoreQuery(final SirenPrimitiveQuery query) {
    if (query == null)
      throw new NullPointerException("Query may not be null");
    this.filter = null;
    this.query = query;
  }

  /** Wraps a Filter as a Query. The hits will get a constant score
   * dependent on the boost factor of this query.
   * If you simply want to strip off scores from a Query, no longer use
   * {@code new ConstantScoreQuery(new QueryWrapperFilter(query))}, instead
   * use {@link #ConstantScoreQuery(Query)}!
   */
  public SirenConstantScoreQuery(final SirenFilter filter) {
    throw new UnsupportedOperationException("Filter not yet supported");

    // TODO: Activate filters when Siren will support filters.

//    if (filter == null)
//      throw new NullPointerException("Filter may not be null");
//    this.filter = filter;
//    this.query = null;
  }

  /** Returns the encapsulated filter, returns {@code null} if a query is wrapped. */
  public SirenFilter getFilter() {
    return filter;
  }

  /** Returns the encapsulated query, returns {@code null} if a filter is wrapped. */
  public SirenPrimitiveQuery getQuery() {
    return query;
  }

  @Override
  public SirenPrimitiveQuery rewrite(final IndexReader reader) throws IOException {
    if (query != null) {
      SirenPrimitiveQuery rewritten = (SirenPrimitiveQuery) query.rewrite(reader);
      if (rewritten != query) {
        rewritten = new SirenConstantScoreQuery(rewritten);
        rewritten.setBoost(this.getBoost());
        return rewritten;
      }
    }
    return this;
  }

  @Override
  public void extractTerms(final Set<Term> terms) {
    // TODO: OK to not add any terms when wrapped a filter
    // and used with MultiSearcher, but may not be OK for
    // highlighting.
    // If a query was wrapped, we delegate to query.
    if (query != null)
      query.extractTerms(terms);
  }

  protected class SirenConstantWeight extends Weight {

    private final Weight innerWeight;
    private final Similarity similarity;
    private float queryNorm;
    private float queryWeight;

    public SirenConstantWeight(final Searcher searcher) throws IOException {
      this.similarity = SirenConstantScoreQuery.this.getSimilarity(searcher);
      this.innerWeight = (query == null) ? null : query.createWeight(searcher);
    }

    @Override
    public Query getQuery() {
      return SirenConstantScoreQuery.this;
    }

    @Override
    public float getValue() {
      return queryWeight;
    }

    @Override
    public float sumOfSquaredWeights() throws IOException {
      // we calculate sumOfSquaredWeights of the inner weight, but ignore it (just to initialize everything)
      if (innerWeight != null) innerWeight.sumOfSquaredWeights();
      queryWeight = SirenConstantScoreQuery.this.getBoost();
      return queryWeight * queryWeight;
    }

    @Override
    public void normalize(final float norm) {
      this.queryNorm = norm;
      queryWeight *= this.queryNorm;
      // we normalize the inner weight, but ignore it (just to initialize everything)
      if (innerWeight != null) innerWeight.normalize(norm);
    }

    @Override
    public Scorer scorer(final IndexReader reader, final boolean scoreDocsInOrder, final boolean topScorer)
    throws IOException {
      final DocTupCelIdSetIterator it;

      // TODO: Activate filters when Siren will support filters.

//      if (filter != null) {
//        assert query == null;
//        final DocIdSet dis = filter.getDocIdSet(reader);
//        if (dis == null)
//          return null;
//        disi = dis.iterator();
//      } else {

      assert query != null && innerWeight != null;
      it = (SirenScorer) innerWeight.scorer(reader, scoreDocsInOrder, topScorer);

//      }

      if (it == null) {
        return null;
      }

      return new SirenConstantScorer(similarity, it, this);
    }

    @Override
    public boolean scoresDocsOutOfOrder() {
      return (innerWeight != null) ? innerWeight.scoresDocsOutOfOrder() : false;
    }

    @Override
    public Explanation explain(final IndexReader reader, final int doc) throws IOException {
      final Scorer cs = this.scorer(reader, true, false);
      final boolean exists = (cs != null && cs.advance(doc) == doc);

      final ComplexExplanation result = new ComplexExplanation();
      if (exists) {
        result.setDescription(SirenConstantScoreQuery.this.toString() + ", product of:");
        result.setValue(queryWeight);
        result.setMatch(Boolean.TRUE);
        result.addDetail(new Explanation(SirenConstantScoreQuery.this.getBoost(), "boost"));
        result.addDetail(new Explanation(queryNorm, "queryNorm"));
      } else {
        result.setDescription(SirenConstantScoreQuery.this.toString() + " doesn't match id " + doc);
        result.setValue(0);
        result.setMatch(Boolean.FALSE);
      }
      return result;
    }
  }

  protected class SirenConstantScorer extends SirenPrimitiveScorer {

    final DocTupCelIdSetIterator it;
    final float theScore;

    public SirenConstantScorer(final Similarity similarity,
                               final DocTupCelIdSetIterator docIdSetIterator,
                               final Weight w)
    throws IOException {
      super(similarity, w);
      theScore = w.getValue();
      this.it = docIdSetIterator;
    }

    @Override
    public int nextDoc() throws IOException {
      return it.nextDoc();
    }

    @Override
    public int nextPosition()
    throws IOException {
      return it.nextPosition();
    }

    @Override
    public int docID() {
      return it.entity();
    }

    @Override
    public int dataset() {
      return it.dataset();
    }

    @Override
    public int entity() {
      return it.entity();
    }

    @Override
    public int tuple() {
      return it.tuple();
    }

    @Override
    public int cell() {
      return it.cell();
    }

    @Override
    public int pos() {
      return it.pos();
    }

    @Override
    public float score() throws IOException {
      return theScore;
    }

    @Override
    public int advance(final int target) throws IOException {
      return it.advance(target);
    }

    @Override
    public int advance(final int entityID, final int tupleID)
    throws IOException {
      return it.advance(entityID, tupleID);
    }

    @Override
    public int advance(final int entityID, final int tupleID, final int cellID)
    throws IOException {
      return it.advance(entityID, tupleID, cellID);
    }

    private Collector wrapCollector(final Collector collector) {
      return new Collector() {
        @Override
        public void setScorer(final Scorer scorer) throws IOException {
          // we must wrap again here, but using the scorer passed in as parameter:
          collector.setScorer(
            new SirenConstantScorer(
              SirenConstantScorer.this.getSimilarity(),
              (SirenScorer) scorer, SirenConstantScorer.this.weight));
        }

        @Override
        public void collect(final int doc) throws IOException {
          collector.collect(doc);
        }

        @Override
        public void setNextReader(final IndexReader reader, final int docBase)
        throws IOException {
          collector.setNextReader(reader, docBase);
        }

        @Override
        public boolean acceptsDocsOutOfOrder() {
          return collector.acceptsDocsOutOfOrder();
        }
      };
    }

    // this optimization allows out of order scoring as top scorer!
    @Override
    public void score(final Collector collector) throws IOException {
      ((SirenScorer) it).score(this.wrapCollector(collector));
    }

    // this optimization allows out of order scoring as top scorer,
    @Override
    public boolean score(final Collector collector, final int max, final int firstDocID)
    throws IOException {
      return ((SirenScorer) it).score(this.wrapCollector(collector), max, firstDocID);
    }

  }

  @Override
  public Weight createWeight(final Searcher searcher) throws IOException {
    return new SirenConstantScoreQuery.SirenConstantWeight(searcher);
  }

  @Override
  public String toString(final String field) {
    return new StringBuilder("ConstantScore(")
      .append((query == null) ? filter.toString() : query.toString(field))
      .append(')')
      .append(ToStringUtils.boost(this.getBoost()))
      .toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!super.equals(o))
      return false;
    if (o instanceof SirenConstantScoreQuery) {
      final SirenConstantScoreQuery other = (SirenConstantScoreQuery) o;
      return
        ((this.filter == null) ? other.filter == null : this.filter.equals(other.filter)) &&
        ((this.query == null) ? other.query == null : this.query.equals(other.query));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return 31 * super.hashCode() +
      ((query == null) ? filter : query).hashCode();
  }

}
