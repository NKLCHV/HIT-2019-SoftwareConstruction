package applications;

import APIs.AtomStructureAPI;
import circularOrbit.AtomStructureOrbit;
import ortherAPIs.AtomStructureDifference;
import physicalObject.Electron;

public class AtomStructure {
    private AtomStructureOrbit C1 = new AtomStructureOrbit();
    private AtomStructureAPI API = new AtomStructureAPI();
    private boolean intied = false;

    public AtomStructureOrbit getAtomStructureOrbit() {
        return C1;
    }

    public void init(String filename) {
        if (filename != null) {
            C1.readFiles(filename);
            intied = true;
        }
    }

    public void clear() {
        C1 = new AtomStructureOrbit();
        intied = false;
    }

    /**
     * 一个电子从t1跃迁到t2
     *
     * @param t1 原轨道
     * @param t2 目标轨道
     */
    public void electronictransition(int t1, int t2) {
        if (intied){
            if (t1 > 0 && t2 > 0 && t1 <= C1.electronTrackNumber && t2 <= C1.electronTrackNumber && t1 != t2) {
               C1 =  API.electronictransition(C1, t1, t2);
            }
        }
    }

    /**
     * 比较本系统和C2系统的不同
     *
     * @param C2 另一个系统
     * @return 无法比较时返回null
     */
    public AtomStructureDifference getDifference(AtomStructureOrbit C2) {
        AtomStructureDifference difference = null;
        if (intied)
            if (C2 != null) {
                difference = (AtomStructureDifference) API.getDifference(C1, C2);
            }
        return difference;
    }

    /**
     * 获得两个轨道间的逻辑距离
     *
     * @param t1 轨道1
     * @param t2 轨道2
     * @return 当轨道不合法时，返回MAX_VALUE
     */
    public int getLogicalDistance(int t1, int t2) {
        int x = Integer.MAX_VALUE;
        if (intied)
            if (t1 > 0 && t2 > 0 && t1 <= C1.electronTrackNumber && t2 <= C1.electronTrackNumber)
                x = API.getLogicalDistance(C1, t1, t2);
        return x;
    }

    /**
     * 计算轨道分布的熵值
     *
     * @return 0-1的double型
     */
    public double getObjectDistributionEntropy() {
        if (intied)
            return API.getObjectDistributionEntropy(C1);
        return 0;
    }

    /**
     * 去除t1上的一个电子
     *
     * @param t1 轨道
     */
    public void removeElectronic(int t1) {
        if (C1 != null)
            if (t1 > 0 && t1 < C1.electronTrackNumber)
               C1 = API.removeElectronic(C1, t1);
    }

    /**
     * 向t1轨道添加一个电子
     *
     * @param t1 轨道
     */
    public void addElectronic(int t1) {
        if (intied)
            if (t1 > 0 && t1 < C1.electronTrackNumber) {
                C1.electronTracks[t1]++;
                Electron electron = new Electron(C1.atomName);
                C1.objectSet.add(electron);
            }
    }
}
