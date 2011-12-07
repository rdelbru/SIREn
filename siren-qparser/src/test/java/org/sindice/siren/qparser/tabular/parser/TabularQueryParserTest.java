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
 * @project siren
 * @author Renaud Delbru [ 25 Apr 2008 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.tabular.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sindice.siren.analysis.IntNumericAnalyzer;
import org.sindice.siren.qparser.analysis.NTripleTestHelper;

public class TabularQueryParserTest {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testTabularQuerySimpleTriple()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> <http://o> .";
    final String query = " [0]<http://s> [1]<http://p> [2]<http://o>";

    assertTrue(TabularQueryParserTestHelper.match(ntriple, query));
  }

  @Test
  public void testTabularQuerySimpleTriple2()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntriple = "<http://s> <http://p> <http://o> .";
    
    String query = " [0]<http://s> [1]<http://p> [3]<http://o>";
    assertFalse(TabularQueryParserTestHelper.match(ntriple, query));
    
    query = " [0]<http://s> [1]<http://p> [2]<http://o>";
    assertTrue(TabularQueryParserTestHelper.match(ntriple, query));
  }
  
  @Test
  public void testTabularQuery()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntuple = "<http://s> <http://p> \"literal\" <http://o> \"literal2\" .";
    
    String query = " [0]<http://s> [1]<http://p> [2]<http://o>";
    assertFalse(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = " [0]<http://s> [1]<http://p> [2]\"literal\"";
    assertTrue(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = " [0]<http://s> [1]<http://p> [3]\"literal\"";
    assertFalse(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = "[1]<http://p> [3]<http://o>";
    assertTrue(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = "[1]<http://p> [4]\"literal2\"";
    assertTrue(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = " [3]<http://o> [4]\"literal2\"";
    assertTrue(TabularQueryParserTestHelper.match(ntuple, query));
  }
  
  public void testTabularQuery2()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final String ntuple = "\"literal\" \"some long literal\" <http://o1> <http://o2> \"some long literal\" <http://o3> .";
    
    String query = "[0]<http://o1>";
    assertFalse(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = "[2]<http://o1>";
    assertTrue(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = "[5]<http://o3> [1]'some AND literal' [2]<http://o1>";
    assertTrue(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = "[4]\"some literal\"";
    assertFalse(TabularQueryParserTestHelper.match(ntuple, query));
  }
  
  @Test
  public void testTabularQueryDatatype()
  throws Exception {
    TabularQueryParserTestHelper.registerTokenConfig(NTripleTestHelper._defaultField,
      "int4", new IntNumericAnalyzer(4));
    
    final String ntuple = "<http://stephane> <http://price> <ie> \"500\"^^<int4> <pl> \"25\"^^<int4> .\n";

    String query = " [3]'[100 TO 2000]'^^<int4>";
    assertTrue(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = "[5]'[100 TO 2000]'^^<int4>";
    assertFalse(TabularQueryParserTestHelper.match(ntuple, query));
    
    query = "[5]'[3 TO 30]'^^<int4>";
    assertTrue(TabularQueryParserTestHelper.match(ntuple, query));
  }
  
  @Test
  public void testScatteredTabularMultiFieldQuery()
  throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
    final Map<String, Float> boosts = new HashMap<String, Float>();
    boosts.put(NTripleTestHelper._defaultField, 1.0f);
    boosts.put(NTripleTestHelper._implicitField, 1.0f);
    Map<String, String> ntuples = new HashMap<String, String>();
    ntuples.put(NTripleTestHelper._defaultField, "<http://s> <http://p1> \"literal\" \"literal2\" \"literal3\" .\n");
    ntuples.put(NTripleTestHelper._implicitField, "<http://s> <http://p2> <http://o> <http://o1> .\n");
    final String query = "[0]<http://s> [4]'literal3' AND\r\n [1]<http://p2> \n\r \n [2]<http://o>";

    // Should match, the two field content are matching either one of the two triple patterns
    assertTrue(TabularQueryParserTestHelper.match(ntuples, boosts, query, true));

    ntuples = new HashMap<String, String>();
    ntuples.put(NTripleTestHelper._implicitField, "<http://s> <http://p2> <http://o2> <http://o1> .\n");

    // Should not match, only the first field content is matching one triple pattern
    assertFalse(TabularQueryParserTestHelper.match(ntuples, boosts, query, true));
  }
  
}
