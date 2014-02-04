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

import org.sindice.siren.index.codecs.block.AForBlockCompressor;
import org.sindice.siren.index.codecs.block.AForBlockDecompressor;

/**
 * Implementation of the {@link Siren10PostingsFormat} based on AFOR.
 */
public class Siren10AForPostingsFormat extends Siren10PostingsFormat {

  public static final String NAME = "Siren10AFor";

  public Siren10AForPostingsFormat() {
    super(NAME);
  }

  /**
   * Create a SIREn 1.0 posting format with VInt codec
   * <p>
   * The block size parameter is used only during indexing.
   */
  public Siren10AForPostingsFormat(final int blockSize) {
    super(NAME, blockSize);
  }

  @Override
  protected Siren10BlockStreamFactory getFactory() {
    final Siren10BlockStreamFactory factory = new Siren10BlockStreamFactory(blockSize);
    factory.setDocsBlockCompressor(new AForBlockCompressor());
    factory.setFreqBlockCompressor(new AForBlockCompressor());
    factory.setNodBlockCompressor(new AForBlockCompressor());
    factory.setPosBlockCompressor(new AForBlockCompressor());
    factory.setDocsBlockDecompressor(new AForBlockDecompressor());
    factory.setFreqBlockDecompressor(new AForBlockDecompressor());
    factory.setNodBlockDecompressor(new AForBlockDecompressor());
    factory.setPosBlockDecompressor(new AForBlockDecompressor());
    return factory;
  }

}
