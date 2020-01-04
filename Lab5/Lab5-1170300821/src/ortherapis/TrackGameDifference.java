package ortherapis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import track.GameTrack;

public class TrackGameDifference extends Difference {

  public int dtracksNum;
  public int dobjectsNum;
  public int dgroupNumbers;
  public List<Map<GameTrack, List<String>>> dgameGroup = new ArrayList<>();
}
