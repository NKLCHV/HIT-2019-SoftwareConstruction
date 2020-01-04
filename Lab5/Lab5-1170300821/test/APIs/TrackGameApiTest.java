package apis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import circularorbit.TrackGameOrbit;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TrackGameApiTest {

  TrackGameApi API = new TrackGameApi();
  TrackGameOrbit C1 = new TrackGameOrbit();
  TrackGameOrbit C2 = new TrackGameOrbit();


  @Test
  public void test1() {
    C1.readFiles("src\\text\\TrackGame.txt");
    C2.readFiles("src\\text\\TrackGame.txt");

    C1 = API.randomSort(C1);
    C2 = API.randomSort(C2);
    assertEquals(3, C1.gameGroup.size());

    assertEquals(2147483647,
        API.getLogicalDistance(C1, C1.objectList.get(1), C1.objectList.get(2)));

    assertEquals("Park", API.findAthlete(C1, 1, 1, 1).getName());

    assertEquals(0, API.getDifference(C1, C2).dtracksnum);

    C1 = API.s2bSort(C1);
    assertEquals(3, C1.gameGroup.size());

    API.moveInGroup(C1, C1.objectList.get(1), C1.objectList.get(2));
    assertEquals("Park", API.findAthlete(C1, 1, 1, 1).getName());

    API.moveBetweenGroup(C1, C1.objectList.get(1), C1.objectList.get(12));
    assertEquals("Park", API.findAthlete(C1, 1, 1, 1).getName());

    List<String> di = API.writeDifference(C1.gameGroup.get(0), C1.trackSet.get(0));
    assertEquals("null-Bolt", di.get(0));
  }
}
