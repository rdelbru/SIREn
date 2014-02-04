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
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.PostingsFormat;
import org.apache.lucene.codecs.lucene40.Lucene40Codec;
import org.apache.lucene.codecs.lucene40.Lucene40PostingsFormat;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.JsonTokenizer;
import org.sindice.siren.analysis.filter.DatatypeAnalyzerFilter;
import org.sindice.siren.analysis.filter.PositionAttributeFilter;
import org.sindice.siren.analysis.filter.SirenPayloadFilter;
import org.sindice.siren.index.codecs.siren10.Siren10AForPostingsFormat;

/**
 * This class shows how to configure the SIREn codec for indexing JSON data
 * into a particular field.
 */
public class SimpleIndexer {

  private final Directory dir;
  private final IndexWriter writer;

  public static final String DEFAULT_ID_FIELD = "id";
  public static final String DEFAULT_SIREN_FIELD = "siren-field";

  public SimpleIndexer(final File path) throws IOException {
    dir = FSDirectory.open(path);
    writer = this.initializeIndexWriter();
  }

  public void close() throws IOException {
    writer.close();
    dir.close();
  }

  private IndexWriter initializeIndexWriter() throws IOException {
    final IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40,
      this.initializeAnalyzer());

    // Register the SIREn codec
    config.setCodec(new Siren10Codec());

    return new IndexWriter(dir, config);
  }

  private Analyzer initializeAnalyzer() {
    return new Analyzer() {

      @Override
      protected TokenStreamComponents createComponents(final String fieldName,
                                                       final Reader reader) {
        final Version matchVersion = Version.LUCENE_40;
        final JsonTokenizer src = new JsonTokenizer(reader);
        TokenStream tok = new DatatypeAnalyzerFilter(matchVersion, src,
          new StandardAnalyzer(matchVersion),
          new StandardAnalyzer(matchVersion));
        tok = new LowerCaseFilter(matchVersion, tok);
        // The PositionAttributeFilter and SirenPayloadFilter are mandatory
        // and must be always the last filters in your token stream
        tok = new PositionAttributeFilter(tok);
        tok = new SirenPayloadFilter(tok);
        return new TokenStreamComponents(src, tok);
      }

    };
  }

  public void addDocument(final String id, final String json)
  throws IOException {
    final Document doc = new Document();

    doc.add(new StringField(DEFAULT_ID_FIELD, id, Store.YES));

    final FieldType sirenFieldType = new FieldType();
    sirenFieldType.setIndexed(true);
    sirenFieldType.setTokenized(true);
    sirenFieldType.setOmitNorms(true);
    sirenFieldType.setStored(false);
    sirenFieldType.setStoreTermVectors(false);

    doc.add(new Field(DEFAULT_SIREN_FIELD, json, sirenFieldType));

    writer.addDocument(doc);
  }

  public void commit() throws IOException {
    writer.commit();
  }

  /**
   * Simple example of a SIREn codec that will use the SIREn posting format
   * for a given field.
   */
  private class Siren10Codec extends Lucene40Codec {

    final PostingsFormat lucene40 = new Lucene40PostingsFormat();
    PostingsFormat defaultTestFormat = new Siren10AForPostingsFormat();

    public Siren10Codec() {
      Codec.setDefault(this);
    }

    @Override
    public PostingsFormat getPostingsFormatForField(final String field) {
      if (field.equals(DEFAULT_SIREN_FIELD)) {
        return defaultTestFormat;
      }
      else {
        return lucene40;
      }
    }

    @Override
    public String toString() {
      return "Siren10Codec[" + defaultTestFormat.toString() + "]";
    }

  }

}
