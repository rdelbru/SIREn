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
package org.sindice.siren.search.node;

import org.apache.lucene.index.Term;
import org.junit.Test;
import org.sindice.siren.search.node.NodeBooleanClause.Occur;
import org.sindice.siren.util.SirenTestCase;

public class TestNodeBooleanQuery extends SirenTestCase {

  @Test
  public void testEquality() throws Exception {
    final NodeBooleanQuery bq1 = new NodeBooleanQuery();
    bq1.add(new NodeTermQuery(new Term("field", "value1")), NodeBooleanClause.Occur.SHOULD);
    bq1.add(new NodeTermQuery(new Term("field", "value2")), NodeBooleanClause.Occur.SHOULD);

    final NodeBooleanQuery bq2 = new NodeBooleanQuery();
    bq2.add(new NodeTermQuery(new Term("field", "value1")), NodeBooleanClause.Occur.SHOULD);
    bq2.add(new NodeTermQuery(new Term("field", "value2")), NodeBooleanClause.Occur.SHOULD);

    assertEquals(bq2, bq1);
  }

  @Test
  public void testSetLevelConstraint() {
    final NodeTermQuery ntq = new NodeTermQuery(new Term("field", "value"));
    final NodeBooleanQuery bq = new NodeBooleanQuery();
    bq.add(ntq, Occur.MUST);
    bq.setLevelConstraint(3);

    assertEquals(3, bq.getLevelConstraint());
    // node queries in node boolean clauses must have been updated
    assertEquals(3, ntq.getLevelConstraint());

    final NodeTermQuery ntq2 = new NodeTermQuery(new Term("field", "value"));
    bq.add(ntq2, Occur.MUST);
    // new clause must have been updated
    assertEquals(3, ntq2.getLevelConstraint());
  }

  @Test
  public void testSetNodeConstraint() {
    final NodeTermQuery ntq = new NodeTermQuery(new Term("field", "value"));
    final NodeBooleanQuery bq = new NodeBooleanQuery();
    bq.add(ntq, Occur.MUST);
    bq.setNodeConstraint(2,6);

    assertEquals(2, bq.lowerBound);
    assertEquals(6, bq.upperBound);
    // node queries in node boolean clauses must have been updated
    assertEquals(2, ntq.lowerBound);
    assertEquals(6, ntq.upperBound);

    final NodeTermQuery ntq2 = new NodeTermQuery(new Term("field", "value"));
    bq.add(ntq2, Occur.MUST);
    // new clause must have been updated
    assertEquals(2, ntq2.lowerBound);
    assertEquals(6, ntq2.upperBound);
  }

  @Test
  public void testSetAncestor() {
    final NodeTermQuery ntq = new NodeTermQuery(new Term("field", "value"));
    final NodeBooleanQuery bq1 = new NodeBooleanQuery();
    bq1.add(ntq, Occur.MUST);

    final NodeBooleanQuery bq2 = new NodeBooleanQuery();

    bq1.setAncestorPointer(bq2);

    assertSame(bq2, bq1.ancestor);
    // node queries in node boolean clauses must have been updated
    assertSame(bq2, ntq.ancestor);

    final NodeTermQuery ntq2 = new NodeTermQuery(new Term("field", "value"));
    bq1.add(ntq2, Occur.MUST);
    // new clause must have been updated
    assertSame(bq2, ntq2.ancestor);
  }

}
