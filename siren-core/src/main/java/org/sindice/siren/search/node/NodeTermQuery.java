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

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DocsAndPositionsEnum;
import org.apache.lucene.index.IndexReaderContext;
import org.apache.lucene.index.ReaderUtil;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermContext;
import org.apache.lucene.index.TermState;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.Similarity.ExactSimScorer;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;
import org.sindice.siren.index.DocsNodesAndPositionsEnum;

/**
 * A {@link NodePrimitiveQuery} that matches nodes containing a term.
 *
 * <p>
 *
 * Provides an interface to iterate over the candidate documents and nodes
 * containing the term. This may be combined with other terms with a
 * {@link NodeBooleanQuery}.
 */
public class NodeTermQuery extends NodePrimitiveQuery {

  private final Term term;
  private final int docFreq;
  private final TermContext perReaderTermState;

  protected class NodeTermWeight extends Weight {

    private final Similarity similarity;
    private final Similarity.SimWeight stats;
    private final TermContext termStates;

    public NodeTermWeight(final IndexSearcher searcher, final TermContext termStates)
    throws IOException {
      assert termStates != null : "TermContext must not be null";
      this.termStates = termStates;
      this.similarity = searcher.getSimilarity();
      this.stats = similarity.computeWeight(
        NodeTermQuery.this.getBoost(),
        searcher.collectionStatistics(term.field()),
        searcher.termStatistics(term, termStates));
    }

    @Override
    public String toString() {
      return "weight(" + NodeTermQuery.this + ")";
    }

    @Override
    public Query getQuery() {
      return NodeTermQuery.this;
    }

    @Override
    public float getValueForNormalization() throws IOException {
      return stats.getValueForNormalization();
    }

    @Override
    public void normalize(final float queryNorm, final float topLevelBoost) {
      stats.normalize(queryNorm, topLevelBoost);
    }

    @Override
    public Scorer scorer(final AtomicReaderContext context,
                         final boolean scoreDocsInOrder,
                         final boolean topScorer, final Bits acceptDocs)
    throws IOException {
      assert termStates.topReaderContext == ReaderUtil.getTopLevelContext(context) : "The top-reader used to create Weight (" + termStates.topReaderContext + ") is not the same as the current reader's top-reader (" + ReaderUtil.getTopLevelContext(context);
      final TermsEnum termsEnum = this.getTermsEnum(context);
      if (termsEnum == null) {
        return null;
      }

      final DocsAndPositionsEnum docsEnum = termsEnum.docsAndPositions(acceptDocs, null);
      final DocsNodesAndPositionsEnum sirenDocsEnum = NodeTermQuery.this.getDocsNodesAndPositionsEnum(docsEnum);
      return new NodeTermScorer(this, sirenDocsEnum, this.createDocScorer(context));
    }

    /**
     * Creates an {@link ExactDocScorer} for this {@link TermWeight}
     **/
    ExactSimScorer createDocScorer(final AtomicReaderContext context)
    throws IOException {
      return similarity.exactSimScorer(stats, context);
    }

    /**
     * Returns a {@link TermsEnum} positioned at this weights Term or null if
     * the term does not exist in the given context
     */
    TermsEnum getTermsEnum(final AtomicReaderContext context) throws IOException {
      final TermState state = termStates.get(context.ord);
      if (state == null) { // term is not present in that reader
        assert this.termNotInReader(context.reader(), term) : "no termstate found but term exists in reader term=" + term;
        return null;
      }
      //System.out.println("LD=" + reader.getLiveDocs() + " set?=" + (reader.getLiveDocs() != null ? reader.getLiveDocs().get(0) : "null"));
      final TermsEnum termsEnum = context.reader().terms(term.field()).iterator(null);
      termsEnum.seekExact(term.bytes(), state);
      return termsEnum;
    }

    private boolean termNotInReader(final AtomicReader reader, final Term term) throws IOException {
      // only called from assert
      //System.out.println("TQ.termNotInReader reader=" + reader + " term=" + field + ":" + bytes.utf8ToString());
      return reader.docFreq(term) == 0;
    }

