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
package org.sindice.siren.qparser.keyword.nodes;

import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

/**
 * A {@link ProtectedQueryNode} represents a term in which all special
 * characters are escaped.
 *
 * <p>
 *
 * For example, the colon in 'http://acme.org' is not interpreted as a twig
 * query operator, and the expression is not converted into a {@link TwigQueryNode}.
 */
public class ProtectedQueryNode extends FieldQueryNode {

  public ProtectedQueryNode(final CharSequence field, final CharSequence text,
                            final int begin, final int end) {
    super(field, text, begin, end);
  }

  @Override
  public CharSequence toQueryString(final EscapeQuerySyntax escaper) {
    return "'" + escaper + "'";
  }

  @Override
  public String toString() {
    return "<protected start='" + this.begin + "' end='" + this.end
        + "' field='" + this.field + "' term='" + this.text + "'/>";
  }

  @Override
  public ProtectedQueryNode cloneTree() throws CloneNotSupportedException {
    final ProtectedQueryNode clone = (ProtectedQueryNode) super.cloneTree();
    // nothing to do here
    return clone;
  }

}
