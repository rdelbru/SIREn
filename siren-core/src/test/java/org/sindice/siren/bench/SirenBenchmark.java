/**
 * Copyright 2010, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 21 Jan 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.bench;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.sindice.siren.util.IOUtils;

import com.google.caliper.SimpleBenchmark;

public class SirenBenchmark extends SimpleBenchmark {

  protected Random rand = new Random(42);

  File[] benchmarkFiles = new File[] {
    new File("./src/test/resources/demo/ntriples/foaf1.nt"),
    new File("./src/test/resources/demo/ntriples/foaf2.nt"),
    new File("./src/test/resources/data/ntriples/apple-freebase.nt"),
    new File("./src/test/resources/data/ntriples/cloudland.nt"),
    new File("./src/test/resources/data/ntriples/dagoneye.nt"),
    new File("./src/test/resources/data/ntriples/dblp.nt"),
    new File("./src/test/resources/data/ntriples/france.nt"),
    new File("./src/test/resources/data/ntriples/galway.nt"),
    new File("./src/test/resources/data/ntriples/resex.nt"),
    new File("./src/test/resources/data/ntriples/toystory3.nt")
  };

  protected String readNTriplesFile(final File file) throws IOException {
    final List<String> lines = FileUtils.readLines(file);
    return IOUtils.sortAndFlattenNTriples(lines.toArray(new String[lines.size()]));
  }

  protected File nextFile() {
    return benchmarkFiles[rand.nextInt(10)];
  }

}
