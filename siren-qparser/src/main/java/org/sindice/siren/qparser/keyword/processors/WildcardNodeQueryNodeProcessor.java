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

package org.sindice.siren.qparser.keyword.processors;

import java.util.List;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.sindice.siren.qparser.keyword.nodes.ArrayQueryNode;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;
import org.sindice.siren.qparser.keyword.nodes.WildcardNodeQueryNode;

/**
 * This processor transforms a {@link FieldQueryNode} equal to <code>"*"</code>
 * into a {@link WildcardNodeQueryNode}.
 * 
 * <p>
 * 
 * This processor throws a {@link QueryNodeException} if it finds a
 * {@link TwigQueryNode} with both root and child being an
 * {@link WildcardNodeQueryNode}.
 */
public class WildcardNodeQueryNodeProcessor
extends QueryNodeProcessorImpl {

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {
    if ((node.getParent() instanceof TwigQueryNode ||
         node.getParent() instanceof ArrayQueryNode) &&
        this.isEmptyNode(node)) {
      return new WildcardNodeQueryNode();
    }
    return node;
  }

  /**
   * Return <code>true</code> if the {@link QueryNode} is a {@link FieldQueryNode},
   * which text is equal to "*".
   */
  private boolean isEmptyNode(final QueryNode q) {
    if (q instanceof FieldQueryNode) {
      final FieldQueryNode fq = (FieldQueryNode) q;
      final CharSequence text = fq.getText();
      if (text != null && text.length() == 1 && text.charAt(0) == '*') {
        return true;
      }
    }
    return false;
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof TwigQueryNode) {
      final TwigQueryNode twig = (TwigQueryNode) node;
      if (twig.getChild() instanceof WildcardNodeQueryNode &&
          twig.getRoot() instanceof WildcardNodeQueryNode) {
        throw new QueryNodeException(new MessageImpl("Twig with both root and child empty is not allowed."));
      }
    }
    return node;
  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
  throws QueryNodeException {
    return children;
  }

}
