# SIREn Demo Build Instructions

## Introduction

Basic steps:
  0) Install SIREn
  1) Build the demo with Maven
  2) Run the Lucene demo
  3) Run the Solr demo

## Install SIREn

We'll assume you already did this. However, if this is not the case, then
please follows the instructions from the file BUILD.txt at the root of your
SIREn installation.

## Build the demo with Maven

In order to run the demo, we first need to (1) build a jar that contains the
binary of the demo and of all its dependencies, and (2) generate the directory
that contains an instance of Solr with an example configuration.

From the command line, change (cd) into the directory of the siren-demo module
of your SIREn installation. Then, typing

    $ mvn clean package assembly:single

at your shell prompt should run the Maven assembly task.

The Maven assembly task will create the jar with dependencies at the location
"./target/siren-demo-jar-with-dependencies.jar", and a directory
"./target/example/" that contains the Solr instance.

## Run the Lucene demo

Assuming you are still in the directory of the siren-demo module, you can
execute the BNB demo by typing

    $ java -cp ./target/siren-demo-jar-with-dependencies.jar
	             org.sindice.siren.demo.bnb.BNBDemo

or the Movie demo by typing

    $ java -cp ./target/siren-demo-jar-with-dependencies.jar
	             org.sindice.siren.demo.movie.MovieDemo

The demos will index a collection of JSON documents and execute various search
queries. It must output on the standard output the results of the execution.

## Run the Solr demo

Assuming you are still in the directory of the siren-demo module, change (cd)
into the directory of the Solr example:

    $ cd ./target/example/

and follow the instructions in the README.txt file.

- - -

Copyright 2014, National University of Ireland, Galway

