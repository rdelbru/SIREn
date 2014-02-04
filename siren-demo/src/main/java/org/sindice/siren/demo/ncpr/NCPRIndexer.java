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

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.LineReader;

/**
 * This class indexes the NCPR JSON dataset in a Solr example configured with
 * SIREn.
 */
public class NCPRIndexer {

  /**
   * URL of SIREn index
   */
  private final String INDEX_URL = "http://localhost:8983/solr/";

  private final SolrServer server;

  private final ObjectMapper mapper;

  private static final Logger logger = LoggerFactory.getLogger(NCPRIndexer.class);

  public NCPRIndexer() {
    this.server = new HttpSolrServer(INDEX_URL);
    this.mapper = new ObjectMapper();
  }

  /**
   * Reads and parses the input file line by line. Each line is expected to be a
   * serialised JSON object. It creates a {@link SolrInputDocument} for each
   * JSON object, add it to the index, and commit the documents at the end of
   * the process.
   */
  public void index(final File input) throws IOException, SolrServerException {
    logger.info("Loading JSON objects from {}", input);
    final LineReader reader = new LineReader(new FileReader(input));

    int counter = 0;
    String line;
    while ((line = reader.readLine()) != null) {
      server.add(this.parseObject(line));
      counter++;
    }
    server.commit();
    logger.info("Loaded {} JSON objects", counter);
  }

  /**
   * Parses a serialised JSON object and creates a {@link SolrInputDocument}
   * with two fields:
   * <ul>
   *  <li> <code>id</code> containing the id of the device
   *  <li> <code>name</code> containing the name of the device
   *  <li> <code>DeviceController_facet</code> containing the name of the device controller
   *  <li> <code>json</code> containing the serialised JSON object
   * </ul>
   */
  private SolrInputDocument parseObject(final String object)
  throws JsonProcessingException, IOException {
    final SolrInputDocument doc = new SolrInputDocument();
    final JsonNode node = mapper.readTree(object);
    doc.addField("id", node.path("ChargeDeviceId").asText());
    doc.addField("name", node.path("ChargeDeviceName").asText());
    doc.addField("DeviceController_facet", node.path("DeviceController").path("OrganisationName").asText());
    doc.addField("json", node);
    return doc;
  }

  public void close() {
    this.server.shutdown();
  }

  public static void main(final String[] args)
  throws JsonProcessingException, IOException, SolrServerException {
    final NCPRIndexer indexer = new NCPRIndexer();
    try {
      if (args.length == 0) {
        logger.error("Input file missing");
        System.exit(1);
      }
      indexer.index(new File(args[0]));
    }
    finally {
      indexer.close();
    }
  }

}
