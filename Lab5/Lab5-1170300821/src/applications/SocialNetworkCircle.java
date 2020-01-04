package applications;

import api.SocialNetworkCircleApi;
import circularorbit.SocialNetworkCircleOrbit;
import java.util.Iterator;
import org.apache.log4j.Logger;
import physicalobject.Friend;
import track.SocialNetworkCircleTrack;

public class SocialNetworkCircle {

  private SocialNetworkCircleOrbit orbit = new SocialNetworkCircleOrbit();
  private SocialNetworkCircleApi api = new SocialNetworkCircleApi();
  private boolean inited = false;

  public Logger logger = Logger.getLogger(SocialNetworkCircle.class);

  public SocialNetworkCircleOrbit getOrbit() {
    return orbit;
  }

  /**
   * 初始化.
   *
   * @param filename 文件名
   */
  public void init(String filename) {
    if (filename != null) {
      orbit.readFiles(filename);
      orbit.readFiles1(filename);
      orbit.bfsTrackMaker();
      orbit.makeTreeFriendShip();
      inited = true;
      logger.debug("SocialNetworkCircle has been initialized");
    } else {
      logger.info("SocialNetworkCircle try to init by an empty filename");
    }
  }

  /**
   * 清理轨道系统数据.
   */
  public void clean() {
    orbit = new SocialNetworkCircleOrbit();
    inited = false;
    logger.debug("SocialNetworkCircle has been cleaned");
  }

  /**
   * 判定用户是在哪个轨道上.
   *
   * @param friend 用户
   * @return 用户所在轨道
   */
  public SocialNetworkCircleTrack findFriend(Friend friend) {
    if (inited) {
      if (friend != null) {
        logger.debug("SocialNetworkCircle find a friend");
        return api.findFriend(orbit, friend);
      } else {
        logger.info("SocialNetworkCircle try to find a null friend");
      }
    } else {
      logger.info("SocialNetworkCircle try to find a friend before init");
    }
    return null;
  }

  /**
   * 判定用户是在哪个轨道上.
   *
   * @param name 用户姓名
   * @return 用户所在轨道
   */
  public SocialNetworkCircleTrack findFriend(String name) {
    if (inited) {
      Friend friend = null;
      for (Friend tempfriend : orbit.objectSet) {
        if (tempfriend.getObjectName().equals(name)) {
          friend = tempfriend;
          logger.debug("SocialNetworkCircle find a friend");
        }
      }
      if (friend != null) {
        return api.findFriend(orbit, friend);
      }
    } else {
      logger.info("SocialNetworkCircle try to find a friend before init");
    }
    return null;
  }

  /**
   * 朋友的关联度.
   *
   * @param friend 朋友
   * @return 关联度
   */
  public int informationDiffusion(Friend friend) {
    if (inited) {
      if (friend != null && orbit.objectSet.contains(friend)) {
        logger.debug("SocialNetworkCircle find a friend's informationDiffusion");
        return orbit.treeFriendShip.get(friend).size();
      }
    } else {
      logger
          .info("SocialNetworkCircle try to find a friend's informationDiffusion before init");
    }
    return 0;
  }

  /**
   * 计算两个轨道上的 用户之间的逻辑距离.
   *
   * @param e1 用户1
   * @param e2 用户2
   * @return 当用户不存在时，返回MAX_VALUE
   */
  public int getLogicalDistance(Friend e1, Friend e2) {
    if (inited) {
      if (e1 != null && e2 != null) {
        if (orbit.objectSet.contains(e1) && orbit.objectSet.contains(e2)) {
          logger.debug("SocialNetworkCircle has get logical distance");
          if (e1.equals(e2)) {
            return 0;
          } else {
            return api.getLogicalDistance(orbit, e1, e2);
          }
        } else {
          logger.info("SocialNetworkCircle try to get logical distance by wrong friends");
        }
      }
    }
    logger.info("SocialNetworkCircle try to get logical distance before init");
    return Integer.MAX_VALUE;
  }

  /**
   * 计算两个轨道上的 用户之间的逻辑距离.
   *
   * @param name1 用户1姓名
   * @param name2 用户2姓名
   * @return 当用户不存在时，返回MAX_VALUE
   */
  public int getLogicalDistance(String name1, String name2) {
    Friend e1 = null;
    Friend e2 = null;
    if (inited) {
      for (Friend friend : orbit.objectSet) {
        if (friend.getObjectName().equals(name1)) {
          e1 = friend;
        }
        if (friend.getObjectName().equals(name2)) {
          e2 = friend;
        }
      }

    } else {
      logger.info("SocialNetworkCircle try to get logical distance before init");
    }
    if (e1 != null && e2 != null) {
      return getLogicalDistance(e1, e2);
    }

    return Integer.MAX_VALUE;
  }

  /**
   * 删除一个朋友关系.
   *
   * @param f1 朋友1
   * @param f2 朋友2
   */
  public void deleteARelasion(Friend f1, Friend f2) {
    if (inited) {
      if (orbit.friendship.keySet().contains(f1) && orbit.friendship.get(f1).contains(f2)) {
        Iterator<Friend> it = orbit.friendship.get(f1).iterator();
        while (it.hasNext()) {
          Friend friend = it.next();
          if (friend.equals(f2)) {
            it.remove();
          }
        }
        orbit.bfsTrackMaker();
        orbit.makeTreeFriendShip();
        logger.debug("SocialNetworkCircle  delete a relation");
      } else {
        logger.info("SocialNetworkCircle try to delete a relation between wrong friends");
      }
    } else {
      logger.info("SocialNetworkCircle try to delete a relation before init");
    }

  }

  /**
   * 添加一个朋友关系.
   *
   * @param f1 朋友1
   * @param f2 朋友2
   */
  public void addAnRelasion(Friend f1, Friend f2) {
    if (inited) {
      if (orbit.objectSet.contains(f1)) {
        orbit.friendship.get(f1).add(f2);
        orbit.bfsTrackMaker();
        orbit.makeTreeFriendShip();
        logger.debug("SocialNetworkCircle  add a relation");
      } else {
        logger.info("SocialNetworkCircle try to add a relation between wrong friends");
      }
    } else {
      logger.info("SocialNetworkCircle try to add a relation before init");
    }
  }
}
