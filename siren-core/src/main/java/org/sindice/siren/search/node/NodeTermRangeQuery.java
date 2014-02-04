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

import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TermRangeTermsEnum;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.ToStringUtils;

/**
 * A {@link NodePrimitiveQuery} that matches documents within an range of terms.
 *
 * <p>This query matches the documents looking for terms that fall into the
 * supplied range according to {@link
 * Byte#compareTo(Byte)}. It is not intended
 * for numerical ranges; use {@link NumericRangeQuery} instead.
 *
 * <p>This query uses the {@link
 * MultiNodeTermQuery#CONSTANT_SCORE_AUTO_REWRITE_DEFAULT}
 * rewrite method.
 *
 * <p> Code taken from {@link TermRangeQuery} and adapted for SIREn.
 */
public class NodeTermRangeQuery extends MultiNodeTermQuery {

  private final BytesRef lowerTerm;
  private final BytesRef upperTerm;
  private final boolean includeLower;
  private final boolean includeUpper;


  /**
   * Constructs a query selecting all terms greater/equal than <code>lowerTerm</code>
   * but less/equal than <code>upperTerm</code>.
   *
   * <p>
   * If an endpoint is null, it is said
   * to be "open". Either or both endpoints may be open.  Open endpoints may not
   * be exclusive (you can't select all but the first or last term without
   * explicitly specifying the term to exclude.)
   *
   * @param field The field that holds both lower and upper terms.
   * @param lowerTerm
   *          The term text at the lower end of the range
   * @param upperTerm
   *          The term text at the upper end of the range
   * @param includeLower
   *          If true, the <code>lowerTerm</code> is
   *          included in the range.
   * @param includeUpper
   *          If true, the <code>upperTerm</code> is
   *          included in the range.
   */
  public NodeTermRangeQuery(final String field, final BytesRef lowerTerm,
                             final BytesRef upperTerm,
                             final boolean includeLower,
                             final boolean includeUpper) {
    super(field);
    this.lowerTerm = lowerTerm;
    this.upperTerm = upperTerm;
    this.includeLower = includeLower;
    this.includeUpper = includeUpper;
  }

  /**
   * Factory that creates a new TermRangeQuery using Strings for term text.
   */
  public static NodeTermRangeQuery newStringRange(final String field,
                                                   final String lowerTerm,
                                                   final String upperTerm,
                                                   final boolean includeLower,
                                                   final boolean includeUpper) {
    final BytesRef lower = lowerTerm == null ? null : new BytesRef(lowerTerm);
    final BytesRef upper = upperTerm == null ? null : new BytesRef(upperTerm);
    return new NodeTermRangeQuery(field, lower, upper, includeLower, includeUpper);
  }

  /** Returns the lower value of this range query */
  public BytesRef getLowerTerm() { return lowerTerm; }

  /** Returns the upper value of this range query */
  public BytesRef getUpperTerm() { return upperTerm; }

  /** Returns <code>true</code> if the lower endpoint is inclusive */
  public boolean includesLower() { return includeLower; }

  /** Returns <code>true</code> if the upper endpoint is inclusive */
  public boolean includesUpper() { return includeUpper; }

  @Override
  protected TermsEnum getTermsEnum(final Terms terms, final AttributeSource atts) throws IOException {
    if (lowerTerm != null && upperTerm != null && lowerTerm.compareTo(upperTerm) > 0) {
      return TermsEnum.EMPTY;
    }

    final TermsEnum tenum = terms.iterator(null);

    if ((lowerTerm == null || (includeLower && lowerTerm.length == 0)) && upperTerm == null) {
      return tenum;
    }
    return new TermRangeTermsEnum(tenum,
        lowerTerm, upperTerm, includeLower, includeUpper);
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String field) {
      final StringBuffer buffer = new StringBuffer();
      buffer.append(includeLower ? '[' : '{');
      // TODO: all these toStrings for queries should just output the bytes, it might not be UTF-8!
      buffer.append(lowerTerm != null ? ("*".equals(lowerTerm.utf8ToString()) ? "\\*" : lowerTerm.utf8ToString())  : "*");
      buffer.append(" TO ");
      buffer.append(upperTerm != null ? ("*".equals(upperTerm.utf8ToString()) ? "\\*" : upperTerm.utf8ToString()) : "*");
      buffer.append(includeUpper ? ']' : '}');
      buffer.append(ToStringUtils.boost(this.getBoost()));
      return this.wrapToStringWithDatatype(buffer).toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (includeLower ? 1231 : 1237);
    result = prime * result + (includeUpper ? 1231 : 1237);
    result = prime * result + ((lowerTerm == null) ? 0 : lowerTerm.hashCode());
    result = prime * result + ((upperTerm == null) ? 0 : upperTerm.hashCode());
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
    final NodeTermRangeQuery other = (NodeTermRangeQuery) obj;
    if (includeLower != other.includeLower)
      return false;
    if (includeUpper != other.includeUpper)
      return false;
    if (lowerTerm == null) {
      if (other.lowerTerm != null)
        return false;
    } else if (!lowerTerm.equals(other.lowerTerm))
      return false;
    if (upperTerm == null) {
      if (other.upperTerm != null)
        return false;
    } else if (!upperTerm.equals(other.upperTerm))
      return false;
    return true;
  }

}
