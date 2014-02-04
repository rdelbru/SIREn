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

package org.sindice.siren.util;

import java.io.CharArrayReader;

/**
 * Implementation of the {@link CharArrayReader} that allows to reset the reader
 * to a new char array input.
 */
public class ReusableCharArrayReader extends CharArrayReader {

  /**
   * @param buf
   */
  public ReusableCharArrayReader(final char[] buf) {
    super(buf);
  }

  public ReusableCharArrayReader(final char[] buf, final int offset, final int length) {
    super(buf, offset, length);
  }

  public void reset(final char[] toReset) {
    this.buf = toReset;
    this.pos = 0;
    this.count = toReset.length;
  }

  public void reset(final char[] toReset, final int offset, final int len) {
    this.buf = toReset;
    this.pos = offset;
    this.count = len;
  }

  @Override
  public String toString() {
    return new String(buf, this.pos, this.count);
  }

}
