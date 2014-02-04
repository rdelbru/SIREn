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

package org.sindice.siren.search;

import static org.sindice.siren.search.AbstractTestSirenScorer.NodeTermQueryBuilder.ntq;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.sindice.siren.index.DocsAndNodesIterator;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;
import org.sindice.siren.search.node.MultiNodeTermQuery.RewriteMethod;
import org.sindice.siren.search.node.NodeBooleanClause;
import org.sindice.siren.search.node.NodeBooleanClause.Occur;
import org.sindice.siren.search.node.NodeBooleanQuery;
import org.sindice.siren.search.node.NodeNumericRangeQuery;
import org.sindice.siren.search.node.NodePhraseQuery;
import org.sindice.siren.search.node.NodePrimitiveQuery;
import org.sindice.siren.search.node.NodeQuery;
import org.sindice.siren.search.node.NodeScorer;
import org.sindice.siren.search.node.NodeTermQuery;
import org.sindice.siren.search.node.TupleQuery;
import org.sindice.siren.search.node.TwigQuery;
import org.sindice.siren.search.node.TwigQuery.EmptyRootQuery;
import org.sindice.siren.util.BasicSirenTestCase;

public abstract class AbstractTestSirenScorer extends BasicSirenTestCase {

  public static LuceneProxyNodeQuery dq(final NodeQuery nq) {
    return new LuceneProxyNodeQuery(nq);
  }

  protected NodeScorer getScorer(final NodeQueryBuilder builder)
  throws IOException {
    return (NodeScorer) this.getScorer(builder.getNodeQuery());
  }

  protected Scorer getScorer(final Query query) throws IOException {
    final Weight weight = searcher.createNormalizedWeight(query);
    assertTrue(searcher.getTopReaderContext() instanceof AtomicReaderContext);
    final AtomicReaderContext context = (AtomicReaderContext) searcher.getTopReaderContext();
    return weight.scorer(context, true, true, context.reader().getLiveDocs());
  }

  public static abstract class LuceneQueryBuilder {

    public abstract Query getQuery();

  }

  public static abstract class NodeQueryBuilder {

    public NodeQueryBuilder bound(final int lowerBound, final int upperBound) {
      this.getNodeQuery().setNodeConstraint(lowerBound, upperBound);
      return this;
    }

    public NodeQueryBuilder level(final int level) {
      this.getNodeQuery().setLevelConstraint(level);
      return this;
    }

    public abstract NodeQuery getNodeQuery();

    public abstract Query getLuceneProxyQuery();

    /**
     * Should be implemented only by {@link NodePrimitiveQuery} builders
     */
    public NodeQueryBuilder setDatatype(final String datatype) {
      throw new UnsupportedOperationException();
    }

  }

  public static class NodeNumericRangeQueryBuilder extends NodeQueryBuilder {

    protected final NodeNumericRangeQuery<? extends Number> nmq;

    public NodeNumericRangeQueryBuilder setRewriteMethod(final RewriteMethod method) {
      nmq.setRewriteMethod(method);
      return this;
    }

    private NodeNumericRangeQueryBuilder(final String field,
                                         final int precisionStep,
                                         final Integer min,
                                         final Integer max,
                                         final boolean minInclusive,
                                         final boolean maxInclusive) {
      nmq = NodeNumericRangeQuery
      .newIntRange(field, precisionStep, min, max, minInclusive, maxInclusive);
    }

    private NodeNumericRangeQueryBuilder(final String field,
                                         final int precisionStep,
                                         final Float min,
                                         final Float max,
                                         final boolean minInclusive,
                                         final boolean maxInclusive) {
      nmq = NodeNumericRangeQuery
      .newFloatRange(field, precisionStep, min, max, minInclusive, maxInclusive);
    }

    private NodeNumericRangeQueryBuilder(final String field,
                                         final int precisionStep,
                                         final Double min,
                                         final Double max,
                                         final boolean minInclusive,
                                         final boolean maxInclusive) {
      nmq = NodeNumericRangeQuery
      .newDoubleRange(field, precisionStep, min, max, minInclusive, maxInclusive);
    }

    private NodeNumericRangeQueryBuilder(final String field,
                                         final int precisionStep,
                                         final Long min,
                                         final Long max,
                                         final boolean minInclusive,
                                         final boolean maxInclusive) {
      nmq = NodeNumericRangeQuery
      .newLongRange(field, precisionStep, min, max, minInclusive, maxInclusive);
    }

