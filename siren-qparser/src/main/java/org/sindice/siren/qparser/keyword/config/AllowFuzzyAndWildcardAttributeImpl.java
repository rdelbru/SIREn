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
 * @project siren
 * @author Renaud Delbru [ 21 May 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010, 2011, by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword.config;

import org.apache.lucene.queryParser.core.config.QueryConfigHandler;
import org.apache.lucene.util.AttributeImpl;
import org.sindice.siren.qparser.keyword.query.processors.AllowFuzzyAndWildcardProcessor;

/**
 * This attribute is used by {@link AllowFuzzyAndWildcardProcessor} processor and
 * must be defined in the {@link QueryConfigHandler}. It basically tells the
 * processor if it should allow fuzzy and wildcard query. <br/>
 *
 * @see org.apache.lucene.queryParser.standard.config.AllowFuzzyAndWildcardAttribute
 */
public class AllowFuzzyAndWildcardAttributeImpl
extends AttributeImpl
implements AllowFuzzyAndWildcardAttribute {

  private static final long serialVersionUID = -2804763012723049527L;

  private boolean allowFuzzyAndWildcard = false; // not allowed by default

  public void setAllowFuzzyAndWildcard(final boolean allowFuzzyAndWildcard) {
    this.allowFuzzyAndWildcard = allowFuzzyAndWildcard;
  }

  public boolean isAllowFuzzyAndWildcard() {
    return this.allowFuzzyAndWildcard;
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void copyTo(final AttributeImpl target) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(final Object other) {

    if (other instanceof AllowFuzzyAndWildcardAttributeImpl
        && ((AllowFuzzyAndWildcardAttributeImpl) other).allowFuzzyAndWildcard == this.allowFuzzyAndWildcard) {

      return true;

    }

    return false;

  }

  @Override
  public int hashCode() {
    return this.allowFuzzyAndWildcard ? -1 : Integer.MAX_VALUE;
  }

  @Override
  public String toString() {
    return "<allowFuzzyAndWildcard allowFuzzyAndWildcard="
        + this.allowFuzzyAndWildcard + "/>";
  }

}