    @Override
    public Explanation explain(final AtomicReaderContext context, final int doc) throws IOException {
      final NodeScorer scorer = (NodeScorer) this.scorer(context, true, false, context.reader().getLiveDocs());

      if (scorer != null) {
        if (scorer.skipToCandidate(doc) && scorer.doc() == doc) {
          final ExactSimScorer docScorer = similarity.exactSimScorer(stats, context);
          final ComplexExplanation result = new ComplexExplanation();
          result.setDescription("weight("+this.getQuery()+" in "+doc+") [" + similarity.getClass().getSimpleName() + "], result of:");
          while (scorer.nextNode()) {
            final ComplexExplanation nodeMatch = new ComplexExplanation();
            nodeMatch.setDescription("in "+scorer.node()+"), result of:");
            final float freq = scorer.freqInNode();
            final Explanation scoreExplanation = docScorer.explain(doc, new Explanation(freq, "termFreq=" + freq));
            nodeMatch.setValue(scoreExplanation.getValue());
            nodeMatch.setMatch(true);
            nodeMatch.addDetail(scoreExplanation);
            result.addDetail(nodeMatch);
          }
          result.setMatch(true);
          return result;
        }
      }
      return new ComplexExplanation(false, 0.0f, "no matching term");
    }

  }

  /** Constructs a query for the term <code>t</code>. */
  public NodeTermQuery(final Term t) {
    this(t, -1);
  }

  /** Expert: constructs a TermQuery that will use the
   *  provided docFreq instead of looking up the docFreq
   *  against the searcher. */
  public NodeTermQuery(final Term t, final int docFreq) {
    term = t;
    this.docFreq = docFreq;
    perReaderTermState = null;
  }

  /** Expert: constructs a TermQuery that will use the
   *  provided docFreq instead of looking up the docFreq
   *  against the searcher. */
  public NodeTermQuery(final Term t, final TermContext states) {
    assert states != null;
    term = t;
    docFreq = states.docFreq();
    perReaderTermState = states;
  }

  /** Returns the term of this query. */
  public Term getTerm() {
    return term;
  }

  @Override
  public Weight createWeight(final IndexSearcher searcher) throws IOException {
    final IndexReaderContext context = searcher.getTopReaderContext();
    final TermContext termState;
    if (perReaderTermState == null || perReaderTermState.topReaderContext != context) {
      // make TermQuery single-pass if we don't have a PRTS or if the context differs!
      termState = TermContext.build(context, term, true); // cache term lookups!
    } else {
     // PRTS was pre-build for this IS
     termState = this.perReaderTermState;
    }

    // we must not ignore the given docFreq - if set use the given value (lie)
    if (docFreq != -1)
      termState.setDocFreq(docFreq);

    return new NodeTermWeight(searcher, termState);
  }

  @Override
  public void extractTerms(final Set<Term> terms) {
    terms.add(this.getTerm());
  }

  /**
   * Prints a user-readable version of this query.
   * <p>
   * The term is wrapped in simple quotes, so that any special characters it
   * may contains are disabled. See ProtectedQueryNode in siren-qparser.
   */
  @Override
  public String toString(final String field) {
    final StringBuffer buffer = new StringBuffer();
    final CharSequence text = term.text();
    if (text.length() != 0) {
      buffer.append("'").append(text).append("'");
    }
    buffer.append(ToStringUtils.boost(this.getBoost()));
    return this.wrapToStringWithDatatype(buffer).toString();
  }

  /** Returns true iff <code>o</code> is equal to this. */
  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof NodeTermQuery)) return false;
    final NodeTermQuery other = (NodeTermQuery) o;
    return (this.getBoost() == other.getBoost()) &&
            this.term.equals(other.term) &&
            this.levelConstraint == other.levelConstraint &&
            this.lowerBound == other.lowerBound &&
            this.upperBound == other.upperBound &&
            StringUtils.equals(this.datatype, other.datatype);
  }

  /** Returns a hash code value for this object. */
  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost())
      ^ term.hashCode()
      ^ levelConstraint
      ^ upperBound
      ^ lowerBound;
  }

}
