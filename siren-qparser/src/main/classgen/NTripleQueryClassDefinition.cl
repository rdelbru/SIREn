package org.sindice.siren.qparser.ntriple.query.model;

attr "org.apache.lucene.search.Query" query with TriplePattern, Expression, Clause, NTripleQuery, Value;
attr "java.util.Map" queries with TriplePattern, Expression, Clause, NTripleQuery, Value;

NTripleQuery  ::= {ClauseQuery} Clause:c
                | {EmptyQuery}

Clause        ::= {UnaryClause} Expression:expr
                | {BinaryClause} Expression:lhe "int":op Expression:rhe
                | {NestedClause} Clause:lhc "int":op Expression:rhe

Expression    ::= {SimpleExpression} TriplePattern:tp
                | {QueryExpression} NTripleQuery:q

TriplePattern ::= Resource:s Resource:p Value:o

Value         ::=   {Resource}
                  | {Literal} "org.sindice.siren.qparser.ntriple.DatatypeValue":l
                  | {LiteralPattern} "org.sindice.siren.qparser.ntriple.DatatypeValue":lp
                      
Resource      ::=   {URIPattern} "org.sindice.siren.qparser.ntriple.DatatypeValue":up
                  | {Wildcard} String:v

Operator      ::= enum AND, OR, MINUS