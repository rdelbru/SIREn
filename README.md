# SIREn: Efficient semi-structured Information Retrieval for Lucene/Solr

## Introduction

Efficient, large scale handling of semi-structured data (including RDF) is
increasingly an important issue to many web and enterprise information reuse 
scenarios.

While Lucene has long offered these capabilities, its native capabilities are
not intended for large semi-structured document collections (or documents with
very different schemas). For this reason we developed SIREn - Semantic
Information Retrieval Engine - a Lucene/Solr plugin to overcome these shortcomings
and efficiently index and query RDF, as well as any textual document with an
arbitrary amount of metadata fields.

SIREn is a Lucene/Solr extension for effificent semi-structured full-text search.
SIREn is not a complete application by itself, but rather a code library and API
that can easily be used to create a full-featured semi-structured search engine.

## Reference

If you are using SIREn for your scientific work, please cite the following article
as follow:

Renaud Delbru, Stephane Campinas, Giovanni Tummarello, **Searching web data: An 
entity retrieval and high-performance indexing model**, *In Web Semantics: 
Science, Services and Agents on the World Wide Web*, ISSN 1570-8268, 
[10.1016/j.websem.2011.04.004](http://www.sciencedirect.com/science/article/pii/S1570826811000230).

## Resources

SIREn web site:
  http://siren.sindice.com/

You can download SIREn at:
  https://github.com/rdelbru/SIREn

Please join the SIREn-User mailing list by subscribing at:
  http://lists.deri.org/mailman/listinfo/siren

