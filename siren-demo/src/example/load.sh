#! /bin/bash

CP=./../siren-demo-jar-with-dependencies.jar
INPUT=./json/ncpr-with-datatypes.json

java -cp $CP org.sindice.siren.demo.ncpr.NCPRIndexer $INPUT