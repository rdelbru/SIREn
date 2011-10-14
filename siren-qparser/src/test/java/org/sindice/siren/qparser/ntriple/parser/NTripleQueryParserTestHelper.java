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
 * @project solr-plugins
 *
 * @author Renaud Delbru [ 25 Apr 2008 ]
 * @link http://renaud.delbru.fr/
 * All rights reserved.
 */
package org.sindice.siren.qparser.ntriple.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute.Operator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.sindice.siren.qparser.analysis.NTripleQueryAnalyzer;
import org.sindice.siren.qparser.analysis.NTripleTestHelper;
import org.sindice.siren.qparser.ntriple.NTripleQueryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NTripleQueryParserTestHelper extends NTripleTestHelper {

  private static final Logger logger = LoggerFactory.getLogger(NTripleTestHelper.class);
  
  public static float getScore(final Map<String, String> ntriples,
                               final Map<String, Float> boosts, final String query,
                               final boolean scattered)
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    RAMDirectory ramDir = null;

    try {
      ramDir = new RAMDirectory();
      NTripleQueryParserTestHelper.index(ramDir, ntriples);
      return NTripleQueryParserTestHelper.getScore(ramDir, query, boosts, scattered);
    }
    finally {
      if (ramDir != null) ramDir.close();
    }
  }

  public static boolean match(final Map<String, String> ntriples,
                              final Map<String, Float> boosts, final String query,
                              final boolean scattered)
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    RAMDirectory ramDir = null;

    try {
      ramDir = new RAMDirectory();
      NTripleQueryParserTestHelper.index(ramDir, ntriples);
      return NTripleQueryParserTestHelper.match(ramDir, query, boosts, scattered);
    }
    finally {
      if (ramDir != null) ramDir.close();
    }
  }

  public static boolean match(final String ntriple, final String query)
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    RAMDirectory ramDir = null;

    try {
      ramDir = new RAMDirectory();
      NTripleQueryParserTestHelper.index(ramDir, ntriple);
      return NTripleQueryParserTestHelper.match(ramDir, query, _defaultField);
    }
    finally {
      if (ramDir != null) ramDir.close();
    }
  }

  public static boolean matchImplicit(final String ntriple, final String query)
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    RAMDirectory ramDir = null;

    try {
      ramDir = new RAMDirectory();
      NTripleQueryParserTestHelper.indexImplicit(ramDir, ntriple);
      return NTripleQueryParserTestHelper.match(ramDir, query, _implicitField);
    }
    finally {
      if (ramDir != null) ramDir.close();
    }
  }

  private static void index(final RAMDirectory ramDir, final String ntriple)
  throws CorruptIndexException, LockObtainFailedException, IOException {
    IndexWriter ramWriter = null;

    try {
      ramWriter = NTripleTestHelper.createRamIndexWriter(ramDir);

      final Document doc = new Document();
      doc.add(new Field(_ID_FIELD, "doc1", Store.NO, Index.NOT_ANALYZED_NO_NORMS));
      doc.add(new Field(_defaultField, ntriple, Store.NO, Index.ANALYZED_NO_NORMS, TermVector.WITH_POSITIONS_OFFSETS));
      ramWriter.addDocument(doc);
      ramWriter.commit();
    }
    finally {
      if (ramWriter != null) ramWriter.close();
    }
  }

  private static void indexImplicit(final RAMDirectory ramDir, final String ntriple)
  throws CorruptIndexException, LockObtainFailedException, IOException {
    IndexWriter ramWriter = null;

    try {
      ramWriter = NTripleTestHelper.createRamIndexWriter(ramDir);

      final Document doc = new Document();
      doc.add(new Field(_ID_FIELD, "doc1", Store.NO, Index.ANALYZED_NO_NORMS));
      doc.add(new Field(_implicitField, ntriple, Store.NO, Index.ANALYZED_NO_NORMS, TermVector.WITH_POSITIONS_OFFSETS));
      ramWriter.addDocument(doc);
      ramWriter.commit();
    }
    finally {
      if (ramWriter != null) ramWriter.close();
    }
  }

  private static void index(final RAMDirectory ramDir,
                            final Map<String, String> ntriples)
  throws CorruptIndexException, LockObtainFailedException, IOException {
    IndexWriter ramWriter = null;

    try {
      ramWriter = NTripleTestHelper.createRamIndexWriter(ramDir);

      final Document doc = new Document();
      doc.add(new Field(_ID_FIELD, "doc1", Store.NO, Index.NOT_ANALYZED_NO_NORMS));
      for (final Entry<String, String> entry : ntriples.entrySet()) {
        doc.add(new Field(entry.getKey(), entry.getValue(), Store.NO, Index.ANALYZED_NO_NORMS));
      }
      ramWriter.addDocument(doc);
      ramWriter.commit();
    }
    finally {
      if (ramWriter != null) ramWriter.close();
    }
  }

  private static boolean match(final RAMDirectory ramDir, final String query,
                               final String field)
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    IndexSearcher ramSearcher = null;
    try {
      ramSearcher = NTripleTestHelper.createRamIndexSearcher(ramDir);
      final Query q = NTripleQueryParser.parse(query, matchVersion, field,
        new NTripleQueryAnalyzer(), tokenConfigMap, Operator.AND);
      logger.debug("{} = {}", query, q.toString());
      final int hits = ramSearcher.search(q, null, 100).totalHits;
      return (hits >= 1);
    } finally {
      if (ramSearcher != null) ramSearcher.close();
    }
  }

  private static boolean match(final RAMDirectory ramDir, final String query,
                               final Map<String, Float> boosts,
                               final boolean scattered)
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    IndexSearcher ramSearcher = null;
    try {
      ramSearcher = NTripleTestHelper.createRamIndexSearcher(ramDir);
      final Query q = NTripleQueryParser.parse(query, matchVersion, boosts, scattered,
        new NTripleQueryAnalyzer(), tokenConfigMap, Operator.AND);
      logger.debug("{} = {}", query, q.toString());
      final int hits = ramSearcher.search(q, null, 100).totalHits;
      return (hits >= 1);
    } finally {
      if (ramSearcher != null) ramSearcher.close();
    }
  }

  private static float getScore(final RAMDirectory ramDir, final String query,
                                  final Map<String, Float> boosts,
                                  final boolean scattered)
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    IndexSearcher ramSearcher = null;
    try {
      ramSearcher = NTripleTestHelper.createRamIndexSearcher(ramDir);
      final Query q = NTripleQueryParser.parse(query, matchVersion, boosts, scattered,
        new NTripleQueryAnalyzer(), tokenConfigMap, Operator.AND);
      logger.debug("{} = {}", query, q.toString());
      final ScoreDoc[] result = ramSearcher.search(q, null, 100).scoreDocs;
      assertEquals(1, result.length);
      return result[0].score;
    } finally {
      if (ramSearcher != null) ramSearcher.close();
    }
  }

}
