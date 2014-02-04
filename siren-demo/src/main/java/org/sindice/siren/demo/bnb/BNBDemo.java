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
package org.sindice.siren.demo.bnb;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.search.Query;
import org.sindice.siren.demo.SimpleIndexer;
import org.sindice.siren.demo.SimpleSearcher;
import org.sindice.siren.qparser.json.JsonQueryParser;
import org.sindice.siren.qparser.json.dsl.QueryBuilder;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Index a set of bibliographical references encoded in JSON and execute various
 * search queries over the JSON data structure.
 * <p>
 * Each search query is written using both the keyword query syntax and the
 * JSON query syntax.
 */
public class BNBDemo {

  private final File indexDir;

  private static final File BNB_PATH = new File("./src/main/resources/bnb/data.json");

  private static final Logger logger = LoggerFactory.getLogger(BNBDemo.class);

  public BNBDemo(final File indexDir) {
    this.indexDir = indexDir;
    if (indexDir.exists()) {
      logger.error("Existing directory {} - aborting", indexDir);
      System.exit(1);
    }
    logger.info("Creating index directory {}", indexDir);
    indexDir.mkdirs();
  }

  public void index() throws IOException {
    final SimpleIndexer indexer = new SimpleIndexer(indexDir);
    try {
      int counter = 0;
      final LineIterator it = FileUtils.lineIterator(BNB_PATH);
      while (it.hasNext()) {
        final String id = Integer.toString(counter++);
        final String content = (String) it.next();
        logger.info("Indexing document {}", id);
        indexer.addDocument(id, content);
      }
      LineIterator.closeQuietly(it);
      logger.info("Commiting all pending documents");
      indexer.commit();
    }
    finally {
      logger.info("Closing index");
      indexer.close();
    }
  }

  public void search() throws QueryNodeException, IOException {
    final SimpleSearcher searcher = new SimpleSearcher(indexDir);
    final String[] keywordQueries = this.getKeywordQueries();
    final String[] jsonQueries = this.getJsonQueries();

    assert keywordQueries.length == jsonQueries.length;

    for (int i = 0; i < keywordQueries.length; i++) {
      Query q = searcher.parseKeywordQuery(keywordQueries[i]);
      logger.info("Executing keyword query: '{}'", keywordQueries[i]);
      String[] results = searcher.search(q, 1000);
      logger.info("Keyword query returned {} results: {}", results.length, Arrays.toString(results));

      q = searcher.parseJsonQuery(jsonQueries[i]);
      logger.info("Executing json query: '{}'", jsonQueries[i]);
      results = searcher.search(q, 1000);
      logger.info("Json query returned {} results: {}", results.length, Arrays.toString(results));
    }

  }

  /**
   * Get a list of queries that are based on the keyword query syntax
   *
   * @see KeywordQueryParser
   */
  private String[] getKeywordQueries() {
    final String[] queries = {
      "Cambridge",
      "placeOfPublication : Cambridge",
      "publisher : Cambridge Scholars",
      "subject : Environmental",
      "(subject : Environmental) AND (issued : 2009)",
      "type : [text, monographic]",
      "identifier : { id : 9780852935392, type : isbn }",
      "(subject : Computer security) AND (isPartOf : { identifier : { id : \"0302-9743\" }})"
    };
    return queries;
  }

  /**
   * Get a list of queries that are based on the JSON query syntax
   *
   * @see JsonQueryParser
   */
  private String[] getJsonQueries() throws QueryNodeException {
    final QueryBuilder b = new QueryBuilder();
    final String[] queries = {
      b.newNode("Cambridge").toString(),
      b.newTwig("placeOfPublication").with(b.newNode("Cambridge")).toString(),
      b.newTwig("publisher").with(b.newNode("Cambridge Scholars")).toString(),
      b.newTwig("subject").with(b.newNode("Environmental")).toString(),
      b.newBoolean().with(b.newTwig("subject").with(b.newNode("Environmental")))
                    .with(b.newTwig("issued").with(b.newNode("2009"))).toString(),
      b.newTwig("type").with(b.newNode("text"))
                       .with(b.newNode("monographic")).toString(),
      b.newTwig("identifier").with(
        // here, twig with empty root node to represent first nested entity node
        b.newTwig().with(b.newTwig("id").with(b.newNode("9780852935392")))
                   .with(b.newTwig("type").with(b.newNode("isbn")))
      ).toString(),
      b.newBoolean().with(b.newTwig("subject").with(b.newNode("Computer security")))
                    .with(b.newTwig("isPartOf").with(
                      // here, twig with empty root node to represent first nested entity node
                      b.newTwig().with(
                        b.newTwig("identifier").with(
                          // here, twig with empty root node to represent second nested entity node
                          b.newTwig().with(
                            b.newTwig("id").with(b.newNode("\"0302-9743\""))
                          )
                        )
                      )
                    )).toString()
    };
    return queries;
  }

  public static void main(final String[] args) throws IOException {
    final File indexDir = new File("./target/demo/bnb/");
    final BNBDemo demo = new BNBDemo(indexDir);
    try {
      demo.index();
      demo.search();
    }
    catch (final Throwable e) {
      logger.error("Unexpected error during demo", e);
    }
    finally {
      logger.info("Deleting index directory {}", indexDir);
      FileUtils.deleteQuietly(indexDir);
    }
  }

}
