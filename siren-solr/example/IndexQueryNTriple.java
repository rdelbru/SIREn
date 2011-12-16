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

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.sindice.siren.solr.SirenParams;
import org.xml.sax.SAXException;

public class IndexQueryNTriple {
  
  /**
   * URL of SIREn index
   */
  private final String INDEX_URL = "http://localhost:8080/siren";
  
  private final SolrServer server;
  
  /*
   * Data about people in ntriple format
   */
  
  private final String[] doc1 = { "http://example.org/person/jim",
                                  "<http://example.org/person/jim> <http://example.org/schema/type> <http://example.org/schema/Person> .\n" +
                                  "<http://example.org/person/jim> <http://example.org/schema/type> <http://example.org/schema/Student> .\n" +
                                  "<http://example.org/person/jim> <http://example.org/schema/name> \"Jim Maple\" .\n" +
                                  "<http://example.org/person/jim> <http://example.org/schema/age> \"26\" .\n" +
                                  "<http://example.org/person/jim> <http://example.org/schema/workplace> \"DERI Galway\" .\n" };

  private final String[] doc2 = { "http://example.org/person/john",
                                  "<http://example.org/person/john> <http://example.org/schema/type> <http://example.org/schema/Person> .\n" +
                                  "<http://example.org/person/john> <http://example.org/schema/type> <http://example.org/schema/Student> .\n" +
                                  "<http://example.org/person/john> <http://example.org/schema/name> \"John Gartner\" .\n" +
                                  "<http://example.org/person/john> <http://example.org/schema/age> \"34\" .\n" +
                                  "<http://example.org/person/john> <http://example.org/schema/workplace> \"DERI Galway\" .\n" };

  private final String[] doc3 = { "http://example.org/person/jack",
                                  "<http://example.org/person/jack> <http://example.org/schema/type> <http://example.org/schema/Person> .\n" +
                                  "<http://example.org/person/jack> <http://example.org/schema/type> <http://example.org/schema/Professor> .\n" +
                                  "<http://example.org/person/jack> <http://example.org/schema/name> \"Jack Hollow\" .\n" +
                                  "<http://example.org/person/jack> <http://example.org/schema/age> \"52\" .\n" +
                                  "<http://example.org/person/jack> <http://example.org/schema/workplace> \"NUI Galway\" .\n" };
  
  public IndexQueryNTriple(final String solrServerUrl)
  throws SolrServerException, IOException {
    server = new CommonsHttpSolrServer(solrServerUrl == null ? INDEX_URL : solrServerUrl);
    
    // Clear the index
    clear();
  }
  
  /**
   * Index the ntriple data 
   * @throws IOException 
   * @throws SolrServerException 
   * @throws IOException
   * @throws SolrServerException
   */
  public void index() throws SolrServerException, IOException {
    // add this document
    final SolrInputDocument document1 = new SolrInputDocument();
    document1.addField("url", doc1[0]);
    document1.addField("ntriple", doc1[1]);
    add(document1);
    // add this document
    final SolrInputDocument document2 = new SolrInputDocument();
    document2.addField("url", doc2[0]);
    document2.addField("ntriple", doc2[1]);
    add(document2);
    // add this document
    final SolrInputDocument document3 = new SolrInputDocument();
    document3.addField("url", doc3[0]);
    document3.addField("ntriple", doc3[1]);
    add(document3);
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
    System.out.println("Query: galway");
    final SolrQuery query1 = new SolrQuery();
    query1.setQueryType("siren");
    query1.setQuery("galway"); // keyword query
    final QueryResponse response1 = server.query(query1);
    for (SolrDocument d : response1.getResults()) {
      System.out.println(d);
    }
    System.out.println();
    
    System.out.println("Query: * <name> 'john AND gartner'");
    final SolrQuery query2 = new SolrQuery();
    query2.setQueryType("siren");
    query2.set(SirenParams.NQ, "* <name> 'john AND gartner'"); // ntriple query
    final QueryResponse response2 = server.query(query2);
    for (SolrDocument d : response2.getResults()) {
      System.out.println(d);
    }
    System.out.println();
    
    System.out.println("Query: * <workplace> <galway> AND * <type> <student> AND * <age> \"26\"");
    final SolrQuery query3 = new SolrQuery();
    query3.setQueryType("siren");
    query3.set(SirenParams.NQ, "* <workplace> <galway> AND * <type> <student> AND * <age> \"26\""); // ntriple query
    final QueryResponse response3 = server.query(query3);
    for (SolrDocument d : response3.getResults()) {
      System.out.println(d);
    }
    System.out.println();
    
    System.out.println("Query: galway AND * <type> <student>");
    final SolrQuery query4 = new SolrQuery();
    query4.setQueryType("siren");
    query4.setQuery("galway");
    query4.set(SirenParams.NQ, "* <type> <student>"); // keyword and ntriple query
    final QueryResponse response4 = server.query(query4);
    for (SolrDocument d : response4.getResults()) {
      System.out.println(d);
    }
    System.out.println();
  }
  
  public static void main(String[] args)
  throws FileNotFoundException, IOException, SolrServerException, ParserConfigurationException, SAXException {
    final IndexQueryNTriple ntriple = new IndexQueryNTriple(args.length == 1 ? args[0] : null);
    
    System.out.println("\n\n\t*** Demo for loading, indexing and querying ntriple data ***\n");
    ntriple.index();
    ntriple.query();
  }
  
}
