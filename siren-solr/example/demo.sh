#! /bin/bash

## Compile the java sources
javac -d . -classpath .:./solr/lib/siren-core-0.2.3-SNAPSHOT.jar:./solr/lib/siren-solr-0.2.3-SNAPSHOT.jar:./lib/solr-solrj-3.4.0.jar:./lib/commons-io-2.1.jar *.java

## Run the demos

echo "### IndexQueryNTriple.java"
java -cp .:./lib/solr-solrj-3.4.0.jar:./lib/commons-httpclient-3.1.jar:./lib/slf4j-api-1.6.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-codec-1.5.jar:./lib/commons-io-2.1.jar:./solr/lib/siren-core-0.2.3-SNAPSHOT.jar org.sindice.siren.demo.IndexQueryNTriple

echo "### IndexQueryTabular.java"
java -cp .:./lib/solr-solrj-3.4.0.jar:./lib/commons-httpclient-3.1.jar:./lib/slf4j-api-1.6.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-codec-1.5.jar:./lib/commons-io-2.1.jar:./solr/lib/siren-core-0.2.3-SNAPSHOT.jar org.sindice.siren.demo.IndexQueryTabular

echo "### IndexQueryNTripleAndTabular.java"
java -cp .:./lib/solr-solrj-3.4.0.jar:./lib/commons-httpclient-3.1.jar:./lib/slf4j-api-1.6.4.jar:./lib/commons-logging-1.1.1.jar:./lib/commons-codec-1.5.jar:./lib/commons-io-2.1.jar:./solr/lib/siren-core-0.2.3-SNAPSHOT.jar org.sindice.siren.demo.IndexQueryNTripleAndTabular
