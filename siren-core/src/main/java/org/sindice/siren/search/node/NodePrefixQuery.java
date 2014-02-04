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

package org.sindice.siren.search.node;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.PrefixTermsEnum;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.ToStringUtils;

/**
 * A {@link NodePrimitiveQuery} that matches documents containing terms with a
 * specified prefix. A PrefixQuery is built by QueryParser for input like
 * <code>app*</code>.
 *
 * <p>
 *
 * This query uses the
 * {@link MultiNodeTermQuery#CONSTANT_SCORE_AUTO_REWRITE_DEFAULT} rewrite
 * method.
 *
 * <p> Code taken from {@link PrefixQuery} and adapted for SIREn.
 **/
public class NodePrefixQuery extends MultiNodeTermQuery {

  private final Term prefix;

  /**
   * Constructs a query for terms starting with <code>prefix</code>.
   **/
  public NodePrefixQuery(final Term prefix) {
    super(prefix.field());
    this.prefix = prefix;
  }

  /**
   * Returns the prefix of this query.
   **/
  public Term getPrefix() { return prefix; }

  @Override
  protected TermsEnum getTermsEnum(final Terms terms, final AttributeSource atts)
  throws IOException {
    final TermsEnum tenum = terms.iterator(null);

    if (prefix.bytes().length == 0) {
      // no prefix -- match all terms for this field:
      return tenum;
    }
    return new PrefixTermsEnum(tenum, prefix.bytes());
  }

  /** Prints a user-readable version of this query. */
  @Override
  public String toString(final String field) {
    final StringBuffer buffer = new StringBuffer();
    buffer.append(prefix.text());
    buffer.append('*');
    buffer.append(ToStringUtils.boost(this.getBoost()));
    return this.wrapToStringWithDatatype(buffer).toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
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
    final NodePrefixQuery other = (NodePrefixQuery) obj;
    if (prefix == null) {
      if (other.prefix != null)
        return false;
    } else if (!prefix.equals(other.prefix))
      return false;
    return true;
  }

}

