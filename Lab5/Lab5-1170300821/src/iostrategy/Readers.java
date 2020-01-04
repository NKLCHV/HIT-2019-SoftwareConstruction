package iostrategy;

import circularorbit.ConcreteCircularOrbit;

public interface Readers {
  public ConcreteCircularOrbit readfile(ConcreteCircularOrbit orbit0, String filename);
}
