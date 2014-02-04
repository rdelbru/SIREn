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
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermContext;
import org.apache.lucene.index.TermState;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.ComplexExplanation;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.Similarity.SloppySimScorer;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.ToStringUtils;
import org.sindice.siren.index.DocsNodesAndPositionsEnum;

/**
 * A {@link NodePrimitiveQuery} that matches nodes containing a particular
 * sequence of terms. A {@link NodePhraseQuery} is built for input like
 * <code>"new york"</code>.
 * <p>
 * This query may be combined with other queries with a {@link NodeBooleanQuery}
 * or a {@link TwigQuery}.
 * <p>
 * Code taken from {@link PhraseQuery} and adapted for the Siren use case.
 */
public class NodePhraseQuery extends NodePrimitiveQuery {

  private String          field;

  private final ArrayList<Term> terms       = new ArrayList<Term>(4);

  private final ArrayList<Integer> positions   = new ArrayList<Integer>(4);

  private int             maxPosition = 0;

  /** Constructs an empty phrase query. */
  public NodePhraseQuery() {}

  /**
   * Adds a term to the end of the query phrase. The relative position of the
   * term is the one immediately after the last term added.
   */
  public void add(final Term term) {
    int position = 0;

    if (positions.size() > 0) {
      position = (positions.get(positions.size() - 1)).intValue() + 1;
    }

    this.add(term, position);
  }

  /**
   * Adds a term to the end of the query phrase. The relative position of the
   * term within the phrase is specified explicitly. This allows e.g. phrases
   * with more than one term at the same position or phrases with gaps (e.g. in
   * connection with stopwords).
   *
   * @param term
   * @param position
   */
  public void add(final Term term, final int position) {
    if (terms.size() == 0) {
      field = term.field();
    }
    else if (term.field() != field) {
      throw new IllegalArgumentException(
        "All phrase terms must be in the same field: " + term);
    }

    terms.add(term);
    positions.add(Integer.valueOf(position));
    if (position > maxPosition) maxPosition = position;
  }

  /** Returns the set of terms in this phrase. */
  public Term[] getTerms() {
    return terms.toArray(new Term[0]);
  }

  /**
   * Returns the relative positions of terms in this phrase.
   */
  public int[] getPositions() {
    final int[] result = new int[positions.size()];
    for (int i = 0; i < positions.size(); i++) {
      result[i] = (positions.get(i)).intValue();
    }
    return result;
  }

  @Override
  public Query rewrite(final IndexReader reader) throws IOException {
    if (terms.isEmpty()) {
      final NodeBooleanQuery bq = new NodeBooleanQuery();
      bq.setBoost(this.getBoost());
      return bq;
    }
    else if (terms.size() == 1) {
      final NodeTermQuery tq = new NodeTermQuery(terms.get(0));
      tq.setBoost(this.getBoost());
      return tq;
    }
    else {
      return super.rewrite(reader);
    }
  }

  private class NodePhraseWeight extends Weight {

    private final Similarity similarity;
    private final Similarity.SimWeight stats;
    private transient TermContext states[];

    public NodePhraseWeight(final IndexSearcher searcher) throws IOException {
      this.similarity = searcher.getSimilarity();
      final IndexReaderContext context = searcher.getTopReaderContext();
      states = new TermContext[terms.size()];
      final TermStatistics termStats[] = new TermStatistics[terms.size()];
      for (int i = 0; i < terms.size(); i++) {
        final Term term = terms.get(i);
        states[i] = TermContext.build(context, term, true);
        termStats[i] = searcher.termStatistics(term, states[i]);
      }
      stats = similarity.computeWeight(NodePhraseQuery.this.getBoost(), searcher.collectionStatistics(field), termStats);
    }

    @Override
    public String toString() {
      return "weight(" + NodePhraseQuery.this + ")";
    }

    @Override
    public Query getQuery() {
      return NodePhraseQuery.this;
    }

    @Override
    public float getValueForNormalization() {
      return stats.getValueForNormalization();
    }

    @Override
    public void normalize(final float queryNorm, final float topLevelBoost) {
      stats.normalize(queryNorm, topLevelBoost);
    }

