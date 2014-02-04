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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to generate {@link AForFrameCompressor}.
 */
public class AForFrameCompressorGenerator {

  private FileWriter writer;
  private final int[] frameSizes = new int[99];

  public AForFrameCompressorGenerator() throws IOException {
  }

  public void generate(final File file) throws IOException {
    writer = new FileWriter(file);
    writer.append(FILE_HEADER); writer.append('\n');
    this.generateClass();
    writer.close();
  }

  protected void generateClass() throws IOException {
    writer.append("/**\n");
    writer.append(" * This class contains a lookup table of functors for compressing fames.\n");
    writer.append(" */\n");
    writer.append("public class AForFrameCompressor {\n\n");
    this.generateFrameSizeTable();
    this.generateTable();
    this.generateAbstractInnerClass();
    for (int i = 0; i < 99; i++) {
      this.generateInnerClass(i);
    }
    writer.append("}\n");
  }

  protected void generateFrameSizeTable() throws IOException {
    int frameSize = 0;
    frameSizes[0] = 0;
    for (int i = 1; i <= 32; i++) {
      frameSize += 4;
      frameSizes[i] = frameSize;
    }
    frameSizes[33] = 0;
    frameSize = 0;
    for (int i = 1; i <= 32; i++) {
      frameSize += 2;
      frameSizes[i+33] = frameSize;
    }
    frameSizes[66] = 0;
    for (int i = 1; i <= 32; i++) {
      frameSizes[i+66] = i;
    }
  }

  protected void generateTable() throws IOException {
    writer.append("  public static final FrameCompressor[] compressors = new FrameCompressor[] {\n");
    for (int i = 0; i < 99; i++) {
      writer.append("    new FrameCompressor"+i+"(),\n");
    }
    writer.append("  };\n\n");
  }

  protected void generateAbstractInnerClass() throws IOException {
    writer.append("  static abstract class FrameCompressor {\n");
    writer.append("    public abstract void compress(final IntsRef input, final BytesRef output);\n");
    writer.append("  }\n\n");
  }

  protected void generateInnerClass(final int numFramebits) throws IOException {
    writer.append("  static final class FrameCompressor" + Integer.toString(numFramebits) + " extends FrameCompressor {\n");
    this.generateMethod(numFramebits);
    writer.append("  }\n\n");
  }

  protected void generateMethodHeader() throws IOException {
    writer.append("      ");
    writer.append("final int[] unCompressedData = input.ints;\n");
    writer.append("      ");
    writer.append("final byte[] compressedArray = output.bytes;\n");
    writer.append("      ");
    writer.append("final int outputOffset = output.offset + 1;\n");
    writer.append("      ");
    writer.append("final int offset = input.offset;\n");
  }

  protected void generateMethodFooter(final int numFramebits) throws IOException {
    writer.append("      ");
    if (numFramebits <= 32)
      writer.append("input.offset += 32;\n");
    else if (numFramebits <= 65)
      writer.append("input.offset += 16;\n");
    else
      writer.append("input.offset += 8;\n");
    writer.append("      ");
    writer.append("output.offset += " + (1 + frameSizes[numFramebits]) + ";\n");
  }

  protected void generateMethod(final int numFrameBits) throws IOException {
    writer.append("    public final void compress(final IntsRef input, final BytesRef output) {\n");
    if (numFrameBits == 0 || numFrameBits == 33 || numFrameBits == 66) {
      writer.append("      ");
      writer.append("output.offset += 1;\n");
      writer.append("      ");
      writer.append("input.offset += " + (numFrameBits == 0 ? 32 : (numFrameBits == 33 ? 16 : 8)) + ";\n");
      writer.append("  }\n");
      return;
    }
    this.generateMethodHeader();
    if (numFrameBits <= 32)
      this.generateIntValues(32);
    else if (numFrameBits <= 65)
      this.generateIntValues(16);
    else
      this.generateIntValues(8);
    if (numFrameBits <= 32) {
      if (numFrameBits <= 8) {
        this.generateInstructions1to8(numFrameBits, 32);
      }
      else {
        this.generateInstructions9to32(numFrameBits, 32);
      }
    }
    else if (numFrameBits <= 65) {
      if (numFrameBits - 33 <= 8) {
        this.generateInstructions1to8(numFrameBits - 33, 16);
      }
      else {
        this.generateInstructions9to32(numFrameBits - 33, 16);
      }
    }
    else {
      if (numFrameBits - 66 <= 8) {
        this.generateInstructions1to8(numFrameBits - 66, 8);
      }
      else {
        this.generateInstructions9to32(numFrameBits - 66, 8);
      }
    }
    this.generateMethodFooter(numFrameBits);
    writer.append("    }\n");
  }

