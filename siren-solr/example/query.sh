#!/bin/sh

echo "Query: galway"
curl "http://localhost:8080/siren/select?indent=on&q=galway&start=0&rows=10&fl=*%2Cscore&qt=siren&wt=standard"

echo "Query: * <name> 'john AND gartner'"
curl "http://localhost:8080/siren/select?indent=on&nq=*+%3Cname%3E+%27john+AND+gartner%27&start=0&rows=10&fl=*%2Cscore&qt=siren&wt=standard" -H 'Content-type:text/xml; charset=utf-8'

echo "Query: * <workplace> <galway> AND * <type> <student> AND * <age> \"26\""
curl "http://localhost:8080/siren/select?indent=on&nq=*+%3Cworkplace%3E+%3Cgalway%3E+AND+*+%3Ctype%3E+%3Cstudent%3E+AND+*+%3Cage%3E+%2226%22&start=0&rows=10&fl=*%2Cscore&qt=siren&wt=standard" -H 'Content-type:text/xml; charset=utf-8'

echo "Query: galway AND * <type> <student>"
curl "http://localhost:8080/siren/select?indent=on&version=2.2&q=galway&nq=*+%3Ctype%3E+%3Cstudent%3E&fq=&start=0&rows=10&fl=*%2Cscore&qt=siren&wt=standard"
