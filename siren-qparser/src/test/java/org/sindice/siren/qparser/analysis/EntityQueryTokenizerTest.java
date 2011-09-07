/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
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
