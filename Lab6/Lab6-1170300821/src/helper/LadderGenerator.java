package helper;

import ADT.Ladder;
import com.google.common.collect.Lists;
import java.util.List;

public class LadderGenerator {

  public static List<Ladder> generateLadders(int number, int length) {
    List<Ladder> result = Lists.newArrayList();
    for (int i = 0; i < number; i++) {
      String direction = "";
      Ladder tmpladder = new Ladder(length, direction, i);
      result.add(tmpladder);
    }
    return result;
  }

}
