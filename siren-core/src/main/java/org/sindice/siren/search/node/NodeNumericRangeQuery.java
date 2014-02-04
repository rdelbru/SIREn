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
import java.util.Comparator;
import java.util.LinkedList;

import org.apache.lucene.analysis.NumericTokenStream;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.index.FilteredTermsEnum;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;
import org.apache.lucene.util.ToStringUtils;

/**
 * A {@link NodePrimitiveQuery} that matches numeric values within a
 * specified range.
 *
 * <p>
 *
 * To use this, you must first index the
 * numeric values using {@link NumericField} (expert: {@link
 * NumericTokenStream}).  If your terms are instead textual,
 * you should use {@link NodeTermRangeQuery}.</p>
 *
 * <p>You create a new {@link NodeNumericRangeQuery} with the static
 * factory methods, eg:
 *
 * <pre>
 * Query q = NodeNumericRangeQuery.newFloatRange("weight", 0.03f, 0.10f, true, true);
 * </pre>
 *
 * matches all documents whose float valued "weight" field
 * ranges from 0.03 to 0.10, inclusive.
 *
 * <p>The performance of {@link NodeNumericRangeQuery} is much better
 * than the corresponding {@link NodeTermRangeQuery} because the
 * number of terms that must be searched is usually far
 * fewer, thanks to trie indexing, described below.</p>
 *
 * <p>You can optionally specify a <a
 * href="#precisionStepDesc"><code>precisionStep</code></a>
 * when creating this query.  This is necessary if you've
 * changed this configuration from its default (4) during
 * indexing.  Lower values consume more disk space but speed
 * up searching.  Suitable values are between <b>1</b> and
 * <b>8</b>. A good starting point to test is <b>4</b>,
 * which is the default value for all <code>Numeric*</code>
 * classes.  See <a href="#precisionStepDesc">below</a> for
 * details.
 *
 * <p>This query defaults to {@linkplain
 * MultiNodeTermQuery#CONSTANT_SCORE_AUTO_REWRITE_DEFAULT} and never relies on
 * uses {@linkplain MultiNodeTermQuery#CONSTANT_SCORE_FILTER_REWRITE}. Good
 * performance is expected for
 * 32 bit (int/float) ranges with precisionStep &le;8 and 64
 * bit (long/double) ranges with precisionStep &le;6.
 * In the other cases, bad performance has to be expected as the
 * number of terms is likely to be high.
 *
 * <p> See {@link NumericRangeQuery} for more information on how it works.
 *
 * <p> Code taken from {@link NumericRangeQuery} and adapted for SIREn.
 **/
public final class NodeNumericRangeQuery<T extends Number> extends MultiNodeTermQuery {

  private final String pstepDatatype;

