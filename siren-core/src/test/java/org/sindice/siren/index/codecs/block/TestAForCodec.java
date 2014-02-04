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

package org.sindice.siren.index.codecs.block;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;
import org.junit.Test;
import org.sindice.siren.index.codecs.CodecTestCase;
import org.sindice.siren.index.codecs.siren10.DocsFreqBlockIndexInput;
import org.sindice.siren.index.codecs.siren10.DocsFreqBlockIndexInput.DocsFreqBlockReader;
import org.sindice.siren.index.codecs.siren10.DocsFreqBlockIndexOutput;
import org.sindice.siren.index.codecs.siren10.DocsFreqBlockIndexOutput.DocsFreqBlockWriter;
import org.sindice.siren.index.codecs.siren10.Siren10BlockStreamFactory;

public class TestAForCodec extends CodecTestCase {

  private DocsFreqBlockIndexOutput getIndexOutput(final int blockSize) throws IOException {
    final Siren10BlockStreamFactory factory = new Siren10BlockStreamFactory(blockSize);
    factory.setDocsBlockCompressor(new AForBlockCompressor());
    factory.setFreqBlockCompressor(new AForBlockCompressor());
    return factory.createDocsFreqOutput(directory, "test", newIOContext(random()));
  }

  private DocsFreqBlockIndexInput getIndexInput() throws IOException {
    final Siren10BlockStreamFactory factory = new Siren10BlockStreamFactory(0);
    factory.setDocsBlockDecompressor(new AForBlockDecompressor());
    factory.setFreqBlockDecompressor(new AForBlockDecompressor());
    return factory.openDocsFreqInput(directory, "test", newIOContext(random()));
  }

  public void testReadDoc() throws IOException {

    final DocsFreqBlockIndexOutput out = this.getIndexOutput(512);
    final DocsFreqBlockWriter writer = out.getBlockWriter();

    writer.setNodeBlockIndex(out.index());
    writer.setPosBlockIndex(out.index());
    for (int i = 0; i < 11777; i++) {
      if (writer.isFull()) {
        writer.flush();
      }
      writer.write(i);
    }

    writer.flush(); // flush remaining data
    out.close();

    final DocsFreqBlockIndexInput in = this.getIndexInput();
    final DocsFreqBlockReader reader = in.getBlockReader();

    reader.setNodeBlockIndex(in.index());
    reader.setPosBlockIndex(in.index());
    for (int i = 0; i < 11777; i++) {
      if (reader.isExhausted()) {
        reader.nextBlock();
      }
      assertEquals(i, reader.nextDocument());
    }

    in.close();
  }

  public void testReadDocAndFreq() throws IOException {

    final DocsFreqBlockIndexOutput out = this.getIndexOutput(512);
    final DocsFreqBlockWriter writer = out.getBlockWriter();

    writer.setNodeBlockIndex(out.index());
    writer.setPosBlockIndex(out.index());
    for (int i = 0; i < 11777; i++) {
      if (writer.isFull()) {
        writer.flush();
      }
      writer.write(i);
      writer.writeNodeFreq(random().nextInt(10) + 1);
    }

    writer.flush(); // flush remaining data
    out.close();

    final DocsFreqBlockIndexInput in = this.getIndexInput();
    final DocsFreqBlockReader reader = in.getBlockReader();

    reader.setNodeBlockIndex(in.index());
    reader.setPosBlockIndex(in.index());
    for (int i = 0; i < 11777; i++) {
      if (reader.isExhausted()) {
        reader.nextBlock();
      }
      assertEquals(i, reader.nextDocument());
      final int frq = reader.nextNodeFreq();
      assertTrue(frq > 0);
      assertTrue(frq <= 10);
    }

    in.close();
  }

  public void testRandomDoc() throws IOException {

    final int blockSize = BLOCK_SIZES[random().nextInt(BLOCK_SIZES.length)];
    final DocsFreqBlockIndexOutput out = this.getIndexOutput(blockSize);
    final DocsFreqBlockWriter writer = out.getBlockWriter();

    writer.setNodeBlockIndex(out.index());
    writer.setPosBlockIndex(out.index());

    // generate doc ids
    final Set<Integer> docIds = new TreeSet<Integer>();
    final int lenght = (int) this.nextLong(128000, 512000);
    for (int i = 0; i < lenght; i++) {
      docIds.add((int) this.nextLong(0, ((1L << 31) - 1)));
    }

    for (final int docId : docIds) {
      if (writer.isFull()) {
        writer.flush();
      }
      writer.write(docId);
    }

    writer.flush(); // flush remaining data
    out.close();

    final DocsFreqBlockIndexInput in = this.getIndexInput();
    final DocsFreqBlockReader reader = in.getBlockReader();

    reader.setNodeBlockIndex(in.index());
    reader.setPosBlockIndex(in.index());
    for (final int docId : docIds) {
      if (reader.isExhausted()) {
        reader.nextBlock();
      }
      assertEquals(docId, reader.nextDocument());
    }

    in.close();
  }

  @Test
  public void testIntegerRange() throws Exception {
    this.doTestIntegerRange(1, 32, new AForBlockCompressor(), new AForBlockDecompressor());
  }

  @Test
  public void testShortPostingList() throws IOException {

    final DocsFreqBlockIndexOutput out = this.getIndexOutput(512);
    final DocsFreqBlockWriter writer = out.getBlockWriter();

    writer.setNodeBlockIndex(out.index());
    writer.setPosBlockIndex(out.index());
    for (int i = 0; i < 5; i++) {
      if (writer.isFull()) {
        writer.flush();
      }
      writer.write(i);
    }

    writer.flush(); // flush remaining data
    out.close();

    final DocsFreqBlockIndexInput in = this.getIndexInput();
    final DocsFreqBlockReader reader = in.getBlockReader();

    reader.setNodeBlockIndex(in.index());
    reader.setPosBlockIndex(in.index());
    for (int i = 0; i < 5; i++) {
      if (reader.isExhausted()) {
        reader.nextBlock();
      }
      assertEquals(i, reader.nextDocument());
    }

    in.close();
  }

  @Test
  public void testIncompleteFrame() throws IOException {
    final BlockCompressor compressor = new AForBlockCompressor();

    final IntsRef input = new IntsRef(64);
    final BytesRef output = new BytesRef(compressor.maxCompressedSize(64));

    // fill first part with 1
    for (int i = 0; i < 33; i++) {
      input.ints[i] = 1;
    }

    // fill the rest with random numbers
    for (int i = 33; i < 64; i++) {
      input.ints[i] = (int) this.nextLong(64, Short.MAX_VALUE);
    }

    input.offset = 0;
    input.length = 33;

    // the random numbers after the end of the input array should not impact
    // compression
    compressor.compress(input, output);

    // should be frame code 1 : 32 ints encoded with 1 bits
    assertEquals(1, output.bytes[0]);
    // followed by 4 bytes at 255
    assertEquals(0xFF, output.bytes[1] & 0xFF);
    assertEquals(0xFF, output.bytes[2] & 0xFF);
    assertEquals(0xFF, output.bytes[3] & 0xFF);
    assertEquals(0xFF, output.bytes[4] & 0xFF);
    // then frame code 34 : 16 ints encoded with 1 bits
    assertEquals(34, output.bytes[5]);
    // followed by 1 byte with at least 128 and a second byte with 0
    assertEquals(128, output.bytes[6] & 0x80);
    assertEquals(0, output.bytes[7] & 0xFF);
    // followed by frame code 33: 16 ints encoded with 0 bits
    assertEquals(33, output.bytes[8]);
  }

}
