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
import org.sindice.siren.qparser.json.nodes.NodeQueryNode;

/**
 * Parses a <code>node</code> property and returns a {@link NodeQueryNode}.
 */
public class NodePropertyParser extends JsonPropertyParser {

  public static final String NODE_PROPERTY = "node";

  public NodePropertyParser(final JsonNode node, final CharSequence field) {
    super(node, field);
  }

  @Override
  String getProperty() {
    return NODE_PROPERTY;
  }

  @Override
  NodeQueryNode parse() throws ParseException {
    final NodeQueryNode queryNode = new NodeQueryNode();
    queryNode.setField(field);

    final JsonNode objectNode = node.path(this.getProperty());

    final QueryPropertyParser queryParser = new QueryPropertyParser(objectNode, field);
    if (queryParser.isPropertyDefined()) {
      queryNode.setValue(queryParser.parse());
    }

    final LevelPropertyParser levelParser = new LevelPropertyParser(objectNode, field);
    levelParser.setOptional(true);
    if (levelParser.isPropertyDefined()) {
      queryNode.setTag(levelParser.getProperty(), levelParser.parse());
    }

    final RangePropertyParser rangeParser = new RangePropertyParser(objectNode, field);
    rangeParser.setOptional(true);
    if (rangeParser.isPropertyDefined()) {
      queryNode.setTag(rangeParser.getProperty(), rangeParser.parse());
    }

    return queryNode;
  }

}
