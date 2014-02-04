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

import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.queryparser.flexible.core.QueryNodeParseException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.parser.SyntaxParser;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.sindice.siren.qparser.json.ParseException;
import org.sindice.siren.qparser.json.nodes.TopLevelQueryNode;

/**
 * Parser for the SIREN's JSON query syntax
 *
 * <p>
 *
 * This parser is based on the Jackson's JSON parser and uses internally
 * an {@link ObjectMapper} to create a tree model of the JSON data. The
 * tree model is then traversed using a property-centric visitor model.
 * The parser works as follows:
 * <ol>
 *   <li> The JSON data is parsed and a JSON tree model is created.
 *   <li> The tree model is then traversed using {@link JsonPropertyParser}s.
 *   For each property found in the tree model, the corresponding
 *   {@link JsonPropertyParser} is applied.
 * </ol>
 */
public class JsonSyntaxParser implements SyntaxParser {

  private final ObjectMapper mapper;

  public JsonSyntaxParser() {
    this.mapper = new ObjectMapper();
  }

  @Override
  public QueryNode parse(final CharSequence query, final CharSequence field)
  throws QueryNodeParseException {
    try {
      final JsonNode node = mapper.readTree(query.toString());
      final String fieldname = this.getFirstFieldName(node);
      final TopLevelQueryNode topNode = new TopLevelQueryNode();

      // check for node property
      if (fieldname.equals(NodePropertyParser.NODE_PROPERTY)) {
        final NodePropertyParser nodeParser = new NodePropertyParser(node, field);
        topNode.add(nodeParser.parse());
        return topNode;
      }
      // check for twig property
      if (fieldname.equals(TwigPropertyParser.TWIG_PROPERTY)) {
        final TwigPropertyParser twigParser = new TwigPropertyParser(node, field);
        topNode.add(twigParser.parse());
        return topNode;
      }
      // check for boolean property
      if (fieldname.equals(BooleanPropertyParser.BOOLEAN_PROPERTY)) {
        final BooleanPropertyParser booleanParser = new BooleanPropertyParser(node, field);
        topNode.add(booleanParser.parse());
        return topNode;
      }
      throw new ParseException("Invalid JSON query: unknown property '" + fieldname + "'");
    }
    catch (final IOException e) {
      throw new ParseException("Invalid JSON query", e);
    }

  }

  private String getFirstFieldName(final JsonNode node) throws ParseException {
    final Iterator<String> fieldNames = node.getFieldNames();
    if (fieldNames.hasNext()) {
      return fieldNames.next();
    }
    throw new ParseException("Invalid JSON query: either a node, boolean or twig query must be defined");
  }

}
