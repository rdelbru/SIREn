/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
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
package org.sindice.siren.solr.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.sindice.siren.solr.SirenParams;
import org.sindice.siren.solr.SolrServerWrapper;
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
public class IndexQueryCSV {

  /**
   * The path to the SIREn index
   */
  private static final String SOLR_HOME = "./src/test/resources/solr.home/";
  
  /**
   * Creates a SolrServer, with SIREn as a plugin
   */
  protected static SolrServerWrapper wrapper;
  
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

  public IndexQueryCSV()
  throws IOException, ParserConfigurationException, SAXException {
    csvConfig.put(4, "xsd:double"); // latitude_deg
    csvConfig.put(5, "xsd:double"); // longitude_deg
    csvConfig.put(6, "xsd:double"); // elevation_ft
    csvConfig.put(14, XSDDatatype.XSD_ANY_URI); // home_link
    csvConfig.put(15, XSDDatatype.XSD_ANY_URI); // wikipedia_link
    
    wrapper = new SolrServerWrapper(SOLR_HOME);
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
    final String tuples = CSV2Tabular.convert(csvConfig, lines);
    
    // add this document as tabular data
    final SolrInputDocument document = new SolrInputDocument();
    document.addField("url", "http://www.ourairports.com/data/airports.csv");
    document.addField("tabular", tuples);
    wrapper.add(document);
    wrapper.commit();
  }
  
  /**
   * Query the indexed csv data using Tabular query, with the parameter {@link SirenParams#TQ}.
   * @throws SolrServerException
   */
  public void query()
  throws SolrServerException {
    System.out.println("is there a heliport ?");
    final SolrQuery query1 = new SolrQuery();
    query1.setQueryType("siren");
    query1.set(SirenParams.TQ, "[2]\"heliport\""); // tabular query
    final QueryResponse response1 = wrapper.getServer().query(query1);
    for (SolrDocument d : response1.getResults()) {
      System.out.println(d);
    }
    System.out.println();
    
    System.out.println("is there a heliport or a small aiport where the elevation_ft is > 1000?");
    final SolrQuery query2 = new SolrQuery();
    query2.setQueryType("siren");
    query2.set(SirenParams.TQ, "[2]'heliport OR small_airport' [6]'[1000 TO *]'^^<xsd:double>"); // tabular query
    final QueryResponse response2 = wrapper.getServer().query(query2);
    for (SolrDocument d : response2.getResults()) {
      System.out.println(d);
    }
    System.out.println();
  }
  
  public void close() {
    wrapper.close();
  }
  
  public static void main(String[] args)
  throws FileNotFoundException, IOException, SolrServerException, ParserConfigurationException, SAXException {
    final IndexQueryCSV csv = new IndexQueryCSV();
    
    csv.index(new File("./src/test/resources/demo/airports.csv"));
    
    System.out.println("\n\n\t*** Demo for loading, indexing and querying csv data ***\n");
    csv.query();
    csv.close();
  }
  
}
