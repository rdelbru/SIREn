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
 * @project siren-core_rdelbru
 * @author Campinas Stephane [ 5 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.util;

import java.io.CharArrayReader;

/**
 * Implementation of the {@link CharArrayReader} that allow to reset the reader
 * to a new char array input. 
 */
public class ReusableCharArrayReader extends CharArrayReader {

  /**
   * @param buf
   */
  public ReusableCharArrayReader(char[] buf) {
    super(buf);
  }
  
  public ReusableCharArrayReader(char buf[], int offset, int length) {
    super(buf, offset, length);
  }
  
  public void reset(char[] toReset) {
    this.buf = toReset;
    this.pos = 0;
    this.count = toReset.length;
  }
  
  public void reset(char[] toReset, int offset, int len) {
    this.buf = toReset;
    this.pos = offset;
    this.count = len;
  }

}
