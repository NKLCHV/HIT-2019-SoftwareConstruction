package helper;

import ADT.Monkey;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;


public class MonkeyGenerator {

  public static List<Monkey> generateMonkeys(int generation, int MV, int amount) {
    List<Monkey> result = Lists.newArrayList();
    for (int i = 0; i < amount; i++) {
      int tempid = generation * 10 + i;
      int tmprandom = new Random().nextInt(50);
      String tmpdirection = "";
      if (tmprandom % 2 == 0) {
        tmpdirection = "L->R";
      } else {
        tmpdirection = "R->L";
      }
      int tmpspeed = new Random().nextInt(MV + 1);
      while (tmpspeed == 0) {
        tmpspeed = new Random().nextInt(MV);
      }
      Monkey tmpmonkey = new Monkey(tempid, tmpdirection, tmpspeed);
      tmpmonkey.setBirthday(System.currentTimeMillis());
      result.add(tmpmonkey);
      Thread tmpthread = new Thread(tmpmonkey);
      tmpthread.start();
    }
    return result;
  }
}
