package circularorbit;

import track.Track;

public interface CircularOrbit<L, E> {

  /**
   * 增加中心点物体.
   */
  public void addCentralObject(L object);

  /**
   * 增加一条轨道.
   */
  public void addTrack(Track t);

  /**
   * 去除一条轨道.
   */
  public void removeTrack(Track t);

  /**
   * 向特定轨道上增加一个物体.
   */
  public void addObjectToTrack(E object, Track t);

  /**
   * 增加两个轨道物体之间的关系.
   */
  public void addRealationship(E object1, E object2);

  /**
   * 从外部文件读取数据构造轨道系统对象.
   */
  public boolean readFiles(String filename);
}
