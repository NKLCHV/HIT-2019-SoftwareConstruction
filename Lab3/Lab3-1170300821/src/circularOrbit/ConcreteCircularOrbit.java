package circularOrbit;

import track.Track;

import java.util.*;

public abstract class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {
    public Set<Track> trackSet = new HashSet<>();//轨道的集合
    public Set<E> objectSet = new HashSet<>();//轨道上物体的集合
    public Set<L> centralObjectSet = new HashSet<>();//中心物体的集合
    public Map<Track, Set<E>> relationMap = new HashMap<>();//轨道和物体的对应关系
    public Map<E, E> objectRelation = new HashMap<>();//物体间的关系


    /**
     * 增加中心点物体
     *
     * @param object
     */
    @Override
    public void addCentralObject(L object) {
        centralObjectSet.add(object);
    }

    /**
     * 增加一条轨道
     *
     * @param t
     */
    @Override
    public void addTrack(Track t) {
        boolean flag = true;
        for (Track track : trackSet) {
            if (track.equal(t)) {
                flag = false;
            }
        }
        if (flag)
            trackSet.add(t);
    }

    /**
     * 去除一条轨道
     *
     * @param t
     */
    @Override
    public void removeTrack(Track t) {
        Iterator<Track> it = trackSet.iterator();
        while (it.hasNext()) {
            Track temp = it.next();
            if (temp.equal(t))
                it.remove();
        }
    }

    /**
     * 向特定轨道上增加一个物体
     *
     * @param object
     * @param t
     */
    @Override
    public void addObjectToTrack(E object, Track t) {
        objectSet.add(object);
        for (Track key : relationMap.keySet()) {
            if (key.equal(t)) {
                relationMap.get(key).add(object);
            }
        }
    }

    /**
     * 增加两个轨道物体之间的关系
     *
     * @param object1
     * @param object2
     */
    @Override
    public void addRealationship(E object1, E object2) {
        objectRelation.put(object1, object2);
    }

    /**
     * 从外部文件读取数据构造轨道系统对象
     *
     * @param filename
     */
    @Override
    public void readFiles(String filename) {

    }
}
