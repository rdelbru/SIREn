# SIREn: Open-Source Semi-Structured Information Retrieval Engine

## Overview

SIREn is a Lucene/Solr extension for efficient schemaless semi-structured full-text search.
SIREn is not a complete application by itself, but rather a code library and API
that can easily be used to create a full-featured semi-structured search engine.

Efficient, large scale handling of semi-structured data is an increasingly
important issue in information search scenarios on the web as well as in the enterprise..

While Lucene has long offered these capabilities, its native capabilities are
not intended for collections of schemaless semi-structured documents, e.g.,
collections where the schema varies across documents or collections with a
complex schema and a complex nested structure. For this reason we have developed SIREn, a
Lucene/Solr plugin to overcome these shortcomings and to efficiently index and
query complex JSON documents with arbitrary schema.

For its features, SIREn can be seen as being halfway between Solr (of which
it offers all the search features) and MongoDB (given it can index arbitrary
JSON documents).

## Description

The SIREn project is composed of six modules:

* **siren-parent**: This module provides the parent pom that defines
configuration shared across all the other modules.

* **siren-core**: This module provides the core functionality of SIREn such
as the low-level indexing and search APIs.

* **siren-qparser**: This module provides a number of query parsers to easily
create complex queries through rich query languages.

* **siren-solr**: This module provides plugins for Solr to integrate the core
functionality and the query languages of SIREn into the Solr API.

* **siren-demo**: This module provides a demonstration of the functionality of
SIREn.

## Reference

If you are using SIREn for your scientific work, please cite the following article
as follow:

> Renaud Delbru, Stephane Campinas, Giovanni Tummarello, **Searching web data: An
> entity retrieval and high-performance indexing model**, *In Web Semantics:
> Science, Services and Agents on the World Wide Web*, ISSN 1570-8268,
> [10.1016/j.websem.2011.04.004](http://www.sciencedirect.com/science/article/pii/S1570826811000230).

## Resources

* The SIREn [Java Documentation](http://rdelbru.github.io/SIREn/site/1.0/apidocs/index.html) where you can find
a technical description of the SIREn architecture and API.
* The SIREn [web site](http://rdelbru.github.io/SIREn/) where you can download SIREn.
* A public talk at the Lucene Revolution 2013 conference: [High Performance JSON Search and Relational Faceted
Browsing with Lucene](http://www.youtube.com/watch?v=-KiZsx8GYtc).
* A [scientific publication](http://renaud.delbru.fr/doc/pub/jws2010-erm.pdf) with details about the data model
and algorithms behind SIREn.


## Community

Please join the SIREn-User mailing list by subscribing at [SIREn-User](https://groups.google.com/d/forum/siren-user).

## Acknowledgements

The SIREn project is based upon works supported by:

* the European FP7 Okkam (GA 215032) and LOD2 (257943) projects,
* the SFI funded project Lion2 under Grant No. SFI/08/CE/I1380,
* the Irish Research Council for Science, Engineering and Technology.

- - -

Copyright 2014, National University of Ireland, Galway
