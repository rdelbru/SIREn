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

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.util.Bits;

/**
 * Expert: A {@link NodeQuery} that filters nodes and return their ancestors.
 *
 * <p>
 *
 * Internal class that is created by the {@link TwigQuery} during query
 * rewriting.
 */
class AncestorFilterQuery extends NodeQuery {

  private final NodeQuery q;
  private final int ancestorLevel;

  /**
   * Expert: constructs a AncestorFilterQuery that will use the
   * provided {@link NodeQuery} and that will filter matching nodes to return
   * their ancestor node based on the given ancestor level. */
  public AncestorFilterQuery(final NodeQuery q, final int ancestorLevel) {
    this.q = q;
    this.ancestorLevel = ancestorLevel;
    this.setLevelConstraint(ancestorLevel);
  }

  public NodeQuery getQuery() {
    return q;
  }

  @Override
  public Weight createWeight(final IndexSearcher searcher) throws IOException {
    return new AncestorFilterWeight(searcher);
  }

  @Override
  public String toString(final String field) {
    return q.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof AncestorFilterQuery)) {
      return false;
    }
    final AncestorFilterQuery other = (AncestorFilterQuery) o;
    return (this.getBoost() == other.getBoost()) &&
           this.q.equals(other.q) &&
           this.lowerBound == other.lowerBound &&
           this.upperBound == other.upperBound &&
           this.levelConstraint == other.levelConstraint;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Float.floatToIntBits(this.getBoost());
    result = prime * result + q.hashCode();
    result = prime * result + lowerBound;
    result = prime * result + upperBound;
    result = prime * result + levelConstraint;
    return result;
  }

  protected class AncestorFilterWeight extends Weight {

    final Weight weight;

    public AncestorFilterWeight(final IndexSearcher searcher) throws IOException {
      this.weight = q.createWeight(searcher);
    }

    @Override
    public String toString() {
      return "weight(" + AncestorFilterQuery.this + ")";
    }

    @Override
    public Query getQuery() {
      return AncestorFilterQuery.this;
    }

    @Override
    public float getValueForNormalization() throws IOException {
      return weight.getValueForNormalization();
    }

    @Override
    public void normalize(final float queryNorm, final float topLevelBoost) {
      weight.normalize(queryNorm, topLevelBoost);
    }

    @Override
    public Scorer scorer(final AtomicReaderContext context,
                         final boolean scoreDocsInOrder,
                         final boolean topScorer, final Bits acceptDocs)
    throws IOException {
      final NodeScorer scorer = (NodeScorer) weight.scorer(context,
        scoreDocsInOrder, topScorer, acceptDocs);
      if (scorer == null) {
        return null;
      }
      return new AncestorFilterScorer(scorer, ancestorLevel);
    }

    @Override
    public Explanation explain(final AtomicReaderContext context, final int doc)
    throws IOException {
      return weight.explain(context, doc);
    }

  }

}
