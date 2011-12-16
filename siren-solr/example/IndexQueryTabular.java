/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren-solr
 * @author Campinas Stephane [ 9 Dec 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.sindice.siren.solr.SirenParams;
import org.sindice.siren.util.CSV2Tabular;
import org.sindice.siren.util.XSDDatatype;
import org.xml.sax.SAXException;

/**
 * Demo showing how to convert a csv formated file into tuples, with an unlimited
 * number of fields.
 * Indexed tuples can then be queried using tabular query through the solr parameter
 * {@link SirenParams#TQ}.
 * The grammar of a tabular query is:<br>
 * <code>
 *    ([x]&lt;URIPattern&gt; |[x]'LiteralPattern'(^^&lt;DATATYPE&gt;)? |[x]"LITERAL"(^^&lt;DATATYPE&gt;)? )+ .
 * </code>
 * <br>
 * where x is the field position to evaluate the associated expression on.
 */
public class IndexQueryTabular {
  
  /**
   * URL of SIREn index
   */
  private final String INDEX_URL = "http://localhost:8080/siren";
  
  private final SolrServer server;
  
  /**
   * Contains the datatype of some fields. Any field which is not in this configuration
   * is considered of type {@link XSDDatatype#XSD_STRING}.
   */
  private final HashMap<Integer, String> csvConfig = new HashMap<Integer, String>();
  
  /* The fields name:
   * 
   * "id","ident","type","name","latitude_deg","longitude_deg","elevation_ft",
   * "continent","iso_country","iso_region","municipality","scheduled_service",
   * "gps_code","iata_code","local_code","home_link","wikipedia_link","keywords"
   */

  public IndexQueryTabular(final String solrServerUrl)
  throws SolrServerException, IOException {
    server = new CommonsHttpSolrServer(solrServerUrl == null ? INDEX_URL : solrServerUrl);
    
    // Clear the index
    clear();
    csvConfig.put(4, "xsd:double"); // latitude_deg
    csvConfig.put(5, "xsd:double"); // longitude_deg
    csvConfig.put(6, "xsd:double"); // elevation_ft
    csvConfig.put(14, XSDDatatype.XSD_ANY_URI); // home_link
    csvConfig.put(15, XSDDatatype.XSD_ANY_URI); // wikipedia_link
  }
  
  /**
   * Load the csv data into an array. Discard the first line, as it is the fields name.
   * @param csv
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private String[] loadCSV(File csv)
  throws FileNotFoundException, IOException {
    final List<String> lines = IOUtils.readLines(new FileReader(csv));
    
    lines.remove(0); // Removes the fields' name line
    return lines.toArray(new String[lines.size()]);
  }
  
  /**
   * Index the csv data file by converting each line into a tuple. 
   * @param csv
   * @throws FileNotFoundException
   * @throws IOException
   * @throws SolrServerException
   */
  public void index(File csv)
  throws FileNotFoundException, IOException, SolrServerException {
    final String[] lines = loadCSV(csv);
    
    for (int i = 0; i < lines.length; i++) {
      final String tuple = CSV2Tabular.convert(csvConfig, new String[] { lines[i] });
      // add this document as tabular data
      final SolrInputDocument document = new SolrInputDocument();
      document.addField("url", "http://www.ourairports.com/data/airports.csv_line" + i);
      document.addField("tabular", tuple);
      add(document);      
    }
    commit();
  }
  
  /**
   * Add a {@link SolrInputDocument}.
   */
  public void add(final SolrInputDocument doc)
  throws SolrServerException, IOException {
    final UpdateRequest request = new UpdateRequest();
    request.add(doc);
    request.process(server);
  }
  
  /**
   * Commit all documents that have been submitted
   */
  public void commit()
  throws SolrServerException, IOException {
    server.commit();
  }
  
  /**
   * Delete all the documents
   */
  public void clear() throws SolrServerException, IOException {
    server.deleteByQuery("*:*");
    commit();
  }

  /**
   * Query the indexed csv data using Tabular query, with the parameter {@link SirenParams#TQ}.
   * @throws SolrServerException
   */
  public void query()
  throws SolrServerException {
    System.out.println("[2]\"heliport\"");
    final SolrQuery query1 = new SolrQuery();
    query1.setQueryType("siren");
    query1.set(SirenParams.TQ, "[2]\"heliport\""); // tabular query
    final QueryResponse response1 = server.query(query1);
    for (SolrDocument d : response1.getResults()) {
      System.out.println(d);
    }
    System.out.println();
    
    System.out.println("[2]'heliport OR small_airport' [6]'[1000 TO *]'^^<xsd:double>");
    final SolrQuery query2 = new SolrQuery();
    query2.setQueryType("siren");
    query2.set(SirenParams.TQ, "[2]'heliport OR small_airport' [6]'[1000 TO *]'^^<xsd:double>"); // tabular query
    final QueryResponse response2 = server.query(query2);
    for (SolrDocument d : response2.getResults()) {
      System.out.println(d);
    }
    System.out.println();
  }
  
  public static void main(String[] args)
  throws FileNotFoundException, IOException, SolrServerException, ParserConfigurationException, SAXException {
    final IndexQueryTabular csv = new IndexQueryTabular(args.length == 1 ? args[0] : null);
    
    System.out.println("\n\n\t*** Demo for loading, indexing and querying tabular data ***\n");
    csv.index(new File(args.length == 1 ? args[0] : "./airports.csv"));
    csv.query();
  }
  
}
