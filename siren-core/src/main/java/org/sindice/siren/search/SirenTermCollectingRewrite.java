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
 * @project siren-core_rdelbru
 * @author Campinas Stephane [ 21 Sep 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FilteredTermEnum;
import org.apache.lucene.search.Query;
import org.sindice.siren.search.SirenMultiTermQuery.RewriteMethod;

/**
 * Class copied from TermCollectingRewrite for the siren use case
 */
abstract class SirenTermCollectingRewrite<Q extends Query> extends RewriteMethod {

  /** Return a suitable top-level Query for holding all expanded terms. */
  protected abstract Q getTopLevelQuery() throws IOException;
  
  /** Add a MultiTermQuery term to the top-level query */
  protected abstract void addClause(Q topLevel, Term term, float boost) throws IOException;
  
  protected final void collectTerms(IndexReader reader, SirenMultiTermQuery query, TermCollector collector) throws IOException {
    final FilteredTermEnum enumerator = query.getEnum(reader);
    try {
      do {
        final Term t = enumerator.term();
        if (t == null || !collector.collect(t, enumerator.difference()))
          break;
      } while (enumerator.next());    
    } finally {
      enumerator.close();
    }
  }
  
  protected interface TermCollector {
    /** return false to stop collecting */
    boolean collect(Term t, float boost) throws IOException;
  }

}
