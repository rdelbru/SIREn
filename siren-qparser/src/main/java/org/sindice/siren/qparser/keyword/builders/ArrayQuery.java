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
package org.sindice.siren.qparser.keyword.builders;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Query;
import org.sindice.siren.qparser.keyword.nodes.ArrayQueryNode;

/**
 * An {@link ArrayQuery} is a {@link Query} object that is used to store
 * a list of queries.
 *
 * @see ArrayQueryNode
 */
final class ArrayQuery
extends Query {

  private final ArrayList<Query> elements = new ArrayList<Query>();

  /**
   * Add a {@link Query} to the array
   */
  public void addElement(final Query q) {
    elements.add(q);
  }

  /**
   * Returns the list of queries.
   */
  public List<Query> getElements() {
    return elements;
  }

  @Override
  public String toString(final String field) {
    final StringBuilder sb = new StringBuilder("[ ");

    for (int i = 0; i < elements.size(); i++) {
      sb.append(elements.get(i).toString(field));
      if (i + 1 != elements.size()) {
        sb.append(", ");
      }
    }
    sb.append(" ]");
    return sb.toString();
  }

}
