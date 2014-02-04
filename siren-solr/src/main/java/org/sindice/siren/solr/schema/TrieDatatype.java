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

package org.sindice.siren.solr.schema;

import java.util.Locale;
import java.util.Map;

import org.apache.solr.common.SolrException;
import org.apache.solr.schema.TrieField.TrieTypes;
import org.sindice.siren.analysis.DoubleNumericAnalyzer;
import org.sindice.siren.analysis.FloatNumericAnalyzer;
import org.sindice.siren.analysis.IntNumericAnalyzer;
import org.sindice.siren.analysis.LongNumericAnalyzer;
import org.sindice.siren.search.node.NodeNumericRangeQuery;
import org.sindice.siren.solr.analysis.DateNumericAnalyzer;

/**
 * Provides a datatype to support for {@link NodeNumericRangeQuery}.
 * It supports integer, float, long and double types.
 * <p>
 * For each number being of this datatype, multiple terms are generated as
 * per the algorithm described in {@link NodeNumericRangeQuery}. The possible
 * number of terms increases dramatically with lower precision steps.
 * <p>
 * Note that if you use a precisionStep of 32 for int/float and 64 for
 * long/double, then multiple terms will not be generated, range search will be
 * no faster than any other number field.
 * <p>
 * Expert: The query analyzer is used as a container for the numeric range query
 * configuration, and should not be used to analyze query terms.
 */
public class TrieDatatype extends Datatype {

  public static final int DEFAULT_PRECISION_STEP = 8;

  protected int precisionStepArg = TrieDatatype.DEFAULT_PRECISION_STEP;  // the one passed in or defaulted
  protected int precisionStep;     // normalized
  protected TrieTypes type;
  protected Object missingValue;

  @Override
  protected void init(final Map<String, String> args) {
    final String p = args.remove("precisionStep");
    if (p != null) {
       precisionStepArg = Integer.parseInt(p);
    }
    // normalize the precisionStep
    precisionStep = precisionStepArg;
    if (precisionStep <= 0 || precisionStep >= 64) {
      precisionStep = Integer.MAX_VALUE;
    }

    final String t = args.remove("type");
    if (t != null) {
      try {
        type = TrieTypes.valueOf(t.toUpperCase(Locale.ROOT));
      }
      catch (final IllegalArgumentException e) {
        throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                "Invalid datatype specified for field: " + args.get("name"), e);
      }
    }

    this.initAnalyzers();
  }

  /**
   * Initialise the numeric analyzers. The query analyzer is just used as a
   * container of the numeric configuration, i.e., type and precision step.
   */
  private void initAnalyzers() {
    switch (type) {
      case INTEGER:
        queryAnalyzer = analyzer = new IntNumericAnalyzer(precisionStep);
        break;

      case LONG:
        queryAnalyzer = analyzer = new LongNumericAnalyzer(precisionStep);
        break;

      case FLOAT:
        queryAnalyzer = analyzer = new FloatNumericAnalyzer(precisionStep);
        break;

      case DOUBLE:
        queryAnalyzer = analyzer = new DoubleNumericAnalyzer(precisionStep);
        break;

      case DATE:
        queryAnalyzer = analyzer = new DateNumericAnalyzer(precisionStep);
        break;

      default:
        throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "Unknown " +
        		"type for trie datatype");
    }
  }

  /**
   * @return the precisionStep used to index values by this datatype
   */
  public int getPrecisionStep() {
    return precisionStepArg;
  }

  /**
   * @return the type of this datatype
   */
  public TrieTypes getType() {
    return type;
  }

}


