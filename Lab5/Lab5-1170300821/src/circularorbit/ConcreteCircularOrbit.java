package circularorbit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import track.Track;

public abstract class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {

  public Set<Track> trackSEt = new HashSet<Track>();//轨道的集合
  public Set<E> objectSEt = new HashSet<E>();//轨道上物体的集合
  public Set<L> centraLObjectSet = new HashSet<L>();//中心物体的集合
  public Map<Track, List<E>> relatioNMap = new HashMap<Track, List<E>>();//轨道和物体的对应关系
  public Map<E, E> objecTRelation = new HashMap<E, E>();//物体间的关系


  /**
   * 增加中心点物体.
   */
  @Override
  public void addCentralObject(L object) {
    centraLObjectSet.add(object);
  }

  /**
   * 增加一条轨道.
   */
  @Override
  public void addTrack(Track t) {
    boolean flag = true;
    for (Track track : trackSEt) {
      if (track.equal(t)) {
        flag = false;
      }
    }
    if (flag) {
      trackSEt.add(t);
    }
  }

  /**
   * 去除一条轨道.
   */
  @Override
  public void removeTrack(Track t) {
    Iterator<Track> it = trackSEt.iterator();
    while (it.hasNext()) {
      Track temp = it.next();
      if (temp.equal(t)) {
        it.remove();
      }
    }
  }

  /**
   * 向特定轨道上增加一个物体.
   */
  @Override
  public void addObjectToTrack(E object, Track t) {
    objectSEt.add(object);
    List<Track> list = new ArrayList<Track>(relatioNMap.keySet());
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equal(t)) {
        relatioNMap.get(list.get(i)).add(object);
      }
    }
  }

  /**
   * 增加两个轨道物体之间的关系.
   */
  @Override
  public void addRealationship(E object1, E object2) {
    objecTRelation.put(object1, object2);
  }

  /**
   * 从外部文件读取数据构造轨道系统对象.
   */
  @Override
  public boolean readFiles(String filename) {
    return true;
  }
}
