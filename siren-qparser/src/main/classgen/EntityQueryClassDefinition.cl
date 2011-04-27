package org.sindice.siren.qparser.entity.query.model;

attr "org.apache.lucene.search.BooleanQuery" query with Query;
attr "org.sindice.siren.search.SirenTupleQuery" query with Clause;
attr "org.sindice.siren.search.SirenCellQuery" query with EClause;
                  
Query         ::= Clause*
                  
Clause        ::=   {EQuery} String:field EClauseList:eclauses "int":op
                  | {KQuery} String:field KClauseList:kclauses "int":op
                  
EClauseList   ::= EClause*
                  
EClause       ::=   {AVClause} KClauseList:a KClauseList:v "int":op
                  | {AClause} KClauseList:a "int":op
                  | {VClause} KClauseList:v "int":op
                  
KClauseList   ::= KClause*
                  
KClause       ::= String:term "int":op

Operator      ::= enum PLUS, MINUS, NONE