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
