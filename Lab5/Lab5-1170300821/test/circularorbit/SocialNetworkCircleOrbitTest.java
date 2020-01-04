package circularorbit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SocialNetworkCircleOrbitTest {

  SocialNetworkCircleOrbit C1 = new SocialNetworkCircleOrbit();

  @Test
  public void test1() {
    SocialNetworkCircleOrbit orbit = new SocialNetworkCircleOrbit();
    orbit.readFiles("src\\text\\SocialNetworkCircle.txt");
    orbit.readFiles1("src\\text\\SocialNetworkCircle.txt");
    assertEquals(7, orbit.objectSet.size());
    assertEquals(6, orbit.objectRelation.size());
    assertEquals(7, orbit.friendship.size());

    orbit.bfsTrackMaker();
    assertEquals(2, orbit.relationMap.size());

    orbit.makeTreeFriendShip();
    assertEquals(2, orbit.treeFriendShip.size());
  }
}
