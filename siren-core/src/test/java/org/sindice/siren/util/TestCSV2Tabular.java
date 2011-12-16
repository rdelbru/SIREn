/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
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
 * @project siren-core
 * @author Campinas Stephane [ 8 Dec 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;


/**
 * 
 */
public class TestCSV2Tabular {

  @Test
  public void testConvertSimpleCSV()
  throws Exception {
    final String[] csv = new String[] { "tata,toto,titi", "tutu,tete,tyty" };
    final String expectedTuples = "\"tata\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"toto\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"titi\"^^<" + XSDDatatype.XSD_STRING + "> .\n" +
                                  "\"tutu\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"tete\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"tyty\"^^<" + XSDDatatype.XSD_STRING + "> .\n";
    
    assertEquals(expectedTuples, CSV2Tabular.convert(csv));
  }
  
  @Test
  public void testConfig()
  throws Exception {
    final String[] csv = new String[] { "10,toto,4.5", "23,titi,3" };
    final String expectedTuples = "\"10\"^^<" + XSDDatatype.XSD_INT + "> " +
                                  "\"toto\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"4.5\"^^<" + XSDDatatype.XSD_FLOAT + "> .\n" +
                                  "\"23\"^^<" + XSDDatatype.XSD_INT + "> " +
                                  "\"titi\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"3\"^^<" + XSDDatatype.XSD_FLOAT + "> .\n";
    final HashMap<Integer, String> config = new HashMap<Integer, String>();
    
    config.put(0, XSDDatatype.XSD_INT);
    config.put(2, XSDDatatype.XSD_FLOAT);
    assertEquals(expectedTuples, CSV2Tabular.convert(config, csv));
  }
  
  @Test
  public void testSep()
  throws Exception {
    final String[] csv = new String[] { "tata:toto:titi" };
    final String expectedTuples = "\"tata\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"toto\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"titi\"^^<" + XSDDatatype.XSD_STRING + "> .\n";
    
    assertEquals(expectedTuples, CSV2Tabular.convert(csv, ':'));
  }
  
  @Test
  public void testEmptyValue()
  throws Exception {
    final String[] csv = new String[] { "tata,,titi", ",toto,titi",
                                        "tata,toto,", ",," };
    final String expectedTuples = "\"tata\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"titi\"^^<" + XSDDatatype.XSD_STRING + "> .\n" +
                                  "\"\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"toto\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"titi\"^^<" + XSDDatatype.XSD_STRING + "> .\n"+
                                  "\"tata\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"toto\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"\"^^<" + XSDDatatype.XSD_STRING + "> .\n" +
                                  "\"\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"\"^^<" + XSDDatatype.XSD_STRING + "> .\n";
    
    assertEquals(expectedTuples, CSV2Tabular.convert(csv));
  }
  
  @Test
  public void testRemoveQuotes()
  throws Exception {
    final String[] csv = new String[] { "tata,\"toto\",titi",
                                        "\"tata\",toto,titi",
                                        "tata,toto,\"titi\""};
    final String expectedTuples = "\"tata\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"toto\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"titi\"^^<" + XSDDatatype.XSD_STRING + "> .\n" +
                                  "\"tata\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"toto\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"titi\"^^<" + XSDDatatype.XSD_STRING + "> .\n" +
                                  "\"tata\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"toto\"^^<" + XSDDatatype.XSD_STRING + "> " +
                                  "\"titi\"^^<" + XSDDatatype.XSD_STRING + "> .\n";
    
    assertEquals(expectedTuples, CSV2Tabular.convert(csv));
  }
  
}
