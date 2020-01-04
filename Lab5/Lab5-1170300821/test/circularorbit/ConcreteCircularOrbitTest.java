package circularorbit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import centralobject.CentralObject;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ortherapis.Position;
import physicalobject.PhysicalObject;
import track.Track;

public class ConcreteCircularOrbitTest {

  private ConcreteCircularOrbit orbit = new ConcreteCircularOrbit() {
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

    @Override
    public int hashCode() {
      return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }

    @Override
    public String toString() {
      return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
      super.finalize();
    }
  };

  @Test
  public void t1() {
    Track track = new Track(1) {
      @Override
      public int getTrackRadius() {
        return super.getTrackRadius();
      }

      @Override
      public boolean equal(Track track) {
        return super.equal(track);
      }

      @Override
      public int hashCode() {
        return super.hashCode();
      }

      @Override
      public boolean equals(Object obj) {
        return super.equals(obj);
      }

      @Override
      protected Object clone() throws CloneNotSupportedException {
        return super.clone();
      }

      @Override
      public String toString() {
        return super.toString();
      }

      @Override
      protected void finalize() throws Throwable {
        super.finalize();
      }
    };
    CentralObject object = new CentralObject("P") {
      @Override
      public String getName() {
        return super.getName();
      }
    };
    PhysicalObject object1 = new PhysicalObject("A", null) {
      @Override
      public String getObjectName() {
        return super.getObjectName();
      }

      @Override
      public Position getPosition() {
        return super.getPosition();
      }
    };
    PhysicalObject object2 = new PhysicalObject("B", null) {
      @Override
      public String getObjectName() {
        return super.getObjectName();
      }

      @Override
      public Position getPosition() {
        return super.getPosition();
      }
    };
    orbit.addTrack(track);
    assertEquals(1, orbit.trackSEt.size());
    orbit.addCentralObject(object);
    assertEquals(1, orbit.centraLObjectSet.size());
    orbit.addRealationship(object1, object2);
    assertEquals(1, orbit.objecTRelation.size());

    List<PhysicalObject> set = new ArrayList<>();
    orbit.relatioNMap.put(track, set);
//        System.out.println(orbit.relationMap.size());
    orbit.addObjectToTrack(object1, track);
    assertEquals(1, orbit.relatioNMap.size());

    orbit.removeTrack(track);
    assertEquals(0, orbit.trackSEt.size());

    assertEquals(true, orbit.readFiles(""));
  }
}
