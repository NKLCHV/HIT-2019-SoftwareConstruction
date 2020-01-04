package helper;

import ADT.Ladder;
import ADT.Monkey;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Strategy1 implements FindALadder {

  @Override
  public Ladder choose(List<Ladder> ladders, Monkey monkey) {
    Ladder result = null;
    List<Ladder> templist = Lists.newArrayList();
    Iterator<Ladder> ladderit = ladders.iterator();
    while (ladderit.hasNext()) {
      Ladder ladder = (Ladder) ladderit.next();
      if (ladder.getDirection().equals("")) {
        templist.add(ladder);
      }
    }
    if (templist.isEmpty()) {
      Iterator<Ladder> ladderit2 = ladders.iterator();
      while (ladderit2.hasNext()) {
        Ladder ladder = (Ladder) ladderit2.next();
        if (ladder.getDirection().equals(monkey.getDirection())) {
          templist.add(ladder);
        }
      }
    }
    if (!templist.isEmpty()) {
      Random tmp = new Random();
      int choice = tmp.nextInt(templist.size());
      result = templist.get(choice);
    }

    return result;
  }

}
