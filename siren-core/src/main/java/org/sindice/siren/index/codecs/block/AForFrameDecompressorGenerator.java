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
 * This class is used to generate {@link AForFrameDecompressor}.
 */
public class AForFrameDecompressorGenerator {

  private FileWriter writer;
  public static final int[] frameSizes = new int[99];
  private final int[] MASK = { 0x00000000, 0x00000001, 0x00000003, 0x00000007, 0x0000000f, 0x0000001f, 0x0000003f,
                               0x0000007f, 0x000000ff, 0x000001ff, 0x000003ff, 0x000007ff, 0x00000fff, 0x00001fff,
                               0x00003fff, 0x00007fff, 0x0000ffff, 0x0001ffff, 0x0003ffff, 0x0007ffff, 0x000fffff,
                               0x001fffff, 0x003fffff, 0x007fffff, 0x00ffffff, 0x01ffffff, 0x03ffffff, 0x07ffffff,
                               0x0fffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, 0xffffffff };

  public AForFrameDecompressorGenerator() throws IOException {
    this.generateFrameSizeTable();
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

  public void generate(final File file) throws IOException {
    writer = new FileWriter(file);
    writer.append(FILE_HEADER); writer.append('\n');
    this.generateClass();
    writer.close();
  }

  protected void generateClass() throws IOException {
    writer.append("/**\n");
    writer.append(" * This class contains a lookup table of functors for decompressing fames.\n");
    writer.append(" */\n");
    writer.append("public class AForFrameDecompressor {\n\n");
    this.generateTable();
    this.generateAbstractInnerClass();
    for (int i = 0; i < 99; i++) {
      this.generateInnerClass(i);
    }
    writer.append("}\n");
  }

  protected void generateTable() throws IOException {
    writer.append("  public static final FrameDecompressor[] decompressors = new FrameDecompressor[] {\n");
    for (int i = 0; i < 99; i++) {
      writer.append("    new FrameDecompressor"+i+"(),\n");
    }
    writer.append("  };\n\n");
  }

  protected void generateAbstractInnerClass() throws IOException {
    writer.append("  static abstract class FrameDecompressor {\n");
    writer.append("    public abstract void decompress(final BytesRef input, final IntsRef output);\n");
    writer.append("  }\n\n");
  }

  protected void generateInnerClass(final int numFramebits) throws IOException {
    writer.append("  static final class FrameDecompressor" + Integer.toString(numFramebits) + " extends FrameDecompressor {\n");
    this.generateMethod(numFramebits);
    writer.append("  }\n\n");
  }

  private void zeros(final int numFrameBits)
  throws IOException {
    final int nb;
    if (numFrameBits <= 32)
      nb = 32;
    else if (numFrameBits <= 65)
      nb = 16;
    else
      nb = 8;
    writer.append("      ");
    writer.append("final int[] unCompressedData = output.ints;\n");
    writer.append("      ");
    writer.append("final int outOffset = output.offset;\n");
    for (int i = 0; i < nb; i++) {
        writer.append("      ");
        writer.append("unCompressedData[outOffset" + ((i == 0) ? "" : " + " +i) + "] = 0;\n");
    }
    writer.append("      output.offset += " + nb + ";\n");
    writer.append("      input.offset += 1;\n");
    writer.append("  }\n");
  }

  private void generate816Routines(final int numFrameBits) throws IOException {
    for (int i = 0, j = 0; j < 16; ) {
      if (i == 0)
        writer.append("      unCompressedData[outOffset] = " + (numFrameBits == 8 ? "compressedArray[inOffset] & 0xFF" :
                                                                                      "((compressedArray[inOffset] & 0xFF) << 8) | (compressedArray[inOffset + 1] & 0xFF)") + ";\n");
      else
        writer.append("      unCompressedData[outOffset + " + j + "] = " + (numFrameBits == 8 ? "compressedArray[inOffset + " + i + "] & 0xFF" :
                                                                                                  "((compressedArray[inOffset + " + i + "] & 0xFF) << 8) | (compressedArray[inOffset + " + (i+1) +"] & 0xFF)") + ";\n");
      if (numFrameBits == 16)
          i += 2;
      else
          i++;
      j++;
    }
    this.generateMethodFooter(numFrameBits + 33);
    writer.append("    }\n");
  }

  protected void generateMethod(final int numFrameBits) throws IOException {
    writer.append("    public final void decompress(final BytesRef input, final IntsRef output) {\n");
    // Zeros case
    if (numFrameBits == 0 || numFrameBits == 33 || numFrameBits == 66) {
      this.zeros(numFrameBits);
      return;
    }
    this.generateMethodHeader(numFrameBits);
    // for bits < 7, use byte variables
    if (numFrameBits < 41 && numFrameBits > 33) {
      this.generate1To7Routines(numFrameBits - 33);
      writer.append("    }\n");
      return;
    }
    // 8 and 16 BFS special case
    if (numFrameBits == 41 || numFrameBits == 49) {
      this.generate816Routines(numFrameBits - 33);
      return;
    }
    if (numFrameBits <= 32)
      this.generateIntValues(numFrameBits);
    else if (numFrameBits <= 65)
      this.generateShortValues(numFrameBits - 33);
    else
      this.generateByteValues(numFrameBits - 66);
    if (numFrameBits <= 32)
      this.generateInstructions32(numFrameBits);
    else if (numFrameBits <= 65)
      this.generateInstructions16(numFrameBits - 33);
    else
      this.generateInstructions8(numFrameBits - 66);
    this.generateMethodFooter(numFrameBits);
    writer.append("    }\n");
  }

  private void generate1To7Routines(final int numFrameBits)
  throws IOException {
    final int mask = (1 << numFrameBits) - 1;
    int shift = 8;
    int bytePtr = 0, intPtr = 0;

    this.generateByteValues(numFrameBits*2);

    while (intPtr != 16) { // while we didn't process 16 integers
      while (shift >= numFrameBits) {
        shift -= numFrameBits;
        writer.append("      ");
        if (shift == 0) {
          writer.append("unCompressedData[outOffset + "+intPtr+"] = i"+bytePtr+" & "+mask+";\n");
        }
        else {
          writer.append("unCompressedData[outOffset + "+intPtr+"] = (i"+bytePtr+" >>> "+shift+") & "+mask+";\n");
        }
        intPtr++;
      }

      if (shift > 0) {
        writer.append("      ");
        writer.append("unCompressedData[outOffset + "+intPtr+"] = ((i"+bytePtr+" & "+((1 << shift) - 1)+") << "+(numFrameBits - shift)+")");
        bytePtr++;
        shift = 8 - (numFrameBits - shift);
        writer.append(" | (i"+bytePtr+" >>> "+shift+") & "+((1 << (8 - shift)) - 1)+";\n");
        intPtr++;
      }
      else {
        bytePtr++;
        shift = 8;
      }
    }
    this.generateMethodFooter(numFrameBits + 33);
  }

  protected void generateMethodFooter(final int numFrameBits) throws IOException {
    writer.append("      ");
    writer.append("input.offset += " + (1 + frameSizes[numFrameBits]) + ";\n");
    writer.append("      ");
    if (numFrameBits <= 32)
      writer.append("output.offset += 32;\n");
    else if (numFrameBits <= 65)
      writer.append("output.offset += 16;\n");
    else
      writer.append("output.offset += 8;\n");
  }

  protected void generateMethodHeader(final int numFrameBits) throws IOException {
    writer.append("      ");
    writer.append("final int[] unCompressedData = output.ints;\n");
    writer.append("      ");
    writer.append("final byte[] compressedArray = input.bytes;\n");
    writer.append("      ");
    writer.append("final int inOffset = input.offset + 1;\n");
    writer.append("      ");
    writer.append("final int outOffset = output.offset;\n");
  }

  protected void generateIntValues(final int numFrameBits) throws IOException {
    for (int i = 0, j = 0; i < numFrameBits; i++, j += 4) {
      writer.append("      ");
      writer.append("final int i"+i+" = ((compressedArray[inOffset + "+j+"] & 0xFF) << 24) | ");
      writer.append("((compressedArray[inOffset + "+(j+1)+"] & 0xFF) << 16) | ");
      writer.append("((compressedArray[inOffset + "+(j+2)+"] & 0xFF) << 8) | ");
      writer.append("((compressedArray[inOffset + "+(j+3)+"] & 0xFF));\n");
    }
    writer.append("\n");
  }

  protected void generateByteValues(final int numFrameBits) throws IOException {
    for (int i = 0; i < numFrameBits; i++) {
      writer.append("      final byte i" + i + " = compressedArray[inOffset + " + i + "];\n");
    }
    writer.append("\n");
  }

  protected void generateShortValues( final int numFrameBits )
  throws IOException
  {
    for ( int i = 0, j = 0; i < numFrameBits; i++, j += 2 )
    {
      writer.append( "      " );
      writer.append( "final short i" + i + " = (short) (((compressedArray[inOffset + " + j + "] & 0xFF) << 8) | " );
      writer.append( "((compressedArray[inOffset + " + ( j + 1 ) + "] & 0xFF)));\n" );
    }
    writer.append( "\n" );
  }

  protected void generateInstructions32(final int numFrameBits) throws IOException {
    final int mask = (1 << numFrameBits) - 1;
    int shift = 32;
    int bytePtr = 0, intPtr = 0;

    while (intPtr != 32) { // while we didn't process 32 integers
      while (shift >= numFrameBits) {
        shift -= numFrameBits;
        writer.append("      ");
        if (shift == 0 && mask != 0) {
          writer.append("unCompressedData[outOffset + "+intPtr+"] = i"+bytePtr+" & "+mask+";\n");
        }
        else if (shift == 0 && mask == 0) {
          writer.append("unCompressedData[outOffset + "+intPtr+"] = i"+bytePtr+";\n");
        }
        else if (shift + numFrameBits == 32) {
          writer.append("unCompressedData[outOffset + "+intPtr+"] = (i"+bytePtr+" >>> "+shift+");\n");
        }
        else {
          writer.append("unCompressedData[outOffset + "+intPtr+"] = (i"+bytePtr+" >>> "+shift+") & "+mask+";\n");
        }
        intPtr++;
      }

      if (shift > 0) {
        writer.append("      ");
//        writer.append("unCompressedData[outOffset + "+intPtr+"] = ((i"+bytePtr+" & "+((1 << shift) - 1)+") << "+(numFrameBits - shift)+")");
        writer.append("unCompressedData[outOffset + "+intPtr+"] = ((i"+bytePtr+" << "+(numFrameBits - shift)+")");
        bytePtr++;
        shift = 32 - (numFrameBits - shift);
//         writer.append(" | (i"+bytePtr+" >>> "+shift+") & "+((1 << (32 - shift)) - 1)+";\n");
        writer.append(" | (i"+bytePtr+" >>> "+shift+")) & "+mask+";\n");
        intPtr++;
      }
      else {
        bytePtr++;
        shift = 32;
      }
    }
  }

  protected void generateInstructions8(final int numFrameBits) throws IOException {
    final int mask = (1 << numFrameBits) - 1;
    int shift = 8;
    int bytePtr = 0, intPtr = 0;

    while (intPtr < 8) {
      while (shift >= numFrameBits) {
        shift -= numFrameBits;
        writer.append("      ");
        if (shift == 0 && mask != 0) {
          writer.append("unCompressedData[outOffset + " + intPtr + "] = i" + bytePtr + " & " + mask + ";\n");
        } else if (shift == 0 && mask == 0) {
          writer.append("unCompressedData[outOffset + " + intPtr + "] = i" + bytePtr + ";\n");
        }
        else {
          writer.append("unCompressedData[outOffset + " + intPtr + "] = (i" + bytePtr + " >>> " + shift + ") & " + mask + ";\n");
        }
        intPtr++;
      }

      if (shift > 0) {
        writer.append("      ");
        writer.append("unCompressedData[outOffset + " + intPtr + "] = ((i" + bytePtr + " << " + (numFrameBits - shift) + ")");
        bytePtr++;
        shift = 8 - (numFrameBits - shift);
        if (shift >= 0) {
          if (mask == 0)
            writer.append(" | (i" + bytePtr + " >>> " + shift + " & " + MASK[8 - shift] + "));\n");
          else
            writer.append(" | (i" + bytePtr + " >>> " + shift + " & " + MASK[8 - shift] + ")) & " + mask + ";\n");
        }
        else {
          writer.append(" | (i" + bytePtr + " << " + (-shift) + " & " + MASK[8 - shift] + ")");
          while (shift + 8 < 0) {
            writer.append(" | (i" + (++bytePtr) + " << " + (-8 - shift) + " & " + MASK[-shift] + ")");
            shift += 8;
          }
          if (mask == 0)
            writer.append(" | (i" + (bytePtr + 1) + " >>> " + (8 + shift) + " & " + MASK[-shift] + "));\n");
          else
            writer.append(" | (i" + (bytePtr + 1) + " >>> " + (8 + shift) + " & " + MASK[-shift] +  ")) & " + mask + ";\n");
        }
        intPtr++;
      } else {
        bytePtr++;
        shift += 8;
      }
    }
  }

  protected void generateInstructions16(final int numFrameBits) throws IOException {
    final int mask = ( 1 << numFrameBits ) - 1;
    int shift = 16;
    int bytePtr = 0, intPtr = 0;

    while ( intPtr < 16 ) {
      while ( shift >= numFrameBits ) {
        shift -= numFrameBits;
        writer.append( "      " );
        if ( shift == 0 && mask != 0 )
        {
          writer.append( "unCompressedData[outOffset + " + intPtr + "] = i" + bytePtr + " & " + mask + ";\n" );
        }
        else if ( shift == 0 && mask == 0 )
        {
          writer.append( "unCompressedData[outOffset + " + intPtr + "] = i" + bytePtr + ";\n" );
        }
        else {
          writer.append( "unCompressedData[outOffset + " + intPtr + "] = (i" + bytePtr + " >>> " + shift + ") & " + mask + ";\n" );
        }
        intPtr++;
      }

      if ( shift > 0 ) {
        writer.append( "      " );
        writer.append( "unCompressedData[outOffset + " + intPtr + "] = ((i" + bytePtr + " << " + ( numFrameBits - shift ) + ")" );
        bytePtr++;
        shift = 16 - ( numFrameBits - shift );
        if ( shift >= 0 )
        {
          if ( mask == 0 )
            writer.append( " | (i" + bytePtr + " >>> " + shift + " & " + MASK[16 - shift] + "));\n" );
          else
            writer.append( " | (i" + bytePtr + " >>> " + shift + " & " + MASK[16 - shift] + ")) & " + mask + ";\n" );
        }
        else {
          if ( mask == 0 )
            writer.append( " | (i" + bytePtr + " << " + ( -shift ) + " & " + MASK[16 - shift] + ") | (i" + ( bytePtr + 1 ) + " >>> " + ( 16 + shift ) + " & " + MASK[-shift] + "));\n" );
          else
            writer.append( " | (i" + bytePtr + " << " + ( -shift ) + " & " + MASK[16 - shift] + ") | (i" + ( bytePtr + 1 ) + " >>> " + ( 16 + shift ) + " & " + MASK[-shift] + ")) & " + mask + ";\n" );
        }
        intPtr++;
      }
      else {
        bytePtr++;
        shift += 16;
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
  "/* This program is generated, do not modify. See AForFrameDecompressorGenerator.java */\n" +
  "package org.sindice.siren.index.codecs.block;\n" +
  "\n" +
  "import org.apache.lucene.util.BytesRef;\n" +
  "import org.apache.lucene.util.IntsRef;\n";

  /**
   * @param args
   * @throws IOException
   */
  public static void main(final String[] args) throws IOException {
    final File file = new File("./src/main/java/org/sindice/siren/index/codecs/block", "AForFrameDecompressor.java");
    final AForFrameDecompressorGenerator generator = new AForFrameDecompressorGenerator();
    generator.generate(file);
  }

}
