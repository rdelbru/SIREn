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

import org.junit.Test;
import org.sindice.siren.index.codecs.CodecTestCase;
import org.sindice.siren.index.codecs.siren10.DocsFreqBlockIndexInput;
import org.sindice.siren.index.codecs.siren10.DocsFreqBlockIndexInput.DocsFreqBlockReader;
import org.sindice.siren.index.codecs.siren10.DocsFreqBlockIndexOutput;
import org.sindice.siren.index.codecs.siren10.DocsFreqBlockIndexOutput.DocsFreqBlockWriter;
import org.sindice.siren.index.codecs.siren10.Siren10BlockStreamFactory;

public class TestVariableIntCodec extends CodecTestCase {

  private DocsFreqBlockIndexOutput getIndexOutput(final int blockSize) throws IOException {
    final Siren10BlockStreamFactory factory = new Siren10BlockStreamFactory(blockSize);
    factory.setDocsBlockCompressor(new VIntBlockCompressor());
    factory.setFreqBlockCompressor(new VIntBlockCompressor());
    return factory.createDocsFreqOutput(directory, "test", newIOContext(random()));
  }

  private DocsFreqBlockIndexInput getIndexInput() throws IOException {
    final Siren10BlockStreamFactory factory = new Siren10BlockStreamFactory(0);
    factory.setDocsBlockDecompressor(new VIntBlockDecompressor());
    factory.setFreqBlockDecompressor(new VIntBlockDecompressor());
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

  @Test
  public void testIntegerRange() throws Exception {
    this.doTestIntegerRange(1, 32, new VIntBlockCompressor(), new VIntBlockDecompressor());
  }

}
