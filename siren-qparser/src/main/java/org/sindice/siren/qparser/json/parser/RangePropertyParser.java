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
package org.sindice.siren.qparser.json.parser;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.sindice.siren.qparser.json.ParseException;

/**
 * Parses a <code>range</code> property and returns an array of integers.
 */
public class RangePropertyParser extends JsonPropertyParser {

  public static final String RANGE_PROPERTY = "range";

  RangePropertyParser(final JsonNode node, final CharSequence field) {
    super(node, field);
  }

  @Override
  String getProperty() {
    return RANGE_PROPERTY;
  }

  @Override
  int[] parse() throws ParseException {
    final int[] range = new int[2];
    final JsonNode value = node.path(RANGE_PROPERTY);

    if (!(value.isArray() && (value.size() == 2))) {
      throw new ParseException("Invalid value for property '" + RANGE_PROPERTY + "'");
    }

    final Iterator<JsonNode> it = value.iterator();
    JsonNode e;
    for (int i = 0; i < 2; i++) {
      e = it.next();
      if (!e.isInt()) {
        throw new ParseException("Invalid property '" + RANGE_PROPERTY + "': range value is not an integer");
      }
      range[i] = e.asInt();
    }

    return range;
  }

}
