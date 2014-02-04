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
package org.sindice.siren.index.codecs.siren10;

import static org.sindice.siren.analysis.MockSirenDocument.doc;
import static org.sindice.siren.analysis.MockSirenToken.node;
import static org.sindice.siren.analysis.MockSirenToken.token;

import java.io.IOException;

import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.LuceneTestCase;
import org.junit.Test;
import org.sindice.siren.analysis.MockSirenDocument;
import org.sindice.siren.index.DocsAndNodesIterator;
import org.sindice.siren.index.codecs.RandomSirenCodec.PostingsFormatType;
import org.sindice.siren.index.codecs.siren10.Siren10PostingsReader.Siren10DocsEnum;
import org.sindice.siren.index.codecs.siren10.Siren10PostingsReader.Siren10DocsNodesAndPositionsEnum;
import org.sindice.siren.util.BasicSirenTestCase;

public class TestSiren10PostingsFormat extends BasicSirenTestCase {

  @Override
  protected void configure() throws IOException {
    this.setAnalyzer(AnalyzerType.MOCK);
    this.setPostingsFormat(PostingsFormatType.SIREN_10);
  }

  @Test
  public void testSimpleNextDocument() throws IOException {
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0))),
      doc(token("aaa", node(5,3,6,3)), token("bbb", node(5,3,6,3,7)))
    );

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, "aaa"));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();
    assertEquals(-1, e.doc());
    assertEquals(0, e.nodeFreqInDoc());
    assertTrue(e.nextDocument());
    assertEquals(0, e.doc());
    assertEquals(2, e.nodeFreqInDoc());
    assertTrue(e.nextDocument());
    assertEquals(1, e.doc());
    assertEquals(1, e.nodeFreqInDoc());
    assertTrue(e.nextDocument());
    assertEquals(2, e.doc());
    assertEquals(1, e.nodeFreqInDoc());

    assertFalse(e.nextDocument());
    assertEquals(DocsAndNodesIterator.NO_MORE_DOC, e.doc());
  }

  @Test
  public void testSkipDoc() throws IOException {
    final MockSirenDocument[] docs = new MockSirenDocument[2048];
    for (int i = 0; i < 2048; i += 4) {
      docs[i] = doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2)));
      docs[i + 1] = doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0)));
      docs[i + 2] = doc(token("aaa", node(5,3,6,3)), token("bbb", node(5,3,6,3,7)));
      docs[i + 3] = doc(token("bbb", node(2,0)), token("aaa", node(5,3,6)));
    }
    this.addDocuments(docs);

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();

    // first skip in skiplist is at 512
    assertTrue(e.skipTo(502));
    assertEquals(502, e.doc());
    assertEquals(1, e.nodeFreqInDoc());

    // must have used the second skip
    assertTrue(e.skipTo(1624));
    assertEquals(1624, e.doc());
    assertEquals(2, e.nodeFreqInDoc());

    // no other skip, must have used the linear scan
    assertTrue(e.skipTo(2000));
    assertEquals(2000, e.doc());
    assertEquals(2, e.nodeFreqInDoc());

    assertFalse(e.skipTo(256323));

  }

  @Test
  public void testSimpleNextNode() throws IOException {
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0))),
      doc(token("aaa", node(5,3,6,3)), token("bbb", node(5,3,6,3,7)))
    );

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();
    assertEquals(-1, e.doc());
    assertEquals(0, e.nodeFreqInDoc());
    assertEquals(node(-1), e.node());

    assertTrue(e.nextDocument());
    assertEquals(0, e.doc());
    assertEquals(2, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(node(1), e.node());
    assertTrue(e.nextNode());
    assertEquals(node(2), e.node());
    assertFalse(e.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, e.node());

    assertTrue(e.nextDocument());
    assertEquals(1, e.doc());
    assertEquals(1, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(node(1,0), e.node());
    assertFalse(e.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, e.node());

    assertTrue(e.nextDocument());
    assertEquals(2, e.doc());
    assertEquals(1, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(node(5,3,6,3), e.node());
    assertFalse(e.nextNode());
    assertEquals(DocsAndNodesIterator.NO_MORE_NOD, e.node());

    assertFalse(e.nextDocument());
    assertEquals(DocsAndNodesIterator.NO_MORE_DOC, e.doc());
  }

  @Test
  public void testSimpleSkipNode() throws IOException {
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2))),
      doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0))),
      doc(token("aaa", node(5,3,6,3)), token("bbb", node(5,3,6,3,7)))
    );

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();
    assertEquals(-1, e.doc());
    assertEquals(0, e.nodeFreqInDoc());

    // skip to 2 using linear scan. Node should be also be skipped.
    assertTrue(e.skipTo(2));
    assertEquals(2, e.doc());
    assertEquals(1, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(node(5,3,6,3), e.node());
    assertFalse(e.nextNode());

    assertFalse(e.nextDocument());
  }

  @Test
  public void testSkipNode() throws IOException {
    final MockSirenDocument[] docs = new MockSirenDocument[2048];
    for (int i = 0; i < 2048; i += 4) {
      docs[i] = doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2)));
      docs[i + 1] = doc(token("aaa", node(1,0)), token("bbb", node(1,0,1,0)));
      docs[i + 2] = doc(token("aaa", node(5,3,6,3)), token("bbb", node(5,3,6,3,7)));
      docs[i + 3] = doc(token("bbb", node(2,0)), token("aaa", node(5,3,6)));
    }
    this.addDocuments(docs);

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();

    // first skip in skiplist is at 512
    assertTrue(e.skipTo(502));
    assertEquals(502, e.doc());
    assertEquals(1, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(node(5,3,6,3), e.node());
    assertFalse(e.nextNode());

    // skip to 504 and scan partially nodes
    assertTrue(e.nextDocument());
    assertTrue(e.nextDocument());
    assertEquals(504, e.doc());
    assertEquals(2, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(node(1), e.node());

    // must have used the second skip
    assertTrue(e.skipTo(1624));
    assertEquals(1624, e.doc());
    assertEquals(2, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(node(1), e.node());
    assertTrue(e.nextNode());
    assertEquals(node(2), e.node());
    assertFalse(e.nextNode());

    // no other skip, must have used the linear scan
    assertTrue(e.skipTo(2000));
    assertEquals(2000, e.doc());
    assertEquals(2, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(node(1), e.node());
    assertTrue(e.nextNode());
    assertEquals(node(2), e.node());
    assertFalse(e.nextNode());

    assertFalse(e.skipTo(256323));

  }

  @Test
  public void testSimpleNextPosition() throws IOException {
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2))),
      doc(token("bbb", node(1,0)), token("bbb", node(1,0,1,0))),
      doc(token("bbb", node(5,3,6)), token("aaa", node(5,3,6,3)), token("aaa", node(5,3,6,3)))
    );

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();
    assertEquals(-1, e.doc());
    assertEquals(0, e.nodeFreqInDoc());
    assertEquals(node(-1), e.node());
    assertEquals(-1, e.pos());

    assertTrue(e.nextDocument());
    assertEquals(0, e.doc());
    assertEquals(2, e.nodeFreqInDoc());

    assertTrue(e.nextNode());
    assertEquals(node(1), e.node());
    assertEquals(1, e.termFreqInNode());

    assertTrue(e.nextPosition());
    assertEquals(0, e.pos());
    assertFalse(e.nextPosition());

    assertTrue(e.nextNode());
    assertEquals(node(2), e.node());
    assertEquals(1, e.termFreqInNode());

    assertTrue(e.nextPosition());
    assertEquals(0, e.pos());
    assertFalse(e.nextPosition());

    assertFalse(e.nextNode());

    assertTrue(e.nextDocument());
    assertEquals(2, e.doc());
    assertEquals(1, e.nodeFreqInDoc());

    assertTrue(e.nextNode());
    assertEquals(node(5,3,6,3), e.node());
    assertEquals(2, e.termFreqInNode());

    assertTrue(e.nextPosition());
    assertEquals(0, e.pos());
    assertTrue(e.nextPosition());
    assertEquals(1, e.pos());
    assertFalse(e.nextPosition());

    assertFalse(e.nextNode());

    assertFalse(e.nextDocument());
  }

  @Test
  public void testSimpleFrequencies() throws IOException {
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2))),
      doc(token("aaa", node(1)), token("aaa", node(1)), token("aaa", node(2)))
    );

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();
    assertEquals(-1, e.doc());

    // freqs should be set to 0 at the beginning
    assertEquals(0, e.nodeFreqInDoc());
    assertEquals(0, e.termFreqInNode());

    // nodeFreqInDoc should be set after calling nextDocument
    assertTrue(e.nextDocument());
    assertEquals(2, e.nodeFreqInDoc());
    // termFreqInNode should be set to 0
    assertEquals(0, e.termFreqInNode());
    // calling termFreqInNode should not change the freq settings
    assertEquals(2, e.nodeFreqInDoc());

    // termFreqInNode should be set after calling nextNode
    assertTrue(e.nextNode());
    // nodeFreqInDoc and nodeFreqInDoc should not have changed of settings
    assertEquals(2, e.nodeFreqInDoc());
    // termFreqInNode should be set to 1
    assertEquals(1, e.termFreqInNode());
    // calling termFreqInNode should not change the freqs settings
    assertEquals(2, e.nodeFreqInDoc());

    // calling nextPosition should not change freqs settings
    assertTrue(e.nextPosition());
    assertEquals(2, e.nodeFreqInDoc());
    assertEquals(1, e.termFreqInNode());

    // partially scanned position should not have consequences on nodeFreqInDoc
    // settings
    assertTrue(e.nextDocument());
    assertEquals(2, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(2, e.termFreqInNode());
    assertTrue(e.nextPosition());
    assertEquals(2, e.termFreqInNode());
    assertTrue(e.nextNode());
    assertEquals(1, e.termFreqInNode());
  }

  @Test
  public void testSimpleMerge() throws IOException {
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2)))
    );
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2)))
    );

    this.forceMerge();

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();

    assertTrue(e.nextDocument());
    assertEquals(0, e.doc());
    assertEquals(2, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(1, e.termFreqInNode());
    assertTrue(e.nextPosition());
    assertEquals(0, e.pos());
    assertTrue(e.nextNode());
    assertEquals(1, e.termFreqInNode());
    assertTrue(e.nextPosition());
    assertEquals(0, e.pos());

    assertTrue(e.nextDocument());
    assertEquals(1, e.doc());
    assertEquals(2, e.nodeFreqInDoc());
    assertTrue(e.nextNode());
    assertEquals(1, e.termFreqInNode());
    assertTrue(e.nextPosition());
    assertEquals(0, e.pos());
    assertTrue(e.nextNode());
    assertEquals(1, e.termFreqInNode());
    assertTrue(e.nextPosition());
    assertEquals(0, e.pos());
  }

  @Test
  public void testMergeBlockSize() throws IOException {
    // reduce block size
    this.setPostingsFormat(new Siren10VIntPostingsFormat(2));

    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0))),
      doc(token("aaa", node(1)), token("bbb", node(1,0))),
      doc(token("aaa", node(1)), token("bbb", node(1,0)))
    );

    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0))),
      doc(token("aaa", node(1)), token("bbb", node(1,0)))
    );

    this.forceMerge();
  }

  @Test
  public void testStressMerge() throws IOException {
    this.addDocuments(
      doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2)))
    );

    while (this.reader.numDocs() < 10000) {
      final int batchSize = LuceneTestCase.random().nextInt(20);
      final MockSirenDocument[] docs = new MockSirenDocument[batchSize];
      for (int i = 0; i < batchSize; i++) {
        docs[i] = doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2)));
      }
      this.addDocuments(docs);
      this.forceMerge();
    }

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();

    for (int i = 0; i < reader.numDocs(); i++) {
      assertTrue(e.nextDocument());
      assertEquals(i, e.doc());
      assertEquals(2, e.nodeFreqInDoc());

      assertTrue(e.nextNode());
      assertEquals(node(1), e.node());
      assertEquals(1, e.termFreqInNode());
      assertTrue(e.nextPosition());
      assertEquals(0, e.pos());

      assertTrue(e.nextNode());
      assertEquals(node(2), e.node());
      assertEquals(1, e.termFreqInNode());
      assertTrue(e.nextPosition());
      assertEquals(0, e.pos());

      assertFalse(e.nextNode());
    }
  }

  @Test
  public void testSkipDataCheckIndex() throws IOException {
    // The Lucene CheckIndex was catching a problem with how skip data level
    // were computed on this configuration.
    this.setPostingsFormat(new Siren10VIntPostingsFormat(256));

    final MockSirenDocument[] docs = new MockSirenDocument[1000];

    for (int i = 0; i < 1000; i++) {
     docs[i] = doc(token("aaa", node(1)), token("bbb", node(1,0)), token("aaa", node(2)));
    }
    this.addDocuments(docs);

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
  }

  @Test
  public void testDeltaNode() throws IOException {
    final MockSirenDocument[] docs = new MockSirenDocument[2048];
    for (int i = 0; i < 2048; i += 2) {
      docs[i] = doc(token("aaa", node(1,1)), token("aaa", node(2,1)), token("aaa", node(2,5)));
      docs[i + 1] = doc(token("aaa", node(5,3,1)), token("aaa", node(5,3,6,3)),
                        token("aaa", node(5,3,6,5)), token("aaa", node(6)));
    }
    this.addDocuments(docs);

    final AtomicReader aReader = SlowCompositeReaderWrapper.wrap(reader);
    final DocsEnum docsEnum = aReader.termDocsEnum(new Term(DEFAULT_TEST_FIELD, new BytesRef("aaa")));
    assertTrue(docsEnum instanceof Siren10DocsEnum);
    final Siren10DocsNodesAndPositionsEnum e = ((Siren10DocsEnum) docsEnum).getDocsNodesAndPositionsEnum();

    for (int i = 0; i < 2048; i += 2) {
      assertTrue(e.nextDocument());
      assertTrue(e.nextNode());
      assertEquals(node(1,1), e.node());
      assertTrue(e.nextNode());
      assertEquals(node(2,1), e.node());
      assertTrue(e.nextNode());
      assertEquals(node(2,5), e.node());
      assertTrue(e.nextDocument());
      assertTrue(e.nextNode());
      assertEquals(node(5,3,1), e.node());
      assertTrue(e.nextNode());
      assertEquals(node(5,3,6,3), e.node());
      assertTrue(e.nextNode());
      assertEquals(node(5,3,6,5), e.node());
      assertTrue(e.nextNode());
      assertEquals(node(6), e.node());
    }
  }

}
