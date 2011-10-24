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
 * @author Renaud Delbru [ 6 Oct 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.document.NumericField.DataType;
import org.sindice.siren.util.XSDPrimitiveTypeParser;

public class LongNumericAnalyzer
extends NumericAnalyzer {

  public LongNumericAnalyzer(final int precisionStep) {
    super(precisionStep);
  }

  /* (non-Javadoc)
   * @see org.sindice.siren.analysis.NumericAnalyzer#setNumericValue(java.io.Reader)
   */
  @Override
  protected void setNumericValue(final SirenNumericTokenStream tokenStream, final Reader reader)
  throws IOException {
    tokenStream.setLongValue(XSDPrimitiveTypeParser.parseLong(reader));
  }

  @Override
  public DataType getNumericType() {
    return DataType.LONG;
  }

}
