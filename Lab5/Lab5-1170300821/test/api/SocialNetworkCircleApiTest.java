package api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import circularorbit.SocialNetworkCircleOrbit;
import org.junit.jupiter.api.Test;
import ortherapis.SocialNetworkCircleDifference;
import track.SocialNetworkCircleTrack;

public class SocialNetworkCircleApiTest {

  @Test
  public void test1() {
    SocialNetworkCircleOrbit orbit = new SocialNetworkCircleOrbit();
    SocialNetworkCircleApi API = new SocialNetworkCircleApi();
    orbit.readFiles("src\\text\\SocialNetworkCircle.txt");
    orbit.readFiles1("src\\text\\SocialNetworkCircle.txt");
    orbit.bfsTrackMaker();
    orbit.makeTreeFriendShip();

    SocialNetworkCircleTrack track = API.findFriend(orbit, orbit.objectSet.get(3));
    assertEquals("FrankLee", orbit.objectSet.get(3).getName());
    assertEquals(2, track.getTrackRadius());

    assertEquals(0.18819167801785988, API.getObjectDistributionEntropy(orbit));

    assertEquals(2, API.getFriendTrackRadius(orbit, orbit.objectSet.get(3)));

    assertEquals(2, API.getLogicalDistance(orbit, orbit.objectSet.get(3), orbit.objectSet.get(2)));

    SocialNetworkCircleOrbit orbit1 = new SocialNetworkCircleOrbit();
    orbit1.readFiles("src\\text\\SocialNetworkCircle.txt");
    orbit1.readFiles1("src\\text\\SocialNetworkCircle.txt");
    orbit1.bfsTrackMaker();
    orbit1.makeTreeFriendShip();
    SocialNetworkCircleDifference difference = (SocialNetworkCircleDifference) API
        .getDifference(orbit, orbit1);
    assertEquals("TommyWong-TommyWong",
        ((SocialNetworkCircleDifference) API.getDifference(orbit, orbit1)).dcentralName);
    assertEquals(0, difference.dtrackNum);
    assertEquals(-2, difference.dfriendNum[1]);
  }
}
