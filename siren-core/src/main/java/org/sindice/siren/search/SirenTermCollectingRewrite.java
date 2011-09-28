/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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
 * Code taken from {@link TermCollectingRewrite} and adapted for SIREn.
 */
abstract class SirenTermCollectingRewrite<Q extends Query> extends RewriteMethod {

  /** Return a suitable top-level Query for holding all expanded terms. */
  protected abstract Q getTopLevelQuery() throws IOException;

  /** Add a MultiTermQuery term to the top-level query */
  protected abstract void addClause(Q topLevel, Term term, float boost) throws IOException;

  protected final void collectTerms(final IndexReader reader, final SirenMultiTermQuery query, final TermCollector collector) throws IOException {
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