    @Override
    public Scorer scorer(final AtomicReaderContext context, final boolean scoreDocsInOrder,
                         final boolean topScorer, final Bits acceptDocs)
    throws IOException {
      assert !terms.isEmpty();
      final AtomicReader reader = context.reader();
      final Bits liveDocs = acceptDocs;
      final PostingsAndPosition[] postings = new PostingsAndPosition[terms.size()];

      final Terms fieldTerms = reader.terms(field);
      if (fieldTerms == null) {
        return null;
      }

      // Reuse single TermsEnum below:
      final TermsEnum te = fieldTerms.iterator(null);

      for (int i = 0; i < terms.size(); i++) {
        final Term t = terms.get(i);
        final TermState state = states[i].get(context.ord);
        if (state == null) { /* term doesnt exist in this segment */
          assert this.termNotInReader(reader, t): "no termstate found but term exists in reader";
          return null;
        }
        te.seekExact(t.bytes(), state);

        final DocsNodesAndPositionsEnum postingsEnum = NodePhraseQuery.this.getDocsNodesAndPositionsEnum(te.docsAndPositions(liveDocs, null));

        // PhraseQuery on a field that did not index positions (maybe not a siren field)
        if (postingsEnum == null) {
          assert te.seekExact(t.bytes(), false) : "termstate found but no term exists in reader";
          // term does exist, but has no positions
          throw new IllegalStateException("field \"" + t.field() + "\" was " +
          		"indexed without position data; cannot run NodePhraseQuery " +
          		"(term=" + t.text() + ")");
        }
        postings[i] = new PostingsAndPosition(postingsEnum, positions.get(i).intValue());
      }

      return new NodeExactPhraseScorer(this, postings,
        similarity.sloppySimScorer(stats, context),
        similarity.exactSimScorer(stats, context));
    }

    // TODO: Review this explanation for node match
    @Override
    public Explanation explain(final AtomicReaderContext context, final int doc)
    throws IOException {
      final NodeScorer scorer = (NodeScorer) this.scorer(context, true, false, context.reader().getLiveDocs());
      if (scorer != null) {
        if (scorer.skipToCandidate(doc) && scorer.doc() == doc) {
          final SloppySimScorer docScorer = similarity.sloppySimScorer(stats, context);
          final ComplexExplanation result = new ComplexExplanation();
          result.setDescription("weight("+this.getQuery()+" in "+doc+") [" + similarity.getClass().getSimpleName() + "], result of:");
          while (scorer.nextNode()) {
            final ComplexExplanation nodeMatch = new ComplexExplanation();
            nodeMatch.setDescription("in "+scorer.node()+"), result of:");
            final float freq = scorer.freqInNode();
            final Explanation scoreExplanation = docScorer.explain(doc, new Explanation(freq, "phraseFreq=" + freq));
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

    // only called from assert
    private boolean termNotInReader(final AtomicReader reader, final Term term)
    throws IOException {
      return reader.docFreq(term) == 0;
    }

  }

  @Override
  public Weight createWeight(final IndexSearcher searcher)
  throws IOException {
    return new NodePhraseWeight(searcher);
  }

  /**
   * @see org.apache.lucene.search.Query#extractTerms(java.util.Set)
   */
  @Override
  public void extractTerms(final Set<Term> queryTerms) {
    queryTerms.addAll(terms);
  }

  @Override
  public String toString(final String f) {
    final StringBuffer buffer = new StringBuffer();

    buffer.append("\"");
    final String[] pieces = new String[maxPosition + 1];
    for (int i = 0; i < terms.size(); i++) {
      final int pos = (positions.get(i)).intValue();
      String s = pieces[pos];
      if (s == null) {
        s = (terms.get(i)).text();
      }
      else {
        s = s + "|" + (terms.get(i)).text();
      }
      pieces[pos] = s;
    }
    for (int i = 0; i < pieces.length; i++) {
      if (i > 0) {
        buffer.append(' ');
      }
      final String s = pieces[i];
      if (s == null) {
        buffer.append('?');
      }
      else {
        this.escapeDoubleQuote(buffer, s);
      }
    }
    buffer.append("\"");

    buffer.append(ToStringUtils.boost(this.getBoost()));

    return this.wrapToStringWithDatatype(buffer).toString();
  }

  /**
   * Prefix with a backslash any unescaped double quote
   */
  private void escapeDoubleQuote(final StringBuffer buffer, final String s) {
    int index = 0;
    int prevIndex = 0;

    while ((index = s.indexOf('"', prevIndex)) != -1) {
      buffer.append(s, prevIndex, index);
      if (buffer.charAt(buffer.length() - 1) != '\\') {
        buffer.append('\\');
      }
      buffer.append('"');
      prevIndex = index + 1;
    }
    buffer.append(s, prevIndex, s.length());
  }

  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof NodePhraseQuery)) {
      return false;
    }
    final NodePhraseQuery other = (NodePhraseQuery) o;
    return (this.getBoost() == other.getBoost()) &&
      this.terms.equals(other.terms) &&
      this.positions.equals(other.positions) &&
      this.levelConstraint == other.levelConstraint &&
      this.lowerBound == other.lowerBound &&
      this.upperBound == other.upperBound &&
      StringUtils.equals(this.datatype, other.datatype);
  }

  @Override
  public int hashCode() {
    return Float.floatToIntBits(this.getBoost())
      ^ terms.hashCode()
      ^ positions.hashCode()
      ^ levelConstraint
      ^ upperBound
      ^ lowerBound;
  }

  static class PostingsAndPosition {

    final DocsNodesAndPositionsEnum postings;
    final int position;

    public PostingsAndPosition(final DocsNodesAndPositionsEnum postings, final int position) {
      this.postings = postings;
      this.position = position;
    }

  }

}
