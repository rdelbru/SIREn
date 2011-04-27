/**
 * Copyright 2010, Renaud Delbru
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
 * @author Renaud Delbru [ 8 Sep 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import java.io.StringReader;

import org.apache.lucene.analysis.Tokenizer;
import org.junit.Test;

public class EntityQueryTokenizerTest extends TokenizerTestBase {

  private final Tokenizer _t = new EntityQueryTokenizer(new StringReader(""));

  @Test
  public void testTerm()
  throws Exception {
    this.assertTokenizesTo(_t, "  field:()",
      new String[] { "field:", "(", ")" },
      new String[] { "<TERM>", "<LPAR>", "<RPAR>" });
    this.assertTokenizesTo(_t, "  field:field  (  )  ",
      new String[] { "field:field", "(", ")" },
      new String[] { "<TERM>", "<LPAR>", "<RPAR>" });
    this.assertTokenizesTo(_t, "  fi=e_ld:f(i-e)l+d  ",
      new String[] { "fi=e_ld:f","(", "i-e", ")", "l+d" },
      new String[] { "<TERM>", "<LPAR>", "<TERM>","<RPAR>", "<TERM>" });
    this.assertTokenizesTo(_t, "  http://example.org/property  ",
      new String[] { "http://example.org/property" },
      new String[] { "<TERM>" });
    this.assertTokenizesTo(_t, "  http://example.org/select?param=value&a=b  ",
      new String[] { "http://example.org/select?param=value&a=b" },
      new String[] { "<TERM>" });
    this.assertTokenizesTo(_t, "  h  ",
      new String[] { "h" },
      new String[] { "<TERM>" });
    this.assertTokenizesTo(_t, "  hh  ",
      new String[] { "hh" },
      new String[] { "<TERM>" });
    this.assertTokenizesTo(_t, "  h(h)  ",
      new String[] { "h", "(", "h", ")" },
      new String[] { "<TERM>", "<LPAR>", "<TERM>","<RPAR>" });
    this.assertTokenizesTo(_t, "  h\\(h\\)  ",
      new String[] { "h\\(h\\)" },
      new String[] { "<TERM>" });
  }

  @Test
  public void testUnaryOperator()
  throws Exception {
    this.assertTokenizesTo(_t, "  +field()",
      new String[] { "+", "field", "(", ")" },
      new String[] { "<PLUS>", "<TERM>", "<LPAR>", "<RPAR>" });
    this.assertTokenizesTo(_t, "+ term1 -term2 term3",
      new String[] { "+", "term1", "-", "term2", "term3" },
      new String[] { "<PLUS>", "<TERM>", "<MINUS>", "<TERM>", "<TERM>" });
    this.assertTokenizesTo(_t, "++term1 +-term2",
      new String[] { "+", "+", "term1", "+", "-", "term2" },
      new String[] { "<PLUS>", "<PLUS>", "<TERM>", "<PLUS>", "<MINUS>", "<TERM>" });
    this.assertTokenizesTo(_t, " term1+ +term2",
      new String[] { "term1+", "+", "term2" },
      new String[] { "<TERM>", "<PLUS>", "<TERM>" });
  }

  @Test
  public void testQuote()
  throws Exception {
    this.assertTokenizesTo(_t, " \" a quote \" ",
      new String[] { "\"", "a", "quote", "\"" },
      new String[] { "<QUOTE>", "<TERM>", "<TERM>", "<QUOTE>" });
    this.assertTokenizesTo(_t, " \" a \\\" quote \" ",
      new String[] { "\"", "a", "\\\"", "quote", "\"" },
      new String[] { "<QUOTE>", "<TERM>", "<TERM>", "<TERM>", "<QUOTE>" });
  }

  @Test
  public void testEqual()
  throws Exception {
    this.assertTokenizesTo(_t, " attr = val ",
      new String[] { "attr", "=", "val" },
      new String[] { "<TERM>", "<EQUAL>", "<TERM>" });
    this.assertTokenizesTo(_t, " attr=val ",
      new String[] { "attr=val" },
      new String[] { "<TERM>" });
  }

}
