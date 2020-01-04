package iostrategy;

import circularorbit.ConcreteCircularOrbit;

public class ReadFiles {

  private Readers reader;

  public ReadFiles(Readers reader) {
    this.reader = reader;
  }

  public ConcreteCircularOrbit readfile(ConcreteCircularOrbit orbit, String filename) {
    return  reader.readfile(orbit, filename);
  }
}
