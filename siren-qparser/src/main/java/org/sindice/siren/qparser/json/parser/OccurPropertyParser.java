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

import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier;
import org.apache.lucene.search.BooleanClause.Occur;
import org.codehaus.jackson.JsonNode;
import org.sindice.siren.qparser.json.ParseException;

/**
 * Parses a <code>occur</code> property and returns a {@link Modifier}.
 */
public class OccurPropertyParser extends JsonPropertyParser {

  public static final String OCCUR_PROPERTY = "occur";

  public OccurPropertyParser(final JsonNode node, final CharSequence field) {
    super(node, field);
  }

  @Override
  String getProperty() {
    return OCCUR_PROPERTY;
  }

  @Override
  Modifier parse() throws ParseException {
    final JsonNode value = node.path(OCCUR_PROPERTY);

    if (!value.isTextual()) {
      throw new ParseException("Invalid property'" + OCCUR_PROPERTY + "': value is not textual");
    }

    try {
      final Occur occur = Occur.valueOf(value.asText());
      switch (occur) {
        case MUST:
          return Modifier.MOD_REQ;

        case SHOULD:
          return Modifier.MOD_NONE;

        case MUST_NOT:
          return Modifier.MOD_NOT;

        default:
          throw new ParseException("Invalid value '" + value.asText() + "' for property '" + OCCUR_PROPERTY + "'");
      }
    }
    catch (final IllegalArgumentException e) {
      throw new ParseException("Invalid value '" + value.asText() + "' for property '" + OCCUR_PROPERTY + "'", e);
    }
  }

}
