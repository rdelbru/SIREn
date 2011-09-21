/**
 * Copyright 2011, Campinas Stephane
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren-core_rdelbru
 * @author Campinas Stephane [ 21 Sep 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FilteredTermEnum;
import org.apache.lucene.search.FuzzyTermEnum;
import org.apache.lucene.search.SingleTermEnum;
import org.apache.lucene.util.ToStringUtils;

/**
 * 
 */
public class SirenFuzzyQuery extends SirenMultiTermQuery {

  public final static float   defaultMinSimilarity  = 0.5f;
  public final static int     defaultPrefixLength   = 0;
  public final static int     defaultMaxExpansions  = Integer.MAX_VALUE;
  
  private float               minimumSimilarity;
  private int                 prefixLength;
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
  public SirenFuzzyQuery(Term term, float minimumSimilarity, int prefixLength, int maxExpansions) {
    this.term = term;
    
    if (minimumSimilarity >= 1.0f)
      throw new IllegalArgumentException("minimumSimilarity >= 1");
    else if (minimumSimilarity < 0.0f)
      throw new IllegalArgumentException("minimumSimilarity < 0");
    if (prefixLength < 0)
      throw new IllegalArgumentException("prefixLength < 0");
    if (maxExpansions < 0)
      throw new IllegalArgumentException("maxExpansions < 0");
    
    setRewriteMethod(new SirenMultiTermQuery.TopTermsScoringSirenBooleanQueryRewrite(maxExpansions));
    
    if (term.text().length() > 1.0f / (1.0f - minimumSimilarity)) {
      this.termLongEnough = true;
    }
    
    this.minimumSimilarity = minimumSimilarity;
    this.prefixLength = prefixLength;
  }
  
  /**
   * Calls {@link #SirenFuzzyQuery(Term, float) SirenFuzzyQuery(term, minimumSimilarity, prefixLength, Integer.MAX_VALUE)}.
   */
  public SirenFuzzyQuery(Term term, float minimumSimilarity, int prefixLength) {
    this(term, minimumSimilarity, prefixLength, defaultMaxExpansions);
  }
  
  /**
   * Calls {@link #SirenFuzzyQuery(Term, float) SirenFuzzyQuery(term, minimumSimilarity, 0, Integer.MAX_VALUE)}.
   */
  public SirenFuzzyQuery(Term term, float minimumSimilarity) {
    this(term, minimumSimilarity, defaultPrefixLength, defaultMaxExpansions);
  }

  /**
   * Calls {@link #SirenFuzzyQuery(Term, float) SirenFuzzyQuery(term, 0.5f, 0, Integer.MAX_VALUE)}.
   */
  public SirenFuzzyQuery(Term term) {
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
  protected FilteredTermEnum getEnum(IndexReader reader) throws IOException {
    if (!termLongEnough) {  // can only match if it's exact
      return new SingleTermEnum(reader, term);
    }
    return new FuzzyTermEnum(reader, getTerm(), minimumSimilarity, prefixLength);
  }
  
  /**
   * Returns the pattern term.
   */
  public Term getTerm() {
    return term;
  }
    
  @Override
  public String toString(String field) {
    final StringBuilder buffer = new StringBuilder();
    if (!term.field().equals(field)) {
        buffer.append(term.field());
        buffer.append(":");
    }
    buffer.append(term.text());
    buffer.append('~');
    buffer.append(Float.toString(minimumSimilarity));
    buffer.append(ToStringUtils.boost(getBoost()));
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
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    SirenFuzzyQuery other = (SirenFuzzyQuery) obj;
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
