package APIs;

import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import ortherAPIs.Difference;

public abstract class CircularOrbitAPIs<L, E> {
    /**
     * 计算多轨道系统中各轨道上物体分布的熵值
     * @param c
     * @return
     */
    public double getObjectDistributionEntropy(CircularOrbit c) {
        //
        ConcreteCircularOrbit cCon = (ConcreteCircularOrbit) c;
        int objectNumber = cCon.objectSet.size();
        double entropy = 1;



        return entropy;
    }

    /**
     * 计算任意两个物体之间的最短逻辑距离
     * @param c
     * @param e1
     * @param e2
     * @return
     */
    public int getLogicalDistance(CircularOrbit c, E e1, E e2) {
        int distance = 0;
        return distance;
    }

    /**
     * 计算任意两个物体之间的物理距离
     * @param c
     * @param e1
     * @param e2
     * @return
     */
    public double getPhysicalDistance(CircularOrbit c, E e1, E e2) {
        double distance = 0;
        return distance;
    }

    /**
     * 计算两个多轨道系统之间的差异
     * @param c1
     * @param c2
     * @return
     */
    public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
        return null;
    }
}
