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

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CachingTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.NoTokenFoundQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.RangeQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.TextableQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.TokenizedPhraseQueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.nodes.RegexpQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.WildcardQueryNode;
import org.apache.lucene.util.Version;

/**
 * This processor analyses a {@link FieldQueryNode} using a {@link WhitespaceAnalyzer}
 * and outputs a {@link TokenizedPhraseQueryNode} if more than one token are returned.
 *
 * <p>
 *
 * This processor operates on every {@link FieldQueryNode} that is not
 * {@link WildcardQueryNode}, {@link FuzzyQueryNode} or
 * {@link RangeQueryNode} contained in the query node tree, and it applies
 * a {@link WhitespaceAnalyzer} to that {@link FieldQueryNode} object.
 *
 * <p>
 *
 * If the analyzer returns only one term, the node is returned unchanged.
 *
 * <p>
 *
 * If the analyzer returns more than one term, a {@link TokenizedPhraseQueryNode}
 * is created and returned.
 *
 * <p>
 *
 * If no term is returned by the analyzer, a {@link NoTokenFoundQueryNode} object
 * is returned.
 */
public class PhraseQueryNodeProcessor extends QueryNodeProcessorImpl {

  private final Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_40);

  @Override
  protected QueryNode postProcessNode(final QueryNode node) throws QueryNodeException {
    if (node instanceof TextableQueryNode
    && !(node instanceof WildcardQueryNode)
    && !(node instanceof FuzzyQueryNode)
    && !(node instanceof RegexpQueryNode)
    && !(node.getParent() instanceof RangeQueryNode)) {

      final FieldQueryNode fieldNode = ((FieldQueryNode) node);
      final String text = fieldNode.getTextAsString();
      final String field = fieldNode.getFieldAsString();

      final TokenStream source;
      try {
        source = this.analyzer.tokenStream(field, new StringReader(text));
        source.reset();
      } catch (final IOException e1) {
        throw new RuntimeException(e1);
      }
      final CachingTokenFilter buffer = new CachingTokenFilter(source);

      int numTokens = 0;
      try {
        while (buffer.incrementToken()) {
          numTokens++;
        }
      } catch (final IOException e) {
        // ignore
      }

      try {
        // rewind the buffer stream
        buffer.reset();
        // close original stream - all tokens buffered
        source.close();
      } catch (final IOException e) {
        // ignore
      }

      if (!buffer.hasAttribute(CharTermAttribute.class)) {
        return new NoTokenFoundQueryNode();
      }
      final CharTermAttribute termAtt = buffer.getAttribute(CharTermAttribute.class);

      if (numTokens == 0) {
        return new NoTokenFoundQueryNode();
      } else if (numTokens != 1) {
        // phrase query
        final TokenizedPhraseQueryNode pq = new TokenizedPhraseQueryNode();

        for (int i = 0; i < numTokens; i++) {
          String term = null;

          try {
            final boolean hasNext = buffer.incrementToken();
            assert hasNext == true;
            term = termAtt.toString();

          } catch (final IOException e) {
            // safe to ignore, because we know the number of tokens
          }

          final FieldQueryNode newFieldNode = new FieldQueryNode(field, term, -1, -1);
          newFieldNode.setPositionIncrement(i);
          pq.add(newFieldNode);
        }
        return pq;
      }
    }
    return node;
  }

  @Override
  protected QueryNode preProcessNode(final QueryNode node) throws QueryNodeException {
    return node;
  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
      throws QueryNodeException {
    return children;
  }

}
