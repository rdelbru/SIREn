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
 * Abstraction over the JSON property parsers.
 *
 * <p>
 *
 *
 */
abstract class JsonPropertyParser {

  final CharSequence field;

  final JsonNode node;

  boolean optional = false;

  JsonPropertyParser(final JsonNode node, final CharSequence field) {
    this.node = node;
    this.field = field;
  }

  /**
   * Set the specified property as optional
   */
  void setOptional(final boolean optional) {
    this.optional = optional;
  }

  /**
   * Return true if the specified property is optional
   */
  boolean isOptional() {
    return optional;
  }

  /**
   * Check if the {@link JsonNode} is a JSON Object node and contains value for
   * specified property. If this is the case, returns true; otherwise returns
   * false.
   * <p>
   * If the property is not defined and is not optional, throw a
   * {@link ParseException}.
   */
  boolean isPropertyDefined() throws ParseException {
    if (node.has(this.getProperty())) {
      return true;
    }
    else {
      if (this.optional) {
        return false;
      }
      throw new ParseException("Missing property '" + this.getProperty() + "'");
    }
  }

  /**
   * Return the property associated to this parser
   */
  abstract String getProperty();

  abstract Object parse() throws ParseException;

}
