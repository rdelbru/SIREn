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

import java.io.CharArrayReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.DatatypeAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;
import org.sindice.siren.util.XSDDatatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class TupleTokenAnalyzerFilter extends TokenFilter {

  private final Logger logger = LoggerFactory.getLogger(TupleTokenAnalyzerFilter.class);

  private final HashMap<CharBuffer, Analyzer> dtsAnalyzer = new HashMap<CharBuffer, Analyzer>();

  private CharTermAttribute termAtt;
  private OffsetAttribute offsetAtt;
  private PositionIncrementAttribute posIncrAtt;
  private TypeAttribute typeAtt;
  private DatatypeAttribute dtypeAtt;
  private TupleAttribute tupleAtt;
  private CellAttribute cellAtt;

  private CharTermAttribute tokenTermAtt;
  private OffsetAttribute tokenOffsetAtt;
  private PositionIncrementAttribute tokenPosIncrAtt;
  private TypeAttribute tokenTypeAtt;
  private DatatypeAttribute tokenDtypeAtt;
  private TupleAttribute tokenTupleAtt;
  private CellAttribute tokenCellAtt;

  private boolean isConsumingToken = false;
  private TokenStream curentStream;

  private final CharBuffer xsdString = CharBuffer.wrap(XSDDatatype.XSD_STRING);
  private final CharBuffer xsdAnyURI = CharBuffer.wrap(XSDDatatype.XSD_ANY_URI);

  public TupleTokenAnalyzerFilter(final TokenStream input, final Analyzer stringAnalyzer, final Analyzer anyURIAnalyzer) {
    super(input);
    this.initAttributes();
    // register the default analyzers
    this.register(xsdString, stringAnalyzer);
    this.register(xsdAnyURI, anyURIAnalyzer);
  }

  private void initAttributes() {
    termAtt = input.getAttribute(CharTermAttribute.class);
    offsetAtt = input.getAttribute(OffsetAttribute.class);
    posIncrAtt = input.getAttribute(PositionIncrementAttribute.class);
    typeAtt = input.getAttribute(TypeAttribute.class);
    dtypeAtt = input.getAttribute(DatatypeAttribute.class);
    tupleAtt = input.getAttribute(TupleAttribute.class);
    cellAtt = input.getAttribute(CellAttribute.class);
  }

  private void initTokenAttributes() {
    tokenTermAtt = curentStream.addAttribute(CharTermAttribute.class);
    tokenOffsetAtt = curentStream.addAttribute(OffsetAttribute.class);
    tokenPosIncrAtt = curentStream.addAttribute(PositionIncrementAttribute.class);
    tokenTypeAtt = curentStream.addAttribute(TypeAttribute.class);
    tokenDtypeAtt = curentStream.addAttribute(DatatypeAttribute.class);
    tokenTupleAtt = curentStream.addAttribute(TupleAttribute.class);
    tokenCellAtt = curentStream.addAttribute(CellAttribute.class);
  }

  /**
   * Map the given analyzer to that dataTypeURI
   * @param dataTypeURI
   * @param analyzer
   */
  public void register(final CharBuffer dataTypeURI, final Analyzer analyzer) {
    if (!dtsAnalyzer.containsKey(dataTypeURI)) {
      dtsAnalyzer.put(dataTypeURI, analyzer);
    }
  }

  @Override
  public boolean incrementToken()
  throws IOException {
    do {
      if (!isConsumingToken) {
        if (!input.incrementToken())
          return false;
        final CharBuffer dt = dtypeAtt.datatypeURI();
        if (dt.length() == 0) { // empty datatype, e.g., a bnode
          return true;
        }
        final Analyzer analyzer;
        if (dtsAnalyzer.containsKey(dt))
          analyzer = dtsAnalyzer.get(dt);
        else {
          logger.info("No mapping found for the DataType {}, using the default xsd:string analyzer", dt);
          analyzer = dtsAnalyzer.get(xsdString);
        }

        curentStream = analyzer.reusableTokenStream("", new CharArrayReader(termAtt.buffer(), 0, termAtt.length()));
        curentStream.reset();
        this.initTokenAttributes();
      }
      // Consume the token with the registered analyzer
      isConsumingToken = curentStream.incrementToken();
    } while(!isConsumingToken);
    this.copyInnerStreamAttributes();
    return true;
  }

  private void copyInnerStreamAttributes() {
    final int len = tokenTermAtt.length();
    termAtt.copyBuffer(tokenTermAtt.buffer(), 0, len);
    offsetAtt.setOffset(tokenOffsetAtt.startOffset(), tokenOffsetAtt.endOffset());
    posIncrAtt.setPositionIncrement(tokenPosIncrAtt.getPositionIncrement());
    typeAtt.setType(tokenTypeAtt.type());
    dtypeAtt.setDatatypeURI(tokenDtypeAtt.datatypeURI().array());
    tupleAtt.setTuple(tokenTupleAtt.tuple());
    cellAtt.setCell(tokenCellAtt.cell());
  }

}