    public static NodeNumericRangeQueryBuilder nmqInt(final String field,
                                                      final int precisionStep,
                                                      final Integer min,
                                                      final Integer max,
                                                      final boolean minInclusive,
                                                      final boolean maxInclusive) {
      return new NodeNumericRangeQueryBuilder(field, precisionStep, min, max, minInclusive, maxInclusive);
    }

    public static NodeNumericRangeQueryBuilder nmqFloat(final String field,
                                                        final int precisionStep,
                                                        final Float min,
                                                        final Float max,
                                                        final boolean minInclusive,
                                                        final boolean maxInclusive) {
      return new NodeNumericRangeQueryBuilder(field, precisionStep, min, max, minInclusive, maxInclusive);
    }

    public static NodeNumericRangeQueryBuilder nmqDouble(final String field,
                                                         final int precisionStep,
                                                         final Double min,
                                                         final Double max,
                                                         final boolean minInclusive,
                                                         final boolean maxInclusive) {
      return new NodeNumericRangeQueryBuilder(field, precisionStep, min, max, minInclusive, maxInclusive);
    }

    public static NodeNumericRangeQueryBuilder nmqLong(final String field,
                                                       final int precisionStep,
                                                       final Long min,
                                                       final Long max,
                                                       final boolean minInclusive,
                                                       final boolean maxInclusive) {
      return new NodeNumericRangeQueryBuilder(field, precisionStep, min, max, minInclusive, maxInclusive);
    }

    @Override
    public NodeQuery getNodeQuery() {
      return nmq;
    }

    @Override
    public Query getLuceneProxyQuery() {
      return new LuceneProxyNodeQuery(nmq);
    }

    @Override
    public NodeQueryBuilder setDatatype(final String datatype) {
      nmq.setDatatype(datatype);
      return this;
    }

  }

  public static class NodeTermQueryBuilder extends NodeQueryBuilder {

    protected final NodeTermQuery ntq;

    private NodeTermQueryBuilder(final String fieldName, final String term) {
      final Term t = new Term(fieldName, term);
      ntq = new NodeTermQuery(t);
    }

    public static NodeTermQueryBuilder ntq(final String term) {
      return new NodeTermQueryBuilder(DEFAULT_TEST_FIELD, term);
    }

    @Override
    public NodeQuery getNodeQuery() {
      return ntq;
    }

    @Override
    public Query getLuceneProxyQuery() {
      return new LuceneProxyNodeQuery(ntq);
    }

    @Override
    public NodeQueryBuilder setDatatype(final String datatype) {
      ntq.setDatatype(datatype);
      return this;
    }

  }

  public static class NodePhraseQueryBuilder extends NodeQueryBuilder {

    protected final NodePhraseQuery npq;

    private NodePhraseQueryBuilder(final String fieldName, final String[] terms) {
      npq = new NodePhraseQuery();
      for (int i = 0; i < terms.length; i++) {
        if (terms[i].isEmpty()) { // if empty string, skip it
          continue;
        }
        final Term t = new Term(fieldName, terms[i]);
        npq.add(t, i);
      }
    }

    /**
     * If term is equal to an empty string, this is considered as a position
     * gap.
     */
    public static NodePhraseQueryBuilder npq(final String ... terms) {
      return npq(DEFAULT_TEST_FIELD, terms);
    }

    /**
     * If term is equal to an empty string, this is considered as a position
     * gap.
     * The field value is passed as an argument
     */
    public static NodePhraseQueryBuilder npq(final String field, final String[] terms) {
      return new NodePhraseQueryBuilder(field, terms);
    }

    @Override
    public NodeQuery getNodeQuery() {
      return npq;
    }

    @Override
    public Query getLuceneProxyQuery() {
      return new LuceneProxyNodeQuery(npq);
    }


    @Override
    public NodeQueryBuilder setDatatype(final String datatype) {
      npq.setDatatype(datatype);
      return this;
    }

  }

  public static class BooleanClauseBuilder {

    public static BooleanBag must(final NodeQueryBuilder builder) {
      return BooleanBag.must(builder.getNodeQuery());
    }

    public static BooleanBag must(final LuceneQueryBuilder... builders) {
      final Query[] queries = new Query[builders.length];
      for (int i = 0; i < builders.length; i++) {
        queries[i] = builders[i].getQuery();
      }
      return BooleanBag.must(queries);
    }

    public static BooleanBag must(final String term) {
      return BooleanBag.must(ntq(term).ntq);
    }

