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
package org.sindice.siren.qparser.entity;

import org.apache.lucene.util.Version;
import org.junit.Test;
import org.sindice.siren.qparser.entity.query.TabularEntityQueryBuilder;
import org.sindice.siren.qparser.entity.query.model.EClauseList;
import org.sindice.siren.qparser.entity.query.model.EQuery;
import org.sindice.siren.qparser.entity.query.model.KClause;
import org.sindice.siren.qparser.entity.query.model.KClauseList;
import org.sindice.siren.qparser.entity.query.model.Query;
import org.sindice.siren.qparser.entity.query.model.VClause;


public class EntityQueryBuilderTest {

  private final String  _field = "triple";
  private final Version _version = Version.LUCENE_31;
  
  /**
   * Test a TriplePattern composed of 3 URI: s, p, o.
   */
  @Test
  public void test1() {
    final Query q = new Query(new EQuery("field", new EClauseList(new VClause(new KClauseList(new KClause("term", 0)), 0)), 0));

    final TabularEntityQueryBuilder translator = new TabularEntityQueryBuilder(_version, new String[] {});
    q.accept(translator);
  }

} 
