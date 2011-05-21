/**
 * Copyright 2010, 2011, Renaud Delbru
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
