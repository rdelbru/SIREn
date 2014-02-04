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
package org.sindice.siren.demo.ncpr;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.sindice.siren.analysis.JsonTokenizer;

import com.google.common.io.LineReader;

/**
 * This class convert the original NCPR JSON dataset into a JSON dataset with
 * explicit value datatypes.
 */
public class DatatypeConverter {

  private final ObjectMapper mapper;
  private final JsonGenerator generator;

  private final static String INPUT_FILE = "./src/example/json/ncpr.json";
  private final static String OUTPUT_FILE = "./src/example/json/ncpr-with-datatypes.json";

  public DatatypeConverter() throws IOException {
    this.mapper = new ObjectMapper();
    generator = mapper.getJsonFactory().createJsonGenerator(new File(OUTPUT_FILE), JsonEncoding.UTF8);
  }

  public void process() throws IOException {
    final FileReader fileReader = new FileReader(new File(INPUT_FILE));
    final LineReader reader = new LineReader(fileReader);
    try {
      JsonNode obj;
      String line;
      while ((line = reader.readLine()) != null) {
        obj = mapper.readTree(line);
        this.convertRatedOutputCurrent(obj);
        this.convertRatedOutputVoltage(obj);
        this.convertRatedOutputkW(obj);
        this.convertLatitude(obj);
        this.convertLongitude(obj);
        this.convertTetheredCable(obj);
        this.convertChargeMode(obj);
        this.convertDeviceControllerWebsite(obj);
        this.convertDeviceOwnerWebsite(obj);
        generator.writeTree(obj);
        generator.writeRaw('\n');
      }
    }
    finally {
      fileReader.close();
    }
  }

  private void convertRatedOutputkW(final JsonNode obj) {
    final Iterator<JsonNode> nodes = obj.path("Connector").iterator();
    while (nodes.hasNext()) {
      final ObjectNode node = (ObjectNode) nodes.next();
      final JsonNode current = node.path("RatedOutputkW");
      if (!current.isMissingNode() && !current.isNull()) {
        final double value = Double.parseDouble(current.asText());
        node.put("RatedOutputkW", value);
      }
      if (current.isNull()) {
        node.remove("RatedOutputkW");
      }
    }
  }

  private void convertRatedOutputVoltage(final JsonNode obj) {
    final Iterator<JsonNode> nodes = obj.path("Connector").iterator();
    while (nodes.hasNext()) {
      final ObjectNode node = (ObjectNode) nodes.next();
      final JsonNode current = node.path("RatedOutputVoltage");
      if (!current.isMissingNode() && !current.isNull()) {
        final int value = Integer.parseInt(current.asText());
        node.put("RatedOutputVoltage", value);
      }
      if (current.isNull()) {
        node.remove("RatedOutputVoltage");
      }
    }
  }

  private void convertRatedOutputCurrent(final JsonNode obj) {
    final Iterator<JsonNode> nodes = obj.path("Connector").iterator();
    while (nodes.hasNext()) {
      final ObjectNode node = (ObjectNode) nodes.next();
      final JsonNode current = node.path("RatedOutputCurrent");
      if (!current.isMissingNode() && !current.isNull()) {
        final int value = Integer.parseInt(current.asText());
        node.put("RatedOutputCurrent", value);
      }
      if (current.isNull()) {
        node.remove("RatedOutputCurrent");
      }
    }
  }

  private void convertTetheredCable(final JsonNode obj) {
    final Iterator<JsonNode> nodes = obj.path("Connector").iterator();
    while (nodes.hasNext()) {
      final ObjectNode node = (ObjectNode) nodes.next();
      final JsonNode current = node.path("TetheredCable");
      if (!current.isMissingNode() && !current.isNull()) {
        final int value = Integer.parseInt(current.asText());
        node.put("TetheredCable", value);
      }
      if (current.isNull()) {
        node.remove("TetheredCable");
      }
    }
  }

  private void convertChargeMode(final JsonNode obj) {
    final Iterator<JsonNode> nodes = obj.path("Connector").iterator();
    while (nodes.hasNext()) {
      final ObjectNode node = (ObjectNode) nodes.next();
      final JsonNode current = node.path("ChargeMode");
      if (!current.isMissingNode() && !current.isNull()) {
        final int value = Integer.parseInt(current.asText());
        node.put("ChargeMode", value);
      }
      if (current.isNull()) {
        node.remove("ChargeMode");
      }
    }
  }

  private void convertLatitude(final JsonNode obj) {
    final ObjectNode node = (ObjectNode) obj.path("ChargeDeviceLocation");
    final JsonNode current = node.path("Latitude");
    if (!current.isMissingNode() && !current.isNull()) {
      final double value = Double.parseDouble(current.asText());
      node.put("Latitude", value);
    }
    if (current.isNull()) {
      node.remove("Latitude");
    }
  }

  private void convertLongitude(final JsonNode obj) {
    final ObjectNode node = (ObjectNode) obj.path("ChargeDeviceLocation");
    final JsonNode current = node.path("Longitude");
    if (!current.isMissingNode() && !current.isNull()) {
      final double value = Double.parseDouble(current.asText());
      node.put("Longitude", value);
    }
    if (current.isNull()) {
      node.remove("Longitude");
    }
  }

  private void convertDeviceControllerWebsite(final JsonNode obj) {
    final ObjectNode node = (ObjectNode) obj.path("DeviceController");
    final JsonNode current = node.path("Website");
    if (!current.isMissingNode() && !current.isNull()) {
      node.put("Website", this.createValueDatatype("uri", current.asText()));
    }
    if (current.isNull()) {
      node.remove("Website");
    }
  }

  private void convertDeviceOwnerWebsite(final JsonNode obj) {
    final ObjectNode node = (ObjectNode) obj.path("DeviceOwner");
    final JsonNode current = node.path("Website");
    if (!current.isMissingNode() && !current.isNull()) {
      node.put("Website", this.createValueDatatype("uri", current.asText()));
    }
    if (current.isNull()) {
      node.remove("Website");
    }
  }

  private JsonNode createValueDatatype(final String datatype, final String value) {
    final ObjectNode node = mapper.createObjectNode();
    node.put(JsonTokenizer.DATATYPE_LABEL, datatype);
    node.put(JsonTokenizer.DATATYPE_VALUES, value);
    return node;
  }

  public void close() throws IOException {
    generator.close();
  }

  public static void main(final String[] args) throws IOException {
    final DatatypeConverter preprocessing = new DatatypeConverter();
    preprocessing.process();
    preprocessing.close();
  }

}
