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
import org.sindice.siren.qparser.json.nodes.ArrayQueryNode;
import org.sindice.siren.qparser.json.nodes.TwigQueryNode;

/**
 * Parses a <code>twig</code> property and returns a {@link TwigQueryNode}.
 */
public class TwigPropertyParser extends JsonPropertyParser {

  public static final String TWIG_PROPERTY = "twig";

  public TwigPropertyParser(final JsonNode node, final CharSequence field) {
    super(node, field);
  }

  @Override
  String getProperty() {
    return TWIG_PROPERTY;
  }

  @Override
  TwigQueryNode parse() throws ParseException {
    final TwigQueryNode twigNode = new TwigQueryNode();
    twigNode.setField(field);

    final JsonNode objectNode = node.path(this.getProperty());

    final RootPropertyParser rootParser = new RootPropertyParser(objectNode, field);
    rootParser.setOptional(true);
    if (rootParser.isPropertyDefined()) {
      twigNode.setRoot(rootParser.parse());
    }

    final LevelPropertyParser levelParser = new LevelPropertyParser(objectNode, field);
    levelParser.setOptional(true);
    if (levelParser.isPropertyDefined()) {
      twigNode.setTag(levelParser.getProperty(), levelParser.parse());
    }

    final RangePropertyParser rangeParser = new RangePropertyParser(objectNode, field);
    rangeParser.setOptional(true);
    if (rangeParser.isPropertyDefined()) {
      twigNode.setTag(rangeParser.getProperty(), rangeParser.parse());
    }

    final ChildPropertyParser childParser = new ChildPropertyParser(objectNode, field);
    childParser.setOptional(true);
    if (childParser.isPropertyDefined()) {
      final ArrayQueryNode arrayNode = childParser.parse();
      twigNode.add(arrayNode.getChildren());
    }

    final DescendantPropertyParser descendantParser = new DescendantPropertyParser(objectNode, field);
    descendantParser.setOptional(true);
    if (descendantParser.isPropertyDefined()) {
      final ArrayQueryNode arrayNode = descendantParser.parse();
      twigNode.add(arrayNode.getChildren());
    }

    return twigNode;
  }

}
