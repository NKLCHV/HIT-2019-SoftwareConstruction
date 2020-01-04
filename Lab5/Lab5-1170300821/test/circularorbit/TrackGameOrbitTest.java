package circularorbit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import physicalobject.Athlete;
import track.GameTrack;


public class TrackGameOrbitTest {

  private TrackGameOrbit C1 = new TrackGameOrbit();

  @Test
  public void test1() {
    C1.readFiles("src\\text\\TrackGame.txt");
    assertEquals(5, C1.numOfTracksInFile);
    assertEquals("100", C1.gameType);
    assertEquals(13, C1.objectList.size());

    GameTrack track = new GameTrack(1);
    C1.addTrack(track);
    assertEquals(1, C1.trackSet.size());
    C1.removeTrack(track);
    assertEquals(0, C1.trackSet.size());

    Athlete athlete = new Athlete("a", 22, "NNN", 34, 23.32);
    List<Athlete> list = new ArrayList<>();
    Map<GameTrack, List<Athlete>> map = new HashMap<>();
    map.put(track, list);
    C1.gameGroup.add(map);
    C1.addObjectToTrack(athlete, track);
    assertEquals(1, C1.gameGroup.get(0).get(track).size());

    C1.removeObject(track, athlete);
    assertEquals(0, C1.trackSet.size());
  }
}
