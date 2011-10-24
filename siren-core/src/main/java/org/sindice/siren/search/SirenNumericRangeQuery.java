/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
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
import java.util.LinkedList;

import org.apache.lucene.analysis.NumericTokenStream;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.document.NumericField.DataType;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.search.FilteredTermEnum;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.util.NumericUtils;
import org.apache.lucene.util.StringHelper;
import org.apache.lucene.util.ToStringUtils;

/**
 * <p>A {@link SirenPrimitiveQuery} that matches numeric values within a
 * specified range.  To use this, you must first index the
 * numeric values using {@link NumericField} (expert: {@link
 * NumericTokenStream}).  If your terms are instead textual,
 * you should use {@link SirenTermRangeQuery}.</p>
 *
 * <p>You create a new SirenNumericRangeQuery with the static
 * factory methods, eg:
 *
 * <pre>
 * Query q = SirenNumericRangeQuery.newFloatRange("weight", 0.03f, 0.10f, true, true);
 * </pre>
 *
 * matches all documents whose float valued "weight" field
 * ranges from 0.03 to 0.10, inclusive.
 *
 * <p>The performance of SirenNumericRangeQuery is much better
 * than the corresponding {@link SirenTermRangeQuery} because the
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
 * SirenMultiTermQuery#CONSTANT_SCORE_AUTO_REWRITE_DEFAULT} and never relies on
 * uses {@linkplain SirenMultiTermQuery#CONSTANT_SCORE_FILTER_REWRITE}. Good
 * performance is expected for
 * 32 bit (int/float) ranges with precisionStep &le;8 and 64
 * bit (long/double) ranges with precisionStep &le;6.
 * In the other cases, bad performance has to be expected as the
 * number of terms is likely to be high.
 *
 * <p> @see {@link NumericRangeQuery} for more information on how it works.
 *
 * <p> Code taken from {@link NumericRangeQuery} and adapted for SIREn.
 **/
public final class SirenNumericRangeQuery<T extends Number> extends SirenMultiTermQuery {

  private static final long serialVersionUID = 4836211972429767278L;
  
