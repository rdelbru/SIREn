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

public class MockSirenReader extends Reader {

  private final MockSirenDocument doc;

  public MockSirenReader(final MockSirenDocument doc) {
    this.doc = doc;
  }

  public MockSirenDocument getDocument() {
    return doc;
  }

  @Override
  public int read(final char[] cbuf, final int off, final int len) throws IOException {
    return -1;
  }

  @Override
  public void close() throws IOException {}

}
