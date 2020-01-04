package api;

import circularorbit.AtomStructureOrbit;
import circularorbit.CircularOrbit;
import ortherapis.AtomStructureDifference;

public class AtomStructureApi extends CircularOrbitApis {

  private void checkRep(AtomStructureOrbit orbit) {
    int counter = 0;
    for (int i = 0; i < orbit.electronTracks.length; i++) {
      counter += orbit.electronTracks[i];
    }
    assert counter == orbit.objectSet.size();

    int counter1 = 0;
    for (int i = 0; i < orbit.electronTracks.length; i++) {
      if (orbit.electronTracks[i] != 0) {
        counter1++;
      }
    }
    assert counter1 == orbit.trackSet.size();
  }

  /**
   * 计算轨道分布的熵值.
   */
  @Override
  public double getObjectDistributionEntropy(CircularOrbit c) {
    assert c != null;
    AtomStructureOrbit c1 = (AtomStructureOrbit) c;
    if (c1.electronTrackNumber <= 1) {
      return 0;
    }
    double max = c1.electronTrackNumber;
    double min = 1;

    double[] list = new double[c1.electronTrackNumber];
    for (int i = 0; i < c1.electronTrackNumber; i++) {
      list[i] = ((i + 2) - min) / (max - min + 1);
      //System.out.println(list[i]);
    }

    double sum = 0;
    int sumOfObjects = c1.objectSet.size();
    for (int i = 0; i < list.length; i++) {
      sum += list[i] * c1.electronTracks[i];
    }

    for (int i = 0; i < list.length; i++) {
      list[i] = list[i] / sum;
    }

    double k = 1 / Math.log(sumOfObjects);
    double result = 0;
    for (int i = 0; i < list.length; i++) {
      result += -k * list[i] * Math.log(list[i]);
    }
    checkRep(c1);
    assert result >= 0 && result <= 1;
    return result;
  }

  /**
   * 计算逻辑距离.
   *
   * @param c 轨道系统
   * @param t1 轨道1
   * @param t2 轨道2
   * @return 距离
   */
  public int getLogicalDistance(CircularOrbit c, int t1, int t2) {
    AtomStructureOrbit orbit = (AtomStructureOrbit) c;
    if (orbit.electronTracks[t1] > 0 && orbit.electronTracks[t2] > 0) {
      return Math.abs(t1 - t2);
    } else {
      return Integer.MAX_VALUE;
    }
  }

  /**
   * 去除一个电子.
   *
   * @param c 原子系统
   * @param i 电子轨道
   * @return 新原子系统
   */
  public AtomStructureOrbit removeElectronic(AtomStructureOrbit c, int i) {
    assert c != null && c.electronTracks[i] > 0;
    c.electronTracks[i]--;
    c.objectSet.remove(c.objectSet.size() - 1);
    assert c != null;
    return c;
  }

  @Override
  public AtomStructureDifference getDifference(CircularOrbit c1, CircularOrbit c2) {
    assert c1 != null && c2 != null;
    AtomStructureOrbit orbit1 = (AtomStructureOrbit) c1;
    AtomStructureOrbit orbit2 = (AtomStructureOrbit) c2;
    AtomStructureDifference difference = new AtomStructureDifference();
    difference.dname = orbit1.atomName + "-" + orbit2.atomName;
    difference.dobjectsNum = orbit1.objectSet.size() - orbit2.objectSet.size();
    difference.dtracksNum = orbit1.trackSet.size() - orbit2.trackSet.size();

    int size = Math.max(orbit1.trackSet.size(), orbit2.trackSet.size());
    difference.ddistributed = new int[size];
    for (int i = 0; i < size; i++) {
      difference.ddistributed[i] = orbit1.electronTracks[i] - orbit2.electronTracks[i];
    }
    checkRep(orbit1);
    checkRep(orbit2);
    assert difference != null;
    return difference;
  }

  /**
   * 电子跃迁.
   *
   * @param orbit 轨道系统
   * @param t1 原轨道
   * @param t2 目标轨道
   * @return 修改后轨道系统
   */
  public AtomStructureOrbit electronictransition(AtomStructureOrbit orbit, int t1, int t2) {
    assert orbit != null && orbit.electronTracks[t1] > 0 && orbit.electronTracks[t2] > 0;
    if (orbit.electronTracks[t1] > 0 && orbit.electronTracks[t2] > 0) {
      orbit.electronTracks[t1]--;
      orbit.electronTracks[t2]++;
    }
    checkRep(orbit);
    assert orbit != null;
    return orbit;
  }
}
