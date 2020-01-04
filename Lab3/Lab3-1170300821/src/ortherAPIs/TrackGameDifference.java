package ortherAPIs;

import physicalObject.Athlete;
import track.GameTrack;
import track.Track;

import java.util.*;

public class TrackGameDifference extends Difference {
    public int DtracksNum;
    public int DobjectsNum;
    public int DgroupNumbers;
    public List<Map<GameTrack,List<String>>> DgameGroup = new ArrayList<>();
}