  private SirenNumericRangeQuery(final DataType datatype,
                                 final String field, final int precisionStep,
                                 final int valSize, final T min, final T max,
                                 final boolean minInclusive,
                                 final boolean maxInclusive) {
    assert (valSize == 32 || valSize == 64);
    if (precisionStep < 1)
      throw new IllegalArgumentException("precisionStep must be >=1");
    this.field = StringHelper.intern(field);
    this.precisionStep = precisionStep;
    this.valSize = valSize;
    this.min = min;
    this.max = max;
    this.minInclusive = minInclusive;
    this.maxInclusive = maxInclusive;
    this.datatype = datatype;
    
    switch (valSize) {
      case 64:
        this.setRewriteMethod(
          CONSTANT_SCORE_AUTO_REWRITE_DEFAULT
        );
        break;
      case 32:
        this.setRewriteMethod(
          CONSTANT_SCORE_AUTO_REWRITE_DEFAULT
        );
        break;
      default:
        // should never happen
        throw new IllegalArgumentException("valSize must be 32 or 64");
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
  public static SirenNumericRangeQuery<Long> newLongRange(final String field,
      final int precisionStep, final Long min, final Long max, final boolean minInclusive,
      final boolean maxInclusive) {
    return new SirenNumericRangeQuery<Long>(DataType.LONG, field, precisionStep, 64, min, max,
      minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>long</code>
   * range using the default <code>precisionStep</code> {@link NumericUtils#PRECISION_STEP_DEFAULT} (4).
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static SirenNumericRangeQuery<Long> newLongRange(final String field,
      final Long min, final Long max, final boolean minInclusive,
      final boolean maxInclusive) {
    return new SirenNumericRangeQuery<Long>(DataType.LONG, field, NumericUtils.PRECISION_STEP_DEFAULT,
      64, min, max, minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>int</code>
   * range using the given <a href="#precisionStepDesc"><code>precisionStep</code></a>.
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static SirenNumericRangeQuery<Integer> newIntRange(final String field,
      final int precisionStep, final Integer min, final Integer max,
      final boolean minInclusive, final boolean maxInclusive) {
    return new SirenNumericRangeQuery<Integer>(DataType.INT, field, precisionStep, 32, min,
      max, minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>int</code>
   * range using the default <code>precisionStep</code> {@link NumericUtils#PRECISION_STEP_DEFAULT} (4).
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static SirenNumericRangeQuery<Integer> newIntRange(final String field,
      final Integer min, final Integer max, final boolean minInclusive,
      final boolean maxInclusive) {
    return new SirenNumericRangeQuery<Integer>(DataType.INT, field, NumericUtils.PRECISION_STEP_DEFAULT,
      32, min, max, minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>double</code>
   * range using the given <a href="#precisionStepDesc"><code>precisionStep</code></a>.
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static SirenNumericRangeQuery<Double> newDoubleRange(final String field,
      final int precisionStep, final Double min, final Double max,
      final boolean minInclusive, final boolean maxInclusive) {
    return new SirenNumericRangeQuery<Double>(DataType.DOUBLE, field, precisionStep, 64, min, max,
      minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>double</code>
   * range using the default <code>precisionStep</code> {@link NumericUtils#PRECISION_STEP_DEFAULT} (4).
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static SirenNumericRangeQuery<Double> newDoubleRange(final String field,
      final Double min, final Double max, final boolean minInclusive,
      final boolean maxInclusive) {
    return new SirenNumericRangeQuery<Double>(DataType.DOUBLE, field, NumericUtils.PRECISION_STEP_DEFAULT,
      64, min, max, minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>float</code>
   * range using the given <a href="#precisionStepDesc"><code>precisionStep</code></a>.
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static SirenNumericRangeQuery<Float> newFloatRange(final String field,
      final int precisionStep, final Float min, final Float max,
      final boolean minInclusive, final boolean maxInclusive) {
    return new SirenNumericRangeQuery<Float>(DataType.FLOAT, field, precisionStep, 32, min, max,
      minInclusive, maxInclusive);
  }

  /**
   * Factory that creates a <code>SirenNumericRangeQuery</code>, that queries a <code>float</code>
   * range using the default <code>precisionStep</code> {@link NumericUtils#PRECISION_STEP_DEFAULT} (4).
   * You can have half-open ranges (which are in fact &lt;/&le; or &gt;/&ge; queries)
   * by setting the min or max value to <code>null</code>. By setting inclusive to false, it will
   * match all documents excluding the bounds, with inclusive on, the boundaries are hits, too.
   */
  public static SirenNumericRangeQuery<Float> newFloatRange(final String field,
    final Float min, final Float max, final boolean minInclusive,
    final boolean maxInclusive) {
    return new SirenNumericRangeQuery<Float>(DataType.FLOAT, field, NumericUtils.PRECISION_STEP_DEFAULT,
      32, min, max, minInclusive, maxInclusive);
  }

  @Override
  protected FilteredTermEnum getEnum(final IndexReader reader)
  throws IOException {
    return new NumericRangeTermEnum(reader);
  }

  /** Returns the field name for this query */
  public String getField() { return field; }

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
    final StringBuilder sb = new StringBuilder();
    if (!this.field.equals(field)) sb.append(this.field).append(':');
    return sb.append(minInclusive ? '[' : '{')
      .append((min == null) ? "*" : min.toString())
      .append(" TO ")
      .append((max == null) ? "*" : max.toString())
      .append(maxInclusive ? ']' : '}')
      .append(ToStringUtils.boost(this.getBoost()))
      .toString();
  }

  @Override
  public final boolean equals(final Object o) {
    if (o==this) return true;
    if (!super.equals(o))
      return false;
    if (o instanceof SirenNumericRangeQuery) {
      final SirenNumericRangeQuery q=(SirenNumericRangeQuery)o;
      return (
        field==q.field &&
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
    hash += field.hashCode()^0x4565fd66 + precisionStep^0x64365465;
    if (min != null) hash += min.hashCode()^0x14fa55fb;
    if (max != null) hash += max.hashCode()^0x733fa5fe;
    return hash +
      (Boolean.valueOf(minInclusive).hashCode()^0x14fa55fb)+
      (Boolean.valueOf(maxInclusive).hashCode()^0x733fa5fe);
  }

  // field must be interned after reading from stream
  private void readObject(final java.io.ObjectInputStream in)
  throws java.io.IOException, ClassNotFoundException {
    in.defaultReadObject();
    field = StringHelper.intern(field);
  }

  // members (package private, to be also fast accessible by NumericRangeTermEnum)
  String field;
  final int precisionStep, valSize;
  final T min, max;
  final boolean minInclusive,maxInclusive;
  final DataType datatype;
  
  /**
   * Subclass of FilteredTermEnum for enumerating all terms that match the
   * sub-ranges for trie range queries.
   * <p>
   * WARNING: This term enumeration is not guaranteed to be always ordered by
   * {@link Term#compareTo}.
   * The ordering depends on how {@link NumericUtils#splitLongRange} and
   * {@link NumericUtils#splitIntRange} generates the sub-ranges. For
   * {@link SirenMultiTermQuery} ordering is not relevant.
   */
  private final class NumericRangeTermEnum extends FilteredTermEnum {

    private final IndexReader reader;
    private final LinkedList<String> rangeBounds = new LinkedList<String>();
    private final Term termTemplate = new Term(field);
    private String currentUpperBound = null;

    NumericRangeTermEnum(final IndexReader reader) throws IOException {
      this.reader = reader;

      switch (valSize) {
        case 64: {
          // lower
          long minBound = Long.MIN_VALUE;
          if (min instanceof Long) {
            minBound = min.longValue();
          } else if (min instanceof Double) {
            minBound = NumericUtils.doubleToSortableLong(min.doubleValue());
          }
          if (!minInclusive && min != null) {
            if (minBound == Long.MAX_VALUE) break;
            minBound++;
          }

          // upper
          long maxBound = Long.MAX_VALUE;
          if (max instanceof Long) {
            maxBound = max.longValue();
          } else if (max instanceof Double) {
            maxBound = NumericUtils.doubleToSortableLong(max.doubleValue());
          }
          if (!maxInclusive && max != null) {
            if (maxBound == Long.MIN_VALUE) break;
            maxBound--;
          }

          NumericUtils.splitLongRange(new NumericUtils.LongRangeBuilder() {
            @Override
            public final void addRange(final String minPrefixCoded, final String maxPrefixCoded) {
            rangeBounds.add(datatype.name() + precisionStep + minPrefixCoded);
            rangeBounds.add(datatype.name() + precisionStep + maxPrefixCoded);
            }
          }, precisionStep, minBound, maxBound);
          break;
        }

        case 32: {
          // lower
          int minBound = Integer.MIN_VALUE;
          if (min instanceof Integer) {
            minBound = min.intValue();
          } else if (min instanceof Float) {
            minBound = NumericUtils.floatToSortableInt(min.floatValue());
          }
          if (!minInclusive && min != null) {
            if (minBound == Integer.MAX_VALUE) break;
            minBound++;
          }

          // upper
          int maxBound = Integer.MAX_VALUE;
          if (max instanceof Integer) {
            maxBound = max.intValue();
          } else if (max instanceof Float) {
            maxBound = NumericUtils.floatToSortableInt(max.floatValue());
          }
          if (!maxInclusive && max != null) {
            if (maxBound == Integer.MIN_VALUE) break;
            maxBound--;
          }

          NumericUtils.splitIntRange(new NumericUtils.IntRangeBuilder() {
            @Override
            public final void addRange(final String minPrefixCoded, final String maxPrefixCoded) {
              rangeBounds.add(datatype.name() + precisionStep + minPrefixCoded);
              rangeBounds.add(datatype.name() + precisionStep + maxPrefixCoded);
            }
          }, precisionStep, minBound, maxBound);
          break;
        }

        default:
          // should never happen
          throw new IllegalArgumentException("valSize must be 32 or 64");
      }

      // seek to first term
      this.next();
    }

    @Override
    public float difference() {
      return 1.0f;
    }

    /** this is a dummy, it is not used by this class. */
    @Override
    protected boolean endEnum() {
      throw new UnsupportedOperationException("not implemented");
    }

    /** this is a dummy, it is not used by this class. */
    @Override
    protected void setEnum(final TermEnum tenum) {
      throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Compares if current upper bound is reached.
     * In contrast to {@link FilteredTermEnum}, a return value
     * of <code>false</code> ends iterating the current enum
     * and forwards to the next sub-range.
     */
    @Override
    protected boolean termCompare(final Term term) {
      return (term.field() == field && term.text().compareTo(currentUpperBound) <= 0);
    }

    /** Increments the enumeration to the next element.  True if one exists. */
    @Override
    public boolean next() throws IOException {
      // if a current term exists, the actual enum is initialized:
      // try change to next term, if no such term exists, fall-through
      if (currentTerm != null) {
        assert actualEnum != null;
        if (actualEnum.next()) {
          currentTerm = actualEnum.term();
          if (this.termCompare(currentTerm))
            return true;
        }
      }

      // if all above fails, we go forward to the next enum,
      // if one is available
      currentTerm = null;
      while (rangeBounds.size() >= 2) {
        assert rangeBounds.size() % 2 == 0;
        // close the current enum and read next bounds
        if (actualEnum != null) {
          actualEnum.close();
          actualEnum = null;
        }
        final String lowerBound = rangeBounds.removeFirst();
        this.currentUpperBound = rangeBounds.removeFirst();
        // create a new enum
        actualEnum = reader.terms(termTemplate.createTerm(lowerBound));
        currentTerm = actualEnum.term();
        if (currentTerm != null && this.termCompare(currentTerm))
          return true;
        // clear the current term for next iteration
        currentTerm = null;
      }

      // no more sub-range enums available
      assert rangeBounds.size() == 0 && currentTerm == null;
      return false;
    }

    /** Closes the enumeration to further activity, freeing resources.  */
    @Override
    public void close() throws IOException {
      rangeBounds.clear();
      currentUpperBound = null;
      super.close();
    }

  }

}

