package helper;

import ADT.Ladder;
import ADT.Monkey;
import java.util.List;

public interface FindALadder {

  public Ladder choose(List<Ladder> ladders, Monkey monkey);
}