  protected void generateIntValues(final int nb) throws IOException {
    for (int i = 0; i < nb; i += 2) {
      writer.append("      ");
      writer.append("final int intValues"+i+" = unCompressedData[offset + "+i+"], ");
      writer.append("intValues"+(i+1)+" = unCompressedData[offset + "+(i+1)+"];\n");
    }
    writer.append("\n");
  }

  protected void generateInstructions1to8(final int numFrameBits, final int nb) throws IOException {
    int intPtr = 0;
    int bytePtr = 0;
    boolean first = true;
    int shift = 8;

    while (intPtr != nb) { // while we didn't process 32 integers
      while (shift >= numFrameBits) { // while shift is inferior to numFrameBits,
        shift -= numFrameBits;
        if (!first) { // if not first instruction, just add indentation and logic or
          writer.append("\n                                                | ");
        }
        else { // if first instruction, add byte array assignment
          writer.append("      compressedArray[outputOffset + "+bytePtr+"] = (byte) (");
          first = false;
        }
        if (shift == 0) { // if shift == 0, we do not have to add it
          writer.append("(intValues"+intPtr+" & "+((1 << numFrameBits) - 1)+")");
        }
        else {
          writer.append("((intValues"+intPtr+" & "+((1 << numFrameBits) - 1)+") << "+shift+")");
        }
        intPtr++;
      }

      if (shift > 0) { // when shift is inferior to numFrameBits and not equal to 0, special case.
        final int remainingBits = numFrameBits - shift;
        writer.append("\n                                                | ");
        writer.append("((intValues"+intPtr+" >>> "+remainingBits+") & 0xFF));\n");
        bytePtr++;
        shift = 8 - remainingBits;
        writer.append("      compressedArray[outputOffset + "+bytePtr+"] = (byte) (");
        writer.append("((intValues"+intPtr+" & "+((1 << remainingBits) - 1)+") << "+shift+")");
        intPtr++;
      }
      else {
        writer.append(");\n");
        bytePtr++;
        shift = 8;
        first = true;
      }
    }
  }

  protected void generateInstructions9to32(final int numFrameBits, final int nb) throws IOException {
    int intPtr = 0;
    int bytePtr = 0;
    int shift = numFrameBits;

    while (intPtr != nb) { // while we didn't process 32 integers
      while (shift > 8) { // while the shift is superior to the size of a byte
        shift -= 8;
        writer.append("      compressedArray[outputOffset + "+bytePtr+"] = (byte) ((intValues"+intPtr+" >>> "+shift+") & 0xFF);\n");
        bytePtr++; // increment the byte pointer
      }
      // if shift == 8, we just have to copy the less significant byte of the integer
      if (shift == 8) {
        writer.append("      compressedArray[outputOffset + "+bytePtr+"] = (byte) (intValues"+intPtr+" & 0xFF);\n");
        bytePtr++;
        intPtr++;
        shift = numFrameBits;
      }
      else {
        // If the shift is less than 8 (size of byte), special case with two
        // integers.
        writer.append("      compressedArray[outputOffset + "+bytePtr+"] = (byte) ((intValues"+intPtr+" & "+((1 << shift) - 1)+") << "+(8 - shift)+"\n");
        intPtr++; // increment the integer pointer for the next instruction
        writer.append("                                                | ");
        writer.append("(intValues"+intPtr+" >>> "+(numFrameBits - (8 - shift))+") & 0xFF);\n");
        bytePtr++; // increment byte pointer
        shift = numFrameBits - (8 - shift); // set shift to numFrameBits minus the number of bits shifted in the last instruction
      }
    }
  }

  private static final String FILE_HEADER =
  "/**\n" +
  " * Copyright 2014 National University of Ireland, Galway.\n" +
  " *\n" +
  " * This file is part of the SIREn project. Project and contact information:\n" +
  " *\n" +
  " *  https://github.com/rdelbru/SIREn\n" +
  " *\n" +
  " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
  " * you may not use this file except in compliance with the License.\n" +
  " * You may obtain a copy of the License at\n" +
  " *\n" +
  " *  http://www.apache.org/licenses/LICENSE-2.0\n" +
  " *\n" +
  " * Unless required by applicable law or agreed to in writing, software\n" +
  " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
  " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
  " * See the License for the specific language governing permissions and\n" +
  " * limitations under the License.\n" +
  " */ \n" +
  "/* This program is generated, do not modify. See AForFrameCompressorGenerator.java */\n" +
  "package org.sindice.siren.index.codecs.block;\n" +
  "\n" +
  "import org.apache.lucene.util.BytesRef;\n" +
  "import org.apache.lucene.util.IntsRef;\n";

  /**
   * @param args
   * @throws IOException
   */
  public static void main(final String[] args) throws IOException {
    final File file = new File("./src/main/java/org/sindice/siren/index/codecs/block", "AForFrameCompressor.java");
    final AForFrameCompressorGenerator generator = new AForFrameCompressorGenerator();
    generator.generate(file);
  }

}
