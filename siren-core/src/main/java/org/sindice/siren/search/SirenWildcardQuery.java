/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
 * @project siren-core
 * @author Renaud Delbru [ 28 Sep 2011 ]
 * @link http://renaud.delbru.fr/
 */
package org.sindice.siren.search;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FilteredTermEnum;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SingleTermEnum;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.WildcardTermEnum;
import org.apache.lucene.util.ToStringUtils;

/**
 * Implements the wildcard search query. Supported wildcards are <code>*</code>, which
 * matches any character sequence (including the empty one), and <code>?</code>,
 * which matches any single character. Note this query can be slow, as it
 * needs to iterate over many terms. In order to prevent extremely slow WildcardQueries,
 * a Wildcard term should not start with one of the wildcards <code>*</code> or
 * <code>?</code>.
 *
 * <p>This query uses the {@link
 * SirenMultiTermQuery#CONSTANT_SCORE_AUTO_REWRITE_DEFAULT}
 * rewrite method.
 *
 * <p> Code taken from {@link WildcardQuery} and adapted for SIREn.
 *
 * @see WildcardTermEnum
 **/
public class SirenWildcardQuery extends SirenMultiTermQuery {

  private final boolean termContainsWildcard;
  private final boolean termIsPrefix;
  protected Term term;

  public SirenWildcardQuery(final Term term) {
    this.term = term;
    final String text = term.text();
    this.termContainsWildcard = (text.indexOf('*') != -1)
        || (text.indexOf('?') != -1);
    this.termIsPrefix = termContainsWildcard
        && (text.indexOf('?') == -1)
        && (text.indexOf('*') == text.length() - 1);
  }

  @Override
  protected FilteredTermEnum getEnum(final IndexReader reader) throws IOException {
    if (termContainsWildcard)
      return new WildcardTermEnum(reader, this.getTerm());
    else
      return new SingleTermEnum(reader, this.getTerm());
  }

  /**
   * Returns the pattern term.
   */
  public Term getTerm() {
    return term;
  }

  @Override
  public Query rewrite(final IndexReader reader) throws IOException {
    if (termIsPrefix) {
      final SirenMultiTermQuery rewritten = new SirenPrefixQuery(term.createTerm(term.text()
          .substring(0, term.text().indexOf('*'))));
      rewritten.setBoost(this.getBoost());
      rewritten.setRewriteMethod(this.getRewriteMethod());
      return rewritten;
    }
    else {
      return super.rewrite(reader);
    }
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String field) {
    final StringBuilder buffer = new StringBuilder();
    if (!term.field().equals(field)) {
      buffer.append(term.field());
      buffer.append(":");
    }
    buffer.append(term.text());
    buffer.append(ToStringUtils.boost(this.getBoost()));
    return buffer.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((term == null) ? 0 : term.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final SirenWildcardQuery other = (SirenWildcardQuery) obj;
    if (term == null) {
      if (other.term != null)
        return false;
    } else if (!term.equals(other.term))
      return false;
    return true;
  }

}

