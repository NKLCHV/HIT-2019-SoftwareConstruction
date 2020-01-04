package APIs;

import circularOrbit.TrackGameOrbit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TrackGameAPITest {
    TrackGameAPI API = new TrackGameAPI();
    TrackGameOrbit C1= new TrackGameOrbit();

    @Test
    public void test1(){
        C1.readFiles("src\\text\\TrackGame.txt");
        C1 = API.randomSort(C1);
        assertEquals(3,C1.objectSet.size());

    }
}
