# SIREn Solr Example

This is an example version of Solr 4.0 with SIREn. This directory contains an 
instance of the Jetty Servlet container setup to run Solr using an example 
configuration. We provide a solr configuration example which use SIREn for 
indexing and searching JSON data.

## Starting Solr

To run this example:

    $ java -jar start.jar

in this example directory, and when Solr is started connect to 

    http://localhost:8983/solr/
    
## Loading the dataset

To add documents to the index, use the load.sh script (while Solr is running):

    $ bash load.sh

This example executes the class NCPRIndexer.java which loads into Solr the 
National Charge Point Registry dataset in JSON format.

If you go to the statistic page 

    http://localhost:8983/solr/#/collection1

of the Solr admin interface, you should see the counter 'Num Docs' equals to 
1078 (the NCPR dataset contains 1078 JSON documents). 

## Querying the dataset

You can execute queries using the Solr admin interface

    http://localhost:8983/solr/#/collection1/query

The keyword query handler is configured by default. You can enter in the field
'q' a simple keyword query

    Newcastle
    
to get a list of JSON objects containing this term

    http://localhost:8983/solr/collection1/select?q=Newcastle&wt=xml
    
or more complex query

    DeviceOwner : { OrganisationName : transport scotland }
    
to get a list of JSON objects which are owned by the organisation 'Transport 
Scotland'

    http://localhost:8983/solr/collection1/select?q=DeviceOwner+%3A+{+OrganisationName+%3A+Transport+Scotland+}&wt=xml

You can also switch to the JSON query handler by replacing '/select' by 'json'
in the field 'Request-Handler (qt)'. For example, you can enter the previous
simple query in JSON query syntax:

	{ "node" : { "query" : "Newcastle" } }

to get a list of JSON objects containing this term

	http://localhost:8983/solr/collection1/select?q={+%22node%22+%3A+{+%22query%22+%3A+%22Newcastle%22+}+}&wt=xml&qt=json

We provide also some examples on how to query programmatically the Solr 
instance in the class NCPRQuery.java. You can use the query.sh script to execute
it.

## Notes About These Examples

For more information please read...

 * example/solr/README.txt
   For more information about the "Solr Home" and Solr specific configuration
 * http://lucene.apache.org/solr/tutorial.html
   For a Tutorial on how to use Solr
 * http://wiki.apache.org/solr/SolrResources 
   For a list of other tutorials and introductory articles.

### SolrHome

By default, start.jar starts Solr in Jetty using the default Solr Home
directory of "./solr/" (relative to the working directory of the servlet 
container). To run other example configurations, you can specify the 
solr.solr.home system property when starting jetty.

    java -Dsolr.solr.home=/path/to/solr/home -jar start.jar

### Logging

By default, Jetty & Solr will log to the console. This can be convenient when 
first getting started, but eventually you will want to log to a file. To 
configure logging, you can just pass a system property to Jetty on startup:

    java -Djava.util.logging.config.file=etc/logging.properties -jar start.jar
 
This will use Java Util Logging to log to a file based on the config in
etc/logging.properties. Logs will be written in the logs directory. It is
also possible to setup log4j or other popular logging frameworks.

- - -

This file was written by Renaud Delbru <renaud.delbru@deri.org> for SIREn.

Copyright (c) 2012
Renaud Delbru,
Digital Enterprise Research Institute,
National University of Ireland, Galway.
All rights reserved.
