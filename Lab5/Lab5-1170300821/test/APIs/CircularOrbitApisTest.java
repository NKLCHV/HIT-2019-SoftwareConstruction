package apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import circularorbit.CircularOrbit;
import circularorbit.ConcreteCircularOrbit;
import org.junit.jupiter.api.Test;
import ortherapis.Difference;
import track.Track;

public class CircularOrbitApisTest {

  @Test
  public void test1() {
    CircularOrbitApis API = new CircularOrbitApis() {
      @Override
      public double getObjectDistributionEntropy(CircularOrbit c) {
        return super.getObjectDistributionEntropy(c);
      }

      @Override
      public int getLogicalDistance(CircularOrbit c, Object e1, Object e2) {
        return super.getLogicalDistance(c, e1, e2);
      }

      @Override
      public double getPhysicalDistance(CircularOrbit c, Object e1, Object e2) {
        return super.getPhysicalDistance(c, e1, e2);
      }

      @Override
      public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
        return super.getDifference(c1, c2);
      }
    };

    ConcreteCircularOrbit orbit = new ConcreteCircularOrbit() {
      @Override
      public void addCentralObject(Object object) {
        super.addCentralObject(object);
      }

      @Override
      public void addTrack(Track t) {
        super.addTrack(t);
      }

      @Override
      public void removeTrack(Track t) {
        super.removeTrack(t);
      }

      @Override
      public void addObjectToTrack(Object object, Track t) {
        super.addObjectToTrack(object, t);
      }

      @Override
      public void addRealationship(Object object1, Object object2) {
        super.addRealationship(object1, object2);
      }

      @Override
      public boolean readFiles(String filename) {
        return super.readFiles(filename);
      }
    };

    assertEquals(1, API.getObjectDistributionEntropy(orbit));
    assertEquals(0, API.getLogicalDistance(orbit, null, null));
    assertEquals(0, API.getPhysicalDistance(orbit, null, null));
    assertEquals(null, API.getDifference(orbit, null));
  }
}