  private NodeNumericRangeQuery(final String field, final int precisionStep,
                                 final NumericType dataType,
                                 final T min, final T max,
                                 final boolean minInclusive,
                                 final boolean maxInclusive) {
    super(field);
    if (precisionStep < 1)
      throw new IllegalArgumentException("precisionStep must be >=1");
    this.precisionStep = precisionStep;
    this.min = min;
    this.max = max;
    this.minInclusive = minInclusive;
    this.maxInclusive = maxInclusive;
    this.datatype = dataType;
    pstepDatatype = dataType.toString() + precisionStep;

    // For bigger precisionSteps this query likely
    // hits too many terms, so set to CONSTANT_SCORE_FILTER right off
    // (especially as the FilteredTermsEnum is costly if wasted only for AUTO tests because it
    // creates new enums from IndexReader for each sub-range)
    switch (dataType) {
      case LONG:
      case DOUBLE:
        // TODO: to uncomment when filter-based rewrite method is implemented
//        this.setRewriteMethod( (precisionStep > 6) ?
//          CONSTANT_SCORE_FILTER_REWRITE :
//          CONSTANT_SCORE_AUTO_REWRITE_DEFAULT
//        );
        this.setRewriteMethod(CONSTANT_SCORE_AUTO_REWRITE_DEFAULT);
        break;
      case INT:
      case FLOAT:
        // TODO: to uncomment when filter-based rewrite method is implemented
//        this.setRewriteMethod( (precisionStep > 8) ?
//          CONSTANT_SCORE_FILTER_REWRITE :
//          CONSTANT_SCORE_AUTO_REWRITE_DEFAULT
//        );
        this.setRewriteMethod(CONSTANT_SCORE_AUTO_REWRITE_DEFAULT);
        break;
      default:
        // should never happen
        throw new IllegalArgumentException("Invalid numeric NumericType");
    }

    // shortcut if upper bound == lower bound
    if (min != null && min.equals(max)) {
      this.setRewriteMethod(CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE);
    }
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>long</code>
   * range using the given <a href="#precisionStepDesc"><code>precisionStep</code></a>.
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static NodeNumericRangeQuery<Long> newLongRange(final String field,
      final int precisionStep, final Long min, final Long max, final boolean minInclusive,
      final boolean maxInclusive) {
    return new NodeNumericRangeQuery<Long>(field, precisionStep,
      NumericType.LONG, min, max, minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>long</code>
   * range using the default <code>precisionStep</code> {@link NumericUtils#PRECISION_STEP_DEFAULT} (4).
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static NodeNumericRangeQuery<Long> newLongRange(final String field,
      final Long min, final Long max, final boolean minInclusive,
      final boolean maxInclusive) {
    return new NodeNumericRangeQuery<Long>(field,
      NumericUtils.PRECISION_STEP_DEFAULT, NumericType.LONG, min, max,
      minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>int</code>
   * range using the given <a href="#precisionStepDesc"><code>precisionStep</code></a>.
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static NodeNumericRangeQuery<Integer> newIntRange(final String field,
      final int precisionStep, final Integer min, final Integer max,
      final boolean minInclusive, final boolean maxInclusive) {
    return new NodeNumericRangeQuery<Integer>(field, precisionStep,
      NumericType.INT, min,
      max, minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>int</code>
   * range using the default <code>precisionStep</code> {@link NumericUtils#PRECISION_STEP_DEFAULT} (4).
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static NodeNumericRangeQuery<Integer> newIntRange(final String field,
      final Integer min, final Integer max, final boolean minInclusive,
      final boolean maxInclusive) {
    return new NodeNumericRangeQuery<Integer>(field,
      NumericUtils.PRECISION_STEP_DEFAULT, NumericType.INT, min, max,
      minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>double</code>
   * range using the given <a href="#precisionStepDesc"><code>precisionStep</code></a>.
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static NodeNumericRangeQuery<Double> newDoubleRange(final String field,
      final int precisionStep, final Double min, final Double max,
      final boolean minInclusive, final boolean maxInclusive) {
    return new NodeNumericRangeQuery<Double>(field, precisionStep,
      NumericType.DOUBLE, min, max, minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>double</code>
   * range using the default <code>precisionStep</code> {@link NumericUtils#PRECISION_STEP_DEFAULT} (4).
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static NodeNumericRangeQuery<Double> newDoubleRange(final String field,
      final Double min, final Double max, final boolean minInclusive,
      final boolean maxInclusive) {
    return new NodeNumericRangeQuery<Double>(field,
      NumericUtils.PRECISION_STEP_DEFAULT, NumericType.DOUBLE, min, max,
      minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>float</code>
   * range using the given <a href="#precisionStepDesc"><code>precisionStep</code></a>.
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static NodeNumericRangeQuery<Float> newFloatRange(final String field,
      final int precisionStep, final Float min, final Float max,
      final boolean minInclusive, final boolean maxInclusive) {
    return new NodeNumericRangeQuery<Float>(field, precisionStep,
      NumericType.FLOAT, min, max, minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>float</code>
   * range using the default <code>precisionStep</code> {@link NumericUtils#PRECISION_STEP_DEFAULT} (4).
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static NodeNumericRangeQuery<Float> newFloatRange(final String field,
    final Float min, final Float max, final boolean minInclusive,
    final boolean maxInclusive) {
    return new NodeNumericRangeQuery<Float>(field,
      NumericUtils.PRECISION_STEP_DEFAULT, NumericType.FLOAT, min, max,
      minInclusive, maxInclusive);
  }

  @Override @SuppressWarnings("unchecked")
  protected TermsEnum getTermsEnum(final Terms terms, final AttributeSource atts) throws IOException {
    // very strange: java.lang.Number itsself is not Comparable, but all subclasses used here are
    return (min != null && max != null && ((Comparable<T>) min).compareTo(max) > 0) ?
      TermsEnum.EMPTY :
      new NumericRangeTermsEnum(terms.iterator(null));
  }

  /** Returns <code>true</code> if the lower endpoint is inclusive */
  public boolean includesMin() { return minInclusive; }

  /** Returns <code>true</code> if the upper endpoint is inclusive */
  public boolean includesMax() { return maxInclusive; }

  /** Returns the lower value of this range query */
  public T getMin() { return min; }

  /** Returns the upper value of this range query */
  public T getMax() { return max; }

  /** Returns the precision step. */
  public int getPrecisionStep() { return precisionStep; }

  @Override
  public String toString(final String field) {
    final StringBuffer sb = new StringBuffer();
    sb.append(minInclusive ? '[' : '{')
      .append((min == null) ? "*" : min.toString())
      .append(" TO ")
      .append((max == null) ? "*" : max.toString())
      .append(maxInclusive ? ']' : '}')
      .append(ToStringUtils.boost(this.getBoost()));
    return this.wrapToStringWithDatatype(sb).toString();
  }

  @Override
  @SuppressWarnings({"rawtypes"})
  public final boolean equals(final Object o) {
    if (o==this) return true;
    if (!super.equals(o))
      return false;
    if (o instanceof NodeNumericRangeQuery) {
      final NodeNumericRangeQuery q=(NodeNumericRangeQuery)o;
      return (
        (q.min == null ? min == null : q.min.equals(min)) &&
        (q.max == null ? max == null : q.max.equals(max)) &&
        minInclusive == q.minInclusive &&
        maxInclusive == q.maxInclusive &&
        precisionStep == q.precisionStep
      );
    }
    return false;
  }

  @Override
  public final int hashCode() {
    int hash = super.hashCode();
    hash += precisionStep^0x64365465;
    if (min != null) hash += min.hashCode()^0x14fa55fb;
    if (max != null) hash += max.hashCode()^0x733fa5fe;
    return hash +
      (Boolean.valueOf(minInclusive).hashCode()^0x14fa55fb)+
      (Boolean.valueOf(maxInclusive).hashCode()^0x733fa5fe);
  }

  // members (package private, to be also fast accessible by NumericRangeTermEnum)
  final int precisionStep;
  final NumericType datatype;
  final T min, max;
  final boolean minInclusive,maxInclusive;

  // used to handle float/double infinity correctly
  static final long LONG_NEGATIVE_INFINITY =
    NumericUtils.doubleToSortableLong(Double.NEGATIVE_INFINITY);
  static final long LONG_POSITIVE_INFINITY =
    NumericUtils.doubleToSortableLong(Double.POSITIVE_INFINITY);
  static final int INT_NEGATIVE_INFINITY =
    NumericUtils.floatToSortableInt(Float.NEGATIVE_INFINITY);
  static final int INT_POSITIVE_INFINITY =
    NumericUtils.floatToSortableInt(Float.POSITIVE_INFINITY);

  /**
   * Subclass of FilteredTermsEnum for enumerating all terms that match the
   * sub-ranges for trie range queries, using flex API.
   * <p>
   * WARNING: This term enumeration is not guaranteed to be always ordered by
   * {@link Term#compareTo}.
   * The ordering depends on how {@link NumericUtils#splitLongRange} and
   * {@link NumericUtils#splitIntRange} generates the sub-ranges. For
   * {@link MultiTermQuery} ordering is not relevant.
   */
  private final class NumericRangeTermsEnum extends FilteredTermsEnum {

    private BytesRef currentLowerBound, currentUpperBound;

    private final LinkedList<BytesRef> rangeBounds = new LinkedList<BytesRef>();
    private final Comparator<BytesRef> termComp;

    NumericRangeTermsEnum(final TermsEnum tenum) throws IOException {
      super(tenum);
      switch (datatype) {
        case LONG:
        case DOUBLE: {
          // lower
          long minBound;
          if (datatype == NumericType.LONG) {
            minBound = (min == null) ? Long.MIN_VALUE : min.longValue();
          } else {
            assert datatype == NumericType.DOUBLE;
            minBound = (min == null) ? LONG_NEGATIVE_INFINITY
              : NumericUtils.doubleToSortableLong(min.doubleValue());
          }
          if (!minInclusive && min != null) {
            if (minBound == Long.MAX_VALUE) break;
            minBound++;
          }

          // upper
          long maxBound;
          if (datatype == NumericType.LONG) {
            maxBound = (max == null) ? Long.MAX_VALUE : max.longValue();
          } else {
            assert datatype == NumericType.DOUBLE;
            maxBound = (max == null) ? LONG_POSITIVE_INFINITY
              : NumericUtils.doubleToSortableLong(max.doubleValue());
          }
          if (!maxInclusive && max != null) {
            if (maxBound == Long.MIN_VALUE) break;
            maxBound--;
          }

          NumericUtils.splitLongRange(new NumericUtils.LongRangeBuilder() {
            @Override
            public final void addRange(final BytesRef minPrefixCoded, final BytesRef maxPrefixCoded) {
              final BytesRef min = new BytesRef(pstepDatatype);
              min.append(minPrefixCoded);
              final BytesRef max = new BytesRef(pstepDatatype);
              max.append(maxPrefixCoded);
              rangeBounds.add(min);
              rangeBounds.add(max);
            }
          }, precisionStep, minBound, maxBound);
          break;
        }

        case INT:
        case FLOAT: {
          // lower
          int minBound;
          if (datatype == NumericType.INT) {
            minBound = (min == null) ? Integer.MIN_VALUE : min.intValue();
          } else {
            assert datatype == NumericType.FLOAT;
            minBound = (min == null) ? INT_NEGATIVE_INFINITY
              : NumericUtils.floatToSortableInt(min.floatValue());
          }
          if (!minInclusive && min != null) {
            if (minBound == Integer.MAX_VALUE) break;
            minBound++;
          }

          // upper
          int maxBound;
          if (datatype == NumericType.INT) {
            maxBound = (max == null) ? Integer.MAX_VALUE : max.intValue();
          } else {
            assert datatype == NumericType.FLOAT;
            maxBound = (max == null) ? INT_POSITIVE_INFINITY
              : NumericUtils.floatToSortableInt(max.floatValue());
          }
          if (!maxInclusive && max != null) {
            if (maxBound == Integer.MIN_VALUE) break;
            maxBound--;
          }

          NumericUtils.splitIntRange(new NumericUtils.IntRangeBuilder() {
            @Override
            public final void addRange(final BytesRef minPrefixCoded, final BytesRef maxPrefixCoded) {
              final BytesRef min = new BytesRef(pstepDatatype);
              min.append(minPrefixCoded);
              final BytesRef max = new BytesRef(pstepDatatype);
              max.append(maxPrefixCoded);
              rangeBounds.add(min);
              rangeBounds.add(max);
            }
          }, precisionStep, minBound, maxBound);
          break;
        }

        default:
          // should never happen
          throw new IllegalArgumentException("Invalid NumericType");
      }

      termComp = this.getComparator();
    }

    private void nextRange() {
      assert rangeBounds.size() % 2 == 0;

      currentLowerBound = rangeBounds.removeFirst();
      assert currentUpperBound == null || termComp.compare(currentUpperBound, currentLowerBound) <= 0 :
        "The current upper bound must be <= the new lower bound";

      currentUpperBound = rangeBounds.removeFirst();
    }

    @Override
    protected final BytesRef nextSeekTerm(final BytesRef term) throws IOException {
      while (rangeBounds.size() >= 2) {
        this.nextRange();

        // if the new upper bound is before the term parameter, the sub-range is never a hit
        if (term != null && termComp.compare(term, currentUpperBound) > 0)
          continue;
        // never seek backwards, so use current term if lower bound is smaller
        return (term != null && termComp.compare(term, currentLowerBound) > 0) ?
          term : currentLowerBound;
      }

      // no more sub-range enums available
      assert rangeBounds.isEmpty();
      currentLowerBound = currentUpperBound = null;
      return null;
    }

    @Override
    protected final AcceptStatus accept(final BytesRef term) {
      while (currentUpperBound == null || termComp.compare(term, currentUpperBound) > 0) {
        if (rangeBounds.isEmpty())
          return AcceptStatus.END;
        // peek next sub-range, only seek if the current term is smaller than next lower bound
        if (termComp.compare(term, rangeBounds.getFirst()) < 0)
          return AcceptStatus.NO_AND_SEEK;
        // step forward to next range without seeking, as next lower range bound is less or equal current term
        this.nextRange();
      }
      return AcceptStatus.YES;
    }

  }

}

