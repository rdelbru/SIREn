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
package org.sindice.siren.search.node;

import java.io.IOException;
import java.util.Set;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;

/**
 * Class that act as a bridge between the SIREn query API and the Lucene query
 * API.
 */
public class LuceneProxyNodeQuery extends Query {

  private final NodeQuery nodeQuery;

  protected class LuceneProxyNodeWeight extends Weight {

    private final Weight weight;

    public LuceneProxyNodeWeight(final Weight weight) {
      this.weight = weight;
    }

    @Override
    public Explanation explain(final AtomicReaderContext context, final int doc)
    throws IOException {
      final LuceneProxyNodeScorer dScorer = (LuceneProxyNodeScorer) this.scorer(context, true, false, context.reader().getLiveDocs());

      if (dScorer != null) {
        if (dScorer.advance(doc) != DocIdSetIterator.NO_MORE_DOCS && dScorer.docID() == doc) {
          final Explanation exp = dScorer.getWeight().explain(context, doc);
          exp.setValue(dScorer.score());
          return exp;
        }
      }
      return new ComplexExplanation(false, 0.0f, "no matching term");
    }

    @Override
    public Query getQuery() {
      return nodeQuery;
    }

    @Override
    public float getValueForNormalization()
    throws IOException {
      return weight.getValueForNormalization();
    }

    @Override
    public void normalize(final float norm, final float topLevelBoost) {
      weight.normalize(norm, topLevelBoost);
    }

    @Override
    public Scorer scorer(final AtomicReaderContext context,
                         final boolean scoreDocsInOrder,
                         final boolean topScorer,
                         final Bits acceptDocs)
    throws IOException {
      final NodeScorer nodeScorer = (NodeScorer) weight.scorer(context,
        scoreDocsInOrder, topScorer, acceptDocs);
      return nodeScorer == null ? null // no match
                                : new LuceneProxyNodeScorer(nodeScorer);
    }

  }

  public LuceneProxyNodeQuery(final NodeQuery nq) {
    this.nodeQuery = nq;
  }

  @Override
  public Weight createWeight(final IndexSearcher searcher)
  throws IOException {
    return new LuceneProxyNodeWeight(nodeQuery.createWeight(searcher));
  }

  @Override
  public Query rewrite(final IndexReader reader)
  throws IOException {
    final Query rewroteQuery = nodeQuery.rewrite(reader);

    if (nodeQuery == rewroteQuery) {
      return this;
    }
    final LuceneProxyNodeQuery q = new LuceneProxyNodeQuery((NodeQuery) rewroteQuery);
    q.setBoost(nodeQuery.getBoost());
    return q;
  }

  @Override
  public void extractTerms(final Set<Term> terms) {
    nodeQuery.extractTerms(terms);
  }

  @Override
  public String toString(final String field) {
    final StringBuffer buffer = new StringBuffer();
    final boolean withParen = (this.getBoost() != 1.0) ||
                              (nodeQuery instanceof TwigQuery);
    if (withParen) {
      buffer.append('(');
    }
    buffer.append(nodeQuery.toString(field));
    if (withParen) {
      buffer.append(')').append(ToStringUtils.boost(this.getBoost()));
    }
    return buffer.toString();
  }

  /** Returns true iff <code>o</code> is equal to this. */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof LuceneProxyNodeQuery)) return false;
    final LuceneProxyNodeQuery other = (LuceneProxyNodeQuery) o;
    return (this.getBoost() == other.getBoost()) &&
           this.nodeQuery.equals(other.nodeQuery);
  }

  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost()) ^ nodeQuery.hashCode();
  }

  public NodeQuery getNodeQuery() {
    return nodeQuery;
  }

  @Override
  public void setBoost(final float b) {
    nodeQuery.setBoost(b);
  }

}
