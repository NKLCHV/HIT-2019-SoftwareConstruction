package ADT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ladder {

  /*
   * AF(count rungList,monkeyNumber,direction,start,end,ID)=Ladder;
   * RI: count,ID, monkeyNumber must not be negative, direction must not be null.
   * Safety from Representation Exposure: All the key fields are private and immutable.
   */
  private int count = 0;
  public List<Rung> rungList = null;
  private int monkeyNumber = 0;
  private String direction;
  private Rung start;
  private Rung end;
  private int ID = 0;

  private void checkRep() {
    assert direction.equals("L->R") || direction.equals("R->L") || direction.equals("");
    assert monkeyNumber >= 0;
    assert ID >= 0;
    assert monkeyNumber >= 0;
  }

  public int getID() {
    return ID;
  }

  public void setID(int iD) {
    ID = iD;
  }

  public Rung getStart() {
    return start;
  }

  public void setStart() {
    start = rungList.get(0);
  }

  public Rung getEnd() {
    return end;
  }

  public void setEnd() {
    end = rungList.get(count - 1);
  }

  public Ladder(int count, String direction, int ID) {
    setCount(count);
    rungList = new ArrayList<>(count);
    for (int i = 0; i < count; i++) {
      Rung tmprung = new Rung(null, i);
      rungList.add(tmprung);
    }
    setStart();
    setEnd();
    setDirection(direction);
    setID(ID);
    checkRep();
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }


  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<Rung> getRungList() {
    return rungList;
  }

  public void setMonkeynumber() {
    int temp = 0;
    Iterator<Rung> riIterator = rungList.iterator();
    while (riIterator.hasNext()) {
      Rung rung = (Rung) riIterator.next();
      if (rung.hasMonkey()) {
        temp = temp + 1;
      }
    }
    monkeyNumber = temp;
  }

  /*
   * @return 梯子上猴子的数量
   */
  public int getMonkeyNumber() {
    int temp = 0;
    Iterator<Rung> riIterator = rungList.iterator();
    while (riIterator.hasNext()) {
      Rung rung = (Rung) riIterator.next();
      if (rung.hasMonkey()) {
        temp = temp + 1;
      }
    }
    checkRep();
    return temp;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Ladder [count=");
    builder.append(count);
    builder.append(",  direction=");
    builder.append(direction);
    builder.append(",  rungList=");
    builder.append(rungList);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + count;
    result = prime * result + ((rungList == null) ? 0 : rungList.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Ladder)) {
      return false;
    }
    Ladder other = (Ladder) obj;
    if (count != other.count) {
      return false;
    }
    if (rungList == null) {
      if (other.rungList != null) {
        return false;
      }
    } else if (!rungList.equals(other.rungList)) {
      return false;
    }
    return true;
  }

}