    public static BooleanBag must(final String... terms) {
      final Query[] queries = new Query[terms.length];
      for (int i = 0; i < terms.length; i++) {
        queries[i] = ntq(terms[i]).ntq;
      }
      return BooleanBag.must(queries);
    }

    public static BooleanBag should(final NodeQueryBuilder builder) {
      return BooleanBag.should(builder.getNodeQuery());
    }

    public static BooleanBag should(final LuceneQueryBuilder... builders) {
      final Query[] queries = new Query[builders.length];
      for (int i = 0; i < builders.length; i++) {
        queries[i] = builders[i].getQuery();
      }
      return BooleanBag.should(queries);
    }

    public static BooleanBag should(final String term) {
      return BooleanBag.should(ntq(term).ntq);
    }

    public static BooleanBag should(final String ... terms) {
      final Query[] queries = new Query[terms.length];
      for (int i = 0; i < terms.length; i++) {
        queries[i] = ntq(terms[i]).ntq;
      }
      return BooleanBag.should(queries);
    }

    public static BooleanBag not(final NodeQueryBuilder builder) {
      return BooleanBag.not(builder.getNodeQuery());
    }

    public static BooleanBag not(final LuceneQueryBuilder... builders) {
      final Query[] queries = new Query[builders.length];
      for (int i = 0; i < builders.length; i++) {
        queries[i] = builders[i].getQuery();
      }
      return BooleanBag.not(queries);
    }

    public static BooleanBag not(final String term) {
      return BooleanBag.not(ntq(term).ntq);
    }

    public static BooleanBag not(final String ... terms) {
      final Query[] queries = new Query[terms.length];
      for (int i = 0; i < terms.length; i++) {
        queries[i] = ntq(terms[i]).ntq;
      }
      return BooleanBag.not(queries);
    }

  }

  public static class NodeBooleanQueryBuilder extends NodeQueryBuilder {

    protected NodeBooleanQuery nbq;

    private NodeBooleanQueryBuilder(final BooleanBag[] clauses) {
      nbq = new NodeBooleanQuery();
      for (final BooleanBag bag : clauses) {
        for (final NodeBooleanClause clause : bag.toNodeBooleanClauses()) {
          nbq.add(clause);
        }
      }
    }

    public static NodeBooleanQueryBuilder nbq(final BooleanBag ... clauses) {
      return new NodeBooleanQueryBuilder(clauses);
    }

    @Override
    public NodeQuery getNodeQuery() {
      return nbq;
    }

    @Override
    public NodeBooleanQueryBuilder bound(final int lowerBound, final int upperBound) {
      return (NodeBooleanQueryBuilder) super.bound(lowerBound, upperBound);
    }

    @Override
    public Query getLuceneProxyQuery() {
      return new LuceneProxyNodeQuery(nbq);
    }

  }

  public static class BooleanQueryBuilder extends LuceneQueryBuilder {

    protected BooleanQuery bq;

    private BooleanQueryBuilder(final BooleanBag[] clauses) {
      bq = new BooleanQuery();
      for (final BooleanBag bag : clauses) {
        for (final BooleanClause clause : bag.toBooleanClauses()) {
          bq.add(clause);
        }
      }
    }

    public static BooleanQueryBuilder bq(final BooleanBag... clauses) {
      return new BooleanQueryBuilder(clauses);
    }

    @Override
    public Query getQuery() {
      return bq;
    }

  }

  public static class TwigQueryBuilder extends NodeQueryBuilder {

    protected TwigQuery twq;

    private TwigQueryBuilder(final int rootLevel, final NodeQueryBuilder builder) {
      twq = new TwigQuery(rootLevel);
      twq.addRoot(builder.getNodeQuery());
    }

    private TwigQueryBuilder(final int rootLevel) {
      twq = new TwigQuery(rootLevel);
    }

    public static TwigQueryBuilder twq(final int rootLevel, final BooleanBag ... clauses) {
      return new TwigQueryBuilder(rootLevel, NodeBooleanQueryBuilder.nbq(clauses));
    }

    public static TwigQueryBuilder twq(final int rootLevel) {
      return new TwigQueryBuilder(rootLevel);
    }

    public TwigQueryBuilder root(final NodeQueryBuilder root) {
      if (!(twq.getRoot() instanceof EmptyRootQuery)) {
        throw new IllegalArgumentException("The root is already set: " + twq.getRoot());
      }
      twq.addRoot(root.getNodeQuery());
      return this;
    }

