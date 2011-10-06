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
 * @project siren-core_rdelbru
 * @author Campinas Stephane [ 3 Oct 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.analysis.filter;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArrayMap;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;
import org.sindice.siren.analysis.TupleAnalyzer;
import org.sindice.siren.analysis.TupleTokenizer;
import org.sindice.siren.analysis.attributes.DatatypeAttribute;
import org.sindice.siren.util.ReusableCharArrayReader;
import org.sindice.siren.util.XSDDatatype;

/**
 * This class performs post-processing operation on the tokens extracted by the
 * {@link TupleTokenizer} class, e.g., Literal, URI. An URI and a Literal have
 * default analyzers assigned, specified through the {@link TupleAnalyzer}
 * constructor. 
 */
public class TupleTokenAnalyzerFilter extends TokenFilter {

  private final CharArrayMap<Analyzer> dtsAnalyzer;

  private CharTermAttribute termAtt;
  private OffsetAttribute offsetAtt;
  private PositionIncrementAttribute posIncrAtt;
  private TypeAttribute typeAtt;
  private DatatypeAttribute dtypeAtt;

  private CharTermAttribute tokenTermAtt;
  private OffsetAttribute tokenOffsetAtt;
  private PositionIncrementAttribute tokenPosIncrAtt;
  private TypeAttribute tokenTypeAtt;
  private DatatypeAttribute tokenDtypeAtt;

  private boolean isConsumingToken = false;
  private TokenStream curentStream;

  private ReusableCharArrayReader reusableCharArray;
  
  public TupleTokenAnalyzerFilter(final Version version, final TokenStream input, final Analyzer stringAnalyzer, final Analyzer anyURIAnalyzer) {
    super(input);
    dtsAnalyzer = new CharArrayMap<Analyzer>(version, 64, false);
    this.initAttributes();
    // register the default analyzers
    this.register(XSDDatatype.XSD_STRING.toCharArray(), stringAnalyzer);
    this.register(XSDDatatype.XSD_ANY_URI.toCharArray(), anyURIAnalyzer);
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
  }

  /**
   * Initialise the attributes of the inner stream used to tokenize the incoming token.
   */
  private void initTokenAttributes() {
    tokenTermAtt = curentStream.addAttribute(CharTermAttribute.class);
    tokenOffsetAtt = curentStream.addAttribute(OffsetAttribute.class);
    tokenPosIncrAtt = curentStream.addAttribute(PositionIncrementAttribute.class);
    tokenTypeAtt = curentStream.addAttribute(TypeAttribute.class);
    tokenDtypeAtt = curentStream.addAttribute(DatatypeAttribute.class);
  }

  /**
   * Map the given analyzer to that dataTypeURI
   * @param dataTypeURI
   * @param analyzer
   */
  public void register(final char[] dataTypeURI, final Analyzer analyzer) {
    if (!dtsAnalyzer.containsKey(dataTypeURI)) {
      dtsAnalyzer.put(dataTypeURI, analyzer);
    }
  }
  
  @Override
  public boolean incrementToken()
  throws IOException {
    /*
     * the use of the loop is necessary in the case where it was consuming a token
     * but that token stream reached the end, and so incrementToken return false.
     * The loop makes sure that the next token is processed.
     */
    do {
      if (!isConsumingToken) {
        if (!input.incrementToken())
          return false;
        
        final char[] dt = dtypeAtt.datatypeURI();
        if (dt == null || dt.length == 0) { // empty datatype, e.g., a bnode
          return true;
        }
        if (!dtsAnalyzer.containsKey(dt)) // the datatype is not registered, leave the token as it is
          return true;
        
        final Analyzer analyzer = dtsAnalyzer.get(dt);
        if (reusableCharArray == null) {
          reusableCharArray = new ReusableCharArrayReader(termAtt.buffer(), 0, termAtt.length());
        } else
          reusableCharArray.reset(termAtt.buffer(), 0, termAtt.length());
        curentStream = analyzer.reusableTokenStream("", reusableCharArray);
        this.initTokenAttributes();
      }
      // Consume the token with the registered analyzer
      isConsumingToken = curentStream.incrementToken();
    } while(!isConsumingToken);
    this.copyInnerStreamAttributes();
    return true;
  }

  /**
   * Copy the inner's stream attributes values to the main stream's ones
   */
  private void copyInnerStreamAttributes() {
    final int len = tokenTermAtt.length();
    termAtt.copyBuffer(tokenTermAtt.buffer(), 0, len);
    offsetAtt.setOffset(tokenOffsetAtt.startOffset(), tokenOffsetAtt.endOffset());
    posIncrAtt.setPositionIncrement(tokenPosIncrAtt.getPositionIncrement());
    typeAtt.setType(tokenTypeAtt.type());
    dtypeAtt.setDatatypeURI(tokenDtypeAtt.datatypeURI());
    // TupleTokenizer handles the setting of tuple/cell values
  }

  @Override
  public void close()
  throws IOException {
    try {
      if (curentStream != null)
        curentStream.close();
    } finally {
      super.close();
    }
  }

}
