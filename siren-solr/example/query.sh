#!/bin/sh
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

echo "Query: * <name> 'john AND gartner'"
curl "http://localhost:8080/siren/select?indent=on&q=*+%3Cname%3E+%27john+AND+gartner%27&start=0&rows=10&fl=*%2Cscore&qt=ntriple&wt=standard" -H 'Content-type:text/xml; charset=utf-8'

echo "Query: * <workplace> <galway> AND * <type> <student> AND * <age> \"26\""
curl "http://localhost:8080/siren/select?indent=on&q=*+%3Cworkplace%3E+%3Cgalway%3E+AND+*+%3Ctype%3E+%3Cstudent%3E+AND+*+%3Cage%3E+%2226%22&start=0&rows=10&fl=*%2Cscore&qt=ntriple&wt=standard" -H 'Content-type:text/xml; charset=utf-8'