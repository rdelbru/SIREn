/**
 * Copyright 2011, Campinas Stephane
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
 * @project siren-core
 * @author Campinas Stephane [ 26 May 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.similarity;

import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.Weight;

/**
 * Customise the scoring to the SIREn use case
 */
public abstract class SirenWeight extends Weight {

  private static final long serialVersionUID = 1L;
  
  protected final Similarity similarity = new SirenSimilarity();

}
