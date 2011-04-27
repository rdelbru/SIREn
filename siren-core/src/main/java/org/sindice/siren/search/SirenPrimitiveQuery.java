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
 * @author Renaud Delbru [ 6 Jul 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Weight;

/**
 * Abstract class for the SIREn primitive queries, such as
 * {@link SirenTermQuery} or {@link SirenPhraseQuery}.
 */
public abstract class SirenPrimitiveQuery
extends Query {

  /** Expert: Constructs an appropriate Weight implementation for this query.
  *
  * <p>Only implemented by primitive queries, which re-write to themselves.
  */
  @Override
  public abstract Weight createWeight(Searcher searcher) throws IOException;

}
