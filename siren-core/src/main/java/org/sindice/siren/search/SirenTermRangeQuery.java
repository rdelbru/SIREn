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
 * @author Renaud Delbru [ 29 Sep 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.search;

import java.io.IOException;
import java.text.Collator;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FilteredTermEnum;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TermRangeTermEnum;
import org.apache.lucene.util.ToStringUtils;

/**
 * A Query that matches documents within an range of terms.
 *
 * <p>This query matches the documents looking for terms that fall into the
 * supplied range according to {@link
 * String#compareTo(String)}, unless a <code>Collator</code> is provided. It is not intended
 * for numerical ranges; use {@link SirenNumericRangeQuery} instead.
 *
 * <p>This query uses the {@link
 * SirenMultiTermQuery#CONSTANT_SCORE_AUTO_REWRITE_DEFAULT}
 * rewrite method.
 *
 * <p> Code taken from {@link TermRangeQuery} and adapted for SIREn.
 */
public class SirenTermRangeQuery extends SirenMultiTermQuery {

  private final String lowerTerm;
  private final String upperTerm;
  private final Collator collator;
  private final String field;
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
  public SirenTermRangeQuery(final String field, final String lowerTerm, final String upperTerm,
                             final boolean includeLower, final boolean includeUpper) {
    this(field, lowerTerm, upperTerm, includeLower, includeUpper, null);
  }

  /** Constructs a query selecting all terms greater/equal than
   * <code>lowerTerm</code> but less/equal than <code>upperTerm</code>.
   * <p>
   * If an endpoint is null, it is said
   * to be "open". Either or both endpoints may be open.  Open endpoints may not
   * be exclusive (you can't select all but the first or last term without
   * explicitly specifying the term to exclude.)
   * <p>
   * If <code>collator</code> is not null, it will be used to decide whether
   * index terms are within the given range, rather than using the Unicode code
   * point order in which index terms are stored.
   * <p>
   * <strong>WARNING:</strong> Using this constructor and supplying a non-null
   * value in the <code>collator</code> parameter will cause every single
   * index Term in the Field referenced by lowerTerm and/or upperTerm to be
   * examined.  Depending on the number of index Terms in this Field, the
   * operation could be very slow.
   *
   * @param lowerTerm The Term text at the lower end of the range
   * @param upperTerm The Term text at the upper end of the range
   * @param includeLower
   *          If true, the <code>lowerTerm</code> is
   *          included in the range.
   * @param includeUpper
   *          If true, the <code>upperTerm</code> is
   *          included in the range.
   * @param collator The collator to use to collate index Terms, to determine
   *  their membership in the range bounded by <code>lowerTerm</code> and
   *  <code>upperTerm</code>.
   */
  public SirenTermRangeQuery(final String field, final String lowerTerm, final String upperTerm,
                             final boolean includeLower, final boolean includeUpper,
                             final Collator collator) {
    this.field = field;
    this.lowerTerm = lowerTerm;
    this.upperTerm = upperTerm;
    this.includeLower = includeLower;
    this.includeUpper = includeUpper;
    this.collator = collator;
  }

  /** Returns the field name for this query */
  public String getField() { return field; }

  /** Returns the lower value of this range query */
  public String getLowerTerm() { return lowerTerm; }

  /** Returns the upper value of this range query */
  public String getUpperTerm() { return upperTerm; }

  /** Returns <code>true</code> if the lower endpoint is inclusive */
  public boolean includesLower() { return includeLower; }

  /** Returns <code>true</code> if the upper endpoint is inclusive */
  public boolean includesUpper() { return includeUpper; }

  /** Returns the collator used to determine range inclusion, if any. */
  public Collator getCollator() { return collator; }

  @Override
  protected FilteredTermEnum getEnum(final IndexReader reader) throws IOException {
    return new TermRangeTermEnum(reader, field, lowerTerm,
        upperTerm, includeLower, includeUpper, collator);
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String field) {
      final StringBuilder buffer = new StringBuilder();
      if (!this.getField().equals(field)) {
          buffer.append(this.getField());
          buffer.append(":");
      }
      buffer.append(includeLower ? '[' : '{');
      buffer.append(lowerTerm != null ? lowerTerm : "*");
      buffer.append(" TO ");
      buffer.append(upperTerm != null ? upperTerm : "*");
      buffer.append(includeUpper ? ']' : '}');
      buffer.append(ToStringUtils.boost(this.getBoost()));
      return buffer.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((collator == null) ? 0 : collator.hashCode());
    result = prime * result + ((field == null) ? 0 : field.hashCode());
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
    final SirenTermRangeQuery other = (SirenTermRangeQuery) obj;
    if (collator == null) {
      if (other.collator != null)
        return false;
    } else if (!collator.equals(other.collator))
      return false;
    if (field == null) {
      if (other.field != null)
        return false;
    } else if (!field.equals(other.field))
      return false;
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
