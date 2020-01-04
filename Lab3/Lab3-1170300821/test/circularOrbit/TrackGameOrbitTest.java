package circularOrbit;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class TrackGameOrbitTest {
    private TrackGameOrbit C1 = new TrackGameOrbit();
    @Test
    public void test1(){
        C1.readFiles("src\\text\\TrackGame.txt");
        assertEquals(5,C1.numOfTracksInFile);
        assertEquals("100",C1.gameType);
        assertEquals(13,C1.objectSet.size());
    }
}
