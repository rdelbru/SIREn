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
package org.sindice.siren.demo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.sindice.siren.qparser.json.JsonQueryParser;
import org.sindice.siren.qparser.keyword.KeywordQueryParser;
import org.sindice.siren.util.JSONDatatype;
import org.sindice.siren.util.XSDDatatype;

/**
 * This class shows how to use the SIREn's parsers to search over JSON data.
 * <p>
 * There is no specific configuration required for searching over fields indexed
 * with the SIREn's posting format.
 */
public class SimpleSearcher {

  private final Directory dir;

  private final SearcherManager mgr;

  public SimpleSearcher(final File path) throws IOException {
    dir = FSDirectory.open(path);
    mgr = new SearcherManager(dir, null);
  }

  public void close() throws IOException {
    mgr.close();
    dir.close();
  }

  public String[] search(final Query q, final int n) throws IOException {
    IndexSearcher searcher = mgr.acquire();
    try {
      final ScoreDoc[] results = searcher.search(q, null, n).scoreDocs;
      final String[] ids = new String[results.length];

      for (int i = 0; i < results.length; i++) {
        ids[i] = this.retrieve(results[i].doc).get(SimpleIndexer.DEFAULT_ID_FIELD);
      }

      return ids;
    }
    finally {
      mgr.release(searcher);
      searcher = null;
    }
  }

  public Document retrieve(final int docID) throws IOException {
    IndexSearcher searcher = mgr.acquire();

    try {
      return searcher.doc(docID);
    }
    finally {
      mgr.release(searcher);
      searcher = null;
    }
  }

  public Query parseKeywordQuery(final String keywordQuery) throws QueryNodeException {
    final KeywordQueryParser parser = new KeywordQueryParser();
    parser.setDatatypeAnalyzers(this.getDatatypeAnalyzers());
    return parser.parse(keywordQuery, SimpleIndexer.DEFAULT_SIREN_FIELD);
  }

  public Query parseJsonQuery(final String JsonQuery) throws QueryNodeException {
    final JsonQueryParser parser = new JsonQueryParser();
    final KeywordQueryParser kParser = new KeywordQueryParser();
    kParser.setDatatypeAnalyzers(this.getDatatypeAnalyzers());
    kParser.setAllowTwig(false);
    parser.setKeywordQueryParser(kParser);
    return parser.parse(JsonQuery, SimpleIndexer.DEFAULT_SIREN_FIELD);
  }

  private Map<String, Analyzer> getDatatypeAnalyzers() {
    final Map<String, Analyzer> analyzers = new HashMap<String, Analyzer>();
    analyzers.put(XSDDatatype.XSD_STRING, new StandardAnalyzer(Version.LUCENE_40));
    analyzers.put(JSONDatatype.JSON_FIELD, new WhitespaceAnalyzer(Version.LUCENE_40));
    return analyzers;
  }

}
