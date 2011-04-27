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
