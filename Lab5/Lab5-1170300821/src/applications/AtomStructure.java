package applications;

import api.AtomStructureApi;
import circularorbit.AtomStructureOrbit;
import org.apache.log4j.Logger;
import ortherapis.AtomStructureDifference;
import physicalobject.Electron;

public class AtomStructure {

  private AtomStructureOrbit orbit = new AtomStructureOrbit();
  private AtomStructureApi api = new AtomStructureApi();
  private boolean intied = false;

  public Logger logger = Logger.getLogger(AtomStructure.class);


  public AtomStructureOrbit getAtomStructureOrbit() {
    return orbit;
  }

  /**初始化.
   *
   * @param filename 文件名
   */
  public void init(String filename) {
    if (filename != null) {
      orbit.readFiles(filename);
      intied = true;
      logger.debug("AtomStructure has been initialized");
    } else {
      logger.info("AtomStructure try to init by an empty filename");
    }
  }

  /**
   * 清除系统数据.
   */
  public void clean() {
    orbit = new AtomStructureOrbit();
    intied = false;
    logger.debug("AtomStructure has been cleaned");
  }

  /**
   * 一个电子从t1跃迁到t2.
   *
   * @param t1 原轨道
   * @param t2 目标轨道
   */
  public void electronictransition(int t1, int t2) {
    if (intied) {
      assert t1 > 0 && t2 > 0 && t1 <= orbit.electronTrackNumber && t2 <= orbit.electronTrackNumber
          && t1 != t2;
      if (t1 > 0 && t2 > 0 && t1 <= orbit.electronTrackNumber && t2 <= orbit.electronTrackNumber
          && t1 != t2) {
        orbit = api.electronictransition(orbit, t1, t2);
        logger.debug("AtomStructure electronic transfer successfully");
      } else {
        logger.info("numbers of tracks are wrong");
      }
    } else {
      logger.info("AtomStructure try to electronic transfer before init");
    }
  }

  /**
   * 比较本系统和C2系统的不同.
   *
   * @param c2 另一个系统
   * @return 无法比较时返回null
   */
  public AtomStructureDifference getDifference(AtomStructureOrbit c2) {
    AtomStructureDifference difference = null;
    if (intied) {
      if (c2 != null) {
        difference = api.getDifference(orbit, c2);
        logger.debug("AtomStructure has take difference");
      } else {
        logger.info("AtomStructure try to take difference with an empty system");
      }
    }
    logger.info("AtomStructure try to get difference before init");

    return difference;
  }

  /**
   * 获得两个轨道间的逻辑距离.
   *
   * @param t1 轨道1
   * @param t2 轨道2
   * @return 当轨道不合法时，返回MAX_VALUE
   */
  public int getLogicalDistance(int t1, int t2) {
    int x = Integer.MAX_VALUE;
    if (intied) {
      if (t1 > 0 && t2 > 0 && t1 <= orbit.electronTrackNumber && t2 <= orbit.electronTrackNumber) {
        x = api.getLogicalDistance(orbit,t1,t2);
        logger.debug("AtomStructure has get logical distance");
      } else {
        logger.info("AtomStructure try to get logical distance by wrong tracks");
      }
    } else {
      logger.info("AtomStructure try to get logical distance before init");
    }
    return x;
  }

  /**
   * 计算轨道分布的熵值.
   *
   * @return 0-1的double型
   */
  public double getObjectDistributionEntropy() {
    if (intied) {
      logger.debug("AtomStructure get Object Distribution Entropy");
      return api.getObjectDistributionEntropy(orbit);
    } else {
      logger.info("AtomStructure try to get Object Distribution Entropy before init");
      return 0;
    }
  }

  /**
   * 去除t1上的一个电子.
   *
   * @param t1 轨道
   */
  public void removeElectronic(int t1) {
    if (orbit != null) {
      if (t1 > 0 && t1 < orbit.electronTrackNumber) {
        orbit = api.removeElectronic(orbit, t1);
        logger.debug("AtomStructure remove Electronic");
      } else {
        logger.info("AtomStructure try to remove Electronic from wrong track");
      }
    } else {
      logger.info("AtomStructure try to remove Electronic before init");
    }
  }

  /**
   * 向t1轨道添加一个电子.
   *
   * @param t1 轨道
   */
  public void addElectronic(int t1) {
    if (intied) {
      if (t1 > 0 && t1 <= orbit.electronTrackNumber) {
        orbit.electronTracks[t1]++;
        Electron electron = new Electron(orbit.atomName);
        orbit.objectSet.add(electron);
        logger.debug("AtomStructure add Electronic");
      } else {
        logger.info("AtomStructure try to add Electronic to wrong track");
      }
    }
    logger.info("AtomStructure try to add Electronic before init");
  }
}
