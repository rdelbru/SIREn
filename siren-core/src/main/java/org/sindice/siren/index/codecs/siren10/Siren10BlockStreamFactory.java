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

import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.sindice.siren.index.codecs.block.BlockCompressor;
import org.sindice.siren.index.codecs.block.BlockDecompressor;
import org.sindice.siren.index.codecs.block.BlockIndexInput;
import org.sindice.siren.index.codecs.block.BlockIndexOutput;

/**
 * This class creates {@link BlockIndexOutput} and {@link BlockIndexInput}
 * for the SIREn 1.0 postings format.
 */
public class Siren10BlockStreamFactory {

  private final int blockSize;

  private BlockCompressor docsBlockCompressor;
  private BlockDecompressor docsBlockDecompressor;
  private BlockCompressor freqBlockCompressor;
  private BlockDecompressor freqBlockDecompressor;
  private BlockCompressor nodBlockCompressor;
  private BlockDecompressor nodBlockDecompressor;
  private BlockCompressor posBlockCompressor;
  private BlockDecompressor posBlockDecompressor;

  public Siren10BlockStreamFactory(final int blockSize) {
    this.blockSize = blockSize;
  }

  public void setDocsBlockCompressor(final BlockCompressor compressor) {
    this.docsBlockCompressor = compressor;
  }

  public void setDocsBlockDecompressor(final BlockDecompressor decompressor) {
    this.docsBlockDecompressor = decompressor;
  }

  public void setFreqBlockCompressor(final BlockCompressor compressor) {
    this.freqBlockCompressor = compressor;
  }

  public void setFreqBlockDecompressor(final BlockDecompressor decompressor) {
    this.freqBlockDecompressor = decompressor;
  }

  public void setNodBlockCompressor(final BlockCompressor compressor) {
    this.nodBlockCompressor = compressor;
  }

  public void setNodBlockDecompressor(final BlockDecompressor decompressor) {
    this.nodBlockDecompressor = decompressor;
  }

  public void setPosBlockCompressor(final BlockCompressor compressor) {
    this.posBlockCompressor = compressor;
  }

  public void setPosBlockDecompressor(final BlockDecompressor decompressor) {
    this.posBlockDecompressor = decompressor;
  }

  public DocsFreqBlockIndexOutput createDocsFreqOutput(final Directory dir,
                                                       final String fileName,
                                                       final IOContext context)
  throws IOException {
    return new DocsFreqBlockIndexOutput(
      dir.createOutput(fileName, context),
      blockSize,
      docsBlockCompressor, freqBlockCompressor);
  }

  public DocsFreqBlockIndexInput openDocsFreqInput(final Directory dir,
                                                   final String fileName,
                                                   final IOContext context)
  throws IOException {
    return new DocsFreqBlockIndexInput(
      dir.openInput(fileName, context),
      docsBlockDecompressor, freqBlockDecompressor);
  }

  public NodBlockIndexOutput createNodOutput(final Directory dir,
                                             final String fileName,
                                             final IOContext context)
  throws IOException {
    return new NodBlockIndexOutput(
      dir.createOutput(fileName, context),
      blockSize,
      nodBlockCompressor);
  }

  public NodBlockIndexInput openNodInput(final Directory dir,
                                         final String fileName,
                                         final IOContext context)
  throws IOException {
    return new NodBlockIndexInput(
      dir.openInput(fileName, context),
      nodBlockDecompressor);
  }

  public PosBlockIndexOutput createPosOutput(final Directory dir,
                                             final String fileName,
                                             final IOContext context)
  throws IOException {
    return new PosBlockIndexOutput(
      dir.createOutput(fileName, context),
      blockSize,
      posBlockCompressor);
  }

  public PosBlockIndexInput openPosInput(final Directory dir,
                                           final String fileName,
                                           final IOContext context)
  throws IOException {
    return new PosBlockIndexInput(
      dir.openInput(fileName, context),
      posBlockDecompressor);
  }

}
