package helper;

import ADT.Ladder;
import ADT.Monkey;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public class Strategy2 implements FindALadder {

  @Override
  public Ladder choose(List<Ladder> ladders, Monkey monkey) {
    Ladder result = null;
    List<Ladder> templist = Lists.newArrayList();
    Iterator<Ladder> ladderit = ladders.iterator();
    while (ladderit.hasNext()) {
      Ladder ladder = (Ladder) ladderit.next();
      if (ladder.getDirection().equals(monkey.getDirection()) || ladder.getDirection().equals("")) {
        templist.add(ladder);
      }
    }
    Iterator<Ladder> ladderit2 = templist.iterator();
    int minnumber = 1000;
    while (ladderit2.hasNext()) {
      Ladder ladder = (Ladder) ladderit2.next();
      if (ladder.getMonkeyNumber() < minnumber) {
        minnumber = ladder.getMonkeyNumber();
        result = ladder;
      }
    }
    return result;
  }

}
