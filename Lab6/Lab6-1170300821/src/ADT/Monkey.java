package ADT;

import application.Simulator;
import helper.FindALadder;
import helper.Strategy1;
import helper.Strategy2;
import java.util.List;
import org.apache.log4j.Logger;

public class Monkey implements Runnable {

  /*
   * AF(ID,direction,speed
   * status,localRung,localLadder,birthtime,totalTime)=Monkey; RI:direction must
   * be "L -> R" or " R -> L",speed,ID,birthday,totalTime must be positive. Safety
   * From Representation Exposure: all the fields are private, and unmutable.
   * Thread Safety : add lock to localRung.
   */
  private int ID = 0;
  private String direction;
  private int speed = 0;
  private int status;
  private Rung localRung = null;
  private Ladder localLadder = null;
  private long birthday = 0;
  private long totalTime = 0;

  private void checkRep() {
    assert direction.equals("L->R") || direction.equals("R->L");
    assert speed >= 0;
    assert birthday >= 0;
    assert totalTime >= 0;
    assert ID >= 0;
  }

  public int getStatus() {
    return status;
  }

  public long getBirthday() {
    return birthday;
  }

  public void setBirthday(long birthday) {
    this.birthday = birthday;
  }

  public static final Logger mylog = Logger.getLogger("Monkey Logger");

  public Rung getLocalRung() {
    return localRung;
  }

  public void setLocalRung(Rung localRung) {
    this.localRung = localRung;
  }

  public Ladder getLocalLadder() {
    return localLadder;
  }

  public void setLocalLadder(Ladder localLadder) {
    this.localLadder = localLadder;
  }

  public int getID() {
    return ID;
  }

  public void setID(int iD) {
    ID = iD;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public int getSpeed() {
    return speed;
  }

  public long getTotalTime() {
    return totalTime;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public Monkey(int ID, String direction, int speed) {
    setID(ID);
    setDirection(direction);
    setSpeed(speed);
    status = -1;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Monkey [ID=");
    builder.append(ID);
    builder.append(", direction=");
    builder.append(direction);
    builder.append(", speed=");
    builder.append(speed);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ID;
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    long temp;
    temp = Double.doubleToLongBits(speed);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    if (!(obj instanceof Monkey)) {
      return false;
    }
    Monkey other = (Monkey) obj;
    if (ID != other.ID) {
      return false;
    }
    if (direction == null) {
      if (other.direction != null) {
        return false;
      }
    } else if (!direction.equals(other.direction)) {
      return false;
    }
    if (Double.doubleToLongBits(speed) != Double.doubleToLongBits(other.speed)) {
      return false;
    }
    return true;
  }

  @Override
  public void run() {
    int choice = 1;
    try {
      // 选择策略一来选择梯子
      switch (choice) {
        case 0:
          FindALadder chooosestrategy = new Strategy1();
          Ladder tmpLadder = chooosestrategy.choose(Simulator.ladders, this);
          setLocalLadder(tmpLadder);
          if (localLadder != null) {
            localRung = localLadder.getStart();
          }
          long currenttime = 0;
          tmpLadder = getLadder(chooosestrategy, tmpLadder);
          if (tmpLadder.getMonkeyNumber() == 0) {
            tmpLadder.setDirection(direction);
          }
          status = 1;
          localLadder.setMonkeynumber();
          crossRiver();
          checkRep();
          break;
        case 1:
          FindALadder choosestrategy = new Strategy2();
          Ladder tmpLadder2 = choosestrategy
              .choose(Simulator.ladders, this);
          currenttime = 0;
          setLocalLadder(tmpLadder2);
          if (localLadder != null) {
            localRung = localLadder.getStart();
          }
          tmpLadder2 = getLadder(choosestrategy, tmpLadder2);
          if (tmpLadder2.getMonkeyNumber() == 0) {
            tmpLadder2.setDirection(direction);
          }
          status = 1;
          localLadder.setMonkeynumber();
          crossRiver();
          checkRep();
          break;
        default:
          break;

      }
    } catch (SecurityException e1) {
      e1.printStackTrace();
    }

  }

  private void crossRiver() {
    long currenttime;
    synchronized (localRung) {// 对每只猴子脚下的踏板进行加锁
      while (!localRung.equals(localLadder.getEnd())) {// 猴子开始在梯子上行走
        List<Rung> tmplist = localLadder.getRungList();
        int localposition = tmplist.indexOf(localRung);
        currenttime = System.currentTimeMillis();// 获取当前时间
        mylog
            .info("Monkey " + ID + "  finds a ladder " + localLadder.getID() + " and is running on "
                + localposition + " rung " + "has used " + (currenttime - birthday) / 1000
                + " secs.");
        boolean flag = true;
        int tmpposition = 0;
        int targetposition = 0;
        if (localposition + speed < localLadder.getCount()) {
          targetposition = localposition + speed;
        } else {
          targetposition = localLadder.getCount();
        } // 判断猴子当前位置到最远能到达的位置上是否有其他猴子
        for (int i = localposition; i < targetposition; i++) {
          flag = flag & (!tmplist.get(i).hasMonkey());
          if (tmplist.get(i).hasMonkey()) {
            tmpposition = i;
          }
        }
        if (flag) {
          if (localposition + speed < localLadder.getCount()) {
            localRung = tmplist.get(localposition + speed);
            localLadder.getRungList().get(localposition + speed).setMonkey(this);
            localLadder.getRungList().get(localposition).setMonkey(null);
            localposition = targetposition;
          } // 没有就按速度大小前进，并且不会到达终点
          else {
            localRung = localLadder.getEnd();
            localLadder.getRungList().get(localLadder.getCount() - 1).setMonkey(this);
            localLadder.getRungList().get(localposition).setMonkey(null);
            localposition = targetposition;
          }// 按速度大小前进，会到达终点
        } else {
          localRung = tmplist.get(tmpposition);
          localLadder.getRungList().get(tmpposition).setMonkey(this);
          localLadder.getRungList().get(localposition).setMonkey(null);
          localposition = tmpposition;
        } // 有就在其后排队
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      if (localRung.equals(localLadder.getEnd())) {
        localRung.setMonkey(null);
        currenttime = System.currentTimeMillis();
        totalTime = (currenttime - birthday) / 1000;
        mylog.info("Monkey  " + ID + " has passed the river total used "
            + (currenttime - birthday) / 1000 + " secs.");
        status = 10;
      } // 到达终点，从梯子上将其撤下
    }
  }

  private Ladder getLadder(FindALadder chooosestrategy, Ladder tmpLadder) {
    long currenttime;
    while (localRung == null) {
      try {
        currenttime = System.currentTimeMillis();
        mylog.info("Monkey " + ID + " has been waiting on the bank for "
            + (currenttime - birthday) / 1000 + "secs.");
        Thread.sleep(1000);
        tmpLadder = chooosestrategy.choose(Simulator.ladders, this);
        localRung = tmpLadder.getStart();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } // 猴子没能按照策略选择到合适的梯子，因此在岸上等待
    return tmpLadder;
  }


}
