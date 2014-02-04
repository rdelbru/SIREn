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
package org.sindice.siren.util;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;

/**
 * Contains some utility methods for manipulating arrays.
 */
public class ArrayUtils {

  /**
   * Increase the size of the array if needed. Copy the original content into
   * the new one and update the int[] reference inside the IntsRef.
   */
  public static final void growAndCopy(final IntsRef ref, final int size) {
    final int[] newArray = grow(ref.ints, size);
    System.arraycopy(ref.ints, 0, newArray, 0, ref.ints.length);
    ref.ints = newArray;
  }

  /**
   * Increase the size of the array if needed. Do not copy the content of the
   * original array into the new one.
   * <p>
   * Do not over allocate.
   */
  public static final int[] grow(final int[] array, final int size) {
    assert size >= 0: "size must be positive (got " + size + "): likely integer overflow?";
    if (array.length < size) {
      final int[] newArray = new int[size];
      return newArray;
    } else {
      return array;
    }
  }

  /**
   * Increase the size of the array if needed. Do not copy the content of the
   * original array into the new one.
   */
  public static final IntsRef grow(final IntsRef ref, final int minSize) {
    ref.ints = grow(ref.ints, minSize);
    return ref;
  }

  /**
   * Increase the size of the array if needed. Do not copy the content of the
   * original array into the new one.
   */
  public static final byte[] grow(final byte[] array, final int minSize) {
    assert minSize >= 0: "size must be positive (got " + minSize + "): likely integer overflow?";
    if (array.length < minSize) {
      final byte[] newArray = new byte[minSize];
      return newArray;
    } else {
      return array;
    }
  }

  /**
   * Increase the size of the array if needed. Do not copy the content of the
   * original array into the new one.
   */
  public static final BytesRef grow(final BytesRef ref, final int minSize) {
    ref.bytes = grow(ref.bytes, minSize);
    return ref;
  }

}
