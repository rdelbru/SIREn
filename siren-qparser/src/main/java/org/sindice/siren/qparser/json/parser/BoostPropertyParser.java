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

import org.codehaus.jackson.JsonNode;
import org.sindice.siren.qparser.json.ParseException;

/**
 * Parses a <code>boost</code> property and returns a {@link Float}.
 */
public class BoostPropertyParser extends JsonPropertyParser {

  public static final String BOOST_PROPERTY = "boost";

  BoostPropertyParser(final JsonNode node, final CharSequence field) {
    super(node, field);
  }

  @Override
  String getProperty() {
    return BOOST_PROPERTY;
  }

  @Override
  Float parse() throws ParseException {
    final JsonNode value = node.path(BOOST_PROPERTY);
    if (value.isFloatingPointNumber()) {
      return (float) value.asDouble();
    }
    throw new ParseException("Invalid property '" + BOOST_PROPERTY + "': value is not a float");
  }

}
