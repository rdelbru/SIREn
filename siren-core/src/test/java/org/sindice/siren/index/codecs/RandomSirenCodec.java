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
package org.sindice.siren.index.codecs;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.PostingsFormat;
import org.apache.lucene.codecs.lucene40.Lucene40Codec;
import org.apache.lucene.codecs.lucene40.Lucene40PostingsFormat;
import org.sindice.siren.index.codecs.siren10.Siren10AForPostingsFormat;
import org.sindice.siren.index.codecs.siren10.Siren10VIntPostingsFormat;
import org.sindice.siren.util.SirenTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomSirenCodec extends Lucene40Codec {

  final Random random;
  private final HashSet<String> sirenFields = new HashSet<String>();
  final PostingsFormat lucene40 = new Lucene40PostingsFormat();
  PostingsFormat defaultTestFormat;

  private static final int[] BLOCK_SIZES = new int[] {1, 2, 16, 32, 64, 128, 256, 512, 1024};

  public enum PostingsFormatType {
    RANDOM, SIREN_10
  }

  protected static final Logger logger = LoggerFactory.getLogger(RandomSirenCodec.class);

  public RandomSirenCodec(final Random random) {
    this(random, PostingsFormatType.RANDOM);
  }

  public RandomSirenCodec(final Random random, final PostingsFormatType formatType) {
    this.addSirenFields(SirenTestCase.DEFAULT_TEST_FIELD);
    this.random = random;
    this.defaultTestFormat = this.getPostingsFormat(formatType);
    Codec.setDefault(this);
  }

  public RandomSirenCodec(final Random random, final PostingsFormat format) {
    this.addSirenFields(SirenTestCase.DEFAULT_TEST_FIELD);
    this.random = random;
    this.defaultTestFormat = format;
    Codec.setDefault(this);
  }

  public void addSirenFields(final String... fields) {
    sirenFields.addAll(Arrays.asList(fields));
  }

  @Override
  public PostingsFormat getPostingsFormatForField(final String field) {
    if (sirenFields.contains(field)) {
      return defaultTestFormat;
    }
    else {
      return lucene40;
    }
  }

  @Override
  public String toString() {
    return "RandomSirenCodec[" + defaultTestFormat.toString() + "]";
  }

  private PostingsFormat getPostingsFormat(final PostingsFormatType formatType) {
    switch (formatType) {
      case RANDOM:
        return this.newRandomPostingsFormat();

      case SIREN_10:
        return this.newSiren10PostingsFormat();

      default:
        throw new InvalidParameterException();
    }
  }

  private PostingsFormat newSiren10PostingsFormat() {
    final int blockSize = this.newRandomBlockSize();
    final int i = random.nextInt(2);
    switch (i) {

      case 0:
        return new Siren10VIntPostingsFormat(blockSize);

      case 1:
        return new Siren10AForPostingsFormat(blockSize);

      default:
        throw new InvalidParameterException();
    }
  }

  private PostingsFormat newRandomPostingsFormat() {
    final int i = random.nextInt(1);
    switch (i) {

      case 0:
        return this.newSiren10PostingsFormat();

      default:
        throw new InvalidParameterException();
    }
  }

  private int newRandomBlockSize() {
    return BLOCK_SIZES[random.nextInt(BLOCK_SIZES.length)];
  }

}
