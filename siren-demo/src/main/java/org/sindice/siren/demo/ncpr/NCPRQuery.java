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

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.codehaus.jackson.JsonProcessingException;
import org.sindice.siren.qparser.json.dsl.QueryBuilder;
import org.sindice.siren.qparser.json.dsl.TwigQuery;
import org.sindice.siren.search.node.NodeTermQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class executes various queries, each one showing a particular feature
 * available in SIREn.
 */
public class NCPRQuery {

  /**
   * URL of SIREn index
   */
  private final String INDEX_URL = "http://localhost:8983/solr/";

  private final SolrServer server;

  private static final Logger logger = LoggerFactory.getLogger(NCPRQuery.class);

  public NCPRQuery() {
    this.server = new HttpSolrServer(INDEX_URL);
  }

  public void execute() throws SolrServerException, QueryNodeException {
    this.query(this.getGeoQuery());
    this.query(this.getOutputCurrentQuery());
    this.query(this.getAccessible24Hours());
    this.query(this.getWebsiteQuery());
    this.query(this.getDeviceControllerFacet());
    this.query(this.getNestedQuery());
    this.query(this.getJsonQuery());
  }

  public void query(final SolrQuery query) throws SolrServerException {
    final QueryResponse rsp = this.server.query(query);
    logger.info("Query: {}", query.getQuery());
    logger.info("Hits: {}", rsp.getResults().getNumFound());
    logger.info("Execution time: {} ms", rsp.getQTime());
    logger.info(rsp.toString());
  }

  /**
   * A query that shows how to use a custom datatype (uri)
   */
  private SolrQuery getWebsiteQuery() {
    final SolrQuery query = new SolrQuery();
    query.setQuery("DeviceOwner : { Website : uri(www.sourcelondon.net) }");
    return query;
  }

  /**
   * A query that shows how to use range query on geo-location data (double)
   */
  private SolrQuery getGeoQuery() {
    final SolrQuery query = new SolrQuery();
    query.setQuery("ChargeDeviceLocation : { " +
    		"Latitude : xsd:double([52 TO 53]), " +
    		"Longitude : xsd:double([-2 TO 2]) " +
    		"}");
    return query;
  }

  /**
   * A query that shows how to use range query on long numeric value
   */
  private SolrQuery getOutputCurrentQuery() {
    final SolrQuery query = new SolrQuery();
    query.setQuery("(ChargeDeviceLocation : { " +
    		"Latitude : xsd:double([52 TO 53]), " +
    		"Longitude : xsd:double([-2 TO 2]) " +
    		"}) AND " +
    		"(Connector : [ { RatedOutputCurrent : xsd:long([32 TO *]) } ])");
    return query;
  }

  /**
   * A query that shows how to combine range and textual queries
   */
  private SolrQuery getAccessible24Hours() {
    final SolrQuery query = new SolrQuery();
    query.setQuery("(ChargeDeviceLocation : { " +
        "Latitude : xsd:double([52 TO 53]), " +
        "Longitude : xsd:double([-2 TO 2]) " +
        "}) " +
        "AND " +
        "(Connector : [ { RatedOutputCurrent : xsd:long([32 TO *]) } ]) " +
        "AND " +
        "(Accessible24Hours : true)");
    return query;
  }

  /**
   * A query that shows how to combine SIREn query with the Solr's facet feature
   * on a Solr field.
   */
  private SolrQuery getDeviceControllerFacet() {
    final SolrQuery query = new SolrQuery();
    query.setQuery("Connector : [ { RatedOutputCurrent : xsd:long([32 TO *]) } ]");
    query.addFacetField("DeviceController_facet");
    return query;
  }

  /**
   * A query that shows how to use the nested query parameter to use Solr's
   * query parsers on Solr's fields.
   */
  private SolrQuery getNestedQuery() {
    final SolrQuery query = new SolrQuery();
    query.setQuery("DeviceOwner : { Website : uri(www.sourcelondon.net) }");
    query.setParam("nested", "{!lucene} name:university");
    return query;
  }

  /**
   * A query that shows how to build programatically a query for the JSON
   * query parser.
   */
  private SolrQuery getJsonQuery() throws QueryNodeException {
    final SolrQuery query = new SolrQuery();
    query.setRequestHandler("json");

    // Use the SIREn core API for building query expressions.
    final NodeTermQuery uri = new NodeTermQuery(new Term("", "www.sourcelondon.net"));
    uri.setDatatype("uri");

    final NodeTermQuery website = new NodeTermQuery(new Term("", "Website"));
    website.setDatatype("json:field");

    final NodeTermQuery owner = new NodeTermQuery(new Term("", "DeviceOwner"));
    owner.setDatatype("json:field");

    // Use the query builder to create twig query using the JSON query syntax.
    final QueryBuilder builder = new QueryBuilder();
    final TwigQuery twq = builder.newTwig(owner.toString("json"))
                           .with(
                             builder.newTwig(website.toString("json"))
                                    .with(builder.newNode(uri.toString("json"))),
                             2);

    query.setQuery(twq.toString());
    return query;
  }

  public void close() {
    this.server.shutdown();
  }

  public static void main(final String[] args)
  throws JsonProcessingException, IOException, SolrServerException, QueryNodeException {
    final NCPRQuery queryer = new NCPRQuery();
    try {
      queryer.execute();
    }
    finally {
      queryer.close();
    }
  }

}
