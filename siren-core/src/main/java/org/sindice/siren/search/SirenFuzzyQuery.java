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
/**
 * @project siren-core
 * @author Campinas Stephane [ 21 Sep 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FilteredTermEnum;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.FuzzyTermEnum;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.search.SingleTermEnum;
import org.apache.lucene.util.ToStringUtils;

/**
 * Implements the fuzzy search query. The similarity measurement
 * is based on the Levenshtein (edit distance) algorithm.
 *
 * <p><em>Warning:</em> this query is not very scalable with its default prefix
 * length of 0 - in this case, *every* term will be enumerated and
 * cause an edit score calculation.
 *
 * <p>This query uses {@link MultiTermQuery.TopTermsScoringBooleanQueryRewrite}
 * as default. So terms will be collected and scored according to their
 * edit distance. Only the top terms are used for building the {@link BooleanQuery}.
 * It is not recommended to change the rewrite mode for fuzzy queries.
 *
 * <p> Code taken from {@link FuzzyQuery} and adapted for SIREn.
 */
public class SirenFuzzyQuery extends SirenMultiTermQuery {

  public final static float   defaultMinSimilarity  = 0.5f;
  public final static int     defaultPrefixLength   = 0;
  public final static int     defaultMaxExpansions  = Integer.MAX_VALUE;

  private final float               minimumSimilarity;
  private final int                 prefixLength;
  private boolean             termLongEnough        = false;

  protected Term              term;

  /**
   * Create a new SirenFuzzyQuery that will match terms with a similarity
   * of at least <code>minimumSimilarity</code> to <code>term</code>.
   * If a <code>prefixLength</code> &gt; 0 is specified, a common prefix
   * of that length is also required.
   *
   * @param term the term to search for
   * @param minimumSimilarity a value between 0 and 1 to set the required similarity
   *  between the query term and the matching terms. For example, for a
   *  <code>minimumSimilarity</code> of <code>0.5</code> a term of the same length
   *  as the query term is considered similar to the query term if the edit distance
   *  between both terms is less than <code>length(term)*0.5</code>
   * @param prefixLength length of common (non-fuzzy) prefix
   * @param maxExpansions the maximum number of terms to match. If this number is
   *  greater than {@link BooleanQuery#getMaxClauseCount} when the query is rewritten,
   *  then the maxClauseCount will be used instead.
   * @throws IllegalArgumentException if minimumSimilarity is &gt;= 1 or &lt; 0
   * or if prefixLength &lt; 0
   */
  public SirenFuzzyQuery(final Term term, final float minimumSimilarity, final int prefixLength, final int maxExpansions) {
    this.term = term;

    if (minimumSimilarity >= 1.0f)
      throw new IllegalArgumentException("minimumSimilarity >= 1");
    else if (minimumSimilarity < 0.0f)
      throw new IllegalArgumentException("minimumSimilarity < 0");
    if (prefixLength < 0)
      throw new IllegalArgumentException("prefixLength < 0");
    if (maxExpansions < 0)
      throw new IllegalArgumentException("maxExpansions < 0");

    this.setRewriteMethod(new SirenMultiTermQuery.TopTermsScoringSirenBooleanQueryRewrite(maxExpansions));

    if (term.text().length() > 1.0f / (1.0f - minimumSimilarity)) {
      this.termLongEnough = true;
    }

    this.minimumSimilarity = minimumSimilarity;
    this.prefixLength = prefixLength;
  }

  /**
   * Calls {@link #SirenFuzzyQuery(Term, float) SirenFuzzyQuery(term, minimumSimilarity, prefixLength, Integer.MAX_VALUE)}.
   */
  public SirenFuzzyQuery(final Term term, final float minimumSimilarity, final int prefixLength) {
    this(term, minimumSimilarity, prefixLength, defaultMaxExpansions);
  }

  /**
   * Calls {@link #SirenFuzzyQuery(Term, float) SirenFuzzyQuery(term, minimumSimilarity, 0, Integer.MAX_VALUE)}.
   */
  public SirenFuzzyQuery(final Term term, final float minimumSimilarity) {
    this(term, minimumSimilarity, defaultPrefixLength, defaultMaxExpansions);
  }

  /**
   * Calls {@link #SirenFuzzyQuery(Term, float) SirenFuzzyQuery(term, 0.5f, 0, Integer.MAX_VALUE)}.
   */
  public SirenFuzzyQuery(final Term term) {
    this(term, defaultMinSimilarity, defaultPrefixLength, defaultMaxExpansions);
  }

  /**
   * Returns the minimum similarity that is required for this query to match.
   * @return float value between 0.0 and 1.0
   */
  public float getMinSimilarity() {
    return minimumSimilarity;
  }

  /**
   * Returns the non-fuzzy prefix length. This is the number of characters at the start
   * of a term that must be identical (not fuzzy) to the query term if the query
   * is to match that term.
   */
  public int getPrefixLength() {
    return prefixLength;
  }

  @Override
  protected FilteredTermEnum getEnum(final IndexReader reader) throws IOException {
    if (!termLongEnough) {  // can only match if it's exact
      return new SingleTermEnum(reader, term);
    }
    return new FuzzyTermEnum(reader, this.getTerm(), minimumSimilarity, prefixLength);
  }

  /**
   * Returns the pattern term.
   */
  public Term getTerm() {
    return term;
  }

  @Override
  public String toString(final String field) {
    final StringBuilder buffer = new StringBuilder();
    if (!term.field().equals(field)) {
        buffer.append(term.field());
        buffer.append(":");
    }
    buffer.append(term.text());
    buffer.append('~');
    buffer.append(Float.toString(minimumSimilarity));
    buffer.append(ToStringUtils.boost(this.getBoost()));
    return buffer.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Float.floatToIntBits(minimumSimilarity);
    result = prime * result + prefixLength;
    result = prime * result + ((term == null) ? 0 : term.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final SirenFuzzyQuery other = (SirenFuzzyQuery) obj;
    if (Float.floatToIntBits(minimumSimilarity) != Float
        .floatToIntBits(other.minimumSimilarity))
      return false;
    if (prefixLength != other.prefixLength)
      return false;
    if (term == null) {
      if (other.term != null)
        return false;
    } else if (!term.equals(other.term))
      return false;
    return true;
  }

}
