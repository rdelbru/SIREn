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
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CachingTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.GroupQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.NoTokenFoundQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.OrQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.RangeQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.TextableQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.TokenizedPhraseQueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys;
import org.apache.lucene.queryparser.flexible.standard.nodes.RegexpQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.WildcardQueryNode;
import org.apache.lucene.search.MultiPhraseQuery;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.DatatypeQueryNode;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;
import org.sindice.siren.qparser.keyword.nodes.WildcardNodeQueryNode;

/**
 * This processor analyzes query terms based on their datatype.
 *
 * <p>
 *
 * This processor retrieves the {@link Analyzer} associated with the TAG
 * {@link DatatypeQueryNode#DATATYPE_TAGID} in the key
 * {@link KeywordConfigurationKeys#DATATYPES_ANALYZERS}, and uses it on the
 * {@link FieldQueryNode} text, which is not a {@link WildcardQueryNode},
 * a {@link FuzzyQueryNode}, a {@link RegexpQueryNode} or the bound of a
 * {@link RangeQueryNode}.
 *
 * <p>
 *
 * If no term is returned by the analyzer, a {@link NoTokenFoundQueryNode} object
 * is returned. An {@link WildcardNodeQueryNode} is returned instead if an ancestor
 * is a {@link TwigQueryNode}.
 *
 * <p>
 *
 * If the analyzer returns only one term, the
 * returned term is set to the {@link FieldQueryNode} and it is returned.
 *
 * <p>
 *
 * If the analyzer returns more than one term at different positions, a
 * {@link TokenizedPhraseQueryNode} is created. If they are all at the same
 * position, a {@link OrQueryNode} object is created and returned.
 *
 * <p>
 *
 * If the analyzer returns multiple terms and the parent node is a
 * {@link TokenizedPhraseQueryNode}, a {@link QueryNodeException} is thrown
 * because {@link MultiPhraseQuery} are not supported in SIREn.
 */
public class DatatypeAnalyzerProcessor
extends QueryNodeProcessorImpl {

  private boolean positionIncrementsEnabled;
  private int nbTwigs = 0;

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof TwigQueryNode) {
      nbTwigs++;
    }
    return node;
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof TextableQueryNode
        && !(node instanceof WildcardQueryNode)
        && !(node instanceof FuzzyQueryNode)
        && !(node instanceof RegexpQueryNode)
        && !(node.getParent() instanceof RangeQueryNode)) {

      this.positionIncrementsEnabled = false;
      final Boolean positionIncrementsEnabled = this.getQueryConfigHandler().get(ConfigurationKeys.ENABLE_POSITION_INCREMENTS);
      if (positionIncrementsEnabled != null) {
          this.positionIncrementsEnabled = positionIncrementsEnabled;
      }

      final FieldQueryNode fieldNode = ((FieldQueryNode) node);
      final String text = fieldNode.getTextAsString();
      final String field = fieldNode.getFieldAsString();
      final String datatype = (String) fieldNode.getTag(DatatypeQueryNode.DATATYPE_TAGID);

      if (datatype == null) {
        return node;
      }

      final Analyzer analyzer = this.getQueryConfigHandler()
                                .get(KeywordConfigurationKeys.DATATYPES_ANALYZERS)
                                .get(datatype);
      if (analyzer == null) {
        throw new QueryNodeException(new MessageImpl(
          QueryParserMessages.INVALID_SYNTAX, "No analyzer associated with " + datatype));
      }

      PositionIncrementAttribute posIncrAtt = null;
      int numTokens = 0;
      int positionCount = 0;
      boolean severalTokensAtSamePosition = false;

      final TokenStream source;
      try {
        source = analyzer.tokenStream(field, new StringReader(text));
        source.reset();
      } catch (final IOException e1) {
        throw new RuntimeException(e1);
      }
      final CachingTokenFilter buffer = new CachingTokenFilter(source);

      if (buffer.hasAttribute(PositionIncrementAttribute.class)) {
        posIncrAtt = buffer.getAttribute(PositionIncrementAttribute.class);
      }

      try {
        while (buffer.incrementToken()) {
          numTokens++;
          final int positionIncrement = (posIncrAtt != null) ? posIncrAtt
              .getPositionIncrement() : 1;
          if (positionIncrement != 0) {
            positionCount += positionIncrement;
          } else {
            severalTokensAtSamePosition = true;
          }
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
        if (nbTwigs != 0) { // Twig special case
          return new WildcardNodeQueryNode();
        }
        return new NoTokenFoundQueryNode();
      }
      else if (numTokens == 1) {
        String term = null;
        try {
          boolean hasNext;
          hasNext = buffer.incrementToken();
          assert hasNext == true;
          term = termAtt.toString();
        } catch (final IOException e) {
          // safe to ignore, because we know the number of tokens
        }
        fieldNode.setText(term);
        return fieldNode;
      }
      else {
        // no phrase query:
        final LinkedList<QueryNode> children = new LinkedList<QueryNode>();

        int position = -1;

        for (int i = 0; i < numTokens; i++) {
          String term = null;
          final int positionIncrement = 1;

          try {
            final boolean hasNext = buffer.incrementToken();
            assert hasNext == true;
            term = termAtt.toString();

          } catch (final IOException e) {
            // safe to ignore, because we know the number of tokens
          }

          final FieldQueryNode newFieldNode = new FieldQueryNode(field, term, -1, -1);

          if (this.positionIncrementsEnabled) {
            position += positionIncrement;
            newFieldNode.setPositionIncrement(position);
          } else {
            newFieldNode.setPositionIncrement(i);
          }

          children.add(new FieldQueryNode(field, term, -1, -1));
        }

        if (node.getParent() instanceof TokenizedPhraseQueryNode) {
          throw new QueryNodeException(new MessageImpl("Cannot build a MultiPhraseQuery"));
        }
        // If multiple terms at one single position, this must be a query
        // expansion. Perform a OR between the terms.
        if (severalTokensAtSamePosition && positionCount == 1) {
          return new GroupQueryNode(new OrQueryNode(children));
        }
        // if several tokens at same position && position count > 1, then
        // results can be unexpected
        else {
          final TokenizedPhraseQueryNode pq = new TokenizedPhraseQueryNode();
          for (int i = 0; i < children.size(); i++) {
            pq.add(children.get(i));
          }
          return pq;
        }
      }
    } else if (node instanceof TwigQueryNode) {
      nbTwigs--;
      assert nbTwigs >= 0;
    }
    return node;
  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
  throws QueryNodeException {
    return children;
  }

}
