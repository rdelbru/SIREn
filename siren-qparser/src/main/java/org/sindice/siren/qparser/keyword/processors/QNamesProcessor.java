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
import java.util.Properties;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.DatatypeQueryNode;
import org.sindice.siren.qparser.keyword.nodes.ProtectedQueryNode;

/**
 * This processor replaces QNames occurring in a
 * {@link ProtectedQueryNode} by their namespace.
 *
 * <p>
 *
 * The QNames mapping is provided by {@link KeywordConfigurationKeys#QNAMES}.
 */
public class QNamesProcessor
extends QueryNodeProcessorImpl {

  /** The property file containing the mapping */
  private Properties          qnames;
  private final StringBuilder sb = new StringBuilder();

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof ProtectedQueryNode || node instanceof DatatypeQueryNode) {
      if (qnames == null) {
        qnames = this.getQueryConfigHandler().get(KeywordConfigurationKeys.QNAMES);
      }
      if (qnames == null) { // the KeywordConfigurationKeys.QNAMES_PATH is not set
        return node;
      }
      // Replace the qname
      final CharSequence text;
      if (node instanceof ProtectedQueryNode) {
        text = ((ProtectedQueryNode) node).getText();
      } else {
        text = ((DatatypeQueryNode) node).getDatatype();
      }
      if (replace(text)) {
        if (node instanceof ProtectedQueryNode) {
          final ProtectedQueryNode pqn = (ProtectedQueryNode) node;
          pqn.setText(sb.toString());
          pqn.setEnd(pqn.getText().length());
        } else {
          ((DatatypeQueryNode) node).setDatatype(sb.toString());
        }
      }
    }
    return node;
  }

  /**
   * Replace in the text the qualified name by its corresponding namespace.
   * This fills the attribute {@link #sb} with the text replacement.
   * @param text the String with the qualified name
   * @return true if there was a qualified name and that it has been replaced.
   */
  private boolean replace(CharSequence text) {
    final int termLength = text.length();
    int offset = 0;
    if ((offset = this.findDelimiter(text)) != termLength) {
      final CharSequence prefix = this.convertQName(text, offset);
      final CharSequence suffix = text.subSequence(offset + 1, termLength); // skip the QName delimiter
      sb.setLength(0);
      sb.append(prefix);
      sb.append(suffix);
      return true;
    }
    return false;
  }

  /**
   * Find the offset of the QName delimiter. If no delimiter is
   * found, return last offset, i.e., {@code termLength}.
   */
  protected int findDelimiter(final CharSequence c) {
    final int len = c.length();
    int ptr = 0;

    while (ptr < len - 1) {
      if (this.isQNameDelim(c.charAt(ptr))) {
        if (!this.isNameStartChar(c.charAt(ptr + 1))) {
          break; // if this is not a valid name start char, we can stop
        }
        return ptr;
      }
      ptr++;
    }

    return len;
  }

  /**
   * Based on <a>http://www.w3.org/TR/REC-xml/#NT-Name</a>
   */
  protected boolean isNameStartChar(final char c) {
    return c == ':' || c == '_' || Character.isLetter(c);
  }

  /**
   * Return <code>true</code> if the character is a colon.
   */
  protected boolean isQNameDelim(final char c) {
    return c == ':';
  }

  /**
   * Convert the QName to the associated namespace. If the prefix is not a
   * qname, it just returns the original prefix.
   */
  protected CharSequence convertQName(final CharSequence c, final int offset) {
    final String prefix = c.subSequence(0, offset).toString();

    if (qnames.containsKey(prefix)) {
      return qnames.getProperty(prefix);
    }
    return c.subSequence(0, offset + 1);
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node)
  throws QueryNodeException {
    return node;
  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
  throws QueryNodeException {
    return children;
  }

}
