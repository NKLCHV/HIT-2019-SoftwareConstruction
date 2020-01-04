package api;

import circularorbit.CircularOrbit;
import circularorbit.ConcreteCircularOrbit;
import ortherapis.Difference;

public abstract class CircularOrbitApis<L, E> {

  /**
   * 计算多轨道系统中各轨道上物体分布的熵值.
   */
  public double getObjectDistributionEntropy(CircularOrbit c) {
    //
    ConcreteCircularOrbit c1 = (ConcreteCircularOrbit) c;
    int objectNumber = c1.objectSEt.size();
    double entropy = 1;

    return entropy;
  }

  /**
   * 计算任意两个物体之间的最短逻辑距离.
   */
  public int getLogicalDistance(CircularOrbit c, E e1, E e2) {
    int distance = 0;
    return distance;
  }

  /**
   * 计算任意两个物体之间的物理距离.
   */
  public double getPhysicalDistance(CircularOrbit c, E e1, E e2) {
    double distance = 0;
    return distance;
  }

  /**
   * 计算两个多轨道系统之间的差异.
   * @return
   */
  public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
    return null;
  }
}
