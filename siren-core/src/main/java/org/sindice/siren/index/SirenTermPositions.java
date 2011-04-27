/**
 * Copyright 2009, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 9 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.index;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermPositions;
import org.sindice.siren.search.SirenIdIterator;

/**
 * Decorator of the {@link TermPositions} class that implements all the logic
 * to iterate over the tuple table structure.
 */
public class SirenTermPositions implements TermPositions {

  /**
   * The wrapped Lucene {@link TermPositions}.
   */
  private final TermPositions _termPositions;

  /** Flag to know if tuple information are loaded */
  private boolean             _isTuplesLoaded = false;

  /** Flag to know if next or skipTo has been called */
  private boolean             _isFirstTime = true;

  /** Payload byte array buffer */
  private final byte[]        payloadBuffer = new byte[6];

  /** Capacity of the buffers */
  private int                 BUFFER_CAPACITY = 32;
  /** index of the first element that should not be read or written */
  private int                 _bufferLimit = -1;
  /** index of the next element to be read or written */
  private int                 _bufferPosition = -1;

  /** Tuple, cell and position buffers */
  private int[]               _tupleBuffer = new int[BUFFER_CAPACITY];
  private int[]               _cellBuffer = new int[BUFFER_CAPACITY];
  private int[]               _positionBuffer = new int[BUFFER_CAPACITY];

  /** Current structural and positional information */
  private int                 dataset = -1;
  private int                 entity = -1;
  private int                 tuple = -1;
  private int                 cell = -1;
  private int                 pos = -1;

  public SirenTermPositions(final TermPositions termPositions) {
    _termPositions = termPositions;
  }

  protected void reinit() {
    _isFirstTime = true; _isTuplesLoaded = false;
    _bufferLimit = _bufferPosition = -1;
    dataset = entity = tuple = cell = pos = -1;
  }

  @Override
  public void close()
  throws IOException {
    _termPositions.close();
  }

  @Override
  public int read(final int[] docs, final int[] freqs)
  throws IOException {
    return _termPositions.read(docs, freqs);
  }

  @Override
  public void seek(final Term term)
  throws IOException {
    _termPositions.seek(term);
    this.reinit();
  }

  @Override
  public void seek(final TermEnum termEnum)
  throws IOException {
    _termPositions.seek(termEnum);
    this.reinit();
  }

  @Override
  public byte[] getPayload(final byte[] data, final int offset)
  throws IOException {
    return _termPositions.getPayload(data, offset);
  }

  @Override
  public int getPayloadLength() {
    return _termPositions.getPayloadLength();
  }

  @Override
  public boolean isPayloadAvailable() {
    return _termPositions.isPayloadAvailable();
  }

  @Override
  public int doc() {
    return entity;
  }

  /** Returns the current dataset identifier. */
  public int dataset() {
    return dataset;
  }

  /** Returns the current entity identifier. */
  public int entity() {
    return entity;
  }

  /** Returns the current tuple identifier. */
  public int tuple() {
    return tuple;
  }

  /** Returns the current cell identifier. */
  public int cell() {
    return cell;
  }

  /** Returns the current position. */
  public int pos() {
    return pos;
  }

  @Override
  public int freq() {
    return _termPositions.freq();
  }

  /**
   * Returns the frequency of the term within the current cell.
   * <p> This is invalid until {@link #nextPosition()} is called for the first time.
   **/
  public int freqCell() {
    int f = 0;
    for (int p = _bufferPosition; p < _bufferLimit && cell == _cellBuffer[p]; p++)
      f++;
    return f;
  }

  @Override
  public boolean next()
  throws IOException {
    if (!_termPositions.next()) {
      _termPositions.close();      // close stream
      dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // set to sentinel value
      return false;
    }
    entity = _termPositions.doc();
    dataset = tuple = cell = pos = -1;
    _isFirstTime = _isTuplesLoaded = false;
    return true;
  }

  /**
   * Move to the next tuple, cell and position in the current entity.  It is not
   * error to call this more than {@link #freq()} times without calling
   * {@link #next()}. Instead, the method returns -1 if no more entries in the
   * list.
   *
   * <p> We changed the behavior of the Lucene method because when using
   * {@link #skipTo(int, int)} or {@link #skipTo(int, int, int)}, we can skip
   * positions and therefore the value returned by {@link #freq()} is invalid.
   *
   * <p> This is invalid until {@link #next()} is called for the first time.
   */
  @Override
  public int nextPosition()
  throws IOException {
    if (_isFirstTime)
      throw new RuntimeException("Invalid call, next should be called first.");

    this.loadTuples();

    if (++_bufferPosition < _bufferLimit) {
      tuple = _tupleBuffer[_bufferPosition];
      cell = _cellBuffer[_bufferPosition];
      pos = _positionBuffer[_bufferPosition];
      return pos;
    }

    return SirenIdIterator.NO_MORE_POS;
  }

  /** Skips to the first match (including the current) whose
   * is greater than or equal to a given entity.
   */
  @Override
  public boolean skipTo(final int entityID)
  throws IOException {
    if (entity == entityID) { // optimised case: reset buffer
      dataset = tuple = cell = pos = -1;
      _bufferPosition = 0;
      return true;
    }

    if (!_termPositions.skipTo(entityID)) {
      _termPositions.close();         // close stream
      dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // sentinel value
      return false;
    }
    entity = _termPositions.doc();
    dataset = tuple = cell = pos = -1;
    _isFirstTime = _isTuplesLoaded = false;
    return true;
  }

