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

package org.sindice.siren.analysis.filter;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.util.CharArrayMap;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.JsonTokenizer;
import org.sindice.siren.analysis.attributes.DatatypeAttribute;
import org.sindice.siren.analysis.attributes.NodeAttribute;
import org.sindice.siren.util.JSONDatatype;
import org.sindice.siren.util.ReusableCharArrayReader;
import org.sindice.siren.util.XSDDatatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class performs post-processing operation on the tokens extracted by
 * {@link JsonTokenizer} based on the {@link DatatypeAttribute}.
 * <p>
 * This filter provides a {@link #register(char[], Analyzer)} method which allows
 * to register an {@link Analyzer} to a specific datatype.
 */
public class DatatypeAnalyzerFilter extends TokenFilter {

  private final static Logger logger =
    LoggerFactory.getLogger(DatatypeAnalyzerFilter.class);

  private final CharArrayMap<Analyzer> dtsAnalyzer;

  private CharTermAttribute termAtt;
  private OffsetAttribute offsetAtt;
  private PositionIncrementAttribute posIncrAtt;
  private TypeAttribute typeAtt;
  private DatatypeAttribute dtypeAtt;
  private NodeAttribute nodeAtt;

  private CharTermAttribute tokenTermAtt;
  private OffsetAttribute tokenOffsetAtt;
  private PositionIncrementAttribute tokenPosIncrAtt;
  private TypeAttribute tokenTypeAtt;

  private boolean isConsumingToken = false;
  private TokenStream currentStream;

  private ReusableCharArrayReader reusableCharArray;

  public DatatypeAnalyzerFilter(final Version version,
                                final TokenStream input) {
    super(input);
    dtsAnalyzer = new CharArrayMap<Analyzer>(version, 64, false);
    this.initAttributes();
  }

  /**
   * Create a {@link DatatypeAnalyzerFilter} with the given default
   * {@link Analyzer}s for the {@link JSONDatatype#JSON_FIELD} and
   * {@link XSDDatatype#XSD_STRING}.
   *
   * @param version The Lucene version to use
   * @param input the input token stream
   * @param fieldAnalyzer the default field name {@link Analyzer}
   * @param valueAnalyzer the default value {@link Analyzer}
   */
  public DatatypeAnalyzerFilter(final Version version,
                                final TokenStream input,
                                final Analyzer fieldAnalyzer,
                                final Analyzer valueAnalyzer) {
    this(version, input);
    // register the default analyzers
    this.register(XSDDatatype.XSD_STRING.toCharArray(), valueAnalyzer);
    this.register(JSONDatatype.JSON_FIELD.toCharArray(), fieldAnalyzer);
  }

  /**
   * Initialise the attributes of the main stream
   */
  private void initAttributes() {
    termAtt = input.getAttribute(CharTermAttribute.class);
    offsetAtt = input.getAttribute(OffsetAttribute.class);
    posIncrAtt = input.getAttribute(PositionIncrementAttribute.class);
    typeAtt = input.getAttribute(TypeAttribute.class);
    dtypeAtt = input.getAttribute(DatatypeAttribute.class);
    nodeAtt = this.addAttribute(NodeAttribute.class);
  }

  /**
   * Initialise the attributes of the inner stream used to tokenize the incoming token.
   */
  private void initTokenAttributes() {
    tokenTermAtt = currentStream.addAttribute(CharTermAttribute.class);
    tokenOffsetAtt = currentStream.addAttribute(OffsetAttribute.class);
    tokenPosIncrAtt = currentStream.addAttribute(PositionIncrementAttribute.class);
    tokenTypeAtt = currentStream.addAttribute(TypeAttribute.class);
  }

  /**
   * Map the given analyzer to that dataTypeURI
   */
  public void register(final char[] dataTypeURI, final Analyzer analyzer) {
    if (!dtsAnalyzer.containsKey(dataTypeURI)) {
      dtsAnalyzer.put(dataTypeURI, analyzer);
    }
  }

  @Override
  public final boolean incrementToken()
  throws IOException {
    /*
     * the use of the loop is necessary in the case where it was consuming a token
     * but that token stream reached the end, and so incrementToken return false.
     * The loop makes sure that the next token is processed.
     */
    do {
      if (!isConsumingToken) {
        if (!input.incrementToken()) {
          return false;
        }

        final char[] dt = dtypeAtt.datatypeURI();
        if (dt == null || dt.length == 0) { // empty datatype, e.g., a bnode
          // TODO GH-164
          logger.warn("Empty datatype for the token [{}]", termAtt);
          return true;
        }

        // the datatype is not registered, leave the token as it is
        if (!dtsAnalyzer.containsKey(dt)) {
          throw new IOException("Unregistered datatype [" + new String(dt)
            + "]. Use the #register method.");
        }

        final Analyzer analyzer = dtsAnalyzer.get(dt);
        if (reusableCharArray == null) {
          reusableCharArray = new ReusableCharArrayReader(termAtt.buffer(), 0, termAtt.length());
        } else {
          reusableCharArray.reset(termAtt.buffer(), 0, termAtt.length());
        }
        currentStream = analyzer.tokenStream("", reusableCharArray);
        currentStream.reset(); // reset to prepare the stream for consumption
        this.initTokenAttributes();
      }
      // Consume the token with the registered analyzer
      isConsumingToken = currentStream.incrementToken();
    } while(!isConsumingToken);
    this.copyInnerStreamAttributes();
    return true;
  }

  /**
   * Copy the inner's stream attributes values to the main stream's ones. This filter
   * uses an inner stream, therefore it needs to be cleared so that other filters
   * have clean attributes data. Because of that, the attributes datatypeURI and
   * node have to saved in order to be restored after.
   */
  private void copyInnerStreamAttributes() {
    // backup datatype and node path
    final IntsRef nodePath = IntsRef.deepCopyOf(nodeAtt.node());
    final char[] dt = dtypeAtt.datatypeURI();
    // clear attributes
    input.clearAttributes();
    // copy inner attributes
    final int len = tokenTermAtt.length();
    termAtt.copyBuffer(tokenTermAtt.buffer(), 0, len);
    offsetAtt.setOffset(tokenOffsetAtt.startOffset(), tokenOffsetAtt.endOffset());
    posIncrAtt.setPositionIncrement(tokenPosIncrAtt.getPositionIncrement());
    typeAtt.setType(tokenTypeAtt.type());
    // TupleTokenizer handles the setting of tuple/cell values and the datatype URI

    // restore datatype and node
    nodeAtt.copyNode(nodePath);
    dtypeAtt.setDatatypeURI(dt);
  }

  @Override
  public void close()
  throws IOException {
    try {
      if (currentStream != null) {
        currentStream.close();
      }
    } finally {
      super.close();
    }
  }

}
