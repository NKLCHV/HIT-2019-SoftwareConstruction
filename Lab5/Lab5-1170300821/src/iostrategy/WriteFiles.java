package iostrategy;

import circularorbit.ConcreteCircularOrbit;

public class WriteFiles {

  public Writters writters;

  public WriteFiles(Writters writters) {
    this.writters = writters;
  }

  public void writeFiles(ConcreteCircularOrbit orbit0,String filename) {
    writters.writeFiles(orbit0,filename);
  }
}