  /** Skips to the first match (including the current) whose
   * is greater than or equal to a given entity and tuple.
   */
  public boolean skipTo(final int entityID, final int tupleID)
  throws IOException {
    // optimisation: if current entity is the right one, don't call skipTo
    // and avoid to reset buffer
    if (entity == entityID || this.skipTo(entityID)) {
      // If we skipped to the right entity, load the tuples and let's try to
      // find the right one
      if (entity == entityID) {
        // if tuples were already loaded, it does not do anything. Useful in the
        // case where skipTo is called multiples times with the same entityID in
        // order to avoid to load tuples multiples times.
        this.loadTuples();
        // If tuple is not found, just move to the next entity (SRN-17), and
        // to the next cell (SRN-24)
        if (!this.findTuple(tupleID)) {
          if (this.next()) {
            this.nextPosition(); // advance to the first position (SRN-24)
            return true;
          }
          // position stream exhausted
          _termPositions.close(); // close stream
          dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // sentinel value
          return false;
        }
      }
      return true;
    }
    // position stream exhausted
    _termPositions.close(); // close stream
    dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // sentinel value
    return false;
  }

  /**
   * Find the first tuple in the buffer whose
   * is greater than or equal the target tuple, starting at the current buffer
   * position.
   *
   * @param tupleID The target tuple identifier
   * @return True if the tuple is found, false otherwise (SRN-17)
   */
  private boolean findTuple(final int tupleID) {
    while (++_bufferPosition < _bufferLimit) {
      // if the current tuple is lesser than the target, continue
      if (_tupleBuffer[_bufferPosition] < tupleID) {
        continue;
      } else {
        tuple = _tupleBuffer[_bufferPosition];
        cell = _cellBuffer[_bufferPosition];
        pos = _positionBuffer[_bufferPosition];
        return true;
      }
    }
    return false;
  }

  /** Skips to the first match (including the current) whose
   * is greater than or equal to a given entity, tuple and cell.
   */
  public boolean skipTo(final int entityID, final int tupleID, final int cellID)
  throws IOException {
    // optimisation: if current entity is the right one, don't call skipTo
    // and avoid to reset buffer
    if (entity == entityID || this.skipTo(entityID)) {
      // If we skipped to the right entity, load the tuples and let's try to
      // find the right one
      if (entity == entityID) {
        // if tuples were already loaded, it does not do anything. Useful in the
        // case where skipTo is called multiples times with the same entityID in
        // order to avoid to load tuples multiples times.
        this.loadTuples();
        // If tuple and cell are not found, just move to the next entity
        // (SRN-17), and to the next cell (SRN-24)
        if (!this.findCell(tupleID, cellID)) {
          if (this.next()) {
            this.nextPosition(); // advance to the first position (SRN-24)
            return true;
          }
          // position stream exhausted
          _termPositions.close(); // close stream
          dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // sentinel value
          return false;
        }
      }
      return true;
    }
    // position stream exhausted
    _termPositions.close(); // close stream
    dataset = entity = tuple = cell = pos = Integer.MAX_VALUE; // sentinel value
    return false;
  }

  /**
   * Find the first tuple and cell in the buffer whose
   * is greater than or equal than the target tuple and cell, starting at the
   * current buffer position.
   *
   * @param tupleID The target tuple identifier
   * @return True if the tuple and cell are found, false otherwise (SRN-17)
   */
  private boolean findCell(final int tupleID, final int cellID) {
    while (++_bufferPosition < _bufferLimit) {
      // if the current tuple is lesser or equal than the target,
      // and the current cell is lesser than the target, continue
      if (_tupleBuffer[_bufferPosition] < tupleID ||
          (_tupleBuffer[_bufferPosition] == tupleID && _cellBuffer[_bufferPosition] < cellID)) {
        continue;
      } else {
        tuple = _tupleBuffer[_bufferPosition];
        cell = _cellBuffer[_bufferPosition];
        pos = _positionBuffer[_bufferPosition];
        return true;
      }
    }
    return false;
  }

  protected void loadTuples() throws IOException {
    if (_isTuplesLoaded) return;

    this.initiateBuffer(_termPositions.freq());
    for (int i = 0; i < _termPositions.freq(); i++) {
      _positionBuffer[i] = _termPositions.nextPosition();
      final AbstractSirenPayload payload = this.getPayload();
      _tupleBuffer[i] = payload.getTupleID();
      _cellBuffer[i] = payload.getCellID();
    }
    _isTuplesLoaded = true;
  }

  protected void initiateBuffer(final int size) {
    if (BUFFER_CAPACITY < size) {
      BUFFER_CAPACITY = size;
      _positionBuffer = new int[BUFFER_CAPACITY];
      _tupleBuffer = new int[BUFFER_CAPACITY];
      _cellBuffer = new int[BUFFER_CAPACITY];
    }
    _bufferLimit = size;
    _bufferPosition = -1;
  }

  protected AbstractSirenPayload getPayload() throws IOException {
    final PackedIntSirenPayload payload = new PackedIntSirenPayload(
      _termPositions.getPayload(payloadBuffer, 0), 0,
      _termPositions.getPayloadLength());
    return payload;
  }

  @Override
  public String toString() {
    final StringBuilder b = new StringBuilder();
    b.append("eid=");
    b.append(entity);
    b.append(",tid=");
    b.append(tuple);
    b.append(",cid=");
    b.append(cell);
    b.append(",pos=");
    b.append(pos);
    return b.toString();
  }

}
