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
 * @project siren-solr
 * @author Renaud Delbru [ 17 Oct 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.solr.schema;

import java.util.Locale;
import java.util.Map;

import org.apache.lucene.document.NumericField;
import org.apache.solr.common.SolrException;
import org.sindice.siren.analysis.DoubleNumericAnalyzer;
import org.sindice.siren.analysis.FloatNumericAnalyzer;
import org.sindice.siren.analysis.IntNumericAnalyzer;
import org.sindice.siren.analysis.LongNumericAnalyzer;
import org.sindice.siren.search.SirenNumericRangeQuery;

/**
 * Provides field types to support for {@link SirenNumericRangeQuery}.
 * It supports integer, float, long and double types.
 * <p>
 * For each cell being of this datatype, multiple terms are generated as
 * per the algorithm described in {@link SirenNumericRangeQuery}. The possible
 * number of terms increases dramatically with lower precision steps.
 * <p>
 * Note that if you use a precisionStep of 32 for int/float and 64 for
 * long/double, then multiple terms will not be generated, range search will be
 * no faster than any other number field.
 * <p>
 * The query analyzer is used as a container for the numeric range query
 * configuration, and should not be used to analyze query terms.
 */
public class TrieDatatype extends Datatype {

  public static final int DEFAULT_PRECISION_STEP = 8;

  protected int precisionStepArg = TrieDatatype.DEFAULT_PRECISION_STEP;  // the one passed in or defaulted
  protected int precisionStep;     // normalized
  protected NumericField.DataType type;

  @Override
  protected void init(final Map<String, String> args) {
    final String p = args.remove("precisionStep");
    if (p != null) {
       precisionStepArg = Integer.parseInt(p);
    }
    // normalize the precisionStep
    precisionStep = precisionStepArg;
    if (precisionStep<=0 || precisionStep>=64) {
      precisionStep = Integer.MAX_VALUE;
    }

    final String t = args.remove("type");
    if (t != null) {
      try {
        type = NumericField.DataType.valueOf(t.toUpperCase(Locale.ENGLISH));
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
      case INT:
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

      default:
        throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "Unknown " +
        		"type for trie field");
    }
  }

  /**
   * @return the precisionStep used to index values into the field
   */
  public int getPrecisionStep() {
    return precisionStepArg;
  }

  /**
   * @return the type of this field
   */
  public NumericField.DataType getType() {
    return type;
  }

}


