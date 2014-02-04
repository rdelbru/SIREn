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

package org.sindice.siren.analysis.filter;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;

/**
 * Abstract class implementation of the SIREn payload coder/decoder.
 *
 * <p>
 *
 * The SIREn payload stores information about a token such as:
 * <ul>
 * <li> the dewey code of the node from which this token comes from;
 * <li> the relative position of the token within the node.
 * </ul>
 */
public abstract class PayloadCodec {

  public PayloadCodec() {}

  public abstract IntsRef getNode();

  public abstract int getPosition();

  /**
   * Encode the information into a byte array
   */
  public abstract BytesRef encode(IntsRef node, int pos);

  /**
   * Decode the node and position from the byte array
   */
  public abstract void decode(BytesRef data);

}