    public TwigQueryBuilder with(final NodeQueryBuilder nq) {
      twq.addChild(nq.getNodeQuery(), Occur.MUST);
      return this;
    }

    public TwigQueryBuilder without(final NodeQueryBuilder nq) {
      twq.addChild(nq.getNodeQuery(), Occur.MUST_NOT);
      return this;
    }

    public TwigQueryBuilder optional(final NodeQueryBuilder nq) {
      twq.addChild(nq.getNodeQuery(), Occur.SHOULD);
      return this;
    }

    public TwigQueryBuilder with(final TwigChildBuilder child) {
      twq.addChild(child.nbq, Occur.MUST);
      return this;
    }

    public TwigQueryBuilder without(final TwigChildBuilder child) {
      twq.addChild(child.nbq, Occur.MUST_NOT);
      return this;
    }

    public TwigQueryBuilder optional(final TwigChildBuilder child) {
      twq.addChild(child.nbq, Occur.SHOULD);
      return this;
    }

    public TwigQueryBuilder with(final TwigDescendantBuilder desc) {
      twq.addDescendant(desc.level, desc.nbq, Occur.MUST);
      return this;
    }

    public TwigQueryBuilder without(final TwigDescendantBuilder desc) {
      twq.addDescendant(desc.level, desc.nbq, Occur.MUST_NOT);
      return this;
    }

    public TwigQueryBuilder optional(final TwigDescendantBuilder desc) {
      twq.addDescendant(desc.level, desc.nbq, Occur.SHOULD);
      return this;
    }

    @Override
    public NodeQuery getNodeQuery() {
      return twq;
    }

    @Override
    public Query getLuceneProxyQuery() {
      return new LuceneProxyNodeQuery(twq);
    }

  }

  public static class TwigChildBuilder {

    NodeBooleanQuery nbq;

    private TwigChildBuilder(final BooleanBag[] clauses) {
      nbq = NodeBooleanQueryBuilder.nbq(clauses).nbq;
    }

    public static TwigChildBuilder child(final BooleanBag ... clauses) {
      return new TwigChildBuilder(clauses);
    }

  }

  public static class TwigDescendantBuilder {

    int level;
    NodeBooleanQuery nbq;

    private TwigDescendantBuilder(final int level, final BooleanBag[] clauses) {
      this.level = level;
      nbq = NodeBooleanQueryBuilder.nbq(clauses).nbq;
    }

    public static TwigDescendantBuilder desc(final int level, final BooleanBag ... clauses) {
      return new TwigDescendantBuilder(level, clauses);
    }

  }

  public static class TupleQueryBuilder extends NodeQueryBuilder {

    protected TupleQuery tq;

    private TupleQueryBuilder() {
      tq = new TupleQuery(true);
    }

    private TupleQueryBuilder(final int rootLevel) {
      tq = new TupleQuery(rootLevel, true);
    }

    public static TupleQueryBuilder tuple() {
      return new TupleQueryBuilder();
    }

    public static TupleQueryBuilder tuple(final int rootLevel) {
      return new TupleQueryBuilder(rootLevel);
    }

    public TupleQueryBuilder with(final NodeBooleanQueryBuilder ... clauses) {
      for (final NodeBooleanQueryBuilder clause : clauses) {
        tq.add(clause.nbq, Occur.MUST);
      }
      return this;
    }

    public TupleQueryBuilder without(final NodeBooleanQueryBuilder ... clauses) {
      for (final NodeBooleanQueryBuilder clause : clauses) {
        tq.add(clause.nbq, Occur.MUST_NOT);
      }
      return this;
    }

    public TupleQueryBuilder optional(final NodeBooleanQueryBuilder ... clauses) {
      for (final NodeBooleanQueryBuilder clause : clauses) {
        tq.add(clause.nbq, Occur.SHOULD);
      }
      return this;
    }

    @Override
    public NodeQuery getNodeQuery() {
      return tq;
    }

    @Override
    public Query getLuceneProxyQuery() {
      return new LuceneProxyNodeQuery(tq);
    }

  }

  /**
   * Assert if a scorer reaches end of stream, and check if sentinel values are
   * set.
   */
  public static void assertEndOfStream(final NodeScorer scorer) throws IOException {
    assertFalse(scorer.nextCandidateDocument());
    assertEquals(DocsAndNodesIterator.NO_MORE_DOC, scorer.doc());
    assertFalse(scorer.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, scorer.node());
  }

}
