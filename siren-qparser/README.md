# SIREn QParser

## Introduction

This module extends the lucene-queryparser module to provide a number of query
parsers to easily create complex queries through rich query languages.
Currently, there are two query languages:

* The **keyword query language**: proposes a language that is designed for
	human-centric interaction. It is based on the Lucene's standard query syntax
	and includes syntactic sugar inspired from the JSON syntax for querying nested
	objects.
* The **JSON query language**: proposes an advanced language that is designed
	for program-centric interaction. This language is a simple JSON serialisation
	of the search API of siren-core and covers all the search features of SIREn.

**Author**: Renaud Delbru
**Email**: renaud.delbru@deri.org

**Author**: Stephane Campinas
**Email**: stephane.campinas@deri.org

## Module Description

* **src/main/java**

    The source code of the query parsers.

* **src/main/javacc**

    The javacc source code of the keyword query parser.

* **src/test/java**

    The source code of the unit tests.

- - -

Copyright 2014, National University of Ireland, Galway
