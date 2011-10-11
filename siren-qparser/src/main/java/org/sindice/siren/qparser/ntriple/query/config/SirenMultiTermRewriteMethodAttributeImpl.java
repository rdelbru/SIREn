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
 * @project siren-qparser_rdelbru
 * @author Campinas Stephane [ 7 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.qparser.ntriple.query.config;

import org.apache.lucene.queryParser.standard.config.MultiTermRewriteMethodAttributeImpl;
import org.apache.lucene.util.AttributeImpl;
import org.sindice.siren.search.SirenMultiTermQuery;

/**
 * Copied from {@link MultiTermRewriteMethodAttributeImpl} for the Siren use case
 */
public class SirenMultiTermRewriteMethodAttributeImpl extends AttributeImpl implements SirenMultiTermRewriteMethodAttribute {

  private static final long serialVersionUID = -2104763012723049527L;
  
  { enableBackwards = false; }
  
  private SirenMultiTermQuery.RewriteMethod sirenMultiTermRewriteMethod = SirenMultiTermQuery.CONSTANT_SCORE_AUTO_REWRITE_DEFAULT;

  public SirenMultiTermRewriteMethodAttributeImpl() {
    // empty constructor
  }

  public void setSirenMultiTermRewriteMethod(SirenMultiTermQuery.RewriteMethod method) {
    sirenMultiTermRewriteMethod = method;
  }

  public SirenMultiTermQuery.RewriteMethod getSirenMultiTermRewriteMethod() {
    return sirenMultiTermRewriteMethod;
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void copyTo(AttributeImpl target) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(Object other) {

    if (other instanceof SirenMultiTermRewriteMethodAttributeImpl
        && ((SirenMultiTermRewriteMethodAttributeImpl) other).sirenMultiTermRewriteMethod == this.sirenMultiTermRewriteMethod) {

      return true;

    }

    return false;

  }

  @Override
  public int hashCode() {
    return sirenMultiTermRewriteMethod.hashCode();
  }

  @Override
  public String toString() {
    return "<sirenMultiTermRewriteMethod sirenMultiTermRewriteMethod="
        + this.sirenMultiTermRewriteMethod + "/>";
  }

}
