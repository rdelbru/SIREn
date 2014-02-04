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

package org.sindice.siren.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.FieldType.NumericType;
import org.sindice.siren.search.node.NodeNumericRangeQuery;

/**
 * Abstraction over the analyzer for numeric datatype.
 * <p>
 * At indexing time, this class provides a {@link NumericTokenizer} for indexing
 * numeric values that can be used by a {@link NodeNumericRangeQuery}. At query
 * time, this class acts as a container for parameters that are required in the
 * query parsers to process numeric range queries.
 * <p>
 * See {@link NodeNumericRangeQuery} for more information about numeric range
 * queries.
 */
public abstract class NumericAnalyzer extends Analyzer {

  protected final int precisionStep;

  public NumericAnalyzer(final int precisionStep) {
    this.precisionStep = precisionStep;
  }

  /**
   * Returns the precision step of this analyzer.
   */
  public int getPrecisionStep() {
    return precisionStep;
  }

  /**
   * Returns the {@link NumericParser} associated to this analyzer.
   * @param <T>
   */
  public abstract NumericParser<? extends Number> getNumericParser();

  public abstract class NumericParser<T extends Number> {

    /**
     * Reads a textual representation of a numeric using a
     * {@link Reader}, parses the encoded numeric value and convert the
     * numeric value to a sortable signed int or long (in the case of a float or
     * double).
     * <p>
     * This is used at index time, in {@link NumericTokenizer}.
     */
    public abstract long parseAndConvert(Reader input) throws IOException;

    /**
     * Reads a textual representation of a numeric using a
     * {@link Reader}, and parses the encoded numeric value.
     * <p>
     * This is used at query time, for creating a {@link NodeNumericRangeQuery}.
     */
    public abstract T parse(Reader input) throws IOException;

    /**
     * Returns the {@link NumericType} of this parser.
     */
    public abstract NumericType getNumericType();

    /**
     * Returns the size in bits of the numeric value.
     */
    public abstract int getValueSize();

  }

}
